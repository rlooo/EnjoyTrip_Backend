package com.ssafy.enjoytrip.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.user.model.UserFileInfoDto;
import com.ssafy.enjoytrip.user.model.UserDto;

@Mapper
public interface UserMapper {
    int idCheck(String userId) throws SQLException;

    UserDto loginUser(Map<String, Object> map) throws SQLException;

    void registUser(UserDto userDto) throws SQLException;
    
    void modifyUser(UserDto userDto) throws SQLException;

    UserDto getUser(String userId) throws SQLException;

    UserDto getUserInfo(String userId) throws SQLException;

    void updateUser(UserDto userDto) throws SQLException;

    void deleteUser(String userId) throws SQLException;

    List<UserDto> getUserList(Map<String, Object> map) throws SQLException;

    void registerFile(UserDto userDto) throws Exception;

    void deleteImg(String userId) throws SQLException;

    List<UserFileInfoDto> fileInfoList(String userId) throws Exception;

    public UserDto login(UserDto userDto) throws SQLException;

    public void saveRefreshToken(Map<String, String> map) throws SQLException;

    public Object getRefreshToken(String userId) throws SQLException;

    public void deleteRefreshToken(Map<String, String> map) throws SQLException;

	public String findPwByEmail(String email) throws SQLException;
}
