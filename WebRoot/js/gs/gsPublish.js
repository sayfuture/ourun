	var url;//表单提交请求的地址
   	var parameter = "";
   	
   	
    /*
 	  DataGrid 初始化
 	*/
	$(function(){
		$('#toolbar a').hide();
		$('#list').datagrid({   
	      	url:'findPublishList.do',
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
			width : 150
		}, {
			field : 'productCode',
			title : '商品编码',
			width : 150
		}, {
			field : 'productName',
			title : '商品名称',
			width : 150
		}, {
			field : 'price',
			title : '标准价格',
			width : 150
		}, {
			field : 'pv',
			title : '标准PV',
			width : 150
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
		}
		,{
			field : 'updateUser',
			title : '提交人',
			width : 150
		},{
			field : 'updateTimeBasePo',
			title : '提交时间',
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
	      		select_gsSkulist(row) ;
	        }
 		});  
	});	
	
   	
/**
	触发“查询”按钮的点击事件后，所执行的方法
*/
function searchPublish(){
	$('#list').datagrid({   
      	url:'findPublishList.do',
      	singleSelect : true, 
      	pagination: true,
      	pageSize:10,pageList:[10,20,50],
      	queryParams : {
			parameter : "{'productCode':'"
					+ $('#productCodeForSearch').val()
					+ "','productName':'"
					+ $('#productNameForSearch').val()
					+"','productStatus':'"
					+$('#productStatusForSearch').val()
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
		width : 150
	}, {
		field : 'productCode',
		title : '商品编码',
		width : 150
	}, {
		field : 'productName',
		title : '商品名称',
		width : 150
	}, {
		field : 'price',
		title : '标准价格',
		width : 150
	}, {
		field : 'pv',
		title : '标准PV',
		width : 150
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
	},{
		field : 'updateUser',
		title : '提交人',
		width : 150
	},{
		field : 'updateTimeBasePo',
		title : '提交时间',
		width : 150
	}/*, {
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
      		buttons2();
      	},
      	onLoadSuccess:function(){
      		buttons();
      	},
       onDblClickRow: function (index, row) {
    	   select_gsSkulist(row) ;
    	   
       }
	});  
}

function buttons2(){
	$.post('../../getEmployeeButtons.do', {						//通过post方法发送请求，id为参数
		'functionType':'gsPublish'
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
		'functionType':'gsPublish'
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


function returnPublish() {
 	var row = $('#list').datagrid('getSelected');
 	var ids = row.id;
	if (row){
		if(row.status==2){
			$.messager.confirm('', '您确定要退回商品吗?', function(r) {				//确认对话框,判断是否继续进行操作
			if (r) {	
				$.post('returnPublish.do', {						//通过post方法发送请求，id为参数
					id:ids
				}, function(result) {												//异步请求完成后的回调函数
					if(result){	
						$.messager.alert('操作提示', '操作成功!','info');								// 调用Commons_message.js中的方法
						$("#list").datagrid("reload");
					}else{
						$.messager.alert('操作提示', '操作失败，不能退回商品！','info');
						$("#list").datagrid("reload");	
					}
				}, 'json');
			}});
		}
		else{
			$.messager.alert('操作提示', '只可能对审核商品进行退回！','info');
		}
   	}else{
   		$.messager.alert('操作提示', '请先选择商品，再删除！','info');
   	}
}
function stopPublish() {
 	var row = $('#list').datagrid('getSelected');
 	var ids = row.id;
	if (row){
		if(row.status==0){
		$.messager.confirm('', '您确定要取消发布商品吗?', function(r) {				//确认对话框,判断是否继续进行操作
		if (r) {	
			$.post('stopPublish.do', {						//通过post方法发送请求，id为参数
				id:ids
			}, function(result) {												//异步请求完成后的回调函数
				if(result){	
					$.messager.alert('操作提示', '操作成功!','info');								// 调用Commons_message.js中的方法
					$("#list").datagrid("reload");
				}else{
					$.messager.alert('操作提示', '操作失败，不能取消发布商品！','info');
					$("#list").datagrid("reload");	
				}
			}, 'json');
		}});
		}else{
			$.messager.alert('操作提示', '只可能对发布商品进行取消发布！','info');
		}
   	}else{
   		$.messager.alert('操作提示', '请先选择商品，再删除！','info');
   	}
}
    
function startPublish() {
	var row = $('#list').datagrid('getSelected');
	var ids = row.id;
	if (row){	
		if(row.status==2||row.status==3){
		$.messager.confirm('', '您确定要上架商品吗?', function(r) {				//确认对话框,判断是否继续进行操作
		if (r) {	
			$.post('startPublish.do', {						//通过post方法发送请求，id为参数
				id:ids
			}, function(result) {												//异步请求完成后的回调函数
				if(result){	
					$.messager.alert('操作提示', '操作成功!','info');								// 调用Commons_message.js中的方法
					$("#list").datagrid("reload");
				}else{
					$.messager.alert('操作提示', '操作失败，不能发布商品，可能没有绑定商品销售规格！','info');
					$("#list").datagrid("reload");	
				}
			}, 'json');
		}});
		}else{
			$.messager.alert('操作提示', '只可能对审核商品进行上架！','info');
		}
   	}else{
   		$.messager.alert('操作提示', '请先选择商品，再删除！','info');
   	}
}
   
function select_gsSkulist(row) {
	var gid = row.id;
	$('#dlg_gsGoods').dialog('open').dialog('setTitle','添加商品');	//显示对话框，并设置标题
	$("#tabs").tabs("select", 0);
	setImg(gid);
	setPorpValues(gid);
}

function setPorpValues(gid) {
	$('#gsSkulist').datagrid({
		url : 'findGsSkus.do',
		singleSelect : true,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {
			parameter : "{gid:"+gid+"}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		},
//		{
//			field : 'props',
//			title : '属性组合编号',
//			width : 150
//		},
		{
			field : 'propsname',
			title : '属性组合名称',
			width : 150
		}, {
			field : 'price',
			title : '单价(元)',
			width : 70
		}, {
			field : 'pv',
			title : 'pv值',
			width : 70
		}, {
			field : 'quantity',
			title : '库存(数量）',
			width : 70
		}, 
		{field:'is_def',title:'默认',width:70,formatter:function(value,row,index){					          		
      		if(value==1){return "<span style='color: red;'>是</span>";}else{return "否";}
      	}},{
			field : 'createTimeBasePo',
			title : '创建时间',
			width : 200
		}  ] ],
		onBeforeLoad : function() {
		},
		onLoadSuccess : function() {
		},
		onDblClickRow : function(index, row) {
			editGsPropName1(row);
		}
	});
}
function setImg(id) {
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