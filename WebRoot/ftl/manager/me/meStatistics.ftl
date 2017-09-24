<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>店员统计</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/me/meStatistics.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
	</style>
</head>
<body>
	<div>
  		<table>
    		<tr>
      			<td> 
      				真实名：<input type="text" id="realNameForSearch" />
      				&nbsp;
      				手机号：<input type="text" id="cellphoneForSearch" />
        				
        			&nbsp;<input type="button" onclick="search()" value="查询" id="search"  />
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
	</div>
	
	<table id="list" style="height:410px;" ></table>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:800px; height:450px; padding:5px 5px;top:20px;">
		发送开始时间开始：<input type="text" id="searchstart_time" name="searchstart_time" class="easyui-datebox"/>
		发送结束时间结束：<input type="text" id="searchend_time" name="searchend_time" class="easyui-datebox"/>
		<input type="button" onclick="statisticsSearch()" value="查询" id="searchForStatistics"  />
		<table id="weCustomerlist" style="height:410px;" ></table>
	</div>		
	
</body>
</html>