<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<title>修改数据字典</title>
	</head>

	<body>
		<web:location location="系统维护-修改${dictionarySort.name}" />

		<div class="form-content">
			<form action="${path}/system/dictionary!updateDictionary.action" method="post" class="form">
				<input type="hidden" name="dictionary.dictionarySortId" value="${dictionary.dictionarySortId}">
				<input type="hidden" name="dictionary.id" value="${dictionary.id}">
				<input type="hidden" name="dictionary.identifier" value="${dictionary.identifier}">
				<web:transfer />
				<table>
					<tr>
						<td width="30%">
							<label>
								数据字典分类名称
							</label>
						</td>
						<td>
							<input type="text" value="${dictionarySort.name}" size="40" disabled="disabled">
						</td>
					</tr>
					<tr>
						<td width="30%">
							<label>
								数据字典名称:
							</label>
						</td>
						<td>
							<input type="text" name="dictionary.name" size="40" value="${dictionary.name}"
								maxlength="200">
						</td>
					</tr>
					<tr>
						<td width="30%">
							<label>
								显示顺序
							</label>
						</td>
						<td>
							<input type="text" name="dictionary.orderCode" size="40" value="${dictionary.orderCode}"
								maxlength="5" class="name:显示顺序---check:INT---info:请输入有效数字">
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" class="button" value="保 存" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
