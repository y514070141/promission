$(function () {
    /*员式数据列表*/
    $("#dg").datagrid({
        url:"/employeeList",
        columns:[[
            {field:'username',title:'姓名',width:100,align:'center'},
            {field:'inputtime',title:'入职时间',width:100,align:'center'},
            {field:'tel',title:'电话',width:100,align:'center'},
            {field:'email',title:'邮箱',width:100,align:'center'},
            {field:'department',title:'部门',width:100,align:'center',formatter: function(value,row,index){
                    if (value){
                        return value.name;
                    }
                }},
            {field:'state',title:'状态',width:100,align:'center',formatter: function(value,row,index){
                    if(row.state){
                        return "在职";
                    }else {
                        return "<font style='color: red'>离职</font>"
                    }
                }},
            {field:'admin',title:'管理员',width:100,align:'center',formatter: function(value,row,index){
                    if(row.admin){
                        return "是";
                    }else {
                        return "否"
                    }
                }},
        ]],
        fit:true,
        fitColumns:true,
        rownumbers:true,
        pagination:true,
        singleSelect:true,
        striped:true,
        toolbar:"#tb",
        pageList:[5,10,15,20],//初始化选择每页显示条数大小
        pageSize:5,//初始化页面数量 必须和集合一样
        onClickRow:function (rowIndex,rowData) {
            // alert(rowData.state)
            if(rowData.state){
                $("#delete").linkbutton("enable");
            }else {
                $("#delete").linkbutton("disable");
            }
        }
    });
    /*保存编辑*/
    $("#dialog").dialog({
        width:350,
        height:420,
        closed:true,
        buttons:[{
            text:'保存',
            handler:function(){
                /*判断当前是添加 还是编辑*/
                var id = $("[name='id']").val();
                var url;
                if(id){
                    /*编辑*/
                    url = "updateEmployee";
                }else {
                    /*添加*/
                    url= "saveEmployee";
                }
                /*提交表单*/
                $("#employeeForm").form("submit",{
                    url:url,
                    onSubmit:function(param){
                        //传参数
                        //console.log("param-----------------------------------"+param);
                        //获取选中角色 的 rid
                        var values=$("#role").combobox("getValues");
                        for(var i=0;i<values.length;i++){
                            var rid=values[i];//便利每一个rid
                            param["roles["+i+"].rid"]=rid;//把每一个rid放到集合
                        }
                    },
                    success:function (data) {
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg);
                            /*关闭对话框 */
                            $("#dialog").dialog("close");
                            /*重新加载数据表格*/
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("温馨提示",data.msg);
                        }
                    }
                });
            }
        },{
            text:'关闭',
            handler:function(){
                $("#dialog").dialog("close");
            }
        }]
    });
    /*监听添加*/
    $("#add").click(function () {
        /*设置标签*/
        $("#dialog").dialog("setTitle","添加员工");
        /*显示密码*/
        $("#password").show();
        /*清空对话框中的数据*/
        $("#employeeForm").form("clear");
        /*添加密码验证*/
        $("[name='password']").validatebox({required:true});
        /*打开对话框*/
        $("#dialog").dialog("open");
    });
    /*监听编辑*/
    $("#edit").click(function () {
        /*获取当前选中的行*/
        var rowData = $("#dg").datagrid("getSelected");
        console.log(rowData);
        if(!rowData){
            $.messager.alert("提示","选择一行数据进行编辑");
            return;
        }
        /*取消密码验证*/
        $("[name='password']").validatebox({required:false});
        $("#password").hide();
        /*弹出对话框*/
        $("#dialog").dialog({setTitle:"编辑员工",closed:false});
        // $("#dialog").dialog("open");
        /*回显部门*/
        rowData["department.id"] = rowData["department"].id;
        /*回显管理员*/
        rowData["admin"] = rowData["admin"]+"";
        //回显角色
        $.get("getRoleById?id="+rowData.id,function (data) {
            //接收的rid的集合 存到 下拉框
            $("#role").combobox("setValues",data);
        });
        /*选中数据的回示*/
        $("#employeeForm").form("load",rowData);

    });
    /*部门选择 下拉列表*/
    $("#department").combobox({
        width:150,
        panelHeight:'auto',
        editable:false,
        url:'departList',
        textField:'name',
        valueField:'id',
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#department").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    //角色下拉列表
    $("#role").combobox({
        width:150,
        panelHeight:'auto',
        editable:false,
        url:'roleList',
        textField:'rname',
        valueField:'rid',
        multiple:true,
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#role").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    /*是否为管理员选择*/
    $("#state").combobox({
        width:150,
        panelHeight:'auto',
        textField:'label',
        valueField:'value',
        editable:false,
        data:[{
            label:'是',
            value:'true'
        },{
            label:'否',
            value:'false'
        }],
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#state").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }
    });
    //离职状态设置
    $("#delete").click(function () {
        var rowData=$("#dg").datagrid("getSelected");
        // console.log(rowData);
        if(!rowData){
            $.messager.alert("提示","请选择一行编辑","info");
            return;
        }
    //    如果选中一行
        $.messager.confirm("确认","是否做离职操作",function (res) {
            // alert(res);  //res 回调
            if(res){//如果点击了 确定 才执行
                $.get("updateState?id="+rowData.id,function (data) {
                    if(data.success){
                        //提示成功 并刷新界面
                        $.messager.alert("提示",data.msg);
                        //刷新保留在当前页
                        $("#dg").datagrid("reload");
                    }else {
                        $.messager.alert("提示",data.msg);
                    }
                });
            }
        });

    });
    //    模糊查询
    $("#searchBtn").click(function () {
        var keyword=$("[name='keyword']").val();
        // alert(keyword)
        $("#dg").datagrid("load",{keyword:keyword});
    });
    //刷新
    $("#reload").click(function () {
        //清空搜索框
        $("[name='keyword']").val('');
        //刷新视图
        $("#dg").datagrid("reload",{});
    });
});