package com.isylph.oss.application;

import com.isylph.basis.base.RetPage;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.entity.OssFileInfoVO;
import com.isylph.oss.api.entity.OssFileLocationDTO;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.api.entity.OssFileLocationSaveCmd;
import com.isylph.oss.api.entity.OssFileLocationUpdateCmd;

import java.io.InputStream;
import java.util.List;

public interface OssApplicationService {

    OssFileLocationDTO findOssFileLocation(Long id);

    OssFileLocationDTO findOssFileLocation(String module);

    RetPage<OssFileLocationDTO> queryOssFileLocation(OssFileLocationQuery req);

    int saveOssFileLocation(OssFileLocationSaveCmd req);

    int updateOssFileLocation(OssFileLocationUpdateCmd req);

    int removeOssFileLocation(Long id);


    FileData saveImage(String module, String subdirectory, String fileName, InputStream fileStream);

    FileData savePdf(String module, String subdirectory, String fileName, InputStream fileStream);

    FileData saveGeneralFile(String module, String subdirectory, String fileName, InputStream fileStream);

    String readTextFromFile(String path);

    FileData getFileInfo( String guid);

    List<FileData> getFileInfo(List<String> guids);
}
