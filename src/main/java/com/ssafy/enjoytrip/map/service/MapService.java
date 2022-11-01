package com.ssafy.enjoytrip.map.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.enjoytrip.map.model.DoDto;
import com.ssafy.enjoytrip.map.model.GugunDto;
import com.ssafy.enjoytrip.map.model.AreaDto;

public interface MapService {
	List<DoDto> loadDo(HttpServletRequest request, HttpServletResponse response) throws Exception;

	List<GugunDto> loadGugun(int areacode) throws Exception;

	List<AreaDto> search(int sidoCode, int gugunCode, int kindCode, int page) throws Exception;

	List<AreaDto> searchByTitle(String title, int page) throws Exception;

}
