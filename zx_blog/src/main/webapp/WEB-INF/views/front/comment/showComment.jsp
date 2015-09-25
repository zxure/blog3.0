<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
	<c:forEach items="${messages}" var="message">
		<c:forEach items="${message }" var="msg">
		留言人：${msg.commenterName }<br />
		留言内容：${msg.commentContent }<br />
		留言时间:${msg.commentTime }<br />
		</c:forEach>
	</c:forEach>
	<div>
		<form action="${path }/makeComment${urlSuffix}" method="post">
			留言者：<input type="text" name="commenterName"><br />
			留言内容：<input type="text" name="commentContent"><br />
			<input type="submit" value="提交">
		</form>
	</div>
<%@ include file="../sidebar.jsp" %>
<%@ include file="../footer.jsp" %>