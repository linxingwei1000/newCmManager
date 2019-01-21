<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- 根据权限位的值显示当前拥有的权限 -->
<c:set var="userType" value="${param.userType }"/>
<c:choose>
	<c:when test="${userType == 1 }">superMan</c:when>
	<c:when test="${userType == 2 }">管理员</c:when>
	<c:when test="${userType == 3 }">会员</c:when>
</c:choose>     