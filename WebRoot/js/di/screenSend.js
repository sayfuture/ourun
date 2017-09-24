
var url;var parameter = "";
$(function() {
//	$('#toolbar a').hide();
	$('#weCustomerlist').datagrid({
		url : getCurProjPath()+'/manager/erp/we/findWeCustomerList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50,2000 ],
		queryParams : {parameter : "{}"},
		columns : [ [ {
			field : 'openId',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'customer_name',
			title : '客户名称',
			width : 100
		}, {
			field : 'wechat_name',
			title : '微信昵称',
			width : 150
		}, {
			field : 'phone',
			title : '手机号',
			width : 100
		}, {
			field : 'car_type',
			title : '车型',
			width : 150
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
			width : 150,
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
		'reload',{ 	parameter : "{'customer_name':'" + $('#searchname').val()
		+ "','start_time':'"+$('#searchstart_time').datebox('getValue')
		+ "','end_time':'"+$('#searchend_time').datebox('getValue')
		+"'}"
	});
}
function wxsendbutton(){
	var row = $('#weCustomerlist').datagrid('getSelected');
	if (row) {
		$('#dlg_card').dialog('open').dialog('setTitle', '发送集客券');
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
				width : 100
			}, {
				field : 'use_num',
				title : '可使用数量',
				width : 100
			}, {
				field : 'share_num',
				title : '分享数量',
				width : 100
			}, {
				field : 'card_worth',
				title : '卡券面值',
				width : 100
			}, {
				field : 'companyId',
				title : '门店',
				width : 150,
				formatter:function(value){
					
				}
			}] ]
		});
		url=getCurProjPath()+'/manager/erp/di/screenSendimpl.do';
	} else {
		$.messager.alert('操作提示', '请先选择需要发送的客户！', 'info');
	}
	
}

function screenSend(){
	var cardrow = $('#cardlist').datagrid('getSelected');
	if(cardrow){
	var openids="";
	var row = $('#weCustomerlist').datagrid('getSelections');
	for(var i=0;i<row.length;i++){
		openids=openids+row[i].openId+",";
	}
	var templatecardrow = $('#templateCardlist').datagrid('getSelected');
	if(templatecardrow){
	$("#weCustomerIds").val(openids);
	$("#cardId").val(cardrow.id);
	$("#templateId").val(templatecardrow.id);
	$('#screenSendfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_card').dialog('close');
			$('#dlg_templateCard').dialog('close');
			message_op(true, 'weCustomerlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
	}else{
		$.messager.alert('操作提示', '请先选择需要发送的模板信息！', 'info');
		return false;
	}
	}else{
		$.messager.alert('操作提示', '请先选择需要发送的集客券！', 'info');
		return false;
	}
}
function opentemplateCard(){
	var cardrow = $('#cardlist').datagrid('getSelected');
	if(cardrow){
	$('#dlg_templateCard').dialog('open').dialog('setTitle', '选择模板');
	$('#templateCardlist').datagrid({
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
		}] ]
	});
	}else{
		$.messager.alert('操作提示', '请先选择需要发送的集客券！', 'info');
	}
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'groupSend'
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
