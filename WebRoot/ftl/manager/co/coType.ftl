<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>栏目内容管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/co/coType.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
	</style>
	</head>
	<body>
	<div id="toolbar">
	  		<a href="javascript:void(0)" class="easyui-linkbutton btn" 
    		iconCls="icon-add" plain="true" onclick="newObj(0)" >添加类型</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editObj()">修改类型</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj()">删除类型</a>
    	
	</div>
	<div id="dlg" class ="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"
	  style="width:800px; height:300px; padding: 30px 30px; top:50px;">
	  <div class="ftitle" style="margin:10px;"></div>
  		
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<input type="hidden" id="level" name="level" />
      		<input type="hidden" id="pid" name="superCoType.id" />
  				<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">类&nbsp;型&nbsp;名</th> 
   					<td class="shandan" colspan="3"><span><input name="name" id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:300px;"/></span><span id="mess" style="color:red; margin-left:10px;"></span></td> 
  				</tr>
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">英&nbsp;文&nbsp;名</th> 
   					<td class="shandan" colspan="3"><span><input name="engName" id="engName" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:300px;"/></span><span id="msg" style="color:red; margin-left:10px;"></span></td> 
  				</tr>
  				<tr> 
 				 	<th style="height:41px;width:250px;" scope="row" abbr="L2 Cache" class="specalt">父商品分类名称</th> 
   					<td class="shandan"><span><input name="pname" id="pname" disabled="disabled" class="easyui-validatebox" style="width:200px;"/></span></td> 
  				</tr>
	            <tr>
  					<td colspan="4" style="text-align:right;" style="height:45px;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			   </table>
  		</form>
  		</div>
	<table id="list" style="height:410px;" >
	</table>
	</html>
	