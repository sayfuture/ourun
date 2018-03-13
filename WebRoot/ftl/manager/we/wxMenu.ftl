
<!DOCTYPE HTML PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>微信菜单管理管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/we/wxMenu.js" charset="utf-8"></script>
</head>
<body>
	<div>
		<table>
		<tr>
			<td>
				微信菜单管理名称：<input type="text" id="searchname" />
				<input type="button" onclick="search()" value="查询" id="search" disabled />
			<td>
		<tr>
		<table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addWxMenubutton()">新增微信菜单管理 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWxMenubutton()">修改微信菜单管理 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyWxMenubutton()">删除微信菜单管理</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="createWxMenubutton()">发布微信菜单</a>
	</div>
	<table id="wxMenulist" style="height:410px;" ></table>
	<div id="dlg_wxMenu" class="easyui-dialog" closed="true"  modal="true"buttons="#dlg-buttons" style="width:790px; height:400px; padding:1px 1px;top:1px;">
		<div class="ftitle" style="margin:10px;"></div>
		<form id="wxMenufm" novalidate method="post">
			<input type="hidden" id="id" name="id" />
            <input type="hidden" name="superWxMenu.id"  id="p_id" />
			<table class="mytable2" id="mytable" cellspacing="0">
                <tr>
                    <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">一级菜单</th>
                    <td class="shandan"><span><input   id="p_idInfo" class="easyui-validatebox" data-options="validType:'length[1,20]'" style="width:200px;"readonly="readonly"/></span></td>
                </tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">菜单名称</th>
					<td class="shandan"><span><input name="name"  id="name" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">类型</th>
					<td class="shandan"><span>
						<select  name="type"  id="seltype" class="easyui-combobox" data-options="required:true"  style="width:200px;">
							 <option value="view">网页链接类型</option>
							 <option value="click">点击类型</option>
							 <option value="miniprogram">小程序类型</option>
						</select>
					</span></td>
				</tr>
				<div id="clickType">
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">点击类型返回的信息</th>
					<td class="shandan"><span><input name="key"  id="key" class="easyui-validatebox"  data-options="required:true" style="width:600px;"/></span></td>
				</tr>
                </div>
                <div id="viewType">
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">网页链接</th>
					<td class="shandan"><span><input name="url"  id="url" class="easyui-validatebox"  data-options="required:true" style="width:600px;"/></span></td>
				</tr>
				</div>
				<div id="miniprogramType">
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">小程序的appid</th>
					<td class="shandan"><span><input name="appid"  id="appid" class="easyui-validatebox"  data-options="required:true" style="width:600px;"/></span></td>
				</tr>
				<tr>
					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">小程序的页面路径</th>
					<td class="shandan"><span><input name="pagepath"  id="pagepath" class="easyui-validatebox" data-options="required:true" style="width:600px;"/></span></td>
				</tr>
                </div>
				<tr>
				<td colspan="4" style="text-align:right;">
					<a href="javascript:saveWxMenubutton();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_wxMenu').dialog('close')">取消</a>
				</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
