<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>freemarker：null处理</title>
</head>
<body>
<h1>第一种处理方式：default内建函数</h1>
<h1>默认值：您好：${name?default("默认值")}，热烈欢迎：${message}</h1>
<h1>显示空：您好：${name?default("")}，热烈欢迎：${message}</h1>

<hr color="back" size="3">
<h1>第二种处理方式：!处理空置</h1>
<h1>默认值：您好：${name!"默认值"}，热烈欢迎：${message}</h1>
<h1>显示空：您好：${name!}，热烈欢迎：${message}</h1>

<hr color="back" size="3">
<h1>第三种处理方式：if判断处理空置</h1>
<h1>默认值：您好：
<#if name??>
    ${name}，
</#if>
热烈欢迎：${message}</h1>


</body>
</html>