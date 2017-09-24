var url;// 表单提交请求的地址
var parameter = "";
var oldName = "";// 修改时验证是否修改了名字
var oldName2 = "";// 修改时验证是否修改了名字

/*
 * DataGrid 初始化
 */
$(function() {
//	$('#toolbar a').hide();
	
	//初始化组下拉框
	$('#list').datagrid({
						url : getCurProjPath()+'/manager/erp/me/findMemberByConditionGroup.do',
						singleSelect : true,
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
										}
									}
								},
								{
									field : 'birthday',
									title : '生日',
									width : 100,
									formatter : function(value, row, index) {
										if(value==null)
											return;
										var birthdayDate = new Date(value);
										var birthdayDateString = birthdayDate
												.format("yyyy-MM-dd");
										return birthdayDateString;
									}
								},
								{
									field : 'maritalState',
									title : '个人情况',
									width : 70,
									formatter : function(value, row, index) {
										if (value == 1) {
											return '单身';
										}
										if (value == 2) {
											return '恋爱';
										}
										if (value == 3) {
											return '已婚';
										}
										if (value == 0) {
											return '保密';
										}
									}
								},
								{
									field : 'identity',
									title : '身份',
									width : 70,
									formatter : function(value, row, index) {
										if (value == 0) {
											return '<font color="brown">其它</font>';
										}
										if (value == 1) {
											return '<font color="yellow">临时</font>';
										}
										if (value == 2) {
											return '<font color="green">在职</font>';
										}
										if (value == 3) {
											return '<font color="red">离职</font>';
										}
									}
								},{
									field : 'showMeInfo',
									title : '查看详情',
									width : 150,
									formatter : function(value, row, index) {
										return "<img alt='img' src='../../../js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:6px 2px 3px 0;width: 15px; height: 15px;' /><span style='cursor:pointer;display:block;margin:-19px 0 1px 20px;' onclick='showStatisticsInfo(\""
										+ row.id + "\");'>查看发送详情</span>";
									}
								}
								] ],
						onBeforeLoad : function() {
							buttons2();
						},
						onLoadSuccess : function() {
							buttons();
						},
						onDblClickRow : function(index, row) {
							editUser1(row);
						}
					});
});

function showStatisticsInfo(id){
	$('#dlg').dialog('open').dialog('setTitle', '发送详情');
	$('#weCustomerlist').datagrid({
		url : getCurProjPath()+'/manager/erp/di/findDiSendRecodeList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {parameter : "{'meId':'"+id+"'}"},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'weCustomer.wechat_name',
			title : '微信昵称',
			width : 150,
			formatter:function(value,row){
				if(row.weCustomer!=null)
					if(row.weCustomer.wechat_name!=null)
					return row.weCustomer.wechat_name;
			return "";
			}
		}, {
			field : 'weCustomer.customer_name',
			title : '客户名称',
			width : 100,
			formatter:function(value,row){
				if(row.weCustomer!=null)
					if(row.weCustomer.customer_name!=null)
					return row.weCustomer.customer_name;
			return "";
			}
		}, {
			field : 'dicard',
			title : '卡券名称',
			width : 100,
			formatter:function(value,row){
				if(row.diCard!=null)
					return row.diCard.card_name;
			return "";
			}
		}, {
			field : 'weCustomer.phone',
			title : '手机号',
			width : 100,
			formatter:function(value,row){
				if(row.weCustomer!=null)
					if(row.weCustomer.phone!=null)
					return row.weCustomer.phone;
			return "";
			}
		}, {
			field : 'sharenum',
			title : '分享人数',
			width : 70
		}, {
			field : 'createTimeBasePo',
			title : '获得时间',
			width : 120
		}, {
			field : 'weCustomer.is_follow',
			title : '是否关注',
			width : 80,
			formatter:function(value,row){
				if(row.weCustomer!=null)
					if(row.weCustomer.is_follow!=null)
						if(row.weCustomer.is_follow==0)
							return "<font color='red'>未关注</font>";	
						if(row.weCustomer.is_follow==1)
							return "<font color='green'>已关注</font>";
				return "";
			}
		}] ]
	});
}

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */
function search() {
	$('#list').datagrid(
	'reload',{ 
		parameter : "{'realName':'" + $('#realNameForSearch').val()+
					"','cellphone':'"+$('#cellphoneForSearch').val()+"'}"
	});
}
function statisticsSearch(){
	var row = $('#list').datagrid('getSelected');
	$('#weCustomerlist').datagrid(
			'reload',{ 
			parameter : "{'start_time':'"+$('#searchstart_time').datebox('getValue')
					+ "','end_time':'"+$('#searchend_time').datebox('getValue')
					+"','meId':'"+row.id
					+"'}"
			});
}

