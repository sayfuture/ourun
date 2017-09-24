package com.mxcx.erp.au.adaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuEmployeeAuthority;

public class AuHQL {
	/**
	 * 根据人员和功能查询按钮名称集合
	 * @param auEmployee人员
	 * @param functionType功能
	 * @return按钮名称集合
	 */
	public static List<String> getHQL(AuEmployee auEmployee,List<AuAuthorityPosition> a ,String functionType){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> buttons = new ArrayList<String>(0);
		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
			map.put(employeeAuthority.getAuAuthority().getEname(), new ArrayList<String>(0));
		}
		for (AuAuthorityPosition auAuthorityPosition : a) {
			map.put(auAuthorityPosition.getAuAuthority().getEname(), new ArrayList<String>(0));
		}
		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
			for(String key:map.keySet()){
				if(employeeAuthority.getAuAuthority().getEname().equals(key)){
					if(null != employeeAuthority.getAuButton() && employeeAuthority.getAuButton().getState() != 0){
						map.get(key).add(employeeAuthority.getAuButton().getFunName());
					}
				}
			}
		}
		for (AuAuthorityPosition auAuthorityPosition : a) {
			for(String key:map.keySet()){
				if(auAuthorityPosition.getAuAuthority().getEname().equals(key)){
					if(null != auAuthorityPosition.getAuButton() && auAuthorityPosition.getAuButton().getState() != 0){
						map.get(key).add(auAuthorityPosition.getAuButton().getFunName());
					}
				}
			}
		}
		for (String key : map.keySet()) {
//			System.out.println(key+"   "+functionType);
			if( key.equals(functionType)){
				buttons = map.get(key);
			}
		}
		 
		
		return buttons;
	}
	
	public static Map<String, List<String>> getMap(AuEmployee auEmployee){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
 		if (auEmployee.getAuEmployeeAuthorityList() == null) {
			
		}
		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
			map.put(employeeAuthority.getAuAuthority().getId(), new ArrayList<String>(0));
		}
		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
			for(String key:map.keySet()){
				if(employeeAuthority.getAuAuthority().getId().equals(key)){
					if(null != employeeAuthority.getAuButton()){
						map.get(key).add(employeeAuthority.getAuButton().getId());
					}
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 判断当前按钮是否是用户可以操作的
	 * @param auEmployee登录用户
	 * @param functionType权限
	 * @param buttonId要操作的按钮 
	 * @return是否是用户可以操作的
	 */
//	public static Boolean checkButtons(AuEmployee auEmployee,FunctionType functionType,String buttonFuName){
//		Boolean flag = false;
//		List<String> ids = getHQL(auEmployee, functionType);
//		if(ids.contains(buttonFuName)){
//			flag = true;
//		}
//		return flag;
//	}
	
	public static Integer getType(AuEmployee auEmployee,FunctionType functionType){
		Integer type = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		if (auEmployee.getAuEmployeeAuthorityList() == null) {
			return 0;
		}
//		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
//			//map.put(employeeAuthority.getAuAuthority().getUrl(), employeeAuthority.getType());
//		}
		
		switch (functionType) {
		case auEmployee:
			for (String key : map.keySet()) {
				if (key.indexOf("employee.do")  > -1) {
					type = map.get(key);
				}
			}
			break;
//		case atAct:
//			for (String key : map.keySet()) {
//				if (key.indexOf("acAct")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case atAct_month:
//			for (String key : map.keySet()) {
//				if (key.indexOf("acAct.do?actDateType=month")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case atAct_week:
//			for (String key : map.keySet()) {
//				if (key.indexOf("acAct.do?actDateType=week")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case atAct_year:
//			for (String key : map.keySet()) {
//				if (key.indexOf("acAct.do?actDateType=year")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case etEnterprise:
//			for (String key : map.keySet()) {
//				if (key.indexOf("etEnterprise.do?status=2")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case etEnterpriseStatus0:
//			for (String key : map.keySet()) {
//				if (key.indexOf("etEnterprise.do?status=0")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//	   case etEnterpriseStatus1:
//			for (String key : map.keySet()) {
//				if (key.indexOf("etEnterprise.do?status=1")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case etEnterpriseSearch:
//			for (String key : map.keySet()) {
//				if (key.indexOf("etEnterprise")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case scSchedule:
//			for (String key : map.keySet()) {
//				if (key.indexOf("schedule")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
//		case etContact:
//			for (String key : map.keySet()) {
//				if (key.indexOf("etContact")  > -1) {
//					type = map.get(key);
//				}
//			}
//			break;
		default:
			break;
		}
		
		return type;
	}
	
	public static Integer getTypeByAct(AuEmployee auEmployee,ActDateType actDateType){
		Integer type = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		if (auEmployee.getAuEmployeeAuthorityList() == null) {
			return 0;
		}
//		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
//	//		map.put(employeeAuthority.getAuAuthority().getUrl(), employeeAuthority.getType());
//		}
		
		switch (actDateType) {
		case month:
			for (String key : map.keySet()) {
				if (key.indexOf("acAct.do?actDateType=month")  > -1) {
					type = map.get(key);
				}
			}
			break;
		case week:
			for (String key : map.keySet()) {
				if (key.indexOf("acAct.do?actDateType=week")  > -1) {
					type = map.get(key);
				}
			}
			break;
		case year:
			for (String key : map.keySet()) {
				if (key.indexOf("acAct.do?actDateType=year")  > -1) {
					type = map.get(key);
				}
			}
			break;
		
		default:
			break;
		}
		
		return type;
	}
	
	public static Integer getTypeByStatus(AuEmployee auEmployee,ActDateType actDateType){
		Integer type = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		if (auEmployee.getAuEmployeeAuthorityList() == null) {
			return 0;
		}
//		for (AuEmployeeAuthority employeeAuthority : auEmployee.getAuEmployeeAuthorityList()) {
//	//		map.put(employeeAuthority.getAuAuthority().getUrl(), employeeAuthority.getType());
//		}
		
		switch (actDateType) {
		case month:
			for (String key : map.keySet()) {
				if (key.indexOf("acAct.do?actDateType=month")  > -1) {
					type = map.get(key);
				}
			}
			break;
		case week:
			for (String key : map.keySet()) {
				if (key.indexOf("acAct.do?actDateType=week")  > -1) {
					type = map.get(key);
				}
			}
			break;
		case year:
			for (String key : map.keySet()) {
				if (key.indexOf("acAct.do?actDateType=year")  > -1) {
					type = map.get(key);
				}
			}
			break;
		
		default:
			break;
		}
		
		return type;
	}
}
