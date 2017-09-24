var url;
var parameter = "";
var employId;

/**
 * 触发“添加人员”按钮的点击事件后，所执行的方法
 */
function newObj() {
	$('#dlg').dialog('open').dialog('setTitle', '添加人员');
	$('#fm').form('clear');
	initRole();
	$("#loginName").validatebox({
		required : true
	});
	$("#loginName").attr("disabled", false);
	$("#sex").val(1);
	$('#preview1').attr("src","");
	$('#preview1').attr("width",0);
	$('#preview1').attr("height",0);
	url = 'addAuEmployee.do';
	document.documentElement.style.overflow = "hidden";
}

/**
 * 修改密码
 */
function updatePassword() {
	var row = $('#list').datagrid('getSelected');
	if (row) {
		$('#dlg3').dialog('open').dialog('setTitle', '修改密码');
		$('#dlg3').form('clear');
		$("#pas").val(row.id);
		$("#rName").html(row.realName);
		$("#lName").html(row.loginName);
		url = 'updatePassword.do';
	} else {
		$.messager.alert('操作提示', '请先选择人员，再修改！', 'info');
	}
}

function updatePassword2() {
	$('#uppassword').form('submit', { // 提交表单
		url : url, // 设置表单请求的地址
		onSubmit : function() { // 在表单提交前要执行的方法，返回false，则放弃请求
			if ($("#password2").val() != $("#password").val()) {
				$.messager.alert("错误", "确认密码输入错误！", "error");
				return false;
			} else {
				return $(this).form('validate');
			}
		},
		success : function(result) { // 请求成功后执行的方法,result为请求返回的布尔值
			$('#dlg3').dialog('close'); // close the dialog //关闭对话框
			message_op(true, 'list'); // 调用Commons_message.js中的方法
		},
		error : function() { // 请求发生错误时，执行的方法
			message_op(false, null);
		}

	});
}

/**
 * 触发“修改人员”按钮的点击事件后，所执行的方法
 */
function editObj() {
	var row = $('#list').datagrid('getSelected');
	if (row) {
		$.ajax({
			method : 'get',
			url : 'getEmployee.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				$("#id").val(row.id);
				$("#loginName").val(row.loginName);
				$("#loginName").validatebox({
					required : true
				});
				$("#loginName").attr("disabled", true);
				if(null != data.shop_file_name1 && data.shop_file_name1 !=""){
					$('#preview1').attr("src",getProsceniumProjPath()+"/upload/sa/"+data.shop_file_name1);
					$('#preview1').attr("width",300);
					$('#preview1').attr("height",120);
				}else{
					$('#preview1').attr("src","");
					$('#preview1').attr("width",0);
					$('#preview1').attr("height",0);
				}
				$("#appid").val(data.appid);
				$("#appsecret").val(data.appsecret);
				$("#wxname").val(data.wxname);
				$("#address").val(data.address);
//				$("#shop_file_name").val(data.shop_file_name);
				$("#realName").val(row.realName);
				$('#age').numberbox('setValue', row.age);
				$('#sex').combobox('setValue', row.sex);
				$("#email").val(row.email);
				$("#tel").val(row.tel);
				$("#tel2").val(row.tel2);
				$("#password").val(row.password);
				data.positionList != null ? initRole(data.positionList)
						: initRole();
				if (data.auDept != null) {
					$("#deptId").val(data.auDept.id);
					$("#seldeptId").val(data.auDept.name);
				}
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dlg').dialog('open').dialog('setTitle', '修改人员');
		url = 'updateEmployee.do';
		document.documentElement.style.overflow = "hidden";
	} else {
		$.messager.alert('操作提示', '请先选择人员，再修改！', 'info');
	}

}
/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveObj() {
	$.post('findLoginNameList.do', {
		parameter : "{'loginName':'" + $('#loginName').val() + "'}"
	}, function(data, status) {
		var obj = eval('(' + data + ')');
		$('#loginName').validatebox({
			required : true,
			validType : [ loginName, 'single[' + obj.total + ']' ]
		});
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				$("#list").datagrid("reload");
				return $(this).form('validate');
			},
			success : function(result) {
				if (result == 'false') {
					$('#dlg').dialog('close');
					message_op(false, null);

				} else {
					$('#dlg').dialog('close');
					message_op(true, 'list');
				}

			},
			error : function() {
				message_op(false, null);
			}

		});
	});

}
/**
 * 触发“删除人员”按钮的点击事件后，所执行的方法
 */
