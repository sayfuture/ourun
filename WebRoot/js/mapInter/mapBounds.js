/**
 * Created by oshao on 2016/1/21.
 */

//    $(function(){
//        getCoordinate("allmap","suggestId","sure","showArea","天津",function(point){
//            $("#x").html(point.lat);//
//            $("#y").html(point.lng);
//        },function(address){
//            console.log(address);
//        });
//    });
/**
 *地图搜索坐标
 * @param mapId  地图容器domID  *
 * @param suggestId 搜索提示domID *
 * @param buttonId 按钮domID 参数 
 * @param showId 显示查询信息domID 
 * @param cityName 初始地图中心城市 例如：天津 *
 * @param pointBack 坐标点结果回调函数 参数 point{lat:"经度",lng:"纬度"}
 * @param addressBack 坐标地址回调函数 参数 address 详细地址
 * @param longitude 经度 参数 
 * @param latitude 纬度 参数 
 */
var getCoordinate = function(mapId,suggestId,buttonId,showId,cityName,pointBack,addressBack,longitude,latitude){
	var map="";
	if(cityName!=null&&cityName!=""){
		 map = initMap(
				 {
					 cityName:cityName,//城市名
				     center:{longitude:"",latitude:""},
	                 zoom:12,// 地图级别
	                 mapDomId:mapId,//地图显示的domId
	    });
	}
	if(longitude!= "" && latitude!= ""){
		 map = initMap({//cityName:cityName,//城市名
			center:{longitude:longitude,latitude:latitude},
	        zoom:12,// 地图级别
	        mapDomId:mapId,//地图显示的domId
	    });
	}
	
    var geoc = new BMap.Geocoder();
    map.addEventListener("click", function(event){
        var point = event.point;
        map.centerAndZoom(point, 18);
        map.addOverlay(new BMap.Marker(point));
        pointBack(point);
        geoc.getLocation(point, function(rs){
            var address = rs.address;
            /*var address = addComp.province + addComp.city +addComp.district+ addComp.street+ addComp.streetNumber;*/
            $("#"+suggestId).val(address);
            if(addressBack!=null){
                addressBack(address);
            }
        });
        var allOverlay = map.getOverlays();
    		for (var i = 0; i < allOverlay.length -1; i++){
    			if(allOverlay[i]){
    				map.removeOverlay(allOverlay[i]);
    			}
    		}
    });
	if(longitude!= "" && latitude!= ""){
		map.clearOverlays(); 
		var new_point = new BMap.Point(longitude,latitude);
		var marker = new BMap.Marker(new_point);  // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
		map.panTo(new_point);  
		geoc.getLocation(new_point, function(rs){
            var address = rs.address;
            $("#"+suggestId).val(address);
        });
	}
	 mapSearchByAddress(suggestId,buttonId,showId,null,map);
	
};
/**
 *切换城市
 * @param cityName 要切换的城市名称 *
 */
var changCity = function(cityName){
    var map = getCurrentMap();
    if(map!=null){
        map.map.centerAndZoom(cityName, 12);
    }
};

function theLocation(x,y){

}


