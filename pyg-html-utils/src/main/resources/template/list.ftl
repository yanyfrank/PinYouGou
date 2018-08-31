<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>freemarker:list指令</title>
</head>
<body>
<table style="width: 600px;height: 300px;" border="1">
    <tr>
        <td>角标</td>
        <td>编号</td>
        <td>姓名</td>
        <td>性别</td>
        <td>年龄</td>
        <td>地址</td>
        <td>操作</td>
    </tr>
    <#list pList as p>
    <#if p_index%2==0>
        <tr style="background-color: red;">
    <#else>
        <tr style="background-color: cornflowerblue;">
    </#if>

            <td>${p_index}</td>
            <td>${p.id}</td>
            <td>${p.username}</td>
            <td>${p.sex}</td>
            <td>${p.age}</td>
            <td>${p.address}</td>
            <td>
                <a href="#">删除</a>
                <a href="#">修改</a>
            </td>
        </tr>
    </#list>
</table>
<hr color="blue" size="6"/>
<h1>${pList?size}</h1>
</body>
</html>