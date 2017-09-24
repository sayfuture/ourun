/**
 * 会员统计
 */
$(function() {
	Highcharts.setOptions({
		 colors: ['#8085e8', '#F45B5B', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#7cb5ec', '#8d4653', '#91e8e1']
	});
	showCharts();
});


function showCharts() {
	clear();
	var startDate = $('#startDate').datebox('getValue');
		startDate = startDate == false ? new Date().getFullYear()+"-01-01" : startDate;
	var endDate = $('#endDate').datebox('getValue');
		endDate = endDate == false ? new Date().getFullYear()+"-12-31" : endDate;
	
	$.post(
			"getCharts.do"
		,
		{
			start : startDate,
			end: endDate
		},
		function(data) {
//			alert(data);
			data = $.parseJSON(data);
//			alert(data);
			if(data.retmsg[0] == "ERROR") {
				$.messager.alert('操作提示', data.retmsg[2], data.retmsg[1]);
				return;
			}
			
			var lc = data.lineChart;//折线图数据
			var pc = data.pieChart;//饼图数据
			
			var categories = [];
			var websiteCreate=[];//网站注册
			var phoneCreate=[];//手机注册
//			var phoneVerify=[];//手机验证
			for(var i=0; i<lc.length; i++) {
				var lcn = lc[i];
				categories[i] = lcn.timeNode;
				websiteCreate[i] = lcn.websiteCreate;//这里
				phoneCreate[i] = lcn.phoneCreate;
//				phoneVerify[i] = lcn.phoneVerify;
			}
			var lo = {
					renderTo: 'container4line',
					categories: categories,
					series: [
					         { name:'网站注册', data: websiteCreate },
					    	 { name:'手机注册', data: phoneCreate }
//					    	 { name:'手机验证', data: phoneVerify }
					    	 ]
				};
			//折线图：注册方式、验证方式
			new Highcharts.Chart(getLine(lo));
			
			var po1 = {
					renderTo: 'container4pie1',
					title: '注册方式',
					series: [{
				    	name: '注册数量',
				    	data: [
				    		['网站', pc.websiteCreate],
				    		['手机', pc.phoneCreate]
				    	]
				    }]
				};
			//饼图1：注册方式
			new Highcharts.Chart(getPie(po1));
			
//			var po2 = {
//					renderTo: 'container4pie2',
//					title: '验证方式',
//					series: [{
//				    	name: '验证数量',
//				    	data: [
//				    		['手机', pc.phoneVerify]
//				    	]
//				    }]
//				};
//			//饼图2：验证方式
//			new Highcharts.Chart(getPie(po2));
			
			//统计表
			$("#websiteCreate").html(pc.websiteCreate);
			$("#phoneCreate").html(pc.phoneCreate);
//			$("#emailVerify").html(pc.emailVerify);
//			$("#phoneVerify").html(pc.phoneVerify);
		}//END CALLBACK
	);//END POST
}//END FUNCTION getMembers

function clear() {
	$("#container4line").html("");
	$("#container4pie1").html("");
	$("#container4pie2").html("");
	$("#websiteCreate").html("");
	$("#phoneCreate").html("");
	$("#phoneVerify").html("");
}

/**
 * 折线图
 */
var	getLine = function(o) {
	return {
       chart: {
          renderTo: o.renderTo,
          type : "line",
          borderWidth: 0,
          width: 1200,
          height: 500
       },
       
       credits: {
           enabled: false
       },
       
       title: {
    	   text: '会员统计'
       },
       
//	   subtitle: {
//		   text: '2014-03-15 至 2014-10-22'
//	   },
	       
       xAxis: {
    	   categories: o.categories
       },
       
       yAxis: {
    	   title: {
    		   text: ''
    	   }
       },
       
       plotOptions: {
    	   line: {
    		   dataLabels: {
    			   enabled: true
    		   },
    		   enableMouseTracking: false
    	   }
       },
       
       series: o.series
	};//END RETURN
};


/**
 * 饼状图
 */
var	getPie = function(o) {
	return {
		chart: {
			renderTo: o.renderTo,
			type : "pie",
			borderWidth : 0,
//			width:500,
//			height:400,
			options3d: {
				enabled: true,
				alpha: 45
			}
		},
		
		credits: {
			enabled: false
	    },
		
		title: {
            text: o.title
        },
		
//        subtitle: {
//        	text: ''
//        },
        
        plotOptions: {
            pie: {
                innerSize: 100,
                depth: 45
            }
        },
        
	    series: o.series
	};
};


