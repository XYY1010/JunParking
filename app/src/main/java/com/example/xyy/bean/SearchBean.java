package com.example.xyy.bean;

/**
 * Created by XYY on 2018/4/24.
 */

public class SearchBean {

    private int iconId;
    private String title;
    private String content;
    private String comments;

    public SearchBean(int iconId, String title, String content, String comments){
        this.iconId = iconId;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
