<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>
		<div class="form-content">
			<form id="_form" class="form"
				action="${path}/system/security!updatePassword.action" method="post">
				<web:transfer />
				<input type="hidden" name="user.id" value="${user.id}">
				<table height="80%">
					<tr height="30px">
						<td width="30%">
							<label>
								用户姓名：
							</label>
						</td>
						<td>
							${user.name}
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								所属分公司：
							</label>
						</td>
						<td>
							${user.filiale.name}
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								所属部门：
							</label>
						</td>
						<td>
							${user.organization.name}
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户职务：
							</label>
						</td>
						<td>
							<web:ShowDictName identifier="user_duty" code="${user.dutyCode}" />
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户帐号：
							</label>
						</td>
						<td>
							${user.username}
						</td>
					</tr>
					<tr height="30px">
						<td width="30%">
							<label>
								用户角色：
							</label>
						</td>
						<td>
							${user.roleNames}&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
