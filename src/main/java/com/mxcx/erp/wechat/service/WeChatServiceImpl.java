package com.mxcx.erp.wechat.service;

import com.google.gson.Gson;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.di.service.DiSendRecodeService;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.me.service.IMeMemberService;
import com.mxcx.erp.qr.QRcode;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.utils.HttpClientUtil;
import com.mxcx.erp.we.dao.entity.TreeWxMenuVo;
import com.mxcx.erp.we.dao.entity.WeCustomer;
import com.mxcx.erp.we.dao.entity.WxMenu;
import com.mxcx.erp.we.service.WeCustomerService;
import com.mxcx.erp.we.service.WxMenuService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
@Service
public class WeChatServiceImpl implements WeChatService{
	private final static Logger log = Logger.getLogger(WeChatServiceImpl.class);
	private  static Map<String,String> token=new HashMap<String,String>();
	private  static Map<String,Map<String,Date>> jsJpatoken=new HashMap<String,Map<String,Date>>();
	@Autowired
	private WeCustomerService weCustomerService;
	@Autowired
	private IMeMemberService iMeMemberService;
	@Autowired
	private DiSendRecodeService diSendRecodeService;
	@Autowired
	private WxMenuService wxMenuService;
	@Override
	public Map<String, Object> getQRcode(String scene_id,AuEmployee auEmployee) throws Exception {
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		System.out.println("appid:"+auEmployee.getAppid());
		System.out.println("token:----"+token.get(auEmployee.getAppid()));
		String url=Constant.QRCODE.replace("TOKEN", token.get(auEmployee.getAppid()));
		log.info("getQRcode URL:"+url);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("expire_seconds",2592000);
		params.put("action_name","QR_SCENE");
		Map<String,Object> params1=new HashMap<String,Object>();
		params1.put("scene_id",new BigDecimal(scene_id));
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("scene",params1);
		params.put("action_info",params2);
		Gson gson=new Gson();
		String jsonObj=gson.toJson(params);
		String result=HttpClientUtil.post1(url,JSONObject.fromObject(jsonObj));
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")){
			if(map.get("errcode").equals(42001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
			throw new Exception("getQRcode errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return map;
	}

	@Override
	public Map<String, Object> getPermanentQRcode(String scene_str,AuEmployee auEmployee) throws Exception {
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url=Constant.PERMANENTQRCODE.replace("TOKENPOST", token.get(auEmployee.getAppid()));
		log.info("getPermanentQRcode URL:"+url);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("action_name","QR_LIMIT_STR_SCENE");
		Map<String,Object> params1=new HashMap<String,Object>();
		params1.put("scene_str",scene_str);
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("scene",params1);
		params.put("action_info",params2);
		Gson gson=new Gson();
		String jsonObj=gson.toJson(params);
		String result=HttpClientUtil.post1(url,JSONObject.fromObject(jsonObj));
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")){
			if(map.get("errcode").equals(42001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
			throw new Exception("getQRcode errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return map;
	}


	@Override
	public Map<String,String> getToken(String appid,String appsecret) throws Exception {
		appid=appid;
		appsecret=appsecret;
		String url=Constant.TOKENURL.replace("APPID", appid).replace("APPSECRET",appsecret);
		log.info(url);
		String tokentemp=HttpClientUtil.get(url);
		log.info("tokentemp:"+tokentemp);
		Map<String,Object> map=json(tokentemp);
		if(map.containsKey("access_token")){
//			token=(String) map.get("access_token");
			token.put(appid,(String) map.get("access_token"));
		}
		if(map.containsKey("errcode")){
			throw new Exception("getToken errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return token;
	}
	//	https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
	private void getJsapi_ticket(String appid,String appsecret)throws Exception{
		if(StringUtils.isEmpty(token.get(appid))){
			this.getToken(appid, appsecret);
		}
		String url=Constant.JSAPI_TICKET.replace("ACCESS_TOKEN", token.get(appid));
		String jsapi_ticket=HttpClientUtil.get(url);
		Map<String,Object> map=json(jsapi_ticket);
		if(map.containsKey("errcode")&&map.get("errcode").equals(0)){
			Map<String,Date> ticket=new HashMap<String, Date>();
			ticket.put(map.get("ticket").toString(),new Date());
			jsJpatoken.put(appid, ticket);
			return;
		}
		if(map.containsKey("errcode")){
			this.getToken(appid, appsecret);
			throw new Exception("getJsapi_ticket errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
	}
	private String getTicket(Date date,String appid,String appsecret) throws Exception {
		Map<String,Date> map=jsJpatoken.get(appid);
		if(map==null){
			this.getJsapi_ticket( appid, appsecret);
			map=jsJpatoken.get(appid);
		}
		Iterator iterator=map.entrySet().iterator();
		String key="";
		Date val=null;
		while (iterator.hasNext()){
			Map.Entry entry = (Map.Entry) iterator.next();
			 key = (String) entry.getKey();
			 val = (Date) entry.getValue();
			 break;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(val);//date 换成已经已知的Date对象
		cal.add(Calendar.HOUR_OF_DAY, Constant.JSTICKET_TIME);// before 1 hour
		if(cal.getTime().getTime()<date.getTime()){
			this.getJsapi_ticket( appid, appsecret);
			this.getTicket(date,appid, appsecret);
		}
			return 	key;
	}
	/**
	 *@Author: hmy
	 *@Description:  获得签名验证消息
	 * @param
	 *@Date: 18:01 2018/1/10
	 */
	@Override
	public  Map<String, String> sign( String url,String appid,String appsecret) throws Exception{
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = Constant.TOKEN;
		Date date=new Date();
		String timestamp = String.valueOf((date.getTime())/1000);
		String string1;
		String signature = "";
		String jsapi_ticket=this.getTicket(date,appid, appsecret);
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println("string1============="+string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.update(string1.getBytes());
			signature = byteToHex(crypt.digest());
			System.out.println("signature---------------"+signature);
		} catch (NoSuchAlgorithmException e) {
			log.error("sign--------"+e);
		}
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("noncestr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		return ret;
	}

	@Override
	public Map<String,Object> wxCreateMenu() throws Exception {
		String toke=token.get("wxcddc3ffbcdf9acf3");
		if(StringUtils.isEmpty(toke)){
			this.getToken("wxcddc3ffbcdf9acf3", "ac3f6ae887ab4a45eae281c8b0db9c6b");
		}
		String url=Constant.CREATE_MENU.replace("ACCESS_TOKEN", token.get("wxcddc3ffbcdf9acf3"));
		log.info("createWXMenu URL:"+url);
		Map<String,Object> params=new HashMap<String,Object>();
		List<WxMenu> firstMenu=wxMenuService.findWxMenuTree(null);
		for(WxMenu wxMenu:firstMenu){
			boolean temp=false;
			for(WxMenu secendMenu:wxMenu.getSub_button()){
				temp=true;
				secendMenu.setSuperWxMenu(null);
			}
			if(temp){
				wxMenu.setType(null);
			}
		}
		params.put("button",firstMenu);
		Gson gson=new Gson();
		String jsonObj=gson.toJson(params);
		System.out.println("createWXMenu---------"+jsonObj);
		String result=HttpClientUtil.post1(url,JSONObject.fromObject(jsonObj));
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")&&!map.get("errcode").equals(0)){
			if(map.get("errcode").equals(42001)){
				this.getToken("wxcddc3ffbcdf9acf3", "ac3f6ae887ab4a45eae281c8b0db9c6b");
			}
			throw new Exception("createWXMenu errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return map;
	}
	@Override
	public Map<String,Object> wxDelMenu() throws Exception {
		String toke=token.get("wxcddc3ffbcdf9acf3");
		if(StringUtils.isEmpty(toke)){
			this.getToken("wxcddc3ffbcdf9acf3", "ac3f6ae887ab4a45eae281c8b0db9c6b");
		}
		String url=Constant.DELETE_MENU.replace("ACCESS_TOKEN", token.get("wxcddc3ffbcdf9acf3"));
		log.info("delWXMenu URL:"+url);
		String result=HttpClientUtil.get(url);
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")&&!map.get("errcode").equals(0)){
			if(map.get("errcode").equals(42001)){
				this.getToken("wxcddc3ffbcdf9acf3", "ac3f6ae887ab4a45eae281c8b0db9c6b");
			}
			throw new Exception("delWXMenu errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return map;
	}
	@Override
	public String generQRcode(HttpServletRequest request,String sence_id,AuEmployee auEmployee) {
		String path="";
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map=this.getQRcode(sence_id,auEmployee);
		} catch (Exception e) {
			try {
				map=this.getQRcode(sence_id,auEmployee);
			}  catch (Exception e1) {
				e1.printStackTrace();
				log.error(e1);
			}
		}
		String projectPath=request.getSession().getServletContext().getRealPath("");
		Random ra =new Random();
		Integer random=ra.nextInt(99999)+1;
		String url=(String) map.get("url");
		if(StringUtils.isNotEmpty(url)){
			path= projectPath+"/upload/QRcode/";
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			path=path+auEmployee.getId()+random+".JPG";
			Boolean flag=QRcode.encode(url,path);
		}
		return auEmployee.getId()+random+".JPG";
	}

	@Override
	public String generPermanentQRcode(HttpServletRequest request,String sence_str,AuEmployee auEmployee) {
		String path="";
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map=this.getPermanentQRcode(sence_str,auEmployee);
		} catch (Exception e) {
			try {
				map=this.getPermanentQRcode(sence_str,auEmployee);
			}  catch (Exception e1) {
				e1.printStackTrace();
				log.error(e1);
			}
		}
		String projectPath=request.getSession().getServletContext().getRealPath("");
		Random ra =new Random();
		Integer random=ra.nextInt(99999)+1;
		String url=(String) map.get("url");
		if(StringUtils.isNotEmpty(url)){
			path= projectPath+"/upload/PermanentQRcode/";
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			path=path+auEmployee.getId()+random+".JPG";
			Boolean flag=QRcode.encode(url,path);
		}
		return auEmployee.getId()+random+".JPG";
	}



	public static Map<String,Object> json(String json){
		log.info("同步返回结果集："+json);
		JSONObject j=JSONObject.fromObject(json);
		Map<String,Object> map=j;
		return map;
	}

//	@Override
//	public List<String> userList(AuEmployee auEmployee) throws Exception {
//		String url=Constant.USERLIST.replace("ACCESS_TOKEN", token.get(auEmployee.getAppid())).replace("&next_openid=NEXT_OPENID","");
//		log.info(url);
//		String result=HttpClientUtil.get(url);
//		Map<String,Object> map=json(result);
//		if(map.containsKey("errcode")){
//			if(map.get("errcode").equals(42001)){
//				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
//			}
//			throw new Exception("userList errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
//		}
//		String data=map.get("data").toString();
//		Map<String,Object> map1=json(data);
//		JSONArray arr=(JSONArray) map1.get("openid");
//		List<String> lists=JSONArray.toList(arr, String.class, new JsonConfig());
//		Double d=Double.valueOf((String) map.get("total"))/10000d;
//		Integer t=(int) Math.ceil(d);
//		for(int i=1;i<t;i++){
//			String result1=HttpClientUtil.get(Constant.USERLIST.replace("ACCESS_TOKEN", token.get(auEmployee.getAppid())).replace("NEXT_OPENID",map.get("next_openid").toString()));
//			Map<String,Object> map2=json(result);
//			String data2=map2.get("data").toString();
//			Map<String,Object> map3=json(data2);
//			lists.addAll(JSONArray.toList((JSONArray) map3.get("openid"), String.class, new JsonConfig()));
//		}
//		return lists;
//	}

	@Override
	public Map<String, Object> getUserinfo(String openId) throws Exception {
		String toke=token.get(Constant.APPID);
		if(StringUtils.isEmpty(toke)){
			this.getToken(Constant.APPID,Constant.APPSECRET);
		}
		String url=Constant.USERINFO.replace("ACCESS_TOKEN",token.get(Constant.APPID)).replace("OPENID",openId);
		log.info(url);
		String result=HttpClientUtil.get(url);
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")){
			if(map.get("errcode").equals(42001)){
				this.getToken(Constant.APPID,Constant.APPSECRET);
			}
			throw new Exception("getUserinfo errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return map;
	}
//	@Override
//	public Map<String,Object> sendNewsInfo(AuEmployee auEmployee,CoContent coContent,String cardId) throws Exception{
//		Map<String,String> param=new HashMap<String,String>();
//		Map<String,Object> param1=new HashMap<String,Object>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		String thumb_media_id;
//		if(StringUtils.isNotEmpty(coContent.getMedia_id())){
//			try{
//				map=this.addMedia(auEmployee, coContent,cardId);
//			}catch(Exception e){
//				try{
//					map=this.addMedia(auEmployee, coContent,cardId);
//				}catch(Exception e1){
//					log.info("sendNewsInfo errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
//					log.info(e1.getMessage());
//				}
//			}
//			thumb_media_id=(String) map.get("media_id");
//		}else{
//			thumb_media_id=coContent.getMedia_id();
//		}
//		param1.put("thumb_media_id",thumb_media_id);
//		param1.put("title", coContent.getTitle());
//		param1.put("content", coContent.getBody());
//		param1.put("show_cover_pic", 1);
//		param1.put("content_source_url", "http://www.vanloon456.cn/ourun/news/user.do?auappid="+auEmployee.getAppid()+"&secret="+auEmployee.getAppsecret());
//		Gson json1=new Gson();
//		json1.toJson(param1);
//		param.put("articles", json1.toString());
//		String newsurl=Constant.UPLOADNEWS.replace("ACCESS_TOKEN",token.get(auEmployee.getAppid()));
//		String newsResult=HttpClientUtil.post(newsurl, param);
//		map=this.json(newsResult);
//		map.put("media_id",thumb_media_id );
//		coContent.setWeimg_url(map.get("url").toString());
//		coContent.setWetype(map.get("type").toString());
//		coContent.setCreated_at(Long.parseLong(map.get("created_at").toString()));
//
//		return map;
//	}
//	@Override
//	public Map<String,Object> addMedia(AuEmployee auEmployee,CoContent coContent,String cardId) throws Exception{
//		String fileurl=PropertiesReader.getInstance().getConfigItem("frontProjectAddress")+"upload/co/"+coContent.getFileUrl();
//		StringBuilder imgurl=new StringBuilder();
//		String url=Constant.ADDNEWS.replace("ACCESS_TOKEN", token.get(auEmployee.getAppid()));
//		Map<String,Object> param=new HashMap<String,Object>();
//		Map<String,Object> param1=new HashMap<String,Object>();
//		param1.put("title", coContent.getTitle());
//		param1.put("thumb_media_id", coContent.getId());
//		param1.put("author", coContent.getSource());
//		param1.put("digest", coContent.getIntroduce());
//		param1.put("show_cover_pic",1);
//		param1.put("content", coContent.getBody());
//		String redirect=Constant.AUTHORIZE_CODE.replace("APPID", auEmployee.getAppid()).replace("REDIRECT_URI", "http://www.vanloon456.cn/ourun/news/user.do?auappid="+auEmployee.getAppid()+"&secret="+auEmployee.getAppsecret())
//				.replace("STATE", cardId);
//		param1.put("content_source_url",redirect);
//		Gson json=new Gson();
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		list.add((param1));
//		param.put("articles", list);
//		System.out.println(JSONObject.fromObject(param).toString()+"--------66666666666666666");
//		String command1 ="curl -F media=@"+fileurl.replace("//", "/")+" "+Constant.UPLOADIMG.replace("ACCESS_TOKEN",token.get(auEmployee.getAppid()));
//		log.info("addMedia--->执行的URL:"+command1);
//		Process	process = Runtime.getRuntime().exec(command1);
//		process.waitFor();
//		InputStream is=process.getInputStream();
//		InputStream nois=process.getErrorStream();
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		imgurl.append(br.readLine().replaceAll("\\/", "/"));
//		System.out.println(imgurl.toString()+"------222222");
//		Map<String,Object> map=this.json(imgurl.toString());
//		if(map.containsKey("errcode")){
//			if(map.get("errcode").equals(42001)){
//				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
//			}
//			throw new Exception("addMedia errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
//		}
//		String wechaturl=(String) map.get("url");
//		Gson gson=new Gson();
//		String jsonObj=gson.toJson(param);
//		System.out.println(jsonObj+"--------88888888-----------"+JSONObject.fromObject(jsonObj));
//		String result=HttpClientUtil.post1(url, JSONObject.fromObject(jsonObj));
//		System.out.println(result+"------1111111111111");
//		map=this.json(result);
//		map.put("url", wechaturl);
//		return map;
//	}

	@Override
	public Map<String,Object> CustomerSend(AuEmployee auEmployee,String openId,DiCard diCard) throws Exception{
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url =Constant.CUSTOMER_SEND.replace("ACCESS_TOKEN",token.get(auEmployee.getAppid()));
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("touser", openId);
		param.put("msgtype", "news");
		Map<String,Object> param1=new HashMap<String,Object>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title",diCard.getCoContent().getTitle());
		System.out.println(diCard.getCoContent().getTitle());
		map.put("description", diCard.getCoContent().getIntroduce());
		String redirect_url="http://www.vanloon456.cn/ourun/news/user.do";
		String redurl=Constant.AUTHORIZE_CODE.replace("APPID",auEmployee.getAppid()).replace("REDIRECT_URI",redirect_url)
				.replace("STATE", "auappid="+auEmployee.getAppid()+"-secret="+auEmployee.getAppsecret()+"-openId="+openId+"-cardId="+diCard.getId())
				.replace("snsapi_base", "snsapi_userinfo");
		map.put("url", redurl);
		map.put("picurl", "http://www.vanloon456.cn/upload/co/"+diCard.getCoContent().getFileUrl());
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		list.add(map);
		param1.put("articles", list);
		param.put("news",param1);
		System.out.println(JSONObject.fromObject(param)+"----------------------");
		String result=HttpClientUtil.post1(url, JSONObject.fromObject(param));
		Map<String,Object> resultMap=this.json(result);
		if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			if(resultMap.get("errcode").equals(42001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
			throw new Exception("CustomerSend errcode:"+resultMap.get("errcode")+"---errmsg:"+resultMap.get("errmsg"));
		}
		return resultMap;
	}


	public Map<String,Object> CustomerSendText(AuEmployee auEmployee,String openId,String text) throws Exception{
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url =Constant.CUSTOMER_SEND.replace("ACCESS_TOKEN",token.get(auEmployee.getAppid()));
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("touser", openId);
		param.put("msgtype", "text");
		Map<String,Object> param1=new HashMap<String,Object>();
		param1.put("content", text);
		param.put("text", param1);
		String result=HttpClientUtil.post1(url, JSONObject.fromObject(param));
		Map<String,Object> resultMap=this.json(result);
		if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			if(resultMap.get("errcode").equals(42001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
			throw new Exception("CustomerSend errcode:"+resultMap.get("errcode")+"---errmsg:"+resultMap.get("errmsg"));
		}
		return resultMap;
	}
	public Map<String,Object> CustomerSendTextNoOpter(String openId,String text) throws Exception{
		String toke=token.get("wxcddc3ffbcdf9acf3");
		if(StringUtils.isEmpty(toke)){
			this.getToken("wxcddc3ffbcdf9acf3", "ac3f6ae887ab4a45eae281c8b0db9c6b");
		}
		String url =Constant.CUSTOMER_SEND.replace("ACCESS_TOKEN",token.get("wxcddc3ffbcdf9acf3"));
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("touser", openId);
		param.put("msgtype", "text");
		Map<String,Object> param1=new HashMap<String,Object>();
		param1.put("content", text);
		param.put("text", param1);
		String result=HttpClientUtil.post1(url, JSONObject.fromObject(param));
		Map<String,Object> resultMap=this.json(result);
		if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			if(resultMap.get("errcode").equals(42001)){
				this.getToken(Constant.APPID,Constant.APPSECRET);
			}
			throw new Exception("CustomerSend errcode:"+resultMap.get("errcode")+"---errmsg:"+resultMap.get("errmsg"));
		}
		return resultMap;
	}


	@Override
	public void saveSendRecord(String openId,AuEmployee auEmployee,DiCard diCard,String user_id){
		WeCustomer weCustomer=weCustomerService.findWeCustomerByID(openId);
		DiSendRecode diSendRecode=new DiSendRecode();
		diSendRecode.setNew_weuser(0);
		if(weCustomer==null){
			weCustomer=new WeCustomer();
			weCustomer.setOpenId(openId);
			weCustomer.setIs_follow(1);
			weCustomer.setCompanyIds(auEmployee.getCompany().getId()+"|");
			weCustomerService.addWeCustomer(weCustomer, auEmployee);
			diSendRecode.setNew_weuser(1);
		}else{
			weCustomer.setIs_follow(1);
			if(StringUtils.isNotEmpty(weCustomer.getCompanyIds())&&weCustomer.getCompanyIds().contains(auEmployee.getCompany().getId())){
			}else
				weCustomer.setCompanyIds(weCustomer.getCompanyIds()+auEmployee.getCompany().getId()+"|");
			weCustomerService.modifyWeCustomer(weCustomer, auEmployee);
		}
		diSendRecode.setCompanyId(auEmployee.getCompany().getId());
		diSendRecode.setDiCard(diCard);
		if(StringUtils.isNotEmpty(user_id)){
			MeMember meMember=iMeMemberService.findMemerByUserId(user_id);
			diSendRecode.setMeMember(meMember);
		}
		diSendRecode.setWeCustomer(weCustomer);

		diSendRecode.setSharenum(0);
		diSendRecodeService.addDiSendRecode(diSendRecode, auEmployee);
	}
	@Override
	public Map<String,Object> newsTemplateList(AuEmployee auEmployee) throws Exception{
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url =Constant.NEWS_TEMPLATE.replace("ACCESS_TOKEN",token.get(auEmployee.getAppid()));
		String result=HttpClientUtil.get(url);
		Map<String,Object> resultMap=this.json(result);
		if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			if(resultMap.get("errcode").equals(42001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
			throw new Exception("newsTemplateList errcode:"+resultMap.get("errcode")+"---errmsg:"+resultMap.get("errmsg"));
		}
		return resultMap;
	}
	public Map<String,Object> sendNews(AuEmployee auEmployee,String templateId,String openId,DiCard diCard) throws Exception{
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url =Constant.SEND_NEWS_TEMPLATE.replace("ACCESS_TOKEN",token.get(auEmployee.getAppid()));
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("touser", openId);
		param.put("template_id", templateId);
		String redirect_url="http://www.vanloon456.cn/ourun/news/user.do";
		String redurl=Constant.AUTHORIZE_CODE.replace("APPID",auEmployee.getAppid()).replace("REDIRECT_URI",redirect_url)
				.replace("STATE", "auappid="+auEmployee.getAppid()+"-secret="+auEmployee.getAppsecret()+"-openId="+openId+"-cardId="+diCard.getId())
				.replace("snsapi_base", "snsapi_userinfo");
		param.put("url",redurl );
		Map<String,Object> param1=new HashMap<String,Object>();
		param.put("data", param1);
		System.out.println(JSONObject.fromObject(param).toString());
		String  result=HttpClientUtil.post1(url, JSONObject.fromObject(param));
		Map<String,Object> resultMap=this.json(result);
		if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			if(resultMap.get("errcode").equals(42001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
			throw new Exception("sendNews errcode:"+resultMap.get("errcode")+"---errmsg:"+resultMap.get("errmsg"));
		}
		return resultMap;
	}


	public void groupSendByOpenId(AuEmployee auEmployee) throws Exception{
		String toke=token.get(auEmployee.getAppid());
		if(StringUtils.isEmpty(toke)){
			this.getToken(auEmployee.getAppid(), auEmployee.getAppsecret());
		}
		String url =Constant.SEND_OPENID.replace("ACCESS_TOKEN", token.get(auEmployee.getAppid()));
		Map<String,Object> param=new HashMap<String,Object>();
		Map<String,Object> content=new HashMap<String,Object>();
		String redirect_url="http://www.vanloon456.cn/ourun/news/user.do";
		String redurl=Constant.AUTHORIZE_CODE.replace("APPID",auEmployee.getAppid()).replace("REDIRECT_URI",redirect_url)
				.replace("STATE", "auappid="+auEmployee.getAppid()+"-secret="+auEmployee.getAppsecret()+"-openId=o7Hc1wIH9B0AFBcXd6xINKjtoJwU-cardId=16")
				.replace("snsapi_base", "snsapi_userinfo");
		content.put("content", "您好，您有一张优惠券可以<a href='"+redurl+"'>点击领取</a>");
		List<String> list=new ArrayList<String>();
		list.add("o7Hc1wIH9B0AFBcXd6xINKjtoJwU");//o7Hc1wIH9B0AFBcXd6xINKjtoJwU
		list.add("o7Hc1wNHkO6XID4JgYmquget5VfM");
		list.add("o7Hc1wA1pu-4658qM2QJZSK7wvHE");
		param.put("touser", list);
		param.put("msgtype", "text");
		param.put("text",content);
		String s=JSONObject.fromObject(param).toString();
		System.out.println("-----------"+s);
		String  result=HttpClientUtil.post1(url, JSONObject.fromObject(param));
		Map<String,Object> resultMap=this.json(result);
		if(resultMap.containsKey("errcode")&&!resultMap.get("errcode").equals(0)){
			if(resultMap.get("errcode").equals(40001)){
				this.getToken(auEmployee.getAppid(),auEmployee.getAppsecret());
			}
//			 throw new Exception("sendNews errcode:"+resultMap.get("errcode")+"---errmsg:"+resultMap.get("errmsg"));
		}
	}

	private static String byteToHex(final byte[] hash) {
		StringBuffer hexStr = new StringBuffer();
		// 字节数组转换为 十六进制 数
		for (int i = 0; i < hash.length; i++) {
			String shaHex = Integer.toHexString(hash[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexStr.append(0);
			}
			hexStr.append(shaHex);
		}
		return hexStr.toString();
	}
}
