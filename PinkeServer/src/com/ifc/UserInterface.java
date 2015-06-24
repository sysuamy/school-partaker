package com.ifc;

import com.bean.UserBean;


public interface UserInterface {

	/**
	 * 检查用户是否存在
	 * @param userName
	 * @return boolean
	 */
	boolean checkUser(String userName);

	/**
	 * 检查密码是否正确
	 * @param userName
	 * @param spwd
	 * @return boolean
	 */
	boolean checkPwd(String userName, String spwd);

	/**
	 * 检验用户名是否存在
	 * @param userName
	 * @return
	 */
	boolean checkExist(String userName);

	/**
	 * 添加用户
	 * @param userBean
	 * @return
	 */
	boolean addUser(UserBean userBean);

	/**
	 * 根据Id查找用户
	 * @param userName
	 * @return
	 */
	UserBean getUserById(String userName);

	/**
	 * 更新用户信息
	 * @param userBean
	 * @return
	 */
	boolean updateUser(UserBean userBean);

	
}
