<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>车辆采购管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">车辆采购管理</li>
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
<p><a href="carPurchaseAction?action=1&recordStatus=1" class="btn btn-primary"><i class="icon-plus icon-white"></i> 车辆买入</a></p>
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
                    <col width="5%"/>
                    <col width="8%"/>
                    <col width="9%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="10%"/>
                </colgroup>
                <tr>
                    <th>车型</th>
                    <th>钥匙编号</th>
                    <th>采购日期</th>
                    <th>采购人</th>
                    <th>车辆信息</th>
                    <th>是否批量</th>
                    <th>展厅标价</th>
                    <th>全款权限底价</th>
                    <th>按揭权限底价</th>
                    <th>中介费</th>
                    <th>已付/采购价</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${list}" varStatus="status">
                    <tr>
                        <td>${cp.carModel  }</td>
                        <td>${cp.keyNum  }</td>
                        <td>${cp.strPurchaseDate }</td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 && sessionScope.account.department == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2||(sessionScope.account.userType == 3 && sessionScope.account.department != 3)}">
                                ${cp.purchasePerson }
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2}">
                                <a href="#carDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="carDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myModalLabel">车辆信息</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:if test="${cp.insidePerson != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">内部合伙人：${cp.insidePerson}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">内部合伙比例：${cp.insideProportion}</label>
                                                    </div>
                                                </c:if>
                                                <c:if test="${cp.outsidePerson != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">外部合伙人：${cp.outsidePerson}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">外部合伙比例：${cp.outsideProportion}</label>
                                                    </div>
                                                </c:if>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">车系：${cp.strCarLine}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">车辆类别：${cp.strCarLevel}</label>
                                                    <div class="col-sm-1"></div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">品牌：${cp.carBrand}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">排量：${cp.carDisplacement}</label>
                                                    <div class="col-sm-1"></div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">车架号：${cp.frameNum}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">取车方式：${cp.strCarTakeType}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">车源渠道：${cp.strCarChannel}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">采购类别：${cp.strPurchaseType}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">来源地：${cp.carResource}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">车牌所在地：${cp.carNumResource}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">出厂日期：${cp.carCreateTime}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">过户日期：${cp.carPurchaseTime}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <c:if test="${cp.carOutColour != '' }">
                                                        <label class="col-sm-3 control-label">外色：${cp.carOutColour}</label>
                                                        <div class="col-sm-1"></div>
                                                    </c:if>
                                                    <c:if test="${cp.carInsideColour != '' }">
                                                        <label class="col-sm-3 control-label">内色：${cp.carInsideColour}</label>
                                                        <div class="col-sm-1"></div>
                                                    </c:if>
                                                    <label class="col-sm-4 control-label">公里数：${cp.carRunNum}万</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">新车指导价：${cp.carNewSale}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">车况：${cp.strCarStatus}</label>
                                                </div>
                                                <c:if test="${cp.carStatusDesc != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">车况描述：${cp.carStatusDesc}</label>
                                                    </div>
                                                </c:if>
                                                <c:if test="${cp.carConfig != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">配置：${cp.carConfig}</label>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="modal-footer">
                                                <label>版权所有© 2018 车猫</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </td>
                        <td><c:if test="${cp.isBath == 0 }">否</c:if><c:if test="${cp.isBath == 1 }">是</c:if></td>
                        <td>${cp.hallMoney}</td>
                        <td>${cp.qAuthorityMoney}</td>
                        <td>${cp.aAuthorityMoney}</td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 && sessionScope.account.department == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2||(sessionScope.account.userType == 3 && sessionScope.account.department != 3)}">
                                ${cp.agencyFee}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2}">
                                ${cp.paidMoney }/${cp.purchaseMoney }
                                <c:if test="${not empty cp.purchasePaidList}"><a href="#recordDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> ，付款记录</a>
                                    <div id="recordDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                    <h4 class="modal-title" id="myRecordLabel">付款记录</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <c:forEach var="ppl" items="${cp.purchasePaidList}" varStatus="status">
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
                            </c:if>
                        </td>
                        <td>
                            <a href="#remarkDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 备注</a>
                            <div id="remarkDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myRemarkLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myRemarkLabel">备注记录</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="ppl" items="${cp.purchaseRemark}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-6 control-label">${ppl.remarkDate}：${ppl.remark}</label>
                                                    <div class="col-sm-1"></div>
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="carRemarkDel?id=${ppl.id}&remarkType=1" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                                <hr/>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <div class="col-sm-2"></div>
                                                <div class="col-sm-4">
                                                    <a href="#remarkDialog" data-toggle="modal" data-url="carRemarkAdd?carRecordId=${cp.id}&remarkType=1" data-title="备注" class="remark-trigger"><i class="icon-trash"></i> 添加备注</a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${sessionScope.account.userType == 3 || (sessionScope.account.userType == 2&&sessionScope.account.department != 4)}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.department == 4}">
                                <a href="#paidDialog" data-toggle="modal" data-url="carPurchasePaid?id=${cp.id }" data-title="采购付款" data-need="${cp.purchaseMoney - cp.paidMoney }" class="paid-trigger"><i class="icon-trash"></i> 付款</a>
                                <a href="carPurchaseAction?id=${cp.id }&action=2&recordStatus=1"><i class="icon-lock"></i> 编辑</a>
                                <a href="#confirmDialog" data-toggle="modal" data-url="carPurchaseDelete?id=${cp.id }&recordStatus=1" data-title="删除记录"
                                   data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%-- 备注 --%>
<div id="remarkDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="remarkDate">*备注日期：</label>
                    <input class="form-control col-xs-2 " type="text" name="remarkDate" id="remarkDate" onclick="new Calendar().show(this);" autocomplete="off"/>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="remark">*备注：</label>
                    <textarea rows="5" cols="" name="remark" id="remark" class="control-box col-xs-7"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary remark-do-trigger">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
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
            <div class="alert alert-success">若为0元，直接点击确定</div>
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
            var need = self.attr('data-need');
            $('.modal-title').html(title);
            $('.modal-body-need').html('还需付款'+ need + '元');
            $('.paid-do-trigger').click(function () {
                document.location.href = url + '&goonPaid=' + document.getElementById('goonPaid').value + '&paidReason=' + document.getElementById('paidReason').value;
            });
        });
    });

    $(function () {
        /*确认*/
        $('.remark-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.remark-do-trigger').click(function () {
                document.location.href = url + '&remarkDate=' + document.getElementById('remarkDate').value + '&remark=' + document.getElementById('remark').value;
            });
        });
    });
</script>
