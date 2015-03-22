<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
		   //判断是否需要刷新左边树(添加、修改、删除)
		   var reloadTree="${transfer_reLoadTree}";
		   if(reloadTree=="true"){
		     parent.tree.root.reload();
		   }
		</script>
	</head>

	<body>
		<web:location location="人事管理-组织机构管理" />

		<div class="content">
			<table class="handle-table">
				<tr>
					<td class="handle-td">
						<input type="button" class="button6" value="添 加 组 织 机 构"
							onclick="addAction('organization!addOrganization.action')" />
					</td>

					<td class="page-td">
						<web:pager/>
					</td>
				</tr>
			</table>
			<table class="grid-table">
				<thead>
					<tr>
						<th width="10%">
							<nobr>
								操作
							</nobr>
						</th>
						<th width="10%">
							机构代码
						</th>
						<th width="25%">
							机构名称
						</th>
						<th width="20%">
							机构类型
						</th>
						<th width="20%">
							所属分公司
						</th>
						<th width="15%">
							联系电话
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.results}" var="organization"
						varStatus="status">
						<tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
							<td>
								<nobr>
									<a
										href="javascript:updateAction('${path}/userManager/organization!modifyOrganization.action?id=${organization.id}')">修改</a>

									<a
										href="javascript:deleteAction('${path}/userManager/organization!deleteOrganization.action?ids=${organization.id}')">删除</a>
								</nobr>
							</td>
							<td>
								${organization.code}
							</td>
							<td>
								${organization.name}
							</td>
							<td>
								<web:ShowDictName identifier="org_type"
									code="${organization.typeCode}" />
							</td>
							<td>
								${organization.filiale.name}
							</td>
							<td>
								${organization.phone}
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>
