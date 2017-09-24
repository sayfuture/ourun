
var url;var parameter = "";
$(function() {
	$('#toolbar a').hide();
	$('#diProcessRecordlist').datagrid({
		url : getCurProjPath()+'/manager/erp/di/findDiProcessRecordList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {parameter : "{}"},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'weCustomer1',
			title : '微信昵称',
			width : 150,
			formatter:function(value,row){
				if(row!=null)
					return row.weCustomer.wechat_name;
			}
		}, {
			field : 'weCustomer',
			title : '客户名称',
			width : 150,
			formatter:function(value,row){
				if(value!=null)
					return value.customer_name;
			}
		}, {
			field : 'diCard',
			title : '卡券名称',
			width : 150,
			formatter:function (value,row){
				if(value!=null)
					return value.card_name;
				else
					return "";
			}
		}, {
			field : 'card_num',
			title : '可使用数量',
			width : 150
		},{
			field : 'gettime',
			title : '领取时间',
			width : 100,
			formatter:function(value,row){
				if(value!=null){
					var testDate = new Date(value); 
					var testStr = testDate.format("yyyy-MM-dd"); 
					return testStr;
					}else
						return "";
			}
		}
//		, {
//			field : 'share_card_num',
//			title : '分享数量',
//			width : 150
//		}
		, {
			field : 'status',
			title : '状态',
			width : 150,
			formatter:function(value,row){
				if(value==0)
					return "<font color='green'>可用</font>";
				if(value==1)
					return "<font color='red'>已过期</font>";
				if(value==2)
					return "<font color='red'>已使用</font>";
			}
		}] ],
		onBeforeLoad : function() {},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
		}
	});
});
function search() {
	$('#diProcessRecordlist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}
function addDiProcessRecordbutton() {
	$('#dlg_diProcessRecord').dialog('open').dialog('setTitle', '添加优惠券核销');
	$('#diProcessRecordfm').form('clear');
	$("#state").val("1");
	url = getCurProjPath()+'/manager/erp/di/addDiProcessRecord.do';
}
function editDiProcessRecordbutton() {
	var row = $('#diProcessRecordlist').datagrid('getSelected');
	if (row) {
		editDiProcessRecordbutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个优惠券核销，再修改！', 'info');
	}
}
function editDiProcessRecordbutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/di/goToModifyDiProcessRecord.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.id);
			$("#name").val(row.name);
			$("#state").val(row.state);
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg_diProcessRecord').dialog('open').dialog('setTitle', '修改优惠券核销');
	url = getCurProjPath()+'/manager/erp/di/modifyDiProcessRecord.do'; 
}
function saveDiProcessRecordbutton() {
	$('#diProcessRecordfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diProcessRecord').dialog('close');
			message_op(true, 'diProcessRecordlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyDiProcessRecordbutton() {
	var row = $('#diProcessRecordlist').datagrid('getSelected');
	var rows = $('#diProcessRecordlist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/di/deleteDiProcessRecord.do', {ids : ids}
			, function(result) {
				if (result) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#diProcessRecordlist").datagrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#diProcessRecordlist").datagrid("reload");
				}
			}, 'json');
		}
		});
	} else {
		$.messager.alert('操作提示', '请先选择优惠券核销，再删除！', 'info');
	}
}
function buttons2() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diProcessRecord'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#diProcessRecordlist').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#diProcessRecordlist').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diProcessRecord'
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
