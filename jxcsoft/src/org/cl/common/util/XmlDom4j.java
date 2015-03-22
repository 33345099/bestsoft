package org.cl.common.util;

import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * dom4j工具类
 * 
 * @author chenl
 * 
 */
public class XmlDom4j {
	private Document document = null;

	/**
	 * 读取XML文件
	 */
	public Document loadFile(String fileName) {
		try {
			// 从类路径读取配置文件。
			ClassLoader cl = XmlDom4j.class.getClassLoader();
			// 加载数据字典分类初始化文件
			InputStream stream = cl.getResourceAsStream(fileName);
			SAXReader reader = new SAXReader();
			document = reader.read(stream);
			stream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * 取得Root节点
	 * 
	 * @param doc
	 * @return
	 */
	public Element getRootElement(Document doc) {
		return doc.getRootElement();
	}
}
