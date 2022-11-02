package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.BoardDto;

public interface BoardService {
    void writeBoard(BoardDto boardDto) throws Exception;

    List<BoardDto> getBoardList(Map<String, Object> map) throws Exception;

    int getTotalBoardCount(Map<String, Object> map) throws Exception;

    BoardDto getBoard(int articleNo) throws Exception;

    void updateView(int articleNo) throws Exception;

    void modifyBoard(BoardDto boardDto) throws Exception;

    void deleteImg(int articleNo) throws Exception;

    void deleteBoard(int articleNo) throws Exception;
}
