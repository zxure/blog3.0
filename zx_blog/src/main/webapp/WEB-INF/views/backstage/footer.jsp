<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			<aside class="zx-info-container" id="info_container"></aside>
			<!-- <script type="text/javascript" src="script/backstage.js"></script> -->
			<script type="text/javascript" src="script/require.js" data-main="script/backstage2.js"></script>
			<script type="text/javascript">
			<c:if test="${post_success == true}">
				$("#info_container").layerMessageBox("info", "发布成功");
			</c:if>
			<c:if test="${update_success == true}">
				$("#info_container").layerMessageBox("info", "更新成功");
			</c:if>
			</script>
		</div>
	</body>

</html>