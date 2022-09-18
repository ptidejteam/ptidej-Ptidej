/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.engine.util;

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRExpression;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRXmlWriteHelper.java,v 1.1 2008/09/29 16:20:17 guehene Exp $
 */
public class JRXmlWriteHelper
{
	private final Writer writer;
	
	private final List indents;
	
	private int indent;
	private final List elementStack;
	private StringBuffer buffer;
	private StackElement lastElement;
		
	protected static class Attribute
	{
		String name;
		String value;
		
		Attribute(String name, String value)
		{
			this.name = name;
			this.value = value;
		}
	}
	
	protected static class StackElement
	{
		String name;
		List atts;
		boolean hasChildren;

		StackElement(String name)
		{
			this.name = name;
			this.atts = new ArrayList();
			this.hasChildren = false;
		}
		
		void addAttribute(String attName, String value)
		{
			this.atts.add(new Attribute(attName, value));
		}
	}

	public JRXmlWriteHelper(Writer writer)
	{
		this.writer = writer;
		
		this.indents = new ArrayList();
		
		this.indent = 0;
		this.elementStack = new ArrayList();
		this.lastElement = null;
		
		clearBuffer();
	}
	
	public void writeProlog(String encoding) throws IOException
	{
		this.writer.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n");
	}
	
	public void writePublicDoctype(String rootElement, String description, String dtdLocation) throws IOException
	{
		this.writer.write("<!DOCTYPE " + rootElement + " PUBLIC \"" + description  + "\" \"" + dtdLocation + "\">\n\n");
	}
	
	public void startElement(String name)
	{
		++this.indent;
		this.lastElement = new StackElement(name);
		this.elementStack.add(this.lastElement);
	}
	
	protected void writeParents(boolean content) throws IOException
	{
		int stackSize = this.elementStack.size();
		
		int startWrite = stackSize - 1;
		while (startWrite >= 0)
		{
			StackElement element = (StackElement) this.elementStack.get(startWrite);
			
			if (element.hasChildren)
			{
				break;
			}
			
			if (startWrite < stackSize - 1)
			{
				element.hasChildren = true;
			}
			else
			{
				element.hasChildren |= content;
			}
			
			--startWrite;
		}
		
		for (int i = startWrite + 1; i < stackSize; ++i)
		{
			StackElement element = (StackElement) this.elementStack.get(i);
			writeElementAttributes(element, i);
		}
	}

	public void writeCDATA(String data) throws IOException
	{
		if (data != null)
		{
			writeParents(true);

			this.buffer.append(getIndent(this.indent));
			this.buffer.append("<![CDATA[");
			this.buffer.append(data);
			this.buffer.append("]]>\n");
			flushBuffer();
		}
	}
	
	public void writeCDATAElement(String name, String data) throws IOException
	{
		if (data != null)
		{
			writeParents(true);

			this.buffer.append(getIndent(this.indent));
			this.buffer.append('<');
			this.buffer.append(name);
			this.buffer.append("><![CDATA[");
			this.buffer.append(data);
			this.buffer.append("]]></");
			this.buffer.append(name);
			this.buffer.append(">\n");
			flushBuffer();
		}
	}
	
	public void writeCDATAElement(String name, String data, String attName, String attValue) throws IOException
	{
		writeCDATAElement(name, data, attName, (Object) attValue);
	}
	
	public void writeCDATAElement(String name, String data, String attName, Object attValue) throws IOException
	{
		if (data != null)
		{
			writeParents(true);

			this.buffer.append(getIndent(this.indent));
			this.buffer.append('<');
			this.buffer.append(name);
			if (attValue != null)
			{
				this.buffer.append(' ');
				this.buffer.append(attName);
				this.buffer.append("=\"");
				this.buffer.append(attValue);
				this.buffer.append("\"");
			}
			this.buffer.append("><![CDATA[");
			this.buffer.append(data);
			this.buffer.append("]]></");
			this.buffer.append(name);
			this.buffer.append(">\n");
			flushBuffer();
		}
	}
	
	protected void writeElementAttributes(StackElement element, int level) throws IOException
	{
		this.buffer.append(getIndent(level));
		this.buffer.append('<');
		this.buffer.append(element.name);
		for (Iterator i = element.atts.iterator(); i.hasNext();)
		{
			Attribute att = (Attribute) i.next();
			this.buffer.append(' ');
			this.buffer.append(att.name);
			this.buffer.append("=\"");
			this.buffer.append(att.value);
			this.buffer.append('"');
		}
		
		if (element.hasChildren)
		{
			this.buffer.append(">\n");
		}
		else
		{
			this.buffer.append("/>\n");
		}
		
		flushBuffer();
	}

	public void closeElement() throws IOException
	{
		closeElement(false);
	}
	
