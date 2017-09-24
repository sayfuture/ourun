package com.mxcx.erp.gs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 商品扩展属性值实体类
 */
@Entity
@Table(name = "GS_PROP_VALUE")
public class GsPropValue extends BasePo {

	private static final long serialVersionUID = -7227860444043514786L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "PROP_ID")
	private String propId;

	@Column(name = "PROP_VALUE")
	private String propValue;

	@Column(name = "SEQ_NO", columnDefinition = "NUMBER default 0")
	private Integer seqNo;
	
	
	@Transient
	private String catId;
	
	@Transient
	private String propName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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

}
