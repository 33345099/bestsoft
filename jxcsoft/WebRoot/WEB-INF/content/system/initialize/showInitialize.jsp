<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
	<head>
		<script type="text/javascript">
var win;
function showInitialize(winName, url) {
	win = showWindow(winName, 650, 220, url, false, true);
	win.show();
}
</script>
	</head>
	<body>
		<web:location location="系统维护-数据初始化" />

		<div class="content">

			<TABLE class="grid-table">
				<thead>
					<tr>
						<th width="15%">
							<nobr>
								操作
							</nobr>
						</th>
						<th width="20%">
							<nobr>
								设置名称
							</nobr>
						</th>
						<th width="50%">
							<nobr>
								说明
							</nobr>
						</th>
						<th width="15%">
							<nobr>
								导入模板
							</nobr>
						</th>
						
					</tr>
				</thead>
				<tr class="td_jg">
					<td>
						<nobr>
							<input type="button" class="button" value="操 作"
								onclick="showInitialize('初始化报价项目','initialize!showInitializeQproject.action')" />
						</nobr>
					</td>
					<td>
						<nobr>
							报价项目初始化
						</nobr>
					</td>
					<td>
						系统将以分公司为单位初始化报价项目
					</td>
					<td>
						<a href="${path}/templet/project.xls">下载</a>
					</td>
					
				</tr>

			</table>
		</div>
	</body>
</html>
