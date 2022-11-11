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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		this.boardService = boardService;
	}

	@GetMapping()
	public ResponseEntity<?> list() {
		logger.debug("boardList call");

		Map<String, Object> response = new HashMap<String, Object>();
		try {
			List<BoardDto> list = boardService.getBoardList(null);

			if (list != null && !list.isEmpty()) {

				return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);

			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}
	@Transactional
	@PostMapping()
	public ResponseEntity<?> write(@Value("${file.path.upload-files}") String filePath, BoardDto boardDto, @RequestParam("upfile") MultipartFile[] files) {
		logger.debug("boardRegister boardDto : {}", boardDto);

		try {
//			FileUpload 관련 설정.
			logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
			if (!files[0].isEmpty()) {

				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = filePath + File.separator + today;
				logger.debug("저장 폴더 : {}", saveFolder);
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();
				List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
				for (MultipartFile mfile : files) {
					FileInfoDto fileInfoDto = new FileInfoDto();
					String originalFileName = mfile.getOriginalFilename();
					if (!originalFileName.isEmpty()) {
						String saveFileName = System.nanoTime()
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
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Transactional
	@GetMapping(value = "/{articleno}")
	public ResponseEntity<?> view(@PathVariable("articleno") int articleNo) throws Exception {
		logger.debug("boardView articleNo : {}", articleNo);
		try {
			BoardDto boardDto = boardService.getBoard(articleNo);
			boardService.updateHit(articleNo);
			if (boardDto != null)
				return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Transactional
	@PutMapping()
	public ResponseEntity<?> modify(@RequestBody BoardDto boardDto) {
		logger.debug("boardModify boardDto : {}", boardDto);
		try {
			boardService.modifyBoard(boardDto);
			List<BoardDto> list = boardService.getBoardList(null);
			return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@Transactional
	@DeleteMapping(value = "/{articleno}")
	public ResponseEntity<?> boardDelete(@PathVariable("articleno") int articleNo) {
		logger.debug("boardDelete articleNo : {}", articleNo);
		try {
			boardService.deleteBoard(articleNo);
			List<BoardDto> list = boardService.getBoardList(null);
			return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
