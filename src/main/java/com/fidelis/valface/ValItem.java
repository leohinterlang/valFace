/**
 *+
 *	ValItem.java
 *	1.0.0  2014-08-25  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.fidelis.argface.Debug;

/**
 * ValFace validation item.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */
public class ValItem {
	
	private String text;
	private boolean range;
	private boolean pattern;
	private ValData begin;
	private ValData end;
	
	public ValItem () {
	}
	
	public ValItem (String text) {
		this.text = text;
	}
	
	public boolean parse (ValType type) {
		if (text.contains("..")) {
			range = true;
			int index = text.indexOf("..");
			String beginVal = text.substring(0, index).trim();
			String endVal   = text.substring(index + 2).trim();
			try {
				begin = ValData.parse(type, beginVal);
				end   = ValData.parse(type, endVal);
			} catch (IllegalArgumentException ex) {
				System.out.println("ERROR - " + ex.getMessage());
				return false;
			}
			if (begin.compareTo(end) >= 0) {
				System.out.println("ERROR - Invalid range specification: " + text);
				return false;
			}
		} else {
			String val = text.trim();
			if (val.startsWith("@")) {
				pattern = true;
				text = val.substring(1);
			} else {
				try {
					begin = ValData.parse(type, val);
				} catch (IllegalArgumentException ex) {
					System.out.println("ERROR - " + ex.getMessage());
					return false;
				}
				Debug.verbose("begin value: " + begin);
			}
		}
		return true;
	}
	
	public boolean validate (String val, ValDef def) {
		ValType type = def.getType();
		ValData value = def.getVarData();
		if (range) {
			return validateRange(value);
		}
		if (type == ValType.DATE) {
			return validateDate(val, def);
		}
		if (type == ValType.STRING) {
			return validateText(val, def.isIgnoreCase());
		}
		return validateNumber(value);
	}
	
	private boolean validateText (String val, boolean ignoreCase) {
		if (pattern) {
			return Pattern.matches(text, val);
		}
		if (ignoreCase) {
			if (val.equalsIgnoreCase(text)) {
				return true;
			}
		} else {
			if (val.equals(text)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateDate (String val, ValDef def) {
		if (pattern) {
			Format fmt = DateFormat.getDateInstance();
			((DateFormat) fmt).setLenient(false);
			if (fmt instanceof SimpleDateFormat) {
				((SimpleDateFormat) fmt).applyPattern(text);
			}
			try {
				Date date = ((DateFormat) fmt).parse(val);
				def.setVarData(new ValData(ValType.DATE, date));
			} catch (ParseException ex) {
				return false;
			}
		}
		else {
			return validateNumber(def.getVarData());
		}
		return true;
	}
	
	private boolean validateRange (ValData value) {
		Debug.trace("validateRange: " + value);
		int beginResult = begin.compareTo(value);
		int endResult   = end.  compareTo(value);
		return (beginResult <= 0 && 0 <= endResult);
	}
	
	private boolean validateNumber (ValData value) {
		return (begin.compareTo(value) == 0);
	}

	/**
	 * @return the text
	 */
	public String getText () {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText (String text) {
		this.text = text;
	}
	
	/**
	 * @return the range
	 */
	public boolean isRange () {
		return range;
	}
	
	/**
	 * @param range the range to set
	 */
	public void setRange (boolean range) {
		this.range = range;
	}
	
	/**
	 * @return the pattern
	 */
	public boolean isPattern () {
		return pattern;
	}
	
	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern (boolean pattern) {
		this.pattern = pattern;
	}
	
	public String toString () {
		String s = String.format("ValItem: %s", text);
		if (range) {
			s += "  Range: " + begin + " .. " + end;
		} else if (begin != null) {
			s += "  Value: " + begin;
		}
		return s;
	}
	
}
