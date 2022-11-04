package com.ssafy.enjoytrip.content.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.content.model.AreaDto;
import com.ssafy.enjoytrip.content.model.PlaceDto;
import com.ssafy.enjoytrip.content.model.SigunguDto;

@Mapper
public interface ContentMapper {
    List<AreaDto> getAreaCode() throws SQLException;

    List<SigunguDto> getSigunguCode(int areaCode) throws SQLException;

    List<PlaceDto> getPlaceInfo(Map<String, Object> map) throws SQLException;

    List<PlaceDto> getRandomPlaceInfo(Map<String, Object> map) throws SQLException;

    List<PlaceDto> getSearchPlaceInfo(Map<String, Object> map) throws SQLException;
}
