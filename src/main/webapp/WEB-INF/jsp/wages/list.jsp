<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <title>工资管理</title>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">工资管理</li>
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
<hr/>
<div class="container">
    <form class="form-horizontal" action="userWagesSearch" method="get">
        <div class="form-group">
            <div class="col-sm-2">
                <select class="form-control" name="searchKey" id="searchKey" onchange="show(this)">
                    <option value="">未选中</option>
                    <option value="staff">员工名</option>
                    <option value="payMonth">工资月份</option>
                </select>
            </div>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="searchValue" id="searchValue" value="${searchValue }" placeholder="请输入" autocomplete="off"/>
            </div>

            <div class="col-sm-2 form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-search icon-white"></i> 查询
                </button>
            </div>
        </div>
        <div class="form-group">
            <div class="alert alert-info" role="alert">注：1.员工名直接填写名字，2.工资月份按指定格式填写，如要查询2018年3月的工资，直接填写：2018年03月</div>
        </div>
    </form>
</div>
<hr/>
<p><a href="userWagesAction?action=1" class="btn btn-primary"><i class="icon-plus icon-white"></i> 员工工资录入</a></p>

<c:choose>
    <c:when test="${not empty dataList }">
        <table class="table table-striped table-bordered table-condensed table-hover">
            <colgroup>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="5%"/>
                <col width="10%"/>
            </colgroup>
            <tr>
                <th>员工</th>
                <th>月份</th>
                <th>基本工资</th>
                <th>事假</th>
                <th>迟到</th>
                <th>未打卡</th>
                <th>质保提成</th>
                <th>销售提成</th>
                <th>认证提成</th>
                <th>定金提成</th>
                <th>签单提成</th>
                <th>保险提成</th>
                <th>采购提成</th>
                <th>车辆分红</th>
                <th>新车提成</th>
                <th>餐补</th>
                <th>其他补贴</th>
                <th>工资合计</th>
                <th>操作</th>
            </tr>
            <c:forEach var="da" items="${dataList }" varStatus="status">
                <tr>
                    <td>${da.staff}</td>
                    <td>${da.payMonth}</td>
                    <td>${da.basePay}</td>
                    <td>${da.compassionateLeave}</td>
                    <td>${da.late}</td>
                    <td>${da.unHitCard}</td>
                    <td>${da.qualityCommission}</td>
                    <td>${da.carCommission}</td>
                    <td>${da.authCommission}</td>
                    <td>${da.depositCommission}</td>
                    <td>${da.billCommission}</td>
                    <td>${da.insuranceCommission}</td>
                    <td>${da.purchaseCommission}</td>
                    <td>${da.shareDividends}</td>
                    <td>${da.newCarCommission}</td>
                    <td>${da.mealSupplement}</td>
                    <td>${da.otherSupplement}</td>
                    <td>${da.wages}</td>
                    <td>
                        <a href="userWagesAction?id=${da.id }&action=2"><i class="icon-lock"></i> 修改</a>
                        <a href="#confirmDialog" data-toggle="modal"
                           data-url="userWagesDelete?id=${da.id }" data-title="删除"
                           data-tip="确认要删除${da.staff }${da.payMonth }工资条嘛？" class="confirm-trigger"><i
                                class="icon-trash"></i> 删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
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

    var searchKey = document.getElementById('searchKey');
    for (var i = 0; i < searchKey.options.length; i++) {
        if (searchKey.options[i].value == '${searchKey}') {
            searchKey.options[i].selected = true;
        }
    }
</script>