 //控制层 
app.controller('contentController' ,function($scope,contentService){
	

	//定义数组，储存页面广告数据
	$scope.catList = [];

    //根据分类id查询广告内容信息
	$scope.findContentListByCategoryId=function(categoryId){
		contentService.findContentListByCategoryId(categoryId).success(
			function(response){
				//把分类id作为数组的角标，在这个角标所对应的位置存储广告集合
				$scope.catList[categoryId]=response;
			}			
		);
	}    
	

    
});	
