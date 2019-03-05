<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <c:if test="${moneyType==1}">
        <title>现金流水管理</title>
    </c:if>
    <c:if test="${moneyType==2}">
        <title>银行流水管理</title>
    </c:if>
    <c:if test="${moneyType==3}">
        <title>poss机流水管理</title>
    </c:if>
    <c:if test="${moneyType==4}">
        <title>借款管理</title>
    </c:if>
    <c:if test="${moneyType==5}">
        <title>合作款管理</title>
    </c:if>
    <c:if test="${moneyType==6}">
        <title>应收款管理</title>
    </c:if>
    <c:if test="${moneyType==7}">
        <title>基本户管理</title>
    </c:if>
    <script type="text/javascript" src="js/nimble.js"></script>
    <script type="text/javascript" src="js/nimble_editable.js"></script>
</head>

<ul class="breadcrumb">
    <li><a href="main">首页</a> <span class="divider"></span></li>
    <c:if test="${moneyType==1}">
        <li class="active">现金流水管理</li>
    </c:if>
    <c:if test="${moneyType==2}">
        <li class="active">银行流水管理</li>
    </c:if>
    <c:if test="${moneyType==3}">
        <li class="active">poss机流水管理</li>
    </c:if>
    <c:if test="${moneyType==4}">
        <li class="active">借款管理</li>
    </c:if>
    <c:if test="${moneyType==5}">
        <li class="active">合作款管理</li>
    </c:if>
    <c:if test="${moneyType==6}">
        <li class="active">应收款管理</li>
    </c:if>
    <c:if test="${moneyType==7}">
        <li class="active">基本户管理</li>
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

<p><a href="moneyAction?action=1&moneyType=${moneyType}" class="btn btn-primary"><i class="icon-plus icon-white"></i> 创建新记录</a></p>
<hr/>
<c:if test="${moneyType==1||moneyType==2||moneyType==3}">
    <div class="container">
        <form class="form-horizontal" action="moneySearch" method="get">
            <div class="form-group">
                <div class="col-sm-2">
                    <select class="form-control" name="searchKey" id="searchKey" onchange="show(this)">
                        <option value="">未选中</option>
                        <option value="actionPerson">经办人</option>
                        <option value="actionMoney">金额</option>
                        <option value="actionDesc">摘要</option>
                    </select>
                </div>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="searchValue1" id="searchValue1" value="${searchValue1 }" placeholder="条件1" autocomplete="off"/>
                </div>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="searchValue2" id="searchValue2" value="${searchValue2 }" placeholder="条件2" autocomplete="off"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">时间段：</label>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="btime" id="btime" value="${btime }" onclick="new Calendar().show(this);" placeholder="开始日期" autocomplete="off"/>
                </div>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="etime" id="etime" value="${etime }" onclick="new Calendar().show(this);" placeholder="结束日期" autocomplete="off"/>
                </div>
            </div>
            <div class="hidden">
                <input type="text" name="moneyType" id="moneyType" value="${moneyType}" autocomplete="off"/>
            </div>
            <div class="form-group">
                <div class="col-sm-2 form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="icon-search icon-white"></i> 查询
                    </button>
                    <a href="moneyReset?moneyType=${moneyType}"><i class="icon-trash"></i> 重置</a>
                </div>
            </div>
        </form>
    </div>
    <hr/>
</c:if>
<c:if test="${moneyType==1||moneyType==2||moneyType==3}">
    <div class="alert alert-info">
        余额：【${balance}】
    </div>
    <hr/>
</c:if>
<c:choose>
    <c:when test="${empty dataList }">
        <div class="alert alert-warning">记录为空</div>
    </c:when>
    <c:otherwise>

        <table class="table table-striped table-bordered table-condensed table-hover">
            <colgroup>
                <col width="20%"/>
                <col width="20%"/>
                <col width="10%"/>
                <col width="10%"/>
                <col width="20%"/>
                <col width="20%"/>
            </colgroup>
            <tr>
                <th>发生日期</th>
                <c:if test="${moneyType==1}">
                    <th>经办人</th>
                    <th>备用金收入</th>
                    <th>现金支出</th>
                </c:if>
                <c:if test="${moneyType==2}">
                    <th>经办人</th>
                    <th>银行收入</th>
                    <th>银行支出</th>
                </c:if>
                <c:if test="${moneyType==3}">
                    <th>经办人</th>
                    <th>poss机收入</th>
                    <th>poss机提现</th>
                </c:if>
                <c:if test="${moneyType==4}">
                    <th>借款人</th>
                    <th>金额</th>
                    <th>科目</th>
                </c:if>
                <c:if test="${moneyType==5}">
                    <th>合伙人</th>
                    <th>金额</th>
                    <th>科目</th>
                </c:if>
                <c:if test="${moneyType==6}">
                    <th>欠款人</th>
                    <th>金额</th>
                    <th>科目</th>
                </c:if>
                <c:if test="${moneyType==7}">
                    <th>经办人</th>
                    <th>金额</th>
                    <th>科目</th>
                </c:if>
                <th>摘要</th>
                <th>操作</th>
            </tr>
            <c:forEach var="da" items="${dataList }" varStatus="status">
                <tr>
                    <td>${da.strActionDate}</td>
                    <td>${da.actionPerson}
                    </td>
                    <c:if test="${moneyType==1||moneyType==2||moneyType==3}">
                        <c:if test="${da.actionType == 1}">
                            <td>--</td>
                            <td>${da.actionFee}</td>
                        </c:if>
                        <c:if test="${da.actionType == 2}">
                            <td>${da.actionFee}</td>
                            <td>--</td>
                        </c:if>
                    </c:if>
                    <c:if test="${moneyType==4||moneyType==5||moneyType==6||moneyType==7}">
                        <td>${da.actionFee}</td>
                        <td>
                            <c:if test="${moneyType==4}">
                                <c:if test="${da.actionType == 1}">还款</c:if>
                                <c:if test="${da.actionType == 2}">借款</c:if>
                            </c:if>
                            <c:if test="${moneyType==5}">
                                <c:if test="${da.actionType == 1}">退</c:if>
                                <c:if test="${da.actionType == 2}">入</c:if>
                            </c:if>
                            <c:if test="${moneyType==6}">
                                <c:if test="${da.actionType == 1}">还款</c:if>
                                <c:if test="${da.actionType == 2}">欠款</c:if>
                            </c:if>
                            <c:if test="${moneyType==7}">
                                <c:if test="${da.actionType == 1}">支出</c:if>
                                <c:if test="${da.actionType == 2}">充值</c:if>
                            </c:if>
                        </td>
                    </c:if>
                    <td><c:if test="${moneyType==2}">账户银行：${da.linkDesc}，</c:if>${da.actionDesc}</td>
                    <td>
                        <a href="moneyAction?id=${da.id }&action=2&moneyType=${moneyType}"><i class="icon-lock"></i> 编辑</a>
                        <a href="#confirmDialog" data-toggle="modal"
                           data-url="moneyDelete?id=${da.id }&moneyType=${moneyType}" data-title="删除"
                           data-tip="确认要删除该条记录嘛？" class="confirm-trigger"><i
                                class="icon-trash"></i> 删除</a>
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

    var searchKey = document.getElementById('searchKey');
    for (var i = 0; i < searchKey.options.length; i++) {
        if (searchKey.options[i].value == '${searchKey}') {
            searchKey.options[i].selected = true;
        }
    }
</script>