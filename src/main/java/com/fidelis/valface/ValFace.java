/**
 *+
 *	ValFace.java
 *	1.0.0  2014-08-20  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fidelis.argface.ArgFace;

/**
 * Validation Interface.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */

public interface ValFace {
	
	/**
	 * Performs validation checks.
	 * 
	 * @param argFace
	 * @return true if all entries are valid
	 */
	boolean validate (ArgFace argFace);
	
	/**
	 * Returns the named value as a Byte.
	 * 
	 * @param name the validation name
	 * @return the Byte value or null
	 */
	Byte getByte (String name);
	
	/**
	 * Returns the named value as a Short.
	 * 
	 * @param name the validation name
	 * @return the Short value or null
	 */
	Short getShort (String name);
	
	/**
	 * Returns the named value as an Integer.
	 * 
	 * @param name the validation name
	 * @return the Integer value or null
	 */
	Integer getInteger (String name);
	
	/**
	 * Returns the named value as a Long.
	 * 
	 * @param name the validation name
	 * @return the Long value or null
	 */
	Long getLong (String name);
	
	/**
	 * Returns the named value as a BigInteger.
	 * 
	 * @param name the validation name
	 * @return the BigInteger value or null
	 */
	BigInteger getBigInteger (String name);
	
	/**
	 * Returns the named value as a Float.
	 * 
	 * @param name the validation name
	 * @return the Float value or null
	 */
	Float getFloat (String name);
	
	/**
	 * Returns the named value as a Double.
	 * 
	 * @param name the validation name
	 * @return the Double value or null
	 */
	Double getDouble (String name);
	
	/**
	 * Returns the named value as a BigDecimal.
	 * 
	 * @param name the validation name
	 * @return the BigDecimal value or null
	 */
	BigDecimal getBigDecimal (String name);

}
