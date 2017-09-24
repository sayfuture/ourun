<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>系统日志</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/lo/logManagement.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
		input,textarea{margin-top:5px;}
	</style>
</head>
<body>
  
	<div>
	<table>
    		<tr>
      			<td> 
      			 开始时间 ：<input type="text" name="startDate"  id="startDate" class="easyui-datebox" panelWidth=200 editable="false"/>
				&nbsp;结束时间：<input type="text" name="endDate"    id="endDate" class="easyui-datebox" panelWidth=200 editable="false"/>
					<input type="hidden" id="userId" />
				&nbsp;操作人员姓名：<input type="text" id="userName" readonly  style="background-color: #B2DFEE;cursor:pointer;" ondblclick="selectUser()"/>
				<br>
				&nbsp;IP地址： <input type="text" id="ip" name="ip" "/>&nbsp;
				&nbsp;功能名：<input type="text" id="logFunctions" name="logFunctions" />
				&nbsp;<input type="button" onclick="search()" value="查询" />
			</td>
    		</tr>
  		</table>
	</div>
	<table id="list" style="height:430px;" ></table>
	
		<!--选择人员弹出框-->
	<div id="auDialog" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons-auDialog" style="width: 600px; height: 460px; padding: 10px 5px;top:5px;left:90" >
		<div>
	  	<table>
	    		<tr>
	      			<td> 操作人员：<input type="text" id="auName" name="auName"/>
        				<input type="button"  value="查询" id="search" onclick="searchUser()" />
        			</td>
	    		</tr>
	  		</table>
		</div>
		<table id="aulist" style="height:330px;" >
		</table>
		<div id="dlg-buttons-auDialog">
	  		<a href="javascript:saveAu();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#auDialog').dialog('close')">取消</a>
		</div>
	</div>
	
	<!--人员详情弹出框-->
	<div id="dial" class="easyui-dialog"	closed="true" buttons="#dlg-buttons-dialog"
		style="width: 800px; height: 280px; padding: 10px 10px;top:20px;left:90" >
		 <div>
		    <table class="mytable2" id="mytable2" cellspacing="0"> 
			    <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">真&nbsp;&nbsp;实&nbsp;&nbsp;名  </th>
			     <td><input disabled="disabled" id="realName"/></td>  <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">登&nbsp;&nbsp;录&nbsp;&nbsp;名  </th>
			     <td><input disabled="disabled" id="loginName"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">年&nbsp;&nbsp;&nbsp;&nbsp;龄 </th>
			     <td><input disabled="disabled" id="ageInfo"/></td>  <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">性&nbsp;&nbsp;&nbsp;&nbsp;别</th>
			     <td><input disabled="disabled" id="sexInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;&nbsp;&nbsp;&nbsp;箱 </th>
			     <td><input disabled="disabled" id="emailInfo"/></td>  <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">联&nbsp;系&nbsp;方&nbsp;式 </th>
			     <td><input disabled="disabled" id="telephoneInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">联&nbsp;系&nbsp;方&nbsp;式&nbsp;2 </th>
			     <td><input disabled="disabled" id="telephoneInfo2"/></td>   </tr>
		    </table>
		 </div>
	  </div>
</body>
</html>