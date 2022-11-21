package com.ssafy.enjoytrip.user.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.user.model.UserFileInfoDto;
import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.service.JwtServiceImpl;
import com.ssafy.enjoytrip.user.model.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
		logger.debug("getUser userId: {}", userId);
		try {
			UserDto userDto = userService.getUser(userId);
			if (userDto != null)
				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	// 회원등록
	@Transactional
	@PostMapping()
	public ResponseEntity<?> regist(@Value("${file.path.upload-files}") String filePath, @RequestBody UserDto userDto,
			@RequestParam("upfile") MultipartFile[] files) {
		logger.debug("userRegister userDto : {}", userDto);
		// try {
		// userService.registUser(userDto);
		// UserDto user = userService.getUser(userDto.getUserId());
		// return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		// } catch (Exception e) {
		// return exceptionHandling(e);
		// }
		try {
			// FileUpload 관련 설정.
			logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
			if (!files[0].isEmpty()) {

				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = filePath + File.separator + today;
				logger.debug("저장 폴더 : {}", saveFolder);
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();
				List<UserFileInfoDto> fileInfos = new ArrayList<UserFileInfoDto>();
				for (MultipartFile mfile : files) {
					UserFileInfoDto fileInfoDto = new UserFileInfoDto();
					String originalFileName = mfile.getOriginalFilename();
					if (!originalFileName.isEmpty()) {
						String saveFileName = System.nanoTime()
								+ originalFileName.substring(originalFileName.lastIndexOf('.'));
						fileInfoDto.setSaveFolder(today);
						fileInfoDto.setOriginalFile(originalFileName);
						fileInfoDto.setSaveFile(saveFileName);
						logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfoDto);

				}
				userDto.setFileInfos(fileInfos);
			}

			userService.registUser(userDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Transactional
	@PutMapping()
	public ResponseEntity<?> modify(@RequestBody UserDto userDto) {
		logger.debug("userModify userDto : {}", userDto);
		try {
			userService.updateUser(userDto);
			UserDto user = userService.getUser(userDto.getUserId());
			return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@Transactional
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<?> userDelete(@PathVariable("userId") String userId) {
		logger.debug("userDelete userId : {}", userId);
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			UserDto loginUser = userService.login(userDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userId", loginUser.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userId", loginUser.getUserId());// key, data

				userService.saveRefreshToken(userDto.getUserId(), refreshToken);
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

	// 회원인증
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userId") String userId,
			HttpServletRequest request) {
		logger.debug("info > userId : {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
				// 로그인 사용자 정보.
				UserDto userDto = userService.getUserInfo(userId);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 로그아웃
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable("userId") String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userId);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	// refresh-token 확인 후 access-token 발급
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, userDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userId", userDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
