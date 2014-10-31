package com.bestsoft.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
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


}
