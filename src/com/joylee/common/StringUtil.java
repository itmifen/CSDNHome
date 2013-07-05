package com.joylee.common;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static Double toDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return null;
	}

	private static final byte[] CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9' };

	// 是否为空，true为空值
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	// 字符串是否相同，true表示相同
	public static boolean isEquals(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}
		if (str1 != null) {
			return str1.equals(str2);
		} else {
			return false;
		}
	}

	// 是否为数字，true表示为纯数字
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	// 获指定长度取随机数
	public static String getRandomString(int length) {
		Random random = new Random(System.currentTimeMillis());
		byte[] randomBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			randomBytes[i] = CHARS[random.nextInt(CHARS.length)];
		}
		return new String(randomBytes);
	}

	/**
	 * 将UMT时间转换为本地时间
	 * 
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date ParseUTCDate(String str) {
		// 格式化2012-03-04T23:42:00+08:00
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
		try {
			Date date = formatter.parse(str);

			return date;
		} catch (ParseException e) {
			// 格式化Sat, 17 Mar 2012 11:37:13 +0000
			// Sat, 17 Mar 2012 22:13:41 +0800
			try {
				SimpleDateFormat formatter2 = new SimpleDateFormat(
						"EEE, dd MMM yyyy HH:mm:ss Z", Locale.CHINA);
				Date date2 = formatter2.parse(str);

				return date2;
			} catch (ParseException ex) {
				return null;
			}
		}
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String toHexString(byte[] b) {
		// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	/* 按照年-月-日 小时:分：秒格式获取当前时间 */

	public static String GetNowTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	/* 按照年-月-日格式获取当前时间 */
	public static String GetNowTime(boolean isNeed) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		return date;
	}

	/* 按照年月日格式获取当前时间 */
	public static String GetYMD() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());

		return date;
	}
	
	/* 按照时分秒格式获取日期字符串 */
	public static String GetHMS(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		Date lsDate=strToDate(strDate,false);
		String date = sdf.format(lsDate);

		return date;
	}
	
	/* 按照年月日格式获取日期字符串 */
	public static String GetYMD(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date lsDate=strToDate(strDate,true);
		String date = sdf.format(lsDate);

		return date;
	}
	/* 按照年月日格式获取日期字符串 */
	public static String GetYMD2(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date lsDate=strToDate(strDate,true);
		String date = sdf.format(lsDate);

		return date;
	}

	/* 按照年-月-日格式获取当前时分秒 */
	public static String GetNowHMS() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}

	/* 将短时间格式字符串转换为时间 */
	public static Date strToDate(String strDate, boolean isYMD) {
		if (isYMD == true) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date strtodate = formatter.parse(strDate, new ParsePosition(0));
			return strtodate;
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date strtodate = formatter.parse(strDate, new ParsePosition(0));
			return strtodate;
		}
	}

	/* 获得短时间格式 */
	public static Date GetDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date strtodate = formatter.parse(GetNowTime(), new ParsePosition(0));
		return strtodate;

	}

	/* 增加日期天数/月数 */
	public static String addDay(int n, boolean m) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(GetNowTime()));
			if (m == false) {
				cd.add(Calendar.DATE, n);// 增加一天
			} else {
				cd.add(Calendar.MONTH, n);// 增加一个月
			}
			return sdf.format(cd.getTime());

		} catch (Exception e) {
			return null;
		}

	}

	/* 四舍五入,2位小数 */
	public static Double round(Double value) {
		
		BigDecimal bd = new BigDecimal(String.valueOf(value));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return  bd.doubleValue();

	}

	// Hex help
	private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1',
			(byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
			(byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
			(byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };

	// //////////////////////////////////////////////////////////////////////////
	// --------------------------------- REVISIONS
	// ------------------------------
	// Date Name Tracking # Description
	// --------- ------------------- ------------- ----------------------
	// 13SEP2011 James Shen Initial Creation
	// //////////////////////////////////////////////////////////////////////////
	/**
	 * convert a byte arrary to hex string
	 * 
	 * @param raw
	 *            byte arrary
	 * @param len
	 *            lenght of the arrary.
	 * @return hex string.
	 */
	public static String getHexString(byte[] raw, int len) {
		byte[] hex = new byte[2 * len];
		int index = 0;
		int pos = 0;

		for (byte b : raw) {
			if (pos >= len)
				break;

			pos++;
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}

		return new String(hex);
	}
	
	//是否为整数
	public static Boolean Isint(String str)
	{
		try {
			//把字符串强制转换为数字 
			int num=Integer.valueOf(str);
			//如果是数字，返回True 
			return true;
		} catch (Exception e) {
			//如果抛出异常，返回False 
			return false;
		}
	}

}