
var url;var parameter = "";
$(function() {
	$('#weCustomerGrouplist').datagrid({
		url : getCurProjPath()+'/manager/erp/we/findWeCustomerList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 100, 500,2000 ],
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
		}
	});
});
function search() {
	$('#weCustomerGrouplist').datagrid(
		'reload',{ 	parameter : "{'customer_name':'" + $('#searchname').val()
		+ "','start_time':'"+$('#searchstart_time').datebox('getValue')
		+ "','end_time':'"+$('#searchend_time').datebox('getValue')
		+"'}"
	});
}
function groupSendbutton(){
	var rows = $('#weCustomerGrouplist').datagrid('getSelections');
	if(rows.length>1){
	$('#dlg_templateCard').dialog('open').dialog('setTitle', '发送集客券');
		$('#templateCardlist').datagrid({
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
	}else{
		$.messager.alert('操作提示', '群发最少选择两条客户记录！', 'info');
	}
}
function inputGroupSend(){
	var openids="";
	$('#fm_groupInfo').form('clear');
	$('#clickDesc').val("请点击");
	var row=$('#templateCardlist').datagrid('getSelected');
	var rows = $('#weCustomerGrouplist').datagrid('getSelections');
	if(row){
		if(rows.length>1){
			for(var i=0;i<rows.length;i++){
				openids=openids+rows[i].openId+",";
			}
			$('#openids').val(openids);
			$('#cardId').val(row.id);
			$('#dlg_gruopInfo').dialog('open').dialog('setTitle', '发送消息');
			url=getCurProjPath()+'/manager/erp/di/groupSend.do';
		}else{
			$.messager.alert('操作提示', '群发最少选择两条客户记录！', 'info');
		}
	}else{
		$.messager.alert('操作提示', '请选择需要发送的集客券！', 'info');
	}
}
function sendGroupInfo(){
	$('#fm_groupInfo').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			if(result=="false"){
				$.messager.alert('操作提示', '发送失败，达到调用次数上限或选择发送数据不正确，请重新尝试', 'error');
			}else{
				$('#dlg_gruopInfo').dialog('close');
				$('#dlg_templateCard').dialog('close');
				message_op(true, 'weCustomerGrouplist');
			}
		},
		error : function() {
			message_op(false, null);
		}
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
