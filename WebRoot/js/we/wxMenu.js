
var url;var parameter = "";
var info="";
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
			width : 150
		}, {
            field : 'type',
            title : '菜单类型',
            width : 80
        }, {
            field : 'key',
            title : '点击信息',
            width : 150
        }, {
			field : 'url',
			title : '链接',
			width : 300
		}, {
			field : 'pagepath',
			title : '小程序链接',
			width : 300
		},{field:'optAu',title:'添加二级菜单',width:90,
			formatter:function(value,row,index){
            if(row._parentId==null||row._parentId==""){
                return  "<img alt='img' src='" + getCurProjPath() + "/js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:4px 17px 0px 0;width: 15px; height: 12px;' /><span style='cursor:pointer;display:block;margin:-20px 0 4px 19px;' onclick='addWxMenubutton(\""+row.id+"\");'>新增</span>";
            }
        	else return "";
		} }
		] ],
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
	$('#wxMenulist').treegrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}*/
function addWxMenubutton(superId) {
	$('#dlg_wxMenu').dialog('open').dialog('setTitle', '添加微信菜单管理');
	$('#wxMenufm').form('clear');
	if(superId!=null&&superId!=""){
		var node = $('#wxMenulist').treegrid('find',superId);
		$("#p_id").val(node.id);
		$("#p_idInfo").val(node.name);
	}
	info="add";
	url = getCurProjPath()+'/manager/erp/wx/addWxMenu.do';
}
function editWxMenubutton() {
	var row = $('#wxMenulist').treegrid('getSelected');
    if(row!=null&&(row.p_id==""&&row.p_id==null)){
        $("#p_id").val(row.id);
        $("#p_idInfo").val(row.name);
    }
	if (row) {
		editWxMenubutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个微信菜单管理，再修改！', 'info');
	}
	info="";
}
function editWxMenubutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/wx/goToModifyWxMenu.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(data.id);
			if(data.superWxMenu!=null&&data.superWxMenu!=""){
                $("#p_id").val(data.superWxMenu.id);
                $("#p_idInfo").val(data.superWxMenu.name);
			}
			$("#seltype").combobox('select',data.type);
			$("#name").val(data.name);
			if(data.type=="view"){
                $("#key").validatebox('disableValidation');
                $("#url").validatebox('enableValidation');
                $("#appid").validatebox('disableValidation');
                $("#pagepath").validatebox('disableValidation');
                $("#url").val(data.url);
			}
			if(data.type=="click"){
                $("#key").validatebox('enableValidation');
                $("#url").validatebox('disableValidation');
                $("#appid").validatebox('disableValidation');
                $("#pagepath").validatebox('disableValidation');
                $("#key").val(data.key);
			}
            if(data.type=="miniprogram"){
                $("#key").validatebox('disableValidation');
                $("#url").validatebox('enableValidation');
                $("#appid").validatebox('enableValidation');
                $("#pagepath").validatebox('enableValidation');
                $("#url").val(data.url);
                $("#appid").val(data.appid);
                $("#pagepath").val(data.pagepath);
			}},
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
			if(info=="add"&&result!="\"\""){
				$.messager.alert('操作提示', result, 'info');
				message_op(false, null);
			}else{
				$('#dlg_wxMenu').dialog('close');
				$('#wxMenulist').treegrid('reload');
			}
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyWxMenubutton() {
	info="";
	var row = $('#wxMenulist').treegrid('getSelected');
	var rows = $('#wxMenulist').treegrid('getSelections');
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
					$("#wxMenulist").treegrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#wxMenulist").treegrid("reload");
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
			var opts = $('#wxMenulist').treegrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#wxMenulist').treegrid("showColumn", opts[i]);
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
        	if(record==null) return;
            if(record.value=="view"){
/*            	$("#clickType").hide();
                $("#viewType").show();
                $("#miniprogramType").hide();*/
                $("#key").validatebox('disableValidation');
                $("#url").validatebox('enableValidation');
                $("#appid").validatebox('disableValidation');
                $("#pagepath").validatebox('disableValidation');
			}
            if(record.value=="click"){
/*                $("#clickType").show();
                $("#viewType").hide();
                $("#miniprogramType").hide();*/
                $("#key").validatebox('enableValidation');
                $("#url").validatebox('disableValidation');
                $("#appid").validatebox('disableValidation');
                $("#pagepath").validatebox('disableValidation');
            }
            if(record.value=="miniprogram"){
           /*     $("#clickType").hide();
                $("#viewType").show();
                $("#miniprogramType").show();*/
                $("#key").validatebox('disableValidation');
                $("#url").validatebox('enableValidation');
                $("#appid").validatebox('enableValidation');
                $("#pagepath").validatebox('enableValidation');
            }
        }
    });

});
function createWxMenubutton(){
	info="";
    $.ajax({
        method : 'post',
        url : getCurProjPath()+'/manager/erp/wx/createWxMenu.do',
        data : 'id=',
        dataType : "json",
        success : function(data) {
       		if(data=="true"||data==true){
                $.messager.alert('操作提示', '操作成功!', 'info');
			}else {
                $.messager.alert('操作提示', '微信创建菜单失败!', 'info');
			}
        },
        error : function(msg) {
            message_op(false, null);
        }
    });
}
function delWXMenu(){
	info="";
	$.ajax({
		method : 'post',
		url : getCurProjPath()+'/manager/erp/wx/delWXMenu.do',
		dataType : "json",
		success : function(data) {
			if(data=="true"||data==true){
				$.messager.alert('操作提示', '操作成功!', 'info');
			}else {
				$.messager.alert('操作提示', '微信删除菜单失败!', 'info');
			}
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
}