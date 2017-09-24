$(function(){
	$.ajaxSetup({
		async:false
	});
	
	$('#list').treegrid({   
	      	url:getCurProjPath()+'/manager/erp/gs/findGsGoodsTypeList.do',
	      	treeField:'name',
	      	nowrap: false,
    		rownumbers: true,
    		animate:true,
    		singleSelect: true, 
    		idField:'id',
    		collapsible:true,
	      	columns:[[ 
				{title:'商品分类名称',field:'name',width:300},
				{title:'是否是热点分类',field:'hotGsGoodsType',width:100,
					formatter:function(value, row, index){
						var str;
						$.ajax({
							method : 'get',
							url : getCurProjPath()+'/manager/erp/gs/getGsGoodsType.do',
							data : 'id=' + row.id,
							dataType : "json",
							success : function(data) {
								str = (null == data.hotGsGoodsType || data.hotGsGoodsType == 0 ? "否":"<span style='color: red;'>是</span>");
							},
							error : function(msg) {
								message_op(false, null);
							}
						});
						return str;
					}},
	          	{field:'id',title:'添加子类',width:250,align:'center',formatter:function(value,row,index){
	          		return  "<img alt='img' src='" + getCurProjPath() + "/js/jquery-easyui-1.3.5/themes/icons/pencil.png' style='margin:4px 17px 0px 0;width: 15px; height: 12px;' /><span style='cursor:pointer;display:block;margin:-20px 0 4px 19px;' onclick='newGsGoodsType(\""+row.id+"\");'>新增</span>";
				}}
			]]
			,onBeforeLoad: function(row, param){
//				debugger
				for(var i in row) {
					//alert(i);
				    if(i == "id"){
				    	//alert(row[i]);
				    	$(this).treegrid('options').url = getCurProjPath()+"/manager/erp/gs/findGsGoodsTypeList.do?pid=" + row[i];
				    }
				}
				
				return true;
			}
   		}); 
	});	
	
// 查询一个商品类型 id:商品类型Id
function getOneGsGoodsType(id){
	var gsGoods;
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/gs/getGsGoodsType.do',
		data : 'id=' + id,
		dataType : "json",
		success : function(data) {
			gsGoods = data;
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	return gsGoods;
}

var url;//表单提交请求的地址
var parameter = "";
var oldName="";//修改时验证是否修改了名字
var flag = ""; // 名称是否存在
   	

//获取项目的URL
function getCurProjPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPath + projectName;
}

function editGsGoodsType() {
	var node = $('#list').treegrid('getSelected');
	if(node){
		$.ajax({
			method:'get',
			url:getCurProjPath()+ '/manager/erp/gs/getGsGoodsType.do',
			data:'id='+node.id,
			dataType: "json",
			success:function(data){
				$("#id").val(node.id);
				$("#uname").val(data.name);
				oldName = $("#uname").val();
				$("#pname").val("不可修改");
			},
			error: function (msg) {
				message_op(false,null);
        	}
		});
		
		//$('#main')[0].src='xxxEdit.action'; 
		$('#openRoleDiv').dialog('open'); 
		$('#dlg').dialog('open').dialog('setTitle','修改商品分类');	//显示对话框，并设置标题
		url = getCurProjPath()+ '/manager/erp/gs/updateGsGoodsType.do';		//重置url
		
	}else{
		$.messager.alert("操作提示", "请先选择您要修改的商品分类！","info");
	}
}
function newGsGoodsType(pid) {

	$('#fm').form('clear');		
	oldName = "";
	if(pid != 0){
		$.ajax({
			method:'get',
			url:getCurProjPath()+ '/manager/erp/gs/getGsGoodsType.do',
			data:'id='+pid,
			dataType: "json",
			success:function(data){
				$("#pid").val(pid);
				$("#pname").val(data.name);
				$("#level").val(data.level + 1);
			},
			error: function (msg) {
				message_op(false,null);
        	}
		});
	}else{
		$("#pname").val("无");
		$("#level").val("0");
		$("#pid").val(null);
	}
	$('#dlg').dialog('open').dialog('setTitle', '添加商品分类');		//显示对话框，并设置标题
	url = getCurProjPath()+ '/manager/erp/gs/addGsGoodsType.do';				//重置url
	document.documentElement.style.overflow = "hidden";
}
function destroyGsGoodsType() {
	var row = $('#list').treegrid('getSelected');						// 获取选择的行的工作组
	
	if (row){																	//判断是否存在被选择的行
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {				//确认对话框,判断是否继续进行操作
		if (r) {	
			$.post(getCurProjPath() + '/manager/erp/gs/deleteGsGoodsType.do', {						//通过post方法发送请求，id为参数
				id:row.id
			}, function(result) {												//异步请求完成后的回调函数
				if(result){			
					message_op(true,'list');								// 调用Commons_message.js中的方法
					$('#list').treegrid('reload');
				}else{
					message_op(false,'list');								// 调用Commons_message.js中的方法
				}
			}, 'json');
		}});
   	}else{
   		$.messager.alert("操作提示", "请先选择您要删除的商户级别！","info");
   	}
}
/**
	触发“保存”按钮的点击事件后，所执行的方法
*/
function saveGsGoodsType() {
		$.post(getCurProjPath() + '/manager/erp/gs/checkGsGoodsTypeName.do',{name:$('#uname').val()},function(status){
			if(status){
				if(status == "true"){
					$('#mess').html("");
					saveObject();
				}else{
					if(oldName == $('#uname').val()){
						$('#mess').html("");
						saveObject();
					}else{
						$('#mess').html("名称已存在");
					}
				}
			}else{
				$.messager.alert('操作提示', '操作有误！','info');
			}
		});
 }
 
 function saveObject(){
 	$('#fm').form('submit', {									//提交表单
				url : url, 											//设置表单请求的地址
				onSubmit : function() {									//在表单提交前要执行的方法，返回false，则放弃请求
				 return $(this).form('validate');
	   },
       success : function(result) {							//请求成功后执行的方法,result为请求返回的布尔值
	     		$('#dlg').dialog('close'); // close the dialog	//关闭对话框
        		message_op(true,'list');						// 调用Commons_message.js中的方法
        		$('#list').treegrid('reload');
       },
       error:function (){										//请求发生错误时，执行的方法
        		message_op(false,null);
       }
 	});
}

