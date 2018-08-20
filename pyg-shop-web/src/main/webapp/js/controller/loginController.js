//定义控制器
app.controller("loginController", function ($scope,loginService) {


    //定义查询所有的方法
    $scope.showName = function () {
        //使用内置服务向后台发送ajax请求
        loginService.showLoginName().success(function (data) {
            $scope.loginName = data.loginName;
        })
    };


});