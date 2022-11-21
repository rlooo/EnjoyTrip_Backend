package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.BoardFileInfoDto;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	@Override
	public void writeBoard(BoardDto boardDto) throws Exception {
		System.out.println("글입력 전 dto : " + boardDto);
		boardMapper.writeBoard(boardDto);
		
		boardMapper.registerFile(boardDto);
		
		
		System.out.println("글입력 후 dto : " + boardDto);

	}

	@Override
	public List<BoardDto> getBoardList(Map<String, Object> map) throws Exception {
		List<BoardDto> list =  boardMapper.getBoardList(map);
		
		for(int i=0;i<list.size();i++) {
			List<BoardFileInfoDto> fileInfos =  boardMapper.fileInfoList(list.get(i).getArticleNo());
			list.get(i).setFileInfos(fileInfos);
			
		}
		return list;
	}

	@Override
	public BoardDto getBoard(int articleNo) throws Exception {
		return boardMapper.getBoard(articleNo);

	}

	@Override
	public void modifyBoard(BoardDto boardDto) throws Exception {
		boardMapper.deleteImg(boardDto.getArticleNo());
		boardMapper.modifyBoard(boardDto);
		boardMapper.registerFile(boardDto);
	}


	@Override
	public void deleteBoard(int articleNo) throws Exception {
		boardMapper.deleteImg(articleNo);
		boardMapper.deleteBoard(articleNo);
	}


	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public List<BoardFileInfoDto> fileInfoList(int articleNo) throws Exception {
		return boardMapper.fileInfoList(articleNo);
	}
	


}
