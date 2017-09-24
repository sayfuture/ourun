package com.mxcx.erp.di.dao.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.erp.me.dao.entity.MeMember;
   /**
    * di_permanent 
    * Mon Jun 19 11:19:37 CST 2017 hmy
    */ 

@Entity
@Table(name = "DI_PERMANENT")
public class DiPermanent extends BasePo {
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "MEID")
	private MeMember meMember;
	
	@ManyToOne
	@JoinColumn(name = "CARD_ID")
	private DiCard diCard;
	
	@Column(name = "IMG_PATH")
	private String img_path;
	
	@Column(name = "COMPANYID")
	private String companyId;
	
	public void setId(String id){
	this.id=id;
	}
	public String getId(){
		return id;
	}
	
	public MeMember getMeMember() {
		return meMember;
	}
	public void setMeMember(MeMember meMember) {
		this.meMember = meMember;
	}
	public DiCard getDiCard() {
		return diCard;
	}
	public void setDiCard(DiCard diCard) {
		this.diCard = diCard;
	}
	public void setImg_path(String img_path){
	this.img_path=img_path;
	}
	public String getImg_path(){
		return img_path;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}

