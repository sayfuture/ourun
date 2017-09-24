package com.mxcx.erp.au.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.au.adaptor.AuHQL;
import com.mxcx.erp.au.adaptor.FunctionType;
import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.au.service.AuAuthorityPositionService;
import com.mxcx.erp.au.service.AuEmployeeService;
import com.mxcx.erp.au.vo.UserLoginVo;
import com.mxcx.erp.base.commons.controller.BaseController;

/**
 * 登录、退出功能控制层
 * 
 * 
 */
@Controller
public class LoginAction extends BaseController{
	
	private final static Logger log = Logger.getLogger(LoginAction.class);
	@Autowired
	private AuEmployeeService auEmployeeService;
	
	@Autowired
	private AuAuthorityPositionService auAuthorityPositionService;
	
//	@Autowired
//	private StudentTestService studentService;
	/**
	 * 查询人员的所有信息
	 * 
	 * @param pageParameter
	 *            分页信息
	 * @return 人员集合(json格式)
	 * @version 1.0
	 */
	@RequestMapping(value = "/manager/loginAction.do", method = RequestMethod.POST)
	@ResponseBody
	public int loginAction(Model map, HttpServletRequest request,
			@RequestParam String name, @RequestParam String password,
			@RequestParam String code) {
//		StudentTest studentTest=new StudentTest();
//		studentService.add(studentTest);
//		studentService.get("");
		int flag = 0;
		Map<String,List<AuAuthority>> map3 = new HashMap<String,List<AuAuthority>>();
		String kaptchaCode = (String) request.getSession().getAttribute("SESSION_RANDOM_CODE_KEY");
//		if (code.equalsIgnoreCase(kaptchaCode)) {
			try {
				flag = this.auEmployeeService.loginCheck(name, password);
				AuEmployee auEmployee = this.auEmployeeService.login(name,password);
				Set positionset = auEmployee.getPositionList();
				Iterator<AuPosition> it = positionset.iterator();  
				String _tempstr="";
				while (it.hasNext()) {  
					AuPosition au = it.next();  
					_tempstr=_tempstr+",'"+au.getId()+"'";
				}  
				if(StringCheck.stringCheck(_tempstr)){
					_tempstr = _tempstr.substring(1);	
				}
				
				List<AuAuthorityPosition> authorityPositionList = this.auAuthorityPositionService.findAuAuthorityPositionListByPosition(_tempstr);
				HttpSession session = request.getSession();
				
				session.setAttribute("auEmployee", auEmployee);
				if(null != auEmployee.getAuEmployeeAuthorityList()){
					map3 = this.auEmployeeService.auMenuAuthority(auEmployee,authorityPositionList);
					session.setAttribute("map3", map3);
					session.setAttribute("auEmployeeAuthorityList", auEmployee.getAuEmployeeAuthorityList());
				}
			} catch (Exception e) {
				log.error("登陆错误。。", e);
				return 3;
				
			}
		return flag;
	}

	/**
	 * 获取当前登录用户的FunctionType的type类型
	 * @param map
	 * @param request
	 * @param functionType
	 * @return
	 */
	@RequestMapping(value = "/manager/getEmployeeAuthority.do", method = RequestMethod.GET)
	@ResponseBody
	public int loginAction(Model map, HttpServletRequest request,@RequestParam FunctionType functionType) {
		return AuHQL.getType((AuEmployee) request.getSession().getAttribute("auEmployee"), functionType);
	}
	
	/**
	 * 登录成功后进入系统
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/manager/index.do")
	public String index(Model map) {
		return "/jsp/manager/commons/main";
	}

	/**
	 * 手机登录
	 * 
	 * @param map
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/manager/androidLogin.do", method = RequestMethod.GET)
	@ResponseBody
	public UserLoginVo androidLogin(Model map, HttpServletRequest request,
			@RequestParam String username, @RequestParam String password) {

		UserLoginVo userLoginVo = new UserLoginVo();
		userLoginVo.setFlag(false);
		try {
			AuEmployee auEmployee = this.auEmployeeService.loginToM(username,password);
			if (auEmployee != null) {
				userLoginVo.setFlag(true);
				userLoginVo.setAuEmployee(auEmployee);
			}
		} catch (Exception e) {
			log.error("错误。。", e);
		}
		return userLoginVo;
	}

	/**
	 * 注销退出
	 * 
	 * @param map
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/manager/logout.do", method = RequestMethod.GET)
	public String logout(Model map, HttpServletRequest request) {
			request.getSession().removeAttribute("auEmployee");
			request.getSession().removeAttribute("auEmployeeAuthorityList");
			request.getSession().invalidate();
			return "/jsp/login";
	}
}