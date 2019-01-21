<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>订金寻车录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="carDepositView">订金寻车</a> <span class="divider"></span></li>
    <li class="active">录入</li>
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
    <form class="form" action="carDepositAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="depositDate">*收订金日期：</label>
            <input class="form-control col-xs-2 " type="text" name="depositDate" id="depositDate" value="${depositDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="salePerson">*销售员：</label>
            <input class="form-control  col-xs-4" type="text" name="salePerson" id="salePerson" placeholder="请输入" value="${salePerson}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carModel">车型：</label>
            <input class="form-control  col-xs-3" type="text" name="carModel" id="carModel" placeholder="请输入" value="${carModel}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carColor">颜色：</label>
            <input class="form-control  col-xs-3" type="text" name="carColor" id="carColor" placeholder="请输入" value="${carColor}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carYear">年份：</label>
            <input class="form-control  col-xs-3" type="text" name="carYear" id="carYear" placeholder="请输入" value="${carYear}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carConfig">配置：</label>
            <input class="form-control  col-xs-4" type="text" name="carConfig" id="carConfig" placeholder="请输入" value="${carConfig}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="giveCarDate">*预约交车日期：</label>
            <input class="form-control  col-xs-3" type="text" name="giveCarDate" id="giveCarDate" placeholder="请点击" value="${giveCarDate}"  onclick="new Calendar().show(this);" autocomplete="off"/>
            <div class="col-xs-1"></div>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="budget">预算：</label>
            <input class="form-control  col-xs-3" type="text" name="budget" id="budget" placeholder="请输入" value="${budget}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="deposit">订金：</label>
            <input class="form-control  col-xs-3" type="text" name="deposit" id="deposit" placeholder="请输入" value="${deposit}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="recordStatus" id="recordStatus" value="${recordStatus}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label  col-xs-2 ">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="carDepositView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>