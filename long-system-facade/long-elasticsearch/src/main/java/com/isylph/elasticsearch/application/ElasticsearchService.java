package com.isylph.elasticsearch.application;

import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.elasticsearch.domain.EsEntity;
import com.isylph.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class ElasticsearchService {

    /**
     * ES地址,ip:port
     */
    @Value("${elasticsearch.address}")
    private String address;

    @Autowired
    private RestHighLevelClient highLevelClient;

    /**
     * 创建或更新索引的映射
     * @param indexName
     * @param
     */
    public void createIndex(String indexName, XContentBuilder builder) {
        try {
            boolean acknowledged;
            if (isIndexExists(indexName)){
                PutMappingRequest request = new PutMappingRequest(indexName);
                request.source(builder);
                AcknowledgedResponse res = highLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
                acknowledged = res.isAcknowledged();
            }else{
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));                request.mapping(builder);

                CreateIndexResponse res = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
                acknowledged = res.isAcknowledged();
            }

            if (acknowledged) {
                log.info("Succeeded in create or update index");
                return;
            } else {
                log.warn("Failed to execute the index operation");
                throw new RuntimeException("Failed to create index");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Failed to create or update index, {}.");
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }

    }

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    public boolean isIndexExists(String indexName) {
        boolean exists = false;
        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            request.humanReadable(true);
            request.local(false);

            exists = highLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName) {
        boolean acknowledged = false;
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = highLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

    /**
     * 保存记录
     * @param index
     * @param entity
     */
    public void saveDocument(String index, EsEntity entity) {
        IndexRequest request = new IndexRequest(index);
        request.id(entity.getId());
        request.source(JacksonUtils.serialize(entity.getData()), XContentType.JSON);
        try {
            highLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.warn("failed to save record: {}, {}", entity, e);
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
    }

    /**
     * 批量保存记录
     * @param index
     * @param list
     */
    public void saveDocument(String index, List<EsEntity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(index).id(item.getId())
                .source(JacksonUtils.serialize(item.getData()), XContentType.JSON)));
        try {
            highLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.warn("failed to save record:{}", e);
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
    }

    /**
     * 删除/批量删除记录
     * @param index
     * @param idList
     * @param <T>
     */
    public <T> void deleteDocuments(String index, Collection<T> idList) {
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(index, item.toString())));
        try {
            highLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.warn("failed to delete record:{}, {}", idList, e);
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
    }

    /**
     * 查询文档
     * @param index
     * @param builder
     * @param c
     * @param <T>
     * @return
     */
    public <T> List<T> searchDocuments(String index, SearchSourceBuilder builder, Class<T> c) {

        SearchRequest request = new SearchRequest(index);
        request.source(builder);
        try {
            SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JacksonUtils.deserialize(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            log.warn("failed to search record:{}", e);
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
    }

    /**
     * 删除符合条件的记录
     * @param index
     * @param builder
     */
    public void deleteDocument(String index, QueryBuilder builder) {

        DeleteByQueryRequest request = new DeleteByQueryRequest(index);
        request.setQuery(builder);

        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            highLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.warn("failed to delete record: {}, {}", builder, e);
            throw new ReturnException(BaseErrorConsts.RET_RPC_ERR);
        }
    }
}
