
var i = 1;
function addFile() {
	i = i + 1;
	currRow = conditionTableText.insertRow();
	cellc = currRow.insertCell();
	cellcContext = "<input type='text'name='attaches.fileNames' readonly='readonly' id='filename" + i + "'  size='30'><input type='file' id='file" + i + "' name='attaches.upload' class='button' style='width:55px;' onchange='checkFile(" + i + ");'>&nbsp;<input type='button' onclick='removeFile();' class='button' value='\u53d6 \u6d88'/><br>";
	cellc.innerHTML = cellcContext;
}
function findTD(o) {
	if (o.nodeName == "TR" || o.nodeName == "TABLE") {
		return;
	}
	if (o.nodeName == "TD") {
		return (o);
	} else {
		return (o.parentElement);
	}
}
function removeFile() {
	o = findTD(event.srcElement);
	conditionTableText.deleteRow(o.parentElement.rowIndex * 1);
}
function checkFile(num) {
	var file = document.getElementById("file" + num).value;
	var filename = document.getElementById("filename" + num);
	if (file != "") {
		filename.value = file.substring(file.lastIndexOf("\\") + 1, file.length);
	}
}

