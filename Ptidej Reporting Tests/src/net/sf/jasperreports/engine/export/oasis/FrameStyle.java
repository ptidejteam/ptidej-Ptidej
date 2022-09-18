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

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: FrameStyle.java,v 1.1 2008/09/29 16:19:59 guehene Exp $
 */
public class FrameStyle extends BorderStyle
{
	/**
	 *
	 */
	private String fill = null;
	private String backcolor = null;

	/**
	 *
	 */
	public FrameStyle(Writer styleWriter, JRPrintElement element)
	{
		super(styleWriter, element);
		
		if (element.getMode() == JRElement.MODE_OPAQUE)
		{
			this.fill = "solid";
			this.backcolor = JRColorUtil.getColorHexa(element.getBackcolor());
		}
		else
		{
			this.fill = "none";
		}
	}
	
	/**
	 *
	 */
	public String getId()
	{
		return this.fill + "|" + this.backcolor + "|" + super.getId(); 
	}

	/**
	 *
	 */
	public void write(String frameStyleName) throws IOException
	{
		this.styleWriter.write("<style:style style:name=\"");
		this.styleWriter.write(frameStyleName);
		this.styleWriter.write("\" style:family=\"graphic\"");
//		styleWriter.write(" style:parent-style-name=\"Frame\"" +
		this.styleWriter.write(">\n");
		this.styleWriter.write(" <style:graphic-properties");
//			styleWriter.write(" style:run-through=\"foreground\"");
//			styleWriter.write(" style:wrap=\"run-through\"");
//			styleWriter.write(" style:number-wrapped-paragraphs=\"no-limit\"");
//			styleWriter.write(" style:wrap-contour=\"false\"");
		this.styleWriter.write(" style:vertical-pos=\"from-top\"");
		this.styleWriter.write(" style:vertical-rel=\"page\"");
		this.styleWriter.write(" style:horizontal-pos=\"from-left\"");
		this.styleWriter.write(" style:horizontal-rel=\"page\"");
		this.styleWriter.write(" draw:fill=\"");
		this.styleWriter.write(this.fill);
		this.styleWriter.write("\" draw:fill-color=\"#");
		this.styleWriter.write(this.backcolor);
		this.styleWriter.write("\"");

		writeBorder(TOP_BORDER);
		writeBorder(LEFT_BORDER);
		writeBorder(BOTTOM_BORDER);
		writeBorder(RIGHT_BORDER);
		
		this.styleWriter.write("/>\n");
		this.styleWriter.write("</style:style>\n");
	}

}

