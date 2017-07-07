package com.ealpha.drawer;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.location.LocationManager;
import android.util.Patterns;

public class Function {

	public String getDateTime() {
		Calendar cal;
		int day, year, hour, minute, second, num;
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		num = cal.get(Calendar.MONTH);
		String month = "";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}

		year = cal.get(Calendar.YEAR);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);

		// "addedon": "27:Dec:2014:1:45:34" DD:MMM:YYYY:HH:MM:SS
		// String shour;
		String sday;
		String sminute;
		String ssecond;

		// if (hour < 10) {
		// shour = "0" + hour;
		// } else {
		// shour = "" + hour;
		// }

		if (day < 10) {
			sday = "0" + day;
		} else {
			sday = "" + day;
		}
		if (minute < 10) {
			sminute = "0" + minute;
		} else {
			sminute = "" + minute;
		}
		if (second < 10) {
			ssecond = "0" + second;
		} else {
			ssecond = "" + second;
		}

		return sday + ":" + month.charAt(0) + "" + month.charAt(1) + ""
				+ month.charAt(2) + ":" + year + ":" + hour + ":" + sminute
				+ ":" + ssecond;

	}

	public boolean CheckGpsEnableOrNot(Context context) {
		boolean gpsStatus = false;
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			gpsStatus = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
			gpsStatus = false;
		}
		return gpsStatus;
	}

	public static boolean isEmailValid(String email) {
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public String getEmailAddress(Context context) {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(context).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				String possibleEmail = "";
				if (account.name != null) {
					possibleEmail = account.name;
				}
				System.out.println("email address : " + possibleEmail);
				return possibleEmail;
			}
		}
		return "";
	}

}
