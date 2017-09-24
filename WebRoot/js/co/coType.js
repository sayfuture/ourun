var url;// 表单提交请求的地址

/**
 * 触发“添加类型”按钮的点击事件后，所执行的方法
 */

function newObj(pid) {
	$('#fm').form('clear'); // 清空表单
	var row = $('#list').datagrid('getSelected'); // 获取选择的行的内容
	if(row==null){pid =0;}else{ pid = row.id;}
	if (pid != 0) {
		$.ajax({
			method : 'post',
			url : getCurProjPath() + '/manager/erp/co/getCoType.do',
			data : 'id=' + pid,
			dataType : 'json',
			success : function(data) {
				$("#pid").val(pid);
				$("#pname").val(data.name);
				$("#level").val(data.level + 1);
			},
			error : function(data) {
				message_op(false, null);
			}
		});
	} else {
		$("#pname").val("无");
		$("#level").val("0");
		$("#pid").val(null);
	}
	$('#dlg').dialog('open').dialog('setTitle', '添加内容分类'); // 显示对话框，并设置标题
	url = getCurProjPath() + '/manager/erp/co/addCoType.do'; // 重置url
	document.documentElement.style.overflow = "hidden";
}

/**
 * 触发“修改类型”按钮的点击事件后，所执行的方法
 */
function editObj() {
	var node = $('#list').treegrid('getSelected');
	if (node) {
		$.ajax({
			method : 'post',
			url : getCurProjPath() + '/manager/erp/co/getCoType.do',
			data : 'id=' + node.id,
			dataType : "json",
			success : function(data) {
				$("#id").val(node.id);
				$("#uname").val(data.name);
				$("#engName").val(data.engName);
				$("#pname").val("不可修改");
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dlg').dialog('open').dialog('setTitle', '修改栏目内容'); // 显示对话框，并设置标题
		url = getCurProjPath() + '/manager/erp/co/updateCoType.do'; // 重置url

	} else {
		$.messager.alert("操作提示", "请先选择您要修改的栏目内容！", "info");
	}
}


/**
 * 触发“删除内容”按钮的点击事件后，所执行的方法
 */
function destroyObj() {
	var row = $('#list').treegrid('getSelected');
	if (row) { 
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deleteBinchCoType.do', { // 通过post方法发送请求，id为参数
					ids : row.id
				}, function(result) { // 异步请求完成后的回调函数
					if (result) {
						$("#list").treegrid('remove', row.id);
		                $('#list').treegrid('reloadFooter');
		                $.messager.alert('操作提示', '操作成功!', 'info'); // 调用Commons_message.js中的方法
					} else {
						$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('操作提示', '请先选择栏目内容，再删除！', 'info');
	}
}

/*
 * DataGrid 初始化
 */
$(function() {
	$('#toolbar a').hide();
	$('#list').treegrid({
						url : getCurProjPath()	+ '/manager/erp/co/findCoType.do',
						treeField : 'name',
						nowrap : false,
						rownumbers : true,
						animate : true,
						singleSelect : false,
						idField : 'id',
						singleSelect : true,
						collapsible : true,
						columns : [ [
								 {
									field : 'id',
									title : 'id',
									width : 100,
									checkbox : true
								},
								{
									title : '栏目内容名称',
									field : 'name',
									width : 300
								},{
									title : '英文名称',
									field : 'engName',
									width : 300
								},{
									title : '创建时间',
									field : 'createTimeBasePo',
									width : 300
								}  ] ],
						onBeforeLoad : function(row, param) {
							buttons2();
							for ( var i in row) {
								if (i == "id") {
									$(this).treegrid('options').url = getCurProjPath()
											+ "/manager/erp/co/findCoType.do?pid="
											+ row[i];
								}
							}
							return true;
						},
						onLoadSuccess : function() {
							buttons();
						}
					});
	
});

function buttons() {
	$.post('../../getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'coType'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			$('#toolbar a').each(function() {
				if ($(this).attr('onClick') == obj) {
					$(this).show();
				}
			});
		});
	});
}

function buttons2() {
	$.post('../../getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'coType'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#list').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#list').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}

// 获取项目的URL
function getCurProjPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
}

function saveObj() {
	$.post(getCurProjPath() + '/manager/erp/co/checkCoTypeName2.do', {
		name : $('#uname').val(),
		engName : $('#engName').val()
	}, function(status) {
		saveObject();
	});
}

function saveObject() {
	$('#fm').form('submit', { // 提交表单
		url : url, // 设置表单请求的地址
		async: true,
		onSubmit : function() { // 在表单提交前要执行的方法，返回false，则放弃请求
			return $(this).form('validate');
		},
		success : function(_data) {
			 var row = $('#list').treegrid('getSelected');
			 if($('#id').val()==""){
				 console.log("添加！");
			     if (row){
			    	 $("#list").treegrid('reload',row.id);
			     }
			     else{
			    	 $("#list").treegrid('reload');
			     }
			      
			 }
			 else{
				 console.log('修改~');
				 var obj =  $("#list").treegrid('getParent',row.id);
				 if(obj==null){
					 $("#list").treegrid('reload');
				 }
				 else{
					 $("#list").treegrid('reload',obj.id);
				 }
			 }
			
		     
		     
			 $('#dlg').dialog('close');
		},
		error : function() { // 请求发生错误时，执行的方法
			message_op(false, null);
		}
	});
}




