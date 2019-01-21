<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>订金寻车管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">订金寻车管理</li>
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
<p><a href="carDepositAction?action=1" class="btn btn-primary"><i class="icon-plus icon-white"></i> 订金寻车</a></p>
<hr/>
<div>
    <c:choose>
        <c:when test="${empty list }">
            <div class="container">
                <div class="alert alert-block">
                    <a href="#" class="close" data-dismiss="alert">x</a>
                    未找到匹配的记录
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <table id="traceTable" class="table table-striped table-bordered table-condensed table-hover">
                <colgroup>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                </colgroup>
                <tr>
                    <th>销售员</th>
                    <th>收订金日期</th>
                    <th>车型</th>
                    <th>颜色</th>
                    <th>年份</th>
                    <th>配置</th>
                    <th>预算</th>
                    <th>预约交车日期</th>
                    <th>订金</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${list}" varStatus="status">
                    <tr>
                        <td>${cp.salePerson  }</td>
                        <td>${cp.strDepositDate  }</td>
                        <td>${cp.carModel }</td>
                        <td>${cp.carColor }</td>
                        <td>${cp.carYear }</td>
                        <td>${cp.carConfig }</td>
                        <td>${cp.budget }</td>
                        <td>${cp.strGiveCarDate }</td>
                        <td>${cp.deposit }
                            <c:if test="${not empty cp.depositPaidList}"><a href="#recordDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> ，付款记录</a>
                            <div id="recordDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myRecordLabel">付款记录</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="ppl" items="${cp.depositPaidList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-5 control-label">付款金额：${ppl.paidMoney}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-5 control-label">描述：${ppl.paidReason}</label>
                                                </div>
                                                <hr/>
                                            </c:forEach>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:if>
                        </td>
                        <td>
                            <a href="#paidDialog" data-toggle="modal" data-url="carDepositPaid?id=${cp.id }" data-title="付款" class="paid-trigger"><i class="icon-trash"></i> 付款</a>
                            <a href="carDepositAction?id=${cp.id }&action=2"><i class="icon-lock"></i> 编辑</a>
                            <c:if test="${ sessionScope.account.userType == 1}">
                            <a href="#confirmDialog" data-toggle="modal" data-url="carDepositDelete?id=${cp.id }" data-title="删除记录"
                               data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%-- 付款 --%>
<div id="paidDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="goonPaid">付款：</label>
                    <input class="form-control  col-xs-4" type="text" name="goonPaid" id="goonPaid" placeholder="请输入金额" autocomplete="off"/>
                    <div class="col-xs-2 modal-body-need"></div>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2" for="paidReason">描述：</label>
                    <input class="form-control  col-xs-4" type="text" name="paidReason" id="paidReason" placeholder="请输入" autocomplete="off"/>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary paid-do-trigger">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

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

<script language="javascript">
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
    });

    $(function () {
        /*确认*/
        $('.paid-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.paid-do-trigger').click(function () {
                document.location.href = url + '&goonPaid=' + document.getElementById('goonPaid').value + '&paidReason=' + document.getElementById('paidReason').value;
            });
        });
    });
</script>
