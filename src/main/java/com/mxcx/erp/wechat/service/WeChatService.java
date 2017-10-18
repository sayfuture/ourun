package com.mxcx.erp.wechat.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.di.dao.entity.DiCard;

public interface WeChatService {

public  Map<String,Object> getQRcode(String scene_id,AuEmployee auEmployee) throws Exception;
public Map<String, Object> getPermanentQRcode(String scene_str,AuEmployee auEmployee) throws Exception;
public  Map<String,String> getToken(String appid,String appsecret) throws Exception;
public String generQRcode(HttpServletRequest request, String sence_id,
		AuEmployee auEmployee);
public String generPermanentQRcode(HttpServletRequest request,String sence_str,AuEmployee auEmployee);
public List<String> userList(AuEmployee auEmployee) throws Exception;
public Map<String,Object> getUserinfo(String openId)throws Exception;

public Map<String,Object> sendNewsInfo(AuEmployee auEmployee,CoContent coContent,String cardId) throws Exception;
public Map<String,Object> addMedia(AuEmployee auEmployee,CoContent coContent,String cardId) throws Exception;

public Map<String,Object> CustomerSend(AuEmployee auEmployee,String openId,DiCard diCard) throws Exception;
public void saveSendRecord(String openId,AuEmployee auEmployee,DiCard diCard,String user_id);
//public void screenSend(String openId,AuEmployee auEmployee,DiCard diCard,String user_id);

public Map<String,Object> newsTemplateList(AuEmployee auEmployee) throws Exception;
public Map<String,Object> sendNews(AuEmployee auEmployee,String templateId,String openId,DiCard diCard) throws Exception;

public Map<String,Object> CustomerSendText(AuEmployee auEmployee,String openId,String text) throws Exception;
public void groupSendByOpenId(AuEmployee loginUser) throws Exception;
}
