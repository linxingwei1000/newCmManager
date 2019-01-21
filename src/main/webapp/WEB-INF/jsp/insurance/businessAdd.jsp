<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>保险业务录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="insuranceView">保险管理</a> <span class="divider"></span></li>
    <li class="active">保险业务录入</li>
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
    <form class="form" action="insuranceBusinessAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="insuranceDate">投保日期：</label>
            <input class="form-control  col-xs-3" type="text" name="insuranceDate" id="insuranceDate" placeholder="请输入" value="${insuranceDate}" onclick="new Calendar().show(this);" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <div class="col-xs-2"></div>
            <select class="form-control  col-xs-2" name="businessType" id="businessType" onchange="">
                <option value="0">保险类型</option>
                <c:forEach var="cp" items="${BUSINESS_TYPE}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="qzxDate">强制险到期日期：</label>
            <input class="form-control  col-xs-3" type="text" name="qzxDate" id="qzxDate" placeholder="请输入" value="${qzxDate}" onclick="new Calendar().show(this);" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="syxDate">商业险到期日期：</label>
            <input class="form-control  col-xs-3" type="text" name="syxDate" id="syxDate" placeholder="请输入" value="${syxDate}" onclick="new Calendar().show(this);" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="qzxCompany">强制险保险公司：</label>
            <input class="form-control col-xs-3" type="text" name="qzxCompany" id="qzxCompany" value="${qzxCompany}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="syxCompany">商业险保险公司：</label>
            <input class="form-control col-xs-3" type="text" name="syxCompany" id="syxCompany" value="${syxCompany}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="qzxFee">强制险保费：</label>
            <input class="form-control col-xs-3" type="text" name="qzxFee" id="qzxFee" value="${qzxFee}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="syxFee">商业险保费：</label>
            <input class="form-control col-xs-3" type="text" name="syxFee" id="syxFee" value="${syxFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="syxDiscount">商业险折扣：</label>
            <input class="form-control col-xs-3" type="text" name="syxDiscount" id="syxDiscount" value="${syxDiscount}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="expenseRebate">保险返点：</label>
            <input class="form-control  col-xs-3" type="text" name="expenseRebate" id="expenseRebate" value="${expenseRebate}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="rebateFee">返点金额：</label>
            <input class="form-control col-xs-3" type="text" name="rebateFee" id="rebateFee" value="${rebateFee}" placeholder="请输入" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="returnFee">返还客户金额：</label>
            <input class="form-control col-xs-3" type="text" name="returnFee" id="returnFee" value="${returnFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="businessDesc">备注信息：</label>
            <input class="form-control col-xs-3" type="text" name="businessDesc" id="businessDesc" value="${businessDesc}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="insuranceId" id="insuranceId" value="${insuranceId}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="insuranceView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    var businessType = document.getElementById('businessType');
    for (var i = 0; i < businessType.options.length; i++) {
        if (businessType.options[i].value == '${businessType}') {
            businessType.options[i].selected = true;
        }
    }
</script>