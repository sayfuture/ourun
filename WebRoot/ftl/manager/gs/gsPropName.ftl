<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品属性维护</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/gs/gsPropName.js" charset="utf-8"></script>
	<style>
		label {cursor: pointer;}
		input[type="checkbox"]{vertical-align:middle;}  
	</style>
</head>
<body>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="addGsPropName()">添加商品属性 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editGsPropName()">修改商品属性 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyGsPropName()">删除商品属性</a>
	</div>
	<div>
  		<table>
    		<tr>
      			<td> 
      				商品属性名称：<input type="text" id="propNameForSearch" />
        			&nbsp;<input type="button" onclick="searchGsPropName()" value="查询" id="search" disabled />
        		</td>
    		</tr>
  		</table>
	</div>
	<table id="list" style="height:200px;" >
	</table>
	<div id="dlg" class="easyui-dialog" closed="true" buttons="#dlg-buttons" style="width:600px; height:420px; padding:30px 30px;top:50px;">
  		<div class="ftitle"></div>
  		<form id="form" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<input type="hidden" id="state" name="state" />
  			<table class="mytable" id="mytable" cellspacing="0"> 
 				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">属性名称</th> 
   					<td class="shandan"><span><input name="propName"  id="propName" class="easyui-validatebox" data-options="required:true,validType:'length[2,12]'" style="width:200px;"/></span></td> 
  				</tr>
  				
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">排序号</th> 
   					<td class="shandan"><span><input name="seqNo"  id="seqNo" class="easyui-numberbox" min="1" max="100" missingMessage="必须填写1~100之间的数字" data-options="required:true,validType:'length[1,3]',digits:true" style="margin-left:-1px;width:200px;height:25px;" /></span></td> 
  				</tr>
  				
  				<div id="selectSub"> </div> 
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt" valign="top">属性选项</th> 
   					<td class="shandan">
   						<span><input type="checkbox" name="isColorProp" id="isColorProp" class="easyui-checkbox" value="1" /><label for="isColorProp">颜色属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isSizeProp" id="isSizeProp" class="easyui-checkbox" value="1" /><label for="isSizeProp">尺码属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isSaleProp" id="isSaleProp" class="easyui-checkbox" value="1" /><label for="isSaleProp">销售属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isKeyProp" id="isKeyProp" class="easyui-checkbox" value="1" /><label for="isKeyProp">关键属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isFilterProp" id="isFilterProp" class="easyui-checkbox" value="1" /><label for="isFilterProp">搜索属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isCustProp" id="isCustProp" class="easyui-checkbox" value="1" /><label for="isCustProp">自定义属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isMust" id="isMust" class="easyui-checkbox" value="1" /><label for="isMust">必需属性</label></span>
   						<br />
   						<span><input type="checkbox" name="isMulti" id="isMulti" class="easyui-checkbox" value="1" /><label for="isMulti">多选属性</label></span>
   					</td> 
  				</tr>
  				<tr>
  					<td colspan="4" style="text-align:right;">
  						<a href="javascript:saveGsPropName();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close');">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>
	
	<div id="dlg_gsPropValueList" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 450px; height: 450px; padding: 10px 30px;fitColumns:true;top:100px;" >
		<div id="toolbar_gsPropValueList">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="addGsPropValue()">添加属性值 </a>
  			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editGsPropValue()">修改属性值 </a>
  			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyGsPropValue()">删除属性值</a>
		</div>
		<div>
  			<table>
    			<tr>
      				<td> 属性值：<input type="text" id="propValueForSearch" />
        				<input type="button" onclick="searchGsPropValue()" value="查询" />
        			</td>
    			</tr>
  			</table>
		</div>
		<form id="form_gsPropValueList" novalidate method="post">
			<input type="hidden" id="propId" name="propId" />
			<table id="list_gsPropValueList" style="height:300px;" ></table>
		</form>
	</div>
	
	<div id="dlg_gsPropValueForm" class="easyui-dialog" closed="true" buttons="#dlg-buttons" style="width:600px; height:240px; padding:30px 30px;top:50px;">
  		<div class="ftitle"></div>
  		<form id="form_gsPropValue" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<input type="hidden" id="propId" name="propId" />
  			<input type="hidden" id="state" name="state" />
  			<table class="mytable" id="mytable" cellspacing="0"> 
 				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">属性值</th> 
   					<td class="shandan"><span><input name="propValue"  id="propValue" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
  				</tr>
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">排序号</th> 
   					<td class="shandan"><span><input name="seqNo"  id="seqNo" class="easyui-numberbox" min="1" max="100" missingMessage="必须填写1~100之间的数字" data-options="validType:'length[1,3]',digits:true" style="margin-left:-1px;width:200px;height:25px;" /></span></td> 
  				</tr>
  				
  				<tr>
  					<td colspan="4" style="text-align:right;">
  						<a href="javascript:saveGsPropValue();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_gsPropValueForm').dialog('close');">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>
	
	
	<table id="list1" style="height:200px;" >
	</table>
</body>
</html>