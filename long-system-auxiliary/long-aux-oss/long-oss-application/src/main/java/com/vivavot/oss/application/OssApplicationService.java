package com.vivavot.oss.application;

import com.vivavot.basis.base.RetPage;
import com.vivavot.basis.types.FileData;
import com.vivavot.oss.api.entity.OssFileLocationDTO;
import com.vivavot.oss.api.entity.OssFileLocationQuery;
import com.vivavot.oss.api.entity.OssFileLocationSaveCmd;
import com.vivavot.oss.api.entity.OssFileLocationUpdateCmd;

import java.io.InputStream;
import java.util.List;

public interface OssApplicationService {

    OssFileLocationDTO findOssFileLocation(Long id);

    OssFileLocationDTO findOssFileLocation(String module);

    RetPage<OssFileLocationDTO> queryOssFileLocation(OssFileLocationQuery req);

    int saveOssFileLocation(OssFileLocationSaveCmd req);

    int updateOssFileLocation(OssFileLocationUpdateCmd req);

    int removeOssFileLocation(Long id);


    FileData saveImage(String module, String fileName, InputStream fileStream, long size, String contentType);

    FileData savePdf(String module, String fileName, InputStream fileStream, long size, String contentType);

    FileData saveGeneralFile(String module, String fileName, InputStream fileStream, long size, String contentType);

    @Deprecated
    String readTextFromFile(String path);

    FileData getFileInfo( String guid);

    InputStream readFile(String guid);

    List<FileData> getFileInfo(List<String> guids);
}
