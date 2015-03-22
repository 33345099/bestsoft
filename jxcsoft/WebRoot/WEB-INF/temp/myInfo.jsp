<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="org.cl.userManager.entity.User"%>
<%
	User user = (User) session.getAttribute("currentUser");
%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/Portal.js">
</script>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/PortalColumn.js">
</script>
		<script type="text/javascript" language="javascript"
			src="${path}/scripts/ext/ux/Portlet.js">
</script>
		<script type="text/javascript">
Ext.onReady(function() {
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.QuickTips.init();
	//创建portal
		var portalPanel = new Ext.ux.Portal(
				{
					renderTo : Ext.getBody(),
					xtype : 'portal',
					border : false,
					items : [
							{
								columnWidth : .50,
								items : [
										{
											title : '公司公告',
											style : 'margin:5px',
											layout : 'fit',
											height : (document.body.clientHeight - 50) / 2 + 50,
											html : "<iframe height='100%' width='100%' scrolling='auto' src='${path}/notice/notice!portalNotice.action'></iframe>"
										},
										{
											title : '通讯录查询',
											style : 'margin:5px',
											height : (document.body.clientHeight - 50) / 2 - 50,
											html : ""
										} ]
							},
							{
								columnWidth : .50,
								items : [

										{
											title : '个人消息',
											style : 'margin:5px',
											height : (document.body.clientHeight - 50) / 2 + 50,
											html : "<iframe height='100%' width='100%' scrolling='auto' src='${path}/userManager/user!portalPersonInfo.action'></iframe>"
										},
										{
											title : '个人信息',
											style : 'margin:5px',
											height : (document.body.clientHeight - 50) / 2 -50,
											html : "<iframe height='100%' width='100%' scrolling='auto' src='${path}/userManager/user!portalPersonInfo.action'></iframe>"
										} ]
							} ]
				});
	});
</script>
	</head>
	<body>

	</body>
</html>
