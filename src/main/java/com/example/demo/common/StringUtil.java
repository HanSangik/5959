package com.example.demo.common;

public class StringUtil {
	public static String nullToValue(Object ob, String value) {
		if(ob==null)
			return value;
		else
			return ob.toString();
		
		
		//String pg = request.getParameter("pg");
		//if(pg==null) pg="";
		
		//String pg = StringUtil.nullToValue(request.getParameter("pg"), "0"); 
	}
}
