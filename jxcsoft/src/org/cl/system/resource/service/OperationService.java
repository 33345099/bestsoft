package org.cl.system.resource.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cl.common.core.orm.BaseService;
import org.cl.common.util.XmlDom4j;
import org.cl.common.widget.pagination.Page;
import org.cl.system.resource.dao.OperationDao;
import org.cl.system.resource.entity.Operation;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "org.cl.system.resource.service.OperationService")
@Transactional()
public class OperationService extends BaseService {
	@Autowired
	private OperationDao operationDao = null;

	private static final Log log = LogFactory.getLog(OperationService.class);

	/**
	 * 分页查询
	 */

	public List findByPage(Page page, Map parameterMap) {
		return operationDao.findByPage(page, parameterMap);
	}

	/**
	 * 是否含有子操作
	 */

	public List<Operation> findByType(String typeCode) {
		return operationDao.findByTypeForJdbc(typeCode);
	}

	/**
	 * 根据类型获取操作
	 */

	public boolean hasChildOperation(String typeCode) {
		long childOperationSize = operationDao
				.childOperationSizeForJdbc(typeCode);
		if (childOperationSize > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void initOperation() {
		log.info("重新根据配置文件resource.xml文件生成数据库中的资源数据");
		operationDao.deleteAllOperationForJdbc();
		XmlDom4j xmlDom4j = new XmlDom4j();
		// 加载数据字典文件
		Document document = xmlDom4j.loadFile("initData/operation.xml");
		// 获取数据字典分类集合
		List<Element> list = xmlDom4j.getRootElement(document).selectNodes(
				"operation");
		Element element = null;
		// 遍历数据字典分类集合
		for (int i = 0; i < list.size(); i++) {
			Operation operation = new Operation();
			element = list.get(i);
			operation.setName(element.attributeValue("name"));
			operation.setIdentifier(element.attributeValue("identifier"));
			operationDao.saveForJdbc(operation);
		}
	}

}
