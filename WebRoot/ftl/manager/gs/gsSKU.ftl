<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品发布</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/gs/gsSKUShow.js" charset="utf-8"></script>
	

	<style>
	label{letter-spacing:8px;}
	th {
		width: 8%;
	}
	</style>
</head>
<body>

	<div>
  		<table>
  			<tr>
      			<td> 
      				商品规格名称：<input type="text" id="gsPropsNameForSearch" />
      				&nbsp;商品名称：<input type="text" id="gsNameForSearch" />
      				&nbsp;商品PV值：<input type="text" id="gsPVForSearch" />
      			&nbsp;	是否绑定：<select id="bindForSearch" class="easyui-combobox" data-options="panelHeight:'auto'" style="margin-left:1px;width:100px;height:25px;">
      							<option value="-1">忽略</option>
	      						<option value="1">是</option>
	      						<option value="0">否</option>
	      					</select>
        		</td>
      			<td> 
      				&nbsp;<input type="button" onclick="searchForGsSKU()" value="查询" id="search"  />
      			</td>
      		</tr>
  		</table> 
	</div>
		<div id="toolbar">
    	
   		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-undo" plain="true" onclick="gsbunding()">商品绑定</a>	
    	<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-undo" plain="true" onclick="gsunbunding()">商品解绑</a>	
    	
	</div>
	<table id="list" style="height:340px;" >
	</table>
	
	<div id="dlg_gsBunding" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:450px; height:500px; padding:5px 5px;top:10px;">	 
	  	 	   <input type="hidden" name="skuid" id="skuid">
	  	  <div>    
	  	       商品代码：<input type="text" id="goodsCodeForSearch" style="width:100px"/>
      		商品名称：<input type="text" id="goodsNameForSearch" style="width:100px"/>
      		<input type="button" onclick="searchForBiGoods()" value="查询" id="searchBiGoods" />
	  	  </div>
	  	  <table  id="mytable" style="height:340px;"> 
      		</table>
      		<br>
      		<br>
      		<table id="mytable1" cellspacing="0" width=100%>
      		    <tr>
      				<td  style="text-align:right;">
      					<a href="javascript:bunding();" class="easyui-linkbutton" iconCls="icon-ok">绑定</a>&nbsp;&nbsp;&nbsp;&nbsp;
      					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_gsBunding').dialog('close');">取消</a>
      				</td>
      			</tr>
 			</table> 
	 </div>
</body>
</html>