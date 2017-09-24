package com.mxcx.erp.gs.dao.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;

@Entity
@Table(name = "GS_SKU_ADDRECORD")
public class GsSkuAddRecord extends BasePo {
	
	private static final long serialVersionUID = -5513241569550630796L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "GOODS_ID")
	private String goods_id;
	
	@Column(name = "PROPS")
	private String props;
	
	@Column(name = "PROPSNAME")
	private String propsname;
	
	@Column(name = "PRICE")
	private double price;
	
	
	@Column(name = "PV")
	private Integer pv;
	
	@Column(name = "ADD_QUANTITY")
	private Integer addQuantity;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "IS_DEF")
	private Integer is_def;
	
	
	@Transient
	private String propName;
	
	@Column(name = "GOODS_NAME")
	private String goodsName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
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

	public Integer getaddQuantity() {
		return addQuantity;
	}

	public void setQuantity(Integer addQuantity) {
		this.addQuantity = addQuantity;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIs_def() {
		return is_def;
	}

	public void setIs_def(Integer is_def) {
		this.is_def = is_def;
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

	public String getPropsname() {
		return propsname;
	}

	public void setPropsname(String propsname) {
		this.propsname = propsname;
	}
	
}