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

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.JRReportTemplate;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseReport.java,v 1.1 2008/09/29 16:21:16 guehene Exp $
 */
public class JRBaseReport implements JRReport, Serializable, JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String PROPERTY_WHEN_NO_DATA_TYPE = "whenNoDataType";

	/**
	 *
	 */
	protected String name = null;
	protected String language = LANGUAGE_JAVA;
	protected int columnCount = 1;
	protected byte printOrder = PRINT_ORDER_VERTICAL;
	protected int pageWidth = 595;
	protected int pageHeight = 842;
	protected byte orientation = ORIENTATION_PORTRAIT;
	protected byte whenNoDataType = WHEN_NO_DATA_TYPE_NO_PAGES;
	protected int columnWidth = 555;
	protected int columnSpacing = 0;
	protected int leftMargin = 20;
	protected int rightMargin = 20;
	protected int topMargin = 30;
	protected int bottomMargin = 30;
	protected boolean isTitleNewPage = false;
	protected boolean isSummaryNewPage = false;
	protected boolean isFloatColumnFooter = false;
	protected boolean ignorePagination = false;

	/**
	 *
	 */
	protected String formatFactoryClass = null;

	/**
	 *
	 */
	protected Set importsSet = null;

	/**
	 * Report templates.
	 */
	protected JRReportTemplate[] templates;

	protected JRReportFont defaultFont = null;
	protected JRReportFont[] fonts = null;
	protected JRStyle defaultStyle = null;
	protected JRStyle[] styles = null;

	/**
	 * The main dataset of the report.
	 */
	protected JRDataset mainDataset;

	/**
	 * Sub datasets of the report.
	 */
	protected JRDataset[] datasets;

	protected JRBand background = null;
	protected JRBand title = null;
	protected JRBand pageHeader = null;
	protected JRBand columnHeader = null;
	protected JRBand detail = null;
	protected JRBand columnFooter = null;
	protected JRBand pageFooter = null;
	protected JRBand lastPageFooter = null;
	protected JRBand summary = null;
	protected JRBand noData = null;


	/**
	 *
	 */
	public JRBaseReport()
	{
	}

	/**
	 * Constructs a copy of a report.
	 *
	 * @param report the original report
	 * @param expressionCollector expression collector used to provide new expression IDs
	 */
	public JRBaseReport(JRReport report, JRExpressionCollector expressionCollector)
	{
		/*   */
		this.name = report.getName();
		this.language = report.getLanguage();
		this.columnCount = report.getColumnCount();
		this.printOrder = report.getPrintOrder();
		this.pageWidth = report.getPageWidth();
		this.pageHeight = report.getPageHeight();
		this.orientation = report.getOrientation();
		this.whenNoDataType = report.getWhenNoDataType();
		this.columnWidth = report.getColumnWidth();
		this.columnSpacing = report.getColumnSpacing();
		this.leftMargin = report.getLeftMargin();
		this.rightMargin = report.getRightMargin();
		this.topMargin = report.getTopMargin();
		this.bottomMargin = report.getBottomMargin();
		this.isTitleNewPage = report.isTitleNewPage();
		this.isSummaryNewPage = report.isSummaryNewPage();
		this.isFloatColumnFooter = report.isFloatColumnFooter();
		this.ignorePagination = report.isIgnorePagination();

		this.formatFactoryClass = report.getFormatFactoryClass();

		/*   */
		String[] imports = report.getImports();
		if (imports != null && imports.length > 0)
		{
			this.importsSet = new HashSet(imports.length);
			this.importsSet.addAll(Arrays.asList(imports));
		}

		/*   */
		JRBaseObjectFactory factory = new JRBaseObjectFactory(this, expressionCollector);

		copyTemplates(report, factory);

		/*   */
		this.defaultFont = factory.getReportFont(report.getDefaultFont());

		/*   */
		JRReportFont[] jrFonts = report.getFonts();
		if (jrFonts != null && jrFonts.length > 0)
		{
			this.fonts = new JRReportFont[jrFonts.length];
			for(int i = 0; i < this.fonts.length; i++)
			{
				this.fonts[i] = factory.getReportFont(jrFonts[i]);
			}
		}

		/*   */
		this.defaultStyle = factory.getStyle(report.getDefaultStyle());

		/*   */
		JRStyle[] jrStyles = report.getStyles();
		if (jrStyles != null && jrStyles.length > 0)
		{
			this.styles = new JRStyle[jrStyles.length];
			for(int i = 0; i < this.styles.length; i++)
			{
				this.styles[i] = factory.getStyle(jrStyles[i]);
			}
		}

		this.mainDataset = factory.getDataset(report.getMainDataset());

		JRDataset[] datasetArray = report.getDatasets();
		if (datasetArray != null && datasetArray.length > 0)
		{
			this.datasets = new JRDataset[datasetArray.length];
			for (int i = 0; i < this.datasets.length; i++)
			{
				this.datasets[i] = factory.getDataset(datasetArray[i]);
			}
		}

		this.background = factory.getBand(report.getBackground());
		this.title = factory.getBand(report.getTitle());
		this.pageHeader = factory.getBand(report.getPageHeader());
		this.columnHeader = factory.getBand(report.getColumnHeader());
		this.detail = factory.getBand(report.getDetail());
		this.columnFooter = factory.getBand(report.getColumnFooter());
		this.pageFooter = factory.getBand(report.getPageFooter());
		this.lastPageFooter = factory.getBand(report.getLastPageFooter());
		this.summary = factory.getBand(report.getSummary());
		this.noData = factory.getBand(report.getNoData());
	}


	protected void copyTemplates(JRReport report, JRBaseObjectFactory factory)
	{
		JRReportTemplate[] reportTemplates = report.getTemplates();
		if (reportTemplates == null || reportTemplates.length == 0)
		{
			this.templates = null;
		}
		else
		{
			this.templates = new JRReportTemplate[reportTemplates.length];
			for (int i = 0; i < reportTemplates.length; i++)
			{
				this.templates[i] = factory.getReportTemplate(reportTemplates[i]);
			}
		}
	}

	public JRBaseReport(JRReport report)
	{
		this(report, null);
	}


	/**
	 *
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 *
	 */
	public String getLanguage()
	{
		return this.language;
	}

	/**
	 *
	 */
	public int getColumnCount()
	{
		return this.columnCount;
	}

	/**
	 *
	 */
	public byte getPrintOrder()
	{
		return this.printOrder;
	}

	/**
	 *
	 */
	public int getPageWidth()
	{
		return this.pageWidth;
	}

	/**
	 *
	 */
	public int getPageHeight()
	{
		return this.pageHeight;
	}

	/**
	 *
	 */
	public byte getOrientation()
	{
		return this.orientation;
	}

	/**
	 *
	 */
	public byte getWhenNoDataType()
	{
		return this.whenNoDataType;
	}

	/**
	 *
	 */
	public void setWhenNoDataType(byte whenNoDataType)
	{
		byte old = getWhenNoDataType();
		this.whenNoDataType = whenNoDataType;
		getEventSupport().firePropertyChange(PROPERTY_WHEN_NO_DATA_TYPE, (float) old, (float) getWhenNoDataType());
	}

	/**
	 *
	 */
	public int getColumnWidth()
	{
		return this.columnWidth;
	}

	/**
	 *
	 */
	public int getColumnSpacing()
	{
		return this.columnSpacing;
	}

	/**
	 *
	 */
	public int getLeftMargin()
	{
		return this.leftMargin;
	}

	/**
	 *
	 */
	public int getRightMargin()
	{
		return this.rightMargin;
	}

	/**
	 *
	 */
	public int getTopMargin()
	{
		return this.topMargin;
	}

	/**
	 *
	 */
	public int getBottomMargin()
	{
		return this.bottomMargin;
	}

	/**
	 *
	 */
	public boolean isTitleNewPage()
	{
		return this.isTitleNewPage;
	}

	/**
	 *
	 */
	public boolean isSummaryNewPage()
	{
		return this.isSummaryNewPage;
	}

	/**
	 *
	 */
	public boolean isFloatColumnFooter()
	{
		return this.isFloatColumnFooter;
	}

	/**
	 *
	 */
	public String getScriptletClass()
	{
		return this.mainDataset.getScriptletClass();
	}

	/**
	 *
	 */
	public String getFormatFactoryClass()
	{
		return this.formatFactoryClass;
	}

	/**
	 *
	 */
	public String getResourceBundle()
	{
		return this.mainDataset.getResourceBundle();
	}

	/**
	 *
	 */
	public String[] getPropertyNames()
	{
		return this.mainDataset.getPropertiesMap().getPropertyNames();
	}

	/**
	 *
	 */
	public String getProperty(String propName)
	{
		return this.mainDataset.getPropertiesMap().getProperty(propName);
	}

	/**
	 *
	 */
	public void setProperty(String propName, String value)
	{
		this.mainDataset.getPropertiesMap().setProperty(propName, value);
	}

	/**
	 *
	 */
	public void removeProperty(String propName)
	{
		this.mainDataset.getPropertiesMap().removeProperty(propName);
	}

	/**
	 *
	 */
	public String[] getImports()
	{
		if (this.importsSet != null)
		{
			return (String[])this.importsSet.toArray(new String[this.importsSet.size()]);
		}
		return null;
	}

	/**
	 * @deprecated
	 */
	public JRReportFont getDefaultFont()
	{
		return this.defaultFont;
	}

	/**
	 * @deprecated
	 */
	public JRReportFont[] getFonts()
	{
		return this.fonts;
	}

	/**
	 *
	 */
	public JRStyle getDefaultStyle()
	{
		return this.defaultStyle;
	}

	/**
	 *
	 */
	public JRStyle[] getStyles()
	{
		return this.styles;
	}

	/**
	 * Gets an array of report parameters (including built-in ones).
	 */
	public JRParameter[] getParameters()
	{
		return this.mainDataset.getParameters();
	}

	/**
	 *
	 */
	public JRQuery getQuery()
	{
		return this.mainDataset.getQuery();
	}

	/**
	 *  Gets an array of report fields.
	 */
	public JRField[] getFields()
	{
		return this.mainDataset.getFields();
	}

	/**
	 *  Gets an array of sort report fields.
	 */
	public JRSortField[] getSortFields()
	{
		return this.mainDataset.getSortFields();
	}

	/**
	 * Gets an array of report variables.
	 */
	public JRVariable[] getVariables()
	{
		return this.mainDataset.getVariables();
	}

	/**
	 *
	 */
	public JRGroup[] getGroups()
	{
		return this.mainDataset.getGroups();
	}

	/**
	 *
	 */
	public JRBand getBackground()
	{
		return this.background;
	}

	/**
	 *
	 */
	public JRBand getTitle()
	{
		return this.title;
	}

	/**
	 *
	 */
	public JRBand getPageHeader()
	{
		return this.pageHeader;
	}

	/**
	 *
	 */
	public JRBand getColumnHeader()
	{
		return this.columnHeader;
	}

	/**
	 *
	 */
	public JRBand getDetail()
	{
		return this.detail;
	}

	/**
	 *
	 */
	public JRBand getColumnFooter()
	{
		return this.columnFooter;
	}

	/**
	 *
	 */
	public JRBand getPageFooter()
	{
		return this.pageFooter;
	}

	/**
	 *
	 */
	public JRBand getLastPageFooter()
	{
		return this.lastPageFooter;
	}

	/**
	 *
	 */
	public JRBand getSummary()
	{
		return this.summary;
	}


	/**
	 *
	 */
	public byte getWhenResourceMissingType()
	{
		return this.mainDataset.getWhenResourceMissingType();
	}

	/**
	 *
	 */
	public void setWhenResourceMissingType(byte whenResourceMissingType)
	{
		this.mainDataset.setWhenResourceMissingType(whenResourceMissingType);
	}


	public JRDataset getMainDataset()
	{
		return this.mainDataset;
	}


	public JRDataset[] getDatasets()
	{
		return this.datasets;
	}


	public boolean isIgnorePagination()
	{
		return this.ignorePagination;
	}

	public boolean hasProperties()
	{
		return this.mainDataset.hasProperties();
	}

	public JRPropertiesMap getPropertiesMap()
	{
		return this.mainDataset.getPropertiesMap();
	}

	public JRPropertiesHolder getParentProperties()
	{
		return null;
	}

	public JRReportTemplate[] getTemplates()
	{
		return this.templates;
	}

	/**
	 * @return the noData
	 */
	public JRBand getNoData() {
		return this.noData;
	}
	
	private transient JRPropertyChangeSupport eventSupport;
	
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (this.eventSupport == null)
			{
				this.eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return this.eventSupport;
	}
}
