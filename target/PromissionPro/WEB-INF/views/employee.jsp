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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/employee.js"></script>
</head>
<body>
<%--工具栏--%>
<div id="tb">
    <shiro:hasPermission name="employee:add">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:edit">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:delete">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="delete">离职</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="reload">刷新</a>
    <input  type="text" name="keyword">
    <button class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</button>
</div>
<%--数据表格   table标签--%>
<table id="dg"></table>
<%--对话框--%>
<div id="dialog">
    <form id="employeeForm">
        <%--        编辑时 隐藏--%>
        <input type="hidden" name="id">
        <table align="center" style="border-spacing: 0px 10px">
            <tr>
                <td>用户名:</td>
                <td><input type="text" name="username" class="easyui-validatebox" data-options="required:true"></td>
            </tr>
            <tr id="password">
                <td>密码:</td>
                <td><input type="text" name="password" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>手机:</td>
                <td><input type="text" name="tel" class="easyui-validatebox" ></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="email" class="easyui-validatebox" ></td>
            </tr>
            <tr>
                <td>入职日期:</td>
                <td><input type="text" name="inputtime" class="easyui-datebox"></td>
            </tr>
            <tr>
                <td>部门:</td>
                <td><input id="department" name="department.id" placeholder="请选择部门"/></td>
            </tr>
            <tr>
                <td>是否管理员:</td>
                <td><input id="state" name="admin" placeholder="是否为管理员"/></td>
            </tr>
            <tr>
                <td>请选择角色:</td>
                <td><input id="role" name="role.rid" placeholder="请选择角色"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
