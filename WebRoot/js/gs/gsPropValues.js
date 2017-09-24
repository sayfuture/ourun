/**
 * 表单URL
 */
var url;


Array.prototype.remove = function(s) {   
    for (var i = 0; i < this.length; i++) {   
        if (s == this[i])   
            this.splice(i, 1);   
    }   
}   
  
/**  
 * Simple Map  
 *   
 *   
 * var m = new Map();  
 * m.put('key','value');  
 * ...  
 * var s = "";  
 * m.each(function(key,value,index){  
 *      s += index+":"+ key+"="+value+"/n";  
 * });  
 * alert(s);  
 *   
 * @author dewitt  
 * @date 2008-05-24  
 */  
function Map() {   
    /** 存放键的数组(遍历用到) */  
    this.keys = new Array();   
    /** 存放数据 */  
    this.data = new Object();   
       
    /**  
     * 放入一个键值对  
     * @param {String} key  
     * @param {Object} value  
     */  
    this.put = function(key, value) {   
        if(this.data[key] == null){   
            this.keys.push(key);   
        }   
        this.data[key] = value;   
    };   
       
    /**  
     * 获取某键对应的值  
     * @param {String} key  
     * @return {Object} value  
     */  
    this.get = function(key) {   
        return this.data[key];   
    };   
       
    /**  
     * 删除一个键值对  
     * @param {String} key  
     */  
    this.remove = function(key) {   
        this.keys.remove(key);   
        this.data[key] = null;   
    };   
       
    /**  
     * 遍历Map,执行处理函数  
     *   
     * @param {Function} 回调函数 function(key,value,index){..}  
     */  
    this.each = function(fn){   
        if(typeof fn != 'function'){   
            return;   
        }   
        var len = this.keys.length;   
        for(var i=0;i<len;i++){   
            var k = this.keys[i];   
            fn(k,this.data[k],i);   
        }   
    };   
       
    /**  
     * 获取键值数组(类似Java的entrySet())  
     * @return 键值对象{key,value}的数组  
     */  
    this.entrys = function() {   
        var len = this.keys.length;   
        var entrys = new Array(len);   
        for (var i = 0; i < len; i++) {   
            entrys[i] = {   
                key : this.keys[i],   
                value : this.data[i]   
            };   
        }   
        return entrys;   
    };   
       
    /**  
     * 判断Map是否为空  
     */  
    this.isEmpty = function() {   
        return this.keys.length == 0;   
    };   
       
    /**  
     * 获取键值对数量  
     */  
    this.size = function(){   
        return this.keys.length;   
    };   
       
    /**  
     * 重写toString   
     */  
    this.toString = function(){   
        var s = "{";   
        for(var i=0;i<this.keys.length;i++,s+=','){   
            var k = this.keys[i];   
            s += k+"="+this.data[k];   
        }   
        s+="}";   
        return s;   
    };   
}   
function setGsKuProp() {
	var gid=$("#gsGoodsId").val();
	$('#gsKuProplist').datagrid({
		url : 'findGsKuPropList.do',
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
		}, {
			field : 'propName',
			title : '属性名称',
			width : 150
		}, {
			field : 'goodsName',
			title : '商品名称',
			width : 150
		}, {
			field : 'createTimeBasePo',
			title : '创建时间',
			width : 200
		} , {
			field : 'createUser',
			title : '创建人',
			width : 200
		} ] ],
		onBeforeLoad : function() {
		},
		onLoadSuccess : function() {
		},
		onDblClickRow : function(index, row) {
			editGsPropName1(row);
		}
	});
}

/**
 * 触发“查询”按钮的点击事件后，所执行的方法
 */
function searchGsPropName() {
	$('#gsKuProplist').datagrid('reload', {
		parameter : "{"
			+ "'propName':'" + $('#propNameForSearch').val() + "'"
			+ "}"
	});
}


