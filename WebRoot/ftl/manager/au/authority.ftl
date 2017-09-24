<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>功能管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
</head>
<body>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="newObj()">添加功能 </a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editObj()">修改功能</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj()">删除功能</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"
			style="width: 700px; height: 430px; padding: 30px 30px; top:10px;" >
  		<div class="ftitle"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">名&nbsp;&nbsp;称</th> 
   					<td class="shandan1"><span><input name="name"  id="uname" class="easyui-validatebox" data-options="required:true,validType:'length[2,10]'" style="width:200px;" /></span></td>
   				</tr>
   				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">路&nbsp;&nbsp;径</th> 
   					<td class="shandan1"><span><input name="url" id="upwd" class="easyui-validatebox"  data-options="required:true,validType:'length[2,200]'" style="width:400px;" /></span></td>
   				</tr>	
   				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">备&nbsp;&nbsp;注</th> 
   					<td class="shandan1"><span><textarea id="remarks" name="remarks" class="easyui-validatebox" data-options="validType:'length[0,200]'" style="width:400px; height:100px;"></textarea></span></td>
   				</tr>
   				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">位&nbsp;置&nbsp;设&nbsp;置</th> 
   					<td class="shandan1">
   						<span>
   							<select id="level" name="level" class="easyui-combobox" data-options="panelHeight:'auto'">
      							<option value="1">系统管理</option>
      							<option value="2">工作量管理</option>
      							<option value="3">商户管理</option>
      							<option value="4">工作日程管理</option>
      							<option value="5">地域管理</option>
      							<option value="6">常用工具管理</option>
      					 	</select>
      					</span>
      				</td>
   				</tr>	 
    			<tr>
  					<td colspan="2" style="text-align:right;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
  		</form>
	</div>
	<div>
  		<table>
    		<tr>
      			<td> 菜单功能名称：<input type="text" id="name" />
        			<input type="button" onclick="search()" value="查询" />
        		</td>
    		</tr>
  		</table>
	</div>
	<table id="list" style="height:340px;" >
	</table>

