package com.ssafy.enjoytrip.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
@RequiredArgsConstructor
public class SocialService {
	
	
    private final KakaoService kakaoService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SocialService(KakaoService kakaoService) {
    	this.kakaoService = kakaoService;
    }
    
    
    public UserDto verificationKakao(String code){

        UserDto userDto = new UserDto();
        // 코드를 이용하여 accessToken 추출
        String accessToken = kakaoService.getAccessTokenByCode(code);
        // accessToken을 이용하여 사용자 정보 추출
        String userInfo = kakaoService.getUserInfoByAccessToken(accessToken);

        try {
            JsonNode jsonNode = objectMapper.readTree(userInfo); //json 형태로 바꿔줌
            String email = String.valueOf(jsonNode.get("kakao_account").get("email"));
      
            String[] emails = String.valueOf(jsonNode.get("kakao_account").get("email")).split("@");
            String emailId = emails[0].replaceAll("\"", "");
            String emailDomain = emails[1].replaceAll("\"", "");
            
            userDto.setEmailId(emailId);
            userDto.setEmailDomain(emailDomain);

            
            String name = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname"));
            userDto.setUserName(name.substring(1, name.length() - 1));
            
            userDto.setUserId(emailId);
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return userDto;
    }
}

