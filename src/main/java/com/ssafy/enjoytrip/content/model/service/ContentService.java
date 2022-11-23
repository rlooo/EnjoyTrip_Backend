package com.ssafy.enjoytrip.content.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.content.model.AreaDto;
import com.ssafy.enjoytrip.content.model.PlaceDto;
import com.ssafy.enjoytrip.content.model.PlanDto;
import com.ssafy.enjoytrip.content.model.SigunguDto;

public interface ContentService {
    List<AreaDto> getAreaCode() throws Exception;

    List<SigunguDto> getSigunguCode(int areaCode) throws Exception;

    List<PlaceDto> getPlaceInfo(Map<String, Object> map) throws Exception;

    List<PlaceDto> getRandomPlaceInfo(Map<String, Object> map) throws Exception;

    List<PlaceDto> getSearchPlaceInfo(Map<String, Object> map) throws Exception;

    void writePlan(PlanDto planDto) throws Exception;

    List<PlanDto> getPlanList() throws Exception;

    List<PlanDto> getUserPlanList(String userId) throws Exception;
}
