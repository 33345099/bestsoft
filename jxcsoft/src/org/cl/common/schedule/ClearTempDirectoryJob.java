package org.cl.common.schedule;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.cl.common.core.SystemConstant;
import org.cl.common.util.SpringTool;

/**
 * 定时清空临时文件夹
 * @author hlw
 *
 */
public class ClearTempDirectoryJob {
	public void work(){
		String dirPath = SpringTool.getRealPath() + SystemConstant.TEMP_DIR;
		System.out.println("开始清空文件夹....................." + dirPath);
		File dir = new File(dirPath);
		if(!dir.exists()){
			System.out.println("文件夹不存在....................." + dirPath);			
			return;
		}
		if(dir.isFile()){
			dir.delete();
		}
		try {
			FileUtils.cleanDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("清空文件夹完成....................." + dirPath);
	}
}
