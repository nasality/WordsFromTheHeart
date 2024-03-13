package com.sishui.words.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateFormat {
    public static String timeStamp2DateString(Timestamp timestamp) {
        // 创建一个 SimpleDateFormat 对象，指定只显示年月日的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 使用 SimpleDateFormat 格式化 Timestamp 对象
        String formattedDate = sdf.format(timestamp);
        return formattedDate;
    }
}
