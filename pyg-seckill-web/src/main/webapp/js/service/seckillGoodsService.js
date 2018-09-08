//服务层
app.service('seckillGoodsService',function($http){
	    	
	//查询秒杀商品数据，在秒杀频道页进行展示
	this.findSeckillGoodsList=function(){
		return $http.get('../seckillGoods/findSeckillGoodsList');
	}

	this.findOne=function (id) {
		return $http.get('../seckillGoods/findOne/'+id);
    }


});
