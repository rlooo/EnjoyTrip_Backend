package com.ssafy.enjoytrip.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.UserFileInfoDto;
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
        return userMapper.idCheck(userId);
    }

    @Override
    public UserDto loginUser(Map<String, Object> map) throws Exception {
        return userMapper.loginUser(map);
    }

    @Override
    public void registUser(UserDto userDto) throws Exception {
        userMapper.registUser(userDto);
        if(userDto.getFileInfos()!=null) {
        	userMapper.registerFile(userDto);
        }
    }

    @Override
    public UserDto getUser(String userId) throws Exception {
        return userMapper.getUser(userId);
    }

    @Override
    public UserDto getUserInfo(String userId) throws Exception {
        return userMapper.getUserInfo(userId);
    }

    @Override
    public void updateUser(UserDto userDto) throws Exception {
        userMapper.deleteImg(userDto.getUserId());
        userMapper.updateUser(userDto);
        userMapper.registerFile(userDto);
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        userMapper.deleteImg(userId);
        userMapper.deleteUser(userId);
    }

    @Override
    public List<UserDto> getUserList(Map<String, Object> map) throws Exception {
        return userMapper.getUserList(map);
    }

    @Override
    public UserDto login(UserDto userDto) throws Exception {
        if (userDto.getUserId() == null || userDto.getUserPw() == null) {
            return null;
        }
        return userMapper.login(userDto);
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", refreshToken);
        userMapper.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String userId) throws Exception {
        return userMapper.getRefreshToken(userId);
    }

    @Override
    public void deleRefreshToken(String userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("token", null);
        userMapper.saveRefreshToken(map);
    }

	@Override
	public List<UserFileInfoDto> fileInfoList(String userId) throws Exception {
		return userMapper.fileInfoList(userId);
	}

	@Override
	public void modifyUser(UserDto userDto) throws Exception {
		userMapper.deleteImg(userDto.getUserId());
		userMapper.modifyUser(userDto);
		userMapper.registerFile(userDto);
		
	}

}
