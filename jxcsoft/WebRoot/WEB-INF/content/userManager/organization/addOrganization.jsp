<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<%@ include file="/common/common.jsp"%>
	</head>

	<body>
		<web:location location="人事管理-添加组织机构" />

		<div class="form-content">
			<form method="post" action="organization!saveOrganization.action"
				class="form">
				<web:transfer />
				<table>
					<tr>
						<td>
							<label>
								上级单位：
							</label>
						</td>
						<td>
							<input type="text" size="30" value="${parent.name}"
								disabled="disabled">
							<input type="hidden" name="organization.parentId"
								value=${parent.id}>
						</td>

						<td>
							<label>
								所属地区：
							</label>
						</td>
						<td>
							<web:select name="organization.cityId" items="${cityList}"
								style="width:215px;" hasDefault="true" />
						</td>
					</tr>

					<tr>
						<td>
							<label>
								<font class="star">*</font>机构名称：
							</label>
						</td>
						<td>
							<input type="text" size="30" name="organization.name"
								value="${organization.name}" maxlength="100"
								class="name:机构名称---check:!NULL---info:机构名称不能为空!">
						</td>

						<td>
							<label>
								<font class="star">*</font>机构代码：
							</label>
						</td>
						<td>
							<input type="text" size="30" name="organization.code"
								value="${organization.code}" maxlength="100"
								class="name:机构代码---check:!NULL---info:机构代码不能为空!">
						</td>

					</tr>

					<tr>

						<td>
							<label>
								机构类型：
							</label>
						</td>
						<td>
							<web:dictionarySelect name="organization.typeCode"
								identifier="org_type" style="width:215px;" hasDefault="true" />
						</td>

						<td>
							<label>
								所属分公司：
							</label>
						</td>
						<td>
							<web:select name="organization.filialeId" items="${filialeList}"
								style="width:215px;" hasDefault="true" />
						</td>
					</tr>

					<tr>
						<td colspan="4" align="center">
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
