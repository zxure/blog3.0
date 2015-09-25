<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" scope="page" /> 
<!DOCTYPE html>
<html>
	<head>
		<base href="${path }/">
		<meta charset="UTF-8">
		<title>zxure_backstage</title>
		<!--让低版本的IE采用最新的渲染模式-->
		<meta http-equip="X-UA-compatible" content="IE-edge">
		<!-- 让页面的宽度强制去适应设备的宽度，并且初始化的缩放比例为1 -->
		<meta name="viewport" content="width=device-width" initical-scale="1">
		<link rel="stylesheet" type="text/css" href="style/backstage/backstage-main.css" />
		<link rel="stylesheet" type="text/css" href="style/font-awesome.min.css"/>
		<script type="text/javascript" src="script/jquery.js"></script>
		<script type="text/javascript" src="script/jquery.validate.min.js"></script>
		<script type="text/javascript" src="script/promise.js"></script>

	</head>
	<body class="zx-login-page">
		<!-- 主体内容 -->
		<div id="container" class="content">
			<section class="zx-login-box">
				<form id="loginForm" class="zx-login-form">
					<div class="zx-username-wrap">
						<span class="zx-form-icon"><i class="fa fa-user"></i></span>
						<input class="zx-input" type="text" name="userName" id="userName" placeholder="用户名">
					</div>

					<div class="zx-password-wrap">
						<span class="zx-form-icon"><i class="fa fa-unlock-alt"></i></span>
						<input class="zx-input" type="password" name="password" id="password" placeholder="密码">
					</div>

					<button type="submit" class="zx-btn zx-btn-blue">login</button>
				</form>
			</section>

			<aside class="zx-info-container" id="info_container"></aside>
		</div>
	</body>
	<script type="text/javascript" src="script/require.js" data-main="script/backstage2.js"></script>
</html>