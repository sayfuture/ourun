package com.mxcx.erp.di.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.di.dao.entity.DiCard;
import com.mxcx.erp.di.service.DiCardService;
import com.mxcx.erp.di.service.DiGroupService;
import com.mxcx.erp.wechat.service.WeChatService;

/**
 * DiCardAction Thu Dec 29 20:51:23 CST 2016 hmy
 */

@Controller
public class DiGroupSendAction extends BaseController {

	@Autowired
	private DiGroupService diGroupService;
	@Autowired
	private DiCardService diCardService;
	@Autowired
	private WeChatService weChatService;
	@RequestMapping(value = "/manager/erp/di/groupInfo.do")
	public String index() {
		return "/ftl/manager/di/groupSend";
	}
	
	
	@RequestMapping(value = "/manager/erp/di/groupSend.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean groupSend( HttpServletRequest request,String desc,String clickDesc,String openids,String cardId) {
		Boolean flag=true;
		if(StringUtils.isEmpty(desc)||StringUtils.isEmpty(clickDesc)||StringUtils.isEmpty(openids))
		{
			flag=false;
		}
		try {
			if(flag){
				String[] openIdGroup=openids.split(",");
				List<String> list=new ArrayList<String>();
				for(int i=0;i<openIdGroup.length;i++){
					list.add(openIdGroup[i]);
				}
				flag=diGroupService.groupSend( this.getLoginUser(request), desc, clickDesc,list,cardId);
				if(flag){
					DiCard diCard=diCardService.findDiCardByID(Integer.valueOf(cardId));
					for(String openId:list){
						weChatService.saveSendRecord(openId, this.getLoginUser(request), diCard, null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e);
		}
		return  flag;
	}
}
