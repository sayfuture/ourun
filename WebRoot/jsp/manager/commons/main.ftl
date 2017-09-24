<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欧润国贸_ERP核心系统</title>
	<link rel="stylesheet" href="${base}/css/main_style.css" type="text/css"></link>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>

	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript">
    var t = setTimeout(time,1000);
    
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
    function time()
    {
       clearTimeout(t);//清除定时器
       dt = new Date();
       var h=dt.getHours();
       var m=dt.getMinutes();
       var s=dt.getSeconds();
       document.getElementById("timeShow").innerHTML =  "当前时间为："+p(h)+"时"+p(m)+"分"+p(s)+"秒";
       t = setTimeout(time,1000); //设定定时器，循环执行             
    } 
	function logout() {
		$.messager.confirm('操作提示', '<br/>确认退出吗？',function(data){
			if(data){location.href = "${base}/manager/logout.do";}
		});
	}
		
		/**修改密码*/
	function updatePassword(){
		$('#dlg3').dialog('open').dialog('setTitle', '修改个人密码');		//显示对话框，并设置标题
		$('#dlg3').form('clear');										//清空表单
		$("#pas").val(${auEmployee.id});
		$("#rName").html("${auEmployee.realName}");
		$("#lName").html("${auEmployee.loginName}");
		url = '${base}/manager/erp/au/updatePassword.do';	
		document.documentElement.style.overflow = "hidden";
	}
	
	/**保存密码*/
	function updatePassword2() {
    	$('#uppassword').form('submit', {									//提交表单
			url : url, 											//设置表单请求的地址
			onSubmit : function() {									//在表单提交前要执行的方法，返回false，则放弃请求
				 if ($("#password2").val() != $("#password").val()){
					$.messager.alert("错误", "确认密码输入错误！", "error");
					return false;
				}else{
					 return $(this).form('validate');
				}					//进行easyUI表单验证
			},
	        success : function(result) {							//请求成功后执行的方法,result为请求返回的布尔值
		        	$('#dlg3').dialog('close'); // close the dialog	//关闭对话框
	        		message_op(true,'list');						// 调用Commons_message.js中的方法
	        },
	        error:function (){										//请求发生错误时，执行的方法
	        	message_op(false,null);
	        }
	        
     	});
	}
	
	
	function Open(text, url) {
        if ($("#tabs").tabs('exists', text)) {
            $('#tabs').tabs('select', text);
        } else {
            $('#tabs').tabs('add', {
                title : text,
                closable : true,
                content : ' <iframe height="100%" width="100%" id="main"  name ="main" frameborder="no" border="0" marginwidth="0" marginheight="0" src="'+url+'"/>'
            });
        }
    }
    
    function changeGuige(e){
	    $("a[name=button ]").removeAttr("style");
	    $(e).attr("style","color: #0081c2;line-heighr:35px;padding-left:17px;font-weight:bolder;background-image: url(${base}/image/qi.png);");
	}
	$(function() {
		$('#tabs').tabs({
		    border:false,
		    onSelect:function(title,index){
				 var currTab = $('#tabs').tabs('getTab', title);
				
             	 var iframe = $(currTab.panel('options').content);
            	 var src = iframe.attr('src');
            	 if(src!=undefined){
            	  $('#tabs').tabs('update', { tab: currTab, options: { content: createFrame(src)} });
            	 }
		    }
		});
	});
	
	function createFrame(url) {
	    var _frame = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	    return _frame;
	}
	</script>
</head>
<body>
<div id="cc" class="easyui-layout" style="width:100%;height:100%;"> 
<div data-options="region:'north',title:'欧润国贸 - ERP后台管理系统',split:true" style="height:90px; background:#196C96;">
<div align="left">
	<img src="${base}/image/crmtitle.jpg" style="float:left;height:60px;width:743px;"></img>
	<!--<div class="header_nav"> style="float:left;margin-left:30px;margin-top:8px;height:32px;width:143px;" 
    <a href="#" title="公司主页"><img src="${base}/image/icon/1.png" ></a>
    <a href="#" title="个人桌面"><img src="${base}/image/icon/2.png" ></a>
    <a href="#" title="邮件发送"><img src="${base}/image/icon/3.png" ></a>
    <a href="#" title="公司主页"><img src="${base}/image/icon/4.png" ></a>
    <a href="#" title="个人桌面"><img src="${base}/image/icon/5.png" ></a>
    </div>-->
    <div style="float:left;margin-top:8px;margin-left:3%;">
		<span style="font-size:10px; font-weight: bold; color:#fff;float:right;margin-top:6px; margin-right:12px;"><label id="timeShow"></lable> </span>
	</div>
	<div style="float:left;margin-top:8px;margin-left:3%;">
		<span style="font-size:8px;color:#56FA8C;float:right; margin-top:6px; margin-right:5px;">
	<!--	[ <a href="#" class="title" style="text-decoration:none;color:#56FA8C;" onclick="updatePassword();">修改密码</a> ] -->
		[ <a href="#" class="title"  style="text-decoration:none;color:#56FA8C;" onclick="logout();">退出</a> ]</span>
		<span style="font-size:10px; font-weight: bold; color:#fff;float:right;margin-top:6px; margin-right:12px;">&nbsp;${auEmployee.company.name}&nbsp;-&nbsp;[${auEmployee.realName}]&nbsp;您好！&nbsp;</span>
	</div>
