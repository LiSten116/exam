package com.hs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.hs.utils.Page;

/**
 * ��ҳ��ǩ��
 * @author snake
 *
 */
public class PageTag extends SimpleTagSupport{
	
	private String action;//��ǩ����

	@Override
	public void doTag() throws JspException, IOException {
		//��ȡjsp���ö���pageContext��ͨ��pageContext���Ի�ȡ���������ö��󣬱���request
		PageContext pageContext = (PageContext)this.getJspContext();
		Page page = (Page)pageContext.getRequest().getAttribute("page");
		//��ȡ��Ŀ����·��
		String path = pageContext.getRequest().getServletContext().getContextPath();
		String basePath = pageContext.getRequest().getScheme()+"://"
				+pageContext.getRequest().getServerName()+":"
				+pageContext.getRequest().getServerPort()+path+"/";
		//��ȡ��ҳ��������html�ַ���
		String pageBar = page.getPageBar(action, basePath);
		pageContext.getOut().println(pageBar);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
