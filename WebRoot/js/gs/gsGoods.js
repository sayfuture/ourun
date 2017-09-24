	var url;//表单提交请求的地址
   	var parameter = "";
   	
   	
    /*
 	  DataGrid 初始化
 	*/
	$(function(){
		$('#fm')[0].reset();  
		$('#toolbar a').hide();
		$('#list').datagrid({   
	      	url:'findGsGoodsList.do',
	      	singleSelect : true, 
	      	pagination: true,
	      	pageSize:10,pageList:[10,20,50],
	      	queryParams:{
  	        parameter:"{}"
  		},
  		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'catName',
			title : '类目',
			width : 80
		}, {
			field : 'productCode',
			title : '商品编码',
			width : 80
		}, {
			field : 'productName',
			title : '商品名称',
			width : 150
		}, {
			field : 'price',
			title : '标准价格',
			width : 80
		}, {
			field : 'pv',
			title : '标准PV',
			width : 80
		}, {
			field:'status',title:'状态',width:70,formatter:function(value,row,index){		
				//0=正常销售 1草稿 2审核
				if(value==0){
		  			return "<span style='color: green;'>已发布</span>";
		  		}else if(value==1){
		  			return "草稿";
		  		}else if(value==2){
		  			return "<span style='color: blue;'>审核</span>";;
		  		}else if(value==3){
		  			return "<span style='color: red;'>取消发布</span>";;
		  		}else if(value==4){
		  			return "<span style='color: red;'>退回</span>";;
		  		}
      	}
		}, 
		 {
			field : 'recommend',
			title : '选择',
			width : 80,
			formatter:function(value,row,index){
				if(value==0)
					return "<span style='color: blue;'>未选</span>";
				if(value==1)
					return "<span style='color: green;'>推荐</span>";
				if(value==2)
					return "<span style='color: red;'>热销</span>";
			}
		},{
			field:'title',
			title:'商品标题',
			width:500
		},{
			field : 'createUser',
			title : '创建人',
			width : 100
		}, {
			field : 'createTimeBasePo',
			title : '创建时间',
			width : 150
		}, {
			field : 'updateUser',
			title : '最后操作人',
			width : 150
		}
		, {
			field : 'updateTimeBasePo',
			title : '最后操作时间',
			width : 150
		}
		
		/*, {
			field : 'onlineDate',
			title : '上架时间',
			width : 150
		}, {
			field : 'offlineDate',
			title : '下架时间',
			width : 150
		}, {
			field : 'status',
			title : '状态',
			width : 150
		}, {
			field : 'createDate',
			title : '创建时间',
			width : 150
		}, {
			field : 'updateDate',
			title : '最后更新时间',
			width : 150
		}*/ ] ],
	      	onBeforeLoad:function(){
	      		
	      	},
	      	onLoadSuccess:function(){
	      		buttons();
	      	},
	      	onDblClickRow: function (index, row) {
	      		editGsGoods(row);
	        }
 		});  
	});	
	
   	
