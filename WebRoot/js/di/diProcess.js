
var url;var parameter = "";
$(function() {
//	$('#toolbar a').hide();
	$('#diProcesslist').datagrid({
		url : getCurProjPath()+'/manager/erp/di/findDiProcessList.do',
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
		},{
			field : 'end_time',
			title : '截止时间',
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
	$('#diProcesslist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}
function addDiProcessbutton() {
	$('#dlg_diProcess').dialog('open').dialog('setTitle', '添加优惠券核销');
	$('#diProcessfm').form('clear');
	$("#state").val("1");
	url = getCurProjPath()+'/manager/erp/di/addDiProcess.do';
}
function customDiProcessbutton(){
	var row = $('#diProcesslist').datagrid('getSelected');
	if (row) {
		$('#diProcessfm').form('clear');
		$('#dlg_diProcess').dialog('open').dialog('setTitle', '核销优惠券');
		$('#customerName').val(row.weCustomer.customer_name);
		$('#customerPhone').val(row.weCustomer.phone);
		$("#state").val("1");
		$("#id").val(row.id);
		url = getCurProjPath()+'/manager/erp/di/consumeCard.do';
	}else {
		$.messager.alert('操作提示', '请先选择一个优惠券核销，再核销！', 'info');
	}
}


function editDiProcessbutton() {
	var row = $('#diProcesslist').datagrid('getSelected');
	if (row) {
		editDiProcessbutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个优惠券核销，再修改！', 'info');
	}
}
function editDiProcessbutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/di/goToModifyDiProcess.do',
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
	$('#dlg_diProcess').dialog('open').dialog('setTitle', '修改优惠券核销');
	url = getCurProjPath()+'/manager/erp/di/modifyDiProcess.do'; 
}
function saveDiProcessbutton() {
	var row = $('#diProcesslist').datagrid('getSelected');
	var num=row.card_num;
	var nownum=$("#customerNums").numberbox('getValue');
	if(nownum>num){
		$.messager.alert('操作提示', '核销数量必须小于可用数量！', 'info');
		return ;
	}
	$('#diProcessfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diProcess').dialog('close');
			message_op(true, 'diProcesslist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyDiProcessbutton() {
	var row = $('#diProcesslist').datagrid('getSelected');
	var rows = $('#diProcesslist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/di/deleteDiProcess.do', {ids : ids}
			, function(result) {
				if (result) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#diProcesslist").datagrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#diProcesslist").datagrid("reload");
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
		'functionType' : 'diProcess'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#diProcesslist').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#diProcesslist').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diProcess'
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
