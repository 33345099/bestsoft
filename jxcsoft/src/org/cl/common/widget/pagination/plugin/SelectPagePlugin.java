package org.cl.common.widget.pagination.plugin;

import java.util.Map;

import org.cl.common.widget.pagination.Page;

/**
 * 提供通过Select选择第几页的分页功能
 * 
 * @author chenl
 * 
 */

public class SelectPagePlugin extends BasePlugin {

	public SelectPagePlugin(Map parsMap) {
		super(parsMap);
	}

	public String outputHtml() {
		Page page = (Page) parsMap.get("page");
		StringBuffer sb = new StringBuffer();
		sb.append("第 ");
		sb
				.append("<select name=\"select\" style=\"font-size: 10px;\" onchange=\"opendataSetPageNum(this.value);opendataPageSubmit();\">");
		for (int i = 0; i < page.getTotalPageCount(); i++) {
			int pageNumTemp = i + 1;
			if (page.getPageNum() == (i + 1)) {
				sb.append("<option value=\"" + pageNumTemp + "\" selected />");
			} else {
				sb.append("<option value=\"" + pageNumTemp + "\"/>");
			}
			sb.append(i + 1);
			sb.append("</option>");
		}
		sb.append("</select>");
		sb.append(" 页");
		return sb.toString();
	}
}
