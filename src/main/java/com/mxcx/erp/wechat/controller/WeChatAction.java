package com.mxcx.erp.wechat.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mxcx.ec.base.commons.util.PropertiesReader;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.service.AuEmployeeService;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.dao.entity.DiProcess;
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.di.service.DiCardService;
import com.mxcx.erp.di.service.DiProcessService;
import com.mxcx.erp.di.service.DiSendRecodeService;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.me.service.IMeMemberService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.utils.HttpClientUtil;
import com.mxcx.erp.we.dao.entity.WeCustomer;
import com.mxcx.erp.we.service.WeCustomerService;
import com.mxcx.erp.wechat.service.WeChatService;
import com.mxcx.erp.wechat.service.WeChatServiceImpl;

/**
 * WeChatAction Thu Dec 29 20:55:34 CST 2016 hmy
 */

@Controller
public class WeChatAction extends BaseController {

	private final static Logger log = Logger.getLogger(WeChatAction.class);
	private  static String token="";
	@Autowired
	private WeChatService weChatService;
	@Autowired
	private DiSendRecodeService diSendRecodeService;
	@Autowired
	private WeCustomerService weCustomerService;
	@Autowired
	private DiCardService diCardService;
	@Autowired
	private AuEmployeeService auEmployeeService;
	@Autowired
	private IMeMemberService iMeMemberService;
	@Autowired
	private DiProcessService diProcessService;
	
	@RequestMapping("/manager/erp/wechat/link1.do")
	@ResponseBody
	public String link1(HttpServletRequest request) {
		return"hello";
	}
	/**
	 * 微信登陆
	 * @param request
	 * @return
	 */
//	@RequestMapping("/manager/erp/wechat/link.do")
//	@ResponseBody
//	public Long link(HttpServletRequest request,HttpServletResponse response) {
//		String signature=request.getParameter("signature");
//		String timestamp=request.getParameter("timestamp");
//		String nonce=request.getParameter("nonce");
//		String echostr=request.getParameter("echostr");
//		log.info(signature+"--"+timestamp+"--"+nonce+"--"+echostr);
//		try{
//			ArrayList<String> list=new ArrayList<String>();
//			list.add(nonce);
//			list.add(timestamp);
//			list.add(Constant.TOKEN);
//			Collections.sort(list);
//			String check=DigestUtils.shaHex(list.get(0)+list.get(1)+list.get(2));
//			if(check.equals(signature)){
//				log.info("true!");
//				return Long.valueOf(echostr);
//			}
//		}catch(Exception e){
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
//		return 0l;
//	}
	
