package com.mxcx.erp.di.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.di.dao.entity.DiProcess;
import com.mxcx.erp.di.dao.entity.DiProcessRecord;
import com.mxcx.erp.di.service.DiProcessRecordService;
import com.mxcx.erp.di.service.DiProcessService;
import com.mxcx.erp.we.service.WeCustomerService;

/**
 * DiProcessAction Thu Dec 29 20:53:47 CST 2016 hmy
 */

@Controller
public class DiProcessAction extends BaseController {

	@Autowired
	private DiProcessService diProcessService;
	@Autowired
	private WeCustomerService weCustomerService;
	@Autowired
	private DiProcessRecordService diProcessRecordService;

	@RequestMapping(value = "/manager/erp/di/diProcess.do")
	public String index() {
		return "/ftl/manager/di/diProcess";
	}

	@RequestMapping("/manager/erp/di/findDiProcessList.do")
	@ResponseBody
	public DataGrid findDiProcessList(HttpServletRequest request,@ModelAttribute("pp") PageParameter pageParameter) {
		DataGrid dataGrid = diProcessService.findDiProcessList(pageParameter,this.getLoginUser(request));
		return dataGrid;
	}

	@RequestMapping(value = "/manager/erp/di/addDiProcess.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean addDiProcess(DiProcess diProcess, HttpServletRequest request) {
		return diProcessService.addDiProcess(diProcess,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/modifyDiProcess.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyDiProcess(DiProcess diProcess,
			HttpServletRequest request) {
		return diProcessService.modifyDiProcess(diProcess,
				this.getLoginUser(request));
	}

	@RequestMapping(value = "/manager/erp/di/deleteDiProcess.do")
	@ResponseBody
	public Boolean deleteDiProcess(String ids, HttpServletRequest request) {
		String teamIds[] = ids != null ? ids.split(",") : null;
		Boolean flag = true;
		try {
			if (null != teamIds) {
				for (String id : teamIds) {
					diProcessService.deleteDiProcess(id,
							this.getLoginUser(request));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/manager/erp/di/goToModifyDiProcess.do")
	@ResponseBody
	public DiProcess goToModifyDiProcess(String id) {
		DiProcess diProcess = diProcessService.findDiProcessByID(id);
		return diProcess;
	}
	
	@RequestMapping(value = "/manager/erp/di/consumeCard.do")
	@ResponseBody
	public Boolean consumeCard(HttpServletRequest request,String customerNums,String id) {
		Boolean falg=true;
		String customerName=request.getParameter("customerName");
		String customerPhone=request.getParameter("customerPhone");
		Integer num=Integer.valueOf(customerNums);
		DiProcess diProcess=diProcessService.findDiProcessByID(id);
		if(StringUtils.isNotEmpty(customerName)){
			diProcess.getWeCustomer().setCustomer_name(customerName);
		}
		if(StringUtils.isNotEmpty(customerPhone)){
			diProcess.getWeCustomer().setPhone(customerPhone);
		}
		if(num.equals(diProcess.getCard_num())){
			DiProcessRecord diProcessRecord=new DiProcessRecord();
			BeanUtils.copyProperties(diProcess, diProcessRecord);
			diProcessRecord.setStatus(2);
//			diProcessRecord.setProcess_id(diProcess.getId());
			diProcessRecord.setCard_num(0);
			falg=diProcessRecordService.addDiProcessRecord(diProcessRecord, this.getLoginUser(request));
			diProcess.setStatus(2);
			diProcess.setCard_num(0);
			falg=diProcessService.modifyDiProcess(diProcess, this.getLoginUser(request));
			weCustomerService.modifyWeCustomer(diProcess.getWeCustomer(),  this.getLoginUser(request));
		}else{
			diProcess.setCard_num(diProcess.getCard_num()-num);
			falg=diProcessService.modifyDiProcess(diProcess, this.getLoginUser(request));
			weCustomerService.modifyWeCustomer(diProcess.getWeCustomer(),  this.getLoginUser(request));
		}
		return falg;
	}
	
	
}
