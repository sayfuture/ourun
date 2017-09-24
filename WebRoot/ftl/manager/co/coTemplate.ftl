
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>消息模板管理管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/co/coTemplate.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				消息模板管理名称：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search"  />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="refreshCoTemplatebutton()">刷新模板列表 </a>
	</div>
	<table id="coTemplatelist" style="height:410px;" ></table>
	<div id="dlg_coTemplate" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:600px; height:420px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="coTemplatefm" novalidate method="post">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="state" name="state" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">title</th> 
					<td class="shandan"><span><input name="title"  id="title" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">content</th> 
					<td class="shandan"><span><input name="content"  id="content" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveCoTemplatebutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_coTemplate').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
