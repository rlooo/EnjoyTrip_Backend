package com.ssafy.enjoytrip.user.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.user.model.UserDto;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class SocialService {
	private static final Logger logger = LoggerFactory.getLogger(SocialService.class);

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private UserService userService;

	private ObjectMapper objectMapper = new ObjectMapper();


	public UserDto verificationKakao(String code) {

		String accessToken = kakaoService.getAccessTokenByCode(code);
		String userInfo = kakaoService.getUserInfoByAccessToken(accessToken);
		
		try {
			JsonNode jsonNode = objectMapper.readTree(userInfo); // json 형태로 바꿔줌
			String email = String.valueOf(jsonNode.get("kakao_account").get("email"));
			

			String[] emails = String.valueOf(jsonNode.get("kakao_account").get("email")).split("@");
			String emailId = emails[0].replaceAll("\"", "");
			String name = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname"));
			
			UserDto userDto = new UserDto();
			userDto.setEmail(email);
			userDto.setUserName(name.substring(1, name.length() - 1));
			userDto.setUserId(emailId);

			UserDto loginUser = userService.getUser(userDto.getUserId());
			
			if (loginUser == null) {// 서비스에 등록된 회원이 아니라면
				userDto.setUserPw((UUID.randomUUID().toString()));

				// 회원가입
				userService.registUser(userDto);
				
				return userDto;

			}
			else {
				return loginUser;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
