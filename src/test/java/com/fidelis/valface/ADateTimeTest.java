/**
 *+
 *	ADateTimeTest.java
 *	1.0.0  Oct 12, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * ADateTimeTest
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ADateTimeTest {
	
	@Rule public TestName testName = new TestName();
	
	private boolean passed;

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
	 * Test method for {@link com.fidelis.valface.ADateTime#of(com.fidelis.valface.ADate, com.fidelis.valface.ATime)}.
	 */
	@Test
	public void testOfADateATime () {
		ADate date = ADate.of(2010, 11, 12);
		ATime time = ATime.of(13, 14, 15, 16);
		ADateTime dt = ADateTime.of(date, time);
		assertEquals(2010, dt.getYear());
		assertEquals(11, dt.getMonth());
		assertEquals(12, dt.getDay());
		assertEquals(13, dt.getHour());
		assertEquals(14, dt.getMinute());
		assertEquals(15, dt.getSecond());
		assertEquals(16, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#of(int, int, int, int, int, int, int)}.
	 */
	@Test
	public void testOfIntIntIntIntIntIntInt () {
		ADateTime dt = ADateTime.of(2011, 12, 13, 14, 15, 16, 17);
		assertEquals(2011, dt.getYear());
		assertEquals(12, dt.getMonth());
		assertEquals(13, dt.getDay());
		assertEquals(14, dt.getHour());
		assertEquals(15, dt.getMinute());
		assertEquals(16, dt.getSecond());
		assertEquals(17, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#of(int, int, int, int, int, int)}.
	 */
	@Test
	public void testOfIntIntIntIntIntInt () {
		ADateTime dt = ADateTime.of(2012, 3, 4, 5, 6, 7);
		assertEquals(2012, dt.getYear());
		assertEquals(3, dt.getMonth());
		assertEquals(4, dt.getDay());
		assertEquals(5, dt.getHour());
		assertEquals(6, dt.getMinute());
		assertEquals(7, dt.getSecond());
		assertEquals(0, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#of(int, int, int, int, int)}.
	 */
	@Test
	public void testOfIntIntIntIntInt () {
		ADateTime dt = ADateTime.of(2013, 4, 5, 6, 7);
		assertEquals(2013, dt.getYear());
		assertEquals(4, dt.getMonth());
		assertEquals(5, dt.getDay());
		assertEquals(6, dt.getHour());
		assertEquals(7, dt.getMinute());
		assertEquals(0, dt.getSecond());
		assertEquals(0, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withYear(int)}.
	 */
	@Test
	public void testWithYear () {
		ADateTime dt = ADateTime.of(2014, 9, 18, 20, 30, 40, 50);
		assertEquals(2014, dt.getYear());
		dt = dt.withYear(1953);
		assertEquals(1953, dt.getYear());
		assertEquals(9, dt.getMonth());
		assertEquals(18, dt.getDay());
		assertEquals(20, dt.getHour());
		assertEquals(30, dt.getMinute());
		assertEquals(40, dt.getSecond());
		assertEquals(50, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withMonth(int)}.
	 */
	@Test
	public void testWithMonth () {
		ADateTime dt = ADateTime.of(2020, 10, 11, 12, 13, 14, 15);
		assertEquals(10, dt.getMonth());
		dt = dt.withMonth(3);
		assertEquals(2020, dt.getYear());
		assertEquals(3, dt.getMonth());
		assertEquals(11, dt.getDay());
		assertEquals(12, dt.getHour());
		assertEquals(13, dt.getMinute());
		assertEquals(14, dt.getSecond());
		assertEquals(15, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withDay(int)}.
	 */
	@Test
	public void testWithDay () {
		ADateTime dt = ADateTime.of(2020, 10, 11, 12, 13, 14, 15);
		assertEquals(11, dt.getDay());
		dt = dt.withDay(17);
		assertEquals(2020, dt.getYear());
		assertEquals(10, dt.getMonth());
		assertEquals(17, dt.getDay());
		assertEquals(12, dt.getHour());
		assertEquals(13, dt.getMinute());
		assertEquals(14, dt.getSecond());
		assertEquals(15, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withHour(int)}.
	 */
	@Test
	public void testWithHour () {
		ADateTime dt = ADateTime.of(2020, 10, 11, 12, 13, 14, 15);
		assertEquals(12, dt.getHour());
		dt = dt.withHour(17);
		assertEquals(2020, dt.getYear());
		assertEquals(10, dt.getMonth());
		assertEquals(11, dt.getDay());
		assertEquals(17, dt.getHour());
		assertEquals(13, dt.getMinute());
		assertEquals(14, dt.getSecond());
		assertEquals(15, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withMinute(int)}.
	 */
	@Test
	public void testWithMinute () {
		ADateTime dt = ADateTime.of(2020, 10, 11, 12, 13, 14, 15);
		assertEquals(13, dt.getMinute());
		dt = dt.withMinute(17);
		assertEquals(2020, dt.getYear());
		assertEquals(10, dt.getMonth());
		assertEquals(11, dt.getDay());
		assertEquals(12, dt.getHour());
		assertEquals(17, dt.getMinute());
		assertEquals(14, dt.getSecond());
		assertEquals(15, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withSecond(int)}.
	 */
	@Test
	public void testWithSecond () {
		ADateTime dt = ADateTime.of(2020, 10, 11, 12, 13, 14, 15);
		assertEquals(14, dt.getSecond());
		dt = dt.withSecond(17);
		assertEquals(2020, dt.getYear());
		assertEquals(10, dt.getMonth());
		assertEquals(11, dt.getDay());
		assertEquals(12, dt.getHour());
		assertEquals(13, dt.getMinute());
		assertEquals(17, dt.getSecond());
		assertEquals(15, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#withNano(int)}.
	 */
	@Test
	public void testWithNano () {
		ADateTime dt = ADateTime.of(2020, 10, 11, 12, 13, 14, 15);
		assertEquals(15, dt.getNano());
		dt = dt.withNano(999999999);
		assertEquals(2020, dt.getYear());
		assertEquals(10, dt.getMonth());
		assertEquals(11, dt.getDay());
		assertEquals(12, dt.getHour());
		assertEquals(13, dt.getMinute());
		assertEquals(14, dt.getSecond());
		assertEquals(999999999, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#parse(java.lang.String)}.
	 */
	@Test
	public void testParse () {
		doParse("2014-09-18T20:30:40.50", 2014, 9, 18, 20, 30, 40, 500000000);
		doParse("20151122T212223.24", 2015, 11, 22, 21, 22, 23, 240000000);
		doParse("19301225t040302.01", 1930, 12, 25, 4, 3, 2, 10000000);
		doParse("2000-1-7T20", 2000, 1, 7, 20, 0, 0, 0);
		passed = true;
	}
	
	private void doParse (String spec, int year, int month, int day,
			int hour, int minute, int second, int nano) {
		ADateTime dt = ADateTime.parse(spec);
		assertEquals(year, dt.getYear());
		assertEquals(month, dt.getMonth());
		assertEquals(day, dt.getDay());
		assertEquals(hour, dt.getHour());
		assertEquals(minute, dt.getMinute());
		assertEquals(second, dt.getSecond());
		assertEquals(nano, dt.getNano());
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#format(java.lang.String)}.
	 */
	@Test
	public void testFormat () {
		ADateTime dt = ADateTime.parse("2020-12-09T19:02:03.123456789");
		doFormat(dt, "Year Month Day Hour Minute Second Nano",
				"2020 12 9 19 2 3 123456789");
		doFormat(dt, "year month day hour ampm minute second nano",
				"2020 12 09 7 pm 02 03 123456789");
		doFormat(dt, "yr mo da hh AMPM mm ss nn1.nn2.nn3",
				"20 12 09 07 PM 02 03 123.456.789");
		doFormat(dt, "<year:> year Leo Hinterlang date",
				"year: 2020 Leo Hinterlang 09te");
		doFormat(dt, "<milliseconds:> milli dowName dow monthName mon micro",
				"milliseconds: 123 Wednesday Wed December Dec 123456");
		passed = true;
	}
	
	private void doFormat (ADateTime dt, String fmt, String expected) {
		String actual = dt.format(fmt);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getYear()}.
	 */
	@Test
	public void testGetYear () {
		ADateTime dt = ADateTime.of(1234, 5, 6, 7, 8, 9, 10);
		assertEquals(1234, dt.getYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getMonth()}.
	 */
	@Test
	public void testGetMonth () {
		ADateTime dt = ADateTime.of(2000, 12, 3, 4, 5, 6, 7);
		assertEquals(12, dt.getMonth());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getDay()}.
	 */
	@Test
	public void testGetDay () {
		ADateTime dt = ADateTime.of(2000, 1, 31, 3, 4, 5, 6);
		assertEquals(31, dt.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getHour()}.
	 */
	@Test
	public void testGetHour () {
		ADateTime dt = ADateTime.of(2000, 1, 2, 23, 4, 5, 6);
		assertEquals(23, dt.getHour());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getMinute()}.
	 */
	@Test
	public void testGetMinute () {
		ADateTime dt = ADateTime.of(2000, 1, 2, 3, 59, 5, 6);
		assertEquals(59, dt.getMinute());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getSecond()}.
	 */
	@Test
	public void testGetSecond () {
		ADateTime dt = ADateTime.of(2000, 1, 2, 3, 4, 59, 6);
		assertEquals(59, dt.getSecond());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#getNano()}.
	 */
	@Test
	public void testGetNano () {
		ADateTime dt = ADateTime.of(2000, 1, 2, 3, 4, 5, 999999999);
		assertEquals(999999999, dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusYears(long)}.
	 */
	@Test
	public void testPlusYears () {
		ADateTime dt = ADateTime.of(2000, 1, 2, 3, 4, 5, 6);
		dt = dt.plusYears(14);
		assertEquals(2014, dt.getYear());
		dt = dt.plusYears(-14);
		assertEquals(2000, dt.getYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusMonths(long)}.
	 */
	@Test
	public void testPlusMonths () {
		ADateTime dt = ADateTime.of(2000, 12, 2, 3, 4, 5, 6);
		dt = dt.plusMonths(1);
		assertEquals(2001, dt.getYear());
		assertEquals(1, dt.getMonth());
		dt = dt.plusMonths(6);
		assertEquals(2001, dt.getYear());
		assertEquals(7, dt.getMonth());
		dt = dt.plusMonths(12);
		assertEquals(2002, dt.getYear());
		assertEquals(7, dt.getMonth());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusDays(long)}.
	 */
	@Test
	public void testPlusDays () {
		ADateTime dt = ADateTime.of(2000, 1, 31, 3, 4, 5, 6);
		dt = dt.plusDays(1);
		assertEquals(2000, dt.getYear());
		assertEquals(2, dt.getMonth());
		assertEquals(1, dt.getDay());
		dt = dt.plusDays(29);
		assertEquals(2000, dt.getYear());
		assertEquals(3, dt.getMonth());
		assertEquals(1, dt.getDay());
		dt = dt.plusDays(365);
		assertEquals(2001, dt.getYear());
		assertEquals(3, dt.getMonth());
		assertEquals(1, dt.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusWeeks(long)}.
	 */
	@Test
	public void testPlusWeeks () {
		ADateTime dt = ADateTime.of(2014, 9, 1, 22, 11, 6, 999);
		ADateTime pw = dt.plusWeeks(1);
		assertEquals(2014, pw.getYear());
		assertEquals(9, pw.getMonth());
		assertEquals(8, pw.getDay());
		pw = dt.plusWeeks(4);
		assertEquals(2014, pw.getYear());
		assertEquals(9, pw.getMonth());
		assertEquals(29, pw.getDay());
		pw = dt.plusWeeks(8);
		assertEquals(2014, pw.getYear());
		assertEquals(10, pw.getMonth());
		assertEquals(27, pw.getDay());
		pw = dt.plusWeeks(-2);
		assertEquals(2014, pw.getYear());
		assertEquals(8, pw.getMonth());
		assertEquals(18, pw.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusHours(long)}.
	 */
	@Test
	public void testPlusHours () {
		ADateTime dt = ADateTime.of(1999, 12, 31, 23, 0);
		ADateTime ph = dt.plusHours(1);
		assertEquals(2000, ph.getYear());
		assertEquals(1, ph.getMonth());
		assertEquals(1, ph.getDay());
		assertEquals(0, ph.getHour());
		assertEquals(0, ph.getMinute());
		ph = dt.plusHours(2);
		assertEquals(2000, ph.getYear());
		assertEquals(1, ph.getMonth());
		assertEquals(1, ph.getDay());
		assertEquals(1, ph.getHour());
		assertEquals(0, ph.getMinute());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusMinutes(long)}.
	 */
	@Test
	public void testPlusMinutes () {
		ADateTime dt = ADateTime.of(2014, 12, 31, 23, 0);
		ADateTime pm = dt.plusMinutes(1);
		assertEquals(2014, pm.getYear());
		assertEquals(12, pm.getMonth());
		assertEquals(31, pm.getDay());
		assertEquals(23, pm.getHour());
		assertEquals(1, pm.getMinute());
		pm = dt.plusMinutes(60);
		assertEquals(2015, pm.getYear());
		assertEquals(1, pm.getMonth());
		assertEquals(1, pm.getDay());
		assertEquals(0, pm.getHour());
		assertEquals(0, pm.getMinute());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusSeconds(long)}.
	 */
	@Test
	public void testPlusSeconds () {
		ADateTime dt = ADateTime.of(1999, 12, 31, 23, 59, 0);
		ADateTime ps = dt.plusSeconds(10);
		assertEquals(1999, ps.getYear());
		assertEquals(12, ps.getMonth());
		assertEquals(31, ps.getDay());
		assertEquals(23, ps.getHour());
		assertEquals(59, ps.getMinute());
		assertEquals(10, ps.getSecond());
		assertEquals(0, ps.getNano());
		ps = dt.plusSeconds(60);
		assertEquals(2000, ps.getYear());
		assertEquals(1, ps.getMonth());
		assertEquals(1, ps.getDay());
		assertEquals(0, ps.getHour());
		assertEquals(0, ps.getMinute());
		assertEquals(0, ps.getSecond());
		assertEquals(0, ps.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#plusNanos(long)}.
	 */
	@Test
	public void testPlusNanos () {
		ADateTime dt = ADateTime.of(1999, 12, 31, 23, 59, 59, 0);
		ADateTime pn = dt.plusNanos(1000);
		assertEquals(1999, pn.getYear());
		assertEquals(12, pn.getMonth());
		assertEquals(31, pn.getDay());
		assertEquals(23, pn.getHour());
		assertEquals(59, pn.getMinute());
		assertEquals(59, pn.getSecond());
		assertEquals(1000, pn.getNano());
		pn = dt.plusNanos(1000000000);
		assertEquals(2000, pn.getYear());
		assertEquals(1, pn.getMonth());
		assertEquals(1, pn.getDay());
		assertEquals(0, pn.getHour());
		assertEquals(0, pn.getMinute());
		assertEquals(0, pn.getSecond());
		assertEquals(0, pn.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusYears(long)}.
	 */
	@Test
	public void testMinusYears () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 1, 0);
		ADateTime my = dt.minusYears(1);
		assertEquals(1999, my.getYear());
		assertEquals(1, my.getMonth());
		assertEquals(1, my.getDay());
		assertEquals(1, my.getHour());
		assertEquals(0, my.getMinute());
		my = dt.minusYears(20);
		assertEquals(1980, my.getYear());
		my = dt.minusYears(1000);
		assertEquals(1000, my.getYear());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusMonths(long)}.
	 */
	@Test
	public void testMinusMonths () {
		ADateTime dt = ADateTime.of(2000, 2, 1, 20, 15);
		ADateTime mm = dt.minusMonths(1);
		assertEquals(2000, mm.getYear());
		assertEquals(1, mm.getMonth());
		assertEquals(1, mm.getDay());
		mm = dt.minusMonths(2);
		assertEquals(1999, mm.getYear());
		assertEquals(12, mm.getMonth());
		assertEquals(1, mm.getDay());
		mm = dt.minusMonths(24);
		assertEquals(1998, mm.getYear());
		assertEquals(2, mm.getMonth());
		assertEquals(1, mm.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusDays(long)}.
	 */
	@Test
	public void testMinusDays () {
		ADateTime dt = ADateTime.of(2000, 1, 10, 23, 0);
		ADateTime md = dt.minusDays(9);
		assertEquals(2000, md.getYear());
		assertEquals(1, md.getMonth());
		assertEquals(1, md.getDay());
		md = dt.minusDays(10);
		assertEquals(1999, md.getYear());
		assertEquals(12, md.getMonth());
		assertEquals(31, md.getDay());
		md = dt.minusDays(365);
		assertEquals(1999, md.getYear());
		assertEquals(1, md.getMonth());
		assertEquals(10, md.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusWeeks(long)}.
	 */
	@Test
	public void testMinusWeeks () {
		ADateTime dt = ADateTime.of(2014, 10, 30, 1, 2, 3, 4);
		ADateTime mw = dt.minusWeeks(1);
		assertEquals(2014, mw.getYear());
		assertEquals(10, mw.getMonth());
		assertEquals(23, mw.getDay());
		mw = dt.minusWeeks(2);
		assertEquals(2014, mw.getYear());
		assertEquals(10, mw.getMonth());
		assertEquals(16, mw.getDay());
		mw = dt.minusWeeks(6);
		assertEquals(2014, mw.getYear());
		assertEquals(9, mw.getMonth());
		assertEquals(18, mw.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusHours(long)}.
	 */
	@Test
	public void testMinusHours () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 2, 45);
		ADateTime mh = dt.minusHours(1);
		assertEquals(2000, mh.getYear());
		assertEquals(1, mh.getMonth());
		assertEquals(1, mh.getDay());
		assertEquals(1, mh.getHour());
		assertEquals(45, mh.getMinute());
		mh = dt.minusHours(3);
		assertEquals(1999, mh.getYear());
		assertEquals(12, mh.getMonth());
		assertEquals(31, mh.getDay());
		assertEquals(23, mh.getHour());
		assertEquals(45, mh.getMinute());
		mh = dt.minusHours(24);
		assertEquals(1999, mh.getYear());
		assertEquals(12, mh.getMonth());
		assertEquals(31, mh.getDay());
		assertEquals(2, mh.getHour());
		assertEquals(45, mh.getMinute());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusMinutes(long)}.
	 */
	@Test
	public void testMinusMinutes () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 0, 2);
		ADateTime mm = dt.minusMinutes(1);
		assertEquals(2000, mm.getYear());
		assertEquals(1, mm.getMonth());
		assertEquals(1, mm.getDay());
		assertEquals(0, mm.getHour());
		assertEquals(1, mm.getMinute());
		mm = dt.minusMinutes(2);
		assertEquals(2000, mm.getYear());
		assertEquals(1, mm.getMonth());
		assertEquals(1, mm.getDay());
		assertEquals(0, mm.getHour());
		assertEquals(0, mm.getMinute());
		mm = dt.minusMinutes(3);
		assertEquals(1999, mm.getYear());
		assertEquals(12, mm.getMonth());
		assertEquals(31, mm.getDay());
		assertEquals(23, mm.getHour());
		assertEquals(59, mm.getMinute());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusSeconds(long)}.
	 */
	@Test
	public void testMinusSeconds () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 0, 0, 2);
		ADateTime ms = dt.minusSeconds(2);
		assertEquals(2000, ms.getYear());
		assertEquals(1, ms.getMonth());
		assertEquals(1, ms.getDay());
		assertEquals(0, ms.getHour());
		assertEquals(0, ms.getMinute());
		assertEquals(0, ms.getSecond());
		ms = dt.minusSeconds(3);
		assertEquals(1999, ms.getYear());
		assertEquals(12, ms.getMonth());
		assertEquals(31, ms.getDay());
		assertEquals(23, ms.getHour());
		assertEquals(59, ms.getMinute());
		assertEquals(59, ms.getSecond());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#minusNanos(long)}.
	 */
	@Test
	public void testMinusNanos () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 0, 0, 1, 0);
		ADateTime mn = dt.minusNanos(1);
		assertEquals(2000, mn.getYear());
		assertEquals(1, mn.getMonth());
		assertEquals(1, mn.getDay());
		assertEquals(0, mn.getHour());
		assertEquals(0, mn.getMinute());
		assertEquals(0, mn.getSecond());
		assertEquals(999999999, mn.getNano());
		mn = dt.minusNanos(1000000000);
		assertEquals(2000, mn.getYear());
		assertEquals(1, mn.getMonth());
		assertEquals(1, mn.getDay());
		assertEquals(0, mn.getHour());
		assertEquals(0, mn.getMinute());
		assertEquals(0, mn.getSecond());
		assertEquals(0, mn.getNano());
		mn = dt.minusNanos((long) 1000000000 * 60);
		assertEquals(1999, mn.getYear());
		assertEquals(12, mn.getMonth());
		assertEquals(31, mn.getDay());
		assertEquals(23, mn.getHour());
		assertEquals(59, mn.getMinute());
		assertEquals(1, mn.getSecond());
		assertEquals(0, mn.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#equals(com.fidelis.valface.ADateTime)}.
	 */
	@Test
	public void testEqualsADateTime () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ADateTime ct = ADateTime.parse("2000-01-01T10:20:30.000000040");
		assertTrue(dt.equals(ct));
		assertTrue(ct.equals(dt));
		ct = ct.plusNanos(1);
		assertFalse(dt.equals(ct));
		assertFalse(ct.equals(dt));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#compareTo(com.fidelis.valface.ADateTime)}.
	 */
	@Test
	public void testCompareTo () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ADateTime ct = ADateTime.parse("2000-01-01T10:20:30.000000040");
		assertTrue(dt.compareTo(ct) == 0);
		assertTrue(ct.compareTo(dt) == 0);
		ct = ct.plusNanos(1);
		assertTrue(dt.compareTo(ct) < 0);
		assertTrue(ct.compareTo(dt) > 0);
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#isAfter(com.fidelis.valface.ADateTime)}.
	 */
	@Test
	public void testIsAfter () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ADateTime ct = ADateTime.of(2000, 1, 1, 10, 20, 30, 41);
		assertTrue(ct.isAfter(dt));
		assertFalse(dt.isAfter(ct));
		ct = ct.minusNanos(1);
		assertFalse(ct.isAfter(dt));
		assertFalse(dt.isAfter(ct));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#isBefore(com.fidelis.valface.ADateTime)}.
	 */
	@Test
	public void testIsBefore () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ADateTime ct = ADateTime.of(1999, 12, 31, 10, 20, 30, 40);
		assertTrue(ct.isBefore(dt));
		assertFalse(dt.isBefore(ct));
		ct = ct.plusDays(1);
		assertFalse(ct.isBefore(dt));
		assertFalse(dt.isBefore(ct));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#isEqual(com.fidelis.valface.ADateTime)}.
	 */
	@Test
	public void testIsEqual () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ADateTime ct = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		assertTrue(dt.isEqual(ct));
		assertTrue(ct.isEqual(dt));
		ct = ct.plusSeconds(10);
		assertFalse(dt.isEqual(ct));
		assertFalse(ct.isEqual(dt));
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#from(java.util.Date)}.
	 */
	@Test
	public void testFromDate () {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		ADateTime dt = ADateTime.from(date);
		assertEquals(cal.get(Calendar.YEAR), dt.getYear());
		assertEquals(cal.get(Calendar.MONTH) + 1, dt.getMonth());
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), dt.getDay());
		assertEquals(cal.get(Calendar.HOUR), dt.getHour());
		assertEquals(cal.get(Calendar.MINUTE), dt.getMinute());
		assertEquals(cal.get(Calendar.SECOND), dt.getSecond());
		assertEquals(cal.get(Calendar.MILLISECOND) * 1000000, dt.getNano());
		passed = true;
	}
	
	@Test
	public void testToDate () {
		ADateTime dt = ADateTime.of(2014, 9, 18, 10, 20, 30, 999000000);
		Date date = dt.toDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(9, cal.get(Calendar.MONTH) + 1);
		assertEquals(18, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(10, cal.get(Calendar.HOUR));
		assertEquals(20, cal.get(Calendar.MINUTE));
		assertEquals(30, cal.get(Calendar.SECOND));
		assertEquals(999, cal.get(Calendar.MILLISECOND));
		passed = true;
	}
	
	@Test
	public void testFromCalendar () {
		Calendar cal = Calendar.getInstance();
		ADateTime dt = ADateTime.from(cal);
		assertEquals(cal.get(Calendar.YEAR), dt.getYear());
		assertEquals(cal.get(Calendar.MONTH) + 1, dt.getMonth());
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), dt.getDay());
		assertEquals(cal.get(Calendar.HOUR), dt.getHour());
		assertEquals(cal.get(Calendar.MINUTE), dt.getMinute());
		assertEquals(cal.get(Calendar.SECOND), dt.getSecond());
		assertEquals(cal.get(Calendar.MILLISECOND) * 1000000, dt.getNano());
		passed = true;
	}
	
	@Test
	public void testToCalendar () {
		ADateTime dt = ADateTime.of(2014, 9, 18, 10, 20, 30, 999000000);
		Calendar cal = dt.toCalendar();
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(9, cal.get(Calendar.MONTH) + 1);
		assertEquals(18, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(10, cal.get(Calendar.HOUR));
		assertEquals(20, cal.get(Calendar.MINUTE));
		assertEquals(30, cal.get(Calendar.SECOND));
		assertEquals(999, cal.get(Calendar.MILLISECOND));
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#toADate()}.
	 */
	@Test
	public void testToADate () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ADate date = dt.toADate();
		assertEquals(date.getYear(), dt.getYear());
		assertEquals(date.getMonth(), dt.getMonth());
		assertEquals(date.getDay(), dt.getDay());
		dt = dt.plusMonths(25).plusDays(14);
		date = dt.toADate();
		assertEquals(date.getYear(), dt.getYear());
		assertEquals(date.getMonth(), dt.getMonth());
		assertEquals(date.getDay(), dt.getDay());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#toATime()}.
	 */
	@Test
	public void testToATime () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		ATime time = dt.toATime();
		assertEquals(time.getHour(), dt.getHour());
		assertEquals(time.getMinute(), dt.getMinute());
		assertEquals(time.getSecond(), dt.getSecond());
		assertEquals(time.getNano(), dt.getNano());
		dt = dt.plusHours(146).plusMinutes(24).plusSeconds(4).plusNanos(1000000);
		time = dt.toATime();
		assertEquals(time.getHour(), dt.getHour());
		assertEquals(time.getMinute(), dt.getMinute());
		assertEquals(time.getSecond(), dt.getSecond());
		assertEquals(time.getNano(), dt.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ADateTime#toString()}.
	 */
	@Test
	public void testToString () {
		ADateTime dt = ADateTime.of(2000, 1, 1, 10, 20, 30, 40);
		assertEquals(dt.toString(), "2000-01-01T10:20:30.000000040");
		dt = dt.withNano(0);
		assertEquals(dt.toString(), "2000-01-01T10:20:30");
		dt = dt.withSecond(0);
		assertEquals(dt.toString(), "2000-01-01T10:20");
		dt = dt.withYear(1889).withMonth(5).withDay(31);
		assertEquals(dt.toString(), "1889-05-31T10:20");
		passed = true;
	}

}
