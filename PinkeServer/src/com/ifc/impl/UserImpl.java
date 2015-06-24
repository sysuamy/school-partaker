package com.ifc.impl;

import com.bean.MessageBean;
import com.bean.UserBean;
import com.ifc.UserInterface;
import com.util.HibernateUtil;

public class UserImpl implements UserInterface {
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	public boolean checkUser(String userName) {
		long count = HibernateUtil.getCountBySql("select count(*) from t_user where suser = '"+userName+"'");
		if(count > 0){
			return true;
		}
		return false;
	}

	public boolean checkPwd(String userName,String spwd) {
		UserBean userBean = hibernateUtil.getObjById(UserBean.class,userName);
		System.out.print("password:"+userBean.getSpwd()+"="+spwd);
		if(userBean != null && userBean.getSpwd().equals(spwd)){
			return true;
		}
		hibernateUtil.closeSession();
		return false;
	}

	public boolean checkExist(String userName) {
		long count = HibernateUtil.getCountBySql("select count(*) from t_user where suser = '"+userName+"'");
		if(count > 0){
			return false;
		}
		return true;
	}

	public boolean addUser(UserBean userBean) {
		MessageBean messageBean = hibernateUtil.saveObj(userBean);
		return messageBean.isMessageFlag();
	}

	public UserBean getUserById(String userName) {
		UserBean userBean = hibernateUtil.getObjById(UserBean.class,userName);
		hibernateUtil.closeSession();
		return userBean;
	}

	public boolean updateUser(UserBean userBean) {
		return hibernateUtil.updateObj(userBean).isMessageFlag();
	}

}
