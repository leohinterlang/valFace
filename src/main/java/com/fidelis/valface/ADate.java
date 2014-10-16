/**
 *+
 *	ADate.java
 *	1.0.0  2014-09-03  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.util.Calendar;
import java.util.Date;

/**
 * Implements a date class based on the proleptic Gregorian Calendar.
 * A date of this type extends the rules of the Gregorian Calendar to the time prior to it's development.
 * To paraphrase the rules:
 * <ul>
 * <li>A year is divided into 12 months.
 * <li>A month is divided into a number of days that correspond with each month.
 * <li>The month of February (2) has 28 days except on a leap year when it has 29 days.
 * <li>A leap year occurs every 4th year with exceptions.
 * <li>Every 100th year is not a leap year with exceptions.
 * <li>Every 400th year is a leap year.
 * </ul>
 * The year 1900 is not a leap year but the year 2000 is.
 * The years 2004, 2008, and 2012 are leap years.
 * All months, other than February, have a fixed number of days.
 * All years are made up of 365 days unless it is a leap year which has 366.
 * <p>
 * This class has components for the year, the month and the day that make up the date.
 * The year is a positive number with a range that begins with 0 and is limited to 9999.
 * The month component goes from 1 for January to 12 for December.
 * The day value must be in a valid range for the corresponding month.
 * In particular, the month of February (2) will have 28 days unless it is a leap year
 * in which case it has 29.
 * <p>
 * At the time of this writing, Java SE 8 is a few months away from being officially released.
 * This release will include a similar class {@code LocalDate} that serves as a model for this version.
 * Central to this idea is that a date be immutable.
 * Once a date has been created with one of the factory methods, it's value can not be changed.
 * Any modifications such as the {@code with} and {@code plus} methods generate a new date.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */

public class ADate implements Comparable<ADate> {
	private static int [ ] monthDays = {
		31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};
	private static String [ ] monthNames = {
		"January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"
	};
	private static String [ ] monNames = {
		"Jan", "Feb", "Mar", "Apr", "May", "Jun",
		"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
	};
	private static String [ ] dayOfWeekNames = {
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
	};
	private static String [ ] dowNames = {
			"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
	};
	private static ADate nowTest = null;
	private int year;
	private int month;
	private int day;
	
	/**
	 * Creates an empty date.
	 */
	private ADate () {
	}
	
	/**
	 * Produces an instance of {@code ADate} that is set to the current date.
	 * The current date is retrieved from the system and used to set the year, month and day components.
	 * 
	 * @return a new instance of {@code ADate} representing the current date.
	 */
	public static ADate now () {
		if (nowTest != null) {
			return nowTest;
		}
		return getNow();
	}
	
	/**
	 * Used only for testing, sets a particular date as the value for "now".
	 * To restore normal operation, use the {@code nowTestClear} method.
	 * 
	 * @param now the date to set for testing
	 * @see #nowTestClear
	 */
	public static void nowTestSet (ADate now) {
		nowTest = now;
	}
	
	/**
	 * Used only for testing, clears the fixed date value used for "now".
	 * This resets the test condition initiated by the {@code nowTestSet}
	 * method.
	 * 
	 * @see #nowTestSet
	 */
	public static void nowTestClear () {
		nowTest = null;
	}
	
	/**
	 * Produces an instance of {@code ADate} from a year, month and day.
	 * This static method creates a new date represented by the components for the year, the month and the day of the month.
	 * The year must be a positive value in the range 0 to 9999.
	 * The month must be the number of the month starting with 1 for January up to 12 for December.
	 * The day must be in the valid range for the given year and month. In particular, February 29 is only valid in a leap year.
	 * 
	 * @param year the year component from 0 to 9999.
	 * @param month the month component in the range 1 to 12.
	 * @param day the day of the month component from 1 to 31 depending on the year and month.
	 * @return a new instance of {@code ADate}.
	 * @throws IllegalArgumentException if any of the components are found to be invalid.
	 */
	public static ADate of (int year, int month, int day) throws IllegalArgumentException {
		checkYear(year);
		checkMonth(month);
		checkDay(year, month, day);
		ADate date = new ADate();
		date.year = year;
		date.month = month;
		date.day = day;
		return date;
	}
	
