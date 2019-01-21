<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建项目信息</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <c:if test="${setupType == 1||setupType == 3}">
        <li><a href="carStockView?recordStatus=2">车辆库存</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${setupType == 2}">
        <li><a href="carSoldView">已售管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${setupType == 4}">
        <li><a href="carSaleView">在售车辆</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${setupType == 5||setupType == 6}">
        <li><a href="newCarView">新车管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${setupType == 7}">
        <li><a href="newCarFinanceView">新车金融方案</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${setupType == 8}">
        <li><a href="mortgageView?mortgageType=${mortgageType}">按揭管理</a> <span class="divider"></span></li>
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
    <form class="form" action="carSaleSetupAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <table id="setupTable" class="table table-striped table-bordered table-condensed table-hover">
                <tr id="tr1">
                    <th>项目</th>
                    <th>金额</th>
                </tr>
                <tr id="tr2">
                    <td id="td1">
                        <input class="form-control  col-xs-6" type="text" name="setupName_1" placeholder="请输入" autocomplete="off"/>
                    </td>
                    <td id="td2">
                        <input class="form-control  col-xs-6" type="text" name="setupFee_1" placeholder="请输入" autocomplete="off"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="form-group form-inline row">
            <input type="button" value="+(增加一行)" onclick="addRow()">
        </div>

        <!--<div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="setupName">项目：</label>
            <input class="form-control  col-xs-3" type="text" name="setupName" id="setupName" placeholder="请输入" value="${setupName}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="setupFee">金额：</label>
            <input class="form-control  col-xs-3" type="text" name="setupFee" id="setupFee" placeholder="请输入" value="${setupFee}" autocomplete="off"/>
        </div>-->
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="carCostId" id="carCostId" value="${carCostId}" autocomplete="off"/>
            <input type="text" name="setupType" id="setupType" value="${setupType}" autocomplete="off"/>
            <input type="text" name="recordStatus" id="recordStatus" value="${recordStatus}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <c:if test="${setupType == 1||setupType == 3}">
                    <a href="carStockView?recordStatus=2" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${setupType == 2}">
                    <a href="carSoldView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${setupType == 4}">
                    <a href="carSaleView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${setupType == 5||setupType == 6}">
                    <a href="newCarView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${setupType == 7}">
                    <a href="newCarFinanceView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${setupType == 8}">
                    <a href="mortgageView?mortgageType=${mortgageType}" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    //追加一行
    function addRow(){
        var tableNode = document.getElementById("setupTable");
        var trNodeList = tableNode.getElementsByTagName("tr");

        //得到最后一行(tr)的所有td
        var tdNodeList = trNodeList[trNodeList.length - 1].getElementsByTagName("td");
        var td_input_name = tdNodeList[0].getElementsByTagName("input")[0].name;
        var strIndex = td_input_name.indexOf("_");
        var number = td_input_name.substring(strIndex + 1);
        var trNode = document.createElement("tr");
        number++;

        var td1_input_setupName = '<td><input class="form-control  col-xs-6" type="text" name="setupName_' + number + '" placeholder="请输入" autocomplete="off"></td>';
        var td2_input_setupFee = '<td><input class="form-control  col-xs-6"  type="text" name="setupFee_' + number + '" placeholder="请输入" autocomplete="off"></td>';
        tdText = td1_input_setupName + td2_input_setupFee;
        trNode.innerHTML = tdText;
        tableNode.appendChild(trNode);
        //alert("追加一行");
    }
</script>