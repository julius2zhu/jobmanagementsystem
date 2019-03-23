<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>登录</title>
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>
</head>
<body>
<div class="bg"></div>
<div class="container">
    <div class="line bouncein">
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height: 150px;"></div>
            <div class="media media-y margin-big-bottom"></div>
            <form id="loginForm" action="student" method="post">
                <div class="panel loginbox">
                    <div class="text-center margin-big padding-big-top">
                        <h1>作业管理系统</h1>
                    </div>
                    <div class="panel-body"
                         style="padding: 30px; padding-bottom: 10px; padding-top: 10px;">
                        <div class="C">
                            <div class="field field-icon-right" style="margin-bottom: 30px;">
                                <input type="text" class="input input-big" name="stuId"
                                       placeholder="登录账号" value="${id}"/> <span
                                    class="icon icon-user margin-small"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="field field-icon-right">
                                <input type="password" class="input input-big" name="stuPwd"
                                       placeholder="登录密码"/> <span
                                    class="icon icon-key margin-small"> </span>

                            </div>
                        </div>

                    </div>

                    <div class="formgroup"
                         style="padding: 30px; padding-bottom: 10px; padding-top: 10px;">
                        <div class="field">
                            <label><input name="pintuer" type="radio"
                                          value="student" checked="checked"/>学生 </label> <label><input
                                name="pintuer" type="radio" value="teacher"/>教师 </label>
                        </div>
                    </div>

                    <div style="padding: 30px;">
                        <input type="submit"
                               class="button button-block bg-main text-big input-big"
                               value="登录">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    var idFlag, pwdFlag = false;
    $(":radio[name='pintuer']").click(function () {
        var value = $(":radio[name=pintuer]:checked").val();
        if (value == "teacher") {
            $("#loginForm").attr("action", "teacher");
        } else {
            $("#loginForm").attr("action", "student");
        }
    })

    $(":text[name='stuId']")
        .blur(
            function () {
                var stuId = $(this).val();
                if (stuId == "") {
                    $(".C .field .input-help").remove();
                    $(".C .field")
                        .append(
                            "<div class='input-help'><ul><li></li></ul></div>");
                    $(".C .field .input-help li").text("");
                    $(".C .field .input-help li").text("请填写帐号");
                    idFlag = false;
                } else {
                    $(".C .field .input-help").remove();
                    idFlag = true;
                }
            })

    $(":text[name='stuId']").focus(function () {
        $(".C .field .input-help").remove();
    })

    $(":password[name='stuPwd']")
        .blur(
            function () {
                var stuPwd = $(this).val();
                if (stuPwd == "") {
                    $(".form-group").removeClass("check-success");
                    $(".form-group").addClass("check-error");
                    $(".form-group .field .input-help").remove();
                    $(".form-group .field")
                        .append(
                            "<div class='input-help'><ul><li></li></ul></div>");
                    $(".form-group .field .input-help li").text("");
                    $(".form-group .field .input-help li").text(
                        "请填写密码");
                    pwdFlag = false;
                } else {
                    $(".form-group").addClass("check-success");
                    $(".form-group").removeClass("check-error");
                    $(".form-group .field .input-help").remove();
                    pwdFlag = true;
                }
            })

    $(":password[name='stuPwd']").focus(function () {
        $(".form-group").addClass("check-success");
        $(".form-group").removeClass("check-error");
        $(".form-group .field .input-help").remove();
    })

    $("#loginForm")
        .submit(
            function () {
                $(":text[name='stuId']").blur();
                $(":password[name='stuPwd']").blur();
                var actionFlag = false;
                if (idFlag && pwdFlag) {
                    var type = $("#loginForm").attr("action");
                    $
                        .ajax({
                            type: "POST",
                            url: "checkpwd?type=" + type + "",
                            contentType: "application/json;charset=utf-8",
                            data: JSON
                                .stringify({
                                    "id": $(
                                        ":text[name='stuId']")
                                        .val(),
                                    "pwd": $(
                                        ":password[name='stuPwd']")
                                        .val()
                                }),
                            async: false,
                            dataType: "json",
                            success: function (result, status) {
                                var flag = result.flag;
                                if (flag == -1) {
                                    $(".C .field .input-help")
                                        .remove();
                                    $(".C .field")
                                        .append(
                                            "<div class='input-help'><ul><li></li></ul></div>");
                                    $(
                                        ".C .field .input-help li")
                                        .text("");
                                    $(
                                        ".C .field .input-help li")
                                        .text("帐号不存在");
                                    actionFlag = false;
                                } else if (flag == 0) {
                                    $(".form-group")
                                        .removeClass(
                                            "check-success");
                                    $(".form-group").addClass(
                                        "check-error");
                                    $(
                                        ".form-group .field .input-help")
                                        .remove();
                                    $(".form-group .field")
                                        .append(
                                            "<div class='input-help'><ul><li></li></ul></div>");
                                    $(
                                        ".form-group .field .input-help li")
                                        .text("");
                                    $(
                                        ".form-group .field .input-help li")
                                        .text("密码错误");
                                    actionFlag = false;
                                } else {
                                    actionFlag = true;
                                }
                                /*  */
                            },
                            error: function (str1, str2, str3) {
                                alert("网络出现问题！")
                            }
                        });
                }

                return actionFlag;
            })
</script>

</body>
</html>