
var url;var parameter = "";
var temp;
$(function() {
//	$('#toolbar a').hide();
	$('#diScanSendlist').datagrid({
		url : getCurProjPath()+'/manager/erp/me/findMemberByConditionGroup.do',
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
			field : 'qrCode',
			title : '二维码下载',
			width : 80,
			formatter:function(value,row){
				if(row.diCard!=null){
					var sence_id=row.user_id+row.diCard.id;
				return "<a href='"+getCurProjPath()+"/manager/erp/wechat/getQRcodeDownloads.do?sence_id="+sence_id+"'>下载</a>";
				}
				else
					return "";
			}
		}, {
			field : 'qrCodePreview',
			title : '二维码预览',
			width : 80,
			formatter:function(value,row){
				if(row.diCard!=null){
					var sence_id=row.user_id+row.diCard.id;
				return "<a href='#' onclick='openbrowse(\""+row.id+"\",\""+sence_id+"\")'>预览</a>";
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
		}, {
			field : 'qrCode1',
			title : '二维码下载',
			width : 80,
			formatter:function(value,row){
				if(row.diCard1!=null){
					var sence_id=row.user_id+row.diCard.id;
					return "<a href='http://"+getCurProjPath()+"/ourun/manager/erp/wechat/getQRcodeDownloads.do?sence_id="+sence_id+"'>下载</a>";
				}else
						return "";
			}
		}, {
			field : 'qrCodePreview1',
			title : '二维码预览',
			width : 80,
			formatter:function(value,row){
				if(row.diCard!=null){
					var sence_id=row.user_id+row.diCard1.id;
					return "<a href='#' onclick='openbrowse(\""+row.id+"\",\""+sence_id+"\")'>预览</a>";
				}	else 
						return "";
			}
		},{
			field : 'bind',
			title : '操作绑定',
			width : 100,
			formatter:function(value,row){
				return "<img alt='img' src='"+getCurProjPath()+"/js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:6px 2px 0px 0;width: 15px; height: 15px;' /><span style='cursor:pointer;display:block;margin:-19px 0 1px 20px;' onclick=bindCard('"
						+ row.id + "');>绑定积客券</span>";
			}
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
function bindCard(id){
	$("#id").val("");
	$('#dlg_diScanSend').dialog('open').dialog('setTitle', '绑定积客券');
	$("#id").val(id);
	url=getCurProjPath()+'/manager/erp/me/bindCard.do'; 
}
function savebind(t){
	temp=t;
	$('#dlg_bindDiCardlist').dialog('open').dialog('setTitle', '选择积客券'); 
	$('#diCardlist').datagrid({
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
		}] ]
	});
}
function savebindDiCardbutton(){
	$('#diScanSendfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diScanSend').dialog('close');
			message_op(true, 'diScanSendlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function savediScanSendbutton(){
	var row = $('#diCardlist').datagrid('getSelected');
	if(temp==0){
		$("#diCardId").val(row.id);
		$("#diCardName").val(row.card_name);
	}
	if(temp==1){
		$("#diCardId1").val(row.id);
		$("#diCardName1").val(row.card_name);
	}
	$('#dlg_bindDiCardlist').dialog('close');
}
function search() {
	$('#diScanSendlist').datagrid(
		'reload',{ 
		parameter : "{'name':'" + $('#searchname').val()+ "'}"
	});
}

function saveScanSendbutton() {
	$('#diScanSendfm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#dlg_diScanSend').dialog('close');
			message_op(true, 'diCardlist');
		},
		error : function() {
			message_op(false, null);
		}
	});
}
function buttons2() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diScanSend'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#diScanSendlist').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#diScanSendlist').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', {
		'functionType' : 'diScanSend'
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

function openbrowse(employeeid,id){
	var url1=getCurProjPath()+"/manager/erp/wechat/getQRcodePicture.do?sence_id="+id+"&employeeid="+employeeid;
	window.open(url1,'_blank','width=300,height=300,top=100px,left=0px');
		return false;
	}

