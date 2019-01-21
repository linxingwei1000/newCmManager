<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建新人员</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="userDepartment">部门管理</a> <span class="divider"></span></li>
    <li class="active">创建新部门</li>
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
    <form class="form" action="userDepartmentAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="dname">部门名称：</label>
            <input class="form-control  col-xs-4" type="text" name="dname" id="dname" placeholder="请输入部门名称"
                   value="${dname }" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">行政页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="accountAble" value="1" <c:if test="${ accountAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="accountAble" value="0" <c:if test="${ accountAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">车辆管理页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="carAble" value="1" <c:if test="${ carAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="carAble" value="0" <c:if test="${ carAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">保险业务页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="insuranceAble" value="1" <c:if test="${ insuranceAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="insuranceAble" value="0" <c:if test="${ insuranceAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">按揭页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="mortgageAble" value="1" <c:if test="${ mortgageAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="mortgageAble" value="0" <c:if test="${ mortgageAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">新车业务页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="newCarAble" value="1" <c:if test="${ newCarAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="newCarAble" value="0" <c:if test="${ newCarAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">资金业务页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="moneyAble" value="1" <c:if test="${ moneyAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="moneyAble" value="0" <c:if test="${ moneyAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">统计页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="statAble" value="1" <c:if test="${ statAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="statAble" value="0" <c:if test="${ statAble == 0 }">checked="checked"</c:if>/>否</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">档案页面权限：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="dossierAble" value="1" <c:if test="${ dossierAble == 1 }">checked="checked"</c:if>/>有</label>
            <label class="radio inline col-xs-2"><input type="radio" name="dossierAble" value="0" <c:if test="${ dossierAble == 0 }">checked="checked"</c:if>/>否</label>
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
                <a href="userDepartment" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>