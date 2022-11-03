package com.ssafy.enjoytrip.user.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.user.model.OAuthTokenDto;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoService {
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper objectMapper = new ObjectMapper();

	
	public String getAccessTokenByCode(String code) { // 인가 코드를 사용하여 access token 받아오기

	    HttpHeaders headers = new HttpHeaders(); //헤더 만들기
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //헤더 contenttype 설정

	    LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 파라미터 설정
	    params.add("grant_type", "authorization_code");
	    params.add("client_id", "af3cf7912cfcd3399b542b15ce6d6032");
	    params.add("redirect_uri", "http://localhost:8080/user/oauth/login/kakao");
	    params.add("code", code);
	    params.add("client_secret", "98nyxszNAhnR94b7QcGGqa8g5P7DLaWQ"); 

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers); // 요청 보낼 entity

	    String url = "https://kauth.kakao.com/oauth/token"; //요청 보낼 url

	    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class); // 응답 받아옴

	    try {
	        return objectMapper.readValue(response.getBody(), OAuthTokenDto.class).getAccess_token(); // 응답에서 accessToken만 반환
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public String getUserInfoByAccessToken(String accessToken) {

	    HttpHeaders headers = new HttpHeaders(); // 요청 헤더
	    headers.set("Authorization", "Bearer " + accessToken); //요청 헤더에 accessToken 넣어줌
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //contentType 설정

	    LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>(); //파라미터 설정

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers); //요청

	    String url = "https://kapi.kakao.com/v2/user/me"; //시용자 정보 api 

	    return restTemplate.postForObject(url, request, String.class); // 요청 보냄
	}

}
