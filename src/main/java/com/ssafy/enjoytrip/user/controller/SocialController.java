package com.ssafy.enjoytrip.user.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.service.KakaoService;
import com.ssafy.enjoytrip.user.model.service.SocialService;
import com.ssafy.enjoytrip.user.model.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/user")
public class SocialController {
	private final Logger logger = LoggerFactory.getLogger(SocialController.class);

	private final SocialService socialService;
	private final UserService userService;
	// private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;

	@Autowired
	public SocialController(SocialService socialService, UserService userService, Environment env) {
		this.socialService = socialService;
		this.userService = userService;
		// this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		this.env = env;
	}

	@GetMapping("/oauth/login/kakao")
	public ResponseEntity socialLogin(@RequestParam("code") String code, HttpServletResponse response)
			throws Exception {
		System.out.println(code);
		UserDto loginUser = socialService.verificationKakao(code);
		System.out.println(loginUser.getUserName());
		UserDto user = null;
		try {
			user = userService.getUser(loginUser.getEmail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(loginUser.toString());
		// 서비스에 등록된 회원이 아니라면
		if (user == null) {

			loginUser.setUserPw((UUID.randomUUID().toString()));

			// 회원가입
			userService.registUser(loginUser);

		}

		System.out.println(env.getProperty("token.expiration_time"));
		// JWT 토큰 생성
		String token = JWT.create().withSubject("JwtToken")
				.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 2)) // 파기일
				.withClaim("emailId", loginUser.getEmail()).sign(Algorithm.HMAC512("enjoytrip"));
		System.out.println(token);
		// JWT 토큰 헤더에 담아 전달
		response.addHeader("JWT", token);

		return new ResponseEntity(HttpStatus.OK);
	}
}