/**
	触发“查询”按钮的点击事件后，所执行的方法
*/
function searchGsGoods(){
	$('#list').datagrid({   
      	url:'findGsGoodsList.do',
      	singleSelect : true, 
      	pagination: true,
      	pageSize:10,pageList:[10,20,50],
      	queryParams : {
			parameter : "{'productCode':'"
					+ $('#productCodeForSearch').val()
					+ "','productName':'"
					+ $('#productNameForSearch').val()
				    + "'}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'catName',
			title : '类目',
			width : 80
		}, {
			field : 'productCode',
			title : '商品编码',
			width : 80
		}, {
			field : 'productName',
			title : '商品名称',
			width : 150
		}, {
			field : 'price',
			title : '标准价格',
			width : 80
		}, {
			field : 'pv',
			title : '标准PV',
			width : 80
		}, {
			field:'status',title:'状态',width:70,formatter:function(value,row,index){		
				//0=正常销售 1草稿 2审核
				if(value==0){
		  			return "<span style='color: green;'>已发布</span>";
		  		}else if(value==1){
		  			return "草稿";
		  		}else if(value==2){
		  			return "<span style='color: blue;'>审核</span>";;
		  		}else if(value==3){
		  			return "<span style='color: red;'>取消发布</span>";;
		  		}else if(value==4){
		  			return "<span style='color: red;'>退回</span>";;
		  		}
      	}
		}, 
		 {
			field : 'recommend',
			title : '选择',
			width : 80,
			formatter:function(value,row,index){
				if(value==0)
					return "<span style='color: blue;'>未选</span>";
				if(value==1)
					return "<span style='color: green;'>推荐</span>";
				if(value==2)
					return "<span style='color: red;'>热销</span>";
			}
		},{
			field:'title',
			title:'商品标题',
			width:500
		},{
			field : 'createUser',
			title : '创建人',
			width : 100
		}, {
			field : 'createTimeBasePo',
			title : '创建时间',
			width : 150
		}, {
			field : 'updateUser',
			title : '最后操作人',
			width : 150
		}
		, {
			field : 'updateTimeBasePo',
			title : '最后操作时间',
			width : 150
		}
		
		/*, {
			field : 'onlineDate',
			title : '上架时间',
			width : 150
		}, {
			field : 'offlineDate',
			title : '下架时间',
			width : 150
		}, {
			field : 'status',
			title : '状态',
			width : 150
		}, {
			field : 'createDate',
			title : '创建时间',
			width : 150
		}, {
			field : 'updateDate',
			title : '最后更新时间',
			width : 150
		}*/ ]],
      	onBeforeLoad:function(){
      		buttons2();
      	},
      	onLoadSuccess:function(){
      		buttons();
      	},
       onDblClickRow: function (index, row) {
    	   editGsGoods(row);
       }
	});  
}

/**
	触发“添加商品”按钮的点击事件后，所执行的方法
*/
function addGsGoods() {
	init();
	$('#fm').form('clear'); // 清空表单
	$('#preview1').attr("src","");
	$('#preview1').attr("width",0);
	$('#preview1').attr("height",0);
	CKEDITOR.instances.goodsDesc.setData(""); 
	$('#dlg_gsGoods').dialog('open').dialog('setTitle','添加商品');	//显示对话框，并设置标题
	$("#tabs").tabs("select", 0);
	$('#fm').form('clear');
	$('#gsGoodsId').val("");
	$('#productName').val("");
	$('#id').val("");
	$("#state").val("1");
	
	url = 'addGsGoods.do';	
	document.documentElement.style.overflow = "hidden";
}

/**
	触发“添加商品”按钮的点击事件后，所执行的方法
*/
function editGsGoods() {
	init();
	init2();
	var row = $('#list').datagrid('getSelected');	//获取选择的行的人员
	if (row){
		if(row.status==1||row.status==4||row.status==3){
			editGsGoods1(row);
		}
		else{
			$.messager.alert('操作提示', '只可以对草稿或退回或取消发布状态的商品进行修改！','info');
		}
	}else{
   		$.messager.alert('操作提示', '请先选择一个商品，再修改！','info');
   	}
	
}

/**
	触发双击行记录事件后，所执行的方法
*/
function editGsGoods1(row) {
	$.ajax({
		method:'get',
		url:'goToModifyGsGoods.do',
		data:'id='+row.id,
		dataType: "json",
		success:function(data){

			$("#id").val(row.id);
			$('#gsGoodsId').val(row.id);
			$("#catId").val(row.catId);
			$("#catName").val(row.catName);
			$("#productName").val(row.productName);
			$("#productCode").val(row.productCode);
			$("#price").val(row.price);
			$("#pv").val(row.pv);
			$("#title").val(row.title);
			CKEDITOR.instances.goodsDesc.setData(row.goodsDesc);
			$("#kucun").val(row.pv);
			$("#state").val(row.state);
			$("input:radio[name='recommend']").each(function(){
				if($(this).val()==row.recommend){
//					$(this).attr("checked",true); //这样的写法不行
					this.checked=true;
				}
			});
//			if(row.status==3){
//				$("#editStatus").show();
//			}else{
//				$("#editStatus").hide();
//			}
			setGsKuProp();
			setSkuList();
			setImg();
		},
		error: function (msg) {
			message_op(false,null);
    	}
	});
	$('#dlg_gsGoods').dialog('open').dialog('setTitle','修改商品');	//显示对话框，并设置标题
	$("#tabs").tabs("select", 0);
	url = 'modifyGsGoods.do';		//重置url
}
     
