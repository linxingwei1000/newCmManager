<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建费用信息</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <c:if test="${setupType == 12}">
        <li><a href="carStockView?recordStatus=2">车辆库存</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${setupType == 13}">
        <li><a href="carSaleView">在售车辆</a> <span class="divider"></span></li>
    </c:if>
    <li class="active">创建项目信息</li>
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
    <form class="form" action="carMoneyAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <table id="moneyTable" class="table table-striped table-bordered table-condensed table-hover">
                <tr id="tr1">
                    <th>费用类型</th>
                    <th>金额</th>
                    <th>描述</th>
                </tr>
                <tr id="tr2">
                    <td id="td1">
                        <c:if test="${setupType == 12}">
                            <select class="form-control  col-xs-2" name="moneyId_1" onchange="">
                                <option value="0">请下拉</option>
                                <c:forEach var="cp" items="${MONEY_RECORD_COST}" varStatus="status">
                                    <option value="${cp.id}">${cp.propertyValue}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${setupType == 13}">
                            <select class="form-control  col-xs-2" name="moneyId_1" onchange="">
                                <option value="0">请下拉</option>
                                <c:forEach var="cp" items="${MONEY_RECORD_SALE}" varStatus="status">
                                    <option value="${cp.id}">${cp.propertyValue}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                    </td>
                    <td id="td2">
                        <input class="form-control  col-xs-6" type="text" name="moneyFee_1" placeholder="请输入" autocomplete="off"/>
                    </td>
                    <td id="td3">
                        <input class="form-control  col-xs-6" type="text" name="moneyDesc_1" placeholder="请输入" autocomplete="off"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="form-group form-inline row">
            <input type="button" value="+(增加一行)" onclick="addRow()">
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="carRecordId" id="carRecordId" value="${carRecordId}" autocomplete="off"/>
            <input type="text" name="setupType" id="setupType" value="${setupType}" autocomplete="off"/>
            <input type="text" name="recordStatus" id="recordStatus" value="${recordStatus}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <c:if test="${setupType == 12}">
                    <a href="carStockView?recordStatus=2" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${setupType == 13}">
                    <a href="carSaleView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    //追加一行
    function addRow(){
        var tableNode = document.getElementById("moneyTable");
        var trNodeList = tableNode.getElementsByTagName("tr");
        var number = trNodeList.length;
        var trNode = document.createElement("tr");

        var array ="${jsCp}".split(";");
        var td1_select_moneyId = '<select class="form-control  col-xs-2" name="moneyId_' + number + '" onchange=""><option value="0">请下拉</option>';
        for(var i=0; i<array.length; i++){
            var cp = array[i].split(",");
            td1_select_moneyId += '<option value="'+ cp[0] +'">'+ cp[1] +'</option>';
        }
        td1_select_moneyId += '</select>';

        var td2_input_moneyFee = '<td><input class="form-control  col-xs-6" type="text" name="moneyFee_' + number + '" placeholder="请输入" autocomplete="off"></td>';
        var td3_input_moneyDesc = '<td><input class="form-control  col-xs-6"  type="text" name="moneyDesc_' + number + '" placeholder="请输入" autocomplete="off"></td>';
        tdText = td1_select_moneyId + td2_input_moneyFee + td3_input_moneyDesc;
        trNode.innerHTML = tdText;
        tableNode.appendChild(trNode);
    }
</script>