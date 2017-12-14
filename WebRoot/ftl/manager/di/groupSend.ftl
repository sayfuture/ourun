
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>群组发送</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/groupSend.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				微信客户名称：<input type="text" id="searchname" />
				预计下次保养时间开始：<input type="text" id="searchstart_time" name="searchstart_time" class="easyui-datebox"/>
				预计下次保养时间结束：<input type="text" id="searchend_time" name="searchend_time" class="easyui-datebox"/>
				<input type="button" onclick="search()" value="查询" id="search"  />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="groupSendbutton()">消息群发</a>
	</div>
	<table id="weCustomerGrouplist" style="height:410px;" ></table>
	<div id="dlg_gruopInfo" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:680px; height:200px; padding:1px 1px;top:1px;">
		<form id="fm_groupInfo" novalidate method="post">
			<input type="hidden" name="openids" id="openids"/>
			<input type="hidden" id="cardId" name="cardId" />
            <input type="hidden" id="meId" name="meId" />
			<table class="mytable" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">描述信息</th> 
					<td class="shandan"><span><input name="desc"  id="desc" class="easyui-validatebox" data-options="required:true" style="width:500px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">点击信息</th> 
					<td class="shandan"><span><input name="clickDesc"  id="clickDesc" class="easyui-validatebox" data-options="required:true" style="width:500px;"/></span></td> 
				</tr>
				<tr>
					<td colspan="4" style="text-align:right;">
						<a href="javascript:sendGroupInfo();" class="easyui-linkbutton" iconCls="icon-ok">发送</a>
		  				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_gruopInfo').dialog('close')">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg_meinfo" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:800px; height:480px; padding:1px 1px;top:1px;">
		<table id="meinfolist" style="height:410px;" ></table>
		<a href="javascript:inputmeinfo();" class="easyui-linkbutton" iconCls="icon-ok">选择发送优惠券</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_meinfo').dialog('close')">取消</a>
	</div>

    <div id="dlg_templateCard" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:400px; height:200px; padding:1px 1px;top:1px;">
        <#--<table id="templateCardlist" style="height:410px;" ></table>-->
		<div align="center"><input type="radio" value="1" name="cardinfo">优惠券（一）</input>&nbsp;&nbsp;&nbsp;
        <input type="radio" value="2" name="cardinfo">优惠券（二）</input>
        </div>
				</br></br>
			<div align="center">
        <a href="javascript:inputGroupSend();" class="easyui-linkbutton" iconCls="icon-ok">填写发送信息</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_templateCard').dialog('close')">取消</a>
            </div>
    </div>

</body>
</html>
