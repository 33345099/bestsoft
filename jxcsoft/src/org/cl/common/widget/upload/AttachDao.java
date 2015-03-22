package org.cl.common.widget.upload;

import java.util.List;

import org.cl.common.core.orm.AttachEntity;
import org.cl.common.core.orm.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 附件公共Dao
 * 
 * @author cl
 * 
 */
@Repository
public class AttachDao extends BaseDao {

	/**
	 * 根据主表ID删除附件
	 * 
	 * @param attachClazz
	 * @param ownerId
	 */
	public void deleteByOwnerId(Class attachClazz, Long ownerId) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from " + attachClazz.getSimpleName()
						+ " where ownerId=?", ownerId);
	}

	/**
	 * 获取附件
	 * 
	 * @param attachClazz
	 * @param ownerId
	 * @return
	 */
	public List<AttachEntity> findByOwnerId(Class attachClazz, Long ownerId) {
		return this.getHibernateTemplate().find(
				" from " + attachClazz.getSimpleName() + " where ownerId=?",
				ownerId);
	}

	public AttachEntity findAttach(String className, Long id) {
		List list = this.getHibernateTemplate().find(
				" from " + className + " where id=?", id);
		if (!list.isEmpty()) {
			return (AttachEntity) list.get(0);
		}
		return null;
	}

	/**
	 * 根据主表ID获取主表
	 * 
	 * @param attachName
	 * @param ownerId
	 * @return
	 */
	public List findByOwnerId(String attachName, Long ownerId) {
		return this.getHibernateTemplate().find(
				" from " + attachName + " where ownerId=?", ownerId);
	}

	public AttachEntity findById(String attachName, Long id) {
		List list = this.getHibernateTemplate().find(
				" from " + attachName + " where id=?", id);
		if (!list.isEmpty()) {
			return (AttachEntity) list.get(0);
		}
		return null;
	}
}
