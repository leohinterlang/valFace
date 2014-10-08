/**
 *+
 *	ATimeTest.java
 *	1.0.0  Oct 1, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * ATimeTest
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ATimeTest {
	
	@Rule public TestName testName = new TestName();
	
	private boolean passed;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp () throws Exception {
		String test = testName.getMethodName();
		System.out.printf("Testing: " + test);
		for (int n = test.length(); n < 25; n++) {
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
	 * Test method for {@link com.fidelis.valface.ATime#getHour()}.
	 */
	@Test
	public void testGetHour () {
		ATime time = ATime.of(12, 34);
		assertEquals(12, time.getHour());
		time = ATime.of(0, 44);
		assertEquals(0, time.getHour());
		time = ATime.of(23, 18);
		assertEquals(23, time.getHour());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#getMinute()}.
	 */
	@Test
	public void testGetMinute () {
		ATime time = ATime.of(12, 34);
		assertEquals(34, time.getMinute());
		time = ATime.of(0, 44);
		assertEquals(44, time.getMinute());
		time = ATime.of(23, 0);
		assertEquals(0, time.getMinute());
		time = ATime.of(14, 59);
		assertEquals(59, time.getMinute());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#getSecond()}.
	 */
	@Test
	public void testGetSecond () {
		ATime time = ATime.of(12, 34, 56);
		assertEquals(56, time.getSecond());
		time = ATime.of(0, 0, 5);
		assertEquals(5, time.getSecond());
		time = ATime.of(1, 2, 0);
		assertEquals(0, time.getSecond());
		time = ATime.of(17, 30);
		assertEquals(0, time.getSecond());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#getNano()}.
	 */
	@Test
	public void testGetNano () {
		ATime time = ATime.of(12, 34, 56, 7890);
		assertEquals(7890, time.getNano());
		time = ATime.of(0, 44, 0, 212313414);
		assertEquals(212313414, time.getNano());
		time = ATime.of(1, 2, 3);
		assertEquals(0, time.getNano());
		time = ATime.of(7, 23, 14, 0);
		assertEquals(0, time.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#of(int, int, int, int)}.
	 */
	@Test
	public void testOfIntIntIntInt () {
		ATime time = ATime.of(12, 34, 56, 7890);
		assertEquals(12, time.getHour());
		assertEquals(34, time.getMinute());
		assertEquals(56, time.getSecond());
		assertEquals(7890, time.getNano());
		time = ATime.of(23, 59, 59, 999999999);
		assertEquals(23, time.getHour());
		assertEquals(59, time.getMinute());
		assertEquals(59, time.getSecond());
		assertEquals(999999999, time.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#of(int, int, int)}.
	 */
	@Test
	public void testOfIntIntInt () {
		ATime time = ATime.of(12, 34, 56);
		assertEquals(12, time.getHour());
		assertEquals(34, time.getMinute());
		assertEquals(56, time.getSecond());
		assertEquals(0, time.getNano());
		time = ATime.of(23, 59, 59);
		assertEquals(23, time.getHour());
		assertEquals(59, time.getMinute());
		assertEquals(59, time.getSecond());
		assertEquals(0, time.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#of(int, int)}.
	 */
	@Test
	public void testOfIntInt () {
		ATime time = ATime.of(12, 34);
		assertEquals(12, time.getHour());
		assertEquals(34, time.getMinute());
		assertEquals(0, time.getSecond());
		assertEquals(0, time.getNano());
		time = ATime.of(23, 59);
		assertEquals(23, time.getHour());
		assertEquals(59, time.getMinute());
		assertEquals(0, time.getSecond());
		assertEquals(0, time.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#ofNanoOfDay(long)}.
	 */
	@Test
	public void testOfNanoOfDay () {
		long nanos = (14 * 3600 + 37 * 60 + 49) * 1000000000L + 987654321L;
		ATime time = ATime.ofNanoOfDay(nanos);
		assertEquals(14, time.getHour());
		assertEquals(37, time.getMinute());
		assertEquals(49, time.getSecond());
		assertEquals(987654321, time.getNano());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.ATime#ofSecondOfDay(long)}.
	 */
	@Test
	public void testOfSecondOfDay () {
		ATime time = ATime.ofSecondOfDay(14 * 3600 + 37 * 60 + 49);
		assertEquals(14, time.getHour());
		assertEquals(37, time.getMinute());
		assertEquals(49, time.getSecond());
		assertEquals(0, time.getNano());
		time = ATime.ofSecondOfDay(23 * 3600 + 59 * 60 + 59);
		assertEquals(23, time.getHour());
		assertEquals(59, time.getMinute());
		assertEquals(59, time.getSecond());
		assertEquals(0, time.getNano());
		try {
			time = ATime.ofSecondOfDay(24 * 3600 + 60 * 60 + 60);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}
		try {
			time = ATime.ofSecondOfDay(-123);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#compareTo(ATime)}.
	 */
	@Test
	public void testCompareTo () {
		ATime time1 = ATime.of(12, 34, 56);
		ATime time2 = ATime.of(12, 34, 55);
		assertTrue(time1.compareTo(time2) > 0);
		assertTrue(time2.compareTo(time1) < 0);
		time2 = ATime.of(12, 34, 56);
		assertTrue(time1.compareTo(time2) == 0);
		time1 = ATime.of(12, 34, 56, 100100100);
		time2 = ATime.of(12, 34, 56, 200200200);
		assertTrue(time1.compareTo(time2) < 0);
		assertTrue(time2.compareTo(time1) > 0);
		time2 = ATime.of(12, 34, 56, 100100100);
		assertTrue(time1.compareTo(time2) == 0);
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#equals(ATime)}.
	 */
	@Test
	public void testEquals () {
		ATime time1 = ATime.of(23, 59, 59);
		ATime time2 = ATime.of(23, 59, 59);
		assertTrue(time1.equals(time2));
		time2 = ATime.of(11, 12, 13);
		assertFalse(time1.equals(time2));
		time1 = ATime.of(10, 11, 12, 123456789);
		time2 = ATime.of(10, 11, 12, 123456789);
		assertTrue(time1.equals(time2));
		time2 = ATime.of(10, 11, 12, 987654321);
		assertFalse(time1.equals(time2));
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#isBefore(ATime)}.
	 */
	@Test
	public void testIsBefore () {
		ATime time1 = ATime.of(12, 34, 56, 7890);
		ATime time2 = ATime.of(12, 34, 56, 7891);
		assertTrue(time1.isBefore(time2));
		assertFalse(time2.isBefore(time1));
		time2 = ATime.of(12, 34, 56, 7890);
		assertFalse(time1.isBefore(time2));
		time1 = ATime.of(23, 59, 59, 100100100);
		time2 = ATime.of(10, 11, 13, 100100100);
		assertFalse(time1.isBefore(time2));
		assertTrue(time2.isBefore(time1));
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#isAfter(ATime)}.
	 */
	@Test
	public void testIsAfter () {
		ATime time1 = ATime.of(20, 10, 30);
		ATime time2 = ATime.of(20, 10, 15);
		assertTrue(time1.isAfter(time2));
		assertFalse(time2.isAfter(time1));
		time2 = ATime.of(20, 10, 30);
		assertFalse(time1.isAfter(time2));
		assertFalse(time2.isAfter(time1));
		time1 = ATime.of(5, 13, 40, 999);
		time2 = ATime.of(5, 13, 40, 1000);
		assertFalse(time1.isAfter(time2));
		assertTrue(time2.isAfter(time1));
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#withHour(int)}.
	 */
	@Test
	public void testWithHour () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.withHour(20);
		assertEquals(20, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time2 = time.withHour(0);
		assertEquals(0, time2.getHour());
		try {
			time2 = time.withHour(99);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#withMinute(int)}.
	 */
	@Test
	public void testWithMinute () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.withMinute(55);
		assertEquals(12, time2.getHour());
		assertEquals(55, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time2 = time.withMinute(0);
		assertEquals(0, time2.getMinute());
		try {
			time2 = time.withMinute(99);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#withSecond(int)}.
	 */
	@Test
	public void testWithSecond () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.withSecond(16);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(16, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time2 = time.withSecond(0);
		assertEquals(0, time2.getSecond());
		try {
			time2 = time.withSecond(99);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#withNano(int)}.
	 */
	@Test
	public void testWithNano () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.withNano(9870);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(9870, time2.getNano());
		time2 = time.withNano(0);
		assertEquals(0, time2.getNano());
		try {
			time2 = time.withNano(1000000000);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			// expected
		}
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#plusHours(long)}.
	 */
	@Test
	public void testPlusHours () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.plusHours(4);
		assertEquals(16, time2.getHour());
		time2 = time.plusHours(8);
		assertEquals(20, time2.getHour());
		time2 = time.plusHours(12);
		assertEquals(0, time2.getHour());
		time2 = time.plusHours(16);
		assertEquals(4, time2.getHour());
		time2 = time.plusHours(20);
		assertEquals(8, time2.getHour());
		time2 = time.plusHours(24);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#plusMinutes(long)}.
	 */
	@Test
	public void testPlusMinutes () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.plusMinutes(10);
		assertEquals(44, time2.getMinute());
		time2 = time.plusMinutes(20);
		assertEquals(54, time2.getMinute());
		time2 = time.plusMinutes(30);
		assertEquals(13, time2.getHour());
		assertEquals(04, time2.getMinute());
		time = time.plusHours(11);
		assertEquals(23, time.getHour());
		time2 = time.plusMinutes(30);
		assertEquals(0, time2.getHour());
		assertEquals(4, time2.getMinute());
		time2 = time.plusMinutes(60);
		assertEquals(0, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#plusSeconds(long)}.
	 */
	@Test
	public void testPlusSeconds () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.plusSeconds(3);
		assertEquals(59, time2.getSecond());
		time2 = time.plusSeconds(4);
		assertEquals(0, time2.getSecond());
		assertEquals(35, time2.getMinute());
		time = ATime.of(23, 59, 56, 123456789);
		time2 = time.plusSeconds(4);
		assertEquals(0, time2.getHour());
		assertEquals(0, time2.getMinute());
		assertEquals(0, time2.getSecond());
		assertEquals(123456789, time2.getNano());
		time2 = time.plusSeconds(94);
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#plusNanos(long)}.
	 */
	@Test
	public void testPlusNanos () {
		ATime time = ATime.of(12, 34, 56, 0);
		ATime time2 = time.plusNanos(999999999);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(999999999, time2.getNano());
		time = ATime.of(23, 59, 59, 999999999);
		time2 = time.plusNanos(1);
		assertEquals(0, time2.getHour());
		assertEquals(0, time2.getMinute());
		assertEquals(0, time2.getSecond());
		assertEquals(0, time2.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#minusHours(long)}.
	 */
	@Test
	public void testMinusHours () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.minusHours(4);
		assertEquals(8, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time2 = time.minusHours(20);
		assertEquals(16, time2.getHour());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#minusMinutes(long)}.
	 */
	@Test
	public void testMinusMinutes () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.minusMinutes(30);
		assertEquals(12, time2.getHour());
		assertEquals(4, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time2 = time.minusMinutes(34);
		assertEquals(12, time2.getHour());
		assertEquals(0, time2.getMinute());
		time2 = time.minusMinutes(35);
		assertEquals(11, time2.getHour());
		assertEquals(59, time2.getMinute());
		time2 = time.minusMinutes(12 * 60);
		assertEquals(0, time2.getHour());
		assertEquals(34, time2.getMinute());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#minusSeconds(long)}.
	 */
	@Test
	public void testMinusSeconds () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.minusSeconds(30);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(26, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time2 = time.minusSeconds(56);
		assertEquals(34, time2.getMinute());
		assertEquals(0, time2.getSecond());
		time2 = time.minusSeconds(57);
		assertEquals(33, time2.getMinute());
		assertEquals(59, time2.getSecond());
		time2 = time.minusSeconds(34 * 60);
		assertEquals(0, time2.getMinute());
		assertEquals(56, time2.getSecond());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#minusNanos(long)}.
	 */
	@Test
	public void testMinusNanos () {
		ATime time = ATime.of(12, 34, 56, 7890);
		ATime time2 = time.minusNanos(7000);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(890, time2.getNano());
		time2 = time.minusNanos(7890);
		assertEquals(56, time2.getSecond());
		assertEquals(0, time2.getNano());
		time2 = time.minusNanos(7891);
		assertEquals(55, time2.getSecond());
		assertEquals(999999999, time2.getNano());
		time2 = time.minusNanos((long) 56 * 1000000000);
		assertEquals(0, time2.getSecond());
		assertEquals(7890, time2.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#toCalendar()}.
	 */
	@Test
	public void testToCalendar () {
		ATime time = ATime.of(12, 34, 56, 789000000);
		Calendar cal = time.toCalendar();
		assertEquals(12, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(34, cal.get(Calendar.MINUTE));
		assertEquals(56, cal.get(Calendar.SECOND));
		assertEquals(789, cal.get(Calendar.MILLISECOND));
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#toDate()}.
	 */
	@Test
	public void testToDate () {
		ATime time = ATime.of(22, 11, 44, 999999999);
		Date date = time.toDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		assertEquals(22, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(11, cal.get(Calendar.MINUTE));
		assertEquals(44, cal.get(Calendar.SECOND));
		assertEquals(999, cal.get(Calendar.MILLISECOND));
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#toSecondOfDay()}.
	 */
	@Test
	public void testToSecondOfDay () {
		ATime time = ATime.of(12, 34, 56);
		int secondOfDay = time.toSecondOfDay();
		ATime time2 = ATime.ofSecondOfDay(secondOfDay);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(0, time2.getNano());
		time = ATime.of(23, 59, 59, 999999999);
		secondOfDay = time.toSecondOfDay();
		time2 = ATime.ofSecondOfDay(secondOfDay);
		assertEquals(23, time2.getHour());
		assertEquals(59, time2.getMinute());
		assertEquals(59, time2.getSecond());
		assertEquals(0, time2.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#toNanoOfDay()}.
	 */
	@Test
	public void testToNanoOfDay () {
		ATime time = ATime.of(12, 34, 56, 7890);
		long nanoOfDay = time.toNanoOfDay();
		ATime time2 = ATime.ofNanoOfDay(nanoOfDay);
		assertEquals(12, time2.getHour());
		assertEquals(34, time2.getMinute());
		assertEquals(56, time2.getSecond());
		assertEquals(7890, time2.getNano());
		time = ATime.of(23, 59, 59, 999999999);
		nanoOfDay = time.toNanoOfDay();
		time2 = ATime.ofNanoOfDay(nanoOfDay);
		assertEquals(23, time2.getHour());
		assertEquals(59, time2.getMinute());
		assertEquals(59, time2.getSecond());
		assertEquals(999999999, time2.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#from(Date)}.
	 */
	@Test
	public void testFromDate () {
		Date date = new Date();
		ATime time = ATime.from(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), time.getHour());
		assertEquals(cal.get(Calendar.MINUTE), time.getMinute());
		assertEquals(cal.get(Calendar.SECOND), time.getSecond());
		assertEquals(cal.get(Calendar.MILLISECOND), time.getNano() / 1000000);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 34);
		cal.set(Calendar.SECOND, 56);
		cal.set(Calendar.MILLISECOND, 789);
		date = cal.getTime();
		time = ATime.from(date);
		assertEquals(12, time.getHour());
		assertEquals(34, time.getMinute());
		assertEquals(56, time.getSecond());
		assertEquals(789000000, time.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#from(Calendar)}.
	 */
	@Test
	public void testFromCalendar () {
		Calendar cal = Calendar.getInstance();
		ATime time = ATime.from(cal);
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), time.getHour());
		assertEquals(cal.get(Calendar.MINUTE), time.getMinute());
		assertEquals(cal.get(Calendar.SECOND), time.getSecond());
		assertEquals(cal.get(Calendar.MILLISECOND), time.getNano() / 1000000);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 34);
		cal.set(Calendar.SECOND, 56);
		cal.set(Calendar.MILLISECOND, 789);
		time = ATime.from(cal);
		assertEquals(12, time.getHour());
		assertEquals(34, time.getMinute());
		assertEquals(56, time.getSecond());
		assertEquals(789000000, time.getNano());
		passed = true;
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#parse(String)}.
	 */
	@Test
	public void testParse () {
		doParse("09", 9, 0, 0, 0);
		doParse("0910", 9, 10, 0, 0);
		doParse("091011", 9, 10, 11, 0);
		doParse("091011.123456789", 9, 10, 11, 123456789);
		doParse("23:24", 23, 24, 0, 0);
		doParse("23:24:25", 23, 24, 25, 0);
		doParse("23:59:59.999999999", 23, 59, 59, 999999999);
		doParse("24:59:59.999999999", -1, 0, 0, 0);
		doParse("23:60:59.999999999", -1, 0, 0, 0);
		doParse("23:59:60.999999999", -1, 0, 0, 0);
		doParse("23:59:59.9999999991", -1, 0, 0, 0);
		doParse("18:", -1, 0, 0, 0);
		passed = true;
	}
	
	private void doParse (String dateString, int h, int m, int s, int n) {
		try {
			ATime time = ATime.parse(dateString);
			if (h < 0) {
				fail("Exception expected");
			}
			assertEquals(h, time.getHour());
			assertEquals(m, time.getMinute());
			assertEquals(s, time.getSecond());
			assertEquals(n, time.getNano());
		} catch (IllegalArgumentException ex) {
			if (h >= 0) {
				fail("Exception not expected");
			}
		}
	}
	
	/**
	 * Test method for {@link com.fidelis.valface.ATime#format(String)}.
	 */
	@Test
	public void testFormat () {
		ATime time = ATime.of(20, 4, 6, 123456789);
		String f = time.format("hh:mm:ss nano");
		assertEquals("08:04:06 123456789", f);
		f = time.format("<Hour: >Hour <Minute: >Minute <Second: >Second <Nano: >Nano");
		assertEquals("Hour: 20 Minute: 4 Second: 6 Nano: 123456789", f);
		String fmt = "Hour hour HH hh - Minute minute mm - Second second ss - milli micro nano AMPM";
		f = time.format(fmt);
		assertEquals("20 8 20 08 - 4 04 04 - 6 06 06 - 123 123456 123456789 PM", f);
		f = time.format("hh : mm : ss  nn1,nn2,nn3 ampm");
		assertEquals("08 : 04 : 06  123,456,789 pm", f);
		passed = true;
	}
	
}