	/**
	 * Produces an instance of {@code ADate} from another {@code ADate}.
	 * The year, month and day components are set identical to the specified date.
	 * 
	 * @param aDate the date that provides the component values for the new date.
	 * @return a new instance of {@code ADate} with the same values.
	 */
	public static ADate of (ADate aDate) {
		ADate date = new ADate();
		date.year = aDate.year;
		date.month = aDate.month;
		date.day = aDate.day;
		return date;
	}
	
	/**
	 * Produces an instance of {@code ADate} from the specified String.
	 * The date String takes the form {@code YEARMODA} where YEAR is a 4 digit year number, MO is a 2 digit month
	 * number, and DA is a 2 digit day number. Two alternative forms allow these 3 fields to be separated by dashes or by dots.
	 * With the field separators, the leading zero for month and day are not required.
	 * In all cases, the year is specified first, then the month and day.
	 * <p>
	 * If the parsed String is not in the proper form or a value for a component is invalid, no values will be set and
	 * an exception is thrown.
	 * <p>
	 * <b>Examples:</b>
	 * <blockquote><pre>
	 * ADate.parse("20130308");  // March 8, 2013
	 * ADate.parse("2000-1-1");  // January 1, 2000
	 * ADate.parse("1941.12.7"); // December 7, 1941
	 * </pre></blockquote>
	 *
	 * @param dateString the String that specifies the date components.
	 * @return a new instance of {@code ADate}.
	 * @throws IllegalArgumentException if the specified date String is not valid.
	 */
	public static ADate parse (String dateString) throws IllegalArgumentException {
		boolean status = true;
		String y = null;
		String m = null;
		String d = null;
		if (dateString.contains("-")) {
			String [ ] parts = dateString.split("\\-");
			if (parts.length == 3) {
				y = parts[0];
				m = parts[1];
				d = parts[2];
			} else {
				status = false;
			}
		} else if (dateString.contains(".")) {
			String [ ] parts = dateString.split("\\.");
			if (parts.length == 3) {
				y = parts[0];
				m = parts[1];
				d = parts[2];
			} else {
				status = false;
			}
		} else if (dateString.length() >= 8) {
			y = dateString.substring(0, 4);
			m = dateString.substring(4, 6);
			d = dateString.substring(6, 8);
		} else {
			status = false;
		}
		int yearValue = 0;
		int monthValue = 0;
		int dayValue = 0;
		if (status) {
			try {
				yearValue = Integer.parseInt(y);
				monthValue = Integer.parseInt(m);
				dayValue = Integer.parseInt(d);
			} catch (NumberFormatException ex) {
				status = false;
			}
		}
		if (status) {
			checkYear(yearValue);
			checkMonth(monthValue);
			checkDay(yearValue, monthValue, dayValue);
		}
		if (! status) {
			throw new IllegalArgumentException("Invalid date String: " + dateString);
		} else {
			ADate date = new ADate();
			date.year = yearValue;
			date.month = monthValue;
			date.day = dayValue;
			return date;
		}
	}
	
	/**
	 * Returns the year component of the date.
	 * 
	 * @return the year number (0 .. 9999).
	 */
	public int getYear () {
		return year;
	}
	
	/**
	 * Returns the month component of the date.
	 * 
	 * @return the month number (1 .. 12).
	 */
	public int getMonth () {
		return month;
	}
	
	/**
	 * Returns the day of the month component of the date.
	 * 
	 * @return the day of the month (1 .. 31).
	 */
	public int getDay () {
		return day;
	}

