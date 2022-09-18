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
import java.util.List;

import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperPrint;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: XmlssStyleBuilder.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class XmlssStyleBuilder
{
	/**
	 * 
	 */
	private List jasperPrintList = null;
	private Writer writer = null;
	
	/**
	 * 
	 */
	public XmlssStyleBuilder(List jasperPrintList, Writer writer)
	{
		this.jasperPrintList = jasperPrintList;
		this.writer = writer;
	}

	/**
	 * 
	 */
	public void build() throws IOException
	{
		buildBeforeAutomaticStyles();
		
		for(int reportIndex = 0; reportIndex < this.jasperPrintList.size(); reportIndex++)
		{
			JasperPrint jasperPrint = (JasperPrint)this.jasperPrintList.get(reportIndex);
			
			buildPageLayout(reportIndex, jasperPrint);
		}

		buildBetweenAutomaticAndMasterStyles();

		for(int reportIndex = 0; reportIndex < this.jasperPrintList.size(); reportIndex++)
		{
			buildMasterPage(reportIndex);
		}

		buildAfterMasterStyles();
		
		this.writer.flush();
	}
	
	/**
	 * 
	 */
	private void buildBeforeAutomaticStyles() throws IOException
	{
		this.writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

		this.writer.write("<office:document-styles");
		this.writer.write(" xmlns:office=\"urn:oasis:names:tc:opendocument:xmlns:office:1.0\"");
		this.writer.write(" xmlns:style=\"urn:oasis:names:tc:opendocument:xmlns:style:1.0\"");
		this.writer.write(" xmlns:text=\"urn:oasis:names:tc:opendocument:xmlns:text:1.0\"");
		this.writer.write(" xmlns:table=\"urn:oasis:names:tc:opendocument:xmlns:table:1.0\"");
		this.writer.write(" xmlns:draw=\"urn:oasis:names:tc:opendocument:xmlns:drawing:1.0\"");
		this.writer.write(" xmlns:fo=\"urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0\"");
		this.writer.write(" xmlns:xlink=\"http://www.w3.org/1999/xlink\"");
		this.writer.write(" xmlns:dc=\"http://purl.org/dc/elements/1.1/\"");
		this.writer.write(" xmlns:meta=\"urn:oasis:names:tc:opendocument:xmlns:meta:1.0\"");
		this.writer.write(" xmlns:number=\"urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0\"");
		this.writer.write(" xmlns:svg=\"urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0\"");
		this.writer.write(" xmlns:chart=\"urn:oasis:names:tc:opendocument:xmlns:chart:1.0\"");
		this.writer.write(" xmlns:dr3d=\"urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0\"");
		this.writer.write(" xmlns:math=\"http://www.w3.org/1998/Math/MathML\"");
		this.writer.write(" xmlns:form=\"urn:oasis:names:tc:opendocument:xmlns:form:1.0\"");
		this.writer.write(" xmlns:script=\"urn:oasis:names:tc:opendocument:xmlns:script:1.0\"");
		this.writer.write(" xmlns:ooo=\"http://openoffice.org/2004/office\"");
		this.writer.write(" xmlns:ooow=\"http://openoffice.org/2004/writer\"");
		this.writer.write(" xmlns:oooc=\"http://openoffice.org/2004/calc\"");
		this.writer.write(" xmlns:dom=\"http://www.w3.org/2001/xml-events\"");
		this.writer.write(" xmlns:xforms=\"http://www.w3.org/2002/xforms\"");
		this.writer.write(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		this.writer.write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		this.writer.write(" office:version=\"");		
		this.writer.write(XmlssContentBuilder.VERSION);		
		this.writer.write("\">\n");		

		this.writer.write(" <office:styles>\n");	
		this.writer.write("<draw:stroke-dash draw:name=\"Dashed\" draw:display-name=\"Dashed\" " +
			"draw:style=\"rect\" draw:dots1=\"1\" draw:dots1-length=\"0.05cm\" draw:dots2=\"1\" " +
			"draw:dots2-length=\"0.05cm\" draw:distance=\"0.05cm\"/>");
		this.writer.write(" </office:styles>\n");	
		this.writer.write(" <office:automatic-styles>\n");	
	}
	
	/**
	 * 
	 */
	private void buildBetweenAutomaticAndMasterStyles() throws IOException
	{
		this.writer.write(" </office:automatic-styles>\n");
		this.writer.write(" <office:master-styles>\n");	
	}
	
	/**
	 * 
	 */
	private void buildAfterMasterStyles() throws IOException
	{
		this.writer.write(" </office:master-styles>\n");	
		this.writer.write("</office:document-styles>\n");
	}

	/**
	 * 
	 */
	private void buildPageLayout(int reportIndex, JasperPrint jasperPrint) throws IOException 
	{
			this.writer.write("<style:page-layout");
			this.writer.write(" style:name=\"page_" + reportIndex + "\">\n");
			
			this.writer.write("<style:page-layout-properties");
			this.writer.write(" fo:page-width=\"" + XmlssUtil.translatePixelsToInchesRound(jasperPrint.getPageWidth()) +"in\"");
			this.writer.write(" fo:page-height=\"" + XmlssUtil.translatePixelsToInchesRound(jasperPrint.getPageHeight()) +"in\"");//FIXMEODT we probably need some actualHeight trick
			this.writer.write(" fo:margin-top=\"0in\"");//FIXMEODT useless?
			this.writer.write(" fo:margin-bottom=\"0in\"");
			this.writer.write(" fo:margin-left=\"0in\"");
			this.writer.write(" fo:margin-right=\"0in\"");

			switch (jasperPrint.getOrientation())
			{
				case JRReport.ORIENTATION_LANDSCAPE:
					this.writer.write(" style:print-orientation=\"landscape\"");
					break;
				default:
					this.writer.write(" style:print-orientation=\"portrait\"");
					break;
			}
			
			this.writer.write("/>\n");
			this.writer.write("</style:page-layout>\n");
	}

	/**
	 * 
	 */
	private void buildMasterPage(int reportIndex) throws IOException 
	{
		this.writer.write("<style:master-page style:name=\"master_");
		this.writer.write(String.valueOf(reportIndex));
		this.writer.write("\" style:page-layout-name=\"page_");
		this.writer.write(String.valueOf(reportIndex));
		this.writer.write("\"/>\n");
	}

}
