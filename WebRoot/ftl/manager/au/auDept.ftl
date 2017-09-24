<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>部门管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auDept.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
</head>
<body>

	
	<div>
  		<table>
    		<tr>
      			<td> 部门名称：<input type="text" id="name" />
        			&nbsp;<input type="button" onclick="search()" value="查询" disabled id="search"/>
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton btn" iconCls="icon-add" plain="true" onclick="newObj()">添加部门</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton btn" iconCls="icon-edit" plain="true" onclick="editObj()">修改部门</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton btn" iconCls="icon-remove" plain="true" onclick="destroyObj()">删除部门</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width: 600px; height: 420px; padding: 30px 30px; top:10px;" >
  		<div class="ftitle" style="margin:10px;"></div>
  		  <form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<input type="hidden" id="parentId" name="superAuDept.id" />
  			<input type="hidden" id="level" name="level" />
  			  <table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th class="specalt">上&nbsp;级&nbsp;部&nbsp;门</th> 
   					<td class="shandan"><span><input name="superAuDept.name"  id="parentName" class="easyui-validatebox" readonly="readonly"    data-options="validType:'length[2,10]'" style="width:200px;"/></span></td> 
  				</tr>
 				 <tr> 
 				 	<th class="specalt">名&nbsp;&nbsp;称</th> 
   					<td class="shandan"><span><input name="name"  id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:200px;"/></span></td> 
  				</tr>
  			<!--	<tr> 
 				 	<th class="specalt">城&nbsp;&nbsp;市</th> 
   					<td class="shandan"><span><input name="cityId"  id="cityId" class="easyui-combobox" data-options="required:true" style="width:200px;"/></span></td> 
  				</tr>-->
  				 <tr> 
 				 	<th class="specalt">序&nbsp;&nbsp;号</th> 
   					<td class="shandan"><span><input name="seqno"  id="seqno" class="easyui-numberbox" data-options="required:true,min:0,max:100000" style="width:200px;"/></span></td> 
  				</tr>
  				<tr> 
 				 	<th class="specalt">说&nbsp;&nbsp;明</th> 
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
	
	<!--	<div id="dlg2" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:1000px; height:470px; padding:1px 1px;top:1px;" >
		<form id="fm2" novalidate method="post">
			<input type="hidden" id="emId" name="deptId"/>
			<table id="list2" style="height:360px;" ></table>
			<div style="color:blue; margin-top:4px;">
	    			友情提示：请认真选择，以免出错！
	    	</div>
		</form>
			<div id="dlg-buttons">
  				<a href="javascript:saveUser2();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  				<a href="javascript:void(0)" class="easyui-linkbutton" 
        		iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
			</div>
	</div>
	-->
	
</body>
</html>