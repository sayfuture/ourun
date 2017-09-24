	var url;//表单提交请求的地址
   	var parameter = "";
   	
   	
    /**
 	  DataGrid 初始化
 	*/
	$(function(){
		$('#list').datagrid({   
	      	url:'findGsSkuAddRecord.do',
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
			field : 'addQuantity',
			title : '增加数量',
			width : 70
		}, 
//		{field:'is_def',title:'默认',width:70,formatter:function(value,row,index){					          		
//			if(value==1){return "<span style='color: red;'>是</span>";}else{return "否";}
//      	}},
      	{
			field : 'createTimeBasePo',
			title : '增加时间',
			width : 200
		},{
			field:'status',title:'规格状态',width:70,
			formatter:function(value,row,index){
				if(value==1){
					return "<span style='color: green;'>正常销售</span>"
				}
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

function searchForGsName(){
	$('#list').datagrid({   
      	url:'findGsByName.do',
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
		field : 'addQuantity',
		title : '增加数量',
		width : 70
	}, 
//	{field:'is_def',title:'默认',width:70,formatter:function(value,row,index){					          		
//		if(value==1){return "<span style='color: red;'>是</span>";}else{return "否";}
//  	}},
	{
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

