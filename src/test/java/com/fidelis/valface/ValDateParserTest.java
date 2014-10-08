/**
 *+
 *	ValDateParserTest.java
 *	1.0.0  Sep 20, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * ValDateParserTest
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ValDateParserTest {
	
	private boolean passed;
	private boolean setTodayFlag;
	private boolean showInfo = false;
	private boolean showError = true;
	private boolean firstCase;
	private String testCase;
	private Calendar testResult;
	
	@Rule public TestName testName = new TestName();
	
	final String[] basicDates = {
			"2014-09-18",			"2014-09-18",
			"1889-05-31",			"1889-05-31",
			"1953-09-18",			"1953-09-18",
			"1963-11-22",			"1963-11-22",
			"123-4-5",				"0123-04-05",
			"2014-9-18",			"2014-09-18",
			"1941-12-7",			"1941-12-07",
			"2000-1-1",				"2000-01-01",
			"20200331",				"2020-03-31",
			"01230405",				"0123-04-05",
			"1900.01.05",			"1900-01-05",
			"123.4.5",				"0123-04-05",
			"2001-10",				"2001-10-01",
			"2002.02",				"2002-02-01",
			"2003.March",			"2003-03-01",
			"2003.Feb.20",			"2003-02-20",
			"2004-July-4",			"2004-07-04",
			"2005-October",			"2005-10-01",
			"2010",					"2010-01-01",
			"2012.4",				"2012-04-01",
			"setToday",				"2014-09-18",
			"today",				"2014-09-18",
			"tomorrow",				"2014-09-19",
			"yesterday",			"2014-09-17",
			"year",					"2014-01-01",
			"month",				"2014-09-01",
			"week",					"2014-09-15",
			"November",				"2014-11-01",
			"April",				"2014-04-01",
			"Oct",					"2014-10-01",
			"Feb",					"2014-02-01",
			"Monday",				"2014-09-15",
			"Tuesday",				"2014-09-16",
			"Wednesday",			"2014-09-17",
			"Thursday",				"2014-09-18",
			"Friday",				"2014-09-19",
			"Saturday",				"2014-09-20",
			"Sunday",				"2014-09-21",
			"Mon",					"2014-09-15",
			"Wed",					"2014-09-17",
			"Fri",					"2014-09-19",
			"2020.end",				"2020-12-31",
			"2021.June.end",		"2021-06-30",
			"2022-end",				"2022-12-31",
			"2023-July-end",		"2023-07-31",
			"end.2024",				"2024-12-31",
			"end.2025.June",		"2025-06-30",
			"end.2026-07",			"2026-07-31",
			"end.2027-Aug",			"2027-08-31",
			"2028-09.end",			"2028-09-30"
	};
	
	final String[] basicExceptions = {
			"12345",
			"123456",
			"1234567",
			"A",
			"1A",
			"12A",
			"123A",
			"1234A",
			"12345A",
			"123456A",
			"1234567A",
			"12345678A",
			"1234-00",
			"1234-13",
			"1234-05-00",
			"1234-05-32",
			"1234-01A",
			"1234.01A",
			"1234.01.A",
			"1234.01.0A",
			"1234.06-end",
			"1234-09.18",
			"1234.05-06",
			"1234--05-06",
			"1234-05--06",
			"1234..05.06",
			"1234.05..06",
			"1234/10/11",
			"1234/",
			"end.2027-08-15",
			"end.2028.09.15",
			"end.2029.10.end",
			"end.2030.end",
			"end.2031-March.end",
			"end.2032-April-end",
			"end.2033-02-end",
			"end.2034-01.end",
			"end-2035-12-end",
			"end.2036-11-15.end"
	};
	
	final String[] modifiers = {
			"setToday",				"2014-09-18",
			"today",				"2014-09-18",
			"year",					"2014-01-01",
			"year.end",				"2014-12-31",
			"end.year",				"2014-12-31",
			"month",				"2014-09-01",
			"month.end",			"2014-09-30",
			"end.month",			"2014-09-30",
			"week",					"2014-09-15",
			"week.end",				"2014-09-21",
			"end.week",				"2014-09-21",
			"July",					"2014-07-01",
			"July.end",				"2014-07-31",
			"end.July",				"2014-07-31",
	};
	
	final String[] modExceptions = {
			"today.end",
			"end.today",
			"tomorrow.end",
			"end.tomorrow",
			"yesterday.end",
			"end.yesterday",
			"Saturday.end"
	};
	
	final String[] traditionalDates = {
			"setToday",				"2014-09-18",
			"this.week",			"2014-09-14",
			"last.week",			"2014-09-07",
			"today.plus.1.week",	"2014-09-25",
			"today.minus.1.week",	"2014-09-11",
			"Sunday",				"2014-09-14",
			"Monday",				"2014-09-15",
			"Tuesday",				"2014-09-16",
			"Wednesday",			"2014-09-17",
			"Thursday",				"2014-09-18",
			"Friday",				"2014-09-19",
			"Saturday",				"2014-09-20"
			
	};
	
	final String[] dateStrings = {
			"setToday",				"2014-09-18",
			"today",				"2014-09-18",
			"tomorrow",				"2014-09-19",
			"yesterday",			"2014-09-17",
			"today.plus.1.year",	"2015-09-18",
			"today.plus.5.years",	"2019-09-18",
			"today.minus.1.year",	"2013-09-18",
			"today.minus.5.years",	"2009-09-18",
			"today.plus.1.month",	"2014-10-18",
			"today.plus.3.months",	"2014-12-18",
			"today.plus.4.months",	"2015-01-18",
			"today.minus.1.month",	"2014-08-18",
			"today.minus.3.months",	"2014-06-18",
			"today.minus.9.months",	"2013-12-18",
			"today.plus.1.day",		"2014-09-19",
			"today.plus.7.days",	"2014-09-25",
			"today.plus.14.days",	"2014-10-02",
			"today.minus.1.day",	"2014-09-17",
			"today.minus.7.days",	"2014-09-11",
			"today.minus.14.days",	"2014-09-04",
			"today.plus.1.week",	"2014-09-25",
			"today.plus.2.weeks",	"2014-10-02",
			"today.plus.4.weeks",	"2014-10-16",
			"today.minus.1.week",	"2014-09-11",
			"today.minus.2.weeks",	"2014-09-04",
			"today.minus.4.weeks",	"2014-08-21",
			"setToday",				"2014-09-14",
			"this.year",			"2014-01-01",
			"this.year.end",		"2014-12-31",
			"next.year",			"2015-01-01",
			"next.year.end",		"2015-12-31",
			"last.year",			"2013-01-01",
			"last.year.end",		"2013-12-31",
			"this.month",			"2014-09-01",
			"this.month.end",		"2014-09-30",
			"next.month",			"2014-10-01",
			"next.month.end",		"2014-10-31",
			"last.month",			"2014-08-01",
			"last.month.end",		"2014-08-31",
			"this.week",			"2014-09-08",
			"this.week.end",		"2014-09-14",
			"next.week",			"2014-09-15",
			"next.week.end",		"2014-09-21",
			"last.week",			"2014-09-01",
			"last.week.end",		"2014-09-07",
			"this.Friday",			"2014-09-12",
			"next.Friday",			"2014-09-19",
			"last.Friday",			"2014-09-05",
			"this.Tuesday",			"2014-09-09",
			"next.Tuesday",			"2014-09-16",
			"last.Tuesday",			"2014-09-02",
			"this.November",		"2014-11-01",
			"this.November.end",	"2014-11-30",
			"next.November",		"2015-11-01",
			"next.November.end",	"2015-11-30",
			"last.November",		"2013-11-01",
			"last.November.end",	"2013-11-30",
			"this.April",			"2014-04-01",
			"this.April.end",		"2014-04-30",
			"next.April",			"2015-04-01",
			"next.April.end",		"2015-04-30",
			"last.April",			"2013-04-01",
			"last.April.end",		"2013-04-30",
			"end.this.year",		"2014-12-31",
			"end.next.year",		"2015-12-31",
			"end.last.year",		"2013-12-31",
			"end.this.month",		"2014-09-30",
			"end.next.month",		"2014-10-31",
			"end.last.month",		"2014-08-31",
			"end.this.week",		"2014-09-14",
			"end.next.week",		"2014-09-21",
			"end.last.week",		"2014-09-07",
			"end.this.November",	"2014-11-30",
			"end.next.November",	"2015-11-30",
			"end.last.November",	"2013-11-30",
			"year",					"2014-01-01",	// this.year
			"month",				"2014-09-01",	// this.month
			"week",					"2014-09-08",	// this.week
			"November",				"2014-11-01",	// this.November
			"November.end",			"2014-11-30",	// this.November.end
			"April",				"2014-04-01",	// this.April
			"April.end",			"2014-04-30",	// this.April.end
			"Tuesday",				"2014-09-09",	// this.Tuesday
			"Sunday",				"2014-09-14",	// this.Sunday
			"today.year.1953",		"1953-09-14",
			"today.month.02",		"2014-02-14",
			"today.month.January",	"2014-01-14",
			"today.day.18",			"2014-09-18",
			"today.month.April.day.15", "2014-04-15",
			"today.year.1889.month.May.day.31", "1889-05-31",
			"yesterday.month.12.plus.10.years", "2024-12-13",
			"month.plus.7.days",	"2014-09-08",
			"monday.plus.2.weeks",	"2014-09-22",
			"tue",					"2014-09-09",
			"dec",					"2014-12-01"
	};
	final String[] dateExceptions = {
			"setToday", "2014-09-18",
			"2014-99-01",
			"2014-01-99",
			"bad-date",
			"today.bad",
			"today.plus",
			"tomorrow.minus",
			"today.plus.bad",
			"tomorrow.minus.bad",
			"yesterday.plus.10",
			"yesterday.minus.20",
			"today.plus.10.bad",
			"tomorrow.minus.20.bad",
			"last",
			"next",
			"this",
			"last.bad",
			"tues",
			"decem"
	};
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp () throws Exception {
		String test = testName.getMethodName();
		testing(test);
		firstCase = true;
		passed = false;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown () throws Exception {
		String test = testName.getMethodName();
		if (showInfo || (showError && (passed == false))) {
			System.out.print("         " + test);
		}
		for (int n = test.length(); n < 30; n++) {
			System.out.print('.');
		}
		System.out.println(passed ? " passed" : " failed ***");
	}

	/**
	 * Test method for {@link com.fidelis.valface.ValDateParser#parse(java.lang.String)}.
	 */
	@Test
	public void testParse () {
		passed = testDateSet(dateStrings);
		assertTrue(passed);
	}
	
	@Test
	public void testParseExceptions () {
		passed = testExceptions(dateExceptions);
		assertTrue(passed);
	}
	
	@Test
	public void testBasicDates () {
		passed = testDateSet(basicDates);
		assertTrue(passed);
	}
	
	@Test
	public void testBasicExceptions () {
		passed = testExceptions(basicExceptions);
		assertTrue(passed);
	}
	
	@Test
	public void testModifiers () {
		passed = testDateSet(modifiers);
		assertTrue(passed);
	}
	
	@Test
	public void testModExceptions () {
		passed = testExceptions(modExceptions);
		assertTrue(passed);
	}
	
	@Test
	public void testTraditionalDates () {
		ValDateParser.setTraditional(true);
		passed = testDateSet(traditionalDates);
		ValDateParser.setTraditional(false);
		assertTrue(passed);
	}
	
	private boolean testDateSet (String[] dateSet) {
		
		// Begin with actual date for today.
		setToday("setToday");
		setToday("today");
		
		boolean noError = true;
		Calendar expect = null;
		boolean first = true;
		for (String s : dateSet) {
			if (setToday(s)) {
				continue;
			}
			if (first) {
				first = false;
				testCase = s;
				testResult = parseThis(s);
			} else {
				first = true;
				expect = ADate.parse(s).toCalendar();
				if (! compareDates(expect, testResult)) {
					noError = false;
				}
			}
		}
		return noError;
	}
	
	private boolean testExceptions (String[] badDates) {
		boolean noError = true;
		for (String s : badDates) {
			if (setToday(s)) {
				continue;
			}
			try {
				testCase = s;
				testResult = ValDateParser.parse(s);
				testCase(s, testResult);
				error("Exception expected");
				noError = false;
			} catch (IllegalArgumentException ex) {
				info(s, ex);
			}
		}
		return noError;
	}

	private Calendar parseThis (String text) {
		Calendar result = null;
		try {
			result = ValDateParser.parse(text);
			info(text, result);
		} catch (IllegalArgumentException ex) {
			info(text, ex);
		}
		return result;
	}

	private boolean compareDates (Calendar expect, Calendar actual) {
		int year = actual.get(YEAR);
		int month = actual.get(MONTH) + 1;
		int day = actual.get(DAY_OF_MONTH);
		int expYear = expect.get(YEAR);
		int expMonth = expect.get(MONTH) + 1;
		int expDay = expect.get(DAY_OF_MONTH);
		boolean ok = true;
		if (year != expYear) {
			error("Wrong year:", expYear, year);
			ok = false;
		}
		if (month != expMonth) {
			error("Wrong month:", expMonth, month);
			ok = false;
		}
		if (day != expDay) {
			error("Wrong day:" , expDay, day);
			ok = false;
		}
		return ok;
	}
	
	private boolean setToday (String text) {
		if (setTodayFlag == false) {
			if (text.equals("setToday")) {
				setTodayFlag = true;
				return true;
			}
		}
		else {
			setTodayFlag = false;
			if (text.equals("today")) {
				ADate.nowTestClear();
				return true;
			}
			ADate newToday = ADate.parse(text);
			ADate.nowTestSet(newToday);
			info("Today set to ----", ADate.now().toCalendar());
			return true;
		}
		return false;
	}
	
	private void info (String text, Calendar result) {
		if (showInfo) {
			testCase(text, result);
		}
	}
	
	private void info (String text, Exception ex) {
		if (showInfo) {
			testCase(text, ex);
		}
	}
	
	private void testCase (String text, Calendar result) {
		if (firstCase) {
			firstCase = false;
			System.out.println();
		}
		System.out.println("   Case: " + testFormat(text, result));
	}
	
	private void testCase (String text, Exception ex) {
		if (firstCase) {
			firstCase = false;
			System.out.println();
		}
		System.out.printf("   Case: %-30s %s%n", text, ex.getMessage());
	}
	
	private String testFormat (String text, Calendar result) {
		return String.format("%-30s %2$tY-%2$tm-%2$td", text, result);
	}
	
	private void testing (String text) {
		System.out.print("Testing: " + text);
	}
	
	private void error (String text, int expect, int actual) {
		error(text + " expected: " + expect + " actual: " + actual);
	}
	
	private void error (String text) {
		if (showError) {
			if (! showInfo) {
				testCase(testCase, testResult);
			}
			System.out.println("Error -- " + text);
		}
	}

}
