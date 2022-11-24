package com.ssafy.enjoytrip.content.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.content.model.AreaDto;
import com.ssafy.enjoytrip.content.model.PlaceDto;
import com.ssafy.enjoytrip.content.model.PlanDto;
import com.ssafy.enjoytrip.content.model.SigunguDto;
import com.ssafy.enjoytrip.content.model.service.ContentService;

@RestController
@RequestMapping("/content")
@CrossOrigin("*")
public class ContentController {

    private ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/search")
    public String search() {
        return "/content/search";
    }

    @GetMapping("/getArea")
    public ResponseEntity<?> getArea() {
        try {
            List<AreaDto> list = contentService.getAreaCode();
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<AreaDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/getSigungu/{areaCode}")
    public ResponseEntity<?> getSigungu(@PathVariable int areaCode) {
        try {
            List<SigunguDto> list = contentService.getSigunguCode(areaCode);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<SigunguDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/getPlace/{areaCode}/{sigunguCode}")
    public ResponseEntity<?> getPlace(@PathVariable Map<String, Object> map) {
        try {
            List<PlaceDto> list = contentService.getPlaceInfo(map);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<PlaceDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/searchPlace/{key}")
    public ResponseEntity<?> searchPlace(@PathVariable Map<String, Object> map) {
        try {
            List<PlaceDto> list = contentService.getSearchPlaceInfo(map);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<PlaceDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/place/random")
    public ResponseEntity<?> getRandomPlace() {
        try {
            List<PlaceDto> list = contentService.getRandomPlaceInfo();
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<PlaceDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PostMapping("/plan/write")
    public ResponseEntity<?> writePlan(@RequestBody PlanDto planDto) {
        try {
            contentService.writePlan(planDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/plan/list")
    public ResponseEntity<?> getPlan() {
        try {
            List<PlanDto> list = contentService.getPlanList();
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<PlanDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/plan/list/{userId}")
    public ResponseEntity<?> getUserPlanList(@PathVariable String userId) {
        try {
            List<PlanDto> list = contentService.getUserPlanList(userId);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<PlanDto>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/plan/view/{articleNo}")
    public ResponseEntity<?> getPlanView(
            @PathVariable("articleNo") int articleNo) {
        try {
            Map<String, List<?>> map = contentService.getPlanPlace(articleNo);
            contentService.updateHit(articleNo);
            if (map.get("planItems") != null && !map.get("planItems").isEmpty()) {
                return new ResponseEntity<Map<String, List<?>>>(map, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/plan/{articleNo}")
    public ResponseEntity<?> planDelete(@PathVariable("articleNo") int articleNo) {
        try {
            contentService.deletePlan(articleNo);
            Map<String, List<?>> map = contentService.getPlanPlace(articleNo);
            if (map.get("planItems") != null && !map.get("planItems").isEmpty()) {
                return new ResponseEntity<Map<String, List<?>>>(map, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
