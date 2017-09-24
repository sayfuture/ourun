/*

	   //使用方法 
		var now = new Date(); 
		var nowStr = now.format("yyyy-MM-dd hh:mm:ss"); 
		//使用方法2: 
		var testDate = new Date(); 
		var testStr = testDate.format("YYYY年MM月dd日hh小时mm分ss秒"); 
		alert(testStr); 
		//示例： 
		alert(new Date().Format("yyyy年MM月dd日")); 
		alert(new Date().Format("MM/dd/yyyy")); 
		alert(new Date().Format("yyyyMMdd")); 
		alert(new Date().Format("yyyy-MM-dd hh:mm:ss"));
*/
Date.prototype.format = function(format){ 
	var o = { 
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	}; 

	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	}
	return format; 
} ;

function getCurProjPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
}

//获取前台项目的URL
function getProsceniumProjPath() {
//	return "/Zjhdweb";
	return "";
}

function fixWidth(percent) {
	return document.body.clientWidth * percent;
}  

function ajaxPost(url,jsonParam, callback,isAsync){
	 
	 var asy  =  arguments[3] ? false : true; 
	 $.ajax({
	    type: "POST",
	    url: url,
	    data: jsonParam,
		async:asy,
	    dataType: "json",
	    success: function(data) {
			callback(data);
	    }
	});
}

