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

package net.sf.jasperreports.engine.export.xmlss;

import java.io.IOException;
import java.io.Writer;



/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: XmlssContentBuilder.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class XmlssContentBuilder
{
	/**
	 *
	 */
	public static final String VERSION = "1.0";

	/**
	 * 
	 */
	private Writer writer = null;
	private Writer styleWriter = null;
	private Writer bodyWriter = null;
	
	/**
	 * 
	 */
	public XmlssContentBuilder(
			Writer writer,
			Writer styleWriter,
			Writer bodyWriter
			)
	{
		this.writer = writer;
		this.styleWriter = styleWriter;
		this.bodyWriter = bodyWriter;
	}
	

	public void build() throws IOException
	{
		
		this.writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><?mso-application progid=\"Excel.Sheet\"?>\n");

		this.writer.write("<Workbook\n");
		this.writer.write(" xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\n");
		this.writer.write(" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n");
		this.writer.write(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n");
		this.writer.write(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n");
		this.writer.write(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">\n");
		
		this.writer.write("<ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\n");
		this.writer.write(" <WindowHeight>9000</WindowHeight>\n");
		this.writer.write(" <WindowWidth>13860</WindowWidth>\n");
		this.writer.write(" <WindowTopX>240</WindowTopX>\n");
		this.writer.write(" <WindowTopY>75</WindowTopY>\n");
		this.writer.write(" <ProtectStructure>False</ProtectStructure>\n");
		this.writer.write(" <ProtectWindows>False</ProtectWindows>\n");
		this.writer.write("</ExcelWorkbook>\n");
		
		this.writer.write("<ss:Styles>\n");
		this.writer.flush();
		
		this.writer.write(this.styleWriter.toString());
		this.writer.flush();

		this.writer.write("</ss:Styles>\n");
		this.writer.flush();
		
		this.writer.write(this.bodyWriter.toString());
		this.writer.flush();

		this.writer.write("</Workbook>\n");
		
		this.writer.flush();
		this.writer.close();
		
	}

}
