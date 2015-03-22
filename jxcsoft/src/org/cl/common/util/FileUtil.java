package org.cl.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class FileUtil {

	/**
	 * 常见图片文件类型
	 */
	public static final Set<String> IMAGE_CONTENT_TYPE = new HashSet<String>();
	static{
		IMAGE_CONTENT_TYPE.add("image/png");
		IMAGE_CONTENT_TYPE.add("image/x-png");
		IMAGE_CONTENT_TYPE.add("image/gif");
		IMAGE_CONTENT_TYPE.add("image/jpeg");
		IMAGE_CONTENT_TYPE.add("image/pjpeg");
		IMAGE_CONTENT_TYPE.add("image/jpg");
	}

	/**
	 * 读取文件, 保存到字节数组中<br><font color="red">一次性读出整个文件, 只适合对较小文件操作!</font>
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] file2ByteArray(File file){
		if(null == file || !file.exists()){
			throw new IllegalArgumentException("文件不能为空!");
		}
		FileInputStream in = null;
		byte[] b = null;
		try {
			in = new FileInputStream(file);
			b = new byte[in.available()];
			in.read(b);
		} catch (Exception e) {
			throw new RuntimeException("读写文件时出错!", e);
		}finally{
			if(in!=null){				
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return b;
	}

	/**
	 * 将字节数组中的数据写入文件中<br><font color="red">一次写入所有字节, 只适合对较小文件操作</font><br><font color="violet">如果文件已存在, 则不写</font>
	 * @param bytes
	 * @param filePath 文件完整路径, 包括文件路径和文件名
	 * @return
	 * @throws IOException
	 */
	public static File byteArray2File(byte[] bytes, String filePath){
		if(null == bytes || StringUtils.isBlank(filePath)){
			throw new IllegalArgumentException("字节和文件路径不能为空!");
		}
		File file = new File(filePath);
		if(file.exists()){
			//如果已存在, 则不写
			return file;
		}
		FileOutputStream out = null;
		try {
			makeDir(file.getAbsolutePath().replace(file.getName(), ""));
			file.createNewFile();
			out = new FileOutputStream(file);
			out.write(bytes);
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException("读写文件时出错!", e);
		}finally{
			if(out != null){				
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return file;
	}

	/**
	 * 根据给定路径创建文件夹
	 * 
	 * @param filepath
	 */
	public static void makeDir(String filepath)
	{
		File dir = new File(filepath);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}

	/**
	 * 判断文件类型是否包含
	 * @param contentType 文件类型
	 * @param contentTypes 允许的文件类型
	 * @return
	 */
	public static boolean isAllowedContentType(String contentType, Collection<String> contentTypes){
		if(null == contentType){
			return false;
		}
		return contentTypes.contains(contentType.toLowerCase());
	}

	public static String generateFileName(String srcName){
		String suffix = ".temp_";
		int index = srcName.lastIndexOf(".");
		if(index!=-1){
			suffix = srcName.substring(index);
		}
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(100) + suffix;
		return fileName;
	}

	/**
	 * 创建新文件并且保存
	 * 
	 * @param src
	 * @return
	 */
	public static int createNewFile(File src, String newFileName,
			String uploadPath) {
		String fileDir = ServletActionContext.getServletContext().getRealPath(
				"/" + uploadPath)
				+ "/";
		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File dst = new File(dir, newFileName);
		InputStream is = null;
		OutputStream os = null;
		int fileSize = 0;
		try {
			is = new BufferedInputStream(new FileInputStream(src));
			os = new BufferedOutputStream(new FileOutputStream(dst));
			fileSize = is.available();
			byte buffer[] = new byte[8192];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileSize;
	}

	/**
	 * 删除物理文件
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 获取附件原名陈
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		if (fileName.indexOf(".") >= 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}

	/**
	 * 创建新名称
	 * 
	 * @param seq
	 * @param fileNames
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String createNewFileName(Long seq, String fileNames) {
		Calendar now = Calendar.getInstance();
		String newFileName = String.valueOf(now.get(now.YEAR))
				+ String.valueOf(now.get(now.MONTH) + 1)
				+ String.valueOf(now.get(now.DATE));
		newFileName += String.valueOf(now.get(now.HOUR))
				+ String.valueOf(now.get(now.MINUTE))
				+ String.valueOf(now.get(now.SECOND));
		newFileName = newFileName + "-" + seq + "-" + fileNames;
		return newFileName;
	}
}
