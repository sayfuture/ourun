package com.mxcx.erp.me.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.me.dao.entity.MeLevel;
import com.mxcx.erp.me.service.IMeLevelService;

/**
 * 级别管理Action层
 * 
 * @author  201409015
 * 
 */
@Controller
public class MeLevelAction  extends BaseController {
	

	@Autowired
	private IMeLevelService meLevelService;
	
	/**
	 * 进入会员级别管理
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/meLevel.do")
	public String index() {
		return "/ftl/manager/me/meLevel";
	}
	
	/**
	 * 分页查询会员级别信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/findMemberLevelList.do")
	@ResponseBody
	public DataGrid findMemberLevelList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = meLevelService.findMemberLevelList(pageParameter);
		return dataGrid;
	}
	
	/**
	 * 根据会员级别名查询级别信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/findMemberLevelByName.do")
	@ResponseBody
	public DataGrid findLevelByName(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter){
		DataGrid dataGrid = meLevelService.findMemberLevelByName(pageParameter);
		return dataGrid;
	}
	
	/**
	 * 注册会员级别
	 * @param auMember
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/addMemberLevel.do", method = RequestMethod.POST)
	public Boolean addMember(MeLevel meLevel, HttpServletRequest request){
		return meLevelService.addMemberLevel(meLevel, this.getLoginUser(request));
	}
	
	/**
	 * 编辑会员级别
	 * @param auMember
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/modifyMemberLevel.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyMember(MeLevel meLevel, HttpServletRequest request){
		return meLevelService.modifyMemberLevel(meLevel, this.getLoginUser(request));
	}
	
	/**
	 * 删除会员级别
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/deleteMemberLevel.do")
	@ResponseBody
	public Boolean deleteLevel(String ids, HttpServletRequest request){
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String id : deptIds) {
					meLevelService.deleteMemberLevel(id, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 跳转至会员级别修改页面
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/me/goToModifyMemberLevel.do")
	@ResponseBody
	public MeLevel goToModifyLevel(String id){
		MeLevel meLevel = meLevelService.findMemberLevelByID(id);
		return meLevel;
	}
	
}