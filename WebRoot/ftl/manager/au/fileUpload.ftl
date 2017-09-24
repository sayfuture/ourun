<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>test</title>
	
</head>
<body>
	<form method="POST" enctype="multipart/form-data" /*onsubmit="procSet()"*/
		action="${base}/manager/demo/upload.do">
		File to upload: <input type="file" id="uploadify" name="upfile">
		<input type="submit" value="Submit"> to upload the file!
	</form>

	<script type="text/javascript"> 
			 /*$(function() {
			 	var form=$('#form1');
    			$("#uploadify").uploadify({
        				'height':30,
        				'method':'post',
        				'debug': true,
        				'uploadLimit' : 1,
        				'fileTypeExts':'*.txt;*.gif;*.jpg;*.png',
        				'fileSizeLimit' : '100KB',
        				'swf':'${base}/js/uploadify/uploadify.swf',
        				'uploader':'${base}/manager/demo/upload.do',
        				'width':120
    				});
			});*/
	</script>
</body>
</html>