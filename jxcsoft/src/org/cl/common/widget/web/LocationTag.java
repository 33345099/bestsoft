package org.cl.common.widget.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author cl
 * 
 */
public class LocationTag extends TagSupport {
	private String location = null;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		StringBuffer html = new StringBuffer();
		html.append("<table class='navigation'>");
		html.append("<tr>");
		html.append("<td>");
		html.append("<img src='" + request.getContextPath() + "/images/navigation.gif' />");
		html.append("<b>当前位置:&nbsp;</b>");
		String[] locations = location.split("-");
		for (int i = 0; i < locations.length; i++) {
			if (i == locations.length - 1) {
				html.append("<span class='navigation-end'>" + locations[i] + "</span>");
			} else {
				html.append("<span class='navigation-front'>" + locations[i] + "</span>-&gt;");
			}
		}
		html.append("</td>");
		html.append("</tr>");
		html.append("</table>");
		try {
			pageContext.getOut().println(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
