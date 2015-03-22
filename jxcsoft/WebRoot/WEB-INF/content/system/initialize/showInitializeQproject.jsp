<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
var pbar = null;
function exportFile(thisForm) {
	if (!CheckFormFunction(thisForm)) {
		return;
	}
	$('_form').submit();
	$('div-form').style.display = "none";
	$('div-progress').style.display = "";
	pbar = new Ext.ProgressBar( {
		width : 420,
		renderTo : 'p3'
	});
	Ext.fly('progressText').update('正在上传，请耐心等待......');
	pbar.wait( {});
}
function callback(returnmsg, isSuccess) {
	if (isSuccess) {
		alert(returnmsg);
		parent.location.reload();
		parent.win.close();
	} else {
		pbar.reset(true);
		Ext.fly('message').update(returnmsg);
		$('div-form').style.display = "";
		$('div-progress').style.display = "none";
	}
}
</script>
	</head>

	<body>

		<div class="form-content" id="div-form">
			<form id="_form" class="form" method="post"
				enctype="multipart/form-data" target="hidden_frame"
				action="${path}/system/initialize!initializeQproject.action">
				<table>
					<tr>
						<td colspan="2" align="center">
							<font id="message" color="red"></font>
						</td>
					</tr>
					<tr>
						<td class="normalTd" width="20%">
							<label>
								<font class="star">*</font>所属分公司：
							</label>
						</td>
						<td>
							<web:select name="filialeId" items="${filialeList}"
								style="width:220px;" hasDefault="true"
								className="check:!NULL---info:所属分公司！" />
						</td>
					</tr>
					<tr>
						<td class="normalTd" width="20%">
							<label>
								<font class="star">*</font>导入Excel：
							</label>
						</td>
						<td>
							<web:uploadFile isMultiple="false" />
						</td>
					</tr>

					<tr>
						<td colspan="2" align="center">
							<input type="button" class="button" value="上 传"
								onclick="exportFile(this.form)" />
						</td>
					</tr>
				</table>
			</form>
			<iframe name='hidden_frame' id="hidden_frame" style='display: none'></iframe>
		</div>

		<div id="div-progress" style='display: none; margin: 50px 0 0 20px;'>
			<div id="p3"></div>
			<span class="status" id="progressText">开始导入</span>
		</div>
	</body>
</html>