</div>

<div id="dlg3" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons"	style="width: 650px; height: 330px; padding: 30px 50px" >
  		<div class="ftitle"></div>
  		<form id="uppassword" novalidate method="post">
  			<input type="hidden" name="id" id="pas" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">真实姓名:&nbsp;&nbsp;</th> 
   					<td class="shandan"><span id="rName"></span></td> 
  				</tr>
  				 <tr> 
    				<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">登录名:</th> 
   					<td class="shandan"><span id="lName"></span></td> 
  				</tr>
  				 <tr> 
    				<th scope="row" abbr="L2 Cache" class="specalt">新密码:</th> 
   					<td class="shandan"><input name="password1" type="password" id="password" class="easyui-validatebox" data-options="required:true,validType:'length[6,12]'" style="width:200px;" /></td> 
  				</tr>
  				 <tr> 
    				<th scope="row" abbr="L2 Cache" class="specalt" style="padding-right:18px;">确认密码:</th> 
   					<td class="shandan"><input name="password2" type="password" id="password2" class="easyui-validatebox" data-options="required:true,validType:'length[6,12]',equalTo:'#password'" style="width:200px;" /></td> 
  				</tr>
  				<td colspan="2" style="text-align:right;padding-right:49px;">
  						<a href="javascript:updatePassword2();" class="easyui-linkbutton" style="margin-top:10px;" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg3').dialog('close')">取消</a>
        			</td>
  				</table>
  		</form>
	</div>
</div>   
<div data-options="region:'west',title:'&nbsp;&nbsp;&nbsp;&nbsp;我的菜单',split:true" style="width:220px; text-align:center;" >
	<div id="menu" class="easyui-accordion" data-options="fit:false"  > 
	
	<#if map3??&&map3?size gt 0>
		<#list map3?keys as key>
	    <div title="${key}" data-options="" style="overflow:hidden;padding:0px;">   
			<table id="browse_table"  border="1px" bordercolor="#F2F2F2"  cellspacing="0px" >
			<#assign mapUser=map3[key] >  
                     <#list mapUser as contents> 
						<tr >
							<td style="height:24px;padding:0;outline-style:none;">
								<a href="#" style="text-decoration:none;"  onclick="Open('${contents.name}', '${contents.url}');changeGuige(this); "  name="button"  class="leftMenu" >${contents.name} </a>
							</td>
						</tr>
					  </#list>  
			</table>
		</div>
		</#list>
	 </#if>
	</div>
	 
</div>   
    
 <div region="center" title="">
    <div class="easyui-tabs" fit="true" border="false" id="tabs"  >
      <div title="欢迎使用">
		

		<div class="body">
				<div class="title">
					<div class="title_info zs">
						${auEmployee.realName} <label>您好，欢迎使用ERP后台管理系统</label> 
					</div>
				</div>
				<div>
					<div style="padding:10px 0 10px 70px;">
						您已经访问本系统&nbsp;${auEmployee.loginCount}&nbsp;次， 上一次访问是 ${(auEmployee.lastdate?string("yyyy-MM-dd HH:mm:ss"))!''} 
					</div>
				</div>
				<div style="clear:both"></div>
				<div class="use_meth">
					<div class="title">
						<div class="title_info light">
							ERP系统版权所有
						</div>
					</div>
					 <p>版权所有：欧润国际贸易有限公司 </p>
					 <p>公司网址：<a href="#">www.ourun.com</a> </p>
					 <p>版本信息：ERP核心系统1.0.5</p>
				</div>
					<p class="line">
					</p>
			</div>


		
	  </div>
    </div>
  </div></body>
</html>
