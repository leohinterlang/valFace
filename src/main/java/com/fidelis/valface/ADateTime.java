/**
 *+
 *	ADateTime.java
 *	1.0.0  Oct 9, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.util.Calendar;
import java.util.Date;

/**
 * ADateTime
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ADateTime implements Comparable<ADateTime> {
	
	private static final int HOURS_DAY		= 24;
	private static final int MINUTES_HOUR	= 60;
	private static final int SECONDS_MINUTE	= 60;
	private static final int NANOS_SECOND	= 1000000000;
	private static final int MINUTES_DAY	= MINUTES_HOUR * HOURS_DAY;
	private static final int SECONDS_DAY	= SECONDS_MINUTE * MINUTES_DAY;
	private static final long NANOS_DAY		= (long) NANOS_SECOND * SECONDS_DAY;
	private static final int SECONDS_HOUR	= SECONDS_MINUTE * MINUTES_HOUR;
	private static final int NANOS_MINUTE	= NANOS_SECOND * SECONDS_MINUTE;
	private static final int NANOS_HOUR		= NANOS_MINUTE * MINUTES_HOUR;
	private static final int NANOS_MILLI	= 1000000;
	
	private ADate date;
	private ATime time;
	
	
	/**
	 * Private constructor from an ADate and an ATime component.
	 * 
	 * @param date the ADate component
	 * @param time the ATime component
	 */
	private ADateTime (ADate date, ATime time) {
		this.date = date;
		this.time = time;
	}
	
	/**
	 * Returns a new ADateTime from an ADate and an ATime component.
	 * 
	 * @param date the ADate component
	 * @param time the ATime component
	 * @return a new ADateTime
	 */
	public static ADateTime of (ADate date, ATime time) {
		return new ADateTime(date, time);
	}
	
	/**
	 * Returns a new ADateTime from the specified field values.
	 * 
	 * @param year the year value: 0 to 9999
	 * @param month the month value: 1 to 12
	 * @param day the day of month value: 1 to 31 depending on the year and month
	 * @param hour the hour of the day value: 0 to 23
	 * @param minute the minute of hour value: 0 to 59
	 * @param second the second of minute value: 0 to 59
	 * @param nano the nanosecond of second value: 0 to 999,999,999
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the value of any field is invalid
	 */
	public static ADateTime of (int year, int month, int day,
			int hour, int minute, int second, int nano) {
		ADate date = ADate.of(year, month, day);
		ATime time = ATime.of(hour, minute, second, nano);
		return of(date, time);
	}
	
	/**
	 * Returns a new ADateTime from the specified field values.
	 * The nanosecond field is set to zero.
	 * 
	 * @param year the year value: 0 to 9999
	 * @param month the month value: 1 to 12
	 * @param day the day of month value: 1 to 31 depending on the year and month
	 * @param hour the hour of the day value: 0 to 23
	 * @param minute the minute of hour value: 0 to 59
	 * @param second the second of minute value: 0 to 59
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the value of any field is invalid
	 */
	public static ADateTime of (int year, int month, int day,
			int hour, int minute, int second) {
		return of(year, month, day, hour, minute, second, 0);
	}
	
	/**
	 * Returns a new ADateTime from the specified field values.
	 * The second and nanosecond fields are set to zero.
	 * 
	 * @param year the year value: 0 to 9999
	 * @param month the month value: 1 to 12
	 * @param day the day of month value: 1 to 31 depending on the year and month
	 * @param hour the hour of the day value: 0 to 23
	 * @param minute the minute of hour value: 0 to 59
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the value of any field is invalid
	 */
	public static ADateTime of (int year, int month, int day,
			int hour, int minute) {
		return of(year, month, day, hour, minute, 0, 0);
	}
	
	/**
	 * Returns a new ADateTime with the year field modified.
	 * 
	 * @param year the year value: 0 to 9999
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the year value is invalid
	 */
	public ADateTime withYear (int year) {
		return of(date.withYear(year), time);
	}
	
	/**
	 * Returns a new ADateTime with the month field modified.
	 * 
	 * @param month the month value: 1 to 12
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the month value is invalid
	 */
	public ADateTime withMonth (int month) {
		return of(date.withMonth(month), time);
	}
	
	/**
	 * Returns a new ADateTime with the day of month field modified.
	 * 
	 * @param day the day of month value: 1 to 31 depending on the year and month
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the day of month value is invalid
	 */
	public ADateTime withDay (int day) {
		return of(date.withDay(day), time);
	}
	
	/**
	 * Returns a new ADateTime with the hour field modified.
	 * 
	 * @param hour the hour value: 0 to 23
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the hour value is invalid
	 */
	public ADateTime withHour (int hour) {
		return of(date, time.withHour(hour));
	}
	
	/**
	 * Returns a new ADateTime with the minute field modified.
	 * 
	 * @param minute the minute value: 0 to 59
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the minute value is invalid
	 */
	public ADateTime withMinute (int minute) {
		return of(date, time.withMinute(minute));
	}
	
	/**
	 * Returns a new ADateTime with the second field modified.
	 * 
	 * @param second the second value: 0 to 59
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the second value is invalid
	 */
	public ADateTime withSecond (int second) {
		return of(date, time.withSecond(second));
	}
	
	/**
	 * Returns a new ADateTime with the nanosecond field modified.
	 * 
	 * @param nano the nanosecond value: 0 to 999,999,999
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the nanosecond value is invalid
	 */
	public ADateTime withNano (int nano) {
		return of(date, time.withNano(nano));
	}
	
	/**
	 * Returns a new ADateTime from a date-time string specification.
	 * The date portion of the specification is separated from the time portion
	 * by the letter "T" (either upper or lower case).
	 * <blockquote><code><pre>
	 * &lt;date&gt;T&lt;time&gt;
	 * 2014-09-18T10:20:30.123456789
	 * </pre></code></blockquote>
	 * 
	 * @param dateTimeSpec the date-time specification
	 * @return a new ADateTime
	 * @throws IllegalArgumentException if the specification is invalid
	 * @see com.fidelis.valface.ADate#parse(String)
	 * @see com.fidelis.valface.ATime#parse(String)
	 */
	public static ADateTime parse (String dateTimeSpec) {
		String[] parts = dateTimeSpec.split("[Tt]");
		if (parts.length == 2) {
			ADate date = ADate.parse(parts[0]);
			ATime time = ATime.parse(parts[1]);
			return of(date, time);
		}
		else {
			throw new IllegalArgumentException(
					"Invalid date-time specification: " + dateTimeSpec);
		}
	}
	
	/**
	 * Returns a String representation of this ADateTime formatted by the specified pattern.
	 * The pattern contains keywords that are replaced by values from the date-time fields.
	 * <p>
	 * The valid date keywords include:
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
	 * The valid time keywords include:
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
	 * 
	 * @param pattern the format pattern
	 * @return the formatted String
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
				int len = date.keywordSubstitute(kw, sb);
				if (len == 0) {
					len = time.keywordSubstitute(kw, sb);
				}
				
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
	
	/**
	 * Returns the value of the year field for this ADateTime.
	 * 
	 * @return the value of the year field
	 */
	public int getYear () {
		return date.getYear();
	}
	
	/**
	 * Returns the value of the month field for this ADateTime.
	 * 
	 * @return the value of the month field
	 */
	public int getMonth () {
		return date.getMonth();
	}
	
	/**
	 * Returns the value of the day field for this ADateTime.
	 * 
	 * @return the value of the day field
	 */
	public int getDay () {
		return date.getDay();
	}
	
	/**
	 * Returns the value of the hour field for this ADateTime.
	 * 
	 * @return the value of the hour field
	 */
	public int getHour () {
		return time.getHour();
	}
	
	/**
	 * Returns the value of the minute field for this ADateTime.
	 * 
	 * @return the value of the minute field
	 */
	public int getMinute () {
		return time.getMinute();
	}
	
	/**
	 * Returns the value of the second field for this ADateTime.
	 * 
	 * @return the value of the second field
	 */
	public int getSecond () {
		return time.getSecond();
	}
	
	/**
	 * Returns the value of the nanosecond field for this ADateTime.
	 * 
	 * @return the value of the nanosecond field
	 */
	public int getNano () {
		return time.getNano();
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of years added to it.
	 * 
	 * @param years the number of years to add
	 * @return a new ADateTime
	 */
	public ADateTime plusYears (long years) {
		return of(date.plusYears(years), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of months added to it.
	 * 
	 * @param months the number of months to add
	 * @return a new ADateTime
	 */
	public ADateTime plusMonths (long months) {
		return of(date.plusMonths(months), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of days added to it.
	 * 
	 * @param days the number of days to add
	 * @return a new ADateTime
	 */
	public ADateTime plusDays (long days) {
		return of(date.plusDays(days), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of weeks added to it.
	 * 
	 * @param weeks the number of weeks to add
	 * @return a new ADateTime
	 */
	public ADateTime plusWeeks (long weeks) {
		return of(date.plusDays(7 * weeks), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of hours added to it.
	 * 
	 * @param hours the number of hours to add
	 * @return a new ADateTime
	 */
	public ADateTime plusHours (long hours) {
		if (hours < 0) {
			return minusHours(-hours);
		}
		long totalHours = time.getHour() + hours;
		ADate newDate = date;
		if (totalHours >= HOURS_DAY) {
			newDate = date.plusDays(totalHours / HOURS_DAY);
		}
		ATime newTime = time.plusHours(hours);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of minutes added to it.
	 * 
	 * @param minutes the number of minutes to add
	 * @return a new ADateTime
	 */
	public ADateTime plusMinutes (long minutes) {
		if (minutes < 0) {
			return minusMinutes(-minutes);
		}
		long totalMinutes = time.getHour() * MINUTES_HOUR + time.getMinute() + minutes;
		ADate newDate = date;
		if (totalMinutes >= MINUTES_DAY) {
			newDate = date.plusDays(totalMinutes / MINUTES_DAY);
		}
		ATime newTime = time.plusMinutes(minutes);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of seconds added to it.
	 * 
	 * @param seconds the number of seconds to add
	 * @return a new ADateTime
	 */
	public ADateTime plusSeconds (long seconds) {
		if (seconds < 0) {
			return minusSeconds(-seconds);
		}
		long totalSeconds = time.toSecondOfDay() + seconds;
		ADate newDate = date;
		if (totalSeconds >= SECONDS_DAY) {
			newDate = date.plusDays(totalSeconds / SECONDS_DAY);
		}
		ATime newTime = time.plusSeconds(seconds);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of nanoseconds added to it.
	 * 
	 * @param nanos the number of nanoseconds to add
	 * @return a new ADateTime
	 */
	public ADateTime plusNanos (long nanos) {
		if (nanos < 0) {
			return minusNanos(-nanos);
		}
		long totalNanos = time.toNanoOfDay() + nanos;
		ADate newDate = date;
		if (totalNanos >= NANOS_DAY) {
			newDate = date.plusDays(totalNanos / NANOS_DAY);
		}
		ATime newTime = time.plusNanos(nanos);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of years subtracted from it.
	 * 
	 * @param years the number of years to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusYears (long years) {
		return of(date.minusYears(years), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of months subtracted from it.
	 * 
	 * @param months the number of months to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusMonths (long months) {
		return of(date.minusMonths(months), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of days subtracted from it.
	 * 
	 * @param days the number of days to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusDays (long days) {
		return of(date.minusDays(days), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of weeks subtracted from it.
	 * 
	 * @param weeks the number of weeks to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusWeeks (long weeks) {
		return of(date.minusDays(7 * weeks), time);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of hours subtracted from it.
	 * 
	 * @param hours the number of hours to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusHours (long hours) {
		if (hours < 0) {
			return plusHours(-hours);
		}
		long totalHours = time.getHour() - hours;
		ADate newDate = date;
		if (totalHours < 0) {
			newDate = date.minusDays(1 - totalHours / HOURS_DAY);
		}
		ATime newTime = time.minusHours(hours);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of minutes subtracted from it.
	 * 
	 * @param minutes the number of minutes to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusMinutes (long minutes) {
		if (minutes < 0) {
			return plusMinutes(-minutes);
		}
		long totalMinutes = time.getHour() * MINUTES_HOUR + time.getMinute() - minutes;
		ADate newDate = date;
		if (totalMinutes < 0) {
			newDate = date.minusDays(1 - totalMinutes / MINUTES_DAY);
		}
		ATime newTime = time.minusMinutes(minutes);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of seconds subtracted from it.
	 * 
	 * @param seconds the number of seconds to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusSeconds (long seconds) {
		if (seconds < 0) {
			return plusSeconds(-seconds);
		}
		long totalSeconds = time.toSecondOfDay() - seconds;
		ADate newDate = date;
		if (totalSeconds < 0) {
			newDate = date.minusDays(1 - totalSeconds / SECONDS_DAY);
		}
		ATime newTime = time.minusSeconds(seconds);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a copy of this ADateTime with a number of nanoseconds subtracted from it.
	 * 
	 * @param nanos the number of nanoseconds to subtract
	 * @return a new ADateTime
	 */
	public ADateTime minusNanos (long nanos) {
		if (nanos < 0) {
			return plusNanos(-nanos);
		}
		long totalNanos = time.toNanoOfDay() - nanos;
		ADate newDate = date;
		if (totalNanos < 0) {
			newDate = date.minusDays(1 - totalNanos / NANOS_DAY);
		}
		ATime newTime = time.minusNanos(nanos);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns true if this ADateTime is equal to another ADateTime.
	 * 
	 * @param other the ADateTime to compare
	 * @return true if they are equal
	 */
	public boolean equals (ADateTime other) {
		return compareTo(other) == 0;
	}
	
	/**
	 * Compares this ADateTime with another ADateTime and returns an integer that indicates
	 * if this is less than, greater than or equal to the other.
	 * 
	 * @param other the ADateTime to compare
	 * @return an integer that is less than, greater than or equal to zero
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo (ADateTime other) {
		int cmp = date.compareTo(other.date);
		if (cmp == 0) {
			cmp = time.compareTo(other.time);
		}
		return cmp;
	}
	
	/**
	 * Returns true if the value of this ADateTime comes after (is greater than) another
	 * ADateTime.
	 * 
	 * @param other the ADateTime to compare
	 * @return true if this comes after the other
	 */
	public boolean isAfter (ADateTime other) {
		return compareTo(other) > 0;
	}
	
	/**
	 * Returns true if the value of this ADateTime comes before (is less than) another
	 * ADateTime.
	 * 
	 * @param other the ADateTime to compare
	 * @return true if this comes after the other
	 */
	public boolean isBefore (ADateTime other) {
		return compareTo(other) < 0;
	}
	
	/**
	 * Returns true if the value of this ADateTime is equal to another ADateTime.
	 * 
	 * @param other the ADateTime to compare
	 * @return true if this is equal to the other
	 */
	public boolean isEqual (ADateTime other) {
		return compareTo(other) == 0;
	}
	
	/**
	 * Returns a new ADateTime from a {@code java.util.Date}.
	 * 
	 * @param date the {@code java.util.Date}
	 * @return a new ADateTime;
	 */
	public static ADateTime from (Date date) {
		ADate newDate = ADate.from(date);
		ATime newTime = ATime.from(date);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a {@code java.util.Date} from this ADateTime.
	 * 
	 * @return a {@code java.util.Date}
	 */
	public Date toDate () {
		Calendar calendar = toCalendar();
		return calendar.getTime();
	}
	
	/**
	 * Returns a new ADateTime from a {@code java.util.Calendar}.
	 * 
	 * @param calendar the {@code java.util.Calendar}
	 * @return a new ADateTime
	 */
	public static ADateTime from (Calendar calendar) {
		ADate newDate = ADate.from(calendar);
		ATime newTime = ATime.from(calendar);
		return of(newDate, newTime);
	}
	
	/**
	 * Returns a {@code java.util.Calendar} from this ADateTime.
	 * 
	 * @return a {@code java.util.Calendar}
	 */
	public Calendar toCalendar () {
		Calendar calDate = date.toCalendar();
		Calendar calTime = time.toCalendar();
		calTime.set(calDate.get(Calendar.YEAR),
				calDate.get(Calendar.MONTH),
				calDate.get(Calendar.DAY_OF_MONTH));
		return calTime;
	}
	
	/**
	 * Returns an ADate from this ADateTime.
	 * 
	 * @return the ADate
	 */
	public ADate toADate () {
		return date;
	}
	
	/**
	 * Returns an ATime from this ADateTime.
	 * 
	 * @return the ATime
	 */
	public ATime toATime () {
		return time;
	}
	
	/**
	 * Returns a String representation for this ADateTime.
	 * The format is based on the ISO-8601 standard.
	 * <blockquote><code>
	 * yyyy-mm-ddThh:mm:ss.nano
	 * </code></blockquote>
	 * 
	 * @return the date-time String
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return date.toString() + "T" + time.toString();
	}

}
