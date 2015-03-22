<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<%
String status = (String)request.getAttribute("regeditStatus");
%>
</head>
<body>
<%if("1".equals(status)){ %>
<script type="text/javascript">
alert("注册成功！");
window.location.href = "<%=request.getContextPath()%>/login.jsp";
</script>
<%}else{
  if("2".equals(status)){
   %>
   <script type="text/javascript">
alert("注册失败！");
</script>
   <%
  }
  %>
  <form method="post" action="<%=request.getContextPath()%>/system/security!regedit.action">
<input type="text" id="code" name="code"/>
<input type="submit" value="注册">
</form>
  <%
	}%>
</body>
</html>