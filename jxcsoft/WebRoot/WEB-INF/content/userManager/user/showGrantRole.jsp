<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<script type="text/javascript">
		var tree=null;
		Ext.onReady(function() {
			// 生成树
			var rootNode = new Ext.tree.AsyncTreeNode({
				text : '所有角色'
			});

			var treeloader = new Ext.tree.TreeLoader({
				dataUrl : path+'/userManager/user!grantRoleTree.action?userId=${userId}'
			});
			
			tree = new Ext.tree.TreePanel({
				renderTo : 'west-div',
				rootVisible : true,
				loader : treeloader,
				root : rootNode,
				autoHeight:true,
				border:false,
				bodyBorder:false,
				buttonAlign:'center',
				buttons: [{
			            text: '保存',
			            handler: function(){
			                var roleInfo = '';
			                var selNodes = tree.getChecked();
			                Ext.each(selNodes, function(node){
			                    if(roleInfo.length > 0){
			                    	roleInfo += ',';
			                    }
			                    roleInfo += node.id;
			                })
			              Ext.Ajax.request({
									url : path+'/userManager/user!grantRole.action',
									params : {
										roleInfo : roleInfo,
										userId:${userId}
									},
									success : function(response, options) {
										var responseText=response.responseText;
										if(responseText.trim()=='true'){
											Ext.MessageBox.alert('状态', '操作成功',showResult);
											
										}else{
											Ext.MessageBox.alert('状态', '操作失败',showResult);
											 parent.win.close();
										}
										
									},
									failure : function(response, options) {
										Ext.MessageBox.alert('警告', '服务器连接异常，操作失败！');
									},
									waitMsg : '正在提交数据，稍后...'
								});
			               
			            }
			        },{
			            text: '取消',
			            handler: function(){
			                top.closeWindow();
			            }
			        }]
			});
			rootNode.expand();
		});
		function showResult(){
			 parent.location.reload();
			 parent.win.close();
		}
		</script>
	</head>
	<body>
		<div id="west-div">
		</div>
	</body>
</html>
