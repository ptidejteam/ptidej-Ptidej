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
package net.sf.jasperreports.engine.export;

/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id: PdfFont.java,v 1.1 2008/09/29 16:20:46 guehene Exp $
 */
public class PdfFont
{
	private String pdfFontName;
	private String pdfEncoding;
	private boolean isPdfEmbedded;
	private boolean isPdfSimulatedBold;
	private boolean isPdfSimulatedItalic;

	
	public PdfFont(String pdfFontName, String pdfEncoding, boolean isPdfEmbedded)
	{
		this(pdfFontName, pdfEncoding, isPdfEmbedded, false, false);
	}

	public PdfFont(
		String pdfFontName, 
		String pdfEncoding, 
		boolean isPdfEmbedded,
		boolean isPdfSimulatedBold,
		boolean isPdfSimulatedItalic
		)
	{
		this.pdfFontName = pdfFontName;
		this.pdfEncoding = pdfEncoding;
		this.isPdfEmbedded = isPdfEmbedded;
		this.isPdfSimulatedBold = isPdfSimulatedBold;
		this.isPdfSimulatedItalic = isPdfSimulatedItalic;
	}

	public String getPdfFontName()
	{
		return this.pdfFontName;
	}

	public String getPdfEncoding()
	{
		return this.pdfEncoding;
	}

	public boolean isPdfEmbedded()
	{
		return this.isPdfEmbedded;
	}

	public boolean isPdfSimulatedBold()
	{
		return this.isPdfSimulatedBold;
	}

	public boolean isPdfSimulatedItalic()
	{
		return this.isPdfSimulatedItalic;
	}

}