/**
	触发“保存”按钮的点击事件后，所执行的方法
*/
function saveGsGoods() {
	$('#fm').form('submit', {									//提交表单
		url : url, 											//设置表单请求的地址
		onSubmit : function() {									//在表单提交前要执行的方法，返回false，则放弃请求
			 return $(this).form('validate');					//进行easyUI表单验证
		},
        success : function(result) {							//请求成功后执行的方法,result为请求返回的布尔值
        	result = eval(result);
			$('#id').val(result);
			$('#gsGoodsId').val(result); // 鍥剧墖
			init2();
			setImg();
			setGsKuProp();
			setSkuList();
        	message_op(true,'list');						// 调用Commons_message.js中的方法
        },
        error:function (){										//请求发生错误时，执行的方法
        	message_op(false,null);
        }
        
 	});
 }
    
/**
	触发“删除人员级别”按钮的点击事件后，所执行的方法
*/
function destroyGsGoods() {
 	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections');						// 获取选择的行的人员
	var ids ='';
	for(var i = 0;i<rows.length;i++){
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}				
	
	if (row){
		if(row.status==1){
			$.messager.confirm('', '您确定要删除该记录吗?', function(r) {				//确认对话框,判断是否继续进行操作
				if (r) {	
					$.post('deleteGsGoods.do', {						//通过post方法发送请求，id为参数
						ids:ids
					}, function(result) {												//异步请求完成后的回调函数
						if(result){	
							$.messager.alert('操作提示', '操作成功!','info');								// 调用Commons_message.js中的方法
							$("#list").datagrid("reload");
						}else{
							$.messager.alert('操作提示', '操作失败，不能删除！','info');
							$("#list").datagrid("reload");	
						}
					}, 'json');
				}});
		}
		else{
			$.messager.alert('操作提示', '只可以对草稿状态的商品进行删除！','info');
		}
		
   	}else{
   		$.messager.alert('操作提示', '请先选择商品，再删除！','info');
   	}
}
function audiGsgoods() {
 	var row = $('#list').datagrid('getSelected');
	var rows = $('#list').datagrid('getSelections');						// 获取选择的行的人员
	var ids ='';
	for(var i = 0;i<rows.length;i++){
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}				
	if (row){
			$.messager.confirm('', '您确定要提交该审核记录吗?', function(r) {				//确认对话框,判断是否继续进行操作
				if (r) {	
					$.post('audiGsgoods.do', {						//通过post方法发送请求，id为参数
						id:ids
					}, function(result) {												//异步请求完成后的回调函数
						if(result){	
							$.messager.alert('操作提示', '操作成功!','info');								// 调用Commons_message.js中的方法
							$("#list").datagrid("reload");
						}else{
							$.messager.alert('操作提示', '操作失败，不能提交！','info');
							$("#list").datagrid("reload");	
						}
					}, 'json');
				}});
   	}else{
   		$.messager.alert('操作提示', '请先选择商品！','info');
   	}
}



function buttons2(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'gsGoods'
	}, function(data) {
		$.each(eval('('+data+')'),function(index, obj){
			var opts = $('#list').datagrid('getColumnFields', false);  
			for(var i=0;i<opts.length;i++){
				if(opts[i] == obj){
					$('#list').datagrid( "showColumn",opts[i]);
				}
			}
		});
	});
}

function buttons(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'gsGoods'
	}, function(data) {
		$.each(eval('('+data+')'),function(index, obj){
			$('#toolbar a').each(function(){
				if($(this).attr('onClick') == obj){
					$(this).show();
				}
				if($("#search").attr('onClick') == obj){
   					$("#search").attr("disabled", false);
   				}
			});
		});
	});
}


