<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
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

	<form method="post" action="">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 成绩导出</strong>
			</div>
			<div class="padding border-bottom">
				<ul class="search">
					<li>
						<button type="button" class="button border-green" id="checkall">
							<span class="icon-check"></span> 全选
						</button>&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:;"
						class="button border-yellow" id="export"> <span
							class="icon-reply">导出</span>
					</a>

					</li>

				</ul>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th width="120">序号</th>
					<th>作业名</th>
				</tr>
				<c:forEach items="${taskList}" var="task" varStatus="status">
					<tr>
						<td><input type="checkbox" name="id[]" value="${task.taskId}" />
							${status.count}</td>
						<td>${task.taskName}</td>
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
	
		/* $("#checkall").click(function(){ 
		 $("input[name='id[]']").each(function(){
		 if (this.checked) {
		 this.checked = false;
		 }
		 else {
		 this.checked = true;
		 }
		 });
		 })  */
		//验证session是否有效
/* 		 {

		 	var name = $("#i_info").attr("data-info");
		 	if(name==""){
		 		top.location="./";
		 	}
		 	
		 }
		  */
		 
		$("#checkall").click(function() {
			if ($("input[name='id[]']").is(":checked")) {
				
				$("input[name='id[]']").prop('checked',false);
			} else {
				
				$("input[name='id[]']").prop('checked',true);
			}
		})
		
		$("#export").click(function(){
			var idsStr = "";
			$("input[name='id[]']").each(function(){
				if (this.checked) {
					 idsStr += this.value+",";
				}
					 
			 });
			if(idsStr==""){
				alert("您还未选择作业，请选择作业后再导出！");
			}else{
				idsStr = idsStr.substr(0,idsStr.lastIndexOf(","));
				window.location.href="download/results/"+idsStr;
			}
			/* $.ajax({
				type:"get",
				url:"download/results/"+idsStr,
				success:function(){},
				error:function(str1,str2,str3){
					alert("Sorry,下载出错！"+str1+str2);
				}
			}); */
		})
	</script>

</body>
</html>