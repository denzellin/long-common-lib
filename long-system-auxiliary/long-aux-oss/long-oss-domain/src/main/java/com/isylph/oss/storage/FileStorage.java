package com.isylph.oss.storage;

import com.isylph.basis.types.FileData;
import com.isylph.oss.domain.entity.GeneralFile;

public interface FileStorage {

    <T extends GeneralFile> FileData saveFile(T file);

    String readTextFile(String path);
}
