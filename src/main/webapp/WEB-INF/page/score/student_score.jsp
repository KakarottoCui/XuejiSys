<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/17
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生成绩</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div class="layui-row">
            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        </div>

    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        table.render({
            elem: '#currentTableId',
            url: '${basePath}score/query_student_score',
            // contentType:'application/json',
            method:"post",
            toolbar: '#toolbar',
            defaultToolbar: ['filter', 'exports', 'print'],
            page: false,
            cols: [[
                {field: 'stuName',  title: '姓名',templet: '<div>{{d.student.stuName}}</div>'},
                {field: 'courseName', title: '课程名称',templet: '<div>{{d.course.courseName}}</div>'},
                {field: 'year', title: '年份',templet: '<div>{{d.section.year}}</div>'},
                {field: 'type', title: '类型',templet: '<div>{{d.section.type}}</div>'},
                {field: 'score', title: '分数'}
            ]],
            done: function(res, page, count){
                for(var index in res.data){
                    if(res.data[index].selected >= 1){
                        res.data[index]["LAY_CHECKED"]='true';
                        var index= res.data[index]['LAY_TABLE_INDEX'];
                        $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                        $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                    }
                }
            },
            skin: 'line'
        });


    });
</script>
</body>
</html>
