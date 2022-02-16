<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/14
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>专业管理</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div style="margin: 10px">
            <form class="layui-form layui-form-pane">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">班级名称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="clazzName" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-primary"  lay-submit lay-filter="search-btn"><i class="layui-icon"></i> 搜 索</button>
                    </div>
                </div>
            </form>
        </div>
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add">
                    <i class="fa fa-plus"></i>
                    添加
                </button>
                <button class="layui-btn layui-btn-sm layui-btn-normal data-delete-btn" lay-event="update">
                    <i class="fa fa-pencil"></i>
                    修改
                </button>
                <button class="layui-btn layui-btn-sm layui-btn-danger data-delete-btn" lay-event="delete">
                    <i class="fa fa-remove"></i>
                    删除
                </button>
            </div>
        </script>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        //向后端发起请求，后端返回的是json数据，并且字段名要对应
        table.render({
            elem: '#currentTableId',
            url: '${basePath}clazz/query',
            // contentType:'application/json',
            method:"post",
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports', 'print'],
            page: true,
            cols: [[
                {type: "checkbox", width: 50},
                {field: 'id', width: 80, title: 'ID'},
                {field: 'clazzName',  title: '班级名称'},
                // 这里不应该是显示id，而是显示对应的专业
                // {field: 'subjectId', title: '所属系'},
                // 后台传过来的是subject对象，使用这样的方式来显示
                {field: 'subjectName', title: '专业名称',templet:'<div>{{d.subject.subjectName}}</div>'},
                {field: 'remark',title: '备注'}
            ]],
            skin: 'line'
        });

        // 监听搜索操作
        form.on('submit(search-btn)', function (data) {
            console.log(data.field);
            //执行搜索重载
            table.reload('currentTableId', {
                // contentType:'application/json',
                where: data.field
            });
            return false;
        });
        /**
         * toolbar事件监听
         * 增删改操作
         */
        table.on('toolbar(currentTableFilter)', function (obj) {
            if (obj.event === 'add') {   // 监听添加操作
                var index = layer.open({
                    title: '添加班级',
                    type: 2,
                    shade: 0.2,
                    shadeClose: false,
                    area: ['50%', '50%'],
                    // 到控制层，然后再跳转到添加页面
                    content: '${basePath}clazz/add',
                    end:function(){
                        table.reload('currentTableId');
                    }
                });
            } else if (obj.event === 'update') {  // 监听修改操作
                // 表格的选中状态,选中几个就显示几条数据
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;

                // 只能一个一个的修改
                if(data.length !=1){
                    layer.msg("请选择一行数据修改",{time:1000});
                    return;
                }
                // 条件都满足可以进行修改，向controller发起请求，获取要修改的详细信息
                var index = layer.open({
                    title: '修改班级',
                    type: 2,
                    shade: 0.2,
                    shadeClose: false,
                    area: ['50%', '50%'],
                    content: '${basePath}clazz/detail/'+data[0].id,
                    end:function(){
                        table.reload('currentTableId');
                    }
                });
            }else if (obj.event === 'delete') { // 监听删除操作
                // 表格的选中状态,选中几个就显示几条数据
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                //console.log(data);

                if(data.length ==0){
                    layer.msg("请选择行数据删除",{time:1000});
                    return;
                }
                // 获取要删除的id
                var arr = [];
                for(index in data){
                    arr.push(data[index].id);
                }
                layer.confirm('真的要删除吗', function (index) {
                    $.ajax({
                        url:"${basePath}clazz/delete",
                        type:"POST",
                        dataType:'json',
                        // 发送的字符串，逗号分隔，用于批量删除
                        data:"ids="+arr.join(","),
                        success:function(data){
                            layer.msg(data.msg,{time:500},
                                function(){
                                    table.reload("currentTableId");
                                });
                        },
                        error:function(data){
                            console.log(data);
                        }
                    });
                });
            }
        });
    });
</script>
</body>
</html>
