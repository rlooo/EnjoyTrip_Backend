package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.enjoytrip.board.model.BoardDto;

public interface BoardService {
	int writePost(BoardDto boardDto) throws Exception;
	List<BoardDto> listPost(Map<String, String> map) throws Exception;
	BoardDto getPost(int postId) throws Exception;
	void updateHit(int postId) throws Exception;
	void modifyPost(BoardDto boardDto) throws Exception;
	void deletePost(int postId) throws Exception;
	void loadboard(HttpServletRequest request, HttpServletResponse response, String queryString);
}
