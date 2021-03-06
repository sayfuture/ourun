package com.mxcx.erp.we.dao.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mxcx.erp.base.commons.controller.BasePo;
   /**
    * we_customer 
    * Thu Dec 29 20:55:34 CST 2016 hmy
    */ 

@Entity
@Table(name = "WE_CUSTOMER")
public class WeCustomer extends BasePo {
	@Id
	@Column(name = "OPENID", nullable = false)
	private String openId;
	@Column(name = "WECHAT_NAME")
	private String wechat_name;
	@Column(name = "IS_FOLLOW")
	private Integer is_follow;
	@Column(name = "PHONE")
	private String phone;
   @Column(name = "CITY")
   private String city;
   @Column(name = "PROVINCE")
   private String province;
   @Column(name = "ADDRESS")
   private String address;
	@Column(name = "CUSTOMER_NAME")
	private String customer_name;
	@Column(name = "COMPANYIDS")
	private String companyIds;
	@Column(name = "CAR_TYPE")
	private String car_type;
	@Column(name = "KILOMETERS")
	private Integer kilometers;
	@Column(name = "NEXT_MAINTAIN_TIME")
	private Date next_maintain_time;
	@Column(name = "NEXT_MAINTAIN_CONTENT")
	private String next_maintain_content;
    @Column(name = "AREA")
    private String area;
	public void setWechat_name(String wechat_name){
	this.wechat_name=wechat_name;
	}
	public String getWechat_name(){
		return wechat_name;
	}
	public void setIs_follow(Integer is_follow){
	this.is_follow=is_follow;
	}
	public Integer getIs_follow(){
		return is_follow;
	}
	public void setPhone(String phone){
	this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}
	public void setCustomer_name(String customer_name){
	this.customer_name=customer_name;
	}
	public String getCustomer_name(){
		return customer_name;
	}
	public void setCompanyIds(String companyIds){
	this.companyIds=companyIds;
	}
	public String getCompanyIds(){
		return companyIds;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public Integer getKilometers() {
		return kilometers;
	}
	public void setKilometers(Integer kilometers) {
		this.kilometers = kilometers;
	}
	public Date getNext_maintain_time() {
		return next_maintain_time;
	}
	public void setNext_maintain_time(Date next_maintain_time) {
		this.next_maintain_time = next_maintain_time;
	}
	public String getNext_maintain_content() {
		return next_maintain_content;
	}
	public void setNext_maintain_content(String next_maintain_content) {
		this.next_maintain_content = next_maintain_content;
	}

	   public String getCity() {
		   return city;
	   }

	   public void setCity(String city) {
		   this.city = city;
	   }

	   public String getProvince() {
		   return province;
	   }

	   public void setProvince(String province) {
		   this.province = province;
	   }

	   public String getAddress() {
		   return address;
	   }

	   public void setAddress(String address) {
		   this.address = address;
	   }

	   public String getArea() {
		   return area;
	   }

	   public void setArea(String area) {
		   this.area = area;
	   }
   }

