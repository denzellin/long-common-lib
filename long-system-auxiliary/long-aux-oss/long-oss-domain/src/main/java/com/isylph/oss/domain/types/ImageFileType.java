package com.isylph.oss.domain.types;

public enum ImageFileType {

    /**
     * JPEG
     */
    JPEG("FFD8FF", "jpeg"),

    /**
     * PNG
     */
    PNG("89504E47", "png"),

    /**
     * GIF
     */
    GIF("47494638", "gif"),

    /**
     * TIFF
     */
    TIFF("49492A00", "tiff"),

    /**
     * Windows bitmap
     */
    BMP("424D", "bmp"),

    /**
     * CAD
     */
    DWG("41433130", "dwg"),

    /**
     * Adobe photoshop
     */
    PSD("38425053", "psd");


    private String value;

    private String suffix;

    ImageFileType(String value, String suffix) {
        this.value = value;
        this.suffix = suffix;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