	/**
	 * Returns a copy of this date with the year changed to the value specified.
	 * If the day of the month becomes invalid due to this change, it will be adjusted to the last valid day of the month.
	 * 
	 * @param year the year number (0 .. 9999).
	 * @return an {@code ADate} with the year value changed.
	 * @throws IllegalArgumentException if the year is not valid.
	 */
	public ADate withYear (int year) throws IllegalArgumentException {
		checkYear(year);
		ADate date = new ADate();
		date.year = year;
		date.month = month;
		date.day = day;
		adjustDay(date);
		return date;
	}
	
	/**
	 * Returns a copy of this date with the month changed to the value specified.
	 * If the day of the month becomes invalid due to this change, it will be adjusted to the last valid day of the month.
	 * 
	 * @param month the month number (1 .. 12).
	 * @return an {@code ADate} with the month value changed.
	 * @throws IllegalArgumentException if the month is invalid.
	 */
	public ADate withMonth (int month) throws IllegalArgumentException {
		checkMonth(month);
		ADate date = new ADate();
		date.year = year;
		date.month = month;
		date.day = day;
		adjustDay(date);
		return date;
	}
	
	/**
	 * Returns a copy of this date with the day of the month changed to the value specified.
	 * If the day of the month is invalid for the year and month of this date, an exception is thrown.
	 * 
	 * @param day the day of the month (1 .. 31).
	 * @return a new {@code ADate} instance with the day of the month changed.
	 * @throws IllegalArgumentException if the day is invalid.
	 */
	public ADate withDay (int day) throws IllegalArgumentException {
		checkDay(year, month, day);
		ADate date = new ADate();
		date.year = year;
		date.month = month;
		date.day = day;
		return date;
	}
	
	/**
	 * Checks that a specified year value is valid.
	 * If the year is outside the range 0 to 9999, an exception is thrown.
	 * 
	 * @param year the year value to be checked.
	 * @throws IllegalArgumentException if the year is not valid.
	 */
	private static void checkYear (int year) throws IllegalArgumentException {
		if (0 > year || year > 9999) {
			throw new IllegalArgumentException("Invalid year specified: " + year + " (0 .. 9999)");
		}
	}
	
	/**
	 * Checks that a specifed month value is valid.
	 * If the month is outside the range 1 to 12, an exception is thrown.
	 * 
	 * @param month the month value to be checked.
	 * @throws IllegalArgumentException if the month is not valid.
	 */
	private static void checkMonth (int month) throws IllegalArgumentException {
		if (1 > month || month > 12) {
			throw new IllegalArgumentException("Invalid month specified: " + month + " (1 .. 12)");
		}
	}
	
	/**
	 * Checks that a specified day value is valid.
	 * The day of the month must be in the range appropriate for the indicated month and year.
	 * If the day is outside the range 1 to the maximum day for the given month and year, an exception is thrown.
	 * 
	 * @param year the year number.
	 * @param month the month number.
	 * @param day the day value to be checked.
	 * @throws IllegalArgumentException if the day is not valid.
	 */
	private static void checkDay (int year, int month, int day) throws IllegalArgumentException {
		int max = lengthOfMonth(year, month);
		if (1 > day || day > max) {
			throw new IllegalArgumentException("Invalid day specified: " + day + " (1 .. " + max + ")");
		}
	}
	
	/**
	 * Adjusts the day of the month if it exceeds the length of the month.
	 * If the day component of the specified date is greater than the maximum day for the indicated month
	 * and year, it is adjusted downward to achieve a valid date.
	 * For example, the date April 31 is adjusted to become April 30.
	 * 
	 * @param date the {@code ADate} to be adjusted.
	 */
	private void adjustDay (ADate date) {
		int max = date.lengthOfMonth();
		if (date.day > max) {
			date.day = max;
		}
	}
	
