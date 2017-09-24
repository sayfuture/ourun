<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>积分规则管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auPoint.js" charset="utf-8"></script>
</head>
<body>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newPoint()">添加功能 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPoint()">修改功能</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyPoint()">删除功能</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"
			style="width: 700px; height: 430px; padding: 30px 30px; top:10px;" >
  		<div class="ftitle"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th class="specalt">名&nbsp;&nbsp;称</th> 
   					<td class="shandan1"><span><input name="name"  id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,200]'" style="width:200px;" /></span></td>
   				</tr>
   				 <tr> 
 				 	<th class="specalt">点&nbsp;&nbsp;数</th> 
   					<td class="shandan1"><span><input name="pointnum" id="pointnum" class="easyui-numberbox"  data-options="required:true,validType:'length[1,8]'" style="width:200px;" /></span></td>
   				</tr>	
   				<tr> 
 				 	<th class="specalt">调用参数</th> 
   					<td class="shandan1"><span><input name="operation" id="operation" class="easyui-validatebox"  data-options="required:true,validType:'length[1,200]'" style="width:200px;" /></span><font color="red">注：该参数为系统内部调用从参数,不能重复</font></td>
   				</tr>
   				<tr> 
 				 	<th class="specalt">积分类型</th> 
   					<td class="shandan1"><span>
   						<select name="type" id="type" class="easyui-combobox" data-options="required:true,panelHeight:'auto'" style="margin-left:1px;width:200px;height:25px;">
      						<option value="1">商户</option>
      						<option value="2">会员</option>
      					</select>
      				</span></td>
   				</tr>		
   				<tr> 
 				 	<th class="specalt">备&nbsp;&nbsp;注</th> 
   					<td class="shandan1"><span><textarea id="remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[0,200]'" style="width:400px; height:100px;"></textarea></span></td>
   				</tr>
    			<tr>
  					<td colspan="2" style="text-align:right;">
  						<a href="javascript:savePoint();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>
	<div>
  		<table>
    		<tr>
      			<td> 积分名称：<input type="text" id="name" /> 
      			&nbsp;
      				  积分类型：<select  id="_type" data-options="required:true" style="margin-left:1px;width:100px;height:25px;">
      						<option value="0"></option>
      						<option value="1">商户</option>
      						<option value="2">会员</option>
      					  </select>
      			&nbsp;
      			               积分参数：<input type="text" id="_operation" />
        			<input type="button" onclick="search()" value="查询" id="search" />
        		</td>
    		</tr>
  		</table>
	</div>
	<table id="list" style="height:340px;" ></table>
</body>
</html>