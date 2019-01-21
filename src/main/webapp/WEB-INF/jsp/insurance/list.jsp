<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="display" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>保险管理</title>
</head>
<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <li class="active">保险管理</li>
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
    <form class="form-inline" action="insuranceSearch" method="get">
        <div class="col-sm-2">
            <div class="form-group">
                <select class="form-control" name="searchKey" id="searchKey" onchange="show(this)">
                    <option value="">未选择中</option>
                    <option value="consumerName">客户姓名</option>
                    <option value="insuranceDate">投保时间</option>
                    <option value="qzxDate">强制险时间</option>
                    <option value="syxDate">商业险时间</option>
                    <option value="businessType">保险类型</option>
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
        <div class="col-sm-2">
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-search icon-white"></i> 查询
                </button>
            </div>
        </div>
    </form>
</div>
<hr/>
<p><a href="insuranceAction?action=1" class="btn btn-primary"><i class="icon-plus icon-white"></i> 客户基础信息录入</a></p>
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
                <p><a href="insuranceExport" class="btn btn-primary"><i class="icon-plus icon-white"></i> 导出</a></p>
            </c:if>
            <table id="traceTable" class="table table-striped table-bordered table-condensed table-hover">
                <colgroup>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="8%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="6%"/>
                    <col width="5%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="9%"/>
                </colgroup>
                <tr>
                    <th>客户姓名</th>
                    <th>客户性别</th>
                    <th>出生日期</th>
                    <th>电话号码</th>
                    <th>客户类别</th>
                    <th>品牌</th>
                    <th>车型</th>
                    <th>行驶证登记日期</th>
                    <th>行驶证发证日期</th>
                    <th>业务员</th>
                    <th>续保押金</th>
                    <th>押金处理</th>
                    <th>业务详情</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="cp" items="${dataList}" varStatus="status">
                    <tr>
                        <td>${cp.consumerName  }</td>
                        <td><c:if test="${cp.consumerSex == 1 }">男</c:if><c:if test="${cp.consumerSex == 2 }">女</c:if></td>
                        <td>${cp.consumerBirth  }</td>
                        <td>${cp.consumerPhone }</td>
                        <td><c:if test="${cp.consumerType == 1 }">全款</c:if><c:if test="${cp.consumerType == 2 }">按揭</c:if><c:if test="${cp.consumerType == 3 }">外拓</c:if></td>
                        <td>${cp.carBrand }</td>
                        <td>${cp.carModel  }</td>
                        <td>${cp.strTravelRegister }</td>
                        <td>${cp.strTravelLssuing }</td>
                        <td>${cp.businessPerson  }</td>
                        <td>${cp.renewalFee  }</td>
                        <td><c:if test="${cp.dealRenewal == 1 }">转收入</c:if><c:if test="${cp.dealRenewal == 2 }">退还</c:if><c:if test="${cp.dealRenewal == 3 }">暂存</c:if><c:if test="${cp.dealRenewal == 0}">无</c:if></td>
                        <td><a href="#business-${cp.id}" data-toggle="modal"><i class="icon-trash"></i> 查看详情</a>
                            <div id="business-${cp.id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">业务信息</h4>
                                        </div>
                                        <div class="modal-body">
                                            <c:forEach var="pl" items="${cp.list}" varStatus="status">
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">投保日期：${pl.strInsuranceDate}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">保险类型：${pl.strBusinessType}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">强制险到期日期：${pl.strQzxDate}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">强制险保险公司：${pl.qzxCompany}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">强制险保费：${pl.qzxFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">商业险到期日期：${pl.strSyxDate}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">商业险保险公司：${pl.syxCompany}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">商业险保费：${pl.syxFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-3 control-label">商业险折扣：${pl.syxDiscount}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">保险返点：${pl.expenseRebate}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-3 control-label">返点金额：${pl.rebateFee}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-sm-4 control-label">返还客户金额：${pl.returnFee}</label>
                                                    <div class="col-sm-1"></div>
                                                    <label class="col-sm-4 control-label">备注信息：${pl.businessDesc}</label>
                                                </div>
                                                <div class="form-group row">
                                                    <a href="insuranceBusinessAction?id=${pl.id }&action=2&insuranceId=${cp.id}"><i class="icon-lock"></i>修改</a>
                                                    <a href="#confirmDialog" data-toggle="modal" data-url="insuranceBusinessDelete?id=${pl.id }" data-title="删除记录"
                                                       data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                                                </div>
                                                <hr/>
                                            </c:forEach>
                                            <div class="form-group row">
                                                <a href="insuranceBusinessAction?insuranceId=${cp.id }&action=1"><i class="icon-lock"></i> 业务录入</a>
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
                            <a href="insuranceAction?id=${cp.id }&action=2"><i class="icon-lock"></i> 编辑</a>
                            <c:if test="${sessionScope.account.userType == 1 || sessionScope.account.userType == 2}">
                                <a href="#confirmDataDialog" data-toggle="modal" data-url="insuranceStatus?id=${cp.id }" data-title="隐藏记录"
                                   data-tip="确认业务数据已录入？" class="confirm-data-trigger"><i class="icon-trash"></i> 转数据库</a>
                                <a href="#confirmDialog" data-toggle="modal" data-url="insuranceDelete?id=${cp.id }" data-title="删除记录"
                                   data-tip="确认要删除记录嘛？" class="confirm-trigger"><i class="icon-trash"></i> 删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%-- 删除确认 --%>
<div id="confirmDataDialog" class="modal fade">
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
                <button class="btn btn-primary confirm-data-do-trigger">确定</button>
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
            if (obj.value == "insuranceDate"|| obj.value == "qzxDate"|| obj.value == "syxDate" ) {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "block";
                document.getElementById("det").style.display = "block";
                document.getElementById("dsOne").style.display = "none";
                document.getElementById("dsTwo").style.display = "none";
            } else if (obj.value == "consumerName" ||obj.value == "businessType" ) {
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
        $('.confirm-data-trigger').click(function () {
            var self = $(this);
            var tip = self.attr('data-tip');
            var title = self.attr('data-title');
            var url = self.attr('data-url');
            $('.modal-title').html(title);
            $('.modal-body-inner').html(tip);
            $('.confirm-data-do-trigger').click(function () {
                document.location.href = url;
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

            if (searchKey.options[i].value == "insuranceDate" || searchKey.options[i].value == "qzxDate" || searchKey.options[i].value == "syxDate" ) {
                document.getElementById("dsv").style.display = "none";
                document.getElementById("dbt").style.display = "block";
                document.getElementById("det").style.display = "block";
                document.getElementById("dsOne").style.display = "none";
                document.getElementById("dsTwo").style.display = "none";
            } else if (searchKey.options[i].value == "consumerName"||searchKey.options[i].value == "businessType") {
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
