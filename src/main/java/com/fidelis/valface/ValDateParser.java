/**
 *+
 *	ValDateParser.java
 *	1.0.0  Sep 9, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.util.Calendar;
import com.fidelis.argface.Debug;

/**
 * Validation Interface Date Parser.
 * 
 * <blockquote>
 * <dl><dt>Format: &lt;basic-date&gt;[.&lt;modifier&gt;]...</dt></dl>
 * <p>
 * <dl>
 * <dt>&lt;basic-date&gt;:</dt>
 * <dd>A basic-date is the starting point for expressing a date.
 * The modifiers allow variations to what has already been specified.
 * The dash "-" used between numeric fields of a date may also be
 * specified with the dot "." character.
 * But it is invalid to switch between one and the other.
 * Don't use "2010-10.10 or "1892.12-25" forms; those won't work.</dd>
 * </dl>
 * <blockquote>
 * <dl>
 * <dt>YYYY-MM-DD</dt>
 * <dd>Standard ISO-8601 format.<br>
 * <code>2014-09-18</code></dd>
 * 
 * <dt>YYYY-MM</dt>
 * <dd>First day of given Year and Month.<br>
 * <code>2014-09 => 2014-09-01</code></dd>
 * 
 * <dt>YYYY</dt>
 * <dd>January 1st of the specified Year.<br>
 * <code>2014 => 2014-01-01</code></dd>
 * 
 * <dt>YYYY-&lt;Month&gt;-DD</dt>
 * <dd>The given month name or 3 letter abbreviation.<br>
 * <code>2014-September-18<br>
 * 2014-Sep-18</code></dd>
 * 
 * <dt>YYYYMMDD</dt>
 * <dd>Compact ISO-8601 format.<br>
 * <code>20140918</code></dd>
 * 
 * <dt>YYYY.end</dt>
 * <dd>The end of the specified year.<br>
 * <code>2014.end => 2014-12-31</code></dd>
 * 
 * <dt>YYYY.MM.end</dt>
 * <dd>The end of the month for the given year.<br>
 * <code>2000.Feb.end => 2000-02-29<br>
 * 1900.02.end => 1900-02-28</code></dd>
 * 
 * <dt>today</dt>
 * <dd>Today's date.<br>
 * <code>2014-09-18</code></dd>
 * 
 * <dt>tomorrow</dt>
 * <dd>The day after today's date.<br>
 * <code>2014-09-19</code></dd>
 * 
 * <dt>yesterday</dt>
 * <dd>The day before today's date.<br>
 * <code>2014-09-17</code></dd>
 * 
 * <dt>[this.]&lt;entity&gt;<dt>
 * <dd>The current entity.<br>
 * <code>this.year => 2014-01-01<br>
 * week => 2014-09-15<br>
 * Wednesday => 2014-09-17</code></dd>
 * 
 * <dt>next.&lt;entity&gt;<dt>
 * <dd>The next entity.<br>
 * <code>next.year => 2015-01-01<br>
 * next.week => 2014-09-22<br>
 * next.Wednesday => 2014-09-24</code></dd>
 * 
 * <dt>last.&lt;entity&gt;</dt>
 * <dd>The previous entity.<br>
 * <code>last.year => 2013-01-01<br>
 * last.week => 2014-09-08<br>
 * last.Wednesday => 2014-09-10</code></dd>
 * 
 * </dl>
 * <p>
 * <dl>
 * <dt>&lt;entity&gt;:</dt>
 * <dd>An entity defines a fixed date relative to the current date.
 * They have a confined range with a starting and ending point.
 * Each entity has a bracketing effect set by the current date.
 * <p>
 * For example, a week is defined as starting on Monday and ending on Sunday.
 * Given today's date, as a Thursday, the "week" entity refers to
 * the set of days starting with the Monday before and ending with the Sunday after.
 * The term "this.Tuesday" refers to the Tuesday of the current week.
 * It does not mean the next sequential Tuesday in the future.</dd>
 * </dl>
 * <blockquote>
 * <dl>
 * <dt>year</dt>
 * <dd>Specifies an annual entity.
 * It starts on January 1st and ends on December 31st.<br>
 * <code>this.year => 2014-01-01<br>
 * next.year => 2015-01-01<br>
 * last.year => 2013-01-01</code></dd>
 * 
 * <dt>month</dt>
 * <dd>Specifies a monthly entity.
 * It starts with the 1st day of the month and ends with the last day of the month.<br>
 * <code>this.month => 2014-09-01<br>
 * next.month => 2014-10-01<br>
 * last.month => 2014-08-01</code></dd>
 * 
 * <dt>week</dt>
 * <dd>Specifies a weekly entity.
 * It starts on a Monday and ends on a Sunday.<br>
 * <code>this.week => 2014-09-15<br>
 * next.week => 2014-09-22<br>
 * last.week => 2014-09-08</code></dd>
 * <dt>&lt;month-name&gt;<dt>
 * <dd>Specifies a particular month entity.<br>
 * <code>this.November => 2014-11-01<br>
 * next.May => 2015-05-01<br>
 * last.Jan => 2013-01-01</code></dd>
 * <dt>&lt;day-of-week&gt;</dt>
 * <dd>Specifies a particular day of the week entity.<br>
 * <code>this.Wednesday => 2014-09-17<br>
 * next.Saturday => 2014-09-27<br>
 * last.Tue => 2014-09-09</code></dd>
 * </dl>
 * </blockquote>
 * </blockquote>
 * <dl>
 * <dt>&lt;modifier&gt;:</dt>
 * <dd>Modifiers make changes to a basic date.</dd>
 * </dl>
 * <blockquote>
 * <dl>
 * <dt>plus.&lt;number&gt;.&lt;units&gt;</dt>
 * <dd>Adds the &lt;number&gt; of &lt;units&gt; to a &lt;basic-date&gt;.<br>
 * <code>2014.plus.8.months => 2014-09-01<br>
 * 2014.plus.8.months.plus.17.days => 2014-09-18</code></dd>
 * <dt>minus.&lt;number&gt;.&lt;units&gt;</dt>
 * <dd>Subtracts the &lt;number&gt; of &lt;units&gt; from a &lt;basic-date&gt;.<br>
 * <code>2014-09-18.minus.8.months => 2014-01-18<br>
 * 2014-09-18.minus.8.months.minus.17.days => 2014-01-01</code></dd>
 * <dt>year.&lt;number&gt;</dt>
 * <dd>Changes the year to the given &lt;number&gt;.<br>
 * <code>today.year.2000 => 2000-09-18<br>
 * last.month.year.1890 => 1890-08-01</code></dd>
 * <dt>month.&lt;number/name&gt;</dt>
 * <dd>Changes the month to the given &lt;number/name&gt;.<br>
 * <code>today.month.April => 2014-04-18<br>
 * last.year.month.3 => 2013-03-01</code></dd>
 * <dt>day.&lt;number&gt;</dt>
 * <dd>Changes the day of the month to the given &lt;number&gt;.<br>
 * <code>today.day.20 => 2014-09-20<br>
 * next.November.day.11 => 2015-11-11</code></dd>
 * </dl>
 * <p>
 * <dl><dt>&lt;units&gt;:</dt></dl>
 * <blockquote>
 * <dl>
 * <dt>year[s]</dt>
 * <dt>month[s]</dt>
 * <dt>week[s]</dt>
 * <dt>day[s]</dt>
 * </dl>
 * </blockquote>
 * </blockquote>
 * </blockquote>
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ValDateParser {
	
	private static final ValDateParser INSTANCE = new ValDateParser();
	private static boolean traditionalMode = false;
	
	private static final String[] monthNames = {
		"january", "february", "march",
		"april", "may", "june",
		"july", "august", "september",
		"october", "november", "december"
	};
	private static final String[] dayNames = {
		"monday", "tuesday", "wednesday", "thursday",
		"friday", "saturday", "sunday"
	};
	
	private boolean kw_end;
	private boolean endMonth;
	
	// Entity flags.
	private boolean entityYear;
	private boolean entityMonth;
	private boolean entityWeek;
	private boolean entityMonthName;
	private boolean entityDowName;
	
	private String dateSpec;
	private String failMessage;
	private Integer number;
	
	private String[] parts;
	private int partIndex;
	private String part;
	private int month;
	private int dow;
	private ADate aDate;
	
	/**
	 * Returns a {@code Calendar} representation for a date string.
	 * 
	 * @param dateSpec the date specification
	 * @return a Calendar representation of the date
	 * @throws IllegalArgumentException for parsing and semantic errors
	 */
	public static Calendar parse (String dateSpec) throws IllegalArgumentException {
		return INSTANCE.parseDate(dateSpec);
	}
	
	/**
	 * Sets the traditional mode of operation as specified.
	 * With this mode enabled, the days that compose a week take on
	 * their traditional Gregorian calendar meaning. That is, a week begins
	 * on a Sunday and ends on a Saturday.
	 * 
	 * @param traditional true to enable this mode of operation
	 */
	public static void setTraditional (boolean traditional) {
		traditionalMode = traditional;
	}
	
	/**
	 * Returns true if traditional mode is enabled.
	 * 
	 * @return true if traditional mode is enabled
	 */
	public static boolean isTraditional () {
		return traditionalMode;
	}
	
	/**
	 * Returns a {@code Calendar} representation for a date string.
	 * 
	 * @param dateSpec the date specification
	 * @return a Calendar representation of the date
	 * @throws IllegalArgumentException for parsing and semantic errors
	 */
	private Calendar parseDate (String dateSpec) throws IllegalArgumentException {
		this.dateSpec = dateSpec;
		
		// Indicate no "end" keyword encountered.
		kw_end = false;
		endMonth = false;
		
		// Start with today's date.
		aDate = ADate.now();
		
		// Break the date specification into parts.
		parts = dateSpec.split("\\.");
		partIndex = 0;
		
		// Parse initial parts.
		while (nextPart()) {
			
			// Today with possible modifiers.
			if (part.equals("today")) {
				disallowEnd();
				modifiers();
			}
			
			// Yesterday with possible modifiers.
			else if (part.equals("yesterday")) {
				aDate = aDate.plusDays(-1);
				disallowEnd();
				modifiers();
			}
			
			// Tomorrow with possible modifiers.
			else if (part.equals("tomorrow")) {
				aDate = aDate.plusDays(1);
				disallowEnd();
				modifiers();
			}
			
			// Next specification.
			else if (part.equals("next")) {
				nextSpec();
				modifiers();
			}
			
			// Last specification.
			else if (part.equals("last")) {
				lastSpec();
				modifiers();
			}
			// This specification.
			else if (part.equals("this")) {
				thisSpec(null);
				modifiers();
			}
			
			// The "end" keyword.
			else if (part.equals("end")) {
				kw_end = true;
			}
			
			// Short form of this.year.
			else if (part.equals("year")) {
				thisSpec(part);
				modifiers();
			}
			
			// Short form of this.month.
			else if (part.equals("month")) {
				thisSpec(part);
				modifiers();
			}
			
			// Short form of this.week.
			else if (part.equals("week")) {
				thisSpec(part);
				modifiers();
			}
			
			// Short form of this.<month-name>.
			else if ((monthName(part)) != 0) {
				thisSpec(part);
				modifiers();
			}
			
			// Short form of this.<dow-name>.
			else if ((dowName(part)) != 0) {
				thisSpec(part);
				modifiers();
			}
			
			// Try parsing a numeric date.
			else {
				String theDate = composeDate();
				if (theDate == null) {
					invalidDate(dateSpec);
				}
				aDate = ADate.parse(theDate);
				if (endMonth) {
					int lastDay = aDate.lengthOfMonth();
					aDate = aDate.withDay(lastDay);
				}
				modifiers();
			}
		}
		Debug.verbose("Result date: " + aDate);
		return aDate.toCalendar();
	}
	
	private void disallowEnd () throws IllegalArgumentException {
		if (kw_end) {
			invalidDate(dateSpec);
		}
	}
	
	private void invalidDate (String text) throws IllegalArgumentException {
		throw new IllegalArgumentException("Invalid date specification: " + text);
	}
	
	private String composeDate () {
		String year = "0000";
		String month = "01";
		String day = "01";
		
		// Can be YYYY-MM-DD or YYYY-MM.
		// Also allow YYYY-end or YYYY-MM-end.
		if (part.contains("-")) {
			String[] dateParts = part.split("\\-");
			if (dateParts.length > 0) {
				year = dateParts[0];
				if (! year.matches("\\d{1,4}")) {
					return null;
				}
			}
			if (dateParts.length > 1) {
				month = dateParts[1].toLowerCase();
				if (isEnd(month)) {
					month = "12";
					day = "31";
				}
				else if (! month.matches("\\d{1,2}")) {
					int m = monthName(month);
					if (m != 0) {
						month = String.valueOf(m);
					} else {
						return null;
					}
				}
			}
			if (dateParts.length > 2) {
				day = dateParts[2].toLowerCase();
				if (isEnd(day)) {
					endMonth = true;
					day = "01";
				}
				else if (! day.matches("\\d{1,2}")) {
					return null;
				}
				else {
					disallowEnd();
				}
			}
			
			// No day part: YYYY-MM.
			else {
				
				// Could be end.YYYY-MM
				if (kw_end) {
					endMonth = true;
				}
				
				// Check for YYYY-MM.end" from next part.
				else if (nextPart()) {
					if (isEnd(part)) {
						endMonth = true;
					} else {
						--partIndex;
					}
				}
			}
		}
		
		// YYYYMMDD format. Requires 8 digits.
		else if (part.matches("\\d{8}")) {
			return part;
		}
		
		// Just YYYY. Allow 1 to 4 digits.
		else if (part.matches("\\d{1,4}")) {
			year = part;
			
			// No next part. Could be end.YYYY
			if (! nextPart()) {
				if (kw_end) {
					month = "12";
					day = "31";
				}
			}
			
			// Part following YYYY.
			else {
				
				// Allow month name: full or abbreviated.
				int m = monthName(part);
				if (m != 0) {
					part = String.valueOf(m);
				}
				
				// YYYY.end = December 31.
				if (isEnd(part)) {
					month = "12";
					day = "31";
				}
				
				// Not month number.
				else if (! part.matches("\\d{1,2}")) {
					--partIndex;
					
					// end.YYYY.something
					if (kw_end) {
						month = "12";
						day = "31";
					}
				}
				
				// YYYY.MM
				else {
					month = part;
					
					// No next part. Could be end.YYYY.MM
					if (! nextPart()) {
						if (kw_end) {
							endMonth = true;
						}
					}
					
					// Next part following YYYY.MM
					else {
						
						// YYYY.MM.end
						if (isEnd(part)) {
							endMonth = true;
						}
						
						// Not day number.
						else if (! part.matches("\\d{1,2}")) {
							--partIndex;
							
							// end.YYYY.MM.something
							if (kw_end) {
								endMonth = true;
							}
						}
						
						// YYYY.MM.DD
						else {
							day = part;
							disallowEnd();
						}
					}
				}
			}
		}
		else {
			return null;
		}
		return year + "-" + month + "-" + day;
	}
	
	private boolean isEnd (String text) throws IllegalArgumentException {
		if (text.equals("end")) {
			disallowEnd();
			return true;
		}
		return false;
	}
	
	/**
	 * Process date modifiers.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void modifiers () throws IllegalArgumentException {
		
		// Modifier loop.
		while (nextPart()) {
			
			// Plus modifier.
			if (part.equals("plus")) {
				plusModifier();
			}
			
			// Minus modifier.
			else if (part.equals("minus")) {
				minusModifier();
			}
			
			// Year modifier.
			else if (part.equals("year")) {
				yearModifier();
			}
			
			// Month modifier.
			else if (part.equals("month")) {
				monthModifier();
			}
			
			// Day modifier.
			else if (part.equals("day")) {
				dayModifier();
			}
			
			// Otherwise, throw exception.
			else {
				invalidDate(dateSpec);
			}
		}
	}
	
	/**
	 * Process plus modifier.
	 * Format: .plus.number.units
	 * 
	 * @throws IllegalArgumentException
	 */
	private void plusModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"plus\" modifier");
		getNumber();
		completeNumberUnits();
	}
	
	/**
	 * Process minus modifier.
	 * Format: .minus.number.units
	 * 
	 * @throws IllegalArgumentException
	 */
	private void minusModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"minus\" modifier");
		getNumber();
		number = -number;
		completeNumberUnits();
	}
	
	/**
	 * Process year modifier.
	 * Format: .year.number
	 * 
	 * @throws IllegalArgumentException
	 */
	private void yearModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"year\" modifier");
		getNumber();
		aDate = aDate.withYear(number);
	}
	
	/**
	 * Process month modifier.
	 * Format: .month.number/name
	 * 
	 * @throws IllegalArgumentException
	 */
	private void monthModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"month\" modifier");
		nextPartOrFail("number/name");
		number = monthName(part);
		if (number == 0) {
			getNumber(part);
		}
		aDate = aDate.withMonth(number);
	}
	
	/**
	 * Process day modifier.
	 * Format: .day.number
	 * 
	 * @throws IllegalArgumentException
	 */
	private void dayModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"day\" modifier");
		getNumber();
		aDate = aDate.withDay(number);
	}
	
	/**
	 * Process "next" specification.
	 * Format: next.entity
	 * 
	 * @throws IllegalArgumentException
	 */
	private void nextSpec () throws IllegalArgumentException {
		setFailMessage("Bad \"next\" specification");
		getEntity();
		if (entityYear) {
			aDate = aDate.plusYears(1);
			completeYear();
		}
		else if (entityMonth) {
			aDate = aDate.plusMonths(1);
			completeMonth();
		}
		else if (entityWeek) {
			completeWeek(8 - dayOfWeek(aDate));
		}
		else if (entityMonthName) {
			aDate = aDate.plusYears(1).withMonth(month);
			completeMonth();
		}
		else if (entityDowName) {
			aDate = aDate.plusDays(7 + dow - dayOfWeek(aDate));
		}
	}
	
	/**
	 * Process "last" specification.
	 * Format: last.entity
	 * 
	 * @throws IllegalArgumentException
	 */
	private void lastSpec () throws IllegalArgumentException {
		setFailMessage("Bad \"last\" specification");
		getEntity();
		if (entityYear) {
			aDate = aDate.plusYears(-1);
			completeYear();
		}
		else if (entityMonth) {
			aDate = aDate.plusMonths(-1);
			completeMonth();
		}
		else if (entityWeek) {
			completeWeek(-7 - (dayOfWeek(aDate) - 1));
		}
		else if (entityMonthName) {
			aDate = aDate.plusYears(-1).withMonth(month);
			completeMonth();
		}
		else if (entityDowName) {
			aDate = aDate.plusDays(-8 - (dayOfWeek(aDate) - 1) + dow);
		}
	}
	
	/**
	 * Process "this" specification.
	 * Format: this.entity
	 * 
	 * @param entity the entity specifier or null
	 * @throws IllegalArgumentException
	 */
	private void thisSpec (String entity) throws IllegalArgumentException {
		setFailMessage("Bad \"this\" specification");
		if (entity == null) {
			nextPartOrFail("<entity>");
			entity = part;
		}
		getEntity(part);
		if (entityYear) {
			completeYear();
		}
		else if (entityMonth) {
			completeMonth();
		}
		else if (entityWeek) {
			completeWeek(- (dayOfWeek(aDate) - 1));
		}
		else if (entityMonthName) {
			aDate = aDate.withMonth(month);
			completeMonth();
		}
		else if (entityDowName) {
			aDate = aDate.plusDays(-1 - (dayOfWeek(aDate) - 1) + dow);
		}
	}
	
	/**
	 * Retrieve the entity specifier.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void getEntity () throws IllegalArgumentException {
		nextPartOrFail("<entity>");
		getEntity(part);
	}
	
	/**
	 * Sets the entity specifier variables.
	 * Format: year | month | week | month-name | dow-name
	 * 
	 * @param entity the entity specifier
	 * @throws IllegalArgumentException
	 */
	private void getEntity (String entity) throws IllegalArgumentException {
		entityYear = false;
		entityMonth = false;
		entityWeek = false;
		entityMonthName = false;
		entityDowName = false;
		if (entity.equals("year")) {
			entityYear = true;
		}
		else if (entity.equals("month")) {
			entityMonth = true;
		}
		else if (entity.equals("week")) {
			entityWeek = true;
		}
		else if ((month = monthName(entity)) != 0) {
			entityMonthName = true;
		}
		else if ((dow = dowName(entity)) != 0) {
			entityDowName = true;
		}
		else {
			throw new IllegalArgumentException(
					failMessage + ": invalid <entity>: " + entity);
		}
	}
	
	/**
	 * Retrieve the number specifier.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void getNumber () throws IllegalArgumentException {
		nextPartOrFail("<number>");
		getNumber(part);
	}
	
	/**
	 * Convert the number specifier to an Integer.
	 * 
	 * @param part the number specifier
	 * @throws IllegalArgumentException
	 */
	private void getNumber (String part) throws IllegalArgumentException {
		try {
			number = Integer.valueOf(part);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
					failMessage + ": invalid <number>: " + part);
		}
	}
	
	/**
	 * Completes the plus/minus modifiers according to the number and units.
	 * Format: .number.units
	 * Units: year[s] | month[s] | week[s] | day[s]
	 * If the units are missing or is invalid, an exception is thrown.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void completeNumberUnits () throws IllegalArgumentException {
		nextPartOrFail("<units>");
		if (plurals(part, "year")) {
			aDate = aDate.plusYears(number);
		}
		else if (plurals(part, "month")) {
			aDate = aDate.plusMonths(number);
		}
		else if (plurals(part, "week")) {
			aDate = aDate.plusDays(number * 7);
		}
		else if (plurals(part, "day")) {
			aDate = aDate.plusDays(number);
		}
		else {
			throw new IllegalArgumentException(
					failMessage + ": invalid units: " + part);
		}
	}
	
	/**
	 * Completes year modifiers setting the month and day according to
	 * the occurrence of the "end" keyword.  If the "end" keyword is not
	 * present, the value January 1 is set. Otherwise, the value December 31
	 * is set.
	 */
	private void completeYear () {
		int month = 1;
		int day = 1;
		if (endSpec()) {
			month = 12;
			day = 31;
		}
		aDate = aDate.withMonth(month).withDay(day);
	}
	
	/**
	 * Completes month modifiers setting the day according to the
	 * occurrence of the "end" keyword.  If the "end" keyword is not
	 * present, the first of the month is set. Otherwise the last day
	 * of the month is set.
	 */
	private void completeMonth () {
		int day = 1;
		if (endSpec()) {
			day = aDate.lengthOfMonth();
		}
		aDate = aDate.withDay(day);
	}
	
	/**
	 * Completes week modifiers setting the day of the week according to
	 * the occurrence of the "end" keyword.  If the "end" keyword is not
	 * present, the date is set to correspond with the beginning of the week;
	 * Monday. Otherwise the date is set to correspond with the end of the
	 * week; Sunday.
	 *  
	 * @param days the number of days to adjust the date
	 */
	private void completeWeek (int days) {
		if (endSpec()) {
			days += 6;
		}
		aDate = aDate.plusDays(days);
	}
	
	/**
	 * Returns true if the "end" keyword is encountered in the specification.
	 * The "end" keyword may appear at the beginning or the end of the "entity"
	 * specifiers such as "next.year.end" or "end.next.year".
	 * 
	 * @return true if the "end" keyword is present
	 */
	private boolean endSpec () {
		
		// No "end" keyword so far. Check the next part.
		if (! kw_end) {
			if (nextPart()) {
				
				// Next part is "end" keyword.
				if (part.equals("end")) {
					kw_end = true;
				}
				
				// Not "end" keyword. Back off the part index.
				else {
					--partIndex;
				}
			}
		}
		return kw_end;
	}
	
	/**
	 * Sets the "failMessage" for use in generating the exception message.
	 * 
	 * @param message the fail message string
	 */
	private void setFailMessage (String message) {
		failMessage = message;
	}
	
	/**
	 * Returns the next part of the original input text or fails.
	 * If there is no next part, this method will throw an exception.
	 * The "failMessage" and the given "missing" string are used to
	 * generate the exception message.
	 * 
	 * @param missing the expected missing item name
	 */
	private void nextPartOrFail (String missing) {
		if (! nextPart()) {
			throw new IllegalArgumentException(failMessage + ": missing " + missing);
		}
	}
	
	/**
	 * Returns true if there is another part to the original input text.
	 * Sets the "part" string with the next value in all lower case.
	 * The "partIndex" is used to access the next "part". The "partIndex"
	 * is then incremented in preparation for the next call to this method.
	 * If there are no more parts, the value false is returned.
	 * 
	 * @return true if there is another part
	 */
	private boolean nextPart () {
		if (partIndex < parts.length) {
			part = parts[partIndex].toLowerCase();
			Debug.verbose("part[" + partIndex + "] : " + part);
			++partIndex;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the units matches the text or the text with an "s".
	 * 
	 * @param units the name to match
	 * @param text the name to be pluralized
	 * @return true if the units match the text or the pluralized text
	 */
	private boolean plurals (String units, String text) {
		if (units.equals(text)) {
			return true;
		}
		return units.equals(text + "s");
	}
	
	private int dayOfWeek (ADate aDate) {
		int dow = aDate.dayOfWeek();
		if (isTraditional()) {
			dow += 1;
			if (dow == 8) {
				dow = 1;
			}
		}
		return dow;
	}
	
	/**
	 * Returns the number for a given day of the week name.
	 * Monday = 1 and Sunday = 7.
	 * Day name abbreviations of 3 characters will also work.
	 * If the name is not a day of the week, 0 is returned.
	 * 
	 * @param name the day of the week name or abbreviation
	 * @return the dow number or 0 if there is no match
	 */
	private int dowName (String name) {
		int dayNumber = 0;
		if (isTraditional()) {
			dayNumber = 1;
		}
		boolean found = false;
		for (String day : dayNames) {
			++dayNumber;
			if (day.equals(name)) {
				found = true;
				break;
			}
			else if (day.substring(0, 3).equals(name)) {
				found = true;
				break;
			}
		}
		if (! found) {
			dayNumber = 0;
		}
		else if (isTraditional()) {
			if (dayNumber == 8) {
				dayNumber = 1;
			}
		}
		return dayNumber;
	}
	
	/**
	 * Returns the number for a given month name.
	 * Month name abbreviations of 3 characters will also work.
	 * If the name is not a month name, the value 0 is returned.
	 * 
	 * @param name the month name or abbreviation
	 * @return the month number or 0 if there is no match
	 */
	private int monthName (String name) {
		int monthNumber = 0;
		for (String month : monthNames) {
			++monthNumber;
			if (month.equals(name)) {
				return monthNumber;
			}
			else if (month.substring(0, 3).equals(name)) {
				return monthNumber;
			}
		}
		return 0;
	}

}
