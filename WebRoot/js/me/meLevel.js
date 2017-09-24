	var url;//表单提交请求的地址
   	var parameter = "";
   	
    /*
 	  DataGrid 初始化
 	*/
	$(function(){
		$('#toolbar a').hide();
		
		$('#list').datagrid({   
	      	url:'findMemberLevelList.do',
	      	singleSelect : false, 
	      	pagination: true,
	      	pageSize:10,pageList:[10,20,50],
	      	queryParams:{
  	       parameter:"{}"
  		},
	      	columns:[[
	          	{field:'id',title:'id',width:100,checkbox:true},
	          	{field:'levelName',title:'会员级别名',width:150},
	          	{field:'startPosition',title:'起始积分',width:150},
	          	{field:'endPosition',title:'结束积分',width:150}
	      	]],
	      	onBeforeLoad:function(){
	      		
	      	},
	      	onLoadSuccess:function(){
	      		buttons();
	      	},
	      	onDblClickRow: function (index, row) {
	      		editMemberLevel1(row);
	        }
 		});  
	});	
   	
/**
	触发“查询”按钮的点击事件后，所执行的方法
*/
function search(){
	$('#list').datagrid({   
      	url:'findMemberLevelByName.do',
      	singleSelect : false, 
      	pagination: true,
      	pageSize:10,pageList:[10,20,50],
  		queryParams:{
	       parameter:"{'levelName':'" + $('#levelNameForSearch').val() +"'}"
		},
      	columns:[[
          	{field:'id',title:'id',width:100,checkbox:true},
          	{field:'levelName',title:'会员级别名',width:150},
          	{field:'startPosition',title:'起始积分',width:150},
          	{field:'endPosition',title:'结束积分',width:150},
      	]],
      	onBeforeLoad:function(){
      		buttons2();
      	},
      	onLoadSuccess:function(){
      		buttons();
      	},
       onDblClickRow: function (index, row) {
    	   editMemberLevel1(row);
       }
	});  
}

/**
	触发“添加会员级别”按钮的点击事件后，所执行的方法
*/
function addMemberLevel() {
	$('#dlg_level').dialog('open').dialog('setTitle','添加会员级别');	//显示对话框，并设置标题
	$('#fm').form('clear');
	$("#state").val("1");
	url = 'addMemberLevel.do';		//重置url
	document.documentElement.style.overflow = "hidden";
}

/**
	触发“添加会员级别”按钮的点击事件后，所执行的方法
*/
function editMemberLevel() {
	var row = $('#list').datagrid('getSelected');	//获取选择的行的人员
	if (row){
		editMemberLevel1(row);
	}else{
   		$.messager.alert('操作提示', '请先选择一个会员级别，再修改！','info');
   	}
}

/**
	触发双击行记录事件后，所执行的方法
*/
function editMemberLevel1(row) {
	
	$.ajax({
		method:'get',
		url:'goToModifyMemberLevel.do',
		data:'id='+row.id,
		dataType: "json",
		success:function(data){
			$("#id").val(row.id);
			$("#levelName").val(row.levelName);
			$("#startPosition").val(row.startPosition);
			$("#endPosition").val(row.endPosition);
			
			$("#state").val(row.state);
		},
		error: function (msg) {
			message_op(false,null);
    	}
	});
	$('#dlg_level').dialog('open').dialog('setTitle','修改会员级别');	//显示对话框，并设置标题
	url = 'modifyMemberLevel.do';		//重置url
}
     
/**
	触发“保存”按钮的点击事件后，所执行的方法
*/
function saveMemberLevel() {
	$('#fm').form('submit', {									//提交表单
		url : url, 											//设置表单请求的地址
		onSubmit : function() {									//在表单提交前要执行的方法，返回false，则放弃请求
			 return $(this).form('validate');					//进行easyUI表单验证
		},
        success : function(result) {							//请求成功后执行的方法,result为请求返回的布尔值
	        	$('#dlg_level').dialog('close'); // close the dialog	//关闭对话框
        		message_op(true,'list');						// 调用Commons_message.js中的方法
        },
        error:function (){										//请求发生错误时，执行的方法
        	message_op(false,null);
        }
        
 	});
 }
    
/**
	触发“删除人员级别”按钮的点击事件后，所执行的方法
*/
function destroyMemberLevel() {
 	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections');						// 获取选择的行的人员
	var ids ='';
	for(var i = 0;i<rows.length;i++){
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}				
	
	if (row){	
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {				//确认对话框,判断是否继续进行操作
		if (r) {	
			$.post('deleteMemberLevel.do', {						//通过post方法发送请求，id为参数
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
   		$.messager.alert('操作提示', '请先选择会员级别，再删除！','info');
   	}
}
    
function buttons2(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'meLevel'
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

function buttons(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'meLevel'
	}, function(data) {
		$.each(eval('('+data+')'),function(index, obj){
			$('#toolbar a').each(function(){
				if($(this).attr('onClick') == obj){
					$(this).show();
				}
				if($("#search").attr('onClick') == obj){
   					$("#search").attr("disabled", false);
   				}
			});
		});
	});
}