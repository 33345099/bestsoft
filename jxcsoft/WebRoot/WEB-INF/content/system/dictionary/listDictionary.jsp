<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>
		<web:location location="基础信息管理-系统码表-计量单位" />

		<div class="content">

			<table class="handle-table" border="0" cellspacing="1"
				cellpadding="0">
				<tr>
					<td class="handle-td">
						<input type="button" class="button6" value="添 加 计 量 单 位"
							onclick="addAction('${path}/system/dictionary!addDictionary.action')" />
					</td>

					<td class="page-td">
						<web:pager url="${path}/system/dictionary!listDictionary.action" />
					</td>
				</tr>
			</table>

			<TABLE class="grid-table">
				<tr>
					<th width="10%">
						<nobr>
							操作
						</nobr>
					</th>
					<th width="20%">
						序号
					</th>
					<th width="70%">
						名称
					</th>
					
				</tr>
				<c:forEach items="${page.results}" var="dictionary"
					varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td>
							<nobr>
								<a
									href="javascript:updateAction('${path}/system/dictionary!modifyDictionary.action?id=${dictionary.id}')">修改</a>

								<a
									href="javascript:addAction('${path}/system/dictionary!deleteDictionary.action?ids=${dictionary.id}')">删除</a>
							</nobr>
						</td>
						<td>
							${dictionary.orderCode}
						</td>
						<td>
							${dictionary.name}
						</td>
						
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
