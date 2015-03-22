/**
 * 根据ID获取HTML元素
 * 
 * @param {}
 *            value html元素ID
 * @return {}
 */
function $(value) {
	return document.getElementById(value);
}
/**
 * 注销
 */
function logout() {
	var flag = window.confirm("\u662f\u5426\u786e\u8ba4\u9000\u51fa\uff01");
	if (flag) {
		window.location.href = path + "/systemTemp/security!logout.action";
	}
}
/**
 * 截取前后空格
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 创建form
 * 
 * @return {}
 */
function getNewForm(formName) {
	var form = document.createElement("form");
	form.name = formName;
	document.body.appendChild(form);
	form.method = "post";
	return form;
}
/**
 * 创建新的form元素，如text
 * 
 * @param {}
 *            form
 * @param {}
 *            elementName
 * @param {}
 *            elementValue
 * @return {}
 */
function createNewFormElement(form, elementName, elementValue) {
	var newElement = document.createElement("<input name='" + elementName
			+ "' type='hidden'>");
	inputForm.appendChild(newElement);
	newElement.value = elementValue;
	return newElement;
}
function confirmAction(actionUrl, confirm) {
	var flag = window.confirm(confirm);
	// 如果拥有可以删除，则提交生产的form对象
	if (flag) {
		var _form = document.getElementById("searchFormTemp");
		if (_form == null) {
			_form = document.getElementById("searchBackFormTemp");
		}
		if (_form == null) {
			_form = document.getElementById("transferForm");
		}
		if (_form == null) {
			_form = document.getElementById("searchForm");
		}
		if (_form == null) {
			_form = getNewForm("searchFormTemp");
		}
		_form.action = actionUrl;
		_form.submit();
	} else {
		return;
	}
}
function multAction(actionUrl, parameterName, idName, type, confirm) {
	// 取出删除记录集合
	var idArray = document.getElementsByName(idName);
	var ids = "";
	// 遍历每一个集合，删除的记录ID组装成字符串，以,隔开
	for ( var i = 0; i < idArray.length; i++) {
		if (idArray[i].checked) {
			ids = ids + idArray[i].value + ",";
		}
	}
	// 如果为空字符，则用户没有选择任何记录
	if (ids == "") {
		alert("\u8bf7\u9009\u62e9\u4e00\u6761\u8bb0\u5f55");
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	var idStrArray = ids.split(",");
	if (typeof (confirm) == "undefined") {
		confirm = "\u662f\u5426\u786e\u8ba4\u5220\u9664!";
	}
	var flag = null;
	if (type) {
		flag = window.confirm(confirm);
	} else {
		flag = true;
	}
	// 如果拥有可以删除，则提交生产的form对象
	if (flag) {
		var _form = document.getElementById("searchFormTemp");
		if (_form == null) {
			_form = document.getElementById("searchBackFormTemp");
		}
		if (_form == null) {
			_form = document.getElementById("transferForm");
		}
		if (_form == null) {
			_form = document.getElementById("_form");
		}
		if (_form == null) {
			_form = getNewForm("searchFormTemp");
		}
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = parameterName;
		hiddenInput.value = ids;
		_form.appendChild(hiddenInput);
		_form.action = actionUrl;
		_form.submit();
	} else {
		return;
	}
}
/**
 * 删除操作调用的js函数
 * 
 * @param {}
 *            actionUrl
 * @param {}
 *            parameterName
 * @param {}
 *            idName
 */
function deleteAction(actionUrl, type) {
	var flag = null;
	if (type && type == 1) {
		flag = window.confirm("您确定要撤消!");
	} else {
		flag = window.confirm("\u662f\u5426\u786e\u8ba4\u5220\u9664\uff01");
	}
	// 如果拥有可以删除，则提交生产的form对象
	if (flag) {
		var _form = document.getElementById("searchFormTemp");
		if (_form == null) {
			_form = document.getElementById("searchBackFormTemp");
		}
		if (_form == null) {
			_form = document.getElementById("transferForm");
		}
		if (_form == null) {
			_form = document.getElementById("_form");
		}
		if (_form == null) {
			_form = getNewForm("searchFormTemp");
		}
		_form.action = actionUrl;
		_form.submit();
	} else {
		return;
	}
}
function href(url) {
	var _form = document.getElementById("searchFormTemp");
	if (_form == null) {
		_form = document.getElementById("'searchBackFormTemp'  ");
	}
	if (_form == null) {
		_form = document.getElementById("transferForm");
	}
	if (_form == null) {
		_form = getNewForm("searchFormTemp");
	}
	_form.action = url;
	_form.submit();
}
/**
 * 添加操作调用的js函数
 * 
 * @param {}
 *            actionUrl
 */
function addAction(actionUrl) {
	var _form = document.getElementById("searchFormTemp");
	if (_form == null) {
		_form = document.getElementById("searchBackFormTemp");
	}
	if (_form == null) {
		_form = document.getElementById("transferForm");
	}
	if (_form == null) {
		_form = document.getElementById("_form");
	}
	if (_form == null) {
		_form = getNewForm("searchFormTemp");
	}
	_form.action = actionUrl;
	_form.submit();
}
/**
 * 修改操作调用的js函数
 * 
 * @param {}
 *            actionUrl
 * @param {}
 *            parameterName
 * @param {}
 *            idName
 */
function updateAction(actionUrl, parameterName, idName, confirm) {
	var _form = document.getElementById("searchFormTemp");
	if (_form == null) {
		_form = document.getElementById("searchBackFormTemp");
	}
	if (_form == null) {
		_form = document.getElementById("transferForm");
	}
	if (_form == null) {
		_form = document.getElementById("_form");
	}
	if (_form == null) {
		_form = getNewForm("searchFormTemp");
	}
	if (null != parameterName) {
		var cbs = document.getElementsByName(parameterName);
		if (cbs == undefined) {
			alert("\u8bf7\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff01");
			return;
		}
		var id = null;
		var selectNum = 0;
		for ( var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				selectNum = selectNum + 1;
				id = cbs[i].value;
			}
		}
		if (selectNum == 0) {
			alert("\u8bf7\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff01");
			return;
		}
		if (selectNum > 1) {
			alert("\u53ea\u80fd\u9009\u62e9\u4e00\u6761\u8bb0\u5f55!");
			return;
		}
		var hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = idName;
		hiddenInput.value = id;
		_form.appendChild(hiddenInput);
	}
	if (typeof (confirm) != "undefined") {
		var flag = window.confirm(confirm);
		if (!flag) {
			return;
		}
	}
	_form.action = actionUrl;
	_form.submit();
}
/**
 * checkbox全选函数 cAllName是全选checkbox按钮的ID checkboxsName是全选checkbox组的name
 */
