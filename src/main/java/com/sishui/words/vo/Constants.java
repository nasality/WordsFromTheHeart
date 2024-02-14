package com.sishui.words.vo;

public enum Constants {
    DEFAULT_COVER("http://s8gqejvtl.hb-bkt.clouddn.com/images/placeholder.jpg");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}