package com.sesasis.donusum.yok.core.enums;

public enum JasperMediaTypes {
    PDF("application/pdf"),HTML("text/html");

    private String desc;

    JasperMediaTypes(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
