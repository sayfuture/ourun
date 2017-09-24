
var url;var parameter = "";
$(function() {
//	$('#toolbar a').hide();
	$('#coTemplatelist').datagrid({
		url : getCurProjPath()+'/manager/erp/co/findCoTemplateList.do',
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
			field : 'title',
			title : '标题',
			width : 150
		}, {
			field : 'content',
			title : '内容',
			width : 250
		}] ],
		onBeforeLoad : function() {},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
			//destroyCoTemplatebutton1(row);
		}
	});
});
function search() {
	$('#coTemplatelist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}
function refreshCoTemplatebutton(){
	$.messager.confirm('', '您确定要刷新该记录吗?', function(r) {
		if (r) {
			$.ajax({
				method : 'post',
				url : getCurProjPath()+'/manager/erp/co/refreshCoTemplate.do',
				dataType : "json",
				success : function(data) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#coTemplatelist").datagrid("reload");
				},
				error : function(msg) {
					message_op(false, null);
				}
			});
		}
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'coTemplate'
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
