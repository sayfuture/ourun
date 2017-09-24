package com.mxcx.erp.me.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.me.dao.entity.MeLevel;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.me.service.IMeLevelService;
import com.mxcx.erp.me.service.IMeMemberService;

/**
 * 会员管理
 * 
 * @author  20140909
 * 
 */
@Controller
public class MeMemberAction  extends BaseController {
	

	@Autowired
	private IMeMemberService memberService;
	@Autowired
	private IMeLevelService meLevelService;
	
	/**
	 * 进入会员管理
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/meMember.do")
	public ModelAndView index() {
		List<MeLevel> levelList = meLevelService.findMemberLevelList();
		return new ModelAndView("/ftl/manager/me/meMember","levelList", levelList);
	}
	/**
	 * 进入扫码发送
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/meMemberScanSend.do")
	public ModelAndView meMemberScanSend(Model map, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(
				"/ftl/manager/di/diScanSend");
		return modelAndView;
	}
	
	/**
	 * 绑定积客券信息
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/bindCard.do")
	@ResponseBody
	public Boolean bindCard(MeMember member){
		  return memberService.bindCard(member);
	}
	
	/**
	 * 分页查询会员信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/findMemberList.do")
	@ResponseBody
	
	public DataGrid findMemberList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = memberService.findMemberList(pageParameter);
		
		return dataGrid;
	}
	
	/**
	 * 根据组合条件查询指定会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/findMemberByConditionGroup.do")
	@ResponseBody
	public DataGrid findMemberByConditionGroup(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = memberService.findMemberByConditionGroup(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}
	
	/**
	 * 编辑会员
	 * @param auMember
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/modifyMember.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyMember(MeMember member, HttpServletRequest request){
		return memberService.modifyMember(member, this.getLoginUser(request));
	}
	
	/**
	 * 新增店员
	 * @param auMember
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/addMember.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addMember(MeMember member, HttpServletRequest request){
		return memberService.addMember(member, this.getLoginUser(request));
	}
	/**
	 * 删除会员
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/deleteMember.do")
	@ResponseBody
	public Boolean deleteMember(String ids, HttpServletRequest request){
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String id : deptIds) {
					memberService.deleteMember(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 跳转至会员注册页面
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/goToRegisterMember.do")
	public String goToRegisterMember(){
		return "/ftl/manager/au/auMember";
	}
	
	/**
	 * 跳转至会员修改页面
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/goToModifyMember.do")
	@ResponseBody
	public MeMember goToModifyMember(String id){
		MeMember auMember = memberService.findMemerByID(id);
		return auMember;
	}
	/**
	 * 根据真实名查看会员详情
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/findMemberByRealName.do")
	@ResponseBody
	public MeMember findMemberByRealName(String realName,String nickName){
		return  memberService.findMemberByRealName(realName,nickName);
	}
	
	/**
	 * 初始化密码
	 * 
	 * @param ids待删除会员id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/me/initMemberPassword.do")
	@ResponseBody
	public Boolean initMemberPassword(String id, HttpServletRequest request) {
		return memberService.initPassword(id, this.getLoginUser(request));
	}
	
	/**
	 * 初始化积分
	 * 
	 * @param ids待初始化会员id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/me/initMemberScore.do")
	@ResponseBody
	public Boolean initMemberScore(String id, HttpServletRequest request) {
		return memberService.initScore(id, this.getLoginUser(request));
	}
	
	/**
	 * 启用/冻结会员
	 * 
	 * @param ids 待启用/冻结会员id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/me/setMemberAvailability.do")
	@ResponseBody
	public Boolean setMemberAvailability(String ids, HttpServletRequest request) {
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String id : deptIds) {
					memberService.validateMemer(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
}