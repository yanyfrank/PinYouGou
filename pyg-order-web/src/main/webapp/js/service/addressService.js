//服务层
app.service('addressService',function($http){
	    	
	//查询订单收货人地址列表
	this.findAddressList=function(){
		return $http.get('../address/findAddressList');
	};

});
