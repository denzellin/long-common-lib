package com.isylph.oss.domain.entity;

import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.oss.api.consts.Errors;
import com.isylph.oss.domain.service.OssFileService;
import com.isylph.oss.domain.types.Module;
import com.isylph.utils.StringUtils;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Getter
public class ImageFile extends GeneralFile {

    private ImageFile(String fileName, String subdirectory, Module module, String suffix, InputStream inputStream) {
        super(fileName, subdirectory, module, suffix, inputStream);
    }

    public static ImageFile create(String module, String subdirectory, String fileName, InputStream fileStream, OssFileService ossFileService){

        //通过文件头魔术值校验文件格式
        ByteArrayOutputStream byteArrayOutputStream = ossFileService.inputStreamCache(fileStream);
        String suffix = ossFileService.checkImageTypeFileSuffix(byteArrayOutputStream);

        if (StringUtils.isEmpty(suffix)){
            throw new ReturnException(Errors.UPLOAD_INVALID_FORMAT);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        ImageFile file = new ImageFile(fileName, subdirectory, new Module(module), suffix, inputStream);
        return file;
    }


}
