package com.mxcx.erp.me.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.me.dao.entity.MeMember;
import com.mxcx.erp.me.service.IMeMemberStatisticsService;

/**
 * 会员统计
 * @author 孙瑞 2014/10/21
 */
@Controller
@RequestMapping("/manager/erp/me/statistics")
public class MeMemberStatisticsAction extends BaseController {

	@Autowired
	private IMeMemberStatisticsService memberStatisticsService;
	@RequestMapping(value = "/statistics.do")
	public String index() {
		return "ftl/manager/me/meMemberStatistics";
	}
	
	@RequestMapping(value = "/getCharts.do")
	@ResponseBody
	public Object getCharts(String start, String end) {
		System.out.println(start+"  "+end);
		Chart chart = new Chart();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = sdf.parse(start);
			Date endDate =  sdf.parse(end);
			
			if( !startDate.before(endDate) && !startDate.equals(endDate) ) {
				return chart.retmsg("ERROR", "info", "结束日期不能小于开始日期");
			}
			
			//验证：查询周期不得超过一年
			
			PieChart pieChart = new PieChart();
			Map<String, Integer> indexMap = new HashMap<String, Integer>();
			List<LineChartNode> dataList = new ArrayList<LineChartNode>();
			int num = -1;
			
			Calendar cs = Calendar.getInstance();
			cs.setTime(startDate);
			Calendar ce = Calendar.getInstance();
			ce.setTime(endDate);
			
			String startGroupNm = cs.get(Calendar.YEAR) + "年" + (cs.get(Calendar.MONTH)+1) + "月";
			String endGroupNm   = ce.get(Calendar.YEAR) + "年" + (ce.get(Calendar.MONTH)+1) + "月";
			String groupNm = null;
			do {
				groupNm = startGroupNm;
				indexMap.put(groupNm, ++num);
				dataList.add(new LineChartNode().setTimeNode(groupNm));
				cs.set(Calendar.MONTH, cs.get(Calendar.MONTH)+1);//下个月
				startGroupNm = cs.get(Calendar.YEAR) + "年" + (cs.get(Calendar.MONTH)+1) + "月";
			} while (!groupNm.equals(endGroupNm));
			
			//查询会员记录
			List<MeMember> members = memberStatisticsService.getMembers(startDate, endDate);
			
			for(int i=0; i<members.size(); i++) {
				MeMember member = members.get(i);
				
				Calendar c = Calendar.getInstance();
				c.setTime(member.getCreateDate());
				String currGroupNm = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH)+1) + "月";
				
				Integer index = indexMap.get(currGroupNm);
				if (index == null) {
					//Exception：
				}
				LineChartNode lcn = dataList.get(index);
				if (lcn == null) {
					//Exception：
				}
				
				//注册方式： 0：网站注册 1：手机注册
				int registerMethod = member.getRegisterMethod();
				if(registerMethod == 1) {
					lcn.phoneCreate++;
					pieChart.phoneCreate++;
				}
				else {
					lcn.websiteCreate++;
					pieChart.websiteCreate++;
				}
				
				//验证方式：1-手机
//				int validationMethod = member.getValidationMethod();
//				if(validationMethod == 1) {
//					lcn.phoneVerify++;
//					pieChart.phoneVerify++;
//				}
//				else {
//					lcn.phoneVerify++;
//					pieChart.phoneVerify++;
//				}
			}//end FOR
			
			chart.lineChart = dataList;
			chart.pieChart = pieChart;
		}
		catch (Exception e) {
			return chart.retmsg("ERROR", "error", "统计数据生成失败");
		}
		return chart;
	}
	
	private class Chart {
		public List<LineChartNode>  lineChart = null;
		public PieChart  pieChart = null;
		public String[] retmsg = new String[3];
		
		public Chart retmsg(String retCode, String level, String retDesc) {
			this.retmsg[0] = retCode;
			this.retmsg[1] = level;
			this.retmsg[2] = retDesc;
			return this;
		}
	}
	
	private class LineChartNode {
		public String timeNode = null;//时间
		public int websiteCreate = 0;//网站注册
		public int phoneCreate = 0;//手机注册
//		public int emailVerify = 0;//邮件验证
//		public int phoneVerify = 0;//手机验证
		@Override
		public String toString() {
			return "LineChart [时间=" + timeNode + ", 网站注册=" + websiteCreate
					+ ", 手机注册=" + phoneCreate +
//							", 手机验证=" + phoneVerify +
							"]";
		}
		public LineChartNode setTimeNode(String timeNode) {
			this.timeNode = timeNode;
			return this;
		}
	}//end inner class LineChart
	
	private class PieChart {
		public int websiteCreate = 0;//网站注册
		public int phoneCreate = 0;//手机注册
//		public int emailVerify = 0;//邮件验证
//		public int phoneVerify = 0;//手机验证
		@Override
		public String toString() {
			return "LineChart [网站注册=" + websiteCreate
					+ ", 手机注册=" + phoneCreate +  
//					", 手机验证=" + phoneVerify + 
					"]";
		}
	}//end inner class PieChart
}