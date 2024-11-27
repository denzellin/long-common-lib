package com.isylph.oss.domain.entity;

import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.oss.api.consts.Errors;
import com.isylph.oss.domain.service.OssFileService;
import com.isylph.oss.domain.types.Module;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Getter
public class PdfFile extends GeneralFile {
    private PdfFile(String fileName, String subdirectory, Module module, String suffix, ByteArrayInputStream inputStream) {
        super(fileName, subdirectory, module, suffix, inputStream);
    }

    public static PdfFile create(String module, String subdirectory, String fileName, InputStream fileStream, OssFileService ossFileService){

        //通过文件头魔术值校验文件格式
        ByteArrayOutputStream byteArrayOutputStream = ossFileService.inputStreamCache(fileStream);
        byte[] ar = byteArrayOutputStream.toByteArray();
        if (ar.length < 5){
            throw new ReturnException(Errors.UPLOAD_INVALID_FORMAT);
        }
        if (!(ar[0] == 0x25 && // %
                ar[1] == 0x50 && // P
                ar[2] == 0x44 && // D
                ar[3] == 0x46 && // F
                ar[4] == 0x2D)){

            throw new ReturnException(Errors.UPLOAD_INVALID_FORMAT);
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(ar);

        PdfFile file = new PdfFile(fileName, subdirectory, new Module(module), "pdf", inputStream);
        return file;
    }

}
