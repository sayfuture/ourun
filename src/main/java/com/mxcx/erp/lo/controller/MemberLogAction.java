package com.mxcx.erp.lo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.lo.service.MemberLogService;

/**
 * 会员日志管理控制层
 * 
 * @author 王森20140917
 * 
 */
@Controller
public class MemberLogAction extends BaseController {
	private final static Logger log = Logger.getLogger(MemberLogAction.class);
	@Autowired
	private MemberLogService memberLogService;
	
	/**
	 * 会员日志管理
	 */
	@RequestMapping("/manager/erp/lo/memberLog.do")
	public String index(Model map) {
		return "/ftl/manager/lo/memberLog";
	}
	
	
	/**
	 * 查询会员日志的所有信息
	 * 
	 * @param pageParameter
	 *         分页信息
	 * @return 功能集合(json格式)
	 * @author 王森20140912
	 *
	 */
	@RequestMapping(value = "/manager/erp/lo/findMemberLog.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		return memberLogService.findList(pageParameter);
	}

	}

