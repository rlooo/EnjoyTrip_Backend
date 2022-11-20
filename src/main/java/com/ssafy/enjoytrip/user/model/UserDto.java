package com.ssafy.enjoytrip.user.model;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String userName;
    private String userPw;
    private int userAge;
    private String email;
    private String joinDate;
    private String profileImg;
    private int isManager; // 1: Manage, 0: User(default = 0)

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userName=" + userName + ", userPw=" + userPw + ", userAge=" + userAge
				+ ", email=" + email  + ", joinDate=" + joinDate + ", profileImg="
				+ profileImg + ", isManager=" + isManager + "]";
	}

}
