package com.mxcx.erp.we.dao.entity;

import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.erp.au.dao.entity.AuDept;

import java.util.Date;
import java.util.List;

/**
 * 工作组实体类
 * 
 * @author  2014/06/23
 * 
 */
public class TreeWxMenuVo {

	private String id;

	private String name; // 工作组名称

	private String notes; // 小组说明
	
	private List<WxMenu> wxMenus;
	
	private boolean checked=false;//是否选中

	
	private String state;

	private String url;
	
	private String pagepath;
	
	private String type;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<WxMenu> getWxMenus() {
		return wxMenus;
	}

	public void setWxMenus(List<WxMenu> wxMenus) {
		this.wxMenus = wxMenus;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}