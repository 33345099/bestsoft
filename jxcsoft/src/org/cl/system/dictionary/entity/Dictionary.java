package org.cl.system.dictionary.entity;

import org.cl.common.core.orm.BaseEntity;

/**
 * 数据字典实体类
 * 
 * @author chenl
 * @Apr 13, 2009
 */
public class Dictionary extends BaseEntity {

	/**
	 * 数据字典分类ID
	 */
	private Long dictionarySortId;

	/**
	 * 数据字典分类标识符
	 */
	private String identifier;

	private DictionarySort dictionarySort = null;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 排序
	 */
	private int orderCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDictionarySortId() {
		return this.dictionarySortId;
	}

	public void setDictionarySortId(Long dictionarySortId) {
		this.dictionarySortId = dictionarySortId;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

	public DictionarySort getDictionarySort() {
		return dictionarySort;
	}

	public void setDictionarySort(DictionarySort dictionarySort) {
		this.dictionarySort = dictionarySort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}