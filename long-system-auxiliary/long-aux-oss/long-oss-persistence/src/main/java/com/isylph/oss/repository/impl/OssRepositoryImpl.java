package com.isylph.oss.repository.impl;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.domain.entity.OssFileAttachment;
import com.isylph.oss.domain.entity.OssFileLocation;
import com.isylph.oss.domain.types.AttachmentId;
import com.isylph.oss.domain.types.FileGuid;
import com.isylph.oss.domain.types.LocationId;
import com.isylph.oss.domain.types.Module;
import com.isylph.oss.persistence.conf.FileServerManager;
import com.isylph.oss.persistence.converter.OssBuilder;
import com.isylph.oss.persistence.dao.OssFileAttachmentMapper;
import com.isylph.oss.persistence.dao.OssFileLocationMapper;
import com.isylph.oss.persistence.model.OssFileAttachmentPO;
import com.isylph.oss.persistence.model.OssFileLocationPO;
import com.isylph.oss.repository.OssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OssRepositoryImpl implements OssRepository {

    @Autowired
    private OssFileAttachmentMapper ossFileAttachmentMapper;

    @Autowired
    private OssFileLocationMapper ossFileLocationMapper;

    @Autowired
    private OssBuilder ossBuilder;

    @Autowired
    private FileServerManager fileServerManager;

    @Override
    public OssFileAttachment findOssFileAttachment(AttachmentId id) {

        OssFileAttachmentPO po = ossFileAttachmentMapper.selectById(id.getId());
        if (po == null){
            return null;
        }
        return ossBuilder.toOssFileAttachment(po);
    }

    @Override
    public OssFileAttachment findOssFileAttachment(FileGuid id) {
        OssFileAttachmentPO po = ossFileAttachmentMapper.getByGuid(id.getGuid());
        if (po == null){
            return null;
        }
        return ossBuilder.toOssFileAttachment(po);
    }



    @Override
    public int saveOssFileAttachment(OssFileAttachment req) {
        OssFileAttachmentPO po = ossBuilder.toOssFileAttachmentPo(req);
        if (po.getId() == null){
            return ossFileAttachmentMapper.insert(po);
        }else {
            return ossFileAttachmentMapper.updateById(po);
        }
    }

    @Override
    public int removeOssFileAttachment(AttachmentId id) {
        return ossFileAttachmentMapper.deleteById(id.getId());
    }

    @Override
    public int removeOssFileAttachment(FileGuid id) {
        OssFileAttachmentPO po = ossFileAttachmentMapper.getByGuid(id.getGuid());
        if (po == null){
            return 0;
        }
        return ossFileAttachmentMapper.deleteById(po.getId());
    }

    @Override
    public OssFileLocation findOssFileLocation(LocationId id) {
        OssFileLocationPO po = ossFileLocationMapper.selectById(id.getId());
        if (po == null){
            return null;
        }
        return ossBuilder.toOssFileLocation(po);
    }

    @Override
    public OssFileLocation findOssFileLocation(Module module) {
        OssFileLocationPO po = ossFileLocationMapper.getByModule(module.getModule());
        if (po == null){
            return null;
        }
        return ossBuilder.toOssFileLocation(po);
    }

    @Override
    public RetPage<OssFileLocation> queryOssFileLocation(OssFileLocationQuery req) {
        RetPage<OssFileLocation> ret = new RetPage<>();

        int cnt = ossFileLocationMapper.countSelectViewListPage(req);
        if (cnt > 0){
            List<OssFileLocationPO> list = ossFileLocationMapper.selectViewListPage(req);
            ret.setRecords(ossBuilder.toOssFileLocation(list));
        }
        ret.setTotal(cnt);
        return ret;
    }

    @Override
    public int countOssFileLocation(OssFileLocationQuery req) {
        return ossFileLocationMapper.countSelectViewListPage(req);
    }

    @Override
    public int saveOssFileLocation(OssFileLocation req) {
        OssFileLocationPO po = ossBuilder.toOssFileLocationPo(req);
        return ossFileLocationMapper.insert(po);
    }

    @Override
    public int removeOssFileLocation(LocationId id) {
        return ossFileLocationMapper.deleteById(id.getId());
    }

    @Override
    public FileData getFile(FileGuid guid) {
        OssFileAttachmentPO po = ossFileAttachmentMapper.getByGuid(guid.getGuid());
        if (po == null){
            return null;
        }
        return new FileData(po.getGuid(), fileServerManager.assembleUrl(po.getPath()), po.getName());
    }

    @Override
    public List<FileData> getFile(List<FileGuid> guids) {
        if (CollectionUtils.isEmpty(guids)){
            return new ArrayList<>();
        }
        List<FileData> ret = guids.stream().map(guid -> {
            OssFileAttachmentPO po = ossFileAttachmentMapper.getByGuid(guid.getGuid());
            if (po == null){
                return null;
            }
            return new FileData(po.getGuid(), fileServerManager.assembleUrl(po.getPath()), po.getName());

        }).collect(Collectors.toList());
        return ret;
    }
}
