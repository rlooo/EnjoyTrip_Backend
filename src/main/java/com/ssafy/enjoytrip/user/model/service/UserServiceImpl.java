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
        return userMapper.idCheck(userId);
    }

    @Override
    public UserDto loginUser(Map<String, Object> map) throws Exception {
        return userMapper.loginUser(map);
    }

    @Override
    public void registUser(UserDto userDto) throws Exception {
    	userMapper.registUser(userDto);
    	userMapper.registerFile(userDto);
    }

    @Override
    public UserDto getUser(String userId) throws Exception {
        return userMapper.getUser(userId);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
