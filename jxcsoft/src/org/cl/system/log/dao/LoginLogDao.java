package org.cl.system.log.dao;

import org.cl.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository()
public class LoginLogDao extends BaseDao {

	public void delete(Long id) {
		this.getHibernateTemplate().bulkUpdate(" delete from OperationLog where loginLogId=?", id);
		this.getHibernateTemplate().bulkUpdate(" delete from LoginLog where id=?", id);
	}

}
