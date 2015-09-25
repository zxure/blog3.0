package zx.blog.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDateUtil {
	
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	/**
	 * 取得当前时间的时间戳
	 * @return
	 */
	public static int getCurrentTimeInt()
	{
		return (int)(System.currentTimeMillis()/1000);
	}
	
	/**
	 * 获取当前时间的时间字符串
	 */
	public static String getCurrentTimestr()
	{
		return TimeDateUtil.formatTime(new Date());
	}
	
	/**
	 * 格式化时间 为 yyyy-MM-dd hh:mm
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date)
	{
		return TIME_FORMAT.format(date);
	}
	
	/**
	 * 格式化时间 为 yyyy-MM-dd hh:mm
	 * @param timeInt
	 * @return
	 */
	public static String formatTime(int timestamp)
	{
		return TIME_FORMAT.format(TimeDateUtil.getDateFromTimestamp(timestamp));
	}
	
	/**
	 * 获得发布的时间字符串
	 * @param postTime
	 * @return
	 */
	public static String getPostTimeStr(int postTime){
		//间隔的秒数
		int postTimeGapInt = getCurrentTimeInt() - postTime;
		if(postTimeGapInt < 60){//一分以内
			return postTimeGapInt + "秒";
		} else if(postTimeGapInt < 3600){ //一小时以内 
			return (postTimeGapInt / 60) + "分";
		} else if(postTimeGapInt < 86400){ // 一天以内 
			return (postTimeGapInt / 3600) + "时" + ((postTimeGapInt % 3600)/60) + "分";
		} else {//超过了一天
			int left = postTimeGapInt % 86400;
			return (postTimeGapInt / 86400) + "天 " + (left / 3600) + "时 ";
		}
	}

	/**
	 * 时间转换为时间戳
	 * @param parameter
	 * @return
	 */
	public static int getTimestampFromDate(Date date)
	{
		return (int) (date.getTime() / 1000);
	}
	
	/**
	 * 时间戳转换为时间
	 * @param timeStamp
	 * @return
	 */
	public static Date getDateFromTimestamp(int timeStamp)
	{
		return new Date(timeStamp * 1000L);
	}
	
	/**
	 * 根据时间字符串得到时间
	 * @throws ParseException 
	 */
	public static Date getDateFromTimeStr(String timeStr)
	{
		try
		{
			return TimeDateUtil.TIME_FORMAT.parse(timeStr);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 根据时间字符串得到时间戳
	 * @param parameter
	 * @return
	 */
	public static int getTimestampFromTimestr(String timeStr)
	{
		return TimeDateUtil.getTimestampFromDate(TimeDateUtil.getDateFromTimeStr(timeStr));
	}
}
