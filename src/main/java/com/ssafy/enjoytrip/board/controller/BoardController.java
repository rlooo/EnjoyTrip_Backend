package com.ssafy.enjoytrip.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.FileInfoDto;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import com.ssafy.enjoytrip.util.PageNavigation;


@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private final BoardService boardService;
	
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService=boardService;
	}
	
	
	@PostMapping("/write")
	public void write(BoardDto boardDto, @RequestParam("upfile") MultipartFile[] files) throws Exception {
		logger.debug("write boardDto : {}", boardDto);
		
//		FileUpload 관련 설정.
		logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		if (!files[0].isEmpty()) {
			String realPath = servletContext.getRealPath("/upload");
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = realPath + File.separator + today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			boardDto.setFileInfos(fileInfos);
		}
		boardService.writeBoard(boardDto);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam Map<String, Object> map){
		logger.debug("list parameter : {}", map);
		Map<String, Object> response= new HashMap<String, Object>();
		try {
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		List<BoardDto> list = boardService.getBoardList(map);
		
		if (list != null && pageNavigation!=null&&!list.isEmpty()) {
			response.put("articles", list);
			response.put("navigation", pageNavigation);
			response.put("pgno", map.get("pgno"));
			response.put("key", map.get("key"));
			response.put("word", map.get("word"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		
		}catch (Exception e) {
			return exceptionHandling(e);
		}
		
		
		return response;
    }
	
	@GetMapping("/view")
	public Map<String, Object> view(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map) throws Exception{
		logger.debug("view articleNo : {}", articleNo);
		Map<String, Object> response= new HashMap<>();
		BoardDto boardDto = boardService.getBoard(articleNo);
		boardService.updateHit(articleNo);
		response.put("article", boardDto);
		response.put("pgno", map.get("pgno"));
		response.put("key", map.get("key"));
		response.put("word", map.get("word"));
		return response;
	}
	

}