<script type="text/javascript">
	
   	var url;//表单提交请求的地址
   	// var parameter = "{'aaa':'aaa','bbb':'bbb'}";
   	var parameter = "";
   	var oldName="";//修改时验证是否修改了名字
   	
   	/**
   		触发“添加功能”按钮的点击事件后，所执行的方法
   	*/
	function newObj() {
		$('#dlg').dialog('open').dialog('move',{
			top:250
		}).dialog('setTitle', '添加功能');		//显示对话框，并设置标题
		$('#fm').form('clear');										//清空表单
		$('#level').val(1);
		url = '${base}/manager/erp/au/addAuthority.do';				//重置url
		document.documentElement.style.overflow = "hidden";
	}
	/**
   		触发“修改功能”按钮的点击事件后，所执行的方法
   	*/
	function editObj() {
		var row = $('#list').datagrid('getSelected');			//获取选择的行的功能
		if (row){
																//异步请求，通过id获得用户的功能，并把功能导入表单中
			$.ajax({
				method:'get',
				url:'${base}/manager/erp/au/getAuthority.do',
				data:'id='+row.id,
				dataType: "json",
				success:function(data){
					$("#id").val(row.id);
					$("#uname").val(row.name);
					$("#upwd").val(row.url);
					$("#remarks").val(row.remarks);
					$('#level').val(row.level);
					oldName=$("#uname").val();
				},
				error: function (msg) {
					message_op(false,null);
            	}
			});
			$('#dlg').dialog('open').dialog('setTitle','修改功能');	//显示对话框，并设置标题
			url = '${base}/manager/erp/au/updateAuthority.do';		//重置url
		}else{
       		$.messager.alert('操作提示', '请先选择功能，再修改！','info');
       	}
    }
    function editUser1(row) {
													//异步请求，通过id获得用户的功能，并把功能导入表单中
		$.ajax({
			method:'get',
			url:'${base}/manager/erp/au/getAuthority.do',
			data:'id='+row.id,
			dataType: "json",
			success:function(data){
				$("#id").val(row.id);
				$("#uname").val(row.name);
				$("#upwd").val(row.url);
				$("#remarks").val(row.remarks);
				$('#level').val(row.level);
				oldName=$("#uname").val();
			},
			error: function (msg) {
				message_op(false,null);
        	}
		});
		$('#dlg').dialog('open').dialog('setTitle','修改功能');	//显示对话框，并设置标题
		url = '${base}/manager/erp/au/updateAuthority.do';		//重置url

    }
    /**
   		触发“保存”按钮的点击事件后，所执行的方法
   	*/
    function saveObj() {
    	
    	$('#fm').form('submit', {									//提交表单
			url : url, 											//设置表单请求的地址
			onSubmit : function() {									//在表单提交前要执行的方法，返回false，则放弃请求
				 return $(this).form('validate');					//进行easyUI表单验证
			},
	        success : function(result) {							//请求成功后执行的方法,result为请求返回的布尔值
		        	$('#dlg').dialog('close'); // close the dialog	//关闭对话框
	        		message_op(true,'list');						// 调用Commons_message.js中的方法
	        },
	        error:function (){										//请求发生错误时，执行的方法
	        	message_op(false,null);
	        }
	        
     	});
     }
     /**
   		触发“删除功能”按钮的点击事件后，所执行的方法
   	*/
     function destroyObj() {
     	var row = $('#list').datagrid('getSelected');		
		var rows = $('#list').datagrid('getSelections');						// 获取选择的行的功能
		var ids ='';
		for(var i = 0;i<rows.length;i++){
			ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
		}				
		
		if (row){																	//判断是否存在被选择的行
			$.messager.confirm('', '您确定要删除该记录吗?', function(r) {				//确认对话框,判断是否继续进行操作
			if (r) {	
				$.post('${base}/manager/erp/au/deleteBinch.do', {						//通过post方法发送请求，id为参数
					ids:ids
				}, function(result) {												//异步请求完成后的回调函数
					if(result){	
						$.messager.alert('操作提示', '操作成功!','info');								// 调用Commons_message.js中的方法
						$("#list").datagrid("reload");
					}else{
						$.messager.alert('操作提示', '操作失败，不能删除！','info');
						$("#list").datagrid("reload");	
					}
				}, 'json');
			}});
       	}else{
       		$.messager.alert('操作提示', '请先选择功能，再删除！','info');
       	}
	}
	 /**
	   动态验证用户名是否重复
	 */
	 $(function(){
	 	/**
	 		自定义名字验证规则
	 	*/
		$.extend($.fn.validatebox.defaults.rules, { 
			single: { 
				validator: function(value,param){
					return param[0]==0||value==oldName; 
				}, 
			message:'名字已存在' 
			} 
		});
		
	 	//输入内容改变时验证名字是否存在
	 	$('#uname').change(function(){
	
			//获取已有验证规则
	 		var rule=eval("({"+$('#uname').attr('data-options')+"})").validType;
			
			//动态请求
	 		$.post('${base}/manager/erp/au/findList.do',{parameter:"{'name':'"+$('#uname').val()+"'}"},function(data,status){
	 			var result=data.substring(9,10);
	 			//console.log(result);
 
 				//动态绑定验证规则
	 			$('#uname').validatebox({
					required:true,
					validType:[rule,'single['+result+']']
				});
	 		});
	 		//end ajax method
	 	});
	 	//end chenge method
	 	
	 	
	 });
	
      /**
   		触发“查询”按钮的点击事件后，所执行的方法
   		*/
    
    function search(){
     	parameter =	"'name':'" + $('#name').val() + "'";		//定义dategrid请求参数
     	$('#list').datagrid('reload',{							//重载行，等同于'load'方法。即重新请求url加载数据
		  	parameter:"{'name':'" + $('#name').val() + "','type':'1'}"		//参数
		});
    }
    
   /*
   	  DataGrid 初始化
   	*/
	$(function(){
		$('#list').datagrid({   
	      	url:'${base}/manager/erp/au/findList.do',
	      	singleSelect : false, 
	      	pagination: true,
	      	pageList:[10,20,50],
	      	queryParams:{
    	       parameter:"{}"
    		},
	      	columns:[[   
	          	{field:'id',title:'id',width:100,checkbox:true},   
	          	{field:'name',title:'名称',width:250},
	          	{field:'url',title:'路径',width:500},
	          	{field:'remarks',title:'备注',width:500},
	          	{field:'level',title:'菜单位置',width:100,formatter:function(value,row,index){
	          		if(value == 1){
	          			return "<span>系统管理</span>";
	          		}
	          		if(value == 2){
	          			return "<span>工作量管理</span>";
	          		}
	          		if(value == 3){
	          			return "<span>商户管理</span>";
	          		}
	          		if(value == 4){
	          			return "<span>工作日程管理</span>";
	          		}
	          		if(value == 5){
	          			return "<span>地域管理</span>";
	          		}
	          		if(value == 6){
	          			return "<span>常用工具管理</span>";
	          		}
	          	}}
	      	]],
            onDblClickRow: function (index, row) {
                editUser1(row);
            }   
   		});  
	});	
	
	document.onkeydown = function(event) {    
          var target, code, tag;    
        if (!event) {    
            event = window.event; //针对ie浏览器    
            target = event.srcElement;    
            code = event.keyCode;    
            if (code == 13) {    
                 tag = target.tagName;    
                 if (tag == "TEXTAREA") { return true; }    
                 else { return false; }    
             }    
         }    
         else {    
             target = event.target; //针对遵循w3c标准的浏览器，如Firefox    
             code = event.keyCode;    
            if (code == 13) {    
                 tag = target.tagName;    
                 if (tag == "INPUT") { return false; }    
                 else { return true; }     
            }    
       }    
     };
</script>
</body>
</html>