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
  <div class="panel-head"><strong><span class="icon-key"></span> 修改会员密码</strong></div>
  <div class="body-content">
    <form method="post" class="form-x" action="modifyStuPassword">
       
      <div class="form-group">
        <div class="label">
          <label for="sitename">原始密码：</label>
        </div>
        <div class="field">
        <input id="info" type="text" style="display:none" data-stuname="${stuName}" data-teaname="${teaName}" name="id" value="${id}">
          <input type="password" class="input w50" id="oldPwd" name="oldPwd" size="10" placeholder="请输入原始密码" data-validate="required:请输入原始密码" />       
        </div>
      </div>      
      <div class="form-group">
        <div class="label">
          <label for="sitename">新密码：</label>
        </div>
        <div class="field">
          <input type="password" class="input w50" name="newPwd" size="10" placeholder="请输入新密码" data-validate="required:请输入新密码,length#>=5:新密码不能小于5位,length#<=10:新密码不能大于10位" />         
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label for="sitename">确认新密码：</label>
        </div>
        <div class="field">
          <input type="password" class="input w50" name="reNewPwd" size="10" placeholder="请再次输入新密码" data-validate="required:请再次输入新密码,repeat#newPwd:两次输入的密码不一致" />          
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button class="button bg-main icon-check-square-o" type="submit"> 提交</button>   
        </div>
      </div>      
    </form>
  </div>
</div>
<script type="text/javascript">
	$("#oldPwd").blur(function(){
		var stuName = $("#info").attr("data-stuname");
		var teaName = $("#info").attr("data-teaname");
		if(stuName != ""){
			$(".form-x").attr("action","modifyStuPassword");
		}else if(teaName != ""){
			$(".form-x").attr("action","modifyTeaPassword");
		}else{
			top.location="./";
		}	
	})
	

</script>

</body></html>