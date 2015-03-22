package org.cl.common.exception;

/**
 * 导入页面错误展现
 * 
 * @author chenl
 * @data Feb 1, 2010
 */
@SuppressWarnings("serial")
public class ImportViewException extends RuntimeException {
	private String msg = null;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ImportViewException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ImportViewException() {
		super();
	}
}
