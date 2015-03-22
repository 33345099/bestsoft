/**
 * 创建左右布局的对象
 * 
 * @param {}
 *            westContentEl 左边布局的El元素
 * @param {}
 *            centerContentEl 中间布局的El元素
 * @param {}
 *            westTitle 左边布局的标题
 * @param {}
 *            centerTitle 中间布局的标题
 * @param {}
 *            westWidth 左边宽度
 */
function createViewportForLeftRightObject(westContentEl, centerContentEl,
		westTitle, centerTitle, westWidth) {
	this.westContentEl = westContentEl == undefined
			? "west-div"
			: westContentEl;
	this.centerContentEl = centerContentEl == undefined
			? "center-div"
			: centerContentEl;
	this.westTitle = westTitle == undefined ? "" : westTitle;
	this.centerTitle = centerTitle == undefined ? "" : centerTitle;
	this.westWidth = westWidth == undefined ? 220 : westWidth;
	this.viewport = new Ext.Viewport({
				layout : 'border',
				items : [{
							region : 'west',
							contentEl : this.westContentEl,
							title : this.westTitle,
							split : false,
							frame:true,
							width : this.westWidth
						}, {
							region : 'center',
							contentEl : this.centerContentEl,
							title : this.centerTitle,
							split : false,
							autoScroll : false
						}]
			});
}

/**
 * 创建树
 * 
 * @param {}
 *            renderTo 填充区域
 * @param {}
 *            rooName 根节点名称
 * @param {}
 *            rootVisible 根节点是否可见
 * @param {}
 *            dataUrl 加载地址
 * @param {}
 *            height 高度
 * @param {}
 *            width 宽度
 * @param {}
 *            isDynamic 是否是动态树
 * @return {}
 */
function createTree(renderTo, dataUrl, rootId, rooName, rootVisible, width,
		height, isDynamic) {
	// 根节点
	rootNode = new Ext.tree.AsyncTreeNode({
				id : rootId,
				text : rooName
			});
	if (dataUrl.indexOf('?') > 0) {
		dataUrl = dataUrl + '&'
	} else {
		dataUrl = dataUrl + '?'
	}
	// 加载路径
	var treeloader = new Ext.tree.TreeLoader({
				dataUrl : dataUrl + 'parentId=' + rootId
			});
	// 生成树
	var tree = new Ext.tree.TreePanel({
				renderTo : renderTo,
				rootVisible : rootVisible,
				loader : treeloader,
				frame:false,
				//autoWidth:true,
				autoScroll:true,
				border:false,
				root : rootNode
			});
	if (rootVisible) {
		rootNode.expand();
	}
	if (isDynamic) {
		tree.on('beforeload', function(node) {
					if (dataUrl.indexOf('?') > 0) {
						tree.loader.dataUrl = dataUrl + '&parentId=' + node.id;
					} else {
						tree.loader.dataUrl = dataUrl + 'parentId=' + node.id;
					}
				});
	}
	if (height) {
		tree.setHeight(height);
	}else{
		tree.setHeight(500);
	}
	if (width) {
		tree.setWidth(width);
	}else{
		tree.setWidth("auto");
	}
	return tree;
}

// 弹出公共窗口对象
function showWindow(title, width, height, src, scrolling, modal, x, y) {
	var win = createWindow(title, width, height, src, x, y, scrolling, modal);
	return win;
}

/**
 * 创建弹出窗口
 * 
 * @param title
 *            标题
 * @param width
 *            宽度
 * @param height
 *            高度
 * @param src
 *            数据源
 * @param x
 *            X坐标
 * @param y
 *            y坐标
 * @param scrolling
 *            是否允许出现滚动条
 * @param modal
 *            是否模态
 * @return
 */
function createWindow(title, width, height, src, x, y, scrolling, modal) {
	var window = new Ext.Window({
		width : width,
		height : height,
		title : title,
		autoScroll : scrolling,
		border : false,
		draggable : true,
		x : x,
		y : y,
		modal : modal,
		html : "<iframe src='"
				+ src
				+ "' frameborder='0' width='100%' height='100%'  name='globFrm'></iframe>",
		layout : "fit"
	});
	return window;
}

/**
 * 关闭Ext打开的窗口
 */
function closeWindow() {
	top.$('globFrm').src = "";
	win.close();
}