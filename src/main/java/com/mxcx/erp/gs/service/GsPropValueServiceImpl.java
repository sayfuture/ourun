package com.mxcx.erp.gs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsPropValue;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 级别业务层接口实现
 * 
 * @author 20140912
 * 
 */
@Service
public class GsPropValueServiceImpl extends BaseService<GsPropValue> implements
		GsPropValueService {

	@Autowired
	private IBaseDao<GsPropValue> gsPropValueDao;

	/**
	 * 系统日志服务
	 */
	@Autowired
	private LogManagementService logManagementService;

	/**
	 * @see com.mxcx.erp.me.service.GsPropValueService#addGsPropValue(GsPropValue
	 *      gsPropValue, AuEmployee auEmployee)
	 */
	@Override
	public Boolean addGsPropValue(GsPropValue gsPropValue, AuEmployee auEmployee) {
		boolean flag = false;
		try {
			gsPropValue.setId(UuidDitch.getId(LogModule.GS_PROP_VALUE
					.getModuleNo()));
			gsPropValue.setState(1);
			flag = addPo(gsPropValue, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_PROP_VALUE.toString(),
					LogFunction.GS_PROP_VALUE_CREATE.toString(),
					gsPropValue.toString());
		}
		return flag;
	}

	@Override
	public Boolean deleteGsPropValue(String id, AuEmployee auEmployee) {
		GsPropValue gsPropValue = null;
		Boolean flag = true;
		try {
			gsPropValue = (GsPropValue) this.getOne(id, GsPropValue.class);
			removeByState(gsPropValue);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_PROP_VALUE.toString(),
					LogFunction.GS_PROP_VALUE_DELETE.toString(),
					gsPropValue.toString());
		}
		return flag;
	}

	@Override
	public Boolean modifyGsPropValue(GsPropValue gsPropValue,
			AuEmployee auEmployee) {
		Boolean flag = true;

		GsPropValue tmpGsPropValue = null;
		try {
			tmpGsPropValue = (GsPropValue) this.gsPropValueDao.getById(
					GsPropValue.class, gsPropValue.getId());
			tmpGsPropValue.setPropId(gsPropValue.getPropId());
			tmpGsPropValue.setPropValue(gsPropValue.getPropValue());
			tmpGsPropValue.setSeqNo(gsPropValue.getSeqNo());

			modify(tmpGsPropValue, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_PROP_VALUE.toString(),
					LogFunction.GS_PROP_VALUE_UPDATE.toString(),
					gsPropValue.toString());
		}
		return flag;
	}

	public List<GsPropValue> findGsPropValueByPropidList(String propId) {
		StringBuffer hql = new StringBuffer(
				"from GsPropValue t where t.state = 1");
		Map<String, Object> paraMap = new HashMap();
		if (StringCheck.stringCheck(propId)) {
			hql.append(" and t.propId = :propId ");
			paraMap.put("propId", propId);
		}
		return this.baseDao.find(hql + "", paraMap);
	}

	@Override
	public DataGrid findGsPropValueList(PageParameter pageParameter) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select a.id");
		sqlBuffer.append("       ,a.prop_id      propId");
		sqlBuffer.append("       ,a.prop_value   propValue");
		sqlBuffer.append("       ,a.seq_no       seqNo");
		sqlBuffer.append("       ,a.state");
		sqlBuffer.append("       ,a.create_user  createUser");
		sqlBuffer.append("       ,a.create_date  createDate");
		sqlBuffer.append("       ,a.update_user  updateUser");
		sqlBuffer.append("       ,a.update_date  updateDate");
		sqlBuffer.append("       ,b.cat_id       catId");
		sqlBuffer.append("       ,b.prop_name    propName");

		sqlBuffer.append(" from gs_prop_value a");
		sqlBuffer.append(" left join gs_prop_name b on b.id = a.prop_id");
		sqlBuffer.append(" where 1 = 1");
		sqlBuffer.append(" and a.state = 1");

		// 以下添加查询条件
		String propId = (String) pageParameter.getParaMap().get("propId");
		if (StringCheck.stringCheck(propId)) {
			sqlBuffer.append(" and a.prop_id = '").append(propId).append("'");
		}
		String propValue = (String) pageParameter.getParaMap().get("propValue");
		if (StringCheck.stringCheck(propValue)) {
			sqlBuffer.append(" and a.prop_value = '").append(propValue)
					.append("'");
		}
		String propName = (String) pageParameter.getParaMap().get("propName");
		if (StringCheck.stringCheck(propName)) {
			sqlBuffer.append(" and b.prop_name = '").append(propName)
					.append("'");
		}

		return gsPropValueDao.findbatisByMysql("find_mybatis_GsPropValue",
				sqlBuffer.toString(), pageParameter);

		// StringBuffer hql = new
		// StringBuffer("from GsPropValue a where a.state = 1");
		//
		// // 以下添加查询条件
		// Map<String, Object> parmMap = new HashMap<String, Object>();
		// if(pageParameter.getParaMap() != null
		// && pageParameter.getParaMap().containsKey("propId")
		// && pageParameter.getParaMap().get("propId") != null
		// && !"".equals(pageParameter.getParaMap().get("propId"))) {
		//
		// hql.append(" and a.propId = :propId");
		// parmMap.put("propId", pageParameter.getParaMap().get("propId"));
		// }
		// if(pageParameter.getParaMap() != null
		// && pageParameter.getParaMap().containsKey("propValue")
		// && pageParameter.getParaMap().get("propValue") != null
		// && !"".equals(pageParameter.getParaMap().get("propValue"))) {
		//
		// hql.append(" and a.propValue = :propValue");
		// parmMap.put("propValue",
		// pageParameter.getParaMap().get("propValue"));
		// }
		//
		// // 排序
		// hql.append(" order by a.createDate desc");
		//
		// pageParameter.setParaMap(parmMap);
		// return gsPropValueDao.findByhql(hql.toString(), pageParameter);
	}

	/**
	 * @see com.mxcx.erp.me.service.GsPropValueService#findGsPropValueByID(String
	 *      id)
	 */
	@Override
	public GsPropValue findGsPropValueByID(String id) {
		return (GsPropValue) getOne(id, GsPropValue.class);
	}

