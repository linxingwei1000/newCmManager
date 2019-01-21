<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建新记录</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="moneyHouseView">房租平台广告管理</a> <span class="divider"></span></li>
    <li class="active">创建新记录</li>
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
    <form class="form" action="moneyHouseAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionPerson">项目：</label>
            <input class="form-control  col-xs-4" type="text" name="actionPerson" id="actionPerson" placeholder="请输入" value="${actionPerson}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionDate">起始日期：</label>
            <input class="form-control col-xs-2" type="text" name="actionDate" id="actionDate" value="${actionDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="actionEndDate">截至日期：</label>
            <input class="form-control col-xs-2" type="text" name="actionEndDate" id="actionEndDate" value="${actionEndDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>

        </div>

        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionFee">金额：</label>
            <input class="form-control  col-xs-4" type="text" name="actionFee" id="actionFee" placeholder="请输入" value="${actionFee}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="actionDesc">事由：</label>
            <textarea rows="5" cols="" name="actionDesc" id="actionDesc" class="control-box col-xs-7"></textarea>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="moneyHouseView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    var actionDesc = document.getElementById('actionDesc');
    actionDesc.innerHTML = '${actionDesc}';
</script>