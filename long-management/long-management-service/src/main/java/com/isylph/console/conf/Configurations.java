package com.isylph.console.conf;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class Configurations {

    @Value("${server.storage.img-path:null}")
    public String _imgPath;

    @Value("${server.local.file-url:null}")
    public String _fileApiUrl;

    public static String fileApiUrl;

    public static String imgPath;

    @PostConstruct
    public void init() {

        Configurations.imgPath = _imgPath;
        Configurations.fileApiUrl = _fileApiUrl;

    }

}
