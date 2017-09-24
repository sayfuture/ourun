	var url;//表单提交请求的地址
   	var parameter = "";
   	
   	
    /*
 	  DataGrid 初始化
 	*/
	$(function(){
		$('#list').datagrid({   
	      	url:'findSKUList.do',
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
		},
		{
			field : 'goodsName',
			title : '商品名称',
			width : 150
		},
		{
			field : 'propsname',
			title : '商品规格名称',
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
		},{
			field:'status',title:'规格状态',width:70,
			formatter:function(value,row,index){
				if(value==1){
					return "<span style='color: green;'>正常销售</span>"}
				if(value==0){
					return "<span style='color: red;'>取消发布</span>"
				}
			}
		},{
			field:'goodsCode',title:'是否绑定',width:100,
			formatter:function(value,row,index){
				if(value==null){
					return "<span style='color: green;'>未绑定</span>"}
				if(value!=null){
					return "<span style='color: red;'>"+value+"</span>"
				}
			}
		} ] ],
	      	onBeforeLoad:function(){
	      	},
	      	onLoadSuccess:function(){
	      	},
	      	onDblClickRow: function (index, row) {
	        }
 		});  
	});	
	
function gsbunding(){
	var row = $('#list').datagrid('getSelected');
	if(row){
		if(row.goodsCode==null||row.goodsCode==""){
				$('#skuid').attr("value",row.id);
				$('#dlg_gsBunding').dialog('open').dialog('setTitle','商品规格绑定');	//显示对话框，并设置标题
				$('#mytable').datagrid({   
			      	url:'findUnbundingGoods.do',
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
					},
					{
						field : 'goodsCode',
						title : '商品代码',
						width : 130,
					},
					{
						field : 'name',
						title : '商品名称',
						width : 150,
					}
					]]
				});
			url ='bunding.do';
			}else{
				$.messager.alert('操作提示', '绑定过的商品请解绑再绑定！','info');
		}
		}else{
			$.messager.alert('操作提示', '请选择一个商品，再绑定！','info');
		}
}

function searchForGsSKU(){
	$('#list').datagrid({   
      	url:'searchForGsSKU.do',
      	singleSelect : true, 
      	pagination: true,
      	pageSize:10,pageList:[10,20,50],
      	queryParams:{
      		parameter : "{'propsName':'"
      			+ $('#gsPropsNameForSearch').val()
      			+ "','name':'"
      			+ $('#gsNameForSearch').val()
      			+"','pv':'"
      			+$('#gsPVForSearch').val()
      			+"','bind':'"
      			+$('#bindForSearch').val()
      		    + "'}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		},
		{
			field : 'goodsName',
			title : '商品名称',
			width : 150
		},
		{
			field : 'propsname',
			title : '商品规格名称',
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
		},{
			field:'status',title:'规格状态',width:70,
			formatter:function(value,row,index){
				if(value==1){
					return "<span style='color: green;'>正常销售</span>"}
				if(value==0){
					return "<span style='color: red;'>取消发布</span>"
				}
			}
		},{
			field:'goodsCode',title:'是否绑定',width:100,
			formatter:function(value,row,index){
				if(value==null){
					return "<span style='color: green;'>未绑定</span>"}
				if(value!=null){
					return "<span style='color: red;'>"+value+"</span>"
				}
			}
		} ] ],
      	onBeforeLoad:function(){
      	},
      	onLoadSuccess:function(){
      	},
      	onDblClickRow: function (index, row) {
        }
		});  
}


	
function bunding(){
	var row = $('#mytable').datagrid('getSelected');
	if(row){
		$.messager.confirm('确认对话框', '您确定绑定该商品？', function(r){
			if (r){

				$.ajax({
					method : 'post',
					url : url,
					data : 'id=' + row.id+'&goodscode='+row.goodsCode+'&skuid='+$('#skuid').val(),
					dataType : "json",
					success : function(data) {
						if(data){
							message_op(true,null);
							
						}else{
							message_op(false,null);
						}
						$('#list').datagrid('reload');
						$('#dlg_gsBunding').dialog('close');
					},
					error : function(msg) {
						message_op(false, null);
					}
				})
			}
		});
	}else{
		$.messager.alert('操作提示', '请选择一个商品，再绑定！','info');
	}

}
	function gsunbunding(){
		var row = $('#list').datagrid('getSelected');	
		if(row){
			$.messager.confirm('确认对话框', '您确定解绑该商品？', function(r){
				$.ajax({
					method : 'post',
					url : "gsunbunding.do",
					data : 'id=' + row.id,
					dataType : "json",
					success : function(data) {
						if(data){
							message_op(true,null);
							
						}else{
							message_op(false,null);
						}
						$('#list').datagrid('reload');
					},
					error : function(msg) {
						message_op(false, null);
					}
				})
			})
		}else{
			$.messager.alert('操作提示', '请选择一个已绑定商品，再操作！','info');
		}
	}
function searchForBiGoods(){
	$('#mytable').datagrid({   
      	url:'searchForBiGoods.do',
      	singleSelect : true, 
      	pagination: true,
      	pageSize:10,pageList:[10,20,50],
      	queryParams:{
	        parameter:"{'goodsCode':'"+
		        $('#goodsCodeForSearch').val()
		        +"','goodsName':'"+
		        $('#goodsNameForSearch').val()
		        +"'}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		},
		{
			field : 'goodsCode',
			title : '商品代码',
			width : 130,
		},
		{
			field : 'name',
			title : '商品名称',
			width : 150,
		}
		]]
	});
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

