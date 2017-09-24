	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>test</title>
</head>
<body>

	<table>
	<tr>
		<td>Status</td>
		<td>Name</td>
		<td>Size</td>
		<td>Oparation</td>
	</tr>
	<tr>
		<td>${status}</td>
		<td>${name}</td>
		<td>${size}</td>
		<td>
			<form id="form1" method="post" action="${base}/manager/core/demo/downLoad.do"  >
				<input type="hidden" value="${name}" name="filename">
				<input type="hidden" value="${serverpath}" name="filePath">
				<input type="submit" value="Download" onclick="procSet()">
			</form>
		</td>
	</tr>
	</table>

	<div id="p" class="easyui-progressbar" style="width:400px;display:none;"></div>
	
	<div id="overlay">
		<!--半透明遮罩-->
	</div>

	<div id="olContent" style="display:none;">
    		<img src="${base}/js/jquery-easyui-1.3.5/themes/icons/load.gif"/>
	</div>
	
	<script type="text/javascript">
		hideOverlay();//隐藏缓冲条
		$(function(){
			
			var form=$('#form1');
		    form.submit(function(){
		    	
                $.ajax({
                    url: form.attr('action'),   // 提交的页面
                    data: form.serialize(), // 从表单中获取数据
                    type: "POST",       
                    async:true
                    /*,beforeSend: function()          // 设置表单提交前方法
                    {
                    	showOverlay();
                        console.log(form.serialize());
                    },
                    error: function(request) {      // 设置表单提交出错
                        console.log("表单提交出错，请稍候再试");
                    },
                    success: function(data) {
                        console.log("post 完成");
                    }*/
                });
                //return false;
            });
       });
		

		/**
		   下载进程
		*/
	    function procSet(){
			 	showOverlay();//展示进度条
  			 	procHandler=setInterval("test()",150);//设置时钟轮询后台进程
  			 }
		/**
		   异步请求后台进度
		*/
		function test(){

			$.ajax({
				method:'post',
				async:true,
				url:'http://localhost:8080/CERP-Core/manager/core/demo/process.do',
				success:function(data){
					if (data<100){    
						$('#p').progressbar('setValue', data); 
					}else{
						hideOverlay();//上传完毕，隐藏缓冲条
						$('#p').progressbar('setValue', '100');
						clearInterval(procHandler);//清理定时器
					}  
					},
				error:function (msg) { 
						console.log("Error:"+msg);
					} 
				});
		}

	/**
	  显示缓冲遮罩
	*/
    function showOverlay(){
        $("#overlay").show();
        $("#olContent").show("slow");
        $("#p").show();
        // 禁止body的滚动条
        $(document.body).css("overflow","hidden");
        $("html").css("overflow","hidden");
    }

	/**
	 隐藏缓冲图片
	*/
    function hideOverlay(){
        $("#olContent").hide("slow");
        $("#p").hide("slow");
        $("#overlay").hide();
        $(document.body).css("overflow","auto");
        $("html").css("overflow","auto");
    }
	</script>
</body>
</html>
