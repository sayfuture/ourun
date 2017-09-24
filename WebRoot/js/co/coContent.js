var url;// 表单提交请求的地址
var parameter = "";
var oldName = "";// 修改时验证是否修改了名字
var different;
/**
 * 触发“添加栏目”按钮的点击事件后，所执行的方法
 */
function newObj1() {
	$('#dlg').dialog('open').dialog('setTitle', '添加栏目'); // 显示对话框，并设置标题
	$('#fm').form('clear'); // 清空表单
//	getcityHaveNation("cityId");
	$('#preview1').attr("src","");
	
	$('#preview1').attr("width",0);
	$('#preview1').attr("height",0);
	CKEDITOR.instances.body.setData("");
	url = 'addCoContent.do'; // 重置url
	different="add";
}
/**
 * 触发“修改栏目”按钮的点击事件后，所执行的方法
 */
function editObj1() {
	var row = $('#list').datagrid('getSelected'); // 获取选择的行的栏目
	if (row) {
		// 异步请求，通过id获得用户的栏目，并把栏目导入表单中
		$.ajax({
			method : 'get',
			url : 'getCoContent.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				$("#id").val(row.id);
				$("#uname").val(row.title);
				$("#introduce").val(row.introduce);
				CKEDITOR.instances.body.setData(row.body);
				if(null != row.fileUrl && row.fileUrl !=""){
					$('#preview1').attr("src",getProsceniumProjPath()+"/upload/co/"+row.fileUrl);
					$('#preview1').attr("width",300);
					$('#preview1').attr("height",120);
				}else{
					$('#preview1').attr("src","");
					$('#preview1').attr("width",0);
					$('#preview1').attr("height",0);
				}
				$("#source").val(row.source);
//				if(data.cityId==null||data.cityId==""){
//					getcityHaveNation("cityId","");
//				}else{
//					getcityHaveNation("cityId",data.cityId);
//				}
				oldName = $("#uname").val();
				$('#coTypeName2').val(row.coType.name);
				$('#coTypeId').val(row.coType.id);
				$('#keywords').val(row.keywords);
				 $("input[name='weight']").each(function () {
				        if ($(this).val() == row.weight) {
				        	this.checked=true;
				        }else{
				        	this.checked=false;
				        }
				     });
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dlg').dialog('open').dialog('setTitle', '修改栏目'); // 显示对话框，并设置标题
		url = 'updateCoContent.do'; // 重置url
		different="update";
	} else {
		$.messager.alert('操作提示', '请先选择栏目，再修改！', 'info');
	}
}

/**
 * 触发“查看栏目”按钮的点击事件后，所执行的方法
 */
function showUser() {
	var row = $('#list').datagrid('getSelected'); // 获取选择的行的栏目
	if (row) {
		$('#showName').html(row.title);
		$('#showIntroduce').html(row.introduce);
		$('#showSource').html(row.source);
		$('#showClick').html(row.click);
		$('#showType').html(null == row.coType ? "未添加" : row.coType.name);
		$('#showTime').html(row.time2);
		CKEDITOR.instances.show.setData(row.body);
	} else {
		$.messager.alert('操作提示', '请先选择栏目，再操作！', 'info');
	}
}

/**
 * 触发“保存”按钮的点击事件后，所执行的方法
 */
function saveObj() {
	if(different=="add"){
		if($("#strFileName").val()==""){
			$.messager.alert('操作提示', '添加详情，请上传封面图片！', 'info');
			return;
		}
	}
	jqueryMaskStart();
		$('#fm').form('submit', { // 提交表单
			url : url, // 设置表单请求的地址
			onSubmit : function() { // 在表单提交前要执行的方法，返回false，则放弃请求
				return $(this).form('validate'); // 进行easyUI表单验证
			},
			success : function(result) { // 请求成功后执行的方法,result为请求返回的布尔值
				jqueryMaskEnd();
				$('#dlg').dialog('close'); // close the dialog //关闭对话框
				message_op(true, 'list'); // 调用Commons_message.js中的方法
			},
			error : function() { // 请求发生错误时，执行的方法
				jqueryMaskEnd();
				message_op(false, null);
			}
		});
}
/**
 * 触发“删除栏目”按钮的点击事件后，所执行的方法
 */
function destroyObj1() {
	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections'); // 获取选择的行的栏目
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}

	if (row) { // 判断是否存在被选择的行
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) { // 确认对话框,判断是否继续进行操作
			if (r) {
				$.post('deleteCoContents.do', { // 通过post方法发送请求，id为参数
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
		$.messager.alert('操作提示', '请先选择栏目，再删除！', 'info');
	}
}

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */
function search() {
	$('#list').datagrid(
			'reload',
			{ // 重载行，等同于'load'方法。即重新请求url加载数据
				parameter : "{'title':'" + $('#name').val() + "','time1':'"
						+ $('#time1').datebox('getValue') + "','time2':'"
						+ $('#time2').datebox('getValue') + "','coTypeId':'"
						+ $('#coTypeId2').val()
						+ "','type':'like'}" // 参数
			});
}

/*
 * DataGrid 初始化
 */
$(function() {
	$.ajaxSetup({
		async:false
	});
	$('#toolbar a').hide();
	$('#list').datagrid({
		url : 'findCoContent.do',
		singleSelect : false,
		pagination : true,
		pageSize:10,pageList:[10,20,50],
		queryParams : {
			parameter : "{}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		},
		{field:'index',title:'序号',width:38, align: 'center',formatter:function(val,row,index){
		     var options = $("#list").datagrid('getPager').data("pagination").options; 
		     var currentPage = options.pageNumber;
		     var pageSize = options.pageSize;
		     return (pageSize * (currentPage -1))+(index+1);
		    }
		},
		{
			field : 'title',
			title : '标题',
			width : 330
		}, {
			field : 'coType',
			title : '栏目类型',
			width : 100,
			formatter:function(value,row,index){
				return value.name;
			}
		},
//		{
//			field : 'weight',
//			title : '轮播图是否显示',
//			width : 100,
//			formatter:function(value){
//				if(value==1){
//					return "<font color='green'>显示</font>";
//				}else{
//					return "<font color='red'>不显示</font>";
//				}
//			}
//		}, 
		{
			field : 'click',
			title : '点击量',
			width : 50
		}, {
			field : 'time2',
			title : '上传时间',
			width : 150
		},{
			field : 'createUser',
			title : '创建人',
			width : 100,
			formatter : function(value, row, index) {
				if(null != value && value != ""){
					var user = getUser(value);
					return null == user || undefined == user?"未添加姓名":null == user.realName || user.realName == "" ? "未添加":user.realName;
				}else{
					return "未添加";
				}
			}
		}
		] ],
		onLoadSuccess : function() {
			buttons();
			
		}
	});
});
function preview(previewURL){
	//获取鑫家网域名
	var xinJiaUrl = getPathRemote();
	//预留城市代码，现固定
	var cityId = "35";
	var requestUrl = xinJiaUrl + "/city/" + cityId + previewURL;
	window.open (requestUrl,'newwindow');
}


//获取创建人
function getUser(id){
	var user;
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/au/getEmployee.do',
		data : 'id=' + id,
		dataType : "json",
		success : function(data) {
			user = data;
		}
	});
	return user;
}

function buttons() {
	$.post('../../getEmployeeButtons.do', { // 通过post方法发送请求，id为参数
		'functionType' : 'coContent'
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
document.onkeydown = function(event) {
	var target, code, tag;
	if (!event) {
		event = window.event; // 针对ie浏览器
		target = event.srcElement;
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "TEXTAREA") {
				return true;
			} else {
				return false;
			}
		}
	} else {
		target = event.target; // 针对遵循w3c标准的浏览器，如Firefox
		code = event.keyCode;
		if (code == 13) {
			tag = target.tagName;
			if (tag == "INPUT") {
				return false;
			} else {
				return true;
			}
		}
	}
};

/**
 * 显示添加表单时，初始化角色下拉框方法
 */
function initCoType(select) {
	$('#coTypeId').combobox({
		url : 'getCoTypeList.do',
		valueField : 'id',
		textField : 'name',
		editable : false,
		onLoadSuccess : function() {
			var data = $('#coTypeId').combobox('getData');
			select += '';
			if (select == 'undefined' || select.length < 1) {
				select = data[0].id;
			}
			if (data.length > 0) {
				$('#coTypeId').combobox('select', select);
			}
		}
	});
}

//获取项目的URL
function getCurProjPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
}

function check(){
	$('#dlg2').dialog('open').dialog('setTitle', '选择栏目类型'); 
	$('#list2').treegrid({
				url : getCurProjPath() + '/manager/erp/co/findCoType.do',
				queryParams:{id:null},
				treeField : 'name',
				nowrap : false,
				rownumbers : true,
				animate : true,
				singleSelect : true,
				idField : 'id',
				collapsible : true,
				columns : [ [
						{
							title : '栏目分类名称',
							field : 'name',
							width : 300
						} ] ],
				onBeforeLoad : function(row, param) {
					for ( var i in row) {
						if (i == "id") {
							$(this).treegrid('options').url = getCurProjPath()
									+ "/manager/erp/co/findCoType.do?pid="
									+ row[i];
						}
					}
					return true;
				}
			});
}
/**添加时的选择框**/
function checkCoType(){
	var node = $('#list2').treegrid('getSelected');
	if(node){
		$('#coTypeName2').val(node.name);
		$('#coTypeId').val(node.id);
		$('#dlg2').dialog('close');
	}else{
		$.messager.alert("系统提示","请选择栏目类型","info");
	}
}

function check3(){
	$('#dlg3').dialog('open').dialog('setTitle', '选择栏目类型'); 
	$('#list3').treegrid({
				url : getCurProjPath() + '/manager/erp/co/findCoType.do',
				queryParams:{id:null},
				treeField : 'name',
				nowrap : false,
				rownumbers : true,
				animate : true,
				singleSelect : true,
				idField : 'id',
				collapsible : true,
				columns : [ [
						{
							title : '栏目分类名称',
							field : 'name',
							width : 300
						} ] ],
				onBeforeLoad : function(row, param) {
					for ( var i in row) {
						if (i == "id") {
							$(this).treegrid('options').url = getCurProjPath()
									+ "/manager/erp/co/findCoType.do?pid="
									+ row[i];
						}
					}
					return true;
				}
			});
}
/**搜索时的选择框**/
function checkCoType3(){
	var node = $('#list3').treegrid('getSelected');
	if(node){
		$('#coTypeName').val(node.name);
		$('#coTypeId2').val(node.id);
		$('#dlg3').dialog('close');
	}else{
		$.messager.alert("系统提示","请选择栏目类型","info");
	}
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




//function getcityHaveNation(citydiv,grandpid){
//	$('#'+citydiv).combobox({
//		url:getCurProjPath()+'/manager/erp/ar/findCityHaveNation.do',
//		editable:false, //不可编辑状态
//        cache: false,
//        panelHeight: 'auto',//自动高度适合
//        valueField: 'id',    
//        textField: 'regionName', 
//        onLoadSuccess:function(){
//        	debugger
//        	if(grandpid!=null){
//        		$("#"+citydiv).combobox('select',grandpid);
//        	}
//        }
//	});
//}