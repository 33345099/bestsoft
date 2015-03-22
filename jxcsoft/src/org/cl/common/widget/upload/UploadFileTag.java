package org.cl.common.widget.upload;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 上传文件
 * 
 * @author cl
 * 
 */
public class UploadFileTag extends TagSupport {

	private boolean isMultiple = true;

	private boolean isUploadImage = false;

	private boolean isDeleteFile = false;

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		StringBuffer html = new StringBuffer();
		html.append("<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/scripts/uploadFile.js\"></script>");

		html.append("<table>");

		html.append("<tr>");

		html.append("<td align='left'>");

		html.append("<table id='conditionTableText'>");

		html.append("<tr>");

		html.append("<td>");

		html.append("<input type='hidden' name='isDeleteFile' value='"
				+ isDeleteFile + "'/>");

		html.append("<input type='hidden' name='isUploadImage' value='"
				+ isUploadImage + "'/>");

		html
				.append("<input type='text' readonly='readonly' id='filename0' name='attaches.fileNames' size='30'>");

		html
				.append("<input type='file' id='file0' name='attaches.upload' class='button' style='width:55px;' onchange='checkFile(0);'>");

		if (!isMultiple) {
			html.append("&nbsp;&nbsp;");
			html.append("<font color='red'>注意：上传附件最大为5M</font>");
		}
		html.append("&nbsp;");

		if (isMultiple) {
			html
					.append("<input type='button' onclick='removeFile();' class='button' value='取 消' />");
		}

		html.append("<br>");

		html.append("</td>");

		html.append("</tr>");

		html.append("</table>");

		html.append("</td>");

		html.append("</tr>");

		html.append("<tr>");

		html.append("<td align='left'>");

		if (isMultiple) {
			html
					.append("<input type='button' value='继 续 添 加 附 件' class='button6' onclick='addFile();'>");
			html.append("&nbsp;&nbsp;");
			html.append("<font color='red'>注意：上传附件最大为20M</font>");
		}
		html.append("</td>");
		html.append("</tr>");
		html.append("</table>");
		try {
			pageContext.getOut().println(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public boolean getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}

	public boolean isUploadImage() {
		return isUploadImage;
	}

	public void setUploadImage(boolean isUploadImage) {
		this.isUploadImage = isUploadImage;
	}

	public boolean isDeleteFile() {
		return isDeleteFile;
	}

	public void setDeleteFile(boolean isDeleteFile) {
		this.isDeleteFile = isDeleteFile;
	}

	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}
}