	/**
	 * Returns a copy of this date with the specified number of years added to it.
	 * If the resulting date is outside the valid date range, an exception is thrown.
	 * If the year change results in going from a leap year to a standard year,
	 * the day component may be adjusted from February 29th to the 28th.
	 * 
	 * @param years the number of years to add. Negative values can be used to achieve subtraction.
	 * @return a new {@code ADate} instance with the modified date.
	 * @throws IllegalArgumentException if the resulting date is not valid.
	 */
	public ADate plusYears (long years) throws IllegalArgumentException {
		int yearValue = (int) (year + years);
		return withYear(yearValue);
	}
	
	/**
	 * Returns a copy of this date with the specified number of months added to it.
	 * The addition will affect both the month and year components to maintain a valid date.
	 * If the resulting date is invalid, an exception is thrown.
	 * This will only occur if the year falls outside the range 0 to 9999.
	 * As with the {@link #withMonth(int) withMonth} method, the day component may be adjusted
	 * due to a change in the month.
	 * 
	 * @param months the number of months to be added. Negative values can be used to achieve subtraction.
	 * @return a new {@code ADate} instance with the modified date.
	 * @throws IllegalArgumentException if the resulting date is not valid.
	 */
	public ADate plusMonths (long months) throws IllegalArgumentException {
		int monthValue = (int) (month + months);
		ADate date = new ADate();
		if (1 <= monthValue && monthValue <= 12) {
			date.year = year;
			date.month = monthValue;
			date.day = day;
			adjustDay(date);
			return date;
		} else if (monthValue > 12) {
			date.year = year + monthValue / 12;
			date.month = monthValue % 12;
			if (date.month == 0) {
				date.year--;
				date.month = 12;
			}
		} else if (monthValue < 1) {
			date.year = year + monthValue / 12 - 1;
			date.month = 12 + monthValue % 12;
		}
		date.day = day;
		checkYear(date.year);
		adjustDay(date);
		return date;
	}
	
	/**
	 * Returns a copy of this date with the specified number of days added to it.
	 * The year, month and day components may all be modified as a result of this addition.
	 * If the resulting date is invalid, an exception is thrown.
	 * This will only occur if the year falls outside the range 0 to 9999.
	 * 
	 * @param days the number of days to be added. Negative values can be used to achieve subtraction.
	 * @return a new {@code ADate} instance with the date modified.
	 * @throws IllegalArgumentException if the resulting date is not valid.
	 */
	public ADate plusDays (long days) throws IllegalArgumentException {
		int dayValue = (int) (day + days);
		ADate date = new ADate();
		date.year = year;
		date.month = month;
		int max = lengthOfMonth(date.year, date.month);
		if (1 <= dayValue && dayValue <= max) {
			date.day = dayValue;
		} else if (dayValue > max) {
			int val = dayValue;
			while (val > max) {
				val -= max;
				date.month++;
				if (date.month > 12) {
					date.year++;
					date.month = 1;
				}
				max = lengthOfMonth(date.year, date.month);
			}
			date.day = val;
		} else if (dayValue <= 0) {
			int val = dayValue;
			do {
				if (--date.month == 0) {
					date.month = 12;
					date.year--;
				}
				max = lengthOfMonth(date.year, date.month);
				val += max;
			} while (val <= 0);
			date.day = val;
		}
		checkYear(date.year);
		return date;
	}
	
	public ADate minusYears (long years) {
		if (years < 0) {
			return plusYears(years);
		}
		return plusYears(-years);
	}
	
	public ADate minusMonths (long months) {
		if (months < 0) {
			return plusMonths(months);
		}
		return plusMonths(-months);
	}
	
	public ADate minusDays (long days) {
		if (days < 0) {
			return plusDays(days);
		}
		return plusDays(-days);
	}

