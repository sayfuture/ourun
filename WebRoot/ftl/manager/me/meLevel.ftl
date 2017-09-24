<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员级别管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/me/meLevel.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
	</style>
</head>
<body>
	<div>
  		<table>
    		<tr>
      			<td> 
      				会员级别名称：<input type="text" id="levelNameForSearch" />
        		&nbsp;	<input type="button" onclick="search()" value="查询" id="search"  />
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="addMemberLevel()">新增会员级别 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editMemberLevel()">修改会员级别 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyMemberLevel()">删除会员级别</a>
	</div>

	<table id="list" style="height:340px;" >
	</table>
	
	<div id="dlg_level" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:600px; height:250px; padding:5px 5px;top:20px;">
  		<div class="ftitle"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<input type="hidden" id="state" name="state" />
  			
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">会员级别名称</th> 
   					<td class="shandan"><span><input name="levelName"  id="levelName" class="easyui-validatebox" data-options="required:true,validType:'length[2,12]'" style="width:200px;"/></span></td> 
  				</tr>
 				<tr> 
 					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">起始积分</th> 
   					<td class="shandan"><span><input name="startPosition"  id="startPosition" class="easyui-validatebox" data-options="required:true" style="width:200px;"/></span></td>
 				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">结束积分</th> 
   					<td class="shandan"><span><input name="endPosition"  id="endPosition" class="easyui-validatebox" data-options="required:true" style="width:200px;"/></span></td>
				</tr>
  				<tr>
  					<td colspan="4" style="text-align:right;">
  						<a href="javascript:saveMemberLevel();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg_level').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
    		
  		</form>
	</div>
</body>
</html>