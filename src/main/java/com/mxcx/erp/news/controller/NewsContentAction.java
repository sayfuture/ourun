package com.mxcx.erp.news.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.service.AuEmployeeService;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.co.service.CoContentService;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.dao.entity.DiSendRecode;
import com.mxcx.erp.di.service.DiCardService;
import com.mxcx.erp.di.service.DiProcessRecordService;
import com.mxcx.erp.di.service.DiProcessService;
import com.mxcx.erp.di.service.DiSendRecodeService;
import com.mxcx.erp.utils.Constant;
import com.mxcx.erp.utils.HttpClientUtil;
import com.mxcx.erp.we.service.WeCustomerService;
import com.mxcx.erp.wechat.service.WeChatServiceImpl;

@Controller
public class NewsContentAction extends BaseController{
	@Autowired
	private CoContentService coContentService;
	@Autowired
	private DiProcessService diProcessService;
	@Autowired
	private DiCardService diCardService;
	@Autowired
	private DiProcessRecordService diProcessRecordService;
	@Autowired
	private DiSendRecodeService diSendRecodeService;
	@Autowired
	private AuEmployeeService auEmployeeService;
	@Autowired
	private WeCustomerService weCustomerService;
	private final static Logger log = Logger.getLogger(NewsContentAction.class);
	@RequestMapping(value = "/news/{id}")
	public ModelAndView newsInfo(@PathVariable String id,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/ftl/news/singlenews");
		CoContent coContent=(CoContent) coContentService.getOne(id,CoContent.class);
		view.addObject("coContent",coContent);
		
//	    String redirect_url="http://www.vanloon123.cn/ourun/news/cardInfo.do";
//	    https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=http://www.vanloon123.cn/ourun/news/cardInfo.do&response_type=code&scope=snsapi_userinfo&state=auappid=${appid}-secret=${secret}-openId=${openId}-cardId=${cardId}-recode=${recode}#wechat_redirect
//	    String redurl=Constant.AUTHORIZE_CODE.replace("APPID",auEmployee.getAppid()).replace("REDIRECT_URI",redirect_url)
//	    		.replace("STATE", "auappid=${appid}-secret=${secret}-openId=${openId}-cardId=${cardId}-recode=${recode}")
//	    		.replace("snsapi_base", "snsapi_userinfo");
		
		
//		List<Map<String, Object>> content=coContentService.getRelArticles(coContent.getId(), coContent.getCoType().getId());
//		view.addObject("content",content);
		return view;
	}
	
