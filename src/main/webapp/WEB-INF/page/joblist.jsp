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
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="${ctx}/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/css/admin.css">
    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
</head>
<body>
<i id="i_info" data-info="${stuName}" style="display: none;"></i>
<div class="panel admin-panel">
    <div class="panel-head">
        <strong class="icon-reorder">作业列表</strong>
    </div>

    <table class="table table-hover text-center">
        <tr>
            <th width="120">序号</th>
            <th>作业名</th>
            <th>题目内容</th>
            <th>截至时间</th>
            <th>作业状态</th>
            <th>上交作业</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${taskList}" var="task" varStatus="status">
            <form method="post" action="upload" onsubmit="return checkTime();" enctype="multipart/form-data">
                <input type="hidden" name="studentId" value="${studentId}">
                <tr>
                    <td width="120">${status.count}</td>
                    <td data-name="id" style="display: none">
                        <input type="text" name="taskid" value="${task.taskId}"/></td>
                    <td><input type="text"
                               style="cursor: text; background-color: white; border: none; text-align: center"
                               name="taskname" value="${task.taskName}" readonly="readonly"/></td>
                    <td><a href="download/task/${task.taskId}" class="button border-main"><span
                            class="icon-download">下载</span></a></td>
                    <td data-name="time"><fmt:formatDate value="${task.taskExpiry}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <c:if test="${resultList[status.count-1].submit}">
                        <td><input type="text"
                                   style="background-color: white; border: none; text-align: center; width: 100px;"
                                   name="submit" value="已提交" disabled="disabled"/></td>
                        <td align="center">
                            <input type="file" style="text-align: center; width: 200px;" multiple="multiple"
                                   name="uploadfile"></td>
                        <td><input disabled="disabled" type="submit" onclick="return upload();"
                                   class="button border-red" value="上传"></td>
                    </c:if>

                    <c:if test="${!resultList[status.count-1].submit}">
                        <td><input type="text"
                                   style="background-color: white; border: none; text-align: center; width: 100px;"
                                   name="submit" value="未提交" disabled="disabled"/></td>
                        <td align="center">
                            <input type="file" style="text-align: center; width: 200px;" multiple="multiple"
                                                  name="uploadfile">
                        </td>
                        <td><input disabled="disabled" type="submit" class="button border-green" value="上传"></td>
                    </c:if>

                </tr>
            </form>
        </c:forEach>
    </table>

</div>

<script type="text/javascript">
    //验证session是否有效
    /* {

        var name = $("#i_info").attr("data-info");
        if(name==""){
            top.location="./";
        }

    } */
    var taskId;

    function upload() {
        return confirm("作业已提交过，如存在同名文件，将会覆盖，是否继续？");
    }

    $(":submit").click(function () {
        var td = $(this).parent().prevAll("td[data-name='id']");
        taskId = td.find(":text[name='taskid']").val();
    })

    function checkTime() {
        var actionFlag = false;
        $.ajax({
            type: "POST",
            url: "checkTime",
            contentType: "application/json;charset=utf-8",
            data: JSON
                .stringify({
                    "taskId": taskId
                }),
            async: false,
            dataType: "json",
            success: function (result, status) {
                var flag = result.flag;
                if (flag == -1) {
                    actionFlag = false;
                } else if (flag == 0) {
                    actionFlag = true;
                } else {
                    actionFlag = false;
                }
                /*  */
            },
            error: function (str1, str2, str3) {
                alert("网络出现问题！")
            }
        });
        if (actionFlag == false) {
            alert("已超过规定时间，无法完成作业提交！");
        }
        return actionFlag;
    }

    $(":file").change(function () {
        var button = $(this).parent().next().children(":submit");
        if ($(this).val() != "") {
            button.removeAttr("disabled");
        } else {
            button.attr("disabled", "disabled");
        }
    })
</script>
</body>
</html>