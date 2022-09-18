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

import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: BorderStyle.java,v 1.1 2008/09/29 16:20:00 guehene Exp $
 */
public abstract class BorderStyle extends Style
{
	/**
	 *
	 */
	private static final String[] border = new String[]{"top", "left", "bottom", "right"};
	protected static final int TOP_BORDER = 0;
	protected static final int LEFT_BORDER = 1;
	protected static final int BOTTOM_BORDER = 2;
	protected static final int RIGHT_BORDER = 3;
	
	protected JRPrintElement element = null;
	
	private String[] borderColor = new String[4];
	private String[] borderWidth = new String[4];
	private String[] borderStyle = new String[4];
	private String[] borderPadding = new String[4];

	/**
	 *
	 */
	public BorderStyle(Writer styleWriter, JRPrintElement element)
	{
		super(styleWriter);
		
		this.element = element;
	}
	
	/**
	 *
	 */
	public void setBox(JRLineBox box) throws IOException
	{
		appendBorder(box.getTopPen(), TOP_BORDER);
		this.borderPadding[TOP_BORDER] = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(box.getTopPadding().intValue()));
		appendBorder(box.getLeftPen(), LEFT_BORDER);
		this.borderPadding[LEFT_BORDER] = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(box.getLeftPadding().intValue()));
		appendBorder(box.getBottomPen(), BOTTOM_BORDER);
		this.borderPadding[BOTTOM_BORDER] = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(box.getBottomPadding().intValue()));
		appendBorder(box.getRightPen(), RIGHT_BORDER);
		this.borderPadding[RIGHT_BORDER] = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(box.getRightPadding().intValue()));
	}

	/**
	 *
	 */
	public void setPen(JRPen pen) throws IOException
	{
		if (
			this.borderWidth[TOP_BORDER] == null
			&& this.borderWidth[LEFT_BORDER] == null
			&& this.borderWidth[BOTTOM_BORDER] == null
			&& this.borderWidth[RIGHT_BORDER] == null
			)
		{
			appendBorder(pen, TOP_BORDER);
			appendBorder(pen, LEFT_BORDER);
			appendBorder(pen, BOTTOM_BORDER);
			appendBorder(pen, RIGHT_BORDER);
		}
	}

	/**
	 *
	 */
	public String getId()
	{
		return 
			this.borderWidth[TOP_BORDER] + "|" + this.borderColor[TOP_BORDER] + "|" + this.borderStyle[TOP_BORDER] + "|" + this.borderPadding[TOP_BORDER]
			+ "|" + this.borderWidth[LEFT_BORDER] + "|" + this.borderColor[LEFT_BORDER] + "|" + this.borderStyle[LEFT_BORDER] + "|" + this.borderPadding[LEFT_BORDER]
			+ "|" + this.borderWidth[BOTTOM_BORDER] + "|" + this.borderColor[BOTTOM_BORDER] + "|" + this.borderStyle[BOTTOM_BORDER] + "|" + this.borderPadding[BOTTOM_BORDER]
			+ "|" + this.borderWidth[RIGHT_BORDER] + "|" + this.borderColor[RIGHT_BORDER] + "|" + this.borderStyle[RIGHT_BORDER] + "|" + this.borderPadding[RIGHT_BORDER]; 
	}

	/**
	 *
	 */
	protected void writeBorder(int side) throws IOException
	{
		if (this.borderWidth[side] != null)
		{
			this.styleWriter.write(" fo:border-");
			this.styleWriter.write(border[side]);
			this.styleWriter.write("=\"");
			this.styleWriter.write(this.borderWidth[side]);
			this.styleWriter.write("in ");
			this.styleWriter.write(this.borderStyle[side]); 
			this.styleWriter.write(" #");
			this.styleWriter.write(this.borderColor[side]);
			this.styleWriter.write("\"");
		}

		if (this.borderPadding[side] != null)
		{
			this.styleWriter.write(" fo:padding-");
			this.styleWriter.write(border[side]);
			this.styleWriter.write("=\"");
			this.styleWriter.write(this.borderPadding[side]);
			this.styleWriter.write("in\"");
		}
	}

	/**
	 *
	 */
	private void appendBorder(JRPen pen, int side) throws IOException
	{
		double width = pen.getLineWidth().doubleValue();
		String style = null;

		if (width > 0f)
		{
			switch (pen.getLineStyle().byteValue())//FIXMEBORDER is this working? deal with double border too.
			{
				case JRPen.LINE_STYLE_DOTTED :
				{
					style = "dotted";
					break;
				}
				case JRPen.LINE_STYLE_DASHED :
				{
					style = "dashed";
					break;
				}
				case JRPen.LINE_STYLE_SOLID :
				default :
				{
					style = "solid";
					break;
				}
			}

			this.borderWidth[side] = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(width));
		}
		else
		{
			style = "none";
		}

		this.borderStyle[side] = style;
		this.borderColor[side] = JRColorUtil.getColorHexa(pen.getLineColor());
	}

}

