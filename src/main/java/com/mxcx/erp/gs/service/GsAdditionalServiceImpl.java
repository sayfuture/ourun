package com.mxcx.erp.gs.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsSku;
import com.mxcx.erp.gs.dao.entity.GsSkuAddRecord;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 
 */
@Service
@Transactional
public class GsAdditionalServiceImpl extends BaseService<GsSku> implements GsAdditionalService {
	@Autowired
	private LogManagementService logManagementService; // 日志记录

	@Autowired
	private IBaseDao<GsSku> gsSkuDAO;
	@Autowired
	private IBaseDao<GsSkuAddRecord> gsSkuAddRecordDAO;
	@Autowired
	private IBaseDao<GsGoods> gsGoodsDAO;
	
	@Override
	public DataGrid findAdditionalList(PageParameter pageParameter) {
				StringBuilder sql=this.getSql();
				sql.append("order by gs.quantity asc");
				DataGrid dataGrid=this.baseDao.findbatisByMysql("find_mybatis_GsSKu",sql.toString(), pageParameter);
		return dataGrid;
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
		this.saveRecord(gsSku,Integer.valueOf(addNum),request);
		}else{
			flag=false;
		}
		return flag;
	}


	@Override
	public DataGrid findGsName(String gsName, PageParameter pageParameter) {
		StringBuilder sql=this.getSql();
		if(StringCheck.stringCheck(gsName))
		{sql.append(" and gg.product_name='"+gsName+"'");
		}
		sql.append(" order by gs.quantity asc");
		DataGrid dataGrid=this.baseDao.findbatisByMysql("find_mybatis_GsSKu",sql.toString(), pageParameter);
		return dataGrid;
	}
	
	private StringBuilder getSql(){
		StringBuilder sql=new StringBuilder("select gs.id, gg.product_name as goodsName,gs.propsname, ");
		sql.append("gs.price,gs.pv,gs.quantity,gs.status,gs.is_def,gs.CREATE_DATE as createDate ");
		sql.append(" from gs_sku gs, gs_goods gg   where gs.state=1 and gs.goods_id=gg.id ");
		return sql;
	}
	
	private void saveRecord(GsSku gsSku,Integer quantity,HttpServletRequest request){
		AuEmployee auEmployee = (AuEmployee) request.getSession().getAttribute("auEmployee");
		GsSkuAddRecord record=new GsSkuAddRecord();
		record.setId(UuidDitch.getId(LogModule.LOGMANAGEMENT.getModuleNo()));
		GsGoods gsGoods=gsGoodsDAO.get(GsGoods.class,gsSku.getGoods_id());
		record.setGoodsName(gsGoods.getProductName());
		record.setPropsname(gsSku.getPropsname());
		record.setPrice(gsSku.getPrice());
		record.setCreateUser(auEmployee.getId());
		record.setCreateDate(new Date());
		record.setPv(gsSku.getPv());
		record.setState(gsSku.getState());
		record.setStatus(gsSku.getStatus());
		record.setGoods_id(gsSku.getGoods_id());
		//增加商品数量
		record.setQuantity(quantity);
		gsSkuAddRecordDAO.add(record);
	}

}