function destroyObj() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的人员
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deleteAuEmployeeBinch.do', { // 通过post方法发送请求，id为参数
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
		$.messager.alert('操作提示', '请先选择人员，再删除！', 'info');
	}
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
				return param[0] == 0;
			},
			message : '名字已存在'
		}
	});

	// 输入内容改变时验证名字是否存在
	$('#loginName')
			.change(
					function() {
						// 获取已有验证规则
						var loginName = eval("({"
								+ $('#loginName').attr('data-options') + "})").validType;

						// 动态请求
						$.post('findLoginNameList.do', {
							parameter : "{'loginName':'"
									+ $('#loginName').val() + "'}"
						}, function(data, status) {
							var obj = eval('(' + data + ')');
							// console.log(result);
							// 动态绑定验证规则
							$('#loginName').validatebox(
									{
										required : true,
										validType : [ loginName,
												'single[' + obj.total + ']' ]
									});
						});
					});
});

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */

function search() {
	$('#list').datagrid(
			'reload',
			{
				parameter : "{'realName':'" + $('#name').val()
						+ "','companyId':'" + $("#auName").combobox("getValue")
						+ "'}"
			});
}

function search2() {
	parameter = "'name':'" + $("#name2").val() + "','type':'1'"; // 定义dategrid请求参数
	$('#list2').datagrid('reload', { // 重载行，等同于'load'方法。即重新请求url加载数据
		parameter : "{'name':'" + $('#name2').val() + "','type':'1'}" // 参数
	});
}

/**
 * 显示添加表单时，初始化角色下拉框方法
 */
function initRole(rows) {
	$('#roleId').combobox({
		url : 'getAuPositionList.do',
		valueField : 'id',
		textField : 'name',
		editable : false,
		multiple : "multiple",
		onLoadSuccess : function() {
			for ( var i = 0; i < rows.length; i++) {
				$('#roleId').combobox('select', rows[i].id);
			}
		}
	});
}

/**
 * 显示添加表单时，初始化部门下拉框方法
 */

/**
 * DataGrid 初始化
 */
