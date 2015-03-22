package org.cl.common.widget.upload;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.cl.common.core.orm.AttachEntity;

/**
 * 附件标签
 * 
 * @author chenl
 * @version Dec 19, 2008
 */
public class ListAttachTag extends TagSupport {

	/**
	 * 附件集合
	 */
	private Set files = null;

	private Integer length = 20;

	/**
	 * 保存目录
	 */
	private String uploadPath = null;

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		StringBuffer searchHtml = new StringBuffer();
		Iterator it = files.iterator();
		AttachEntity attachEntity = null;
		String fileName = null;
		while (it.hasNext()) {
			attachEntity = (AttachEntity) it.next();
			fileName = attachEntity.getFileName();
			searchHtml.append("<span id=\"" + attachEntity.getId() + "\" title=\"" + fileName
					+ "\">");
			searchHtml.append("<a href='" + request.getContextPath()
					+ "/common/attach!download.action?download_fileId=" + attachEntity.getId()
					+ "&instanceName=" + attachEntity.getClass().getSimpleName() + "&upload_path="
					+ uploadPath + "'>");
			if (fileName.length() > (length + 3)) {
				fileName = fileName.substring(0, length) + "...";
			}
			searchHtml.append(fileName + "." + attachEntity.getFileType());
			searchHtml.append("</a>");
			searchHtml.append("</span><br>");
		}
		try {
			pageContext.getOut().println(searchHtml.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		searchHtml = null;
		return SKIP_BODY;
	}

	public Set getFiles() {
		return files;
	}

	public void setFiles(Set files) {
		this.files = files;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
