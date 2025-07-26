package com.isylph.oss.storage;

import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.OssFileAttachment;

public interface FileStorage {

    <T extends GeneralFile> OssFileAttachment saveFile(T file) throws Exception;

    default String readTextFile(String path){
        return "";
    }
}
