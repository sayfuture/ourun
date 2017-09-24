package com.mxcx.erp.gs.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;

@Entity
@Table(name = "GS_SKU_PROP")
public class GsKuProp extends BasePo {
	
	private static final long serialVersionUID = -5513241569550630796L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "GID")
	private String gid;
	
	@Column(name = "PROPID")
	private String propid;
	
	@Transient
	private String propName;
	
	@Transient
	private String goodsName;
	
	@Transient
	private List<GsPropValue> gsPropValueList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getPropid() {
		return propid;
	}

	public void setPropid(String propid) {
		this.propid = propid;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public List<GsPropValue> getGsPropValueList() {
		return gsPropValueList;
	}

	public void setGsPropValueList(List<GsPropValue> gsPropValueList) {
		this.gsPropValueList = gsPropValueList;
	}
	
}