/**
 * 动态验证用户名是否重复
 */
$(function() {
	/**
	 * 自定义名字验证规则
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		single : {
			validator : function(value, param) {
				return param[0] == 0 || value == oldName;
			},
			message : '名字已存在'
		}
	});

	// 输入内容改变时验证名字是否存在
	$('#realName').change(
			function() {
				// 获取已有验证规则
				var rule = eval("({" + $('#realName').attr('data-options')
						+ "})").validType;

				// 动态请求
				$.post(getCurProjPath()+'/manager/erp/me/findEmployeeList.do', {
					parameter : "{'realName':'" + $('#realName').val() + "'}"
				}, function(data, status) {
					var result = data.substring(9, 10);
					// console.log(result);

					// 动态绑定验证规则
					$('#realName').validatebox({
						required : true,
						validType : [ rule, 'single[' + result + ']' ]
					});
				});
			});
});

/**
 * 动态验证用户名是否重复
 */
/*$(function() {
	*//**
	 * 自定义名字验证规则
	 *//*
	$.extend($.fn.validatebox.defaults.rules, {
		single : {
			validator : function(value, param) {
				return param[0] == 0 || value == oldName2;
			},
			message : '名字已存在'
		}
	});

	// 输入内容改变时验证名字是否存在
	$('#loginName').change(
			function() {
				// 获取已有验证规则
				var rule = eval("({" + $('#loginName').attr('data-options')
						+ "})").validType;

				// 动态请求
				$.post(getCurProjPath()+'/manager/erp/me/findLoginNameList.do', {
					parameter : "{'loginName':'" + $('#loginName').val() + "'}"
				}, function(data, status) {
					var result = data.substring(9, 10);
					// console.log(result);

					// 动态绑定验证规则
					$('#loginName').validatebox({
						required : true,
						validType : [ rule, 'single[' + result + ']' ]
					});
				});
			});
});*/

function buttons2() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'meStatistics'
	}, function(data) {
		$.each(eval('(' + data + ')'), function(index, obj) {
			var opts = $('#list').datagrid('getColumnFields', false);
			for ( var i = 0; i < opts.length; i++) {
				if (opts[i] == obj) {
					$('#list').datagrid("showColumn", opts[i]);
				}
			}
		});
	});
}
function buttons() {
	$.post(getCurProjPath()+'/manager/getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'meStatistics'
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

/**
 * 触发“初始化密码”按钮的点击事件后，所执行的方法
 */
function initMemberPassword(id) {
	$.messager.confirm('', '您确定要初始化密码吗?', function(r) { // 确认对话框,判断是否继续进行操作
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/me/initMemberPassword.do', { // 通过post方法发送请求，id为参数
				id : id
			}, function(result) { // 异步请求完成后的回调函数
				if (result) {
					message_op(true, 'list'); // 调用Commons_message.js中的方法
				} else {
					message_op(false, 'list'); // 调用Commons_message.js中的方法
				}
			}, 'json');
		}
	});
}

/**
 * 触发“初始化密码”按钮的点击事件后，所执行的方法
 */
function initMemberScore(id) {
	$.messager.confirm('', '您确定要初始化积分吗?', function(r) { // 确认对话框,判断是否继续进行操作
		if (r) {
			$.post(getCurProjPath()+'/manager/erp/me/initMemberScore.do', { // 通过post方法发送请求，id为参数
				id : id
			}, function(result) { // 异步请求完成后的回调函数
				if (result) {
					message_op(true, 'list'); // 调用Commons_message.js中的方法
				} else {
					message_op(false, 'list'); // 调用Commons_message.js中的方法
				}
			}, 'json');
		}
	});
}

