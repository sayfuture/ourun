var url;// 表单提交请求的地址

/*
 * DataGrid 初始化
 */
$(function() {   
    	$('#list').datagrid({ 
		url : 'findLogManagement.do',
		singleSelect : false,
		pagination : true,
		pageSize:10,
		pageList : [ 10, 20, 50 ],
		queryParams : {},
		
		columns:[ [
//		            {field:'id',title:'编号',width:100,checkbox:true},   
					{field:'index',title:'序号',width:38, align: 'center',formatter:function(val,row,index){
					     var options = $("#list").datagrid('getPager').data("pagination").options; 
					     var currentPage = options.pageNumber;
					     var pageSize = options.pageSize;
					     return (pageSize * (currentPage -1))+(index+1);
					    }},
		            {field:'auEmployee',title:'操作人员名',width:150,align : 'center', formatter:function(value,row,index){
		          		if(value.realName!=null){return value.realName+'';}else return "";
		          	}},
		        	{field:'ip',title:'ip地址',width:100},
		        	{field:'flag',title:'操作结果',width:80,align : 'center',formatter:function(value,row,index){
		          		if(value==1) {return "成功";}
		          		else return "失败";
		          	}},
		          	{field:'time2',title:'操作时间',width:130},
		          	{field:'logModule',title:'操作模块',align : 'center',width:150},
		        	{field:'logFunctions',title:'功能名',align : 'center',width:150}
		        	,  {
		    			field : 'preGeneratorPage',
		    			title : '操作',
		    			width : 140,
		    			formatter : function(value, row, index) {
		    				return "<img alt='img' src='../../../js/jquery-easyui-1.3.5/themes/icons/search.png' style='margin:4px 17px 0px 0px;width: 15px; height: 12px;' /><span style='cursor:pointer;display:block;margin:-15px 0 4px 15px;'"+
		    				"onclick=showEt('"+row.createUser+"');"+
		    				">查看操作员详情</span>";
		    			}
		    		}
		      	]], 
		    	onLoadSuccess:function(){	
//				      initPosSelect($("#auPosition").combobox("getValue"));		      
			 	}
    	});
    });	
    
    function buttons(){
    	$.post('../../getEmployeeButtons.do', {		//通过post方法发送请求，id为参数
    		'functionType':'logManagement'
    	}, function(data) {
    		$.each(eval('('+data+')'),function(index, obj){
    			$('#toolbar a').each(function(){
    				if($(this).attr('onClick') == obj){
    					$(this).linkbutton({disabled:false});
    				}
    				if($("#search").attr('onClick') == obj){
       					$("#search").attr("disabled", false);
       				}
    			});
    		});
    	});
    } 		
    
    function search(){
    	
    	var startDate = $('#startDate').datebox('getValue');
    	startDate = startDate == false ? "" : startDate;
    	var endDate = $('#endDate').datebox('getValue');
    	endDate = endDate == false ? "" : endDate;
    	var ip = $('#ip').val();
    	ip = ip == false ? "" : ip;
    	var name= $('#userName').val();
    	var logFunctions=$('#logFunctions').val();
//    	var auPositionId=$("#auPosition").combobox("getValue");
//    	auPositionId = auPositionId == false ? "" : auPositionId;
    	$('#list').datagrid(
			'reload',
			{
				parameter : "{'startDate':'" + startDate + "'," +
						"'endDate':'"+ endDate + "','ip':'"+ip+"','name':'" + name+ "','logFunctions':'"+logFunctions+"'}"
			});
}
    function selectUser() {
    	$('#auDialog').dialog('open').dialog('setTitle', '选择操作人员名'); // 显示对话框，并设置标题
    	    $('#aulist').datagrid({
    		url : getCurProjPath()+'/manager/erp/au/findLoginName.do',
    		singleSelect : true,
    		selectOnCheck: false,
          	checkOnSelect: true,
    		pagination : true,
    		pageSize:10,
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
    			field : 'realName',
    			title : '真实名',
    			width : 150
    		} ,
    		 {
    			field : 'loginName',
    			title : '登录名',
    			width : 150
    		}
    		] ]
    	});
    }   
    
    function searchUser(){	
    	 $('#aulist').datagrid('reload',{
    		 parameter:"{'realName':'"+$('#auName').val()+"'}"
    	 });
    
    }
 
    /**
     * 保存操作人员方法
     * 
     * @returns
     */
    function saveAu() {
    	var row = $('#aulist').datagrid('getSelected');	
    	if(row){
    		$('#userName').val('');
    		$('#userName').val(row.realName);
    		$('#userId').val(row.id);
    		$('#auDialog').dialog('close');
    	}else{
    		$.messager.alert('操作提示', '请先选择人员！', 'info');
    	}
    }
    
    
    //获得角色
	function initPosSelect(select){
		$('#auPosition').val('');
		$('#auPosition').combobox({
				url:getCurProjPath() + '/manager/erp/au/getAuPositionListByEmp.do',
				valueField:'id',
				textField:'name',
				editable:false ,
				onLoadSuccess:function(){
					var data = $('#auPosition').combobox('getData');
					select+='';
					if(select=='undefined'||select.length<1){
						select=data[0].id;
					}
        			if (data.length > 0) {
             			$('#auPosition').combobox('select', select);
        			}
				}
			});
    }
	
	
	function showEt(id) {
		if (id) {
			detailMe(id);
		} else {
			$.messager.alert('操作提示', '请选择一个人员', 'info');
		}
	}

	function detailMe(id) {
		$.ajax({
			url : getCurProjPath() + "/manager/erp/au/findAuEmployeeByRealName.do",
			method : 'post',
			data : 'empid='+id,
			dataType : "json",
			success : function(result) {
				$('#realName').val(result.realName);
				$('#loginName').val(result.loginName);
				if (result.sex == 1) {
					$('#sexInfo').val("男");
				} else {
					$('#sexInfo').val("女");
				}
				$('#telephoneInfo').val(result.tel);
				$('#telephoneInfo2').val(result.tel2);
				$('#ageInfo').val(result.age);
				$('#emailInfo').val(result.email);
				$('#postionInfo').val(result.auPosition.name);
				$('#deptInfo').val(result.auDept.name);
			},
			error : function(msg) {
				message_op(false, null);
			}
		});
		$('#dial').dialog('open').dialog('setTitle', '操作人员详情');
	}
	
  //获取项目的URL
  function getCurProjPath() {
      var curWwwPath = window.document.location.href;
      var pathName = window.document.location.pathname;
      var pos = curWwwPath.indexOf(pathName);
      var localhostPath = curWwwPath.substring(0, pos);
      var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
      return localhostPath + projectName;
  }