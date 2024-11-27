package com.isylph.oss.application.impl;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.entity.OssFileLocationDTO;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.api.entity.OssFileLocationSaveCmd;
import com.isylph.oss.api.entity.OssFileLocationUpdateCmd;
import com.isylph.oss.application.OssApplicationService;
import com.isylph.oss.application.assembler.OssAssembler;
import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.ImageFile;
import com.isylph.oss.domain.entity.OssFileLocation;
import com.isylph.oss.domain.entity.PdfFile;
import com.isylph.oss.domain.service.OssFileService;
import com.isylph.oss.domain.types.FileGuid;
import com.isylph.oss.domain.types.LocationId;
import com.isylph.oss.domain.types.Module;
import com.isylph.oss.repository.OssRepository;
import com.isylph.oss.storage.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OssApplicationServiceImpl implements OssApplicationService {

    @Autowired
    private OssRepository ossRepository;

    @Autowired
    private OssAssembler ossAssembler;

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private FileStorage fileStorage;


    @Override
    public OssFileLocationDTO findOssFileLocation(Long id) {
        OssFileLocation o = ossRepository.findOssFileLocation(new LocationId(id));
        if(null == o){
            return null;
        }

        return ossAssembler.toOssFileLocationDto(o);
    }

    @Override
    public OssFileLocationDTO findOssFileLocation(String module) {
        OssFileLocation o = ossRepository.findOssFileLocation(new Module(module));
        if(null == o){
            return null;
        }

        return ossAssembler.toOssFileLocationDto(o);
    }

    @Override
    public RetPage<OssFileLocationDTO> queryOssFileLocation(OssFileLocationQuery req) {
        RetPage<OssFileLocation> r = ossRepository.queryOssFileLocation(req);
        RetPage<OssFileLocationDTO> ret = new RetPage<>();
        if (r.getTotal() > 0){
            ret.setRecords(ossAssembler.toOssFileLocationDto(r.getRecords()));
        }
        ret.setTotal(r.getTotal());

        return ret;
    }

    @Override
    public int saveOssFileLocation(OssFileLocationSaveCmd req) {
        return ossRepository.saveOssFileLocation(
                ossAssembler.toOssFileLocation(req)
        );
    }

    @Override
    public int updateOssFileLocation(OssFileLocationUpdateCmd req) {
        return ossRepository.saveOssFileLocation(
                ossAssembler.toOssFileLocation(req)
        );
    }

    @Override
    public int removeOssFileLocation(Long id) {
        return ossRepository.removeOssFileLocation(new LocationId(id));
    }

    @Override
    public FileData saveImage(String module, String subdirectory, String fileName, InputStream fileStream) {
        ImageFile file = ImageFile.create(module, subdirectory, fileName, fileStream, ossFileService);

        return fileStorage.saveFile(file);
    }

    @Override
    public FileData savePdf(String module, String subdirectory, String fileName, InputStream fileStream) {
        PdfFile file = PdfFile.create(module, subdirectory, fileName, fileStream, ossFileService);

        return fileStorage.saveFile(file);
    }

    @Override
    public FileData saveGeneralFile(String module, String subdirectory, String fileName, InputStream fileStream) {
        GeneralFile file = GeneralFile.create(module, subdirectory, fileName, fileStream);

        return fileStorage.saveFile(file);
    }

    @Override
    public String readTextFromFile(String path) {
        return null;
    }

    @Override
    public FileData getFileInfo(String guid) {
        return ossRepository.getFile(new FileGuid(guid));
    }

    @Override
    public List<FileData> getFileInfo(List<String> guids) {
        if (CollectionUtils.isEmpty(guids)){
            return null;
        }

        List<FileGuid> ids = guids.stream().map(id -> new FileGuid(id)).collect(Collectors.toList());
        return ossRepository.getFile(ids);
    }
}
