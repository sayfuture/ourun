
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>优惠券核销管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/diProcessRecord.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				优惠券核销名称：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search"  />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDiProcessRecordbutton()">新增优惠券核销 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editDiProcessRecordbutton()">修改优惠券核销 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyDiProcessRecordbutton()">删除优惠券核销</a>
	</div>
	<table id="diProcessRecordlist" style="height:410px;" ></table>
	<div id="dlg_diProcessRecord" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:600px; height:420px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
	</div>
</body>
</html>
