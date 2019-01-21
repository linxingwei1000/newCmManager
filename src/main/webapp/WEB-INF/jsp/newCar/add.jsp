<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>新车业务添加</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="newCarView">新车业务</a> <span class="divider"></span></li>
    <li class="active">新车业务添加</li>
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
    <form class="form" action="newCarAction" method="post">
        <hr/>
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
            <label class="control-label col-xs-2 " for="purchasePerson">采购员：</label>
            <input class="form-control  col-xs-4" type="text" name="purchasePerson" id="purchasePerson" placeholder="请输入" value="${purchasePerson}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="purchaseMoney">采购价格：</label>
            <input class="form-control col-xs-2 " type="text" name="purchaseMoney" id="purchaseMoney" placeholder="请输入" value="${purchaseMoney}"  autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="salePerson">销售员：</label>
            <input class="form-control  col-xs-4" type="text" name="salePerson" id="salePerson" placeholder="请输入" value="${salePerson}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="saleDate">销售日期：</label>
            <input class="form-control col-xs-2 " type="text" name="saleDate" id="saleDate" value="${saleDate}" onclick="new Calendar().show(this);" placeholder="销售日期（必填)" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="saleMoney">销售价格：</label>
            <input class="form-control  col-xs-3" type="text" name="saleMoney" id="saleMoney" placeholder="请输入" value="${saleMoney}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="agencyFee">中介费：</label>
            <input class="form-control col-xs-3" type="text" name="agencyFee" id="agencyFee" value="${agencyFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="payMoney">*应付金额：</label>
            <input class="form-control  col-xs-4" type="text" name="payMoney" id="payMoney" placeholder="请输入" value="${payMoney}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="paidMoney">已付金额：</label>
            <input class="form-control  col-xs-4" type="text" name="paidMoney" id="paidMoney" placeholder="请输入" value="${paidMoney}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="businessExpenseFee">商业保险费：</label>
            <input class="form-control  col-xs-4" type="text" name="businessExpenseFee" id="businessExpenseFee" placeholder="请输入" value="${businessExpenseFee}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="forceExpenseFee">强制保险费：</label>
            <input class="form-control col-xs-2 " type="text" name="forceExpenseFee" id="forceExpenseFee" value="${forceExpenseFee}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="expenseCompany">保险公司：</label>
            <input class="form-control col-xs-2 " type="text" name="expenseCompany" id="expenseCompany" value="${expenseCompany}" placeholder="请输入" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="expenseRebate">保险返点：</label>
            <input class="form-control  col-xs-4" type="text" name="expenseRebate" id="expenseRebate" placeholder="请输入" value="${expenseRebate}" autocomplete="off"/>
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
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="saleType">付款方式：</label>
            <select class="form-control  col-xs-2" name="saleType" id="saleType" onchange="show(this)">
                <option value="1">全款</option>
                <option value="2">按揭</option>
            </select>
        </div>
        <div id="sale-type"  style="display: none">
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="mortgageCommissioner">对接按揭专员：</label>
                <input class="form-control  col-xs-4" type="text" name="mortgageCommissioner" id="mortgageCommissioner" placeholder="请输入" value="${mortgageCommissioner}"  autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2 " for="mortgageCompany">按揭公司：</label>
                <input class="form-control  col-xs-4" type="text" name="mortgageCompany" id="mortgageCompany" placeholder="请输入" value="${mortgageCompany}" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="loanFee">贷款金额：</label>
                <input class="form-control  col-xs-4" type="text" name="loanFee" id="loanFee" placeholder="请输入" value="${loanFee}"  autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2 " for="interestRate">利率：</label>
                <input class="form-control  col-xs-4" type="text" name="interestRate" id="interestRate" placeholder="请输入" value="${interestRate}" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="mortgageRebate">按揭返点：</label>
                <input class="form-control  col-xs-4" type="text" name="mortgageRebate" id="mortgageRebate" placeholder="请输入" value="${mortgageRebate}"  autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2 " for="backFee">返还金额：</label>
                <input class="form-control  col-xs-4" type="text" name="backFee" id="backFee" placeholder="请输入" value="${backFee}" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="assessmentFee">评估费：</label>
                <input class="form-control  col-xs-4" type="text" name="assessmentFee" id="assessmentFee" placeholder="请输入" value="${assessmentFee}"  autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2 " for="riskFee">风险金：</label>
                <input class="form-control  col-xs-4" type="text" name="riskFee" id="riskFee" placeholder="请输入" value="${riskFee}" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="renewalFee">续保押金：</label>
                <input class="form-control  col-xs-4" type="text" name="renewalFee" id="renewalFee" placeholder="请输入" value="${renewalFee}"  autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2 " for="padFee">垫资费：</label>
                <input class="form-control  col-xs-4" type="text" name="padFee" id="padFee" placeholder="请输入" value="${padFee}" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="doorFee">上门费：</label>
                <input class="form-control  col-xs-4" type="text" name="doorFee" id="doorFee" placeholder="请输入" value="${doorFee}"  autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2 " for="stampDuty">印花税：</label>
                <input class="form-control  col-xs-4" type="text" name="stampDuty" id="stampDuty" placeholder="请输入" value="${stampDuty}" autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2 " for="otherFee">其它费用：</label>
                <input class="form-control  col-xs-3" type="text" name="otherFee" id="otherFee" placeholder="请输入" value="${otherFee}"  autocomplete="off"/>
            </div>
            <div class="form-group form-inline row">
                <label class="control-label col-xs-2" for="mortgageMoney">放款金额：</label>
                <input class="form-control col-xs-3" type="text" name="mortgageMoney" id="mortgageMoney" value="${mortgageMoney}" placeholder="请输入" autocomplete="off"/>
                <div class="col-xs-1"></div>
                <label class="control-label col-xs-2" for="aMortgageMoney">已放款：</label>
                <input class="form-control col-xs-3" type="text" name="aMortgageMoney" id="aMortgageMoney" value="${aMortgageMoney}" placeholder="请输入" autocomplete="off"/>
            </div>
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
                <a href="newCarView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">

    function show(obj) {
        var id = obj.id;
        if (id == "saleType") {
            if (obj.value == "2" ) {
                document.getElementById("sale-type").style.display = "block";
            } else {
                document.getElementById("sale-type").style.display = "none";
            }
        }
    }

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

    var saleType = document.getElementById('saleType');
    for (var i = 0; i < saleType.options.length; i++) {
        if (saleType.options[i].value =='${saleType}') {
            saleType.options[i].selected = true;

            if('${saleType}' == 2){
                document.getElementById("sale-type").style.display = "block";
            }

            if('${saleType}' == 1){
                document.getElementById("sale-type").style.display = "none";
            }
        }
    }
</script>