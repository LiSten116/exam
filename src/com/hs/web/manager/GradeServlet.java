package com.hs.web.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.hs.service.GradeService;
import com.hs.service.impl.GradeServiceImpl;
import com.hs.utils.Page;

/**
 * Servlet implementation class GradeServlet
 */
@WebServlet("/manager/grade")
public class GradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeServlet() {
        super();
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
		//��ȡ��ѯ�ؼ���
		String name = request.getParameter("name");
		//��ѯ�꼶�б�
		GradeService gradeService = new GradeServiceImpl();
		Page page = gradeService.getGradesByName(name,Integer.parseInt(curPage));
		//����ѯ�������ҳ��
		request.setAttribute("page", page);
		//�ؼ��ֻ���
		request.setAttribute("name", name);
		request.getRequestDispatcher("/WEB-INF/page/manager/grade_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
