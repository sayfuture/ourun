var url;// 表单提交请求的地址
// var parameter = "{'aaa':'aaa','bbb':'bbb'}";
var parameter = "";
var oldOperation = "";// 修改时验证是否修改了操作

$(function(){
	$.parser.parse();  
});

/**
 * 触发“添加积分”按钮的点击事件后，所执行的方法
 */
function newPoint() {
	$('#dlg').dialog('open').dialog('move', {
		top : 100
	}).dialog('setTitle', '添加积分'); // 显示对话框，并设置标题
	$('#fm').form('clear'); // 清空表单
	url = 'addPoint.do'; // 重置url
	document.documentElement.style.overflow = "hidden";
}
/**
 * 触发“修改功能”按钮的点击事件后，所执行的方法
 */
function editPoint() {
	var row = $('#list').datagrid('getSelected'); // 获取选择的行的功能
	if (row) {
		// 异步请求，通过id获得用户的功能，并把功能导入表单中
		$.ajax({
			method : 'get',
			url : 'getPoint.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				$("#id").val(row.id);
				$("#uname").val(row.name);
				$("#pointnum").val(row.pointnum);
				$("#operation").val(row.operation);
				$("#remarks").val(row.remarks);
				$('#type').val(row.type);
				oldOperation = $("#operation").val();
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dlg').dialog('open').dialog('setTitle', '修改功能'); // 显示对话框，并设置标题
		url = 'updatePoint.do'; // 重置url
	} else {
		$.messager.alert('操作提示', '请先选择积分，再修改！', 'info');
	}
}
function editPoint1(row) {
	// 异步请求，通过id获得用户的功能，并把功能导入表单中
	$.ajax({
		method : 'get',
		url : 'getPoint.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.id);
			$("#uname").val(row.name);
			$("#pointnum").val(row.pointnum);
			$("#operation").val(row.operation);
			$("#remarks").val(row.remarks);
			$('#type').val(row.type);
			oldOperation = $("#operation").val();
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg').dialog('open').dialog('setTitle', '修改功能'); // 显示对话框，并设置标题
	url = 'updatePoint.do'; // 重置url

}
/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function savePoint() {

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
function destroyPoint() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的功能
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) { // 判断是否存在被选择的行
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deletePoints.do', { // 通过post方法发送请求，id为参数
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
				return param[0] == 0 || value == oldOperation;
			},
			message : '名字已存在'
		}
	});

	// 输入内容改变时验证名字是否存在
	$('#operation')
			.change(
					function() {

						// 获取已有验证规则
						var rule = eval("({" + $('#operation').attr('data-options')
								+ "})").validType;

						// 动态请求
						$.post('findListByOperation.do', {
							parameter : "{'operation':'" + $('#operation').val() + "'}"
						}, function(data, status) {
							var result = data.substring(9, 10);
							// console.log(result);

							// 动态绑定验证规则
							$('#operation').validatebox({
								required : true,
								validType : [ rule, 'single[' + result + ']' ]
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
		parameter : "{'name':'" + $('#name').val() + "','type':'" + $('#_type').val() + "','operation':'" + $('#_operation').val() + "'}" // 参数
	});
}

/*
 * DataGrid 初始化
 */
$(function() {
	$('#toolbar a').hide();
	
	$('#list').datagrid({
		url : 'findPointList.do',
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
		}, {
			field : 'name',
			title : '名称',
			width : 250
		}, {
			field : 'pointnum',
			title : '点数',
			width : 200,formatter:function(value,row,index){
          		if(value < 0){return '<font color=red>'+value+'</font>';}
          		if(value >= 0){return value;}
          	}
		}, {
			field : 'operation',
			title : '调用参数',
			width : 250
		},{
			field : 'type',
			title : '积分类型',
			width : 200,formatter:function(value,row,index){
          		if(value==1){return '商户';}
          		if(value==2){return '会员';}
          	}
		},{
			field : 'remarks',
			title : '备注',
			width : 500
		} ] ],
		onLoadSuccess:function(){
			buttons();
		}
	});
});

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