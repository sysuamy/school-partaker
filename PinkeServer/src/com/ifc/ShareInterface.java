package com.ifc;

import java.util.List;
import java.util.Map;

import com.bean.ShareBean;
import com.bean.UserBean;

public interface ShareInterface {

	/**
	 * 获取分享信息集合
	 * @param params 
	 * @return
	 */
	List<ShareBean> getList(Map<String, Object> params);

	/**
	 * 通过ID获取
	 * @param activeId
	 * @return
	 */
	ShareBean getShareById(int activeId);

	/**
	 * 
	 * @param shareBean
	 * @return
	 */
	boolean add(ShareBean shareBean);

	/**
	 * 
	 * @param shareBean
	 * @return
	 */
	boolean update(ShareBean shareBean);
    
	/**
	 * 
	 * @param UserList
	 * @return
	 */
	List<UserBean> getUserListByActiveId(int activeId);
	
	public int deleteJoinUser(String  user,int activityId);
	
}
