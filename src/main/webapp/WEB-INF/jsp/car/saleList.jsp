<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>在售车辆管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">在售车辆</li>
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
                    <col width="5%"/>
                    <col width="7%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="13%"/>
                </colgroup>
                <tr>
                    <th>车型</th>
                    <th>钥匙编号</th>
                    <th>采购日期</th>
                    <th>采购人</th>
                    <th>车架号</th>
                    <th>车辆信息</th>
                    <th>售前费用</th>
                    <th>销售信息</th>
                    <th>保险信息</th>
                    <th>销售费用</th>
                    <th>销售整备</th>
                    <th>转已售日期</th>
                    <th>订金</th>
                    <th>已收金额/应收金额</th>
                    <th>贷款金额/放款金额</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${list}" varStatus="status">
                    <tr>
                        <td>${cp.carRecord.carModel}</td>
                        <td>${cp.carRecord.keyNum}</td>
                        <td>${cp.carRecord.strPurchaseDate}</td>
                        <td>${cp.carRecord.purchasePerson}</td>
                        <td>${cp.carRecord.frameNum}</td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2}">
                                <a href="#carDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="carDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myModalLabel">车辆信息</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">展厅标价：${cp.carRecord.hallMoney}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">全款权限底价：${cp.carRecord.qAuthorityMoney}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">按揭权限底价：${cp.carRecord.aAuthorityMoney}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">中介费：${cp.carRecord.agencyFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">已付/采购价：${cp.carRecord.paidMoney }/${cp.carRecord.purchaseMoney }</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">其他收入：${cp.otherAllFee }</label>
                                                    <div class="col-sm-5">
                                                        <c:forEach var="oi" items="${cp.otherList}" varStatus="status">
                                                            <div class="form-group row">
                                                                <label class="col-sm-6 control-label">项目：${oi.setupName}</label>
                                                                <label class="col-sm-6 control-label">金额：${oi.setupFee}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <c:if test="${cp.carRecord.outsidePerson != ''}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">外部合伙人：${cp.carRecord.outsidePerson}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">外部合伙金额：${cp.carRecord.outsideMoney}</label>
                                                    </div>
                                                </c:if>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">车系：${cp.carRecord.strCarLine}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">车辆类别：${cp.carRecord.strCarLevel}</label>
                                                    <div class="col-sm-1"></div>
                                                </div>
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
                                                        <a href="carPurchaseAction?id=${cp.carRecord.id }&action=2&recordStatus=3"><i class="icon-lock"></i> 修改车辆信息</a>
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
                            ${cp.allCost}，<a href="#CarMoneyCost-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                            <div id="CarMoneyCost-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="CarMoneyCostLabel">售前费用信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="pl" items="${cp.costList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">${pl.linkName}：${pl.money}，备注：${pl.moneyDesc}</label>
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="carMoneyDelete?id=${pl.id}&carRecordId=${pl.carRecordId}&setupType=12&recordStatus=3" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">其他费用：${cp.otherAllFee}</label>
                                                <div class="col-sm-5">
                                                    <c:forEach var="pl" items="${cp.otherList}" varStatus="status">
                                                        <div class="form-group row">
                                                            <label class="col-sm-4 control-label">项目：${pl.setupName}：${pl.setupFee}</label>
                                                            <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${cp.carRecord.id}&setupType=3&recordStatus=3" data-title="删除记录"
                                                               data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">售前整备费：${cp.preAllFee }</label>
                                                <div class="col-sm-5">
                                                    <c:forEach var="pl" items="${cp.preList}" varStatus="status">
                                                        <div class="form-group row">
                                                            <label class="col-sm-4 control-label">项目：${pl.setupName}：${pl.setupFee}</label>
                                                            <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${cp.carRecord.id}&setupType=1&recordStatus=3" data-title="删除记录"
                                                               data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <c:if test="${sessionScope.account.department != 3}">
                                                <div class="form-group row">
                                                    <div class="col-sm-3">
                                                        <a href="carMoneyAction?carRecordId=${cp.carRecord.id }&action=1&setupType=12&recordStatus=3"><i class="icon-trash"></i> 添加成本录入</a>
                                                    </div>
                                                    <div class="col-sm-1"></div>
                                                    <div class="col-sm-3">
                                                        <a href="carSaleSetupAction?carCostId=${cp.carRecord.id }&action=1&setupType=3&recordStatus=3"><i class="icon-trash"></i> 添加其他费用</a>
                                                    </div>
                                                    <div class="col-sm-1"></div>
                                                    <div class="col-sm-3">
                                                        <a href="carSaleSetupAction?carCostId=${cp.carRecord.id }&action=1&setupType=1&recordStatus=3"><i class="icon-trash"></i> 添加售前整备</a>
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
                        </td>
                        <td>
                            <c:if test="${cp.carRecord.isSale == 0}">否，<a href="carSaleInfoAction?recordId=${cp.carRecord.id }&action=1"><i class="icon-lock"></i> 录入</a></c:if>
                            <c:if test="${cp.carRecord.isSale == 1}"><a href="#saleDetail-${cp.carSaleInfo.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="saleDetail-${cp.carSaleInfo.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="mySaleLabel">销售信息</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">销售员：${cp.carSaleInfo.salePerson}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">销售日期：${cp.carSaleInfo.strSaleDate}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">销售价格：${cp.carSaleInfo.saleMoney}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">中介费：${cp.carSaleInfo.agencyFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">预收保险金额：${cp.carSaleInfo.unearnedInsurance}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">客户属性：${cp.carSaleInfo.strConsumerProperty}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">获客渠道：${cp.carSaleInfo.strConsumerResource}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户姓名：${cp.carSaleInfo.consumerName}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">客户年龄：${cp.carSaleInfo.strConsumerAge}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户性别：<c:if test="${cp.carSaleInfo.consumerSex == 1}">男</c:if><c:if test="${cp.carSaleInfo.consumerSex == 2}">女</c:if></label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">电话号码：${cp.carSaleInfo.consumerPhone}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户居住地：${cp.carSaleInfo.consumerAddress}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">付款方式：<c:if test="${cp.carSaleInfo.saleType == 1}">全款</c:if><c:if test="${cp.carSaleInfo.saleType == 2}">按揭</c:if></label>
                                                </div>
                                                <c:if test="${cp.carSaleInfo.saleType == 2}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">对接按揭专员：${cp.carSaleInfo.mortgageRecord.mortgageCommissioner}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">按揭公司：${cp.carSaleInfo.mortgageRecord.mortgageCompany}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">利率：${cp.carSaleInfo.mortgageRecord.interestRate}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">按揭返点：${cp.carSaleInfo.mortgageRecord.mortgageRebate}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">返还金额：${cp.carSaleInfo.mortgageRecord.backFee}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">评估费：${cp.carSaleInfo.mortgageRecord.assessmentFee}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">风险金：${cp.carSaleInfo.mortgageRecord.riskFee}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">续保押金：${cp.carSaleInfo.mortgageRecord.renewalFee}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">垫资费：${cp.carSaleInfo.mortgageRecord.padFee}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">上门费：${cp.carSaleInfo.mortgageRecord.doorFee}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">印花税：${cp.carSaleInfo.mortgageRecord.stampDuty}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">其它费用：${cp.carSaleInfo.mortgageRecord.otherFee}</label>
                                                    </div>
                                                </c:if>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">是否产生售后服务基金：<c:if test="${cp.carSaleInfo.isProduce == 1}">是</c:if><c:if test="${cp.carSaleInfo.isProduce == 0}">否</c:if></label>
                                                </div
                                                <c:if test="${sessionScope.account.department != 3}">
                                                    <div class="form-group row">
                                                        <a href="carSaleInfoAction?id=${cp.carSaleInfo.id }&recordId=${cp.carRecord.id }&action=2"><i class="icon-lock"></i> 修改销售信息</a>
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
                            <c:if test="${cp.carRecord.isSale == 0}">先录入销售信息</c:if>
                            <c:if test="${cp.carRecord.isSale == 1&&empty cp.carSaleInfo.expenseCompany}">
                                否，<a href="carExpenseAction?id=${cp.carSaleInfo.id }&action=1"><i class="icon-lock"></i> 录入</a>
                            </c:if>
                            <c:if test="${cp.carRecord.isSale == 1&& not empty cp.carSaleInfo.expenseCompany}">
                                <a href="#expenseDetail-${cp.carSaleInfo.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="expenseDetail-${cp.carSaleInfo.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myExpenseLabel">保险信息</h4>
                                            </div>
                                            <div class="modal-body">
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">保险公司：${cp.carSaleInfo.expenseCompany }</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">保险返点：${cp.carSaleInfo.expenseRebate}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">商业保险费：${cp.carSaleInfo.businessExpenseFee}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">强制保险费：${cp.carSaleInfo.forceExpenseFee}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">实收保费总金额：${cp.carSaleInfo.allUnearnedInsurance}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-5 control-label">支付保险公司金额：${cp.carSaleInfo.payCompanyFee}</label>
                                            </div>
                                            <c:if test="${not empty cp.moneyAssistList}">
                                                <c:forEach var="pma" items="${cp.moneyAssistList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-12 control-label">预收保险金额：${pma.oldPayMoney}，总保险金额：${pma.newPayMoney}，
                                                            <c:if test="${pma.difference < 0}">
                                                            备注：退还客户${0-pma.difference}元多收保费
                                                            </c:if>
                                                        </label>
                                                    </div>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${sessionScope.account.department != 3}">
                                                <div class="form-group row">
                                                    <a href="carExpenseAction?id=${cp.carSaleInfo.id }&action=2"><i class="icon-lock"></i> 修改保险信息</a>
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
                            ${cp.allSf}，<a href="#sfDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                            <div id="sfDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="mySfLabel">销售费用信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="pl" items="${cp.sfList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">${pl.linkName}：${pl.money}，备注：${pl.moneyDesc}</label>
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="carMoneyDelete?id=${pl.id}&carRecordId=${pl.carRecordId}&setupType=13&recordStatus=3" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                            </c:forEach>
                                            <c:if test="${sessionScope.account.department != 3}">
                                                <div class="form-group row">
                                                    <div class="col-sm-4">
                                                        <a href="carMoneyAction?carRecordId=${cp.carRecord.id }&action=1&setupType=13&recordStatus=3"><i class="icon-trash"></i> 添加销售费用录入</a>
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
                        </td>
                        <td>
                            ${cp.saleAllFee}，<a href="#CarSf-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 明细</a>
                            <div id="CarSf-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="CarSfLabel">销售整备明细</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="pl" items="${cp.saleList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">项目：${pl.setupName}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">金额：${pl.setupFee}</label>
                                                    <c:if test="${sessionScope.account.department != 3}">
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${pl.carCostId}&setupType=4" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                    </c:if>
                                                </div>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <div class="col-sm-2"></div>
                                                <c:if test="${sessionScope.account.department != 3}">
                                                    <div class="col-sm-4">
                                                        <a href="carSaleSetupAction?carCostId=${cp.carRecord.id }&action=1&setupType=4"><i class="icon-trash"></i> 添加销售整备</a>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <c:if test="${sessionScope.account.userType == 3 || sessionScope.account.userType == 2}">*******</c:if>
                            <c:if test="${sessionScope.account.userType == 1 }">
                                <c:if test="${cp.carRecord.soldDate == 0}">
                                    <a href="#dateDialog" data-toggle="modal" data-url="carSoldDate?id=${cp.carRecord.id }" data-title="添加日期" class="date-trigger"><i class="icon-trash"></i> 添加日期</a>
                                </c:if>
                                <c:if test="${cp.carRecord.soldDate != 0}">${cp.carRecord.strSoldDate},<a href="#dateDialog" data-toggle="modal" data-url="carSoldDate?id=${cp.carRecord.id }" data-title="修改日期" class="date-trigger"><i class="icon-trash"></i> 修改</a>
                                </c:if>
                            </c:if>
                        </td>
                        <td>${cp.carRecord.deposit}
                            <c:if test="${not empty cp.backList}"><a href="#backDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> ，退订金记录</a>
                            <div id="backDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myBackLabel">退订金记录</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="ppl" items="${cp.backList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-5 control-label">退已付金额：${ppl.paidMoney}</label>
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
                            <c:if test="${cp.carRecord.isSale == 0}">    -</c:if>
                            <c:if test="${cp.carRecord.isSale == 1}">${cp.carSaleInfo.paidMoney }/${cp.carSaleInfo.payMoney }</c:if>
                            <c:if test="${not empty cp.salePaidList}"><a href="#recordDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> ，付款记录</a>
                                <div id="recordDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myRecordLabel">付款记录</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="ppl" items="${cp.salePaidList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-5 control-label">付款金额：${ppl.paidMoney}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-5 control-label">描述：${ppl.paidReason}</label>
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carSaleDelete?id=${ppl.id}&carSaleInfoId=${cp.carSaleInfo.id}" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
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
                        <td><c:if test="${cp.carSaleInfo.saleType == 1}">    -</c:if>
                            <c:if test="${cp.carSaleInfo.saleType == 2}">${cp.carSaleInfo.mortgageRecord.loanFee}/${cp.carSaleInfo.mortgageRecord.mortgageMoney}</c:if>
                            <c:if test="${not empty cp.mortgagePaidList}"><a href="#mrecordDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> ，放款记录</a>
                                <div id="mrecordDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myMRecordLabel">放款记录</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="ppl" items="${cp.mortgagePaidList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-5 control-label">放款金额：${ppl.paidMoney}</label>
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
                            <a href="#remarkDetail-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 备注</a>
                            <div id="remarkDetail-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myRemarkLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myRemarkLabel">备注记录</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="ppl" items="${cp.carRecord.saleRemark}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-6 control-label">${ppl.remarkDate}：${ppl.remark}</label>
                                                    <div class="col-sm-1"></div>
                                                    <c:if test="${sessionScope.account.department != 3}">
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carRemarkDel?id=${ppl.id}&remarkType=3" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                    </c:if>
                                                </div>
                                                <hr/>
                                            </c:forEach>
                                            <c:if test="${sessionScope.account.department != 3}">
                                                <div class="form-group row">
                                                    <div class="col-sm-2"></div>
                                                    <div class="col-sm-4">
                                                        <a href="#remarkDialog" data-toggle="modal" data-url="carRemarkAdd?carRecordId=${cp.carRecord.id}&remarkType=3" data-title="备注" class="remark-trigger"><i class="icon-trash"></i> 添加备注</a>
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
                            <c:if test="${cp.carRecord.isSale == 1
                            && sessionScope.account.userType != 3
                            && sessionScope.account.department != 3}">
                                <a href="#paidDialog" data-toggle="modal" data-url="carSalePaid?id=${cp.carSaleInfo.id }" data-title="收账"
                                   data-need="${cp.carSaleInfo.payMoney - cp.carSaleInfo.paidMoney }" class="paid-trigger"><i class="icon-trash"></i> 收账</a>
                            </c:if>
                            <c:if test="${cp.carSaleInfo.saleType == 2
                            && sessionScope.account.userType != 3
                            && sessionScope.account.department != 3}">
                                <a href="#mortgageDialog" data-toggle="modal" data-url="mortgageCarPaid?id=${cp.carSaleInfo.mortgageRecord.id }" data-title="放款"
                                   class="mortgage-trigger"><i class="icon-trash"></i> 放款</a>
                            </c:if>
                            <c:if test="${sessionScope.account.department != 3}">
                                <a href="#saleDialog" data-toggle="modal" data-url="carStatusChange?id=${cp.carRecord.id }&changeType=2" data-title="车辆回库存"
                                   data-tip="确认要退回库存嘛？" class="sale-trigger"><i class="icon-trash"></i> 退订</a>
                            </c:if>
                            <c:if test="${ sessionScope.account.userType == 1}">
                                <a href="#confirmDialog" data-toggle="modal" data-url="carPurchaseDelete?id=${cp.carRecord.id }&recordStatus=3" data-title="删除记录"
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

<%-- 日期添加 --%>
<div id="dateDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="soldDate">转已售日期：</label>
                    <input class="form-control col-xs-2 " type="text" name="soldDate" id="soldDate" value="${soldDate}" onclick="new Calendar().show(this);" placeholder="请输入" autocomplete="off"/>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary date-do-trigger">确定</button>
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
                    <label class="control-label col-xs-4" for="goonPaid">金额：</label>
                    <input class="form-control  col-xs-4" type="text" name="goonPaid" id="goonPaid" placeholder="请输入金额" autocomplete="off"/>
                    <div class="col-xs-2 modal-body-need"></div>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-4" for="paidReason">描述：</label>
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

<%-- 放款 --%>
<div id="mortgageDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2" for="mortgageMoney">金额：</label>
                    <input class="form-control  col-xs-4" type="text" name="mortgageMoney" id="mortgageMoney" placeholder="请输入金额" autocomplete="off"/>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="mortgageReason">摘要：</label>
                    <textarea rows="5" cols="" name="mortgageReason" id="mortgageReason" class="control-box col-xs-7"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary mortgage-do-trigger">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

<%-- 金额修改 --%>
<div id="moneyDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-4" for="modMoney">新预收保险金额：</label>
                    <input class="form-control  col-xs-4" type="text" name="modMoney" id="modMoney" placeholder="请输入金额" autocomplete="off"/>
                    <div class="col-xs-2 modal-body-need"></div>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-4" for="modDesc">修改原因：</label>
                    <input class="form-control  col-xs-4" type="text" name="modDesc" id="modDesc" placeholder="请输入" autocomplete="off"/>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary money-do-trigger">确定</button>
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
                    <label class="control-label col-xs-4" for="backMoney">退已付金额：</label>
                    <input class="form-control  col-xs-4" type="text" name="backMoney" id="backMoney" placeholder="请输入金额" autocomplete="off"/>
                    <div class="col-xs-2 modal-body-need"></div>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-4" for="backDesc">备注：</label>
                    <input class="form-control  col-xs-4" type="text" name="backDesc" id="backDesc" placeholder="请输入" autocomplete="off"/>
                </div>
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
        $('.date-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.date-do-trigger').click(function () {
                document.location.href = url + '&soldDate=' + document.getElementById('soldDate').value;
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
            if(need != null){
                $('.modal-body-need').html('还需付款'+ need + '元');
            }
            $('.paid-do-trigger').click(function () {
                document.location.href = url + '&goonPaid=' + document.getElementById('goonPaid').value + '&paidReason=' + document.getElementById('paidReason').value;
            });
        });
    });

    $(function () {
        /*确认*/
        $('.mortgage-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.mortgage-do-trigger').click(function () {
                document.location.href = url + '&goonPaid=' + document.getElementById('mortgageMoney').value + '&paidReason=' + document.getElementById('mortgageReason').value;
            });
        });
    });

    $(function () {
        /*确认*/
        $('.money-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.money-do-trigger').click(function () {
                document.location.href = url + '&modMoney=' + document.getElementById('modMoney').value + '&modDesc=' + document.getElementById('modDesc').value;
            });
        });
    });

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
                document.location.href = url + '&backMoney=' + document.getElementById('backMoney').value + '&backDesc=' + document.getElementById('backDesc').value;
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
