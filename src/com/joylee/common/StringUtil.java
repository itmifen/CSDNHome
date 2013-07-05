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
	 * �ַ���ת����
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

	// �Ƿ�Ϊ�գ�trueΪ��ֵ
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	// �ַ����Ƿ���ͬ��true��ʾ��ͬ
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

	// �Ƿ�Ϊ���֣�true��ʾΪ������
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

	// ��ָ������ȡ�����
	public static String getRandomString(int length) {
		Random random = new Random(System.currentTimeMillis());
		byte[] randomBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			randomBytes[i] = CHARS[random.nextInt(CHARS.length)];
		}
		return new String(randomBytes);
	}

	/**
	 * ��UMTʱ��ת��Ϊ����ʱ��
	 * 
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date ParseUTCDate(String str) {
		// ��ʽ��2012-03-04T23:42:00+08:00
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
		try {
			Date date = formatter.parse(str);

			return date;
		} catch (ParseException e) {
			// ��ʽ��Sat, 17 Mar 2012 11:37:13 +0000
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

	/* ������-��-�� Сʱ:�֣����ʽ��ȡ��ǰʱ�� */

	public static String GetNowTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		return formatter.format(curDate);
	}

	/* ������-��-�ո�ʽ��ȡ��ǰʱ�� */
	public static String GetNowTime(boolean isNeed) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		return date;
	}

	/* ���������ո�ʽ��ȡ��ǰʱ�� */
	public static String GetYMD() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());

		return date;
	}
	
	/* ����ʱ�����ʽ��ȡ�����ַ��� */
	public static String GetHMS(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		Date lsDate=strToDate(strDate,false);
		String date = sdf.format(lsDate);

		return date;
	}
	
	/* ���������ո�ʽ��ȡ�����ַ��� */
	public static String GetYMD(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date lsDate=strToDate(strDate,true);
		String date = sdf.format(lsDate);

		return date;
	}
	/* ���������ո�ʽ��ȡ�����ַ��� */
	public static String GetYMD2(String strDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date lsDate=strToDate(strDate,true);
		String date = sdf.format(lsDate);

		return date;
	}

	/* ������-��-�ո�ʽ��ȡ��ǰʱ���� */
	public static String GetNowHMS() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}

	/* ����ʱ���ʽ�ַ���ת��Ϊʱ�� */
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

	/* ��ö�ʱ���ʽ */
	public static Date GetDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date strtodate = formatter.parse(GetNowTime(), new ParsePosition(0));
		return strtodate;

	}

	/* ������������/���� */
	public static String addDay(int n, boolean m) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(GetNowTime()));
			if (m == false) {
				cd.add(Calendar.DATE, n);// ����һ��
			} else {
				cd.add(Calendar.MONTH, n);// ����һ����
			}
			return sdf.format(cd.getTime());

		} catch (Exception e) {
			return null;
		}

	}

	/* ��������,2λС�� */
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
	
	//�Ƿ�Ϊ����
	public static Boolean Isint(String str)
	{
		try {
			//���ַ���ǿ��ת��Ϊ���� 
			int num=Integer.valueOf(str);
			//��������֣�����True 
			return true;
		} catch (Exception e) {
			//����׳��쳣������False 
			return false;
		}
	}

}