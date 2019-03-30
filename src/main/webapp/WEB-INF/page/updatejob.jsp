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

<link rel="stylesheet" href="${ctx}/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css">

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/pintuer.js"></script>

<script src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"></script>

</head>

<body>

<i id="i_info" data-info="${teaName}" style="display: none;"></i>

<div class="panel admin-panel">
  <div class="panel-head" id="add"><strong><span class="icon-pencil-square-o"></span>修改作业</strong></div>
  <div class="body-content">
    <form method="post" class="form-x" action="updatetask" enctype="multipart/form-data">
      <div class="form-group">
        <div class="label">
          <label>作业名：</label>
        </div>
        <div class="field">
        <input type="text" name="taskId" style="display: none;" class="input w50" value="${taskId}" />
          <input type="text" name="taskName" class="input w50" value="${taskName}"  data-validate="required:请输入作业名" />
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>题目内容：</label>
        </div>
        <div class="field">
          <input type="file"  class="input w50" multiple="multiple" name="uploadfile">
        </div>
      </div>


      <div class="form-group">
        <div class="label">
          <label>截止时间：</label>
        </div>
        <div class="input-append date form_datetime">
          <input class="input w50" value="${taskExpiry}" data-validate="required:请选择时间"
                 type="datetime" style="cursor: text;background-color:white;text-align:center"
                 name="taskExpiry">
          <span class="add-on"><i class="icon-th"></i></span>
        </div>
      </div>

      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
          <button class="button bg-main icon-check-square-o" type="submit" onclick="return check();" >修改提交</button>
        </div>
      </div>
    </form>
  </div>
</div>


<script type="text/javascript">

//验证session是否有效
/* {

	var name = $("#i_info").attr("data-info");
	if(name==""){
		top.location="./";
	}

} */

    $(".form_datetime").datetimepicker({
      format: "yyyy-mm-dd hh:ii:ss",
      autoclose: true,
      todayBtn: true,
      language:'zh-CN',
      pickerPosition:"bottom-left"
    });
    function check(){
    	if($(":file").val()==""){
    		return true;
    	}else{
    		return confirm("由于您选择了文档，本次操作会导致已提交过的文档被覆盖，是否继续？");
    	}
    }
  </script>
</body>
</html>