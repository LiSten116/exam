package com.hs.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.hs.dao.ClazzDao;
import com.hs.model.Clazz;
import com.hs.utils.C3P0Utils;
import com.hs.utils.Page;

public class ClazzDaoImpl implements ClazzDao{
	
	private QueryRunner db = new QueryRunner(C3P0Utils.getDataSource());
	
	/**
	 * ��ѯ�༶�б�
	 */
	@Override
	public List<Map<String,Object>> queryClazzList(String gradeName, String majorName, int curPage,Page page)
			throws SQLException {
		//��������ѯ����clazzΪ�����ؼ����꼶��רҵ����ȡ�꼶��רҵ����
		String sql = "select t1.id,t1.cno,t2.name as gradeName,t3.name as majorName "
				+ " from clazz t1 "
				+ " left join grade t2 on t1.fk_grade=t2.id "
				+ " left join major t3 on t1.fk_major=t3.id where t1.del_flag=0 ";
		//�жϲ�ѯ�ؼ����Ƿ�Ϊ��
		if(StringUtils.isNotBlank(gradeName)) {
			sql += " and t2.name like '%"+gradeName+"%'";
			page.appendParam("gradeName", gradeName);
		}
		if(StringUtils.isNotBlank(majorName)) {
			sql += " and t3.name like '%"+majorName+"%'";
			page.appendParam("majorName", majorName);
		}
		sql += " order by t1.id desc limit "+(curPage-1)*page.getPageSize()+","+page.getPageSize();
		return db.query(sql, new MapListHandler());
	}
	
	/**
	 * ��ѯ�༶����
	 */
	public int queryClazzCount(String gradeName, String majorName) throws SQLException {
		String sql = "select count(*) from clazz t1 "
				+ "left join grade t2 on t1.fk_grade=t2.id "
				+ "left join major t3 on t1.fk_major=t3.id where t1.del_flag=0 ";
		//�жϲ�ѯ�ؼ����Ƿ�Ϊ��
		if(StringUtils.isNotBlank(gradeName)) {
			sql += " and t2.name like '%"+gradeName+"%'";
		}
		if(StringUtils.isNotBlank(majorName)) {
			sql += " and t3.name like '%"+majorName+"%'";
		}
		Long rowsCount = db.query(sql, new ScalarHandler<>());
		return rowsCount.intValue();
	}
	
	/**
	 * ����༶��Ϣ
	 */
	@Override
	public int saveClazz(String gradeId, String majorId, String cno) throws SQLException {
		String sql = "insert into clazz(cno,fk_grade,fk_major) values("+cno+","+gradeId+","+majorId+")";
		return db.update(sql);
	}
	
	/**
	 * ����������ѯ�༶��Ϣ
	 */
	@Override
	public Clazz getClazzByGidAndMidAndCno(String gradeId, String majorId, String cno) throws SQLException {
		String sql = "select * from clazz where fk_grade="+gradeId+" and fk_major="+majorId+" and cno="+cno+" and del_flag=0";
		return db.query(sql, new BeanHandler<Clazz>(Clazz.class));
	}
	
	/**
	 * ɾ���༶����ɾ����
	 */
	@Override
	public int delClazz(String clazzId) throws SQLException{
		String sql = "update clazz set del_flag=1 where id="+clazzId;
		return db.update(sql);
	}
}
