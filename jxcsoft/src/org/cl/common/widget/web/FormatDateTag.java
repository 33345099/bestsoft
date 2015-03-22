package org.cl.common.widget.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.cl.common.util.DateUtils;
import org.cl.common.util.StrUtils;

public class FormatDateTag extends TagSupport {
	private Long value = null;

	public int doStartTag() throws JspException {
		String html = null;
		if (StrUtils.isNullOrBlank(value) || value.compareTo(0l) == 0) {
			html = "";
		} else {
			html = value.toString();
			if (html.length() == 12) {
				html = DateUtils.LongToStr(value, "yyyyMMddHHmm",
						"yyyy-MM-dd HH:mm");
			} else if (html.length() == 8) {
				html = DateUtils.LongToStr(value, "yyyyMMdd", "yyyy-MM-dd");
			}
		}
		try {
			pageContext.getOut().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		value = null;
		return EVAL_PAGE;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
