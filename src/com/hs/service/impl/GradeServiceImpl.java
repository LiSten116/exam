package com.hs.service.impl;

import java.util.List;

import com.hs.dao.GradeDao;
import com.hs.dao.impl.GradeDaoImpl;
import com.hs.model.Grade;
import com.hs.service.GradeService;
import com.hs.utils.Page;

public class GradeServiceImpl implements GradeService{

	private GradeDao gradeDao = new GradeDaoImpl();
	
	/**
	 * �����꼶���Ʋ�ѯ�б�
	 */
	@Override
	public Page<Grade> getGradesByName(String name,Integer curPage) {
		Page page = new Page();
		try {
			//��ѯ��ǰҳ��Ҫ��ʾ�ļ�¼�б�
			List<Grade> list = gradeDao.getGradesByName(name,page,curPage);
			//��ѯ�ܼ�¼��
			int rowsCount = gradeDao.getGradesCount(name);
			//����ѯ�����װ��page������
			page.setParam(list, curPage, rowsCount);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * �����꼶��Ϣ
	 */
	@Override
	public String saveGrade(String name) {
		String result = null;
		try {
			//1���жϸ��꼶�����Ƿ��Ѿ������
			Grade grade = gradeDao.queryByName(name);
			if(grade!=null) {
				//�Ѵ���
				result = "exist";
			}else {
				//������,������ӷ���
				int rows = gradeDao.saveGrade(name);
				if(rows==1) {
					result = "ok";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * ��ѯ�����꼶�б�
	 */
	@Override
	public List<Grade> getGradeList() {
		List<Grade> list = null;
		try {
			list = gradeDao.getGradeList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