function saveGsPropName() {
$.messager.confirm('', '您确定要保存吗?', function(r) {
if (r) {	
	var rows = $('#list_gsPropName').datagrid('getSelections');						
	var ids ='';
	for(var i = 0;i<rows.length;i++){
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}	
	var gid = $("#gsGoodsId").val();
	ajaxPost("bandGsKuProp.do",{"ids":ids,"gid":gid},function(result){
		if(result==0){
			$("#gsKuProplist").datagrid("reload");	
    		message_op(true,'list');
    	}
    	else if(result==2){
    		$.messager.alert('操作失败', '商品为发布状态，请下架该商品！', 'error');
    	}
    	else{
    		message_op(false,null);
    	}
	});

	$('#select_gsPropName').dialog('close');
}});
}
function selectGsPropName() {
	$('#select_gsPropName').dialog('open').dialog('setTitle', '选择属性');
	$('#list_gsPropName').datagrid({
		url : 'findGsPropNameList.do',
		singleSelect : false,
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 50 ],
		queryParams : {
			parameter : "{}"
		},
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			checkbox : true
		}, {
			field : 'propName',
			title : '属性名称',
			width : 150
		}, {
			field : 'seqNo',
			title : '排序号',
			width : 100
		} ] ]
	});
}

function removeGsPropName() {
	var row = $('#gsKuProplist').datagrid('getSelected');
	var rows = $('#gsKuProplist').datagrid('getSelections');
	var ids = '';
	for ( var i = 0; i < rows.length; i++) {
		ids = ids == '' ? rows[i].id : ids + ',' + rows[i].id;
	}
	if (row) {
		$.messager.confirm('', '您确定要删除该记录吗?', function(r) {
			if (r) {
				$.post('removeGsPropName.do', {
					ids : ids
				}, function(result) {
					if (result) {
						$('#gsKuProplist').datagrid('reload');
					} else {
						$('#gsKuProplist').datagrid('reload');
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('操作提示', '请先选择商品属性信息，再删除！', 'info');
	}
}


function showGsSku() {
	var gid = $("#gsGoodsId").val();
	var num=0;
	if (gid) {
		ajaxPost("showGsSku.do",{"gid":gid},function(data){
			   var str="<table border=\"0\">";
			     $.each(data,function(i,prop){
			      var json_e =eval(prop.gsPropValueList);
			      var propValuestr ="";
			      $.each(json_e,function(j,m){  
			    	  propValuestr =propValuestr +' <input type="checkbox" name="isPropvalue'+i+'" id="isPropvalue'+i+'" class="easyui-checkbox" value="'+m.id+':'+m.propValue+':'+prop.propName+'" /> '+m.propValue+'&nbsp;&nbsp;&nbsp;&nbsp;';
					});
			      str+="<tr>";
			      str+="<td   style='width:100px;' >   <b>"+prop.propName+"</b> </td>";
			      str+="<td>"+propValuestr+"</td>";
			      str+="</tr>";
			      num++
			     });
			   
			     str+="</table><br><div id='toolbar1'><a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' id ='in_form'plain='true' onclick='showinGsSkuform("+num+")'>生成表单</a></div><div id='inform'></div>";
			     $("#invalue").html(str);
			     $.parser.parse('#invalue');
			     $('#dlg6').dialog('open').dialog('setTitle', '生成所有规格商品'); // 显示对话框，并设置标题
	});
		
		
	} else {
		$.messager.alert('操作提示', '请先添加基本信息，再操作！', 'info');
	}
	
}


function showinGsSkuform(len){
		if((len == "" || len == undefined || len == null)){
			$('#dlg6').dialog('close');
			$("#tabs").tabs("select", 2);
			$.messager.alert('操作提示', '请先绑定商品属性！', 'info');
			return;
		}
		var chk_value =new Array();
		for(var i=0;i<len;i++){
			chk_value[i]=new Array();
			$('input[name="isPropvalue'+i+'"]:checked').each(function(j){
			//	alert(i+"  "+j +"  "+$(this).val());
				chk_value [i][j]=$(this).val();
				
			}); 
		}
		var map = new Map();
		for(var i=0;i<chk_value.length;i++){ 
		    var arr_link=new Array(); 
		    arr_link=chk_value[i];
		    if(arr_link.length>0){
		    	str0=arr_link[0].split(":");
		    	map.put(str0[2],arr_link);
		    }
	   
		 }
	 var str=	retrunstr(map);
	 $("#inform").html(len==0 ?'你还没有选择任何内容！':str);
	 $.parser.parse('#inform');
		
}; 



function retrunstr(map){
	var temp =[];
	var gid = $("#gsGoodsId").val();
	if (gid) {
		//<a href="javascript:saveGsPropvalues();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	var tempstr='<form id="propValuesform" novalidate method="post">'+
		'<fieldset><table style="width:800px;" ><table style="width:800px;" ><input type="hidden" value="'+gid+'" name="gid"  />';
	 tempstr+='<tr>';
	 map.each(function(key,value,index){     
		 tempstr+='<td  style="width:100px;">'+key+'</td>';
		 temp[index]= map.get(key);
	    }); 
	 tempstr+='<td>库存</td>';
	 tempstr+='<td>金额</td>';
	 tempstr+='<td>PV值</td>';
	 tempstr+='</tr>';
	var len = 0; 
	switch(map.keys.length ){
		case 1:
			len=0;
			for(i=0;i<temp[0].length;i++){
						 str0=temp[0][i].split(":"); 
						 tempstr+='<tr><input type="hidden" value="'+str0[0]+'" id="propvalues'+len+'"  name="propvalues'+len+'"  />'+
			              '<input type="hidden" value="'+str0[1]+'" id="propnames'+len+'"  name="propnames'+len+'"  />';
						 tempstr+='<td>'+str0[1]+'</td>';
						 tempstr+='<td><input class="easyui-numberbox"   style="width:100px;" data-options="required:true" id="kucun'+len+'"  name="kucun'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true,min:0,precision:2"  id="price'+len+'" name="price'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true"  id="pv'+len+'" name="pv'+len+'"/></td>';
						 tempstr+='</tr>';
						 len++;
			}
			tempstr+='<tr><td><input type="hidden" value="'+len+'" name="len"  /> </td></tr></table> </fieldset></form>';
			return tempstr;
			break;
		case 2:
			len=0;
			for(i=0;i<temp[0].length;i++){
				for(j=0;j<temp[1].length;j++){
						  str0=temp[0][i].split(":"); 
						  str1=temp[1][j].split(":"); 
						//alert("aaa"+i+"     "+j+"     "+temp[0][i]+"     "+temp[1][j]+"   "+temp[2][k]);
						  tempstr+='<tr><input type="hidden" value="'+str0[0]+':'+str1[0]+'" id="propvalues'+len+'"  name="propvalues'+len+'"  />'+
			              '<input type="hidden" value="'+str0[1]+'-'+str1[1]+'" id="propnames'+len+'"  name="propnames'+len+'"  />';
						 tempstr+='<td>'+str0[1]+'</td><td>'+str1[1]+'</td>';
						 tempstr+='<td><input class="easyui-numberbox"   style="width:100px;" data-options="required:true" id="kucun'+len+'"  name="kucun'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true,min:0,precision:2"  id="price'+len+'" name="price'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true"  id="pv'+len+'" name="pv'+len+'"/></td>';
						 tempstr+='</tr>';
						 len++;
				}
			}
			tempstr+='<tr><td><input type="hidden" value="'+len+'" name="len"  /> </td></tr></table> </fieldset></form>';
			return tempstr;
			break;
		case 3:
			len=0;
			for(i=0;i<temp[0].length;i++){
				for(j=0;j<temp[1].length;j++){
					for(k=0;k<temp[2].length;k++){	
						  str0=temp[0][i].split(":"); 
						  str1=temp[1][j].split(":"); 
						  str2=temp[2][k].split(":"); 
						 tempstr+='<tr><input type="hidden" value="'+str0[0]+':'+str1[0]+':'+str2[0]+'" id="propvalues'+len+'"  name="propvalues'+len+'"  />'+
						              '<input type="hidden" value="'+str0[1]+'-'+str1[1]+'-'+str2[1]+'" id="propnames'+len+'"  name="propnames'+len+'"  />';
						 tempstr+='<td>'+str0[1]+'</td><td>'+str1[1]+'</td><td>'+str2[1]+'</td>';
						 tempstr+='<td><input class="easyui-numberbox"   style="width:100px;" data-options="required:true" id="kucun'+len+'"  name="kucun'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true,min:0,precision:2"  id="price'+len+'" name="price'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true"  id="pv'+len+'" name="pv'+len+'"/></td>';
						 tempstr+='</tr>';
						 len++;
					}
				}
			}
			tempstr+='<tr><td><input type="hidden" value="'+len+'" name="len"  /> </td></tr></table> </fieldset></form>';
			return tempstr;
			break;
			
		case 4:
			len=0;
			for(i=0;i<temp[0].length;i++){
				for(j=0;j<temp[1].length;j++){
					for(k=0;k<temp[2].length;k++){
						for(l=0;l<temp[3].length;l++){
						  str0=temp[0][i].split(":"); 
						  str1=temp[1][j].split(":"); 
						  str2=temp[2][k].split(":"); 
						  str3=temp[3][l].split(":"); 
						//alert("aaa"+i+"     "+j+"     "+temp[0][i]+"     "+temp[1][j]+"   "+temp[2][k]);
						  tempstr+='<tr><input type="hidden" value="'+str0[0]+':'+str1[0]+':'+str2[0]+':'+str3[0]+'" id="propvalues'+len+'"  name="propvalues'+len+'"  />'+
			              '<input type="hidden" value="'+str0[1]+'-'+str1[1]+'-'+str2[1]+'-'+str3[1]+'" id="propnames'+len+'"  name="propnames'+len+'"  />';
						 tempstr+='<td>'+str0[1]+'</td><td>'+str1[1]+'</td><td>'+str2[1]+'</td><td>'+str3[1]+'</td>';
						 tempstr+='<td><input class="easyui-numberbox"   style="width:100px;" data-options="required:true" id="kucun'+len+'"  name="kucun'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;"data-options="required:true,min:0,precision:2"  id="price'+len+'" name="price'+len+'"/></td>';
						 tempstr+='<td><input class="easyui-numberbox"  style="width:100px;" data-options="required:true"  id="pv'+len+'" name="pv'+len+'"/></td>';
						 tempstr+='</tr>';
						 len++;
						}
					}
				}
			}
			tempstr+='<tr><td><input type="hidden" value="'+len+'" name="len"  /> </td></tr></table> </fieldset></form>';
			return tempstr;
			break;
	}
	
	} else {
		$.messager.alert('操作提示', '请先添加基本信息，再操作！', 'info');
	}
}
//function   isUnsignedInteger(s)
//{	
//	return /(^([1-9]\d*|0)$)/.test(s);
//}
function saveGsPropvalues(){
	$('#propValuesform').form('submit', {									//提交表单
		url : "addGsSkus.do", 
		onSubmit : function() {	
			return $(this).form('validate');		
		},
        success : function(result) {
        	if(result==0){
        		$("#gsSkulist").datagrid("reload");	
        		message_op(true,'list');
        		$('#dlg6').dialog('close');
        		
        	}
        	else if(result==2){
        		$.messager.alert('操作失败', '商品为发布状态，请下架该商品！', 'error');
        	}
        	else{
        		message_op(false,null);
        	}
        	
        },
        error:function (){										
        	
        }
        
 	});
}





function setSkuList() {
	var gid=$("#gsGoodsId").val();
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
				if(value==0){
					return "<span style='color: red;'>停用</span>"}
				if(value==1){
					return "<span style='color: green;'>启用</span>"
				}
			}
		} ] ],
		onBeforeLoad : function() {
		},
		onLoadSuccess : function() {
		},
		onDblClickRow : function(index, row) {
			editGsPropName1(row);
		}
	});
}


