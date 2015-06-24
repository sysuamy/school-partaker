package com.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * UserBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_USER", schema = "PINKE")
public class UserBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897688197658039128L;
	private String suser;
	private String sname;
	private String spwd;
	private String sphone;
	private String saddress;
	private String simgPath;
	private int ivTimes;
	private int ivTitle;
	private int ivScore;
	private String sarea;
	private int ssex;
	private String sschool;
	private String semail;
	private String scard;
	private Set<ShareBean> shareSet;
	// Constructors

	/** default constructor */
	public UserBean() {
	}

	/** minimal constructor */
	public UserBean(String suser, int ivTimes, int ivTitle, int ivScore) {
		this.suser = suser;
		this.ivTimes = ivTimes;
		this.ivTitle = ivTitle;
		this.ivScore = ivScore;
	}


	// Property accessors
	@Id
	@Column(name = "SUSER", unique = true, nullable = false, length = 50)
	public String getSuser() {
		return this.suser;
	}

	public void setSuser(String suser) {
		this.suser = suser;
	}

	@Column(name = "SNAME", length = 50)
	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@Column(name = "SPWD", length = 50)
	public String getSpwd() {
		return this.spwd;
	}

	public void setSpwd(String spwd) {
		this.spwd = spwd;
	}

	@Column(name = "SPHONE", length = 50)
	public String getSphone() {
		return this.sphone;
	}

	public void setSphone(String sphone) {
		this.sphone = sphone;
	}

	@Column(name = "SADDRESS", length = 50)
	public String getSaddress() {
		return this.saddress;
	}

	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}

	@Column(name = "SIMG_PATH", length = 50)
	public String getSimgPath() {
		return this.simgPath;
	}

	public void setSimgPath(String simgPath) {
		this.simgPath = simgPath;
	}

	@Column(name = "IV_TIMES", nullable = false, precision = 0)
	public int getIvTimes() {
		return this.ivTimes;
	}

	public void setIvTimes(int ivTimes) {
		this.ivTimes = ivTimes;
	}

	@Column(name = "IV_TITLE", nullable = false, precision = 0)
	public int getIvTitle() {
		return this.ivTitle;
	}

	public void setIvTitle(int ivTitle) {
		this.ivTitle = ivTitle;
	}

	@Column(name = "IV_SCORE", nullable = false, precision = 0)
	public int getIvScore() {
		return this.ivScore;
	}

	public void setIvScore(int ivScore) {
		this.ivScore = ivScore;
	}

	@Column(name = "SAREA", length = 20)
	public String getSarea() {
		return this.sarea;
	}

	public void setSarea(String sarea) {
		this.sarea = sarea;
	}

	@Column(name = "SCARD", length = 20)
	public String getScard() {
		return this.scard;
	}

	public void setScard(String scard) {
		this.scard = scard;
	}

	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},   
	        targetEntity=com.bean.ShareBean.class,
	        mappedBy="userSet")
	public Set<ShareBean> getShareSet() {
		return shareSet;
	}

	public void setShareSet(Set<ShareBean> shareSet) {
		this.shareSet = shareSet;
	}

	@Column(name = "SSEX", nullable = false, precision = 0)
	public int getSsex() {
		return ssex;
	}

	public void setSsex(int ssex) {
		this.ssex = ssex;
	}

	@Column(name = "SSCHOOL", length = 50)
	public String getSschool() {
		return sschool;
	}

	public void setSschool(String sschool) {
		this.sschool = sschool;
	}

	@Column(name = "SEMAIL", length = 100)
	public String getSemail() {
		return semail;
	}

	public void setSemail(String semail) {
		this.semail = semail;
	}
}