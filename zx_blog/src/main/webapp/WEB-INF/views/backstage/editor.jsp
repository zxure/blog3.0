<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
	<!-- 页面主体内容开始 -->
	<div class="zx-page-main zx-scroll-auto">
		<!-- 内容 -->
		<div class="zx-page-content-editor">
			<form id="article-editor-form" data-formType="${optType }">
				<div class="zx-article-edit-head-wrap">
					<select name="categoryId" id="categoryId">
						<c:forEach items="${categories}" var="category">
						<option value ="${category.categoryId }" 
						<c:if test="${category.categoryId == article.articleId }">
							selected="selected"
						</c:if>
						>${category.categoryName }</option>
						</c:forEach>
					</select>
					<c:if test="${optType == 'postArticle'}">
						<input type="text" name="title" class="zx-editor-article-title" id="title" placeholder="文章标题"/>
					</c:if>					  
					<c:if test="${optType == 'updateArticle'}">
						<input type="text" name="title" placeholder="文章标题" id="title" value="${article.title }"/>
						<button type="button" class="zx-btn zx-btn-red" data-id="${article.articleId }" id="article-delete-btn">删除文章</button>
					</c:if>
				</div>
				<!-- 加载编辑器内容 -->
				<c:if test="${optType == 'postArticle'}">
				<script id="editor-container" name="content" type="text/plain"></script>
				</c:if>
				<c:if test="${optType == 'updateArticle'}">
				<script id="editor-container" name="content" type="text/plain">${article.content}</script>
				</c:if>
				<!-- 配置文件 -->
				<script type="text/javascript" src="script/ueditor/ueditor.config.js"></script>
				<!-- 编辑器源码文件 -->
				<script type="text/javascript" src="script/ueditor/ueditor.all.js"></script>
				<div class="zx-article-edit-footer-wrap">
					<button type="button" id="form-submit" class="zx-btn zx-btn-blue">
					<c:if test="${optType == 'postArticle'}">发布</c:if>
					<c:if test="${optType == 'updateArticle'}">更新</c:if>
					</button>
				</div>
				
			</form>
		</div>
	</div>
	<!-- 页面主体内容结束 -->
<%@include file="footer.jsp" %>