/**
 * 表单URL
 */
var url;
/**
 * 查询参数
 */
var parameter = "";

/**
 * 列表初始化
 */
$(function() {
	$('#toolbar a').hide();
	$('#list').datagrid({
		url : 'findGsPropValueList.do',
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
			field : 'propName',
			title : '属性名',
			width : 150
		}, {
			field : 'propValue',
			title : '属性值',
			width : 150
		}, {
			field : 'seqNo',
			title : '排序号',
			width : 100
		} ] ],
		onBeforeLoad : function() {
			// rowButtons
		},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
			editGsPropValue1(row);
		}
	});
});

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */
function searchGsPropValue() {
	$('#list').datagrid('reload', {
		parameter : "{"
			+ "'propName':'" + $('#propNameForSearch').val() + "',"
			+ "'propValue':'" + $('#propValueForSearch').val() + "'"
			+ "}"
	});
}

/**
 * 触发“添加属性值”按钮的点击事件后，所执行的方法
 */
function addGsPropValue() {
	$('#dlg').dialog('open').dialog('setTitle', '添加属性值'); //显示对话框，并设置标题
	$('#form').form('clear');
	$("#state").val("1");
	url = 'addGsPropValue.do'; //重置url
}

/**
 * 触发“修改属性值”按钮的点击事件后，所执行的方法
 */
function editGsPropValue() {
	var row = $('#list').datagrid('getSelected'); //获取选择的行的人员
	if (row) {
		editGsPropValue1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个属性值，再修改！', 'info');
	}
}

/**
 * 触发双击行记录事件后，所执行的方法
 */
function editGsPropValue1(row) {

	$.ajax({
		method : 'get',
		url : 'goToModifyGsPropValue.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.id);
			$("#propValue").val(row.propValue);
			$("#propId").val(row.propId);
			$("#propName").val(row.propName);
			$('#seqNo').numberbox('setValue',row.seqNo); 
			$("#state").val(row.state);
			
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg').dialog('open').dialog('setTitle', '修改属性值'); //显示对话框，并设置标题
	url = 'modifyGsPropValue.do'; //重置url
}

/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveGsPropValue() {
	$('#form').form('submit', { //提交表单
		url : url, //设置表单请求的地址
		onSubmit : function() { //在表单提交前要执行的方法，返回false，则放弃请求
			return $(this).form('validate'); //进行easyUI表单验证
		},
		success : function(result) { //请求成功后执行的方法,result为请求返回的布尔值
			$('#dlg').dialog('close'); // close the dialog	//关闭对话框
			message_op(true, 'list'); // 调用Commons_message.js中的方法
		},
		error : function() { //请求发生错误时，执行的方法
			message_op(false, null);
		}

	});
}

/**
 * 触发“删除属性值”按钮的点击事件后，所执行的方法
 */
function destroyGsPropValue() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的人员
	var ids = '';
	for (var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { //确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deleteGsPropValue.do', { //通过post方法发送请求，id为参数
					ids : ids
				}, function(result) { //异步请求完成后的回调函数
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
		$.messager.alert('操作提示', '请先选择属性值，再删除！', 'info');
	}
}

function buttons() {
	$.post('../../getEmployeeButtons.do', { //通过post方法发送请求，id为参数
		'functionType' : 'gsPropValue'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			$('#toolbar a').each(function() {
				if ($(this).attr('onClick') == obj) {
					$(this).show();
				}
				if ($("#search").attr('onClick') == obj) {
					$("#search").attr("disabled", false);
				}
			});
		});
	});
}

function rowButtons() {
	$.post('../../getEmployeeButtons.do', { //通过post方法发送请求，id为参数
		'functionType' : 'gsPropValue'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#list').datagrid('getColumnFields', false);
			for (var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#list').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}

function selectGsPropName() {
	$('#select_gsPropName').dialog('open').dialog('setTitle', '选择属性');
	$('#list_gsPropName').datagrid({
		url : 'findGsPropNameList.do',
		singleSelect : true,
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
			field : 'propName',
			title : '属性名称',
			width : 150
		}, {
			field : 'seqNo',
			title : '排序号',
			width : 100
		} ] ]
	});
}

function searchGsPropName() {
	$('#list_gsPropName').datagrid('reload', {
		parameter : "{"
			+ "'propName':'" + $('#select_gsPropName #propName').val() + ""
			+ "'}"
	});
}

function saveGsPropName() {
	var row = $('#list_gsPropName').datagrid('getSelected');
	if (row) {
		$("#propId").val(row.id);
		$("#propName").val(row.propName);
	}
	$('#select_gsPropName').dialog('close');
}