function showNotGsSku() {
	var gid = $("#gsGoodsId").val();
	
	if (gid) {
		 $("#gid").val(gid) ;
		 $("#price").val("") ;
		 $("#pv").val("") ;
		 $("#quantity").val("") ;
		 $('#dlg7').dialog('open').dialog('setTitle', '生成无规格商品'); // 显示对话框，并设置标题
		 url = "saveNotGsSku.do";
	} else {
		$.messager.alert('操作提示', '请先添加商品基本信息，再操作！', 'info');
	}
	
}

function saveNotGsSku() {
	$('#fm7').form('submit', {									//提交表单
		url : url, 
		onSubmit : function() {	
			return $(this).form('validate');		
		},
        success : function(result) {
        	if(result==0){
        		$("#gsSkulist").datagrid("reload");	
        		message_op(true,'list');
        		$('#dlg7').dialog('close');
        	}
        	else if(result==1){
        		$.messager.alert('操作提示', '已经添加了一条无规格属性记录！', 'error');
        	}
        	else {
        		message_op(false,null);
        	}
        },
        error:function (){	}
 	});
}

function editGsSku() {
	var gid = $("#gsGoodsId").val();
	var row = $('#gsSkulist').datagrid('getSelected');			//获取选择的行的人员
	if (row){
		$.ajax({
			method:'get',
			url:'getGsSku.do',
			data:'id='+row.id,
			dataType: "json",
			success:function(data){
				$("#gid").val(gid);
				$("#gsSkuId").val(row.id);
				$('#price').numberbox('setValue',row.price);
				$('#pv').numberbox('setValue',row.pv);
				$('#quantity').numberbox('setValue',row.quantity);
			},
			error: function (msg) {
				message_op(false,null);
        	}
		});
		$('#dlg7').dialog('open').dialog('setTitle','修改规格');	//显示对话框，并设置标题
		url = 'updateGsSku.do';		//重置url
	}else{
   		$.messager.alert('操作提示', '请先选择人员，再修改！','info');
   	}
}

