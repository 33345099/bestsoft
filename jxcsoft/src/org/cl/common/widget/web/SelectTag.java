package org.cl.common.widget.web;

import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.jsp.JspException;

import org.cl.common.util.StrUtils;

/**
 * 下拉框Tag
 * 
 * @author chenl
 * @Apr 1, 2010
 */
public class SelectTag extends BaseSelectTag {

	/**
	 * 集合
	 */
	private List items = null;

	/**
	 * ID显示名称
	 */
	private String idTag = null;

	/**
	 * 内容显示名称
	 */
	private String nameTag = null;

	public int doStartTag() throws JspException {
		// Assert.notNull(items,"集合不为空");
		if (StrUtils.isNullOrBlank(idTag)) {
			idTag = "id";
		}
		if (StrUtils.isNullOrBlank(nameTag)) {
			nameTag = "name";
		}
		html = new StringBuffer();
		Method method = null;
		Object object = null;
		String idValue = null;
		String nameValue = null;
		html.append("<select ");
		super.loadCommonPropertys(html);
		html.append(">");
		if (hasDefault) {
			html.append("<option value='-1'></option>");
		}
		if (null != items) {
			for (int i = 0; i < items.size(); i++) {
				String isSelect = "";
				object = items.get(i);
				// 获取select ID值
				try {
					method = object.getClass().getMethod(
							"get" + idTag.substring(0, 1).toUpperCase()
									+ idTag.substring(1));
					idValue = method.invoke(object) != null ? method.invoke(
							object).toString() : null;
					if (StrUtils.isNotNullOrBlank(limit)) {
						if (limit.indexOf(idValue) < 0) {
							continue;
						}
					}
					method = object.getClass().getMethod(
							"get" + nameTag.substring(0, 1).toUpperCase()
									+ nameTag.substring(1));
					nameValue = method.invoke(object) != null ? method.invoke(
							object).toString() : null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 判断是否默认选中
				if (value != null) {
					if (null != multiple && multiple.equals("multiple")) {
						String[] valueArray = value.split(",");
						for (int j = 0; j < valueArray.length; j++) {
							if (valueArray[j].equals(idValue)) {
								isSelect = "selected";
							}
						}
					} else {
						if (value.equals(idValue)) {
							isSelect = "selected";
						}
					}
				}
				html.append("<option value=\"" + idValue + "\" ");
				if (StrUtils.isNotNullOrBlank(isSelect)) {
					html.append(isSelect);
				}
				html.append(">");
				html.append(nameValue);
				html.append("</option>");
			}
		}
		html.append("</select>");
		try {
			pageContext.getOut().println(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.initProperty();
		items = null;
		idTag = null;
		nameTag = null;
		return EVAL_PAGE;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public String getIdTag() {
		return idTag;
	}

	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}

	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}

}
