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
package net.sf.jasperreports.engine.xml;

import java.io.IOException;

import net.sf.jasperreports.engine.JRConditionalStyle;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRStyleContainer;
import net.sf.jasperreports.engine.util.JRXmlWriteHelper;


/**
 * Base XML writer.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRXmlBaseWriter.java,v 1.1 2008/09/29 16:19:53 guehene Exp $
 */
public abstract class JRXmlBaseWriter
{
	
	protected JRXmlWriteHelper writer;
	
	/**
	 * Sets the XML write helper.
	 * 
	 * @param aWriter the XML write helper
	 */
	protected void useWriter(JRXmlWriteHelper aWriter)
	{
		this.writer = aWriter;
	}
	
	/**
	 * Writes a style.
	 * 
	 * @param style the style to write.
	 * @throws IOException
	 */
	protected void writeStyle(JRStyle style) throws IOException
	{
		this.writer.startElement(JRXmlConstants.ELEMENT_style);
		this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, style.getName());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isDefault, style.isDefault());
		writeStyleReferenceAttr(style);
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_mode, style.getOwnMode(), JRXmlConstants.getModeMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_forecolor, style.getOwnForecolor());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_backcolor, style.getOwnBackcolor());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_fill, style.getOwnFill(), JRXmlConstants.getFillMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_radius, style.getOwnRadius());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_scaleImage, style.getOwnScaleImage(), JRXmlConstants.getScaleImageMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_hAlign, style.getOwnHorizontalAlignment(), JRXmlConstants.getHorizontalAlignMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_vAlign, style.getOwnVerticalAlignment(), JRXmlConstants.getVerticalAlignMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_rotation, style.getOwnRotation(), JRXmlConstants.getRotationMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_lineSpacing, style.getOwnLineSpacing(), JRXmlConstants.getLineSpacingMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_markup, style.getOwnMarkup());
		this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pattern, style.getOwnPattern());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isBlankWhenNull, style.isOwnBlankWhenNull());
		
		this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_fontName, style.getOwnFontName());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_fontSize, style.getOwnFontSize());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isBold, style.isOwnBold());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isItalic, style.isOwnItalic());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isUnderline, style.isOwnUnderline());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isStrikeThrough, style.isOwnStrikeThrough());
		this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfFontName, style.getOwnPdfFontName());
		this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfEncoding, style.getOwnPdfEncoding());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_isPdfEmbedded, style.isOwnPdfEmbedded());

		writePen(style.getLinePen());
		writeBox(style.getLineBox());
		
		if (toWriteConditionalStyles())
		{
			JRConditionalStyle[] conditionalStyles = style.getConditionalStyles();
			if (!(style instanceof JRConditionalStyle) && conditionalStyles != null)
			{
				for (int i = 0; i < conditionalStyles.length; i++)
				{
					writeConditionalStyle(conditionalStyles[i]);
				}
			}
		}

		this.writer.closeElement();
	}

	protected void writeStyleReferenceAttr(JRStyleContainer styleContainer)
	{
		if (styleContainer.getStyle() != null)
		{
			this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_style, styleContainer.getStyle().getName());
		}
		else if (styleContainer.getStyleNameReference() != null)
		{
			this.writer.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_style, styleContainer.getStyleNameReference());
		}
	}

	/**
	 * Decides whether conditional styles are to be written.
	 * 
	 * @return whether conditional styles are to be written
	 */
	protected abstract boolean toWriteConditionalStyles();

	/**
	 * Writes a conditional style.
	 * 
	 * @param style the conditional style
	 * @throws IOException
	 */
	protected void writeConditionalStyle(JRConditionalStyle style) throws IOException
	{
		this.writer.startElement(JRXmlConstants.ELEMENT_conditionalStyle);
		this.writer.writeExpression(JRXmlConstants.ELEMENT_conditionExpression, style.getConditionExpression(), false);
		writeStyle(style);
		this.writer.closeElement();
	}

	/**
	 *
	 */
	protected void writePen(JRPen pen) throws IOException
	{
		writePen(JRXmlConstants.ELEMENT_pen, pen);
	}

	/**
	 *
	 */
	private void writePen(String element, JRPen pen) throws IOException
	{
		this.writer.startElement(element);
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_lineWidth, pen.getOwnLineWidth());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_lineStyle, pen.getOwnLineStyle(), JRXmlConstants.getLineStyleMap());
		this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_lineColor, pen.getOwnLineColor());
		this.writer.closeElement(true);
	}

	/**
	 *
	 */
	protected void writeBox(JRLineBox box) throws IOException
	{
		if (box != null)
		{
			this.writer.startElement(JRXmlConstants.ELEMENT_box);
			
			this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_padding, box.getOwnPadding());
			this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_topPadding, box.getOwnTopPadding());
			this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_leftPadding, box.getOwnLeftPadding());
			this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_bottomPadding, box.getOwnBottomPadding());
			this.writer.addAttribute(JRXmlConstants.ATTRIBUTE_rightPadding, box.getOwnRightPadding());

			writePen(JRXmlConstants.ELEMENT_pen, box.getPen());
			writePen(JRXmlConstants.ELEMENT_topPen, box.getTopPen());
			writePen(JRXmlConstants.ELEMENT_leftPen, box.getLeftPen());
			writePen(JRXmlConstants.ELEMENT_bottomPen, box.getBottomPen());
			writePen(JRXmlConstants.ELEMENT_rightPen, box.getRightPen());

			this.writer.closeElement(true);
		}
	}

}
