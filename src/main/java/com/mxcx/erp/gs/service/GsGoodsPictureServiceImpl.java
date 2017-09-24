package com.mxcx.erp.gs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.base.adaptor.FilePath;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.base.commons.service.ISystemUpload;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.ImageUtil;
import com.mxcx.ec.base.commons.util.PropertiesReader;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsGoods;
import com.mxcx.erp.gs.dao.entity.GsGoodsPicture;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 商品图片图片图片业务层接口实现
 * 
 * @author 20140915
 */
@Service
public class GsGoodsPictureServiceImpl extends BaseService<GsGoodsPicture> implements GsGoodsPictureService {
	@Autowired
	private IBaseDao<GsGoodsPicture> dao; // 商品图片持久层
	@Autowired
	private GsGoodsService gsGoodsService; // 商品业务层接口
	
	
	@Autowired
	private ISystemUpload systemUpload; // 上传接口
	@Autowired
	private LogManagementService logManagementService; // 日志记录业务层接口
	public static final String FILESEPARATOR = System.getProperty("file.separator");  

	/**
	 * 添加商品图片
	 * 
	 * @param auEmployee操作人员
	 * @param GsGoodsPicture商品图片对象
	 * @param filePath图片名称
	 * @return是否成功
	 */
	@Override
	public Boolean addGsGoodsPicture(HttpServletRequest request, GsGoodsPicture gsGoodsPicture, AuEmployee auEmployee, String filePath) {
		Boolean flag = true;
		try {
			String timedate = FilePath.getDatetime()+"/";;
			String fileName = this.systemUpload.systemUpload(request, filePath, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath+timedate);
			if (null != fileName) {
				this.addPicture(request, timedate+fileName);
				gsGoodsPicture.setUrl(timedate+fileName);
			}
			String gid = gsGoodsPicture.getGsGoodsId();
			
			String hql="select count(*) from GsGoodsPicture x where x.state = 1 and x.gsGoodsId =:gsGoodsId";
			Map<String ,Object> map= new HashMap<String ,Object>();
			map.put("gsGoodsId", gid);
			long count = this.baseDao.count(hql,map);
			if(count==0){
				gsGoodsPicture.setDef(1);
			}
			else{
				gsGoodsPicture.setDef(0);
			}
			
			gsGoodsPicture.setGsGoodsId(gsGoodsPicture.getGsGoodsId());
			gsGoodsPicture.setState(1);
			gsGoodsPicture.setId(UuidDitch.getId(LogModule.GS_GOODS_PICTURE.getModuleNo()));
			this.addPo(gsGoodsPicture, auEmployee);
			if(count==0){
			 GsGoods gsGoods = (GsGoods) this.gsGoodsService.getOne(gid,GsGoods.class);
			 gsGoods.setPicId(gsGoodsPicture.getId());
			 this.gsGoodsService.modify(gsGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.GS_GOODS_PICTURE.toString(), LogFunction.GS_GOODS_PICTURE_CREATE.toString(),"");
		}
		return flag;
	}

	@Override
	public void updateAllPicture(HttpServletRequest request){
		List<GsGoodsPicture> list = this.findAllList();
//		String baseUrl = request.getSession().getServletContext().getRealPath(FILESEPARATOR);
		String baseUrl = PropertiesReader.getInstance().getConfigItem("frontProjectAddress") ;
		baseUrl =baseUrl + FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath;
//		baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf(PropertiesReader.getInstance().getConfigItem("rootPath").replace("/", ""))) + PropertiesReader.getInstance().getConfigItem("frontProject").replace("/", "") + FILESEPARATOR + FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath ;
		
		for (GsGoodsPicture pe : list) {
			String fileName = pe.getFilePath();
			String[] strs = fileName.split("\\.");
			fileName = baseUrl + fileName;
			/*File file = new File(fileName);
			if (file.exists()) {*/
			ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_oneLargest."+strs[1], 354, 220);
			ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_thirdLargest."+strs[1], 221, 136);
			ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_secondLargest."+strs[1], 316, 195);
			ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_small."+strs[1], 79, 49);
//			ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_forthLargest."+strs[1], 460, 289);
//			ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_fifthLargest."+strs[1], 800, 470);
			//}
		}
	}
	
	/**
	 * 删除图片
	 * @param request
	 * @param file原来图片名称
	 * @author 王森 2014-12-16 下午6:27:51
	 */
	private void deletePicture(HttpServletRequest request, String file){
		String[] strs2 = null != file ? file.split("\\.") : null;
		if(null != strs2){
			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, strs2[0]+"_oneLargest."+strs2[1]);
			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, strs2[0]+"_thirdLargest."+strs2[1]);
			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, strs2[0]+"_secondLargest."+strs2[1]);
			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, strs2[0]+"_small."+strs2[1]);
