package com.mxcx.erp.di.dao.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mxcx.ec.base.commons.dao.entity.TransparentPo;
import com.mxcx.erp.we.dao.entity.WeCustomer;
   /**
    * di_process 
    * Thu Dec 29 20:53:47 CST 2016 hmy
    */ 

@Entity
@Table(name = "DI_PROCESS_RECORD")
public class DiProcessRecord implements TransparentPo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4108682298568583178L;
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
//	@Column(name = "PROCESS_ID", nullable = false)
//	private String process_id;
	@ManyToOne
	@JoinColumn(name = "WECHAT_CUSTOMERID")
	private WeCustomer weCustomer;
	@ManyToOne
	@JoinColumn(name = "CARD_ID")
	private DiCard diCard;
	@Column(name = "CARD_NUM")
	private Integer card_num;
	@Column(name = "SHARE_CARD_NUM")
	private Integer share_card_num;
	@Column(name = "END_TIME")
	private Date end_time;
	@Column(name = "GETTIME")
	private Date gettime;
	@Column(name = "COMPANYID")
	private String companyId;
	@Column(name = "STATUS")
	private Integer status;
	public void setId(String id){
	this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setEnd_time(Date end_time){
	this.end_time=end_time;
	}
	public Date getEnd_time(){
		return end_time;
	}
	public void setGettime(Date gettime){
	this.gettime=gettime;
	}
	public Date getGettime(){
		return gettime;
	}
	public void setCompanyId(String companyId){
	this.companyId=companyId;
	}
	public String getCompanyId(){
		return companyId;
	}
	public void setStatus(Integer status){
	this.status=status;
	}
	public Integer getStatus(){
		return status;
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
	public Integer getCard_num() {
		return card_num;
	}
	public void setCard_num(Integer card_num) {
		this.card_num = card_num;
	}
	public Integer getShare_card_num() {
		return share_card_num;
	}
	public void setShare_card_num(Integer share_card_num) {
		this.share_card_num = share_card_num;
	}
}