$(function(){
	$('#uname').blur(function(){
		$.post(getCurProjPath() + '/manager/erp/gs/checkGsGoodsTypeName.do',{name:$('#uname').val()},function(status){
			if(status){
				if(status == "true"){
					$('#mess').html("");
				}else{
					if(oldName == $('#uname').val()){
						$('#mess').html("");
					}else{
						$('#mess').html("名称已存在");
					}
				}
			}else{
				$.messager.alert('操作提示', '操作有误！','info');
			}
		});
	});
 });


//批量导入
function batchAddGsGoodsType(){
	$('#batchAdd').dialog('open').dialog('setTitle', '批量导入商品类型');		//显示对话框，并设置标题
}

function checkFile(){
	var fileName = $('#fileName').val();  
    if('' == fileName) {  
        alert("请选择上传文件");  
        return false;  
    }  
    var bingIndex = fileName.lastIndexOf('.');  
    if(-1 == bingIndex) {  
        alert("上传文件格式错误");  
        return false;  ;  
    }  
    var suffix = fileName.substring(bingIndex + 1, fileName.length);  
    if('xls' != suffix ) {  
    	alert("请上传xls格式文件");  
        return false;
    }  
    $('#batchAdd').dialog('close');
    $('#msg').dialog('open').dialog('setTitle', '');
}

function doSubmit(){  
   	$('#fileForm').form('submit', {		//提交表单
			url : getCurProjPath() + '/manager/erp/gs/batchAddGsGoodsType.do', 	//设置表单请求的地址
			onSubmit : function() {		//在表单提交前要执行的方法，返回false，则放弃请求
			return checkFile();
	   	},
       	success : function(result) {		//请求成功后执行的方法,result为请求返回的布尔值
	     	$('#msg').dialog('close');  	//关闭对话框
	     	$('#list').treegrid('reload');
       	},
       	error:function (){	//请求发生错误时，执行的方法
        	message_op(false,null);
       	}
	});
}

// 设置热门分类
function setHotGsGoodsType(){
	var row = $('#list').treegrid('getSelected');	
	if(row){
		var str;
		$.ajax({
			method : 'get',
			url : getCurProjPath()+'/manager/erp/gs/getGsGoodsType.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				str = (null == data.hotGsGoodsType || data.hotGsGoodsType == 0 ? 0:1);
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		 if(str==1){
			 $.messager.confirm('', "您确定要设置"+row.name+"为普通商品分类吗?", function(r) {
				 if (r) {
					 setHot(row.id, 0);
				 }
			 });
		   }else{
			$.messager.confirm('', "您确定要设置"+row.name+"为热点商品分类吗?", function(r) {				//确认对话框,判断是否继续进行操作
				if (r) {
					setHot(row.id, 1);
				}
			});
		  }
	}else{
		$.messager.alert('操作提示', '请选择数据再操作！','info');
	}
}

function setHot(id, type){
	$.post('changeToHotGsGoodsType.do',{id:id,type:type},function(data){
		if(data){			
			$.messager.alert('操作提示', '操作成功！','info');								// 调用Commons_message.js中的方法
		}else{
			$.messager.alert('操作提示', '操作失败！','info');								// 调用Commons_message.js中的方法
		}
		$('#list').treegrid('reload');
	});
}
