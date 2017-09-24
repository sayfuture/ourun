package com.mxcx.erp.base.commons.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


/**
 * 邮件业务层接口实现
 * 
 * @author  20140910
 * 
 */
@Service("auHTMLEmailServiceImpl")
public class HTMLEmailServiceImpl implements IEmailService{

	@Override
	public void send(String toEmail, String subject, String content) throws Exception {
         
		 try {  
			 MimeMessage mimeMessage=mailSender.createMimeMessage();
			 MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,false,"utf-8");//由于是html邮件，不是mulitpart类型
			 helper.setTo(toEmail);
			 helper.setFrom(emailBaseInfo.getFromEmail());
			 helper.setSubject(subject);//主题  
			 helper.setText(content,true);//邮件HTML内容  
			 mailSender.send(mimeMessage);
		   } catch (Exception e) {  
			   e.printStackTrace();
			   throw e;
		   }
	}
	
	@Autowired
	private EmailBaseInfo emailBaseInfo;

	@Autowired
	private JavaMailSender mailSender;
}
