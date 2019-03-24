<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="${ctx}/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/css/admin.css">
    <!-- font-awesome图标 -->
    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
</head>
<body>
<i id="i_info" data-info="${teaName}" style="display: none;"></i>
<form method="post" onsubmit="return false;">
    <div class="panel admin-panel">
        <div class="panel-head"><strong class="icon-reorder"> 作业列表</strong></div>
        <div class="padding border-bottom">
            <ul class="search">
                <li>
                    <a class="button border-main icon-plus-square-o" href="addjob"> 添加作业</a>
                    <button type="button" class="button border-green" id="checkall"><span class="icon-check"></span> 全选
                    </button>
                    <button class="button border-red"><span class="icon-trash-o"></span> 批量删除</button>
                </li>

            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width="120">ID</th>
                <th>作业名</th>
                <th>题目文档</th>
                <th>截止时间</th>
                <th>操作</th>

            </tr>
            <c:forEach items="${taskList}" varStatus="status" var="task">
                <tr>
                    <td>
                        <input type="checkbox" name="id[]" value="${task.taskId}"/>
                            ${status.count}
                    </td>
                    <td>${task.taskName}</td>
                    <td><a href="download/task/${task.taskId}" class="button border-main"><span
                            class="icon-download">下载</span></a></td>
                    <td><fmt:formatDate value="${task.taskExpiry}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <div class="button-group">
                            <a name="edit" class="button border-main" href="javascript:;"><span
                                    class="icon-edit"></span>修改</a>
                            <a class="button border-red" href="deleteTask?id=${task.id}"><span
                                    class="icon-trash-o"></span>删除</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <!-- <tr>
              <td colspan="8"><div class="pagelist"> <a href="">上一页</a> <span class="current">1</span><a href="">2</a><a href="">3</a><a href="">下一页</a><a href="">尾页</a> </div></td>
            </tr> -->
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

    function del(id) {
        if (confirm("您确定要删除吗?")) {

        }
    }

    $(".border-red").click(function () {
        // alert("该功能未开放，如有需求请选择修改操作！")
    })

    /* $("#checkall").click(function(){
      $("input[name='id[]']").each(function(){
          if (this.checked) {
              this.checked = false;
          }
          else {
              this.checked = true;
          }
      });
    }) */
    $("#checkall").click(function () {
        if ($("input[name='id[]']").is(":checked")) {

            $("input[name='id[]']").prop('checked', false);
        } else {

            $("input[name='id[]']").prop('checked', true);
        }
    })

    function DelSelect() {
        var Checkbox = false;
        $("input[name='id[]']").each(function () {
            if (this.checked == true) {
                Checkbox = true;
            }
        });
        if (Checkbox) {
            var t = confirm("您确认要删除选中的内容吗？");
            if (t == false) return false;
        }
        else {
            alert("请选择您要删除的内容!");
            return false;
        }
    }

    $("a[name='edit']").click(function () {
        var td = $(this).parent().parent().prevAll().eq(-1);
        var taskId = td.children("input").val();
        var taskName = td.next().text();
        var taskExpiry = td.next().next().next().text();
        var url = "updatejob?taskId=" + taskId + "&taskName=" + taskName + "&taskExpiry=" + taskExpiry;
        window.location.href = url;
    })

</script>
</body>
</html>