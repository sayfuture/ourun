package com.mxcx.erp.lo.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.ec.base.commons.util.DateUtil;

/**
 * 系统日志实体类
 * 
 * @author 王森20140917
 * 
 */
@Entity
@Table(name = "LO_MANAGEMENT")
public class LogManagement extends BasePo{

	private static final long serialVersionUID = 5031820903345210676L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;

	@Column(name = "IP")
	private String ip;  //ip地址
		
	@Column(name = "LOG_MODULE")
	private String logModule;  //模块名
	
	@Column(name = "LOG_FUNCTIONS")
	private String logFunctions;  //功能名
	
	@Column(name = "FLAG")
	private Boolean flag;  //操作结果
	
	@Column(name = "TIME")
	private Date time; // 操作时间
	
	@Column(name = "DATAVALUE")
	private String dataValue; // 内容正文
	
	@ManyToOne
	@JoinColumn(name = "AU_EMPLOYEE")
	private AuEmployee auEmployee; // 操作人信息
	
	@ManyToOne
	@JoinColumn(name = "AU_POSITION")
	private AuPosition auPosition; // 操作人角色

	@Transient
	private String time2;

	public String getTime2() {
		if (time != null) {
			time2 = DateUtil.format(time, "yyyy-MM-dd HH:mm:ss");
		}
		return time2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogModule() {
		return logModule;
	}

	public void setLogModule(String logModule) {
		this.logModule = logModule;
	}

	public void setLogFunctions(String logFunctions) {
		this.logFunctions = logFunctions;
	}

	public String getLogFunctions() {
		return logFunctions;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public AuEmployee getAuEmployee() {
		return auEmployee;
	}

	public void setAuEmployee(AuEmployee auEmployee) {
		this.auEmployee = auEmployee;
	}

	public AuPosition getAuPosition() {
		return auPosition;
	}

	public void setAuPosition(AuPosition auPosition) {
		this.auPosition = auPosition;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

}
