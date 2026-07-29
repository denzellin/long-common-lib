package com.vivavot.oss.storage;

import com.vivavot.oss.domain.entity.GeneralFile;
import com.vivavot.oss.domain.entity.OssFileAttachment;

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
