<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>已售管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">已售管理</li>
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

<hr/>
<div class="container">
    <form class="form-horizontal" action="carSearchPurchase" method="get">
        <div class="form-group">
            <div class="col-sm-2">
                <select class="form-control" name="searchKey" id="searchKey" onchange="show(this)">
                    <option value="">未选择中</option>
                    <option value="frame_num">车架号</option>
                    <option value="key_num">钥匙编号</option>
                    <option value="car_brand">品牌</option>
                    <option value="car_model">车型</option>
                    <option value="purchase_person">采购人</option>
                    <option value="inside_person">内部合伙人</option>
                    <option value="purchase_date">采购日期</option>
                    <option value="sale_date">销售日期</option>
                </select>
            </div>
            <div class="col-sm-2" id="dsv" style="display: none">
                <input class="form-control" type="text" name="searchValue" id="searchValue" value="${searchValue }" placeholder="请输入" autocomplete="off"/>
            </div>
            <div class="col-sm-2" id="dbt" style="display: none">
                <input class="form-control" type="text" name="btime" id="btime" value="${btime }" onclick="new Calendar().show(this);" placeholder="开始日期（必填)" autocomplete="off"/>
            </div>
            <div class="col-sm-2" id="det" style="display: none">
                <input class="form-control" type="text" name="etime" id="etime" value="${etime }" onclick="new Calendar().show(this);" placeholder="结束日期" autocomplete="off"/>
            </div>
            <div class="col-sm-2" id="dsOne" style="display: block"></div>
            <div class="col-sm-2" id="dsTwo" style="display: block"></div>
            <div class="hidden">
                <input type="text" name="recordStatus" id="recordStatus" value="4" autocomplete="off"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">转已售日期：</label>
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
            </div>
        </div>
    </form>
