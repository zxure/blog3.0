<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--  主体右边 -->
<div class="zx-content-right">
	<div class="zx-panel">
		<h3 class="zx-panel-title"><strong> <i class="fa fa-tags"></i>文章分类 </strong></h3>
		<ul>
			<c:forEach items="${categories}" var="category">
			<li><a href="article/cat/${category.categoryId}">${category.categoryName}(${category.totalArticleNum })</a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="zx-panel">
		<h3 class="zx-panel-title"><strong><i class="fa fa-heart"></i> 热评文章</strong></h3>
		<!-- 多说热评文章 start -->
		<div class="ds-top-threads" data-range="daily" data-num-items="5"></div>
		<!-- 多说热评文章 end -->
	</div>	
	<div class="zx-panel">
		<h3 class="zx-panel-title"><strong><i class="fa fa-heart"></i> 友情链接</strong></h3>
		<ul>
			<li><a href="http://jelon.in">jelon</a></li>
<!-- 			<li><a href="http://jelon.in">apache</a></li>
			<li><a href="http://jelon.in">google</a></li> -->
		</ul>
	</div>	
</div>
