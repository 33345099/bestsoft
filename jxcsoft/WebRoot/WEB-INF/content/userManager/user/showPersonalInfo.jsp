<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>
		<web:location location="个人应用-个人信息" />

		<div class="form-content">
			<form id="_form" class="form" cellspacing="1"
				action="user!updatePersonalInfo.action" method="post">
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
							<input type="text" name="user.name" value="${user.name}"
								size="30%" />(修改用户姓名)
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户部门：
							</label>
						</td>
						<td>
							<input type="text" value="${user.organization.name}" size="30%"
								disabled="disabled" />
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户所属组：
							</label>
						</td>
						<td>
							<input type="text" value="${user.groupNames}" size="30%"
								disabled="disabled" />
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户帐号：
							</label>
						</td>
						<td>
							<input type="text" value="${user.username}" size="30%"
								disabled="disabled" />
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户密码：
							</label>
						</td>
						<td>
							<input type="password" name="user.password"
								value="${user.password}" size="32%" />
							(修改密码)
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户邮箱帐户：
							</label>
						</td>
						<td>
							<input type="text" name="user.mailbox.account"
								value="${user.mailbox.account}" size="30%" />
							(配置个人邮箱可以接受系统发送的邮件通知,请配置北大内部邮箱)
						</td>
					</tr>

					<tr height="30px">
						<td width="30%">
							<label>
								用户邮箱账户密码：
							</label>
						</td>
						<td>
							<input type="password" name="user.mailbox.password"
								value="${user.mailbox.password}" size="32%" />(修改用户邮箱账户密码)
						</td>
					</tr>


					<tr>
						<td colspan="2" style="padding-left:35%">
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
