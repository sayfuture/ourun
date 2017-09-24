package com.mxcx.erp.gs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.auremote.vo.TreeGridVo;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;
import com.mxcx.erp.gs.dao.entity.GsGoodsType;
import com.mxcx.erp.lo.service.LogManagementService;

/**
 * 商品分类业务层
 * 
 */
@Service
public class GsGoodsTypeServiceImpl extends BaseService<GsGoodsType> implements
		GsGoodsTypeService {
	@Autowired
	private LogManagementService logManagementService; // 日志记录

	/**
	 * 查询商品分类列表
	 * 
	 * @param id
	 *            父级ID
	 * @return treeGridVo easyui封装树
	 */
	@Override
	public List<TreeGridVo> findList(String id) {
		String hql = "from GsGoodsType x where x.state=1 ";

		hql += !StringCheck.stringCheck(id) ? "and  x.level = 0 " : "and x.gsGoodsType.id = '"
				+ id + "'";
		List<TreeGridVo> treeGridVos = new ArrayList<TreeGridVo>();
		List<GsGoodsType> list = baseDao.find(hql);

		for (GsGoodsType etType : list) {
			TreeGridVo treeGridVo = new TreeGridVo();
			treeGridVo.setId(etType.getId());
			treeGridVo.setName(etType.getName());

			treeGridVo.setState(isReGsType(etType));
			treeGridVos.add(treeGridVo);
		}
		return treeGridVos;
	}

	/**
	 * 判断是否拥有子类别，进行树展开判断
	 * 
	 * @param gsGoodsType
	 *            父级
	 * @return 展开状态
	 */
	private String isReGsType(GsGoodsType gsGoodsType) {
		Integer i = 0;
		for (GsGoodsType ntypet : gsGoodsType.getGsGoodsTypes()) {
			if (ntypet.getState().equals(1)) {
				i++;
			}
		}

		return i == 0 ? "open" : "closed";
	}

	/**
	 * 判断商品分类是否名称重复
	 * 
	 * @param name
	 *            名称
	 * @return 是否存在
	 */
	@Override
	public Boolean checkName(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		List<GsGoodsType> list = baseDao.find(
				"from GsGoodsType x where x.state=1 and x.name =:name", map);
		return list.size() == 0 ? true : false;
	}

	@Override
	public List<TreeGridVo> findList2(String id) {
		String hql = "from GsGoodsType x where x.state=1 ";
		if (null == id || id.isEmpty() || id.equals("null")) {
			id = null;
		}
		hql += id == null ? "and  x.level = 0 " : "and x.gsGoodsType.id = '"
				+ id + "'";
		List<TreeGridVo> treeGridVos = new ArrayList<TreeGridVo>();
		List<GsGoodsType> list = baseDao.find(hql);

		for (GsGoodsType etType : list) {
			TreeGridVo treeGridVo = new TreeGridVo();
			treeGridVo.setId(etType.getId());
			treeGridVo.setName(etType.getName());

			treeGridVo.setState(isReGsType(etType));
			treeGridVos.add(treeGridVo);
		}
		return treeGridVos;
	}

	/**
	 * 根据下级找上级
	 * 
	 * @param id下级Id
	 * @return
	 */
	public GsGoodsType findUpper(String id) {
		GsGoodsType gd = (GsGoodsType) this.getOne(id, GsGoodsType.class);
		List<GsGoodsType> list = new ArrayList<GsGoodsType>(0);
		if (gd.getLevel() != 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", id);
			list = baseDao
					.find("from GsGoodsType x left join fetch x.gsGoodsType where x.state=1 and x.id=:typeId",
							map);
		}
		return list.size() != 0 ? list.get(0) : null;
	}

	/**
	 * 商品文件分类上传
	 * 
	 * @param request
	 * @return
	 */
	public File goodsTypeFileUpload(HttpServletRequest request) {
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件
		MultipartFile file = multipartRequest.getFile("fileName");
		// 服务器存储文件路径
		String filePath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "fileupload" + File.separator;
		// 创建文件夹
		File dirPath = new File(filePath);
		if (!dirPath.exists()) {
			dirPath.mkdir();
		}
		// 文件重命名，避免文件名重复
		String filename = System.currentTimeMillis() + ".xls";
		// 创建文件
		File uploadFile = new File(filePath + filename);
		try {
			// Copy文件
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadFile;
	}

	/**
	 * 批量导入商品分类
	 * 
	 * @param request
	 * @param file
	 *            商品分类文件
	 * @return
	 */
	public void batchAddGoodsType(AuEmployee auEmployee, File file) {
		// 将Excel表读到workbook里
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 得到sheet页总数
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			GsGoodsType level_1 = null; // 一级分类
			GsGoodsType level_2 = null;
			GsGoodsType level_3 = null;
			GsGoodsType level_4 = null;
			HSSFSheet sheet = workbook.getSheetAt(i); // 获取sheet页
			// 存储一级分类
			level_1 = saveGsGoodsType(null, sheet.getSheetName(), 0, auEmployee);
			// sheet页总行数
			int rows = sheet.getPhysicalNumberOfRows();
			for (int j = 2; j < rows; j++) { // 略过前两行
				HSSFRow row = sheet.getRow(j); // 得到行
				if (row != null) {
					// 存储二级分类
					if (row.getCell(0).getStringCellValue() != null
							&& !"".equals(row.getCell(0).getStringCellValue())) {
						level_2 = saveGsGoodsType(level_1, row.getCell(0)
								.getStringCellValue(), 1, auEmployee);
					}
					// 存储三级分类
					if (row.getCell(1).getStringCellValue() != null
							&& !"".equals(row.getCell(1).getStringCellValue())) {
						level_3 = saveGsGoodsType(level_2, row.getCell(1)
								.getStringCellValue(), 2, auEmployee);
					}
					// 存储四级分类
					if (row.getCell(2).getStringCellValue() != null
							&& !"".equals(row.getCell(2).getStringCellValue())) {
						level_4 = saveGsGoodsType(level_3, row.getCell(2)
								.getStringCellValue(), 3, auEmployee);
					}
					// 存储五级分类
					if (row.getCell(3).getStringCellValue() != null
							&& !"".equals(row.getCell(3).getStringCellValue())) {
						saveGsGoodsType(level_4, row.getCell(3)
								.getStringCellValue(), 4, auEmployee);
					}
				}
			}
		}
	}

	/**
	 * 商品分类入库
	 * 
	 * @param pid
	 *            商品上级分类
	 * @param name
	 *            商品分类名称
	 * @param level
	 *            商品分类级别
	 * @param auEmployeet
	 *            用户信息
	 * @return
	 */
	public GsGoodsType saveGsGoodsType(GsGoodsType pGsGoodsType, String name,
			int level, AuEmployee auEmployee) {
		GsGoodsType gsGoodsType = new GsGoodsType();
		gsGoodsType.setId(UuidDitch.getId(String
				.valueOf(LogModule.GS_GOODS_TYPE.getModuleNo())));
		gsGoodsType.setGsGoodsType(pGsGoodsType);
		gsGoodsType.setName(name);
		gsGoodsType.setLevel(level);
		addPo(gsGoodsType, auEmployee);
		return gsGoodsType;
	}

	/**
	 * 设为热门/普通商圈 0：普通；1：热点；
	 */
	@Override
	public Boolean changeToHotGsGoodsType(String id, int type,AuEmployee auEmployee) {
		Boolean flag = true;
		GsGoodsType gsGoodsTypeTemp = null;
		try {
			gsGoodsTypeTemp = (GsGoodsType) this.baseDao.getById(
					GsGoodsType.class, id);
			gsGoodsTypeTemp.setHotGsGoodsType(type);
			this.modify(gsGoodsTypeTemp, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.logManagementService.addLogManagement(auEmployee,
					ToolUtils.getHostAddress(), flag,
					LogModule.GS_GOODS_TYPE.toString(),
					LogFunction.GS_GOODS_TYPE_HOT.toString(), "");
		}
		return flag;
	}

	@Override
	public Boolean addGsGoodsType(GsGoodsType gsGoodsType, AuEmployee auEmployee) {
		Boolean flag = true;
		try {
			if (gsGoodsType.getGsGoodsType().getId() == null|| "".equals(gsGoodsType.getGsGoodsType().getId())) {
				gsGoodsType.setGsGoodsType(null);
			}
			gsGoodsType.setId(UuidDitch.getId(LogModule.GS_GOODS_TYPE.getModuleNo()));
			gsGoodsType.setHotGsGoodsType(0);
			gsGoodsType.setGsNum(0);
			this.addPo(gsGoodsType, auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(),flag, LogModule.GS_GOODS_TYPE.toString(),LogFunction.GS_GOODS_TYPE_CREATE.toString(),gsGoodsType.getId());
		}
		return flag;
	}

	@Override
	public Boolean deleteGsGoodsType(String  id,AuEmployee auEmployee) {
		Boolean flag = true;
		GsGoodsType gsGoodsType = null;
		try {
			gsGoodsType = (GsGoodsType) this.getOne(id,GsGoodsType.class);
			
			this.removeByState(gsGoodsType);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(
					auEmployee, ToolUtils.getHostAddress(),
					flag, LogModule.GS_GOODS_TYPE.toString(),
					LogFunction.GS_GOODS_TYPE_DELETE.toString(),
					gsGoodsType.getId());
		}
		return flag;
	}

	@Override
	public Boolean updateGsGoodsType(GsGoodsType gsGoodsType,AuEmployee auEmployee) {
		Boolean flag = true;
		GsGoodsType oldGsGoodsType = null;
		try {
			oldGsGoodsType = (GsGoodsType) this.getOne(gsGoodsType.getId(), GsGoodsType.class);
			oldGsGoodsType.setName(gsGoodsType.getName());
			this.modify(oldGsGoodsType,auEmployee);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			this.logManagementService.addLogManagement(
					auEmployee, ToolUtils.getHostAddress(),
					flag, LogModule.GS_GOODS_TYPE.toString(),
					LogFunction.GS_GOODS_TYPE_UPDATE.toString(),
					oldGsGoodsType.getId());
		}
		return flag;
	}
}