package com.bean;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * ShareBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SHARE", schema = "PINKE")
public class ShareBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 372010683760717490L;
	private int MId;
	private String MTitle;
	private String MCategory;
	private String MPlace;
	private String MPlaceStr;
	private Date MStartDate;
	private String MStartDateStr;
	private Date MEndDate;
	private String MEndDateStr;
	private int MJoinNum;
	private int MNeedNum;
	private String MIconUrl;
	private String MContent;
	private String MContacts;
	private String MState;
	private String MLaunch;
	private List<UserBean> userSet;
	private UserBean launcherInfo;

	// Constructors

	/** default constructor */
	public ShareBean() {
	}

	/** full constructor */
	public ShareBean(String MTitle, String MCategory, String MPlace,
			Date MStartDate, Date MEndDate, int MJoinNum,
			int MNeedNum, String MIconUrl, String MContent,
			String MContacts, String MState) {
		this.MTitle = MTitle;
		this.MCategory = MCategory;
		this.MPlace = MPlace;
		this.MStartDate = MStartDate;
		this.MEndDate = MEndDate;
		this.MJoinNum = MJoinNum;
		this.MNeedNum = MNeedNum;
		this.MIconUrl = MIconUrl;
		this.MContent = MContent;
		this.MContacts = MContacts;
		this.MState = MState;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@SequenceGenerator(name="generator",sequenceName="seq_t_share",allocationSize = 1,initialValue = 1)
	@Column(name = "M_ID", unique = true, nullable = false, precision = 0)
	public int getMId() {
		return this.MId;
	}

	public void setMId(int MId) {
		this.MId = MId;
	}

	@Column(name = "M_TITLE", length = 20)
	public String getMTitle() {
		return this.MTitle;
	}

	public void setMTitle(String MTitle) {
		this.MTitle = MTitle;
	}

	@Column(name = "M_CATEGORY", length = 20)
	public String getMCategory() {
		return this.MCategory;
	}

	public void setMCategory(String MCategory) {
		this.MCategory = MCategory;
	}

	@Column(name = "M_PLACE", length = 20)
	public String getMPlace() {
		return this.MPlace;
	}

	public void setMPlace(String MPlace) {
		this.MPlace = MPlace;
	}
	
	@Column(name = "M_PLACE_STR")
	public String getMPlaceStr() {
		return MPlaceStr;
	}

	public void setMPlaceStr(String mPlaceStr) {
		MPlaceStr = mPlaceStr;
	}

	@Column(name = "M_START_DATE", length = 20)
	public Date getMStartDate() {
		return this.MStartDate;
	}

	public void setMStartDate(Date MStartDate) {
		this.MStartDate = MStartDate;
	}

	@Column(name = "M_END_DATE", length = 20)
	public Date getMEndDate() {
		return this.MEndDate;
	}

	public void setMEndDate(Date MEndDate) {
		this.MEndDate = MEndDate;
	}

	@Column(name = "M_JOIN_NUM", precision = 0)
	public int getMJoinNum() {
		return this.MJoinNum;
	}

	public void setMJoinNum(int MJoinNum) {
		this.MJoinNum = MJoinNum;
	}

	@Column(name = "M_NEED_NUM", precision = 0)
	public int getMNeedNum() {
		return this.MNeedNum;
	}

	public void setMNeedNum(int MNeedNum) {
		this.MNeedNum = MNeedNum;
	}

	@Column(name = "M_ICON_URL", length = 50)
	public String getMIconUrl() {
		return this.MIconUrl;
	}

	public void setMIconUrl(String MIconUrl) {
		this.MIconUrl = MIconUrl;
	}

	@Column(name = "M_CONTENT", length = 500)
	public String getMContent() {
		return this.MContent;
	}

	public void setMContent(String MContent) {
		this.MContent = MContent;
	}

	@Column(name = "M_CONTACTS", length = 50)
	public String getMContacts() {
		return this.MContacts;
	}

	public void setMContacts(String MContacts) {
		this.MContacts = MContacts;
	}

	@Column(name = "M_STATE", length = 10)
	public String getMState() {
		return this.MState;
	}

	public void setMState(String MState) {
		this.MState = MState;
	}

	@Column(name = "M_Launch",length = 20)
	public String getMLaunch() {
		return MLaunch;
	}

	public void setMLaunch(String mLaunch) {
		MLaunch = mLaunch;
	}

	@ManyToMany(
			targetEntity=com.bean.UserBean.class,
			cascade={CascadeType.MERGE,CascadeType.PERSIST}
			)
	@JoinTable(
			name="t_user_share",   
			joinColumns={@JoinColumn(name="m_id")},   
            inverseJoinColumns={@JoinColumn(name="suser")}   
			)
	public List<UserBean> getUserSet() {
		return userSet;
	}

	public void setUserSet(List<UserBean> userSet) {
		this.userSet = userSet;
	}

	@Column(name="M_START_DATE_STR",length = 20)
	public String getMStartDateStr() {
		return MStartDateStr;
	}

	public void setMStartDateStr(String mStartDateStr) {
		MStartDateStr = mStartDateStr;
	}

	@Column(name="M_END_DATE_STR",length = 20)
	public String getMEndDateStr() {
		return MEndDateStr;
	}

	public void setMEndDateStr(String mEndDateStr) {
		MEndDateStr = mEndDateStr;
	}

	@Transient
	public UserBean getLauncherInfo() {
		return launcherInfo;
	}

	public void setLauncherInfo(UserBean launcherInfo) {
		this.launcherInfo = launcherInfo;
	}
	
}