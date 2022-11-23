package com.ssafy.enjoytrip.user.model.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface MailService{

	public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;
	public String createKey();
	public String sendSimpleMessage(String to) throws Exception;

}
