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

/*
 * Contributors:
 * Ryan Johnson - delscovich@users.sourceforge.net
 * Carlton Moore - cmoore79@users.sourceforge.net
 */
package net.sf.jasperreports.engine.convert;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRReportTemplate;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRStyleContainer;
import net.sf.jasperreports.engine.JRStyleSetter;
import net.sf.jasperreports.engine.JRTemplate;
import net.sf.jasperreports.engine.JRTemplateReference;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.base.JRBasePrintFrame;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRExpressionUtil;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlTemplateLoader;

import org.apache.commons.collections.SequencedHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 * @version $Id: ReportConverter.java,v 1.1 2008/09/29 16:20:07 guehene Exp $
 */
public class ReportConverter 
{

	private static final Log log = LogFactory.getLog(ReportConverter.class);
	public static final Color GRID_LINE_COLOR = new Color(170, 170, 255);
	
	private final JRReport report;
	private JasperPrint jasperPrint;
	private JRPrintPage page;
	int pageWidth;
	private int offsetY;
	private int upColumns;
	private int downColumns;
	
	/**
	 * List containing page elements in a given order 
	 */
	private List pageElements = new ArrayList();
	
	private StyleFactory styleFactory;
	protected Map stylesMap;
	protected final boolean cacheStyles;

	
	/**
	 *
	 */
	public ReportConverter(JRReport report, boolean ignoreContent, boolean cacheStyles)
	{
		this.report = report;
		this.cacheStyles = cacheStyles;
		
		if (report instanceof JasperDesign)
		{
			((JasperDesign)report).preprocess();
		}
		
		convert(ignoreContent);
	}
	
	/**
	 *
	 */
	protected void convert(boolean ignoreContent)
	{
		this.jasperPrint = new JasperPrint();
		
		this.jasperPrint.setDefaultFont(this.report.getDefaultFont());
		this.jasperPrint.setFormatFactoryClass(this.report.getFormatFactoryClass());
		//FIXME locale and timezone settings jasperprint object
		//jasperPrint.setLocaleCode(JRDataUtils.getLocaleCode(Locale.getDefault()));
		//jasperPrint.setTimeZoneId(JRDataUtils.getTimeZoneId(TimeZone.getDefault()));
		this.jasperPrint.setName(this.report.getName());
		this.jasperPrint.setOrientation(this.report.getOrientation());
		this.jasperPrint.setPageWidth(this.report.getPageWidth());
		this.jasperPrint.setPageHeight(this.report.getPageHeight());
		
		JRProperties.transferProperties(this.report, this.jasperPrint, JasperPrint.PROPERTIES_PRINT_TRANSFER_PREFIX);

		setStyles(this.report);

		if (!ignoreContent)
		{
			this.pageWidth = this.report.getPageWidth();
			this.page = new JRBasePrintPage();
			
			this.offsetY = this.report.getTopMargin();

			addBand(this.report.getBackground());
			addBand(this.report.getTitle());
			addBand(this.report.getPageHeader());
			this.upColumns = this.offsetY;
			addBand(this.report.getColumnHeader());

			JRGroup[] groups = this.report.getGroups();
			if (groups != null)
			{
				for (int i = 0; i < groups.length ; i++)
				{
					addBand(groups[i].getGroupHeader());
				}
			}
			
			addBand(this.report.getDetail());

			if (groups != null)
			{
				for (int i = 0; i < groups.length ; i++)
				{
					addBand(groups[i].getGroupFooter());
				}
			}
			
			addBand(this.report.getColumnFooter());
			this.downColumns = this.offsetY;
			addBand(this.report.getPageFooter());
			addBand(this.report.getLastPageFooter());
			addBand(this.report.getSummary());
			addBand(this.report.getNoData());
			
			this.jasperPrint.setPageHeight(this.offsetY + this.report.getBottomMargin());
			
			// column dotted delimitation 
			int colX = this.report.getLeftMargin();
			for(int i = 0; i < this.report.getColumnCount(); i++)
			{
				addColumnSeparator(colX);
				colX += this.report.getColumnWidth();
				addColumnSeparator(colX);
				colX += this.report.getColumnSpacing();
			}

			// page dotted contour line
			addHorizontalGridLine(0, this.report.getTopMargin(), this.pageWidth);
			addHorizontalGridLine(0, this.offsetY, this.pageWidth);
			addVerticalGridLine(this.report.getLeftMargin(), 0, this.jasperPrint.getPageHeight());
			addVerticalGridLine(this.pageWidth - this.report.getRightMargin(), 0, this.jasperPrint.getPageHeight());

			this.page.setElements(this.pageElements);
			this.jasperPrint.addPage(this.page);
		}
	}

