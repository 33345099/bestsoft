<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<title>角色管理</title>
		<script type="text/javascript">
var win;
function grantMenuResource(roleId) {
	win = showWindow('菜单授权', 338, 500,
			'${path}/userManager/role!showGrantMenu.action?roleId=' + roleId,
			false, true);
	win.show();
}
function grantOperationResource(roleId) {
	win = showWindow(
			'操作授权',
			338,
			500,
			'${path}/userManager/role!showGrantOperation.action?roleId=' + roleId,
			false, true);
	win.show();
}
</script>
	</head>
	<body>

		<web:location location="系统维护-角色管理" />

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="handle-td">


						<security:security identifier="userManager_manage_role">
							<input type="button" class="button6" value="点 击 添 加 记 录"
								onclick="addAction('${path}/userManager/role!addRole.action?_parentId=${_parentId}')" />
						</security:security>
					</td>

					<td class="page-td">
						<web:pager />
					</td>
				</tr>
			</table>
			<TABLE class="grid-table">
				<tr>
					<th width="30%">
						<nobr>
							角色名称
						</nobr>
					</th>
					<th width="30%">
						<nobr>
							描述
						</nobr>
					</th>

					<security:security identifier="userManager_manage_role">
						<th width="10%">
							<nobr>
								菜单授权
							</nobr>
						</th>
						<th width="10%">
							<nobr>
								操作授权
							</nobr>
						</th>

						<th width="10%">
							<nobr>
								操作
							</nobr>
						</th>
					</security:security>
				</tr>
				<c:forEach items="${page.results}" var="role" varStatus="status">
					<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
						<td>
							${role.name}
						</td>
						<td>
							${role.description}
						</td>

						<security:security identifier="userManager_manage_role">
							<td>
								<a href="javascript:grantMenuResource(${role.id})">操作</a>
							</td>
							<td>
								<a href="javascript:grantOperationResource(${role.id})">操作</a>
							</td>
							<td>
								<nobr>
									<a
										href="javascript:updateAction('${path}/userManager/role!modifyRole.action?_parentId=${_parentId}&id=${role.id}')">修改</a>

									<a
										href="javascript:updateAction('${path}/userManager/role!deleteRole.action?_parentId=${_parentId}&ids=${role.id}')">删除</a>

								</nobr>
							</td>
						</security:security>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
