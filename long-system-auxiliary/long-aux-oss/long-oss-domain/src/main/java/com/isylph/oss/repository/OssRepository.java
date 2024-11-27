package com.isylph.oss.repository;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.domain.entity.OssFileAttachment;
import com.isylph.oss.domain.entity.OssFileLocation;
import com.isylph.oss.domain.types.AttachmentId;
import com.isylph.oss.domain.types.FileGuid;
import com.isylph.oss.domain.types.LocationId;
import com.isylph.oss.domain.types.Module;

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

    List<FileData> getFile(List<FileGuid> guids);


}
