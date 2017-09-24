package com.mxcx.erp.co.dao.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mxcx.ec.base.commons.dao.entity.TransparentPo;

   /**
    * co_template 
    * Fri Feb 17 09:56:39 CST 2017 hmy
    */ 

@Entity
@Table(name = "CO_TEMPLATE")
public class CoTemplate implements TransparentPo {
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "CONTENT")
	private String content;
	@Column(name = "COMPANYID")
	private String companyId;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	public void setId(String id){
	this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setTitle(String title){
	this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setContent(String content){
	this.content=content;
	}
	public String getContent(){
		return content;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}

