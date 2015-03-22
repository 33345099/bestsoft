<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<title>组织机构管理</title>
		<script type="text/javascript">
		var tree=null;
		Ext.onReady(function() {
			// 创建左右布局的对象
			var viewport = createViewportForLeftRightObject('west-div','center-div','', '',200);
			// 生成树
	        tree = createTree('west-div','organization!treeOrganization.action', ${organization.id}, '${organization.name}', true, null, 600, true);
	        // 单击树节点时在左边框架显示此菜单的子菜单集合,如果是非叶子节点则显示子菜单，如果是叶子节点则显示操作集合
            tree.on("click", function(node, event) {
	           node.expand();
	           if(node.attributes.type!='bj'){
	              window.mainFrame.location.href='organization!listOrganization.action?transfer_parentId='+node.id;
	            }
            });
		});
		</script>
	</head>
	<body>
		<div id="west-div">

		</div>
		<div id="center-div">
			<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
				src="organization!listOrganization.action?transfer_parentId=${organization.id}"></iframe>
		</div>
	</body>
</html>