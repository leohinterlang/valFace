/**
 *+
 *	ValPrototype.java
 *	1.0.0  2014-08-22  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.fidelis.argface.ArgReflect;

/**
 * ValFace Prototype implementation.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */
public class ValPrototype extends ValBase implements ValFace {
	
	private ArgReflect	reflect;
	
	protected ValPrototype () {
		reflect = new ArgReflect();
		reflect.setPrivateAccess(true);
	}
	
	public static ValPrototype create () {
		return new ValPrototype();
	}
	
	public static ValPrototype create (String valText, Object vi) {
		ValPrototype instance = create();
		instance.setValText(valText);
		if (instance.parseValText(vi)) {
			return instance;
		}
		return null;
	}
	
	public static ValPrototype create (String[] valText, Object vi) {
		ValPrototype instance = create();
		instance.setValText(valText);
		if (instance.parseValText(vi)) {
			return instance;
		}
		return null;
	}
	
	private boolean parseValText (Object vi) {
		reflect.setObject(vi);
		return parseValText();
	}
	
	protected boolean modelPostDefinition (ValDef def) {
		
		// Variable name specified.
		String varName = def.getVarName();
		if (varName != null) {
			
			// Find getter for return type.
			Class<?> typeClass = null;
			Method getter = reflect.findGetter(varName, null);
			if (getter != null) {
				typeClass = getter.getReturnType();
			}
			
			// No getter, try variable field.
			else {
				Field field = reflect.findField(varName, null);
				if (field != null) {
					typeClass = field.getType();
					def.setVarField(field);
				}
			}
			
			// Variable type undetermined.
			if (typeClass == null) {
				System.out.println("Named variable not found: " + varName);
			}
			
			// Set the definition data type.
			else {
				String typeName = typeClass.getSimpleName();
				ValData varData = new ValData(typeName, null);
				ValType varType = varData.getType();
				if (varType == null) {
					System.out.println("Unknown data type: " + typeName);
				} else {
					def.setVarData(varData);
				}
				
				// Find setter method. (Optional)
				Method setter = reflect.findSetter(varName, typeClass);
				if (setter != null) {
					def.setVarSetter(setter);
				}
			}
		}
		return true;
	}
	
	protected boolean modelSetValue (ValDef def, Object value) {
		Method setter = def.getVarSetter();
		Field field = def.getVarField();
		if (setter != null) {
			return reflect.setValue(setter, value);
		}
		else if (field != null) {
			return reflect.setValue(field, value);
		}
		return true;
	}
	
	protected ValType modelGetType (String varName) {
		return findFieldType(varName);
	}
	
	ValType findFieldType (String fieldName) {
		Field field = reflect.findField(fieldName, null);
		if (field == null) {
			System.out.println("Field not found: " + fieldName);
			return null;
		}
		Class<?> c = field.getType();
		String typeName = c.getSimpleName();
		System.out.println("Field: " + fieldName + " is type: " + typeName);
		return ValType.getType(typeName);
	}

}
