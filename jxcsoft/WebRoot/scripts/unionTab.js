function unionTab(tbId, colLength) {
	var tb = $(tbId);
	//   检查表格是否规整     
	if (!checkTab(tb)) {
		return;
	}
	var rowCount = tb.rows.length; // 行数  
	//为每个单元格命名,表头不命名  
	for (i = 1; i < rowCount; i++) {
		for (j = 0; j < colLength; j++) {
			tb.rows[i].cells[j].id = "tb_" + i.toString() + "_" + j.toString();
		}
	}
	var obj1 = null;
	var obj2 = null;
	var flag = false;
	var difference = 0;
	//开始遍历合并
	for ( var i = 1; i < rowCount; i++) {
		obj1 = tb.rows[i];
		for ( var j = i + 1; j < rowCount; j++) {
			obj2 = tb.rows[j];
			var flag = checkUnion(i, j, colLength);
			//需要合并
			if (flag) {
				union(i, j, colLength);
			} else {
				break;
			}
			difference = j;
		}
		i = difference;
	}
}

/**
 * 合并
 * @param {Object} obj1
 * @param {Object} obj2
 */
function union(obj1, obj2, colLength) {
	for ( var i = 0; i < colLength; i++) {
		$("tb_" + obj1 + "_" + i).rowSpan++;
		$("tb_" + obj2 + "_" + i).parentNode.removeChild($("tb_" + obj2 + "_" + i));
	}
}

function checkUnion(obj1, obj2, colLength) {
	for ( var i = 0; i < colLength; i++) {
		if (null == $("tb_" + obj1 + "_" + i)) {
			return false;
		}
		var a = $("tb_" + obj1 + "_" + i).innerText.trim();
		var b = $("tb_" + obj2 + "_" + i).innerText.trim();
		if (a != b) {
			return false;
		}
	}
	return true;
}

/////////////////////////////////////////     
// 功能：检查表格是否规整  
// 参数：tb－－需要检查的表格ID  
// ///////////////////////////////////////  
function checkTab(tb) {
	if (tb.rows.length == 0 || tb.rows.length == 1) {
		return false;
	}
	if (tb.rows[0].cells.length == 0)
		return false;
	return true;
}