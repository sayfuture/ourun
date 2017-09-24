package com.mxcx.erp.gs.dao.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 商品分类实体类
 * 
 * 
 */
@Entity
@Table(name = "GS_GOODS")
public class GsGoods extends BasePo{
	private static final long serialVersionUID = 7027656289068189944L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "PRODUCT_CODE", nullable = false)
	private String productCode;
	
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CAT_ID")
	private String catId;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "PV")
	private Integer pv;
	
	@Column(name = "PICID")
	private String picId;
	
	@Transient
	private String picUrl;
	
	@Column(name = "ONLINE_DATE")
	private Date onlineDate;
	
	@Column(name = "OFFLINE_DATE")
	private Date offlineDate;
	
	@Column(name = "GOODS_DESC")
	private String goodsDesc;
	
	@Column(name = "IS_TOP")
	private Integer isTop;
	
	@Column(name = "TOP_DATE")
	private Date topDate;
	
	@Column(name = "TOP_USER")
	private String topUser;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "FAVOUR_NUM")
	private Integer favourNum;
	
	@Column(name = "HITS_NUM")
	private Integer hitsNum;
	
	@Column(name = "MAXBUY")
	private Integer maxbuy;
	
	@Column(name = "BUYCOUNT")
	private Integer buycount;
	
	@Column(name = "RECOMMEND")
	private String recommend;

	@Transient
	private Integer quantity;
	
	@Transient
	private String catName;
	@Transient
	private String  gsSkuDefId;
	


	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopDate() {
		return topDate;
	}

	public void setTopDate(Date topDate) {
		this.topDate = topDate;
	}

	public String getTopUser() {
		return topUser;
	}

	public void setTopUser(String topUser) {
		this.topUser = topUser;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getFavourNum() {
		return favourNum;
	}

	public void setFavourNum(Integer favourNum) {
		this.favourNum = favourNum;
	}

	public Integer getHitsNum() {
		return hitsNum;
	}

	public void setHitsNum(Integer hitsNum) {
		this.hitsNum = hitsNum;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getGsSkuDefId() {
		return gsSkuDefId;
	}

	public void setGsSkuDefId(String gsSkuDefId) {
		this.gsSkuDefId = gsSkuDefId;
	}

	public Integer getMaxbuy() {
		return maxbuy;
	}

	public void setMaxbuy(Integer maxbuy) {
		this.maxbuy = maxbuy;
	}

	public Integer getBuycount() {
		return buycount;
	}

	public void setBuycount(Integer buycount) {
		this.buycount = buycount;
	}

}
