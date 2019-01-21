<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>金融方案添加</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="newCarFinanceView">新车金融方案</a> <span class="divider"></span></li>
    <li class="active">金融方案添加</li>
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

<div class="container">
    <form class="form" action="newCarFinanceAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="financeCompany">金融公司：</label>
            <input class="form-control  col-xs-4" type="text" name="financeCompany" id="financeCompany" placeholder="请输入" value="${financeCompany}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carBrand">车辆品牌：</label>
            <input class="form-control  col-xs-4" type="text" name="carBrand" id="carBrand" placeholder="请输入" value="${carBrand}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carModel">车型：</label>
            <input class="form-control col-xs-2 " type="text" name="carModel" id="carModel" placeholder="请输入" value="${carModel}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carConfig">车配置：</label>
            <input class="form-control  col-xs-4" type="text" name="carConfig" id="carConfig" placeholder="请输入" value="${carConfig}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="guidancePrice">厂家指导价：</label>
            <input class="form-control col-xs-2 " type="text" name="guidancePrice" id="guidancePrice"  placeholder="请输入" value="${guidancePrice}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="downPayments">首付：</label>
            <input class="form-control  col-xs-4" type="text" name="downPayments" id="downPayments" placeholder="请输入" value="${downPayments}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="monthMortgage">月供：</label>
            <input class="form-control col-xs-2 " type="text" name="monthMortgage" id="monthMortgage" placeholder="请输入" value="${monthMortgage}"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="serviceFee">服务费：</label>
            <input class="form-control  col-xs-4" type="text" name="serviceFee" id="serviceFee" placeholder="请输入" value="${serviceFee}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="otherFee">其他费用：</label>
            <input class="form-control col-xs-2 " type="text" name="otherFee" id="otherFee" value="${otherFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="consumerName">客户姓名：</label>
            <input class="form-control  col-xs-4" type="text" name="consumerName" id="consumerName" placeholder="请输入" value="${consumerName}"  autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="consumerPhone">客户电话：</label>
            <input class="form-control  col-xs-4" type="text" name="consumerPhone" id="consumerPhone" placeholder="请输入" value="${consumerPhone}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 ">客户性别：</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerSex" value="1" <c:if test="${ consumerSex == 1 }">checked="checked"</c:if>/>男</label>
            <label class="radio inline col-xs-2"><input type="radio" name="consumerSex" value="2" <c:if test="${ consumerSex == 2 }">checked="checked"</c:if>/>女</label>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="consumerAddress">客户居住地：</label>
            <input class="form-control col-xs-2 " type="text" name="consumerAddress" id="consumerAddress" value="${consumerAddress}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <div class="col-xs-2"></div>
            <select class="form-control  col-xs-2" name="consumerAge" id="consumerAge" onchange="">
                <option value="0">客户年龄段</option>
                <c:forEach var="cp" items="${CONSUMER_GENERATION}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="consumerProperty" id="consumerProperty" onchange="">
                <option value="0">客户属性</option>
                <c:forEach var="cp" items="${CAR_CONSUMER_PROPERTY}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="consumerResource" id="consumerResource" onchange="">
                <option value="0">获客渠道</option>
                <c:forEach var="cp" items="${CAR_CONSUMER_RESOURCE}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
            <select class="form-control  col-xs-2" name="purchaseUse" id="purchaseUse" onchange="">
                <option value="0">购车用途</option>
                <c:forEach var="cp" items="${CAR_PURCHASE_USE}" varStatus="status">
                    <option value="${cp.id}">${cp.propertyValue}</option>
                </c:forEach>
            </select>
        </div>
        <hr/>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label  col-xs-2 ">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="newCarFinanceView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">

    var consumerAge = document.getElementById('consumerAge');
    for (var i = 0; i < consumerAge.options.length; i++) {
        if (consumerAge.options[i].value == '${consumerAge}') {
            consumerAge.options[i].selected = true;
        }
    }

    var consumerProperty = document.getElementById('consumerProperty');
    for (var i = 0; i < consumerProperty.options.length; i++) {
        if (consumerProperty.options[i].value == '${consumerProperty}') {
            consumerProperty.options[i].selected = true;
        }
    }

    var consumerResource = document.getElementById('consumerResource');
    for (var i = 0; i < consumerResource.options.length; i++) {
        if (consumerResource.options[i].value == '${consumerResource}') {
            consumerResource.options[i].selected = true;
        }
    }

    var purchaseUse = document.getElementById('purchaseUse');
    for (var i = 0; i < purchaseUse.options.length; i++) {
        if (purchaseUse.options[i].value == '${purchaseUse}') {
            purchaseUse.options[i].selected = true;
        }
    }
</script>