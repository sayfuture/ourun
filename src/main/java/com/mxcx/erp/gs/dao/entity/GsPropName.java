package com.mxcx.erp.gs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 商品扩展属性实体类
 */
@Entity
@Table(name = "GS_PROP_NAME")
public class GsPropName extends BasePo {
	
	private static final long serialVersionUID = -5513241569550630796L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "CAT_ID")
	private String catId;
	
	@Column(name = "PROP_NAME")
	private String propName;
	
	@Column(name = "IS_COLOR_PROP", columnDefinition = "NUMBER default 0")
	private Integer isColorProp;
	
	@Column(name = "IS_SIZE_PROP", columnDefinition = "NUMBER default 0")
	private Integer isSizeProp;
	
	@Column(name = "IS_SALE_PROP", columnDefinition = "NUMBER default 0")
	private Integer isSaleProp;
	
	@Column(name = "IS_KEY_PROP", columnDefinition = "NUMBER default 0")
	private Integer isKeyProp;
	
	@Column(name = "IS_FILTER_PROP", columnDefinition = "NUMBER default 0")
	private Integer isFilterProp;
	
	@Column(name = "IS_CUST_PROP", columnDefinition = "NUMBER default 0")
	private Integer isCustProp;
	
	@Column(name = "IS_MUST", columnDefinition = "NUMBER default 0")
	private Integer isMust;
	
	@Column(name = "IS_MULTI", columnDefinition = "NUMBER default 0")
	private Integer isMulti;
	
	@Column(name = "SEQ_NO", columnDefinition = "NUMBER default 0")
	private Integer seqNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public Integer getIsColorProp() {
		return isColorProp;
	}

	public void setIsColorProp(Integer isColorProp) {
		this.isColorProp = isColorProp;
	}

	public Integer getIsSizeProp() {
		return isSizeProp;
	}

	public void setIsSizeProp(Integer isSizeProp) {
		this.isSizeProp = isSizeProp;
	}

	public Integer getIsSaleProp() {
		return isSaleProp;
	}

	public void setIsSaleProp(Integer isSaleProp) {
		this.isSaleProp = isSaleProp;
	}

	public Integer getIsKeyProp() {
		return isKeyProp;
	}

	public void setIsKeyProp(Integer isKeyProp) {
		this.isKeyProp = isKeyProp;
	}

	public Integer getIsFilterProp() {
		return isFilterProp;
	}

	public void setIsFilterProp(Integer isFilterProp) {
		this.isFilterProp = isFilterProp;
	}

	public Integer getIsCustProp() {
		return isCustProp;
	}

	public void setIsCustProp(Integer isCustProp) {
		this.isCustProp = isCustProp;
	}

	public Integer getIsMust() {
		return isMust;
	}

	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}

	public Integer getIsMulti() {
		return isMulti;
	}

	public void setIsMulti(Integer isMulti) {
		this.isMulti = isMulti;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

}