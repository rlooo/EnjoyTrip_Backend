package com.ssafy.enjoytrip.map.mapper;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.map.model.DoDto;
import com.ssafy.enjoytrip.map.model.GugunDto;
import com.ssafy.enjoytrip.map.model.AreaDto;

@Mapper
public interface MapMapper {
	List<DoDto> loadDo(HttpServletRequest request, HttpServletResponse response) throws SQLException;
	List<GugunDto> loadGugun(int areacode) throws SQLException;
	List<AreaDto> search(int sidoCode, int gugunCode, int kindCode, int page) throws SQLException;
	List<AreaDto> searchByTitle(String title, int page) throws SQLException;
}
