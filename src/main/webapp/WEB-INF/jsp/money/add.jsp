<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>创建新记录</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <c:if test="${moneyType==1}">
        <li><a href="moneyView?moneyType=1">现金流水管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${moneyType==2}">
        <li><a href="moneyView?moneyType=2">银行流水管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${moneyType==3}">
        <li><a href="moneyView?moneyType=3">poss机流水管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${moneyType==4}">
        <li><a href="moneyView?moneyType=4">借款管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${moneyType==5}">
        <li><a href="moneyView?moneyType=5">合作款管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${moneyType==6}">
        <li><a href="moneyView?moneyType=6">应收款管理</a> <span class="divider"></span></li>
    </c:if>
    <c:if test="${moneyType==7}">
        <li><a href="moneyView?moneyType=7">基本户管理</a> <span class="divider"></span></li>
    </c:if>
    <li class="active">创建新记录</li>
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
    <form class="form" action="moneyAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionDate">日期：</label>
            <input class="form-control col-xs-2" type="text" name="actionDate" id="actionDate" value="${actionDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
        </div>

        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionPerson">
                <c:if test="${moneyType==1||moneyType==2||moneyType==3||moneyType==7}">
                    经办人：
                </c:if>
                <c:if test="${moneyType==4}">
                    借款人：
                </c:if>
                <c:if test="${moneyType==5}">
                    合伙人：
                </c:if>
                <c:if test="${moneyType==6}">
                    欠款人：
                </c:if>
            </label>
            <input class="form-control  col-xs-4" type="text" name="actionPerson" id="actionPerson" placeholder="请输入" value="${actionPerson}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2" for="actionFee">金额：</label>
            <input class="form-control  col-xs-4" type="text" name="actionFee" id="actionFee" placeholder="请输入" value="${actionFee}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">科目：</label>
            <c:if test="${moneyType==1}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>现金支出</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>备用金收入</label>
            </c:if>
            <c:if test="${moneyType==2}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>银行支出</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>银行收入</label>
                <select class="form-control  col-xs-2" name="bankNameId" id="bank-name" onchange="">
                    <option value="0">银行</option>
                    <c:forEach var="cp" items="${BANK_NAME}" varStatus="status">
                        <option value="${cp.id}">${cp.propertyValue}</option>
                    </c:forEach>
                </select>
            </c:if>
            <c:if test="${moneyType==3}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>poss机提现</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>poss机收入</label>
            </c:if>
            <c:if test="${moneyType==4}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>还款</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>借款</label>
            </c:if>
            <c:if test="${moneyType==5}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>退</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>入</label>
            </c:if>
            <c:if test="${moneyType==6}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>还款</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>欠款</label>
            </c:if>
            <c:if test="${moneyType==7}">
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="1" <c:if test="${ actionType == 1 }">checked="checked"</c:if>/>支出</label>
                <label class="radio inline col-xs-2"><input type="radio" name="actionType" value="2" <c:if test="${ actionType == 2 }">checked="checked"</c:if>/>充值</label>
            </c:if>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="actionDesc">事由：</label>
            <textarea rows="5" cols="" name="actionDesc" id="actionDesc" class="control-box col-xs-7"></textarea>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="moneyType" id="moneyType" value="${moneyType}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <c:if test="${moneyType==1}">
                    <a href="moneyView?moneyType=1" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${moneyType==2}">
                    <a href="moneyView?moneyType=2" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${moneyType==3}">
                    <a href="moneyView?moneyType=3" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${moneyType==4}">
                    <a href="moneyView?moneyType=4" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${moneyType==5}">
                    <a href="moneyView?moneyType=5" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${moneyType==6}">
                    <a href="moneyView?moneyType=6" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
                <c:if test="${moneyType==7}">
                    <a href="moneyView?moneyType=7" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
                </c:if>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    var bankName = document.getElementById('bank-name');
    for (var i = 0; i < bankName.options.length; i++) {
        if (bankName.options[i].value == '${bankNameId}') {
            bankName.options[i].selected = true;
        }
    }

    var actionDesc = document.getElementById('actionDesc');
    actionDesc.innerHTML = '${actionDesc}';
</script>