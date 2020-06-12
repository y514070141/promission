<%--
  Created by IntelliJ IDEA.
  User: xiaoyue
  Date: 2020/6/2
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/static/common/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/role.js"></script>
<%--    修改easyui样式--%>
    <style>
        .panel-header{
            height: 25px;
        }
        .panel-title{
            margin-top: -5px;
        }
    </style>
</head>
<body>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="remove">删除</a>
</div>
<table id="role_dg"></table>

<div id="dialog">
    <form id="myform">
        <table align="center" style="border-spacing: 20px 30px">
            <input type="hidden" name="rid">
            <tr align="center">
                <td>角色编号: <input type="text" name="rnum"  ></td>
                <td>角色名称: <input type="text" name="rname"  ></td>
            </tr>
            <tr>
                <td><div id="role_data1"></div></td>
                <td><div id="role_data2"></div></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
