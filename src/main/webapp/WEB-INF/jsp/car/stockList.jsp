<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>在店库存管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">在店库存</li>
</ul>

<c:if test="${not empty tip }">
    <c:choose>
        <c:when test="${fn:startsWith(requestScope.tip,'ok') }">
            <div class="alert alert-success">${fn:substringAfter(requestScope.tip, 'ok') }</div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">${requestScope.tip }</div>
        </c:otherwise>
    </c:choose>
</c:if>

<hr/>
<div class="container">
    <form class="form-horizontal" action="carSearchPurchase" method="get">
        <div class="form-group">
            <div class="col-sm-2">
                <select class="form-control" name="searchKey" id="searchKey">
                    <option value="">未选择中</option>
                    <option value="frame_num">车架号</option>
                    <option value="key_num">钥匙编号</option>
                    <option value="car_brand">品牌</option>
                    <option value="car_model">车型</option>
                    <option value="purchase_person">采购人</option>
                </select>
            </div>
            <div class="col-sm-2" id="dsv">
                <input class="form-control" type="text" name="searchValue" id="searchValue" value="${searchValue }" placeholder="请输入" autocomplete="off"/>
            </div>
            <div class="hidden">
                <input type="text" name="recordStatus" id="recordStatus" value="2" autocomplete="off"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">采购日期：</label>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="zbtime" id="zbtime" value="${zbtime }" onclick="new Calendar().show(this);" placeholder="开始日期" autocomplete="off"/>
            </div>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="zetime" id="zetime" value="${zetime }" onclick="new Calendar().show(this);" placeholder="结束日期" autocomplete="off"/>
            </div>
        </div>
        <div class="form-group">
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-search icon-white"></i> 查询
                </button>
                <a href="carStockReset"><i class="icon-trash"></i> 重置</a>
            </div>
        </div>
    </form>
</div>
<hr/>
<div class="alert alert-info">
    车辆库存总数：【${carNum}】
