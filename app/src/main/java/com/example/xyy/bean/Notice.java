package com.example.xyy.bean;

/**
 * Created by XYY on 2018/4/23.
 */

public class Notice {

    private String content;
    private int imgId;
    private String time;

    public Notice(String content, int imgId, String time){
        this.content = content;
        this.imgId = imgId;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public int getImgId() {
        return imgId;
    }

    public String getTime() {
        return time;
    }

}
