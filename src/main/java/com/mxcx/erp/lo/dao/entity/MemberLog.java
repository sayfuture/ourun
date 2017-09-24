package com.mxcx.erp.lo.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mxcx.erp.base.commons.controller.BasePo;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.erp.me.dao.entity.MeMember;

/**
 * 会员日志实体类
 * @author 王森20140917
 *
 */
@Entity
@Table(name = "LO_MEMBER")
public class MemberLog extends BasePo {

	private static final long serialVersionUID = 5020354533741975886L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "IP")
	private String ip;           // 服务器ip
	
	@Column(name = "REMOTE_IP")
	private String remoteIp;           // 会员登录ip
	
	@Column(name = "FLAG")
	private Boolean flag;        //操作结果
	
	@Column(name = "DATAVALUE")
	private String dataValue; // 内容正文
	
	
	@ManyToOne
	@JoinColumn(name = "ME_MEMBER")
	private MeMember meMember;       //会员id
	
	@Column(name = "LOG_MODULE")
	private String logModule;  //模块名
	
	@Column(name = "LOG_FUNCTIONS")
	private String logFunctions;     //会员功能名

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

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getLogFunctions() {
		return logFunctions;
	}

	public void setLogFunctions(String logFunctions) {
		this.logFunctions = logFunctions;
	}

	public MeMember getMeMember() {
		return meMember;
	}

	public void setMeMember(MeMember meMember) {
		this.meMember = meMember;
	}

	public String getLogModule() {
		return logModule;
	}

	public void setLogModule(String logModule) {
		this.logModule = logModule;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	
}
