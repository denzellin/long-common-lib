package com.isylph.oss.external.impl;



import com.isylph.oss.domain.types.FileGuid;
import com.isylph.oss.domain.types.RandomGUID;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.OssFileAttachment;
import com.isylph.oss.storage.FileStorage;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository("fileStorageMinio")
public class FileStorageImpl implements FileStorage {

    @Value("${oss.minio.access-key}")
    private String accessKey;
    @Value("${oss.minio.secret}")
    private String secretKey;
    @Value("${oss.minio.url}")
    private String minioUrl;

    private MinioClient minioClient;

    @PostConstruct
    public void init(){

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)  // 连接超时
                .writeTimeout(60, TimeUnit.SECONDS)    // 写超时
                .readTimeout(60, TimeUnit.SECONDS)     // 读超时
                .retryOnConnectionFailure(true)
                .connectionPool(new okhttp3.ConnectionPool(
                        10,      // 最大空闲连接数
                        5,       // 保持时间
                        TimeUnit.MINUTES
                ))
                .build();

        // 构造 MinIO 客户端（复用连接池）
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .httpClient(httpClient)
                .build();
    }

    @Override
    public <T extends GeneralFile> OssFileAttachment saveFile(T file) throws Exception{

        // 检查桶是否存在，不存在则创建
        String bucketName = file.getModule().getModule();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("bucket create: {}", bucketName);
        }

        String storeFileName = generateRandomName(file.getSuffix());
        // 上传文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(storeFileName)     // 对象名
                        .stream( file.getInputStream(),file.getSize(),-1 )
                        .contentType(file.getContentType())
                        .build()
        );

        String guid = new RandomGUID().toString();
        return new OssFileAttachment()
                .setGuid(new FileGuid(guid))
                .setName(file.getFileName())
                .setPath(bucketName + File.separator + storeFileName);
    }

}

