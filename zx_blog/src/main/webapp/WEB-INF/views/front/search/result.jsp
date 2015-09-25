<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>
	<!-- 结果如下 -->
	<c:forEach items="${result }" var="article">
		<article class="zx-post">
			<h2 class="zx-post-title">
				<span class="zx-post-title-icon"></span> <a
					href="${path }/article/${article.articleId}${urlSuffix}">${article.title}</a>
			</h2>
			<ul class="zx-post-meta">
				<li><span class="zx-css-icon zx-post-author-icon"></span><a
					href="#">${article.userId}</a></li>
				<li><span class="zx-css-icon zx-post-date-icon"></span>${article.postTime}
				</li>
				<li><span class="zx-css-icon zx-post-category-icon"></span><a
					href="${path }/article/category/${article.categoryId}${urlSuffix}">${article.categoryName}</a></li>
				<li><span class="zx-css-icon zx-post-commend-icon"></span><a
					href="#">评论</a></li>
			</ul>
			<div class="zx-post-content">${article.content }</div>
		</article>
	</c:forEach>
<!-- start of right -->
	<%@include file="../sidebar.jsp" %>
	<!-- end right -->
<%@include file="../footer.jsp"%>