	@RequestMapping("/manager/erp/wechat/link.do")
	@ResponseBody
	public void link(HttpServletRequest request,HttpServletResponse response) {
		try{
         SAXReader reader = new SAXReader();
         Document document=reader.read(request.getInputStream());
         Element root = document.getRootElement();
         Element ToUserName=root.element("ToUserName");
         Element FromUserName=root.element("FromUserName");
         Element CreateTime=root.element("CreateTime");
         Element MsgType=root.element("MsgType");
         Element Event=root.element("Event");
         Element EventKey=root.element("EventKey");
         Element Ticket=root.element("Ticket");
         String eventKey=EventKey.getText().replace("qrscene_", "");
         String openId=FromUserName.getText();
         System.out.println(eventKey+"-----------openId:"+openId);
         AuEmployee auEmployee=auEmployeeService.getAuEmployeeBywxname(ToUserName.getText());
         DiCard diCard;
         String user_id;
         Integer cardId;
         //分享出去的情况，查询到发送记录，保存并修改
         if(eventKey.substring(0, 2).equals("41")){
     		Date date=new Date();
        	  user_id=eventKey.substring(2, 6);
        	  cardId=Integer.valueOf(eventKey.substring(6, eventKey.length()));
         	 DiProcess diProcess=diProcessService.findDiProcessByID(cardId+"", openId);
         	diCard=diCardService.findDiCardByID(cardId);
         	WeCustomer weCustomer=weCustomerService.findWeCustomerByID(openId);
			if(weCustomer==null){
				weCustomer=new WeCustomer();
				weCustomer.setOpenId(openId);
				weCustomer.setIs_follow(1);
				weCustomer.setCompanyIds(auEmployee.getCompany().getId()+"|");
				weCustomerService.addWeCustomer(weCustomer, auEmployee);
			}else{
				if(weCustomer.getIs_follow()!=null&&weCustomer.getIs_follow().equals(0)){
					weCustomer.setIs_follow(1);
					if(StringUtils.isNotEmpty(weCustomer.getCompanyIds())&&weCustomer.getCompanyIds().contains(auEmployee.getCompany().getId())){}else{
						weCustomer.setCompanyIds(weCustomer.getCompanyIds()+auEmployee.getCompany().getId()+"|");
					}
					weCustomerService.modifyWeCustomer(weCustomer, auEmployee);
				}
			}
         	 if(diProcess!=null){
 				if(diProcess.getStatus().equals(Constant.USED_SIGN)){
 					weChatService.CustomerSendText(auEmployee, openId,  "您已使用过这张优惠券，敬请关注下次活动");
 				}else{
 					weChatService.CustomerSendText(auEmployee, openId,  "已领取过，请到店铺："+auEmployee.getAddress()+"使用！");}
 				return;
         	 }else{
         		diProcessService.saveDiProcessInfo(auEmployee, diCard, weCustomer);
         	 }
        	  if(diCard.getUsed_num()>diCard.getTotal_num()||diCard.getUsed_num().equals(diCard.getTotal_num())){
        		 weChatService.CustomerSendText(auEmployee, openId, "本次活动优惠券已领取完了，敬请关注下次活动！");
        		 return;
        	 }
        	 if(date.getTime()>diCard.getVaildtime().getTime()){
        		 weChatService.CustomerSendText(auEmployee, openId, "优惠券已过期，敬请关注下次活动！");
        		 return;
        	 }
        	 diCard.setUsed_num(diCard.getUsed_num()+1);
      	   Map<String,Object> resultMap=weChatService.CustomerSend(auEmployee, openId, diCard);
			diCardService.modifyDiCardUsedNum(diCard, auEmployee);
        	 weChatService.CustomerSendText(auEmployee, openId, "领取成功！");
        	   weChatService.saveSendRecord(openId, auEmployee, diCard, user_id);
         }else{
              user_id=eventKey.substring(0, 4);
              cardId=Integer.valueOf(eventKey.substring(4, eventKey.length()));
              diCard=diCardService.findDiCardByID(cardId);
              Map<String,Object> resultMap=weChatService.CustomerSend(auEmployee, openId, diCard);
     				weChatService.saveSendRecord(openId, auEmployee, diCard, user_id);
         }
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getStackTrace());
		}
	}
	@RequestMapping("/manager/erp/wechat/reback.do")
	@ResponseBody
	public void reback(HttpServletRequest request,HttpServletResponse response){
		System.out.println("reback@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
	/**
	 * 创建零时二维码ticket
	 * @param scene_id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/manager/erp/wechat/getQRcode.do")
	@ResponseBody
	public static Map<String,Object> getQRcode(String scene_id) throws Exception{
		String url=Constant.QRCODE.replace("TOKEN", token);
		log.info("getQRcode URL:"+url);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("expire_seconds",2592000);
		params.put("action_name","QR_SCENE");
		Map<String,Object> params1=new HashMap<String,Object>();
		params1.put("scene_id",123);
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("scene",params1);
		params.put("action_info",params2);
		Gson gson=new Gson();
		String jsonObj=gson.toJson(params);
		String result=HttpClientUtil.post1(url,JSONObject.fromObject(jsonObj));
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")){
			if(map.get("errcode").equals(40014)){
				token=getToken();
			}
			throw new Exception("getQRcode errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return map;
	}
	
	@RequestMapping("/manager/erp/wechat/getQRcodePicture.do")
	public String getQRcodePicture(HttpServletRequest request,HttpServletResponse response){
		String sence_id=request.getParameter("sence_id");
		String employeeid=request.getParameter("employeeid");
		HttpSession session = request.getSession();
		String filePath=weChatService.generQRcode(request, sence_id, this.getLoginUser(request));
		session.setAttribute("path", PropertiesReader.getInstance().getConfigItem("rootPath")+"/upload/QRcode/"+filePath);
			return "/ftl/manager/QRcodePicture";
	}
	
	@RequestMapping("/manager/erp/wechat/getPermanentQRcodePicture.do")
	public String getPermanentQRcodePicture(HttpServletRequest request,HttpServletResponse response){
		String sence_str=request.getParameter("sence_str");
		String employeeid=request.getParameter("employeeid");
		HttpSession session = request.getSession();
		String filePath=weChatService.generPermanentQRcode(request, sence_str, this.getLoginUser(request));
		session.setAttribute("path", PropertiesReader.getInstance().getConfigItem("rootPath")+"/upload/PermanentQRcode/"+filePath);
			return "/ftl/manager/QRcodePicture";
	}
	
	@RequestMapping("/manager/erp/wechat/getQRcodeDownloads.do")
	public void getQRcodeDownloads(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String sence_id=request.getParameter("sence_id");
		String QRfilePath=weChatService.generQRcode(request, sence_id, this.getLoginUser(request));
		String filePath = request.getSession().getServletContext().getRealPath("")+
				"/upload/QRcode/"+ QRfilePath; //文件在项目中的路径
		File file=new File(filePath);  
		 InputStream inputStream = null;  
	        OutputStream outputStream = null;     
	        // 以流的形式下载文件  
	        try {  
	            inputStream = new BufferedInputStream( new FileInputStream(file));  
	            outputStream = new BufferedOutputStream(response.getOutputStream());  
	            byte[] buffer = new byte[inputStream.available()];  
	            inputStream.read(buffer);  
	            inputStream.close();  
	            // 清空response  
	            response.reset();  
	            // 设置response的Header  
	            response.addHeader("Content-Disposition","attachment;filename=" + QRfilePath);  
	            response.addHeader("Content-Length", "" + file.length());  
	            response.setContentType("application/octet-stream");  
	            outputStream.write(buffer);  
	            outputStream.flush();  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        }finally{  
	            inputStream.close();  
	            outputStream.close();  
	        }  
	}
	
	@RequestMapping("/manager/erp/wechat/getPermanentQRcodeDownloads.do")
	public void getPermanentQRcodeDownloads(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String scene_str=request.getParameter("scene_str");
		String QRfilePath=weChatService.generPermanentQRcode(request, scene_str, this.getLoginUser(request));
		String filePath = request.getSession().getServletContext().getRealPath("")+
				"/upload/PermanentQRcode/"+ QRfilePath; //文件在项目中的路径
		File file=new File(filePath);  
		 InputStream inputStream = null;  
	        OutputStream outputStream = null;     
	        // 以流的形式下载文件  
	        try {  
	            inputStream = new BufferedInputStream( new FileInputStream(file));  
	            outputStream = new BufferedOutputStream(response.getOutputStream());  
	            byte[] buffer = new byte[inputStream.available()];  
	            inputStream.read(buffer);  
	            inputStream.close();  
	            // 清空response  
	            response.reset();  
	            // 设置response的Header  
	            response.addHeader("Content-Disposition","attachment;filename=" + QRfilePath);  
	            response.addHeader("Content-Length", "" + file.length());  
	            response.setContentType("application/octet-stream");  
	            outputStream.write(buffer);  
	            outputStream.flush();  
	        } catch (Exception e) {  
	            // TODO: handle exception  
	        }finally{  
	            inputStream.close();  
	            outputStream.close();  
	        }  
	}
	
	
	
	@RequestMapping("/manager/erp/wechat/userList.do")
	@ResponseBody
	public  List<String> userList() throws Exception{
		String url=Constant.USERLIST.replace("ACCESS_TOKEN", token).replace("&next_openid=NEXT_OPENID","");
		log.info(url);
		String result=HttpClientUtil.get(url);
		Map<String,Object> map=json(result);
		if(map.containsKey("errcode")){
			if(map.get("errcode").equals(40014)){
				token=getToken();
			}
			throw new Exception("userList errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		String data=map.get("data").toString();
		Map<String,Object> map1=json(data);
		JSONArray arr=(JSONArray) map1.get("openid");
		List<String> lists=JSONArray.toList(arr, String.class, new JsonConfig());
		Double d=Double.valueOf((String) map.get("total"))/10000d;
		Integer t=(int) Math.ceil(d);
		for(int i=1;i<t;i++){
			String result1=HttpClientUtil.get(Constant.USERLIST.replace("ACCESS_TOKEN", token).replace("NEXT_OPENID",map.get("next_openid").toString()));
			Map<String,Object> map2=json(result);
			String data2=map2.get("data").toString();
			Map<String,Object> map3=json(data2);
			lists.addAll(JSONArray.toList((JSONArray) map3.get("openid"), String.class, new JsonConfig()));
		}
		return lists;
	}
	
	/**
	 * 获取token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manager/erp/wechat/getToken.do")
	@ResponseBody
	public static String getToken() throws Exception {
		String url=Constant.TOKENURL.replace("APPID", Constant.APPID).replace("APPSECRET",Constant.APPSECRET);
		log.info(url);
		String tokentemp=HttpClientUtil.get(url);
		log.info("tokentemp:"+tokentemp);
		Map<String,Object> map=json(tokentemp);
		if(map.containsKey("access_token")){
			token=(String) map.get("access_token");
		}
		if(map.containsKey("errcode")){
			throw new Exception("getToken errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		return token;
	}
    private static Map<String,Object> json(String json){
    	log.info("同步返回结果集："+json);
    	JSONObject j=JSONObject.fromObject(json);
    	Map<String,Object> map=j;
    	return map;
    }
    
//	@RequestMapping("/manager/erp/wechat/getNewsList.do")
//	@ResponseBody
//	public  Map<String, Object> getNewsList() throws Exception {
//		AuEmployee auEmployee=auEmployeeService.findAuEmployeeById("1212");
//		Map<String, Object> map=weChatService.newsTemplateList(auEmployee);
//	return map;
//	}
//	@RequestMapping("/manager/erp/wechat/sendNews.do")
//	@ResponseBody
//	public  Map<String, Object> sendNews(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		AuEmployee auEmployee=auEmployeeService.findAuEmployeeById("1212");
//		String templateId=request.getParameter("templateId");
//		Map<String, Object> map=weChatService.sendNews(auEmployee,templateId,"o7Hc1wIH9B0AFBcXd6xINKjtoJwU",null);
//	return map;
//	}
}
