package com.isylph.oss.domain.entity;

import com.isylph.oss.domain.types.Module;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

@Getter
public class TextFile extends GeneralFile {


    private static final String TEXT_SUFFIX = "txt";

    private TextFile(String subdirectory, Module module, ByteArrayInputStream inputStream) {
        super("", subdirectory, module, TEXT_SUFFIX, inputStream);
    }

    public static TextFile create(String module, String subdirectory, String content){

        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        TextFile file = new TextFile(subdirectory, new Module(module), inputStream);
        return file;
    }

}
