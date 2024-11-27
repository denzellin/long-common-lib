package com.isylph.oss.external.impl;



import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OSSClientTool {

    private OSSClient ossClient;

    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String ossEndpoint;
    private int timeout;

    /**
     * OSS初始化
     */
    public void init() {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(timeout);
        conf.setMaxErrorRetry(10);
        ossClient = new OSSClient("http://" + ossEndpoint, accessKeyId, accessKeySecret, conf);
        System.out.println("OSS初始化");
    }

    public void destroy() {
        ossClient.shutdown();
    }

    /**
     * 指定的key是否存在
     */
    public boolean isExist(String key) {
        key = genKey(key);
        return ossClient.doesObjectExist(bucketName, key);
    }

    /**
     * 从OSS中获取文件输入流
     */
    public InputStream getObjInputStream(String key) {
        key = genKey(key);
        OSSObject obj = ossClient.getObject(bucketName, key);
        return obj.getObjectContent();
    }

    /**
     * 将输入流下载存到指定的File文件中
     */
    public void saveIsToFile(InputStream is, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 10];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.safeClose(fos);
        }
    }


    /**
     * 文件下载,以流的形式
     */
    public void downObj(String key, File file) {
        key = genKey(key);
        InputStream is = getObjInputStream(key);
        saveIsToFile(is, file);
    }


    public String uploadObj(String path, String name, InputStream fileStream) {
        return uploadObj(path, name, fileStream, null);
    }

    /**
     * 简单上传OSS文件
     *
     * @param name        文件名
     * @param fileStream  文件流对象
     * @param path        存储路径
     * @param contentType 手动设置文件类型：image/png
     * @return OSS文件Key的路径
     */
    public String uploadObj(String path, String name, InputStream fileStream, String contentType) {
        String key = path + "/" + name;
        key = genKey(key);
        ObjectMetadata meta = null;
        if (contentType != null) {
            meta = new ObjectMetadata();
            meta.setContentType(contentType);
        }
        ossClient.putObject(bucketName, key, fileStream, meta);

        return "http://" + bucketName + "." + ossEndpoint + "/" + key;
    }

    /**
     * 删除指定key
     */
    public void delObj(String key) {
        ossClient.deleteObject(bucketName, key);
    }

    /**
     * 处理key开头是/,返回开头没有/的key
     */
    private String genKey(String key) {
        if (key != null) {
            key = key.replaceAll("\\\\", "/");
        }
        while (key != null && key.startsWith("/")) {
            key = key.substring(1, key.length());
        }
        return key;
    }


    public OSSClient getOSSClient() {
        return ossClient;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setBucketName(String ossBucket) {
        this.bucketName = ossBucket;
    }

    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}