//			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, strs2[0]+"_forthLargest."+strs2[1]);
//			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, strs2[0]+"_fifthLargest."+strs2[1]);
			this.systemUpload.systemDeleteFile(request, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath, file);
		}
	}
	
	/**
	 * 添加图片
	 * @param request
	 * @param fileName新生成的图片名称
	 * @author 王森 2014-12-16 下午6:30:14
	 */
	private void addPicture(HttpServletRequest request, String fileName){
//		String baseUrl = request.getSession().getServletContext().getRealPath(FILESEPARATOR);
		String baseUrl = PropertiesReader.getInstance().getConfigItem("frontProjectAddress") ;
		baseUrl =baseUrl + FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath;
//		baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf(PropertiesReader.getInstance().getConfigItem("rootPath").replace("/", ""))) + PropertiesReader.getInstance().getConfigItem("frontProject").replace("/", "") + FILESEPARATOR + FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath ;
		String[] strs = fileName.split("\\.");
		fileName = baseUrl + fileName;
//		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_oneLargest."+strs[1], 354, 220);
		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_oneLargest."+strs[1], 800, 470);
		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_thirdLargest."+strs[1], 221, 136);
		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_secondLargest."+strs[1], 316, 195);
		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_small."+strs[1], 79, 49);
//		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_forthLargest."+strs[1], 460, 289);
//		ImageUtil.createMinImg(fileName, baseUrl + strs[0]+"_fifthLargest."+strs[1], 800, 470);
	}
	
	/**
	 * 修改商品图片
	 * @param auEmployee操作人员
	 * @param GsGoodsPicture商品图片对象
	 * @param filePath图片名称
	 * @return是否成功
	 */
	@Override
	public Boolean modifyGsGoodsPicture(HttpServletRequest request, GsGoodsPicture gsGoodsPicture, AuEmployee auEmployee, String filePath) {
		Boolean flag = true;
		try {
			GsGoodsPicture gsGoodsPictureTemp = (GsGoodsPicture) this.dao.getById(GsGoodsPicture.class, gsGoodsPicture.getId());
			gsGoodsPictureTemp.setName(gsGoodsPicture.getName());
			String timedate = FilePath.getDatetime()+"/";
			String fileName = this.systemUpload.systemUpload(request, filePath, FilePath.GS_PICTURE_UPLOAD_FILE_PATH.filePath+timedate); // 缩略图
			if (null != fileName) {
				this.deletePicture(request, gsGoodsPictureTemp.getUrl());
				gsGoodsPictureTemp.setUrl(timedate+fileName);
				this.addPicture(request, timedate+fileName);
			}
			
			this.modify(gsGoodsPictureTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.GS_GOODS_PICTURE.toString(), LogFunction.GS_GOODS_PICTURE_UPDATE.toString(),"");
		}
		return flag;
	}

	/**
	 * 删除商品图片
	 * 
	 * @param auEmployee操作人员
	 * @param id商品图片对象Id
	 * @param filePath图片名称
	 * @return是否成功
	 */
	@Override
	public Boolean removeGsGoodsPictures(HttpServletRequest request, String id, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			GsGoodsPicture button = (GsGoodsPicture) this.getOne(id, GsGoodsPicture.class);
			this.deletePicture(request, button.getUrl());
			this.remove(button);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.GS_GOODS_PICTURE.toString(), LogFunction.GS_GOODS_PICTURE_DELETE.toString(),"");
		}
		return flag;
	}
	
	/**
	 * 根据商品查询商品图片分页
	 * @param pageParameter  分页参数
	 * @param Id 商品Id
	 * @return 商品图片分页
	 */
	@Override
	public DataGrid findList(PageParameter pageParameter, String id) {
		String hql = "from GsGoodsPicture x where x.state =1 and x.gsGoodsId =:id order by x.createDate desc ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		pageParameter.setParaMap(map);
		return this.baseDao.findByhql(hql, pageParameter);
	}

	/**
	 * 根据商品查询商品图片集合
	 * @param pageParameter  分页参数
	 * @param Id 商品Id
	 * @return 商品图片集合
	 */
	@Override
	public List<GsGoodsPicture> findList(String id) {
		String hql = "from GsGoodsPicture x where x.state =1 and x.gsGoodsId =:id order by x.createDate desc ";
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringCheck.stringCheck(id)){
			map.put("id", id);
		}
		return this.baseDao.find(hql, map);
	}
	
	private List<GsGoodsPicture> findAllList() {
		String hql = "from GsGoodsPicture x where x.state =1";
		return this.baseDao.find(hql);
	}

	/**
	 * 查询所有商品图片（集合）
	 * 
	 * @return 商品图片分页
	 */
	@Override
	public List<GsGoodsPicture> findList() {
		return this.baseDao.find("from GsGoodsPicture x where x.state =1 order by x.createDate desc ");
	}

	@Transactional
	public boolean setGsGoodsPicture(String id, String imgId) {
		Boolean flag = true;
		 try{
			
			 
			 String hql =" update GsGoodsPicture t set t.def=0 where t.gsGoodsId ='"+id+"'";
			 Map<String, Object> params = new HashMap<String, Object>();
			 this.baseDao.executeHql(hql);
			 GsGoodsPicture gsGoodsPicture = (GsGoodsPicture)this.getOne(imgId, GsGoodsPicture.class);
			 gsGoodsPicture.setDef(1);
			 this.modify(gsGoodsPicture);
			 GsGoods gsGoods = (GsGoods) this.gsGoodsService.getOne(id,GsGoods.class);
			 gsGoods.setPicId(imgId);
			 this.gsGoodsService.modify(gsGoods);
		 }catch(Exception e){
		 e.printStackTrace();
		 flag = false;
		 }
		return flag;
		
	}
}