<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="${ctx}/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/css/admin.css">
    <!-- font-awesome图标 -->
    <link rel="stylesheet"
          href="${ctx}/font-awesome/css/font-awesome.min.css">


    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
</head>
<body>
<i id="i_info" data-info="${stuName}" style="display: none;"></i>

<form method="post" action="">
    <div class="panel admin-panel">
        <div class="panel-head">
            <strong class="icon-reorder"> 个人成绩</strong>
        </div>

        <table class="table table-hover text-center">
            <tr>

                <th>作业名</th>
                <th>成绩</th>

            </tr>
            <c:forEach items="${resultList}" var="result" varStatus="status">
                <tr>
                    <td>${taskList[status.count-1].taskName}</td>
                    <td>${result.score}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="8">
                    <div class="pagelist">
                        <a href="">上一页</a> <span class="current">1</span><a href="">2</a><a
                            href="">3</a><a href="">下一页</a><a href="">尾页</a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>
<script type="text/javascript">
    //验证session是否有效
    /* {

        var name = $("#i_info").attr("data-info");
        if(name==""){
            top.location="./";
        }

    } */

</script>
</body>
</html>