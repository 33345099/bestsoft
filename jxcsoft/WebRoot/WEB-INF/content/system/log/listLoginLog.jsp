<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<title>登录日志管理</title>
	</head>

	<body>
		<web:location location="系统维护-登录日志" />

		<form name="searchForm" class="search_form"
			action="${path}/system/loginLog!listLoginLog.action" method="post">
			<table>
				<tr>
					<td>
						<label>
							姓名：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_LIKE_S" size="18"
							value="${filter_and_userName_LIKE_S}" />
					</td>
					<td>
						<label>
							登录开始时间：
						</label>
					</td>
					<td>
						<input type="text" size="22" name="filter_and_loginTime_GE_T"
							id="filter_and_loginTime_GE_T" readonly="true"
							onClick="WdatePicker()" value="${filter_and_loginTime_GE_T}" />
						<img onclick="WdatePicker({el:'filter_and_loginTime_GE_T'})"
							class="wdateIcon" />
					</td>
					<td>
						<nobr>
							登录截至时间：
						</nobr>
					</td>
					<td>
						<input type="text" size="22" name="filter_and_loginTime_LE_T"
							id="filter_and_loginTime_LE_T" readonly="true"
							onClick="WdatePicker()" value="${_loginTime_endTime}" />
						<img onclick="WdatePicker({el:'filter_and_loginTime_LE_T'})"
							class="wdateIcon" />
					</td>
				</tr>
				<tr>
					<td align="center" colspan="6">
						<input type="hidden" name="_pageNum" value="${page.pageNum}" />
						<input type="submit" class="button" value="查 询" />
						<input type="button" class="button" value="清 空"
							onclick="clearSearchForm()" />
					</td>
				</tr>
			</table>
		</form>

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
					<th width="10%">
						<nobr>
							姓名
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							所属部门
						</nobr>
					</th>
					<th width="20%">
						<nobr>
							登录时间
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							登录人IP地址
						</nobr>
					</th>
					
				</tr>
				<c:forEach items="${page.results}" var="loginLog" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<!-- td>
							<a
								href="javascript:href('operationLog!listOperationLogForLoginLog.action?transfer_loginLogId=${loginLog.id}')">查看</a>
						</td -->
						<td>
							<nobr>
								${loginLog.userName}
							</nobr>
						</td>
						<td>
							${loginLog.orgName}
						</td>
						<td>
							<nobr>
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${loginLog.loginTime}" />
							</nobr>
						</td>
						<td>
							${loginLog.loginIp}
						</td>
						
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
