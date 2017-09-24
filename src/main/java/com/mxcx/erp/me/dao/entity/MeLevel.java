package com.mxcx.erp.me.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 会员级别实体类
 * 
 * @author  2014/09/12
 * 
 */
@Entity
@Table(name = "ME_LEVEL")
public class MeLevel  extends BasePo {

	private static final long serialVersionUID = 3701366750338098729L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "LEVEL_NAME", nullable = false)
	private String levelName;	// 级别名称
	
	@Column(name = "START_POSITION", nullable = false)
	private Long startPosition; // 起始积分区间
	
	@Column(name = "END_POSITION", nullable = false)
	private Long endPosition; // 结束积分区间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Long getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Long startPosition) {
		this.startPosition = startPosition;
	}

	public Long getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Long endPosition) {
		this.endPosition = endPosition;
	}
}
