<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>越权操作</title>
<link rel="shortcut icon" href="img/fav.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
</head>
<body>
<div class="container">
	<div class="alert alert-warning">
		<strong>提示:</strong>
		<p>您暂无【 <b>${permission }</b> 】操作权限，请向管理员申请(管理员邮箱地址：linxingwei@yixin.im)</p>
		<p>
			<a href="index" class="btn btn-success"><i class="icon-user icon-white"></i> 换个账号登录?</a>
			<a href="main" class="btn"><i class="icon-share-alt "></i> 返回管理首页</a> 
		</p>
	</div>
</div>
</body>
</html>