$(function() {
	$('#toolbar a').hide();
	$('#list')
			.datagrid(
					{
						url : 'findEmployeeList.do',
						singleSelect : true,
						pagination : true,
						pageSize : 10,
						pageList : [ 10, 20, 50 ],
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
									field : 'index',
									title : '序号',
									width : 38,
									align : 'center',
									formatter : function(val, row, index) {
										var options = $("#list").datagrid(
												'getPager').data("pagination").options;
										var currentPage = options.pageNumber;
										var pageSize = options.pageSize;
										return (pageSize * (currentPage - 1))
												+ (index + 1);
									}
								},
								{
									field : 'optAu',
									title : '功能设置',
									width : 90,
									formatter : function(value, row, index) {
										var str = "<a style='cursor:pointer;display:inline-block;margin:-19px 0 1px 20px;' title='权限设置' class='easyui-linkbutton btn' onclick='setAuthority(\""
												+ row.id
												+ "\");'><img alt='img' src='../../../js/jquery-easyui-1.3.5/themes/icons/product.png' style='margin:6px 10px 3px 0;width: 15px; height: 15px;' /></a>";
										str += "<a style='cursor:pointer;' class='easyui-linkbutton btn' title='绑定所属分公司数据' onclick='checkCompany(\""
												+ row.id
												+ "\");'><img alt='img' src='../../../js/jquery-easyui-1.3.5/themes/icons/band.png' style='margin:6px 2px 3px 0;width: 15px; height: 15px;' /></a>";
										return str;
									}
								},
								{
									field : 'optPw',
									title : '初始化密码',
									width : 90,
									hidden : true,
									formatter : function(value, row, index) {
										return "<img alt='img' src='../../../js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:6px 2px 3px 0;width: 15px; height: 15px;' /><span style='cursor:pointer;display:block;margin:-19px 0 1px 20px;' onclick='initPassword(\""
												+ row.id + "\");'>初始化密码</span>";
									}
								},
								{
									field : 'loginName',
									title : '登录名',
									width : 100
								},
								{
									field : 'realName',
									title : '真实姓名',
									width : 100
								},
								{
									field : 'sex',
									title : '性别',
									width : 40,
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
									field : 'company',
									title : '所属机构',
									width : 120,
									formatter : function(value, row, index) {
										return value.name + '';
									}
								},
								{
									field : 'auDept',
									title : '所属部门',
									width : 120,
									formatter : function(value, row, index) {
										return value.name + '';
									}
								},
								// {field:'auEmployeeDeptList1',title:'绑定部门',width:120,
								// formatter:function(value,row,index){
								// var str='';
								// if(value!=null){
								// if(value.length>0){
								// for(var i=0;i<value.length;i++)
								// {
								// if(value.length-1==i){
								// str+= value[i].auDept.name;
								// }else
								// str+= value[i].auDept.name+",";
								// }
								// }
								// }
								// return str;
								// }},
								{
									field : 'auEmployeeDeptList2',
									title : '绑定机构',
									width : 120,
									formatter : function(value, row, index) {
										var arr = row.auEmployeeDeptList1;
										var str = '';
										if (arr.length > 0) {
											for ( var i = 0; i < arr.length; i++) {
												if (arr.length - 1 == i) {
													str += arr[i].superAuDept.name;
												} else
													str += arr[i].superAuDept.name
															+ ",";
											}
										}
										return str;
									}
								},
								{
									field : 'age',
									title : '年龄',
									width : 50
								},
								{
									field : 'tel',
									title : '手机号',
									width : 100
								},
								{
									field : 'email',
									title : '邮箱',
									width : 100
								},
								{
									field : 'positionList',
									title : '岗位设定',
									width : 420,
									formatter : function(value) {
										var temp = "";
										for ( var i = 0; i < value.length; i++) {
											temp = temp + value[i].name + "、";
										}
										temp = temp.substring(0,
												temp.length - 1);
										return temp;
									}
								}

						] ],
						onBeforeLoad : function() {
							buttons2();
						},
						onLoadSuccess : function() {
							initAuSelect($("#auName").combobox("getValue"));
							buttons();
						},
						onDblClickRow : function(index, row) {
							dBObj(row.id);

						}
					});
});

