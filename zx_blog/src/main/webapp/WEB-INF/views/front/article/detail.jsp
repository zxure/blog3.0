<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
	<!-- 主体部分 -->
	<div class="zx-content">
		<!--  主体左边 -->
		<div class="zx-content-left">
			<div class="zx-article-container-detail">
				<article class="zx-article-detail">
					<header>
						<h1 class="zx-article-title-detail">
							<span class="zx-article-title-detail-name">
								<a href="article/${article.articleId}">${article.title}</a>
							</span>
						</h1>
 						<div class="zx-article-detail-meta">
							<span> ${article.authorName} 发布于  ${article.postTimeStr } 之前</span>
						</div>
					</header>
					<div class="zx-article-detail-content">
						${article.content }
					</div>
				</article>
			</div>
			<!-- 评论  -->
			<div class="ds-thread" data-thread-key="article_${article.articleId}" data-title="${article.title}" data-url="http://localhost:8080/zx_blog/article/${article.articleId
			}"></div>
			<!-- 多说评论框 end -->
		</div>
		<%@ include file="../sidebar.jsp" %>
	</div>
<%@ include file="../footer.jsp" %>
