<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>按揭管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <c:if test="${mortgageType==1}">
        <li class="active">代办按揭管理</li>
    </c:if>
    <c:if test="${mortgageType==2}">
        <li class="active">外拓按揭管理</li>
    </c:if>

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
    <form class="form-inline" action="mortgageSearch" method="get">
        <div class="col-sm-2">
            <div class="form-group">
                <select class="form-control" name="searchKey" id="searchKey" onchange="show(this)">
                    <option value="">未选择中</option>
                    <option value="consumerName">客户姓名</option>
                    <c:if test="${mortgageType==1}">
                        <option value="actionPerson">业务员</option>
                    </c:if>
                    <c:if test="${mortgageType==2}">
                        <option value="actionPerson">委托对象</option>
                    </c:if>
                    <option value="mortgageCompany">按揭公司</option>
                    <option value="mortgageCommissioner">按揭专员</option>
                    <option value="mortgageDate">放款日期</option>
                </select>
            </div>
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
            <input type="text" name="mortgageType" id="mortgageType" value="${mortgageType}" autocomplete="off"/>
        </div>
        <div class="col-sm-2">
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-search icon-white"></i> 查询
                </button>
                <a class="btn btn-small" href="mortgageAction?action=1&mortgageType=${mortgageType}">按揭录入</a>
            </div>
        </div>
    </form>
