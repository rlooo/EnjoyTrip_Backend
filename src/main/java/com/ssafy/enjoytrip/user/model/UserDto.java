package com.ssafy.enjoytrip.user.model;

import java.util.List;

import com.ssafy.enjoytrip.user.model.UserFileInfoDto;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String userName;
    private String userPw;
    private int userAge;
    private String email;
    private String joinDate;
    private int isManager; // 1: Manage, 0: User(default = 0)
    private List<UserFileInfoDto> fileInfos;

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

	public List<UserFileInfoDto> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<UserFileInfoDto> fileInfos) {
		this.fileInfos = fileInfos;
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
				+ ", email=" + email + ", joinDate=" + joinDate + ", isManager=" + isManager + ", fileInfos="
				+ fileInfos + "]";
	}



}
