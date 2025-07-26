package com.isylph.oss.application.impl;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.basis.types.FileData;
import com.isylph.oss.api.consts.Errors;
import com.isylph.oss.api.entity.OssFileLocationDTO;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.api.entity.OssFileLocationSaveCmd;
import com.isylph.oss.api.entity.OssFileLocationUpdateCmd;
import com.isylph.oss.application.OssApplicationService;
import com.isylph.oss.application.assembler.OssAssembler;
import com.isylph.oss.domain.entity.GeneralFile;
import com.isylph.oss.domain.entity.ImageFile;
import com.isylph.oss.domain.entity.OssFileAttachment;
import com.isylph.oss.domain.entity.OssFileLocation;
import com.isylph.oss.domain.entity.PdfFile;
import com.isylph.oss.domain.service.OssFileService;
import com.isylph.oss.domain.types.FileGuid;
import com.isylph.oss.domain.types.LocationId;
import com.isylph.oss.domain.types.Module;
import com.isylph.oss.persistence.conf.FileServerManager;
import com.isylph.oss.repository.OssRepository;
import com.isylph.oss.storage.FileStorage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OssApplicationServiceImpl implements OssApplicationService {

    @Value("${oss.type:Local}")
    private String ossType;

    @Autowired
    private OssRepository ossRepository;

    @Autowired
    private OssAssembler ossAssembler;

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private Map<String, FileStorage> fileStorageMap;

    private FileStorage fileStorage;

    @Autowired
    private FileServerManager fileServerManager;

    @PostConstruct
    public void init(){
        String type = "fileStorage" + ossType;
        fileStorage = fileStorageMap.get(type);
        if (fileStorage == null){
            log.warn("Invalid type: {}", type);
            throw new IllegalArgumentException();
        }
    }

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

    private <T extends GeneralFile> FileData saveFile(T file) {

        try {
            OssFileAttachment attachment = fileStorage.saveFile(file);
            String url = fileServerManager.assembleUrl(attachment.getPath());
            ossRepository.saveOssFileAttachment(attachment);
            return new FileData(attachment.getGuid().getGuid(), url, file.getFileName());
        }catch (Exception e) {
            log.info("File to save file: {}", e.getMessage());
            throw new ReturnException(Errors.UPLOAD_SAVE_FILE_FAIL);
        }
    }

    @Override
    public FileData saveImage(String module, String fileName, InputStream fileStream, long size, String contentType) {
        ImageFile file = ImageFile.create(module, fileName, fileStream, size, contentType, ossFileService);

        return saveFile(file);
    }

    @Override
    public FileData savePdf(String module, String fileName, InputStream fileStream, long size, String contentType) {
        PdfFile file = PdfFile.create(module, fileName, fileStream, size, contentType, ossFileService);

        return saveFile(file);
    }

    @Override
    public FileData saveGeneralFile(String module, String fileName, InputStream fileStream, long size, String contentType) {
        GeneralFile file = GeneralFile.create(module, fileName, fileStream, size, contentType);

        return saveFile(file);
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
