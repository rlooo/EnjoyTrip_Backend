package com.ssafy.enjoytrip.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import com.ssafy.enjoytrip.board.model.service.BoardServiceImpl;
import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.util.ParameterCheck;

@Controller
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService;

	public void init() {
		boardService = BoardServiceImpl.getBoardService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String act = request.getParameter("act");
		System.out.println("act>>"+act);
		int pgNo = ParameterCheck.notNumberToOne(request.getParameter("pgno"));
		String key = ParameterCheck.nullToBlank(request.getParameter("key"));
		String word = ParameterCheck.nullToBlank(request.getParameter("word"));
		String queryString = "pgno=" + pgNo + "&key=" + key + "&word=" + ParameterCheck.urlEncoding(word);
		System.out.println(queryString);

		if ("list".equals(act)) {
			list(request, response, queryString);
		} else if ("mvwrite".equals(act)) {
			mvwrite(request, response, "/board/board_write.jsp");
		} else if ("write".equals(act)) {
			write(request, response, queryString);
		} else if ("view".equals(act)) {
			view(request, response, queryString);
		} else if ("mvmodify".equals(act)) {
			mvModify(request, response, queryString);
		} else if ("modify".equals(act)) {
			modify(request, response, queryString);
		} else if ("delete".equals(act)) {
			delete(request, response, queryString);
		} else if("forgot".equals(act)){
			forgot(request, response, queryString);
		}else if("loadboard".equals(act)){
			loadboard(request, response, queryString);
			System.out.println("onload");
		}else{
			redirect(request, response, "/index.jsp");
		}
  
	}


	private void loadboard(HttpServletRequest request, HttpServletResponse response, String queryString) {
		boardService.loadboard(request, response, queryString);
	}

	private void mvwrite(HttpServletRequest request, HttpServletResponse response, String string) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		request.setAttribute("userId", userDto.getUserId());
		forward(request, response, "/board/board_write.jsp");
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

	private void modify(HttpServletRequest request, HttpServletResponse response, String queryString) throws ServletException, IOException {
			
		BoardDto boardDto = new BoardDto();
		boardDto.setArticleNo(Integer.parseInt(request.getParameter("articleno")));
		boardDto.setSubject(request.getParameter("subject"));
		boardDto.setContent(request.getParameter("content"));
		try {
			boardService.modifyPost(boardDto);
			redirect(request, response, "/board?act=list&" + queryString);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글작성 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}


	}

	private void delete(HttpServletRequest request, HttpServletResponse response, String queryString) throws ServletException, IOException {
		try {
			int postId = Integer.parseInt(request.getParameter("articleno"));
			boardService.deletePost(postId);
			redirect(request, response, "/board?act=list&" + queryString);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 삭제 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}
	}

	private void mvModify(HttpServletRequest request, HttpServletResponse response, String queryString)	throws IOException, ServletException {
		try {
			int postId = Integer.parseInt(request.getParameter("articleno"));
			BoardDto boardDto = boardService.getPost(postId);
			boardService.updateHit(postId);
			request.setAttribute("article", boardDto);
			forward(request, response, "/board/board_modify.jsp?" + queryString);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 얻기 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}

	}

	private void view(HttpServletRequest request, HttpServletResponse response, String queryString)	throws ServletException, IOException {

		try {
			int postId = Integer.parseInt(request.getParameter("articleno"));
			BoardDto boardDto = boardService.getPost(postId);
			boardService.updateHit(postId);
			request.setAttribute("article", boardDto);
			forward(request, response, "/board/board_view.jsp?" + queryString);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 얻기 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}

	}

	private void write(HttpServletRequest request, HttpServletResponse response, String queryString) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		
		if (userDto != null) {

			BoardDto boardDto = new BoardDto();
			boardDto.setUserId(userDto.getUserId()); //수정
			boardDto.setSubject(request.getParameter("subject"));
			boardDto.setUserId(request.getParameter("author")); //수정
			boardDto.setContent(request.getParameter("content"));
			try {
				boardService.writePost(boardDto);
				redirect(request, response, "/board?act=list&" + queryString);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글작성 중 에러발생!!!");
				forward(request, response, "/error/error.jsp");
			}
		} else {
			redirect(request, response, "/user?act=mvlogin");
		}

	}

	private void list(HttpServletRequest request, HttpServletResponse response, String queryString)
			throws ServletException, IOException {

		try {
			int pgNo = ParameterCheck.notNumberToOne(request.getParameter("pgno"));
			String key = ParameterCheck.nullToBlank(request.getParameter("key"));
			String word = ParameterCheck.nullToBlank(request.getParameter("word"));
			Map<String, String> map = new HashMap<>();
			map.put("pgno", pgNo + "");
			map.put("key", key);
			map.put("word", word);
			List<BoardDto> list = boardService.listPost(map);
			request.setAttribute("posts", list);
			System.out.println("a");
			forward(request, response, "/board/board_list.jsp?" + queryString);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글목록 얻기 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}

	}
	
	private void forgot(HttpServletRequest request, HttpServletResponse response, String queryString) {
		
		
	}

}
