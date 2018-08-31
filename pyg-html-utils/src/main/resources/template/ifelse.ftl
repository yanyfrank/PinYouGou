<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>freemarker获取对象数据</title>
</head>
<body>
<h1>
    <#if flag=1>
        <div style="height: 100px;width: 100px;background-color: yellow">

        </div>

        <#elseif flag=2>
            <div style="height: 100px;width: 100px;background-color: cornflowerblue">

            </div>
        <#else>
            <div style="height: 100px;width: 100px;background-color:chartreuse">

            </div>

    </#if>
</h1>
</body>
</html>