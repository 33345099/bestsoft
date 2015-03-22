<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<title>登录日志管理</title>
	</head>

	<body>
		<web:location location="系统维护-操作日志" />

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="handle-td">

					</td>

					<td class="page-td">
						<web:pager />
					</td>
				</tr>
			</table>

			<table class="grid-table">
				<tr>
					<th>
						业务名称
					</th>
					<th>
						操作名称
					</th>
					<th>
						操作时间
					</th>
					<th>
						是否成功
					</th>
				</tr>
				<c:forEach items="${page.results}" var="operationLog" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td>
							${operationLog.businessName}
						</td>
						<td>
							${operationLog.operationName}
						</td>
						<td>
							<nobr>
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${operationLog.operationDate}" />
							</nobr>
						</td>
						<td>
							<web:showYesNoName value="${operationLog.isSuccess}" aliasYes="成功" aliasNo="失败" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>