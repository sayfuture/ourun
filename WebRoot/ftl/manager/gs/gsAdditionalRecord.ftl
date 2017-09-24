<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品发布</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/gs/gsAdditionalRecord.js" charset="utf-8"></script>
	
	<script type="text/javascript">
    window.onload = function()
    {
        CKEDITOR.replace( 'goodsDesc', {
		height : 500
		});
	};
	</script>
	<style>
	label{letter-spacing:8px;}
	th {
		width: 8%;
	}
	</style>
</head>
<body>
	<div id="toolbar">
    	
	</div>
	<div>
  		<table>
  			<tr>
      			<td> 
      				商品名称：<input type="text" id="gsNameForSearch" />
        		</td>
      			&nbsp;<td> 
      				<input type="button" onclick="searchForGsName()" value="查询" id="search"  />
      			</td>
      		</tr>
  		</table> 
	</div>
	<table id="list" style="height:340px;" >
	</table>
	
</body>
</html>