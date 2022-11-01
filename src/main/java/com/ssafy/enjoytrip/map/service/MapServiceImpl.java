package com.ssafy.enjoytrip.map.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.map.model.DoDto;
import com.ssafy.enjoytrip.map.model.GugunDto;
import com.ssafy.enjoytrip.map.mapper.MapMapper;
import com.ssafy.enjoytrip.map.model.AreaDto;

@Service
public class MapServiceImpl implements MapService{
	private MapMapper mapMapper;
	
	@Autowired
	private MapServiceImpl(MapMapper mapMapper) {
		this.mapMapper = mapMapper;
	}

	@Override
	public List<DoDto> loadDo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapMapper.loadDo(request, response);
	}

	@Override
	public List<GugunDto> loadGugun(int areacode) throws Exception{
		return mapMapper.loadGugun(areacode);
	}

	@Override
	public List<AreaDto> search(int sidoCode, int gugunCode, int kindCode, int page) throws Exception {
		List<AreaDto> list = mapMapper.search(sidoCode, gugunCode, kindCode, page);
		int startIdx = (page - 1) * 20;
		int endIdx = page * 20;
		if(endIdx > list.size()) endIdx = list.size();
		list.get(list.size() - 1).setLast(true);
		try {
			list = list.subList(startIdx, endIdx);			
		} catch (Exception e) {
			list = null;
		}
//		여기서 페이지 따라 20개씩 자르기
		return list;
	}

	@Override
	public List<AreaDto> searchByTitle(String title, int page) throws Exception {
		List<AreaDto> list = mapMapper.searchByTitle(title, page);
		int startIdx = (page - 1) * 18;
		int endIdx = page * 18;
		if(endIdx > list.size()) endIdx = list.size();
		list.get(list.size() - 1).setLast(true);
		try {
			list = list.subList(startIdx, endIdx);			
		} catch (Exception e) {
			list = null;
		}
		return list;
	}
	
}
