<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>修改档案</title>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li><a href="dossierView">档案管理</a> <span class="divider"></span></li>
    <li class="active">修改档案数据</li>
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
    <form class="form" action="dossierAction" method="post">
        <hr/>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carCreateTime">出厂日期：</label>
            <input class="form-control col-xs-4" type="text" name="carCreateTime" id="carCreateTime" value="${carCreateTime}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carPurchaseTime">登记日期：</label>
            <input class="form-control  col-xs-4" type="text" name="carPurchaseTime" id="carPurchaseTime" value="${carPurchaseTime}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carKeyNum">钥匙数量：</label>
            <input class="form-control  col-xs-3" type="text" name="carKeyNum" id="carKeyNum" placeholder="请输入" value="${carKeyNum}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carOwner">现车主名字：</label>
            <input class="form-control  col-xs-3" type="text" name="carOwner" id="carOwner" placeholder="请输入" value="${carOwner}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carNum">现车牌：</label>
            <input class="form-control  col-xs-3" type="text" name="carNum" id="carNum" placeholder="请输入" value="${carNum}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="carNumResource">现车牌所在地：</label>
            <input class="form-control  col-xs-3" type="text" name="carNumResource" id="carNumResource" placeholder="请输入" value="${carNumResource}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="purchaseTimes">过户次数：</label>
            <input class="form-control  col-xs-3" type="text" name="purchaseTimes" id="purchaseTimes" placeholder="请输入" value="${purchaseTimes}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="qzxDate">强制险到期时间：</label>
            <input class="form-control col-xs-4" type="text" name="qzxDate" id="qzxDate" value="${qzxDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="syxDate">商业险到期时间：</label>
            <input class="form-control  col-xs-4" type="text" name="syxDate" id="syxDate" value="${syxDate}" onclick="new Calendar().show(this);" placeholder="请点击" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="qzxPerson">强制险被保险人：</label>
            <input class="form-control  col-xs-3" type="text" name="qzxPerson" id="qzxPerson" placeholder="请输入" value="${qzxPerson}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="syxPerson">商业险被保险人：</label>
            <input class="form-control  col-xs-3" type="text" name="syxPerson" id="syxPerson" placeholder="请输入" value="${syxPerson}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="qzxCompany">强制险保险公司：</label>
            <input class="form-control  col-xs-3" type="text" name="qzxCompany" id="qzxCompany" placeholder="请输入" value="${qzxCompany}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="syxCompany">商业险保险公司：</label>
            <input class="form-control  col-xs-3" type="text" name="syxCompany" id="syxCompany" placeholder="请输入" value="${syxCompany}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="qzxAddress">强制险保险公司所在地：</label>
            <input class="form-control  col-xs-3" type="text" name="qzxAddress" id="qzxAddress" placeholder="请输入" value="${qzxAddress}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="syxAddress">商业险保险公司所在地：</label>
            <input class="form-control  col-xs-3" type="text" name="syxAddress" id="syxAddress" placeholder="请输入" value="${syxAddress}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">签单照片：</label>
            <div class="form-group form-inline row">
                <label class="radio inline col-xs-2"><input type="radio" name="billSign" value="0" <c:if test="${ billSign == 0}">checked="checked"</c:if>/>无</label>
                <label class="radio inline col-xs-2"><input type="radio" name="billSign" value="1" <c:if test="${ billSign == 1}">checked="checked"</c:if>/>有</label>
            </div>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">登记证书：</label>
            <div class="form-group form-inline row">
                <label class="radio inline col-xs-2"><input type="radio" name="register" value="0" <c:if test="${ register == 0}">checked="checked"</c:if>/>无</label>
                <label class="radio inline col-xs-2"><input type="radio" name="register" value="1" <c:if test="${ register == 1}">checked="checked"</c:if>/>有</label>
            </div>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">行驶证：</label>
            <div class="form-group form-inline row">
                <label class="radio inline col-xs-2"><input type="radio" name="drivingLicense" value="0" <c:if test="${ drivingLicense == 0}">checked="checked"</c:if>/>无</label>
                <label class="radio inline col-xs-2"><input type="radio" name="drivingLicense" value="1" <c:if test="${ drivingLicense == 1}">checked="checked"</c:if>/>有</label>
            </div>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">强制险贴：</label>
            <div class="form-group form-inline row">
                <label class="radio inline col-xs-2"><input type="radio" name="qzxStick" value="0" <c:if test="${ qzxStick == 0}">checked="checked"</c:if>/>无</label>
                <label class="radio inline col-xs-2"><input type="radio" name="qzxStick" value="1" <c:if test="${ qzxStick == 1}">checked="checked"</c:if>/>有</label>
            </div>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2">违章是否处理：</label>
            <div class="form-group form-inline row">
                <label class="radio inline col-xs-2"><input type="radio" name="breakRule" value="0" <c:if test="${ breakRule == 0}">checked="checked"</c:if>/>否</label>
                <label class="radio inline col-xs-2"><input type="radio" name="breakRule" value="1" <c:if test="${ breakRule == 1}">checked="checked"</c:if>/>是</label>
            </div>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="carDischarge">排放标准：</label>
            <input class="form-control  col-xs-3" type="text" name="carDischarge" id="carDischarge" placeholder="请输入" value="${carDischarge}" autocomplete="off"/>
            <div class="col-xs-1"></div>
            <label class="control-label col-xs-2 " for="annualTrial">年审到期时间：</label>
            <input class="form-control  col-xs-3" type="text" name="annualTrial" id="annualTrial" placeholder="请点击" onclick="new Calendar().show(this);"  value="${annualTrial}" autocomplete="off"/>
        </div>
        <div class="form-group form-inline row">
            <label class="control-label col-xs-2 " for="abs">摘要：</label>
            <textarea rows="5" cols="" name="abs" id="abs" class="control-box col-xs-7"></textarea>
        </div>
        <div class="hidden">
            <input type="text" name="action" id="action" value="${action}" autocomplete="off"/>
            <input type="text" name="id" id="id" value="${id}" autocomplete="off"/>
            <input type="text" name="over" id="over" value="1" autocomplete="off"/>
            <input type="text" name="carRecordId" id="carRecordId" value="${carRecordId}" autocomplete="off"/>
        </div>
        <hr/>
        <div class="control-group">
            <label class="control-label">&nbsp;</label>
            <div class="controls">
                <input type="submit" value="保存" class="btn btn-primary"/>
                <a href="dossierView" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
            </div>
        </div>
    </form>
</div>

<script language="javascript">
    var remark = document.getElementById('remark');
    remark.innerHTML = '${remark}';

    var abs = document.getElementById('abs');
    abs.innerHTML = '${abs}';
</script>