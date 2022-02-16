<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/17
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>专业管理</title>
    <link rel="stylesheet" href="${basePath}static/css/style.css" media="all">
    <link rel="stylesheet" href="${basePath}static/lib/layui-src/css/layui.css" media="all">
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main width_80">
        <form class="layui-form">
            <table class="table-list" border="0">
                <tr>
                    <th width="80px">ID</th>
                    <th width="150px">姓名</th>
                    <th>
                        分值
                    </th>
                </tr>
                <c:forEach items="${list}" var="student">
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.stuName}</td>
                        <td>
                            <input type="text" lay-verify="required" style="width: 200px" class="layui-input" name="score" value="${student.score}">
                            <input type="hidden" lay-verify="required" style="width: 200px" class="layui-input" name="id" value="${student.id}">
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-submit lay-event="save" lay-filter="save">
                    <i class="fa fa-plus"></i>
                    保存评分
                </button>
            </div>
        </form>
    </div>
</div>
<script src="${basePath}static/lib/layui-src/layui.js?<%=System.currentTimeMillis()%>" charset="utf-8"></script>
<script src="${basePath}static/js/lay-config.js?v=2.0.0" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var $ = layui.jquery,form = layui.form;
        form.on('submit(save)', function (data) {
            var scoreArray = $('input[name="score"]');
            // 成绩数组
            var arr = [];
            for(var i=0;i<scoreArray.length;i++){
                arr.push(scoreArray[i].value);
            }

            // 学生id数组
            var idArray = $('input[name="id"]');
            var idArr = [];
            for(var i=0;i<idArray.length;i++){
                idArr.push(idArray[i].value);
            }

            $.ajax({
                url:"${basePath}section/teacher_student_score",
                type:"POST",
                dataType:'json',
                data: {
                    sectionId:"${sectionId}",
                    courseId:"${courseId}",
                    stuIds:idArr.join(","),
                    scores:arr.join(","),
                },
                success:function(data){
                    layer.msg(data.msg,{time:500},
                        function(){
                            parent.layer.close(index);
                        });
                }
            });
            return false;
        });

    });
</script>
</body>
</html>