	/**
	 * Compares this date with another date to determine if they are equal.
	 * The year, month and day components of this date are compared with those
	 * of the specified "other" date.
	 * If they are the same, then {@code true} is returned.
	 * 
	 * @param other the date to be compared with this one.
	 * @return true if the dates are equal, false otherwise.
	 */
	public boolean equals (ADate other) {
		return compareTo(other) == 0;
	}
	
	/**
	 * Compares this date with another date.
	 * This date is compared with the "other" date to determined if the "other" is less than,
	 * greater than, or equal to this one. This relation is presented as a return value that is
	 * either less than, greater than, or equal to zero.
	 * <p>
	 * This method implements the {@link java.lang.Comparable Comparable} interface so that
	 * dates can be sorted.
	 * 
	 * @param other the date to compare with this one.
	 * @return an integer that is either less than, greater than, or equal to zero.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo (ADate other) {
		int diff = year - other.year;
		if (diff == 0) {
			diff = month - other.month;
			if (diff == 0) {
				diff = day - other.day;
			}
		}
		return diff;
	}

	/**
	 * Returns a new {@code ADate} instance set to the current date.
	 * This method uses the Calendar.getInstance method to obtain the current date.
	 * 
	 * @return the current date.
	 */
	private static ADate getNow () {
		Calendar today = Calendar.getInstance();
		ADate date = new ADate();
		date.year = today.get(Calendar.YEAR);
		date.month = today.get(Calendar.MONTH) + 1;
		date.day = today.get(Calendar.DAY_OF_MONTH);
		return date;
	}
	
	/**
	 * Produces a new {@code ADate} instance from the number of days since the start of the timeline.
	 * January 1, 0000 is day number 1.
	 * This is identical to the {@link #fromDays(int) fromDays} method.
	 * To determine the number of days in a date, use the {@link #getDays() getDays} or the
	 * {@link #intValue() intValue} method.
	 * 
	 * @param days the number of days since the start of the timeline.
	 * @return a new {@code ADate} instance.
	 */
	public static ADate valueOf (int days) {
		return fromDays(days);
	}
	
	/**
	 * Returns the number of days that this date represents since the start of the timeline.
	 * This is identical to the {@link #getDays() getDays} method.
	 * To produce a new {@code ADate} instance from this value, use the {@link #valueOf(int) valueOf}
	 * method or the {@link #fromDays(int) fromDays} method.
	 * 
	 * @return the number of days since the start of this timeline.
	 */
	public int intValue () {
		return getDays();
	}
	
	/**
	 * Produces a new {@code ADate} instance from the specified {@code String}.
	 * The valid date forms are "yearmoda", "year-mo-da", or "year.mo.da".
	 * This is identical to the {@link #parse(String) parse} method.
	 * 
	 * @param dateString the {@code String} specification for the date.
	 * @return a new {@code ADate} instance.
	 * @throws IllegalArgumentException if the specified date String is not valid.
	 */
	public static ADate valueOf (String dateString) throws IllegalArgumentException {
		return ADate.parse(dateString);
	}
	
	/**
	 * Produces a new {@code ADate} instance from another {@code ADate}.
	 * The year, month, and day components from the specified date are used to produce the new date with the same values.
	 * This is identical to the {@link #of(ADate) of} method.
	 * 
	 * @param aDate the date that provides the year, month, and day for the new date.
	 * @return a new {@code ADate} instance.
	 */
	public static ADate valueOf (ADate aDate) {
		return ADate.of(aDate);
	}
	
	/**
	 * Determines if this date is in a leap year.
	 * A leap year occurs every 4 years unless the year is evenly divisible by 100 and not evenly divisible by 400.
	 * Thus 1900 is not a leap year but 2000 is.
	 * 
	 * @return true if the year component for this date is a leap year.
	 */
	public boolean isLeapYear () {
		return isLeapYear(year);
	}
	
