
   	var url;//表单提交请求的地址
   	var parameter = "";
   	var oldName="";//修改时验证是否修改了名字
   	
   	/**
   		触发“添加角色”按钮的点击事件后，所执行的方法
   	*/
	function newObj() {
		$('#dlg').dialog('open').dialog('setTitle', '添加角色');		//显示对话框，并设置标题
		$('#fm').form('clear');										//清空表单
		initParent();												//初始化上一级列表
		url = 'addAuPosition.do';	
		document.documentElement.style.overflow = "hidden";
	}
	/**
   		触发“修改角色”按钮的点击事件后，所执行的方法
   	*/
	function editObj() {
		var row = $('#list').datagrid('getSelected');			//获取选择的行的角色
		if (row){
															//异步请求，通过id获得用户的角色，并把角色导入表单中
			$.ajax({
				method:'get',
				url:'getAuPosition.do',
				data:'id='+row.id,
				dataType: "json",
				success:function(data){
					$("#id").val(row.id);
					$("#uname").val(row.name);
					oldName=$("#uname").val();
					initParent(row.baseuser1.id);
				},
				error: function (msg) {
					message_op(false,null);
            	}
			});
			$('#dlg').dialog('open').dialog('setTitle','修改角色');	//显示对话框，并设置标题
			url = 'updateAuPosition.do';		//重置url
		}else{
       		$.messager.alert('操作提示', '请先选择角色，再修改！','info');
       	}
    }
    
    function editUser1(row) {
											//异步请求，通过id获得用户的角色，并把角色导入表单中
		$.ajax({
			method:'get',
			url:'getAuPosition.do',
			data:'id='+row.id,
			dataType: "json",
			success:function(data){
				$("#id").val(row.id);
				$("#uname").val(row.name);
				oldName=$("#uname").val();
				initParent(row.baseuser1.id);
			},
			error: function (msg) {
				message_op(false,null);
        	}
		});
		$('#dlg').dialog('open').dialog('setTitle','修改角色');	//显示对话框，并设置标题
		url = 'updateAuPosition.do';		//重置url
		
    }
    /**
   		触发“保存”按钮的点击事件后，所执行的方法
   	*/
    function saveObj() {
    	$('#fm').form('submit', {									//提交表单
			url : url,												//设置表单请求的地址
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
     
      function saveUser2() {
      	var rows = $("#list2").datagrid("getRows");
      	var ids = '';
      	for(var i=0;i<rows.length;i++){
			ids = ids =='' ? rows[i].id:ids+','+rows[i].id;
		}
		
    	$('#fm2').form('submit', {		
			url : "addAuPositionAuthority.do?ids="+ids            ,//设置表单请求的地址
			onSubmit : function() {									                   //在表单提交前要执行的方法，返回false，则放弃请求
				 return $(this).form('validate');					                 //进行easyUI表单验证
			},
	        success : function(result) {						                     	//请求成功后执行的方法,result为请求返回的布尔值
		        if(result == 'true'){
		        	$('#dlg2').dialog('close'); // close the dialog	//关闭对话框
	        		message_op(true,'list');						// 调用Commons_message.js中的方法
	        	}else{
	        		message_op(false,null);
	        	}
	        },
	        error:function (){										            //请求发生错误时，执行的方法
	        	message_op(false,null);
	        }
	        
     	});
     }
     
     /**
   		触发“删除角色”按钮的点击事件后，所执行的方法
   	*/
     function destroyObj() {
     	var row = $('#list').datagrid('getSelected');
		var rows = $('#list').datagrid('getSelections');						// 获取选择的行的角色
		var ids ='';
		for(var i = 0;i<rows.length;i++){
			ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
		}				
		
		if (row){																	//判断是否存在被选择的行
			$.messager.confirm('', '您确定要删除该记录吗?', function(r) {				//确认对话框,判断是否继续进行操作
			if (r) {	
				$.post('deleteAuPositionBinch.do', {						//通过post方法发送请求，id为参数
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
       		$.messager.alert('操作提示', '请先选择角色，再删除！','info');
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
	 		$.post('findAuPositionList.do',{parameter:"{'name':'"+$('#uname').val()+"'}"},function(data,status){
	 			var result=data.substring(9,10);
	 			//console.log(result);
 
 				//动态绑定验证规则
//	 			$('#uname').validatebox({
//					required:true,
//					validType:[rule,'single['+result+']']
//				});
	 		});
	 		//end ajax method
	 	});
	 	//end chenge method
	 	
	 	
	 });
	
      /**
   		触发“查询”按钮的点击事件后，所执行的方法
   		*/
    
    function search(name){
     	parameter =	"'name':'" + $("#name").val() + "','type':'1'";		//定义dategrid请求参数
     	$('#list').datagrid('reload',{							//重载行，等同于'load'方法。即重新请求url加载数据
		  	parameter:"{'name':'" + $('#name').val() + "','type':'1'}"		//参数
		});
    }
    
    function search2(name){
     	parameter =	"'name':'" + $("#name2").val() + "','type':'1'";		//定义dategrid请求参数
     	$('#list2').datagrid('reload',{							//重载行，等同于'load'方法。即重新请求url加载数据
		  	parameter:"{'name':'" + $('#name2').val() + "','type':'1'}"		//参数
		});
    }
    
      /**
      	显示添加表单时，初始化复选框方法
      */
    function initParent(){
    	$('#pa').combobox({
				url:'findAuAuthorityList.do',
				valueField:'id',
				textField:'name',
				editable:false ,
				onLoadSuccess:function(){
					var data = $('#pa').combobox('getData');
					select+='';
					
					//数据加载成功后设置默认选中项，若未指定选中项选择第一项
					if(select=='undefined'||select.trim().length<1){
						console.log(data);
						select=data[0].id;
					}
        			if (data.length > 0) {
             			$('#pa').combobox('select', select);
        			}
				
				}
			});
		
    }
   /*
   	  DataGrid 初始化
   	*/
   	$(function(){
   		$('#toolbar a').hide();
		$('#list').datagrid({   
	      	url:'findAuPositionList.do',
	      	singleSelect : true, 
	      	pagination: true,
	      	pageSize : 10,
	      	pageList:[10,20,50],
	      	queryParams:{
    	       parameter:"{}"
    		},
	      	columns:[[   
	          	{field:'id',title:'id',width:100,checkbox:true}, 
	          	{field:'index',title:'序号',width:45, align: 'center',formatter:function(val,row,index){
	   		     var options = $("#list").datagrid('getPager').data("pagination").options; 
	   		     var currentPage = options.pageNumber;
	   		     var pageSize = options.pageSize;
	   		     return (pageSize * (currentPage -1))+(index+1);
	   		 }
	   		},
	          	{field:'name',title:'名称',width:250},
	          	{field:'option',title:'功能设置',width:250,hidden:true,formatter:function(value,row,index){
				        return  "<img alt='img' src='../../../js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:6px 2px 0px 0;width: 15px; height: 15px;' />" +
				        		"<span style='cursor:pointer;display:block;margin:-19px 0 1px 20px;' onclick='setAuthority(\""+row.id+"\");'>功能设置</span>";
				}}
	      	]],
	      	onBeforeLoad:function(){
	      		buttons2();
	      	},
    		onLoadSuccess:function(){
    			buttons();
    		}   
   		});  
	});	
   	
   	// 按钮级控制
   	function buttons2(){
		$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
			'functionType':'auPosition'
		}, function(data) {
			$.each(eval('('+data+')'),function(index, obj){
				var opts = $('#list').datagrid('getColumnFields', false);  
				for(var i=0;i<opts.length;i++){
					if(opts[i] == obj){
						$('#list').datagrid( "showColumn",opts[i]);
					}
				}
			});
		});
	}
   	
	// 按钮级控制
   	function buttons(){
   		$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
   			'functionType':'auPosition'
   		}, function(data) {
   			$.each(eval('('+data+')'),function(index, obj){
   				$('#toolbar a').each(function(){
   					if($(this).attr('onClick') == obj){
   						$(this).show();
   					}
   				});
   				if($("#search").attr('onClick') == obj){
   					$("#search").attr("disabled", false);
   				}
   			});
   		});
   	}
	
	function setAuthority(id){
		$("#posId").val(id);
		$('#dlg2').dialog('open').dialog('setTitle', '功能设置');		//显示对话框，并设置标题
		$('#list2').datagrid({   
	      	url:'findList.do',
	      	singleSelect : false, 
	      	pagination: true,
	      	selectOnCheck: false,
	      	checkOnSelect: false,
	      	fitColumns:true,
	      	pageSize:10,pageList:[10,20,50],
	      	queryParams:{
	    		 parameter:"{}"
    		},
	      	columns:[[   
	          	{field:'id',title:'id',width:30,checkbox:true},   
	          	{field:'name',title:'权限名称',width:150},
	          	{field:'type',title:'权限类型',width:420,formatter:function(value,row,index){
	          		return  getCheckBoxStringByAu(row.id);
	          	}}
	      	]],
	      	onLoadSuccess: function (data,index) {
          		$.each(data.rows, function(index, item){
              		$.post(getCurProjPath()+"/manager/erp/au/getButtonByPositionAndAuthority.do",
              				{
              					'positionId':id,
              					'authorityId':item.id
              				}, 
	              		 function(data)  {
              				 $.each(eval('('+data+')'),function(key,value){
		              				if(item.id == value.auAuthority.id){
		              					$('#list2').datagrid('checkRow', index);   //选中功能
		              					$("input[name='"+item.id+"']").each(function(){
											if(value.auButton!=undefined && $(this).attr('id') == value.auButton.funName){
												$(this).attr("checked", true);
											}
										});
		              				}
		              		});
              		 });
      			});
				
            }
   		});  
	}
	
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
     
     
     
     function getCheckBoxStringByAu(id){
 		var checkBoxStr = "";
  		$.ajax({
 			method:'post',
 			url:getCurProjPath()+'/manager/erp/au/getAubuttonByAuthority.do',
 			dataType: "json",
 			async:false,
 			data:{
 				'id':id
 			},
 			success:function(data){
 				$.each(data,function(key,value){
 	 				checkBoxStr = checkBoxStr + "<input type='checkbox' id='"+value.funName+"' name='"+id+"' value='"+value.id+"' />"+value.btnName+"&nbsp;&nbsp;";
 	  			});
 			},
 			error: function (msg) {
 				alert('msg111111----'+msg);
         	}
 		});
  		return checkBoxStr;
 	}
     
   //获取项目的URL
 	function getCurProjPath() {
 	    var curWwwPath = window.document.location.href;
 	    var pathName = window.document.location.pathname;
 	    var pos = curWwwPath.indexOf(pathName);
 	    var localhostPath = curWwwPath.substring(0, pos);
 	    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
 	    return localhostPath + projectName;
 	}