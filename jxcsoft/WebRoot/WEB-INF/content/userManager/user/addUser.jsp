<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type='text/javascript'
			src='${path}/dwr/interface/orgInterface.js'>
		</script>
		<script type='text/javascript'
			src='${path}/dwr/interface/userService.js'>
		</script>
		<script type="text/javascript">
			function dataScope() {
				var dataScopeLevel = getCheckRadio('user.dataScopeLevelCode');
				var organizationList = null;
				DWREngine.setAsync(false);
				orgInterface.getOrgIdByDataScopeLevel('${organization.filialeId}',
						dataScopeLevel, {
							callback : function(data) {
								organizationList = data;
							}
						});
				if (dataScopeLevel == "single_filiale" || dataScopeLevel == "single_shop") {
					$('user.dataScopeOrgList').multiple = "";
				} else {
					$('user.dataScopeOrgList').multiple = true;
				}
				DWRUtil.removeAllOptions('user.dataScopeOrgList');
				DWRUtil.addOptions('user.dataScopeOrgList', organizationList, 'id', 'name');
				if (dataScopeLevel == '${user.dataScopeLevelCode}') {
					var userDataScopeOrgs = '${user.dataScopeOrgList}';
					for ( var i = 0; i < $('user.dataScopeOrgList').length; i++) {
						var radioValue = $('user.dataScopeOrgList').options[i].value;
						if (userDataScopeOrgs.indexOf("," + radioValue + ",") >= 0) {
							$('user.dataScopeOrgList').options[i].selected = true;
						}
					}
				}
			}
			function submitForm(){
				var _form=$('_form');
				if(CheckFormFunction(_form)){
					DWREngine.setAsync(false);
					var userName = $('user.username').value;
					var isUnique;
					userService.isUniqueUserByUsername(userName,true,null,{
						callback : function(data) {
							isUnique = data;
						}
					});
					if(!isUnique){
						alert("该用户名已存在");
						return false;
					}
					_form.submit();
				}
			}
		</script>
	</head>

	<body>
		<web:location location="人事管理-添加用户" />

		<div class="form-content">
			<form id="_form" class="form" action="user!saveUser.action"
				method="post">
				<web:transfer />
				<input type="hidden" name="user.organizationId"
					value="${organization.id}">
				<table>
					<tr>
						<td>
							<label>
								所属部门：
							</label>
						</td>
						<td>
							<input type="text" value="${organization.name}" size="30"
								disabled="disabled">
						</td>

						<td>
							<label>
								<font class="star">*</font>用户姓名：
							</label>
						</td>
						<td>
							<input type="text" name="user.name" value="${user.name}"
								size="30" maxlength="200"
								class="name:用户姓名---check:!NULL---info:用户姓名不能为空！">
						</td>
					</tr>

					<tr>
						<td>
							<label>
								<font class="star">*</font>登录名：
							</label>
						</td>
						<td>
							<input type="text" name="user.username" value="${user.username}"
								maxlength="20" size="30"
								class="name:用户登录名---check:!NULL---info:用户登录名不能为空！">

						</td>

						<td>
							<label>
								<font class="star">*</font>用户口令：
							</label>
						</td>
						<td>
							<input type="password" name="user.password"
								value="${user.password}" maxlength="10" size="32"
								class="name:用户口令---check:!NULL&长度大于5---info:用户口令不能为空！||用户口令长度6～16位">

						</td>
					</tr>

					<tr>
						<td>
							<label>
								<font class="star">*</font>查看客户联系方式：
							</label>
						</td>
						<td>
							<web:yesNoSelect name="user.isLookCustomerContract"
								value="user.isLookCustomerContract" aliasYes="可看" aliasNo="不可看"
								style="width:215px;" className="check:!NULL---info:查看客户联系方式不能为空" />
						</td>

						<td>
							<label>
								授权折扣率：
							</label>
						</td>
						<td>
							<input type="text" name="user.discountRate"
								value="${user.discountRate}" maxlength="4" size="30"
								class="name:授权折扣率---check:ALLOWANCE---info:请输入有效的授权折扣率！">
							(>=0且<=10)

						</td>
					</tr>


					<tr>
						<td>
							<label>
								所属工种：
							</label>
						</td>
						<td>
							<web:dictionarySelect name="user.dutyCode" identifier="user_duty"
								style="width:215px" />
						</td>
						<td>
						</td>
						<td>
						</td>

					</tr>

					<tr>
						<td>
							<nobr>
								<label>
									客户资料授权范围：
								</label>
							</nobr>
						</td>
						<td>
							<web:dictionarySelect identifier="data_scope_level"
								value="${user.dataScopeLevelCode}" type="radio" newline="true"
								name="user.dataScopeLevelCode" id="user.dataScopeLevelCode"
								hasDefault="true" onclick="dataScope()" />
						</td>
						<td colspan="2">
							<select size="7" style="width: 280px;"
								name="user.dataScopeOrgList">
							</select>
						</td>
					</tr>

					<tr>
						<td>
							<label>
								角色：
							</label>
						</td>
						<td colspan="3">
							<web:select name="roles" items="${roleList}" style="width:225px;"
								value="0" multiple="multiple" size="6" />
						</td>

					</tr>


					<tr>
						<td colspan="4" align="center">
							<input type="button" class="button" value="保 存"
								onclick="submitForm()" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>

				</table>
			</form>
		</div>
	</body>
</html>
