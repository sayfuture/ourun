package com.mxcx.erp.au.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxcx.erp.au.dao.entity.AuAuthority;
import com.mxcx.erp.au.dao.entity.AuButton;
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
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 按钮业务层接口实现
 * 
 * @author 20140903
 * 
 */
@Service
public class AuButtonServiceImpl extends BaseService<AuButton> implements AuButtonService {
	@Autowired
	private IBaseDao<AuButton> buttonDao;  //按钮dao层
	
	/**
	 * 系统日志服务
	 */
	@Autowired
	private LogManagementService logManagementService;
	
	@Autowired
	private IBaseDao<AuAuthority> authorityDao; //功能dao层

	/**
	 * 添加一个按钮对象
	 */
	@Override
	public Boolean addAuButton(AuButton auButton,AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			//绑定功能
			auButton.setAuAuthority((AuAuthority) authorityDao.get(AuAuthority.class, auButton.getAuthorityId()));
			//设置id
			auButton.setId(UuidDitch.getId(LogModule.AUBUTTON.getModuleNo()));
			this.addPo(auButton, auEmployee);
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}finally {
		}
		return flag;
	}

	/**
	 * 修改一个按钮对象
	 */
	@Override
	public Boolean modifyAuButton(AuButton auButton,AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			AuButton auButtonTemp = (AuButton) this.baseDao.getById(
					AuButton.class, auButton.getId());
			//设置按钮名称
			auButtonTemp.setBtnName(auButton.getBtnName());
			//设置功能名称
			auButtonTemp.setFunName(auButton.getFunName());
			//设置按钮说明名称
			auButtonTemp.setNotes(auButton.getNotes());
			//设置权限
			auButtonTemp.setAuAuthority((AuAuthority) authorityDao.get(AuAuthority.class, auButton.getAuthorityId()));
			this.modify(auButtonTemp,auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}finally {
		}
		return flag;
	}
	
	
	/**
	 * 删除一个按钮对象
	 */
	@Override
	public Boolean removeAuButton(String id,AuEmployee auEmployee) {
		Boolean flag = true;
		AuButton button =null;
		try {
			// 循环删除按钮对象
			button = (AuButton) this.getOne(id, AuButton.class);
			this.removeByState(button);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}finally {
			logManagementService.addLogManagement(auEmployee,ToolUtils.getHostAddress(), flag,LogModule.AUBUTTON.toString(),LogFunction.AUBUTTON_DELETE.toString(),button.toString());
		}
		return flag;
	}

	/**
	 * 获取按钮datagrid
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from AuButton x inner join fetch x.auAuthority as aa where x.state = 1 ";
		//查询所属功能
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("auAuthority")
				&& pageParameter.getParaMap().get("auAuthority") != null
				&& !pageParameter.getParaMap().get("auAuthority").equals("")
				&& pageParameter.getParaMap().containsKey("auAuthority")) {
			map.put("auAuthority", pageParameter.getParaMap().get("auAuthority"));
			hql += " and aa.id=:auAuthority";
		}
		//查询功能名称
		if (null != pageParameter.getParaMap()
				&& pageParameter.getParaMap().containsKey("btnName")
				&& pageParameter.getParaMap().get("btnName") != null
				&& !pageParameter.getParaMap().get("btnName").equals("")
				&& pageParameter.getParaMap().containsKey("btnName")) {
			hql += " and x.btnName like:btnName";
			map.put("btnName", "%" + pageParameter.getParaMap().get("btnName") + "%");
		}
		hql+=" order by x.createDate desc";
		pageParameter.setParaMap(map);
		return this.baseDao.findByhql(hql, pageParameter);
	}

	/**
	 * 获取按钮list
	 */
	@Override
	public List<AuButton> findList() {
		return this.baseDao.find("from AuButton x where x.state = 1 ");
	}
	
	/**
	 * 
	 */
	@Override
	public List<AuButton> getAubuttonByAuthority(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from AuButton x inner join fetch x.auAuthority as aa where x.state = 1 ";
		if(StringCheck.stringCheck(id)){
			map.put("auAuthority", id);
			hql += "and aa.id=:auAuthority";
		}
		return this.baseDao.find(hql, map);
	}
}