function editGsSkuStatus(){
	var gid = $("#gsGoodsId").val();
	var row = $('#gsSkulist').datagrid('getSelected');
	if(row){
		//debugger
		if(row.status==1){
			$.messager.confirm('确认对话框', '您想要停用该商品规格吗？', function(r){
				if(r){
					$.ajax({
						method:'get',
						url:'editGsSkuStatus.do',
						data:'id='+row.id+'&status=0',
						dataType: "json",
						success:function(data){
							if(data==true)
							message_op(true,'gsSkulist');
							if(data==false)
								message_op(false,null);
						},
						error: function (msg) {
							message_op(false,null);
			        	}
					});
					}
				}
			);
		}
		if(row.status==0){
			$.messager.confirm('确认对话框', '您想要启用该商品规格吗？', function(r){
				if(r){
					$.ajax({
						method:'get',
						url:'editGsSkuStatus.do',
						data:'id='+row.id+'&status=1',
						dataType: "json",
						success:function(data){
							if(data==true)
								message_op(true,'gsSkulist');
							if(data==false)
								message_op(false,null);
						},
						error: function (msg) {
							message_op(false,null);
			        	}
					});
					}
				}
			);
		}
	}else{
		$.messager.alert('操作提示', '请先选择商品规格，再修改！','info');
	}
}















  












