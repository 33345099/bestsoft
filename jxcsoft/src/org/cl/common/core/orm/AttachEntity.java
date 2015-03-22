package org.cl.common.core.orm;

/**
 * 附件基础实体类
 * 
 * @author chenl
 * @data Nov 24, 2009
 */
public class AttachEntity extends BaseEntity {

	protected static String uploadPath = null;

	private Long ownerId = null;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件地址
	 */
	private String filePath;

	/**
	 * 附件类型
	 */
	private String fileType;

	/**
	 * 附件类型
	 */
	private String fileTypeName;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 附件说明信息
	 */
	private String description;

	/**
	 * 文件大小,单位为K
	 */
	private Double fileSize;

	/**
	 * 待上传的文件对象
	 */
	private byte[] attach;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getAttach() {
		return attach;
	}

	public void setAttach(byte[] attach) {
		this.attach = attach;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
}
