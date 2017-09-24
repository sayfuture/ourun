
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>筛选发送管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/screenSend.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				微信客户名称：<input type="text" id="searchname" />
				预计下次保养时间开始：<input type="text" id="searchstart_time" name="searchstart_time" class="easyui-datebox"/>
				预计下次保养时间结束：<input type="text" id="searchend_time" name="searchend_time" class="easyui-datebox"/>
				<input type="button" onclick="search()" value="查询" id="search"  />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="wxsendbutton()">微信发送集客券</a>
	</div>
	<table id="weCustomerlist" style="height:410px;" ></table>
	<div id="dlg_card" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:800px; height:480px; padding:1px 1px;top:1px;">
		<table id="cardlist" style="height:410px;" ></table>
			
		<a href="javascript:opentemplateCard();" class="easyui-linkbutton" iconCls="icon-ok">选择模板</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_card').dialog('close')">取消</a>
		
	</div>
	<div id="dlg_templateCard" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:800px; height:480px; padding:1px 1px;top:1px;">
		<form id="screenSendfm"  method="post">
			<input type="hidden" id="weCustomerIds" name="weCustomerIds" />
			<input type="hidden" id="cardId" name="cardId" />
			<input type="hidden" id="templateId" name="templateId" />
		</form>
		<table id="templateCardlist" style="height:410px;" ></table>
			
		<a href="javascript:screenSend();" class="easyui-linkbutton" iconCls="icon-ok">发送</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_templateCard').dialog('close')">取消</a>
		
	</div>
	
</body>
</html>
