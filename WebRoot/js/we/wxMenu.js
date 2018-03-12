
var url;var parameter = "";
$(function() {
	//$('#toolbar a').hide();
	$('#wxMenulist').treegrid({
		url : getCurProjPath()+'/manager/erp/wx/findWxMenuList.do',
        idField:'id',
        treeField:'name',
        nowrap: false,
        rownumbers: true,
		columns : [ [ {
			field : 'name',
			title : '菜单名称',
			width : 100,
			checkbox : true
		}, {
            field : 'type',
            title : '菜单类型',
            width : 150
        }, {
			field : 'url',
			title : '链接',
			width : 150
		}, {
			field : 'pagepath',
			title : '小程序链接',
			width : 150
		}] ],
		onBeforeLoad : function() {},
		onLoadSuccess : function() {
			buttons();
		},
		onDblClickRow : function(index, row) {
			//destroyWxMenubutton1(row);
		}
	});
});
/*function search() {
	$('#wxMenulist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}*/
function addWxMenubutton() {
	$('#dlg_wxMenu').dialog('open').dialog('setTitle', '添加微信菜单管理');
	$('#wxMenufm').form('clear');
	$("#state").val("1");
	url = getCurProjPath()+'/manager/erp/wx/addWxMenu.do';
}
function editWxMenubutton() {
	var row = $('#wxMenulist').datagrid('getSelected');
	if (row) {
		editWxMenubutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个微信菜单管理，再修改！', 'info');
	}
}
function editWxMenubutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/wx/goToModifyWxMenu.do',
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
	$('#dlg_wxMenu').dialog('open').dialog('setTitle', '修改微信菜单管理');
	url = getCurProjPath()+'/manager/erp/wx/modifyWxMenu.do';
}
function saveWxMenubutton() {
	$('#wxMenufm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_wxMenu').dialog('close');
			message_op(true, 'wxMenulist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyWxMenubutton() {
	var row = $('#wxMenulist').datagrid('getSelected');
	var rows = $('#wxMenulist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/wx/deleteWxMenu.do', {ids : ids}
			, function(result) {
				if (result) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#wxMenulist").datagrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#wxMenulist").datagrid("reload");
				}
			}, 'json');
		}
		});
	} else {
		$.messager.alert('操作提示', '请先选择微信菜单管理，再删除！', 'info');
	}
}
function buttons2() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'wxMenu'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#wxMenulist').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#wxMenulist').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'wxMenu'
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
$(function () {
	$("#seltype").combobox({
        onSelect: function(record){
        	debugger
            if(record.value=="view"){
            	$("#clickType").hide();
                $("#viewType").show();
                $("#miniprogramType").hide();
                $("#key").validatebox('disableValidation');
                $("#url").validatebox('enableValidation');
                $("#appid").validatebox('disableValidation');
                $("#pagepath").validatebox('disableValidation');
			}
            if(record.value=="click"){
                $("#clickType").show();
                $("#viewType").hide();
                $("#miniprogramType").hide();
                $("#key").validatebox('enableValidation');
                $("#url").validatebox('disableValidation');
                $("#appid").validatebox('disableValidation');
                $("#pagepath").validatebox('disableValidation');
            }
            if(record.value=="miniprogram"){
                $("#clickType").hide();
                $("#viewType").show();
                $("#miniprogramType").show();
                $("#key").validatebox('disableValidation');
                $("#url").validatebox('enableValidation');
                $("#appid").validatebox('enableValidation');
                $("#pagepath").validatebox('enableValidation');
            }
        }
    });

})