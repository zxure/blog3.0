<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 内容导航 -->
<nav class="zx-page-nav">
	<ul> <!-- zx-page-nav-item-active -->
		<li id="zx-settings-global" class="zx-page-nav-item <c:if test="${isSettingsGlobal == true }">zx-page-nav-item-active</c:if>">
			<a href="admin/settings/global"><i class="fa fa-cog zx-nav-icon"></i> 全局设置</a>
		</li>
		<li id="zx-settings-user" class="zx-page-nav-item <c:if test="${isSettingsUser == true }">zx-page-nav-item-active</c:if>">
			<a href="admin/settings/user"><i class="fa fa-users zx-nav-icon"></i> 用户管理</a>
		</li>
		<li id="zx-settings-category" class="zx-page-nav-item <c:if test="${isSettingsCategory == true }">zx-page-nav-item-active</c:if>">
			<a href="admin/settings/category"><i class="fa fa-book zx-nav-icon"></i> 类别管理</a>
		</li>
		<li id="zx-settings-tag" class="zx-page-nav-item <c:if test="${isSettingsTag == true }">zx-page-nav-item-active</c:if>">
			<a href="admin/settings/tag"><i class="fa fa-tags zx-nav-icon"></i> 标签管理</a>
		</li>
		<li id="zx-settings-library" class="zx-page-nav-item <c:if test="${isSettingsLibrary == true }">zx-page-nav-item-active</c:if>">
			<a href="admin/settings/library"><i class="fa fa-code zx-nav-icon"></i> 实验室</a>
		</li>
	</ul>
</nav>
