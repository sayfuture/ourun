//package com.mxcx.erp.au.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.mxcx.erp.au.dao.entity.AuButton;
//import com.mxcx.erp.au.service.AuButtonService;
//import com.mxcx.erp.commons.base.controller.BaseController;
//import com.mxcx.ec.base.commons.util.DataGrid;
//import com.mxcx.ec.base.commons.dao.entity.PageParameter;
//
///**
// * 按钮管理控制层
// * 
// * @author 20140903
// * 
// */
//@Controller
//public class AuButtonAuthorityAction extends BaseController {
//	//按钮业务类
//	@Autowired
//	private AuButtonService auButtonService;
//	
//	/**
//	 * 按钮页链接
//	 * @param map
//	 * @return
//	 */
//	@RequestMapping("/manager/erp/au/button.do")
//	public String index(Model map) {
//		return "/ftl/manager/au/auButton";
//	}
//
//	/**
//	 * 查询按钮的所有信息
//	 * 
//	 * @param pageParameter
//	 *            分页信息
//	 * @return 人员集合(json格式)
//	 * @version 1.0
//	 */
//	@RequestMapping(value = "/manager/erp/au/findAuButtonDatagrid.do", method = RequestMethod.POST)
//	@ResponseBody
//	public DataGrid findDatagrid(HttpServletRequest request,
//			@ModelAttribute("pp") PageParameter pageParameter) {
//		return auButtonService.findList(pageParameter);
//	}
//
//	/**
//	 * 添加按钮
//	 * 
//	 * @param auButton
//	 *            按钮实体
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/manager/erp/au/addAuButton.do", method = RequestMethod.POST)
//	@ResponseBody
//	public Boolean addAuButton(AuButton auButton, HttpServletRequest request) {
//		return auButtonService.addAuButton(auButton, getLoginUser(request));
//	}
//	
//	/**
//	 * 更新按钮
//	 * @param auButton
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/manager/erp/au/updateAuButton.do")
//	@ResponseBody
//	public Boolean updateAuButton(AuButton auButton, HttpServletRequest request) {
//		return auButtonService.modifyAuButton(auButton,getLoginUser(request));
//	}
//	
//	/**
//	 * 删除按钮（一个、多个）
//	 * @param ids
//	 * @return
//	 */
//	@RequestMapping("/manager/erp/au/deleteAuButtons.do")
//	@ResponseBody
//	public Boolean deleteAuButtons(String ids) {
//		return this.auButtonService.removeAuButtons(ids);
//	}
//
//	/**
//	 * 获取一个按钮
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping("/manager/erp/au/getAubutton.do")
//	@ResponseBody
//	public AuButton getAubutton(String id) {
//		return (AuButton) auButtonService.getOne(id, AuButton.class);
//	}
//}