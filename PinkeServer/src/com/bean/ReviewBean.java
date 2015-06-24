package com.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ReviewBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_REVIEW", schema = "PINKE")
public class ReviewBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6744106876519182642L;
	private int reKey;
	private int reFkey;
	private String reDate;
	private String reSender;
	private String reAccepter;
	private String reContent;

	// Constructors

	/** default constructor */
	public ReviewBean() {
	}

	/** full constructor */
	public ReviewBean(int reFkey, String reDate, String reSender,
			String reAccepter, String reContent) {
		this.reFkey = reFkey;
		this.reDate = reDate;
		this.reSender = reSender;
		this.reAccepter = reAccepter;
		this.reContent = reContent;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "mySeq")
	@SequenceGenerator(name="mySeq",sequenceName="seq_t_review",allocationSize = 1,initialValue = 1)
	@Column(name = "RE_KEY", unique = true, nullable = false, precision = 0)
	public int getReKey() {
		return this.reKey;
	}

	public void setReKey(int reKey) {
		this.reKey = reKey;
	}

	@Column(name = "RE_FKEY", precision = 0)
	public int getReFkey() {
		return this.reFkey;
	}

	public void setReFkey(int reFkey) {
		this.reFkey = reFkey;
	}

	@Column(name = "RE_DATE", length = 20)
	public String getReDate() {
		return this.reDate;
	}

	public void setReDate(String reDate) {
		this.reDate = reDate;
	}

	@Column(name = "RE_SENDER", length = 20)
	public String getReSender() {
		return this.reSender;
	}

	public void setReSender(String reSender) {
		this.reSender = reSender;
	}

	@Column(name = "RE_ACCEPTER", length = 20)
	public String getReAccepter() {
		return this.reAccepter;
	}

	public void setReAccepter(String reAccepter) {
		this.reAccepter = reAccepter;
	}

	@Column(name = "RE_CONTENT", length = 200)
	public String getReContent() {
		return this.reContent;
	}

	public void setReContent(String reContent) {
		this.reContent = reContent;
	}

}