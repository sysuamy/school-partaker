package com.ifc.impl;

import java.util.List;
import java.util.Map;

import com.bean.ShareBean;
import com.bean.UserBean;
import com.ifc.ShareInterface;
import com.util.HibernateUtil;

public class ShareImpl implements ShareInterface{
	private HibernateUtil hibernateUtil;

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

	private String paramsHql(Map<String,Object> params){
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1 = 1 and a.MState='¿ª·Å'");
		String myActive = (String)params.get("myactive");
		String activeWeek = (String)params.get("activeweek");
		String regionId = (String)params.get("regionid");
		String suser = (String)params.get("suser");
		String category = (String)params.get("category");
		
		if(myActive!=null&&!"null".equals(myActive)&&!"".equals(myActive)){
			if("myjoin".equalsIgnoreCase(myActive)){
				sb.append(" and b.suser = '"+suser+"' and a in elements(b.shareSet)");
			}
			
			if("mylaunch".equalsIgnoreCase(myActive)){
				sb.append(" and a.MLaunch = '"+suser+"'");
			}
		}
		
		if(regionId != null && !"".equals(regionId) && !"null".equals(regionId)){
			sb.append(" and a.MPlace = '"+regionId+"'");
		}
		
		if(category != null && !"".equals(category) && !"null".equals(category)){
			sb.append(" and a.MCategory = '"+category+"'");
		}
		
		if(activeWeek != null && !"".equals(activeWeek) && !"null".equals(activeWeek)){
			if("1".equals(activeWeek)){
				sb.append(" and to_date(a.MStartDate)<=trunc(sysdate+7) and to_date(a.MStartDate)>=trunc(sysdate-7)");
			}
			if("2".equals(activeWeek)){
				sb.append(" and to_date(a.MStartDate)=trunc(sysdate)");
			}
			if("3".equals(activeWeek)){
				sb.append(" and to_date(a.MStartDate)=trunc(sysdate+1)");
			}
			if("4".equals(activeWeek)){
				sb.append(" and to_date(a.MStartDate)>=trunc(sysdate)");
			}
			
		}
		return sb.toString();
	}

	public List<ShareBean> getList(Map<String, Object> params) {
		String start = (String)params.get("start");
		String limit = (String)params.get("limit");
		String paramsHql = this.paramsHql(params);
		return hibernateUtil.getList("select distinct a From ShareBean a left outer join a.userSet as b " + paramsHql + " order by a.MId desc", Integer.parseInt(start), Integer.parseInt(limit));
	}

	public ShareBean getShareById(int activeId) {
		ShareBean shareBean =  hibernateUtil.getObjById(ShareBean.class,activeId);
		if(shareBean!=null && shareBean.getUserSet()!=null){
			for(UserBean userBean : shareBean.getUserSet()){}
		}
		hibernateUtil.closeSession();
		return shareBean;
	}

	public boolean add(ShareBean shareBean) {
		return hibernateUtil.saveObj(shareBean).isMessageFlag();
	}

	public boolean update(ShareBean shareBean) {
		return hibernateUtil.updateObj(shareBean).isMessageFlag();
	}

	public List<UserBean> getUserListByActiveId(int activeId) {
		ShareBean shareBean =  hibernateUtil.getObjById(ShareBean.class,activeId);
		hibernateUtil.closeSession();
		if(shareBean!=null && shareBean.getUserSet()!=null){
			return shareBean.getUserSet();
		}
		return null;
	}
	
	public int deleteJoinUser(String  user,int activityId){
		return hibernateUtil.deleteUserShareBySql("delete from T_USER_SHARE where SUSER='"+user+"' and M_ID="+activityId+"");
	}
}
