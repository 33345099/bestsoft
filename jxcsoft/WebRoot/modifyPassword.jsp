<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<title>修改密码</title>
		<script type="text/javascript">
           function submitForm(){
		     var _form=$("_form");
		     if(CheckFormFunction(_form)){
		        if($('newPassword1').value!=$('newPassword2').value){
		           alert("新密码输入不一致！");
		           $('newPassword1').value='';
		           $('newPassword2').value='';
		           return;
		        }
	            _form.submit(); 
	          }
	        }
   </script>
	</head>

	<body>
		<div class="content">
			<form action="${path}/systemTemp/security!updatePassword.action"
				method="post" id="_form" class="form">
				<table>
					<tr>
						<td>
							<nobr>
								<label>
									<font class="star">*</font>用户名：
								</label>
							</nobr>
						</td>
						<td>
							<input type="text" value="${user.name}" disabled="disabled"
								size="40">
						</td>
					</tr>
					<tr>
						<td>
							<nobr>
								<label>
									<font class="star">*</font>新密码：
								</label>
							</nobr>
						</td>
						<td>
							<input type="password" name="newPassword1" size="40"
								class="name:新密码---check:!NULL&长度大于5---info:新密码不能为空！||新密码长度6～16位">
						</td>
					</tr>
					<tr>
						<td>
							<nobr>
								<label>
									<font class="star">*</font>新密码确认：
								</label>
							</nobr>
						</td>
						<td>
							<input type="password" name="newPassword2" size="40"
								class="name:新密码确认---check:!NULL---info:新密码确认不能为空！">
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="保 存"
								onclick="submitForm()" />
							<input type="reset" class="button" value="重置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
