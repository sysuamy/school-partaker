package com.zx.pinke.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AppPreference;

public class AccountInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3868811973552063898L;
	public static final String ITEM_SESSION_ID = "sessionId";
	public static final String ITEM_LOGIN_TIME = "loginTime";
	public static final String ITEM_ACCOUNT = "account";
	public static final String ITEM_PASSWORD = "password";
	public static final String ITEM_USER_NAME = "userName";
	public static final String ITEM_THUMB_URL = "thumb_url";
	public static final String ITEM_STAFF_TIME = "staffTime";
	public static final String ITEM_STAFF_SCORE = "staffScore";
	public static final String ITEM_LEVEL = "level";
	public static final String ITEM_PHONE = "phone";
	public static final String ITEM_SEX = "sex";
	public static final String ITEM_SCHOOL = "school";
	public static final String ITEM_EMAIL = "email";
	public static final String ITEM_AREA = "area";
	public static final String ITEM_AREASTR = "areaStr";
	
	private static final long EXPIRE_SPAN = 30 * 60 * 1000;//���ð�Сʱ�Ự��ʱ
	
	private String sessionId;
	private long loginTime = 0;
	
	private String account;//�ʺ�
	private String password;//����
	private String userName;//�û���
	private int staffTime = 0;//����ʱ��
	private int staffScore = 0;//��������
	private int level;//�ȼ�
	private String thumbUrl;//ͷ��
	private String phone;//�ֻ���
	private int sex;//�ֻ���
	private String school;//�ֻ���
	private String email;//�ֻ���
	private String area;//�ֻ���
	private String areaStr;//�ֻ���
	
	public static AccountInfo getAccountInfo(){
		AccountInfo info = new AccountInfo();
		String accountInfo = AppPreference.getString(AppPreference.IV.PREF_KEY_ACCOUNT, "");
		if(TextUtils.isEmpty(accountInfo)){
			return info;
		}
		
		try {
			JSONObject jsonInfo = new JSONObject(accountInfo);
			info.setSessionId(jsonInfo.getString(ITEM_SESSION_ID));
			info.setAccount(jsonInfo.getString(ITEM_ACCOUNT));
			info.setLoginTime(jsonInfo.getLong(ITEM_LOGIN_TIME));
			info.setUserName(jsonInfo.getString(ITEM_USER_NAME));
			info.setPassword(jsonInfo.getString(ITEM_PASSWORD));
			info.setThumbUrl(jsonInfo.getString(ITEM_THUMB_URL));
			info.setStaffTime(jsonInfo.getInt(ITEM_STAFF_TIME));
			info.setStaffScore(jsonInfo.getInt(ITEM_STAFF_SCORE));
			info.setLevel(jsonInfo.getInt(ITEM_LEVEL));
			info.setPhone(jsonInfo.getString(ITEM_PHONE));
			info.setSex(jsonInfo.getInt(ITEM_SEX));
			info.setSchool(jsonInfo.getString(ITEM_SCHOOL));
			info.setEmail(jsonInfo.getString(ITEM_EMAIL));
			info.setArea(jsonInfo.getString(ITEM_AREA));
			info.setAreaStr(jsonInfo.getString(ITEM_AREASTR));
			
		} catch (JSONException e) {
			e.printStackTrace();
			return new AccountInfo();
		}
		
		return info;
	}
	
	public static void saveAccountInfo(String sessionId,String account,String password,JSONObject jsonInfo) throws Exception{
		
		String thumbUrl = jsonInfo.getString("simgPath") == null ? null : (AppConfig.NetWork.IV.PRE_SERVER_ADDRESS + jsonInfo.getString("simgPath"));//ͷ��http��ַ
		String level = jsonInfo.getString("ivTitle");//�ȼ�
		String userName = jsonInfo.getString("sname");//�û���
		int staffTime = jsonInfo.getInt("ivTimes");//����ʱ��
		int staffScore = jsonInfo.getInt("ivScore");//��������
		String phone = jsonInfo.getString("sphone");//�ֻ���
		
		int sex = jsonInfo.getInt("ssex");//����ʱ��
		String school = jsonInfo.getString("sschool");//�ֻ���
		String email = jsonInfo.getString("semail");//�ֻ���
		String area = jsonInfo.getString("sarea");//�ֻ���
		String areaStr = jsonInfo.getString("saddress");//�ֻ���
		
		JSONObject info = new JSONObject();
		
		info.put(ITEM_SESSION_ID, sessionId);
		info.put(ITEM_ACCOUNT, account);
		info.put(ITEM_PASSWORD, password);
		info.put(ITEM_THUMB_URL, thumbUrl);
		info.put(ITEM_LEVEL, level);
		info.put(ITEM_USER_NAME, userName);
		info.put(ITEM_LOGIN_TIME, System.currentTimeMillis());
		info.put(ITEM_STAFF_TIME, staffTime);
		info.put(ITEM_STAFF_SCORE, staffScore);
		info.put(ITEM_PHONE, phone);
		info.put(ITEM_SEX, sex);
		info.put(ITEM_SCHOOL, school);
		info.put(ITEM_EMAIL, email);
		info.put(ITEM_AREA, area);
		info.put(ITEM_AREASTR, areaStr);
		
		System.out.println("-->"+info.toString());
		AppPreference.putString(AppPreference.IV.PREF_KEY_ACCOUNT, info.toString());
	}
	
	public static void clearAccontInfo(){
		AppPreference.putString(AppPreference.IV.PREF_KEY_ACCOUNT, "");
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean expired(){
		return (loginTime + EXPIRE_SPAN)<System.currentTimeMillis();
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStaffTime() {
		return staffTime;
	}
	public void setStaffTime(int staffTime) {
		this.staffTime = staffTime;
	}
	public int getStaffScore() {
		return staffScore;
	}
	public void setStaffScore(int staffScore) {
		this.staffScore = staffScore;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaStr() {
		return areaStr;
	}

	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}
	
}
