package org.cl.system.security.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts2.ServletActionContext;
import org.cl.common.util.StrUtils;
import org.cl.system.resource.entity.Operation;
import org.cl.userManager.entity.User;

/**
 * 权限标签
 * 
 * @author chenl
 * @data Jan 22, 2010
 */
public class SecurityTag extends TagSupport {
	private String tag = null;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int doStartTag() throws JspException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("currentUser");
		boolean hasPass = hasSecurity(user, tag);
		if (hasPass) {
			return EVAL_BODY_INCLUDE;
		}
		tag = null;
		return SKIP_BODY;
	}

	public static boolean hasSecurity(User user, String identifier) {
		if (user.getType().equals("0")) {
			return true;
		}
		List<Operation> list = user.getOperations();
		if (StrUtils.isNullOrBlank(identifier)) {
			return true;
		}

		String[] identifierArray = identifier.trim().split(",");
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				Operation operation = list.get(i);
				for (int j = 0; j < identifierArray.length; j++) {
					if (operation.getIdentifier().equals(
							identifierArray[j].trim())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
