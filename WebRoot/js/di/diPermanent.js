
var url;var parameter = "";
$(function() {
//	$('#toolbar a').hide();
	$('#diPermanentlist').datagrid({
		url : getCurProjPath()+'/manager/erp/di/findDiPermanentList.do',
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
			field : 'diCard',
			title : '卡券名称',
			width : 150,
			formatter:function(value,row){
				if(value!=null)
					return value.card_name;
				else
					return "";
			}
		}, {
			field : 'meMember',
			title : '员工名称',
			width : 150,
			formatter:function(value,row){
				if(value!=null)
					return value.realName;
				else
					return "";
			}
		}, {
			field : 'download',
			title : '下载',
			width : 100,
			formatter:function(value,row){
				if(row.diCard!=null){
					var scene_str=row.meMember.user_id+row.diCard.id;
				return "<a href='"+getCurProjPath()+"/manager/erp/wechat/getPermanentQRcodeDownloads.do?scene_str="+scene_str+"'>下载</a>";
				}
				else
					return "";
			}
		}, {
			field : 'imgPath',
			title : '预览',
			width : 150,
			formatter:function(value,row){
				if(row.diCard!=null){
					var scene_str=row.meMember.user_id+row.diCard.id;
				return "<a href='#' onclick='openbrowse(\""+row.id+"\",\""+scene_str+"\")'>预览</a>";
				}else 
					return "";
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
//			buttons();
		},
		onDblClickRow : function(index, row) {
			//destroyDiPermanentbutton1(row);
		}
	});
});
function search() {
	$('#diPermanentlist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}
function addDiPermanentbutton() {
	$('#dlg_diPermanent').dialog('open').dialog('setTitle', '添加长期二维码');
	$('#diPermanentfm').form('clear');
	$("#state").val("1");
	url = getCurProjPath()+'/manager/erp/di/addDiPermanent.do';
}
function editDiPermanentbutton() {
	var row = $('#diPermanentlist').datagrid('getSelected');
	if (row) {
		editDiPermanentbutton1(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个长期二维码，再修改！', 'info');
	}
}
function editDiPermanentbutton1(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/di/goToModifyDiPermanent.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#id").val(data.id);
			if(data.meMember!=null){
			$("#meName").val(data.meMember.realName);
			$("#meid").val(data.meMember.id);
			}
			if(data.diCard!=null)
			{$("#cardName").val(data.diCard.card_name);
			$("#cardid").val(data.diCard.id);
			}
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg_diPermanent').dialog('open').dialog('setTitle', '修改长期二维码');
	url = getCurProjPath()+'/manager/erp/di/modifyDiPermanent.do'; 
}
function saveDiPermanentbutton() {
	$('#diPermanentfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diPermanent').dialog('close');
			message_op(true, 'diPermanentlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function destroyDiPermanentbutton() {
	var row = $('#diPermanentlist').datagrid('getSelected');
	var rows = $('#diPermanentlist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/di/deleteDiPermanent.do', {ids : ids}
			, function(result) {
				if (result) {
					$.messager.alert('操作提示', '操作成功!', 'info');
					$("#diPermanentlist").datagrid("reload");
				} else {
					$.messager.alert('操作提示', '操作失败，不能删除！', 'info');
					$("#diPermanentlist").datagrid("reload");
				}
			}, 'json');
		}
		});
	} else {
		$.messager.alert('操作提示', '请先选择长期二维码，再删除！', 'info');
	}
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diPermanent'
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
function choiceCard(){
	$('#dlg_Card').dialog('open').dialog('setTitle', '选择卡券');
		$('#cardlist').datagrid({
			url : getCurProjPath()+'/manager/erp/di/findDiCardList.do',
			singleSelect : true,
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
				field : 'vaildtime',
				title : '有效期',
				width : 100,
				formatter:function(value){
					if(value!=null){
						var testDate = new Date(value); 
						var testStr = testDate.format("yyyy-MM-dd"); 
						return testStr;
						}else
							return "";
				}
			}, {
				field : 'share_num',
				title : '分享数量',
				width : 100
			}, {
				field : 'card_worth',
				title : '卡券面值',
				width : 100
			}] ]
		});
}

function choiceMe(){
	$('#dlg_member').dialog('open').dialog('setTitle', '选择员工');
		$('#memberlist').datagrid({
			url : getCurProjPath()+'/manager/erp/me/findMemberByConditionGroup.do',
			singleSelect : false,
			pagination : true,
			pageSize:10,pageList:[10,20,50],
			queryParams : {
				parameter : "{}"
			},
			columns : [ [
					{
						field : 'id',
						title : 'id',
						width : 100,
						checkbox : true
					},
					{
						field : 'cellphone',
						title : '手机',
						width : 100
					},
					{
						field : 'realName',
						title : '真实名',
						width : 150
					},
					{
						field : 'sex',
						title : '性别',
						width : 70,
						formatter : function(value, row, index) {
							if (value == 1) {
								return '男';
							}
							if (value == 0) {
								return '女';
							}}}]]
		});
}

function getcard(){
	var row = $('#cardlist').datagrid('getSelected');
	if(row){
		$("#cardName").val(row.card_name);
		$("#cardid").val(row.id);
		$('#dlg_Card').dialog('close');
	}
}

function getmember(){
	var row = $('#memberlist').datagrid('getSelected');
	if(row){
		$("#meName").val(row.realName);
		$("#meid").val(row.id);
		$('#dlg_member').dialog('close');
	}
}
function openbrowse(employeeid,id){
	var url1=getCurProjPath()+"/manager/erp/wechat/getPermanentQRcodePicture.do?sence_str="+id+"&employeeid="+employeeid;
	window.open(url1,'_blank','width=300,height=300,top=100px,left=0px');
		return false;
	}
