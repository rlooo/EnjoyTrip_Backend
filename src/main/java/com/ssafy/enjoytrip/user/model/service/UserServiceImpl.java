package com.ssafy.enjoytrip.user.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int idCheck(String userId) throws Exception {
        return 0;
    }

    @Override
    public UserDto loginUser(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public void registUser(UserDto userDto) throws Exception {

    }

    @Override
    public UserDto getUser(String userId) throws Exception {
        return null;
    }

    @Override
    public void updateUser(UserDto userDto) throws Exception {

    }

    @Override
    public void deleteUser(String userId) throws Exception {

    }

    @Override
    public List<UserDto> getUserList(Map<String, Object> map) throws Exception {
        return null;
    }

}
