<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <title>自定义公式</title>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
    <style>
        .operation{
            font-size: 20px;
            color: #a94442;
        }
    </style>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">自定义公式</li>
</ul>

<c:if test="${not empty tip }">
    <c:choose>
        <c:when test="${fn:startsWith(requestScope.tip,'ok') }">
            <div class="alert alert-success">${fn:substringAfter(requestScope.tip, 'ok') }</div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-error">${requestScope.tip }</div>
        </c:otherwise>
    </c:choose>
</c:if>

<%-- redirect响应提示 --%>
<c:if test="${not empty param.tip }">
    <%
        String tip = java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("tip"), "utf-8"), "utf-8");
        if (org.apache.commons.lang3.StringUtils.startsWith(tip, "ok")) {
    %>
    <div class="alert alert-success"><%=tip.substring(2) %>
    </div>
    <%
    } else {
    %>
    <div class="alert alert-error"><%=tip %>
    </div>
    <%
        }
    %>
</c:if>

<div class="container">
    <form class="form" action="" method="post">
        <hr/>
        <div class="alert alert-info">
            点击属性直接添加，不需要的话，点击下面公式中的属性直接删除；请在输入框中输入数字
        </div>
        <hr/>
        <div class="form-group form-inline row">
            <span onclick="divClick(this)" style="color: #0092DC">全款权限底价&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #0000FF">按揭权限底价&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #002a80">销售毛利润&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #c0a16b">采购价&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #bf800c">销售价&nbsp;&nbsp;</span>
        </div>
        <div class="form-group form-inline row">
            <span onclick="divClick(this)" style="color: #003399">+&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #003399">-&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #003399">*&nbsp;&nbsp;</span>
            <span onclick="divClick(this)" style="color: #003399">/&nbsp;&nbsp;</span>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-1">常量：</label>
            <input class="form-control  col-xs-3" type="text" placeholder="请输入" autocomplete="off" onblur="inputChange(this)"/>
        </div>
        <hr/>
        <div class="form-group form-inline row" id="formula">自定义计算公式=</div>
        <div class="form-group form-inline row">
            <label class="control-label  col-xs-2 ">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="提交" class="btn btn-primary"/>
                <a href="carDepositView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    function divClick(a){
        addChild(a.innerHTML, a.getAttribute("style"));
    }

    function inputChange(a) {
        var value = a.value;
        if(value !== ""){
            addChild(value + "  ", "color: #002a80");
        }
    }

    function addChild(value, style) {
        var e = document.getElementById("formula");
        var child = document.createElement("span");
        child.setAttribute("style", style);
        child.setAttribute("onclick", "spanDelete(this)");
        child.innerHTML = value;
        e.appendChild(child);
    }
    
    function spanDelete(a) {
        var e = document.getElementById("formula");
        e.removeChild(a);
    }
</script>