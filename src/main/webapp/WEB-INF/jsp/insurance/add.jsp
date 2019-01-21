<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>保险录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="insuranceView">保险管理</a> <span class="divider"></span></li>
    <li class="active">保险录入</li>
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
    <form class="form" action="insuranceAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="consumerName">客户名：</label>
            <input class="form-control  col-xs-4" type="text" name="consumerName" id="consumerName" placeholder="请输入" value="${consumerName}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">性别：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerSex" value="1" <c:if test="${ consumerSex == 1 }">checked="checked"</c:if>/>男</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerSex" value="2" <c:if test="${ consumerSex == 2 }">checked="checked"</c:if>/>女</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="consumerBirth">生日：</label>
            <input class="form-control  col-xs-3" type="text" name="consumerBirth" id="consumerBirth" placeholder="请输入" value="${consumerBirth}" onclick="new Calendar().show(this);" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="consumerPhone">手机：</label>
            <input class="form-control col-xs-3" type="text" name="consumerPhone" id="consumerPhone" value="${consumerPhone}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">客户类别：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerType" value="1" <c:if test="${ consumerType == 1 }">checked="checked"</c:if>/>全款</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerType" value="2" <c:if test="${ consumerType == 2 }">checked="checked"</c:if>/>按揭</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerType" value="3" <c:if test="${ consumerType == 3 }">checked="checked"</c:if>/>外拓</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="carBrand">品牌：</label>
            <input class="form-control  col-xs-3" type="text" name="carBrand" id="carBrand" placeholder="请输入" value="${carBrand}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="carModel">车型：</label>
            <input class="form-control col-xs-3" type="text" name="carModel" id="carModel" value="${carModel}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="travelRegister">行驶证登记日期：</label>
            <input class="form-control col-xs-3" type="text" name="travelRegister" id="travelRegister" value="${travelRegister}" placeholder="请输入" onclick="new Calendar().show(this);" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="travelLssuing">行驶证发证日期：</label>
            <input class="form-control  col-xs-3" type="text" name="travelLssuing" id="travelLssuing" placeholder="请输入" value="${travelLssuing}" onclick="new Calendar().show(this);" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="businessPerson">业务员：</label>
            <input class="form-control  col-xs-3" type="text" name="businessPerson" id="businessPerson" placeholder="请输入" value="${businessPerson}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2" for="renewalFee">续保押金：</label>
            <input class="form-control col-xs-4" type="text" name="renewalFee" id="renewalFee" value="${renewalFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">续保押金处理：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="dealRenewal" value="1" <c:if test="${ dealRenewal == 1 }">checked="checked"</c:if>/>转收入</label>
            <label class="radio inline col-xs-2"><input type="radio" name="dealRenewal" value="2" <c:if test="${ dealRenewal == 2 }">checked="checked"</c:if>/>退还</label>
            <label class="radio inline col-xs-2"><input type="radio" name="dealRenewal" value="3" <c:if test="${ dealRenewal == 3 }">checked="checked"</c:if>/>暂存</label>
            <label class="radio inline col-xs-2"><input type="radio" name="dealRenewal" value="0" <c:if test="${ dealRenewal == 0 }">checked="checked"</c:if>/>无</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="renewalDesc">备注：</label>
            <textarea rows="5" cols="" name="renewalDesc" id="renewalDesc" class="control-box col-xs-7"></textarea>
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
                <a href="insuranceView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    var renewalDesc = document.getElementById('renewalDesc');
    renewalDesc.innerHTML = '${renewalDesc}';
</script>