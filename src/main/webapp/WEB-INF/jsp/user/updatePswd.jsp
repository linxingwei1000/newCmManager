<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<title>修改密码</title>
    
<ul class="breadcrumb">
	<li><a href="main">首页</a> <span class="divider"></span></li>
	<li><a href="userList">员工列表</a> <span class="divider"></span></li>
	<li class="active">修改密码</li>
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


<form class="form-horizontal col-xs-4 col-xs-offset-3"  action="userPswdUpdate" method="post" >
	<div class="control-group">
		<label class="control-label">&nbsp;</label>
		<div class="controls">
			<h5>修改密码</h5>
		</div>
	</div>
	<hr />
	<c:if test="${sessionScope.account.id == id }">
		<div class="control-group">
			<label class="control-label" for="oldpsw">旧密码：</label>
			<div class="controls">
				<input class="form-control" type="password" name="oldpsw" id="oldpsw" placeholder="请输入旧密码"  value="${oldpsw }" autocomplete="off" />
			</div>
		</div>
	</c:if>
	<div class="control-group">
		<label class="control-label" for="newpsw">新密码：</label>
		<div class="controls">
			<input class="form-control" type="password" name="newpsw" id="newpsw" placeholder="请输入新密码"  value="${newpsw }" autocomplete="off" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="repsw">新密码密码：</label>
		<div class="controls">
			<input class="form-control" type="password" name="repsw" id="repsw" placeholder="请输入新密码密码"  value="${repsw }" autocomplete="off" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">&nbsp;</label>
		<div class="controls">
			<input type="hidden" name="id" id="id" value="${id}" />
			<input type="submit" value="保存" class="btn btn-primary" />
			<a href="userList" class="btn btn-small"><i class="icon-share-alt icon-share-alt-white"></i> 返回</a>
		</div>
	</div>
</form>

<script type="text/javascript">
$(function(){
	if($('#oldpsw').val() == ''){
		$('#oldpsw').focus();
	}	
	else if($('#newpsw').val() == ''){
		$('#newpsw').focus();
	}	
	else if($('#repsw').val() == ''){
		$('#repsw').focus();
	}	
});
</script>
