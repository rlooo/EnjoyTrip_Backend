package com.ssafy.enjoytrip.user.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.user.model.UserDto;

@Mapper
public interface UserMapper {

	int idCheck(String userId) throws SQLException;
	void joinMember(UserDto memberDto) throws SQLException;
	UserDto loginMember(String userId, String userPwd) throws SQLException;
	List<UserDto> listMember() throws SQLException;
	void modifyMember(UserDto user) throws SQLException;
	void deleteMember(String userId) throws SQLException;
	String confirmPassword(String userId) throws SQLException;

}
