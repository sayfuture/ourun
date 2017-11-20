
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
	$('#dlg_meinfo').dialog('open').dialog('setTitle', '员工及卡券信息');
		$('#meinfolist').datagrid({
			url : getCurProjPath()+'/manager/erp/me/findMemberByConditionGroup.do',
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
				field : 'realName',
				title : '员工姓名',
				width : 100
			}, {
				field : 'diCard.card_name',
				title : '优惠券（一）',
				width : 100,
				formatter:function(value,row){
					if(row.diCard!=null)
						return row.diCard.card_name;
					else
						return "";
				}
			}, {
				field : 'get_limit',
				title : '活动总数量',
				width : 80,
				formatter:function(value,row){
					if(row.diCard!=null)
						return row.diCard.total_num;
					else
						return "";
				}
			}, {
				field : 'used_num',
				title : '已领取数量',
				width : 80,
				formatter:function(value,row){
					if(row.diCard!=null)
						return row.diCard.used_num;
					else
						return "";
				}
			}, {
				field : 'end_time',
				title : '活动截止日期',
				width : 80,
				formatter:function(value,row){
					if(row.diCard!=null){
						var testDate = new Date(row.diCard.vaildtime);
						var testStr = testDate.format("yyyy-MM-dd");
						return testStr;
					}else
						return "";
				}
			}, {
				field : 'diCard1.card_name',
				title : '优惠券（二）',
				width : 100,
				formatter:function(value,row){
					if(row.diCard1!=null)
						return row.diCard1.card_name;
					else
						return "";
				}
			}, {
				field : 'get_limit1',
				title : '活动总数量',
				width : 80,
				formatter:function(value,row){
					if(row.diCard!=null)
						return row.diCard1.total_num;
					else
						return "";
				}
			}, {
				field : 'used_num1',
				title : '已领取数量',
				width : 80,
				formatter:function(value,row){
					if(row.diCard1!=null)
						return row.diCard1.used_num;
					else
						return "";
				}
			}, {
				field : 'end_time',
				title : '活动截止日期',
				width : 80,
				formatter:function(value,row){
					if(row.diCard1!=null){
						var testDate = new Date(row.diCard1.vaildtime);
						var testStr = testDate.format("yyyy-MM-dd");
						return testStr;
					}else
						return "";
				}
			}] ]
		});
	}else{
		$.messager.alert('操作提示', '群发最少选择两条客户记录！', 'info');
	}
}
function inputmeinfo(){
	$('#fm_groupInfo').form('clear');
	var merow=$('#meinfolist').datagrid('getSelected');
	if(merow){
		$('#dlg_templateCard').dialog('open').dialog('setTitle', '发送集客券');
		$('#meId').val(merow.user_id);
	}else{
		$.messager.alert('操作提示', '请选择需要发送的员工信息！', 'info');
	}
}
function inputGroupSend(){
	var cardInfo=$("input[name='cardinfo']:checked").val();
	if(cardInfo==null||cardInfo==""){
		$.messager.alert('操作提示', '请选择需要的优惠券！', 'info');
		return;
	}
	var openids="";
	$('#templateCardlist').datagrid('getSelected');
	$('#clickDesc').val("请点击");
	var row=$('#meinfolist').datagrid('getSelected');
	var rows = $('#weCustomerGrouplist').datagrid('getSelections');
	if(row){
		if(rows.length>1){
			for(var i=0;i<rows.length;i++){
				openids=openids+rows[i].openId+",";
			}
			$('#openids').val(openids);
			if(cardInfo=="1")
				$('#cardId').val(row.diCard.id);
			if(cardInfo=="2")
				$('#cardId').val(row.diCard1.id);
			$('#dlg_gruopInfo').dialog('open').dialog('setTitle', '发送消息');
			url=getCurProjPath()+'/manager/erp/di/groupSend.do';
		}else{
			$.messager.alert('操作提示', '群发最少选择两条客户记录！', 'info');
		}
	}else{
		$.messager.alert('操作提示', '请选择员工信息！', 'info');
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
				$('#dlg_meinfo').dialog('close');
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
