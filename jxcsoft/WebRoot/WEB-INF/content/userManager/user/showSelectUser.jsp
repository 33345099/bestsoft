<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<%@ include file="/common/common.jsp"%>
		<script type="text/javascript">
        Ext.onReady(function() {

        	 var orgTree = new Ext.tree.TreePanel({
        		id:'orgTree',
 				rootVisible : false,
 				autoHeight:true,
 				border:true,
 				root: new Ext.tree.AsyncTreeNode({
 				    id:-1,
 		            text:'组织机构',
 		            expanded:true
 		        })	    				
     	     });
     	     
     	    orgTree.on('beforeload', function(node) {
				orgTree.loader.dataUrl ='user!orgJson.action?parentId='+node.id;
			});
			
        	 var panel =new Ext.TabPanel({
        		 renderTo: Ext.getBody(),
                 activeTab: 0,
                 items:[
                    {contentEl:'tab1',title: '按照部门选择',items:[orgTree]}
                 ],
                buttonAlign:'center',
 				buttons: [{
 			            text: '保存',
 			            handler: function(){
 			               //获取用户选择的组织机构
 			               var ids="";
 			               var names=""; 
 			               for(var i=0;i<orgTree.getChecked().length;i++){
 			                  ids=ids+orgTree.getChecked()[i].id;
 			                  names=names+orgTree.getChecked()[i].text;
 			                  if(i<orgTree.getChecked().length-1){
 			                    ids=ids+",";
 			                    names=names+",";
 			                  }
 			               }
 			               parent.$('${userIdTag}').value=ids;
 			               parent.$('${userNameTag}').value=names;
 			               parent.$('${userNameTempTag}').value=names;
 			               parent.win.close();
 			            }
 			        },{
 			            text: '取消',
 			            handler: function(){
 			        	    parent.win.close();
 			            }
 			        }]
		   });
        });
		</script>
	</head>
	<body style="margin: 0; padding: 0">

		<div id="tab1" class="x-hide-display"></div>

		<div id="tab2" class="x-hide-display"></div>
	</body>
</html>