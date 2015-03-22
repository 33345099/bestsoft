package org.cl.common.widget.web;

/**
 * 页面元素标签基础类
 * 
 * @author chenl
 * @data 2009-7-24
 */
public class BaseSelectTag extends HtmlTag {
	/**
	 * 是否多选
	 */
	protected String multiple = null;

	/**
	 * 是否有个默认的选择框
	 */
	protected boolean hasDefault = true;

	protected String size = null;

	protected String type = null;

	protected boolean newline = false;

	/**
	 * ondblclick函数
	 */
	protected String ondblclick = null;

	protected String limit = null;

	protected String notLimit = null;

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	/**
	 * 组装公共属性集合
	 * 
	 * @param sb
	 */
	protected void loadCommonPropertys(StringBuffer sb) {
		super.loadCommonPropertys(sb);
		loadProperty(sb, "multiple", multiple);
		loadProperty(sb, "size", size);
		loadProperty(sb, "ondblclick", ondblclick);

	}

	/**
	 * 还原所有属性
	 */
	protected void initProperty() {
		super.initProperty();
		multiple = null;
		hasDefault = true;
	}

	public boolean getHasDefault() {
		return hasDefault;
	}

	public void setHasDefault(boolean hasDefault) {
		this.hasDefault = hasDefault;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getNewline() {
		return newline;
	}

	public void setNewline(boolean newline) {
		this.newline = newline;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getNotLimit() {
		return notLimit;
	}

	public void setNotLimit(String notLimit) {
		this.notLimit = notLimit;
	}
}
