<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>销售成本录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="carSaleView">在售库存</a> <span class="divider"></span></li>
    <li class="active">销售成本录入</li>
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
    <form class="form" action="carSfAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="transferFee">过户费：</label>
            <input class="form-control  col-xs-4" type="text" name="transferFee" id="transferFee" placeholder="请输入"
                   value="${transferFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="transferSubsidy">过户补贴：</label>
            <input class="form-control  col-xs-3" type="text" name="transferSubsidy" id="transferSubsidy" placeholder="请输入" value="${transferSubsidy }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="transferCrossingFee">过户过路费：</label>
            <input class="form-control  col-xs-3" type="text" name="transferCrossingFee" id="transferCrossingFee" placeholder="请输入" value="${transferCrossingFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="transferBillingFee">过户开票费：</label>
            <input class="form-control  col-xs-3" type="text" name="transferBillingFee" id="transferBillingFee" placeholder="请输入" value="${transferBillingFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="transferOilFee">过户加油费：</label>
            <input class="form-control  col-xs-3" type="text" name="transferOilFee" id="transferOilFee" placeholder="请输入" value="${transferOilFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="removeCard">拆牌拓印：</label>
            <input class="form-control  col-xs-3" type="text" name="removeCard" id="removeCard" placeholder="请输入" value="${removeCard }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="rubbing">其他费用：</label>
            <input class="form-control  col-xs-3" type="text" name="rubbing" id="rubbing" placeholder="请输入" value="${rubbing }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="cattleFee">过户黄牛费：</label>
            <input class="form-control  col-xs-3" type="text" name="cattleFee" id="cattleFee" placeholder="请输入" value="${cattleFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">是否产生售后服务基金：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="isProduce" value="1" <c:if test="${ isProduce == 1 }">checked="checked"</c:if>/>是</label>
            <label class="radio inline col-xs-2"><input type="radio" name="isProduce" value="0" <c:if test="${ isProduce == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="recordId" id="recordId" value="${recordId}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="carSaleView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>