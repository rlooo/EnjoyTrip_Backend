package com.ssafy.enjoytrip.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.user.model.UserDto;

public interface UserService {
    int idCheck(String userId) throws Exception;

    UserDto loginUser(Map<String, Object> map) throws Exception;

    void registUser(UserDto userDto) throws Exception;

    UserDto getUser(String userId) throws Exception;

    UserDto getUserInfo(String userId) throws Exception;

    void updateUser(UserDto userDto) throws Exception;

    void deleteUser(String userId) throws Exception;

    List<UserDto> getUserList(Map<String, Object> map) throws Exception;

    public UserDto login(UserDto userDto) throws Exception;

    public void saveRefreshToken(String userId, String refreshToken) throws Exception;

    public Object getRefreshToken(String userId) throws Exception;

    public void deleRefreshToken(String userId) throws Exception;
}
