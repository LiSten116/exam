package com.hs.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hs.service.LoginService;
import com.hs.service.impl.LoginServiceImpl;
import com.hs.utils.MD5Util;

/**
 * Servlet implementation class DoLoginServlet
 */
@WebServlet("/doLogin")
public class DoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ��¼���û���������,�Լ���ɫID
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleId = request.getParameter("role");
		String auto = request.getParameter("auto");
		//����service�㷽��
		LoginService loginService = new LoginServiceImpl();
		String result = loginService.login(Integer.parseInt(roleId), username, password,request);
		//���ѡ���Զ���¼����Ҫ����¼��Ϣ���浽cookie��
		if("true".equals(auto)) {
			String info = username+"<>"+password+"<>"+roleId;
			Cookie cookie = new Cookie("exam_user_info",info);
			cookie.setMaxAge(30*24*60*60);//����30��
			response.addCookie(cookie);
		}else {
			//���û��ѡ���Զ���¼����cookie���Ƴ���¼��Ϣ
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("exam_user_info")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		response.getWriter().write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
