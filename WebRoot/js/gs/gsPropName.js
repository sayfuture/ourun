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
		url : 'findGsPropNameList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 5,
		pageList : [ 5, 20, 50 ],
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
		}, {
			field : 'isColorProp',
			title : '颜色属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isSizeProp',
			title : '尺码属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isSaleProp',
			title : '销售属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isKeyProp',
			title : '关键属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isFilterProp',
			title : '搜索属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isCustProp',
			title : '自定义属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isMust',
			title : '必需属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'isMulti',
			title : '多选属性',
			width : 100,
			formatter : fmtYesOrNot
		}, {
			field : 'setPropValues',
			title : '设置属性值',
			width : 90,
			hidden : true,
			formatter : function(value, row, index) {
				return "<img alt='img' src='" + getCurProjPath() + "/js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:6px 2px 0px 0;width: 15px; height: 15px;' />" +
						"<a style='cursor:pointer;display:block;margin:-19px 0 1px 20px;' class='easyui-linkbutton btn' onclick='setPropValues(\"" + row.id + "\");'>设置属性值</a>";
			}
		} ] ],
		onBeforeLoad : function() {
			rowButtons();
		},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
			editGsPropName1(row);
		}
	});
	
});

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */
function searchGsPropName() {
	$('#list').datagrid('reload', {
		parameter : "{"
			+ "'propName':'" + $('#propNameForSearch').val() + "'"
			+ "}"
	});
}

/**
 * 触发“添加商品属性”按钮的点击事件后，所执行的方法
 */
function addGsPropName() {
	$('#dlg').dialog('open').dialog('setTitle', '添加商品属性'); //显示对话框，并设置标题
	$('#form').form('clear');
	$("#state").val("1");
//	var list=[{id:"123",name:"mycheckbox"},{id:"456",name:"mycheckbox1"} ,{id:"789",name:"mycheckbox2"}];
//	for(var node in list){
//		$("#selectSub").append('<input type="checkbox" value="'+list[node].id+'" name="mycheckbox">'+list[node].name+'</input>');
//	}
	
//	 var items = "123,789".split(/[,，]/g);
//     $.each(items, function (index, item) {
//         $("input[name='mycheckbox']").each(function () {
//             if ($(this).val() == item) {
//                 $(this).attr("checked",true);
//             }
//         });
//     });
	
	url = 'addGsPropName.do'; //重置url
}

/**
 * 触发“修改商品属性”按钮的点击事件后，所执行的方法
 */
function editGsPropName() {
	var row = $('#list').datagrid('getSelected'); //获取选择的行的人员
	if (row) {
		editGsPropName1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个商品属性，再修改！', 'info');
	}
}

/**
 * 触发双击行记录事件后，所执行的方法
 */
