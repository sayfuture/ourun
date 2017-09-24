package com.mxcx.erp.au.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 角色业务层接口实现
 * 
 * @author  20140624
 * 
 */
@Service
public class AuPositionServiceImpl extends BaseService<AuPosition> implements AuPositionService {
	@Autowired
	private IBaseDao<AuPosition> auPositionDao; // 角色持久层
	@Autowired
	private LogManagementService logManagementService; // 日志记录业务层接口
	
	/**
	 * 添加角色
	 * 
	 * @param auPosition角色对象
	 * @return是否成功
	 */
	@Override
	public Boolean addAuPosition(AuPosition auPosition, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			auPosition.setId(UuidDitch.getId(LogModule.AUPOSITION.getModuleNo()));
			auPosition.setState(1);
			this.addPo(auPosition, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.AUPOSITION.toString(), LogFunction.AUPOSITION_CREATE.toString(),auPosition.toString());
		}
		return flag;
	}
	
	/**
	 * 删除角色
	 * @param id角色id
	 * @return是否成功
	 */
	@Override
	public Boolean deleteAuPosition(String id, AuEmployee employee){
		AuPosition auPosition = null;
		Boolean flag = true;
		try{
			auPosition = (AuPosition)this.auPositionDao.getById(AuPosition.class, id);
			this.removeByState(auPosition);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(employee, ToolUtils.getHostAddress(), flag, LogModule.AUPOSITION.toString(), LogFunction.AUPOSITION_DELETE.toString(),auPosition.toString());
		}
		return flag;
	}

	/**
	 * 修改角色
	 * 
	 * @param auPosition 角色对象
	 * @return是否成功
	 */
	@Override
	public Boolean modifyAuPosition(AuPosition auPosition, AuEmployee auEmployee) {
		Boolean flag = true;
		AuPosition auPositionTemp = null; 
		try {
			auPositionTemp = (AuPosition) this.auPositionDao.getById(AuPosition.class, auPosition.getId());
			auPositionTemp.setName(auPosition.getName());
			this.modify(auPositionTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.AUPOSITION.toString(), LogFunction.AUPOSITION_UPDATE.toString(),auPositionTemp.toString());
		}
		return flag;
	}

	/**
	 * 查询所有角色（分页）
	 * 
	 * @param pageParameter本页数据信息
	 * @return功能分页
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap();
		String hql = "from AuPosition x where x.state = 1 ";
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null && !pageParameter.getParaMap().containsKey("type")) {
			hql += "and x.name=:name";
			map.put("name", pageParameter.getParaMap().get("name"));
		}
		
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null && pageParameter.getParaMap().containsKey("type")) {
			hql += "and x.name like:name ";
			map.put("name", "%"+ pageParameter.getParaMap().get("name")+"%");
		}
		hql+=" order by x.createDate desc";
		pageParameter.setParaMap(map);
		return this.auPositionDao.findByhql(hql, pageParameter);
	}

	/**
	 * 查询所有角色（集合）
	 * 
	 * @param pageParameter本页数据信息
	 * @return角色集合
	 */
	@Override
	public List<AuPosition> findAuPositionList() {
		return this.auPositionDao.find("from AuPosition x where x.state = 1 order by x.createDate desc");
	}
}