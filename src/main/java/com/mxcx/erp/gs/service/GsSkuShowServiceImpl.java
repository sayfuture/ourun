package com.mxcx.erp.gs.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.erp.gs.dao.entity.GsSku;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 
 */
@Service
@Transactional
public class GsSkuShowServiceImpl extends BaseService<GsSku> implements GsSkuShowService {
	@Autowired
	private LogManagementService logManagementService; // 日志记录
	@Autowired
	private IBaseDao<GsSku> gsSkuDAO;

	
	@Override
	public DataGrid findSkuList(PageParameter pageParameter) {
		StringBuilder sql=this.getSql();
		sql.append("order by gs.create_Date desc");
		DataGrid dataGrid=this.baseDao.findbatisByMysql("find_mybatis_GsSKu",sql.toString(), pageParameter);
		return dataGrid;
	}
	@Override
	public DataGrid searchForGsSKU(PageParameter pageParameter) {
		String propsName=pageParameter.getParaMap().get("propsName").toString();
		String name=pageParameter.getParaMap().get("name").toString();
		String pv=pageParameter.getParaMap().get("pv").toString();
		String bind=pageParameter.getParaMap().get("bind").toString();
		StringBuilder sql=this.getSql();
		if(StringUtils.isNotEmpty(propsName)){
			sql.append(" and gs.propsname like '%"+propsName+"%'");
		}
		if(StringUtils.isNotEmpty(name)){
			sql.append(" and gg.product_name like '%"+name+"%'");
		}
		if(StringUtils.isNotEmpty(pv)){
			sql.append(" and gs.pv like '%"+pv+"%'");
		}
		if(StringUtils.isNotEmpty(bind)&&!bind.equals("-1")){
			if(bind.equals("1"))
				sql.append(" and gs.goods_code is not null ");
			if(bind.equals("0"))
				sql.append(" and gs.goods_code is null ");
		}
		sql.append("  order by gs.create_Date desc");
		DataGrid dataGrid=this.baseDao.findbatisByMysql("find_mybatis_GsSKu",sql.toString(), pageParameter);
		return dataGrid;
	}

	private StringBuilder getSql(){
		StringBuilder sql=new StringBuilder();
		sql.append("select gs.id as id, gs.price as price,gs.pv as pv,gs.QUANTITY as  quantity,");
		sql.append("gs.is_def as is_def,gs.create_date as createTimeBasePo ,gs.status as status,gs.goods_code as goodsCode,gs.propsname as propsname,gg.PRODUCT_NAME as goodsName ");
		sql.append("from Gs_Sku gs,gs_goods gg where gs.state=1 and gs.goods_id=gg.id  " );
		return sql;
	}
	
	
	@Override
	public Boolean gsAdd(HttpServletRequest request) {
		Boolean flag=true;
		String id=request.getParameter("id");
		String addNum=request.getParameter("addNum");
		GsSku gsSku=null;
		if(StringCheck.stringCheck(id)){
			gsSku=gsSkuDAO.get( GsSku.class,id);
		}else{
			flag=false;
		}
		if(StringCheck.stringCheck(addNum)){
		Integer newQuantity=gsSku.getQuantity()+Integer.valueOf(addNum);
		
		gsSku.setQuantity(newQuantity);
		gsSku.setUpdateDate(new Date());
		gsSkuDAO.modify(gsSku);
		}else{
			flag=false;
		}
		return flag;
	}

}