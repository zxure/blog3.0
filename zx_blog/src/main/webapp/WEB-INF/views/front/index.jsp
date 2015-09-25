<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
		<!-- 主体部分 -->
		<div class="zx-content">
			<!--  主体左边 -->
			<div class="zx-content-left">
				<div class="zx-article-container">
					<c:forEach items="${articles }" var="article">
					<article class="zx-article">
						<header>
							<h1 class="zx-article-title">
								<span class="zx-article-title-category-name">
									<a href="article/cat/${article.categoryId}">${article.categoryName}</a>
								</span>
								<span class="zx-article-title-name">
									<a href="article/${article.articleId}">${article.title}</a>
								</span>
							</h1>
							<div class="zx-article-meta">
								<span><a href="about"><i class="fa fa-user"></i> ${article.authorName}</a></span>
								<span><i class="fa fa-clock-o"></i> 发布于  ${article.postTimeStr } 之前 </span>
								<span><i class="fa fa-eye"></i> 浏览(${article.totalViewTimes}) </span>
							</div>
						</header>
						<div class="zx-article-content">
							${article.content }
						</div>

						<footer class="zx-article-footer">
							<span class="ds-thread-count" data-thread-key="article_${article.articleId}" data-count-type="comments"></span>
							<span class="zx-read-more"><a class="zx-button zx-button-primary" href="article/${article.articleId}">Read more...</a></span>
						</footer>
					</article>
					</c:forEach>
				</div>
				
				<c:if test="${pageInfo != null}">
				<div class="zx-pagination-container">
					<ul class="zx-pagination">
						<c:if test="${pageInfo.currentPage > 1}">
						<li><span><a href="article/page/${pageInfo.currentPage - 1}"><i class="fa fa-angle-double-left fa-align-center"></i></a></span></li>
						</c:if>
						<c:forEach begin="${pageInfo.start}" end="${pageInfo.end}" var="i">
						<c:if test="${pageInfo.currentPage==i}">
						<li class="zx-active"><span>${i}</span></li>
						</c:if>
						<c:if test="${pageInfo.currentPage!=i}">
						<li><span><a href="article/page/${i}">${i}</a></span></li>
						</c:if>
						</c:forEach>
						<c:if test="${pageInfo.currentPage < pageInfo.totalPage}">
						<li><span><a href="article/page/${pageInfo.currentPage + 1}"><i class="fa fa-angle-double-right"></i></a></span></li>
						</c:if>
					</ul>
				</div>
				</c:if>
			</div>
			<!-- start of right -->
			<%@include file="sidebar.jsp" %>
			<!-- end right -->
		</div>
<%@include file="footer.jsp"%>