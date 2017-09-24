<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>积客券</title>
	<link href="../css/public.css" rel="stylesheet" />
	<link href="../css/Index.css" rel="stylesheet" />
<script src="../js/commons/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	        function share() {
        }
        $(function () {

                $("#div_heade").css('background-color', '#08B343');
                $("#a_show").text('赠送给朋友');
                $("#div_qrcode").css('display', 'none');
                $("#div_share").css('display', 'block');

            jsapi.init(window.location.href.split('#')[0], "", function () {
                share();
            });
             
        });
	  $(function () {
        $("#div_qrcode").css('left', (document.body.clientWidth - 200) / 2);
        $("#div_share").css('left', (document.body.clientWidth - 200) / 2);
        var dlink = $.trim($("#dlinks").val());
        if (dlink.length == 0) {
            $("#dlinkImg").hide();
        } else {
            $("#dlinkImg").show();
        }
    });
    function showMask() {
        $("#mask1").fadeIn(200);
        share();
    }
    function hidenMask() {
        $("#mask1").fadeOut(200);
    }
    function goDetails() {
        if ($("#dlinks").val() == "" || $("#dlinks").val() == null) {
            if ($("#couponsetid").val() != '') {
                location.href = "CouponXQ.aspx?couponsetid=" + $("#couponsetid").val();
            }
        }
        else {
            location.href = $("#dlinks").val();
        }

    }
    function showAddress(obj) {
        alert(obj.text());
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }

	


</script>
</head>
<body class="h">

    <div id="mask1" style="display: none;">
        <div style="top: 0; left: 0; width: 100%; height: 100%; background: #000; opacity: 0.8; filter: alpha(opacity=80); z-index: 9; position: fixed;" onclick="hidenMask()">
        </div>

        <div id="div_share" style="margin: auto; z-index: 10; position: absolute; clear: both; top: 25%; width: 200px; height: 200px;">
            <div style="height: 50px"></div>
            <div style="text-align: center; color: #FFF; font-size: 16px;">
                <#if share_card_num lt 1> 
					已分享完毕不能再分享
                  <#else>   
                <span>1、点击右上角按钮</span><br />
                <span>2、选择发送给朋友</span>    
                   </#if>  
            </div>
        </div>
    </div>
    
    <div class="linqu" id="div_heade" style="background-color: rgb(8, 179, 67);">
    <#if diCard ??>
        <p>${diCard.card_name}</p>
        <div class="linquct">
            <em>￥${diCard.card_worth}</em>
            <a href="javascript:showMask()" id="a_show">分享给朋友</a>
            <p class="limg0">有效期至：${endtime}</p>
        </div>
    </div>
    <div class="liqua">
        
        <h2>使用说明: ${diCard.use_explain}</h2>
        
        <div class="liquimg">
            
            <div id="dlinkImg" onclick="goDetails()" style="width: 55px; height: 58px; left: 50%; top: 50%; margin-left: -30px; margin-top: -28px; position: absolute; z-index: 1; background-image: url('../image/serverft3.png');">
            </div>
            
            <input type="hidden" id="couponsetid" value="" />
            <input type="hidden" id="dlinks" value="https://mp.weixin.qq.com/s/awc79ElEqQSQ6R2SM_Gicg" />
            <a href="javascript:goDetails()" class="liquimgas">
                
                <img src="/upload/co/${diCard.coContent.fileUrl}" />
                
                <p>查看详情  <span>></span></p>
            </a>
        </div>
</#if>
         <#if auEmployee ??>
        <a href="#">
            <p style="width: 85%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: block; float: left;">
       	  店名：${auEmployee.realName}
            </p>
            <i class="liquone"></i>
        </a>
        <a href="tel:// ${auEmployee.tel}">
            <p style="width: 85%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: block; float: left;">
                <p style="width: 85%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: block; float: left;">联系电话：${auEmployee.tel}</p>
                <i class="liqutwo"></i>
        </a>
         <a href="#">
            <p style="width: 85%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: block; float: left;">
         	店铺地址：${auEmployee.address}
            </p>
            <i class="liquone"></i>
        </a>
    </div>
    </#if>
    <div style="height: 20px;"></div>

</body>
</html>
<script type="text/javascript">
//进入优惠券详情页面(type=0:进入自己可用的页面)(type=1:进入分享给朋友的页面)
function goCouponDetails(type) {
//if (type == '0' && parseInt(getQueryString('couponNumber'))>0) {
if (type == '0' && parseInt("1") > 0) {
location.href = 'CouponDetial.do?couponId=4984&openid=oPz4pwggfnyz46ElDZyZ4k9qdufU&csid=8888&guid=a677c5f1-f4bc-4f65-ab0d-130d3f5b34ff&type=0';
}
//else if (type == '1' && parseInt(getQueryString('shareSum')) > 0) {
else if (type == '1') {
location.href = 'CouponDetial.aspx?couponId=4984&openid=oPz4pwggfnyz46ElDZyZ4k9qdufU&csid=8888&guid=a677c5f1-f4bc-4f65-ab0d-130d3f5b34ff&type=1';
}
}
</script>
