package com.mxcx.erp.gs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 套餐图片
 * 
 * @author DANDAN 2014/09/19
 * 
 */
@Entity
@Table(name = "GS_GOODS_PICTURE")
public class GsGoodsPicture extends BasePo {
	private static final long serialVersionUID = 362932517343552933L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "NAME")
	private String name; // 图片说明

	@Column(name = "URL")
	private String url; // 图片路径


	@Column(name = "GS_GOODS_ID")
	private  String gsGoodsId;
	
	
	@Column(name = "DEF")
	private Integer def;
	
	
	@Transient
	private String filePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getFilePath() {
		if(url != null){
			filePath = url;
		}
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getGsGoodsId() {
		return gsGoodsId;
	}

	public void setGsGoodsId(String gsGoodsId) {
		this.gsGoodsId = gsGoodsId;
	}

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}


	
}