	@RequestMapping(value = "/news/user")
	public ModelAndView wechatUserInfo(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		System.out.println(state);
		int appidstart=state.lastIndexOf("auappid=");
		int appidend=state.indexOf("-secret=");
		String appid=state.substring(appidstart+8, appidend);
		int secretstart=state.lastIndexOf("secret=");
		int secretend=state.indexOf("-openId");
		String secret=state.substring(secretstart+7, secretend);
		int openstart=state.indexOf("openId=");
		int opentend=state.indexOf("-cardId");
		String openid=state.substring(openstart+7, opentend);
		int cardIdstart=state.indexOf("cardId=");
		String cardId=state.substring(cardIdstart+7, state.length());
//		int recomopenIdstart=state.indexOf("recomopenId=");
//		String recomopenId=state.substring(recomopenIdstart+12, state.length());
		
		if(StringUtils.isNotEmpty(code)){
		System.out.println("code------------:"+code);
		System.out.println("appid:"+appid+"--secret:"+secret+"--openid:"+openid+"--cardId:"+cardId);
		String url=Constant.OAUTH_ACCESS_TOKEN.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
		String result=HttpClientUtil.get(url);
		Map<String,Object> map=WeChatServiceImpl.json(result);
		if(map.containsKey("errcode")){
			log.error("wechatUserInfo errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
		}
		String newOpenid=(String) map.get("openid");
		if(StringUtils.isEmpty(newOpenid))
			newOpenid=openid;
		System.out.println("newOpenid:-------"+newOpenid);
		AuEmployee auEmployee=auEmployeeService.findAuEmployeeBywxInfo(appid,secret);
		DiCard diCard=diCardService.findDiCardByID(Integer.valueOf(cardId));
		DiSendRecode diSendRecode=diSendRecodeService.findDiSendRecode(openid, cardId);
			view.addObject("card_num",diCard.getUse_num());
		view.addObject("recode", diSendRecode.getId());
		view.addObject("openId", newOpenid);
		view.addObject("cardId",cardId);
		view.addObject("appid",appid);
		view.addObject("secret",secret);
		view.addObject("auEmployeeInfo",auEmployee );
		view.setViewName("/ftl/news/publicInfo");
		}
		return view;
	}
	@RequestMapping(value = "/news/cardInfo.do")
	public ModelAndView cardInfo(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
//		String cardId=request.getParameter("cardId");
//		String openId=request.getParameter("openId");
//		String diSendRecode=request.getParameter("diSendRecode");
//		String type=request.getParameter("type");
//		String appid=request.getParameter("appid");
//		String secret=request.getParameter("secret");
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		int appidstart=state.lastIndexOf("auappid=");
		int appidend=state.indexOf("-openId");
		String appid=state.substring(appidstart+8, appidend);
//		int secretstart=state.lastIndexOf("secret=");
//		int secretend=state.indexOf("-openId");
//		String secret=state.substring(secretstart+7, secretend);
		int openstart=state.indexOf("openId=");
		int opentend=state.indexOf("-cardId");
		String openid=state.substring(openstart+7, opentend);
		int cardIdstart=state.indexOf("cardId=");
		int cardIdend=state.indexOf("-recode");
		String cardId=state.substring(cardIdstart+7,cardIdend);
		int recordstart=state.indexOf("recode=");
		String record=state.substring(recordstart+7,state.length());
		String newOpenid=openid;
		AuEmployee auEmployee=auEmployeeService.findAuEmployeeBywxInfo(appid,null);
		if(StringUtils.isNotEmpty(code)){
		System.out.println("code------------:"+code);
		System.out.println("appid:"+appid+"--secret:"+auEmployee.getAppsecret()+"--openid:"+openid+"--cardId:"+cardId);
		String url=Constant.OAUTH_ACCESS_TOKEN.replace("APPID", appid).replace("SECRET", auEmployee.getAppsecret()).replace("CODE", code);
		String result=HttpClientUtil.get(url);
		Map<String,Object> map=WeChatServiceImpl.json(result);
		if(map.containsKey("errcode")){
			log.error("wechatUserInfo errcode:"+map.get("errcode")+"---errmsg:"+map.get("errmsg"));
			if(map.get("errcode").equals(40163)||map.get("errcode").equals(40029)){
				view.addObject("recode", record);
				view.addObject("openId", openid);
				view.addObject("cardId",cardId);
				view.addObject("appid",appid);
				view.addObject("secret",auEmployee.getAppsecret());
				view.addObject("auEmployeeInfo",auEmployee );
				view.setViewName("/ftl/news/publicInfo");
				return view;
			}
		}
		 newOpenid=(String) map.get("openid");
		System.out.println("cardInfo---newOpenId:"+newOpenid);
		}
		
		DiCard diCard=diCardService.findDiCardByID(Integer.valueOf(cardId));
		String endtime=DateUtil.format(diCard.getVaildtime(), "yyyy-MM-dd");
		view.addObject("cardId",diCard.getId());
		view.addObject("diCard",diCard);
		view.addObject("endtime",endtime);
		view.addObject("auEmployee",auEmployee);
		view.addObject("type",0);
		view.addObject("diSendRecode",record);
		view.addObject("openId",newOpenid);
		view.addObject("appid",appid);
		view.addObject("secret",auEmployee.getAppsecret());
		view.setViewName("/ftl/news/cardInfo");
		return view;
	}
	
	@RequestMapping(value = "/news/processInfo.do")
	public ModelAndView processInfo(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String cardId=request.getParameter("cardId");
		String openId=request.getParameter("openId");
		String diSendRecodeId=request.getParameter("diSendRecode");
		String type=request.getParameter("type");
		String appid=request.getParameter("appid");
		String secret=request.getParameter("secret");
		AuEmployee auEmployee=auEmployeeService.findAuEmployeeBywxInfo(appid,secret);
		DiSendRecode diSendRecode=diSendRecodeService.findDiSendRecodeByID(diSendRecodeId);
		view.setViewName("/ftl/news/resultPage");
		if(diSendRecode==null){
			
		}else
		{
		
		diProcessService.saveProcessInfo(request,view, diSendRecode, openId, cardId, auEmployee);
		}
		view.addObject("auEmployee",auEmployee);
		
		return view;
	}
	
//	@RequestMapping(value = "/news/shareCardInfo.do")
//	public ModelAndView shareCardInfo(HttpServletRequest request){
//		ModelAndView view = new ModelAndView();
//		String cardId=request.getParameter("cardId");
//		String openId=request.getParameter("openId");
//		String processId=request.getParameter("processId");
//		String type=request.getParameter("type");
//		String appid=request.getParameter("appid");
//		String secret=request.getParameter("secret");
//		String share_card_num=request.getParameter("share_card_num");
//		AuEmployee auEmployee=auEmployeeService.findAuEmployeeBywxInfo(appid,secret);
//		DiProcess diProcess=diProcessService.findDiProcessByID(processId);
//		Date endDate=diProcess.getEnd_time();
//		String endtime=DateUtil.format(endDate, "yyyy-MM-dd");
//		DiCard diCard=diCardService.findDiCardByID(Integer.valueOf(cardId));
//		view.addObject("diCard",diCard);
//		view.addObject("endtime",endtime);
//		view.addObject("auEmployee",auEmployee);
//		view.addObject("share_card_num",Integer.valueOf(share_card_num));
//		view.addObject("type",type);
//		view.setViewName("/ftl/news/shareCardInfo");
//		return view;
//	}
	public static void main(String[] args) {
		String state="auappid=${appid}-secret=${secret}-openId=${openId}-cardId=${cardId}-recode=${recode}";
		int appidstart=state.lastIndexOf("auappid=");
		int appidend=state.indexOf("-secret=");
		String appid=state.substring(appidstart+8, appidend);
		int secretstart=state.lastIndexOf("secret=");
		int secretend=state.indexOf("-openId");
		String secret=state.substring(secretstart+7, secretend);
		int openstart=state.indexOf("openId=");
		int opentend=state.indexOf("-cardId");
		String openid=state.substring(openstart+7, opentend);
		int cardIdstart=state.indexOf("cardId=");
		int cardIdend=state.indexOf("-recode");
		String cardId=state.substring(cardIdstart+7,cardIdend);
		int recordstart=state.indexOf("recode=");
		String record=state.substring(recordstart+7,state.length());
	System.out.println(appid+"-"+secret+"-"+openid+"-"+cardId+"-"+record);
	}
	
}