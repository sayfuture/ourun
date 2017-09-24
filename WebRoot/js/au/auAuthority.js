
var url;// 表单提交请求的地址
var parameter = "";
var oldName = "";// 修改时验证是否修改了名字

$(function(){

	$.parser.parse();  
});

/**
 * 触发“添加功能”按钮的点击事件后，所执行的方法
 */
function newObj() {

	$('#dlg').dialog('open').dialog('move', {
		top : 10
	}).dialog('setTitle', '添加功能'); // 显示对话框，并设置标题
	$('#fm').form('clear'); // 清空表单
	$('#level').val(1);
	initMenuSelect('');
	url = 'addAuthority.do'; // 重置url
	document.documentElement.style.overflow = "hidden";
}
/**
 * 触发“修改功能”按钮的点击事件后，所执行的方法
 */
function editObj() {
	var row = $('#list').datagrid('getSelected'); // 获取选择的行的功能
	if (row) {
		// 异步请求，通过id获得用户的功能，并把功能导入表单中
		$.ajax({
			method : 'get',
			url : 'getAuthority.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				$("#id").val(row.id);
				$("#uname").val(row.name);
				$("#ename").val(row.ename);
				$("#upwd").val(row.url);
				$("#remarks").val(row.remarks);
				$('#level').val(row.level);
				oldName = $("#uname").val();
				initMenuSelect(row.auMenu.id);
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dlg').dialog('open').dialog('setTitle', '修改功能'); // 显示对话框，并设置标题
		url = 'updateAuthority.do'; // 重置url
	} else {
		$.messager.alert('操作提示', '请先选择功能，再修改！', 'info');
	}
}
function editUser1(row) {
	// 异步请求，通过id获得用户的功能，并把功能导入表单中
	$.ajax({
		method : 'get',
		url : 'getAuthority.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.id);
			$("#uname").val(row.name);
			$("#ename").val(row.ename);
			$("#upwd").val(row.url);
			$("#remarks").val(row.remarks);
			$('#level').val(row.level);
			oldName = $("#uname").val();
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg').dialog('open').dialog('setTitle', '修改功能'); // 显示对话框，并设置标题
	url = 'updateAuthority.do'; // 重置url

}
/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveObj() {
	$('#fm').form('submit', { // 提交表单
		url : url, // 设置表单请求的地址
		onSubmit : function() { // 在表单提交前要执行的方法，返回false，则放弃请求
			return $(this).form('validate'); // 进行easyUI表单验证
		},
		success : function(result) { // 请求成功后执行的方法,result为请求返回的布尔值
			$('#dlg').dialog('close'); // close the dialog //关闭对话框
			message_op(true, 'list'); // 调用Commons_message.js中的方法
		},
		error : function() { // 请求发生错误时，执行的方法
			message_op(false, null);
		}

	});
}
/**
 * 触发“删除功能”按钮的点击事件后，所执行的方法
 */
function destroyObj() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的功能
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) { // 判断是否存在被选择的行
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deleteBinch.do', { // 通过post方法发送请求，id为参数
					ids : ids
				}, function(result) { // 异步请求完成后的回调函数
					if (result) {
						$.messager.alert('操作提示', '操作成功!', 'info'); // 调用Commons_message.js中的方法
						$("#list").datagrid("reload");
					} else {
						$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
						$("#list").datagrid("reload");
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('操作提示', '请先选择功能，再删除！', 'info');
	}
}
/**
 * 动态验证用户名是否重复
 */
$(function() {
	/**
	 * 自定义名字验证规则
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		single : {
			validator : function(value, param) {
				return param[0] == 0 || value == oldName;
			},
			message : '名字已存在'
		}
	});

	// 输入内容改变时验证名字是否存在
	$('#uname').change(
					function() {

						// 获取已有验证规则
						var rule = eval("({" + $('#uname').attr('data-options')
								+ "})").validType;

						// 动态请求
						$.post('findList.do', {
							parameter : "{'name':'" + $('#uname').val() + "'}"
						}, function(data, status) {
							var obj = eval('('+data+')');
							// console.log(result);

							// 动态绑定验证规则
							$('#uname').validatebox({
								required : true,
								validType : [ rule, 'single[' + obj.total + ']' ]
							});
						});
						// end ajax method
					});
	// end chenge method

});

function buttons(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'auAuthority'
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

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */

function search() {
	parameter = "'name':'" + $('#name').val() + "'"; // 定义dategrid请求参数
	$('#list').datagrid('reload', { // 重载行，等同于'load'方法。即重新请求url加载数据
		parameter : "{'name':'" + $('#name').val() + "','type':'1'}" // 参数
	});
}

/*
 * DataGrid 初始化
 */
$(function() {
	$('#toolbar a').hide();
	$('#list').datagrid({
		url : 'findList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {
			parameter : "{}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		},
		{field:'index',title:'序号',width:38, align: 'center',formatter:function(val,row,index){
		     var options = $("#list").datagrid('getPager').data("pagination").options; 
		     var currentPage = options.pageNumber;
		     var pageSize = options.pageSize;
		     return (pageSize * (currentPage -1))+(index+1);
		 }
		},{
			field : 'name',
			title : '名称',
			width : 150
		}, {
			field : 'url',
			title : '路径',
			width : 350
		},{
			field : 'level',
			title : '菜单位置',
			width : 100,
			formatter : function(value, row, index) {
				return row.auMenu.name;
			}
		}, {
			field : 'remarks',
			title : '备注',
			width : 400
		}
		
		] ],
		onLoadSuccess:function(){
			buttons();
		}
	});
});


function initMenuSelect(select){
	$('#auMenuId').combobox({
			url:'getAuMenuList.do',
			valueField:'id',
			textField:'name',
			editable:false ,
			onLoadSuccess:function(){
				
				var data = $('#auMenuId').combobox('getData');
				
				select+='';
				if(select==undefined||select.length<1){
					$('#auMenuId').combobox('setValue', data[0].id);
				}else if (data.length > 0) {
         			$('#auMenuId').combobox('select', select);
    			}
			}
		});
}


document.onkeydown = function(event) {
	var target, code, tag;
	if (!event) {
		event = window.event; // 针对ie浏览器
		target = event.srcElement;
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "TEXTAREA") {
				return true;
			} else {
				return false;
			}
		}
	} else {
		target = event.target; // 针对遵循w3c标准的浏览器，如Firefox
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "INPUT") {
				return false;
			} else {
				return true;
			}
		}
	}
};