package com.ssafy.enjoytrip.user.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
public class MyAuthentication extends Authenticator {
	PasswordAuthentication pa;

	public MyAuthentication() {

		String id = "jey0623@naver.com"; // 네이버 이메일 아이디
		String pw = "wldnro3236!"; // 네이버 비밀번호

		// ID와 비밀번호를 입력한다.
		pa = new PasswordAuthentication(id, pw);
	}

	// 시스템에서 사용하는 인증정보
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}
