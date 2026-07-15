package com.isylph.oss.storage;

import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.OssFileAttachment;

import java.io.InputStream;
import java.util.UUID;

public interface FileStorage {

    <T extends GeneralFile> OssFileAttachment saveFile(T file) throws Exception;

    InputStream readFile(OssFileAttachment file);

    default String generateRandomName(String suffix) {
        return UUID.randomUUID() + "." + suffix;
    }

    default String readTextFile(String path){
        return "";
    }
}
