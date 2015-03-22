package org.cl.system.resource.dao;

import java.util.List;

import org.cl.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

@Repository()
public class SequenceDao extends BaseDao {

	public synchronized Long setDocSeq() {
		this.getJdbcTemplate().update(" update sys_sequence set doc_seq=doc_seq+1");
		Long seq = this.getJdbcTemplate().queryForLong("select doc_seq from sys_sequence ");
		return seq;
	}

	public List findAll() {
		return this.getHibernateTemplate().find(" from Sequence ");
	}

	public Long setAttachSeq() {
		this.getJdbcTemplate().update(" update sys_sequence set attach_seq=attach_seq+1");
		Long seq = this.getJdbcTemplate().queryForLong("select attach_seq from sys_sequence ");
		return seq;
	}
}
