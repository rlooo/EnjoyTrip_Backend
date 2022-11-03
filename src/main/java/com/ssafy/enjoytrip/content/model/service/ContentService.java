package com.ssafy.enjoytrip.content.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.content.model.AreaDto;
import com.ssafy.enjoytrip.content.model.PlaceDto;
import com.ssafy.enjoytrip.content.model.SigunguDto;

public interface ContentService {
    List<AreaDto> getAreaCode() throws Exception;

    List<SigunguDto> getSigunguCode(int areaCode) throws Exception;

    List<PlaceDto> getPlaceInfo(Map<String, Object> map) throws Exception;
}