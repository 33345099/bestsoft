/**
 * @date 2011-5-5
 */
package org.cl.system.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cl.common.util.DateUtils;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * @author hlw
 *
 */
public class KeyManager {
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";
	private String fileName = "initData/sys.ini";
	private int totalDay = 0;
	private Date startDay;
	private String originalCode = "";

	public KeyManager(){
		this.readInfo();
	}

	public boolean regedit(String code){
		if(code.equals(originalCode)){
			//TODO
			//注册成功!
			this.totalDay = -1;
			this.writeInfo();
			return true;
		}
		return false;
	}

	/**
	 * 是否已注册
	 * @return
	 */
	public boolean isRegedited(){
		return this.totalDay == -1;
	}

	/**
	 * 判断是否过期
	 * @return
	 */
	public boolean isOutOfDate(){
		if(this.isRegedited()){
			//已注册!
			return false;
		}
		float passedDays = DateUtils.daysBetween(startDay, new Date());
		if(passedDays >= 0 && passedDays < totalDay){
			return false;
		}
		return true;
	}

	private File getFile() throws Exception{
		return new DefaultResourceLoader().getResource("classpath:/" + fileName).getFile();
	}

	private void readInfo(){
		try {
			BufferedReader in = new BufferedReader(new FileReader(getFile()));
			String totalDayStr = in.readLine();
			originalCode = in.readLine();
			String startDayStr = in.readLine();
			in.close();
			totalDay = Integer.parseInt(totalDayStr);
			if(startDayStr == null){
				startDay = new Date();
				writeInfo();
			}else{
				startDay = new SimpleDateFormat(dateFormat).parse(startDayStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeInfo(){
		try {
			String startDayStr = new SimpleDateFormat(dateFormat).format(startDay);
			BufferedWriter out = new BufferedWriter(new FileWriter(getFile()));
			out.append(String.valueOf(totalDay));
			out.newLine();
			out.append(originalCode);
			out.newLine();
			out.append(startDayStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