function initComB(divid,checkboxName,vname,items,url,onchageid){
	$.post(getCurProjPath()+url, {						
			'onchageid':onchageid
		}, function(data) {
			var str="";
			$.each(eval('('+data+')'),function(index, obj){
				str+='<input type="checkbox" value="'+obj.id+'" name="'+checkboxName+'">'+eval('obj.'+vname)+'</input>';
			});
			$("#"+divid+"").html(str);
			if (items!=undefined && items!=''){
				$.each(items, function (index, item) {
					 $("input[name='"+checkboxName+"']").each(function () {
				        if ($(this).val() == item.id) {
				            $(this).attr("checked",true);
				        }
				     });
				});
			}
		
		});
}
function initComR(divid,checkboxName,vname,items,url,onchageid){
	$.post(getCurProjPath()+url, {						
			'onchageid':onchageid
		}, function(data) {
			var str="";
			$.each(eval('('+data+')'),function(index, obj){
				str+='<input type="radio" value="'+obj.id+'" name="'+checkboxName+'">'+eval('obj.'+vname)+'</input>';
			});
			$("#"+divid+"").html(str);
			if (items!=undefined && items!=''){
//				$.each(items, function (index, item) {
					 $("input[name='"+checkboxName+"']").each(function () {
				        if ($(this).val() == items) {
				        	this.checked=true;
				        }
				     });
//				});
			}
		
		});
}
function initComS(divid,flag,name,select,curUrl,onchageid){
	jqueryMaskStart();
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?") ==-1 ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
	        onLoadSuccess:function(){
	        	jqueryMaskEnd();
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}


function initComS_S(divid,flag,name,select,curUrl,onchageid,onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl){
	
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?") ==-1 ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
			onSelect: function(record){
				if (onchageDivid!=undefined && onchageDivid!=null&&onchageDivid!=""){
					initComS(onchageDivid,flag,onchageName,onchageSelect,onchageUrl,record.id);
				}
				
	        },
	        onLoadSuccess:function(){
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}



function initComS_B(divid,flag,name,select,curUrl,onchageid,onchageDivid,onchageflag,onchageName,vname,onchageSelect,onchageUrl){
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?")  ==-1  ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
			onSelect: function(record){
				if (record!=undefined && record!=null){
					initComB(onchageDivid,onchageName,vname,onchageSelect,onchageUrl,record.id);
				}
	        },
	        onLoadSuccess:function(){
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}
function initComS_S_S(divid,flag,name,select,curUrl,onchageid,onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl,onchageid2,onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2){
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?") ==-1 ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
			onSelect: function(record){
				$('#'+onchageDivid2+'').combobox('loadData', [{}]);
				if (onchageDivid!=undefined && onchageDivid!=null&&onchageDivid!=""){
//					initComS(onchageDivid,flag,onchageName,onchageSelect,onchageUrl,record.id);
					initComS_S(onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl,record.id,onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2);
				}
				
	        },
	        onLoadSuccess:function(){
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}


function initComS_S_B(divid,flag,name,select,curUrl,onchageid,onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl,onchageid2,onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2){
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?") ==-1 ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
			onSelect: function(record){
				if (onchageDivid!=undefined && onchageDivid!=null&&onchageDivid!=""){
//					initComS(onchageDivid,flag,onchageName,onchageSelect,onchageUrl,record.id);
					initComS_B(onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl,record.id,onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2);
				}
				
	        },
	        onLoadSuccess:function(){
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}

function initComS_S_B_B_B(divid,flag,name,select,curUrl,onchageid,onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl,
		onchageid2,onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2,
		onchageDivid3,onchageflag3,onchageName3,vname3,onchageSelect3,onchageUrl3,
		onchageDivid4,onchageflag4,onchageName4,vname4,onchageSelect4,onchageUrl4
		){
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?") ==-1 ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
			onSelect: function(record){
				if (onchageDivid!=undefined && onchageDivid!=null&&onchageDivid!=""){
//					initComS(onchageDivid,flag,onchageName,onchageSelect,onchageUrl,record.id);
					initComS_B_B_B(onchageDivid,onchageflag,onchageName,onchageSelect,onchageUrl,record.id,
							onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2,
							onchageDivid3,onchageflag3,onchageName3,vname3,onchageSelect3,onchageUrl3,
							onchageDivid4,onchageflag4,onchageName4,vname4,onchageSelect4,onchageUrl4);
				}
				
	        },
	        onLoadSuccess:function(){
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}
function initComS_B_B_B(divid,flag,name,select,curUrl,onchageid,
		onchageDivid,onchageflag,onchageName,vname,onchageSelect,onchageUrl,
		onchageDivid1,onchageflag1,onchageName1,vname1,onchageSelect1,onchageUrl1,
		onchageDivid2,onchageflag2,onchageName2,vname2,onchageSelect2,onchageUrl2
){
	curUrl =getCurProjPath()+curUrl;
	if(curUrl.indexOf("?")  ==-1  ){
		curUrl+="?1=1";
	}
	if(flag==''||flag==undefined ){
		flag=0;
	}
	if(onchageid==''||onchageid==undefined ){
		onchageid="";
	}
	curUrl=curUrl+'&flag='+flag+'&onchageid='+onchageid;
	$('#'+divid+'').combobox({
			url:curUrl,
			valueField:'id',
			textField: name ,
			editable:false ,
			panelHeight:'auto',
			onSelect: function(record){
				if (record!=undefined && record!=null){
					initComB(onchageDivid,onchageName,vname,onchageSelect,onchageUrl,record.id);
					initComB(onchageDivid1,onchageName1,vname1,onchageSelect1,onchageUrl1,onchageid);
					initComB(onchageDivid2,onchageName2,vname2,onchageSelect2,onchageUrl2,record.id);
				}
	        },
	        onLoadSuccess:function(){
				var data = $('#'+divid+'').combobox('getData');
					if(flag==2 && (select==undefined || select=='')){
						select=data[0].id;
						$('#'+divid+'').combobox('select', select);
					}
					else if(flag!=3&&flag!=4){
						$('#'+divid+'').combobox('select', select);
					}
					if (select!=undefined && select!=''){
						$('#'+divid+'').combobox('select', select);
					}
			}
		});
}




function jqueryMaskStart(){
//    $("#"+buttonName).click(function(){  
    $("<div id=\"datagrid-mask\" class=\"datagrid-mask\" style=\"z-index:99999\"></div>").css({display:"block",width:document.body.clientWidth,height:document.body.clientHeight}).appendTo("body");  
    $("<div id=\"datagrid-mask-msg\" class=\"datagrid-mask-msg\" style=\"z-index:999999\"></div>").html("正在处理，请稍候。。。").appendTo("body")  
    .css({display:"block",left:(document.body.clientWidth - 190) / 2,top:(document.body.clientHeight - 45) / 2});  
//    });
};
function jqueryMaskEnd(){
	$("body").find(".datagrid-mask-msg").remove();  
	$("body").find(".datagrid-mask").remove();
}
//获取创建人
function getUser(id){
	var user;
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/au/getEmployee.do',
		data : 'id=' + id,
		dataType : "json",
		async : false,
		success : function(data) {
			user = data;
		},
		error : function(msg) {
			return '';
		}
	});
	return user;
}

/**
 * 地区js工具，获得市级及市级以下下拉列表
 * @param citydiv	城市DIV
 * @param grandpid	城市返回的值
 * @param countydiv	区县DIV
 * @param pid		区县返回的值
 * @param areadiv	地区DIV
 */
function getcity(citydiv,grandpid,countydiv,pid,areadiv){
	$('#'+citydiv).combobox({
		url:getCurProjPath()+'/manager/erp/ar/findCity.do',
		editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',//自动高度适合
        valueField: 'id',    
        textField: 'regionName', 
        onLoadSuccess:function(){
        	if(grandpid!=null){
        		$("#"+citydiv).combobox('select',grandpid);
        	}
        },
        onSelect: function(record){
        	if(countydiv!=null&&countydiv!=""){
        	$('#'+countydiv).combobox({
        		url:getCurProjPath()+"/manager/erp/ar/findCounty.do?id="+record.id,
        		editable:false, //不可编辑状态
                cache: false,
                panelHeight: 'auto',//自动高度适合
                valueField: 'id',    
                textField: 'regionName',
                onLoadSuccess:function(){
                	if(pid!=null){
                		$("#"+countydiv).combobox('select',pid);
                	}
                },
                onSelect: function(record){
                	if(areadiv!=null&&areadiv!=""){
                		$("#"+areadiv).val(record.id);
                	}
                }
        	});
        }
        }
	});
}

/**
 * 获取鑫家网站路径（暂时处理为固定）
 * @returns {String}
 */
function getPathRemote(){
	var url = "http://www.520xinjia.com";
	return url;
}
