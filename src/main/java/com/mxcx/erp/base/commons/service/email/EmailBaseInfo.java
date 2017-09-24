package com.mxcx.erp.base.commons.service.email;

/**
 * Email基本配置信息
 * 09/11: 目前仅包含发送邮件的来源
 * 
 * @author  20140911
 *
 */
public class EmailBaseInfo {
	
	
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}
	
	private String fromEmail;
	
}
