package com.ssafy.enjoytrip.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.content.model.AreaDto;
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

    @GetMapping("/getSigungu/{areacode}")
    public ResponseEntity<?> getSigungu(@PathVariable("areacode") int areaCode) {
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

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
