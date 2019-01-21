<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <title>资金流水数据</title>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">资金流水数据</li>
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

<hr/>
<c:choose>
    <c:when test="${not empty moneyFlowStat}">
        <table class="table table-striped table-bordered table-condensed table-hover">
            <colgroup>
                <col width="5%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="5%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="7%"/>
                <col width="6%"/>
            </colgroup>
            <tr>
                <th>现金</th>
                <th>银行</th>
                <th>poss机</th>
                <th>基本户</th>
                <th>房租平台广告</th>
                <th>按揭返点金额</th>
                <th>应收账款</th>
                <th>商品库存</th>
                <th>资产总计</th>
                <th>流动资产总计</th>
                <th>借款</th>
                <th>利息</th>
                <th>合作款</th>
                <th>负债</th>
                <th>车均库存</th>
            </tr>
            <tr>
                <td>${moneyFlowStat.cash}</td>
                <td>${moneyFlowStat.bank}</td>
                <td>${moneyFlowStat.poss}</td>
                <td>${moneyFlowStat.basic}</td>
                <td>${moneyFlowStat.house}</td>
                <td>${moneyFlowStat.backMoney}</td>
                <td>${moneyFlowStat.receivable}</td>
                <td>${moneyFlowStat.goods}</td>
                <td>${moneyFlowStat.all}</td>
                <td>${moneyFlowStat.all - moneyFlowStat.liabilities}</td>
                <td>${moneyFlowStat.loan}</td>
                <td>${moneyFlowStat.interest}</td>
                <td>${moneyFlowStat.cooperate}</td>
                <td>${moneyFlowStat.liabilities}</td>
                <td>${moneyFlowStat.stockTime}</td>
            </tr>
        </table>
    </c:when>
</c:choose>