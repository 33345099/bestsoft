<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>

		<web:location location="系统维护-修改角色" />


		<div class="form-content">
			<form action="role!updateRole.action" method="post" id="_form"
				class="form">
				<input type="hidden" name="role.id" value="${role.id}">
				<table width="100%">
					<tr>
						<td width="30%" class="labelTd">
							<label>
								<font class="star">*</font>角色名称：
							</label>
						</td>
						<td>
							<input type="text" name="role.name" size="70"
								value="${role.name}" maxlength="200"
								class="name:角色名称---check:!NULL---info:角色名称不能为空！">
						</td>
					</tr>

					<tr>
						<td class="labelTd">
							<label>
								说明：
							</label>
						</td>
						<td>
							<textarea rows="10" cols="55" name="role.description">${role.description}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="保 存"
								onclick="submitForm(this.form)" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
