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
        <div class="panel-head"><strong class="icon-reorder">学生列表</strong></div>
        <div class="padding border-bottom">
            <ul class="search">
                <li>
                    <a class="button border-main icon-plus-square-o" href="addjob">添加学生</a>
                    <button type="button" class="button border-green" id="checkall">
                        <span class="icon-check"></span> 全选
                    </button>
                    <button class="button border-red"><span class="icon-trash-o"></span>批量删除</button>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width="120">序号</th>
                <th>学生学号</th>
                <th>学生姓名</th>
                <th>所在系部</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${students}" varStatus="status" var="student">
                <tr>
                    <td>
                        <input type="checkbox" name="id[]" value="${student.stuId}"/>
                            ${status.count}
                    </td>
                    <td align="center">
                        <input
                                style="width: 100px; border: 0px; background-color: white; cursor: text; text-align: center;"
                                type="text"
                                value="${student.stuId}"/>
                    </td>
                    <td align="center">
                        <input
                                style="width: 100px; border: 0px; background-color: white; cursor: text; text-align: center;"
                                type="text"
                                value="${student.stuName}"/>
                    </td>

                    <td align="center">
                        <input
                                style="width: 200px; border: 0px; background-color: white; cursor: text; text-align: center;"
                                type="text"
                                value="${student.department}"/>
                    </td>
                    <td>
                        <div class="button-group">
                            <a name="edit" class="button border-main"
                               href="updateStudentInfo?studentId=${student.stuId}&studentName=${student.stuName}&depart=${student.department}">修改</a>
                        </div>
                        <div class="button-group">
                            <a class="button border-red" href="deleteStudentInfo?studentId=${student.stuId}">
                                <span class="icon-trash-o">
                                </span>删除
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="8">
                    <div class="pagelist">
                        <a href="managejob?currentPage=1">首页</a> <a
                            href="managejob?currentPage=${currentPage-1}">上一页</a>
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
                        <a href="managejob?currentPage=${currentPage+1}">下一页</a> <a
                            href="managejob?currentPage=${totalPage}">尾页</a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>
<script type="text/javascript">

</script>
</body>
</html>