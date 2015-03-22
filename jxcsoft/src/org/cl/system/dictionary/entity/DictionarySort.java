package org.cl.system.dictionary.entity;

import java.util.HashSet;
import java.util.Set;

import org.cl.common.core.orm.BaseEntity;

/**
 * 数据字典分类表
 * 
 * @author chenl 2010-10-4
 */
public class DictionarySort extends BaseEntity {

	private String name;

	private String identifier;

	private String description;

	private Integer isSystem;

	private Set<Dictionary> dictionarys = new HashSet();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getDictionarys() {
		return dictionarys;
	}

	public void setDictionarys(Set dictionarys) {
		this.dictionarys = dictionarys;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}