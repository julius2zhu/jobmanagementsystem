<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试列表</title>
<base href="${ctx}">
</head>
<body>
<h2>Hello World!</h2>
	<table id="tab">
  		<tr>
  			<th>学生ID</th>
  			<th>作业ID</th>
  			<th>是否提交</th>
  		<th>分数</th>
  		</tr>
  		 
  		<%----%> <c:forEach items="${resultList}"  var="result">
     	<tr>
  			<td>${result.stuId}</td>
  			<td>${result.taskId}</td>
  			<td>${result.submit}</td>
  			<td>${result.score}</td>
  		</tr>
     </c:forEach> 
  	</table>
	
</body>
</html>