package com.ssafy.enjoytrip.user.service;

import java.sql.SQLException;
import java.util.List;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	private UserMapper userMapper;

	@Autowired
	private UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return userMapper.idCheck(userId);
	}

	@Override
	public void joinMember(UserDto memberDto) throws Exception {
		userMapper.joinMember(memberDto);

	}

	@Override
	public UserDto loginMember(String userId, String userPwd) throws Exception {
		return userMapper.loginMember(userId, userPwd);
	}

	@Override
	public List<UserDto> listMember() throws Exception {
		return userMapper.listMember();
	}

	@Override
	public void deleteMember(String userId) throws SQLException {
		userMapper.deleteMember(userId);

	}

	@Override
	public void modifyMember(UserDto user) throws Exception {
		userMapper.modifyMember(user);

	}

	@Override
	public void certificationMember(String userEmail) throws Exception {
		Properties p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true"); // gmail은 true 고정
		p.put("mail.smtp.host", "smtp.naver.com"); // smtp 서버 주소
		p.put("mail.smtp.auth", "true"); // gmail은 true 고정
		p.put("mail.smtp.port", "587"); // 네이버 포트
		p.put("mail.smtp.port", "587"); // 네이버 포트

		Authenticator auth = new MyAuthentication();
		// session 생성 및 MimeMessage생성
		Session session = Session.getDefaultInstance(p, auth);
		MimeMessage msg = new MimeMessage(session);

		try {
			// 편지보낸시간
			msg.setSentDate(new Date());
			InternetAddress from = new InternetAddress();
			from = new InternetAddress("jey0623@naver.com"); // 발신자 아이디
			// 이메일 발신자
			msg.setFrom(from);
			// 이메일 수신자
			InternetAddress to = new InternetAddress(userEmail);
			msg.setRecipient(Message.RecipientType.TO, to);
			// 이메일 제목
			msg.setSubject("[EnjoyTrip] 비밀번호 찾기 인증번호", "UTF-8");
			// 이메일 내용
			msg.setText("인증번호 : 1234", "UTF-8");
			// 이메일 헤더
			msg.setHeader("content-Type", "text/html");
			// 메일보내기
			javax.mail.Transport.send(msg, msg.getAllRecipients());

		} catch (AddressException addr_e) {
			addr_e.printStackTrace();
		} catch (MessagingException msg_e) {
			msg_e.printStackTrace();
		} catch (Exception msg_e) {
			msg_e.printStackTrace();
		}

	}

	@Override
	public String confirmPassword(String userId) throws Exception {
		return userMapper.confirmPassword(userId);
	}

}
