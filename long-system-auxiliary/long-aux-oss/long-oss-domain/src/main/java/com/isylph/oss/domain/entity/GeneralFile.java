package com.isylph.oss.domain.entity;

import com.isylph.oss.domain.types.Module;
import lombok.Getter;

import java.io.InputStream;

@Getter
public class GeneralFile {

    public static GeneralFile create(String module, String fileName, InputStream fileStream, long size, String contentType){

        String suffix="";
        int last = fileName.lastIndexOf(".");
        if (last != -1){
            suffix = fileName.substring(last+1);
        }
        return new GeneralFile(fileName, new Module(module), suffix, fileStream, size, contentType);
    }

    private final String fileName;

    private final Module module;

    private final String suffix;

    private final InputStream inputStream;

    private final Long size;

    private final String contentType;

    public GeneralFile(String fileName, Module module, String suffix, InputStream inputStream, Long size, String contentType) {
        this.fileName = fileName;
        this.module = module;
        this.suffix = suffix;
        this.inputStream = inputStream;
        this.size = size;
        this.contentType = contentType;
    }
}
