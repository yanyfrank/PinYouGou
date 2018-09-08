<%--
  Created by IntelliJ IDEA.
  User: YYF
  Date: 2018/8/31
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试单点登陆--品优购系统2</title>
</head>
<body>
<h1>
  您好！欢迎您：  <%= request.getRemoteUser()%> 测试单点登陆品优购系统2
</h1>
<hr color="red" size="2">
<a href="http://192.168.18.11:6080/cas/logout">单点退出</a>
</body>
</html>
