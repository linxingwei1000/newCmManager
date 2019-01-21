<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>新工资录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="userDepartment">部门管理</a> <span class="divider"></span></li>
    <li class="active">新工资录入</li>
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
    <form class="form" action="userWagesAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="staff">员工：</label>
            <input class="form-control  col-xs-4" type="text" name="staff" id="staff" placeholder="请输入" value="${staff}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="soldDate">转已售截至日期：</label>
            <input class="form-control col-xs-2 " type="text" name="soldDate" id="soldDate" value="${soldDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="stime">月份开始日期：</label>
            <input class="form-control col-xs-2 " type="text" name="stime" id="stime" value="${stime}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="etime">月份结束日期：</label>
            <input class="form-control col-xs-2 " type="text" name="etime" id="etime" value="${etime}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="basePay">基本工资：</label>
            <input class="form-control  col-xs-3" type="text" name="basePay" id="basePay" value="${basePay}" placeholder="请输入数字" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="compassionateLeave">事假：</label>
            <input class="form-control  col-xs-3" type="text" name="compassionateLeave" id="compassionateLeave" value="${compassionateLeave}" placeholder="请输入数字"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="late">迟到：</label>
            <input class="form-control  col-xs-3" type="text" name="late" id="late" value="${late}" placeholder="请输入数字" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="unHitCard">未打卡：</label>
            <input class="form-control  col-xs-3" type="text" name="unHitCard" id="unHitCard" value="${unHitCard}" placeholder="请输入数字"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="qualityCommission">质保提成：</label>
            <input class="form-control  col-xs-3" type="text" name="qualityCommission" id="qualityCommission" value="${qualityCommission}" placeholder="请输入数字" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="authCommission">认证提成：</label>
            <input class="form-control  col-xs-3" type="text" name="authCommission" id="authCommission" value="${authCommission}" placeholder="请输入数字"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="depositCommission">定金提成：</label>
            <input class="form-control  col-xs-3" type="text" name="depositCommission" id="depositCommission" value="${depositCommission}" placeholder="请输入数字" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="billCommission">签单提成：</label>
            <input class="form-control  col-xs-3" type="text" name="billCommission" id="billCommission" value="${billCommission}" placeholder="请输入数字"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="insuranceCommission">保险提成：</label>
            <input class="form-control  col-xs-3" type="text" name="insuranceCommission" id="insuranceCommission" value="${insuranceCommission}" placeholder="请输入数字" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="newCarCommission">新车销售提成：</label>
            <input class="form-control  col-xs-3" type="text" name="newCarCommission" id="newCarCommission" value="${newCarCommission}" placeholder="请输入数字"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="mealSupplement">餐补：</label>
            <input class="form-control  col-xs-3" type="text" name="mealSupplement" id="mealSupplement" value="${mealSupplement}" placeholder="请输入数字" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="otherSupplement">其他补贴：</label>
            <input class="form-control  col-xs-3" type="text" name="otherSupplement" id="otherSupplement" value="${otherSupplement}" placeholder="请输入数字"  autocomplete="off"/>
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
                <a href="userWagesView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>