<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/common.jsp"%>

<html>
	<head>
		<script type="text/javascript">
		var tree=null;
		var notCheckChild=true;
		Ext.onReady(function() {
			// 生成树
			var rootNode = new Ext.tree.AsyncTreeNode({
				text : 'root'
			});

			var treeloader = new Ext.tree.TreeLoader({
				dataUrl : path+'/userManager/role!grantOperationTree.action?roleId=${roleId}'
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
			                var grantInfo = '';
			                var selNodes = tree.getChecked();
			         
			                Ext.each(selNodes, function(node){
			                    if(grantInfo.length > 0){
			                    	grantInfo += ',';
			                    }
			                    grantInfo += node.id;
			                })
			              Ext.Ajax.request({
									url : path+'/userManager/role!grantOperation.action',
									params : {
			            	            grantInfo : grantInfo,
										roleId:${roleId}
									},
									success : function(response, options) {
										var responseText=response.responseText;
										if(responseText.trim()=='true'){
											Ext.MessageBox.alert('状态', '操作成功',showResult);
											
										}else{
											Ext.MessageBox.alert('状态', '操作失败',showResult);
											top.closeWindow();
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
			                parent.win.close();
			            }
			        }]
			});
			tree.on('checkchange', function(node, check){ 
			      if(node.hasChildNodes()){
			    	  var childNodeArray=node.childNodes;
				       if(check&&notCheckChild){
				           node.expand();
				    	   for(var i=0;i<childNodeArray.length ;i++){
				    		  //设置UI状态为未选中状态
				    		   childNodeArray[i].getUI().toggleCheck(true);
				    		 //设置节点属性为未选中状态
				    		   childNodeArray[i].attributes.checked=true;
						   }
					   }else if(notCheckChild){
						   for(var i=0;i<childNodeArray.length ;i++){
					    		  //设置UI状态为未选中状态
					    		   childNodeArray[i].getUI().toggleCheck(false);
					    		 //设置节点属性为未选中状态
					    		   childNodeArray[i].attributes.checked=false;
							}
						     
					   }
				       notCheckChild=true;
				    }else if(node.id.indexOf('operation')>=0){
				    	if(check){
				    		notCheckChild=false;
				    		parentNode=node.parentNode;
				    		if(parentNode.attributes.checked==false){
				    			//设置UI状态为未选中状态
					    		   parentNode.getUI().toggleCheck(true);
					    		 //设置节点属性为未选中状态
					    		   parentNode.attributes.checked=true;
					    	}
					   }else{
						   notCheckChild=true;
					   }
				  }
			});
						
		});
		function showResult(){
			 parent.win.close();
			}
		</script>
	</head>
	<body>
		<div id="west-div">
		</div>
	</body>
</html>
