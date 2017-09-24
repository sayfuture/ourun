
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>集客券发送记录管理管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/diSendRecode.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				集客券发送记录管理名称：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search" disabled />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDiSendRecodebutton()">新增集客券发送记录管理 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editDiSendRecodebutton()">修改集客券发送记录管理 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyDiSendRecodebutton()">删除集客券发送记录管理</a>
	</div>
	<table id="diSendRecodelist" style="height:410px;" ></table>
	<div id="dlg_diSendRecode" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:600px; height:420px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="diSendRecodefm" novalidate method="post">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="state" name="state" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">openid</th> 
					<td class="shandan"><span><input name="openid"  id="openid" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">cardid</th> 
					<td class="shandan"><span><input name="cardid"  id="cardid" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">meid</th> 
					<td class="shandan"><span><input name="meid"  id="meid" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">companyid</th> 
					<td class="shandan"><span><input name="companyid"  id="companyid" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">sharenum</th> 
					<td class="shandan"><span><input name="sharenum"  id="sharenum" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">new_weuser</th> 
					<td class="shandan"><span><input name="new_weuser"  id="new_weuser" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveDiSendRecodebutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_diSendRecode').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
