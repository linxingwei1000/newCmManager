<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>车辆成本录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <c:if test="${recordStatus==2}">
        <li><a href="carStockView?recordStatus=2">在店库存</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${recordStatus==3}">
        <li><a href="carSaleView">在售车辆</a> <span class="divider"></span></li>
    </c:if>
    <li class="active">车辆成本录入</li>
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
    <form class="form" action="carCostAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carPickPerson">提车经办人：</label>
            <input class="form-control  col-xs-4" type="text" name="carPickPerson" id="carPickPerson" placeholder="请输入"
                   value="${carPickPerson }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="mentionFee">提档费：</label>
            <input class="form-control  col-xs-3" type="text" name="mentionFee" id="mentionFee" placeholder="请输入" value="${mentionFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="mentionSubsidy">提档补贴：</label>
            <input class="form-control  col-xs-3" type="text" name="mentionSubsidy" id="mentionSubsidy" placeholder="请输入" value="${mentionSubsidy }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="crossingFee">提车过路费：</label>
            <input class="form-control  col-xs-3" type="text" name="crossingFee" id="crossingFee" placeholder="请输入" value="${crossingFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="travelFee">差旅费：</label>
            <input class="form-control  col-xs-3" type="text" name="travelFee" id="travelFee" placeholder="请输入" value="${travelFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="putFee">入档费：</label>
            <input class="form-control  col-xs-3" type="text" name="putFee" id="putFee" placeholder="请输入" value="${putFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="putSubsidy">入档补贴：</label>
            <input class="form-control  col-xs-3" type="text" name="putSubsidy" id="putSubsidy" placeholder="请输入" value="${putSubsidy }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="mailFee">邮递费：</label>
            <input class="form-control  col-xs-3" type="text" name="mailFee" id="mailFee" placeholder="请输入" value="${mailFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="freightFee">运费：</label>
            <input class="form-control  col-xs-3" type="text" name="freightFee" id="freightFee" placeholder="请输入" value="${freightFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="billingFee">提车开票费：</label>
            <input class="form-control  col-xs-3" type="text" name="billingFee" id="billingFee" placeholder="请输入" value="${billingFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="oilFee">提车加油费：</label>
            <input class="form-control  col-xs-3" type="text" name="oilFee" id="oilFee" placeholder="请输入" value="${oilFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="cattleFee">提车黄牛费：</label>
            <input class="form-control  col-xs-3" type="text" name="cattleFee" id="cattleFee" placeholder="请输入" value="${cattleFee }" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="expenseFee">保险费：</label>
            <input class="form-control  col-xs-3" type="text" name="expenseFee" id="expenseFee" placeholder="请输入" value="${expenseFee }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="otherFee">其他费用：</label>
            <input class="form-control  col-xs-3" type="text" name="otherFee" id="otherFee" placeholder="请输入" value="${otherFee }" autocomplete="off"/>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="recordStatus" id="recordStatus" value="${recordStatus}" autocomplete="off"/>
            <input type="text" name="recordId" id="recordId" value="${recordId}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <c:if test="${recordStatus==2}">
                    <a href="carStockView?recordStatus=2" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${recordStatus==3}">
                    <a href="carSaleView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>

            </div>
        </div>
    </form>
</div>