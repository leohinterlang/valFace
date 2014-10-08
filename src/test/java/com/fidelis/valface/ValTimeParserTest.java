/**
 *+
 *	ValTimeParserTest.java
 *	1.0.0  Oct 5, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * ValTimeParserTest
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ValTimeParserTest {
	
	@Rule public TestName testName = new TestName();
	
	private boolean passed;
	private boolean firstCase;
	private String testCase;
	private ATime testResult;
	private boolean setNowFlag;
	
	private boolean showInfo = true;
	private boolean showError = true;
	
	private static final String[] basicTimes = {
		"15",				"15:00:00.000",
		"1530",				"15:30:00.000",
		"153045",			"15:30:45.000",
		"153045.999999999",	"15:30:45.999999999",
		"23:59",			"23:59:00.000",
		"23:59:59",			"23:59:59.000",
		"23:59:59.1234",	"23:59:59.1234",
		"23:59:59.000123",	"23:59:59.000123",
		"23:59:59.000000123",	"23:59:59.000000123",
		"23:59:59.00001",		"23:59:59.00001",
		"23:59:59.01",			"23:59:59.01"
	};
	
	private static final String[] basicExceptions = {
		"30",
		"1299",
		"123499",
		"123456.9999999990",
		"12:",
		":34",
		"12:34x",
		":34:56"
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
	
	@Test
	public void testBasicTimes () {
		passed = testTimeSet(basicTimes);
		assertTrue(passed);
	}

	@Test
	public void testBasicExceptions () {
		passed = testExceptions(basicExceptions);
		assertTrue(passed);
	}

	private boolean testTimeSet (String[] timeSet) {
		
		// Begin with the current time.
		setNow("setNow");
		setNow("now");
		
		boolean noError = true;
		ATime expect = null;
		boolean first = true;
		for (String s : timeSet) {
			if (setNow(s)) {
				continue;
			}
			if (first) {
				first = false;
				testCase = s;
				testResult = parseThis(s);
			} else {
				first = true;
				expect = ATime.parse(s);
				if (! compareTimes(expect, testResult)) {
					noError = false;
				}
			}
		}
		return noError;
	}
	
	private boolean testExceptions (String[] badTimes) {
		boolean noError = true;
		for (String s : badTimes) {
			if (setNow(s)) {
				continue;
			}
			try {
				testCase = s;
				testResult = ValTimeParser.parse(s);
				testCase(s, testResult);
				error("Exception expected");
				noError = false;
			} catch (IllegalArgumentException ex) {
				info(s, ex);
			}
		}
		return noError;
	}

	private ATime parseThis (String text) {
		ATime result = null;
		try {
			result = ValTimeParser.parse(text);
			info(text, result);
		} catch (IllegalArgumentException ex) {
			info(text, ex);
		}
		return result;
	}

	private boolean compareTimes (ATime expect, ATime actual) {
		int hour = actual.getHour();
		int minute = actual.getMinute();
		int second = actual.getSecond();
		int nano = actual.getNano();
		int expHour = expect.getHour();
		int expMinute = expect.getMinute();
		int expSecond = expect.getSecond();
		int expNano = expect.getNano();
		boolean ok = true;
		if (hour != expHour) {
			error("Wrong hour:", expHour, hour);
			ok = false;
		}
		if (minute != expMinute) {
			error("Wrong minute:", expMinute, minute);
			ok = false;
		}
		if (second != expSecond) {
			error("Wrong second:", expSecond, second);
			ok = false;
		}
		if (nano != expNano) {
			error("Wrong nano:", expNano, nano);
			ok = false;
		}
		return ok;
	}
	
	private boolean setNow (String text) {
		if (setNowFlag == false) {
			if (text.equals("setNow")) {
				setNowFlag = true;
				return true;
			}
		}
		else {
			setNowFlag = false;
			if (text.equals("now")) {
				ADate.nowTestClear();
				return true;
			}
			ATime newNow = ATime.parse(text);
			ATime.nowTestSet(newNow);
			info("Now set to ----", ATime.now());
			return true;
		}
		return false;
	}
	
	private void info (String text, ATime time) {
		if (showInfo) {
			testCase(text, time);
		}
	}
	
	private void info (String text, Exception ex) {
		if (showInfo) {
			testCase(text, ex);
		}
	}
	
	private void testCase (String text, ATime time) {
		if (firstCase) {
			firstCase = false;
			System.out.println();
		}
		System.out.println("   Case: " + testFormat(text, time));
	}
	
	private void testCase (String text, Exception ex) {
		if (firstCase) {
			firstCase = false;
			System.out.println();
		}
		System.out.printf("   Case: %-30s %s%n", text, ex.getMessage());
	}
	
	private String testFormat (String text, ATime time) {
		return String.format("%-30s %s", text, time);
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
