<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品管理</title>
	<link id="easyuiTheme" rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/js/jquery-easyui-1.3.5/themes/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="${base}/css/save.css" type="text/css"></link>
	<script type="text/javascript" src="${base}/js/commons/jquery-1.11.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/commons/commons_manager_message.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${base}/js/commons/tools.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/gs/gsPropValues.js" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/gs/gsGoods.js" charset="utf-8"></script>
	
	<script type="text/javascript">
    window.onload = function()
    {
        CKEDITOR.replace( 'goodsDesc', {
		height : 500
		});
	};
	</script>
	<style>
	label{letter-spacing:8px;}
	th {
		width: 8%;
	}
	</style>
</head>
<body>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-add" plain="true" onclick="addGsGoods()">新增商品</a>
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-edit" plain="true" onclick="editGsGoods()">修改商品</a>	
  		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-remove" plain="true" onclick="destroyGsGoods()">删除商品</a>
    	&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" 
    		iconCls="icon-redo" plain="true" onclick="audiGsgoods()">商品提交审核</a>
	</div>
	<div>
  		<table>
  			<tr>
      			<td> 
      				商品编码：<input type="text" id="productCodeForSearch" />
        		</td>
      			&nbsp;<td> 
      				商品名称：<input type="text" id="productNameForSearch" />
        		</td>
      			&nbsp;<td> 
      				<input type="button" onclick="searchGsGoods()" value="查询" id="search" disabled />
      			</td>
      		</tr>
  		</table>
	</div>
	<table id="list" style="height:340px;" >
	</table>
	
	<div id="dlg_gsGoods" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons" style="width:1000px; height:550px; padding:5px 5px;top:10px;">
  	 <div class="easyui-tabs" style="width:930px;height:450px" id="tabs" selected>
		<!--基本信息tab开始-->
      	<div title="基础信息" style="padding:10px">
		<div class="ftitle" style="margin:20px;"></div>
  		<form id="fm" novalidate method="post" enctype="multipart/form-data">
  			<input type="hidden" id="id" name="id" />
      				<input type="hidden" id="catId" name="catId" />
      				<input type="hidden" id="state" name="state" />
      				<table class="mytable" id="mytable" cellspacing="0"> 
      						<tr>
      						<td colspan="4" style="text-align:left;">
      							<a href="javascript:saveGsGoods();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
      						</td>
      					</tr>
      					
      					<tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">商品名称：</th> 
      						<td class="shandan"><span><input name="productName"  id="productName" class="easyui-validatebox" data-options="required:true,validType:'length[2,12]'" style="width:200px;"/></span></td> 
      					</tr>
      					<tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">商品标题：</th> 
      						<td class="shandan"><span><input name="title"  id="title" class="easyui-validatebox" data-options="required:true,validType:'length[2,40]'" style="width:400px;"/></span></td> 
      					</tr>
      					<tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">商品编码：</th> 
      						<td class="shandan"><span><input name="productCode"  id="productCode" class="easyui-validatebox" data-options="required:true,validType:'length[2,12]'" style="width:200px;"/></span></td> 
      					</tr>
      					
      				   <tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">选择：</th> 
      						<td class="shandan"><span><input type="radio" name="recommend"  value="1">推荐</input>
      						<input type="radio" name="recommend"  value="2">热销</input>
      						<input type="radio" name="recommend"  value="0">未选</input>
      						</span></td> 
      					</tr>
      				
      					<tr> 
		 				 	<th class="specalt">正&nbsp;&nbsp;文</th> 
		   					<td class="shandan2" colspan="3"><textarea id="goodsDesc" name="goodsDesc" style="width:90%;height:1000px;" class="easyui-validatebox"></textarea></td> 
		  				</tr>
		  			
      				</table>
    		
  		</form>
       		</div>
       		<!--基本信息tab结束-->
       		
       		<!--图片tab开始-->
       		<div title="商品图片管理" style="padding:10px" >
        		<div id="toolbar1">
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addImg()">添加图片 </a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editImg()">修改图片</a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyImg()">删除图片</a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addImg3()">设置商品封页</a>
				</div>
				<div id="dlg5" class="easyui-dialog" closed="true" buttons="#dlg-buttons1"
					style="width: 700px; height: 350px; padding: 5px 5px; top:10px;" modal="true" >
  					<div class="ftitle" style="margin:30px;"></div>
  					<table class="mytable2" cellspacing="0"> 
  					<form id="fm3" method="post" enctype="multipart/form-data">
						<input type="hidden" id="gsGoodsId" name="gsGoodsId" />
						<input type="hidden" id="imgId" name="id" />
						<tr> 
	                   	 	<th style="height:41px;width:200px;" scope="row" abbr="L2 Cache" class="specalt">文件上传：</th>
		                    <td class="shandan1" colspan="3"> 
		                    <input id="strFileName" type="file" size="45" name="strFileName" onchange="fileChange(this);"  class="easyui-validatebox" data-options="required:true,validType:'length[1,100]'"> <br />
							 <div id="progressBar" style="display: none;"> 
	                            <div id="theMeter"> 
	                                <div id="progressBarText"></div> 
	                                <div id="progressBarBox" style="color:Silver;border-width:1px;border-style:Solid;width:300px;TEXT-ALIGN:left"> 
	                                    <div id="progressBarBoxContent" style="background-color:#3366FF; height:15px; width:0%;TEXT-ALIGN:left"></div> 
	                                </div>
	                                 <div ><font color="red">文件上传中请稍候。。。</font></div>
	                                <div id="progress-content" style="display: none;"></div>
	                            </div> 
	                    	 </div> 
							</td>
						</tr> 
						<tr> 
						 	<th style="height:41px;width:200px;" scope="row" abbr="L2 Cache" class="specalt">图片说明：</th>
							<td class="shandan1" colspan="3"> 
							  <textarea name="name" style="width:500px; height:100px;" id="alt" class="easyui-validatebox" data-options="validType:'length[0,200]'" ></textarea>
							</td>
						 </tr> 
						 <tr> 
							<td  class="shandan1"   colspan="4">
								<div style="color:red;">只可以上传jpg格式</div>
							</td>
						</tr>
					</form>
					 </table>
					<div id="dlg-buttons1">
						<a href="javascript:addImg2();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg5').dialog('close')">取消</a>
      				</div>
				</div>
				
				<table id="picture" style="height:300px;" ></table>
       		</div>
       		<!--图片信息tab结束-->
       		
       		<!--图片tab开始----------------------------------------------------------->
       		<div title="绑定商品属性" style="padding:10px" >
        		<div id="toolbar1">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="selectGsPropName()">绑定商品属性</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeGsPropName()">删除商品属性</a>
				</div>
				<div>
			  		<table>
			    		<tr>
			      			<td> 
			      				商品属性名称：<input type="text" id="propNameForSearch" />
			        			<input type="button" onclick="searchGsPropName()" value="查询" id="searchGsPropName"  />
			        		</td>
			    		</tr>
			  		</table>
				</div>
				<table id="gsKuProplist" style="height:340px;" ></table>
				
				
			     <div id="select_gsPropName" class="easyui-dialog" closed="true" buttons="#dlg-buttons1"
					style="width: 700px; height: 300px; padding: 5px 5px; top:10px;" modal="true" >
  					<div class="ftitle" style="margin:30px;"></div>
	  				<div>
				  		<table>
				    		<tr>
				      			<td>属性名称：<input type="text" id="propName" />
			        				<input type="button" onclick="searchGsPropName()" value="查询" />
			        			</td>
				    		</tr>
				  		</table>
					</div>
					<table id="list_gsPropName" style="height:220px;" ></table>
				</div>
				<div id="dlg-buttons1">
					<a href="javascript:saveGsPropName();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#select_gsPropName').dialog('close')">取消</a>
      			</div>
       		</div>
       		<!--生成商品规格tab结束-->
       		
       		<!--图片tab开始-->
       		<div title="商品规格管理" style="padding:10px" >
        		<div id="toolbar1">
        			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showNotGsSku()">添加无规格属性</a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showGsSku()">生成商品规格</a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editGsSku()">修改商品规格</a>
  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editGsSkuStatus()" id="editStatus">启用/停用</a>
				</div>
				
				<table id="gsSkulist" style="height:340px;" ></table>
				
				<div id="dlg6" class="easyui-dialog" closed="true" buttons="#dlg-buttons1" style="width: 900px; height: 450px; padding: 0px 0px; top:5px;" modal="true" >
					<div id="invalue"></div>
									
					<div id="dlg-buttons1">
						<a href="javascript:saveGsPropvalues();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg6').dialog('close')">取消</a>
	      			</div>				
				</div>
				<div id="dlg7" class="easyui-dialog" closed="true" buttons="#dlg-buttons1"
					style="width: 700px; height: 300px; padding: 5px 5px; top:0px;" modal="true" >
  					<div class="ftitle" style="margin:30px;"></div>
  					<form id="fm7" method="post" enctype="multipart/form-data">
  						<input type="hidden" id="gid" name="gid" />
  						<input type="hidden" id="gsSkuId" name="gsSkuId" />
  						<table   class="mytable2" id="mytable" cellspacing="0">
						<tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">标准价格：</th> 
      						<td class="shandan"><span><input name="price"  id="price" class="easyui-numberbox" data-options="required:true,min:0,precision:2" style="width:200px;"/></span></td> 
      					</tr>
      					<tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">标准PV：</th> 
      						<td class="shandan"><span><input name="pv"  id="pv" class="easyui-numberbox" data-options="required:true,min:0,precision:0" style="width:200px;"/></span></td> 
      					</tr>
      					
      					<tr> 
      						<th style="height:41px;" scope="row" abbr="L2 Cache" class="specalt">库存：</th> 
      						<td class="shandan"><span><input name="quantity"  id="quantity" class="easyui-numberbox" data-options="required:true,min:0" style="width:200px;"/></span></td> 
      					</tr>
						<table>
					</form>
					<div id="dlg-buttons1">
						<a href="javascript:saveNotGsSku();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg7').dialog('close')">取消</a>
      				</div>
				</div>
       		</div>
       		
       			
       		<!--生成商品规格tab结束-->
       		
    	</div>
	</div>
	
	
	
	
	<div id="select_gsGoodsType" class="easyui-dialog" closed="true"  modal="true" buttons="#dlg-buttons-gsDialog" style="width: 820px; height: 480px; padding: 0px 0px; top:5px;" >
		<div id="list_gsGoodsType" style="width: 820px; height: 480px;">
		</div>
		<div id="dlg-buttons-gsDialog">
	  		<a href="javascript:saveGsGoodsType();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	  		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#select_gsGoodsType').dialog('close')">取消</a>
		</div>
	</div>
	
	
</body>
</html>