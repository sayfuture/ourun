package com.mxcx.erp.au.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.au.adaptor.AuHQL;
import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuEmployeeAuthority;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.au.service.AuAuthorityPositionService;
import com.mxcx.erp.au.service.AuEmployeeAuthorityService;
import com.mxcx.erp.au.service.AuEmployeeDeptService;
import com.mxcx.erp.au.service.AuEmployeeService;
import com.mxcx.erp.au.vo.AuEmployeeManagerVo;
import com.mxcx.erp.base.commons.controller.BaseController;

/**
 * 人员管理控制层
 * 
 * @author  20140626
 * 
 */
@Controller
public class AuEmployeeAction extends BaseController {
	
	
	private final static Logger log = Logger.getLogger(AuEmployeeAction.class);
	@Autowired
	private AuEmployeeService auEmployeeService;
	@Autowired
	private AuEmployeeAuthorityService auEmployeeAuthorityService;
	@Autowired
	private AuAuthorityPositionService auAuthorityPositionService;
	@Autowired
	private AuEmployeeDeptService auEmployeeDeptService;
	/**
	 * 通过人员和权限确定按钮id的集合
	 * @param map
	 * @param request
	 * @param functionType权限
	 * @return
	 */
	@RequestMapping(value = "/manager/getEmployeeButtons.do", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getEmployeeButtons(Model map, HttpServletRequest request, @RequestParam String functionType) {
		List<AuAuthorityPosition> aulist = null;
		if(request.getSession().getAttribute("aulist")==null){
			Set positionset = this.getLoginUser(request).getPositionList();
			Iterator<AuPosition> it = positionset.iterator();  
			String _tempstr="";
			while (it.hasNext()) {  
				AuPosition au = it.next();  
				_tempstr=_tempstr+",'"+au.getId()+"'";
			}  
			if(StringCheck.stringCheck(_tempstr)){
				_tempstr = _tempstr.substring(1);	
			}
			aulist = this.auAuthorityPositionService.findAuAuthorityPositionListByPosition(_tempstr);
			request.getSession().setAttribute("aulist", aulist);
		}
		else{
			aulist =(List<AuAuthorityPosition>) request.getSession().getAttribute("aulist");
		}
		return AuHQL.getHQL(this.getLoginUser(request),aulist,functionType);
	}
	
//	@RequestMapping(value = "/manager/getSelectKeyValue.do", method = RequestMethod.POST)
//	@ResponseBody
//	public List<SysDicValue> getKeyValue(HttpServletRequest request, @RequestParam String flag,@RequestParam String onchageid,@RequestParam String code) {
//		
//		List<SysDicValue> list = sysDicValueService.findSysDicValueList(code);
//		if(("3").equals(flag)){
//			SysDicValue sv = new SysDicValue();
//			sv.setId("");
//			sv.setName("全部");
//			list.add(0, sv); 	
//		}
//		else if(("4").equals(flag)){
//			SysDicValue sv = new SysDicValue();
//			sv.setId("");
//			sv.setName("请选择..");
//			list.add(0, sv);
//		}
//		
//		return list;
//	}
//
//	
//	@RequestMapping(value = "/manager/getCheckBoxKeyValue.do", method = RequestMethod.POST)
//	@ResponseBody
//	public List<SysDicValue> getCheckBoxKeyValue(HttpServletRequest request,@RequestParam String onchageid,@RequestParam String code) {
//		System.out.println(onchageid);
//		List<SysDicValue> list = sysDicValueService.findSysDicValueList(code);
//		return list;
//	}
	
	/**
	 * 进入人员管理
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/employee.do")
	public ModelAndView index(Model map, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(
				"/ftl/manager/au/auEmployee");
		return modelAndView;
	}
	
	/**
	 * 查询人员的所有信息
	 * 
	 * @param pageParameter
	 *            分页信息
	 * @return 人员集合(json格式)
	 * @version 1.0
	 */
	@RequestMapping(value = "/manager/erp/au/findEmployeeList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findList(HttpServletRequest request,	@ModelAttribute("pp") PageParameter pageParameter) {
		
		return auEmployeeService.findList(pageParameter, this.getLoginUser(request));
	}

	/**
	 * 用于登录名称注册验证
	 * 
	 * @param request
	 * @param pageParameter
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/findLoginNameList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findLoginNameList(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {

		return auEmployeeService.findLoginNameList(pageParameter);
	}

	/**
	 * 人员搜索功能
	 * 
	 * @param request
	 * @param pageParameter
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/findLoginName.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findLoginName(HttpServletRequest request,
			@ModelAttribute("pp") PageParameter pageParameter) {
		return auEmployeeService.findLoginName(pageParameter, this.getLoginUser(request));
	}

	/**
	 * 添加新人员
	 * 
	 * @param baseAuEmployee
	 *            新人员的基本信息
	 * @param baseid
	 *            上级id
	 * @return 对应操作成功与失败的布尔值
	 * 
	 * @author Administrator 2014-3-7
	 * @version 1.0
	 */
	@RequestMapping(value = "/manager/erp/au/addAuEmployee.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addAuEmployee(AuEmployee auEmployee,@RequestParam String positionId, @RequestParam String deptId, HttpServletRequest request) {
		/*
		 * 给角色和工作组关联赋值
		 */
		Boolean b =  true;
		if(StringCheck.stringCheck(positionId)){
			String arr[] = positionId.split(",");
			Set<AuPosition> positionList = new HashSet<AuPosition>();
			for (int i = 0; i < arr.length; i++) {
				System.out.println(arr[i]);
				AuPosition an= (AuPosition)this.auEmployeeService.getOne(arr[i], AuPosition.class);
				positionList.add(an);
			}
			auEmployee.setPositionList(positionList);
		}
		AuDept am = new AuDept();
		am.setId(deptId);
		auEmployee.setAuDept(am);
		try {
			 b = auEmployeeService.addAuEmployee(auEmployee, this.getLoginUser(request),request);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return b;
		
	}

	/**
	 * 修改人员信息
	 * 
	 * @param baseAuEmployee
	 *            人员修改后的信息
	 * @param baseid
	 *            上级id
	 * @return 对应操作成功与失败的布尔值
	 * 
	 * @author Administrator 2014-3-7
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/updateEmployee.do")
	@ResponseBody
	public Boolean updateEmployee(AuEmployee auEmployee,@RequestParam String positionId, @RequestParam String deptId, HttpServletRequest request) {
		/*
		 * 给角色和工作组关联赋值
		 */
		AuDept am = new AuDept();
		am.setId(deptId);
		auEmployee.setAuDept(am);
		return auEmployeeService.modifyAuEmployee(auEmployee,positionId, this.getLoginUser(request), request);
	}

	/**
	 * 根据id删除人员记录
	 * 
	 * @param id
	 *            人员id
	 * @return 对应操作成功与失败的布尔值
	 * 
	 * @author Administrator 2014-3-7
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/deleteEmployee.do")
	@ResponseBody
	public Boolean deleteEmployee(String id, HttpServletRequest request) {
		return this.auEmployeeService.deleteAuEmployee(id, this.getLoginUser(request)); 
				
	}

	/**
	 * 批量删除人员
	 * 
	 * @param ids待删除人员id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/deleteAuEmployeeBinch.do")
	@ResponseBody
	public Boolean deleteAuEmployeeBinch(String ids, HttpServletRequest request) {
		String deptIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != deptIds) {
				for (String string : deptIds) {
					AuEmployee auDept = (AuEmployee) auEmployeeService.getOne(string, AuEmployee.class);
					return this.auEmployeeService.deleteAuEmployee(auDept.getId(), this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			log.error("登陆错误。。", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 初始化密码
	 * 
	 * @param ids待删除人员id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/initPassword.do")
	@ResponseBody
	public Boolean initPassword(String id, HttpServletRequest request) {
		return auEmployeeService.initPassword(id, this.getLoginUser(request));
	}

	/**
	 * 根据id查询人员信息
	 * 
	 * @param id
	 *            人员ID
	 * @return 人员对象json
	 * 
	 * @author Administrator 2014-3-7
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/getEmployee.do")
	@ResponseBody
	public AuEmployee getEmployee(String id) {
		AuEmployee auEmployee = (AuEmployee) auEmployeeService.getOne(id,AuEmployee.class);
		return auEmployee;
	}
	
	/**
	 * 根据id查询人员信息
	 * 
	 * @param id
	 *            人员ID
	 * @return 人员对象json
	 * 
	 * @author Administrator 2014-3-7
	 * @version 1.0
	 */
	@RequestMapping(value="/manager/erp/au/getEmployeeName.do",method = RequestMethod.POST)
	@ResponseBody
	public String getEmployeeName(String id) {
		AuEmployee au = (AuEmployee) auEmployeeService.getOne(id,AuEmployee.class);
		if(null!=au){
			return au.getRealName();
		}
		return "";
	}
	
	
	/**
	 * 根据真实名查询
	 * @param realName
	 * @return
	 */
	@RequestMapping("/manager/erp/au/findAuEmployeeByRealName.do")
	@ResponseBody
	public AuEmployee findAuEmployeeByRealName(String empid){
		return auEmployeeService.findAuEmployeeById(empid);
	}
	/**
	 * 设置人员权限
	 * 
	 * @param employeeId人员Id
	 * @param id权限Id数组
	 * @return
	 */
	@RequestMapping("/manager/erp/au/setEmployeeAuthority.do")
	@ResponseBody
	public Boolean setEmployeeAuthority(HttpServletRequest request,
			String employeeId, String[] id, String ids) {
		Boolean flag = true;
		try {
			List<AuEmployeeAuthority> auEmployeeAuthoritieList = this.auEmployeeAuthorityService
					.findAuEmployeeAuthorityListByAuEmployee(employeeId); // 数据库中已存在的人员权限关系
			String[] rowIds = ids != null ? ids.split(",") : null;
			for (AuEmployeeAuthority auEmployeeAuthority : auEmployeeAuthoritieList) {
				for (int i = 0; i < rowIds.length; i++) {
					if (auEmployeeAuthority.getAuAuthority().getId()
							.equals(rowIds[i])) {
						this.auEmployeeAuthorityService.removeOne(
								auEmployeeAuthority.getId(),
								AuEmployeeAuthority.class);
					}
				}
			}
			if (null != id && id.length>0) {
				for (String string : id) {
					String[] i = request.getParameterValues(string);
					this.auEmployeeAuthorityService.addAuEmployeeAuthority(
							employeeId, string, i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	/**
	 * 初始化密码
	 * 
	 * @param ids待删除人员id集合
	 * @return 是否操作成功
	 */
	@RequestMapping("/manager/erp/au/updatePassword.do")
	@ResponseBody
	public Boolean updatePassword(String id, String password1, String password2, HttpServletRequest request) {
		return auEmployeeService.updatePassword(id, password1, password2,this.getLoginUser(request));
	}

	/**
	 * 通过组获取组人员
	 * 
	 * @param ids
	 *            组id集合
	 * @return
	 */
	@RequestMapping("/manager/erp/au/getEmployeeListByDept.do")
	@ResponseBody
	public DataGrid getEmployeeListByDept(HttpServletRequest request,
			String ids, @ModelAttribute("pp") PageParameter pageParameter) {
		if (null != ids && !"".equals(ids)) {
			return this.auEmployeeService.findAuEmployeeListByDeptForES(
					pageParameter, ids);
		}
		return null;
	}

	/**
	 * 获取当前用户对当前功能的type
	 */
	@RequestMapping("/manager/erp/au/checkType.do")
	@ResponseBody
	public Integer checkType(HttpServletRequest request) {
		AuEmployee auEmployee = (AuEmployee) request.getSession().getAttribute("auEmployee");
		Integer type = this.auEmployeeService.checkType(auEmployee);
		return type;
	}

	/**
	 * 手机修改人员时2个平台同步的方法
	 * 
	 * @param employeeManagerVo
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/managerAuEmployeeByMobile.do", method = RequestMethod.POST)
	@ResponseBody
	public AuEmployeeManagerVo managerAuEmployeeByMobile(
			AuEmployeeManagerVo employeeManagerVo) {
		try {
			AuEmployee auEmployee = (AuEmployee) auEmployeeService
					.getOne(employeeManagerVo.getAuEmployee().getId(),
							AuEmployee.class);

			auEmployee.setAge(employeeManagerVo.getAuEmployee().getAge());
			auEmployee.setEmail(employeeManagerVo.getAuEmployee().getEmail());
			auEmployee.setTel(employeeManagerVo.getAuEmployee().getTel());
			auEmployee.setTel2(employeeManagerVo.getAuEmployee().getTel2());
			auEmployee.setRealName(employeeManagerVo.getAuEmployee()
					.getRealName());
			auEmployee.setSex(employeeManagerVo.getAuEmployee().getSex());

			auEmployeeService.modify(auEmployee);
			employeeManagerVo.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
			employeeManagerVo.setFlag(false);
		}
		return employeeManagerVo;
	}

	/**
	 * 根据选择的销售组查询组内的人员，返回组内人员id拼串
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/manager/erp/au/getAllEmployeeList.do", method = RequestMethod.POST)
	@ResponseBody
	public String getAllEmployeeList(String ids) {
		List<AuEmployee> list = this.auEmployeeService
				.findAuEmployeeListByDepts(ids);
		String idsForEmployee = null;
		if (null != list && list.size() > 0) {
			for (AuEmployee e : list) {
				if (null == idsForEmployee) {
					idsForEmployee = e.getId();
				} else {
					idsForEmployee = idsForEmployee + "," + e.getId();
				}
			}
		}
		return idsForEmployee;
	}
	
	@RequestMapping(value = "/manager/erp/au/findEmpCompanyList.do", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid findEmpCompanyList(HttpServletRequest request,@ModelAttribute("pp") PageParameter pageParameter) {
		return auEmployeeService.findEmpCompanyList(pageParameter,this.getLoginUser(request));
	}
	
	
	/**
	 * 分公司绑定
	 * @version 1.0
	 */
	@RequestMapping("/manager/erp/au/savebindCompany.do")
	@ResponseBody
	public Boolean savebindCompany(@RequestParam String employeeId,@RequestParam String deptId, HttpServletRequest request) {
		Boolean flag=auEmployeeDeptService.removeAuDeptAuthority(employeeId);
		if(flag) 	
		flag=auEmployeeDeptService.addAuDeptAuthority(employeeId,deptId, this.getLoginUser(request));
		 	
		return flag;
	}

}