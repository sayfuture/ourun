package com.mxcx.erp.me.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.me.service.IMeMemberService;
import com.mxcx.erp.me.service.IMeMemberStatisticsService;

/**
 * 店员统计
 * @author  2014/10/21
 */
@Controller
public class MeStatisticsAction extends BaseController {

	@Autowired
	private IMeMemberStatisticsService memberStatisticsService;
	@Autowired
	private IMeMemberService memberService;
	
	@RequestMapping(value = "/manager/erp/me/meInfo.do")
	public String index() {
		return "ftl/manager/me/meStatistics";
	}
	
//	@RequestMapping("/manager/erp/me/findDiCardList.do")
//	@ResponseBody
//	public DataGrid findDiCardList(HttpServletRequest request,@ModelAttribute("pp") PageParameter pageParameter) {
//		DataGrid dataGrid = memberService.findMemberByConditionGroup(pageParameter,this.getLoginUser(request));
//		return dataGrid;
//	}
}