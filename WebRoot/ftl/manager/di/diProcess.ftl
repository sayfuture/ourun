
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
	<script type="text/javascript" src="${base}/js/di/diProcess.js" charset="utf-8"></script>
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
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="customDiProcessbutton()">核销优惠券 </a>
	</div>
	<table id="diProcesslist" style="height:410px;" ></table>
	<div id="dlg_diProcess" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:430px; height:300px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="diProcessfm" novalidate method="post">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="state" name="state" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">核销数量</th> 
					<td class="shandan"><span><input name="customerNums"  id="customerNums" class="easyui-numberbox" data-options="required:true,min:0,max:1000" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">客户名称</th> 
					<td class="shandan"><span><input name="customerName"  id="customerName" class="easyui-validatebox" data-options="validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">客户手机号</th> 
					<td class="shandan"><span><input name="customerPhone"  id="customerPhone" class="easyui-validatebox" data-options="validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveDiProcessbutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_diProcess').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