	protected void setStyles(JRReport report)
	{
		this.styleFactory = new StyleFactory();
		this.stylesMap = new SequencedHashMap();
		
		loadReportStyles(report);
		
		try
		{
			for (Iterator it = this.stylesMap.values().iterator(); it.hasNext();)
			{
				JRStyle style = (JRStyle) it.next();
				this.jasperPrint.addStyle(style);
			}
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}

		JRStyle reportDefault = report.getDefaultStyle();
		JRStyle printDefault = null;
		if (reportDefault == null)
		{
			//search for the last default style
			for (Iterator it = this.stylesMap.values().iterator(); it.hasNext();)
			{
				JRStyle style = (JRStyle) it.next();
				if (style.isDefault())
				{
					printDefault = style;
				}
			}
		}
		else
		{
			printDefault = reportDefault;
		}
		
		if (printDefault != null)
		{
			this.jasperPrint.setDefaultStyle(printDefault);
		}		
	}

	protected void loadReportStyles(JRReport report)
	{
		JRReportTemplate[] templates = report.getTemplates();
		if (templates != null)
		{
			Set loadedLocations = new HashSet();
			for (int i = 0; i < templates.length; i++)
			{
				loadReportTemplateStyles(templates[i], loadedLocations);
			}
		}
		
		collectStyles(report.getStyles());
	}

	protected void loadReportTemplateStyles(JRReportTemplate template, Set loadedLocations)
	{
		JRExpression sourceExpression = template.getSourceExpression();
		if (sourceExpression != null)
		{
			String location = JRExpressionUtil.getSimpleExpressionText(sourceExpression);
			if (location == null)
			{
				log.warn("Template source expression " + sourceExpression.getText() + "cannot be evaluated; some styles might remain unresolved.");
			}
			else
			{
				loadTemplateStyles(location, loadedLocations);
			}
		}
	}

	protected void loadTemplateStyles(String location, Set loadedLocations)
	{
		if (!loadedLocations.add(location))
		{
			throw new JRRuntimeException("Circular dependency found for template at location " + location);
		}
		
		JRTemplate template;
		try
		{
			template = JRXmlTemplateLoader.load(location);
		}
		catch (Exception e)
		{
			log.warn("Could not load template from location " + location + "; some styles might remain unresolved.");
			return;
		}
		
		JRTemplateReference[] includedTemplates = template.getIncludedTemplates();
		if (includedTemplates != null)
		{
			for (int i = 0; i < includedTemplates.length; i++)
			{
				JRTemplateReference reference = includedTemplates[i];
				loadTemplateStyles(reference.getLocation(), loadedLocations);
			}
		}
		
		collectStyles(template.getStyles());
	}

	protected void collectStyles(JRStyle[] styles)
	{
		if (styles != null)
		{
			for (int i = 0; i < styles.length; i++)
			{
				JRStyle style = styles[i];
				JRStyle copy = this.styleFactory.getStyle(style);
				this.stylesMap.put(copy.getName(), copy);
			}
		}
	}

	/**
	 *
	 */
	private void addBand(JRBand band)
	{
		if (band != null)
		{
			JRBasePrintFrame frame = new JRBasePrintFrame(null);
			frame.setX(this.report.getLeftMargin());
			frame.setY(this.offsetY);
			frame.setWidth(this.report.getPageWidth() - this.report.getLeftMargin() - this.report.getRightMargin());
			frame.setHeight(band.getHeight());
			
			band.visit(new ConvertVisitor(this, frame));
			
			this.pageElements.add(frame);
			
			this.offsetY += band.getHeight();
			addBandSeparator(this.offsetY);
		}
	}
	
