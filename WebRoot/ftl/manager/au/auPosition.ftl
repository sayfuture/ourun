<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auPosition.js" charset="utf-8"></script>

	<style>
			.datagrid-cell,
			.datagrid-cell-group,
			.datagrid-header-rownumber,
			.datagrid-cell-rownumber {
			  margin: 0;
			  padding: 0 4px;
			  white-space:normal;
			  word-wrap: normal;
			  overflow: hidden;
			  height: 18px;
			  line-height: 18px;
			  font-size: 12px;
			}


			.datagrid-btable .datagrid-cell{
				padding:4px 4px;overflow: hidden;text-overflow:ellipsis;white-space:normal;
			}  
	
	</style></head>
<body>
	<div>
		<table>
			<tr>
  				<td> 角色名称：<input type="text" id="name" />
    				&nbsp;<input type="button" onclick="search()" value="查询" disabled id="search"/>
    			</td>
			</tr>
		</table>
	</div>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newObj()">添加角色</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editObj()">修改角色</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyObj()">删除角色</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 550px; height: 240px; padding: 30px 30px;top:100px" >
  		<div class="ftitle" style="margin:10px;"></div>
  			<form id="fm" novalidate method="post">
  				<input type="hidden" name="id" id="id" />
  				<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th class="specalt">名&nbsp;&nbsp;称</th> 
   					<td class="shandan"><input name="name"  id="uname" class="easyui-validatebox"  data-options="required:true,validType:'length[2,10]'" style="width:200px;"/></td> 
  				</tr>
    			<tr>
  					<td colspan="2" style="text-align:right;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>
	

	<!--角色列表-->
	<table id="list" style="height:440px;" ></table>
	<!--功能设置弹出框-->
	<div id="dlg2" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 950px; height: 490px; padding: 0px 0px;top:0px;fitColumns:true;" >
		<form id="fm2" novalidate method="post">
			<input type="hidden" id="posId" name="positionId"/>
			<table id="list2" style="height:370px;" ></table>
			<div style="color:blue; margin-top:4px;">
	    			友情提示：请认真选择，以免出错！
	    	</div>
		</form>
		
		<div id="dlg-buttons">
			<a href="javascript:saveUser2();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>