package com.arhat.chattest.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class DateUtil {
	public final static String yyyy = "yyyy";
	public final static String MM_dd = "MM-dd";
	public final static String dd = "dd";
	public final static String yyyy_MM_dd = "yyyy-MM-dd";
	public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss SSS";
	public final static String MM_dd_HH_mm_ss = "MM-dd  HH:mm:ss";
	public final static String MM_dd_HH_mm = "MM-dd  HH:mm";
	public final static String yyyy_MM_dd_HH_mm_local = "yyyy��MM��dd�� HH:mm";

	/**
	 * ���ص������ڵ��ַ����������Լ�����ʽ����ʽ����
	 * @param pattern
	 * @return
	 */
	public static String now(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(Calendar.getInstance().getTime());
	}

	public static String now_yyyy() {
		return now(yyyy);
	}

	public static String now_MM_dd() {
		return now(MM_dd);
	}

	public static String now_dd() {
		return now(dd);
	}

	public static String now_yyyy_MM_dd() {
		return now(yyyy_MM_dd);
	}

	public static String now_yyyy_MM_dd_HH_mm_ss() {
		return now(yyyy_MM_dd_HH_mm_ss);
	}

	public static String now_yyyy_MM_dd_HH_mm_ss_SSS() {
		return now(yyyy_MM_dd_HH_mm_ss_SSS);
	}

	public static String now_MM_dd_HH_mm_ss() {
		return now(MM_dd_HH_mm_ss);
	}

	/**
	 * �������
	 * @param birth
	 * @return
	 */
	public static String getAge(String birth) { // �������ĸ�ʽһ��Ҫ��Ӧ1991-05-25
		if ("".equals(birth))
			return "";

		int nowAge = 0;
		Date date = strToDateCN_yyyy_MM_dd(birth);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int birth_year = calendar.get(Calendar.YEAR);
		int birth_month = calendar.get(Calendar.MONTH);
		int birth_day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(new Date());
		int now_year = calendar.get(Calendar.YEAR);
		int now_month = calendar.get(Calendar.MONTH);
		int now_day = calendar.get(Calendar.DAY_OF_MONTH);
		
		nowAge = now_year - birth_year;
		if (now_month < birth_month) {
			nowAge--;
		} else if (now_month == birth_month && now_day < birth_day) {
			nowAge--;
		}
		if (nowAge < 0)
			nowAge = 0;
		return "" + nowAge;
	}

	public static String getStauts(String stime,String rtime){
		if(stime == null || rtime == null)
			return "";
		String status = "";
		try {
			Date sDate = strToDateLoc_yyyy_MM_dd_HH_mm(stime);
			Date rDate = strToDateLoc_yyyy_MM_dd_HH_mm(rtime);
			Date nDate = new Date();
			if(nDate.getTime() < sDate.getTime())
				status = "׼����";
			else if(nDate.getTime() > rDate.getTime()){
				status = "�ѽ���";
			}
			else
				status = "������";
		} catch (Exception e) {
			e.printStackTrace();
			status = "";
		}
		return status;
	}
	
	
	/**
	 * ������ʾ
	 * @param timeString
	 * @return ��Сʱǰ�������
	 */
	private static String getRecentTime(String timeString,Date date) { 
		if(timeString == null || "".equals(timeString))
			return "";
		StringBuilder cn_string = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_YEAR);         //�·��е���
		int week = calendar.get(Calendar.WEEK_OF_YEAR);          //������
		int hour = calendar.get(Calendar.HOUR_OF_DAY);           //ʱ��
		
		int month = calendar.get(Calendar.MONTH)+1;         //�·�.��ʾ��
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK)-1;   //���е���.��ʾ��
		if(day_of_week == 0) day_of_week = 7;
		int minute = calendar.get(Calendar.MINUTE);             //����.��ʾ��
		
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(new Date());
		int now_day = nowCalendar.get(Calendar.DAY_OF_YEAR);
		int now_week = nowCalendar.get(Calendar.WEEK_OF_YEAR);
		int now_hour = nowCalendar.get(Calendar.HOUR_OF_DAY);
		int now_minute = nowCalendar.get(Calendar.MINUTE);
		//ʱ���ַ���
		StringBuilder hoursString = new StringBuilder(""+hour);
		StringBuilder minutesString = new StringBuilder(""+minute);
		
		if(hour<10)
			hoursString.insert(0, "0");
		if(minute<10)
			minutesString.insert(0, "0");
		
		if(day == now_day && hour == now_hour && minute == now_minute){
			cn_string.append("�ո�");
		}
		else if(day == now_day && hour == now_hour){
			cn_string.append(Math.abs(now_minute - minute)).append("����ǰ");
		}
		else if((now_day - day >=0) &&(now_day - day < 1) && (now_hour - hour < 24) && (now_hour - hour >0)){
			cn_string.append(now_hour-hour).append("Сʱǰ");
		}
		else if(now_day == day){
			cn_string.append("����").append(hoursString).append(":").append(minutesString);
		}
		else if(now_day - day == 1)
			cn_string.append("����").append(hoursString).append(":").append(minutesString);
		else if(now_day - day == 2)
			cn_string.append("ǰ��").append(hoursString).append(":").append(minutesString);
		else if(day - now_day == 1)
			cn_string.append("����").append(hoursString).append(":").append(minutesString);
		else if(day - now_day == 2)
			cn_string.append("����").append(hoursString).append(":").append(minutesString);
		else if((week == now_week && day>now_day ) || (week - now_week == 1 && day_of_week == 7))
			cn_string.append("��").append(numToUpper(day_of_week)).append(" ").append(hoursString).append(":").append(minutesString);
		else if(week - now_week ==1)
			cn_string.append("����").append(numToUpper(day_of_week)).append(" ").append(hoursString).append(":").append(minutesString);
		else{  //ֱ����ʾ
			StringBuilder monthString = new StringBuilder(""+month);
			int _day = calendar.get(Calendar.DAY_OF_MONTH);         //�·��е���
			StringBuilder dayString = new StringBuilder(""+_day);
			if(month <10)
				monthString.insert(0, "0");
			if(_day<10)
				dayString.insert(0, "0");
			cn_string.append(monthString).append("��").append(dayString).append("��").append(hoursString).append(":").append(minutesString);
		}
		return cn_string.toString();
	}
	
	/**
	 * 02-01 12:11:56
	 * @param timeString
	 * @return
	 */
	public static String getRecentTimeMM_dd(String timeString){ 
		Date date = strToDateMM_dd(timeString);
		return getRecentTime(timeString, date);
	}
	
	/**
	 * 02-01 12:11:56
	 * @param timeString
	 * @return
	 */
	public static String getRecentTimeMM_dd_ss(String timeString){ 
		Date date = strToDateMM_dd_ss(timeString);
		return getRecentTime(timeString, date);
	}
	
	/**
	 * 2012��01��03�� 12:55
	 * @param timeString
	 * @return
	 */
	public static String getRecentTimeyyyy_MM_dd(String timeString){ 
		Date date = strToDateLoc_yyyy_MM_dd_HH_mm(timeString);
		return getRecentTime(timeString, date);
	}
	
	
	/**
	 * �Ƿ񳬹����죬�ж��Ƿ����ϴ�ͼƬ
	 * @param runtime
	 * @return
	 */
	public static boolean isOver3Day(String runtime){
		boolean is = false;
		Date runDate =  strToDateLoc_yyyy_MM_dd_HH_mm(runtime);
		Date now = new Date();
		if((now.getTime() - runDate.getTime()) > (3*24*60*60*1000)){
			is = true;
		}
		return is;
	}
	
	public static String getHi(){
		int nowHour = new Date().getHours();
		if(nowHour>=0 && nowHour <6){
			return "�賿�ã�";
		}
		else if(nowHour>=6 && nowHour<12){
			return "����ã�";
		}
		else if(nowHour>=12 && nowHour<18){
			return "����ã�";
		}
		else{
			return "���Ϻã�";
		}
	}
	
	public static final String[] zodiacArr = { "��", "��", "��", "��", "��", "ţ", "��", "��", "��", "��", "��", "��" };   
	  
	public static final String[] constellationArr = { "ˮƿ��", "˫����", "ĵ����", "��ţ��", "˫����", "��з��", "ʨ����", "��Ů��", "�����",   
	        "��Ы��", "������", "ħ����" };   
	  
	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };   
	  
	/**  
	 * �������ڻ�ȡ��Ф  
	 * @return  
	 */  
	public static String date2Zodica(String birth) {   
		if ("".equals(birth))
			return "";
	    return zodiacArr[Integer.valueOf(birth.split("-")[0]) % 12];   
	}   
	  
	/**  
	 * �������ڻ�ȡ����  
	 * @param time  
	 * @return  
	 */  
	public static String date2Constellation(String birth) {  // �������ĸ�ʽһ��Ҫ��Ӧ1991-05-25 
		Date date = strToDateCN_yyyy_MM_dd(birth);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
	    
	    if (day < constellationEdgeDay[month]) {   
	        month = month - 1;   
	    }   
	    if (month >= 0) {   
	        return constellationArr[month];   
	    }   
	    //default to return ħ��   
	    return constellationArr[11];   
	} 
	
	
	
	
	/**
	 * 
	 * @param dateString
	 *            yyyy��MM��dd�� HH:mm
	 * @return like: ���� 15��00
	 */
	public static String ch_time(String dateString) {
//		System.out.println("pppppt-->"+dateString);
		String cn_string = null;
		Date date = DateUtil.strToDate(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		String hString,mString;
		if (hour < 10) {
			hString = "0" + hour;
		}else {
			hString = ""+hour;
		}
		if (minute < 10) {
			mString = "0"+minute;
		}else {
			mString = ""+minute;
		}
		String time = hString+":"+mString;
		hString = null;
		mString = null;
		//ȥ�������µ�ʱ��
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.HOUR);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.clear(Calendar.HOUR_OF_DAY);
		nowCalendar.clear(Calendar.HOUR);
		nowCalendar.clear(Calendar.MINUTE);
		nowCalendar.clear(Calendar.SECOND);
		nowCalendar.clear(Calendar.MILLISECOND);
		
		long flag = (calendar.getTimeInMillis() - nowCalendar.getTimeInMillis()
				) / (1000 * 60 * 60 * 24);
//		System.out.println("ppssss->" + flag);
		if (flag == 0) {
			cn_string = "���� " + time;
		} else if (flag == 1) {
			cn_string = "���� " + time;
		} else if (flag == 2) {
			cn_string = "���� " + time;
		} else if (flag <= getDatePlus() + 7 && flag > 0) {
			if (!getNextWeekDate(calendar).equals("")) {
				cn_string = getNextWeekDate(calendar) + time;
			} else {
				cn_string = dateString;
			}
		} else {
			cn_string = dateString;
		}
		time = null;
		return cn_string;
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		if(date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * ������ʱ����ת���ַ��� ��:" 2002-07-01 11:40:02"
	 * 
	 * @param inDate
	 *            ����ʱ�� " 2002-07-01 11:40:02"
	 * @return String ת��������ʱ���ַ���
	 */
	public static String dateToStr_yyyy_MM_dd_HH_mm_ss(Date date) {
		return dateToStr(date, yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * ������ʱ����ת���ַ��� ��:" 2002-07-01 11:40:02"
	 * 
	 * @param inDate
	 *            ����ʱ�� " 2002-07-01 11:40:02"
	 * @return String ת��������ʱ���ַ���
	 */
	public static String dateToStr_yyyy_MM_dd_HH_mm(Date date) {
		return dateToStr(date, yyyy_MM_dd_HH_mm);
	}

	/**
	 * yyyy��MM��dd�� HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStrLocal(Date date) {
		return dateToStr(date, yyyy_MM_dd_HH_mm_local);
	}

	/**
	 * ������ʱ����ת���ַ��� ��:" 2002-07-01 11:40:02"
	 * 
	 * @param inDate
	 *            ����ʱ�� " 2002-07-01 11:40:02"
	 * @return String ת��������ʱ���ַ���
	 */
	public static String dateToStr_MM_dd_HH_mm_ss(Date date) {
		return dateToStr(date, MM_dd_HH_mm_ss);
	}

	/**
	 * ��������ת���ַ��� ��:"2002-07-01"
	 * 
	 * @param inDate
	 *            ���� "2002-07-01"
	 * @return String ת���������ַ���
	 */
	public static String dateToStr_yyyy_MM_dd(Date date) {
		return dateToStr(date, yyyy_MM_dd);
	}

	/**
	 * ���ַ�����(Ӣ�ĸ�ʽ)ת�������� ��: "Tue Dec 26 14:45:20 CST 2000"
	 * 
	 * @param DateFormatStr
	 *            �ַ��� "Tue Dec 26 14:45:20 CST 2000"
	 * @return Date ����
	 */
	public static Date strToDateEN(String shorDateStr) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd hh:mm:ss 'CST' yyyy", java.util.Locale.US);
			return sdf.parse(shorDateStr);
		} catch (Exception e) {
			return new Date();
		}
	}

	/**
	 * ���ַ�����(���ĸ�ʽ)ת�������� ��:"2002-07-01 22:09:55"
	 * 
	 * @param datestr
	 *            �ַ��� "2002-07-01 22:09:55"
	 * @return Date ����
	 */
	public static Date strToDateCN_yyyy_MM_dd_HH_mm_ss(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}
	
	/**
	 * ���ַ�����(���ĸ�ʽ)ת�������� ��:"2002-07-01 22:09"
	 * 
	 * @param datestr
	 *            �ַ��� "2002-07-01 22:09"
	 * @return Date ����
	 */
	public static Date strToDateCN_yyyy_MM_dd_HH_mm(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(yyyy_MM_dd_HH_mm);
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}
	

	/**yyyy_MM_dd_HH_mm
	 * @param datestr
	 * @return
	 */
	public static Date strToDateLoc_yyyy_MM_dd_HH_mm(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(yyyy_MM_dd_HH_mm_local);
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}
	
	/**MM-dd  HH:mm
	 * @param datestr
	 * @return
	 */
	public static Date strToDateMM_dd(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(MM_dd_HH_mm);
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}

	/**MM-dd  HH:mm
	 * @param datestr
	 * @return
	 */
	public static Date strToDateMM_dd_ss(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(MM_dd_HH_mm_ss);
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}

	/**
	 * 
	 * @param datestr
	 * @return
	 */
	public static Date strToDateCN_yyyy_MM_dd(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}

	/**
	 * 
	 * @param datestr
	 *            yyyy��MM��dd�� HH:mm
	 * @return
	 */
	public static Date strToDate(String datestr) {
		Date date = null;
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy��MM��dd�� HH:mm");
			date = fmt.parse(datestr);
		} catch (Exception e) {
			return date;
		}
		return date;
	}

	/**
	 * ת��util.date-->sql.date
	 * 
	 * @param inDate
	 * @return
	 */
	public static java.sql.Date UtilDateToSqlDate(Date inDate) {
		return new java.sql.Date(getDateTime(inDate));
	}

	private static long getDateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		cal.set(year, month, day, 0, 0, 0);
		long result = cal.getTimeInMillis();
		result = result / 1000 * 1000;
		return result;
	}

	/**
	 * �����մ����ݿ���������Map��������Timestamp��ʽ����ָ����pattern
	 * 
	 * @param target
	 *            Ŀ��map,����һ���Ǹմ����ݿ���������
	 * @param pattern
	 *            ��ʽ�����򣬴�����ȡ
	 */
	@Deprecated
	public static void formatMapDate(Map target, String pattern) {
		for (Object item : target.entrySet()) {
			Map.Entry entry = (Map.Entry) item;
			if (entry.getValue() instanceof Timestamp) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				entry.setValue(sdf.format((Timestamp) entry.getValue()));
			}
		}
	}

	/**
	 * ����ת��Ϊ��Сд chenjiandong 20090609 add
	 * 
	 * @param date
	 * @param type
	 *            1;2������ʽ1Ϊ�������ģ�2Ϊ��������
	 * @return
	 */
	public static String dataToUpper(Date date, int type) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper(year, type) + "��" + monthToUppder(month, type) + "��"
				+ dayToUppder(day, type) + "��";
	}

	/**
	 * ������ת��Ϊ��д
	 * 
	 * @param num
	 * @param type
	 * @return
	 */
	public static String numToUpper(int num, int type) {// typeΪ��ʽ1;2
		String u1[] = { "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��" };
		String u2[] = { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		if (type == 1) {
			for (int i = 0; i < str.length; i++) {
				rstr = rstr + u1[Integer.parseInt(str[i] + "")];
			}
		} else if (type == 2) {
			for (int i = 0; i < str.length; i++) {
				rstr = rstr + u2[Integer.parseInt(str[i] + "")];
			}
		}
		return rstr;
	}
	
	/**
	 * ������ת��Ϊ��д
	 * 
	 * @param num
	 * @return
	 */
	public static String numToUpper(int num) {
		String u1[] = { "��", "һ", "��", "��", "��", "��", "��", "��"};
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u1[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	/**
	 * ��ת��Ϊ��д
	 * 
	 * @param month
	 * @param type
	 * @return
	 */
	public static String monthToUppder(int month, int type) {
		if (month < 10) {
			return numToUpper(month, type);
		} else if (month == 10) {
			return "ʮ";
		} else {
			return "ʮ" + numToUpper((month - 10), type);
		}
	}

	/**
	 * ��ת��Ϊ��д
	 * 
	 * @param day
	 * @param type
	 * @return
	 */
	public static String dayToUppder(int day, int type) {
		if (day < 20) {
			return monthToUppder(day, type);
		} else {
			char[] str = String.valueOf(day).toCharArray();
			if (str[1] == '0') {
				return numToUpper(Integer.parseInt(str[0] + ""), type) + "ʮ";
			} else {
				return numToUpper(Integer.parseInt(str[0] + ""), type) + "ʮ"
						+ numToUpper(Integer.parseInt(str[1] + ""), type);
			}
		}
	}

	/**
	 * ��õ�ǰ�����뱾������������(��������Ϊһ�����һ��)
	 * 
	 * @return
	 */
	public static int getDatePlus() {
		Calendar cd = Calendar.getInstance();
		// ��ý�����һ�ܵĵڼ��죬�������ǵ�һ�죬����һ�ǵڶ���......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 8 - dayOfWeek;
		}
	}

	/**
	 * 
	 * @param calendar
	 * @return
	 */
	public static String getNextWeekDate(Calendar calendar) {
		String date = "";
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			date = "������ ";
			break;
		case 2:
			date = "����һ ";
			break;
		case 3:
			date = "���ܶ� ";
			break;
		case 4:
			date = "������ ";
			break;
		case 5:
			date = "������ ";
			break;
		case 6:
			date = "������ ";
			break;
		case 7:
			date = "������ ";
			break;
		default:
			break;
		}
		return date;
	}
	
	public static boolean isToday(String datesString){
		boolean isToday = false;
		Date date = strToDateCN_yyyy_MM_dd_HH_mm(datesString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		Calendar nowCalendar = Calendar.getInstance();
		int nowDay = nowCalendar.get(Calendar.DAY_OF_YEAR);
		if (nowDay == day) {
			isToday = true;
		}
		return isToday;
	}
}
