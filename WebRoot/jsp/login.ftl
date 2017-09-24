<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欧润电商平台-后台登录</title>
    <link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/gray/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/layout.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.7.2.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <script type="text/javascript">
    var _topWin = window;  
	while (_topWin != _topWin.parent.window) {  
	     _topWin = _topWin.parent.window;  
	}  
	if (window != _topWin){_topWin.document.location.href = '${base}/manager/main.do';changeImg();}


	$(window).resize(function(){ 
   	  $("#container").css({ 
   	    position: "absolute", 
        left: ($(window).width() - $("#container").outerWidth())/2, 
        top: ($(window).height() - $("#container").outerHeight())/2 
  	  });        
	}); 

 	$(document).keyup(function(event){
 		 if(event.keyCode ==13){
   			 saveUser3();
  		}
 	});

	$(function(){ 
   		 $(window).resize(); 
	}); 

		function changeImg() { 
       	 	var imgSrc = $("#imgObj"); 
        	var src = imgSrc.attr("src"); 
       		 imgSrc.attr("src", chgUrl(src)); 
    	} 
   	function chgUrl(url) { 
        var timestamp = (new Date()).valueOf(); 
        url = url.substring(0, 50); 
        if ((url.indexOf("&") >= 0)) { 
            url = url + "×tamp=" + timestamp; 
        } else {
            url = url + "?timestamp=" + timestamp; 
        } 
        return url; 
    } 
    
     function saveUser3() {
     		var name = $("#name").val();
			var password = $("#password").val();
			var code = $("#index_code").val();
				if ("" == name) {
					$.messager.alert("提示", "用户名不能为空！", "warning");
					return;
				}
				if ("" == password) {
					$.messager.alert("提示", "密码不能为空！", "warning");
					return;
				}
				if ("" == code) {
					$.messager.alert("提示", "验证码不能为空！", "warning");
					return;
				}
     			
     			$.post("${base}/manager/loginAction.do", {
								name: name,
								password: password,
								code:code
				}, function(returnData) {
						if (returnData) { 
							if (returnData == 0) {
									var url = "${base}/manager/index.do";
									location.href = url;
								} else if(returnData == 3){
									$.messager.alert("错误", "用户名或密码有误！", "error");
								} else if(returnData == 4){
									$.messager.alert("错误", "密码有误！", "error");
								} else if(returnData == 5){
									$.messager.alert("错误", "该用户已经被删除！", "error");
								} else if(returnData == 2){
									$.messager.alert("错误", "验证码错误！", "error");
								}
							} else {
								$.messager.alert("错误", "登录时发生错误！", "error");
							}
						});
					}
</script>
</head>
<body>
    <div id="container">
	<div class="left"></div>
	
    <div class="right" style="margin-top: 130px;">
    <form id="loginForm" method="POST">
        <fieldset>
            <ol>
                <h1>登陆平台</h1>
                <li class="sks1"><label>用&nbsp;户&nbsp;名：</label><input type="text" value="itAdmin" id="name" name="name" class="text" tabindex="1" maxLength="12" autocomplete="off" autofocus/></li>
                <li class="sks1"><label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</label><input type="password" id="password" name="password" class="text" value="111111" maxLength="12" tabindex="2" autocomplete="off"/></li>
                <li class="sks2"><label>验&nbsp;证&nbsp;码：</label><input type="text" id="index_code" name="code" style="ime-mode:disabled" class="txt" tabindex="3" autocomplete="off" MaxLength="4" value="1"/><br/></li>
                <li class="sks"><input type="button" value="LOGIN" class="button" onClick="saveUser3();"/></li>
                <li class="sks3"><label>&nbsp;&nbsp;</label><label class="img"><img  id="imgObj" src="${base}/manager/getCode.do" style="cursor:pointer;width:119px;height:28px;display:block;" alt="ABCD" clstag="regist|keycount|personalreg|06" onclick="" id="verification"  /><a href="#" onclick="changeImg()"><i style="padding:5px;color:#ff9500;font-size:14px;text-decoration: underline;">换一张</i></a></label></li>
            </ol>
        </fieldset>
    </form>
    </div>
	
</div>
 </body>
</html>