function checkAll(thisCheck, checkboxsName) {
	var cabs = thisCheck;
	var cbs = document.getElementsByName(checkboxsName);
	if (cbs == undefined) {
		return;
	}
	if (typeof cbs.length == "undefined") {
		cbs = document.forms[0].id;
		if (cbs.checked && !cbs.checked) {
			cbs.checked = true;
		}
		if (!cbs.checked && cbs.checked) {
			cbs.checked = false;
		}
		return;
	}
	for (var i = 0; i < cbs.length; i++) {
		if (cabs.checked) {
			cbs[i].checked = true;
		} else {
			(cbs[i].checked) = false;
		}
	}
}
function alertJump(info) {
	alert(info);
}
function alertInfo(info) {
	alert(info);
}
/**
 * checkbox全选函数 cAllName是全选checkbox按钮的ID checkboxsName是全选checkbox组的name
 */
function radioValue(name) {
	var value = "";
	var objs = document.getElementsByName(name);
	for (i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			value = objs[i].value;
			break;
		}
	}
	return value;
}


// 下载附件
function download(fid, type) {
	if (document.getElementById("downloadForm") == null) {
		var downloadForm = document.createElement("FORM");
		document.body.appendChild(downloadForm);
		downloadForm.method = "POST";
		downloadForm.action = path + "/common/attach!download.action";
		downloadForm.id = "downloadForm";
		downloadForm.style.display = "none";
		var download_fileId = document.createElement("<input name='download_fileId' id='download_fileId' type='hidden'>");
		var instanceName = document.createElement("<input name='instanceName' id='instanceName' type='hidden'>");
		downloadForm.appendChild(download_fileId);
		downloadForm.appendChild(instanceName);
	}
	if (fid != null && type != null) {
		var fileForm = document.getElementById("downloadForm");
		var fileId = document.getElementById("download_fileId");
		var instanceName = document.getElementById("instanceName");
		instanceName.value = type;
		fileId.value = fid;
		fileForm.submit();
	} else {
		alert("\u53c2\u6570\u4e0d\u6b63\u786e\uff01");
	}
}
function clearSearchForm() {
	var _form = $("searchForm");
	var elements = _form.getElementsByTagName("input");
	for (var j = 0; j < elements.length; j++) {
		if (elements[j].type == "text") {
			elements[j].value = "";
		}
	}
	elements = _form.getElementsByTagName("select");
	for (var j = 0; j < elements.length; j++) {
		elements[j].value = "-1";
	}
}
function submitForm(_form) {
	if (CheckFormFunction(_form)) {
		_form.submit();
	}
}
function checkFile(uploadNameTag, fileNameTag) {
	var file = document.getElementById(uploadNameTag).value;
	var fileName = document.getElementById(fileNameTag);
	if (file != "") {
		fileName.value = file.substring(file.lastIndexOf("\\") + 1, file.length);
	}
}
/**
 * 添加option
 */
