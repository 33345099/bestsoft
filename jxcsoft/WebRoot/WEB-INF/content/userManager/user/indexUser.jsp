<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
			// 创建左右布局的对象
			var viewport = createViewportForLeftRightObject('west-div','center-div','用户管理', '',200);
			// 生成树
	        tree = createTree('west-div','user!treeOrganization.action', ${rootOrganization.id}, '${rootOrganization.name}', true, null, 0, true);
	        // 单击树节点时在左边框架显示此菜单的子菜单集合,如果是非叶子节点则显示子菜单，如果是叶子节点则显示操作集合
            tree.on("click", function(node, event) {
	           node.expand();
	           window.mainFrame.location.href='user!listUser.action?transfer_organizationId='+node.id;
            });
		});
		</script>
	</head>
	<body>
		<div id="west-div">

		</div>
		<div id="center-div">
			<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
				scrolling="auto"
				src="user!listUser.action?transfer_organizationId=${rootOrganization.id}"></iframe>
		</div>
	</body>
</html>