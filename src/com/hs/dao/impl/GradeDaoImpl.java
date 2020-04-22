package com.hs.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;

import com.hs.dao.GradeDao;
import com.hs.model.Grade;
import com.hs.utils.C3P0Utils;
import com.hs.utils.Page;

public class GradeDaoImpl implements GradeDao{

	//�������ݿ��������
	private QueryRunner db = new QueryRunner(C3P0Utils.getDataSource());
	
	/**
	 * �����꼶���Ʋ�ѯ�꼶�б�
	 */
	@Override
	public List<Grade> getGradesByName(String name,Page page,Integer curPage) throws SQLException {
		String sql = "select * from grade where 1=1 ";
		if(StringUtils.isNotBlank(name)) {
			sql += " and name like'%"+name+"%'";
			//����ѯ�ؼ��ַ�װ��page������
			page.appendParam("name", name);
		}
		sql += " order by id desc limit "+(curPage-1)*page.getPageSize()+","+page.getPageSize();
		return db.query(sql, new BeanListHandler<Grade>(Grade.class));
	}
	
	/**
	 * ��ѯ�꼶�ܼ�¼������ҳ�ã�
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public int getGradesCount(String name) throws SQLException{
		String sql = "select count(*) from grade where 1=1 ";
		if(StringUtils.isNotBlank(name)) {
			sql += " and name like'%"+name+"%'";
		}
		Long rowsCount = db.query(sql, new ScalarHandler<>());
		return rowsCount.intValue();
	}
	
	/**
	 * �����꼶��Ϣ
	 */
	@Override
	public int saveGrade(String name) throws SQLException {
		String sql = "insert into grade(name) values('"+name+"')";
		return db.update(sql);
	}
	
	/**
	 * �����꼶���Ʋ�ѯ�꼶��Ϣ
	 */
	@Override
	public Grade queryByName(String name) throws SQLException {
		String sql = "select * from grade where name='"+name+"'";
		return db.query(sql, new BeanHandler<Grade>(Grade.class));
	}
	
	/**
	 * ��ѯ�����꼶�б�
	 */
	@Override
	public List<Grade> getGradeList() throws SQLException {
		String sql = "select * from grade where del_flag=0 order by id desc";
		return db.query(sql, new BeanListHandler<Grade>(Grade.class));
	}
}
