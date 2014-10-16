/**
 *+
 *	TokenStringTest.java
 *	1.0.0  Sep 18, 2014  Leo Hinterlang
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
 * TokenStringTest
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class TokenStringTest {
	
	private boolean passed;
	
	 @Rule public TestName testName = new TestName();

	 @Before
	 public void before () {
		 String test = testName.getMethodName();
		 System.out.printf("Testing: " + test);
		 for (int n = test.length(); n < 30; n++) {
			 System.out.print('.');
		 }
		 passed = false;
	 }
	 
	 @After
	 public void after () {
		 System.out.println(passed ? " passed" : " failed ***");
	 }

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#TokenString(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testTokenString () {
		TokenString source = new TokenString("tok1 tok2 tok3", " \t\n");
		assertNotNull(source);
		assertTrue(source.isFilterLineBreaks());
		assertTrue(source.isTrimSpaces());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#isFilterLineBreaks()}.
	 */
	@Test
	public void testIsFilterLineBreaks () {
		TokenString source = new TokenString("x y z\na b c", " \n");
		assertTrue(source.isFilterLineBreaks());
		for (String token = source.next(); token != null; token = source.next()) {
			assertFalse(token.equals("\n"));
		}
		source = new TokenString("one two three\nfour five six", " \n");
		source.setFilterLineBreaks(false);
		assertFalse(source.isFilterLineBreaks());
		boolean nl = false;
		for (String token = source.next(); token != null; token = source.next()) {
			if (token.equals("\n")) {
				nl = true;
			}
		}
		assertTrue(nl);
		assertFalse(source.isFilterLineBreaks());
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#setFilterLineBreaks(boolean)}.
	 */
	@Test
	public void testSetFilterLineBreaks () {
		testIsFilterLineBreaks();
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#isTrimSpaces()}.
	 */
	@Test
	public void testIsTrimSpaces () {
		TokenString source = new TokenString("me you  them ", " ");
		assertTrue(source.isTrimSpaces());
		for (String token = source.next(); token != null; token = source.next()) {
			assertFalse(token.equals(" "));
		}
		source = new TokenString("mine yours  theirs ", " ");
		source.setTrimSpaces(false);
		boolean spaces = false;
		for (String token = source.next(); token != null; token = source.next()) {
			if (token.equals(" ")) {
				spaces = true;
			}
		}
		assertTrue(spaces);
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#setTrimSpaces(boolean)}.
	 */
	@Test
	public void testSetTrimSpaces () {
		testIsTrimSpaces();
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#close()}.
	 */
	@Test
	public void testClose () {
		TokenString source = new TokenString("a b c", " ");
		assertTrue(source.open());
		int tokenCount = 0;
		for (String token = source.next(); token != null; token = source.next()) {
			++tokenCount;
		}
		source.close();
		assertEquals(tokenCount, 3);
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#getToken()}.
	 */
	@Test
	public void testGetToken () {
		TokenString source = new TokenString("one two three four", " ");
		for (String token = source.next(); token != null; token = source.next()) {
			String tok = source.getToken();
			assertEquals(tok, token);
			source.push();
			tok = source.getToken();
			assertEquals(tok, token);
			source.pop();
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#next()}.
	 */
	@Test
	public void testNext () {
		TokenString source = new TokenString("one two three:30 four", " :");
		int tokenCount = 0;
		for (String token = source.next(); token != null; token = source.next()) {
			++tokenCount;
			switch (tokenCount) {
			case 1:	assertEquals(token, "one");
					break;
			case 2:	assertEquals(token, "two");
					source.push();
					break;
			case 3:	assertEquals(token, "two");
					break;
			case 4:	assertEquals(token, "three");
					break;
			case 5:	assertEquals(token, ":");
					break;
			case 6:	assertEquals(token, "30");
					break;
			case 7:	assertEquals(token, "four");
					break;
			default:	fail("end of tokens");
					break;
			}
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#open()}.
	 */
	@Test
	public void testOpen () {
		testClose();
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#peek()}.
	 */
	@Test
	public void testPeek () {
		TokenString source = new TokenString("one two three", " ");
		int tokenCount = 0;
		String tok = null;
		for (String token = source.next(); token != null; token = source.next()) {
			++tokenCount;
			switch (tokenCount) {
			case 1:	assertEquals(token, "one");
					tok = source.peek();
					assertEquals(tok, "two");
					break;
			case 2:	assertEquals(token, "two");
					tok = source.peek();
					assertEquals(tok, "three");
					break;
			case 3:	assertEquals(token, "three");
					tok = source.peek();
					assertNull(tok);
					break;
			default:	fail("end of tokens");
						break;
			}
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#pop()}.
	 */
	@Test
	public void testPop () {
		TokenString source = new TokenString("red green orange", " ");
		int tokenCount = 0;
		String tok = null;
		for (String token = source.next(); token != null; token = source.next()) {
			++tokenCount;
			switch (tokenCount) {
			case 1:	assertEquals("red", token);
					source.push("black");
					break;
			case 2:	assertEquals("black", token);
					tok = source.pop();
					assertNull(tok);
					source.push("white");
					tok = source.pop();
					assertEquals("white", tok);
					break;
			case 3:	assertEquals("green", token);
					source.push("yellow");
					source.push("blue");
					break;
			case 4:	assertEquals("blue", token);
					tok = source.pop();
					assertEquals("yellow", tok);
					break;
			case 5:	assertEquals("orange", token);
					break;
			default:	fail("no more tokens");
						break;
			}
		}
		passed = true;
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#push(java.lang.String)}.
	 */
	@Test
	public void testPushString () {
		testPop();
	}

	/**
	 * Test method for {@link com.fidelis.valface.TokenString#push()}.
	 */
	@Test
	public void testPush () {
		TokenString source = new TokenString("x y:10 z", " :");
		int tokenCount = 0;
		String tok = null;
		for (String token = source.next(); token != null; token = source.next()) {
			++tokenCount;
			switch (tokenCount) {
			case 1:	assertEquals("x", token);
					source.push();
					break;
			case 2:	assertEquals("x", token);
					source.push("a");
					source.push();
					break;
			case 3:	assertEquals("x", token);
					break;
			case 4:	assertEquals("a", token);
					break;
			case 5:	assertEquals("y", token);
					break;
			case 6:	assertEquals(":", token);
					tok = source.next();
					assertEquals("10", tok);
					source.push("20");
					break;
			case 7:	assertEquals("20", token);
					break;
			case 8:	assertEquals("z", token);
					source.push();
					break;
			case 9:	assertEquals("z", token);
					break;
			default:	fail("end of tokens");
						break;
			}
		}
		passed = true;
	}
	
}
