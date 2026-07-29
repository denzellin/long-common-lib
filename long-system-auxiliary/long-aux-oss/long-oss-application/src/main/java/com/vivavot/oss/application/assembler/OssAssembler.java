package com.vivavot.oss.application.assembler;


import com.vivavot.oss.api.entity.OssFileLocationDTO;
import com.vivavot.oss.api.entity.OssFileLocationSaveCmd;
import com.vivavot.oss.api.entity.OssFileLocationUpdateCmd;
import com.vivavot.oss.domain.entity.OssFileLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface OssAssembler {

    @Mapping(target = "id", source = "id.id")
    OssFileLocationDTO toOssFileLocationDto(OssFileLocation src);

    List<OssFileLocationDTO> toOssFileLocationDto(List<OssFileLocation> src);


    OssFileLocation  toOssFileLocation(OssFileLocationSaveCmd src);

    @Mapping(target = "id", expression = "java(new com.vivavot.oss.domain.types.LocationId(src.getId()))")
    OssFileLocation  toOssFileLocation(OssFileLocationUpdateCmd src);


}