	/**
	 * Determines if the given year is a leap year.
	 * A leap year occurs every 4 years unless the year is evenly divisible by 100 but not evenly divisible by 400.
	 * Thus 1900 is not a leap year but 2000 is.
	 * 
	 * @param year the year to be checked.
	 * @return true if the indicated year is a leap year.
	 */
	public static boolean isLeapYear (int year) {
		return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
	}
	
	/**
	 * Returns the number of days for this date's month.
	 * Thirty days for September, April, June, and November.
	 * Thirty-one for January, March, May, July, August, October, and December.
	 * February has 28 in a standard year and 29 in a leap year.
	 * 
	 * @return the number of days in this date's month.
	 */
	public int lengthOfMonth () {
		return lengthOfMonth(year, month);
	}
	
	/**
	 * Returns the number of days in the specified month and year.
	 * The year is necessary to determine if February has 28 or 29 days based on the leap year
	 * calculation.
	 * 
	 * @param year the year number.
	 * @param month the month number.
	 * @return the number of days in the month.
	 */
	public static int lengthOfMonth (int year, int month) {
		int length = monthDays[month - 1];
		if ((month == 2) && isLeapYear(year)) {
			length++;
		}
		return length;
	}
	
	/**
	 * Returns the number of days in this date's year.
	 * If it is a leap year, the number is 366.
	 * If it is a standard year, the number is 365.
	 * 
	 * @return the number of days in this date's year.
	 */
	public int lengthOfYear () {
		return lengthOfYear(year);
	}
	
	/**
	 * Returns the number of days in the specified year.
	 * If the year is a leap year, the number is 366.
	 * If the year is a standard year, the number is 365.
	 * 
	 * @param year the year number.
	 * @return the number of days in the year.
	 */
	public static int lengthOfYear (int year) {
		return isLeapYear(year) ? 366 : 365;
	}
	
	/**
	 * Returns a new {@code ADate} from a {@code Date}.
	 * 
	 * @param date the java.util.Date object
	 * @return a new ADate
	 */
	public static ADate from (Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return from (cal);
	}
	
	/**
	 * Returns a new {@code ADate} from a {@code Calendar}.
	 * 
	 * @param cal the java.util.Calendar object
	 * @return a new ADate
	 */
	public static ADate from (Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return of (year, month, day);
	}
	
	/**
	 * Converts this {@code ADate} to a {@code java.util.Date}.
	 * What could go wrong?
	 * 
	 * @return a {@code java.util.Date} representation for this date.
	 */
	public Date toDate () {
		return toCalendar().getTime();
	}
	
	/**
	 * Converts this {@code ADate} to a {@code java.util.Calendar}.
	 * This date's year, month, and day components are set for a Calendar instance and returned.
	 * 
	 * @return a {@code java.util.Calendar} representation for this date.
	 */
	public Calendar toCalendar () {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal;
	}
	
	/**
	 * Returns the name for the month.
	 * The months are numbered from 1 to 12 and correspond with the names
	 * {@code January} through {@code December}.
	 * This form is also known as {@code FULL} in the enumeration {@code TextStyle} (Java 8).
	 * 
	 * @return the name for the month.
	 */
	public String monthName () {
		return monthNames [ month - 1 ];
	}
	
	/**
	 * Returns an abbreviated, 3 character name for the month.
	 * The months are numbered from 1 to 12 and correspond with the names {@code Jan} through
	 * {@code Dec}.
	 * This form is also known as {@code SHORT} in the enumeration {@code TextStyle} (Java 8).
	 * 
	 * @return the abbreviated 3 character name for the month.
	 */
	public String mon () {
		return monNames [ month - 1 ];
	}
	
	/**
	 * Returns the name for the day of the week.
	 * The days of the week are numbered from 1 to 7 and correspond with the names {@code Monday}
	 * through {@code Sunday}.
	 * This form is also known as {@code FULL} in the enumeration {@code TextStyle} (Java 8).
	 * <p>
	 * As it says in the Bible:<blockquote>
	 * "On the seventh day God rested from all his works."
	 * </blockquote>
	 * 
	 * @return the name for the day of the week.
	 */
	public String dowName () {
		return dayOfWeekNames [ dayOfWeek() - 1 ];
	}
	
