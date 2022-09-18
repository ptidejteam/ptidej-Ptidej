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

/*
 * Special thanks to Google 'Summer of Code 2005' program for supporting this development
 * 
 * Contributors:
 * Majid Ali Khan - majidkk@users.sourceforge.net
 * Frank Schönheit - Frank.Schoenheit@Sun.COM
 */
package net.sf.jasperreports.engine.export.oasis;

import java.io.IOException;
import java.io.Writer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: TableBuilder.java,v 1.1 2008/09/29 16:19:59 guehene Exp $
 */
public class TableBuilder 
{

	private String tableName = null;
	private int reportIndex = 0;
	private Writer bodyWriter = null;
	private Writer styleWriter = null;
	private boolean isFrame = false;
	private boolean isPageBreak = false;
	

	protected TableBuilder(
		String name, 
		Writer bodyWriter,
		Writer styleWriter
		) 
	{
		this.isFrame = true;
		this.isPageBreak = false;
		
		this.bodyWriter = bodyWriter;
		this.styleWriter = styleWriter;

		this.tableName = "TBL_" + name;
	}

	protected TableBuilder(
		int reportIndex,
		int pageIndex,
		Writer bodyWriter,
		Writer styleWriter
		) 
	{
		this.isFrame = false;
		this.isPageBreak = (reportIndex != 0 || pageIndex != 0);
		
		this.reportIndex = reportIndex;
		this.bodyWriter = bodyWriter;
		this.styleWriter = styleWriter;

		this.tableName = "TBL_" + reportIndex + "_" + pageIndex;
	}


	public void buildTableStyle() throws IOException 
	{
		this.styleWriter.write(" <style:style style:name=\"" + this.tableName + "\"");//FIXMEODT can we have only one page style per report?
		if (!this.isFrame)
		{
			this.styleWriter.write(" style:master-page-name=\"master_" + this.reportIndex +"\"");
		}
		this.styleWriter.write(" style:family=\"table\">\n");
		this.styleWriter.write("   <style:table-properties");		
		if (this.isPageBreak)
			this.styleWriter.write(" fo:break-before=\"page\"");
//		FIXMEODT
//		if (tableWidth != null)
//			styleWriter.write(" style:width=\""+ tableWidth +"in\"");
//		if (align != null)
//			styleWriter.write(" table:align=\""+ align +"\"");
//		if (margin != null)
//			styleWriter.write(" fo:margin=\""+ margin +"\"");
//		if (backGroundColor != null)
//			styleWriter.write(" fo:background-color=\""+ backGroundColor +"\"");
		this.styleWriter.write("/>\n");
		this.styleWriter.write(" </style:style>\n");
	}
	
	public void buildTableHeader() throws IOException 
	{
		this.bodyWriter.write("<table:table");
		if (this.isFrame)
		{
			this.bodyWriter.write(" is-subtable=\"true\"");
		}
		this.bodyWriter.write(" table:name=\"");
		this.bodyWriter.write(this.tableName);
		this.bodyWriter.write("\"");
		this.bodyWriter.write(" table:style-name=\"");
		this.bodyWriter.write(this.tableName);
		this.bodyWriter.write("\"");
		this.bodyWriter.write(">\n");
	}
	
	public void buildTableFooter() throws IOException 
	{
		this.bodyWriter.write("</table:table>\n");
	}
	
	public void buildRowStyle(int rowIndex, int rowHeight) throws IOException 
	{
		String rowName = this.tableName + "_row_" + rowIndex;
		this.styleWriter.write(" <style:style style:name=\"" + rowName + "\"");
		this.styleWriter.write(" style:family=\"table-row\">\n");
		this.styleWriter.write("   <style:table-row-properties");		
		this.styleWriter.write(" style:use-optimal-row-height=\"false\""); 
//FIXMEODT check this		styleWriter.write(" style:use-optimal-row-height=\"true\""); 
		this.styleWriter.write(" style:row-height=\"" + Utility.translatePixelsToInches(rowHeight) + "in\"");
		this.styleWriter.write("/>\n");
		this.styleWriter.write(" </style:style>\n");
	}

	public void buildRowHeader(int rowIndex) throws IOException 
	{
		String rowName = this.tableName + "_row_" + rowIndex;
		this.bodyWriter.write("<table:table-row");
		this.bodyWriter.write(" table:style-name=\"" + rowName + "\"");
		this.bodyWriter.write(">\n");
	}
	
	public void buildRowFooter() throws IOException 
	{
		this.bodyWriter.write("</table:table-row>\n");
	}
	
	public void buildColumnStyle(int colIndex, int colWidth) throws IOException 
	{
		String columnName = this.tableName + "_col_" + colIndex;
		this.styleWriter.write(" <style:style style:name=\"" + columnName + "\"");
		this.styleWriter.write(" style:family=\"table-column\">\n");
		this.styleWriter.write("   <style:table-column-properties");		
		this.styleWriter.write(" style:column-width=\"" + Utility.translatePixelsToInches(colWidth) + "in\"");
		this.styleWriter.write("/>\n");
		this.styleWriter.write(" </style:style>\n");
	}

	public void buildColumnHeader(int colIndex) throws IOException 
	{
		String columnName = this.tableName + "_col_" + colIndex;
		this.bodyWriter.write("<table:table-column");		
		this.bodyWriter.write(" table:style-name=\"" + columnName + "\"");
		this.bodyWriter.write(">\n");
	}

	public void buildColumnFooter() throws IOException 
	{
		this.bodyWriter.write("</table:table-column>\n");		
	}

	public void buildCellHeader(String cellStyleName, int colSpan, int rowSpan) throws IOException 
	{
		//FIXMEODT officevalue bodyWriter.write("<table:table-cell office:value-type=\"string\"");
		this.bodyWriter.write("<table:table-cell");
		if (cellStyleName != null)
			this.bodyWriter.write(" table:style-name=\"" + cellStyleName + "\"");
		if (colSpan > 1)
			this.bodyWriter.write(" table:number-columns-spanned=\"" + colSpan + "\"");
		if (rowSpan > 1)
			this.bodyWriter.write(" table:number-rows-spanned=\"" + rowSpan + "\"");
		
		this.bodyWriter.write(">\n");
	}

	public void buildCellFooter() throws IOException {
		this.bodyWriter.write("</table:table-cell>\n");
	}
	
}