package com.mxcx.erp.di.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.we.dao.entity.WeCustomer;
   /**
    * di_send_recode 
    * Mon Jan 16 14:06:33 CST 2017 hmy
    */ 

@Entity
@Table(name = "DI_SEND_RECODE")
public class DiSendRecode extends BasePo {
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	@ManyToOne
	@JoinColumn(name = "OPENID")
	private WeCustomer weCustomer;
	@ManyToOne
	@JoinColumn(name = "CARDID")
	private DiCard diCard;
	@ManyToOne
	@JoinColumn(name = "MEID")
	private MeMember meMember;
	@Column(name = "COMPANYID")
	private String companyId;
	@Column(name = "SHARENUM")
	private Integer sharenum;		//客户分享出去的人
	@Column(name = "NEW_WEUSER")
	private Integer new_weuser;
	@Transient
	private String openid;
	@Transient
	private Integer cardid;
	public void setId(String id){
	this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setCompanyId(String companyId){
	this.companyId=companyId;
	}
	public String getCompanyId(){
		return companyId;
	}
	public void setSharenum(Integer sharenum){
	this.sharenum=sharenum;
	}
	public Integer getSharenum(){
		return sharenum;
	}
	public void setNew_weuser(Integer new_weuser){
	this.new_weuser=new_weuser;
	}
	public Integer getNew_weuser(){
		return new_weuser;
	}
	public WeCustomer getWeCustomer() {
		return weCustomer;
	}
	public void setWeCustomer(WeCustomer weCustomer) {
		this.weCustomer = weCustomer;
	}
	public DiCard getDiCard() {
		return diCard;
	}
	public void setDiCard(DiCard diCard) {
		this.diCard = diCard;
	}
	public MeMember getMeMember() {
		return meMember;
	}
	public void setMeMember(MeMember meMember) {
		this.meMember = meMember;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getCardid() {
		return cardid;
	}
	public void setCardid(Integer cardid) {
		this.cardid = cardid;
	}
	
}

