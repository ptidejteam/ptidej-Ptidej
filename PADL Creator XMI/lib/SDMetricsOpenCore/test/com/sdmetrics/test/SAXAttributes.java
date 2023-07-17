package com.sdmetrics.test;

import org.xml.sax.helpers.AttributesImpl;

public class SAXAttributes extends AttributesImpl {
	
	public void addAttribute(String name, String value)	{
		addAttribute("",name,name,"CDATA",value);
	}
}
