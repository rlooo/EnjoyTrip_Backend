package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
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

    }

    @Override
    public List<BoardDto> getBoardList(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public int getTotalBoardCount(Map<String, Object> map) throws Exception {
        return 0;
    }

    @Override
    public BoardDto getBoard(int articleNo) throws Exception {
        return null;
    }

    @Override
    public void updateView(int articleNo) throws Exception {

    }

    @Override
    public void modifyBoard(BoardDto boardDto) throws Exception {

    }

    @Override
    public void deleteImg(int articleNo) throws Exception {

    }

    @Override
    public void deleteBoard(int articleNo) throws Exception {

    }

}
