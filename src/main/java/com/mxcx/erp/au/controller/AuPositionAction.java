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

import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.au.service.AuAuthorityPositionService;
import com.mxcx.erp.au.service.AuPositionService;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 角色控制层
 * 
 * @author Administrator
 * 
 */
@Controller
public class AuPositionAction extends BaseController {
	private final static Logger log = Logger.getLogger(AuPositionAction.class);
	@Autowired
	private AuPositionService auPositionService; // 角色业务层接口
	@Autowired
	private AuAuthorityPositionService auAuthorityPositionService; // 角色角色关系业务层

	/**
	 * 进入角色管理
	 * @param map
	 * @return角色页面
	 */
	@RequestMapping("/manager/erp/au/position.do")
	public String positionIndex(Model map) {

		return "/ftl/manager/au/auPosition";
	}

	/**
	 * 添加角色	
	 * 
	 * @param auPosition新角色的基本信息
	 * @return 对应操作成功与失败的布尔值
	 * 
	 */
	@RequestMapping(value = "/manager/erp/au/addAuPosition.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addAuPosition(AuPosition auPosition, HttpServletRequest request) {
		return auPositionService.addAuPosition(auPosition, this.getLoginUser(request));
	}

	/**
	 * 修改设定权限
	 * 
	 * @param positionId角色ID
	 * @param id权限ID数组
	 * @param ids每页10调数据ID的字符串
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/addAuPositionAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addAuPositionAuthority(HttpServletRequest request, String positionId, String[] id,
			String ids) {
		Boolean flag = true;
		try {
			List<AuAuthorityPosition> auAhorityPositionList = this.auAuthorityPositionService.findAuAuthorityPositionListByPosition("'"+positionId+"'"); // 角色关联的所有权限
			String rowIds[] = ids != null ? ids.split(",") : null;
			for (AuAuthorityPosition auAhorityPosition : auAhorityPositionList) {
				for (int i = 0; i < rowIds.length; i++) {
					if (auAhorityPosition.getAuAuthority().getId().equals(rowIds[i])) {
						this.auAuthorityPositionService.removeOne(auAhorityPosition.getId(), AuAuthorityPosition.class); // 删除关联表
					}
				}
			}
			if(null != id && id.length>0){
				for (String string : id) {
					String[] i = request.getParameterValues(string);
					this.auAuthorityPositionService.addAuAuthorityPosition(positionId, string, i); // 向关联表添加数据
				}
			}
		} catch (Exception e) {
			log.error("错误。。", e);
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据id删除角色记录
	 * 
	 * @param id角色id
	 * @return 对应操作成功与失败的布尔值
	 * 
	 */
	@RequestMapping("/manager/erp/au/deleteAuPosition.do")
	@ResponseBody
	public Boolean remove(HttpServletRequest request, String id) {
		return auPositionService.deleteAuPosition(id, this.getLoginUser(request));
	}

	/**
	 * 批量删除角色
	 * 
	 * @param ids待删除角色id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/deleteAuPositionBinch.do")
	@ResponseBody
	public Boolean deleteAuPositionBinch(HttpServletRequest request, String ids) {
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String string : deptIds) {
					auPositionService.deleteAuPosition(string, this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			log.error("错误。。", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 修改角色信息
	 * 
	 * @param auPosition角色修改后的信息
	 * @return 对应操作成功与失败的布尔值
	 * 
	 */
	@RequestMapping("/manager/erp/au/updateAuPosition.do")
	@ResponseBody
	public Boolean updateAuPosition(AuPosition auPosition, HttpServletRequest request) {
		return auPositionService.modifyAuPosition(auPosition, this.getLoginUser(request));
	}

	/**
	 * 根据id查询角色信息
	 * 
	 * @param id角色ID
	 * @return 角色对象json
	 * 
	 */
	@RequestMapping("/manager/erp/au/getAuPosition.do")
	@ResponseBody
	public AuPosition getAuPosition(String id) {
		AuPosition auPosition = (AuPosition) auPositionService.getOne(id, AuPosition.class);
		return auPosition;
	}

	/**
	 * 查询角色的所有信息
	 * 
	 * @param pageParameter分页信息
	 * @return 角色集合(json格式)
	 */
	@RequestMapping(value = "/manager/erp/au/findAuPositionList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findAuPositionList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dd = auPositionService.findList(pageParameter);
		return dd;
	}

	/**
	 * 获取所有角色的集合
	 * @return角色集合
	 */
	@RequestMapping(value = "/manager/erp/au/getAuPositionList.do", method = RequestMethod.POST)
	@ResponseBody
	public List<AuPosition> getAuPositionList() {
		List<AuPosition> auPositionList = this.auPositionService.findAuPositionList();
		return auPositionList;
	}

	/**
	 * 获取所有角色的集合（带全部选项）
	 * @return角色集合
	 */
	@RequestMapping(value = "/manager/erp/au/getAuPositionListByEmp.do", method = RequestMethod.POST)
	@ResponseBody
	public List<AuPosition> getAuPositionListByEmp() {
		List<AuPosition> auPositionList = this.auPositionService.findAuPositionList();
		AuPosition an = new AuPosition();
		an.setId("");
		an.setName("全部");
		auPositionList.add(0, an);
		return auPositionList;
	}
	
	/**
	 * 通过角色查询已经选中的权限用于回显  zzq
	 * 
	 * @param positionId角色Id
	 * @return 集合
	 */
	@RequestMapping(value="/manager/erp/au/getButtonByPositionAndAuthority.do", method = RequestMethod.POST)
	@ResponseBody
	public List<AuAuthorityPosition> getButtonByEmployeeAndAuthority(String positionId,String authorityId) {
		return this.auAuthorityPositionService.getButtonByPositionAndAuthority(positionId,authorityId);
	}
}