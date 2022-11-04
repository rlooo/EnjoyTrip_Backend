package com.ssafy.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;


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
    	System.out.println("글입력 후 dto : " + boardDto);
    	// 파일 받는 부분 쓰기
    	
    }

    @Override
    public List<BoardDto> getBoardList(Map<String, Object> map) throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	String key = (String) map.get("key");
    	if ("userid".equals(key))
			key = "b.user_id";
    	param.put("key", key == null ? "" : key);
    	param.put("word", map.get("word") == null ? "" : map.get("word"));
    	int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : (String) map.get("pgno"));
    	int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
    	param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		return boardMapper.getBoardList(param);
    }


    @Override
    public BoardDto getBoard(int articleNo) throws Exception {
		return boardMapper.getBoard(articleNo);
    	
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

	@Override
	public PageNavigation makePageNavigation(Map<String, Object> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		
		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt((String) map.get("pgno"));
		
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = (String) map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		
		
		return pageNavigation;
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

}
