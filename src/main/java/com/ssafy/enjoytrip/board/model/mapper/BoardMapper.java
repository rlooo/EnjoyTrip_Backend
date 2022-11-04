package com.ssafy.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.BoardDto;

@Mapper
public interface BoardMapper {
    void writeBoard(BoardDto boardDto) throws SQLException;

    List<BoardDto> getBoardList(Map<String, Object> map) throws SQLException;

    int getTotalBoardCount(Map<String, Object> map) throws SQLException;

    BoardDto getBoard(int articleNo) throws SQLException;

    void updateHit(int articleNo) throws SQLException;

    void modifyBoard(BoardDto boardDto) throws SQLException;

    void deleteImg(int articleNo) throws SQLException;

    void deleteBoard(int articleNo) throws SQLException;
    
    int getTotalArticleCount(Map<String, Object> map) throws SQLException;
}
