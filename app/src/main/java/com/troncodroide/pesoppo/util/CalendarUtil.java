package com.troncodroide.pesoppo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.util.Log;

public class CalendarUtil {
	public static String patternDate  = "yyyy-MM-dd";
	public static String patternTime  = "HH:mm:ss.SSS";
	
	public static Calendar parseDateString(String dateString,String pattern) throws ParseException{
		Calendar c =  Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    c.setTime(sdf.parse(dateString));// all done
		return c;
	}
	public static String getDateString(Calendar c, String pattern){
		String toRet = "";
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		if (pattern.contentEquals(patternDate)){
			toRet+=year+"-"+month+"-"+day;
		}else if (pattern.contentEquals(patternTime)){
			toRet+=hour+":"+min+":"+sec+".000";
		}
		return toRet;
	}
	public static String getTodayDateString(){
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH)+1;
		int year = c.get(Calendar.YEAR);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		Log.i("Calendar:",year+"-"+month+"-"+day+ " "+((hour<10)?"0"+hour:hour)+":"+min+":"+sec);
		return year+"-"+month+"-"+day+ " "+((hour<10)?"0"+hour:hour)+":"+min+":"+sec;	
	}


}
