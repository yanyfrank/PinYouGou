//定义控制器
app.controller("specificationController", function ($scope,$controller, specificationService) {

    //继承父控制器
    //使用￥controller服务继承父控制器
    //继承父控制器：就是把父控制器￥scope作用域传递给子控制器作用域
    $controller("baseController",{$scope:$scope});


    /*//定义查询所有的方法
    $scope.findAll = function () {
        //使用内置服务向后台发送ajax请求
        specificationService.findAll().success(function (data) {
            $scope.list = data;
        })
    };*/



    //定义分页查询方法
    $scope.findPage = function (page,rows) {
        //使用内置服务向后台服务发送分页查询请求
        specificationService.findPage(page,rows).success(function (data) {
            //把总记录是赋值给分页控件
            $scope.paginationConf.totalItems = data.total;
            //分页结果
            $scope.list = data.rows;
        })
    };

    //定义保存的函数
    $scope.save = function () {

        var objService = null;
        //判断是否是修改操作，还是保存操作
        if($scope.entity.specification.id!=null){
            objService =  specificationService.update($scope.entity);
        }else{
            objService =  specificationService.add($scope.entity);
        }

        //发送请求
        objService.success(function (data) {
            //判断
            if(data.success){
                //刷新分页列表
                $scope.reloadList();
            }else{
                alert(data.message);
            }
        })
    };

    //根据id查询规格数据方法
    $scope.findOne = function (id) {
        //使用内置服务发送请求
        specificationService.findOne(id).success(function (data) {
            $scope.entity = data;
        })
    };



    //定义删除方法
    $scope.del = function () {
        //发送请求
        specificationService.del($scope.selectIds).success(function (data) {
            //判断
            if(data.success){
                //请求数组
                $scope.selectIds = [];
                $scope.reloadList();
            }else{
                alert(data.message);
            }
        })
    };
    
    //添加规格选项行
    //添加行核心原理就是添加空对象{}
    $scope.addTableRow = function () {
        $scope.entity.specificationOptionList.push({});
    };
    
    //删除规格选项行
    $scope.delTableRow = function (index) {
        $scope.entity.specificationOptionList.splice(index,1);
    }
});