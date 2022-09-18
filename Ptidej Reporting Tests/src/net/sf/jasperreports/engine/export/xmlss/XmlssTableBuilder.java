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

import net.sf.jasperreports.engine.util.JRStringUtil;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: XmlssTableBuilder.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class XmlssTableBuilder 
{

	private int reportIndex = 0;
	private Writer bodyWriter = null;
	private Writer styleWriter = null;
	private boolean isFrame = false;
	

	protected XmlssTableBuilder(
		String name, 
		Writer bodyWriter,
		Writer styleWriter
		) 
	{
		this.isFrame = true;
		
		this.bodyWriter = bodyWriter;
		this.styleWriter = styleWriter;

	}

	protected XmlssTableBuilder(
		int reportIndex,
		int pageIndex,
		Writer bodyWriter,
		Writer styleWriter
		) 
	{
		this.isFrame = false;
		
		this.reportIndex = reportIndex;
		this.bodyWriter = bodyWriter;
		this.styleWriter = styleWriter;

	}
	
	public void buildTableHeader() throws IOException 
	{
		this.bodyWriter.write("<Table>\n");
	}
	
	public void buildTableFooter() throws IOException 
	{
		this.bodyWriter.write("</Table>\n");
	}
	
	public void buildRowHeader(int rowIndex, int rowHeight) throws IOException 
	{
		this.bodyWriter.write("<Row");
		this.bodyWriter.write(" ss:AutoFitHeight=\"0\"");
		this.bodyWriter.write(" ss:Height=\"" + rowHeight + "\"");
		this.bodyWriter.write(">\n");
	}
	
	public void buildRowFooter() throws IOException 
	{
		this.bodyWriter.write("</Row>\n");
	}

	public void buildColumnTag(int colIndex, int colWidth) throws IOException 
	{
		this.bodyWriter.write("<Column");		
		this.bodyWriter.write(" ss:AutoFitWidth=\"0\"");
		this.bodyWriter.write(" ss:Width=\"" + colWidth + "\"");
		this.bodyWriter.write("/>\n");
	}

	public void buildCellHeader(String cellStyleID, int colSpan, int rowSpan, String hyperlinkURL, String tooltip, String formula) throws IOException 
	{
		this.bodyWriter.write("<Cell");
		if (cellStyleID != null)
			this.bodyWriter.write(" ss:StyleID=\"" + cellStyleID + "\"");
		if (colSpan > 1)
			this.bodyWriter.write(" ss:MergeAcross=\"" + colSpan + "\"");
		if (rowSpan > 1)
			this.bodyWriter.write(" ss:MergeDown=\"" + rowSpan + "\"");
		if (formula != null)
			this.bodyWriter.write(" ss:Formula=\"" + formula + "\"");
		if(hyperlinkURL != null)
		{
			this.bodyWriter.write(" ss:HRef=\"" + hyperlinkURL + "\"");
			if(tooltip != null)
			{
				this.bodyWriter.write(" x:HRefScreenTip=\"" + JRStringUtil.xmlEncode(tooltip) + "\"");
			}
		}
		this.bodyWriter.write(">\n");
	}

	public void buildCellFooter() throws IOException {
		this.bodyWriter.write("</Cell>\n");
	}

	public void buildEmptyCell(int emptyCellColSpan, int emptyCellRowSpan) throws IOException
	{
		this.bodyWriter.write("<Cell");
		if (emptyCellColSpan > 1)
		{
			this.bodyWriter.write(" ss:MergeAcross=\"" + (emptyCellColSpan - 1) + "\"");
		}
		if (emptyCellRowSpan > 1)
		{
			this.bodyWriter.write(" ss:MergeDown=\"" + (emptyCellRowSpan - 1) + "\"");
		}
		this.bodyWriter.write("/>\n");
	}
	
}