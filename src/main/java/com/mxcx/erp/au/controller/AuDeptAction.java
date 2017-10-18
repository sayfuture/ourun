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

import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.TreeAuDeptVo;
import com.mxcx.erp.au.service.AuDeptService;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.ec.base.commons.util.DataGrid;

/**
 * 工作组控制层
 * @author  20140625
 * 
 */
@Controller
public class AuDeptAction extends BaseController {
	private final static Logger log = Logger.getLogger(AuDeptAction.class);
	@Autowired
	private AuDeptService auDeptService; // 组业务层接口
	
//	@Autowired
//	private AuDeptAuthorityService auDeptAuthorityService; // 组权限业务层接口
	
	/**
	 * 进入组管理
	 * @param map
	 * @return
	 */
	@RequestMapping("/manager/erp/au/auDept.do")
	public String index(Model map) {
		return "/ftl/manager/au/auDept";
	}

	/**
	 * 查询组的所有信息
	 * 
	 * @param pageParameter 分页信息
	 * @return 组集合(json格式)
	 * @version 1.0
	 */
	@RequestMapping(value = "/manager/erp/au/findAuDeptList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findList(HttpServletRequest request, @ModelAttribute("pp") PageParameter pageParameter) {
		return auDeptService.findList(pageParameter);
	}

	/**
	 * 添加组
	 * 
	 * @param auDept 新组的基本信息
	 * @return 对应操作成功与失败的布尔值
	 *
	 * @version 1.0
			*/
	@RequestMapping(value = "/manager/erp/au/addAuDept.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addAuDept(AuDept auDept, HttpServletRequest request) {
		return auDeptService.addAuDept(auDept, this.getLoginUser(request));
	}
	/**

	 * 修改组信息
	 *
	 * @param auDept 组修改后的信息
	 * @return 对应操作成功与失败的布尔值
	 *
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/updateAuDept.do")
	@ResponseBody
	public Boolean modify(AuDept auDept, HttpServletRequest request) {
		return auDeptService.modifyAuDept(auDept, this.getLoginUser(request));
	}

	/**
	 * 根据id删除组记录
	 * 
	 * @param id
	 *            组id
	 * @return 对应操作成功与失败的布尔值
	 * 
	 * @author Administrator 2014-3-7
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/deleteAuDept.do")
	@ResponseBody
	public Boolean remove(String id, HttpServletRequest request) {
		return auDeptService.deleteAuDept(id, this.getLoginUser(request));
	}

	/**
	 * 批量删除组
	 * 
	 * @param ids 待删除组id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/deleteAuDeptBinch.do")
	@ResponseBody
	public Boolean removeBinch(String ids, HttpServletRequest request) {
		Boolean flag = true;
		try {
			String deptIds[] = ids != null ? ids.split(",") : null;
			for (String string : deptIds) {
				auDeptService.deleteAuDept(string, this.getLoginUser(request));
			}
		} catch (Exception e) {
			log.error("错误。。", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 根据id查询组信息
	 * 
	 * @param id 组ID
	 * @return 组对象json
	 * 
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/getAuDept.do")
	@ResponseBody
	public AuDept getById(String id) {
		AuDept AuDept = (AuDept) auDeptService.getOne(id, AuDept.class);
		return AuDept;
	}

	/**
	 * 获取工作组集合,其中动态添加请选择
	 * 
	 * @return组集合
	 */
	@RequestMapping("/manager/erp/au/getAuDeptList.do")
	@ResponseBody
	public List<TreeAuDeptVo> getAuDeptList(String id) {
		List<TreeAuDeptVo> deptList = this.auDeptService.findTree(id);
		return deptList;
	}
	@RequestMapping("/manager/erp/au/getAuDeptList1.do")
	@ResponseBody
	public List<TreeAuDeptVo> getAuDeptList1(String id) {
		List<TreeAuDeptVo> deptList = this.auDeptService.findTree(id);
		TreeAuDeptVo au = new TreeAuDeptVo();
		au.setId("");
		au.setName("全部");
		deptList.add(0, au);
		return deptList;
	}
	/**
	 * 获取所有组的集合（带全部选项）
	 * @return组集合
	 */
	@RequestMapping("/manager/erp/au/getAuDeptListByEmp.do")
	@ResponseBody
	public List<AuDept> getAuDeptListByEmp() {
		List<AuDept> auDeptList = this.auDeptService.findList();
		AuDept au = new AuDept();
		au.setId("0");
		au.setName("全部");
		auDeptList.add(0, au);
		return auDeptList;
	}
	
	/**
	 * 保存所有组的权限
	 * @return组集合
	 */
//	@RequestMapping("/manager/erp/au/setAuDeptAuthority.do")
//	@ResponseBody
//	public Boolean setAuDeptAuthority(HttpServletRequest request,
//			String deptId, String[] id, String ids) {
//		Boolean flag = true;
//		try {
//			List<AuDeptAuthority> auDeptAuthoritieList = this.auDeptAuthorityService
//					.findAuDeptAuthorityListByAuDept(deptId); // 数据库中已存在的人员权限关系
//			String[] rowIds = ids != null ? ids.split(",") : null;
//			for (AuDeptAuthority auDeptAuthority : auDeptAuthoritieList) {
//				for (int i = 0; i < rowIds.length; i++) {
//					if (auDeptAuthority.getAuAuthority().getId()
//							.equals(rowIds[i])) {
//						this.auDeptAuthorityService.removeOne(
//								auDeptAuthority.getId(),
//								AuDeptAuthority.class);
//					}
//				}
//			}
//			if (null != id && id.length>0) {
//				for (String string : id) {
//					String[] i = request.getParameterValues(string);
//					this.auDeptAuthorityService.addAuDeptAuthority(
//							deptId, string, i);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			flag = false;
//		}
//
//		return flag;
//	}
	
}