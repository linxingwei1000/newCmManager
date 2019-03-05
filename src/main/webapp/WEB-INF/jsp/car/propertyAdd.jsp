<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建新属性</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="carPropertyView">车辆属性</a> <span class="divider"></span></li>
    <li class="active">创建新属性</li>
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
    <form action="carPropertyAction" method="post" class="form-horizontal">
        <hr/>
        <div class="form-group">
            <label class="control-label col-xs-2">属性名：</label>
            <div class="col-xs-10">
                <div class="form-group">
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_LINE" <c:if test="${ propertyKey == 'CAR_LINE' }">checked="checked"</c:if>/>车系
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_LEVEL" <c:if test="${ propertyKey == 'CAR_LEVEL' }">checked="checked"</c:if>/>车级
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_CHANNEL" <c:if test="${ propertyKey == 'CAR_CHANNEL' }">checked="checked"</c:if>/>来源渠道
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_TAKE_TYPE" <c:if test="${ propertyKey == 'CAR_TAKE_TYPE' }">checked="checked"</c:if>/>提车方式
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_STATUS" <c:if test="${ propertyKey == 'CAR_STATUS' }">checked="checked"</c:if>/>车况
                    </label>
                </div>
                <div class="form-group">
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_PURCHASE_TYPE" <c:if test="${ propertyKey == 'CAR_PURCHASE_TYPE' }">checked="checked"</c:if>/>采购类别
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_CONSUMER_PROPERTY" <c:if test="${ propertyKey == 'CAR_CONSUMER_PROPERTY' }">checked="checked"</c:if>/>客户属性
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CAR_CONSUMER_RESOURCE" <c:if test="${ propertyKey == 'CAR_CONSUMER_RESOURCE' }">checked="checked"</c:if>/>获客渠道
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="CONSUMER_GENERATION" <c:if test="${ propertyKey == 'CONSUMER_GENERATION' }">checked="checked"</c:if>/>客户年龄段
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="BUSINESS_TYPE" <c:if test="${ propertyKey == 'BUSINESS_TYPE' }">checked="checked"</c:if>/>保险业务类型
                    </label>
                </div>
                <div class="form-group">
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="MONEY_RECORD_COST" <c:if test="${ propertyKey == 'MONEY_RECORD_COST' }">checked="checked"</c:if>/>成本录入
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="MONEY_RECORD_SALE" <c:if test="${ propertyKey == 'MONEY_RECORD_SALE' }">checked="checked"</c:if>/>销售录入
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="MONEY_RECORD_SALE" <c:if test="${ propertyKey == 'MONEY_RECORD_SALE' }">checked="checked"</c:if>/>销售录入
                    </label>
                    <label class="col-xs-2">
                        <input type="radio" name="propertyKey" value="BANK_NAME" <c:if test="${ propertyKey == 'BANK_NAME' }">checked="checked"</c:if>/>银行名称
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-xs-2" for="propertyValue">属性值：</label>
            <div class="col-xs-4">
                <input class="form-control" type="text" name="propertyValue" id="propertyValue" placeholder="请输入" value="${propertyValue}" autocomplete="off"/>
            </div>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label col-xs-2">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="carPropertyView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>