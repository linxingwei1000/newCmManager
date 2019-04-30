<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <title>员工管理</title>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">员工管理</li>
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

<c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2 }">
    <p>
        <a href="userAddView" class="btn btn-primary"><i class="icon-plus icon-white"></i> 创建新员工</a>
    </p>
</c:if>


<c:choose>
    <c:when test="${empty dataList }">
        <div class="alert alert-warning">员工列表为空</div>
    </c:when>
    <c:otherwise>
        <table class="table table-striped table-bordered table-condensed table-hover">
            <colgroup>
                <col width="6%"/>
                <col width="12%"/>
                <col width="12%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="15%"/>
                <col width="25%"/>
            </colgroup>
            <tr>
                <th>编号</th>
                <th>帐号</th>
                <th>员工类型</th>
                <th>姓名</th>
                <th>手机</th>
                <th>部门</th>
                <th>操作</th>
            </tr>
            <c:forEach var="Account" items="${dataList }" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>
                        <c:choose>
                            <c:when test="${Account.accountNum  == sessionScope.account.accountNum }">
                                <span class="label label-success"><i class="icon-user icon-white"></i> 我自己</span>
                            </c:when>
                            <c:otherwise>
                                ${Account.accountNum }
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${Account.userType == 1 }">superMan</c:when>
                            <c:when test="${Account.userType == 2 }">管理员</c:when>
                            <c:when test="${Account.userType == 3 }">会员</c:when>
                        </c:choose>
                    </td>
                    <td>${Account.name}</td>
                    <td>${Account.phone}</td>
                    <td>${Account.departmentName}</td>
                    <td>
                        <c:choose>
                            <c:when test="${Account.accountNum == sessionScope.account.accountNum }">
                                <a href="userPswdUpdateView?id=${Account.id}"><i class="icon-lock"></i> 修改密码</a>
                                <a href="userUpdateView?id=${Account.id}"><i class="icon-lock"></i> 修改个人信息</a>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${sessionScope.account.userType == 3}">
                                    <span class="label label-warning">您无权操作此员工</span>
                                </c:if>
                                <c:if test="${sessionScope.account.userType == 2}">
                                    <c:choose>
                                        <c:when test="${Account.userType > sessionScope.account.userType && Account.department == sessionScope.account.department }">
                                            <a href="userPswdUpdateView?id=${Account.id}"><i class="icon-lock"></i> 修改密码</a>
                                            <a href="userUpdateView?id=${Account.id}"><i class="icon-lock"></i> 编辑</a>
                                            <a href="#confirmDialog" data-toggle="modal"
                                               data-url="userDelete?id=${Account.id }" data-title="删除员工"
                                               data-tip="确认要删除员工【${Account.accountNum}】嘛？" class="confirm-trigger"><i
                                                    class="icon-trash"></i> 删除</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">您无权操作此员工</span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${ sessionScope.account.userType == 1}">
                                    <c:choose>
                                        <c:when test="${Account.userType > sessionScope.account.userType }">
                                            <a href="userPswdUpdateView?id=${Account.id}"><i class="icon-lock"></i> 修改密码</a>
                                            <a href="userUpdateView?id=${Account.id}"><i class="icon-lock"></i> 编辑</a>
                                            <a href="#confirmDialog" data-toggle="modal"
                                               data-url="userDelete?id=${Account.id}" data-title="删除员工"
                                               data-tip="确认要删除员工【${Account.accountNum}】嘛？" class="confirm-trigger"><i
                                                    class="icon-trash"></i> 删除</a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">您无权操作此员工</span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>


<%-- 删除确认 --%>
<div id="confirmDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class=" modal-body-inner"></div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary confirm-do-trigger">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        /*确认*/
        $('.confirm-trigger').click(function () {
            var self = $(this);
            var tip = self.attr('data-tip');
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.modal-body-inner').html(tip);
            $('.confirm-do-trigger').click(function () {
                document.location.href = url;
            });
        });
        Global.setNavActive("2");
    });
</script>