function editGsPropName1(row) {

	$.ajax({
		method : 'get',
		url : 'goToModifyGsPropName.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.id);
			$("#propName").val(row.propName);
			$("#seqNo").val(row.seqNo);
			$("#state").val(row.state);
			$("#isColorProp").attr("checked", row.isColorProp == 1 ? true : false);
			$("#isSizeProp").attr("checked", row.isSizeProp == 1 ? true : false);
			$("#isSaleProp").attr("checked", row.isSaleProp == 1 ? true : false);
			$("#isKeyProp").attr("checked", row.isKeyProp == 1 ? true : false);
			$("#isFilterProp").attr("checked", row.isFilterProp == 1 ? true : false);
			$("#isCustProp").attr("checked", row.isCustProp == 1 ? true : false);
			$("#isMust").attr("checked", row.isMust == 1 ? true : false);
			$("#isMulti").attr("checked", row.isMulti == 1 ? true : false);
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg').dialog('open').dialog('setTitle', '修改商品属性'); //显示对话框，并设置标题
	url = 'modifyGsPropName.do'; //重置url
}

/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveGsPropName() {
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
 * 触发“删除商品属性”按钮的点击事件后，所执行的方法
 */
function destroyGsPropName() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的人员
	var ids = '';
	for (var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { //确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deleteGsPropName.do', { //通过post方法发送请求，id为参数
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
		$.messager.alert('操作提示', '请先选择商品属性，再删除！', 'info');
	}
}

function buttons() {
	$.post('../../getEmployeeButtons.do', { //通过post方法发送请求，id为参数
		'functionType' : 'gsPropName'
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
		'functionType' : 'gsPropName'
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

fmtYesOrNot = function(value, row, index) {
	if(value == 1) {
		return '是'
	} else {
		return '否'
	}
}

setPropValues = function(propId) {
	$("#propId").val(propId);
	$('#dlg_gsPropValueList').dialog('open').dialog('setTitle', '设置属性值');		//显示对话框，并设置标题
	$('#toolbar_gsPropValueList a').hide();
	$('#list_gsPropValueList').datagrid({
		url : 'findGsPropValueList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {
			parameter : "{" 
					+ "'propId':'" + propId + ""
					+ "'}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
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
			
		},
		onLoadSuccess : function() {
			buttonsGsPropValue();
		},
		onDblClickRow : function(index, row) {
			
		}
	});
}

searchGsPropValue = function() {
	$('#list_gsPropValueList').datagrid('reload', {
		parameter : "{"
			+ "'propId':'" + $("#propId").val() + ","
			+ "'propValue':'" + $('#propValueForSearch').val() + ""
			+ "'}"
	});
}

/**
 * 触发“添加属性值”按钮的点击事件后，所执行的方法
 */
function addGsPropValue() {
	var propId = $("#propId").val();
	
	$('#dlg_gsPropValueForm').dialog('open').dialog('setTitle', '添加属性值'); //显示对话框，并设置标题
	$('#form_gsPropValue').form('clear');
	$("#form_gsPropValue #propId").val(propId);
	$("#form_gsPropValue #state").val("1");
	url = 'addGsPropValue.do'; //重置url
}

/**
 * 触发“修改属性值”按钮的点击事件后，所执行的方法
 */
function editGsPropValue() {
	var row = $('#list_gsPropValueList').datagrid('getSelected'); //获取选择的行的人员
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
			$("#form_gsPropValue #id").val(row.id);
			$("#form_gsPropValue #propValue").val(row.propValue);
			$("#form_gsPropValue #seqNo").val(row.seqNo);
			$("#form_gsPropValue #state").val(row.state);
			
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg_gsPropValueForm').dialog('open').dialog('setTitle', '修改属性值'); //显示对话框，并设置标题
	url = 'modifyGsPropValue.do'; //重置url
}

/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveGsPropValue() {
	$('#form_gsPropValue').form('submit', { //提交表单
		url : url, //设置表单请求的地址
		onSubmit : function() { //在表单提交前要执行的方法，返回false，则放弃请求
			return $(this).form('validate'); //进行easyUI表单验证
		},
		success : function(result) { //请求成功后执行的方法,result为请求返回的布尔值
			$('#dlg_gsPropValueForm').dialog('close'); // close the dialog	//关闭对话框
			message_op(true, 'list_gsPropValueList'); // 调用Commons_message.js中的方法
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
	var row = $('#list_gsPropValueList').datagrid('getSelected');
	var rows = $('#list_gsPropValueList').datagrid('getSelections'); // 获取选择的行的人员
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
						$("#list_gsPropValueList").datagrid("reload");
					} else {
						$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
						$("#list_gsPropValueList").datagrid("reload");
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('操作提示', '请先选择属性值，再删除！', 'info');
	}
}

buttonsGsPropValue = function() {
	$.post('../../getEmployeeButtons.do', { //通过post方法发送请求，id为参数
		'functionType' : 'gsPropValue'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			$('#toolbar_gsPropValueList a').each(function() {
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