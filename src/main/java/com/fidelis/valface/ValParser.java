/**
 *+
 *	ValParser.java
 *	1.0.0  2014-08-20  Leo Hinterlang
 *-
 */
package com.fidelis.valface;

import java.util.ArrayList;
import java.util.List;

import com.fidelis.argface.Debug;

/**
 * Parses the validation text.
 * 
 * @version 1.0.0
 * @author Leo Hinterlang
 */
public class ValParser {
	
	private static final String FAIL = "$FAIL$";
	
	private TokenSource  	source;
	private final String 	delimiters = " \t\n\'|=:;";
	private ValBase	  		valBase;
	private ValDef	   		def;
	private ValItem	  		item;
	private List<ValDef> 	defList	= new ArrayList<ValDef>(20);
	 
	 public boolean parse (String valText, ValBase valBase) {
		 this.valBase = valBase;
		 source = new TokenString(valText, delimiters);
		 String token = nextToken();
		 if (token.equalsIgnoreCase("values")) {
			 token = nextToken();
		 }
		 if (token.equals(":")) {
			 token = nextToken();
		 }
		 while (token != null) {
			 if (token.equals(FAIL)) {
				 return false;
			 }
			 def = new ValDef();
			 def.setName(token);
			 token = parseDefinition();
			 if (token == null) {
				 break;
			 }
			 if (token.equals(FAIL)) {
				 return false;
			 }
			 defList.add(def);
			 if (token.equals(";")) {
				 token = nextToken();
			 } else {
				 token = parseItems();
			 }
		 }
		 return true;
	 }
	 
	 public List<ValDef> getDefList () {
		 return defList;
	 }
	 
	 private String parseDefinition () {
		 String token = null;
		 for (token = nextToken(); token != null; token = nextToken()) {
			 if (token.equals("=") || token.equals(";")) {
				 if (! valBase.postDefinition(def)) {
					 return FAIL;
				 }
				 if (token.equals(";")) {
					 def.defaultItems();
				 }
				 break;
			 }
			 if (! def.parse(token)) {
				 System.out.println("ERROR - Invalid definition token: " + token +
						 " \"=\" expected");
				 return FAIL;
			 }
		 }
		 return token;
	 }
	    
	 private String parseItems () {
		 String token = nextToken();
		 while (token != null) {
			 if (token.equals(";")) {
				 return nextToken();
			 }
			 item = new ValItem();
			 String text = fullItem(token);
			 item.setText(text);
			 if (! item.parse(def.getType())) {
				 return FAIL;
			 }
			 def.addItem(item);
			 token = nextToken();
		 }
		 return null;
	 }
	 
	 private String fullItem (String start) {
		 String text = start;
		 String token = nextToken();
		 while (token != null) {
			 if (token.equals("|")) {
				 return text;
			 }
			 else if (token.equals(";")) {
				 source.push();
				 return text;
			 }
			 text += token;
			 token = nextToken();
		 }
		 return text;
	 }
	 
	 private String nextToken () {
		 String token = source.next();
		 Debug.verbose("token: " + token);
		 return token;
	 }
	 
}
