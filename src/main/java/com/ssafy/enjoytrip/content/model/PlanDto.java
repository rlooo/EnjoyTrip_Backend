package com.ssafy.enjoytrip.content.model;

import java.util.List;

public class PlanDto {
    private String userId;
    private String title;
    private List<String> contentList;
    private List<String> memo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    public List<String> getMemo() {
        return memo;
    }

    public void setMemo(List<String> memo) {
        this.memo = memo;
    }

}
