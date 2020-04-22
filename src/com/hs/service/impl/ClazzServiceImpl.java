package com.hs.service.impl;

import java.util.List;
import java.util.Map;

import com.hs.dao.ClazzDao;
import com.hs.dao.impl.ClazzDaoImpl;
import com.hs.model.Clazz;
import com.hs.service.ClazzService;
import com.hs.utils.Page;

public class ClazzServiceImpl implements ClazzService{

	private ClazzDao clazzDao = new ClazzDaoImpl();
	
	/**
	 * ��ҳ��ѯ�༶�б�
	 */
	@Override
	public Page<Clazz> getClazzPage(String gradeName, String majorName,int curPage) {
		Page page = new Page();
		try {
			List<Map<String,Object>> list = clazzDao.queryClazzList(gradeName, majorName, curPage, page);
			int rowsCount = clazzDao.queryClazzCount(gradeName, majorName);
			page.setParam(list, curPage, rowsCount);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * ����༶��Ϣ
	 */
	@Override
	public String saveClazz(String gradeId, String majorId, String cno) {
		String result = null;
		try {
			//�жϸð༶�Ƿ��Ѿ������
			Clazz clazz = clazzDao.getClazzByGidAndMidAndCno(gradeId, majorId, cno);
			if(clazz!=null) {//�Ѵ���
				result="exist";
			}else {//������
				int rows = clazzDao.saveClazz(gradeId, majorId, cno);
				if(rows==1) {
					result="ok";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ɾ���༶
	 */
	@Override
	public String delClazz(String clazzId) {
		String result = null;
		try {
			int rows = clazzDao.delClazz(clazzId);
			if(rows==1) {
				result="ok";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