</div>
<hr/>
<div>
    <c:choose>
        <c:when test="${empty dataList }">
            <div class="container">
                <div class="alert alert-block">
                    <a href="#" class="close" data-dismiss="alert">x</a>
                    未找到匹配的记录
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${export == 1 }">
                <p><a href="mortgageExport" class="btn btn-primary"><i class="icon-plus icon-white"></i> 导出</a></p>
            </c:if>
            <table id="traceTable" class="table table-striped table-bordered table-condensed table-hover">
                <colgroup>
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
                    <col width="6%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="10%"/>
                </colgroup>
                <tr>
                    <th>
                        <c:if test="${mortgageType==1}">业务员</c:if>
                        <c:if test="${mortgageType==2}">委托对象</c:if>
                    </th>
                    <th>客户姓名</th>
                    <th>客户手机</th>
                    <th>车辆品牌</th>
                    <th>车型</th>
                    <th>按揭公司</th>
                    <th>按揭专员</th>
                    <th>续保押金</th>
                    <th>其他信息</th>
                    <th>放款日期</th>
                    <th>代付金额</th>
                    <th>毛利润</th>
                    <th>应支付客户金额</th>
                    <th>放款金额</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${dataList}" varStatus="status">
                    <tr>
                        <td>${cp.actionPerson}</td>
                        <td>${cp.consumerName}</td>
                        <td>${cp.consumerPhone}</td>
                        <td>${cp.carBrand }</td>
                        <td>${cp.carModel  }</td>
                        <td>${cp.mortgageCompany}</td>
                        <td>${cp.mortgageCommissioner}</td>
                        <td>${cp.renewalFee}</td>
                        <td>
                            <a href="#other-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                            <div id="other-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">其他信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group row">
                                                <label class="col-sm-3 control-label">贷款金额：${cp.loanFee}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-3 control-label">利率：${cp.interestRate}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-3 control-label">按揭返点：${cp.mortgageRebate}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-3 control-label">返还金额：${cp.backFee}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-3 control-label">评估费：${cp.assessmentFee}</label>
                                            </div>
                                            <c:if test="${mortgageType==1}">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">风险金：${cp.riskFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">垫资费：${cp.padFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">上门费：${cp.doorFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">印花税：${cp.stampDuty}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">其它费用：${cp.otherFee}</label>
                                                </div>
                                            </c:if>
                                            <c:if test="${mortgageType==2}">
                                                <div class="form-group row">
                                                    <label class="col-sm-6 control-label">支付委托人返点金额：${cp.payBackFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">签单费：${cp.signBillFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">超年限费：${cp.overYearFee}</label>
                                                </div>
                                            </c:if>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">保险公司：${cp.expenseCompany}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">商业险保费：${cp.businessExpenseFee}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">强制险保费：${cp.forceExpenseFee}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">商业保险返点：${cp.businessExpenseBack}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">商业险返点金额：${cp.businessExpenseRebate}</label>
                                            </div>
                                            <hr/>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <c:if test="${cp.mortgageDate == null}">
                                <a href="#dateDialog" data-toggle="modal" data-url="mortgageDateAction?id=${cp.id }&mortgageType=${mortgageType}" data-title="添加日期" class="date-trigger"><i class="icon-trash"></i> 添加日期</a>
                            </c:if>
                            <c:if test="${cp.mortgageDate != null}">${cp.strMortgageDate},<a href="#dateDialog" data-toggle="modal" data-url="mortgageDateAction?id=${cp.id }&mortgageType=${mortgageType}" data-title="修改日期" class="date-trigger"><i class="icon-trash"></i> 修改</a>
                            </c:if>
                        </td>
                        <td>${cp.agentPayFee},
                            <a href="#pay-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 明细</a>
                            <div id="pay-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="CarSfLabel">代付金额明细</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="pl" items="${cp.agentPayList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">项目：${pl.setupName}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">金额：${pl.setupFee}</label>
                                                    <!--<a href="carSaleSetupAction?id=${pl.id}&action=2&carCostId=${pl.carCostId}&setupType=8"><i class="icon-lock"></i> 修改</a>-->
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="carSaleSetupDelete?id=${pl.id}&carCostId=${pl.carCostId}&setupType=8" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <div class="col-sm-2"></div>
                                                <div class="col-sm-4">
                                                    <a href="carSaleSetupAction?carCostId=${cp.id }&action=1&setupType=8"><i class="icon-trash"></i> 添加项目</a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>${cp.grossProfit}</td>
                        <td>${cp.needPayConsumer}</td>
                        <td>${cp.mortgageMoney}
                            <c:if test="${not empty cp.mortgagePaidList}"><a href="#mrecordDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> ，放款记录</a>
                                <div id="mrecordDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
                            <a href="#mortgageDialog" data-toggle="modal" data-url="mortgagePaid?id=${cp.id }&mortgageType=${mortgageType}" data-title="放款"
                               class="mortgage-trigger"><i class="icon-trash"></i> 放款</a>
                            <a href="mortgageStatus?id=${cp.id }&mortgageType=${mortgageType}"><i class="icon-lock"></i>转数据库</a>
                            <a href="mortgageAction?id=${cp.id }&action=2&mortgageType=${mortgageType}"><i class="icon-lock"></i> 编辑</a>
                            <a href="#confirmDialog" data-toggle="modal" data-url="mortgageDelete?id=${cp.id }&mortgageType=${mortgageType}" data-title="删除记录"
                               data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
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
                    <label class="control-label col-xs-3 " for="soldDate">放款日期：</label>
                    <input class="form-control col-xs-3 " type="text" name="soldDate" id="soldDate" value="${soldDate}" onclick="new Calendar().show(this);" placeholder="请输入" autocomplete="off"/>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary date-do-trigger">确定</button>
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
                    <label class="control-label col-xs-4" for="mortgageMoney">金额：</label>
                    <input class="form-control  col-xs-4" type="text" name="mortgageMoney" id="mortgageMoney" placeholder="请输入金额" autocomplete="off"/>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-4" for="mortgageReason">描述：</label>
                    <input class="form-control  col-xs-4" type="text" name="mortgageReason" id="mortgageReason" placeholder="请输入" autocomplete="off"/>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary mortgage-do-trigger">确定</button>
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
            if (obj.value == "mortgageDate" ) {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "block";
                document.getElementById("det").style.display = "block";
                document.getElementById("dsOne").style.display = "none";
                document.getElementById("dsTwo").style.display = "none";
            } else if (obj.value == "consumerName" || obj.value == "actionPerson" || obj.value == "mortgageCompany"|| obj.value == "mortgageCommissioner") {
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

    var searchKey = document.getElementById('searchKey');
    for (var i = 0; i < searchKey.options.length; i++) {
        if (searchKey.options[i].value == '${searchKey}') {
            searchKey.options[i].selected = true;

            if (searchKey.options[i].value == "mortgageDate" ) {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "block";
                document.getElementById("det").style.display = "block";
                document.getElementById("dsOne").style.display = "none";
                document.getElementById("dsTwo").style.display = "none";
            } else if (searchKey.options[i].value == "consumerName" || searchKey.options[i].value == "actionPerson" || searchKey.options[i].value == "mortgageCompany"|| searchKey.options[i].value == "mortgageCommissioner") {
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
