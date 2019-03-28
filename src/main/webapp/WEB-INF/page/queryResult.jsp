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
    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
</head>
<body>
<i id="i_info" data-info="${teaName}" style="display: none;"></i>
<form id="queryForm" method="post" action="query">
    <div class="panel admin-panel">
        <div class="panel-head">
            <strong class="icon-reorder">成绩查看</strong>
        </div>
        <div class="padding border-bottom">
            <ul class="search">
                <li><select name="taskId" class="input">
                    <option value="-1">请选择要查询作业</option>
                    <c:if test="${taskList!=null}">
                        <c:forEach items="${taskList}" var="task">
                            <c:if test="${taskId == task.taskId}">
                                <option selected="selected" value="${task.taskId}">${task.taskName}</option>
                            </c:if>
                            <c:if test="${taskId != task.taskId}">
                                <option value="${task.taskId}">${task.taskName}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select></li>
                <li>
                    <button type="submit" class="button border-green">查询</button>
                </li>
            </ul>
        </div>

        <table class="table table-hover text-center" data-task="${taskId}">
            <tr>
                <th>序号</th>
                <th>学生学号</th>
                <th>学生名</th>
                <th>成绩</th>
                <th>作业是否提交</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${resultList}" var="result" step="1"
                       varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${result.stuId}</td>
                    <td>${result.studentName}</td>
                    <td align="center">
                        <input
                                style="width: 60px; border: 0px; background-color: white; cursor: text; text-align: center;"
                                class="input-big" type="text" readonly="readonly"
                                value="${result.score}" onblur="blurListen(value);"/>
                        <a class="u_edit border-main" href="javascript:;">
                            <span class="icon-edit text-big"></span>
                        </a>

                        <a class="u_save border-green" style="display: none;"
                           href="javascript:;">
                            <span class="icon-check-square-o text-big"></span>
                        </a>
                    </td>
                    <c:if test="${result.submit== false}">
                        <td>否</td>
                        <td>
                            <div class="button-group">
                                <a class="button border-gray"
                                   href="javascript:;"><span
                                        class="icon-download"></span>下载作业</a>
                            </div>
                        </td>
                    </c:if>
                    <c:if test="${result.submit == true}">
                        <td>是</td>
                        <td>
                            <div class="button-group">
                                <a class="button border-yellow" href="download/result/${taskId}/${result.stuId}"><span
                                        class="icon-download"></span>下载作业</a>
                            </div>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="8">
                    <div class="pagelist">
                        <a href="query?taskId=${taskId}&currentPage=1">首页</a> <a
                            href="query?taskId=${taskId}&currentPage=${currentPage-1}">上一页</a>
                        <span>
                            当前页:
                          <c:choose>
                              <c:when test="${currentPage!=null}">
                                  ${currentPage}
                              </c:when>
                              <c:otherwise>
                                  1
                              </c:otherwise>
                          </c:choose>
                        </span>
                        <span>
                            总页数:
                          <c:choose>
                              <c:when test="${totalPage!=null}">
                                  ${totalPage}
                              </c:when>
                              <c:otherwise>
                                  1
                              </c:otherwise>
                          </c:choose>
                        </span>
                        <a href="query?taskId=${taskId}&currentPage=${currentPage+1}">下一页</a> <a
                            href="query?taskId=${taskId}&currentPage=${totalPage}">尾页</a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>

<script type="text/javascript">
    var scoreFlag = false;

    function blurListen(value) {

        if (value >= 0 && value <= 100) {
            scoreFlag = true;
        } else {
            scoreFlag = false;
            alert("成绩输入有误！");
        }
    }

    $(".u_edit").click(function () {
        var edit = $(this);
        var save = edit.next("a");
        var input = edit.prevAll("input");
        edit.hide();
        save.show();
        input.css('border', '');
        input.removeAttr("readonly");
        edit.blur();
    })
    $(".u_save").click(function () {
        if (scoreFlag == false)
            return;
        var save = $(this);
        var edit = save.prev("a");
        var input = save.prevAll("input");
        var stuId = input.parent().prev().prev().text();
        $.ajax({
            type: "POST",
            url: "updateResult",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                "taskId": parseInt($("table").attr("data-task")),
                "stuId": stuId,
                "score": input.val()
            }),
            success: function (data) {
                if (data == true) {
                    alert("修改成功！");
                } else {
                    alert("修改失败！");
                }
            },
            error: function () {
                alert("修改失败！")
            }
        })
        save.hide();
        edit.show();
        input.css('border', '0px');
        input.attr("readonly", "readonly");
    })

    $(".border-gray").click(function () {
        alert("无作业可下载！");
    })

    $("form").submit(function () {
        if ($("select option:selected").val() == -1) {
            alert("请选择要查询的作业！");
            return false;
        }
    })
</script>

</body>
</html>