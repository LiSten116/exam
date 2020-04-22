package com.hs.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.hs.dao.LoginDao;
import com.hs.model.Manager;
import com.hs.model.Student;
import com.hs.model.Teacher;
import com.hs.utils.C3P0Utils;

public class LoginDaoImpl implements LoginDao{

	//��ȡ���ݿ��������
	private QueryRunner db = new QueryRunner(C3P0Utils.getDataSource());
	
	/**
	 * ��¼��ѯ
	 */
	@Override
	public Object login(Integer roleId, String username, String password) throws SQLException{
		String sql = "";
		Object obj = null;
		if(roleId==1) {//ѧ��
			sql = "select * from student where username='"+username+"'"+" and password='"+password+"' and del_flag=0";
			obj = db.query(sql, new BeanHandler<Student>(Student.class));
		}
		if(roleId==2) {//��ʦ
			sql = "select * from teacher where username='"+username+"'"+" and password='"+password+"' and del_flag=0";
			obj = db.query(sql, new BeanHandler<Teacher>(Teacher.class));
		}
		if(roleId==3) {//����Ա
			sql = "select * from manager where username='"+username+"'"+" and password='"+password+"'";
			obj = db.query(sql, new BeanHandler<Manager>(Manager.class));
		}
		return obj;
	}
	
	/**
	 * �޸�����
	 */
	@Override
	public int updatePassword(Integer roleId, String newPwd, Integer userId) throws SQLException {
		String sql = "";
		int result = 0;
		if(roleId==1) {//ѧ��
			sql = "update student set password='"+newPwd+"' , modified=1 where id="+userId;
			result = db.update(sql);
		}
		if(roleId==2) {//��ʦ
			sql = "update teacher set password='"+newPwd+"' , modified=1 where id="+userId;
			result = db.update(sql);
		}
		if(roleId==3) {//����Ա
			sql = "update manager set password='"+newPwd+"' , modified=1 where id="+userId;
			result = db.update(sql);
		}
		return result;
	}
}
