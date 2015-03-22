package org.cl.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * 
 * @author chenl
 * 
 */
public class DateUtils {
	public static Calendar cal = Calendar.getInstance();
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 返回指定格式的字符串日期
	 * 
	 * @param date
	 *            日期 允许NULL,为NULL时返回空字符
	 * @param format
	 *            返回的字符串日期格式
	 * @return
	 */
	public static String toStr(Date date, String format) {
		String dateStr = null;
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			dateStr = simpleDateFormat.format(date);
		}
		return dateStr;
	}

	public static String LongToStr(Long dateLong, String oldFormat,
			String newFormat) {
		String dateStr = null;
		try {
			Date date = convertDate(dateLong.toString(), oldFormat);
			dateStr = toStr(date, newFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	public static Integer strDateToIntegerDate(String start, String oldFormat,
			String newFormat) {
		String dateStr = null;
		try {
			Date date = convertDate(start, oldFormat);
			dateStr = toStr(date, newFormat);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return new Integer(dateStr);
	}

	/**
	 * 将英文格式的时间字符串转换为中文格式
	 * 
	 * @param enDateStr
	 * @param format
	 * @return
	 */
	public static String enDateStrToZhDateStr(String enDateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE MMM d HH:mm:ss Z SSS yyyy", Locale.US);
		Date date = null;
		try {
			date = sdf.parse(enDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dateStr = DateUtils.toStr(date, format);
		return dateStr;
	}

	/**
	 * 根据字符串返回指定格式的日期
	 * 
	 * @param dateStr
	 *            日期(字符串)
	 * @param format
	 *            日期格式
	 * @return 日期(Date)
	 * @throws ParseException
	 */
	public static Date convertDate(String dateStr, String format)
			throws ParseException {
		java.util.Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		date = simpleDateFormat.parse(dateStr);
		return date;
	}

	public static String format(String dateStr, String format)
			throws ParseException {
		Date date = convertDate(dateStr, format);
		return toStr(date, format);
	}

	/**
	 * 得到当月第一天和最后一天
	 * @param date
	 * @param type 0 得到第一天  1 得到最后一天
	 */
	@SuppressWarnings("static-access")
	public static Date getCurrentMonthEndDay(Date date,int type){ 
		   Calendar cal = Calendar.getInstance(); //日历 
		   cal.setTime(date);
		   if(type==0){
			   cal.set(cal.DATE, 1); 
			   return cal.getTime();
		   }else{
			   // 当前月＋1，即下个月 
			   cal.add(cal.MONTH, 1); //得到下个月的月份 
			   // 将下个月1号作为日期初始
			   cal.set(cal.DATE, 1); 
			   // 下个月1号减去一天，即得到当前月最后一天 
			   cal.add(cal.DATE, -1); 
			   return cal.getTime();
		   }
		} 

	/**
	 * 小时的变动
	 * 
	 * @param hour
	 * @return
	 */
	public static Date minuteChange(Date date, Integer minute) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 小时的变动
	 * 
	 * @param hour
	 * @return
	 */
	public static Date hourChange(Date date, Integer hour) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	/**
	 * 天的变动
	 * 
	 * @param hour
	 * @return
	 */
	public static Date dayChange(Date date, Integer day) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.DAY_OF_WEEK, day);
		return calendar.getTime();
	}

	/**
	 * 月的变动
	 * 
	 * @param hour
	 * @return
	 */
	public static Date monthChange(Date date, Integer month) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 年的变动
	 * 
	 * @param hour
	 * @return
	 */
	public static Date yearChange(Date date, Integer year) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(java.util.Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 获取年
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date) {
		if (null == date) {
			return null;
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取月
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		if (null == date) {
			return null;
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getDay(Date date) {
		if (null == date) {
			return null;
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	public static Long toLong(Date date, String format) {
		if (null == date) {
			return null;
		}
		return new Long(DateUtils.toStr(date, format));
	}

	public static Long toLong(Date date) {
		if (null == date) {
			return null;
		}
		return new Long(DateUtils.toStr(date, "yyyyMMdd"));
	}
	/**
	 * 计算两个日期间的天数，d1早于d2返回正数，否则返回负数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static float daysBetween(Date d1, Date d2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		float day = (c2.getTimeInMillis() - c1.getTimeInMillis())/(60f * 60 * 24 * 1000);
		return day;
	}
}
