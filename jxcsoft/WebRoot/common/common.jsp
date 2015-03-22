<%@ taglib uri="widget/tags-web" prefix="web"%>
<%@ taglib prefix="f" uri="widget/tags-format"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<%@ include file="/common/ext.jsp"%>
<script language="javascript">
var path = '<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="${path}/dwr/engine.js">
</script>
<script type="text/javascript" src="${path}/dwr/util.js">
</script>
<script type="text/javascript" language="javascript"
	src="${path}/scripts/common.js">
</script>
<script language="javascript" type="text/javascript"
	src="${path}/scripts/DatePicker/WdatePicker.js">
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/CheckForm.js">
</script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${path}/styles/layout.css" rel="stylesheet" type="text/css" />
<link href="${path}/styles/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/CheckForm.js">
</script>

<script language="javascript">
var path = '<%=request.getContextPath()%>';
window.onload = check;
function check() {
	if ($('transfer_queryForMore') != null
			&& $('transfer_queryForMore').value == '1') {
		queryForMore(1);
	}
}
</script>