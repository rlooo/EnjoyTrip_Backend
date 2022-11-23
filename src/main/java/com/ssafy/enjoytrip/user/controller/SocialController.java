package com.ssafy.enjoytrip.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ssafy.enjoytrip.user.model.service.JwtServiceImpl;
import com.ssafy.enjoytrip.user.model.service.KakaoService;
import com.ssafy.enjoytrip.user.model.service.SocialService;
import com.ssafy.enjoytrip.user.model.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
public class SocialController {
	private final Logger logger = LoggerFactory.getLogger(SocialController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final SocialService socialService;
	private final UserService userService;
	// private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;
	
	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	public SocialController(SocialService socialService, UserService userService, Environment env) {
		this.socialService = socialService;
		this.userService = userService;
		this.env = env;
	}

	@GetMapping("/oauth/login/kakao")
	public ResponseEntity<Map<String, Object>> socialLogin(@RequestParam("code") String code,
			HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			UserDto loginUser = socialService.verificationKakao(code);
			logger.debug("loginUser => {}", loginUser.toString());
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userId", loginUser.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userId", loginUser.getUserId());// key, data
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
}
