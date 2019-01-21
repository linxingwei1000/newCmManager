<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>档案管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">档案管理</li>
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
<!--<p><a href="carPurchaseAction?action=1&recordStatus=1" class="btn btn-primary"><i class="icon-plus icon-white"></i> 车辆买入</a></p>-->
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
            <table id="traceTable" class="table table-striped table-bordered table-condensed table-hover">
                <colgroup>
                    <col width="9%"/>
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
                    <col width="7%"/>
                </colgroup>
                <tr>
                    <th>车型</th>
                    <th>钥匙编号</th>
                    <th>车辆销售状态</th>
                    <th>现车主名</th>
                    <th>签单照片</th>
                    <th>登记证书</th>
                    <th>行驶证</th>
                    <th>违章是否处理</th>
                    <th>强制险到期时间</th>
                    <th>商业险到期时间</th>
                    <th>档案信息</th>
                    <th>车辆状态</th>
                    <th>保险预算</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${dataList}" varStatus="status">
                    <tr>
                        <td>${cp.carRecord.carModel}</td>
                        <td>${cp.carRecord.keyNum}</td>
                        <td>
                            <c:if test="${cp.carRecord.recordStatus == 1}">买入</c:if>
                            <c:if test="${cp.carRecord.recordStatus == 2}">库存</c:if>
                            <c:if test="${cp.carRecord.recordStatus == 3}">销售</c:if>
                            <c:if test="${cp.carRecord.recordStatus == 4}">已售</c:if>
                        </td>
                        <td>${cp.carOwner}</td>
                        <td><c:if test="${cp.billSign == 0}">无</c:if><c:if test="${cp.billSign == 1}">有</c:if></td>
                        <td><c:if test="${cp.register == 0}">无</c:if><c:if test="${cp.register == 1}">有</c:if></td>
                        <td><c:if test="${cp.drivingLicense == 0}">无</c:if><c:if test="${cp.drivingLicense == 1}">有</c:if></td>
                        <td><c:if test="${cp.breakRule == 0}">否</c:if><c:if test="${cp.breakRule == 1}">是</c:if></td>
                        <td>
                            <a href="#QzxDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> ${cp.strQzxDate}</a>
                            <div id="QzxDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="aLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="aLabel">强制险信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">强制险被保险人：${cp.qzxPerson}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">强制险保险公司：${cp.qzxCompany}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">强制险保险公司所在地：${cp.qzxAddress}</label>
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
                            <a href="#SyxDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> ${cp.strSyxDate}</a>
                            <div id="SyxDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySyxLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="mySyxLabel">商业险信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">商业险被保险人：${cp.syxPerson}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">商业险保险公司：${cp.syxCompany}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">商业险保险公司所在地：${cp.syxAddress}</label>
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
                            <a href="#detail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                            <div id="detail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">档案信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">出厂日期：${cp.carRecord.carCreateTime}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">登记日期：${cp.carRecord.carPurchaseTime}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">车架号：${cp.carRecord.frameNum}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">钥匙数量：${cp.carKeyNum}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">采购人：${cp.carRecord.purchasePerson}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">采购日期：${cp.carRecord.strPurchaseDate}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">现车牌：${cp.carNum}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">现车牌所在地：${cp.carNumResource}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">过户次数：${cp.purchaseTimes}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">强制险贴：<c:if test="${cp.qzxStick == 0}">无</c:if><c:if test="${cp.qzxStick == 1}">有</c:if></label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">排放标准：${cp.carDischarge}</label>
                                                <div class="col-sm-1"></div>
                                                <label class="col-sm-4 control-label">年审到期时间：${cp.strAnnualTrial}</label>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-4 control-label">摘要：${cp.abs}</label>
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
                            <c:if test="${cp.carStatus == 0||cp.carStatus == null}">无</c:if>
                            <c:if test="${cp.carStatus == 1}">在途</c:if>
                            <c:if test="${cp.carStatus == 2}">整备</c:if>
                            <c:if test="${cp.carStatus == 3}">在店</c:if>
                            <c:if test="${ sessionScope.account.department != 3&&sessionScope.account.department != 6}">
                                <a href="#statusDialog" data-toggle="modal" data-url="dossierCarStatus?id=${cp.id }" data-title="修改车辆状态" class="status-trigger"><i class="icon-trash"></i> ，修改车辆状态</a>
                            </c:if>
                        </td>
                        <td>${cp.insuranceBudget}
                            <c:if test="${empty cp.budgetList}">
                                <a href="#preDialog" data-toggle="modal" data-url="dossierBudget?id=${cp.id }" data-title="保险预算" class="pre-trigger"><i class="icon-trash"></i> ，添加预算</a>
                            </c:if>
                            <c:if test="${not empty cp.budgetList}"><a href="#recordDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> ，预算记录</a>
                                <div id="recordDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title" id="myRecordLabel">预算记录</h4>
                                            </div>
                                            <div class="modal-body">
                                                <c:forEach var="ppl" items="${cp.budgetList}" varStatus="status">
                                                    <div class="form-group row">
                                                        <label class="col-sm-5 control-label">金额：${ppl.paidMoney}，${ppl.paidReason}</label>
                                                        <div class="col-sm-1"></div>
                                                            <a href="#confirmDialog" data-toggle="modal" data-url="dossierBudgetDel?id=${ppl.id}" data-title="删除记录"
                                                               data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>  </div>
                                                    <hr/>
                                                </c:forEach>
                                                <c:if test="${ sessionScope.account.department != 3&&sessionScope.account.department != 10}">
                                                    <div class="form-group row">
                                                        <a href="#preDialog" data-toggle="modal" data-url="dossierBudget?id=${cp.id }" data-title="保险预算" class="pre-trigger"><i class="icon-trash"></i> 添加预算</a>
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
                            <a href="#remarkDetail-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 备注</a>
                            <div id="remarkDetail-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myRemarkLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myRemarkLabel">备注记录</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="ppl" items="${cp.remarkList}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-6 control-label">${ppl.remarkDate}：${ppl.remark}</label>
                                                    <div class="col-sm-1"></div>
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="carRemarkDel?id=${ppl.id}&remarkType=4" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                                <hr/>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <div class="col-sm-2"></div>
                                                <div class="col-sm-4">
                                                    <a href="#remarkDialog" data-toggle="modal" data-url="carRemarkAdd?carRecordId=${cp.id}&remarkType=4" data-title="备注" class="remark-trigger"><i class="icon-trash"></i> 添加备注</a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <label>版权所有© 2018 车猫</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${ sessionScope.account.department != 3&&sessionScope.account.department != 10}">
                                <c:if test="${ displayStatus == 1}">
                                    <a href="dossierAction?id=${cp.id }&action=2&carRecordId=${cp.carRecordId}"><i class="icon-lock"></i> 编辑</a>
                                    <c:if test="${ sessionScope.account.department != 6}">
                                        <a href="#confirmDialog" data-toggle="modal" data-url="dossierStatus?id=${cp.id }&displayStatus=3" data-title="转已处理"
                                           data-tip="确认数据都填全了嘛？" class="confirm-trigger"><i class="icon-trash"></i> 转已处理</a>
                                    </c:if>
                                </c:if>
                                <!--<c:if test="${ sessionScope.account.userType == 1}">
                                    <a href="#confirmDialog" data-toggle="modal" data-url="dossierDelete?id=${cp.id }" data-title="删除记录"
                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                </c:if>-->
                                <c:if test="${ displayStatus == 3 && sessionScope.account.department != 6}">
                                    <a href="dossierStatus?id=${cp.id }&displayStatus=1"><i class="icon-lock"></i> 转未处理</a>
                                </c:if>
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

