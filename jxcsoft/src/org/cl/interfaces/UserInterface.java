package org.cl.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.cl.common.util.SpringTool;
import org.cl.common.util.StrUtils;
import org.cl.userManager.entity.Organization;
import org.cl.userManager.entity.User;
import org.cl.userManager.service.OrganizationService;
import org.cl.userManager.service.UserService;

/**
 * 用户接口
 * 
 * @author cl
 * 
 */
public class UserInterface {

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public static User getCurentUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		return user;
	}

	/**
	 * 根据职位和分公司获取用户集合
	 * 
	 * @param filialeId
	 * @param duty
	 * @return
	 */
	public static List getByOrgAndDuty(Long orgId, String duty) {
		UserService userService = (UserService) SpringTool
				.getBean(UserService.class);
		OrganizationService organizationService = (OrganizationService) SpringTool
				.getBean(OrganizationService.class);
		Organization organization = (Organization) organizationService
				.findById(orgId);
		if ("filiale".equals(organization.getTypeCode())) {
			orgId = organization.getFilialeId();
			return userService.getByFilialeAndDuty(orgId, duty);
		} else {
			return userService.getByOrgAndDuty(orgId, duty);
		}
	}

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public static List getUserIdList(String idStr) {
		if (StrUtils.isNullOrBlank(idStr)) {
			return null;
		}
		UserService userService = (UserService) SpringTool
				.getBean(UserService.class);
		List<String> userIdlist = new ArrayList();
		String[] ids = idStr.split(",");
		List<Long> idList = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			boolean flag = true;
			if (ids[i].startsWith("user")) {
				for (int k = 0; k < userIdlist.size(); k++) {
					if (userIdlist.get(k).equals(ids[i].split("-")[1])) {
						flag = false;
						break;
					}
				}
				if (flag) {
					userIdlist.add(ids[i].split("-")[1]);
				}
			} else if (ids[i].startsWith("org")) {
				if (ids[i].split("-")[1].equals("17")) {
					idList = userService.getByFiliale(new Long(ids[i]
							.split("-")[1]));
				} else {
					idList = userService.findUserIdByOrgId(new Long(ids[i]
							.split("-")[1]));
				}

				for (int k = 0; k < idList.size(); k++) {
					for (int h = 0; h < userIdlist.size(); h++) {
						if (userIdlist.get(h).equals(idList.get(k).toString())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						userIdlist.add(idList.get(k).toString());
					}
				}
			} else {
				userIdlist.add(ids[i]);
			}
		}
		return userIdlist;
	}

	/**
	 * 某些页面，特定的工种只能查看自己的数据
	 * 
	 * @param searchMap
	 */
	public static void securityDuty(Map searchMap, String prefix) {
		User user = UserInterface.getCurentUser();
		if (StrUtils.isNotNullOrBlank(prefix)) {
			prefix = prefix + "__";
		} else {
			prefix = "";
		}
		if ("stylist".equals(user.getDutyCode())) {
			searchMap.put("filter_and_" + prefix + "stylistId_EQ_L", user
					.getId());
		}
	}
}