</div>
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
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="16%"/>
                </colgroup>
                <tr>
                    <th>采购人</th>
                    <th>采购日期</th>
                    <th>钥匙编号</th>
                    <th>车型</th>
                    <th>车架号</th>
                    <th>展厅标价</th>
                    <th>全款权限底价</th>
                    <th>按揭权限底价</th>
                    <th>车辆信息</th>
                    <th>提车成本</th>
                    <th>售前整备</th>
                    <th>其他收入</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${list}" varStatus="status">
                    <tr>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 && sessionScope.account.department == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2||(sessionScope.account.userType == 3 && sessionScope.account.department != 3)}">
                                ${cp.carRecord.purchasePerson }
                            </c:if>
                        </td>
                        <td>${cp.carRecord.strPurchaseDate }</td>
                        <td>${cp.carRecord.keyNum}</td>
                        <td>${cp.carRecord.carModel}</td>
                        <td>${cp.carRecord.frameNum}</td>
                        <td>${cp.carRecord.hallMoney}</td>
                        <td>${cp.carRecord.qAuthorityMoney}</td>
                        <td>${cp.carRecord.aAuthorityMoney}</td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2}">
                                <a href="#carDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="carDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myModalLabel">采购信息</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">中介费：${cp.carRecord.agencyFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">已付/采购价：${cp.carRecord.paidMoney }/${cp.carRecord.purchaseMoney }</label>
                                                </div>
                                                <c:if test="${cp.carRecord.outsidePerson != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">外部合伙人：${cp.carRecord.outsidePerson}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">外部合伙金额：${cp.carRecord.outsideMoney}</label>
                                                    </div>
                                                </c:if>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">品牌：${cp.carRecord.carBrand}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">排量：${cp.carRecord.carDisplacement}</label>
                                                    <div class="col-sm-1"></div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">取车方式：${cp.carRecord.strCarTakeType}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">车源渠道：${cp.carRecord.strCarChannel}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">采购类别：${cp.carRecord.strPurchaseType}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">来源地：${cp.carRecord.carResource}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">车牌所在地：${cp.carRecord.carNumResource}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">出厂日期：${cp.carRecord.carCreateTime}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">过户日期：${cp.carRecord.carPurchaseTime}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <c:if test="${cp.carRecord.carOutColour != '' }">
                                                        <label class="col-sm-3 control-label">外色：${cp.carRecord.carOutColour}</label>
                                                        <div class="col-sm-1"></div>
                                                    </c:if>
                                                    <c:if test="${cp.carRecord.carInsideColour != '' }">
                                                        <label class="col-sm-3 control-label">内色：${cp.carRecord.carInsideColour}</label>
                                                        <div class="col-sm-1"></div>
                                                    </c:if>
                                                    <label class="col-sm-4 control-label">公里数：${cp.carRecord.carRunNum}万</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">新车指导价：${cp.carRecord.carNewSale}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">车况：${cp.carRecord.strCarStatus}</label>
                                                </div>
                                                <c:if test="${cp.carRecord.carStatusDesc != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">车况描述：${cp.carRecord.carStatusDesc}</label>
                                                    </div>
                                                </c:if>
                                                <c:if test="${cp.carRecord.carConfig != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">配置：${cp.carRecord.carConfig}</label>
                                                    </div>
                                                </c:if>
                                                <c:if test="${sessionScope.account.userType == 1
                                                ||(sessionScope.account.userType == 2 && sessionScope.account.department == 4)}">
                                                    <div class="form-group row">
                                                        <a href="carPurchaseAction?id=${cp.carRecord.id }&action=2&recordStatus=2"><i class="icon-lock"></i> 修改车辆信息</a>
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
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 && sessionScope.account.department == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2||(sessionScope.account.userType == 3 && sessionScope.account.department != 3)}">
                                ${cp.allCost}，<a href="#CarMoneyCost-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 明细</a>
                                <div id="CarMoneyCost-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="CarMoneyCostLabel">成本录入明细</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="pl" items="${cp.costList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">${pl.linkName}：${pl.money}，备注：${pl.moneyDesc}</label>
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carMoneyDelete?id=${pl.id}&carRecordId=${pl.carRecordId}&setupType=12" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                    </div>
                                                </c:forEach>
                                                <c:if test="${sessionScope.account.department != 3}">
                                                    <div class="form-group row">
                                                        <div class="col-sm-2"></div>
                                                        <div class="col-sm-4">
                                                            <a href="carMoneyAction?carRecordId=${cp.carRecord.id }&action=1&setupType=12"><i class="icon-trash"></i> 添加成本录入</a>
                                                        </div>
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
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 && sessionScope.account.department == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2||(sessionScope.account.userType == 3 && sessionScope.account.department != 3)}">
                                ${cp.preAllFee}，<a href="#CarSaleSetupPre-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 明细</a>
                                <div id="CarSaleSetupPre-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="CarSaleSetupPreLabel">售前整备明细</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="pl" items="${cp.preList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">项目：${pl.setupName}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">金额：${pl.setupFee}</label>
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${pl.carCostId}&setupType=1" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                    </div>
                                                </c:forEach>
                                                <c:if test="${sessionScope.account.department != 3}">
                                                    <div class="form-group row">
                                                        <div class="col-sm-2"></div>
                                                        <div class="col-sm-4">
                                                            <a href="carSaleSetupAction?carCostId=${cp.carRecord.id }&action=1&setupType=1"><i class="icon-trash"></i> 添加售前整备</a>
                                                        </div>
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
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 && sessionScope.account.department == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2||(sessionScope.account.userType == 3 && sessionScope.account.department != 3)}">
                                ${cp.otherAllFee}，<a href="#CarOtherIncome-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 明细</a>
                                <div id="CarOtherIncome-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="CarOtherIncomeLabel">车辆其他收入明细</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="pl" items="${cp.otherList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">项目：${pl.setupName}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">金额：${pl.setupFee}</label>
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${pl.carCostId}&setupType=3" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                    </div>
                                                </c:forEach>
                                                <c:if test="${sessionScope.account.department != 3}">
                                                    <div class="form-group row">
                                                        <div class="col-sm-2"></div>
                                                        <div class="col-sm-4">
                                                            <a href="carSaleSetupAction?carCostId=${cp.carRecord.id }&action=1&setupType=3"><i class="icon-trash"></i> 添加其他收入</a>
                                                        </div>
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
                        <td>
                            <a href="#remarkDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 备注</a>
                            <div id="remarkDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myRemarkLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myRemarkLabel">备注记录</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="ppl" items="${cp.carRecord.stockRemark}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-6 control-label">${ppl.remarkDate}：${ppl.remark}</label>
                                                    <div class="col-sm-1"></div>
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="carRemarkDel?id=${ppl.id}&remarkType=2" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                                <hr/>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <div class="col-sm-2"></div>
                                                <div class="col-sm-4">
                                                    <a href="#remarkDialog" data-toggle="modal" data-url="carRemarkAdd?carRecordId=${cp.carRecord.id}&remarkType=2" data-title="备注" class="remark-trigger"><i class="icon-trash"></i> 添加备注</a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${(sessionScope.account.userType == 3||sessionScope.account.userType == 2) && sessionScope.account.department != 4 && sessionScope.account.department != 5}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.department == 4|| sessionScope.account.department == 5}">
                                <a href="#saleDialog" data-toggle="modal" data-url="carStatusChange?id=${cp.carRecord.id }&changeType=1" data-title="车辆销售"
                                       data-tip="成本录入和售前整备是否已填，确认要销售嘛？" class="sale-trigger"><i class="icon-trash"></i> 车辆销售</a>
                            </c:if>
                            <c:if test="${sessionScope.account.userType == 1 || (sessionScope.account.userType == 2&&sessionScope.account.department == 4)}">
                                <a href="#confirmDialog" data-toggle="modal" data-url="carPurchaseDelete?id=${cp.carRecord.id }&recordStatus=2" data-title="删除记录"
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

<%-- 销售确认 --%>
<div id="saleDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-4" for="deposit">订金：</label>
                    <input class="form-control  col-xs-4" type="text" name="deposit" id="deposit" placeholder="请输入金额" autocomplete="off"/>
                    <div class="col-xs-2 modal-body-need"></div>
                </div>
                <div class=" modal-body-inner"></div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary sale-do-trigger">确定</button>
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
        $('.sale-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            var tip = self.attr('data-tip');
            $('.modal-title').html(title);
            $('.modal-body-inner').html(tip);
            $('.sale-do-trigger').click(function () {
                document.location.href = url + '&deposit=' + document.getElementById('deposit').value;
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

    var searchKey = document.getElementById('searchKey');
    for (var i = 0; i < searchKey.options.length; i++) {
        if (searchKey.options[i].value == '${searchKey}') {
            searchKey.options[i].selected = true;
        }
    }
</script>
