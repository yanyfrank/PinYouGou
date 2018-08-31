//服务层
app.service('userService',function($http){
	    	
	//实现发送消息，获取短信验证码
	this.sendSms=function(phone){
		return $http.get('../user/sendSms/'+phone);
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../user/findPage/'+page+'/'+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../user/findOne/'+id);
	}
	//增加 
	this.add=function(entity,smsCode){
		return  $http.post('../user/add/'+smsCode,entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../user/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../user/delete/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../user/search/'+page+"/"+rows, searchEntity);
	}    	
});
