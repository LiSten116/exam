package com.hs.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hs.dao.LoginDao;
import com.hs.dao.impl.LoginDaoImpl;
import com.hs.model.Manager;
import com.hs.model.Student;
import com.hs.model.Teacher;
import com.hs.service.LoginService;
import com.hs.utils.MD5Util;

public class LoginServiceImpl implements LoginService{

	private LoginDao loginDao = new LoginDaoImpl();
	
	/**
	 * ��¼����
	 */
	@Override
	public String login(Integer roleId, String username, String password,HttpServletRequest request) {
		//���巵��ֵ����
		String result = null;
		try {
			Object obj = loginDao.login(roleId, username, MD5Util.getMD5(password));
			if(obj==null) {//��¼ʧ��
				result="no";
			}else {//��¼�ɹ�
				result="yes";
				if(roleId==1) {
					request.getSession().setAttribute("roleId", roleId);
					request.getSession().setAttribute("user", (Student)obj);
				}else if(roleId==2) {
					request.getSession().setAttribute("roleId", roleId);
					request.getSession().setAttribute("user", (Teacher)obj);
				}else if(roleId==3) {
					request.getSession().setAttribute("roleId", roleId);
					request.getSession().setAttribute("user", (Manager)obj);
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			result="error";
		}
		
		return result;
	}
	
	/**
	 * �޸�����
	 */
	@Override
	public String updatePassword(HttpServletRequest request) {
		String result = null;
		
		//��ȡҳ�����
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String rePwd = request.getParameter("rePwd");
		//�жϾ������Ƿ���ȷ
		HttpSession session = request.getSession();
		Integer roleId = (Integer)session.getAttribute("roleId");
		Object ouser = session.getAttribute("user");
		String username = null;
		Integer userId = null;
		//���ݽ�ɫ����ID��session�л�ȡ�����û�����ת��Ϊ��Ӧ�����Ͳ���ȡusername��id
		if(roleId==1) {//ѧ��
			username = ((Student)ouser).getUsername();
			userId = ((Student)ouser).getId();
		}else if(roleId==2) {//��ʦ
			username = ((Teacher)ouser).getUsername();
			userId = ((Teacher)ouser).getId();
		}else if(roleId==3) {//����Ա
			username = ((Manager)ouser).getUsername();
			userId = ((Manager)ouser).getId();
		}
		try {
			Object user = loginDao.login(roleId, username, MD5Util.getMD5(oldPwd));
			//���userΪNull��ʾԭ�������
			if(user==null) {
				result = "wrong";
			}else {
				//�ж���������������Ƿ�һ��
				if(newPwd!=null && newPwd.equals(rePwd)) {
					int rows = loginDao.updatePassword(roleId, MD5Util.getMD5(newPwd), userId);
					if(rows==1) {
						result = "ok";
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
