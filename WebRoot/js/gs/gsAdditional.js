	var url;//表单提交请求的地址
   	var parameter = "";
   	
   	
    /*
 	  DataGrid 初始化
 	*/
	$(function(){
//		$('#toolbar a').hide();
		$('#list').datagrid({   
	      	url:'findList.do',
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
		} ] ],
	      	onBeforeLoad:function(){
	      	},
	      	onLoadSuccess:function(){
	      	},
	      	onDblClickRow: function (index, row) {
	        }
 		});  
	});	
	
function gsAdd(){
	var row = $('#list').datagrid('getSelected');
	if(row){
		$("#id").attr("value",row.id);
	$('#dlg_gsAdditonal').dialog('open').dialog('setTitle','增加商品数量');	//显示对话框，并设置标题
	url ='gsAdd.do';
	
	}
	else{
		$.messager.alert('操作提示', '请选择商品规格，再操作！','info');
	}
}

function searchForGsName(){
	$('#list').datagrid({   
      	url:'findGsName.do',
      	singleSelect : true, 
      	pagination: true,
      	pageSize:10,pageList:[10,20,50],
      	queryParams:{
	        gsName:$("#gsNameForSearch").val()
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
	} ] ],
      	onBeforeLoad:function(){
      	},
      	onLoadSuccess:function(){
      	},
      	onDblClickRow: function (index, row) {
        }
		});  
}


	
function saveGsAdd(){
	$("#myform").form('submit',{
	method:'get',
	url:url,
	dataType: "json",
	success:function(data){
		if(data=="true"){
		$('#dlg_gsAdditonal').dialog('close');
		message_op(true,'list');
		}
		if(data=="false"){
			$('#dlg_gsAdditonal').dialog('close');
			message_op(false,null);
		}
	},
	error: function (msg) {
		message_op(false,null);
	}
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

