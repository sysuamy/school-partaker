package com.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IVCenterBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_IV_CENTER", schema = "PINKE")
public class IVCenterBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ivKey;
	private String content;
	private String name;
	private String ivDate;
	private UserBean user;
	private String icUrl;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SUSER")
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	// Constructors

	/** default constructor */
	public IVCenterBean() {
	}

	/** full constructor */
	public IVCenterBean(String content, String ivDate) {
		this.content = content;
		this.ivDate = ivDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@SequenceGenerator(name="generator",sequenceName="seq_t_iv_center",allocationSize = 1,initialValue = 1)
	@Column(name = "IV_KEY", unique = true, nullable = false, precision = 0)
	public int getIvKey() {
		return this.ivKey;
	}

	public void setIvKey(int ivKey) {
		this.ivKey = ivKey;
	}

	@Column(name = "CONTENT", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "IV_DATE", length = 20)
	public String getIvDate() {
		return this.ivDate;
	}

	public void setIvDate(String ivDate) {
		this.ivDate = ivDate;
	}

	@Column(name = "IC_URL", length = 50)
	public String getIcUrl() {
		return icUrl;
	}

	public void setIcUrl(String icUrl) {
		this.icUrl = icUrl;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}