/**
 *+
 *	ValBase.java
 *	1.0.0  2014-08-22  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.fidelis.argface.ArgFace;
import com.fidelis.argface.Debug;

/**
 * ValFace Implementation Base Class.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */
public abstract class ValBase {
	
	private ValParser	parser = new ValParser();
	private String		valText;
	private List<ValDef> defList;
	
	protected ValBase () {
	}
	
	protected boolean parseValText () {
		if (valText == null) {
			System.out.println("ERROR - no validation text");
			return false;
		}
		if (! parser.parse(valText, this)) {
			return false;
		}
		defList = parser.getDefList();
		
		// Show the validation definitions and items.
		if (Debug.isVerbose()) {
			for (ValDef df : defList) {
				System.out.println("\n" + df);
				for (ValItem it : df.getItems()) {
					System.out.println(it);
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns the validation text.
	 * 
	 * @return the validation text
	 */
	public String getValText () {
		return valText;
	}

	/**
	 * Sets the validation text.
	 * 
	 * @param valText the valText to set
	 */
	public void setValText (String valText) {
		this.valText = valText;
	}
	
	/**
	 * Sets the validation text from an array of Strings.
	 * 
	 * @param valText the validation text array
	 */
	public void setValText (String[] valText) {
		setValText(arrayToString(valText));
	}

	public boolean validate (ArgFace argFace) {
		for (ValDef def : defList) {
			String name = def.getName();
			String val = argFace.value(name);
			if (val == null) {
				Debug.verbose("No value for: " + name);
				continue;
			}
			if (! def.validate(val)) {
				Debug.verbose("Invalid value \"" + val + "\" for " + name);
			} else {
				Debug.verbose("Value \"" + val + "\" for " + name + " is valid");
				if (! convert(def, val)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Byte getByte (String name) {
		return getNumber(name, ValType.BYTE, Byte.class);
	}
	
	public Short getShort (String name) {
		return getNumber(name, ValType.SHORT, Short.class);
	}
	
	public Integer getInteger (String name) {
		return getNumber(name, ValType.INT, Integer.class);
	}
	
	public Long getLong (String name) {
		return getNumber(name, ValType.LONG, Long.class);
	}
	
	public BigInteger getBigInteger (String name) {
		return getNumber(name, ValType.BIGINT, BigInteger.class);
	}
	
	public Float getFloat (String name) {
		return getNumber(name, ValType.FLOAT, Float.class);
	}
	
	public Double getDouble (String name) {
		return getNumber(name, ValType.DOUBLE, Double.class);
	}
	
	public BigDecimal getBigDecimal (String name) {
		return getNumber(name, ValType.BIGDEC, BigDecimal.class);
	}
	
	@SuppressWarnings ("unchecked")
	private <T extends Number> T getNumber (String name, ValType type, Class<T> target) {
		ValDef def = findDef(name);
		if (def != null) {
			ValData data = def.getVarData();
			if (data != null) {
				if (data.getType().isNumeric()) {
					if (data.numberToType(type)) {
						return (T) data.getValue();
					}
				}
			}
		}
		return null;
	}
	
	protected boolean modelPostDefinition (ValDef def) {
		return true;
	}
	
	protected boolean modelSetValue (ValDef def, Object value) {
		return true;
	}
	
	/**
	 * Post process a {@code ValDef} definition.
	 * This method is called by the parser after encountering the 
	 * equal sign of a validation definition. There may be a type
	 * and/or a variable name specified at this point.
	 * 
	 * @param def the validation definition
	 * @return true if there are no errors
	 */
	boolean postDefinition (ValDef def) {
		
		// Variable name specified.
		// Fill in definition from the model.
		String varName = def.getVarName();
		ValData varData = null;
		ValType varType = null;
		if (varName != null) {
			if (! modelPostDefinition(def)) {
				return false;
			}
			varData = def.getVarData();
			if (varData == null) {
				return false;
			}
			varType = varData.getType();
		}
		
		// No variable type, nothing more to do.
		if (varType == null) {
			return true;
		}
		
		// No type specified, set to type of variable.
		ValType type = def.getType();
		if (type == null) {
			def.setType(varType);
		}
		
		// Type and variable both specified, check compatibility.
		else if (type != varType) {
			if (! type.isCompatible(varType)) {
				System.out.println("ERROR - Incompatible types");
				System.out.println("        " + def.getName() + " is type: " + type);
				System.out.println("        " + varName + " is type: " + varType);
				
				System.out.println(type + " min = " + type.getMin() + " max = " + type.getMax());
				System.out.println(varType + " min = " + varType.getMin() + " max = " +
						varType.getMax());
				return false;
			}
		}
		return true;
	}
	
	private boolean convert (ValDef def, String val) {
		String varName = def.getVarName();
		if (varName == null) {
			return true;
		}
		ValData varData = def.getVarData();
		Object value = varData.getValue();
		if (value != null) {
			modelSetValue(def, value);
		}
		return true;
	}
	
	private ValDef findDef (String name) {
		if (defList != null) {
			for (ValDef def : defList) {
				if (def.getName().equals(name)) {
					return def;
				}
			}
		}
		return null;
	}

	/**
	  * Converts an array of Strings to a String. New line characters are added
	  * after each element of the array.
	  * 
	  * @param array the String array
	  * @return newline delimited String
	  */
	 private String arrayToString (String[] array) {
		 StringBuilder sb = new StringBuilder();
		 for (String s : array) {
			 sb.append(s);
			 sb.append('\n');
		 }
		 return sb.toString();
	 }

}
