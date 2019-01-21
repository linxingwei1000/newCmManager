<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>采购录入</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="carPurchaseView">车辆买入</a> <span class="divider"></span></li>
    <li class="active">买入</li>
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
    <form class="form" action="carPurchaseAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="purchaseDate">*采购日期：</label>
            <input class="form-control col-xs-2 " type="text" name="purchaseDate" id="purchaseDate" value="${purchaseDate}" onclick="new Calendar().show(this);" placeholder="采购日期（必填)" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="purchasePerson">*采购人：</label>
            <input class="form-control  col-xs-4" type="text" name="purchasePerson" id="purchasePerson" placeholder="请输入采购人姓名" value="${purchasePerson}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="insidePerson">内部合伙人：</label>
            <input class="form-control  col-xs-3" type="text" name="insidePerson" id="insidePerson" placeholder="请输入" value="${insidePerson}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="insideProportion">内部合伙比例：</label>
            <input class="form-control  col-xs-3" type="text" name="insideProportion" id="insideProportion" placeholder="请输入" value="${insideProportion}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="outsidePerson">外部合伙人：</label>
            <input class="form-control  col-xs-3" type="text" name="outsidePerson" id="outsidePerson" placeholder="请输入" value="${outsidePerson}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="outsideProportion">外部合伙比例：</label>
            <input class="form-control  col-xs-3" type="text" name="outsideProportion" id="outsideProportion" placeholder="请输入" value="${outsideProportion}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carNewSale">新车指导价：</label>
            <input class="form-control  col-xs-3" type="text" name="carNewSale" id="carNewSale" placeholder="请输入" value="${carNewSale}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="hallMoney">展厅标价：</label>
            <input class="form-control  col-xs-3" type="text" name="hallMoney" id="hallMoney" placeholder="请输入" value="${hallMoney}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="qAuthorityMoney">全款权限底价：</label>
            <input class="form-control  col-xs-3" type="text" name="qAuthorityMoney" id="qAuthorityMoney" placeholder="请输入" value="${qAuthorityMoney}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="aAuthorityMoney">按揭权限底价：</label>
            <input class="form-control  col-xs-3" type="text" name="aAuthorityMoney" id="aAuthorityMoney" placeholder="请输入" value="${aAuthorityMoney}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="purchaseMoney">*采购价格：</label>
            <input class="form-control  col-xs-3" type="text" name="purchaseMoney" id="purchaseMoney" placeholder="请输入" value="${purchaseMoney}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="paidMoney">*已付金额：</label>
            <input class="form-control  col-xs-3" type="text" name="paidMoney" id="paidMoney" placeholder="请输入" value="${paidMoney}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="agencyFee">采购中介费：</label>
            <input class="form-control  col-xs-3" type="text" name="agencyFee" id="agencyFee" placeholder="请输入" value="${agencyFee}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carBrand">*车辆品牌：</label>
            <input class="form-control  col-xs-3" type="text" name="carBrand" id="carBrand" placeholder="请输入" value="${carBrand}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carModel">*车型：</label>
            <input class="form-control  col-xs-3" type="text" name="carModel" id="carModel" placeholder="请输入" value="${carModel}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="frameNum">车架号：</label>
            <input class="form-control  col-xs-4" type="text" name="frameNum" id="frameNum" placeholder="请输入车架号" value="${frameNum}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carDisplacement">排量：</label>
            <input class="form-control  col-xs-4" type="text" name="carDisplacement" id="carDisplacement" placeholder="请输入" value="${carDisplacement}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carConfig">配置：</label>
            <input class="form-control  col-xs-4" type="text" name="carConfig" id="carConfig" placeholder="请输入" value="${carConfig}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carResource">车源所在地：</label>
            <input class="form-control  col-xs-4" type="text" name="carResource" id="carResource" placeholder="请输入"
                   value="${carResource}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carNumResource">车牌所在地：</label>
            <input class="form-control  col-xs-4" type="text" name="carNumResource" id="carNumResource" placeholder="请输入"
                   value="${carNumResource}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carCreateTime">出厂日期：</label>
            <input class="form-control  col-xs-4"  type="text" name="carCreateTime" id="carCreateTime" value="${carCreateTime}" onclick="new Calendar().show(this);" placeholder="出厂日期" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carPurchaseTime">入户日期：</label>
            <input class="form-control  col-xs-4"  type="text" name="carPurchaseTime" id="carPurchaseTime" value="${carPurchaseTime}" onclick="new Calendar().show(this);" placeholder="入户日期" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carOutColour">外观颜色：</label>
            <input class="form-control  col-xs-1" type="text" name="carOutColour" id="carOutColour" placeholder="请输入"
                   value="${carOutColour}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carInsideColour">内饰颜色：</label>
            <input class="form-control  col-xs-1" type="text" name="carInsideColour" id="carInsideColour" placeholder="请输入"
                   value="${carInsideColour}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="keyNum">*钥匙编号：</label>
            <input class="form-control  col-xs-3" type="text" name="keyNum" id="keyNum" placeholder="请输入" value="${keyNum}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carRunNum">公里数（万）：</label>
            <input class="form-control  col-xs-1" type="text" name="carRunNum" id="carRunNum" placeholder="如3.6这样输入" value="${carRunNum}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="isBath">是否批量：</label>
            <select class="form-control  col-xs-2" name="isBath" id="isBath" onchange="show(this)">
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
            <div class="col-xs-1"></div>
            <select class="form-control  col-xs-2" name="bathId" id="bathId" style="display: none" onchange="">
                <option value="0">未选中</option>
                <c:forEach var="cp" items="${CAR_BATH_IDS}" varStatus="status">
                    <option value="${cp.id}">${cp.bathName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group form-inline row">
            <div class="col-xs-2"></div>
            <select class="form-control  col-xs-2" name="carLine" id="carLine" onchange="">
                <option value="0">车系</option>
                <c:forEach var="cp" items="${CAR_LINE}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="carLevel" id="carLevel" onchange="">
                <option value="0">车辆类别</option>
                <c:forEach var="cp" items="${CAR_LEVEL}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="carChannel" id="carChannel" onchange="">
                <option value="0">车源渠道</option>
                <c:forEach var="cp" items="${CAR_CHANNEL}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="carTakeType" id="carTakeType" onchange="">
                <option value="0">提车方式</option>
                <c:forEach var="cp" items="${CAR_TAKE_TYPE}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="carStatus" id="carStatus" onchange="">
                <option value="0">车况</option>
                <c:forEach var="cp" items="${CAR_STATUS}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="purchaseType" id="purchaseType" onchange="">
                <option value="0">采购类别</option>
                <c:forEach var="cp" items="${CAR_PURCHASE_TYPE}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carStatusDesc">车况描述：</label>
            <textarea rows="5" cols="" name="carStatusDesc" id="carStatusDesc" class="control-box col-xs-7"></textarea>
        </div>
        <hr/>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="recordStatus" id="recordStatus" value="${recordStatus}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label  col-xs-2 ">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                    <c:if test="${recordStatus ==1}"><a href="carPurchaseView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a></c:if>
                    <c:if test="${recordStatus ==2}"><a href="carStockView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a></c:if>
            </div>
        </div>
    </form>
</div>

<script language="javascript">

    function show(obj) {
        var id = obj.id;
        if (id == "isBath") {
            if (obj.value == "1" ) {
                document.getElementById("bathId").style.display = "block";
            } else {
                document.getElementById("bathId").style.display = "none";
            }
        }
    }

    var carLine = document.getElementById('carLine');
    for (var i = 0; i < carLine.options.length; i++) {
        if (carLine.options[i].value == '${carLine}') {
            carLine.options[i].selected = true;
        }
    }

    var carLevel = document.getElementById('carLevel');
    for (var i = 0; i < carLevel.options.length; i++) {
        if (carLevel.options[i].value == '${carLevel}') {
            carLevel.options[i].selected = true;
        }
    }

    var carChannel = document.getElementById('carChannel');
    for (var i = 0; i < carChannel.options.length; i++) {
        if (carChannel.options[i].value == '${carChannel}') {
            carChannel.options[i].selected = true;
        }
    }

    var carTakeType = document.getElementById('carTakeType');
    for (var i = 0; i < carTakeType.options.length; i++) {
        if (carTakeType.options[i].value == '${carTakeType}') {
            carTakeType.options[i].selected = true;
        }
    }

    var carStatus = document.getElementById('carStatus');
    for (var i = 0; i < carStatus.options.length; i++) {
        if (carStatus.options[i].value == '${carStatus}') {
            carStatus.options[i].selected = true;
        }
    }

    var purchaseType = document.getElementById('purchaseType');
    for (var i = 0; i < purchaseType.options.length; i++) {
        if (purchaseType.options[i].value == '${purchaseType}') {
            purchaseType.options[i].selected = true;
        }
    }

    var isBath = document.getElementById('isBath');
    for (var i = 0; i < isBath.options.length; i++) {
        if (isBath.options[i].value == '${isBath}') {
            isBath.options[i].selected = true;
        }

        if (isBath.options[i].value == '1') {
            document.getElementById("bathId").style.display = "block";
        }

        if (isBath.options[i].value == '0') {
            document.getElementById("bathId").style.display = "none";
        }
    }

    var bathId = document.getElementById('bathId');
    for (var i = 0; i < bathId.options.length; i++) {
        if (bathId.options[i].value == '${bathId}') {
            bathId.options[i].selected = true;
        }
    }

    var carStatusDesc = document.getElementById('carStatusDesc');
    carStatusDesc.innerHTML = '${carStatusDesc}';
</script>