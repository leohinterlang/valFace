/**
 *+
 *	ValSample.java
 *	1.0.0  2014-08-22  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fidelis.argface.ArgFace;
import com.fidelis.argface.ArgPrototype;
import com.fidelis.argface.Debug;

/**
 * ValFace sample program.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */
public class ValSample {
	
	private final String[] usageText = {
			"Usage:",
				"ValSample [-a <dob>] [-t <tod>] <name> <value> <regex>"
	};
	
	private final String[] valText = {
			"Values:",
				"<name> someString ignoreCase = one | two | three | four | five;",
				"<value> int someNumber = 1 .. 99 | 101,000 | 201.8 |",
					"301 | 4700 | 0xFF | #ABCDEF | 0376;",
				"<regex> String regex = @^[a-z]\\w*$ | true | false ;",
				"-a date dob = today.minus.100.years..today.plus.1.month |",
					"1980.plus.1.month | 123.4.5 | 2014 | 5.6.7.plus.2000.years;",
				"-t time tod = noon .. noon.plus.6.hours | 10:30:00.999999999 |",
					"midnight.minute.30.second.49.nano.123456789|",
					"noon.nano.201201 | noon.nano.234 | now.am | now.pm;"
	};
	
	private ValParser parser;
	
	private boolean aOption;
	private String aDob;
	private boolean tOption;
	private String tTod;
	
	private String nameOperand;
	private String valueOperand;
	private String regexOperand;
	
	private String someString;
	private Integer someNumber;
	private String regex;
	private Calendar dob;
	private ATime tod;

	/**
	 * @param args
	 */
	public static void main (String... args) {
		// Debug.setTrace(true);
		// Debug.setVerbose(true);
		ValSample prog = new ValSample();
		if (! prog.processArguments(args)) {
			System.exit(1);
		}
	}
	
	private boolean processArguments (String... args) {
		ArgFace argFace = ArgPrototype.create(usageText, this);
		if (argFace == null) {
			return false;
		}
		int nArg = argFace.parse(args);
		if (nArg < 0) {
			return false;
		}
		ValFace valFace = ValPrototype.create(valText, this);
		if (valFace == null) {
			return false;
		}
		if (! valFace.validate(argFace)) {
			return false;
		}
		System.out.println("someString: " + someString);
		System.out.println("someNumber: " + someNumber);
		System.out.println("regex: " + regex);
		if (dob != null) {
			Date dobDate = dob.getTime();
			DateFormat fmt = DateFormat.getDateInstance();
			System.out.println("dob: " + fmt.format(dobDate));
		}
		if (tod != null) {
			System.out.println("tod: " + tod);
		}
		return true;
	}

}
