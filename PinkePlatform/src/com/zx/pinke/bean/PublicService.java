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
	private String mTitle;//����
	private String mCategory;//����
	private String mPlace;//��ص�
	private String mStartDate;//���ʼʱ��
	private String mEndDate;//�����ʱ��
	private long mJoinNum;//�ѱ�������
	private long mNeedNum;//��������
	private String mIconUrl;//ͼƬURL
	private byte[] mIcon;//ͼƬ
	private String mContent;//�����
	private String mContacts;//��ϵ��
	private String mState;//״̬
	private String mLaunch;//״̬
	
	private AccountInfo launcherInfo;//״̬
	
	private List<AccountInfo> JoinUser;//�μӴ˻���û�
	
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
