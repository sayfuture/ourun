package com.mxcx.erp.base.commons.service.email;


/**
 * 邮件业务层接口
 * 
 * @author  20140910
 * 
 */
public interface IEmailService {

	public void send(String toEmail, String subject, String content) throws Exception ;
	
}
