package com.hs.dao;

import java.sql.SQLException;
import java.util.List;

import com.hs.model.Menu;

public interface MenuDao {

	/**
	 * ���ݽ�ɫID��ѯ�˵��б�
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenusByRoleId(Integer roleId) throws SQLException;
}
