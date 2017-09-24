package com.mxcx.erp.au.controller;

import java.util.Arrays;
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
import com.mxcx.erp.au.dao.entity.AuMenu;
import com.mxcx.erp.au.service.AuAuthorityService;
import com.mxcx.erp.au.service.AuMenuService;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 菜单管理控制层
 * @author 王森20140905
 *
 */
@Controller
public class AuMenuAction extends BaseController{
	
	private final static Logger log = Logger.getLogger(AuMenuAction.class);
	@Autowired
	private AuMenuService auMenuService;
	
	@Autowired
	private AuAuthorityService auAuthorityService;
	
	/**
	 * 进入组管理
	 * @param map
	 * @return
	 */
	@RequestMapping("/manager/erp/au/auMenu.do")
	public String index(Model map) {
		return "/ftl/manager/au/auMenu";
	}
	
	/**
	 * 添加新信息
	 * @param auMenu 
	 *          新菜单的基本信息
	 * @param   
	 * @return 对应操作成功或失败的布尔值
	 */
	@RequestMapping(value = "/manager/erp/au/addAuMenu.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addAuMenu(AuMenu auMenu,HttpServletRequest request){
		return auMenuService.addAuMenu(auMenu, this.getLoginUser(request));
		
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/manager/erp/au/deleteAuMenu.do")
	@ResponseBody
	public Boolean deleteAuMenu(String id, HttpServletRequest request)
	{
		
		return auMenuService.deleteAuMenu(id, this.getLoginUser(request));
		
	}
	
	/**
	 * 
	 * @param auMenu 修改的菜单信息
	 * @param 
	 * @return  对应操作成功或失败的布尔值
	 */
	@RequestMapping("/manager/erp/au/modifyAuMenu.do")
	@ResponseBody
	public Boolean modifyAuMenu(AuMenu auMenu,HttpServletRequest request)
	{
		return auMenuService.modifyAuMenu(auMenu, this.getLoginUser(request));
	}
	
	/**
	 * 获取一个菜单
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/manager/erp/au/getAuMenu.do")
	@ResponseBody
	public AuMenu getById(String id) {
		AuMenu AuMenu= (AuMenu) auMenuService.getOne(id, AuMenu.class);
		return AuMenu;
	}
	
	/**
	 * 批量删除功能
	 * 
	 * @param ids待删除功能id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/deleteAuMenuBinch.do")
	@ResponseBody
	public Boolean removeBinch(String ids, HttpServletRequest request) {
		Boolean flag = true;
		try {
			String deptIds[] = ids != null ? ids.split(",") : null;
			for (String string : deptIds) {
				auMenuService.deleteAuMenu(string, this.getLoginUser(request));
			}
		} catch (Exception e) {
			log.error("错误。。", e);
			flag = false;
		}
		return flag;
	}
	
/**
 * 
 * @param pageParameter
 * @param 
 * @return 对应操作成功或失败的布尔值
 */
	@RequestMapping(value = "/manager/erp/au/findAuMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter){
				return auMenuService.findList(pageParameter);
		
	}
	
	@RequestMapping(value = "/manager/erp/au/bindMenu.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean bindSyLineStation(String menuid,String selectionIds,String rowsIds,HttpServletRequest request) {
		try {
			AuMenu  auMenu = (AuMenu) this.auMenuService.getOne(menuid, AuMenu.class); 
			
			String[] selectionIdsArray = selectionIds != null ? selectionIds.split(",") : null;
			String[] rowIdsArray = rowsIds != null ? rowsIds.split(",") : null;
			
			List<String> selectionList = Arrays.asList(selectionIdsArray);
			
			List<String> rowList = Arrays.asList(rowIdsArray);
			//站台id
			for(String rowid : rowList){
				AuAuthority aa= (AuAuthority) auAuthorityService.getOne(rowid, AuAuthority.class);
				aa.setAuMenu(null);
				auAuthorityService.modifyAuAuthority(aa, this.getLoginUser(request));
			}
			
			for(String selid : selectionList){
				AuAuthority aa= (AuAuthority) auAuthorityService.getOne(selid, AuAuthority.class);
				aa.setAuMenu(auMenu);
				auAuthorityService.modifyAuAuthority(aa, this.getLoginUser(request));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 通过功能获取所有按钮
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/manager/erp/au/getAuMenuList.do",method = RequestMethod.POST)
	@ResponseBody
	public List<AuMenu> getAubuttonByAuthority() {
		return auMenuService.getAuMenuList();
	}
	
}
