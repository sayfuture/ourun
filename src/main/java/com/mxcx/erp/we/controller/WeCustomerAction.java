package com.mxcx.erp.we.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.service.DiCardService;
import com.mxcx.erp.we.dao.entity.WeCustomer;
import com.mxcx.erp.we.service.WeCustomerService;
import com.mxcx.erp.wechat.controller.WeChatAction;
import com.mxcx.erp.wechat.service.WeChatService;

/**
 * WeCustomerAction Thu Dec 29 20:55:34 CST 2016 hmy
 */

@Controller
public class WeCustomerAction extends BaseController {

	@Autowired
	private WeCustomerService weCustomerService;
	@Autowired
	private WeChatService weChatService;
	@Autowired
	private DiCardService diCardService;
	
	private final static Logger log = Logger.getLogger(WeCustomerAction.class);
	
	
	@RequestMapping(value = "/manager/erp/we/weCustomer.do")
	public String index() {
		return "/ftl/manager/we/weCustomer";
	}

	@RequestMapping(value = "/manager/erp/di/screenSend.do")
	public String screenSend() {
		return "/ftl/manager/di/screenSend";
	}	
	
	@RequestMapping("/manager/erp/we/findWeCustomerList.do")
	@ResponseBody
	public DataGrid findWeCustomerList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
//		try {
//			weChatService.groupSendByOpenId(this.getLoginUser(request));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		DataGrid dataGrid = weCustomerService.findWeCustomerList(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/we/modifyWeCustomer.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyWeCustomer(WeCustomer weCustomer,
			HttpServletRequest request) {
		return weCustomerService.modifyWeCustomerInfo(weCustomer,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/we/deleteWeCustomer.do")
	@ResponseBody
	public Boolean deleteWeCustomer(String ids, HttpServletRequest request) {
		String teamIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != teamIds) {
				for (String id : teamIds) {
					weCustomerService.deleteWeCustomer(id,
							this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/manager/erp/we/goToModifyWeCustomer.do")
	@ResponseBody
	public WeCustomer goToModifyWeCustomer(String id) {
		WeCustomer weCustomer = weCustomerService.findWeCustomerByID(id);
		return weCustomer;
	}
	
	@RequestMapping(value = "/manager/erp/di/screenSendimpl.do")
	@ResponseBody
	public Boolean screenSendimpl(HttpServletRequest request,String weCustomerIds,String cardId,String templateId) {
		String[] openIds=weCustomerIds.split(",");
		DiCard diCard=diCardService.findDiCardByID(Integer.valueOf(cardId));
		for(String openId:openIds){
			try {
//				weChatService.CustomerSend(this.getLoginUser(request), openId, diCard);
				weChatService.sendNews(this.getLoginUser(request), templateId, openId, diCard);
				weChatService.saveSendRecord(openId, this.getLoginUser(request), diCard, null);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return true;
	}
	
}
