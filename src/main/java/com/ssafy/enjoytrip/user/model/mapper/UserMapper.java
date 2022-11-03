package com.ssafy.enjoytrip.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.user.model.UserDto;

@Mapper
public interface UserMapper {
    int idCheck(String userId) throws SQLException;

    UserDto loginUser(Map<String, Object> map) throws SQLException;

    void registUser(UserDto userDto) throws SQLException;

    UserDto getUser(String userId) throws SQLException;
    
    UserDto getUserByEmail(String emailId) throws SQLException;

    void updateUser(UserDto userDto) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    List<UserDto> getUserList(Map<String, Object> map) throws SQLException;
}
