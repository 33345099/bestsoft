<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
			// 生成树
			var rootNode = new Ext.tree.AsyncTreeNode({
				text : 'root'
			});

			var treeloader = new Ext.tree.TreeLoader({
				dataUrl : path+'/userManager/user!changeOrgTree.action?userId=${user.id}'
			});
			
			tree = new Ext.tree.TreePanel({
				renderTo : 'west-div',
				rootVisible : false,
				loader : treeloader,
				root : rootNode,
				autoHeight:true,
				border:false,
				bodyBorder:false,
				buttonAlign:'center',
				buttons: [{
			            text: '保存',
			            handler: function(){
			                var orgId = null;
			                var selNodes = tree.getChecked();
			                Ext.each(selNodes, function(node){
			                    orgId = node.id;
			                })
			              Ext.Ajax.request({
									url : path+'/userManager/user!changeOrg.action',
									params : {
			            	            orgId : orgId,
										userId:${user.id}
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
			tree.on('checkchange', function(node, check){ 
				if(node.attributes.checked==false){
					  return;
					}
			    for(var i=0;i<tree.getChecked().length;i++){
			    	  var temp=tree.getChecked()[i];
 			                  if(temp.id!=node.id){
 			                    //设置UI状态为未选中状态
					    		  temp.getUI().toggleCheck(false);
					    		 //设置节点属性为未选中状态
					    		 temp.attributes.checked=false;
					    		   
 			                  }
 			               }
			});
						
		});
		function showResult(){
			parent.location.href='user!listUser.action?transfer_organizationId=${user.organizationId}';
			 parent.win.close();
		}
		</script>
	</head>
	<body>
		<div id="west-div">
		</div>
	</body>
</html>
