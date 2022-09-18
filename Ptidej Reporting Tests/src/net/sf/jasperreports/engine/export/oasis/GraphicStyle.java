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
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintGraphicElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: GraphicStyle.java,v 1.1 2008/09/29 16:19:59 guehene Exp $
 */
public class GraphicStyle extends Style
{
	/**
	 *
	 */
	private String backcolor = null;
	private String forecolor = null;
	private String style = null;
	private String width = null;
	private String hAlign = null;
	private String vAlign = null;
	
	
	/**
	 *
	 */
	public GraphicStyle(Writer styleWriter, JRPrintGraphicElement element)
	{
		super(styleWriter);
		
		if (element.getMode() == JRElement.MODE_OPAQUE)
		{
			//fill = "solid";
			this.backcolor = JRColorUtil.getColorHexa(element.getBackcolor());
		}
		else
		{
			//fill = "none";
		}

		this.forecolor = JRColorUtil.getColorHexa(element.getLinePen().getLineColor());

		double doubleWidth = element.getLinePen().getLineWidth().doubleValue();
		if (doubleWidth < 0)
		{
			this.style = "none";
		}
		else
		{
			switch (element.getLinePen().getLineStyle().byteValue())
			{
				case JRPen.LINE_STYLE_DOTTED : //FIXMEBORDER
				case JRPen.LINE_STYLE_DASHED :
				{
					this.style = "dash";
					break;
				}
				case JRPen.LINE_STYLE_SOLID :
				default :
				{
					this.style = "solid";
					break;
				}
			}
		}

		this.width = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(doubleWidth));
		byte horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_LEFT;   
		byte verticalAlignment = JRAlignment.VERTICAL_ALIGN_TOP; 
				
		if(element instanceof JRPrintImage)
		{
			JRPrintImage imageElement = (JRPrintImage)element;
			horizontalAlignment = imageElement.getHorizontalAlignment();
			verticalAlignment = imageElement.getVerticalAlignment();
		}
		
		switch(horizontalAlignment)
		{
			case JRAlignment.HORIZONTAL_ALIGN_RIGHT:
			{
				this.hAlign = "right";
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED:
			{
				this.hAlign = "justified";
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_CENTER:
			{
				this.hAlign = "center";
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_LEFT:
			default:
			{
				this.hAlign = "left";
				break;
			}
		}
		
		switch(verticalAlignment)
		{
			case JRAlignment.VERTICAL_ALIGN_BOTTOM:
			{
				this.vAlign = "bottom";
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_MIDDLE:
			{
				this.vAlign = "middle";
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_TOP:
			default:
			{
				this.vAlign = "top";
				break;
			}
			
		}
	}
	
	/**
	 *
	 */
	public String getId()
	{
		//return fill + "|" + backcolor
		StringBuffer id = new StringBuffer();
		id.append(this.backcolor);
		id.append("|");
		id.append(this.forecolor);
		id.append("|");
		id.append(this.style);
		id.append("|");
		id.append(this.width);
		id.append("|");
		id.append(this.hAlign);
		id.append("|");
		id.append(this.vAlign);
		return id.toString();
	}

	/**
	 *
	 */
	public void write(String lineStyleName) throws IOException
	{
		this.styleWriter.write(" <style:style style:name=\"" + lineStyleName + "\"");
		this.styleWriter.write(" style:family=\"graphic\" style:parent-style-name=\"Graphics\">\n");
		this.styleWriter.write("   <style:graphic-properties");		
		this.styleWriter.write(" draw:fill-color=\"#" + this.backcolor + "\"");
		this.styleWriter.write(" style:horizontal-pos=\""+this.hAlign+ "\" style:horizontal-rel=\"paragraph\"");
		this.styleWriter.write(" style:vertical-pos=\""+this.vAlign+ "\" style:vertical-rel=\"paragraph\"");
		this.styleWriter.write(" svg:stroke-color=\"#" + this.forecolor + "\"");
		this.styleWriter.write(" draw:stroke=\"" + this.style + "\"");//FIXMENOW dashed borders do not work; only dashed lines and ellipses seem to work
		this.styleWriter.write(" draw:stroke-dash=\"Dashed\"");
		this.styleWriter.write(" svg:stroke-width=\"" + this.width + "in\"");
		this.styleWriter.write("/>\n");
		this.styleWriter.write("</style:style>\n");
	}

}

