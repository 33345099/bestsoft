package org.cl.common.widget.pagination.plugin;

import java.util.Map;

import org.cl.common.widget.pagination.Page;


/**
 * 基于"首页 上一页 下一页 末页"的分页风格
 * 
 * @author chenl 
 * 
 */

public class ATypePlugin extends BasePlugin {

	public ATypePlugin(Map parsMap) {
		super(parsMap);
	}

	public String outputHtml() {
		Page page = (Page) parsMap.get("page");
		StringBuffer sb = new StringBuffer();
		sb.append("总共" + page.getTotalCount() + "个记录<span style=\"margin-left:2px;\">当前" + page.getPageNum()
				+ "/" + page.getTotalPageCount() + "页</span>");
		// 首页输出
		if (page.isHasFirst()) {
			sb.append("<a href='#' style='color:#000000;text-decoration:none' onclick='opendataSetPageNum(1);opendataPageSubmit();return false;'>");
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\"><font color='blue'>首页</font></span>");
			sb.append("</a>");
		} else {
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\">首页</span>");
		}

		// 上一页输出
		if (page.isHasPrevious()) {
			int prPage = page.getPageNum() - 1;
			sb.append("<a href='#' style='color:#000000;text-decoration:none' onclick='opendataSetPageNum(" + prPage
					+ ");opendataPageSubmit();return false;'>");
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\"><font color='blue'>上一页</font></span>");
			sb.append("</a>");
		} else {
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\">上一页</span>");
		}

		// 下一页输出
		if (page.isHasNext()) {
			int nextPage = page.getPageNum() + 1;
			sb.append("<a href='#' style='color:#000000;text-decoration:none' onclick='opendataSetPageNum(" + nextPage
					+ ");opendataPageSubmit();return false;'>");
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\"><font color='blue'>下一页</font></span>");
			sb.append("</a>");
		} else {
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\">下一页</span>");
		}

		// 末页
		if (page.isHasLast()) {
			int lastPage = page.getTotalPageCount();
			sb.append("<a href='#' style='color:#000000;text-decoration:none' onclick='opendataSetPageNum(" + lastPage
					+ ");opendataPageSubmit();return false;'>");
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\"><font color='blue'>末页</font></span>");
			sb.append("</a>");
		} else {
			sb.append("&nbsp;&nbsp;<span style=\"margin-left:2px;\">末页</span>");
		}
		return sb.toString();
	}
}
