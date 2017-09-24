
var url;var parameter = "";
$(function() {
	$('#toolbar a').hide();
	$('#diSendRecodelist').datagrid({
		url : getCurProjPath()+'/manager/erp/di/findDiSendRecodeList.do',
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
			field : 'name',
			title : '名称',
			width : 150
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
		}
	});
});
function search() {
	$('#diSendRecodelist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}
function addDiSendRecodebutton() {
	$('#dlg_diSendRecode').dialog('open').dialog('setTitle', '添加集客券发送记录管理');
	$('#diSendRecodefm').form('clear');
	$("#state").val("1");
	url = getCurProjPath()+'/manager/erp/di/addDiSendRecode.do';
}
function saveDiSendRecodebutton() {
	$('#diSendRecodefm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diSendRecode').dialog('close');
			message_op(true, 'diSendRecodelist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diSendRecode'
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
