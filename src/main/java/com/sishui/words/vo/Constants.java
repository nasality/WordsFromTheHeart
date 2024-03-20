package com.sishui.words.vo;

public enum Constants {
    DEFAULT_COVER("http://uploads.words-from.fun/images/placeholder.jpg"),
    MINE_BACKGROUND("http://uploads.words-from.fun/images/bjj.png"),
    DEFAULT_AVATAR("http://uploads.words-from.fun/images/avatar.jpg"),
    ZHUIGE_TOPIC("zhuige_bbs_topic");
    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
