<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/me/meMember.js" charset="utf-8"></script>
	<style>label{letter-spacing:8px;}
	</style>
</head>
<body>
	<div>
  		<table>
    		<tr>
      			<td> 
      				<!--用户名：<input type="text" id="loginNameForSearch" />&nbsp;-->
      				真实名：<input type="text" id="realNameForSearch" />
      				<!--&nbsp;启用/冻结：<select id="availabilityForSearch" class="easyui-combobox" data-options="panelHeight:'auto'" style="margin-left:1px;width:100px;height:25px;">
      							<option value="-1">忽略</option>
	      						<option value="1">启用</option>
	      						<option value="0">冻结</option>
	      					</select>-->
      				&nbsp;
      				手机号：<input type="text" id="cellphoneForSearch" />
        				
        				
        				<!--<select  class="easyui-combobox" id="auName" name="auName"  style="margin-left:1px;width:100px;height:25px;" 
						 data-options="url:'../../getTypecombobox.do?code=buildType&type=1',
							valueField:'id',
							textField:'valuename',
							panelHeight:'auto',
							onLoadSuccess:function(data){ $('#auName').combobox('setValue',data[0].id);}"
							"/>-->
									
        			&nbsp;<input type="button" onclick="search()" value="查询" id="search"  />
        		</td>
    		</tr>
  		</table>
	</div>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton " 
    		iconCls="icon-edit" plain="true" onclick="addObj()">新增店员 </a>
  		<a href="javascript:void(0)" class="easyui-linkbutton " 
    		iconCls="icon-edit" plain="true" onclick="editObj()">修改店员 </a>
  		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyObj()">删除店员</a>
    	<!--<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="setAvailability()">启用/冻结会员</a>-->
       <!-- <a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="details()">查看会员详情</a>--> 		
	</div>
	
	<table id="list" style="height:410px;" ></table>
		
	<div id="dlg" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:1000px; height:300px; padding:5px 5px;top:20px;">
  		<div class="ftitle"></div>
  		<form id="fm" novalidate method="post">
  			<input type="hidden" id="id" name="id" />
  			<input type="hidden" id="activity" name="activity" />
  			<input type="hidden" id="password" name="password" />
  			<input type="hidden" id="state" name="state" />
  			<input type="hidden" id="availability" name="availability" />
  			<input type="hidden" id="score" name="score" />
  			
  			<input type="hidden" id="loginName" name="loginName" />
  			<input type="hidden" id="headShowPath" name="headShowPath" />
  			<input type="hidden" id="emailBinding" name="emailBinding" />
  			<input type="hidden" id="cellphoneBinding" name="cellphoneBinding" />
  			<input type="hidden" id="registerMethod" name="registerMethod" />
  			
  			
  			<table class="mytable2" id="mytable" cellspacing="0"> 
 				 <tr> 
  					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">真&nbsp;&nbsp;&nbsp;实&nbsp;&nbsp;&nbsp;名</th> 
   					<td class="shandan"><span><input name="realName"  id="realName" class="easyui-validatebox" data-options="required:true,validType:'length[2,6]'" style="width:200px;"/></span></td>
   					
   					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">手&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;号</th> 
   					<td class="shandan"><span><input name="cellphone"  id="cellphone" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'" style="width:200px;height:25px;" /></span></td>  
  				</tr>
  				<tr>
  					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th> 
   					<td class="shandan">
	   					<span>
	   						<select name="sex" id="sex"  class="easyui-combobox" data-options="required:true,panelHeight:'auto'" style="margin-left:1px;width:200px;height:25px;">
	      						<option value="1">男</option>
	      						<option value="0">女</option>
	      					</select>
	      				</span>
      				</td>
      				<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">身&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</th> 
      				<td class="shandan">
	   					<span>
	   						<select name="identity" id="identity"  class="easyui-combobox" data-options="required:true,panelHeight:'auto'" style="margin-left:1px;width:200px;height:25px;">
	      						<option value="1">临时</option>
	      						<option value="2">在职</option>
	      						<option value="3">离职</option>
	      						<option value="0">其它</option>
	      					</select>
	      				</span>
      				</td>  
  				</tr>
  				<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">生&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</th> 
   					<td class="shandan"><span><input name="birthday"  class="easyui-datebox" id="birthday"   style="margin-left:-1px;width:200px;height:25px;"  editable="false"/></span></td>
  					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">个&nbsp;人&nbsp;情&nbsp;况</th> 
   					<td class="shandan">
	   					<span>
	   						<select name="maritalState" id="maritalState"  class="easyui-combobox" data-options="panelHeight:'auto'" style="margin-left:1px;width:200px;height:25px;">
	      						<option value="1">单身</option>
	      						<option value="2">恋爱</option>
	      						<option value="3">已婚</option>
	      						<option value="0">保密</option>
	      					</select>
	      				</span>
      				</td>
  				</tr>
    			<tr> 
 				 	<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</th> 
   					<td class="shandan"><span><input name="email" id="email" class="easyui-validatebox" data-options="validType:'length[1,50]'" validType="email" invalidMessage="请填写正确的邮件格式" style="width:200px;height:25px;"  maxLength="50" /></span></td>
  					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">兴&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;趣</th> 
   					<td class="shandan">
	   					<span>
	   						<select name="interest" id="interest"  class="easyui-combobox" data-options="panelHeight:'auto'" style="margin-left:1px;width:200px;height:25px;">
	      						<option value="1">美食</option>
	      						<option value="2">娱乐</option>
	      						<option value="3">购物</option>
	      						<option value="4">旅游</option>
	      						<option value="5">酒店</option>
	      						<option value="6">交通</option>
	      						<option value="7">教育</option>
	      						<option value="0">其它</option>
	      					</select>
	      				</span>
      				</td>
  				</tr>
  				<tr>
  					<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">工&nbsp;&nbsp;&nbsp;&nbsp;号</th> 
   					<td class="shandan"><span><input name="user_id" id="user_id" class="easyui-validatebox" data-options="required:true,validType:'validatestr'"   style="width:200px;height:25px;" /></span></td>
  				</tr>
  				<tr>
  					<td colspan="4" style="text-align:right;">
  						<a href="javascript:saveObj();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  						<a href="javascript:void(0)" class="easyui-linkbutton" 
        				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
        			</td>
  				</tr>
  			</table>
    		<div style="color:red; margin-top:10px;">
    			<span style="color:#666;">友情提示：默认初始密码是</span>'111111'
    		</div>
  		</form>
	</div>
	
		<!--会员详情弹出框-->
	<div id="dlg2" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:800px; height:400px; padding:30px 30px;top:120px;">
  		<div class="ftitle"></div>			
  			<table class="mytable2" id="mytable2" cellspacing="0"> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail"  id="memberDetail" style="width:200px;"/></span></td> 
	   	  </tr>
	      <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">真&nbsp;&nbsp;&nbsp;实&nbsp;&nbsp;&nbsp;名</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail2"  id="memberDetail2" style="width:200px;"/></span></td> 
	   	  </tr>
	      <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">性&nbsp;&nbsp;&nbsp;&nbsp;别</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail3"  id="memberDetail3" style="width:200px;"/></span></td> 
	   	  </tr>
          <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">生&nbsp;&nbsp;&nbsp;&nbsp;日</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail4"  id="memberDetail4" style="width:200px;"/></span></td> 
	   	  <tr>
	   	   <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">手&nbsp;&nbsp;&nbsp;&nbsp;机</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail5"  id="memberDetail5" style="width:200px;"/></span></td> 
	   	  </tr>
	   	  <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;&nbsp;&nbsp;&nbsp;箱</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail6"  id="memberDetail6" style="width:200px;"/></span></td> 
	   	  </tr>
          <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">身&nbsp;&nbsp;&nbsp;&nbsp;份</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail7"  id="memberDetail7" style="width:200px;"/></span></td> 
	   	  </tr>
          <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">个&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;情&nbsp;&nbsp&nbsp;况</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail8"  id="memberDetail8" style="width:200px;"/></span></td> 
          </tr>
	      <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">兴&nbsp;&nbsp;&nbsp;&nbsp;趣</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail9"  id="memberDetail9" style="width:200px;"/></span></td> 
	   	  </tr>
           <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">激&nbsp;&nbsp;&nbsp;活&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp&nbsp;态</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail10"  id="memberDetail10" style="width:200px;"/></span></td> 
          </tr>
          <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">启&nbsp;用&nbsp;&nbsp;/&nbsp;&nbsp;冻&nbsp;结</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail11"  id="memberDetail11" style="width:200px;"/></span></td> 
          </tr>   
       	  <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">积&nbsp;&nbsp;&nbsp;&nbsp;分</th> 
	   			<td class="shandan"><span><input disabled="disabled" name="memberDetail12"  id="memberDetail12" style="width:200px;"/></span></td> 
	   	  </tr>
           <tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">会&nbsp;&nbsp;&nbsp;员&nbsp;&nbsp;&nbsp;级&nbsp;&nbsp;&nbsp;&nbsp;别</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail13"  id="memberDetail13" style="width:200px;"/></span></td> 
	   	  </tr>


 			<tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">头&nbsp;&nbsp;&nbsp;像&nbsp;&nbsp;&nbsp;路&nbsp;&nbsp;&nbsp;&nbsp;径</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail14"  id="memberDetail14" style="width:200px;"/></span></td> 
	   	  </tr>

 			<tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">邮&nbsp;&nbsp;&nbsp;箱&nbsp;&nbsp;&nbsp;绑&nbsp;&nbsp;&nbsp;&nbsp;定</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail15"  id="memberDetail15" style="width:200px;"/></span></td> 
	   	  </tr>

 			<tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">手&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;绑&nbsp;&nbsp;&nbsp;&nbsp;定</th>  
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail16"  id="memberDetail16" style="width:200px;"/></span></td> 
	   	  </tr>

 			<tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">注&nbsp;&nbsp;&nbsp;册&nbsp;&nbsp;&nbsp;方&nbsp;&nbsp;&nbsp;&nbsp;式</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail17"  id="memberDetail17" style="width:200px;"/></span></td> 
	   	  </tr>

 			<tr> 
	 			<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">验&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;&nbsp;方&nbsp;&nbsp;&nbsp;&nbsp;式</th> 
	   			<td  class="shandan"><span><input disabled="disabled" name="memberDetail18"  id="memberDetail18" style="width:200px;"/></span></td> 
	   	  </tr>
  			</table>
	</div>
	
</body>
</html>