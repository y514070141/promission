$(function () {
    //datagird加载
    $("#role_dg").datagrid({
        url:"/getRoles",
        columns:[[
            {field:'rnum',title:'角色编号',width:100,align:'center'},
            {field:'rname',title:'角色名称',width:100,align:'center'}
         ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        singleSelect:true,
        striped:true,
        pageList:[5,10,15,20],//初始化选择每页显示条数大小
        pageSize:5,//初始化页面数量 必须和集合一样
        toolbar:"#toolbar",
    });
    //dialog保存和编辑
    $("#dialog").dialog({
        width:500,
        height:500,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function(){
                var rid=$("[name='rid']").val();
                var url;
                console.log("rid="+rid);
                if(rid){
                    url='/updateRole';
                }else {
                    url='/saveRole';
                }
                $("#myform").form("submit",{
                    url:url,
                    onSubmit:function(param){
                        //param["itlike"]="123";
                        //获取·所有行·
                        var rows=$("#role_data2").datagrid("getRows");
                        for(var i=0;i<rows.length;i++){
                            var row=rows[i];
                            //把获取的每一行的权限的id 赋给对象的id
                            param["permissions["+i+"].pid"]=row.pid;
                        }
                    },
                    success:function (data) {
                        //数据转为json
                        data=$.parseJSON(data);
                        console.log(data.msg);
                        if(data.success){
                            $.messager.alert("温馨提示",data.msg);
                            //权限修改 刷新 并 关闭
                            $("#role_dg").datagrid("reload");
                            $("#dialog").dialog("close");
                        }else {
                            $.messager.alert("温馨提示",data.msg);
                        }
                    }
                })
            }
        },{
            text:'关闭',
            handler:function(){
                $("#dialog").dialog("close");
            }
        }]
    });
    //选择权限
    $("#role_data1").datagrid({
        url:'/promissionList',
        title:"所有权限",
        width:200,
        height:250,
        fitColumns:true,
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        singleSelect:true,
        onClickRow:function (rowIndex,rowData) {//点击时候 触发
            //获取已选权限所有行
            var rows=$("#role_data2").datagrid("getRows");
            //循环所有行
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                //如果已选权限id 等于 你所有权限id 让其选中状态
                if(rowData.pid==row.pid){
                    var index=$("#role_data2").datagrid("getRowIndex",row);
                    $("#role_data2").datagrid("selectRow",index);
                    return;
                }
            }
            //把点击的 增加到 已选权限
            $("#role_data2").datagrid("appendRow",rowData);
        }
    });
    //移除权限
    $("#role_data2").datagrid({
        title:"已选权限",
        width:200,
        height:250,
        fitColumns:true,
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        singleSelect:true,
        onClickRow:function (rowIndex,rowData) {
            //alert(rowIndex)
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }
    });
    //保存
    $("#add").click(function () {
        //清空myform
        $("#myform").form("clear");
        //请空已选权限   loadData加载本地数据
        $("#role_dg2").datagrid("loadData",{rows:[]});
        //选中了一行 回显数据 并打开
        $("#dialog").dialog({
            title:'编辑',
            closed:false
        });
    });
    //编辑回显
    $("#edit").click(function () {
        /*获取当前选中的行*/
         var rowData=$("#role_dg").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }
        console.log("rowData="+rowData.rid);
        //加载当前角色 获取属性的对象
        var options=$("#role_data2").datagrid("options");
        //通过属性对象id 查询 当前对象所拥有的权限
        options.url="/getPermissionByRid?rid="+rowData.rid;
        //重新加载datagird
        $("#role_data2").datagrid("load");

        //选中了一行 回显数据 并打开
        $("#dialog").dialog({
            title:'编辑',
            closed:false
        });
        $("#myform").form("load",rowData);
    });
    //监听删除
    $("#remove").click(function () {
        var rowData=$("#role_dg").datagrid("getSelected");
        if(!rowData){
            $.messager.alert("温馨提示","请选择一行进行编辑");
        }else {
            $.messager.confirm("提示","是否做删除操作",function (res) {
                if(res){
                    //删除  ajax  get请求不用转json
                    $.get("deleteRoleByRid?rid="+rowData.rid,function (data) {
                        if(data.success){
                            $.messager.alert("提示",data.msg);
                            $("#role_dg").datagrid("reload");
                        }else {
                            $.messager.alert("提示",data.msg)
                        }
                    });
                    return;
                }
                $.messager.alert("提示","用户取消了操作");
            });
        }
    });
});