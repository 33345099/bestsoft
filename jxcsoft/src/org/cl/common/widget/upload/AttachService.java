package org.cl.common.widget.upload;

import java.io.File;
import java.util.List;

import org.cl.common.core.orm.AttachEntity;
import org.cl.common.core.orm.BaseService;
import org.cl.common.util.FileUtil;
import org.cl.system.dictionary.util.DictionaryInterface;
import org.cl.system.resource.dao.SequenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 附件实体Service
 * 
 * @author chenl
 * 
 */
@Service(value = "org.cl.common.widget.upload.AttachService")
@Transactional
public class AttachService extends BaseService {

	@Autowired
	private AttachDao attachDao = null;

	@Autowired
	private SequenceDao sequenceDao = null;

	public void deleteIds(String attachName, String uploadPath, String ids) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			AttachEntity attachEntity = attachDao.findById(attachName,
					new Long(idArray[i]));
			String filePath = uploadPath + "/" + attachEntity.getFilePath();
			FileUtil.deleteFile(filePath);
			attachDao.delete(attachEntity);
		}
	}

	/**
	 * 上传附件
	 * 
	 * @param attachClazz
	 * @param attaches
	 * @param ownerId
	 * @param uploadPath
	 * @param isDelete
	 * @throws Exception
	 */
	public void uploadFiles(Class attachClazz, Attaches attaches, Long ownerId,
			String uploadPath, String filePathPrefix, boolean isDelete)
			throws Exception {
		// 获取附件及上传附件名称
		File[] uploads = attaches.getUpload();
		String[] fileNames = attaches.getFileNames();
		// 硬盘上传文件并数据库保存附件
		// 是否删除原有附件
		if (isDelete) {
			List<AttachEntity> oldAttachList = attachDao.findByOwnerId(
					attachClazz, ownerId);
			for (int i = 0; i < oldAttachList.size(); i++) {
				attachDao.delete(oldAttachList.get(i));
				String filePath = uploadPath + "/"
						+ oldAttachList.get(i).getFilePath();
				FileUtil.deleteFile(filePath);
			}
		}
		if (null != uploads && uploads.length >= 1) {

			for (int i = 0; i < uploads.length; i++) {
				File file = uploads[i];
				if (file.exists()) {
					// 获取附件类型
					String fileType = fileNames[i].substring(fileNames[i]
							.lastIndexOf(".") + 1, fileNames[i].length());
					// 获取文件类型名称
					String fileTypeName = DictionaryInterface.findNameByCode(
							fileType.toLowerCase(), "file_type");
					if (null == fileTypeName) {
						fileTypeName = fileType;
					}
					// 获取文件原名称
					String fileName = FileUtil.getFileName(fileNames[i]);
					// 获取文件新名称
					String newFileName = FileUtil.createNewFileName(sequenceDao
							.setAttachSeq(), fileNames[i]);
					// 获取文件大小
					int fileSize = FileUtil.createNewFile(file, newFileName,
							uploadPath);
					// 创建新附件实体对象
					AttachEntity attachEntity = (AttachEntity) attachClazz
							.newInstance();
					attachEntity.setFileName(fileName);
					attachEntity.setFileType(fileType);
					attachEntity.setFileTypeName(fileTypeName);
					if (null != filePathPrefix) {
						attachEntity.setFilePath(filePathPrefix + "/"
								+ newFileName);
					} else {
						attachEntity.setFilePath(newFileName);
					}
					attachEntity.setFileSize(fileSize / (1000.0 * 1024.0));// 设置附件大小
					attachEntity.setOwnerId(ownerId);
					// 保存附件
					attachDao.saveOrUpdate(attachEntity);

				}
			}
		}
	}

	/**
	 * 获取附件
	 * 
	 * @param className
	 * @param fileId
	 * @return
	 */
	public AttachEntity findAttach(String className, Long fileId) {
		AttachEntity attachEntity = attachDao.findAttach(className, fileId);
		return attachEntity;
	}

	/**
	 * 根据主表ID获取主表
	 * 
	 * @param attachName
	 * @param ownerId
	 * @return
	 */
	public List findByOwnerId(String attachName, Long ownerId) {
		return attachDao.findByOwnerId(attachName, ownerId);
	}

}