	/**
	 *
	 */
	private void addBandSeparator(int bandY)
	{
		addHorizontalGridLine(0, bandY, this.pageWidth);
	}
	
	/**
	 *
	 */
	private void addColumnSeparator(int colX)
	{
		if (this.downColumns > this.upColumns)
		{
			addVerticalGridLine(colX, this.upColumns, this.downColumns - this.upColumns);
		}
	}
	
	/**
	 *
	 */
	private void addHorizontalGridLine(int x, int y, int width)
	{
		JRPrintFrame printFrame = new JRBasePrintFrame(getDefaultStyleProvider());
		printFrame.setX(x);
		printFrame.setY(y);
		printFrame.setWidth(width);
		printFrame.setHeight(1);
		printFrame.getLineBox().getPen().setLineWidth(0);
		printFrame.getLineBox().getPen().setLineStyle(JRPen.LINE_STYLE_SOLID);
		printFrame.getLineBox().getTopPen().setLineWidth(0.1f);
		printFrame.getLineBox().getTopPen().setLineStyle(JRPen.LINE_STYLE_DASHED);
		printFrame.getLineBox().getTopPen().setLineColor(GRID_LINE_COLOR);
		this.pageElements.add(0, printFrame);
	}
	
	/**
	 *
	 */
	private void addVerticalGridLine(int x, int y, int height)
	{
		JRPrintFrame printFrame = new JRBasePrintFrame(getDefaultStyleProvider());
		printFrame.setX(x);
		printFrame.setY(y);
		printFrame.setWidth(1);
		printFrame.setHeight(height);
		printFrame.getLineBox().getPen().setLineWidth(0);
		printFrame.getLineBox().getPen().setLineStyle(JRPen.LINE_STYLE_SOLID);
		printFrame.getLineBox().getLeftPen().setLineWidth(0.1f);
		printFrame.getLineBox().getLeftPen().setLineStyle(JRPen.LINE_STYLE_DASHED);
		printFrame.getLineBox().getLeftPen().setLineColor(GRID_LINE_COLOR);
		this.pageElements.add(0, printFrame);
	}
	
	/**
	 * 
	 */
	protected JRStyle resolveStyle(JRStyleContainer originalContainer)
	{
		JRStyle originalStyle = originalContainer.getStyle();
		String nameReference = originalContainer.getStyleNameReference();
		JRStyle style;
		if (originalStyle != null)
		{
			style = this.styleFactory.getStyle(originalStyle);
		}
		else if (nameReference != null)
		{
			style = (JRStyle) this.stylesMap.get(nameReference);
			if (style == null)
			{
				log.warn("Style " + nameReference + " could not be resolved.");
			}
		}
		else
		{
			style = null;
		}
		return style;
	}	


	/**
	 * 
	 */	
	public JasperPrint getJasperPrint()
	{
		return this.jasperPrint;
	}
	
	
	/**
	 * 
	 */	
	public JRDefaultStyleProvider getDefaultStyleProvider()
	{
		return this.jasperPrint.getDefaultStyleProvider();
	}

	
	/**
	 * 
	 */	
	protected class StyleFactory extends JRBaseObjectFactory
	{
		public StyleFactory()
		{
			super(ReportConverter.this.getDefaultStyleProvider());
		}

		public JRExpression getExpression(JRExpression expression, boolean assignNotUsedId)
		{
			return expression;
		}

		public JRStyle getStyle(JRStyle style)
		{
			JRBaseStyle baseStyle = null;

			if (style != null)
			{
				baseStyle = (JRBaseStyle)get(style);
				if (
					baseStyle == null
					|| !ReportConverter.this.cacheStyles
					)
				{
					baseStyle = new JRBaseStyle(style, this);
					put(style, baseStyle);
				}
			}

			return baseStyle;
		}

		protected void handleStyleNameReference(JRStyleSetter setter, String nameReference)
		{
			JRStyle style = (JRStyle) ReportConverter.this.stylesMap.get(nameReference);
			if (style == null)
			{
				log.warn("Style " + nameReference + " could not be resolved.");
			}
			else
			{
				setter.setStyle(style);
			}
		}
	}

}