<%-- 车辆状态 --%>
<div id="statusDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2">车辆状态：</label>
                    <div class="form-group form-inline row">
                        <label class="radio inline col-xs-2"><input type="radio" name="carStatus" value="1" />在途</label>
                        <label class="radio inline col-xs-2"><input type="radio" name="carStatus" value="2" />整备</label>
                        <label class="radio inline col-xs-2"><input type="radio" name="carStatus" value="3" />在店</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary status-do-trigger">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

<%-- 保险预算 --%>
<div id="preDialog" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title">提示</h5>
            </div>
            <div class="modal-body">
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="goonPaid">保费预算：</label>
                    <input class="form-control col-xs-4" type="text" name="goonPaid" id="goonPaid" placeholder="请输入金额" autocomplete="off"/>
                </div>
                <div class="form-group form-inline row">
                    <label class="control-label col-xs-2 " for="paidReason">摘要：</label>
                    <textarea rows="5" cols="" name="paidReason" id="paidReason" class="form-control col-xs-4"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary pre-do-trigger">确定</button>
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

    $(function () {
        /*确认*/
        $('.status-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.status-do-trigger').click(function () {
                var carStatus = document.getElementsByName('carStatus');
                var value = 0;
                for (var i=0;i<carStatus.length;i++) {
                    if (carStatus[i].checked) {
                        value = carStatus[i].value;
                        break;
                    }
                }
                document.location.href = url + '&carStatus=' + value;
            });
        });
    });

    $(function () {
        /*确认*/
        $('.pre-trigger').click(function () {
            var self = $(this);
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.pre-do-trigger').click(function () {
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