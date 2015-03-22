package org.cl.common.core.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.cl.common.util.HttpTool;
import org.cl.common.util.ServletUtils;
import org.cl.common.util.SpringTool;
import org.cl.common.widget.pagination.Page;
import org.cl.system.dictionary.service.DictionaryService;
import org.cl.system.dictionary.service.DictionarySortService;
import org.cl.userManager.service.OrganizationService;
import org.cl.userManager.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	protected UserService userService = (UserService) SpringTool
			.getBean(UserService.class);

	protected OrganizationService organizationService = (OrganizationService) SpringTool
			.getBean(OrganizationService.class);

	protected DictionaryService dictionaryService = (DictionaryService) SpringTool
			.getBean(DictionaryService.class);

	protected DictionarySortService dictionarySortService = (DictionarySortService) SpringTool
			.getBean(DictionarySortService.class);

	protected Long id = null;

	protected String ids = null;

	protected Page page = null;

	protected String alert = "操作成功！";

	protected String jumpUrl = null;

	protected String jsonString = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getJsonString() {
		return jsonString;
	}

	/**
	 * 获取查询参数(查询参数以filter开始)
	 * 
	 * @return 查询参数
	 */
	public Map buildSearch() {
		Map<String, String> filterParamMap = ServletUtils
				.getParametersStartWith(ServletActionContext.getRequest(),
						"filter", false);
		filterParamMap.putAll(ServletUtils.getParametersStartWith(
				ServletActionContext.getRequest(), "_", false));
		return filterParamMap;
	}

	/**
	 * 将其他参数转换为查询参数
	 * 
	 * @param searchMap
	 *            查询参数封装MAP
	 * @param relationalOperator
	 *            运算关系
	 * @param operationType
	 *            运算类型
	 * @param propertyType
	 *            属性类型
	 * @param paramName
	 *            参数名称
	 * @param propertyName
	 *            属性名称
	 */
	public void transferBuildSearch(Map searchMap, String relationalOperator,
			String operationType, String propertyType, String paramName,
			String propertyName) {
		String value = ServletActionContext.getRequest()
				.getParameter(paramName);
		buildSearch(searchMap, relationalOperator, operationType, propertyType,
				propertyName, value);
	}

	public void buildSearch(Map searchMap, String relationalOperator,
			String operationType, String propertyType, String propertyName,
			Object propertyValue) {
		searchMap.put("filter_" + relationalOperator + "_" + propertyName + "_"
				+ operationType + "_" + propertyType, propertyValue);
	}

	public void buildSearch(Map searchMap, String parameter,
			Object propertyValue) {
		searchMap.put(parameter, propertyValue);
	}

	/**
	 * json处理函数
	 * 
	 * @param jsonList
	 * @return
	 */
	public String json(List jsonList) {
		JSONArray array = JSONArray.fromObject(jsonList);
		this.jsonString = array.toString();
		return "json";
	}

	/**
	 * 设置跳转页面
	 * 
	 * @param jumpUrl
	 * @return
	 */
	public String jump(String jumpUrl) {
		this.jumpUrl = jumpUrl;
		return "jump";
	}

	/**
	 * 设置跳转页面
	 * 
	 * @param jumpUrl
	 * @return
	 */
	public String jump(String jumpUrl, String alert) {
		this.alert = alert;
		this.jumpUrl = jumpUrl;
		return "jump";
	}

	/**
	 * 判断是否跳转到portal页面
	 * 
	 * @param listUrl
	 * @param portalUrl
	 * @return
	 */
	public String portal(String listUrl, String portalUrl) {
		if (HttpTool.getBooleanParameter("portal", false)) {
			return portalUrl;
		}
		return listUrl;
	}

	/**
	 * 刷新父页面
	 * 
	 * @throws Exception
	 */
	public String setParentForJump(String jump) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('操作成功');parent.location.href='" + jump
				+ "'</script>");
		out.flush();
		out.close();
		return null;
	}
}
