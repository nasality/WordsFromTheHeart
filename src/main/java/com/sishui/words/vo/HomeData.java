package com.sishui.words.vo;


import com.sishui.words.pojo.Tab;
import com.sishui.words.pojo.Topic;
import com.sishui.words.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeData {
    private String title;
    private String desc;
    private String logo;
//    private List<Slide> slides;
//    private List<IconNav> icons;
    private List<Topic> subjectHots;
    private Integer subjectHotWidth;
//    private HotLink hotLink;
//    private ImgsTop imgsTop;
//    private ImgsEmbed imgsEmbed;
//    private PopAd popAd;
//    private Traffic trafficChp;
//    private TrafficList trafficList;
    private List<User> recUser;

    private int tabSwitch;
    private List<Tab> tabs;
    private String curTab;
    private int tabType;
    private int listSwitch;

    //private List<Article> recPosts;
    private String thumb;
    private String style;

}