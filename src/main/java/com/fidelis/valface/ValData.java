/**
 *+
 *	ValData.java
 *	1.0.0  Aug 25, 2014  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

import com.fidelis.argface.Debug;

/**
 * ValFace Validation Data.
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
/**
 * ValData
 *
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ValData {
	
	private ValType	type;
	private Object	value;
	
	private ValData () {
		type = ValType.UNKNOWN;
		value = null;
	}
	
	public ValData (ValType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public static ValData parse (ValType targetType, String val)
		throws IllegalArgumentException {
		
		// Create a new ValData object.
		ValData valData = new ValData();
		
		// If target type is numeric, parse a number.
		if (targetType.isNumeric()) {
			Number number = parseNumber(targetType, val);
			
			// Good number. Set as the ValData value object.
			if (number != null) {
				valData.value = number;
			}
			
			// Otherwise, throw an exception.
			else {
				throw new IllegalArgumentException("Invalid number: " + val +
						" for type " + targetType);
			}
		}
		
		// If target type is a date, parse the date.
		else if (targetType == ValType.DATE) {
			Calendar date = parseDate(val);
			
			// Good date. Set the value.
			valData.value = date;
		}
		
		// If target type is a time, parse the time.
		else if (targetType == ValType.TIME) {
			ATime time = parseTime(val);
			valData.value = time;
		}
		
		// Must be String type. Set the value.
		else {
			valData.value = val;
		}
		
		// Set the ValData type.
		valData.type = targetType;
		
		// Return the new ValData.
		Debug.verbose("ValData.parse: " + valData);
		return valData;
	}
	
	public ValData (String typeName, Object value) {
		Debug.verbose("ValData typeName: " + typeName);
		type = ValType.getType(typeName);
		if (type == null) {
			System.out.println("Unrecognized type: " + typeName);
			return;
		}
		if (value == null) {
			value = type.getDefaultValue();
		} else {
			this.value = value;
		}
	}
	
	public ValData (String val) {
		Number number = convert(val);
		if (number != null) {
			value = number;
			String name = number.getClass().getSimpleName();
			if (name.equals("Integer")) {
				name = "int";
			}
			type = ValType.getType(name);
		} else {
			value = val;
			type = ValType.STRING;
		}
	}
	
	public int compareTo (ValData other) {
		if (type.isNumeric() && other.type.isNumeric()) {
			return compareNumeric(other);
		}
		return compareNonNumeric(other);
	}
	
	public boolean numberToType (ValType targetType) {
		Number number = (Number) value;
		number = numberToType(number, targetType);
		if (number != null) {
			value = number;
			type = targetType;
			return true;
		}
		return false;
	}
	
	private static Number numberToType (Number number, ValType targetType) {
		switch (targetType) {
		case BYTE:
			number = numberToClass(number, Byte.class);
			break;
		case SHORT:
			number = numberToClass(number, Short.class);
			break;
		case INT:
			number = numberToClass(number, Integer.class);
			break;
		case LONG:
			number = numberToClass(number, Long.class);
			break;
		case BIGINT:
			number = numberToClass(number, BigInteger.class);
			break;
		case FLOAT:
			number = numberToClass(number, Float.class);
			break;
		case DOUBLE:
			number = numberToClass(number, Double.class);
			break;
		case BIGDEC:
			number = numberToClass(number, BigDecimal.class);
			break;
		default:
			return null;
		}
		return number;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Number> T numberToClass (Number number, Class<T> target) {
		ValType type = null;
		if (target.isInstance(number)) {
			return (T) number;
		}
		else if (target.equals(Byte.class)) {
			type = ValType.BYTE;
			if (type.checkRange(number.longValue())) {
				return (T) new Byte(number.byteValue());
			}
		}
		else if (target.equals(Short.class)) {
			type = ValType.SHORT;
			if (type.checkRange(number.longValue())) {
				return (T) new Short(number.shortValue());
			}
		}
		else if (target.equals(Integer.class)) {
			type = ValType.INT;
			if (type.checkRange(number.longValue())) {
				return (T) new Integer(number.intValue());
			}
		}
		else if (target.equals(Long.class)) {
			type = ValType.LONG;
			if (type.checkRange(number.longValue())) {
				return (T) new Long(number.longValue());
			}
		}
		else if (target.equals(BigInteger.class)) {
			if (number instanceof BigDecimal) {
				return (T) ((BigDecimal) number).toBigInteger();
			} else {
				return (T) BigInteger.valueOf(number.longValue());
			}
		}
		else if (target.equals(Float.class)) {
			return (T) new Float(number.floatValue());
		}
		else if (target.equals(Double.class)) {
			return (T) new Double(number.doubleValue());
		}
		return null;
	}
	
	private static Number parseNumber (ValType targetType, String val) {
		Number number = null;
		switch (targetType) {
		case BYTE:
			try {
				number = Byte.decode(val);
			} catch (NumberFormatException ex) {
			}
			break;
		case SHORT:
			try {
				number = Short.decode(val);
			} catch (NumberFormatException ex) {
			}
			break;
		case INT:
			try {
				number = Integer.decode(val);
			} catch (NumberFormatException ex) {
			}
			break;
		case LONG:
			try {
				number = Long.decode(val);
			} catch (NumberFormatException ex) {
			}
			break;
		case BIGINT:
			try {
				int pos = isHex(val);
				if (pos > 0) {
					number = new BigInteger(val.substring(pos), 16);
				} else {
					pos = isOct(val);
					if (pos > 0) {
						number = new BigInteger(val.substring(pos), 8);
					}
				}
				if (number == null) {
					number = new BigInteger(val);
				}
			} catch (NumberFormatException ex) {
			}
			break;
		case FLOAT:
			try {
				number = Float.valueOf(val);
			} catch (NumberFormatException ex) {
			}
			break;
		case DOUBLE:
			try {
				number = Double.valueOf(val);
			} catch (NumberFormatException ex) {
			}
			break;
		case BIGDEC:
			try {
				number = new BigDecimal(val);
			} catch (NumberFormatException ex) {
			}
			break;
		default:
			return null;
		}
		if (number == null) {
			Debug.verbose("Try number format for: " + val);
			number = parseNumberFormat(targetType, val);
		}
		return number;
	}
	
	private static Number parseNumberFormat (ValType targetType, String val) {
		NumberFormat nf = NumberFormat.getInstance();
		ParsePosition pp = new ParsePosition(0);
		Number number = nf.parse(val, pp);
		if (pp.getErrorIndex() != -1) {
			return null;
		}
		if (pp.getIndex() < val.length()) {
			return null;
		}
		String typeName = number.getClass().getSimpleName();
		Debug.verbose("number format typeName: " + typeName + " number: " + number);
		return numberToType(number, targetType);
	}
	
	private static Calendar parseDate (String val) throws IllegalArgumentException {
		return ValDateParser.parse(val);
	}
	
	private static ATime parseTime (String val) throws IllegalArgumentException {
		return ValTimeParser.parse(val);
	}
	
	private Number convert (String val) {
		Number number = null;
		if (isIntegral(val)) {
			number = convertIntegral(val);
		} else {
			number = convertDecimal(val);
		}
		if (number == null) {
			NumberFormat nf = NumberFormat.getInstance();
			ParsePosition pp = new ParsePosition(0);
			number = nf.parse(val, pp);
			if (pp.getErrorIndex() != -1) {
				return null;
			}
			if (pp.getIndex() < val.length()) {
				return null;
			}
			if (number instanceof Long) {
				Long minInt = new Long(Integer.MIN_VALUE);
				Long maxInt = new Long(Integer.MAX_VALUE);
				int minResult = ((Long)number).compareTo(minInt);
				int maxResult = ((Long)number).compareTo(maxInt);
				if (minResult >= 0 && 0 >= maxResult) {
					number = new Integer(number.intValue());
				}
			}
		}
		return number;
	}
	
	private int compareNonNumeric (ValData other) {
		if (type == ValType.STRING && other.type == ValType.STRING) {
			return ((String)value).compareTo((String)other.value);
		}
		else if (type == ValType.DATE && other.type == ValType.DATE) {
			Debug.verbose("Compare: " + this + " to " + other);
			return ((Calendar)value).compareTo((Calendar)other.value);
		}
		else if (type == ValType.TIME && other.type == ValType.TIME) {
			Debug.verbose("Compare: " + this + " to " + other);
			return ((ATime)value).compareTo((ATime)other.value);
		}
		return 0;
	}
	
	private int compareNumeric (ValData other) {
		if (type.isIntegral()) {
			Long longValue = ((Number)value).longValue();
			Long otherLongValue = ((Number)other.value).longValue();
			Debug.verbose("compare longs: " + longValue + " to " + otherLongValue);
			return longValue.compareTo(otherLongValue);
		} else {
			Double doubleValue = ((Number)value).doubleValue();
			Double otherDoubleValue = ((Number)other.value).doubleValue();
			Debug.verbose("compare doubles: " + doubleValue + " to " + otherDoubleValue);
			return doubleValue.compareTo(otherDoubleValue);
		}
	}
	
	private static boolean decDigits (String val) {
		boolean status = true;
		for (char c : val.toCharArray()) {
			if (Character.isDigit(c)) {
				continue;
			}
			status = false;
			break;
		}
		return status;
	}
	
	private static boolean hexDigits (String val) {
		for (char c : val.toCharArray()) {
			if (Character.digit(c, 16) < 0) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean octDigits (String val) {
		for (char c : val.toCharArray()) {
			if (Character.digit(c, 8) < 0) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isDecimal (String val) {
		boolean status = false;
		String[] parts = val.split("\\.");
		if (parts.length == 2) {
			if (decDigits(parts[0]) && (decDigits(parts[1]))) {
				status = true;
			}
		}
		return status;
	}
	
	private static boolean isIntegral (String val) {
		boolean status = true;
		boolean hex = false;
		boolean oct = false;
		int pos = isHex(val);
		if (pos > 0) {
			hex = true;
		} else {
			pos = isOct(val);
			if (pos > 0) {
				oct = true;
			} else {
				pos = 0;
			}
		}
		val = val.substring(pos);
		if (hex) {
			status = hexDigits(val);
		} else if (oct) {
			status = octDigits(val);
		} else {
			status = decDigits(val);
		}
		return status;
	}
	
	private static int isHex (String val) {
		int len = val.length();
		int pos = skipSign(val);
		if (pos >= len) {
			return -1;
		}
		if (val.startsWith("0x", pos) ||
			val.startsWith("0X", pos)) {
			return pos + 2;
		} else if (val.startsWith("#", pos)) {
			return pos + 1;
		}
		return -1;
	}
	
	private static int isOct (String val) {
		int len = val.length();
		int pos = skipSign(val);
		if (pos >= len) {
			return -1;
		}
		if (val.startsWith("0", pos)) {
			return pos + 1;
		}
		return -1;
	}
	
	private static int skipSign (String val) {
		char c = val.charAt(0);
		if (c == '+' || c == '-') {
			return 1;
		}
		return 0;
	}
	
	private Number convertIntegral (String val) {
		try {
			Integer number = Integer.decode(val);
			return number;
		} catch (NumberFormatException e) {
		}
		try {
			Long number = Long.decode(val);
			return number;
		} catch (NumberFormatException e) {
		}
		try {
			BigInteger number = null;
			int pos = isHex(val);
			if (pos > 0) {
				number = new BigInteger(val.substring(pos), 16);
			} else {
				pos = isOct(val);
				if (pos > 0) {
					number = new BigInteger(val.substring(pos), 8);
				}
			}
			if (number == null) {
				number = new BigInteger(val);
			}
			return number;
		} catch (NumberFormatException e) {
		}
		return null;
	}
	
	private Number convertDecimal (String val) {
		try {
			Float number = Float.valueOf(val);
			return number;
		} catch (NumberFormatException e) {
		}
		try {
			Double number = Double.valueOf(val);
			return number;
		} catch (NumberFormatException e) {
		}
		try {
			BigDecimal number = new BigDecimal(val);
			return number;
		} catch (NumberFormatException e) {
		}
		return null;
	}
	
	/**
	 * Returns the validation type of this {@code ValData}.
	 * 
	 * @return the validation type
	 */
	public ValType getType () {
		return type;
	}
	
	/**
	 * Returns the value object of this {@code ValData}.
	 * 
	 * @return the value object
	 */
	public Object getValue () {
		return value;
	}
	
	public String toString () {
		String typeName = "*";
		if (value != null) {
			typeName = value.getClass().getSimpleName();
			if (ValType.getType(typeName) == ValType.DATE) {
				Date date = ((Calendar) value).getTime();
				DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
				return "DATE " + df.format(date);
			}
		}
		return String.format("%s (%s) %s", type, typeName, value);
	}

}
