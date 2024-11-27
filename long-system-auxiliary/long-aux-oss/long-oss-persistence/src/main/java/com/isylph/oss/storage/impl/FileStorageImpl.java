package com.isylph.oss.storage.impl;

import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.consts.Errors;
import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.OssFileAttachment;
import com.isylph.oss.domain.entity.OssFileLocation;
import com.isylph.oss.domain.types.FileGuid;
import com.isylph.oss.domain.types.Module;
import com.isylph.oss.domain.types.RandomGUID;
import com.isylph.oss.persistence.conf.FileServerManager;
import com.isylph.oss.repository.OssRepository;
import com.isylph.oss.storage.FileStorage;
import com.isylph.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Repository
public class FileStorageImpl implements FileStorage {

    @Autowired
    private OssRepository ossRepository;

    @Autowired
    private FileServerManager fileServerManager;

    private String generateRandomName(String suffix) {
        return UUID.randomUUID() + "." + suffix;
    }

    private String getLocalFileName(String module,
                                    String subdirectory,
                                    String suffixName){
        OssFileLocation loc =ossRepository.findOssFileLocation(new Module(module));
        if (loc == null){
            throw new ReturnException(Errors.LOCATION_CFG_NO_EXIST);
        }

        String modulePath = loc.getLocation();

        modulePath = StringUtils.isNotEmpty(modulePath) ? modulePath : "default";

        if(!StringUtils.isEmpty(subdirectory)){
            modulePath += "/" + subdirectory;
        }

        String abstractPath = modulePath + "/" + generateRandomName(suffixName);

        return abstractPath;
    }


    @Override
    public <T extends GeneralFile> FileData saveFile(T file) {
        String abstractPath = getLocalFileName(file.getModule().getModule(), file.getSubdirectory(), file.getSuffix());

        String localFilePath = fileServerManager.getLocalDir() + abstractPath;
        File targetFile = new File(localFilePath);

        // 检测是否存在目录
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        log.info("保存至本地路径：{}", localFilePath);

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        } catch (IOException e) {
            log.error("保存文件至本地失败", e);
            targetFile.deleteOnExit();
            throw new ReturnException(Errors.UPLOAD_SAVE_FILE_FAIL);
        }

        String url = fileServerManager.assembleUrl(abstractPath);
        String guid = new RandomGUID().toString();

        log.info("上传成功后的文件url为：{}", url);

        OssFileAttachment attachment = new OssFileAttachment()
                .setGuid(new FileGuid(guid))
                .setName(file.getFileName())
                .setPath(abstractPath);

        ossRepository.saveOssFileAttachment(attachment);

        return new FileData(guid, url, file.getFileName());
    }

    @Override
    public String readTextFile(String path) {

        String localFilePath = fileServerManager.getLocalDir() + path;
        File targetFile = new File(localFilePath);
        String str;
        try (InputStream inputStream = new FileInputStream(targetFile)) {
            str = IOUtils.toString(inputStream, "utf-8");
        } catch (IOException e) {
            log.error("从本地读取文件失败", e);
            targetFile.deleteOnExit();
            throw new ReturnException(Errors.UPLOAD_SAVE_FILE_FAIL);
        }
        return str;
    }
}