//	@Transactional
//	public void findsss() {
//
//		// 删除
//		Student s = (Student) this.getOne("3333333", Student.class);
//		Teacher t = (Teacher) this.getOne("4444444", Teacher.class);
//		s.getTeacherList().remove(t);
//		t.getStudentList().remove(s);
		// 添加
//		
//		if (s == null) {
//			s = new Student();
//		}
//		if (t == null) {
//			 t = new Teacher();
//		}
//		s.setName("小猪1");
//		s.setId("3333333");
//		
//		t.setName("小李");
//		t.setId("4444444");
//		Set<Teacher> t_set = new HashSet<Teacher>();
//		t_set.add(t);
//		s.setTeacherList(t_set);
//		this.addPo(s);
//		// //查询
//		 String hql1 =
//		 "select s from Student as s ";
//		 List list1 = this.baseDao.find(hql1);
//		 Student s = (Student)list1.get(0);
//		 Set s1 = s.getTeacherList();
//		 Set s2 = s.getItemList();
//		 Iterator<Teacher> it = s1.iterator();
//		 while (it.hasNext()) {
//			 Teacher str = it.next();
//		   System.out.println(str.getName());
//		 }
//		 Iterator<Item> it1 = s2.iterator();
//		 while (it1.hasNext()) {
//			 Item str = it1.next();
//		   System.out.println(str.getName());
//		 }
//		 String hql ="select s from PrBaseInfo as s ";
//		 
//		 List list = this.baseDao.find(hql);
//		 PrBaseInfo prBaseInfo = (PrBaseInfo)list.get(0);
//		 Set s3 = prBaseInfo.getProductTypeList();
//		 Iterator<SysDicValue> it2 = s3.iterator();
//		 while (it2.hasNext()) {
//			 SysDicValue str = it2.next();
//			 System.out.println(str.getName());
//		 }
//		 System.out.println(333);
		 
		 
		
	}

