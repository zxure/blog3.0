<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="path" value="${pageContext.request.contextPath}" scope="page" /> 
<!DOCTYPE html>
<html>
	<head>
		<base href="${path}/">
		<title>zxure</title>
		<meta charset="UTF-8">
		<!--让低版本的IE采用最新的渲染模式-->
		<meta http-equip="X-UA-compatible" content="IE-edge">
		<!-- 让页面的宽度强制去适应设备的宽度，并且初始化的缩放比例为1 -->
		<meta name="viewport" content="width=device-width" initical-scale="1">
		<link rel="stylesheet" type="text/css" href="style/main.css" />
		<link rel="stylesheet" type="text/css" href="style/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="style/styles/github.css">
		<script type="text/javascript" src="script/jquery.js"></script>
		<script type="text/javascript" src="script/highlight.pack.js"></script>
	</head>
	<body>
		<!-- 页面头部  -->
		<div class="container">
		<header class="zx-header">
			<section class="zx-header-content">
				<ul class="zx-nav">
					<li class="zx-nav-item"><a href=""><i class="fa fa-home fa-lg"></i> 首页</a></li>
					<!-- <li class="zx-nav-item"><a href="article"><i class="fa fa-book fa-lg"></i> 文章</a></li> -->
					<li class="zx-nav-item"><a href="library"><i class="fa fa-gear fa-lg fa-spin"></i> 实验室</a></li>
					<li class="zx-nav-item"><a href="album"><i class="fa fa-image fa-lg"></i> 相片</a></li>
					<li class="zx-nav-item"><a href="about-me"><i class="fa fa-user fa-lg"></i> 关于我</a></li>
				</ul>

				<div class="zx-info">
					<span class="upper">When what you love gets token from you,</span>
					<span class="subber"> you want to know the truth...</span>
				</div>
			</section>
		</header>
