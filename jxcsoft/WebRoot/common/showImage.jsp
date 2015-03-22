<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%
			String path = request.getContextPath();
			request.setAttribute("path", path);
		%>
		<%@ include file="/common/ext.jsp"%>
		<script type="text/javascript">
var ResizableExample = {
	init : function() {
		var custom = new Ext.Resizable('custom', {
			wrap : true,
			pinned : true,
			minWidth : 50,
			minHeight : 50,
			preserveRatio : true,
			handles : 'all',
			draggable : true,
			dynamic : true
		});
		var customEl = custom.getEl();
		document.body.insertBefore(customEl.dom, document.body.firstChild);
		customEl.center();
		customEl.show(true);
	}
};
Ext.EventManager.onDocumentReady(ResizableExample.init, ResizableExample, true);
</script>
		<style type="text/css">
#basic,#animated {
	border: 1px solid #c3daf9;
	color: #1e4e8f;
	font: bold 14px tahoma, verdana, helvetica;
	text-align: center;
	padding-top: 20px;
}

#snap {
	border: 1px solid #c3daf9;
	overflow: hidden;
}

#custom {
	cursor: move;
}

#custom-rzwrap {
	z-index: 100;
}

#custom-rzwrap .x-resizable-handle {
	width: 11px;
	height: 11px;
	background: transparent
		url(${path}/scripts/ext/resources/images/default/sizer/square.gif)
		no-repeat;
	margin: 0px;
}

#custom-rzwrap .x-resizable-handle-east,#custom-rzwrap .x-resizable-handle-west
	{
	top: 45%;
}

#custom-rzwrap .x-resizable-handle-north,#custom-rzwrap .x-resizable-handle-south
	{
	left: 45%;
}
</style>
	</head>
	<body>
		<img id="custom" src="${imagePath}"
			style="position: absolute; left: 0; top: 0;" />
	</body>
</html>
