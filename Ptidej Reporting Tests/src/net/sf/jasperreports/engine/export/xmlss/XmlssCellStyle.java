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

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: XmlssCellStyle.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class XmlssCellStyle extends XmlssBorderStyle
{

	private static final String ALIGNMENT_LEFT = "Left";
	private static final String ALIGNMENT_RIGHT = "Right";
	private static final String ALIGNMENT_CENTER = "Center";
	private static final String ALIGNMENT_TOP = "Top";
	private static final String ALIGNMENT_BOTTOM = "Bottom";
	private static final String READING_ORDER_LTR = "LeftToRight";
	private static final String READING_ORDER_RTL = "RightToLeft";
	private static final String ROTATE_NONE = "0";
	private static final String ROTATE_LEFT = "90";
	private static final String ROTATE_RIGHT = "-90";
	private static final String STYLE_AUTOMATIC = "Automatic";
	private static final String STYLE_NONE = "None";
	private static final String UNDERLINE_STYLE_SINGLE = "Single";
	
	//private String fill = null;
	private final String id;

	private String backcolor = XmlssCellStyle.STYLE_AUTOMATIC;
	
	private String horizontalAlignment = XmlssCellStyle.ALIGNMENT_LEFT;
	private String verticalAlignment = XmlssCellStyle.ALIGNMENT_TOP;
	private String readingOrder = XmlssCellStyle.READING_ORDER_LTR;
	private String rotate = XmlssCellStyle.ROTATE_NONE;
	private String shrinkToFit;
	private String wrapText = "1";
	
	private JRStyle style;
	private String verticalPosition = XmlssCellStyle.STYLE_NONE;
	private String pattern;
	private String forecolor = XmlssCellStyle.STYLE_AUTOMATIC;
	private JRFont defaultFont;
	private String excelFontName;
	/**
	 *
	 */
	public XmlssCellStyle(
			Writer styleWriter, 
			JRPrintElement element,
			Color cellBackground,
			String pattern,
			boolean isShrinkToFit,
			JRFont defaultFont,
			Map fontMap)
	{
		super(styleWriter, element);

		this.style = element.getStyle() != null ? element.getStyle() : element.getDefaultStyleProvider().getDefaultStyle();
		this.defaultFont = defaultFont;
		this.pattern = pattern;
		this.shrinkToFit = String.valueOf(getBitValue(isShrinkToFit));
		
		switch (element.getMode())
		{
			case JRElement.MODE_OPAQUE: 
				this.backcolor = "#" + JRColorUtil.getColorHexa(element.getBackcolor());
				break;
			case JRElement.MODE_TRANSPARENT:
			default:
				if(cellBackground != null)
					this.backcolor = "#" + JRColorUtil.getColorHexa(cellBackground);
		}
		
		if(element.getForecolor() != null)
		{
			this.forecolor = "#" + JRColorUtil.getColorHexa(element.getForecolor());
		}
		
		byte rotation = element instanceof JRPrintText ? ((JRPrintText)element).getRotation() : JRTextElement.ROTATION_NONE;
		this.rotate = getRotation(rotation);
		
		
		if(element instanceof JRPrintText && ((JRPrintText)element).getRunDirection() == JRPrintText.RUN_DIRECTION_RTL)
			this.readingOrder = XmlssCellStyle.READING_ORDER_RTL;
			
		JRAlignment alignment = element instanceof JRAlignment ? (JRAlignment)element : null;
		if (alignment != null)
		{
			this.horizontalAlignment = getHorizontalAlignment(alignment.getHorizontalAlignment(), alignment.getVerticalAlignment(), rotation);
			this.verticalAlignment = getVerticalAlignment(alignment.getHorizontalAlignment(), alignment.getVerticalAlignment(), rotation);
		}
		
		if(this.style!= null)
		{
			String fontName = this.style.getFontName();
			this.excelFontName = (fontMap != null && fontMap.containsKey(fontName)) 
				? (String) fontMap.get(fontName) 
				: fontName;
			
			this.id =this.horizontalAlignment + "|" +
				this.verticalAlignment + "|" +
				this.readingOrder + "|" +
				this.rotate + "|" +
				this.shrinkToFit + "|" +
				super.getId() + "|" +
				this.excelFontName + "|" +
				this.style.getFontSize() + "|" +
				this.forecolor + "|" +
				this.style.isItalic() + "|" +
				this.style.isBold() + "|" +
				this.style.isStrikeThrough() + "|" +
				this.style.isUnderline() + "|" +
				this.verticalPosition + "|" +
				this.backcolor + "|" +
				this.pattern;
		}
		else
		{
			String fontName = defaultFont.getFontName();
			this.excelFontName = (fontMap != null && fontMap.containsKey(fontName)) 
				? (String) fontMap.get(fontName) 
				: fontName;
			this.id =this.horizontalAlignment + "|" +
				this.verticalAlignment + "|" +
				this.readingOrder + "|" +
				this.rotate + "|" +
				this.shrinkToFit + "|" +
				super.getId() + "|" +
				this.excelFontName + "|" +
				defaultFont.getFontSize() + "|" +
				this.forecolor + "|" +
				defaultFont.isItalic() + "|" +
				defaultFont.isBold() + "|" +
				defaultFont.isStrikeThrough() + "|" +
				defaultFont.isUnderline() + "|" +
				this.verticalPosition + "|" +
				this.backcolor + "|" +
				this.pattern;
		}
	}
	
	/**
	 *
	 */
	public String getId()
	{
		return this.id; 
	}

	/**
	 *
	 */
	public void write(String cellStyleName) throws IOException
	{
		this.styleWriter.write("<ss:Style ss:ID=\"");
		this.styleWriter.write(cellStyleName);
		this.styleWriter.write("\">\n");
		
		this.styleWriter.write(" <ss:Alignment");
		this.styleWriter.write(" ss:Horizontal=\"" + this.horizontalAlignment + "\"");
		this.styleWriter.write(" ss:Vertical=\"" + this.verticalAlignment + "\"");
		this.styleWriter.write(" ss:ReadingOrder=\"" + this.readingOrder + "\"");
		this.styleWriter.write(" ss:Rotate=\"" + this.rotate + "\"");
		this.styleWriter.write(" ss:ShrinkToFit=\"" + this.shrinkToFit + "\"");
		this.styleWriter.write(" ss:WrapText=\"" + this.wrapText + "\"");
				
		this.styleWriter.write("/>\n");

		this.styleWriter.write(" <ss:Borders>");
		writeBorder(TOP_BORDER);
		writeBorder(LEFT_BORDER);
		writeBorder(BOTTOM_BORDER);
		writeBorder(RIGHT_BORDER);
		this.styleWriter.write(" </ss:Borders>\n");
		
		this.styleWriter.write(" <ss:Font");
		this.styleWriter.write(" ss:FontName=\"" + this.excelFontName + "\"");
		if(this.style != null)
		{
			this.styleWriter.write(" ss:Size=\"" + this.style.getFontSize() + "\"");
			this.styleWriter.write(" ss:Bold=\"" + getBitValue(this.style.isBold().booleanValue()) + "\"");
			this.styleWriter.write(" ss:Italic=\"" + getBitValue(this.style.isItalic().booleanValue()) + "\"");
			this.styleWriter.write(" ss:StrikeThrough=\"" + getBitValue(this.style.isStrikeThrough().booleanValue()) + "\"");
			this.styleWriter.write(" ss:Underline=\"" + getUnderlineStyle(this.style.isUnderline().booleanValue()) + "\"");
		}
		else if(this.defaultFont != null)
		{
			this.styleWriter.write(" ss:Size=\"" + this.defaultFont.getFontSize() + "\"");
			this.styleWriter.write(" ss:Bold=\"" + getBitValue(this.defaultFont.isBold()) + "\"");
			this.styleWriter.write(" ss:Italic=\"" + getBitValue(this.defaultFont.isItalic()) + "\"");
			this.styleWriter.write(" ss:StrikeThrough=\"" + getBitValue(this.defaultFont.isStrikeThrough()) + "\"");
			this.styleWriter.write(" ss:Underline=\"" + getUnderlineStyle(this.defaultFont.isUnderline()) + "\"");
		}
		this.styleWriter.write(" ss:Color=\"" + this.forecolor + "\"");
		this.styleWriter.write("/>\n");
		
		this.styleWriter.write(" <ss:Interior");
		this.styleWriter.write(" ss:Color=\"" + this.backcolor + "\"");
		this.styleWriter.write(" ss:Pattern=\"Solid\"");
		this.styleWriter.write("/>\n");
		
		this.styleWriter.write(" <ss:NumberFormat");
		this.styleWriter.write(" ss:Format=\"" + this.pattern + "\"");
		this.styleWriter.write("/>\n");
		
		this.styleWriter.write(" <ss:Protection/>\n");
		
		this.styleWriter.write("</ss:Style>\n");
		
	}

	/**
	 *
	 */
	public static String getVerticalAlignment(
		byte horizontalAlignment, 
		byte verticalAlignment, 
		byte rotation
		)
	{
		switch(rotation)
		{
			case JRTextElement.ROTATION_LEFT:
			{
				switch (horizontalAlignment)
				{
					case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
						return XmlssCellStyle.ALIGNMENT_TOP;
					case JRAlignment.HORIZONTAL_ALIGN_CENTER :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
					case JRAlignment.HORIZONTAL_ALIGN_LEFT :
					default :
						return XmlssCellStyle.ALIGNMENT_BOTTOM;
				}
			}
			case JRTextElement.ROTATION_RIGHT:
			{
				switch (horizontalAlignment)
				{
					case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
						return XmlssCellStyle.ALIGNMENT_BOTTOM;
					case JRAlignment.HORIZONTAL_ALIGN_CENTER :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
					case JRAlignment.HORIZONTAL_ALIGN_LEFT :
					default :
						return XmlssCellStyle.ALIGNMENT_TOP;
				}
			}
			case JRTextElement.ROTATION_UPSIDE_DOWN:
			case JRTextElement.ROTATION_NONE:
			default:
			{
				switch (verticalAlignment)
				{
					case JRAlignment.VERTICAL_ALIGN_BOTTOM :
						return XmlssCellStyle.ALIGNMENT_BOTTOM;
					case JRAlignment.VERTICAL_ALIGN_MIDDLE :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JRAlignment.VERTICAL_ALIGN_TOP :
					default :
						return XmlssCellStyle.ALIGNMENT_TOP;
				}
			}
		}
	}
	
	/**
	 *
	 */
	public static String getHorizontalAlignment(
		byte horizontalAlignment, 
		byte verticalAlignment, 
		byte rotation
		)
	{
		switch(rotation)
		{
			case JRTextElement.ROTATION_LEFT:
			{
				switch (verticalAlignment)
				{
					case JRAlignment.VERTICAL_ALIGN_BOTTOM :
						return XmlssCellStyle.ALIGNMENT_RIGHT;
					case JRAlignment.VERTICAL_ALIGN_MIDDLE :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JRAlignment.VERTICAL_ALIGN_TOP :
					default :
						return XmlssCellStyle.ALIGNMENT_LEFT;
				}
			}
			case JRTextElement.ROTATION_RIGHT:
			{
				switch (verticalAlignment)
				{
					case JRAlignment.VERTICAL_ALIGN_BOTTOM :
						return XmlssCellStyle.ALIGNMENT_LEFT;
					case JRAlignment.VERTICAL_ALIGN_MIDDLE :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JRAlignment.VERTICAL_ALIGN_TOP :
					default :
						return XmlssCellStyle.ALIGNMENT_RIGHT;
				}
			}
			case JRTextElement.ROTATION_UPSIDE_DOWN:
			case JRTextElement.ROTATION_NONE:
			default:
			{
				switch (horizontalAlignment)
				{
					case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
						return XmlssCellStyle.ALIGNMENT_RIGHT;
					case JRAlignment.HORIZONTAL_ALIGN_CENTER :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
					case JRAlignment.HORIZONTAL_ALIGN_LEFT :
					default :
						return XmlssCellStyle.ALIGNMENT_LEFT;
				}
			}
		}
	}
	
	
	private String getRotation(byte rotation)
	{
		switch (rotation)
		{
			case JRTextElement.ROTATION_LEFT:
				return XmlssCellStyle.ROTATE_LEFT;
			case JRTextElement.ROTATION_RIGHT:
				return XmlssCellStyle.ROTATE_RIGHT;
			case JRTextElement.ROTATION_NONE:
			default:
				return XmlssCellStyle.ROTATE_NONE;
		}
	}
	
	private byte getBitValue(boolean isTrue)
	{
			return isTrue ? (byte)1 : 0;
	}

	private String getUnderlineStyle(boolean isUnderline)
	{
		if(isUnderline)
			return XmlssCellStyle.UNDERLINE_STYLE_SINGLE;
		return XmlssCellStyle.STYLE_NONE;
	}
	
}

