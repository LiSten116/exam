package com.hs.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hs.model.Menu;
import com.hs.service.LoginService;
import com.hs.service.MenuService;
import com.hs.service.impl.LoginServiceImpl;
import com.hs.service.impl.MenuServiceImpl;

/**
 * ��ҳ
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�ж��û��Ƿ��¼
		Object obj = request.getSession().getAttribute("user");
		if(obj!=null) {
			//���ݽ�ɫ��ȡ�˵�
			Integer roleId = (Integer)request.getSession().getAttribute("roleId");//��ȡ��ɫID���ڵ�¼�ɹ�ʱ����session��
			//����service��ķ���
			MenuService menuService = new MenuServiceImpl();
			List<Menu> menus = menuService.getMenusByRoleId(roleId);
			//����ѯ������浽request���У�����ҳ��
			request.setAttribute("menus", menus);
			//��ת����ҳ
			request.getRequestDispatcher("/WEB-INF/page/index.jsp").forward(request, response);
		}else {
			//�ж��û��Ƿ�ѡ���Զ���¼
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie:cookies) {
				if("exam_user_info".equals(cookie.getName())) {
					String userInfo = cookie.getValue();
					String[] infoArray = userInfo.split("<>");
					String username = infoArray[0];
					String password = infoArray[1];
					Integer roleId = Integer.parseInt(infoArray[2]);
					//ִ�е�¼
					LoginService loginService = new LoginServiceImpl();
					String result = loginService.login(roleId, username, password,  request);
					if(result.equals("yes")) {
						//����service��ķ���
						MenuService menuService = new MenuServiceImpl();
						List<Menu> menus = menuService.getMenusByRoleId(roleId);
						//����ѯ������浽request���У�����ҳ��
						request.setAttribute("menus", menus);
						request.getRequestDispatcher("/WEB-INF/page/index.jsp").forward(request, response);
						return;
					}else {
						//�ض��򵽵�¼ҳ��
						response.sendRedirect("login");
						return;
					}
				}
			}
			response.sendRedirect("login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
