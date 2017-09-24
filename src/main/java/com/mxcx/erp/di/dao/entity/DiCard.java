package com.mxcx.erp.di.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.erp.co.dao.entity.CoContent;
   /**
    * di_card 
    * Thu Dec 29 20:51:23 CST 2016 hmy
    */ 

@Entity
@Table(name = "DI_CARD")
public class DiCard extends BasePo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CARD_NAME")
	private String card_name;
	@Column(name = "VAILDTIME")
	private Date vaildtime;
	@Column(name = "USE_EXPLAIN")
	private String use_explain;
	@Column(name = "CARD_WORTH")
	private Integer card_worth;
	@Column(name = "USE_NUM")
	private Integer use_num;
	@Column(name = "SHARE_NUM")
	private Integer share_num;
	@ManyToOne
	@JoinColumn(name = "CONTENTID")
	private CoContent coContent;
	@Column(name = "COMPANYID")
	private String companyId;
	@Column(name = "TOTAL_NUM")
	private Integer total_num;
	@Column(name = "USED_NUM")
	private Integer used_num;
	public void setId(Integer id){
	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setCard_name(String card_name){
	this.card_name=card_name;
	}
	public String getCard_name(){
		return card_name;
	}
	public void setVaildtime(Date vaildtime){
		this.vaildtime=vaildtime;
	}
	public Date getVaildtime(){
		return vaildtime;
	}
	
	public String getUse_explain() {
		return use_explain;
	}
	public void setUse_explain(String use_explain) {
		this.use_explain = use_explain;
	}
	public void setUse_num(Integer use_num){
	this.use_num=use_num;
	}
	public Integer getUse_num(){
		return use_num;
	}
	public void setShare_num(Integer share_num){
	this.share_num=share_num;
	}
	public Integer getShare_num(){
		return share_num;
	}
	public void setCompanyId(String companyId){
	this.companyId=companyId;
	}
	public String getCompanyId(){
		return companyId;
	}
	public CoContent getCoContent() {
		return coContent;
	}
	public void setCoContent(CoContent coContent) {
		this.coContent = coContent;
	}
	public Integer getCard_worth() {
		return card_worth;
	}
	public void setCard_worth(Integer card_worth) {
		this.card_worth = card_worth;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public Integer getUsed_num() {
		return used_num;
	}
	public void setUsed_num(Integer used_num) {
		this.used_num = used_num;
	}
	
}

