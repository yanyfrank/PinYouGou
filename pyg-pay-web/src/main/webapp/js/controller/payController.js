 //控制层 
app.controller('payController' ,function($scope,$location,payService){

	//生成二维码
	//1,向支付系统发送生成二维码请求
	//2，支付系统调用微信支付平台支付下单接口
	//3，微信支付平台返回支付地址
	//4，根据此地址生成二维码
	$scope.createQrCode = function () {
		//调用服务层方法，向微信支付平台下单。获取支付地址
		payService.createQrCode().success(function (data) {

			//获取支付地址
			var code_url = data.code_url;
			//支付金额
			$scope.total_fee = data.total_fee;
			//订单号
            $scope.out_trade_no = data.out_trade_no;

			//使用qrious插件生成二维码
            var qr = new QRious({
                element:document.getElementById('qrious'),
                size:300,
                background:'white',
                foreground:'black',
                level:'H',
                value:code_url
            });

            //调用方法
            queryStatus($scope.out_trade_no);


        })
    }

    //实时监控二维码支付状态
    //每3秒查询一次二维码支付状态，如果支付成功，跳转到支付成功页面，否则跳转到支付失败页面
    queryStatus = function (out_trade_no) {
	    //调用服务层方法，查询订单状态
        payService.queryStatus(out_trade_no).success(function (data) {
            //判断
            if(data.success){
                //跳转到支付成功页面
                location.href = "paysuccess.html#?money="+$scope.total_fee;
            }else{

                if(data.message=='二维码超时'){
                    //重新生成二维码
                    $scope.createQrCode();
                }else{
                    //跳转支付失败页面
                    location.href = "payfail.html";
                }

            }
        })

    };
	
	//获取支付成功后的金额
    $scope.loadTotalFee = function () {
        return $location.search()["money"];
    }


});	
