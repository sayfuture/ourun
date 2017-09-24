//获取项目的URL
function getCurProjPath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return localhostPath + projectName;
}

/**
 * 时间字符串转化为固定格式 124233525252-----2014-12-04 12:22:22 yyyy-MM-dd hh:mm:ss
 */
Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
    // millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    return format;
};

//获取创建人
function getUser(id){
	var user;
	$.ajax({
		method : 'get',
		url : getCurProjPath()+'/manager/erp/au/getEmployee.do',
		data : 'id=' + id,
		dataType : "json",
		async : false,
		success : function(data) {
			user = data;
		},
		error : function(msg) {
			return '';
		}
	});
	return user;
}


//获取创建人
function getUserById(id) {
	var type;
	$.ajax({
		method : 'post',
		url : getCurProjPath() + '/manager/erp/au/getEmployeeName.do',
		data : 'id=' + id,
		dataType : "json",
		async : false,
		success : function(data) {
			type = data;
		},
		error : function(msg) {
			message_op(false, null);
		}
	});
	return type;
}
