package com.mxcx.erp.co.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.solr.client.solrj.request.CollectionAdminRequest.Create;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.ec.base.commons.util.DateUtil;

/**
 * 内容分类管理实体类
 * 
 * @author 王森20140910
 */
@Entity
@Table(name = "CO_TYPE")
public class CoType extends BasePo {
	private static final long serialVersionUID = -5699996544479656077L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id; // 主键Id

	@Column(name = "NAME")
	private String name; // 类型名

	@Column(name = "ENG_NAME")
	private String engName; // 英文名
	
	@Column(name = "LEVEL1")
	private Integer level; // 英文名

	@ManyToOne
	@JoinColumn(name = "PARID")
	private CoType superCoType; // 上级内容类型

	@OneToMany(mappedBy = "superCoType")
	@JsonIgnore
	private List<CoType> coTypes;

	@Transient
	private String superCoTypeId; // 上级内容类型Id
	

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

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public CoType getSuperCoType() {
		return superCoType;
	}

	public void setSuperCoType(CoType superCoType) {
		this.superCoType = superCoType;
	}

	public List<CoType> getCoTypes() {
		return coTypes;
	}

	public void setCoTypes(List<CoType> coTypes) {
		this.coTypes = coTypes;
	}

	public String getSuperCoTypeId() {
		return superCoTypeId;
	}

	public void setSuperCoTypeId(String superCoTypeId) {
		this.superCoTypeId = superCoTypeId;
	}
}