<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员统计</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/highcharts/highcharts.js" charset="utf-8"></script> 
	<script type="text/javascript" src="${base}/js/highcharts/modules/exporting.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/highcharts/highcharts-3d.js"></script>
	<!--<script type="text/javascript" src="${base}/js/highcharts/themes/sand-signika.js" charset="utf-8"></script>-->
	<script type="text/javascript" src="${base}/js/me/meMemberStatistics.js" charset="utf-8"></script>
	<style>
		input[type="text"]{width:110px;}
	</style>
</head>
<body bgcolor="white">
	<table><!--class="mytable2" cellspacing="0"-->
		<tr>
			<td>
			开始时间：<input type="text" name="startDate" style="width:150px" id="startDate" class="easyui-datebox" panelWidth=200 editable="false"/>
			&nbsp;结束时间：<input type="text" name="endDate" style="width:150px" id="endDate" class="easyui-datebox" panelWidth=200 editable="false"/>
			&nbsp;<input type="button" onclick="showCharts()" value="查询" />
			</td>
		</tr>
	</table>
	<div style="width:100%; height:2px; border-top:1px solid #4572A7; clear:both;"></div>
	<div id="container4line"></div>
	<div style="width:100%; height:2px; border-top:1px solid #4572A7; clear:both;"></div>

	<div id="container4pie3" style="float:left;width:30%">
		<table class="mytable2" width="100%">
			<tr>
				<td rowspan="2" width="30%" align="center">注册方式</td>
				<td width="30%">网站</td>
				<td id="websiteCreate"></td>
			</tr>
			<tr>
				<td>手机</td>
				<td id="phoneCreate"></td>
			</tr>
			<!--<tr>
				<td rowspan="2" align="center">验证方式</td>
			</tr>
			<tr>
				<td>手机</td>
				<td id="phoneVerify"></td>
			</tr>-->
		</table>
	</div>




	<div id="container4pie1" style="float:left;width:35%"></div>

<!--	<div id="container4pie2" style="float:left;width:35%"></div>-->

</body>
</html>