function setImg() {
	var id = $('#gsGoodsId').val();
	$('#picture').datagrid({
		
						url : getCurProjPath()
								+ "/manager/erp/gs/findGsGoodsPicture.do?id="
								+ id,
						singleSelect : true,
						pagination : true,
						loadMsg : "数据加载中....请等候",
						pageSize:15,pageList:[15,20,50],
						columns : [ [
								{
									field : 'id',
									title : 'id',
									width : 30,
									checkbox : true
								},
								{
									field : 'filePath',
									title : '图片',
									width : 150,
									formatter : function(value) {
										var html = "<img src='"+getProsceniumProjPath()+"/upload/gs/" + value + "' border='0' style='width: 120px; height: 80px;'/>";
										return html;
									}
								}, 
								{field:'def',title:'是否是封面',width:70,formatter:function(value,row,index){					          		
									if(value==1){return "<span style='color: red;'>是</span>";}else{return "否";}
					          	}} , {
									field : 'name',
									title : '图片说明',
									width : 450
								}] ],
						onLoadSuccess : function(data, index) {
							if (data) {$.each(data.rows,function(index, item) {$.post("getGsGoodsImg.do",
																	{
																		id : id
																	},
																	function(data) {if (data) {if (eval('('+ data+ ')') == item.url) {
																				$('#picture').datagrid('checkRow',index);
																			}
																		}
																	});
												});
							}
						}
					});
}

function addImg() {
	var id = $("#gsGoodsId").val();
	if (id) {
		$("#dlg5").dialog('open').dialog('setTitle', '上传图片');
		$('#alt').val("");
		$('#gsGoodsId').val("");
		$('#imgId').val("");
		$("#strFileName").val("");
		$("#gsGoodsId").val(id);
		$("#progressBar").css("display", "none"); 
		url = 'addGsGoodsPicture.do';
	} else {
		$.messager.alert('操作提示', '请先添加基本信息，再操作！', 'info');
	}
}


function editImg() {
	var row = $('#picture').datagrid('getSelected'); // 获取选择的行的人员
	if (row) {
		$.ajax({
			method : 'get',
			url : 'getGsGoodsPicture.do',
			data : 'id=' + row.id,
			dataType : "json",
			success : function(data) {
				$("#imgId").val(row.id);
				$("#alt").val(row.name);
				$("#strFileName").val(row.fileName);
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$("#progressBar").css("display", "none"); 
		$('#dlg5').dialog('open').dialog('setTitle', '修改图片信息'); // 显示对话框，并设置标题
		url = 'updateGsGoodsPicture.do'; // 重置url
	} else {
		$.messager.alert('操作提示', '请先选择图片，再修改！', 'info');
	}
}

function destroyImg() {
	var row = $('#picture').datagrid('getSelected');
	var rows = $('#picture').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		if(row.def!=1){
			$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
				if (r) {
					$.post('deleteGsGoodsPictures.do', {
						ids : ids
					}, function(result) {
						if (result) {
							$('#picture').datagrid('reload');
						} else {
							$('#picture').datagrid('reload');
						}
					}, 'json');
				}
			});
		}
		else{
			$.messager.alert('操作提示', '默认封面不可删除！', 'error');
		}
	} else {
		$.messager.alert('操作提示', '请先选择图片信息，再删除！', 'info');
	}
}

function addImg3() {
	var row = $('#picture').datagrid('getSelected'); // 获取选择的行的人员
	if (row) {
		$.post('setGsGoodsPicture.do', {
			imgId : row.id,
			id : $("#gsGoodsId").val()
		}, function(result) {
			if (result) {
				$.messager.alert('操作提示', '操作成功！', 'info');
				$('#dlg4').dialog('close');
				setImg();
			} else {
				$.messager.alert('操作提示', '操作有误！', 'info');
				$('#dlg4').dialog('close');
				setImg();
				message_op(false, null);
			}
		}, 'json');
	} else {
		$.messager.alert('操作提示', '请先选择图片！', 'info');
	}
}
//隐藏
function init() {
	for ( var i = 1; i < 4; i++) {
		$('#tabs').tabs('disableTab', i);
	}
}

// 显示
function init2() {
	for ( var i = 1; i < 4; i++) {
		$('#tabs').tabs('enableTab', i);
	}
}

