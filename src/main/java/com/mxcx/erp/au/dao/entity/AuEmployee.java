package com.mxcx.erp.au.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 人员实体类
 * 
 * @author  2014/06/23
 * 
 */
@Entity
@Table(name = "AU_EMPLOYEE")
public class AuEmployee extends BasePo {
	private static final long serialVersionUID = 1L;
	/*
	 * 删除
	 */
	public static final int INDEX_STATE_DELETE = 0; // 删除
	/*
	 * 正常
	 */
	public static final int INDEX_STATE_NORMAL = 1; // 正常
	/*
	 * 男
	 */
	public static final int INDEX_SEX_BOY = 1; // 男
	/*
	 * 女
	 */
	public static final int INDEX_SEX_GIRL = 0; // 女

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "REALNAME", nullable = false)
	private String realName; // 真实姓名

	@Column(name = "LOGINNAME", nullable = false)
	private String loginName; // 登录名

	@Column(name = "PASSWORD", nullable = false)
	private String password; // 密码

	@Column(name = "SEX", nullable = false)
	private Integer sex; // 性别

	@Column(name = "AGE")
	private Integer age; // 年龄

	@Column(name = "EMAIL")
	private String email; // 邮箱

	@Column(name = "TEL")
	private String tel; // 手机电话
	
	@Column(name = "TEL2")
	private String tel2; // 座机

	@Column(name = "AVATAR_URL")
	private String avatar_url; 
	
	@Column(name = "LOGINCOUNT")
	private Integer loginCount; 
	
	@Column(name = "LASTDATE")
	private Date lastdate; // 创建时间
	
	@ManyToOne
	@JoinColumn(name = "AU_DEPT")
	private AuDept auDept; 
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="auEmployee",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	@JsonIgnore
	private Set<AuEmployeeDept> auEmployeeDeptList; 

	@OneToMany(mappedBy="auEmployee",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<AuEmployeeAuthority> auEmployeeAuthorityList; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="COMPANYID")
	private AuDept company;
	
	@Transient
	private String companyId;
	
	@Transient
	private Set<AuEmployeeDept> auEmployeeDeptList1;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "AU_EMPLOYEE_POSITION", joinColumns = @JoinColumn(name = "au_employee"), inverseJoinColumns = @JoinColumn(name = "au_position"))
	private Set<AuPosition> positionList;
	
	@Column(name = "APPID")
	private String appid; 
	
	@Column(name = "APPSECRET")
	private String appsecret; 
	
	@Column(name = "WXNAME")
	private String wxname; 
	
	@Column(name = "SHOP_FILE_NAME")
	private String shop_file_name1; 
	
	@Column(name = "ADDRESS")
	private String address; 
	
	public Set<AuEmployeeDept> getAuEmployeeDeptList1() {
		return auEmployeeDeptList1;
	}

	public void setAuEmployeeDeptList1(Set<AuEmployeeDept> auEmployeeDeptList1) {
		this.auEmployeeDeptList1 = auEmployeeDeptList1;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public AuDept getAuDept() {
		return auDept;
	}

	public void setAuDept(AuDept auDept) {
		this.auDept = auDept;
	}

	public Set<AuEmployeeDept> getAuEmployeeDeptList() {
		return auEmployeeDeptList;
	}

	public void setAuEmployeeDeptList(Set<AuEmployeeDept> auEmployeeDeptList) {
		this.auEmployeeDeptList = auEmployeeDeptList;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<AuEmployeeAuthority> getAuEmployeeAuthorityList() {
		return auEmployeeAuthorityList;
	}

	public void setAuEmployeeAuthorityList(List<AuEmployeeAuthority> auEmployeeAuthorityList) {
		this.auEmployeeAuthorityList = auEmployeeAuthorityList;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}


	public AuDept getCompany() {
		return company;
	}

	public void setCompany(AuDept company) {
		this.company = company;
	}

	public Set<AuPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(Set<AuPosition> positionList) {
		this.positionList = positionList;
	}


	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	public String getShop_file_name1() {
		return shop_file_name1;
	}

	public void setShop_file_name1(String shop_file_name1) {
		this.shop_file_name1 = shop_file_name1;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}