<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<title>添加角色</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/CheckForm.js"></script>
	</head>

	<body>

		<web:location location="系统维护-添加角色" />

		<div class="form-content">
			<form action="role!saveRole.action" method="post" id="_form" class="form">
				<table width="100%">
					<tr>
						<td width="30%" class="labelTd">
							<label>
								<font class="star">*</font>角色名称：
							</label>
						</td>
						<td>
							<input type="text" name="role.name" size="70" maxlength="50"
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
							<textarea rows="10" cols="55" name="role.description"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="保 存" onclick="submitForm(this.form)" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
