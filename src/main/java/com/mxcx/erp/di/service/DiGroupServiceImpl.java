package com.mxcx.erp.di.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.utils.HttpClientUtil;
import com.mxcx.erp.wechat.service.WeChatService;
import com.mxcx.erp.wechat.service.WeChatServiceImpl;

/**
 * DiCardServiceImpl Thu Dec 29 20:51:23 CST 2016 hmy
 */

@Service
public class DiGroupServiceImpl extends BaseService<T> implements
DiGroupService {
	private final static Logger log = Logger.getLogger(DiGroupServiceImpl.class);
	private  static Map<String,String> token=new HashMap<String,String>();
	@Autowired
	private WeChatService weChatService;
	
	
	@Override
	public Boolean groupSend(AuEmployee auEmployee, String desc,String clickDesc,List<String> openids,String cardId) throws Exception {
		Boolean flag=true;
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			token=weChatService.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url =Constant.SEND_OPENID.replace("ACCESS_TOKEN", token.get(auEmployee.getAppid()));
		 Map<String,Object> param=new HashMap<String,Object>();
		 Map<String,Object> content=new HashMap<String,Object>();
	    String redirect_url="http://www.vanloon123.cn/ourun/news/user.do";
	    String redurl=Constant.AUTHORIZE_CODE.replace("APPID",auEmployee.getAppid()).replace("REDIRECT_URI",redirect_url)
	    		.replace("STATE", "auappid="+auEmployee.getAppid()+"-secret="+auEmployee.getAppsecret()+"-openId="+openids.get(0)+"-cardId="+cardId)
	    		.replace("snsapi_base", "snsapi_userinfo");
		 content.put("content", desc+"<a href='"+redurl+"'>"+clickDesc+"</a>");
		 param.put("touser", openids);
		 param.put("msgtype", "text");
		 param.put("text",content);
		 String  result=HttpClientUtil.post1(url, JSONObject.fromObject(param));
		 Map<String,Object> resultMap=WeChatServiceImpl.json(result);
		 if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			 flag=false;
			 log.error("GroupSend ERROR code:"+resultMap.get("errcode"));
			 log.error("GroupSend ERROR errmsg:"+resultMap.get("errmsg")+"-----msg_id:"+resultMap.get("msg_id"));
			if(resultMap.get("errcode").equals(42001)){
				token=weChatService.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
	}
		return flag;
	}
	
}
