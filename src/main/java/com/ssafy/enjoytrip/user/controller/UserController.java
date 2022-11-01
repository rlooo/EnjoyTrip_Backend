package com.ssafy.enjoytrip.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.service.UserService;
import com.ssafy.enjoytrip.user.service.UserServiceImpl;

/**
 * Servlet implementation class MainContoller
 */
@Controller
public class UserController extends HttpServlet {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	String path = "/index.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String act = request.getParameter("act");
		System.out.println("doget >>> " + act);

		if ("mvjoin".equals(act)) {
			path = "/user/join.jsp";
			redirect(request, response, path);
		} else if ("idcheck".equals(act)) {
			int cnt = idCheck(request, response);
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(cnt);
		} else if ("join".equals(act)) {
			path = join(request, response);
			forward(request, response, path);
		} else if ("mvlogin".equals(act)) {
			path = "/user/login.jsp";
			redirect(request, response, path);
		} else if ("login".equals(act)) {
			path = login(request, response);
			forward(request, response, path);
		} else if ("logout".equals(act)) {
			path = logout(request, response);
			forward(request, response, path);
		} else if ("memberlist".equals(act)) {
			path = memberList(request, response);
			forward(request, response, path);
		} else if ("mvaccount".equals(act)) {
			mvaccount(request, response);
		} else if ("mvmodify".equals(act)) {
			mvmodify(request, response);
		} else if ("modify".equals(act)) {
			modify(request, response);
		} else if ("delete".equals(act)) {
			delaccount(request, response);
		} else if ("forgot_password".equals(act)) {
			forgotpassword(request, response);
		} else if ("certification".equals(act)) {
			certification(request, response);
		} else {
			redirect(request, response, path);
		}
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private String memberList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<UserDto> list = userService.listMember();
			request.setAttribute("memberlist", list);
			return "/user/memberlist.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "회원 목록 처리중 에러 발생!!!");
			return "/error/error.jsp";
		}
	}

	private String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("userinfo"); // 이거 수정함
		session.invalidate();
		return "/index.jsp";
	}

	private String login(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("sign-in-id");
		String userPwd = request.getParameter("sign-in-password");

		try {
			UserDto userDto = userService.loginMember(userId, userPwd);
			if (userDto != null) { // 로그인 성공(id, pwd 일치!!!!)

				String saveid = request.getParameter("saveid");
				System.out.println(saveid);
				if ("ok".equals(saveid)) { // 아이디 저장 체크 O.
					Cookie cookie = new Cookie("user_id", userId);
					cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
					cookie.setPath(request.getContextPath());

					response.addCookie(cookie);
				} else {
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals("user_id")) {
								cookie.setMaxAge(0);
								cookie.setPath(request.getContextPath());

								response.addCookie(cookie);
								break;
							}
						}
					}
				}
				HttpSession session = request.getSession();
				session.setAttribute("userinfo", userDto);
				String referer = request.getHeader("referer");
				System.out.println(referer);
				return "/index.jsp";
			} else { // 로그인 실패(id, pwd 불일치!!!!)
				request.setAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인!!!");
				return "/user/login.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "로그인 처리중 에러 발생!!!");
			return "/error/error.jsp";
		}
	}

	private String join(HttpServletRequest request, HttpServletResponse response) {
		UserDto userDto = new UserDto();
		userDto.setUserId(request.getParameter("sign-up-id"));
		userDto.setUserName(request.getParameter("sign-up-name"));
		userDto.setUserEmail(request.getParameter("sign-up-email"));
		userDto.setPassword(request.getParameter("sign-up-password"));
		System.out.println(userDto.toString());
		try {
			userService.joinMember(userDto);
			return "/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "회원 가입 처리중 에러 발생");
			return "/error/error.jsp";
		}
	}

	private int idCheck(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userid");
		System.out.println(userId);
		try {
			int count = userService.idCheck(userId);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 500;
	}

	private void mvaccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			UserDto userDto = (UserDto) session.getAttribute("userinfo");
			request.setAttribute("user", userDto);
			forward(request, response, "/user/account_view.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 얻기 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}

	}

	private void delaccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String userId = request.getParameter("userid");
			System.out.println(userId);
			userService.deleteMember(userId);

			session.removeAttribute("userinfo"); // 이거 수정함
			session.invalidate();
			redirect(request, response, "/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 삭제 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}

	}

	private void mvmodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			UserDto userDto = (UserDto) session.getAttribute("userinfo");
			request.setAttribute("user", userDto);
			forward(request, response, "/user/account_modify.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 얻기 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDto userDto = new UserDto();
		userDto.setUserId(request.getParameter("userId"));
		userDto.setUserName(request.getParameter("userName"));
		userDto.setUserEmail(request.getParameter("email"));
		userDto.setPhone(request.getParameter("phone"));
		try {
			userService.modifyMember(userDto);
			request.setAttribute("user", userDto);
			HttpSession session = request.getSession();
			session.setAttribute("userinfo", userDto);
			forward(request, response, "/user?act=mvaccount");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글작성 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}

	}

	private void forgotpassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userEmail = request.getParameter("useremail");
		String userId = request.getParameter("userid");
//		Map<String, String> map = new HashMap<>();
//		map.put("userId", userId);
//		map.put("userEmail", userEmail);

		try {
			userService.certificationMember(userEmail);
			request.setAttribute("userId", userId);
			forward(request, response, "/user/certification.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "인증과정 중 에러발생!!!");
			forward(request, response, "/error/error.jsp");
		}
	}

	private void certification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String certificationNumber = request.getParameter("certification_number");
		String userId = request.getParameter("userid");
		System.out.println(userId);
		System.out.println();
		if(certificationNumber.equals("1234")) {
			String password;
			System.out.println("a");
			try {
				password = userService.confirmPassword(userId);
				System.out.println(password);
				request.setAttribute("password",password);
				forward(request, response, "/user/confirm_password.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "인증과정 중 에러발생!!!");
				forward(request, response, "/error/error.jsp");
			}

		}else {
			request.setAttribute("msg", "잘못된 인증번호입니다.");
			forward(request, response, "/user/forgot_password.jsp");
		}
	}

	//////////////////////////////////////////////////////////////
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
