package com.hs.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;

/**
 * ��ҳ������
 * @author snake
 *
 * @param <T>
 */
public class Page<T> {

	//��ǰҳ����ʾ�����ݼ���
	private List<T> data = null;
	
	//��ǰҳ��
	private int curPage;
	
	//ÿҳ��ʾ�ļ�¼����
	private int pageSize=4;
	
	//�ܼ�¼����
	private int rowsCount;
	
	//��ҳ��
	private int pageCount;
	
	//���ڴ��������ѯ�Ĳ�ѯ�ؼ���
	private Map<String,Object> params;

	public Page() {
		super();
	}
	
	public Page(List<T> data,int curPage,int rowsCount) {
		this.data = data;
		this.curPage = curPage;
		this.rowsCount = rowsCount;
		//Math.ceil()��������
		this.pageCount = ((int)Math.ceil((double)rowsCount/pageSize))==0 ? 1 : ((int)Math.ceil((double)rowsCount/pageSize));
	}
	
	public void setParam(List<T> data,int curPage,int rowsCount) {
		this.data = data;
		this.curPage = curPage;
		this.rowsCount = rowsCount;
		this.pageCount = ((int)Math.ceil((double)rowsCount/pageSize))==0 ? 1 : ((int)Math.ceil((double)rowsCount/pageSize));
	}
	
	/**
	 * ��Ӳ�ѯ�ؼ��ֵ�map��
	 * @param name
	 * @param value
	 */
	public void appendParam(String name,Object value) {
		if(params==null) {
			params = new HashedMap();
		}
		//�������Ʋ�Ϊ���ҵ�ǰmap�в�������key
		if(name!=null && !name.trim().equals("") && params.containsKey(name)==false) {
			params.put(name, value);
		}
	}
	
	/**
	 * ���ɷ�ҳ������
	 * @param action
	 * @param basePath
	 * @return
	 */
	public String getPageBar(String action,String basePath) {
		StringBuffer sb = new StringBuffer();
		//����ҳ������1ҳ��ʱ�����Ҫ��ҳ������
		if(pageCount>1) {
			sb.append("<form method=\"post\" id=\"pageForm\" name=\"pageForm\" style=\"margin-bottom:0;height:35px\">");
			//��������ѯ�Ĳ������õ�form����������
			if(params!=null && !params.isEmpty()) {
				Iterator<Entry<String,Object>> ite = params.entrySet().iterator();
				while(ite.hasNext()) {
					Map.Entry<String,Object> et = (Map.Entry<String,Object>)ite.next();
					String key = (String)et.getKey();
					Object value = (Object)et.getValue();
					sb.append("<input type='hidden' name='"+key+"' value='"+(value!=null?value:"")+"'>");
				}
			}
			
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"col-sm-12 col-md-12\">");
			sb.append("<div class=\"pull-right\">");
			sb.append("<ul class=\"pagination\">");
			//��ҳ����һҳ��ť
			if(curPage==1) {
				//��ǰҳΪ1ʱ����ҳ����һҳΪdisabled���޷����
				sb.append("<li class=\"paginate_button page-item previous disabled\"><a href=\"#\" class=\"page-link\">��ҳ</a></li>");
				sb.append("<li class=\"paginate_button page-item previous disabled\"><a href=\"#\" class=\"page-link\">��һҳ</a></li>");
			}else {
				//	�������ʹ��
				sb.append("<li class=\"paginate_button page-item previous \"><a href=\"javascript:pageForm.action='"+basePath+action+"?curPage=1';pageForm.submit();\" class=\"page-link\">��ҳ</a></li>");
				sb.append("<li class=\"paginate_button page-item previous \"><a href=\"javascript:pageForm.action='"+basePath+action+"?curPage="+(curPage-1)+"';pageForm.submit();\" class=\"page-link\">��һҳ</a></li>");
			}
			//�м������ҳ��
			for(int i=1;i<=pageCount;i++) {
				if(i==curPage) {
					sb.append("<li class=\"paginate_button page-item active\"><a href=\"javascript:pageForm.action='"+basePath+action+"?curPage="+i+"';pageForm.submit();\" class=\"page-link\">"+i+"</a></li>");
				}else {
					sb.append("<li class=\"paginate_button page-item\"><a href=\"javascript:pageForm.action='"+basePath+action+"?curPage="+i+"';pageForm.submit();\" class=\"page-link\">"+i+"</a></li>");
				}
			}
			//��β����һҳ�����һҳ��ť
			if(curPage<pageCount) {
				sb.append("<li class=\"paginate_button page-item\"><a href=\"javascript:pageForm.action='"+basePath+action+"?curPage="+(curPage+1)+"';pageForm.submit();\" class=\"page-link\">��һҳ</a></li>");
				sb.append("<li class=\"paginate_button page-item\"><a href=\"javascript:pageForm.action='"+basePath+action+"?curPage="+pageCount+"';pageForm.submit();\" class=\"page-link\">���һҳ</a></li>");
			}else {
				sb.append("<li class=\"paginate_button page-item disabled\"><a href=\"#\" class=\"page-link\">��һҳ</a></li>");
				sb.append("<li class=\"paginate_button page-item disabled\"><a href=\"#\" class=\"page-link\">���һҳ</a></li>");
			}
			sb.append("</ul>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</form>");
		}
		return sb.toString();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
}
