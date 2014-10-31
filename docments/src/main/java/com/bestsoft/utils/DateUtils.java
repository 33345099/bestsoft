package com.bestsoft.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {
	/**
	 * 私有化构造器.
	 */
	private DateUtils(){
		
	}
	/**
	 * 两个日期比较.
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public static int compareDate(Date dateOne,Date dateTwo){
		long diffvalue = dateTwo.getTime()-dateOne.getTime();
		int result = diffvalue>0?1:diffvalue==0?0:-1;
		return result;
		
	}
	/**
	 * 在现有日期上加减天数
	 * @param baseDate
	 * @param day
	 * @return
	 */
	public static Date dateAddDay(Date baseDate,int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	
	/**
	 * 在现有日期上加减天数
	 * @param baseDate
	 * @param day
	 * @return
	 */
	public static Date dateBeforeDay(Date baseDate,int day){
		return dateAddDay(baseDate, day*-1);
	}
	
	public static String formatDateString(Date d){
		return formatDateString(d, null);
	}
	
	public static String formatDateString(Date d,String formateString){
		if(StringUtils.isEmpty(formateString)){
			formateString = "YYYY-MM-DD";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formateString);
	    return sdf.format(d);
	}


}
