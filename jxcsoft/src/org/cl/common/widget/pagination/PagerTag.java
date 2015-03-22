package org.cl.common.widget.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.cl.common.util.ServletUtils;
import org.cl.common.util.StrUtils;
import org.cl.common.widget.pagination.plugin.ATypePlugin;
import org.cl.common.widget.pagination.plugin.IPlugin;
import org.cl.common.widget.pagination.plugin.SelectPagePlugin;
import org.springframework.util.Assert;

/**
 * 分页标签类
 * 
 * @author chenl
 * 
 */
public class PagerTag extends TagSupport {
	private Boolean hasSelectPage = false;// 是否通过Select选择第几页

	private String url = null; // 分页的action地址

	// 分页其他辅助属性
	private Page page;// page对象

	private List<IPlugin> plugins = null;// 分页插件集合

	private String searchFormName = "searchFormTemp"; // 创建的用于分页的FORM 名称

	/**
	 * 注册分页插件
	 * 
	 * @param plugin
	 */
	public void registerPlugin(IPlugin plugin) {
		plugins.add(plugin);
	}

	public int doStartTag() throws JspException {
		// 取得Page对象，然后根据page对象输出分页的相关信息
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		this.page = (Page) request.getAttribute("page");
		Assert.notNull(page, "request内不包含Page对象");
		this.pageContext.setAttribute("page", page);
		// 分页功能插件注册
		plugins = new ArrayList();
		Map parsMap = new HashMap();
		parsMap.put("page", page);
		// 分页主体
		this.registerPlugin(new ATypePlugin(parsMap));
		if (hasSelectPage) {
			this.registerPlugin(new SelectPagePlugin(parsMap));
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		// 输出显示分页的代码
		StringBuffer pageHtml = new StringBuffer();
		Iterator it = plugins.iterator();
		while (it.hasNext()) {
			IPlugin p = (IPlugin) it.next();
			pageHtml.append(p.outputHtml());
		}
		try {
			// 输出分页创建的form
			pageHtml.append(createPageForm(request));
			pageContext.getOut().println(pageHtml.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 表示按照正常的流程继续执行JSP网页
		return EVAL_PAGE;
	}

	/**
	 * 用于创建分页的辅助代码，包括创建用于分页的Form，主要是从request中取出所有带_的参数，以隐藏域的形式存储值，提交的时候，
	 * 这些值又回被带回Action中， 从而参数不会丢失
	 * 
	 * @param request
	 * 
	 * @return
	 */
	private String createPageForm(HttpServletRequest request) {
		StringBuffer searchHtml = new StringBuffer();
		// 取得request所有的查询参数
		Map paramMap = ServletUtils.getParametersStartWith(request, "transfer,filter", true);
		url = url == null ? "" : url;
		searchHtml.append("<form  id='" + searchFormName + "'  method='post' action='" + url + "' style='display:none'>");
		for (Object key : paramMap.keySet()) {
			Object objectValue = paramMap.get(key);
			if (!objectValue.getClass().isArray()) {
				if (StrUtils.isNotNullOrBlank(objectValue.toString())) {
					// 将查询参数以_TEMP结尾保存到hidden元素中去
					searchHtml.append("<input type='hidden' name='" + key + "'" + " value='" + objectValue.toString() + "'>");
				}
			} else {
				String[] objectValueArray = (String[]) request.getAttribute(key.toString());
				for (int i = 0; i < objectValueArray.length; i++) {
					searchHtml.append("<input type='hidden' name='" + key + "'" + " value='" + objectValueArray[i] + "'>");
				}
			}
		}
		// 保存分页页数
		if (request.getAttribute("page") != null) {
			searchHtml.append("<input type='hidden' name='_pageNum'" + " value='" + ((Page) request.getAttribute("page")).getPageNum() + "'>");
		}
		searchHtml.append("</form>");
		// 创建JavaScript代码
		searchHtml.append("<script type='text/javascript'>\n");
		searchHtml.append("function opendataPageSubmit(){\n");
		searchHtml.append("document.getElementById('" + searchFormName + "').submit();\n");
		searchHtml.append("}\n");
		searchHtml.append("function opendataSetPageNum(num){\ndocument.getElementById('pageNum').value = num;\n}\n");
		searchHtml.append("</script>");

		searchHtml.append("<script type='text/javascript'>\n");
		searchHtml.append("var pageNumHidden=document.createElement('input');");
		searchHtml.append("pageNumHidden.type = 'hidden';");
		searchHtml.append("pageNumHidden.id='pageNum';");
		searchHtml.append("pageNumHidden.name='pageNum';");
		searchHtml.append("document.getElementById('" + searchFormName + "').appendChild(pageNumHidden);");
		searchHtml.append("</script>");
		return searchHtml.toString();
	}

	public Boolean getHasSelectPage() {
		return hasSelectPage;
	}

	public void setHasSelectPage(Boolean hasSelectPage) {
		this.hasSelectPage = hasSelectPage;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