</div>
<hr/>
<div class="alert alert-info">
    车辆已售总数：【${carNum}】
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
            <c:if test="${export == 1 && sessionScope.account.userType == 1 }">
                <p><a href="carRecordExport" class="btn btn-primary"><i class="icon-plus icon-white"></i> 导出</a></p>
            </c:if>
            <table id="traceTable" class="table table-striped table-bordered table-condensed table-hover">
                <colgroup>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="7%"/>
                    <col width="9%"/>
                </colgroup>
                <tr>
                    <th>钥匙编号</th>
                    <th>车架号</th>
                    <th>采购人</th>
                    <th>车型</th>
                    <th>销售员</th>
                    <th>销售日期</th>
                    <th>转已售日期</th>
                    <th>车辆信息</th>
                    <th>售前费用</th>
                    <th>销售信息</th>
                    <th>销售费用</th>
                    <th>提成信息</th>
                    <th>放款信息</th>
                    <th>售后整备</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${list}" varStatus="status">
                    <tr>
                        <td>${cp.carRecord.keyNum}</td>
                        <td>${cp.carRecord.frameNum}</td>
                        <td>
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
                                ${cp.carRecord.purchasePerson}
                            </c:if>
                        </td>
                        <td>${cp.carRecord.carModel}</td>
                        <td>${cp.carSaleInfo.salePerson}</td>
                        <td>${cp.carSaleInfo.strSaleDate}</td>
                        <td>${cp.carRecord.strSoldDate}</td>
                        <td>
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
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
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">订金：${cp.carRecord.deposit}</label>
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
                                                    <label class="col-sm-3 control-label">采购日期：${cp.carRecord.strPurchaseDate}</label>
                                                    <div class="col-sm-1"></div>
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
                                                    <label class="col-sm-3 control-label">新车指导价：${cp.carRecord.carNewSale}</label>
                                                    <div class="col-sm-1"></div>
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
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
                                ${cp.allCost}，<a href="#CarMoneyCost-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="CarMoneyCost-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="CarMoneyCostLabel">售前费用详情</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="pl" items="${cp.costList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">${pl.linkName}：${pl.money}，备注：${pl.moneyDesc}</label>
                                                    </div>
                                                </c:forEach>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">其他费用：${cp.otherAllFee}</label>
                                                    <c:forEach var="oi" items="${cp.otherList}" varStatus="status">
                                                        <div class="form-group row">
                                                            <label class="col-sm-6 control-label">${oi.setupName}：${oi.setupFee}</label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">售前整备费：${cp.preAllFee }</label>
                                                    <div class="col-sm-5">
                                                        <c:forEach var="pl" items="${cp.preList}" varStatus="status">
                                                            <div class="form-group row">
                                                                <label class="col-sm-4 control-label">${pl.setupName}:${pl.setupFee}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
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
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
                            ${cp.carSaleInfo.saleMoney}，<a href="#saleDetail-${cp.carSaleInfo.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                                <div id="saleDetail-${cp.carSaleInfo.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="mySaleLabel">销售信息</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">已付/销售：${cp.carSaleInfo.paidMoney}/${cp.carSaleInfo.saleMoney}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">保险公司：${cp.carSaleInfo.expenseCompany }</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">商业保险费：${cp.carSaleInfo.businessExpenseFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">强制保险费：${cp.carSaleInfo.forceExpenseFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">保险返点：${cp.carSaleInfo.expenseRebate}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">中介费：${cp.carSaleInfo.agencyFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户属性：${cp.carSaleInfo.strConsumerProperty}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">获客渠道：${cp.carSaleInfo.strConsumerResource}</label>

                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户名称：${cp.carSaleInfo.consumerName}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户性别：<c:if test="${cp.carSaleInfo.consumerSex == 1}">男</c:if><c:if test="${cp.carSaleInfo.consumerSex == 2}">女</c:if></label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">客户年龄：${cp.carSaleInfo.strConsumerAge}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">客户居住地：${cp.carSaleInfo.consumerAddress}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">电话号码：${cp.carSaleInfo.consumerPhone}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">付款方式：<c:if test="${cp.carSaleInfo.saleType == 1}">全款</c:if><c:if test="${cp.carSaleInfo.saleType == 2}">按揭</c:if></label>
                                                    <div class="col-sm-1"></div>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">是否产生售后服务基金：<c:if test="${cp.carSaleInfo.isProduce == 1}">是</c:if><c:if test="${cp.carSaleInfo.isProduce == 0}">否</c:if></label>
                                                </div
                                                <c:if test="${cp.carSaleInfo.saleType == 2}">
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">对接按揭专员：${cp.carSaleInfo.mortgageRecord.mortgageCommissioner}</label>
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">按揭公司：${cp.carSaleInfo.mortgageRecord.mortgageCompany}</label>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-sm-4 control-label">贷款金额：${cp.carSaleInfo.mortgageRecord.loanFee}</label>
                                                        <div class="col-sm-1"></div>
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
                                                        <div class="col-sm-1"></div>
                                                        <label class="col-sm-4 control-label">按揭是否已放款：<c:if test="${cp.carSaleInfo.mortgageRecord.isMortgage == 0}">否</c:if><c:if test="${cp.carSaleInfo.mortgageRecord.isMortgage == 1}">是</c:if></label>
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
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
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
                                                    </div>
                                                </c:forEach>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">销售整备费：${cp.saleAllFee}</label>
                                                    <div class="col-sm-5">
                                                        <c:forEach var="pl" items="${cp.saleList}" varStatus="status">
                                                            <div class="form-group row">
                                                                <label class="col-sm-6 control-label">${pl.setupName}：${pl.setupFee}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <label>版权所有© 2018 车猫</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
                                ${cp.carRecord.grossProfit}，
                            </c:if>
                            <a href="#wagesAssistDetail-${cp.wagesAssist.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                            <div id="wagesAssistDetail-${cp.wagesAssist.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myWagesAssistLabel">提成信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                                                ||sessionScope.account.department != 3}">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">车辆毛利润：${cp.carRecord.grossProfit}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">采购提成：${cp.wagesAssist.purchaseCommission }</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">车溢价：${cp.carRecord.vehiclePremium}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">库存周期：${cp.carRecord.stockDuration}天</label>
                                                </div>
                                            </c:if>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">内部入股分红：${cp.wagesAssist.shareDividends}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">销售提成：${cp.wagesAssist.carCommission}</label>
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
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
                                <c:if test="${cp.carSaleInfo.saleType == 1}">    -</c:if>
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
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${sessionScope.account.department == 3&& sessionScope.account.userType == 3}">******</c:if>
                            <c:if test="${(sessionScope.account.department == 3&& sessionScope.account.userType != 3)
                            ||sessionScope.account.department != 3}">
                                ${cp.afterAllFee}<a href="#CarSaleSetupAfter-${cp.carRecord.id}" data-toggle="modal"><i class="icon-trash"></i> ，明细</a>
                                <div id="CarSaleSetupAfter-${cp.carRecord.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="CarSaleSetupPreLabel">售后整备明细</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="pl" items="${cp.afterList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">${pl.setupName}：${pl.setupFee}</label>
                                                    <c:if test="${sessionScope.account.department != 3}">
                                                        <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${pl.carCostId}&setupType=2" data-title="删除记录"
                                                           data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                    </c:if>
                                                </div>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <div class="col-sm-2"></div>
                                                <c:if test="${sessionScope.account.department != 3}">
                                                    <div class="col-sm-4">
                                                        <a href="carSaleSetupAction?carCostId=${cp.carRecord.id }&action=1&setupType=2"><i class="icon-trash"></i> 添加售后整备</a>
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
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${sessionScope.account.department == 3}">******</c:if>
                            <c:if test="${sessionScope.account.department != 3}">
                                <a href="#saleDialog" data-toggle="modal" data-url="carStatusChange?id=${cp.carRecord.id }&changeType=4" data-title="车辆回再售"
                                   data-tip="确认要退回再售嘛？" class="sale-trigger"><i class="icon-trash"></i> 车辆回在售</a>
                                <c:if test="${ sessionScope.account.userType == 1}">
                                    <a href="#confirmDialog" data-toggle="modal" data-url="carPurchaseDelete?id=${cp.carRecord.id }&recordStatus=4" data-title="删除记录"
                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                </c:if>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
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
    function show(obj) {
        var id = obj.id;
        if (id == "searchKey") {
            if (obj.value == "purchase_date" ||obj.value == "sale_date" ) {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "block";
                document.getElementById("det").style.display = "block";
                document.getElementById("dsOne").style.display = "none";
                document.getElementById("dsTwo").style.display = "none";
            } else if (obj.value == "frame_num"
                ||obj.value == "key_num"
                || obj.value == "car_model"
                || obj.value == "purchase_person"
                || obj.value == "inside_person"
                || obj.value == "car_brand") {
                document.getElementById("dsv").style.display = "block";
                document.getElementById("dbt").style.display = "none";
                document.getElementById("det").style.display = "none";
                document.getElementById("dsOne").style.display = "block";
                document.getElementById("dsTwo").style.display = "none";
            } else {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "none";
                document.getElementById("det").style.display = "none";
                document.getElementById("dsOne").style.display = "block";
                document.getElementById("dsTwo").style.display = "block";
            }
        }
    }
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
                document.location.href = url + '&goonPaid=' + document.getElementById('goonPaid').value;
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
                document.location.href = url;
            });
        });
    });

    var searchKey = document.getElementById('searchKey');
    for (var i = 0; i < searchKey.options.length; i++) {
        if (searchKey.options[i].value == '${searchKey}') {
            searchKey.options[i].selected = true;
            if (searchKey.options[i].value == "purchase_ate" ||searchKey.options[i].value == "sale_date" ) {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "block";
                document.getElementById("det").style.display = "block";
                document.getElementById("dsOne").style.display = "none";
                document.getElementById("dsTwo").style.display = "none";
            } else if (searchKey.options[i].value == "frame_num"
                ||searchKey.options[i].value == "key_num"
                || searchKey.options[i].value == "car_model"
                || searchKey.options[i].value == "purchase_person"
                ||searchKey.options[i].value == "inside_person"
                ||searchKey.options[i].value == "car_brand") {
                document.getElementById("dsv").style.display = "block";
                document.getElementById("dbt").style.display = "none";
                document.getElementById("det").style.display = "none";
                document.getElementById("dsOne").style.display = "block";
                document.getElementById("dsTwo").style.display = "none";
            } else {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "none";
                document.getElementById("det").style.display = "none";
                document.getElementById("dsOne").style.display = "block";
                document.getElementById("dsTwo").style.display = "block";
            }
        }
    }
</script>