/**
 * 触发“启用/冻结”按钮的点击事件后，所执行的方法
 */
function setAvailability() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的人员
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) {
		$.messager.confirm('', '您确定要启用/冻结该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post(getCurProjPath()+'/manager/erp/me/setMemberAvailability.do', { // 通过post方法发送请求，id为参数
					ids : ids
				}, function(result) { // 异步请求完成后的回调函数
					if (result) {
						$.messager.alert('操作提示', '操作成功!', 'info'); // 调用Commons_message.js中的方法
						$("#list").datagrid("reload");
					} else {
						$.messager.alert('操作提示', '操作失败，不能启用/冻结！', 'info');
						$("#list").datagrid("reload");
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('操作提示', '请先选择会员，再启用/冻结！', 'info');
	}
}

function details() {
	var row = $('#list').datagrid('getSelected');
	if (row) {
		detailMember(row);
	} else {
		$.messager.alert('操作提示', '请先选择一个会员！', 'info');
	}
}
function detailMember(row) {
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/me/goToModifyMember.do',
		data : 'id=' + row.id,
		dataType : "json",
		success : function(data) {
			$("#memberDetail").val(data.nickName);
			$("#memberDetail2").val(data.realName);
			if (data.sex == 1) {
				$("#memberDetail3").val('男');
			} else if (data.sex == 0) {
				$("#memberDetail3").val('女');
			}

			var birthdayDate = new Date(data.birthday);
			var birthdayDateString = birthdayDate.format("yyyy-MM-dd");
			$("#memberDetail4").val(birthdayDateString);
			$("#memberDetail5").val(data.cellphone);
			$("#memberDetail6").val(data.email);
			if (data.identity == 0) {
				$("#memberDetail7").val('其他');
			} else if (data.identity == 1) {
				$("#memberDetail7").val('学生');
			} else if (data.identity == 2) {
				$("#memberDetail7").val('在职');
			} else if (data.identity == 3) {
				$("#memberDetail7").val('自由职业');
			}
			if (data.maritalState == 0) {
				$("#memberDetail8").val('保密');
			} else if (data.maritalState == 1) {
				$("#memberDetail8").val('单身');
			} else if (data.maritalState == 2) {
				$("#memberDetail8").val('恋爱');
			} else if (data.maritalState == 3) {
				$("#memberDetail8").val('已婚');
			}

			var res = "";
			if (data.interest != null) {
				var shuzu = data.interest.split("-");
				for ( var i = 0; i < shuzu.length; i++) {
					var j = shuzu[i];
					if (j == 0) {
						res += "其他";
					} else if (j == 1) {
						res += "美食  ";
					} else if (j == 2) {
						res += "娱乐  ";
					} else if (j == 3) {
						res += "购物  ";
					} else if (j == 4) {
						res += "旅游  ";
					} else if (j == 5) {
						res += "酒店  ";
					} else if (j == 6) {
						res += "交通  ";
					} else {
						res += "教育  ";
					}
				}
			}
			$("#memberDetail9").val(res);

			if (data.activity == 0) {
				$("#memberDetail10").val('未激活');
			} else if (data.activity == 1) {
				$("#memberDetail10").val('已激活');
			}

			if (data.availability == 0) {
				$("#memberDetail11").val('冻结');
			} else if (data.availability == 1) {
				$("#memberDetail11").val('启用');
			}
			$("#memberDetail12").val(data.score);

			$("#memberDetail14").val(data.headShowPath);

			if (data.emailBinding == 0) {
				$("#memberDetail15").val("未绑定");
			} else {
				$("#memberDetail15").val("已绑定");
			}
			if (data.cellphoneBinding == 0) {
				$("#memberDetail16").val("未绑定");
			} else {
				$("#memberDetail16").val("已绑定");
			}
			if (data.registerMethod == 0) {
				$("#memberDetail17").val("PC");
			} else {
				$("#memberDetail17").val("手机");
			}
			if (data.validationMethod == 0) {
				$("#memberDetail18").val("邮箱");
			} else {
				$("#memberDetail18").val("手机");
			}

		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dlg2').dialog('open').dialog('setTitle', '会员详情');
}
