 //控制层 
app.controller('goodsController' ,function($scope,$controller   ,goodsService,itemCatService,typeTemplateService,uploadService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
		//把富文本编辑器的值取出来赋值需要保存数据字段
		$scope.entity.goodsDesc.introduction = editor.html();
        goodsService.add( $scope.entity  ).success(
			function(response){
				if(response.success){
					//清空页面数据
					$scope.entity={};
					//清空富文本编辑器
					editor.html('');
					alert(response.message);
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};

	//定义方法： 首先查询商品的顶级节点，顶级父节点
	$scope.findItemCatList = function () {
		//调用服务层
		itemCatService.findItemCatListByParentId(0).success(function (data) {
			$scope.cat1List = data;
        })
    };

	//使用angularJS 监听服务 $watch  动态监听变量的变化，一旦发现变量变化后，可以作一些操作
	//监听顶级节点值变量
	//参数1：表示要监听的变量
	//参数2：newValue 是新变化的值 oldvalue： 变化之前的值
	$scope.$watch('entity.goods.category1Id',function (newValue,oldValue) {
		//根据新的分类ID查询子节点
        itemCatService.findItemCatListByParentId(newValue).success(function (data) {
			$scope.cat2List = data;
        })
    });

	//监控第二级节点值变量,一旦发生变化，立马查询二级节点的子节点
    $scope.$watch('entity.goods.category2Id',function (newValue,oldValue) {
        //根据新的分类ID查询子节点
        itemCatService.findItemCatListByParentId(newValue).success(function (data) {
            $scope.cat3List = data;
        })
    });

    //监控第三级节点值变量,一旦发生变化，根据节点id查询节点对象，获得模板id
    $scope.$watch('entity.goods.category3Id',function (newValue,oldValue) {
        //根据新的分类ID查询商品分类对象
		itemCatService.findOne(newValue).success(function (data) {
			//把模板id赋值商品表中模板字段，赋值后保存数据库中
			$scope.entity.goods.typeTemplateId = data.typeId;
        })

    });

    //监控模板id变量,一旦发生变化，根据模板id查询品牌
    $scope.$watch('entity.goods.typeTemplateId',function (newValue,oldValue) {
        //根据新的分类ID查询商品分类对象
        typeTemplateService.findOne(newValue).success(function (data) {
        	//先初始化
			$scope.typeTemplate = data;
            //用JSON.parse转换字符串
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
        })

    });
    
    //定义图片上传方法
	$scope.uploadFile = function () {
		uploadService.uploadFile().success(function (data) {
			//判断
			if(data.success){
				$scope.image_entity.url = data.message;
			}else{
				alert(data.message);
			}
        })
    }
});	
