<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <title>按揭保险数据</title>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">按揭保险数据</li>
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
<div class="container">
    <form class="form-horizontal" action="statOtherData" method="get">
        <!--<div class="form-group">
            <div class="col-sm-2">
                <select class="form-control" name="searchKey" id="searchKey" onchange="show(this)">
                    <option value="">未选中</option>
                    <option value="purchasePerson">采购人</option>
                    <option value="salePerson">销售人</option>
                </select>
            </div>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="searchValue" id="searchValue" value="${searchValue }" placeholder="请输入,可选项" autocomplete="off"/>
            </div>
        </div>-->
        <div class="form-group">
            <label class="col-sm-2 control-label">时间段：</label>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="btime" id="btime" value="${btime }" onclick="new Calendar().show(this);" placeholder="开始日期" autocomplete="off"/>
            </div>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="etime" id="etime" value="${etime }" onclick="new Calendar().show(this);" placeholder="结束日期" autocomplete="off"/>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-2 form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-search icon-white"></i> 查询
                </button>
            </div>
        </div>
    </form>
</div>
<hr/>
<c:choose>
    <c:when test="${not empty otherStat}">
        <table class="table table-striped table-bordered table-condensed table-hover">
            <colgroup>
                <col width="25%"/>
                <col width="25%"/>
                <col width="25%"/>
                <col width="25%"/>
            </colgroup>
            <tr>
                <th>按揭公司占比</th>
                <th>强制险占比</th>
                <th>商业险占比</th>
                <th>续保押金占比</th>
            </tr>
            <tr>
                <td>
                    <c:forEach items="${otherStat.mortgageMap}" var="map">
                        <c:out value="${map.key}"></c:out>:<c:out value="${map.value}"></c:out><br>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${otherStat.qzxMap}" var="map">
                        <c:out value="${map.key}"></c:out>:<c:out value="${map.value}"></c:out><br>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${otherStat.syxMap}" var="map">
                        <c:out value="${map.key}"></c:out>:<c:out value="${map.value}"></c:out><br>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${otherStat.renewalMap}" var="map">
                        <c:out value="${map.key}"></c:out>:<c:out value="${map.value}"></c:out><br>
                    </c:forEach>
                </td>
            </tr>
        </table>
    </c:when>
</c:choose>

<script language="javascript">
    var searchKey = document.getElementById('searchKey');
    for (var i = 0; i < searchKey.options.length; i++) {
        if (searchKey.options[i].value == '${searchKey}') {
            searchKey.options[i].selected = true;
        }
    }
</script>