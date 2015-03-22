package org.cl.common.widget.upload;

import java.io.File;

/**
 * 多文件上传辅助类集合
 * 
 * @author chenl
 * @data Nov 25, 2009
 */
public class Attaches {
	/**
	 * 附件集合
	 */
	private File[] upload = null;

	/**
	 * 附件名称集合
	 */
	protected String[] fileNames = null;

	/**
	 * 上传附件名称
	 */
	protected String[] uploadFileName = null;

	/**
	 * 上传附件类型
	 */
	protected String[] uploadContentType = null;

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String[] getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

}
