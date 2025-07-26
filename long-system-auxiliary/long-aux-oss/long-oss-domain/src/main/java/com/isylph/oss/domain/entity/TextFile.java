package com.isylph.oss.domain.entity;

import com.isylph.oss.domain.types.Module;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

@Getter
public class TextFile extends GeneralFile {


    private static final String TEXT_SUFFIX = "txt";

    private TextFile(Module module, ByteArrayInputStream inputStream, Long size) {
        super("", module, TEXT_SUFFIX, inputStream, size, "text/plain");
    }

    public static TextFile create(String module, String subdirectory, String content, long size){

        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        TextFile file = new TextFile(new Module(module), inputStream, size);
        return file;
    }

}
