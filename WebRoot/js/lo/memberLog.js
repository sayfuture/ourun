var url;// 表单提交请求的地址

/*
 * DataGrid 初始化
 */
$(function() {
	$('#list').datagrid({
		url : 'findMemberLog.do',
		singleSelect : false,
		pagination : true,
		pageSize:10,
		pageList : [ 10, 20, 50 ],
		queryParams : {},
		columns : [ [ {
			field : 'id',
			title : '会员编号',
			width : 50,
			checkbox : true
		}, {
			field : 'state',
			title : '用户名',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				if (row.meMember.nickName != null) {
					return row.meMember.nickName;
				} else
					return "";
			}
		}, {
			field : 'flag',
			title : '操作结果',
			width : 60,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == 1) {
					return "成功";
				} else
					return "失败";
			}
		}, {
			field : 'time2',
			title : '操作时间',
			align : 'center',
			width : 130
		},{
			field:'remoteIp',
			title:'会员IP',
			align:'center',
			width:110
			},
		{
			field : 'ip',
			title : '服务器Ip',
			align : 'center',
			width : 100
		}, 
		{
			field : 'logModule',
			title : '操作模块',
			align : 'center',
			width : 120
		}, {
			field : 'logFunctions',
			title : '会员行为',
			align : 'center',
			width : 240
		} ] ]
	});
});

function search() {
	var startDate = $('#startDate').datebox('getValue');
	startDate = startDate == false ? "" : startDate;
	var endDate = $('#endDate').datebox('getValue');
	endDate = endDate == false ? "" : endDate;
	var remoteIp = $('#remoteIp').val();
	remoteIp = remoteIp == false ? "" : remoteIp;
	var name = $('#name').val();
	name = name == false ? "" : name;
	var action = $('#action').val();
	action = action == false ? "" : action;
	$('#list').datagrid(
			'reload',
			{
				parameter : "{'startDate':'" + startDate + "','endDate':'"
						+ endDate + "','remoteIp':'" + remoteIp + "','name':'" + name
						+ "','action':'" + action + "'}"
			});
}
function showMe() {
	var row = $('#list').datagrid('getSelected');
	if (row) {
		detailMe(row);
	} else {
		$.messager.alert('操作提示', '请选择一个会员', 'info');
	}
}

function detailMe(row) {
	$('#realName').val("");
	$('#loginName').val("");
	$('#sexInfo').val("");
	$('#cellphoneInfo').val("");
	$('#birthdayInfo').val("");
	$('#identityInfo').val("");
	$('#emailInfo').val("");
	$('#interestInfo').val("");
	$('#maritalStateInfo').val("");
	$('#activityInfo').val("");
	$('#availabilityInfo').val("");
	$('#scoreInfo').val("");
	$('#levelInfo').val("");
	$('#registerInfo').val("");
	$('#emailBindingInfo').val("");
	$('#cellphoneBindingInfo').val("");
	$('#headShowPathInfo').val("");
	$.ajax({
		url : getCurProjPath() + "/manager/erp/me/findMemberByRealName.do",
		method : 'post',
		data : {'realName' : row.meMember.realName,'nickName': row.meMember.nickName},
		dataType : "json",
		success : function(result) {
			if(result.realName!=null){
			$('#realName').val(result.realName);} else $('#realName').val("");
			if(result.nickName!=null){
			$('#loginName').val(result.nickName);} else $('#loginName').val("");
			if (result.sex == 1) {
				$('#sexInfo').val("男");
			} else {
				$('#sexInfo').val("女");
			}
			if(result.cellphone!=null){
			$('#cellphoneInfo').val(result.cellphone);} else $('#cellphoneInfo').val("");
			if (result.identity == 0) {
				$("#identityInfo").val('其他');
			} else if (result.identity == 1) {
				$("#identityInfo").val('学生');
			} else if (result.identity == 2) {
				$("#identityInfo").val('在职');
			} else  {
				$("#identityInfo").val('自由职业');
			}
			if(result.email!=null){
			$('#emailInfo').val(result.email);}else $('#emailInfo').val("");

			if (result.maritalState == 0) {
				$("#maritalStateInfo").val('保密');
			} else if (result.maritalState == 1) {
				$("#maritalStateInfo").val('单身');
			} else if (result.maritalState == 2) {
				$("#maritalStateInfo").val('恋爱');
			} else  {
				$("#maritalStateInfo").val('已婚');
			}

			if (result.activity == 0) {
				$("#activityInfo").val('未激活');
			} else if (result.activity == 1) {
				$("#activityInfo").val('已激活');
			}

			if (result.availability == 0) {
				$("#availabilityInfo").val('冻结');
			} else {
				$("#availabilityInfo").val('启用');
			}

			if (result.registerMethod == 0) {
				$("#registerInfo").val('PC');
			} else  {
				$("#registerInfo").val('手机');
			}
			if(result.score!=null){
			$("#scoreInfo").val(result.score);}else $('#scoreInfo').val("");
			if(result.headShowPath!=null){
			$("#headShowPathInfo").val(result.headShowPath);}else $('#headShowPathInfo').val("");

			if (result.emailBinding == 0) {
				$("#emailBindingInfo").val('未绑定');
			} else {
				$("#emailBindingInfo").val('已绑定');
			}
			if (result.cellphoneBinding == 0) {
				$("#cellphoneBindingInfo").val("未绑定");
			} else {
				$("#cellphoneBindingInfo").val("已绑定");
			}
			if (result.registerMethod == 0) {
				$("#validationInfo").val("PC");
			} else {
				$("#validationInfo").val("手机");
			}

			if (result.validationMethod == 0) {
				$("#validationInfo").val("邮箱");
			} else {
				$("#validationInfo").val("手机");
			}
			if(result.level!=null){
			$("#levelInfo").val(result.level.levelName);
			}else{
				$("#levelInfo").val("");
				}
			
			var res = "";
			if(result.interest!=null){
			  var shuzu = result.interest.split("-");
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
			$("#interestInfo").val(res);
			
			var birthdayDate = new Date(result.birthday);
			var birthdayDateString = birthdayDate.format("yyyy-MM-dd");
			$("#birthdayInfo").val(birthdayDateString);

		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	$('#dial').dialog('open').dialog('setTitle', '商户详情');
}

function getCurProjPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
}
