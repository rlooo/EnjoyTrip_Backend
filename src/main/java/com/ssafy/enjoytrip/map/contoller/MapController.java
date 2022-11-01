package com.ssafy.enjoytrip.map.contoller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ssafy.enjoytrip.map.model.DoDto;
import com.ssafy.enjoytrip.map.model.GugunDto;
import com.ssafy.enjoytrip.map.model.AreaDto;
import com.ssafy.enjoytrip.map.service.MapService;
import com.ssafy.enjoytrip.map.service.MapServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class MapController
 */
@Controller
public class MapController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final MapService mapService;
	
	@Autowired
	public MapController(MapService mapService) {
		this.mapService = mapService;
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String path = "/index.jsp";
		
		if("loadDo".equals(action)) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				List<DoDto> list = loadDo(request, response);
				String dtoJsonArr = mapper.writeValueAsString(list);
				
	            response.setContentType("text/plain;charset=utf-8");
	            PrintWriter out = response.getWriter();
	            out.println(dtoJsonArr);
				
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "시도 선택중 에러 발생!!!");
				forward(request, response, "/error/error.jsp");
			}
		}else if("loadGugun".equals(action)) {
			ObjectMapper mapper = new ObjectMapper();
			int areaCode = Integer.parseInt(request.getParameter("areaCode"));
			List<GugunDto> list;
			try {
				list = loadGugun(areaCode);
				String dtoJsonArr = mapper.writeValueAsString(list);
				
				response.setContentType("text/plain;charset=utf-8");
				PrintWriter out = response.getWriter();
				System.out.println("ready to send");
				out.println(dtoJsonArr);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "구군 선택중 에러 발생!!!");
				forward(request, response, "/error/error.jsp");
			}
			
		}else if("search".equals(action)) {
			List<AreaDto> list = null;
			int sidoCode = Integer.parseInt(request.getParameter("sidoCode"));
			int gugunCode = Integer.parseInt(request.getParameter("gugunCode"));
			int kindCode = Integer.parseInt(request.getParameter("kindCode"));
			int page = Integer.parseInt(request.getParameter("page"));
			try {
				
				ObjectMapper mapper = new ObjectMapper();
				list = search(sidoCode, gugunCode, kindCode, page);
				String dtoJsonArr = mapper.writeValueAsString(list);
				
				response.setContentType("text/plain;charset=utf-8");
	            PrintWriter out = response.getWriter();
	            out.println(dtoJsonArr);
				
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "여행지 데이터 로드중 에러 발생!!!");
				forward(request, response, "/error/error.jsp");
			}
		}else if("searchByTitle".equals(action)) {
			List<AreaDto> list = null;
			String title = request.getParameter("title");
			int page = Integer.parseInt(request.getParameter("page"));
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				list = searchByTitle(title, page);
				String dtoJsonArr = mapper.writeValueAsString(list);
				
				
				response.setContentType("text/plain;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(dtoJsonArr);				
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "여행지 데이터 로드중 에러 발생!!!");
				forward(request, response, "/error/error.jsp");
			}
		}
	}

//	private List<AreaDto> searchByTitle(String title, int page) {
//		mapService.searchByTitle(title, page);
//	}


	private List<AreaDto> searchByTitle(String title, int page) throws Exception {
		return mapService.searchByTitle(title, page);
	}


	private List<AreaDto> search(int sidoCode, int gugunCode, int kindCode, int page) throws Exception {
		return mapService.search(sidoCode, gugunCode, kindCode, page); 
	}


	private List<GugunDto> loadGugun(int areaCode) throws Exception {
		return mapService.loadGugun(areaCode);
	}


	private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}


	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}


	private List<DoDto> loadDo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapService.loadDo(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
