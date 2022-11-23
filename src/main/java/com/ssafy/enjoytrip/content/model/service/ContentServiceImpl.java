package com.ssafy.enjoytrip.content.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.content.model.AreaDto;
import com.ssafy.enjoytrip.content.model.PlaceDto;
import com.ssafy.enjoytrip.content.model.PlanDto;
import com.ssafy.enjoytrip.content.model.SigunguDto;
import com.ssafy.enjoytrip.content.model.mapper.ContentMapper;

@Service
public class ContentServiceImpl implements ContentService {
    private ContentMapper contentMapper;

    @Autowired
    public ContentServiceImpl(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    @Override
    public List<AreaDto> getAreaCode() throws Exception {
        return contentMapper.getAreaCode();
    }

    @Override
    public List<SigunguDto> getSigunguCode(int areaCode) throws Exception {
        return contentMapper.getSigunguCode(areaCode);
    }

    @Override
    public List<PlaceDto> getPlaceInfo(Map<String, Object> map) throws Exception {
        return contentMapper.getPlaceInfo(map);
    }

    @Override
    public List<PlaceDto> getRandomPlaceInfo(Map<String, Object> map) throws Exception {
        return contentMapper.getRandomPlaceInfo(map);
    }

    @Override
    public List<PlaceDto> getSearchPlaceInfo(Map<String, Object> map) throws Exception {
        return contentMapper.getSearchPlaceInfo(map);
    }

    @Override
    public void writePlan(PlanDto planDto) throws Exception {
        contentMapper.writePlan(planDto);
        contentMapper.writePlanPlace(planDto);
    }

    @Override
    public List<PlanDto> getPlanList() throws Exception {
        return contentMapper.getPlanList();
    }

}
