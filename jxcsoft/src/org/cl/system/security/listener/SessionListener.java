/**
 * @date 2011-4-25
 */
package org.cl.system.security.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.cl.common.util.SpringTool;
import org.cl.system.security.context.ContextHolder;
import org.cl.userManager.entity.User;

/**
 * @author hlw
 *
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

	/**
	 * session销毁时将当前用户从在线列表中移除
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		if(session != null){
			User user = (User)session.getAttribute("currentUser");
			if(user != null){
				ContextHolder contextHolder = SpringTool.getSpringBean(ContextHolder.class);
				contextHolder.expireUser(user.getUsername());
			}
		}
	}

}
