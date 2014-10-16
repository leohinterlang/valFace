/**
 *+
 *	ADateTest.java
 *	1.0.0  Sep 19, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * ADateTest
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ADateTest {
	
	private boolean passed;

	@Rule public TestName testName = new TestName();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp () throws Exception {
		String test = testName.getMethodName();
		System.out.printf("Testing: " + test);
		for (int n = test.length(); n < 30; n++) {
			System.out.print('.');
		}
		passed = false;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown () throws Exception {
		 System.out.println(passed ? " passed" : " failed ***");
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#now()}.
	 */
	@Test
	public void testNow () {
		ADate today = ADate.now();
		int todayYear = today.getYear();
		int todayMonth = today.getMonth();
		int todayDay = today.getDay();
		assertTrue(todayYear >= 2014);
		assertTrue(1 <= todayMonth && todayMonth <= 12);
		assertTrue(1 <= todayDay && todayDay <= 31);
		
		// Enable test value for "now".
		today = ADate.of(2010, 10, 31);
		ADate.nowTestSet(today);
		ADate now = ADate.now();
		assertEquals(2010, now.getYear());
		assertEquals(10, now.getMonth());
		assertEquals(31, now.getDay());
		
		// Disable test value for "now".
		ADate.nowTestClear();
		ADate newToday = ADate.now();
		assertEquals(todayYear, newToday.getYear());
		assertEquals(todayMonth, newToday.getMonth());
		assertEquals(todayDay, newToday.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#nowTestSet(com.fidelis.valface.ADate)}.
	 */
	@Test
	public void testNowTestSet () {
		ADate date = ADate.of(1941, 12, 7);
		ADate today = ADate.now();
		int ty = today.getYear();
		int tm = today.getMonth();
		int td = today.getDay();
		ADate.nowTestSet(date);
		ADate now = ADate.now();
		assertEquals(1941, now.getYear());
		assertEquals(12, now.getMonth());
		assertEquals(7, now.getDay());
		ADate.nowTestClear();
		ADate newToday = ADate.now();
		assertEquals(ty, newToday.getYear());
		assertEquals(tm, newToday.getMonth());
		assertEquals(td, newToday.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#nowTestClear()}.
	 */
	@Test
	public void testNowTestClear () {
		ADate date = ADate.of(1889, 5, 31);
		ADate today = ADate.now();
		int ty = today.getYear();
		int tm = today.getMonth();
		int td = today.getDay();
		ADate.nowTestSet(date);
		ADate now = ADate.now();
		assertEquals(1889, now.getYear());
		assertEquals(5, now.getMonth());
		assertEquals(31, now.getDay());
		ADate.nowTestClear();
		ADate newToday = ADate.now();
		assertEquals(ty, newToday.getYear());
		assertEquals(tm, newToday.getMonth());
		assertEquals(td, newToday.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#of(int, int, int)}.
	 */
	@Test
	public void testOfIntIntInt () {
		int year = 2003;
		int month = 7;
		int day = 25;
		ADate date = ADate.of(year, month, day);
		assertEquals(year, date.getYear());
		assertEquals(month, date.getMonth());
		assertEquals(day, date.getDay());
		year = 1776;
		month = 99;
		day = 4;
		try {
			date = ADate.of(year, month, day);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
		}
		month = 7;
		day = 44;
		try {
			date = ADate.of(year, month, day);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
		}
		day = 4;
		try {
			date = ADate.of(year, month, day);
			assertEquals(1776, date.getYear());
			assertEquals(7, date.getMonth());
			assertEquals(4, date.getDay());
		} catch (IllegalArgumentException ex) {
			fail("Exception thrown");
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#of(com.fidelis.valface.ADate)}.
	 */
	@Test
	public void testOfADate () {
		int year = 1953;
		int month = 9;
		int day = 18;
		ADate date = ADate.of(year, month, day);
		ADate newDate = ADate.of(date);
		assertEquals(year, newDate.getYear());
		assertEquals(month, newDate.getMonth());
		assertEquals(day, newDate.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#parse(java.lang.String)}.
	 */
	@Test
	public void testParse () {
		ADate date = ADate.parse("1234-05-15");
		assertEquals(1234, date.getYear());
		assertEquals(5, date.getMonth());
		assertEquals(15, date.getDay());
		date = ADate.parse("20021103");
		assertEquals(2002, date.getYear());
		assertEquals(11, date.getMonth());
		assertEquals(3, date.getDay());
		date = ADate.parse("1985.02.13");
		assertEquals(1985, date.getYear());
		assertEquals(2, date.getMonth());
		assertEquals(13, date.getDay());
		try {
			date = ADate.parse("2013-99-01");
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#getYear()}.
	 */
	@Test
	public void testGetYear () {
		ADate date = ADate.of(1492, 8, 23);
		assertEquals(1492, date.getYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#getMonth()}.
	 */
	@Test
	public void testGetMonth () {
		ADate date = ADate.of(2099, 6, 30);
		assertEquals(6, date.getMonth());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#getDay()}.
	 */
	@Test
	public void testGetDay () {
		ADate date = ADate.of(2020, 5, 29);
		assertEquals(29, date.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#withYear(int)}.
	 */
	@Test
	public void testWithYear () {
		ADate date = ADate.of(2000, 10, 13);
		date = date.withYear(1903);
		assertEquals(1903, date.getYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#withMonth(int)}.
	 */
	@Test
	public void testWithMonth () {
		ADate date = ADate.of(2000, 11, 12);
		ADate with = date.withMonth(2);
		assertEquals(2, with.getMonth());
		try {
			with = date.withMonth(99);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#withDay(int)}.
	 */
	@Test
	public void testWithDay () {
		ADate date = ADate.of(2000, 12, 25);
		ADate with = date.withDay(10);
		assertEquals(10, with.getDay());
		try {
			with = date.withDay(99);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#plusYears(int)}.
	 */
	@Test
	public void testPlusYears () {
		ADate date = ADate.of(2000, 1, 1);
		ADate other = date.plusYears(1);
		assertEquals(2001, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusYears(-1);
		assertEquals(1999, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusYears(-100);
		assertEquals(1900, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusYears(100);
		assertEquals(2100, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#plusMonths(int)}.
	 */
	@Test
	public void testPlusMonths () {
		ADate date = ADate.of(2010, 1, 1);
		ADate other = date.plusMonths(1);
		assertEquals(2010, other.getYear());
		assertEquals(2, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusMonths(-1);
		assertEquals(2009, other.getYear());
		assertEquals(12, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusMonths(5);
		assertEquals(2010, other.getYear());
		assertEquals(6, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusMonths(24);
		assertEquals(2012, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#plusDays(int)}.
	 */
	@Test
	public void testPlusDays () {
		ADate date = ADate.of(2008, 2, 28);
		ADate other = date.plusDays(1);
		assertEquals(2008, other.getYear());
		assertEquals(2, other.getMonth());
		assertEquals(29, other.getDay());
		other = date.plusDays(2);
		assertEquals(2008, other.getYear());
		assertEquals(3, other.getMonth());
		assertEquals(1, other.getDay());
		other = date.plusDays(-28);
		assertEquals(2008, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(31, other.getDay());
		other = date.plusDays(-365);
		assertEquals(2007, other.getYear());
		assertEquals(2, other.getMonth());
		assertEquals(28, other.getDay());
		other = date.plusDays(365);
		assertEquals(2009, other.getYear());
		assertEquals(2, other.getMonth());
		assertEquals(27, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#equals(com.fidelis.valface.ADate)}.
	 */
	@Test
	public void testEqualsADate () {
		ADate date = ADate.of(2014, 8, 27);
		ADate other = ADate.of(2014, 8, 27);
		assertTrue(date.equals(other));
		other = ADate.parse("2014-08-27");
		assertTrue(other.equals(date));
		other = ADate.parse("2014-09-01");
		assertFalse(date.equals(other));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#compareTo(com.fidelis.valface.ADate)}.
	 */
	@Test
	public void testCompareTo () {
		ADate date = ADate.of(2020, 11, 11);
		ADate other = ADate.of(2020, 11, 10);
		assertTrue(date.compareTo(other) > 0);
		assertTrue(other.compareTo(date) < 0);
		other = ADate.parse("2020-11-12");
		assertTrue(date.compareTo(other) < 0);
		assertTrue(other.compareTo(date) > 0);
		other = ADate.parse("2020-11-11");
		assertTrue(date.compareTo(other) == 0);
		assertTrue(other.compareTo(date) == 0);
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#valueOf(int)}.
	 */
	@Test
	public void testValueOfInt () {
		ADate date = ADate.of(2001, 9, 11);
		int days = date.getDays();
		ADate other = ADate.valueOf(days);
		assertEquals(2001, other.getYear());
		assertEquals(9, other.getMonth());
		assertEquals(11, other.getDay());
		other = ADate.valueOf(days + 10);
		assertEquals(2001, other.getYear());
		assertEquals(9, other.getMonth());
		assertEquals(21, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#intValue()}.
	 */
	@Test
	public void testIntValue () {
		ADate date = ADate.of(2030, 1, 1);
		int days = date.intValue();
		ADate other = ADate.valueOf(days);
		assertEquals(2030, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#valueOf(java.lang.String)}.
	 */
	@Test
	public void testValueOfString () {
		ADate date = ADate.of(2014, 6, 21);
		ADate other = ADate.valueOf("2014-6-21");
		assertEquals(2014, other.getYear());
		assertEquals(6, other.getMonth());
		assertEquals(21, other.getDay());
		other = ADate.valueOf("20110401");
		assertEquals(2011, other.getYear());
		assertEquals(4, other.getMonth());
		assertEquals(1, other.getDay());
		other = ADate.valueOf(date.toString());
		assertEquals(2014, other.getYear());
		assertEquals(6, other.getMonth());
		assertEquals(21, other.getDay());
		other = ADate.valueOf("1889.5.31");
		assertEquals(1889, other.getYear());
		assertEquals(5, other.getMonth());
		assertEquals(31, other.getDay());
		try {
			other = ADate.valueOf("2010-08-99");
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#valueOf(com.fidelis.valface.ADate)}.
	 */
	@Test
	public void testValueOfADate () {
		ADate date = ADate.of(1994, 12, 28);
		ADate other = ADate.valueOf(date);
		assertEquals(1994, other.getYear());
		assertEquals(12, other.getMonth());
		assertEquals(28, other.getDay());
		other = ADate.valueOf(date.plusDays(4));
		assertEquals(1995, other.getYear());
		assertEquals(1, other.getMonth());
		assertEquals(1, other.getDay());
		other = ADate.valueOf(date.plusDays(-3));
		assertEquals(1994, other.getYear());
		assertEquals(12, other.getMonth());
		assertEquals(25, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#isLeapYear()}.
	 */
	@Test
	public void testIsLeapYear () {
		ADate date = ADate.of(2000, 1, 1);
		ADate other = date;
		assertTrue(other.isLeapYear());
		other = date.plusYears(-100);
		assertFalse(other.isLeapYear());
		other = date.plusYears(4);
		assertTrue(other.isLeapYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#isLeapYear(int)}.
	 */
	@Test
	public void testIsLeapYearInt () {
		ADate date = ADate.of(2008, 1, 1);
		assertTrue(ADate.isLeapYear(date.getYear()));
		assertTrue(ADate.isLeapYear(2000));
		assertTrue(ADate.isLeapYear(2004));
		assertFalse(ADate.isLeapYear(1900));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#lengthOfMonth()}.
	 */
	@Test
	public void testLengthOfMonth () {
		ADate date = ADate.of(2000, 2, 1);
		ADate other = date;
		assertEquals(29, other.lengthOfMonth());
		other = date.plusMonths(1);
		assertEquals(31, other.lengthOfMonth());
		other = date.plusMonths(2);
		assertEquals(30, other.lengthOfMonth());
		other = date.plusYears(-100);
		assertEquals(28, other.lengthOfMonth());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#lengthOfMonth(int, int)}.
	 */
	@Test
	public void testLengthOfMonthIntInt () {
		ADate date = ADate.of(2012, 2, 1);
		ADate other = date;
		assertEquals(29, ADate.lengthOfMonth(other.getYear(), other.getMonth()));
		assertEquals(31, ADate.lengthOfMonth(2012, 3));
		assertEquals(28, ADate.lengthOfMonth(1800, 2));
		assertEquals(31, ADate.lengthOfMonth(1889, 5));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#lengthOfYear()}.
	 */
	@Test
	public void testLengthOfYear () {
		ADate date = ADate.of(2000, 1, 1);
		ADate other = date;
		assertEquals(366, other.lengthOfYear());
		other = date.plusYears(-100);
		assertEquals(365, other.lengthOfYear());
		other = date.plusYears(2);
		assertEquals(365, other.lengthOfYear());
		other = date.plusYears(4);
		assertEquals(366, other.lengthOfYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#lengthOfYear(int)}.
	 */
	@Test
	public void testLengthOfYearInt () {
		ADate date = ADate.of(1900, 1, 1);
		assertEquals(365, ADate.lengthOfYear(date.getYear()));
		assertEquals(366, ADate.lengthOfYear(2000));
		assertEquals(366, ADate.lengthOfYear(1904));
		assertEquals(365, ADate.lengthOfYear(1999));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#from(java.util.Date)}.
	 */
	@Test
	public void testFromDate () {
		ADate date = ADate.now();
		Date dt = new Date();
		ADate other = ADate.from(dt);
		assertEquals(date.getYear(), other.getYear());
		assertEquals(date.getMonth(), other.getMonth());
		assertEquals(date.getDay(), other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#from(java.util.Calendar)}.
	 */
	@Test
	public void testFromCalendar () {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2222);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 22);
		ADate other = ADate.from(cal);
		assertEquals(2222, other.getYear());
		assertEquals(11, other.getMonth());
		assertEquals(22, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#toDate()}.
	 */
	@Test
	public void testToDate () {
		ADate date = ADate.of(1990, 1, 1);
		Date dt = date.toDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		assertEquals(1990, cal.get(Calendar.YEAR));
		assertEquals(Calendar.JANUARY, cal.get(Calendar.MONTH));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		date = ADate.of(2010, 8, 16);
		dt = date.toDate();
		cal.setTime(dt);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(Calendar.AUGUST, cal.get(Calendar.MONTH));
		assertEquals(16, cal.get(Calendar.DAY_OF_MONTH));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#toCalendar()}.
	 */
	@Test
	public void testToCalendar () {
		ADate date = ADate.of(2016, 10, 13);
		Calendar cal = date.toCalendar();
		assertEquals(2016, cal.get(Calendar.YEAR));
		assertEquals(Calendar.OCTOBER, cal.get(Calendar.MONTH));
		assertEquals(13, cal.get(Calendar.DAY_OF_MONTH));
		date = ADate.of(1940, 5, 8);
		cal = date.toCalendar();
		assertEquals(1940, cal.get(Calendar.YEAR));
		assertEquals(Calendar.MAY, cal.get(Calendar.MONTH));
		assertEquals(8, cal.get(Calendar.DAY_OF_MONTH));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#monthName()}.
	 */
	@Test
	public void testMonthName () {
		ADate date = ADate.of(2040, 1, 1);
		ADate other = date;
		assertEquals("January", other.monthName());
		other = date.plusMonths(1);
		assertEquals("February", other.monthName());
		other = date.plusMonths(-1);
		assertEquals("December", other.monthName());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#mon()}.
	 */
	@Test
	public void testMon () {
		ADate date = ADate.of(2050, 7, 1);
		ADate other = date;
		assertEquals("Jul", other.mon());
		other = date.plusMonths(2);
		assertEquals("Sep", other.mon());
		other = date.plusMonths(-2);
		assertEquals("May", other.mon());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#dowName()}.
	 */
	@Test
	public void testDowName () {
		ADate date = ADate.of(2014, 9, 14);
		ADate other = date;
		assertEquals("Sunday", other.dowName());
		other = date.plusDays(3);
		assertEquals("Wednesday", other.dowName());
		other = date.plusDays(-2);
		assertEquals("Friday", other.dowName());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#dow()}.
	 */
	@Test
	public void testDow () {
		ADate date = ADate.of(2015, 1, 12);
		ADate other = date;
		assertEquals("Mon", other.dow());
		other = date.plusDays(5);
		assertEquals("Sat", other.dow());
		other = date.plusDays(-5);
		assertEquals("Wed", other.dow());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#format(java.lang.String)}.
	 */
	@Test
	public void testFormat () {
		ADate date = ADate.of(123, 4, 5);
		String s = date.format("Year year yr");
		assertEquals("123 0123 23", s);
		s = date.format("Month monthName month mon mo");
		assertEquals("4 April 04 Apr 04", s);
		s = date.format("Day day da dowName dow");
		assertEquals("5 05 05 Monday Mon", s);
		s = date.format("days");
		assertEquals("45020", s);
		s = date.format("<year:> year <month:> month <day:> day");
		assertEquals("year: 0123 month: 04 day: 05", s);
		s = date.format(null);
		assertEquals("0123-04-05", s);
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#dayOfWeek()}.
	 */
	@Test
	public void testDayOfWeek () {
		ADate date = ADate.of(2014, 9, 14);
		ADate other = date;
		assertEquals(7, other.dayOfWeek());
		other = date.plusDays(1);
		assertEquals(1, other.dayOfWeek());
		other = date.plusDays(-1);
		assertEquals(6, other.dayOfWeek());
		other = date.plusDays(4);
		assertEquals(4, other.dayOfWeek());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#getDays()}.
	 */
	@Test
	public void testGetDays () {
		ADate date = ADate.of(1889, 5, 31);
		ADate other = date;
		assertEquals(690095, other.getDays());
		other = date.plusDays(100);
		assertEquals(690195, other.getDays());
		other = date.plusDays(-95);
		assertEquals(690000, other.getDays());
		other = ADate.parse("1963-11-22");
		assertEquals(717297, other.getDays());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#fromDays(int)}.
	 */
	@Test
	public void testFromDays () {
		ADate date = ADate.of(1889, 5, 31);
		ADate other = ADate.fromDays(690095);
		assertTrue(date.equals(other));
		other = ADate.fromDays(717297);
		assertEquals(1963, other.getYear());
		assertEquals(11, other.getMonth());
		assertEquals(22, other.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADate#toString()}.
	 */
	@Test
	public void testToString () {
		ADate date = ADate.of(1776, 7, 4);
		String s = date.toString();
		assertEquals("1776-07-04", s);
		passed = true;
	}

}
