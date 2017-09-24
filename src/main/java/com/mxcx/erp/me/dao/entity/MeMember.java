package com.mxcx.erp.me.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.erp.di.dao.entity.DiCard;

@Entity
@Table(name = "ME_MEMBER")
public class MeMember extends BasePo {

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	@Column(name = "NICK_NAME", length = 20)
	private String nickName;
	@Column(name = "open_id", length = 100)
	private String openId;
	@Column(name = "LOGIN_NAME", length = 20)
	private String loginName;
	@Column(name = "REAL_NAME", length = 20)
	private String realName;
	@Column(name = "PASSWORD", length = 32)
	private String password;
	@Column(name = "SEX")
	private Integer sex;
	@Column(name = "EMAIL", length = 100)
	private String email;
	@Column(name = "CELLPHONE")
	private String cellphone;
	@Column(name = "ACTIVITY")
	private Integer activity;
	@Column(name = "AVAILABILITY")
	private Integer availability;
	@Column(name = "MARITAL_STATE")
	private Integer maritalState;
	@Column(name = "INTEREST", length = 30)
	private String interest;
	@Column(name = "IDENTITY")
	private Integer identity;
	@Column(name = "BIRTHDAY")
	private Date birthday;
	@Column(name = "SCORE")
	private Integer score;
	@ManyToOne
	@JoinColumn(name="LEVEL_ID")
	private MeLevel level;
	
	@Column(name = "REGISTER_METHOD")
	private Integer registerMethod;//注册方式1.手机注册0.网站注册
	@Column(name = "VALIDATION_METHOD")
	private Integer validationMethod;//验证方式
	@Column(name = "HEAD_SHOW_PATH")
	private String headShowPath;
	@Column(name = "EMAIL_BINDING")
	private Integer emailBinding;
	@Column(name = "ADV_PASSWORD", length = 32)
	private String advPassword;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "REG_TIME")
	private Date regTime;
	
	@Column(name = "WECHAT", length = 20)
	private String wechat;
	@Column(name = "USER_ID")
	private String user_id;
	
	
	/**
	 * 店面员工设置优惠券
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CARDID")
	private DiCard diCard;

	@Column(name = "GET_LIMIT")
	private Integer get_limit; 
	
	@Column(name = "USED_NUM")
	private Integer used_num; 
	
	/**
	 * 店面员工设置优惠券1
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CARDID1")
	private DiCard diCard1;

	@Column(name = "GET_LIMIT1")
	private Integer get_limit1; 
	
	@Column(name = "USED_NUM1")
	private Integer used_num1; 
	
	@Column(name = "COMPANYID")
	private String companyId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public Integer getMaritalState() {
		return maritalState;
	}

	public void setMaritalState(Integer maritalState) {
		this.maritalState = maritalState;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	

	public MeLevel getLevel() {
		return level;
	}

	public void setLevel(MeLevel level) {
		this.level = level;
	}

	public Integer getRegisterMethod() {
		return registerMethod;
	}

	public void setRegisterMethod(Integer registerMethod) {
		this.registerMethod = registerMethod;
	}

	public Integer getValidationMethod() {
		return validationMethod;
	}

	public void setValidationMethod(Integer validationMethod) {
		this.validationMethod = validationMethod;
	}

	public String getHeadShowPath() {
		return headShowPath;
	}

	public void setHeadShowPath(String headShowPath) {
		this.headShowPath = headShowPath;
	}


	public Integer getEmailBinding() {
		return emailBinding;
	}

	public void setEmailBinding(Integer emailBinding) {
		this.emailBinding = emailBinding;
	}


	public String getAdvPassword() {
		return advPassword;
	}

	public void setAdvPassword(String advPassword) {
		this.advPassword = advPassword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}


	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public DiCard getDiCard() {
		return diCard;
	}

	public void setDiCard(DiCard diCard) {
		this.diCard = diCard;
	}

	public Integer getGet_limit() {
		return get_limit;
	}

	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
	}

	public Integer getUsed_num() {
		return used_num;
	}

	public void setUsed_num(Integer used_num) {
		this.used_num = used_num;
	}

	public DiCard getDiCard1() {
		return diCard1;
	}

	public void setDiCard1(DiCard diCard1) {
		this.diCard1 = diCard1;
	}

	public Integer getGet_limit1() {
		return get_limit1;
	}

	public void setGet_limit1(Integer get_limit1) {
		this.get_limit1 = get_limit1;
	}

	public Integer getUsed_num1() {
		return used_num1;
	}

	public void setUsed_num1(Integer used_num1) {
		this.used_num1 = used_num1;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
