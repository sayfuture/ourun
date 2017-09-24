package com.mxcx.erp.di.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.di.dao.entity.DiProcessRecord;
import com.mxcx.erp.di.service.DiProcessRecordService;

/**
 * DiProcessAction Thu Dec 29 20:53:47 CST 2016 hmy
 */

@Controller
public class DiProcessRecordAction extends BaseController {

	@Autowired
	private DiProcessRecordService diProcessRecordService;

	@RequestMapping(value = "/manager/erp/di/diProcessRecord.do")
	public String index() {
		return "/ftl/manager/di/diProcessRecord";
	}

	@RequestMapping("/manager/erp/di/findDiProcessRecordList.do")
	@ResponseBody
	public DataGrid findDiProcessRecordList(HttpServletRequest request,@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = diProcessRecordService.findDiProcessRecordList(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/di/addDiProcessRecord.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean addDiProcessRecord(DiProcessRecord diProcessRecord, HttpServletRequest request) {
		return diProcessRecordService.addDiProcessRecord(diProcessRecord,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/modifyDiProcessRecord.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyDiProcessRecord(DiProcessRecord diProcessRecord,
			HttpServletRequest request) {
		return diProcessRecordService.modifyDiProcessRecord(diProcessRecord,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/deleteDiProcessRecord.do")
	@ResponseBody
	public Boolean deleteDiProcessRecord(String ids, HttpServletRequest request) {
		String teamIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != teamIds) {
				for (String id : teamIds) {
					diProcessRecordService.deleteDiProcessRecord(id,
							this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/manager/erp/di/goToModifyDiProcessRecord.do")
	@ResponseBody
	public DiProcessRecord goToModifyDiProcessRecord(String id) {
		DiProcessRecord diProcessRecord = diProcessRecordService.findDiProcessRecordByID(id);
		return diProcessRecord;
	}
}
