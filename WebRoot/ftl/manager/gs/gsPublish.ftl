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
	<script type="text/javascript" src="${base}/js/gs/gsPublish.js" charset="utf-8"></script>
	
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
	<div id="toolbar">
    	
    	<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-undo" plain="true" onclick="returnPublish()">商品审核退回</a>	
    	&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-redo" plain="true" onclick="startPublish()">商品发布</a>
    	&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-undo" plain="true" onclick="stopPublish()">商品取消发布</a>	
    	
    	
	</div>
	<div>
  		<table>
  			<tr>
      			<td> 
      				商品编码：<input type="text" id="productCodeForSearch" />
        		</td>
      			<td> 
      				商品名称：<input type="text" id="productNameForSearch" />
        		</td>
        		<td> 
      				商品状态：<select id="productStatusForSearch" class="easyui-combobox" data-options="panelHeight:'auto'" style="margin-left:1px;width:100px;height:25px;">
      							<option value="-1">忽略</option>
	      						<option value="0">已发布</option>
	      						<option value="2">审核</option>
	      						<option value="3">取消发布</option>
	      					</select>
        		</td>
      			<td> 
      				<input type="button" onclick="searchPublish()" value="查询" id="search" disabled />
      			</td>
      		</tr>
  		</table>
	</div>
	<table id="list" style="height:410px;" >
	</table>
	
	<div id="dlg_gsGoods" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:1000px; height:450px; padding:5px 5px;top:10px;">
	<div class="easyui-tabs" style="width:930px;height:450px" id="tabs" selected>
  	 	<div title="商品图片管理" style="padding:10px" >
			<table id="picture" style="height:300px;" ></table>
			</table>
		</div>
  	 	<div title="商品图片管理" style="padding:10px" >
			<table id="gsSkulist" style="height:300px;" ></table>
		</div>
	</div>
	 </div>
</body>
</html>