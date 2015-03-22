<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<title>登录日志管理</title>
	</head>

	<body>
		<web:location location="系统维护-操作日志" />

		<form name="searchForm" class="search_form" method="post"
			action="${path}/system/operationLog!listOperationLog.action">
			<table>
				<tr>
					<td>
						<label>
							姓名：
						</label>
					</td>
					<td>
						<input type="text" name="filter_and_LoginLog-userName_LIKE_S" size="18"
							value="${filter_and_LoginLog-userName_LIKE_S}" />
					</td>
					<td>
						<label>
							操作开始时间：
						</label>
					</td>
					<td>
						<input type="text" size="22" name="filter_and_operationDate_GE_T"
							id="filter_and_operationDate_GE_T" readonly="true"
							onClick="WdatePicker()" value="${filter_and_operationDate_GE_T}" />
						<img onclick="WdatePicker({el:'filter_and_operationDate_GE_T'})"
							class="wdateIcon" />
					</td>
					<td>
						<label>
							操作截至时间：
						</label>
					</td>
					<td>
						<input type="text" size="22" name="filter_and_operationDate_LE_T"
							id="filter_and_operationDate_LE_T" readonly="true"
							onClick="WdatePicker()" value="${filter_and_operationDate_LE_T}" />
						<img onclick="WdatePicker({el:'filter_and_operationDate_LE_T'})"
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

			<TABLE class="grid-table">
				<tr>
					<th width="10%">
						<nobr>
							姓名
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							所属机构
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							IP地址
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							业务名称
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							操作名称
						</nobr>
					</th>
					<th width="15%">
						<nobr>
							操作时间
						</nobr>
					</th>
					<th width="10%">
						<nobr>
							是否成功
						</nobr>
					</th>
				</tr>
				<c:forEach items="${page.results}" var="operationLog"
					varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td>
							<nobr>
								${operationLog.userName}
							</nobr>
						</td>
						<td>
							${operationLog.orgName}
						</td>
						<td>
							${operationLog.loginLog.loginIp}
						</td>
						<td>
							${operationLog.businessName}
						</td>
						<td>
							${operationLog.operationName}
						</td>
						<td>
							<nobr>
								<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${operationLog.operationDate}" />
							</nobr>
						</td>
						<td>
							<web:showYesNoName value="${operationLog.isSuccess}"
								aliasYes="成功" aliasNo="失败" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
