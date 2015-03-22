<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<title>系统信息</title>
	</head>
	<body>

		<table class="navigation">
			<tr>
				<td class="navigation-location">
					<img src="${path}/images/navigation.gif" />
					<b>当前位置:&nbsp;</b>
					<span class="navigation-end">错误信息</span>
				</td>
		</table>

		<div class="content">
			<TABLE class="grid-table">
				<tr>
					<th width="25%">
						错误代码
					</th>
					<th width="40%">
						错误信息
					</th>
					<th width="10%">
						处理结果
					</th>
				</tr>
				<tr>
					<td>
						${businessException.code}
					</td>
					<td>
						${businessException.message}
					</td>
					<td>
						<c:choose>
							<c:when test="${businessException.returnPage!=''}">
								<a
									href="javascript:window.location.href='${path}/${businessException.returnPage}';">返回</a>
							</c:when>
							<c:otherwise>
								<a href="javascript:history.back();">返回</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
