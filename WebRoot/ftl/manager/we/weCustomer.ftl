
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>微信客户管理管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/we/weCustomer.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				微信客户管理名称：<input type="text" id="searchname" />
				预计下次保养时间开始：<input type="text" id="searchstart_time" name="searchstart_time" class="easyui-datebox"/>
				预计下次保养时间结束：<input type="text" id="searchend_time" name="searchend_time" class="easyui-datebox"/>
				<input type="button" onclick="search()" value="查询" id="search"  />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWeCustomerbutton()">修改微信客户信息 </a>
	</div>
	<table id="weCustomerlist" style="height:410px;" ></table>
	<div id="dlg_weCustomer" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:600px; height:420px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="weCustomerfm" novalidate method="post">
			<input type="hidden" id="id" name="openId" />
			<input type="hidden" id="state" name="state" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">微信昵称</th> 
					<td class="shandan"><span><input name="wechat_name"  id="wechat_name" class="easyui-validatebox"  style="width:200px;" readonly="readonly"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">手机号</th> 
					<td class="shandan"><span><input name="phone"  id="phone" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">客户名称</th> 
					<td class="shandan"><span><input name="customer_name"  id="customer_name" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
                <tr>
                    <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">省</th>
                    <td class="shandan"><span><input name="province"  id="province" class="easyui-combobox"  style="width:200px;"/></span></td>
                </tr>
                <tr>
                    <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">市</th>
                    <td class="shandan"><span><input name="city"  id="city" class="easyui-combobox"  style="width:200px;"/></span></td>
                </tr>
                <tr>
                    <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">区县</th>
                    <td class="shandan"><span><input name="area"  id="area" class="easyui-combobox"  style="width:200px;"/></span></td>
                </tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">车型</th> 
					<td class="shandan"><span><input name="car_type"  id="car_type" class="easyui-validatebox" data-options="validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">公里数</th> 
					<td class="shandan"><span><input name="kilometers"  id="kilometers" class="easyui-numberbox" data-options="max:99999990"  style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">预计下次保养时间</th> 
					<td class="shandan"><span><input name="next_maintain_time"  id="next_maintain_time" class="easyui-datebox"  style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">预计下次保养内容</th> 
					<td class="shandan"><span><input name="next_maintain_content"  id="next_maintain_content" class="easyui-validatebox" data-options="validType:'length[0,100]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveWeCustomerbutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_weCustomer').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
