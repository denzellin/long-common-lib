package com.vivavot.oss.persistence.converter;


import com.vivavot.oss.domain.entity.OssFileAttachment;
import com.vivavot.oss.domain.entity.OssFileLocation;
import com.vivavot.oss.persistence.model.OssFileAttachmentPO;
import com.vivavot.oss.persistence.model.OssFileLocationPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OssBuilder {



    @Mapping(target = "id", expression = "java(new com.vivavot.oss.domain.types.AttachmentId(src.getId()))")
    @Mapping(target = "guid", expression = "java(new com.vivavot.oss.domain.types.FileGuid(src.getGuid()))")
    OssFileAttachment toOssFileAttachment(OssFileAttachmentPO src);

    List<OssFileAttachment> toOssFileAttachment(List<OssFileAttachmentPO> src);


    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "guid", source = "guid.guid")
    OssFileAttachmentPO toOssFileAttachmentPo(OssFileAttachment src);

    @Mapping(target = "id", expression = "java(new com.vivavot.oss.domain.types.LocationId(src.getId()))")
    OssFileLocation toOssFileLocation(OssFileLocationPO src);

    List<OssFileLocation> toOssFileLocation(List<OssFileLocationPO> src);

    @Mapping(target = "id", source = "id.id")
    OssFileLocationPO toOssFileLocationPo(OssFileLocation src);
}
