/**
 * @date 2011-4-25
 */
package org.cl.system.security.context;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存系统运行时的信息
 * @author hlw
 *
 */
public class ContextHolder {
	/**
	 * 保存在线用户的集合
	 * key username, value sessionId
	 */
	private Map<String, String> onlineUsersSession;
	private Map<String, Date> onlineUsersTime;

	public ContextHolder(){
		this.onlineUsersSession = Collections.synchronizedMap(new HashMap<String, String>());
		this.onlineUsersTime = Collections.synchronizedMap(new HashMap<String, Date>());
	}

	/**
	 * 获取在线用户列表
	 */
	public HashMap<String, Date> getUserList(){
		return new HashMap<String, Date>(onlineUsersTime);
	}

	/**
	 * 将登陆用户添加到在线列表
	 * @param username 用户标识
	 * @param sessionId sessionID
	 * @return true 列表中原先已存在, false 原先不存在
	 */
	public boolean addUser(String username, Date time, String sessionId){
		boolean alreadExist = this.onlineUsersSession.containsKey(username);
		this.onlineUsersSession.put(username, sessionId);
		this.onlineUsersTime.put(username, time);
		return alreadExist;
	}

	/**
	 * 将用户从在线列表中移除
	 * @param username 用户标识
	 * @return true 存在, false 不存在
	 */
	public boolean expireUser(String username){
		boolean exist = this.onlineUsersSession.containsKey(username);
		if(exist){			
			this.onlineUsersSession.remove(username);
			this.onlineUsersTime.remove(username);
		}
		return exist;
	}
}
