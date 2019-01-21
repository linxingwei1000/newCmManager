<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <title>部门管理</title>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">部门管理</li>
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

<p><a href="userDepartmentAction?action=1" class="btn btn-primary"><i class="icon-plus icon-white"></i> 创建新部门</a></p>

<c:choose>
    <c:when test="${empty dataList }">
        <div class="alert alert-warning">部门列表为空</div>
    </c:when>
    <c:otherwise>

        <table class="table table-striped table-bordered table-condensed table-hover">
            <colgroup>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="10%"/>
            </colgroup>
            <tr>
                <th>部门名称</th>
                <th>行政页面权限</th>
                <th>车辆页面权限</th>
                <th>保险页面权限</th>
                <th>按揭页面权限</th>
                <th>新车页面权限</th>
                <th>资金页面权限</th>
                <th>统计页面权限</th>
                <th>档案页面权限</th>
                <th>操作</th>
            </tr>
            <c:forEach var="da" items="${dataList }" varStatus="status">
                <tr>
                    <td>${da.departmentName}</td>
                    <td>
                        <c:if test="${da.accountAble == 1}">有</c:if>
                        <c:if test="${da.accountAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.carAble == 1}">有</c:if>
                        <c:if test="${da.carAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.insuranceAble == 1}">有</c:if>
                        <c:if test="${da.insuranceAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.mortgageAble == 1}">有</c:if>
                        <c:if test="${da.mortgageAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.newCarAble == 1}">有</c:if>
                        <c:if test="${da.newCarAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.moneyAble == 1}">有</c:if>
                        <c:if test="${da.moneyAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.statAble == 1}">有</c:if>
                        <c:if test="${da.statAble == 0}">否</c:if>
                    </td>
                    <td>
                        <c:if test="${da.dossierAble == 1}">有</c:if>
                        <c:if test="${da.dossierAble == 0}">否</c:if>
                    </td>
                    <td>
                        <a href="userDepartmentAction?id=${da.id }&action=2"><i class="icon-lock"></i> 编辑</a>
                        <a href="#confirmDialog" data-toggle="modal"
                           data-url="userDepartmentDelete?id=${da.id }" data-title="删除部门"
                           data-tip="确认要删除部门【${da.departmentName }】嘛？" class="confirm-trigger"><i
                                class="icon-trash"></i> 删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>


<%-- 删除确认 --%>
<div id="confirmDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class=" modal-body-inner"></div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary confirm-do-trigger">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        /*确认*/
        $('.confirm-trigger').click(function () {
            var self = $(this);
            var tip = self.attr('data-tip');
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.modal-body-inner').html(tip);
            $('.confirm-do-trigger').click(function () {
                document.location.href = url;
            });
        });
        Global.setNavActive("2");
    });
</script>