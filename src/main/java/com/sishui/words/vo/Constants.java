package com.sishui.words.vo;

public enum Constants {
    DEFAULT_COVER("http://s8gqejvtl.hb-bkt.clouddn.com/images/placeholder.jpg"),
    MINE_BACKGROUND("http://s8gqejvtl.hb-bkt.clouddn.com/images/words-logo.png");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
