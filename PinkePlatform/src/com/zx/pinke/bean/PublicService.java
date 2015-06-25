package com.zx.pinke.bean;

import java.io.Serializable;
import java.util.List;

public class PublicService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 249426581419940813L;
	private long mId = 0;
	private String mKey;
	private String mTitle;//标题
	private String mCategory;//活动类别
	private String mPlace;//活动地点
	private String mStartDate;//活动开始时间
	private String mEndDate;//活动结束时间
	private long mJoinNum;//已报名人数
	private long mNeedNum;//需求人数
	private String mIconUrl;//图片URL
	private byte[] mIcon;//图片
	private String mContent;//活动内容
	private String mContacts;//联系人
	private String mState;//状态
	private String mLaunch;//状态
	
	private AccountInfo launcherInfo;//状态
	
	private List<AccountInfo> JoinUser;//参加此活动的用户
	
	public long getId() {
		return mId;
	}
	public void setId(long mId) {
		this.mId = mId;
	}
	public String getKey() {
		return mKey;
	}
	public void setKey(String mKey) {
		this.mKey = mKey;
	}
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getCategory() {
		return mCategory == null ? "" : mCategory;
	}
	public void setCategory(String mCategory) {
		this.mCategory = mCategory;
	}
	public String getPlace() {
		return mPlace == null ? "" : mPlace;
	}
	public void setPlace(String mPlace) {
		this.mPlace = mPlace;
	}
	public String getStartDate() {
		return mStartDate == null ? "" : mStartDate;
	}
	public void setStartDate(String mStartDate) {
		this.mStartDate = mStartDate;
	}
	public String getEndDate() {
		return mEndDate == null ? "" : mEndDate;
	}
	public void setEndDate(String mEndDate) {
		this.mEndDate = mEndDate;
	}
	public long getJoinNum() {
		return mJoinNum;
	}
	public void setJoinNum(long mJoinNum) {
		this.mJoinNum = mJoinNum;
	}
	public String getIconUrl() {
		return mIconUrl;
	}
	public void setIconUrl(String mIconUrl) {
		this.mIconUrl = mIconUrl;
	}
	public byte[] getIcon() {
		return mIcon;
	}
	public void setIcon(byte[] mIcon) {
		this.mIcon = mIcon;
	}
	public String getContent() {
		return mContent == null ? "" : mContent;
	}
	public void setContent(String content) {
		this.mContent = content;
	}
	public String getContacts() {
		return mContacts;
	}
	public void setContacts(String contacts) {
		this.mContacts = contacts;
	}
	public String getState() {
		return mState == null ? "" : mState;
	}
	public void setState(String mState) {
		this.mState = mState;
	}
	public long getNeedNum() {
		return mNeedNum;
	}
	public void setNeedNum(long needNum) {
		this.mNeedNum = needNum;
	}
	public String getLaunch() {
		return mLaunch;
	}
	public void setLaunch(String mLaunch) {
		this.mLaunch = mLaunch;
	}
	public List<AccountInfo> getJoinUser() {
		return JoinUser;
	}
	public void setJoinUser(List<AccountInfo> joinUser) {
		JoinUser = joinUser;
	}
	public AccountInfo getLauncherInfo() {
		return launcherInfo;
	}
	public void setLauncherInfo(AccountInfo launcherInfo) {
		this.launcherInfo = launcherInfo;
	}
	
}
