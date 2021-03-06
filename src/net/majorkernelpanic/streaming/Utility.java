package net.majorkernelpanic.streaming;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

public class Utility {
	public static final String CALENDAR_DATE_FORMAT = "yyyy-MM-dd";
	public static final String CALENDAR_TIME_FORMAT = "HH:mm:ss";
	public static final String CALENDAR_DATE_TIME_FORMAT = CALENDAR_DATE_FORMAT + " " + CALENDAR_TIME_FORMAT;

	static boolean mLogable = true;

	private Utility() {
	}

	public static void setLogable(boolean logable) {
		mLogable = logable;
	}

	public static boolean isNetworkConnected(Context context) {
		boolean result = false;
		ConnectivityManager connectivityManager = null;
		NetworkInfo networkInfo = null;

		if (context == null) {
			return result;
		}

		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager != null) {
			networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}

			networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		}

		return result;
	}

	public static String getCalendarString(Calendar calendar, String format) {
		String result = "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());

		result = simpleDateFormat.format(calendar.getTime());

		return result;
	}

	public static String getCalendarDateString(Calendar calendar) {
		return getCalendarString(calendar, CALENDAR_DATE_FORMAT);
	}

	public static String getCalendarTimeString(Calendar calendar) {
		return getCalendarString(calendar, CALENDAR_TIME_FORMAT);
	}

	public static String getCalendarDateTimeString(Calendar calendar) {
		return getCalendarString(calendar, CALENDAR_DATE_TIME_FORMAT);
	}

	public static String getCurrentDateString() {
		return getCalendarString(Calendar.getInstance(), CALENDAR_DATE_FORMAT);
	}

	public static String getCurrentTimeString() {
		return getCalendarString(Calendar.getInstance(), CALENDAR_TIME_FORMAT);
	}

	public static String getCurrentDateTimeString() {
		return getCalendarString(Calendar.getInstance(), CALENDAR_DATE_TIME_FORMAT);
	}

	public static Calendar stringToCalendar(String string, String format) {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());

		Date data = null;

		if (TextUtils.isEmpty(string)) {
			return calendar;
		}

		try {
			data = simpleDateFormat.parse(string);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (data != null) {
			calendar.setTime(data);
		}

		return calendar;
	}

	public static long getMilliSeconds(String dateTime) {
		long nMilliSeconds = 0;
		Date date = null;

		SimpleDateFormat formatter = new SimpleDateFormat(CALENDAR_DATE_TIME_FORMAT, Locale.getDefault());

		try {
			date = formatter.parse(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (date != null) {
			nMilliSeconds = date.getTime();
		}

		return nMilliSeconds;
	}

	public static long getMilliSeconds(String data, String time) {
		String dateTime;

		long result = 0;

		if (TextUtils.isEmpty(data)) {
			return result;
		}

		if (TextUtils.isEmpty(time)) {
			time = "00:00:00";
		}

		dateTime = data + " " + time;

		result = getMilliSeconds(dateTime);

		return result;
	}

	public static double getJsonObjectDouble(JSONObject jsonObject, String key) {
		double result = 0;

		try {
			if (jsonObject != null) {
				if (jsonObject.has(key)) {
					result = jsonObject.getDouble(key);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static int getJsonObjectInt(JSONObject jsonObject, String key) {
		int result = 0;

		try {
			if (jsonObject != null) {
				if (jsonObject.has(key)) {
					result = jsonObject.getInt(key);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String getJsonObjectString(JSONObject jsonObject, String key) {
		String result = "";

		try {
			if (jsonObject != null) {
				if (jsonObject.has(key)) {
					result = jsonObject.getString(key);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static double Round(double v, double n) {
		double p = Math.pow(10, n);
		return (Math.round(v * p)) / p;
	}

	public static boolean isFileExists(String path, boolean isDirectory) {
		boolean result = false;

		if (TextUtils.isEmpty(path)) {
			return result;
		}

		File file = new File(path);

		if (file != null) {
			if (isDirectory) {
				result = file.exists() && file.isDirectory();
			} else {
				result = file.exists();
			}
		}

		return result;
	}

	public static String getFileExtension(String path) {
		int index = 0;
		String result = "";

		if (TextUtils.isEmpty(path)) {
			return result;
		}

		index = path.lastIndexOf(".");
		if (index == -1) {
			return result;
		}

		result = path.substring(index + 1);

		return result;
	}

	public static boolean createFile(String path, boolean isDirectory) {
		boolean result = false;

		if (TextUtils.isEmpty(path)) {
			return result;
		}

		File file = new File(path);

		try {
			if (file != null) {
				if (isDirectory) {
					result = file.mkdirs();
				} else {
					result = file.createNewFile();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<String> stringToArrayList(String string) {
		if (TextUtils.isEmpty(string)) {
			return null;
		}

		ArrayList<String> result = new ArrayList<String>(Arrays.asList(string.split(",")));

		return result;
	}

	public static byte[] stringToByteArray(String string) {
		byte[] result = null;

		if (string == null) {
			return result;
		}

		result = new byte[string.length() * 2];

		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);

			result[2 * i] = (byte) ((c >> 8) & 0xff);
			result[2 * i + 1] = (byte) (c & 0xff);
		}

		return result;
	}

	public static String charArrayToString(char[] charArray, int offset) {
		String result = "";

		if (charArray == null) {
			return result;
		}

		if (charArray.length < 2) {
			return result;
		}

		for (int i = offset; i < charArray.length - 1; i += 2) {
			result += (char) (((charArray[i] << 8) & 0xff00) + charArray[i + 1]);
		}

		return result;
	}

	public static byte[] charArrayToByteArray(char[] charArray, int offset) {
		byte[] result = null;

		if (charArray == null) {
			return result;
		}

		if (offset > charArray.length - 1) {
			return result;
		}

		result = new byte[charArray.length - offset];

		for (int i = offset; i < charArray.length; i++) {
			result[i - offset] = (byte) charArray[i];
		}

		return result;
	}

	public static double randomDouble(double min, double max) {
		double result = 0;
		Random random = new Random(System.nanoTime());

		result = random.nextDouble() * (max - min) + min;

		return result;
	}

	public static boolean isNumber(String str) {
		if (TextUtils.isEmpty(str)) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;
		// deal with any possible sign up front
		int start = (chars[0] == '-') ? 1 : 0;
		if (sz > start + 1) {
			if (chars[start] == '0' && chars[start + 1] == 'x') {
				int i = start + 2;
				if (i == sz) {
					return false; // str == "0x"
				}
				// checking hex (it can't be anything else)
				for (; i < chars.length; i++) {
					if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f')
							&& (chars[i] < 'A' || chars[i] > 'F')) {
						return false;
					}
				}
				return true;
			}
		}
		sz--; // don't want to loop to the last char, check it afterwords
				// for type qualifiers
		int i = start;
		// loop to the next to last char or to the last char if we need another
		// digit to
		// make a valid number (e.g. chars[0..5] = "1234E")
		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					// two decimal points or dec in exponent
					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {
				// we've already taken care of hex.
				if (hasExp) {
					// two E's
					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false; // we need a digit after the E
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				// no type qualifier, OK
				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {
				// can't have an E at the last byte
				return false;
			}
			if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {
				// not allowing L with an exponent
				return foundDigit && !hasExp;
			}
			// last character is illegal
			return false;
		}
		// allowSigns is true iff the val ends in 'E'
		// found digit it to make sure weird stuff like '.' and '1E-' doesn't
		// pass
		return !allowSigns && foundDigit;
	}

	/**
	 * int类型转换为byte[],4个字节
	 */
	public static byte[] toByteArray(int iSource) {
		byte[] bLocalArr = new byte[4];
		for (int i = 0, a = 3; i < 4; i++, a--) {
			bLocalArr[a] = (byte) (iSource >> 8 * i & 0xFF);
		}
		return bLocalArr;
	}

	/*
	 * 将两个byte数组拼接成一个byte数组
	 */
	public static byte[] byteMerger(byte[] a, byte[] b) {
		byte[] data = new byte[a.length + b.length];
		System.arraycopy(a, 0, data, 0, a.length);
		System.arraycopy(b, 0, data, a.length, b.length);
		// bianLiByte(data3);
		return data;
	}

	/*
	 * 将三个byte数组拼接成一个byte数组
	 */
	public static byte[] byteMerger(byte[] a, byte[] b, byte[] c) {
		byte[] data = new byte[a.length + b.length + c.length];
		System.arraycopy(a, 0, data, 0, a.length);
		System.arraycopy(b, 0, data, a.length, b.length);
		System.arraycopy(c, 0, data, a.length + b.length, c.length);
		return data;
	}

	/**
	 * byte数组转16进制字符串
	 * 
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv + " ");
		}
		return stringBuilder.toString();
	}
}
