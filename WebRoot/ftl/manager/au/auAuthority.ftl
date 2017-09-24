<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>功能管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auAuthority.js" charset="utf-8"></script>
</head>
<body>
	<div>
  		<table>
    		<tr>
      			<td> 菜单功能名称：<input type="text" id="name" />
        			&nbsp;<input type="button" onclick="search()" value="查询" id="search" />
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newObj()">添加功能 </a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editObj()">修改功能</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyObj()">删除功能</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 700px; height: 370px; padding: 5px 5px; top:10px;" >
  		<div class="ftitle" style="margin:10px;"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th class="specalt">中文名称</th> 
   					<td class="shandan1"><span><input name="name"  id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:200px;" /></span></td>
   				</tr>
   				 <tr> 
 				 	<th class="specalt">英文名称</th> 
   					<td class="shandan1"><span><input name="ename"  id="ename" class="easyui-validatebox" data-options="required:true,validType:'length[2,20]'" style="width:200px;" /></span></td>
   				</tr>
   				 <tr> 
 				 	<th class="specalt">路&nbsp;&nbsp;径</th> 
   					<td class="shandan1"><span><input name="url" id="upwd" class="easyui-validatebox"  data-options="required:true,validType:'length[2,200]'" style="width:400px;" /></span></td>
   				</tr>	
   				<tr> 
 				 	<th class="specalt">备&nbsp;&nbsp;注</th> 
   					<td class="shandan1"><span><textarea id="remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[0,200]'" style="width:400px; height:100px;"></textarea></span></td>
   				</tr>
   				<tr> 
 				 	<th class="specalt">位&nbsp;置&nbsp;设&nbsp;置</th> 
   					<td class="shandan1">
   						<span>
   							<select id="auMenuId" class="easyui-combobox" data-options="panelHeight:'auto'" name="auMenuId" style="width:200px;"></select>
      					</span>
      				</td>
   				</tr>	 
    			<tr>
  					<td colspan="2" style="text-align:right;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>

	<table id="list" style="height:410px;" ></table>
</body>
</html>