	public void closeElement(boolean skipIfEmpty) throws IOException
	{
		--this.indent;

		if (skipIfEmpty && this.lastElement.atts.size() == 0 && !this.lastElement.hasChildren)
		{
			clearBuffer();
		}
		else
		{
			writeParents(false);
			
			if (this.lastElement.hasChildren)
			{
				this.buffer.append(getIndent(this.indent));
				this.buffer.append("</");
				this.buffer.append(this.lastElement.name);
				this.buffer.append(">\n");
				flushBuffer();
			}
		}
		
		this.elementStack.remove(this.indent);
		this.lastElement = this.indent > 0 ? (StackElement) this.elementStack.get(this.indent - 1) : null;
	}
	
	protected char[] getIndent(int level)
	{
		if (level >= this.indents.size())
		{
			for (int i = this.indents.size(); i <= level; ++i)
			{
				char[] str = new char[i];
				Arrays.fill(str, '\t');
				this.indents.add(str);
			}
		}
		
		return (char[]) this.indents.get(level);
	}
	
	protected void flushBuffer() throws IOException
	{
		this.writer.write(this.buffer.toString());
		clearBuffer();
	}

	protected void clearBuffer()
	{
		this.buffer = new StringBuffer();
	}
	

	public void writeExpression(String name, JRExpression expression, boolean writeClass) throws IOException
	{
		writeExpression(name, expression, writeClass, null);
	}


	public void writeExpression(String name, JRExpression expression, boolean writeClass, String defaultClassName) throws IOException
	{
		if (expression != null)
		{
			if (writeClass &&
					(defaultClassName == null || !defaultClassName.equals(expression.getValueClassName())))
			{
				writeCDATAElement(name, expression.getText(), "class", expression.getValueClassName());
			}
			else
			{
				writeCDATAElement(name, expression.getText());
			}
		}
	}

	protected void writeAttribute(String name, String value)
	{
		this.lastElement.addAttribute(name, value);
	}
	
	public void addAttribute(String name, String value)
	{
		if (value != null)
		{
			writeAttribute(name, value);
		}
	}
	
	public void addEncodedAttribute(String name, String value)
	{
		if (value != null)
		{
			writeAttribute(name, JRStringUtil.xmlEncode(value));
		}
	}
	
	public void addAttribute(String name, String value, String defaultValue)
	{
		if (value != null && !value.equals(defaultValue))
		{
			writeAttribute(name, value);
		}
	}
	
	public void addEncodedAttribute(String name, String value, String defaultValue)
	{
		if (value != null && !value.equals(defaultValue))
		{
			writeAttribute(name, JRStringUtil.xmlEncode(value));
		}
	}
	
	public void addAttribute(String name, Object value)
	{
		if (value != null)
		{
			writeAttribute(name, String.valueOf(value));
		}
	}
	
	public void addAttribute(String name, int value)
	{
		writeAttribute(name, String.valueOf(value));
	}
	
	public void addAttributePositive(String name, int value)
	{
		if (value > 0)
		{
			writeAttribute(name, String.valueOf(value));
		}
	}
	
	public void addAttribute(String name, float value)
	{
		writeAttribute(name, String.valueOf(value));
	}
	
	public void addAttribute(String name, float value, float defaultValue)
	{
		if (value != defaultValue)
		{
			writeAttribute(name, String.valueOf(value));
		}
	}
	
	public void addAttribute(String name, double value)
	{
		writeAttribute(name, String.valueOf(value));
	}
	
	public void addAttribute(String name, double value, double defaultValue)
	{
		if (value != defaultValue)
		{
			writeAttribute(name, String.valueOf(value));
		}
	}
	
	public void addAttribute(String name, int value, int defaultValue)
	{
		if (value != defaultValue)
		{
			addAttribute(name, value);
		}
	}
	
	public void addAttribute(String name, boolean value)
	{
		writeAttribute(name, String.valueOf(value));
	}
	
	public void addAttribute(String name, boolean value, boolean defaultValue)
	{
		if (value != defaultValue)
		{
			addAttribute(name, value);
		}
	}
	
	public void addAttribute(String name, Color color)
	{
		if (color != null)
		{
			writeAttribute(name, "#" + JRColorUtil.getColorHexa(color));
		}
	}
	
	public void addAttribute(String name, Color value, Color defaultValue)
	{
		if (value != null && value.getRGB() != defaultValue.getRGB())
		{
			addAttribute(name, value);
		}
	}
	
	public void addAttribute(String name, byte value, Map xmlValues)
	{
		String xmlValue = (String) xmlValues.get(new Byte(value));
		writeAttribute(name, xmlValue);
	}
	
	public void addAttribute(String name, int value, Map xmlValues)
	{
		String xmlValue = (String) xmlValues.get(Integer.valueOf(value));
		writeAttribute(name, xmlValue);
	}
	
	public void addAttribute(String name, byte value, Map xmlValues, byte defaultValue)
	{
		if (value != defaultValue)
		{
			addAttribute(name, value, xmlValues);
		}
	}
	
	public void addAttribute(String name, Object value, Map xmlValues)
	{
		if (value != null)
		{
			String xmlValue = (String) xmlValues.get(value);
			writeAttribute(name, xmlValue);
		}
	}
	
	public void addAttribute(String name, Object value, Map xmlValues, Object defaultValue)
	{
		if (!value.equals(defaultValue))
		{
			addAttribute(name, value, xmlValues);
		}
	}
}
