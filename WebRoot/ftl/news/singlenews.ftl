<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>${coContent.title !''}</title>

<meta name="keywords" content="欧润">
<meta name="toTop" content="true">

<script type="text/javascript" src="${base}/js/news/singlenews.js"></script>
<script type="text/javascript">
        $(function(){
            $(".similar .list ul li").hover(function(){
                $(this).find(".price").addClass("on");
            },function(){
                $(this).find(".price").removeClass("on");
            });
        });
</script>
</head>

<body>

<div class="content overfloatH" style="position:relative;">
	<div class="lcon">
    	<div id="maintext_height" class="maintext">
    	<#if coContent ??>  
            <h1>${coContent.title}</h1>
            <p class="essay_author"> ${coContent.createDate}  来源：欧润商贸</p>
            <div class="mtcomment" id="news_body">
                <p>${coContent.body}</p>
            </div>
			<p class="lfloat colororange fontsize14">文章关键词：&nbsp;<span>${coContent.keywords !''}</span></p>
			
            <div class="operate overfloatH">
	
				<span class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a></span>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{},"":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"24"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
				
            	<div class="noused rfloat fontsize14" id="zhishixfxq_B05_02"><a href="http://localhost:8080/a/city/news/${coContent.id}.html" download="新闻">保存网页</a>
				</div>
            	
        	</div>
        	<#else>  </#if>
        </div>
        <div class="related">
        	<h1>相关文章</h1>
            <ul>
            <#list content as content>
				<#if (content_index<3)>
	            	<li onclick="relContent('${content.id}')">
	                	<#if content??>
	                	<div class="imgbox lfloat"><img src="${base}/upload/co/${content.fileUrl}"></div>
	                    <div class="textbox lfloat">
	                    <h2>${content.title}</h2>
	                    <p>${content.introduce}....<span>[详情]</span></p>
	                    </div>
	                    </#if> 
	                </li>
            	</#if>
            </#list>
            </ul>
        </div>
    </div>
</body>
</html>
