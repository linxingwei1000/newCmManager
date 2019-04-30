<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<title>编辑信息</title>
    
<ul class="breadcrumb">
	<li><a href="main">首页</a> <span class="divider"></span></li>
	<li><a href="userList">员工列表</a> <span class="divider"></span></li>
	<li class="active">编辑信息</li>
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
	String tip=java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("tip"),"utf-8"),"utf-8");
	if(org.apache.commons.lang3.StringUtils.startsWith(tip, "ok")){
	%>
	<div class="alert alert-success"><%=tip.substring(2) %></div>
	<%
	}
	else{
	%>
	<div class="alert alert-error"><%=tip %></div>
	<%
	}
	%>
</c:if>


<form class="form-horizontal"  action="userUpdate" method="post">
	<div class="control-group">
		<label class="control-label">&nbsp;</label>
		<div class="controls">
			<h5>编辑信息</h5>
		</div>
	</div>
	<hr />
	<div class="form-group form-inline row">
		<label class="control-label col-xs-2 ">员工帐号：${updateAccount.accountNum }</label>
	</div>
	<div class="form-group form-inline row">
		<label class="control-label col-xs-2">员工类型：</label>
		<c:if test="${sessionScope.account.userType == 1 }">
			<!--<label class="radio inline col-xs-2"><input type="radio" name="usertype" value="1" <c:if test="${ updateAccount.userType == 1 }">checked="checked"</c:if>/>SuperMan</label>-->
			<label class="radio inline col-xs-2"><input type="radio" name="usertype" value="2" <c:if test="${ updateAccount.userType == 2 }">checked="checked"</c:if>/>管理员</label>
			<label class="radio inline col-xs-2"><input type="radio" name="usertype" value="3" <c:if test="${ updateAccount.userType == 3 }">checked="checked"</c:if>/>会员</label>
		</c:if>
		<c:if test="${sessionScope.account.userType == 2 ||sessionScope.account.userType == 3}">
			<div class="alert alert-success">你没有权限修改员工类型！</div>
		</c:if>
	</div>
	<div class="form-group form-inline row">
		<label class="control-label col-xs-2">部门：</label>
		<c:if test="${sessionScope.account.userType == 1 }">
			<c:forEach var="DepartmentAuthority" items="${list }" varStatus="status">
				<c:if test="${sessionScope.account.department != DepartmentAuthority.id }">
					<label class="radio inline col-xs-2"><input type="radio" name="department" value="${DepartmentAuthority.id}" <c:if test="${ DepartmentAuthority.departmentName == updateAccount.departmentName }">checked="checked"</c:if>/>${DepartmentAuthority.departmentName}</label>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${sessionScope.account.userType == 2 ||sessionScope.account.userType == 3}">
			<div class="alert alert-success">你没有权限修改员工部门！</div>
		</c:if>
	</div>
	<div class="form-group form-inline row">
		<label class="control-label col-xs-2 " for="name">姓名：</label>
		<input class="form-control  col-xs-4" type="text" name="name" id="name" placeholder="请输入姓名"
			   value="${updateAccount.name }" autocomplete="off"/>
	</div>
	<div class="form-group form-inline row">
		<label class="control-label col-xs-2 " for="phone">手机：</label>
		<input class="form-control  col-xs-4" type="text" name="phone" id="phone" placeholder="请输入手机"
			   value="${updateAccount.phone }" autocomplete="off"/>
	</div>
	<hr />
	<div class="control-group">
		<label class="control-label">&nbsp;</label>
		<div class="controls">
			<input type="hidden" name="id" value="${updateAccount.id }" />
			<input type="submit" value="保存" class="btn btn-primary" />
			<a href="userList" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回员工列表</a>
		</div>
	</div>
</form>

<script type="text/javascript">
$(function(){
	if($('#uname').val() == ''){
		$('#uname').focus();
	}	
});
</script>
