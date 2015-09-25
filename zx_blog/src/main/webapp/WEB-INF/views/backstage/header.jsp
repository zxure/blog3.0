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
	<body>
		<!-- 主体内容 -->
		<div id="container" class="content">
			<!-- 导航开始  -->
			<nav class="zx-nav">
				<a class="zx-nav-item zx-blog-logo " href="" title="访问首页">
					<div class="zx-nav-label"><i class="fa fa-plane"></i><span>访问首页</span></div>
				</a>
				<a class="zx-nav-item" href="admin">
					<div class="zx-nav-label"><i class="fa fa-list"></i> 博文列表 </div>
				</a>
				<a class="zx-nav-item" href="admin/article/new">
					<div class="zx-nav-label"> <i class="fa fa-pencil-square-o"></i> 新建博文 </div>
				</a>
				<a class="zx-nav-item" href="admin/settings/">
					<div class="zx-nav-label"> <i class="fa fa-cog"></i> <span>博客设置</span> </div>
				</a>
				<div id="zx-user-toggle-menu" class="zx-nav-item zx-user-menu">
					<div class="zx-nav-label"> 
						<span class="zx-user-icon"><i class="fa fa-user fa-2x"></i></span>
						<div class="zx-user-name">
							zxure
							<i class="fa fa-chevron-down"></i>
						</div>
					</div>
					<div id="zx-user-toggle-menu-container" class="zx-user-toggle-menu-container">
						<div class="zx-triangle-up"></div>
						<ul>
							<li><a href="admin/settings/user"><i class="fa fa-user"></i> 我的资料</a></li>
							<li><a href="admin/logout"><i class="fa fa-power-off"></i> 退出登录</a></li>
						</ul>
					</div>
				</div>
			</nav>
			<!-- 导航结束  -->