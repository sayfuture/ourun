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
	<script type="text/javascript" src="${base}/js/gs/gsAdditional.js" charset="utf-8"></script>
	
	<script type="text/javascript">
    window.onload = function()
    {
        CKEDITOR.replace( 'goodsDesc', {
		height : 500
		});
	};
	</script>
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
      				商品名称：<input type="text" id="gsNameForSearch" />
        		</td>
      			&nbsp;<td> 
      				<input type="button" onclick="searchForGsName()" value="查询" id="search"  />
      			</td>
      		</tr>
  		</table> 
	</div>
		<div id="toolbar">
    	
   		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-undo" plain="true" onclick="gsAdd()">商品补货</a>	
    	
	</div>
	<table id="list" style="height:340px;" >
	</table>
	
	<div id="dlg_gsAdditonal" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:350px; height:200px; padding:5px 5px;top:10px;">
	  	 	<form id="myform" >
	  	 	   <input type="hidden" name="id" id="id">
	  	  <table  id="mytable" cellspacing="0"> 
      		<tr> 
      			<th style="width:10px; height:41px;" scope="row" abbr="L2 Cache" >增加商品数量:</th>
      				<td class="shandan"><span><input name="addNum" id="addNum"  class="easyui-numberbox" min="1" max="10000" missingMessage="必须填写1~10000之间的数字" data-options="validType:'length[1,5]',digits:true" style="margin-left:-1px;width:200px;height:25px;"/></span></td> 
      		</tr>
      		</table>
      		<br>
      		<br>
      		<table id="mytable1" cellspacing="0" width=100%>
      		    <tr>
      				<td  style="text-align:right;">
      					<a href="javascript:saveGsAdd();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
      					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_gsAdditonal').dialog('close');">取消</a>
      				</td>
      			</tr>
 			</table> 
	 		</form>
	 </div>
</body>
</html>