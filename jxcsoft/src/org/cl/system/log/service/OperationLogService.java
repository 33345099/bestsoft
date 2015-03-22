package org.cl.system.log.service;

import org.cl.common.core.orm.BaseService;
import org.cl.system.log.dao.OperationLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.cl.system.log.service.OperationLogService")
@Transactional()
public class OperationLogService extends BaseService {

	@SuppressWarnings("unused")
	@Autowired
	private OperationLogDao operationLogDao = null;
}
