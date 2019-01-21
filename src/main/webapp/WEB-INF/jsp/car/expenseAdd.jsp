<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>保险信息</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="carSaleView">在售库存</a> <span class="divider"></span></li>
    <li class="active">保险信息</li>
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
    <form class="form" action="carExpenseAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="expenseCompany">保险公司：</label>
            <input class="form-control col-xs-3" type="text" name="expenseCompany" id="expenseCompany" value="${expenseCompany}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="expenseRebate">保险返点：</label>
            <input class="form-control  col-xs-3" type="text" name="expenseRebate" id="expenseRebate" placeholder="请输入" value="${expenseRebate}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="businessExpenseFee">商业保险费：</label>
            <input class="form-control  col-xs-3" type="text" name="businessExpenseFee" id="businessExpenseFee" placeholder="请输入" value="${businessExpenseFee}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="forceExpenseFee">强制保险费：</label>
            <input class="form-control col-xs-2" type="text" name="forceExpenseFee" id="forceExpenseFee" value="${forceExpenseFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="allUnearnedInsurance">实收保费总金额：</label>
            <input class="form-control  col-xs-3" type="text" name="allUnearnedInsurance" id="allUnearnedInsurance" placeholder="请输入" value="${allUnearnedInsurance}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="payCompanyFee">支付保险公司金额：</label>
            <input class="form-control  col-xs-3" type="text" name="payCompanyFee" id="payCompanyFee" placeholder="请输入" value="${payCompanyFee}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label  col-xs-2 ">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="carSaleView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>