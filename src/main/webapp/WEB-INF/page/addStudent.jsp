<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="${ctx}/css/pintuer.css">
<link rel="stylesheet" href="${ctx}/css/admin.css">
<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>导入学生名单</strong></div>
  <div class="body-content">
    <form method="post" class="form-x" action="importStudent" enctype="multipart/form-data">  
      
     <div class="form-group">
        <div class="label">
          <label>请选择学生名单：</label>
        </div>
        <div class="field">
          <input type="file" class="input w50" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="uploadfile" multiple="multiple"/>
          
        </div>
      </div>
  
      
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button disabled="disabled" class="button bg-main icon-check-square-o" type="submit"> 提交</button>
        </div>
      </div>
    </form>
  </div>
</div>
<script type="text/javascript">
$(":file").change(function(){
	var button = $(":submit");
	if($(this).val()!=""){
		button.removeAttr("disabled");
	}else{
		button.attr("disabled","disabled");
	}		
})
</script>
</body></html>