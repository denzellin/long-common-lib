package com.isylph.oss.storage;

import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.OssFileAttachment;

import java.util.UUID;

public interface FileStorage {

    <T extends GeneralFile> OssFileAttachment saveFile(T file) throws Exception;

    default String generateRandomName(String suffix) {
        return UUID.randomUUID() + "." + suffix;
    }

    default String readTextFile(String path){
        return "";
    }
}
