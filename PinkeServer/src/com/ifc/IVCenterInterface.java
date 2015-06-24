package com.ifc;

import java.util.List;

import com.bean.IVCenterBean;
import com.bean.ReviewBean;

public interface IVCenterInterface {
	
	/**
	 * 获取志愿者中心集合
	 * @param limit 
	 * @param start 
	 * @return
	 */
	List<IVCenterBean> getList(int start, int limit);

	/**
	 * 发表说说
	 * @param ivCenterBean
	 * @return
	 */
	boolean addIVCenter(IVCenterBean ivCenterBean);

	/**
	 * 添加评论
	 * @param reviewBean
	 * @return
	 */
	boolean addReView(ReviewBean reviewBean);

	/**
	 * 获取该key下的评论
	 * @param reFKey 
	 * @return
	 */
	List<ReviewBean> getReviewList(int reFKey);

}
