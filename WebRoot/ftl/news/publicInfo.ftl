<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" /><title>
积客券
</title><link href="../css/public.css" rel="stylesheet" /><link href="../css/Index.css" rel="stylesheet" />
<script src="../js/commons/jquery-1.11.0.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body class="couponbg">
<div class="couponlistdiv" id="show">
<div class="couponlist">
<a href="javascript:goCouponDetails('0')">
<img src="../image/have1.png" />
<p>点击查看优惠券详情</p>
<!--<span class="h" id="zj">点击查看</span>-->
</a>
</div>
<#if auEmployeeInfo ??>
<div align="center">欢迎光临${auEmployeeInfo.realName}</div>
<i><img <#if auEmployeeInfo.shop_file_name1 ??>src="/upload/sa/${auEmployeeInfo.shop_file_name1}"<#else>src="../image/bk3.png"</#if>
 style=" background-size: 341px 282px;display: block;width:341px; height:282px;margin: 79px auto 0 auto;" /></i>
  </#if>
</div>
</body>
</html>
<script type="text/javascript">
//进入优惠券详情页面(type=0:进入自己可用的页面)(type=1:进入分享给朋友的页面)
function goCouponDetails(type) {
//location.href = 'cardInfo.do?cardId=${cardId}&secret=${secret}&appid=${appid}&recode=${recode}&openId=${openId}&type=0';
 location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=http://www.vanloon456.cn/ourun/news/cardInfo.do&response_type=code&scope=snsapi_userinfo&state=auappid=${appid}-openId=${openId}-cardId=${cardId}-recode=${recode}#wechat_redirect';
}
</script>
