<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>按钮管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auButton.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
	</style>
</head>
<body>
	<!--菜单的id-->
	<input type="hidden" id="authorityIdBase" name="authorityIdBase" />
		<div>
  		<table>
    		<tr>
      			<td> 按钮名称：<input type="text" id="btnNameSearch" value="" />&nbsp;</td>
      			<td> 功能名称：<input name="authorityNameSearch" id="authorityNameSearch" readonly="true" data-options="required:true" style="width:200px;background-color: #B2DFEE;cursor:pointer;" ondblclick="selectAuth(1)"/>
      			</td>
        		<td>&nbsp;<input type="button" onclick="search()" value="查询" /></td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="newObj()">添加按钮 </a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editObj()">修改按钮</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj()">删除按钮</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"	style="width: 700px; height: 430px; padding: 30px 30px; top:10px;" >
  		<div class="ftitle" style="margin:10px;"></div>
  		<form id="fm" novalidate method="post">
  			<!--按钮的id-->
  			<input type="hidden" id="id" name="id" />
  			<!--按钮所属功能的id-->
  			<input type="hidden" id="authorityId" name="authorityId" />
  			
  			<table class="mytable2" id="mytable" cellspacing="0"> 
  				<input type="hidden" id="enterpriseId" name="enterpriseId"/>
 				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">按钮名称</th> 
   					<td class="shandan"><span><input name="btnName"  id="btnName" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:200px;"/></span></td> 
  				</tr>
				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">方法名称</th> 
   					<td class="shandan"><span><input name="funName"  id="funName" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width:200px;"/></span></td> 
  				</tr>
				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">所属功能</th> 
   					<td class="shandan"><span>
   						<input name="authorityName" id="authorityName" class="easyui-validatebox" readonly="true" data-options="required:true" style="width:200px;background-color: #B2DFEE;cursor:pointer;" ondblclick="selectAuth(2)" />
  					</td> 
  				</tr
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

	<table id="list" style="height:410px;" >
	</table>
	
	
	
	<!--选择功能弹出框-->
	<div id="auDialog" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons-auDialog" style="width: 900px; height: 460px; padding: 10px 10px; top:1px;" >
		<div>
	  		<table>
	    		<tr>
	      			<td> 菜单功能名称：<input type="text" id="auName" />
        				<input type="button" onclick="auSearch()" value="查询" />
        			</td>
	    		</tr>
	  		</table>
		</div>
		<div id="aulist" style="height:330px;" >
		</div>
		<div id="dlg-buttons-auDialog">
	  		<a href="javascript:saveAu();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	  		<a href="javascript:void(0)" class="easyui-linkbutton" 
	        	iconCls="icon-cancel" onclick="javascript:$('#auDialog').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>