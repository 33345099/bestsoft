package org.cl.system.resource.service;

import java.util.List;

import org.cl.system.resource.dao.SequenceDao;
import org.cl.system.resource.entity.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.cl.system.resource.service.SequenceService")
@Transactional
public class SequenceService {
	@Autowired
	private SequenceDao sequenceDao = null;

	/**
	 * 初始化监控数据
	 */
	public void initSequence() {
		List list = sequenceDao.findAll();
		if (list.isEmpty()) {
			Sequence sequence = new Sequence();
			sequence.setDocSeq(0l);
			sequenceDao.saveOrUpdate(sequence);
		}
	}
}
