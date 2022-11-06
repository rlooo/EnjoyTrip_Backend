package com.ssafy.enjoytrip.user.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value="/view/{userid}")
	public ResponseEntity<?> getUser(@PathVariable("userid") String userId){
		logger.debug("getUser userid: {}", userId);
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
	
	@Transactional
	@PostMapping(value="/regist")
	public ResponseEntity<?> regist(@RequestBody UserDto userDto) {
		logger.debug("userRegister userDto : {}", userDto);
		try {
			userService.registUser(userDto);
			UserDto user = userService.getUser(userDto.getUserId());
			return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Transactional
	@PutMapping(value="/modify")
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
	@DeleteMapping(value = "/delete/{userid}")
	public ResponseEntity<?> userDelete(@PathVariable("userid") String userId) {
		logger.debug("userDelete userid : {}", userId);
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

}
