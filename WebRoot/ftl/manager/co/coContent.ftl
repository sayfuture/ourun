<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>栏目管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/co/coContent.js" charset="utf-8"></script>
	<script type="text/javascript">
    window.onload = function()
    {
        CKEDITOR.replace( 'body', {
		toolbar :[
					['Source'],
                ['Bold','Italic','Underline','Strike','Subscript','Superscript'],
                ['NumberedList','BulletedList'],
                ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
                ['Link','Unlink','Anchor'],
                ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
'/',
                ['Styles','Format','Font','FontSize'],
                ['TextColor','BGColor']
             ],
		height : 500
		});
	};
</script>
</head>
<body>
	<div>
  		<table>
    		<tr>
      			<td> 
      				栏目名称：<input type="text" id="name" />
        		</td>
        		<td> 
        			&nbsp;栏目类型：<span>
   							<!--栏目类型id-->
   							<input type="hidden" name="coTypeId" id="coTypeId2" />
   							<!--栏目类型name-->
   							<input type="text" id="coTypeName" readonly style="width:200px;background-color: #B2DFEE;cursor:pointer;""  ondblclick="check3()" />
   							<!--栏目类型选择框-->
   							<div id="dlg3" class="easyui-dialog" closed="true"  modal="true" buttons="#buttons"	style="width: 700px; height: 430px; padding: 5px 5px; top:5px;" >
   								<div class="ftitle" style="margin:5px;"></div>
   								<table id="list3" style="height:330px; width:600px;"></table>
   								<div style="float:right;">
   									<a href="javascript:checkCoType3();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  									<a href="javascript:void(0)" class="easyui-linkbutton" 
        									iconCls="icon-cancel" onclick="javascript:$('#dlg3').dialog('close')">取消</a>
   								</div>
   							</div>
      					</span>
        		</td>
        		<td> 
        			&nbsp;开始时间：<input id="time1" editable="false" name="time1" style="width:180px;" panelWidth=200px class="easyui-datebox"  editable="false" />
        		</td>
        		<td> 
        			&nbsp;结束时间：<input id="time2" editable="false" name="time2" style="width:180px;" panelWidth=200px class="easyui-datebox"  editable="false"/>
        		</td>
        		<td> 
        			&nbsp;<input type="button" onclick="search()" value="查询" id="search"  />
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"	style="width: 900px; height: 700px; padding: 10px 10px; top:10px;" >
  		<div class="ftitle" style="margin:10px;"></div>
  		<form id="fm" novalidate method="post" enctype="multipart/form-data">
  			<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				  <tr>
  					<td colspan="4" style="text-align:left;" style="height:30px;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close');">取消</a>
        			</td>
  				</tr>
 				<tr> 
 				 	<th class="specalt">标&nbsp;&nbsp;题</th> 
   					<td class="shandan2" colspan="3"><span><input name="title" type="text" id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'" style="width:300px;" /></span></td> 
  				</tr>
  				<tr> 
   					<th class="specalt">类&nbsp;&nbsp;型</th> 
  					<td class="shandan2" colspan="3">
   						<span>
   							<!--栏目类型id-->
   							<input type="hidden" name="coTypeId" id="coTypeId" />
   							<!--栏目类型name-->
   							<input type="text" id="coTypeName2" readonly style="width:300px;background-color: #B2DFEE;cursor:pointer;" class="easyui-validatebox" data-options="required:true,validType:'length[2,30]'"  ondblclick="check()" />
   							<!--栏目类型选择框-->
   							<div id="dlg2" class="easyui-dialog" closed="true"  modal="true" buttons="#buttons" style="width: 700px; height: 500px; padding: 5px 5px; top:10px;" >
   								<div class="ftitle" style="margin:10px;"></div>
   								<table id="list2" style="height:300px; width:600px;"></table>
   								<div style="float:right;">
   									<a href="javascript:checkCoType();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
   								</div>
   							</div>
      					</span>
      				</td>  
  				</tr>
  				<!--<tr>
  					<th class="specalt">城&nbsp;&nbsp;市</th>
  					<td class="shandan2" colspan="3">
  						<input id="cityId" class="easyui-combobox" name="cityId" style="width:100px;" data-options="required:true"></input>
  					</td>
  				</tr>-->
  				<tr> 
 				 	<th class="specalt">封&nbsp;&nbsp;面</th> 
   					<td class="shandan2" colspan="3">
   						<img id="preview1" pid="" />
						<input id="strFileName" idm="preview1" type="file" size="25" name="strFileName" onchange="javascript:setImagePreview(this);" data-options="required:true,validType:'length[1,100]'"></input>
   					</td> 
  				</tr>
  				<tr> 
 				 	<th class="specalt">源&nbsp;&nbsp;自</th> 
   					<td class="shandan2" colspan="3"><textarea id="source" name="source" style="width:300px;height:30px;" class="easyui-validatebox" data-options="required:false,validType:'length[0,200]'"></textarea></td> 
  				</tr>
  				<tr> 
 				 	<th class="specalt">栏目短文</th> 
   					<td class="shandan2" colspan="3"><textarea id="introduce" name="introduce" style="width:90%;height:50px;" class="easyui-validatebox" data-options="required:false,validType:'length[0,200]'"></textarea></td> 
  				</tr>
  				<tr> 
 				 	<th class="specalt">文章关键字</th> 
   					<td class="shandan2" colspan="3"><input id="keywords" name="keywords" style="width:300px;height:30px;" class="easyui-validatebox" data-options="required:false,validType:'length[0,200]'"></input><font color='red'>*多个关键字请以逗号分隔</font></td> 
  				</tr>
  				<!--<tr> 
 				 	<th class="specalt">新闻轮播图</th> 
   					<td class="shandan2" colspan="3"><input  name="weight" type="radio" value="1" >显示</input><input  name="weight" type="radio" value="0" >不显示</input><span><font color="red">&nbsp;&nbsp;&nbsp;*该项为行业资讯中的轮播图是否显示</span></td> 
  				</tr>-->
  				<tr> 
 				 	<th class="specalt">正&nbsp;&nbsp;文</th> 
   					<td class="shandan2" colspan="3"><textarea id="body" name="body" style="width:90%;height:1000px;" class="easyui-validatebox"></textarea></td> 
  				</tr>

  			</table>
  		</form>
	</div>

	
		<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton btn" 
    		iconCls="icon-add" plain="true" onclick="newObj1()">添加栏目</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" id="editObj1"
    		iconCls="icon-edit" plain="true" onclick="editObj1()">修改栏目</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-search" plain="true" onclick="showObj1()">查看栏目</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj1()">删除栏目</a>
	</div>
	<table id="list" style="height:410px;"></table>
	
</body>
</html>