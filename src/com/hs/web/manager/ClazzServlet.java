package com.hs.web.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.hs.service.ClazzService;
import com.hs.service.impl.ClazzServiceImpl;
import com.hs.utils.Page;

/**
 * Servlet implementation class ClazzServlet
 */
@WebServlet("/manager/clazz")
public class ClazzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClazzServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ��ǰҪ��ѯ��ҳ��
		String curPage = request.getParameter("curPage");
		if(StringUtils.isBlank(curPage)) {
			curPage="1";
		}
		//��ȡ�������
		String gradeName = request.getParameter("gradeName");
		String majorName = request.getParameter("majorName");
		//����service�㷽�����з�ҳ��ѯ
		ClazzService clazzService = new ClazzServiceImpl();
		Page page = clazzService.getClazzPage(gradeName, majorName, Integer.parseInt(curPage));
		request.setAttribute("page", page);
		//�ؼ��ֻ���
		request.setAttribute("gradeName", gradeName);
		request.setAttribute("majorName", majorName);
		request.getRequestDispatcher("/WEB-INF/page/manager/clazz_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
