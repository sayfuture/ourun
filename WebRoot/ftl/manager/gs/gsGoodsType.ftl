<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品分类管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/gs/gsGoodsType.js?v=4" charset="utf-8"></script>
	<style> label{letter-spacing:6px;}
			label.lab{letter-spacing:2px;}
			input,textarea{margin-top:5px;}
	</style>
</head>
<body>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="newGsGoodsType(0)">添加商品目录 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editGsGoodsType()">修改商品分类</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyGsGoodsType()">删除商品分类</a>
    	&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="setHotGsGoodsType()">设置热门分类</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 550px; height: 270px; padding: 10px 10px;top:20px;" >
  		<div class="ftitle" style="margin:20px;"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="level" name="level" />
      		<input type="hidden" id="pid" name="gsGoodsType.id" />
      		<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;width:60px;" scope="row" abbr="L2 Cache" class="specalt">商品分类名称</th> 
   					<td class="shandan"><span><input name="name"  id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'" style="width:250px;"/></span><span id="mess"></span></td> 
  				</tr>
  				 <tr> 
 				 	<th style="height:41px;width:60px;" scope="row" abbr="L2 Cache" class="specalt">父商品分类名称</th> 
   					<td class="shandan"><span><input name="pname" id="pname" disabled="disabled" class="easyui-validatebox" style="width:250px;"/></span></td> 
  				</tr>
  				<tr>
  					<td colspan="2" style="text-align:right;">
  						<a href="javascript:saveGsGoodsType();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>
	<table id="list" style="height:300px;" ></table>
	
	<!-- 批量导入窗口
	<div id="batchAdd" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"
			style="width: 550px; height: 270px; padding: 30px 30px;top:100px;" >
  		
  		<form id="fileForm" novalidate method="post" enctype="multipart/form-data">
  			<table class="mytable2" cellspacing="0"> 
  				<tr><td class="shandan">
  					请选择商品分类文件：<input type="file" id="fileName" name="fileName" />
  				</td></tr>
  				<tr><td class="shandan" align="right">
		  			<a href="javascript:doSubmit();" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
		  			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
		  				onclick="javascript:$('#batchAdd').dialog('close')">取消</a>
		  		</td></tr>
  				
  		</form>
	</div> 
	<div id="msg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"
			style="width: 550px; height: 150px; padding: 30px 30px;top:100px;" >
		<font size="3">数据读取中，请稍等...</font>
	</div>-->

</body>
</html>