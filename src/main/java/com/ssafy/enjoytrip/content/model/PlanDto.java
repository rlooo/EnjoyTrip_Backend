package com.ssafy.enjoytrip.content.model;

import java.util.List;

public class PlanDto {
    private int articleNo;
    private String userId;
    private String title;
    private List<PlanPlaceDto> contentList;

    public int getArticleNo() {
        return articleNo;
    }

    public void setArticleNo(int articleNo) {
        this.articleNo = articleNo;
    }

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

    public List<PlanPlaceDto> getContentList() {
        return contentList;
    }

    public void setContentList(List<PlanPlaceDto> contentList) {
        this.contentList = contentList;
    }

}
