<%@page pageEncoding="UTF-8" language="java"
        contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>车猫管理平台</title>

<div class="container">
    <form action="login" class="form-horizontal" method="post">
        <!--<div class="form-group">
            <img alt="" src="img/cm/cm.jpg">
        </div>-->
        <div class="form-group">
            <br>
            <br>
            <br>
            <br>
            <br>
        </div>
        <div class="form-group">
            <h4 class="text-center">
                <font size="10">管理登录</font>
            </h4>
        </div>
        <div class="form-group">
            <br>
            <br>
            <br>
        </div>
        <div class=" form-group">
            <label class="col-sm-4 control-label" for="accountNum">账号</label>

            <div class="col-sm-4">
                <input class="form-control" type="text" name="accountNum" id="accountNum" placeholder="请输入账号"
                       autocomplete="off" value="${accountNum }"/>
            </div>
            <c:if test="${not empty tip}">
                <span class="col-sm-2 alert-danger">${tip }</span>
            </c:if>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label" for="password">密码</label>

            <div class="col-sm-4">
                <input class="form-control" type="password" name="password" id="password" placeholder="请输入密码 "
                       autocomplete="off" value="${password }"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label" for="code">验证码</label>

            <div class="col-sm-1">
                <img src="code" alt="点击换一个验证码" title="点击换一个验证码" onclick="this.src='code?t='+new Date().getTime();"
                     style="cursor:pointer;"/>
            </div>
            <div class="col-sm-3">
                <input class="form-control input-small" type="text" name="code" id="code" placeholder="请输入验证码 "
                       autocomplete="off"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-4">
                <button type="submit" class="btn btn-primary">登录</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        if ($('#accountNum').val() == '')
            $('#accountNum').focus();
        else if ($('#password').val() == '')
            $('#password').focus();
        else if ($('#code').val() == '')
            $('#code').focus();
    });
</script>
