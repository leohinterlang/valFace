/**
 *+
 *	ATime.java
 *	1.0.0  Sep 30, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.util.Calendar;
import java.util.Date;

/**
 * ATime
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ATime implements Comparable<ATime> {
	
	private static final int	HOURS_DAY 		= 24;
	private static final int	MINUTES_HOUR	= 60;
	private static final int	SECONDS_MINUTE	= 60;
	private static final long	NANOS_SECOND 	= 1000000000L;
	private static final int	MINUTES_DAY		= MINUTES_HOUR * HOURS_DAY;
	private static final int	SECONDS_DAY		= SECONDS_MINUTE * MINUTES_DAY;
	private static final long	NANOS_DAY		= NANOS_SECOND * SECONDS_DAY;
	private static final int	SECONDS_HOUR	= SECONDS_MINUTE * MINUTES_HOUR;
	private static final long	NANOS_HOUR		= NANOS_SECOND * SECONDS_HOUR;
	private static final long	NANOS_MINUTE	= NANOS_SECOND * SECONDS_MINUTE;
	private static final int	NANOS_MILLI		= 1000000;
	private static final int	NANOS_MICRO		= 1000;
	
	private int hour;
	private int minute;
	private int second;
	private int nano;
	
	private static ATime nowTest;
	
	/**
	 * Creates a new {@code ATime} object.
	 *
	 * @param hour the hour value from 0 to 23
	 * @param minute the minute value from 0 to 59
	 * @param second the second value from 0 to 59
	 * @param nanoOfSecond the nanosecond value from 0 to 999,999,999
	 */
	private ATime (int hour, int minute, int second, int nanoOfSecond) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.nano = nanoOfSecond;
	}
	
	/**
	 * Returns the hour-of-day field.
	 * This field includes values in the range 0 to 23.
	 * 
	 * @return the hour of the day
	 */
	public int getHour () {
		return hour;
	}
	
	/**
	 * Returns the minute-of-hour field.
	 * This field includes values in the range 0 to 59.
	 * 
	 * @return the minute of the hour
	 */
	public int getMinute () {
		return minute;
	}
	
	/**
	 * Returns the second-of-minute field.
	 * This field includes values in the range 0 to 59.
	 * 
	 * @return the second of the minute
	 */
	public int getSecond () {
		return second;
	}
	
	/**
	 * Returns the nano-of-second field.
	 * This field includes values in the range 0 to 999,999,999.
	 * 
	 * @return the nanosecond of the second
	 */
	public int getNano () {
		return nano;
	}
	
	/**
	 * Returns a new ATime with the current time of day.
	 * If the {@link #nowTestSet(ATime) nowTestSet} method has set a test value,
	 * that ATime is returned. Otherwise, the current time is returned.
	 * 
	 * @return a new ATime
	 * @see #nowTestSet(ATime)
	 * @see #nowTestClear()
	 */
	public static ATime now () {
		if (nowTest != null) {
			return nowTest;
		}
		Calendar cal = Calendar.getInstance();
		return from(cal);
	}
	
	/**
	 * Sets a test value to be returned by the {@link #now() now} method.
	 * Use {@link #nowTestClear() nowTestClear} to restore the normal use of the now
	 * method.
	 * 
	 * @param now the test value to set
	 * @see #now()
	 * @see #nowTestClear()
	 */
	public static void nowTestSet (ATime now) {
		nowTest = now;
	}
	
	/**
	 * Clears any test value set for the ATime returned by the {@link #now() now} method.
	 * This restores the normal behavior of the now method to return the current time of day.
	 * 
	 * @see #now()
	 * @see #nowTestSet(ATime)
	 */
	public static void nowTestClear () {
		nowTest = null;
	}
	
	/**
	 * Returns a new ATime constructed from a time specification string.
	 * The time specifier indicates the values for the fields that make up the
	 * time. These fields are the <b>hour</b>, the <b>minute</b>, the <b>second</b>
	 * and the fractional part of the second, the <b>nanosecond</b>. If a field is
	 * left unspecified, a zero value is used by default.
	 * <p>
	 * The time specifier takes the form:
	 * <blockquote>
	 * <b><code>HH[[:]mm[[:]ss[.nano]]]</code></b><br><br>
	 * Where:
	 * <blockquote>
	 * HH - the hour from 00 to 23.<br>
	 * mm - the minute from 00 to 59.<br>
	 * ss - the second from 00 to 59.<br>
	 * nano - the fractional part up to 9 digits.<br>
	 * </blockquote>
	 * The hour is the only required field.
	 * The colons (:) may be omitted as long as the {@code HH}, {@code mm}, and {@code ss}
	 * fields are composed of 2 digits each.
	 * If a nano field is included, the decimal point (.) must be included as well.
	 * <p>
	 * Examples:<br><br>
	 * <blockquote><code>
	 * 10<br>
	 * 1030<br>
	 * 10:30<br>
	 * 10:30:45<br>
	 * 10:30:45.123456789<br>
	 * 103045.123456789<br>
	 * </code></blockquote>
	 * </blockquote>
	 * 
	 * @param timeString the time specifier
	 * @return a new ATime
	 * @throws IllegalArgumentException if the time specification is invalid
	 */
	public static ATime parse (String timeString) throws IllegalArgumentException {
		boolean status = true;
		String zero = "0";
		String h = zero;
		String m = zero;
		String s = null;
		String n = null;
		if (timeString.contains(":")) {
			String[] parts = timeString.split("\\:");
			int len = parts.length;
			if (len >= 2) {
				h = parts[0];
				m = parts[1];
			}
			if (len == 3) {
				s = parts[2];
			}
			if (2 > len || len > 3) {
				status = false;
			}
		}
		else {
			int len = timeString.length();
			if (len >= 2) {
				h = timeString.substring(0, 2);
			}
			if (len >= 4) {
				m = timeString.substring(2, 4);
			}
			if (len >= 6) {
				s = timeString.substring(4);
			}
		}
		if (s == null) {
			s = zero;
			n = zero;
		}
		else {
			int index = s.indexOf(".");
			if (index > 0) {
				n = s.substring(index + 1);
				s = s.substring(0, index);
			}
			else {
				n = zero;
			}
		}
		int hour = 0;
		int minute = 0;
		int second = 0;
		int nano = 0;
		if (status) {
			try {
				hour = Integer.valueOf(h);
				minute = Integer.valueOf(m);
				second = Integer.valueOf(s);
				nano = fraction9(n);
			} catch (NumberFormatException ex) {
				status = false;
			}
		}
		if (status) {
			checkHour(hour);
			checkMinute(minute);
			checkSecond(second);
			checkNano(nano);
			return new ATime(hour, minute, second, nano);
		}
		throw new IllegalArgumentException(
				"Invalid time String: " + timeString);
	}
	
	private static int fraction9 (String number) {
		if (! number.matches("\\d{1,9}")) {
			throw new NumberFormatException("Invalid number: " + number);
		}
		int val = Integer.valueOf(number);
		int len = number.length();
		while (len < 9) {
			val *= 10;
			++len;
		}
		return val;
	}
	
	/**
	 * Formats the time based on pattern keywords.
	 * The pattern is scanned for keywords that are replaced by the indicated value or text.
	 * The valid keywords include the following:
	 * <p>
	 * <ul>
	 * <li>Hour - the hour of the day as a 24 hour value (no leading zero).
	 * <li>hour - the hour of the day as a 12 hour value (no leading zero).
	 * <li>HH - 2 digit hour in 24 hour format.
	 * <li>hh - 2 digit hour in 12 hour format.
	 * <li>Minute - the minute of the hour value (no leading zero).
	 * <li>minute - 2 digit minute.
	 * <li>mm - same as "minute".
	 * <li>Second - the second of the hour value (no leading zero).
	 * <li>second - 2 digit second.
	 * <li>ss - same as "second".
	 * <li>Nano - the nanosecond of the second value (no leading zeroes).
	 * <li>nano - 9 digit nanosecond of second.
	 * <li>micro - 6 digit microsecond of second.
	 * <li>milli - 3 digit millisecond of second.
	 * <li>nn1 - 3 digit 1st group of nanosecond.
	 * <li>nn2 - 3 digit 2nd group of nanosecond.
	 * <li>nn3 - 3 digit 3rd group of nanosecond.
	 * <li>AMPM - Uppercase AM/PM indicator.
	 * <li>ampm - Lowercase am/pm indicator.
	 * </ul>
	 * <p>
	 * Other characters appearing in the pattern are copied without translation.
	 * To protect text that may contain segments that make up a keyword, place the
	 * text between angle brackets as in {@code <the second time>}.
	 * <p>
	 * Examples:<br><br>
	 * <blockquote><code>
	 * HH:mm:ss.nano => 20:30:05.123456789<br>
	 * hh:mm ampm => 08:30 pm<br>
	 * &lt;Hour:&gt; Hour &lt;Minute:&gt; Minute => Hour: 20 Minute: 30<br>
	 * HH mm ss - nn1.nn2.nn3 => 20 30 05 - 123.456.789<br>
	 * &lt;Milliseconds:&gt; milli => Milliseconds: 123<br>
	 * &lt;Microseconds:&gt; micro => Microseconds: 123456<br>
	 * </code></blockquote>
	 * 
	 * @param pattern the format pattern
	 * @return a formatted string
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
				"Hour", "hour", "HH", "hh",
				"Minute", "minute", "mm",
				"Second", "second", "ss",
				"Nano", "nano", "milli", "micro",
				"AMPM", "ampm",
				"nn1", "nn2", "nn3"
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
			case 0:		// Hour
				sb.append(String.format("%d", hour));
				break;
			case 1:		// hour
				sb.append(String.format("%d", hour % 12));
				break;
			case 2:		// HH
				sb.append(String.format("%02d", hour));
				break;
			case 3:		// hh
				sb.append(String.format("%02d", hour % 12));
				break;
			case 4:		// Minute
				sb.append(String.format("%d", minute));
				break;
			case 5:		// minute
			case 6:		// mm
				sb.append(String.format("%02d", minute));
				break;
			case 7:		// Second
				sb.append(String.format("%d", second));
				break;
			case 8:		// second
			case 9:		// ss
				sb.append(String.format("%02d", second));
				break;
			case 10:	// Nano
				sb.append(String.format("%d", nano));
				break;
			case 11:	// nano
				sb.append(String.format("%09d", nano));
				break;
			case 12:	// milli
				sb.append(String.format("%03d", nano / NANOS_MILLI));
				break;
			case 13:	// micro
				sb.append(String.format("%06d", nano / NANOS_MICRO));
				break;
			case 14:	// AMPM
				sb.append(hour < 12 ? "AM" : "PM");
				break;
			case 15:	// ampm
				sb.append(hour < 12 ? "am" : "pm");
				break;
			case 16:	// nn1
				sb.append(String.format("%09d", nano).substring(0, 3));
				break;
			case 17:	// nn2
				sb.append(String.format("%09d", nano).substring(3, 6));
				break;
			case 18:	// nn3
				sb.append(String.format("%09d", nano).substring(6, 9));
				break;
			}
		}
		return len;
	}
	
	/**
	 * Returns a new {@code ATime} with the hour field modified.
	 * 
	 * @param hour the new hour value: 0 to 23
	 * @return a new ATime
	 * @throws IllegalArgumentException if the hour value is invalid
	 */
	public ATime withHour (int hour) throws IllegalArgumentException {
		checkHour(hour);
		return new ATime(hour, minute, second, nano);
	}
	
	/**
	 * Returns a new {@code ATime} with the minute field modified.
	 * 
	 * @param minute the new minute value: 0 to 59
	 * @return a new ATime
	 * @throws IllegalArgumentException if the minute value is invalid
	 */
	public ATime withMinute (int minute) throws IllegalArgumentException {
		checkMinute(minute);
		return new ATime(hour, minute, second, nano);
	}
	
	/**
	 * Returns a new {@code ATime} with the second field modified.
	 * 
	 * @param second the new second value: 0 to 59
	 * @return a new ATime
	 * @throws IllegalArgumentException if the second value is invalid
	 */
	public ATime withSecond (int second) throws IllegalArgumentException {
		checkSecond(second);
		return new ATime(hour, minute, second, nano);
	}
	
	/**
	 * Returns a new {@code ATime} with the nanosecond field modified.
	 * 
	 * @param nanoOfSecond the new nanosecond value: 0 to 999,999,999
	 * @return a new ATime
	 * @throws IllegalArgumentException if the nanosecond value is invalid
	 */
	public ATime withNano (int nanoOfSecond) throws IllegalArgumentException {
		checkNano(nanoOfSecond);
		return new ATime(hour, minute, second, nanoOfSecond);
	}
	
	/**
	 * Returns a new {@code ATime} constructed from the specified fields.
	 * 
	 * @param hour the hour of day value: 0 to 23
	 * @param minute the minute of hour value: 0 to 59
	 * @param second the second of minute value: 0 to 59
	 * @param nanoOfSecond the nanosecond of second value: 0 to 999,999,999
	 * @return a new ATime
	 * @throws IllegalArgumentException if the value of any field is invalid
	 */
	public static ATime of (int hour, int minute, int second, int nanoOfSecond)
			throws IllegalArgumentException {
		checkHour(hour);
		checkMinute(minute);
		checkSecond(second);
		checkNano(nanoOfSecond);
		return new ATime(hour, minute, second, nanoOfSecond);
	}
	
	/**
	 * Returns a new {@code ATime} constructed from the specified fields.
	 * The nanosecond field is set to zero.
	 * 
	 * @param hour the hour of day value: 0 to 23
	 * @param minute the minute of hour value: 0 to 59
	 * @param second the second of minute value: 0 to 59
	 * @return a new ATime
	 * @throws IllegalArgumentException if the value of any field is invalid
	 */
	public static ATime of (int hour, int minute, int second)
			throws IllegalArgumentException {
		return of(hour, minute, second, 0);
	}
	
	/**
	 * Returns a new {@code ATime} constructed from the specified fields.
	 * The second and nanosecond fields are set to zero.
	 * 
	 * @param hour the hour of day value: 0 to 23
	 * @param minute the minute of hour value: 0 to 59
	 * @return a new ATime
	 * @throws IllegalArgumentException if the value of any field is invalid
	 */
	public static ATime of (int hour, int minute)
			throws IllegalArgumentException {
		return of(hour, minute, 0, 0);
	}
	
	/**
	 * Returns a new {@code ATime} from a nanosecond of day value.
	 * 
	 * @param nanoOfDay the nano of day value: 0 to 24 * 60 * 60 * 1,000,000,000 - 1
	 * @return a new ATime
	 * @throws IllegalArgumentException if the value is out of range
	 */
	public static ATime ofNanoOfDay (long nanoOfDay)
			throws IllegalArgumentException {
		checkNanoOfDay(nanoOfDay);
		int hour = (int) (nanoOfDay / NANOS_HOUR);
		nanoOfDay -= hour * NANOS_HOUR;
		int minute = (int) (nanoOfDay / NANOS_MINUTE);
		nanoOfDay -= minute * NANOS_MINUTE;
		int second = (int) (nanoOfDay / NANOS_SECOND);
		nanoOfDay -= second * NANOS_SECOND;
		return new ATime(hour, minute, second, (int) nanoOfDay);
	}
	
	/**
	 * Returns a new {@code ATime} from a second of day value.
	 * The nanosecond field is set to zero.
	 * 
	 * @param secondOfDay the second of day value: 0 to 24 * 60 * 60 - 1
	 * @return a new ATime
	 * @throws IllegalArgumentException if the value is out of range
	 */
	public static ATime ofSecondOfDay (long secondOfDay)
			throws IllegalArgumentException {
		checkSecondOfDay(secondOfDay);
		int hour = (int) secondOfDay / SECONDS_HOUR;
		secondOfDay -= hour * SECONDS_HOUR;
		int minute = (int) secondOfDay / SECONDS_MINUTE;
		secondOfDay -= minute * SECONDS_MINUTE;
		return new ATime(hour, minute, (int) secondOfDay, 0);
	}
	
	/**
	 * Returns a nanosecond of day value for this ATime.
	 * 
	 * @return the nanosecond of day value for this ATime
	 */
	public long toNanoOfDay () {
		return hour * NANOS_HOUR + minute * NANOS_MINUTE + second * NANOS_SECOND + nano;
	}
	
	/**
	 * Returns a second of day value for this ATime.
	 * 
	 * @return the second of day value for this ATime.
	 */
	public int toSecondOfDay () {
		return hour * SECONDS_HOUR + minute * SECONDS_MINUTE + second;
	}
	
	/**
	 * Returns this ATime as a {@code java.util.Date}.
	 * The nanosecond is converted to achieve millisecond accuracy.
	 * 
	 * @return this ATime as a java.util.Date
	 */
	public Date toDate () {
		return toCalendar().getTime();
	}
	
	/**
	 * Returns this ATime as a {@code java.util.Calendar}.
	 * The nanosecond is converted to achieve millisecond accuracy.
	 * 
	 * @return this ATime as a java.util.Calendar
	 */
	public Calendar toCalendar () {
		Calendar cal = Calendar.getInstance();
		cal.set(0, 0, 0, hour, minute, second);
		cal.set(Calendar.MILLISECOND, nano / NANOS_MILLI);
		return cal;
	}
	
	/**
	 * Returns a new ATime from a {@code java.util.Date}.
	 * The date's millisecond value is converted for the nanosecond field. 
	 * 
	 * @param date the java.util.Date object
	 * @return a new ATime
	 */
	public static ATime from (Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return from(cal);
	}
	
	/**
	 * Returns a new ATime from a {@code java.util.Calendar}.
	 * The calendar's millisecond value is converted for the nanosecond field.
	 * 
	 * @param calendar the java.util.Calendar object
	 * @return a new ATime
	 */
	public static ATime from (Calendar calendar) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int milli = calendar.get(Calendar.MILLISECOND);
		return new ATime(hour, minute, second, milli * NANOS_MILLI);
	}

	/**
	 * Compares this ATime with another ATime.
	 * Returns a value less than, greater than, or equal to zero as this
	 * ATime is less than, greater than, or equal to the other ATime.
	 * 
	 * @param other the other ATime to compare to
	 * @return a value less than, greater than, or equal to zero
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo (ATime other) {
		int cmp = 0;
		if (this.hour < other.hour) {
			--cmp;
		} else if (this.hour > other.hour) {
			++cmp;
		}
		if (cmp != 0) {
			return cmp;
		}
		if (this.minute < other.minute) {
			--cmp;
		} else if (this.minute > other.minute) {
			++cmp;
		}
		if (cmp != 0) {
			return cmp;
		}
		if (this.second < other.second) {
			--cmp;
		} else if (this.second > other.second) {
			++cmp;
		}
		if (cmp != 0) {
			return cmp;
		}
		if (this.nano < other.nano) {
			--cmp;
		} else if (this.nano > other.nano) {
			++cmp;
		}
		return cmp;
	}
	
	/**
	 * Returns true if this ATime is equal to another ATime.
	 * 
	 * @param other the other ATime to compare
	 * @return true if this ATime equals the other ATime
	 */
	public boolean equals (ATime other) {
		return compareTo(other) == 0;
	}
	
	/**
	 * Returns true if this ATime is before another ATime.
	 * 
	 * @param other the other ATime to compare
	 * @return true if this ATime is before the other ATime
	 */
	public boolean isBefore (ATime other) {
		return compareTo(other) < 0;
	}
	
	/**
	 * Returns true if this ATime is after another ATime.
	 * 
	 * @param other the other ATime to compare
	 * @return true if this ATime is after the other ATime
	 */
	public boolean isAfter (ATime other) {
		return compareTo(other) > 0;
	}
	
	/**
	 * Returns a new ATime with the number of hours added to this ATime.
	 * 
	 * @param hours the number of hours to add
	 * @return a new ATime
	 */
	public ATime plusHours (long hours) {
		int newHour = ((int) (hours % HOURS_DAY) + hour + HOURS_DAY) % HOURS_DAY;
		return new ATime(newHour, minute, second, nano);
	}
	
	/**
	 * Returns a new ATime with the number of minutes added to this ATime.
	 * 
	 * @param minutes the number of minutes to add
	 * @return a new ATime
	 */
	public ATime plusMinutes (long minutes) {
		int minTime = hour * MINUTES_HOUR + minute;
		int newMinTime = ((int) (minutes % MINUTES_DAY) + minTime + MINUTES_DAY) % MINUTES_DAY;
		int newHour = (int) (newMinTime / MINUTES_HOUR) % HOURS_DAY;
		int newMinute = (int) (newMinTime % MINUTES_HOUR);
		return new ATime(newHour, newMinute, second, nano);
	}
	
	/**
	 * Returns a new ATime with the number of seconds added to this ATime.
	 * 
	 * @param seconds the number of seconds to add
	 * @return a new ATime
	 */
	public ATime plusSeconds (long seconds) {
		int secTime = toSecondOfDay();
		int newSecTime = ((int) (seconds % SECONDS_DAY) + secTime + SECONDS_DAY) % SECONDS_DAY;
		int newHour = (int) (newSecTime / SECONDS_HOUR) % HOURS_DAY;
		int newMinute = (int) (newSecTime / SECONDS_MINUTE) % MINUTES_HOUR;
		int newSecond = (int) (newSecTime % SECONDS_MINUTE);
		return new ATime(newHour, newMinute, newSecond, nano);
	}
	
	/**
	 * Returns a new ATime with the number of nanoseconds added to this ATime.
	 * 
	 * @param nanos the number of nanoseconds to add
	 * @return a new ATime
	 */
	public ATime plusNanos (long nanos) {
		long nanoTime = toNanoOfDay();
		long newNanoTime = ((nanos % NANOS_DAY) + nanoTime + NANOS_DAY) % NANOS_DAY;
		int newHour = (int) (newNanoTime / NANOS_HOUR) % HOURS_DAY;
		int newMinute = (int) (newNanoTime / NANOS_MINUTE) % MINUTES_HOUR;
		int newSecond = (int) (newNanoTime / NANOS_SECOND) % SECONDS_MINUTE;
		int newNano = (int) (newNanoTime % NANOS_SECOND);
		return new ATime(newHour, newMinute, newSecond, newNano);
	}
	
	/**
	 * Returns a new ATime with the number of hours subtracted from this ATime.
	 * 
	 * @param hours the number of hours to subtract
	 * @return a new ATime
	 */
	public ATime minusHours (long hours) {
		return plusHours(-hours);
	}
	
	/**
	 * Returns a new ATime with the number of minutes subtracted from this ATime.
	 * 
	 * @param minutes the number of minutes to subtract
	 * @return a new ATime
	 */
	public ATime minusMinutes (long minutes) {
		return plusMinutes(-minutes);
	}
	
	/**
	 * Returns a new ATime with the number of seconds subtracted from this ATime.
	 * 
	 * @param seconds the number of seconds to subtract
	 * @return a new ATime
	 */
	public ATime minusSeconds (long seconds) {
		return plusSeconds(-seconds);
	}
	
	/**
	 * Returns a new ATime with the number of nanoseconds subtracted from this ATime.
	 * 
	 * @param nanos the number of nanoseconds to subtract
	 * @return a new ATime
	 */
	public ATime minusNanos (long nanos) {
		return plusNanos(-nanos);
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder(20);
		sb.append(String.format("%02d:%02d", hour, minute));
		if (second != 0 || nano != 0) {
			sb.append(String.format(":%02d", second));
		}
		if (nano != 0) {
			if ((nano % 1000) != 0) {
				sb.append(String.format(".%09d", nano));
			}
			else if (( nano % 1000000) != 0) {
				sb.append(String.format(".%06d", nano / 1000));
			}
			else {
				sb.append(String.format(".%03d", nano / 1000000));
			}
		}
		return sb.toString();
	}

	private static void checkHour (int hour) throws IllegalArgumentException {
		if (0 > hour || hour >= HOURS_DAY) {
			throw new IllegalArgumentException(
					"Invalid hour specified: " + hour + " (0 .. 23)");
		}
	}
	
	private static void checkMinute (int minute) throws IllegalArgumentException {
		if (0 > minute || minute >= MINUTES_HOUR) {
			throw new IllegalArgumentException(
					"Invalid minute specified: " + minute + " (0 .. 59)");
		}
	}
	
	private static void checkSecond (int second) throws IllegalArgumentException {
		if (0 > second || second >= SECONDS_MINUTE) {
			throw new IllegalArgumentException(
					"Invalid second specifield: " + second + " (0 .. 59)");
		}
	}
	
	private static void checkNano (int nano) throws IllegalArgumentException {
		if (0 > nano || nano >= NANOS_SECOND) {
			throw new IllegalArgumentException(
					"Invalid nanosecond specified: " + nano);
		}
	}
	
	private static void checkSecondOfDay (long secondOfDay)
			throws IllegalArgumentException {
		if (0 > secondOfDay || secondOfDay >= SECONDS_DAY) {
			throw new IllegalArgumentException(
					"Invalid second-of-day specified: " + secondOfDay);
		}
	}
	
	private static void checkNanoOfDay (long nanoOfDay)
			throws IllegalArgumentException {
		if (0 > nanoOfDay || nanoOfDay >= NANOS_DAY) {
			throw new IllegalArgumentException(
					"Invalid nano-of-day specified: " + nanoOfDay);
		}
	}
	
}