function buttons2() {
	$.post('../../getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'auEmployee'
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
	$.post('../../getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'auEmployee'
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
 * 显示添加表单时，初始化角色下拉框方法
 */
function initAuSelect(select) {
	$('#auName').combobox({
		url : getCurProjPath() + '/manager/erp/au/getAuDeptList1.do?parentId=',
		valueField : 'id',
		textField : 'name',
		editable : false,
		onLoadSuccess : function() {
			var data = $('#auName').combobox('getData');
			select += '';
			if (select == 'undefined' || select.length < 1) {
				// console.log(data);
			}
			if (data.length > 0) {
				$('#auName').combobox('select', select);
			}
		}
	});
}
function initPosSelect(select) {
	$('#posName').combobox({
		url : 'getAuPositionListByEmp.do',
		valueField : 'id',
		textField : 'name',
		editable : false,
		onLoadSuccess : function() {
			var data = $('#posName').combobox('getData');
			select += '';
			if (select == 'undefined' || select.length < 1) {
				// console.log(data);
			}
			if (data.length > 0) {
				$('#posName').combobox('select', select);
			}
		}
	});
}

function saveUser2() {
	var rows = $("#list2").datagrid("getRows");
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	$('#fm2').form('submit', { // 提交表单
		url : "setEmployeeAuthority.do?ids=" + ids,// 设置表单请求的地址
		onSubmit : function() { // 在表单提交前要执行的方法，返回false，则放弃请求
			return $(this).form('validate'); // 进行easyUI表单验证
		},
		success : function(result) { // 请求成功后执行的方法,result为请求返回的布尔值
			if (result == 'true') {
				$('#dlg2').dialog('close'); // close the dialog //关闭对话框
				message_op(true, 'list'); // 调用Commons_message.js中的方法
			} else {
				message_op(false, null);
			}
		},
		error : function() { // 请求发生错误时，执行的方法
			message_op(false, null);
		}

	});
}

/**
 * 触发“初始化密码”按钮的点击事件后，所执行的方法
 */
function initPassword(id) {
	$.messager.confirm('', '您确定要初始化密码吗?', function(r) { // 确认对话框,判断是否继续进行操作
		if (r) {
			$.post('initPassword.do', { // 通过post方法发送请求，id为参数
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
 * 功能设置
 */
function setAuthority(id) {
	document.documentElement.style.overflow = "hidden";
	$("#emId").val(id);
	$('#dlg2').dialog('open').dialog('setTitle', '功能设置'); // 显示对话框，并设置标题

	$('#list2').datagrid(
					{
						url : 'findList.do',
						singleSelect : false,
						pagination : true,
						pageSize : 10,
						pageList : [ 10, 20, 50 ],
						selectOnCheck : false,
						checkOnSelect : false,
						fitColumns : true,
						queryParams : {
							parameter : "{}"
						},
						columns : [ [ {
							field : 'id',
							title : 'id',
							width : 100,
							checkbox : true
						}, {
							field : 'name',
							title : '功能名称',
							width : 150
						}, {
							field : 'type',
							title : '功能类型',
							width : 400,
							formatter : function(value, row, index) {
								return getCheckBoxStringByAu(row.id);
							}
						} ] ],
						onLoadSuccess : function(data, index) {
							$.each(data.rows,function(index, item) {
										$.post(
												getCurProjPath()+ "/manager/erp/au/getButtonByEmployeeAndAuthority.do",
												{
													// 'employeeId':$('#loginId').val(),
													'employeeId' : id,
													'authorityId' : item.id
													},
													function(data) {
																$.each(eval('('+ data+ ')'),
																	function(key,value) {
																			if (item.id == value.auAuthority.id) {
																				$('#list2').datagrid('checkRow',index); // 选中功能
																							$("input[name='"+ item.id+ "']").each(function() {
																									if (value.auButton != undefined&& $(this).attr('id') == value.auButton.funName) {
																													$(this).attr("checked",true);
																												}
																											});
																						}
																					});
																});
											});

						}
					});
}

function getCheckBoxStringByAu(id) {
	var checkBoxStr = "";
	$.ajax({
		method : 'post',
		url : getCurProjPath() + '/manager/erp/au/getAubuttonByAuthority.do',
		dataType : "json",
		async : false,
		data : {
			'id' : id
		},
		success : function(data) {
			$.each(data, function(key, value) {
				checkBoxStr = checkBoxStr + "<input type='checkbox' id='"
						+ value.funName + "' name='" + id + "' value='"
						+ value.id + "' />" + value.btnName + "&nbsp;&nbsp;";
			});
		},
		error : function(msg) {
			alert('msg----' + msg);
		}
	});
	return checkBoxStr;
}

// 获取项目的URL
function getCurProjPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
}

function checkDept(id) {
	var temp;
	if (id == null) {
		temp = true;
		$("#queding").hide();
		$('#listDept').treegrid({
			singleSelect : true,

		});
	} else {
		temp = false;
		$("#queding").show();
		$('#listDept').treegrid({
			singleSelect : false,
		});
	}
	employId = id;
	$('#dlg_dept').dialog('open').dialog('setTitle', '选择部门');
	$('#listDept').treegrid({
		url : getCurProjPath() + '/manager/erp/au/getAuDeptList.do',
		queryParams : {
			id : null
		},
		treeField : 'name',
		nowrap : false,
		rownumbers : true,
		animate : true,
		idField : 'id',
		collapsible : true,
		columns : [ [ {
			title : '部门名称',
			field : 'name',
			width : 300
		} ] ],
		onDblClickRow : function(row) {

			if (temp) {
				$("#deptId").val(row.id);
				$("#seldeptId").val(row.name);
				$('#dlg_dept').dialog('close');
			}
		},
		onBeforeExpand : function(data) {
			if (temp)
				return true;
			else
				return false;
		}
	});

}

function saveDept() {
	var arr = $('#listCompany').datagrid('getSelections');

	var ids = "";
	var deptName = "";
	if (arr.length == 0) {
		$('#dlg_company').dialog('close');
		return;
	}
	if (arr.length > 0) {
		for ( var i = 0; i < arr.length; i++) {
			if (i + 1 < arr.length) {
				ids = ids + arr[i].id + ",";
				deptName = deptName + arr[i].name + ",";
			} else {
				ids = ids + arr[i].id;
				deptName = deptName + arr[i].name;
			}
		}
	}
	$("#deptIds").val(ids);
	$("#employeeId").val(employId);

	url = getCurProjPath() + '/manager/erp/au/savebindCompany.do';
	$('#choiceDept').form('submit', {
		url : url,
		onSubmit : function() {
			$("#list").datagrid("reload");
			return $(this).form('validate');
		},
		success : function(result) {
			if (result == 'false') {
				
				$('#dlg_company').dialog('close');
				message_op(false, null);
			} else {
				$('#dlg_company').dialog('close');
				message_op(true, 'list');
			}
		},
		error : function() {
			message_op(false, null);
		}
	});
}

function checkCompany(id) {

	$('#dlg_company').dialog('open').dialog('setTitle', '绑定分公司');

	employId = id;
	$('#listCompany')
			.datagrid(
					{
						url : getCurProjPath()
								+ '/manager/erp/au/findAuDeptList.do',
						singleSelect : false,
						pagination : true,
						pageSize : 10,
						pageList : [ 10, 20, 50 ],
						queryParams : {
							parameter : "{ level : '0'}"
						},
						columns : [ [
								{
									field : 'id',
									title : 'id',
									width : 100,
									checkbox : true
								},
								{
									field : 'index',
									title : '序号',
									width : 38,
									align : 'center',
									formatter : function(val, row, index) {
										var options = $("#listCompany")
												.datagrid('getPager').data(
														"pagination").options;
										var currentPage = options.pageNumber;
										var pageSize = options.pageSize;
										return (pageSize * (currentPage - 1))
												+ (index + 1);
									}
								},
								{
									field : 'name',
									title : '公司名称',
									width : 120,

								},
								{
									field : 'cityId',
									title : '城市',
									width : 120,
									formatter : function(value, row, index) {
										
										if (null != value && value != "") {
											var city = getCity(value);
											
											return null == city || undefined == city ? "未添加城市": null == city.regionName || city.regionName == "" ? "未添加": city.regionName;
										} else {
											return "未添加";
										}
									},

								},
								{
									field : 'createUser',
									title : '创建人',
									width : 120,
									formatter : function(value, row, index) {
										if (null != value && value != "") {
											var user = getUser(value);
											return null == user
													|| undefined == user ? "未添加姓名"
													: null == user.realName
															|| user.realName == "" ? "未添加"
															: user.realName;
										} else {
											return "未添加";
										}
									},

								}

						] ],

					});

}

function getCity(id) {

	var city;
	$.ajax({
		method : 'get',
		//url : getCurProjPath() + '/manager/erp/au/getEmployee.do',
		url : getCurProjPath() + '/manager/erp/ar/getArRegion.do',
		
		data : 'id=' + id,
		dataType : "json",
		async : false,
		success : function(data) {
			
			city = data;
		},
		error : function(msg) {
			return '';
		}
	});
	return city;
}

function dBObj(id) {
	
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/au/getEmployee.do',
		data : 'id=' + id,
		dataType : "json",
		success : function(data) {
			var msg='   ';
		
			for(var i=0;i<data.positionList.length;i++){
				
				msg+=data.positionList[i].name+',';
				
			}
			
			$("#workMsg").text(msg);
			$('#dBWork').dialog('open').dialog('setTitle', '岗位设定');
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
}
//选择图片，并显示到指定区域
function setImagePreview(goal) {
	
	var $goal = $(goal);
    var docObj=document.getElementById($goal.attr('id'));
    var imgObjPreview=document.getElementById($goal.attr('idm'));
    if(docObj.files &&    docObj.files[0]){
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '300px';
        imgObjPreview.style.height = '120px';                    
        //imgObjPreview.src = docObj.files[0].getAsDataURL();
		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    }else{
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("localImag");
        //必须设置初始大小
        localImagId.style.width = "300px";
        localImagId.style.height = "120px";
        //图片异常的捕捉，防止用户修改后缀来伪造图片
   try{
        localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
    }catch(e){
        alert("您上传的图片格式不正确，请重新选择!");
        return false;
    }
    	imgObjPreview.style.display = 'none';
   		document.selection.empty();
    }
    return true;
}