function selectGsGoodsType() {
	$('#select_gsGoodsType').dialog('open').dialog('setTitle', '选择类目');
	
	$('#list_gsGoodsType').treegrid({
		url:getCurProjPath()+'/manager/erp/gs/findGsGoodsTypeList.do',
		singleSelect: true,
		idField:'id',
		queryParams:{id:null},
	    treeField:'name',
	    rownumbers: true,
	    animate:true,
	    collapsible:true,
	    nowrap: false,
		
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'name',
			title : '类目名称',
			width : 250
		} ] ]
	});
}
function saveGsGoodsType() {
	var row = $('#list_gsGoodsType').datagrid('getSelected');
	if (row) {
		$("#catId").val(row.id);
		$("#catName").val(row.name);
	}
	$('#select_gsGoodsType').dialog('close');
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

function addImg2() {
	$('#fm3').form('submit', { // 提交表单
		url : url,
        onSubmit : function() {                                 //在表单提交前要执行的方法，返回false，则放弃请求
             fileChange($('#strFileName'));
                 var max = 100;                              //进度条
                 var prog = 0;
                 var counter = 0;
                 $("#progressBar").css("display", "");       
                 $("#progressBarText").html('上传文件进度: 0%');
                 document.getElementById('progressBarBoxContent').style.width = parseInt(prog) + '%';
                 $("#progress-content").html('0');
                 getProgress();
                 doProgressLoop(prog, max, counter);
             
            return true;                 //进行easyUI表单验证
             //return true;  
        },
       success : function(result) {                         //请求成功后执行的方法,result为请求返回的布尔值 
            clearTimeout(timer1);
            clearTimeout(timer2);
            $("#progressBarText").html('上传文件进度: 100%');
            document.getElementById('progressBarBoxContent').style.width = parseInt(100) + '%';
            if(result == "true"){
				$('#dlg5').dialog('close'); // close the dialog //关闭对话框
				setImg();// 调用Commons_message.js中的方法
				$("#list").datagrid('reload');
			}else{
				$.messager.alert('操作提示', '上传失败请检查图片格式！', 'info');
			}
            
        },
        error:function (){                                      //请求发生错误时，执行的方法
            $.messager.alert('操作提示', '上传失败请检查图片格式！', 'info');
			$('#dlg5').dialog('close'); // close the dialog //关闭对话框
			$("#list").datagrid('reload');
        }
        
    });
 }
 var timer1;
 var timer2;
 function doProgressLoop(prog, max, counter) { 
    var x = $("#progress-content").html();
    var y = parseInt(x);
    if (!isNaN(y)) {
        prog = y;
    }
    counter = counter + 1;
        if (prog < 100) {
            timer1 = setTimeout("getProgress()", 1000);
            timer2 = setTimeout("doProgressLoop(" + prog + "," + max + "," + counter + ")", 1500);
            $("#progressBarText").html('上传文件进度: ' + prog + '%');
            document.getElementById('progressBarBoxContent').style.width = parseInt(prog) + '%';
        }else{
           clearTimeout(timer1);
           clearTimeout(timer2); 
           $("#progressBarText").html('上传文件进度: 100%');
            document.getElementById('progressBarBoxContent').style.width = parseInt(100) + '%';
          
        }
}

function getProgress() {
    $.ajax({
        type: "post",
        dataType: "json",
        url: getCurProjPath()+"/manager/erp/comm/ProgressServlet.do",
        success: function (data) { 
            $("#progress-content").html(data);
        },
        error: function (err) {
           //$("#progressBarText").html("Error retrieving progress");
           alert('上传文件失败');
           clearTimeout(timer1);
           clearTimeout(timer2); 
        }
    });
    
}

//上传文件类型 
function fileChange(target,id) { 
    var filetypes =["RAR",".rar",".ZIP",".Zip",".zip",".jpg"]; 
    var filepath = target.value; 
    if(filepath){ 
        var isnext = false; 
        var fileend = filepath.substring(filepath.lastIndexOf(".")); 
        if(filetypes && filetypes.length>0){ 
            for(var i =0; i<filetypes.length;i++){ 
                if(filetypes[i]==fileend){ 
                    isnext = true; 
                    break; 
                } 
            } 
        } 
        if(!isnext){ 
            alert("不接受此文件类型！"); 
            target.value =""; 
            return false; 
        } 
    }else{ 
        return false; 
    }  
 } 