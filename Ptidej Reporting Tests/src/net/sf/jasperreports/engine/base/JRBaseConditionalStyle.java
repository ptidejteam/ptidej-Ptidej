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
package net.sf.jasperreports.engine.base;

import net.sf.jasperreports.engine.JRAbstractObjectFactory;
import net.sf.jasperreports.engine.JRConditionalStyle;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRStyle;

/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: JRBaseConditionalStyle.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseConditionalStyle extends JRBaseStyle implements JRConditionalStyle
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;


	protected JRExpression conditionExpression = null;



	public JRBaseConditionalStyle()
	{
		super();
	}

	public JRBaseConditionalStyle(JRConditionalStyle style, JRStyle parentStyle, JRAbstractObjectFactory factory)
	{
		this.parentStyle = parentStyle;

		this.mode = style.getOwnMode();
		this.forecolor = style.getOwnForecolor();
		this.backcolor = style.getOwnBackcolor();

		this.linePen = style.getLinePen().clone(this);
		this.fill = style.getOwnFill();

		this.radius = style.getOwnRadius();

		this.scaleImage = style.getOwnScaleImage();
		this.horizontalAlignment = style.getOwnHorizontalAlignment();
		this.verticalAlignment = style.getOwnVerticalAlignment();

		this.lineBox = style.getLineBox().clone(this);

		this.rotation = style.getOwnRotation();
		this.lineSpacing = style.getOwnLineSpacing();
		this.markup = style.getOwnMarkup();

		this.pattern = style.getOwnPattern();

		this.fontName = style.getOwnFontName();
		this.isBold = style.isOwnBold();
		this.isItalic = style.isOwnItalic();
		this.isUnderline = style.isOwnUnderline();
		this.isStrikeThrough = style.isOwnStrikeThrough();
		this.fontSize = style.getOwnFontSize();
		this.pdfFontName = style.getOwnPdfFontName();
		this.pdfEncoding = style.getOwnPdfEncoding();
		this.isPdfEmbedded = style.isOwnPdfEmbedded();
		this.conditionExpression = factory.getExpression(style.getConditionExpression(), true);
	}


	public JRExpression getConditionExpression()
	{
		return this.conditionExpression;
	}
}
