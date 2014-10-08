/**
 *+
 *	ValTimeParser.java
 *	1.0.0  Oct 4, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import com.fidelis.argface.Debug;

/**
 * ValTimeParser
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ValTimeParser {
	
	private static final ValTimeParser INSTANCE = new ValTimeParser();
	
	private String timeSpec;
	private String failMessage;
	
	private String[] parts;
	private int partIndex;
	private String part;
	
	private ATime aTime;
	
	public static ATime parse (String timeSpec) throws IllegalArgumentException {
		return INSTANCE.parseTime(timeSpec);
	}
	
	public ATime parseTime (String timeSpec) throws IllegalArgumentException {
		
		// Copy original specification.
		this.timeSpec = timeSpec;
		
		// Start with the current time.
		aTime = ATime.now();

		// Break the time specification into parts.
		parts = timeSpec.split("\\.");
		partIndex = 0;
		
		// Parse initial parts.
		while (nextPart()) {
			
			// Now with possible modifiers.
			if (part.equals("now")) {
				modifiers();
			}
			
			// Noon.
			else if (part.equals("noon")) {
				aTime = ATime.of(12, 0);
				modifiers();
			}
			
			// Midnight.
			else if (part.equals("midnight")) {
				aTime = ATime.of(0, 0);
				modifiers();
			}
			
			// Try parsing a time string.
			else {
				String timeString = composeTime();
				if (timeString == null) {
					invalidTime(null);
				}
				aTime = ATime.parse(timeString);
				modifiers();
			}
		}
		Debug.verbose("Result time: " + aTime);
		return aTime;
	}
	
	private String composeTime () throws IllegalArgumentException {
		String timeString = part;
		if (nextPart()) {
			
			// Time spec includes nanos: 1 to 9 digits.
			if (part.matches("\\d{1,9}")) {
				timeString += "." + part;
			}
			else {
				--partIndex;
			}
		}
		return timeString;
	}
	
	private void modifiers () throws IllegalArgumentException {
		
		// Modifiers loop.
		while (nextPart()) {
			
			// Plus modifier.
			if (part.equals("plus")) {
				plusModifier();
			}
			
			// Minus modifier.
			else if (part.equals("minus")) {
				minusModifier();
			}
			
			// Hour modifier.
			else if (part.equals("hour")) {
				hourModifier();
			}
			
			// Minute modifier.
			else if (part.equals("minute")) {
				minuteModifier();
			}
			
			// Second modifier.
			else if (part.equals("second")) {
				secondModifier();
			}
			
			// Nano modifier.
			else if (part.equals("nano")) {
				nanoModifier();
			}
			
			// AM modifier.
			else if (part.equals("am")) {
				amModifier();
			}
			
			// PM modifier.
			else if (part.equals("pm")) {
				pmModifier();
			}
			
			// Unrecognized modifier.
			else {
				invalidTime(null);
			}
		}
	}
	
	private void plusModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"plus\" modifier");
		long number = getLongNumber();
		completeNumberUnits(number);
	}
	
	private void minusModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"minus\" modifier");
		long number = getLongNumber();
		completeNumberUnits(-number);
	}
	
	private void hourModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"hour\" modifier");
		int number = getNumber();
		check(number, 0, 23);
		aTime = aTime.withHour(number);
	}
	
	private void minuteModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"minute\" modifier");
		int number = getNumber();
		check(number, 0, 59);
		aTime = aTime.withMinute(number);
	}
	
	private void secondModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"second\" modifier");
		int number = getNumber();
		check(number, 0, 59);
		aTime = aTime.withSecond(number);
	}
	
	private void nanoModifier () throws IllegalArgumentException {
		setFailMessage("Bad \"nano\" modifier");
		int number = getNumber();
		check(number, 0, 999999999);
		aTime = aTime.withNano(number);
	}
	
	private void amModifier () {
		int hour = aTime.getHour();
		if (hour > 12) {
			hour -= 12;
			aTime = aTime.withHour(hour);
		}
	}
	
	private void pmModifier () {
		int hour = aTime.getHour();
		if (hour < 12) {
			hour += 12;
			aTime = aTime.withHour(hour);
		}
	}
	
	private void invalidTime (String text) throws IllegalArgumentException {
		String msg = "Invalid time specification: " + timeSpec;
		if (text != null) {
			msg += "\n    " + text;
		}
		throw new IllegalArgumentException(msg);
	}
	
	private void setFailMessage (String message) {
		failMessage = message;
	}
	
	/**
	 * Retrieve the number specifier.
	 * 
	 * @throws IllegalArgumentException
	 */
	private int getNumber () throws IllegalArgumentException {
		nextPartOrFail("<number>");
		return getNumber(part);
	}
	
	/**
	 * Convert the number specifier to an Integer.
	 * 
	 * @param numberSpec the number specifier
	 * @throws IllegalArgumentException
	 */
	private int getNumber (String numberSpec) throws IllegalArgumentException {
		try {
			return Integer.valueOf(numberSpec);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
					failMessage + ": invalid <number>: " + numberSpec);
		}
	}
	
	private long getLongNumber () throws IllegalArgumentException {
		nextPartOrFail("<number>");
		return getLongNumber(part);
	}
	
	private long getLongNumber (String part) throws IllegalArgumentException {
		try {
			return Long.valueOf(part);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
					failMessage + ": invalid <number>: " + part);
		}
	}
	
	/**
	 * Completes the plus/minus modifiers according to the number and units.
	 * Format: .number.units
	 * Units: hour[s] | minute[s] | second[s] | nano[s]
	 * If the units are missing or is invalid, an exception is thrown.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void completeNumberUnits (long number) throws IllegalArgumentException {
		nextPartOrFail("<units>");
		if (plurals(part, "hour")) {
			aTime = aTime.plusHours(number);
		}
		else if (plurals(part, "minute")) {
			aTime = aTime.plusMinutes(number);
		}
		else if (plurals(part, "second")) {
			aTime = aTime.plusSeconds(number);
		}
		else if (plurals(part, "nano")) {
			aTime = aTime.plusNanos(number);
		}
		else {
			invalidTime(failMessage + ": invalid units: " + part);
		}
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
	
	private void check (int number, int min, int max) throws IllegalArgumentException {
		if (min > number || number > max) {
			String msg = String.format("%s: %s out of range (%d .. %d)",
					failMessage, part, min, max);
			invalidTime(msg);
		}
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
			invalidTime(failMessage + ": missing " + missing);
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
}
