<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
			
			<!-- 页面主体内容开始 -->
			<div class="zx-page-main">
				<!-- 内容导航 -->
				<%@include file="settings-menu.jsp" %>
				<!-- 内容 -->
				<div class="zx-page-content">
					<header class="zx-setting-header">
						<h2 class="zx-setting-title">类别管理</h2>
						<!-- <section class="zx-setting-action-btn-holder">
							<button class="zx-btn zx-btn-blue">保存</button>
						</section> -->
					</header>
	
					<section class="zx-setting-content">
						<c:forEach items="${categories }" var="category">
						<div class="zx-setting-content-item">
							<div class="zx-setting-content-input-holder">
								<span class="zx-setting-content-input">
									<%-- <input type="hidden" name="categoryId" value="${category.categoryId }" id="category-id-${category.categoryId }"> --%>
									<input type="text" name="categoryName" value="${category.categoryName }" disabled="disabled" id="category-input-name-${category.categoryId }">
								</span>
							</div>
							<span class="zx-setting-btn-holder">
								<button class="zx-btn zx-btn-green" title="修改" id="category-modify-${category.categoryId }"><i class="fa fa-pencil"></i> 修改</button>
							</span>
							<span class="zx-setting-btn-holder">
								<button class="zx-btn zx-btn-red" title="删除" id="category-delete-${category.categoryId }"><i class="fa fa-remove"></i> 删除</button>
							</span>
						</div>
						</c:forEach>
						<div class="zx-setting-content-item">
							<div class="zx-setting-content-input-holder">
								<span class="zx-setting-content-input">
									<input type="text" name="categoryName" id="category-add-input-name" placeholder="类别名称">
								</span>
							</div>
							<span class="zx-setting-btn-holder">
								<button class="zx-btn zx-btn-blue" title="添加" id="category-add"><i class="fa fa-plus"></i> 添加</button>
							</span>
						</div>
					</section>
				</div>
			</div>
<%@include file="footer.jsp" %>