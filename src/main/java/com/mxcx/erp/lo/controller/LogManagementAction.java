package com.mxcx.erp.lo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.erp.au.controller.LoginAction;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.lo.dao.entity.LogManagement;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 日志管理控制层
 * 
 * @author 王森20140912
 * 
 */
@Controller
public class LogManagementAction extends BaseController{
	
	private final static Logger log = Logger.getLogger(LogManagementAction.class);
	@Autowired
	private LogManagementService logManagementService;
	
	/**
	 * 日志管理
	 */
	@RequestMapping("/manager/erp/lo/logManagement.do")
	public String index(Model map) {
		return "/ftl/manager/lo/logManagement";
	}
	
	/**
	 * 根据id查询日志信息 
	 * @param id
	 *            
	 */
	@RequestMapping(value="/manager/erp/lo/getLogManagement.do", method = RequestMethod.POST)
	@ResponseBody
	public LogManagement getById(String id){
		LogManagement logManagement=(LogManagement) logManagementService.getOne(id, LogManagement.class);
		return logManagement;
	}
	
	/**
	 * 查询系统日志的所有信息
	 * 
	 * @param pageParameter
	 *         分页信息
	 * @return 功能集合(json格式)
	 * @author 王森20140912
	 *
	 */
	@RequestMapping(value = "/manager/erp/lo/findLogManagement.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		return logManagementService.findList(pageParameter);
	}
	
	/**
	 * 
	 * 
	 * @param 
	 * @return 日志集合
	 */
	@RequestMapping("/manager/erp/lo/findLogManagementList.do")
	@ResponseBody
	public List<LogManagement> getLogManagementList() {
		List<LogManagement> typeList = this.logManagementService.findList();
		return typeList;
	}
}
