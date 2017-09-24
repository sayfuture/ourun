
var url;var parameter = "";
$(function() {
//	$('#toolbar a').hide();
	$('#diCardlist').datagrid({
		url : getCurProjPath()+'/manager/erp/di/findDiCardList.do',
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
			field : 'card_name',
			title : '卡券名称',
			width : 120
		}, {
			field : 'use_num',
			title : '可使用数量',
			width : 100,
			formatter:function(value,row){
				if(row.total_num!=null&&row.used_num!=null)
					return row.total_num-row.used_num;
			}
		}
		, {
		field : 'total_num',
		title : '本次活动总张数',
		width : 100
		}, {
			field : 'used_num',
			title : '已领取张数',
			width : 100
			}
		, {
			field : 'card_worth',
			title : '卡券面值',
			width : 100
		}, {
			field : 'vaildtime',
			title : '有效截止日期',
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
			field : 'vaildtime1',
			title : '提醒',
			width : 150,
			formatter:function(value,row){
				if(row.vaildtime!=null){
					var now=new Date();
					var testDate = new Date(row.vaildtime); 
					if(now>row.vaildtime){
						return "<font color='red'>已过期,请更新时间</font>";
					}else{
						return "<font color='green'>正常</font>";
					}
					return testStr;
					}else
						return "";
			}
		}, 
		{
			field : 'companyId',
			title : '门店',
			width : 150,
			formatter:function(value){
				
			}
		}, {
			field : 'createUser',
			title : '创建者',
			width : 150
		}, {
			field : 'createTimeBasePo',
			title : '创建时间',
			width : 150
		}] ],
		onBeforeLoad : function() {},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
			//destroyDiCardbutton1(row);
		}
	});
});
function search() {
	$('#diCardlist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}
function addDiCardbutton() {
	$('#dlg_diCard').dialog('open').dialog('setTitle', '添加优惠券');
	$('#diCardfm').form('clear');
	$("#state").val("1");
	initComS_S("contentType","","name",'',"/manager/erp/co/findCoTypeList.do?level=1","","contentId",'2',"title",'',"/manager/erp/co/getCoContentByAuthority.do");
	url = getCurProjPath()+'/manager/erp/di/addDiCard.do';
}
function editDiCardbutton() {
	var row = $('#diCardlist').datagrid('getSelected');
	if (row) {
		editDiCardbutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个优惠券，再修改！', 'info');
	}
}
function editDiCardbutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/di/goToModifyDiCard.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(row.id);
			$("#card_name").val(row.card_name);
			if(data.vaildtime!=null){
				var testDate = new Date(data.vaildtime); 
				var testStr = testDate.format("yyyy-MM-dd"); 
				$("#vaildtime").datebox('setValue',testStr);
			}
			$("#use_num").numberbox('setValue',data.use_num);
			$("#total_num").numberbox('setValue',data.total_num);
			$("#card_worth").numberbox('setValue',data.card_worth);
			$("#use_explain").val(row.use_explain);
			initComS_S("contentType","","name",data.coContent.coType.id,"/manager/erp/co/findCoTypeList.do?level=1",'',"contentId",'2',"title",data.coContent.id,"/manager/erp/co/getCoContentByAuthority.do");
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg_diCard').dialog('open').dialog('setTitle', '修改优惠券');
	url = getCurProjPath()+'/manager/erp/di/modifyDiCard.do'; 
}
function saveDiCardbutton() {
	$('#diCardfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diCard').dialog('close');
			message_op(true, 'diCardlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyDiCardbutton() {
	var row = $('#diCardlist').datagrid('getSelected');
	var rows = $('#diCardlist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/di/deleteDiCard.do', {ids : ids}
			, function(result) {
				if (result) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#diCardlist").datagrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#diCardlist").datagrid("reload");
				}
			}, 'json');
		}
		});
	} else {
		$.messager.alert('操作提示', '请先选择优惠券，再删除！', 'info');
	}
}
function buttons2() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diCard'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#diCardlist').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#diCardlist').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diCard'
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
