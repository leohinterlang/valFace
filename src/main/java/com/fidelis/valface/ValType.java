/**
 *+
 *	ValType.java
 *	1.0.0  Aug 23, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * ValFace value types.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public enum ValType {
	UNKNOWN		("unknown",		false,	false,	0,		0),
	BOOLEAN		("boolean", 	false,	false,	0,		0),
	CHAR		("char",		false,	false,	0,		0),
	BYTE		("byte",		true,	true,	Byte.MIN_VALUE,		Byte.MAX_VALUE),
	SHORT		("short",		true,	true,	Short.MIN_VALUE,	Short.MAX_VALUE),
	INT			("int",			true,	true,	Integer.MIN_VALUE,	Integer.MAX_VALUE),
	LONG		("long",		true,	true,	Long.MIN_VALUE,		Long.MAX_VALUE),
	BIGINT		("BigInteger",	true,	true,	999,	999),
	FLOAT		("float",		true,	false,	Float.MIN_VALUE,	Float.MAX_VALUE),
	DOUBLE		("double",		true,	false,	Double.MIN_VALUE,	Float.MAX_VALUE),
	BIGDEC		("BigDecimal",	true,	false,	999,	999),
	STRING		("String",		false,	false,	0,		0),
	DATE		("Date",		false,	false,	0,		0),
	TIME		("Time",		false,	false,	0,		0);
	
	private final String name;
	private final boolean numeric;
	private final boolean integral;
	private final double min;
	private final double max;
	
	private ValType (String name, boolean numeric, boolean integral,
					double min, double max) {
		this.name = name;
		this.numeric = numeric;
		this.integral = integral;
		this.min = min;
		this.max = max;
	}
	
	public static ValType getType (String name) {
		if (name.equalsIgnoreCase("integer")) {
			name = "int";
		} else if (name.endsWith("Calendar")) {
			name = "Date";
		} else if (name.endsWith("Time")) {
			name = "Time";
		}
		for (ValType type : values()) {
			if (name.equalsIgnoreCase(type.name)) {
				return type;
			}
		}
		return null;
	}
	
	public Object getDefaultValue () {
		switch (this) {
		case BOOLEAN:	return new Boolean(false);
		case CHAR:		return new Character(' ');
		case BYTE:		return new Byte((byte) 0);
		case SHORT:		return new Short((short) 0);
		case INT:		return new Integer(0);
		case LONG:		return new Long(0);
		case BIGINT:	return BigInteger.valueOf(0);
		case FLOAT:		return new Float(0.0f);
		case DOUBLE:	return new Double(0.0);
		case BIGDEC:	return new BigDecimal(0);
		case STRING:	return " ";
		case DATE:		return Calendar.getInstance();
		case TIME:		return ATime.now();
		default:		return null;
		}
	}
	
	public boolean isCompatible (ValType other) {
		if (other == STRING) {
			return true;
		}
		if (numeric) {
			if (other.min == 999) {
				return true;
			}
			if (integral) {
				if (other.integral) {
					if (min >= other.min && max <= other.max) {
						return true;
					}
				} else {
					return true;
				}
				return false;
			} else {
				if (other.integral) {
					return true;
				} else {
					if (min >= other.min && max <= other.max) {
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}
	
	public ValType getCommonType (ValType other) {
		return ValType.INT;
	}
	
	public boolean checkRange (long value) {
		if (min <= value && value <= max) {
			return true;
		}
		return false;
	}

	/**
	 * @return the name
	 */
	public String getName () {
		return name;
	}

	/**
	 * @return the numeric
	 */
	public boolean isNumeric () {
		return numeric;
	}

	/**
	 * @return the integral
	 */
	public boolean isIntegral () {
		return integral;
	}

	/**
	 * @return the min
	 */
	public double getMin () {
		return min;
	}

	/**
	 * @return the max
	 */
	public double getMax () {
		return max;
	}
	
}
