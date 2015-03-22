<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="org.cl.userManager.entity.User"%>
<%
	User user = (User) session.getAttribute("currentUser");
%>
<html>
	<head>
		<title>家装erp管理系统</title>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/TabScrollerMenu.js">
</script>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/Portal.js">
</script>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/PortalColumn.js">
</script>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/Portlet.js">
</script>
		<link rel="stylesheet" type="text/css" href="tab-scroller-menu.css" />
		<script type="text/javascript">
var win;
var userId = '${user.id}';
Ext.onReady(function () {
	      var accordion = new Ext.Panel({region:"west", contentEl:"west-div", collapsed:false,title:"\u529f\u80fd\u5bfc\u822a\u680f", split:true, frame:true,width:190,collapsible:false, margins:"0 0 5 5", layout:"accordion", layoutConfig:{animate:true}});
	// 根据模块数组生产导航栏
	<%for (int i = 0; i < user.getModuleMenus().size(); i++) {%>
       var item = new Ext.Panel({title:'<%=user.getModuleMenus().get(i).getName()%>', html:"<div id='tree-" + <%=user.getModuleMenus().get(i).getId()%> + "'><div>", autoScroll:true, border:false});
	   accordion.add(item);
    <%}%>
	 var viewport = new Ext.Viewport({layout:"border", items:[accordion, {region:"center", contentEl:"center-div", title:"当前用户：${currentUser.name}", split:true, autoScroll:false, margins:"0 5 5 0"}]});
	 // 遍历模块数组，生产每一个模块的树型结构
	 <%for (int i = 0; i < user.getModuleMenus().size(); i++) {%>
         var tree = createTree("tree-" + <%=user.getModuleMenus().get(i).getId()%>, path + "/system/security!treeMenu.action?userId=${user.id}", <%=user.getModuleMenus().get(i).getId()%>, '<%=user.getModuleMenus().get(i).getName()%>', false, 500, null, true);
		 tree.on("click", function (node, event) {
		 // 如果是叶子节点，则左边Iframe跳转到指定页面，如果不是叶子节点，则展开节点
		if (node.isLeaf()) {
			window.mainFrame.location.href = path + "/" + node.attributes.url;
		} else {
		if (node.expanded) {
			node.collapse();
		} else {
			node.expand();
		 }
		}
	});
   <%}%>
});
		</script>
	</head>
	<body>
		<div id="west-div">

		</div>
		<div id="center-div" style="height: 100%">
			<iframe id="mainFrame" name="mainFrame" height="100%" width="100%"
				scrolling="auto" src="${path}/system/index!myInfo.action"></iframe>
		</div>
	</body>
</html>
