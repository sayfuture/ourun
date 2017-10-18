<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>店铺管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/au/auEmployee.js" charset="utf-8"></script>
	
</head>
<body>
	<input type="hidden" id="loginId" value="${auEmployee.id}">
		<div>
  		<table>
    		<tr>
      			<td> &nbsp;人员真实姓名：<input type="text" id="name" style="margin-top:2px;"/></span>
      			&nbsp;所属机构：<select id="auName" class="easyui-combobox" name="auId" style="width:200px;"  data-options="panelHeight:'auto'"></select>
        			&nbsp;<input type="button" onclick="search()" value="查询" id="search"/>
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="newObj()">添加店铺账号</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editObj()">修改店铺账号</a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj()">删除店铺账号</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-reload" plain="true" onclick="updatePassword()">修改密码</a>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:1000px; height:380px; padding:5px 5px;top:5px;">
  		<div class="ftitle"></div>
  		<form id="fm" novalidate method="post" enctype="multipart/form-data">
  			<input type="hidden" id="id" name="id" />
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">登&nbsp;&nbsp;&nbsp;录&nbsp;&nbsp;&nbsp;名</th> 
   					<td class="shandan"><span><input name="loginName"  id="loginName" class="easyui-validatebox" data-options="required:true,validType:'length[1,12]'" style="width:200px;"/></span></td> 
   					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">店&nbsp;铺&nbsp;名&nbsp;称</th> 
   					<td class="shandan"><span><input name="realName"  id="realName" class="easyui-validatebox" data-options="required:true,validType:'length[2,12]'" style="width:200px;height:25px;" /></span></td> 
  				</tr>
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</th> 
   					<td class="shandan"><span><input name="age"  id="age" class="easyui-numberbox" min="1" max="100" missingMessage="必须填写1~100之间的数字" data-options="required:true,validType:'length[1,3]',digits:true" style="margin-left:-1px;width:200px;height:25px;" /></span></td>
   					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th> 
   					<td class="shandan">
   					<span>
   						<select class="easyui-combobox" name="sex" id="sex"  data-options=" editable:false,required:true,panelHeight:'auto'" style="margin-left:1px;width:200px;height:25px;">
      						<option value="1">男</option>
      						<option value="0">女</option>
      					</select>
      				</span>
      		<!--	<select  class="easyui-combobox" id="auName1" name="auName1"  style="margin-left:1px;width:100px;height:25px;" 
						 data-options="url:'../../getTypecombobox.do?code=buildType&type=1',required:true,
							valueField:'id',
							textField:'valuename',
							panelHeight:'auto',
							onLoadSuccess:function(data){ $('#auName1').combobox('setValue',data[0].id);}"
							"/> -->
      				</td> 
  				</tr>
    			<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</th> 
   					<td class="shandan"><span><input name="email" id="email" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'" validType="email" invalidMessage="请填写正确的邮件格式" style="width:200px;height:25px;"  maxLength="50" /></span></td>
   					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">设&nbsp;定&nbsp;&nbsp;岗&nbsp;位</th> 
   					<td class="shandan">
   						<span>
   							<select id="roleId" class="easyui-combobox" name="positionId" style="width:300px;height:25px;" data-options="panelHeight:'auto',required:true"></select>
      					</span>
      				</td> 
  				</tr>
  				<tr> 
      				<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">设定所属分公司</th> 
   					<td class="shandan">
   						<span>
   						<input type="hidden" name="deptId" id="deptId">
   							<input type="text" id="seldeptId"  class="easyui-validatebox"  readonly style="width:200px;background-color: #B2DFEE;cursor:pointer;" data-options="required:true"  ondblclick="checkDept()" />
      					</span>
      				</td>
      				<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">手&nbsp;机&nbsp;号&nbsp;</th> 
   					<td class="shandan"><span><input name="tel"  id="tel" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'" style="width:200px;height:25px;" /></span></td>  
  				</tr>
  			<!--	<tr>
  					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">微信appid</th> 
   					<td class="shandan">
   						<span>
   						<input  class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'" style="width:200px;height:25px;" name="appid" id="appid">
      					</span>
      				</td>
      				<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">微信appsecret</th> 
   					<td class="shandan"><span>
   					<input name="appsecret"  id="appsecret" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'" style="width:200px;height:25px;" /></span></td>  
  				</tr>-->
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">店&nbsp;铺&nbsp;图&nbsp;片</th> 
   					<td class="shandan">
   					<img id="preview1" pid="" />
   					<span>
   					<input id="shop_file_name" idm="preview1" type="file" size="25" name="shop_file_name" onchange="javascript:setImagePreview(this);"  data-options="required:true,validType:'length[1,100]'"></input>
   					</span></td>  
  				    
  				  <!--  <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">微&nbsp;信&nbsp;号</th>
   					<td class="shandan"><span>
   					<input name="wxname"  id="wxname" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'" style="width:200px;height:25px;" /></span></td>  -->
  				</tr>
  				<tr>
  				  <th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">店&nbsp;铺&nbsp;地&nbsp;址</th> 
   					<td class="shandan"><span>
   					<input name="address"  id="address" class="easyui-validatebox" data-options="required:true,validType:'length[1,50]'" style="width:200px;height:25px;" /></span></td>  
  				</tr>
  				<tr>
  					<td colspan="4" style="text-align:right;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
    		<div style="color:blue; margin-top:10px;">
    			友情提示：默认初始密码是'111111'
    		</div>
  		</form>
	</div>

	<table id="list" style="height:430px;" >
	</table>
	
	<div id="dlg2" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:1000px; height:470px; padding:1px 1px;top:1px;" >
		<form id="fm2" novalidate method="post">
			<input type="hidden" id="emId" name="employeeId"/>
			<table id="list2" style="height:360px;" ></table>
			<div style="color:blue; margin-top:4px;">
	    			友情提示：请认真选择，以免出错！
	    	</div>
		</form>
			<div id="dlg-buttons">
  				<a href="javascript:saveUser2();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  				<a href="javascript:void(0)" class="easyui-linkbutton" 
        		iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
			</div>
	</div>
	
	<div id="dlg3" class="easyui-dialog" closed="true"  modal="true"style="width: 750px; height: 430px; padding: 20px 20px;top:2px;" >
  		<div class="ftitle"></div>
  		<form id="uppassword" novalidate method="post">
  			<input type="hidden" name="id" id="pas" />
    		<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">真实姓名</th> 
   					<td class="shandan"><span id="rName"></span></td> 
  				</tr>
  				 <tr> 
    				<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">登录名</th> 
   					<td class="shandan"><span id="lName"></span></td> 
  				</tr>
  				 <tr> 
    				<th scope="row" abbr="L2 Cache" class="specalt">新密码</th> 
   					<td class="shandan"><input name="password1" type="password" id="password" class="easyui-validatebox" data-options="required:true,validType:'length[6,12]'" style="width:200px;" /></td> 
  				</tr>
  				 <tr> 
    				<th scope="row" abbr="L2 Cache" class="specalt">确认密码</th> 
   					<td class="shandan"><input name="password2" type="password" id="password2" class="easyui-validatebox" data-options="required:true,validType:'length[6,12]'" style="width:200px;" /></td> 
  				</tr>
  				<tr>
  					<td colspan="2" style="text-align:right;">
  						<a href="javascript:updatePassword2();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg3').dialog('close')">取消</a>
        			</td>
  				</tr>
  				</table>
  		</form>
	</div>
	<div id="dlg_dept" class="easyui-dialog" closed="true"  modal="true" buttons="#buttons"	style="width: 580px; height: 480px; padding: 5px 5px; top:5px;" >
   				<div class="ftitle" style="margin:5px;"></div>
   				<table id="listDept" style="height:400px; width:520px;">
   				
   				</table>
   				
				<div style="float:right;">
  					<a href="javascript:void(0)" class="easyui-linkbutton" 
        					iconCls="icon-cancel" onclick="javascript:$('#dlg_dept').dialog('close')">取消</a>
   				</div>
   				<div style="float:right;">
  					<a href="javascript:saveDept();" class="easyui-linkbutton" iconCls="icon-ok" id="queding" >确定</a>
   				</div>
   			<div style="color:blue; margin-top:4px;">
	    			友情提示：请双击选择部门！
	    	</div>
   	</div>
   	<div id="dlg_company" class="easyui-dialog" closed="true"  modal="true" buttons="#buttons"	style="width: 580px; height: 480px; padding: 5px 5px; top:5px;" >
   				<div class="ftitle" style="margin:5px;"></div>
   				<table id="listCompany" style="height:400px; width:520px;">
   				
   				</table>
   				
				<div style="float:right;">
  					<a href="javascript:void(0)" class="easyui-linkbutton" 
        					iconCls="icon-cancel" onclick="javascript:$('#dlg_company').dialog('close')">取消</a>
   				</div>
   				<div style="float:right;">
  					<a href="javascript:saveDept();" class="easyui-linkbutton" iconCls="icon-ok" id="sure" >确定</a>
   				</div>
   			<div style="color:blue; margin-top:4px;">
	    			友情提示：请选择分公司！
	    	</div>
   	</div>
   <div id="dBWork" class="easyui-dialog" closed="true"  modal="true"  style="width:600px; height:200px; padding:5px 5px;top:100px;">
  		
  		<span id="workMsg" style="font-size:18px;"></span>
  			
    </div>
   	<form id="choiceDept" method="post">
   		<input type="hidden" name="deptId" id="deptIds"/>
   		<input type="hidden" name="employeeId" id="employeeId"/>
   	</form>
   	
   	
</body>
</html>