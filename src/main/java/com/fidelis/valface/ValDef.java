/**
 * 
 */
package com.fidelis.valface;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fidelis.argface.Debug;

/**
 * Validation Definition.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 *
 */
public class ValDef {
	
	private String	name;
	private ValType	type;
	private String	varName;
	private ValData varData;
	private Method	varSetter;
	private Field	varField;
	private boolean ignoreCase;
	private List<ValItem> items = new ArrayList<ValItem>();
	
	private static final String[] DATE_DEFAULTS = {
		"yyyy-MM-dd",
		"MM/dd/yy",
		"MM/dd/yyyy",
		"dd-MMM-yy",
		"dd-MMM-yyyy",
		"MMMM dd, yyyy"
	};
	
	public void addItem (ValItem item) {
		items.add(item);
	}
	
	public boolean parse (String token) {
		if (token.equalsIgnoreCase("ignoreCase")) {
			setIgnoreCase(true);
			return true;
		}
		if (type == null) {
			ValType t = ValType.getType(token);
			if (t != null) {
				setType(t);
				return true;
			}
		}
		if (varName == null) {
			setVarName(token);
			return true;
		}
		return false;
	}
	
	public boolean validate (String val) {
		try {
			varData = ValData.parse(type, val);
		} catch (IllegalArgumentException ex) {
			return false;
		}
		for (ValItem item : items) {
			if (item.validate(val, this)) {
				return true;
			}
		}
		return false;
	}
	
	public void defaultItems () {
		if (type == ValType.DATE) {
			for (String d : DATE_DEFAULTS) {
				ValItem item = new ValItem(d);
				addItem(item);
			}
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName (String name) {
		this.name = name;
	}
	
	/**
	 * @return the type
	 */
	public ValType getType () {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType (ValType type) {
		this.type = type;
	}

	/**
	 * @return the varName
	 */
	public String getVarName () {
		return varName;
	}

	/**
	 * @param varName the varName to set
	 */
	public void setVarName (String varName) {
		this.varName = varName;
	}

	/**
	 * @return the varData
	 */
	public ValData getVarData () {
		return varData;
	}

	/**
	 * Sets the varData for this class.
	 *
	 * @param varData the varData to set
	 */
	public void setVarData (ValData varData) {
		this.varData = varData;
	}


	/**
	 * @return the varSetter
	 */
	public Method getVarSetter () {
		return varSetter;
	}

	/**
	 * Sets the varSetter for this class.
	 *
	 * @param varSetter the varSetter to set
	 */
	public void setVarSetter (Method varSetter) {
		this.varSetter = varSetter;
	}

	/**
	 * @return the varField
	 */
	public Field getVarField () {
		return varField;
	}

	/**
	 * Sets the varField for this class.
	 *
	 * @param varField the varField to set
	 */
	public void setVarField (Field varField) {
		this.varField = varField;
	}

	/**
	 * Returns true if this definition specifies "ignoreCase".
	 * 
	 * @return true if "ignoreCase" specified
	 */
	public boolean isIgnoreCase () {
		return ignoreCase;
	}

	/**
	 * Sets the "ignoreCase" property for this validation definition.
	 *
	 * @param ignoreCase the ignoreCase to set
	 */
	public void setIgnoreCase (boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	/**
	 * @return the items
	 */
	public List<ValItem> getItems () {
		return items;
	}
	
	/**
	 * @param items the items to set
	 */
	public void setItems (List<ValItem> items) {
		this.items = items;
	}
	
	public String toString () {
		return String.format("ValDef: %s %s %s %s %s %s", name, type, varName,
				varData,
				(varSetter == null ? "*" : varSetter.getName()),
				(varField == null ? "*" : varField.getName()));
	}

}
