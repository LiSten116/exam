package com.hs.service;

import java.util.List;

import com.hs.model.Menu;

public interface MenuService {

	/**
	 * ���ݽ�ɫID��ѯ�˵��б�
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenusByRoleId(Integer roleId);
}
