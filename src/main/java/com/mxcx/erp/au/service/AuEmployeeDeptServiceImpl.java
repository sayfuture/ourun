package com.mxcx.erp.au.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuEmployeeDept;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.UuidDitch;

/**
 * 公司权限关系业务层接口实现
 * 
 * @author  20140626
 * 
 */
@Service
public class AuEmployeeDeptServiceImpl extends BaseService<AuEmployeeDept> implements AuEmployeeDeptService {
	
	@Autowired
	private IBaseDao<AuEmployeeDept> baseDao;  //公司员工权限dao
	@Autowired
	private AuDeptService auDeptService; //公司服务层
	@Autowired
	private AuAuthorityService auAuthorityService; //权限业务层
	@Autowired
	private AuEmployeeService auEmployeeService; //人员业务层
	
	/**
	 * 添加公司权限关系
	 * 
	 * @author 
	 */
	@Override
	public boolean addAuDeptAuthority(String employeeId,String deptId,AuEmployee employee) {
		Boolean flag = true;
		try {
			/*
			 * 给角色和工作组关联赋值
			 */
				String[] ids=deptId.split(",");
				AuEmployee employee1=(AuEmployee) auEmployeeService.getOne(employeeId, AuEmployee.class);

				for(int i=0;i<ids.length;i++){
					AuDept auDept=auDeptService.getOne(ids[i]);
					AuDept parentAuDept=auDeptService.getParentDept(auDept);
					AuEmployeeDept auEmployeeDept=new AuEmployeeDept();
					auEmployeeDept.setId(UuidDitch.getId(LogModule.AUEMPLOYEEAUDEPT.getModuleNo()));
					auEmployeeDept.setAuDept(auDept);
					auEmployeeDept.setSuperAuDept(parentAuDept);
					auEmployeeDept.setAuEmployee(employee1);
					this.baseDao.addOrModify(auEmployeeDept);
				}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	@Override
	public boolean removeAuDeptAuthority(String employeeId){
		Boolean flag=true;
		try{
		AuEmployee employee1=(AuEmployee) auEmployeeService.getOne(employeeId, AuEmployee.class);
		Set<AuEmployeeDept> list=employee1.getAuEmployeeDeptList();
		for(AuEmployeeDept auEmployeeDept:list){
			this.baseDao.remove(auEmployeeDept);
		}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
			
		}
		return flag;
	}
	
}