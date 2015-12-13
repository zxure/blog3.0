<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>

			<!-- 页面主体内容开始 -->
			<div class="zx-page-main">
								<!-- 文章信息列表 -->
				<section class="zx-page-article-list">
					<header class="zx-page-article-list-header">
						<span class="zx-page-article-list-header-name">所有博文</span>
						<a href="admin/article/new" title="新建博文" class="zx-page-article-new zx-btn-green"><i class="fa fa-plus"></i></a>
					</header>
					<section class="zx-page-article-list-content">
						<ol class="zx-page-article-list-posts">
							<c:forEach items="${articles}" var="article">
							<li class="zx-page-article-list-item <c:if test="${article.articleId == firstArticle.articleId }" >zx-page-article-list-item-active</c:if> ">
								<a href="admin/article/${article.articleId }" title="编辑该文章">
									<h3 class="zx-page-article-list-item-title">${article.title }</h3>
									<section class="zx-page-article-list-item-meta">
										<div class="zx-page-article-list-item-avatar">
											<img class="zx-page-article-list-item-avatar-img" src="image/${article.authorImgUrl}">
											<span class="zx-page-article-list-item-avatar-name">${article.authorName }</span>
										</div>
										<span class="zx-page-article-list-item-category">${article.categoryName }</span>
										<span class="zx-page-article-list-item-posttime">发布于  ${article.postTimeStr }</span>
									</section>
								</a>
							</li>
							</c:forEach>
						</ol>
					</section>
				</section>
				
				<!-- 文章内容 -->
				<section class="zx-page-article-content">
					<div class="zx-page-article-content-container">
						<header class="zx-page-article-content-header">
							<span>由 ${firstArticle.authorName } 发布</span>
							<a href='admin/editor/${firstArticle.articleId }' class="zx-article-edit-btn" id="article-edit-btn" title="编辑" ><i class="fa fa-edit"> </i> 编辑</a>
							<a href="javascript:void(0);" class="zx-article-delete-btn zx-btn-red" id="article-delete-btn" data-id="${firstArticle.articleId }"  title="删除"><i class="fa fa-times-circle"> </i> 删除</a>
						</header>
						<section class="zx-page-article-content-body">
							<div class="zx-page-article-content-body-wrap">
								<h1>${firstArticle.title }</h1>
								${firstArticle.content }	
							</div>
						</section>
					</div>
				</section>
				<!-- 文章信息列表结束 -->	
			</div>
			<!-- 页面主体内容结束 -->
			
<%@include file="footer.jsp" %>