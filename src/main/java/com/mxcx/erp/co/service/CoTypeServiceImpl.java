package com.mxcx.erp.co.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.auremote.vo.TreeGridVo;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.co.dao.entity.CoType;

/**
 * 内容分类管理业务层接口实现
 * @author 王森20140910
 *
 */

@Service
public class CoTypeServiceImpl extends BaseService<CoType> implements CoTypeService{
	@Autowired
	private IBaseDao<CoType> baseDao;
	

	/**
	 * 查询内容类型列表
	 * 
	 * @param id上级id
	 * @return
	 */
	@Override
	public List<TreeGridVo> findList(String id) {
		if(null != id && id.isEmpty()){
			id = null;
		}
		String hql = "from CoType x where x.state = 1 ";
		hql += id == null ?  " and x.level = 0" : " and x.superCoType.id = '" + id + "'";
		hql+= " order by x.createDate ";
		List<TreeGridVo> to = new ArrayList();
		List<CoType> list = baseDao.find(hql);
		
		for (CoType co : list) {
			TreeGridVo vo = new TreeGridVo();
			vo.setId(co.getId());
			vo.setName(co.getName());
			vo.setState(isReCoType(co));
			vo.setEngName(co.getEngName());
			vo.setCreateDate(co.getCreateDate());
			to.add(vo);
		}
		return to;
	}

	/**
	 * 验证商品分类名称
	 * @param type
	 * @param name分类名称
	 * @return boolean 是否存在
	 */
	@Override
	public Boolean checkName(String type, String name) {
		String hql = "from CoType x where x.state=1 ";
		Map<String, Object> map = new HashMap();
		List<CoType> list = new ArrayList();
		if(type.equals("name")){
			map.put("name", name);
			hql += " and x.name =:name";
		}else if(type.equals("engName")){
			map.put("engName", name);
			hql += " and x.engName =:engName";
		}
		list = baseDao.find(hql, map);
		return list.size() == 0 ? true : false;
	}

	/**
	 * 判断是否拥有子类别，进行树展开判断
	 * 
	 * @param CoType
	 *            父级
	 * @return 展开状态
	 */
	private String isReCoType(CoType coType) {
		Integer i = 0;
		for (CoType ntypet : coType.getCoTypes()) {
			if (ntypet.getState().equals(1)) {
				i++;
			}
		}
		return i == 0 ? "open" : "closed";
	}
	
	/**
	 * 查询内容类型列表
	 * 
	 * @param id上级id
	 * @return
	 */
	@Override
	public List<TreeGridVo> findList2(String id) {
		String hql = "from CoType x where x.state=1 ";
		if (null == id || id.isEmpty() || id.equals("null")){
			id = null;
		}
		hql += id == null ? "and  x.level = 0 " : "and x.CoType.id = '"
				+ id + "'";
		List<TreeGridVo> to = new ArrayList();
		List<CoType> list = baseDao.find(hql);
		
		for (CoType co : list) {
			TreeGridVo vo = new TreeGridVo();
			vo.setId(co.getId());
			vo.setName(co.getName());
			vo.setState(isReCoType(co));
			to.add(vo);
		}
		return to;
	}

	@Override
	public List<CoType> findCoList(String level) {
		String hql="from CoType co where co.state=1 ";
		if(StringUtils.isNotEmpty(level)){
			hql=hql+" and co.level="+level;
		}
		List<CoType> list = baseDao.find(hql);
		return list;
	}
}