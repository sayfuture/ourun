
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>长期二维码管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/diPermanent.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
			<!--	长期二维码名称：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search" disabled /> -->
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDiPermanentbutton()">创建二维码 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editDiPermanentbutton()">修改长期二维码卡券 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyDiPermanentbutton()">删除二维码</a>
	</div>
	<table id="diPermanentlist" style="height:410px;" ></table>
	<div id="dlg_diPermanent" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:600px; height:220px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="diPermanentfm" novalidate method="post">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="meid" name="meMember.id" />
			<input type="hidden" id="cardid" name="diCard.id" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">店员：</th> 
					<td class="shandan"><span>
					<input name="meName"  id="meName" class="easyui-validatebox" data-options="required:true" readonly style="width:200px;background-color: #B2DFEE;cursor:pointer;""  ondblclick="choiceMe()"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">卡券信息：</th> 
					<td class="shandan"><span>
					<input name="cardName"  id="cardName" class="easyui-validatebox" data-options="required:true"  readonly style="width:200px;background-color: #B2DFEE;cursor:pointer;""  ondblclick="choiceCard()"/></span></td> 
				</tr>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveDiPermanentbutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_diPermanent').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg_member" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:500px; height:480px; padding:1px 1px;top:1px;">
		<table id="memberlist" style="height:410px;" ></table>
		<a href="javascript:getmember();" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_member').dialog('close')">取消</a>
	</div>
	
	<div id="dlg_Card" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:600px; height:480px; padding:1px 1px;top:1px;">
		<table id="cardlist" style="height:410px;" ></table>
		<a href="javascript:getcard();" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_Card').dialog('close')">取消</a>
	</div>
	
</body>
</html>
