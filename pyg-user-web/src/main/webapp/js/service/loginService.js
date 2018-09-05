//服务层
app.service('loginService',function($http){
	    	
	//实现发送消息，获取短信验证码
	this.loadLoginName=function(){
		return $http.get('../login/loadLoginName');
	}

});
