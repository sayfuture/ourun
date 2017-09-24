
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>扫码发送</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/diScanSend.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				员工姓名：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search" disabled />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
	</div>
	<table id="diScanSendlist" style="height:410px;" ></table>
	<div id="dlg_diScanSend" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:450px; height:280px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="diScanSendfm" novalidate method="post">
			<input type="hidden" id="id" name="id" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<td style="height:82px;" class="specalt" rowspan="2">二维码（一）</td> 
					<td style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">积客券</td> 
					<td class="shandan"><span>
					<input name="diCard.id" id="diCardId" type="hidden"/>
					<input id="diCardName" readonly style="width:200px;background-color: #B2DFEE;cursor:pointer;""  ondblclick="savebind(0)"/></span></td> 
				</tr>
				<tr>
					<td style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">可领取数量</td> 
					<td class="shandan" ><span><input name="get_limit"  id="get_limit" class="easyui-numberbox" data-options="required:true,min:0,max:10000" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<td style="height:82px;" class="specalt" rowspan="2">二维码（二）</td> 
					<td style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">积客券</td> 
					<td class="shandan" ><span>
					<input name="diCard1.id" id="diCardId1" type="hidden"/>
					<input id="diCardName1" readonly style="width:200px;background-color: #B2DFEE;cursor:pointer;""  ondblclick="savebind(1)"/></span></td> 
				</tr>
				<tr>
					<td style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">可领取数量</td> 
					<td class="shandan" ><span><input name="get_limit1"  id="get_limit1" class="easyui-numberbox" data-options="required:true,min:0,max:10000" style="width:200px;"/></span></td>
				</tr>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:savebindDiCardbutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_diScanSend').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg_bindDiCardlist" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:500px; height:480px; padding:1px 1px;top:1px;">
		<table id="diCardlist" style="height:410px;" ></table>
		<div style="float:right;">
			<a href="javascript:savediScanSendbutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" 
					iconCls="icon-cancel" onclick="javascript:$('#dlg_bindDiCardlist').dialog('close')">取消</a>
   		</div>
	</div>
</body>
</html>
