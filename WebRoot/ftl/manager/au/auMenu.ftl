<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>菜单组管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auMenu.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
	</style>
</head>
<body>
	<div>
  		<table>
    		<tr>
      			<td> 菜单组名称：<input type="text" id="name" />
        			&nbsp;<input type="button" onclick="search()" value="查询"/>
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="newObj()">添加菜单组 </a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editObj()">修改菜单组</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj()">删除菜单组</a>
	</div>
	
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"
			style="width: 600px; height: 330px; padding: 30px 30px; top:10px;" >
  		<div class="ftitle" style="margin:10px;"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">名&nbsp;&nbsp;称</th> 
   					<td class="shandan"><span><input name="name"  id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:200px;"/></span></td> 
  				</tr>
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">说&nbsp;&nbsp;明</th> 
   					<td class="shandan"><textarea id="notes" name="notes" style="width:400px;height:100px;" class="easyui-validatebox" data-options="required:false,validType:'length[0,200]'"></textarea></td> 
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
	
	<div id="dlg2" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 950px; height: 450px; padding: 10px 10px;fitColumns:true;top:5px;" >
		<div>
  			<table>
    			<tr>
      				<td> 功能名称：<input type="text" id="name2" />
        				<input type="button" onclick="search2()" value="查询" />
        			</td>
    			</tr>
  			</table>
		</div>
		<form id="fm2" novalidate method="post">
			<input type="hidden" id="menuId" name="menuId"/>
			<table id="list2" style="height:350px;" ></table>
		</form>
	</div>
		<table id="list" style="height:400px;" >
		</table>
</body>
</html>