package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	private BoardMapper boardMapper;

	@Autowired
	private BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Override
	public int writePost(BoardDto boardDto) throws Exception {
		return boardMapper.writePost(boardDto);
	}

	@Override
	public List<BoardDto> listPost(Map<String, String> map) throws Exception {
		int pgno = Integer.parseInt(map.get("pgno"));
		int spl = SizeConstant.SIZE_PER_LIST;
		int start = (pgno - 1) * spl;
		map.put("start", start + "");
		map.put("spl", spl + "");
		return boardMapper.listPost(map);
	}

	@Override
	public BoardDto getPost(int postId) throws Exception {
		return boardMapper.getPost(postId);
	}

	@Override
	public void updateHit(int postId) throws Exception {
		boardMapper.updateHit(postId);
	}

	@Override
	public void modifyPost(BoardDto boardDto) throws Exception {
		boardMapper.modifyPost(boardDto);
	}

	@Override
	public void deletePost(int postId) throws Exception {
		boardMapper.deletePost(postId);
	}

	@Override
	public void loadboard(HttpServletRequest request, HttpServletResponse response, String queryString) {
		boardMapper.loadboard(request, response, queryString);
	}

}
