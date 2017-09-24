package com.mxcx.erp.au.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.TreeAuDeptVo;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 工作组业务层接口实现
 * 
 * @author  20140625
 * 
 */
@Service
public class AuDeptServiceImpl extends BaseService<AuDept> implements
		AuDeptService {
	@Autowired
	private IBaseDao<AuDept> baseDao; // 组管理持久层
	@Autowired
	private LogManagementService logManagementService; // 日志管理业务层接口

	/**
	 * 添加工作组
	 * 
	 * @param AuDept工作组对象
	 * @return是否成功
	 */
	@Override
	public Boolean addAuDept(AuDept auDept, AuEmployee employee) {
		Boolean flag = true;
		try {
			if(auDept.getLevel()==0){
				auDept.setSuperAuDept(null);
			}
			auDept.setId(UuidDitch.getId(LogModule.AUTEAM.getModuleNo()));
			auDept.setState(1);
			this.addPo(auDept, employee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUTEAM.toString(), LogFunction.AUTEAM_CREATE.toString(),auDept.toString());
		}
		return flag;
	}
	
	/**
	 * 删除工作组
	 * 
	 * @param id组Id
	 * @param employee登录用户
	 * @return是否成功
	 */
	public Boolean deleteAuDept(String id, AuEmployee employee) {
		AuDept auDept = null;
		Boolean flag = true;
		try{
			auDept = (AuDept)this.baseDao.getById(AuDept.class, id);
			this.removeByState(auDept);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUTEAM.toString(), LogFunction.AUTEAM_DELETE.toString(),auDept.toString());
		}
		return flag;
	}

	/**
	 * 修改工作组
	 * 
	 * @param AuDept工作组对象
	 * @param employee登录用户
	 * @return是否成功
	 */
	@Override
	public Boolean modifyAuDept(AuDept auDept, AuEmployee employee) {
		Boolean flag = true;
		AuDept auDeptTemp= null;
		try {
		    auDeptTemp = (AuDept) this.baseDao.getById(AuDept.class, auDept.getId());
			auDeptTemp.setName(auDept.getName());
			auDeptTemp.setSeqno(auDept.getSeqno());
			auDeptTemp.setNotes(auDept.getNotes());
			auDeptTemp.setCityId(auDept.getCityId());
			this.modify(auDeptTemp, employee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUTEAM.toString(), LogFunction.AUTEAM_UPDATE.toString(),auDeptTemp.toString());
		}
		return flag;
	}

	/**
	 * 查询所有工作组（分页）
	 * 
	 * @param pageParameter本页数据信息
	 * @return组分页
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from AuDept x where x.state = 1 ";
		System.out.println(pageParameter.getParaMap().containsKey("name")+"#############");
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null ) {
			hql += "and x.name like:name";
			map.put("name", "%"+ pageParameter.getParaMap().get("name")+"%");
		}
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("level")
				&& pageParameter.getParaMap().get("level") != null ) {
			hql += "and x.level =:level";
			
		
			map.put("level",  Integer.parseInt((String) pageParameter.getParaMap().get("level")));
		}
		
		hql+=" order by x.seqno asc";
		pageParameter.setParaMap(map);
		return this.baseDao.findByhql(hql, pageParameter);
	}
	
	/**
	 * 查询所有工作组（集合）
	 * 
	 * @param pageParameter本页数据信息
	 * @return组集合
	 */
	@Override
	public List<AuDept> findList() {
		return this.baseDao.find("from AuDept x where x.state = 1  order by x.seqno asc");
	}

	public List<TreeAuDeptVo> findTree(String parentId) {
		String hql="from AuDept x where x.state = 1  ";
		hql+= StringUtils.isEmpty(parentId) ? " and x.level=0 " : " and x.superAuDept.id='"+parentId+"'";
		hql=hql+" order by x.seqno asc";
		List<TreeAuDeptVo> auDepts=new ArrayList<TreeAuDeptVo>();
		List<AuDept> lists=baseDao.find(hql);
		for(AuDept auDept:lists){
			TreeAuDeptVo vo=new TreeAuDeptVo();
			vo.setId(auDept.getId());
			vo.setName(auDept.getName());
			vo.setNotes(auDept.getNotes());
			vo.setLevel(auDept.getLevel());
			vo.setSeqno(auDept.getSeqno());
			vo.setState(this.ischildren(auDept));
			auDepts.add(vo);
		}
		return auDepts;
	}

	private String ischildren(AuDept auDept){
		Boolean flag=true;
		for(AuDept au:auDept.getAuDepts()){
			if(au.getState().equals(1)){
				flag=false;
			}
		}
		return flag== true ? "open" : "closed";
	}
	
	@Override
	public AuDept getParentDept(AuDept auDept){
		AuDept superAuDept=new AuDept();
		if((auDept.getSuperAuDept() instanceof AuDept)&&auDept.getSuperAuDept().getLevel()>0){
			superAuDept=this.getOne(auDept.getSuperAuDept().getId());
//			if(superAuDept.getSuperAuDept().getLevel()==0)
//				return superAuDept.getSuperAuDept();
//			else
			this.getParentDept(superAuDept);
		}
		if((auDept instanceof AuDept)&&auDept.getLevel()==0)
			return auDept;
			return auDept.getSuperAuDept();
	}
	
	@Override
	public AuDept getOne(String id){
		AuDept auDept=this.baseDao.get(AuDept.class, id);
			return auDept;
	}
	
}