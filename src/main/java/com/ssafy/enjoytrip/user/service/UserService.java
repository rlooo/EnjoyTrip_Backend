package com.ssafy.enjoytrip.user.service;

import java.util.List;

import com.ssafy.enjoytrip.user.model.UserDto;

public interface UserService {
	int idCheck(String userId) throws Exception; // 아이디 중복검사
	void joinMember(UserDto memberDto) throws Exception; // 회원가입
	UserDto loginMember(String userId, String userPwd) throws Exception; // 로그인
	List<UserDto> listMember() throws Exception; // 회원목록
	void modifyMember(UserDto user) throws Exception; //회원 수정
	void deleteMember(String userId) throws Exception;
	void certificationMember(String userEmail) throws Exception;
	String confirmPassword(String userId) throws Exception;
}
