package com.isylph.oss.domain.entity;

import com.isylph.oss.domain.types.Module;
import lombok.Getter;

import java.io.InputStream;

@Getter
public class GeneralFile {

    public static GeneralFile create(String module, String subdirectory, String fileName, InputStream fileStream){

        String suffix="";
        int last = fileName.lastIndexOf(".");
        if (last != -1){
            suffix = fileName.substring(last+1);
        }
        return new GeneralFile(fileName,  subdirectory, new Module(module), suffix, fileStream);
    }

    private String fileName;

    private String subdirectory;

    private Module module;

    private String suffix;

    private InputStream inputStream;

    public GeneralFile(String fileName, String subdirectory, Module module, String suffix, InputStream inputStream) {
        this.fileName = fileName;
        this.subdirectory = subdirectory;
        this.module = module;
        this.suffix = suffix;
        this.inputStream = inputStream;
    }
}
