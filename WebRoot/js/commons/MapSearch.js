/**
 * Created by oshao on 2016/1/6.
 */
    /*引入<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=1dUvPWzfEuh4mb8XmAYHsEY4"></script>
     $(function(){
     new MapSearch(117.181286,39.129103,function(result){
     alert(result.中学);
     });
     });
    */

var searchResultMap = {};
var searchCount = 0;
var searchTimer = null;
/*
    lng  经度
*   lat 纬度
*   showBack 回调函数 function(r){r为结果，是一个javascript对象}
*   area 搜索的最大范围
*   maxNum 单条搜索最大数量
* */
var MapSearch = function(lng,lat,showBack,area,maxNum){
	mapSearchBykey(lng,lat,area==null?1500:area,maxNum==null?30:maxNum);
    if(showBack==null){
      showBack = function(r){
          console.log(r);
      };
    };
    searchTimer = setInterval("searchRun("+showBack+")",2000);
};

var searchRun=function(callBack){
    if(searchCount<7){
        console.log("waiting!!!---"+searchCount);
    }else{
        clearInterval(searchTimer);
        callBack(searchResultMap);
    };
};


var mapSearchBykey=function(x,y,area,num){
    $("body").append("<div id='hiddenMap' style='display:none'></div>");
    var map = new BMap.Map("hiddenMap");
    var point = new BMap.Point(x,y);
    map.centerAndZoom(point,15);
    searchResultMap={};
    searchCount = 0;
    var local = new BMap.LocalSearch(map,{
        onSearchComplete:function(results){
            var value = "";
            for (var i = 0; i < results.getCurrentNumPois(); i++) {
                if(i!=0){
                    value+=",";
                };
                value+=results.getPoi(i).title;
            };
            console.log(results.keyword+"-"+results.getCurrentNumPois());
            searchResultMap[results.keyword] = value;
            searchCount++;
        }
    });
    local.setPageCapacity(num);

    local.searchNearby("中学",point,area);
    local.searchNearby("小学",point,area);
    local.searchNearby("幼儿园",point,area);
    local.searchNearby("医院",point,area);
    local.searchNearby("公交站",point,area);
    local.searchNearby("公园",point,area);
    local.searchNearby("商圈",point,area);
};