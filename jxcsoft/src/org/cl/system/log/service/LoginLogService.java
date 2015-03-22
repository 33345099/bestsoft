package org.cl.system.log.service;

import org.cl.common.core.orm.BaseService;
import org.cl.system.log.dao.LoginLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.cl.system.log.service.LoginLogService")
@Transactional()
public class LoginLogService extends BaseService {

	@SuppressWarnings("unused")
	@Autowired
	private LoginLogDao loginLogDao = null;
}
