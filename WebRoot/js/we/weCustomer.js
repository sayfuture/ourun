
var url;var parameter = "";
$(function() {
//	$('#toolbar a').hide();
	$('#weCustomerlist').datagrid({
		url : getCurProjPath()+'/manager/erp/we/findWeCustomerList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {parameter : "{}"},
		columns : [ [ {
			field : 'id',
			title : 'openId',
			width : 100,
			checkbox : true
		}, {
			field : 'wechat_name',
			title : '微信昵称',
			width : 150
		}, {
			field : 'customer_name',
			title : '客户名称',
			width : 100
		}, {
			field : 'phone',
			title : '手机号',
			width : 100
		}, {
			field : 'car_type',
			title : '车型',
			width : 150
		}, {
            field : 'province',
            title : '省',
            width : 80,
			formatter:function(value,row){
            	var temp;
                $.ajax({
                    method : 'get',
                    url : getCurProjPath()+'/news/erp/findProvincesById.do',
                    data : 'provinceId=' + value,
                    dataType : "json",
                    async : false,
                    success : function(data) {
                        temp=data.p;
                    },
                    error : function(msg) {
                        temp=msg;
                    }
                });
                return temp;
			}
        }, {
            field : 'city',
            title : '市',
            width : 80,
            formatter:function(value,row){
            	var temp;
                $.ajax({
                    method : 'get',
                    url : getCurProjPath()+'/news/erp/findCitiesById.do',
                    data : 'cityId=' + value,
                    dataType : "json",
                    async : false,
                    success : function(data) {
                        temp= data.n;
                    },
                    error : function(msg) {
                        temp= msg;
                    }
                });
                return temp;
            }
        }, {
            field : 'area',
            title : '县区',
            width : 80,
            formatter:function(value,row){
                var temp;
                $.ajax({
                    method : 'get',
                    url : getCurProjPath()+'/news/erp/findAreaById.do',
                    data : 'areaId=' + value,
                    dataType : "json",
                    async : false,
                    success : function(data) {
                        temp= data.s;
                    },
                    error : function(msg) {
                        temp= msg;
                    }
                });
                return temp;
            }
        }, {
            field : 'address',
            title : '地址',
            width : 200
        }, {
			field : 'kilometers',
			title : '行驶公里数',
			width : 100
		}, {
			field : 'next_maintain_time',
			title : '预计下次保养时间',
			width : 150,
			formatter:function(value){
				if(value!=null){
				var testDate = new Date(value); 
				var testStr = testDate.format("yyyy-MM-dd"); 
				return testStr;
				}else
					return "";
			}
		}, {
			field : 'next_maintain_content',
			title : '预计下次保养内容',
			width : 280
		}, {
			field : 'is_follow',
			title : '是否关注',
			width : 100,
			formatter:function(value){
			if(value==0)
				return "<font color='red'>未关注</font>";	
			if(value==1)
				return "<font color='green'>已关注</font>";
			}
		}] ],
		onBeforeLoad : function() {},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
			//destroyWeCustomerbutton1(row);
		}
	});
});
function search() {
	$('#weCustomerlist').datagrid(
		'reload',{ 
		parameter : "{'customer_name':'" + $('#searchname').val()
					+ "','start_time':'"+$('#searchstart_time').datebox('getValue')
					+ "','end_time':'"+$('#searchend_time').datebox('getValue')
					+"'}"
	});
}
function addWeCustomerbutton() {
	$('#dlg_weCustomer').dialog('open').dialog('setTitle', '添加微信客户管理');
	$('#weCustomerfm').form('clear');
	$("#state").val("1");
	url = getCurProjPath()+'/manager/erp/we/addWeCustomer.do';
}
function editWeCustomerbutton() {
	var row = $('#weCustomerlist').datagrid('getSelected');
	if (row) {
		editWeCustomerbutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个微信客户管理，再修改！', 'info');
	}
}
function editWeCustomerbutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/we/goToModifyWeCustomer.do',
		data : 'id=' + row.openId,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.openId);
			$("#wechat_name").val(data.wechat_name);
			$("#phone").val(data.phone);
			$("#kilometers").numberbox('setValue',data.kilometers);
			$("#customer_name").val(data.customer_name);
			$("#car_type").val(data.car_type);
			if(data.next_maintain_time!=null){
				var testDate = new Date(data.next_maintain_time); 
				var testStr = testDate.format("yyyy-MM-dd"); 
				$("#next_maintain_time").datebox('setValue',testStr);
			}
			$("#next_maintain_content").val(data.next_maintain_content);
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg_weCustomer').dialog('open').dialog('setTitle', '修改微信客户管理');
	url = getCurProjPath()+'/manager/erp/we/modifyWeCustomer.do'; 
}
function saveWeCustomerbutton() {
	$('#weCustomerfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_weCustomer').dialog('close');
			message_op(true, 'weCustomerlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyWeCustomerbutton() {
	var row = $('#weCustomerlist').datagrid('getSelected');
	var rows = $('#weCustomerlist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/we/deleteWeCustomer.do', {ids : ids}
			, function(result) {
				if (result) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#weCustomerlist").datagrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#weCustomerlist").datagrid("reload");
				}
			}, 'json');
		}
		});
	} else {
		$.messager.alert('操作提示', '请先选择微信客户管理，再删除！', 'info');
	}
}
function buttons2() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'weCustomer'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#weCustomerlist').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#weCustomerlist').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'weCustomer'
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
