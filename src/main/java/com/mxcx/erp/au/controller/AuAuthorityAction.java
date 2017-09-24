package com.mxcx.erp.au.controller;

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

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuEmployeeAuthority;
import com.mxcx.erp.au.service.AuAuthorityPositionService;
import com.mxcx.erp.au.service.AuAuthorityService;
import com.mxcx.erp.au.service.AuEmployeeAuthorityService;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 功能管理控制层
 * 
 * @author  2014/06/23
 * 
 */
@Controller
public class AuAuthorityAction extends BaseController {
	private final static Logger log = Logger.getLogger(AuAuthorityAction.class);
	@Autowired
	private AuAuthorityService auAuthorityService; // 功能业务层接口
	
	@Autowired
	private AuEmployeeAuthorityService auEmployeeAuthorityService; // 功能人员业务层接口
	
//	@Autowired
//	private AuDeptAuthorityService auDeptAuthorityService; // 功能公司业务层接口
	
	@Autowired
	private AuAuthorityPositionService auAuthorityPositionService; // 功能角色业务层接口

	/**
	 * 进入功能管理
	 *    
	 * @param map
	 * @return功能页面
	 */
	@RequestMapping("/manager/erp/au/auAuthority.do")
	public String index(Model map) {

		return "/ftl/manager/au/auAuthority";
	}

	/**
	 * 查询功能的所有信息
	 * 
	 * @param pageParameter分页信息
	 * @return 功能集合(json格式)
	 */
	@RequestMapping(value = "/manager/erp/au/findList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid a = auAuthorityService.findList(pageParameter);
		return a;
	}

	/**
	 * 添加新功能
	 * 
	 * @param auAuthority新功能的基本信息
	 * @return 对应操作成功与失败的布尔值
	 * 
	 */
	@RequestMapping(value = "/manager/erp/au/addAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addauauthority(AuAuthority auAuthority ,HttpServletRequest request) {
		return auAuthorityService.addAuAuthority(auAuthority, this.getLoginUser(request));
	}

	/**
	 * 修改功能信息
	 * 
	 * @param auauthority功能修改后的信息
	 * @return 对应操作成功与失败的布尔值
	 */
	@RequestMapping("/manager/erp/au/updateAuthority.do")
	@ResponseBody
	public Boolean modify(AuAuthority auauthority, HttpServletRequest request) {
		return auAuthorityService.modifyAuAuthority(auauthority, this.getLoginUser(request));
	}

	/**
	 * 根据id删除功能记录
	 * 
	 * @param id功能id
	 * @return 对应操作成功与失败的布尔值
	 * 
	 */
	@RequestMapping("/manager/erp/au/deleteAuthority.do")
	@ResponseBody
	public Boolean remove(String id, HttpServletRequest request) {
		return this.auAuthorityService.deleteAuAuthority(id, this.getLoginUser(request));
	}

	/**
	 * 批量删除功能
	 * 
	 * @param ids待删除功能id拼串
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/deleteBinch.do")
	@ResponseBody
	public Boolean removeBinch(String ids,HttpServletRequest request) {
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String string : deptIds) {
						this.auEmployeeAuthorityService.deleteAuthorityAuEmployeeByAuthority(string); // 删除关系表
						this.auAuthorityPositionService.deleteAuthorityAuEmployee(string); // 删除关系表
						this.auAuthorityService.deleteAuAuthority(string, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			log.error("登陆错误。。", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据id查询功能信息
	 * 
	 * @param id功能ID
	 * @return 功能对象json
	 * 
	 */
	@RequestMapping("/manager/erp/au/getAuthority.do")
	@ResponseBody
	public AuAuthority getById(String id) {
		AuAuthority auAuthority = (AuAuthority) auAuthorityService.getOne(id, AuAuthority.class);
		return auAuthority;
	}

	/**
	 * 通过人员查询已经选中的权限用于回显
	 * 
	 * @param authorityId功能Id
	 * @param employeeId人员Id
	 * @return功能人员集合
	 */
	@RequestMapping(value="/manager/erp/au/getButtonByEmployeeAndAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public List<AuEmployeeAuthority> getButtonByEmployeeAndAuthority(String employeeId,String authorityId) {
		return this.auEmployeeAuthorityService.getButtonByEmployeeAndAuthority(employeeId,authorityId);
	}
	
	/**
	 * 通过公司查询已经选中的权限用于回显
	 * 
	 * @param authorityId功能Id
	 * @param employeeId人员Id
	 * @return功能人员集合
	 */
//	@RequestMapping(value="/manager/erp/au/getButtonByDeptAndAuthority.do", method = RequestMethod.POST)
//	@ResponseBody
//	public List<AuDeptAuthority> getButtonByDeptAndAuthority(String deptId,String authorityId) {
//		return this.auDeptAuthorityService.getButtonByDeptAndAuthority(deptId,authorityId);
//	}
	
	
}