package com.ifc.impl;

import java.util.List;

import com.bean.IVCenterBean;
import com.bean.ReviewBean;
import com.ifc.IVCenterInterface;
import com.util.HibernateUtil;

public class IVCenterImpl implements IVCenterInterface{
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	public List<IVCenterBean> getList(int start,int limit) {
		List<IVCenterBean> ivcenterList =  hibernateUtil.getList("From IVCenterBean a order by a.ivKey desc", start, limit);
		for(IVCenterBean bean : ivcenterList){
			bean.setIcUrl(bean.getUser().getSimgPath());
			bean.setName(bean.getUser().getSuser());
		}
		hibernateUtil.closeSession();
		return ivcenterList;
	}

	public boolean addIVCenter(IVCenterBean ivCenterBean) {
		return hibernateUtil.saveObj(ivCenterBean).isMessageFlag();
	}

	public boolean addReView(ReviewBean reviewBean) {
		return hibernateUtil.saveObj(reviewBean).isMessageFlag();
	}

	public List<ReviewBean> getReviewList(int reFKey) {
		return hibernateUtil.getList("From ReviewBean a where a.reFkey = '"+reFKey+"' order by a.reKey desc");
	}
	
}
