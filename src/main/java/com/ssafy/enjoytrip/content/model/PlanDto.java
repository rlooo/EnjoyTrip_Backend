package com.ssafy.enjoytrip.content.model;

import java.util.List;

public class PlanDto {
    private int articleNo;
    private String userId;
    private String title;
    private String startDate;
    private String endDate;
    private String registerDate;
    private int hit;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public List<PlanPlaceDto> getContentList() {
        return contentList;
    }

    public void setContentList(List<PlanPlaceDto> contentList) {
        this.contentList = contentList;
    }

}
