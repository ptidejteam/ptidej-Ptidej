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
package net.sf.jasperreports.engine.export.oasis;

import java.io.IOException;
import java.io.Writer;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: CellStyle.java,v 1.1 2008/09/29 16:19:59 guehene Exp $
 */
public class CellStyle extends BorderStyle
{
	//private String fill = null;
	private String backcolor = null;
	
	private final String verticalAlignment;

	
	/**
	 *
	 */
	public CellStyle(Writer styleWriter, JRPrintElement element)
	{
		super(styleWriter, element);
		
		if (element.getMode() == JRElement.MODE_OPAQUE)
		{
			//fill = "solid";
			this.backcolor = JRColorUtil.getColorHexa(element.getBackcolor());
		}
		else
		{
			//fill = "none";
		}

		byte rotation = element instanceof JRPrintText ? ((JRPrintText)element).getRotation() : JRTextElement.ROTATION_NONE;
		byte vAlign = JRAlignment.VERTICAL_ALIGN_TOP;
		byte hAlign = JRAlignment.HORIZONTAL_ALIGN_LEFT;

		JRAlignment alignment = element instanceof JRAlignment ? (JRAlignment)element : null;
		if (alignment != null)
		{
			vAlign = alignment.getVerticalAlignment();
			hAlign = alignment.getHorizontalAlignment();
		}
		
		this.verticalAlignment = ParagraphStyle.getVerticalAlignment(hAlign, vAlign, rotation);
	}
	
	/**
	 *
	 */
	public String getId()
	{
		return this.backcolor + super.getId() + "|" + this.verticalAlignment; 
	}

	/**
	 *
	 */
	public void write(String cellStyleName) throws IOException
	{
		this.styleWriter.write("<style:style style:name=\"");
		this.styleWriter.write(cellStyleName);
		this.styleWriter.write("\"");
		this.styleWriter.write(" style:family=\"table-cell\">\n");
		this.styleWriter.write(" <style:table-cell-properties");		
		this.styleWriter.write(" fo:wrap-option=\"wrap\"");
		this.styleWriter.write(" style:shrink-to-fit=\"false\"");
		if (this.backcolor != null)
		{
			this.styleWriter.write(" fo:background-color=\"#");
			this.styleWriter.write(this.backcolor);
			this.styleWriter.write("\"");
		}
		
		writeBorder(TOP_BORDER);
		writeBorder(LEFT_BORDER);
		writeBorder(BOTTOM_BORDER);
		writeBorder(RIGHT_BORDER);
		
		if (this.verticalAlignment != null)
		{
			this.styleWriter.write(" style:vertical-align=\"");
			this.styleWriter.write(this.verticalAlignment);
			this.styleWriter.write("\"");
		}

		this.styleWriter.write("/>\n");
		this.styleWriter.write("</style:style>\n");
	}

}

