package com.mxcx.erp.au.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuAuthorityPosition;
import com.mxcx.erp.au.dao.entity.AuButton;
import com.mxcx.erp.au.dao.entity.AuPosition;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.UuidDitch;

/**
 * 角色功能业务层接口实现
 * @author Administrator
 *
 */
@Service
public class AuAuthorityPositionServiceImpl extends BaseService<AuAuthorityPosition> implements AuAuthorityPositionService{
	@Autowired
	private IBaseDao<AuAuthorityPosition> baseDao;
	@Autowired
	private IBaseDao<AuButton> buttonDao;
	@Autowired
	private AuPositionService auPositionService;
	@Autowired
	private AuAuthorityService auAuthorityService; // 功能持久层
	
	@Override
	public List<AuAuthorityPosition> queryDAO(int page, int rows) {
		return this.baseDao.find("from AuAuthorityPosition", page, rows);
	}

	@Override
	public int queryCountDAO() {
		return this.baseDao.count("from AuAuthorityPosition").intValue();
	}

	
	@Override
	public List<AuAuthorityPosition> findAuAuthorityPositionListByPosition(String positions) {
		
		String hql = "from AuAuthorityPosition x inner join fetch x.auPosition as an where an.id in ("+positions+") order by x.auAuthority.name ";
		Map<String,Object> map = new HashMap();
//		map.put("id", positionId);
		List<AuAuthorityPosition> auAhorityPositionList = this.baseDao.find(hql, map);
		return auAhorityPositionList;
	}

	@Override
	public DataGrid findList(PageParameter pageParameter) {
		String hql = "from AuAuthorityPosition x ";
		if (null != pageParameter.getParaMap() && pageParameter.getParaMap().containsKey("name")
				&& pageParameter.getParaMap().get("name") != null) {
			hql += "where x.name=:name";
			// hql += " inner join fetch x.baseuser1 bu where bu.name=:name";
		}

		return this.baseDao.findByhql(hql, pageParameter);
	}
	
	@Override
	public List<AuAuthorityPosition> findAuAuthorityPositionListByAuAhority(String auAuthorityId) {
		String hql = "from AuAuthorityPosition x inner join fetch x.auAuthority as an where an.id=:id";
		Map<String,Object> map = new HashMap();
		map.put("id", auAuthorityId);
		List<AuAuthorityPosition> auAhorityPositionList = this.baseDao.find(hql, map);
		return auAhorityPositionList;
	}
	
	@Override
	public void deleteAuthorityAuEmployee(String authorityId) {
		List<AuAuthorityPosition> auEmployeeAuthorities = this.findAuAuthorityPositionListByAuAhority(authorityId);
		for (AuAuthorityPosition auEmployeeAuthority : auEmployeeAuthorities) {
			this.baseDao.remove(auEmployeeAuthority);
		}
	}
	
	/**
	 * 通过角色和权限查找对应的权限信息  zzq
	 */
	@Override
	public List<AuAuthorityPosition> getButtonByPositionAndAuthority(
			String positionId, String authorityId) {
		String hql = "from AuAuthorityPosition x inner join fetch x.auAuthority as aa inner join fetch x.auPosition as ap where ap.id=:positionId and aa.id=:authorityId";
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringCheck.stringCheck(authorityId)&&StringCheck.stringCheck(positionId)){
			map.put("positionId", positionId);
			map.put("authorityId", authorityId);
		}
		return this.baseDao.find(hql, map);
	}
	
	
	@Override
	public Boolean addAuAuthorityPosition(String positionId, String authorityId,
			String[] type) {
		Boolean flag = true;
		try {
			AuPosition auPosition = (AuPosition) this.auPositionService.getOne(positionId, AuPosition.class);
			AuAuthority auAuthority = (AuAuthority) this.auAuthorityService.getOne(authorityId, AuAuthority.class);
			AuAuthorityPosition ay = null;
			AuButton button ;
			if(null!=type&&type.length>0){
				for(String btnId : type){
					ay = new AuAuthorityPosition();
					button = buttonDao.get(AuButton.class, btnId);
					ay.setId(UuidDitch.getId(LogModule.AUAUTHORITYPOSITION.getModuleNo()));
					ay.setAuAuthority(auAuthority);
					ay.setAuPosition(auPosition);
					ay.setAuButton(button);
					this.baseDao.add(ay);
				}
			}else{
				ay = new AuAuthorityPosition();
				ay.setId(UuidDitch.getId(LogModule.AUAUTHORITYPOSITION.getModuleNo()));
				ay.setAuAuthority(auAuthority);
				ay.setAuPosition(auPosition);
				 this.baseDao.add(ay); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}