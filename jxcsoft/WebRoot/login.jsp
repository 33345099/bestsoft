<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<title>家装erp管理系统</title>
		<%@ include file="/common/common.jsp"%>
		<style type="text/css">
		body{
			background-image: url(${path}/images/loginbg.jpg);
			background-repeat: no-repeat;
			background-position:center;
			}
		</style>
		<script type="text/javascript">
     function submitForm(){
        var flag=true;
        var username=document.getElementById("username");
        var password=document.getElementById("password");
        if(username.value==''){
           alert('用户名不能为空！');
           username.focus();
           return false;
        }
        if(password.value==''){
           password.focus();
           alert('密码不能为空！');
           return false;
        }
        return true;
      }
   </script>
	</head>

	<body bgproperties="fixed" >
		<table width="100%" height="100%" >
			<tr>
				<td align="center" style="padding-bottom: 150px;">
					<table width="500px;" height="282px;" class="loginbg">
						<tr>
							<td style="padding: 180px 0px 0px 528px;">
								<form
									action="<%=request.getContextPath()%>/system/security!login.action"
									method="post" onsubmit="return submitForm()">
									<table>
										<tr>
											<td>
												<font color="red"><b>${error} 
												</font>
											</td>
										</tr>
										<tr height="30">
											<td>
												<input name="username" id="username" type="text" size="18"  style="height:25px;width:140px;"/>
											</td>
										</tr>
										<tr height="30">
											<td>
												<input name="password" id="password" type="password" size="18"
													 style="height:25px;width:140px"/>
											</td>
										</tr>
										<tr height="20">
										<td></td>
										</tr>
										<tr height="30">
											<td align="center">
												<input type="submit" class="button" value="登录" 
													src="<%=request.getContextPath()%>/images/dl.gif" />
											</td>
										</tr>
									</table>
								</form>
							<td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
