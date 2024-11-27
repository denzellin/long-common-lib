package com.isylph.oss.application.assembler;


import com.isylph.oss.api.entity.OssFileLocationDTO;
import com.isylph.oss.api.entity.OssFileLocationSaveCmd;
import com.isylph.oss.api.entity.OssFileLocationUpdateCmd;
import com.isylph.oss.domain.entity.OssFileLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface OssAssembler {

    @Mapping(target = "id", source = "id.id")
    OssFileLocationDTO toOssFileLocationDto(OssFileLocation src);

    List<OssFileLocationDTO> toOssFileLocationDto(List<OssFileLocation> src);


    OssFileLocation  toOssFileLocation(OssFileLocationSaveCmd src);

    @Mapping(target = "id", expression = "java(new com.isylph.oss.domain.types.LocationId(src.getId()))")
    OssFileLocation  toOssFileLocation(OssFileLocationUpdateCmd src);


}
