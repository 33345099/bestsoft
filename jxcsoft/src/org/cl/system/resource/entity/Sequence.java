package org.cl.system.resource.entity;

import org.cl.common.core.orm.BaseEntity;

public class Sequence extends BaseEntity {
	private Long docSeq = null;

	public Long getDocSeq() {
		return docSeq;
	}

	public void setDocSeq(Long docSeq) {
		this.docSeq = docSeq;
	}

}
