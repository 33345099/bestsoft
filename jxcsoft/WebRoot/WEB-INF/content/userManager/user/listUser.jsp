<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type='text/javascript'
			src='${path}/dwr/interface/userService.js'>
</script>
		<script type="text/javascript">
function updateDimission(userId) {
	DWREngine.setAsync(false);
	userService.updateDimission(userId, {
		callback : function(data) {
			if (data == true) {
				$('dimissionHref' + userId).innerHTML = "离职";
				$('dimissionHref' + userId).style.color = 'red';
			} else {
				$('dimissionHref' + userId).innerHTML = "在职";
				$('dimissionHref' + userId).style.color = '';
			}
		}
	});
}

function changeOrg(userId) {
	win = showWindow('操作授权', 338, 500,
			'${path}/userManager/user!showChangeOrg.action?id=' + userId,
			false, true);
	win.show();
}
</script>
	</head>
	<body>

		<web:location location="人事管理-用户管理" />

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="handle-td">
						<input type="button" class="button6" value="添 加 新 用 户"
							onclick="addAction('user!addUser.action')" />
					</td>
					<td class="page-td">
						<web:pager url="user!listUser.action" />
					</td>
				</tr>
			</table>
			<TABLE class="grid-table">
				<thead>
					<tr>
						<th width="6%">
							<nobr>
								操作
							</nobr>
						</th>
						<th width="10%">
							<nobr>
								姓名
							</nobr>
						</th>
						<th width="10%">
							<nobr>
								用户名
							</nobr>
						</th>
						<th width="10%">
							<nobr>
								所属部门
							</nobr>
						</th>

						<th width="10%">
							<nobr>
								授权折扣
							</nobr>
						</th>

						<th width="10%">
							<nobr>
								所属角色
							</nobr>
						</th>

						<th width="10%">
							<nobr>
								职位状态
							</nobr>
						</th>
					</tr>
				</thead>
				<c:forEach items="${page.results}" var="user" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td>
							<nobr>


								<a
									href="javascript:updateAction('user!modifyUser.action?id=${user.id}')">修改</a>
								&nbsp;
								<a
									href="javascript:deleteAction('user!deleteUser.action?ids=${user.id}')">删除</a>
								&nbsp;
								<a href="javascript:changeOrg(${user.id})">部门调整</a> &nbsp;
							</nobr>
						</td>

						<td>
							${user.name}
						</td>
						<td>
							${user.username}
						</td>
						<td>
							${user.organization.name}
						</td>

						<td>
							${user.discountRate}
						</td>

						<td>
							${user.roleNames}
						</td>

						<td>
							<a title="点击修改此状态" id="dimissionHref${user.id}"
								<c:if test="${user.isDimission!=0}">style="color:red"</c:if>
								onclick="javascript:updateDimission(${user.id});return false;"
								href="#"><web:showYesNoName value="${user.isDimission}"
									aliasYes="离职" aliasNo="在职" /> </a> &nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
