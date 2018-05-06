
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>优惠券管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/di/diCard.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				优惠券名称：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search" disabled />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDiCardbutton()">新增优惠券 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editDiCardbutton()">修改优惠券 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyDiCardbutton()">删除优惠券</a>
	</div>
	<table id="diCardlist" style="height:410px;" ></table>
	<div id="dlg_diCard" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:700px; height:560px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="diCardfm" novalidate method="post" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="state" name="state" />
			<table class="mytable2" id="mytable" cellspacing="0"> 
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">积客券名称</th> 
					<td class="shandan" colspan="3"><span><input name="card_name"  id="card_name" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">有效截止日期</th> 
					<td class="shandan" ><span><input name="vaildtime"  id="vaildtime" class="easyui-datebox" data-options="required:true,min:0,max:10000" style="width:100px;"/></span></td> 
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">面值</th> 
					<td class="shandan" ><span><input name="card_worth"  id="card_worth" class="easyui-numberbox" data-options="required:true,min:0,max:10000" style="width:100px;"/></span></td>
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">使用数量</th> 
					<td class="shandan" ><span><input name="use_num"  id="use_num" class="easyui-numberbox" data-options="required:true,min:0,max:10000" style="width:100px;"/></span></td> 
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">本次活动数量</th> 
					<td class="shandan" ><span><input name="total_num"  id="total_num" class="easyui-numberbox" data-options="required:true,min:0,max:9000000" style="width:100px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">详情分类</th> 
					<td class="shandan" colspan="3"><span><input name="contentType"  id="contentType" class="easyui-combobox" data-options="required:true" style="width:100px;"/></span></td>
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">详情内容</th> 
					<td class="shandan" colspan="3"><span><input name="coContent.id"  id="contentId" class="easyui-combobox" data-options="required:true" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">使用说明</th> 
					<td class="shandan" colspan="3"><span><textarea name="use_explain"  id="use_explain"  style="width:400px;height:60px"></textarea></span></td> 
				</tr>
                <tr>
                    <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">活动图片</th>
                    <td class="shandan">
                        <img id="preview1" pid="" />
                        <span>
   					<input id="card_pic" idm="preview1" type="file" size="25" name="card_pic" onchange="javascript:setImagePreview(this);"  data-options="required:true,validType:'length[1,100]'"></input>
   					</span></td>
                </tr>


				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveDiCardbutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_diCard').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
