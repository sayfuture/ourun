package com.mxcx.erp.me.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.TestObj;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.me.dao.entity.MeLevel;

/**
 * 级别业务层接口实现
 * 
 * @author  20140912
 * 
 */
@Service
public class MeLevelServiceImpl extends BaseService<MeLevel> implements IMeLevelService {
	

	
	@Autowired
	private IBaseDao<MeLevel> meLevelDao;
//	@Autowired
//	private IBaseDao1<MeLevel1> meLevelDao1;
	/**
	 * 系统日志服务
	 */
	@Autowired
	private LogManagementService logManagementService;

	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#addMemberLevel(MeLevel meLevel, AuEmployee auEmployee)
	 */
	@Override
	public Boolean addMemberLevel(MeLevel meLevel, AuEmployee auEmployee) {
		boolean flag = false;
		try{
			meLevel.setId(UuidDitch.getId(LogModule.MEMBER_LEVEL.getModuleNo()));
			flag = addPo(meLevel, auEmployee);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.MEMBER_LEVEL.toString(), LogFunction.MEMBER_LEVEL_CREATE.toString(),meLevel.toString());
		}
		return flag;
	}
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#deleteMemberLevel(String id, AuEmployee auEmployee)
	 */
	@Override
	public Boolean deleteMemberLevel(String id, AuEmployee auEmployee) {
		MeLevel meLevel = null;
		Boolean flag = true;
		try{
			meLevel = (MeLevel)this.getOne(id, MeLevel.class);
			removeByState(meLevel);
		} catch (Exception e){
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.MEMBER_LEVEL.toString(), LogFunction.MEMBER_LEVEL_DELETE.toString(),meLevel.toString());
		}
		return flag;
	}
	
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#modifyMemberLevel(MeLevel meLevel, AuEmployee auEmployee)
	 */
	@Override
	public Boolean modifyMemberLevel(MeLevel meLevel, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			modify(meLevel,auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.MEMBER_LEVEL.toString(), LogFunction.MEMBER_LEVEL_UPDATE.toString(),meLevel.toString());
		}
		return flag;
	}
	
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#findMemberLevelList(PageParameter pageParameter)
	 */
	@Override
	public DataGrid findMemberLevelList(PageParameter pageParameter) {
		String hql = "from MeLevel x where x.state = 1 order by x.createDate desc" ;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", null);
//		List<TestObj>  list1= meLevelDao.getSqlSession().selectList("find_mybatis_test",map);
//		List<MeLevel> eztDoctors = this.mybatisSessionTemplate.selectList("findEztDoctorbyDeptId", map);
//		String sql = "select * from me_level";
//		List<MeLevel> l =  meLevelDao.findBySQLQuery(sql);
//		for (int i = 0; i < l.size(); i++) {
//			MeLevel  m = l.get(i);
//			System.out.println(m.getId());
//		}
//		String hql1 = "from MeLevel1 x where x.state = 1 order by x.createDate desc" ;
//		DataGrid d = meLevelDao1.findByhql(hql1, pageParameter);
//		System.out.println(d);
		return meLevelDao.findByhql(hql, pageParameter);
	}
	
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#findMemberLevelByID(String id)
	 */
	@Override
	public MeLevel findMemberLevelByID(String id) {
		return (MeLevel)getOne(id, MeLevel.class);
	}
	
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#findMemberLevelByName(PageParameter pageParameter)
	 */
	@Override
	public DataGrid findMemberLevelByName(PageParameter pageParameter) {
		
		StringBuffer hql = new StringBuffer("from MeLevel mel where mel.state = 1");
			hql.append(" and mel.levelName like :levelName order by mel.createDate desc");
		
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("levelName", "%" + (String) pageParameter.getParaMap().get("levelName") + "%");
		pageParameter.setParaMap(paraMap);
		
		return meLevelDao.findByhql(hql.toString(), pageParameter);
	}
	
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#findMemberLevelByName(PageParameter pageParameter)
	 */
	@Override
	public String scoreToLevelName(List<MeLevel> levelList,Long score) {
		String levelName = "";
		for(MeLevel level: levelList){
			if(score >= level.getStartPosition() && score <=  level.getEndPosition()){
				levelName = level.getLevelName();
			}
		}
		return levelName;
	}
	
	/**
	 * @see  com.mxcx.erp.me.service.IMeLevelService#findMemberLevelList()
	 */
	@Override
	public List<MeLevel> findMemberLevelList() {
		StringBuffer hql = new StringBuffer("from MeLevel mel where mel.state = 1 order by mel.createDate desc");
		return meLevelDao.find(hql.toString());
	}
	
	@Override
	public MeLevel findMemberLevelbyScore(Integer score) {

		StringBuffer hql = new StringBuffer("from MeLevel t where t.state = 1 and t.startPosition<="+score+" and t.endPosition>="+score+"");
		List<MeLevel> list = meLevelDao.find(hql+"");
		if(list!=null){
			return list.get(0);
		}
		return null;
	}
}
