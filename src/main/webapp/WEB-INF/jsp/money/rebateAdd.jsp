<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建新记录</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="moneyRebateView">房租平台广告管理</a> <span class="divider"></span></li>
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
    <form class="form" action="moneyRebateAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="carModel">车型：</label>
            <input class="form-control  col-xs-3" type="text" name="carModel" id="carModel" placeholder="请输入" value="${carModel}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="number">编号：</label>
            <input class="form-control  col-xs-3" type="text" name="number" id="number" placeholder="请输入" value="${number}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="loanDate">贷款日期：</label>
            <input class="form-control col-xs-2" type="text" name="loanDate" id="loanDate" value="${loanDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="billPerson">签单员：</label>
            <input class="form-control col-xs-2" type="text" name="billPerson" id="billPerson" value="${billPerson}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="loan">贷款金额：</label>
            <input class="form-control  col-xs-3" type="text" name="loan" id="loan" placeholder="请输入" value="${loan}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="interestRate">利率：</label>
            <input class="form-control  col-xs-3" type="text" name="interestRate" id="interestRate" placeholder="请输入" value="${interestRate}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="backMoney">返点金额：</label>
            <input class="form-control  col-xs-4" type="text" name="backMoney" id="backMoney" placeholder="请输入" value="${backMoney}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="abs">摘要：</label>
            <textarea rows="5" cols="" name="abs" id="abs" class="control-box col-xs-7"></textarea>
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
                <a href="moneyRebateView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    var abs = document.getElementById('abs');
    abs.innerHTML = '${abs}';
</script>