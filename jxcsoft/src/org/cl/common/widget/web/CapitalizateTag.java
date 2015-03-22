package org.cl.common.widget.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 数字大写
 * 
 * @author cl
 * 
 */
public class CapitalizateTag extends TagSupport {
	private int number = 0;

	public int doStartTag() throws JspException {
		String value = null;
		if (number == 1) {
			value = "一";
		} else if (number == 2) {
			value = "二";
		} else if (number == 3) {
			value = "三";
		} else if (number == 4) {
			value = "四";
		} else if (number == 5) {
			value = "五";
		} else if (number == 6) {
			value = "六";
		} else if (number == 7) {
			value = "七";
		} else if (number == 8) {
			value = "八";
		} else if (number == 9) {
			value = "九";
		} else if (number == 10) {
			value = "十";
		} else if (number == 11) {
			value = "十一";
		} else if (number == 12) {
			value = "十二";
		} else if (number == 13) {
			value = "十三";
		} else if (number == 14) {
			value = "十四";
		} else if (number == 15) {
			value = "十五";
		} else if (number == 16) {
			value = "十六";
		} else if (number == 17) {
			value = "十七";
		} else if (number == 18) {
			value = "十八";
		} else if (number == 19) {
			value = "十九";
		} else if (number == 20) {
			value = "二十";
		} else if (number == 21) {
			value = "二十一";
		} else if (number == 22) {
			value = "二十二";
		} else if (number == 23) {
			value = "二十三";
		} else if (number == 24) {
			value = "二十四";
		} else if (number == 25) {
			value = "二十五";
		} else if (number == 26) {
			value = "二十六";
		} else if (number == 27) {
			value = "二十七";
		} else if (number == 28) {
			value = "二十八";
		} else if (number == 29) {
			value = "二十九";
		} else if (number == 30) {
			value = "三十";
		} else if (number == 31) {
			value = "三十一";
		} else if (number == 32) {
			value = "三十二";
		} else if (number == 33) {
			value = "三十三";
		} else if (number == 34) {
			value = "三十四";
		} else if (number == 35) {
			value = "三十五";
		} else if (number == 36) {
			value = "三十六";
		} else if (number == 37) {
			value = "三十七";
		} else if (number == 38) {
			value = "三十八";
		} else if (number == 39) {
			value = "三十九";
		} else if (number == 40) {
			value = "四十";
		}
		try {
			pageContext.getOut().println(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		value = null;
		return EVAL_PAGE;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
