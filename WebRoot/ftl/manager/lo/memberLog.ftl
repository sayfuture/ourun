<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员日志</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/lo/memberLog.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
		input,textarea{margin-top:5px;}
	</style>
</head>
<body>
<div>
  	<table>
    	<tr>
      		<td>开始时间：<input type="text" name="startDate" style="width:150px" id="startDate" class="easyui-datebox" panelWidth=200 editable="false"/></td>
			<td>&nbsp;结束时间：<input type="text" name="endDate" style="width:150px" id="endDate" class="easyui-datebox" panelWidth=200 editable="false"/></td> 
			<td>&nbsp;会员行为：<input type="text" name="action" id ="action" style="width:120px;"></td>		
			<td>&nbsp;会员名：<input type="text" id="name" name="name" style="width:120px;"/></td>	
			<td>&nbsp;会员IP：<input type="text" id="remoteIp" name="remoteIp" style="width:100px;"/></td>	
			<td>&nbsp;<input type="button" onclick="search()" value="查询" /></td>
			<td>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="showMe()">查看会员详情</a></td>
		</tr>
   </table>
</div>
	<table id="list" style="height:410px;" ></table>	
		
		<!--会员详情弹出框-->
		<div id="dial" class="easyui-dialog"	closed="true" buttons="#dlg-buttons-dialog" style="width: 650px; height: 500px; padding: 30px 30px;top:30px;" >
		 <div>
		    <table class="mytable2" id="mytable2" cellspacing="0"> 
			     <tr>
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">真&nbsp;&nbsp;实&nbsp;&nbsp;名  </th>
			     <td><input disabled="disabled" class="specalt" id="realName"/></td> 
			     	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">用&nbsp;&nbsp;户&nbsp;&nbsp;名  </th>
			     	<td><input disabled="disabled" id="loginName"/></td> </tr>
			     <tr>
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">性&nbsp;&nbsp;&nbsp;&nbsp;别</th>
			     <td><input disabled="disabled" id="sexInfo"/></td> 
			     <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">生&nbsp;&nbsp;&nbsp;&nbsp;日 </th>
			     <td><input disabled="disabled" id="birthdayInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">联&nbsp;系&nbsp;方&nbsp;式 </th>
			     <td><input disabled="disabled" id="cellphoneInfo"/></td> 
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">身&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</th>
			     <td><input disabled="disabled" id="identityInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</th>
			     <td><input disabled="disabled" id="emailInfo"/></td> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">兴&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;趣</th>
			     <td><input disabled="disabled" id="interestInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">个&nbsp;人&nbsp;情&nbsp;况</th>
			     <td><input disabled="disabled" id="maritalStateInfo"/></td>
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">激&nbsp;活&nbsp;状&nbsp;态</th>
			     <td><input disabled="disabled" id="activityInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">冻&nbsp;结&nbsp;/&nbsp;启&nbsp;用</th>
			     <td><input disabled="disabled" id="availabilityInfo"/></td>
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">积&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</th>
			     <td><input disabled="disabled" id="scoreInfo"/></td> </tr>
			       <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">级&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th>
			     <td><input disabled="disabled" id="levelInfo"/></td> 
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">注&nbsp;册&nbsp;方&nbsp;式</th>
			     <td><input disabled="disabled" id="registerInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">验&nbsp;证&nbsp;方&nbsp;式 </th>
			     <td><input disabled="disabled" id="validationInfo"/></td>
			      <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;箱&nbsp;绑&nbsp;定</th>
			     <td><input disabled="disabled" id="emailBindingInfo"/></td> </tr>
			     <tr> <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">手&nbsp;机&nbsp;绑&nbsp;定 </th>
			     <td><input disabled="disabled" id="cellphoneBindingInfo"/></td>  <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">头&nbsp;像&nbsp;路&nbsp;径 </th>
			     <td><input disabled="disabled" id="headShowPathInfo"/></td> </tr>
		    </table>
		 </div>
	  </div>
</body>
</html>