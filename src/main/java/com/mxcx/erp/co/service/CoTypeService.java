package com.mxcx.erp.co.service;

import java.util.List;

import com.mxcx.erp.auremote.vo.TreeGridVo;
import com.mxcx.erp.base.commons.service.IBaseService;
import com.mxcx.erp.co.dao.entity.CoContent;
import com.mxcx.erp.co.dao.entity.CoType;

/**
 * 内容分类管理业务层接口
 * 
 * @author 王森20140910
 * 
 */
public interface CoTypeService extends IBaseService<CoType> {

	/**
	 * 查询内容类型列表
	 * 
	 * @param id上级id
	 * @return
	 */
	public List<TreeGridVo> findList(String id);

	/**
	 * 验证商品分类名称
	 * @param type
	 * @param name分类名称
	 * @return boolean 是否存在
	 */
	public Boolean checkName(String type, String name);

	/**
	 * 查询内容类型列表
	 * 
	 * @param id上级id
	 * @return
	 */
	public List<TreeGridVo> findList2(String id);

	public List<CoType> findCoList(String level);
}