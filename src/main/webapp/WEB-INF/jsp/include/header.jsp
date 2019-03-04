<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false"
                    aria-controls="navbar">
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="main">车猫管理平台</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar">
            <c:choose>
            <c:when test="${empty sessionScope.account }">
                <p class="nav navbar-text navbar-right"><a href="login">尚未登录</a></p>
            </c:when>
            <c:otherwise>
            <!-- 菜单 -->
            <ul id="globalNav" class="nav navbar-nav">
                <!--<li class="level_1"><a href="main">首页</a></li> -->
                <c:if test="${sessionScope.myDepartmentAuthority.accountAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">行政</a>
                        <ul class="dropdown-menu">
                            <li class="level_2"><a href="userList">员工管理</a></li>
                            <c:if test="${sessionScope.account.userType == 1 }">
                                <li class="level_2"><a href="userDepartment">部门管理</a></li>
                                <li class="level_2"><a href="userWagesView">工资管理</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.carAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">车辆管理</a>
                        <ul class="dropdown-menu">
                            <c:if test="${sessionScope.account.userType == 1
                            || (sessionScope.account.userType == 2 && sessionScope.account.department != 3) }">
                                <li class="level_2"><a href="carPropertyView">车辆属性</a></li>
                            </c:if>
                            <c:if test="${sessionScope.account.userType == 1
                            || sessionScope.account.userType ==2 }">
                                <li class="level_2"><a href="carDepositView">订金寻车</a></li>
                            </c:if>
                            <li class="level_2"><a href="carPurchaseView">车辆买入</a></li>
                            <li class="level_2"><a href="carStockView">在店库存</a></li>
                            <c:if test="${ sessionScope.account.department != 3
                             || (sessionScope.account.department == 3 && sessionScope.account.userType !=3)}">
                                <li class="level_2"><a href="carSaleView">在售车辆</a></li>
                            </c:if>
                            <c:if test="${sessionScope.account.userType == 1
                            || sessionScope.account.userType == 2
                            ||(sessionScope.account.department == 3 && sessionScope.account.userType ==3)}">
                                <li class="level_2"><a href="carSoldView">已售管理</a></li>
                            </c:if>
                            <c:if test="${ sessionScope.account.department != 3}">
                                <li class="level_2"><a href="carServiceFundView">售后服务基金</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.insuranceAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">保险管理</a>
                        <ul class="dropdown-menu">
                            <li class="level_2"><a href="insuranceView">保险基本业务</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.mortgageAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">按揭管理</a>
                        <ul class="dropdown-menu">
                            <li class="level_2"><a href="mortgageView?mortgageType=1">代办按揭管理</a></li>
                            <li class="level_2"><a href="mortgageView?mortgageType=2">外拓按揭管理</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.newCarAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">新车管理</a>
                        <ul class="dropdown-menu">
                            <li class="level_2"><a href="newCarView">新车业务</a></li>
                            <li class="level_2"><a href="newCarFinanceView">新车金融方案</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.moneyAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">资金流水管理</a>
                        <ul class="dropdown-menu">
                            <li class="level_2"><a href="moneyView?moneyType=1">现金流水管理</a></li>
                            <li class="level_2"><a href="moneyView?moneyType=3">poss机流水管理</a></li>
                            <c:if test="${sessionScope.account.userType != 3 }">
                                <li class="level_2"><a href="moneyView?moneyType=2">银行流水管理</a></li>
                                <li class="level_2"><a href="moneyView?moneyType=4">借款管理</a></li>
                                <li class="level_2"><a href="moneyView?moneyType=5">合作款管理</a></li>
                                <li class="level_2"><a href="moneyView?moneyType=6">应收款管理</a></li>
                                <li class="level_2"><a href="moneyView?moneyType=7">基本户管理</a></li>
                                <li class="level_2"><a href="moneyHouseView">房租平台广告管理</a></li>
                                <li class="level_2"><a href="moneyRebateView">按揭利率返点管理</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.statAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">数据管理</a>
                        <ul class="dropdown-menu">
                            <c:if test="${ sessionScope.account.department != 3
                             || (sessionScope.account.department == 3 && sessionScope.account.userType !=3)}">
                                <li class="level_2"><a href="statMoneyView">车辆综合数据</a></li>
                                <li class="level_2"><a href="statMoneyFlowView">资金流水数据</a></li>
                                <li class="level_2"><a href="statOtherView">按揭保险数据</a></li>
                            </c:if>
                            <li class="level_2"><a href="statPropertyView">属性数据</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.myDepartmentAuthority.dossierAble == 1 }">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">档案管理</a>
                        <ul class="dropdown-menu">
                            <li class="level_2"><a href="dossierView">档案管理</a></li>
                            <li class="level_2"><a href="dossierDoneView">已处理档案</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <div class="navbar-right">
                <p class="navbar-text hidden-sm hidden-xs">
                    欢迎您，
                    <c:choose>
                        <c:when test="${sessionScope.account.userType == 3 }">
                            <span class="label label-success">会员</span>
                        </c:when>
                        <c:when test="${sessionScope.account.userType == 2 }">
                            <span class="label label-success">管理员</span>
                        </c:when>
                        <c:when test="${sessionScope.account.userType == 1 }">
                            <span class="label label-success">SuperMan</span>
                        </c:when>
                    </c:choose>
                    【${sessionScope.account.name }】
                </p>
                <span class="empty-sep hidden-sm hidden-xs">|</span>
                <a class="navbar-btn btn btn-primary" href="logout"> <i class="icon-share-alt"> </i> 退出系统 </a>
            </div>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</nav>
