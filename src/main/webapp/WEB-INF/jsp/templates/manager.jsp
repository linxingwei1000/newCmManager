<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><decorator:title default="后台"/> - 车猫管理平台</title>
    <link rel="shortcut icon" href="img/cvc.png" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/md5.js"></script>
    <script type="text/javascript" src="js/calendar.js"></script>
    <script type="text/javascript" src="js/base.js"></script>
    <style type="text/css">
        .nav > li .dropdown-menu {
            visibility: visible
        }

        .nav > li:hover .dropdown-menu {
            display: block
        }

        body {
            min-height: 2000px;
            padding-top: 70px;
        }

        .message {
            position: fixed;
            text-align: center;;
            left: 0;
            right: 0;
            top: 55px;
            width: 60%;
            margin-left: auto;
            margin-right: auto;
            filter: alpha(Opacity=90);
            -moz-opacity: 0.9;
            opacity: 0.9;
            z-index: 2000;
        }
    </style>
    <script language="javascript">
        var showed = 0;
        $(document).ready(function () {
            $("table").each(function (a) {
                $(a).attr("onerror", "onError");
            });
        });

        function onError(msg) { // 定义一个默认的输出函数
            var a = $("#prompt");
            if (msg) {
                a.html(msg);
                a.removeClass("alert-success").addClass("alert-danger");
            } else {
                a.html("更新成功。");
                a.removeClass("alert-danger").addClass("alert-success");
            }
            a.slideDown(300);
            showed++;

            setTimeout(function () {
                if (--showed == 0) a.slideUp(300);
            }, 2000);
        }
    </script>
    <decorator:head/>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div id="prompt" class="alert message" style="display: none"></div>

<div class="container">
    <decorator:body/>
</div>

<%@ include file="../include/footer.jsp" %>

</body>
</html>