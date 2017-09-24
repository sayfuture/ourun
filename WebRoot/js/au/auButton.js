var url;// 表单提交请求的地址
var parameter = "";

/**
 * 触发“添加按钮”按钮的点击事件后，所执行的方法
 */
function newObj() {
	$('#dlg').dialog('open').dialog('setTitle', '添加按钮'); // 显示对话框，并设置标题
	$('#fm').form('clear'); // 清空表单
	url = getCurProjPath()+'/manager/erp/au/addAuButton.do'; // 重置url
	document.documentElement.style.overflow = "hidden";
}
/**
 * 触发“修改部门”按钮的点击事件后，所执行的方法
 */
function editObj() {
	var row = $('#list').datagrid('getSelected'); // 获取选择的行的部门
	if (row) {
		// 异步请求，通过id获得用户的部门，并把部门导入表单中
		$.ajax({
			method : 'post',
			url : getCurProjPath()+'/manager/erp/au/getAubutton.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				$("#id").val(row.id);
				$("#funName").val(row.funName);
				$("#btnName").val(row.btnName);
				$("#notes").val(row.notes);
				$("#authorityName").val(row.auAuthority.name);
				$("#authorityId").val(row.auAuthority.id);
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dlg').dialog('open').dialog('setTitle', '修改按钮'); // 显示对话框，并设置标题
		url = getCurProjPath()+'/manager/erp/au/updateAuButton.do'; // 重置url
	} else {
		$.messager.alert('操作提示', '请先选择按钮，再修改！', 'info');
	}
}

/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveObj() {
	$('#fm').form('submit', { // 提交表单
		url : url, // 设置表单请求的地址
		onSubmit : function() { // 在表单提交前要执行的方法，返回false，则放弃请求
			return $(this).form('validate'); // 进行easyUI表单验证
		},
		success : function(result) { // 请求成功后执行的方法,result为请求返回的布尔值
			$('#dlg').dialog('close'); // close the dialog //关闭对话框
			message_op(true, 'list'); // 调用Commons_message.js中的方法
		},
		error : function() { // 请求发生错误时，执行的方法
			message_op(false, null);
		}
	});
}
/**
 * 触发“删除部门”按钮的点击事件后，所执行的方法
 */
function destroyObj() {
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的部门
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (rows) { // 判断是否存在被选择的行
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post(getCurProjPath()+'/manager/erp/au/deleteAuButtons.do', { // 通过post方法发送请求，id为参数
					ids : ids
				}, function(result) { // 异步请求完成后的回调函数
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
		$.messager.alert('操作提示', '请先选择按钮，再删除！', 'info');
	}
}

/**
 * 
 */
function selectAuth(typeStr) {
	$('#auDialog').data({type:typeStr}).dialog('open').dialog('setTitle', '选择菜单'); // 显示对话框，并设置标题
	$('#aulist').datagrid({
		url : getCurProjPath()+'/manager/erp/au/findList.do',
		singleSelect : true,
		selectOnCheck: false,
      	checkOnSelect: true,
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
		},{field:'index',title:'序号',width:38, align: 'center',formatter:function(val,row,index){
		     var options = $("#aulist").datagrid('getPager').data("pagination").options; 
		     var currentPage = options.pageNumber;
		     var pageSize = options.pageSize;
		     return (pageSize * (currentPage -1))+(index+1);
		 }
		},
		{
			field : 'name',
			title : '名称',
			width : 150
		}, {
			field : 'url',
			title : '路径',
			width : 200
		}, {
			field : 'remarks',
			title : '备注',
			width : 300
		}, {
			field : 'level',
			title : '菜单位置',
			width : 100,
			formatter : function(value, row, index) {
				if (value == 1) {
					return "<span>系统管理</span>";
				}
				if (value == 2) {
					return "<span>工作量管理</span>";
				}
				if (value == 3) {
					return "<span>商户管理</span>";
				}
				if (value == 4) {
					return "<span>工作日程管理</span>";
				}
				if (value == 5) {
					return "<span>地域管理</span>";
				}
				if (value == 6) {
					return "<span>常用工具管理</span>";
				}
			}
		} ] ],
      	onLoadSuccess: function (data,index) {
          	if(data){
          		var type = $('#auDialog').data('type');
          		var id = '';
          		id = type==1?$('#authorityIdBase').val():$('#authorityId').val();
          		$.each(data.rows, function(index,item){
          			if(id!=''||id!=undefined){
						if(item.id==id){
		             		$('#aulist').datagrid('checkRow', index);
						}
          			}
          		});
			}
        } 
	});
}

/**
 * 按钮查询方法
 */
function search() {
	$('#list').datagrid(
			'reload',
			{ // 重载行，等同于'load'方法。即重新请求url加载数据
				parameter : "{'btnName':'" + $('#btnNameSearch').val()
						+ "','auAuthority':'" + $('#authorityIdBase').val()
						+ "'}" // 参数
			});
}

/**
 * 菜单查询方法
 * 
 * @returns
 */
function auSearch() {
	$('#aulist').datagrid('reload', { // 重载行，等同于'load'方法。即重新请求url加载数据
		parameter : "{'name':'" + $('#auName').val() + "','type':'1'}" // 参数
	});
}

/**
 * 保存菜单方法
 * 
 * @returns
 */
function saveAu() {
	var row = $('#aulist').datagrid('getChecked');			//获取选择的行的功能
	var type = $('#auDialog').data('type');
	if(row.length>0){
		if(type==1){
			$('#authorityNameSearch').val(row[0].name);
			$('#authorityIdBase').val(row[0].id);
		}else{
			// 用于添加或者修改按钮功能
			$('#authorityName').val(row[0].name);
			$('#authorityId').val(row[0].id);
		}
	}else{
		if(type==1){
			$('#authorityNameSearch').val('');
			$('#authorityIdBase').val('');
		}else{
			// 用于添加或者修改按钮功能
			$('#authorityName').val('');
			$('#authorityId').val('');
		}
	}
	//
	$('#auDialog').dialog('close');
}

/*
 * DataGrid 初始化（按钮）
 */
$(function() {
	$('#toolbar a').hide();
	$('#list').datagrid({
		url : getCurProjPath()+'/manager/erp/au/findAuButtonDatagrid.do',
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
		},{field:'index',title:'序号',width:38, align: 'center',formatter:function(val,row,index){
		     var options = $("#list").datagrid('getPager').data("pagination").options; 
		     var currentPage = options.pageNumber;
		     var pageSize = options.pageSize;
		     return (pageSize * (currentPage -1))+(index+1);
		 }
		},{
			field : 'btnName',
			title : '按钮名称',
			width : 250
		}, {
			field : 'funName',
			title : '方法名称',
			width : 250
		}, {
			field : 'auAuthority',
			title : '所属菜单',
			width : 150,
			formatter:function(val){
          		return val.name;
          	}
		}, {
			field : 'notes',
			title : '按钮说明',
			width : 400
		} ] ],
		onLoadSuccess:function(){
      		buttons();
      	},
		onDblClickRow : function(index, row) {
			editObj();
		}
	});
});
function buttons(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'auButton'
	}, function(data) {
		$.each(eval('('+data+')'),function(index, obj){
			$('#toolbar a').each(function(){
				if($(this).attr('onClick') == obj){
					$(this).show();
				}
				if($("#search").attr('onClick') == obj){
   					$("#search").attr("disabled", false);
   				}
			});
		});
	});
}

//获取项目的URL
function getCurProjPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPath + projectName;
}
