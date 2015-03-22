<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
function showOpenImage(imgId) {
	var imagePath = $(imgId).src;
	window
			.open("${path}/common/attach!showImage.action?imagePath="
					+ imagePath);
}
</script>
	</head>
	<body>

		<div class="content">
			<table class="grid-table">
				<tr>
					<th width="8%">
						<nobr>
							操作
						</nobr>
					</th>
					<th width="20%">
						<nobr>
							照片名称
						</nobr>
					</th>
					<th>
						<nobr>
							照片
						</nobr>
					</th>
				</tr>
				<c:forEach items="${attachList}" var="attach">
					<tr>
						<td>
							<a
								href="javascript:deleteAction('${path}/common/attach!deleteAttach.action?ownerId=${ownerId}&uploadPath=${uploadPath}&attachName=${attachName}&ids=${attach.id}',null,null)">删除</a>
						</td>
						<td>
							<nobr>
								${attach.fileName}
							</nobr>
						</td>
						<td>
							<div style="width: 300px">
								<a href="javascript:showOpenImage('image${attach.id}')"><img
										width="400px" id="image${attach.id}"
										src="${path}/${uploadPathAddress}/${attach.filePath}" /> </a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
