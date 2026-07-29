package com.vivavot.oss.repository;


import com.vivavot.basis.base.RetPage;
import com.vivavot.basis.types.FileData;
import com.vivavot.oss.api.entity.OssFileLocationQuery;
import com.vivavot.oss.domain.entity.OssFileAttachment;
import com.vivavot.oss.domain.entity.OssFileLocation;
import com.vivavot.oss.domain.types.AttachmentId;
import com.vivavot.oss.domain.types.FileGuid;
import com.vivavot.oss.domain.types.LocationId;
import com.vivavot.oss.domain.types.Module;

import java.util.List;

public interface OssRepository {

    OssFileAttachment findOssFileAttachment(AttachmentId id);

    OssFileAttachment findOssFileAttachment(FileGuid id);

    int saveOssFileAttachment(OssFileAttachment req);

    int removeOssFileAttachment(AttachmentId id);

    int removeOssFileAttachment(FileGuid id);


    OssFileLocation findOssFileLocation(LocationId id);

    OssFileLocation findOssFileLocation(Module module);

    RetPage<OssFileLocation> queryOssFileLocation(OssFileLocationQuery req);

    int countOssFileLocation(OssFileLocationQuery req);

    int saveOssFileLocation(OssFileLocation req);

    int removeOssFileLocation(LocationId id);

    FileData getFile(FileGuid guid);

    OssFileAttachment getFileInfo(FileGuid guid);

    List<FileData> getFile(List<FileGuid> guids);


}