function addOptionToSelect(selectName, value, name, isSelect, index) {
	var selectObj = $(selectName);
	var objOption = document.createElement("option");
	if (null != value) {
		objOption.value = value;
	}
	if (null != name) {
		objOption.text = name;
	}
	if (null == index) {
		index = 0;
	}
	selectObj.add(objOption, index);
	if (isSelect) {
		selectObj.options[index].selected = "selected";
	}
}
function selectMultipleUser(userNameTempTag, userNameTag, userIdTag) {
	var url = path + "/userManager/user!showSelectUser.action";
	url = url + "?userNameTempTag=" + userNameTempTag;
	url = url + "&userNameTag=" + userNameTag;
	url = url + "&userIdTag=" + userIdTag;
	win = showWindow("\u9009\u62e9\u7528\u6237", 600, 450, url, false, true);
	win.show();
}
function getCheckRadio(tagName) {
	var radios = document.getElementsByName(tagName);
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			return radios[i].value;
		}
	}
}


function display(thisEl, idTag,hiddenTag,showTag) {
	if ($(idTag).style.display == 'none') {
		$(idTag).style.display = '';
		thisEl.value = hiddenTag;
	} else {
		$(idTag).style.display = 'none';
		thisEl.value = showTag;
	}
}

function queryForMore(showMore) {
	if (typeof(showMore) != "undefined" && showMore == 1) {
		$('transfer_queryForMore').value = '0';
	}

	var hiddenQueryArray = document.getElementsByTagName("tr");
	if ($('transfer_queryForMore').value == '' || $('transfer_queryForMore').value == '0') {
		$('transfer_queryForMore').value = '1';
		$('_queryForMoreButton').value = '不显示更多查询条件';
	} else {
		$('transfer_queryForMore').value = '0';
		$('_queryForMoreButton').value = '显示更多查询条件';
	}
	
	
	for (var i = 0; i < hiddenQueryArray.length; i++) {
		if (hiddenQueryArray[i].className != "queryForMoreTr") {
			continue;
		}

		if ($('transfer_queryForMore').value == '1') {
			if(Ext.isIE){				
				hiddenQueryArray[i].style.display = 'block';
			}else{				
				hiddenQueryArray[i].style.display = 'table-row';
			}
		} else {
			hiddenQueryArray[i].style.display = 'none';
			var elements = hiddenQueryArray[i].getElementsByTagName("input");
			for (var j = 0; j < elements.length; j++) {
				if (elements[j].type == "text") {
					elements[j].value = '';
				}
			}
			elements = hiddenQueryArray[i].getElementsByTagName("select");
			for (var j = 0; j < elements.length; j++) {
				elements[j].value = '-1';
			}
		}
	}
}

var imageWin=null;
function showImage(winName,uploadPath,attachName,ownerId) {
    imageWin = showWindow(winName, 800, 520,
			path+'/common/attach!listImages.action?attachName='+attachName+'&ownerId='+ownerId+'&uploadPath='+uploadPath, false, true);
    imageWin.show();
}

function showProgressImage(ownerId) {
	showImage("查看图片","progress_upload_path","ProgressAcceptanceAttach",ownerId);
}

function openDetailWin(url){
	var detailWin = null;
	detailWin = showWindow(
			'详细内容',
			1000,
			550,
			url,
			false, true);
	detailWin.show();
}

