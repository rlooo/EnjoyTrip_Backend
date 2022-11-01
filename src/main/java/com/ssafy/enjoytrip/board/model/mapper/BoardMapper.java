package com.ssafy.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.BoardDto;

@Mapper
public interface BoardMapper {
	int writePost(BoardDto boardDto) throws SQLException;
	List<BoardDto> listPost(Map<String, String> map) throws SQLException;
	int totalPostCount(Map<String, String> map) throws SQLException;
	BoardDto getPost(int postId) throws SQLException;
	void updateHit(int postId) throws SQLException;
	void modifyPost(BoardDto boardDto) throws SQLException;
	void deletePost(int postId) throws SQLException;
	void loadboard(HttpServletRequest request, HttpServletResponse response, String queryString);
}
