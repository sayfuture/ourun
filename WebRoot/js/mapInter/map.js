/**
 * Created by Administrator on 2016/1/10.
 */
Window.BDMap = null;
Window.mapOptions = {
    center:null,//地图中心点
    cityName:null,//城市名
    zoom:15,// 地图级别
    mapDomId:null,//地图显示的domId
};

var getCurrentMap = function(map){
    return map==null?Window.BDMap:map;
};

var findHouseByPoint = function(centerPoint,range,map){
    var currentMap = getCurrentMap(map);
};

/**
 * 初始化地图
 * @param mapOptions
 */
var initMap = function(mapOptions){
    mapOptions = $.extend({},Window.mapOptions,mapOptions);
    var map = new BMap.Map(mapOptions.mapDomId);
    initCenter(mapOptions,map);
    map.enableScrollWheelZoom();
    map.setMaxZoom(19);
    map.setMinZoom(12);
    Window.BDMap = map;
    return map;
};

/**
 * 设置地图中心 mapOptions参见Window.mapOptions
 */
var initCenter  = function(mapOptions,map){
    var currentMap = getCurrentMap(map);
    if(mapOptions.cityName!=null&&mapOptions.cityName!=""){
        currentMap.centerAndZoom(mapOptions.cityName,mapOptions.zoom);
    }else if(mapOptions.center !=null){
        currentMap.centerAndZoom(new BMap.Point(mapOptions.center.longitude,mapOptions.center.latitude),18);
    }else{
        localCenter(currentMap,mapOptions.zoom);
    }
};

/**
 * 获取当前的位置
 * @returns
 */
var localCenter = function(map,zoom) {
    var currentMap = getCurrentMap(map);
    if(navigator.userAgent.toLowerCase().indexOf("qqbrowser")>-1){
        var myCity = new BMap.LocalCity();
        myCity.get(function(city){
            currentMap.centerAndZoom(city.center,zoom);
        });
    }else{
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (r) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                currentMap.centerAndZoom(r.point,zoom);
            }else{
                var myCity = new BMap.LocalCity();
                myCity.get(function(city){
                    currentMap.centerAndZoom(city.center,zoom);
                });
            }
        }, {enableHighAccuracy: true});
    };
};

/**
 * 计算当前地图显示范围
 * @param map
 * @returns {number} 中心距边的实际距离
 */
var calculatorMapArea = function(map){
    var currentMap = getCurrentMap(map);
    var bounds = currentMap.getBounds();
    var leftPoint = new BMap.Point(bounds.getSouthWest().lng,bounds.getCenter().lat);
    return Math.ceil(currentMap.getDistance(bounds.getCenter(),leftPoint))
};



/*关键词自动提示 suggestInputId-需要自动提示的domId*/
var registerSuggestKey = function(suggestInputId,map,callBack){
    var currentMap = getCurrentMap(map);
    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
        {"input" : suggestInputId,
            "location" : currentMap
        });
    ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        $("#"+suggestInputId).innerHTML = str;
    });

    var myValue;
    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        $("#"+suggestInputId).innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
        setPlace();
    });

    function setPlace(){
        currentMap.clearOverlays();    //清除地图上所有覆盖物
        function myFun() {
            if (callBack != null) {
                callBack(local.getResults().getPoi(0));
            } else {
                var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                currentMap.centerAndZoom(pp, 18);
                currentMap.addOverlay(new BMap.Marker(pp));    //添加标注
            }
        }
        var local = new BMap.LocalSearch(currentMap, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);
    }
};

var mapSearchByPoint=function(point,keyword,callBack,range,num,map){
    range = range==null?1500:range;
    num = num==null?15:num;
    var currentMap = getCurrentMap(map);
    var local = new BMap.LocalSearch(currentMap,{
        onSearchComplete:callBack,
    });
    local.setPageCapacity(num);
    local.searchNearby(keyword,point,range);
};

var mapSearchByAddress = function(inputId,buttonId,showId,callBack,map){
    var currentMap = getCurrentMap(map);
    var local = new BMap.LocalSearch(currentMap,{
        renderOptions:{map:currentMap,autoViewport:true,selectFirstResult:true,panel:showId},
        onSearchComplete:function(data){
            if(callBack!=null){
                callBack(data);
            }
        },
    });
    local.setPageCapacity(5);
    $("#"+buttonId).click(function(){
        var address = $("#"+inputId).val();
        local.search(address,{forceLocal:true});
    });
};