	/**
	 * Returns an abbreviated, 3 character name for the day of the week.
	 * The days of the week are numbered from 1 to 7 and correspond with the names {@code Mon}
	 * through {@code Sun}.
	 * This form is also known as {@code SHORT} in the enumeration {@code TextStyle} (Java 8).
	 * <p>
	 * As it says in the Bible:<blockquote>
	 * "On the seventh day God rested from all his works."
	 * </blockquote>
	 * 
	 * @return the abbreviated 3 character name for the day of the week.
	 */
	public String dow () {
		return dowNames [ dayOfWeek() - 1 ];
	}
	
	/**
	 * Formats the date based on pattern keywords.
	 * The pattern is scanned for keywords that are replaced by the indicated value or text.
	 * The valid keywords include the following:
	 * <ul>
	 * <li>Year - the year number.
	 * <li>Month - the month number.
	 * <li>Day - the day number.
	 * <li>year - 4 digit year number.
	 * <li>yr - 2 digit year without the century digits.
	 * <li>month - 2 digit month number with leading zero when needed.
	 * <li>mo - same as month.
	 * <li>day - 2 digit day number with leading zero when needed.
	 * <li>da - same as day.
	 * <li>monthName - full month name as in {@code September}.
	 * <li>mon - abbreviated, 3 character month name as in {@code Sep}.
	 * <li>dowName - day of the week name as in {@code Tuesday}.
	 * <li>dow - abbreviated, 3 character day of the week name as in {@code Tue}.
	 * <li>days - the number of days that this date represents in the timeline.
	 * </ul>
	 * <p>
	 * Other characters appearing in the pattern are copied without translation.
	 * To protect text that may contain segments that make up a keyword, place the
	 * text between angle brackets as in {@code <more text>}.
	 * <p>
	 * <b>Examples:</b>
	 * <p><blockquote>
	 * {@code today.format("year-mo-da");} 2013-03-08<br>
	 * {@code today.format("<Month: >monthName");} Month: March<br>
	 * {@code today.format("<Today is: >dowName");} Today is: Friday<br>
	 * {@code today.format("mon Day, year dow");} Mar 8, 2013 Fri<br>
	 * </blockquote>
	 * 
	 * @param pattern the format pattern.
	 * @return a formatted date String.
	 */
	public String format (String pattern) {
		
		// Null pattern returns toString.
		if (pattern == null) {
			return toString();
		}
		
		// Setup StringBuilder.
		StringBuilder sb = new StringBuilder(40);
		
		// Pattern character loop.
		boolean inText = false;
		for (int n = 0; n < pattern.length(); n++) {
			char c = pattern.charAt(n);
			
			// Already in angle bracket text.
			if (inText) {
				
				// End of text. Reset flag and continue.
				if (c == '>') {
					inText = false;
					continue;
				}
				
				// Not end of text. Append char and continue.
				else {
					sb.append(c);
					continue;
				}
			}
			
			// Char is not a letter.
			if (! Character.isLetter(c)) {
				
				// Start of angle bracket text.
				if (c == '<') {
					inText = true;
					continue;
				}
				
				// Append non-letter char.
				sb.append(c);
			}
			
			// Char is a letter.
			else {
				
				// Isolate start of possible keyword.
				String kw = pattern.substring(n);
				int len = keywordSubstitute(kw, sb);
				
				// Keyword match. Adjust index.
				if (len != 0) {
					n += len - 1;
				}
				
				// Not a keyword match. Append the char.
				else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
	
	int keywordSubstitute (String kw, StringBuilder sb) {
		String[] keywords = {
				"Year", "year", "yr", "Month", "monthName", "month", "mon", "mo",
				"days", "Day", "day", "da", "dowName", "dow"
		};		// the order of these must not be changed.
		
		int len = 0;
		int m;
		for (m = 0; m < keywords.length; m++) {
			if (kw.startsWith(keywords[m])) {
				len = keywords[m].length();
				break;
			}
		}
		
		// Keyword match found.
		if (len != 0) {

			switch (m) {
			case 0:		// Year
				sb.append(String.format("%d", year));
				break;
			case 1:		// year
				sb.append(String.format("%04d", year));
				break;
			case 2:		// yr
				sb.append(String.format("%02d", year % 100));
				break;
			case 3:		// Month
				sb.append(String.format("%d", month));
				break;
			case 4:		// monthName
				sb.append(monthName());
				break;
			case 5:		// month
			case 7:		// mo
				sb.append(String.format("%02d", month));
				break;
			case 6:		// mon
				sb.append(mon());
				break;
			case 8:		// days
				sb.append(String.format("%d", getDays()));
				break;
			case 9:		// Day
				sb.append(String.format("%d", day));
				break;
			case 10:	// day
			case 11:	// da
				sb.append(String.format("%02d", day));
				break;
			case 12:	// dowName
				sb.append(dowName());
				break;
			case 13:	// dow
				sb.append(dow());
				break;
			}
		}
		return len;
	}
	
	/**
	 * Returns the day of the week as an integer.
	 * The days of the week are numbered from 1 for Monday to 7 for Sunday.
	 * Use {@link #dowName() dowName} to obtain the day of the week name as a String or
	 * {@link #dow() dow} to get the short form of the name.
	 * 
	 * @return the integer value from 1 (Monday) to 7 (Sunday).
	 */
	public int dayOfWeek () {
		return (4 + getDays()) % 7 + 1;
	}
	
	/**
	 * Returns the number of days since the start of this timeline.
	 * January 1, 0000 is day number 1.
	 * The year zero is considered a leap year with 366 days.
	 * Consistent time intervals can be achieved based on the number of days between dates.
	 * 
	 * @return the number of days since the start of this timeline.
	 */
	public int getDays () {
		int y = year - 1;
		int days = (year * 365) + (y / 4) - (y / 100) + (y / 400);
		if (year > 0) {
			++days;
		}
		for (int mo = 1; mo < month; mo++) {
			days += lengthOfMonth(year, mo);
		}
		days += day;
		return days;
	}
	
	/**
	 * Returns a new {@code ADate} instance from the number of days since the start of the timeline.
	 * The timeline begins with the year zero.
	 * January 1, 0000 is day number 1.
	 * Consistent time intervals can be achieved based on the number of days between dates.
	 * 
	 * @param days the number of days since the start of the timeline.
	 * @return a new {@code ADate} instance generated from the number of days.
	 */
	public static ADate fromDays (int days) {
		int year = 0;
		while (days > 146097) {			// 400 yrs
			days -= 146097;
			year += 400;
		}
		while (days > 36524) {			// 100 yrs
			days -= 36524;
			year += 100;
		}
		while (days > 1461) {				// 4 yrs
			days -= 1461;
			year += 4;
		}
		int len = lengthOfYear(year);
		while (days > len) {						// each year
			days -= len;
			year++;
			len = lengthOfYear(year);
		}
		int month = 1;
		len = lengthOfMonth(year, month);
		while (days > len) {
			days -= len;
			month++;
			len = lengthOfMonth(year, month);
		}
		int day = days;
		ADate date = new ADate();
		date.year = year;
		date.month = month;
		date.day = day;
		return date;
	}
	
	/**
	 * Returns this date in the form "year-mo-da".
	 * The year is 4 digits, the month is 2 digits, and the day is 2 digits.
	 * Use {@link #format(String) format} to get other forms.
	 * 
	 * @return the date String as in {@code 2013-03-08}.
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		return String.format("%04d-%02d-%02d", year, month, day);
	}

}
