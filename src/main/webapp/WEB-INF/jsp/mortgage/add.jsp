<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>按揭录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="mortgageView?mortgageType=${mortgageType}"><c:if test="${mortgageType==1}">代办按揭管理</c:if><c:if test="${mortgageType==2}">外扩按揭管理</c:if></a> <span class="divider"></span></li>
    <li class="active">按揭录入</li>
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
    <form class="form" action="mortgageAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionPerson"><c:if test="${mortgageType==1}">业务员</c:if><c:if test="${mortgageType==2}">委托对象</c:if>：</label>
            <input class="form-control col-xs-3" type="text" name="actionPerson" id="actionPerson" placeholder="请输入" value="${actionPerson}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="consumerName">客户姓名：</label>
            <input class="form-control col-xs-3" type="text" name="consumerName" id="consumerName" placeholder="请输入" value="${consumerName}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="consumerPhone">客户手机：</label>
            <input class="form-control col-xs-3" type="text" name="consumerPhone" id="consumerPhone" placeholder="请输入" value="${consumerPhone}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="carBrand">品牌：</label>
            <input class="form-control  col-xs-3" type="text" name="carBrand" id="carBrand" placeholder="请输入" value="${carBrand}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="carModel">车型：</label>
            <input class="form-control col-xs-3" type="text" name="carModel" id="carModel" value="${carModel}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="mortgageCommissioner">对接按揭专员：</label>
            <input class="form-control  col-xs-3" type="text" name="mortgageCommissioner" id="mortgageCommissioner" placeholder="请输入" value="${mortgageCommissioner}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="mortgageCompany">按揭公司：</label>
            <input class="form-control col-xs-3" type="text" name="mortgageCompany" id="mortgageCompany" value="${mortgageCompany}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="loanFee">贷款金额：</label>
            <input class="form-control  col-xs-3" type="text" name="loanFee" id="loanFee" placeholder="请输入" value="${loanFee}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="interestRate">利率：</label>
            <input class="form-control col-xs-3" type="text" name="interestRate" id="interestRate" value="${interestRate}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="mortgageRebate">按揭返点：</label>
            <input class="form-control col-xs-3" type="text" name="mortgageRebate" id="mortgageRebate" value="${mortgageRebate}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="backFee">返还金额：</label>
            <input class="form-control  col-xs-3" type="text" name="backFee" id="backFee" placeholder="请输入" value="${backFee}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="assessmentFee">评估费：</label>
            <input class="form-control col-xs-3" type="text" name="assessmentFee" id="assessmentFee" value="${assessmentFee}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="renewalFee">续保押金：</label>
            <input class="form-control col-xs-3" type="text" name="renewalFee" id="renewalFee" value="${renewalFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <c:if test="${mortgageType==1}">
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="riskFee">风险金：</label>
            <input class="form-control  col-xs-3" type="text" name="riskFee" id="riskFee" value="${riskFee}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="padFee">垫资费：</label>
            <input class="form-control  col-xs-3" type="text" name="padFee" id="padFee" value="${padFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="doorFee">上门费：</label>
            <input class="form-control col-xs-3" type="text" name="doorFee" id="doorFee" value="${doorFee}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="stampDuty">印花税：</label>
            <input class="form-control  col-xs-3" type="text" name="stampDuty" id="stampDuty" value="${stampDuty}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="otherFee">其它费用：</label>
            <input class="form-control col-xs-3" type="text" name="otherFee" id="otherFee" value="${otherFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        </c:if>
        <c:if test="${mortgageType==2}">
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2" for="payBackFee">支付委托人返点金额：</label>
                <input class="form-control  col-xs-3" type="text" name="payBackFee" id="payBackFee" value="${payBackFee}" placeholder="请输入" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2" for="signBillFee">签单费：</label>
                <input class="form-control  col-xs-3" type="text" name="signBillFee" id="signBillFee" value="${signBillFee}" placeholder="请输入" autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2" for="overYearFee">超年限费：</label>
                <input class="form-control  col-xs-3" type="text" name="overYearFee" id="overYearFee" value="${padoverYearFeeFee}" placeholder="请输入" autocomplete="off"/>
            </div>
        </c:if>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="expenseCompany">保险公司：</label>
            <input class="form-control  col-xs-3" type="text" name="expenseCompany" id="expenseCompany" value="${expenseCompany}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="businessExpenseFee">商业险保费：</label>
            <input class="form-control col-xs-3" type="text" name="businessExpenseFee" id="businessExpenseFee" value="${businessExpenseFee}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="forceExpenseFee">强制险保费：</label>
            <input class="form-control  col-xs-3" type="text" name="forceExpenseFee" id="forceExpenseFee" value="${forceExpenseFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="businessExpenseBack">商业保险返点：</label>
            <input class="form-control  col-xs-3" type="text" name="businessExpenseBack" id="businessExpenseBack" value="${businessExpenseBack}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="businessExpenseRebate">商业险返点金额：</label>
            <input class="form-control col-xs-3" type="text" name="businessExpenseRebate" id="businessExpenseRebate" value="${businessExpenseRebate}" placeholder="请输入" autocomplete="off"/>
         </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="mortgageType" id="mortgageType" value="${mortgageType}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="mortgageView?mortgageType=${mortgageType}" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>