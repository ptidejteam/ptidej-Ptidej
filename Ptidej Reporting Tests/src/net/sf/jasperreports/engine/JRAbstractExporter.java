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
package net.sf.jasperreports.engine;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import net.sf.jasperreports.engine.export.ExporterFilter;
import net.sf.jasperreports.engine.export.ExporterFilterFactory;
import net.sf.jasperreports.engine.export.ExporterFilterFactoryUtil;
import net.sf.jasperreports.engine.export.JRExporterContext;
import net.sf.jasperreports.engine.export.data.BooleanTextValue;
import net.sf.jasperreports.engine.export.data.DateTextValue;
import net.sf.jasperreports.engine.export.data.NumberTextValue;
import net.sf.jasperreports.engine.export.data.StringTextValue;
import net.sf.jasperreports.engine.export.data.TextValue;
import net.sf.jasperreports.engine.util.DefaultFormatFactory;
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.util.FormatFactory;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRDataUtils;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRResourcesUtil;
import net.sf.jasperreports.engine.util.JRStyledText;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRAbstractExporter.java,v 1.1 2008/09/29 16:20:42 guehene Exp $
 */
public abstract class JRAbstractExporter implements JRExporter
{
	/**
	 * Property that stores the formula which has to be applied to a given cell in an excel sheet.
	 */
	public static final String PROPERTY_CELL_FORMULA = JRProperties.PROPERTY_PREFIX + "export.xls.formula";

	/**
	 * A property that gives the generic default filter factory class name.
	 * 
	 * @see #PROPERTY_SUFFIX_DEFAULT_FILTER_FACTORY
	 */
	public static final String PROPERTY_DEFAULT_FILTER_FACTORY = 
		JRProperties.PROPERTY_PREFIX + "export.default.filter.factory";

	/**
	 * The suffix applied to properties that give the default filter factory for
	 * a specific exporter.
	 * 
	 * For instance, the default filter factory for XLS exporters is
	 * <code>net.sf.jasperreports.export.xls.default.filter.factory</code>.
	 * 
	 * If this property is not defined for a specific exporter, the generic
	 * exporter factory given by {@link #PROPERTY_DEFAULT_FILTER_FACTORY} is used.
	 */
	public static final String PROPERTY_SUFFIX_DEFAULT_FILTER_FACTORY = "default.filter.factory";
	
	protected static interface ParameterResolver
	{
		String getStringParameter(JRExporterParameter parameter, String property);
		
		String getStringParameterOrDefault(JRExporterParameter parameter, String property);
		
		boolean getBooleanParameter(JRExporterParameter parameter, String property, boolean defaultValue);
		
		int getIntegerParameter(JRExporterParameter parameter, String property, int defaultValue);
	}
	
	protected class ParameterOverrideResolver implements ParameterResolver
	{
		
		public String getStringParameter(JRExporterParameter parameter, String property)
		{
			if (JRAbstractExporter.this.parameters.containsKey(parameter))
			{
				return (String)JRAbstractExporter.this.parameters.get(parameter);
			}
			else
			{
				return 
					JRProperties.getProperty(
						JRAbstractExporter.this.jasperPrint.getPropertiesMap(),
						property
						);
			}
		}

		public String getStringParameterOrDefault(JRExporterParameter parameter, String property)
		{
			if (JRAbstractExporter.this.parameters.containsKey(parameter))
			{
				String value = (String)JRAbstractExporter.this.parameters.get(parameter);
				if (value == null)
				{
					return JRProperties.getProperty(property);
				}
				else
				{
					return value;
				}
			}
			else
			{
				return
					JRProperties.getProperty(
						JRAbstractExporter.this.jasperPrint.getPropertiesMap(),
						property
						);
			}
		}

		public boolean getBooleanParameter(JRExporterParameter parameter, String property, boolean defaultValue)
		{
			if (JRAbstractExporter.this.parameters.containsKey(parameter))
			{
				Boolean booleanValue = (Boolean)JRAbstractExporter.this.parameters.get(parameter);
				if (booleanValue == null)
				{
					return JRProperties.getBooleanProperty(property);
				}
				else
				{
					return booleanValue.booleanValue();
				}
			}
			else
			{
				return 
					JRProperties.getBooleanProperty(
						JRAbstractExporter.this.jasperPrint.getPropertiesMap(),
						property,
						defaultValue
						);
			}
		}

		public int getIntegerParameter(JRExporterParameter parameter, String property, int defaultValue)
		{
			if (JRAbstractExporter.this.parameters.containsKey(parameter))
			{
				Integer integerValue = (Integer)JRAbstractExporter.this.parameters.get(parameter);
				if (integerValue == null)
				{
					return JRProperties.getIntegerProperty(property);
				}
				else
				{
					return integerValue.intValue();
				}
			}
			else
			{
				return 
					JRProperties.getIntegerProperty(
						JRAbstractExporter.this.jasperPrint.getPropertiesMap(),
						property,
						defaultValue
						);
			}
		}
	}
	
	protected class ParameterOverriddenResolver implements ParameterResolver
	{
		
		public String getStringParameter(JRExporterParameter parameter, String property)
		{
			String value;
			JRPropertiesMap hintsMap = JRAbstractExporter.this.jasperPrint.getPropertiesMap();
			if (hintsMap != null && hintsMap.containsProperty(property))
			{
				value = hintsMap.getProperty(property);
			}
			else
			{
				value = (String) JRAbstractExporter.this.parameters.get(parameter);
				
				if (value == null)
				{
					value = JRProperties.getProperty(property);
				}
			}
			return value;
		}

		public String getStringParameterOrDefault(JRExporterParameter parameter, String property)
		{
			String value;
			JRPropertiesMap hintsMap = JRAbstractExporter.this.jasperPrint.getPropertiesMap();
			if (hintsMap != null && hintsMap.containsProperty(property))
			{
				value = hintsMap.getProperty(property);
			}
			else
			{
				value = (String) JRAbstractExporter.this.parameters.get(parameter);
			}
			
			if (value == null)
			{
				value = JRProperties.getProperty(property);
			}
			
			return value;
		}

		public boolean getBooleanParameter(JRExporterParameter parameter, String property, boolean defaultValue)
		{
			boolean value;
			JRPropertiesMap hintsMap = JRAbstractExporter.this.jasperPrint.getPropertiesMap();
			if (hintsMap != null && hintsMap.containsProperty(property))
			{
				String prop = hintsMap.getProperty(property);
				if (prop == null)
				{
					value = JRProperties.getBooleanProperty(property);
				}
				else
				{
					value = JRProperties.asBoolean(prop);
				}
			}
			else
			{
				Boolean param = (Boolean) JRAbstractExporter.this.parameters.get(parameter);
				if (param == null)
				{
					value = JRProperties.getBooleanProperty(property);
				}
				else
				{
					value = param.booleanValue();
				}
			}
			return value;
		}

		public int getIntegerParameter(JRExporterParameter parameter, String property, int defaultValue)
		{
			int value;
			JRPropertiesMap hintsMap = JRAbstractExporter.this.jasperPrint.getPropertiesMap();
			if (hintsMap != null && hintsMap.containsProperty(property))
			{
				String prop = hintsMap.getProperty(property);
				if (prop == null)
				{
					value = JRProperties.getIntegerProperty(property);
				}
				else
				{
					value = JRProperties.asInteger(prop);
				}
			}
			else
			{
				Integer param = (Integer) JRAbstractExporter.this.parameters.get(parameter);
				if (param == null)
				{
					value = JRProperties.getIntegerProperty(property);
				}
				else
				{
					value = param.intValue();
				}
			}
			return value;
		}

	}
	
	// this would make the applet require logging library
	//private final static Log log = LogFactory.getLog(JRAbstractExporter.class);

	private ParameterResolver parameterResolver;
	
	/**
	 *
	 */
	protected Map parameters = new HashMap();

	/**
	 *
	 */
	protected List jasperPrintList = null;
	protected JasperPrint jasperPrint = null;
	protected boolean isModeBatch = true;
	protected int startPageIndex = 0;
	protected int endPageIndex = 0;
	protected int globalOffsetX = 0;
	protected int globalOffsetY = 0;
	protected ClassLoader classLoader = null;
	protected boolean classLoaderSet = false;
	protected URLStreamHandlerFactory urlHandlerFactory = null;
	protected boolean urlHandlerFactorySet = false;
	protected FileResolver fileResolver = null;
	protected boolean fileResolverSet = false;
	protected ExporterFilter filter = null;

	/**
	 *
	 */
	private LinkedList elementOffsetStack = new LinkedList();
	private int elementOffsetX = this.globalOffsetX;
	private int elementOffsetY = this.globalOffsetY;

	/**
	 *
	 */
	protected Map dateFormatCache = new HashMap();
	protected Map numberFormatCache = new HashMap();

	
	/**
	 *
	 */
	protected JRAbstractExporter()
	{
	}
	
	
	/**
	 *
	 */
	public void reset()
	{
		this.parameters = new HashMap();
		this.elementOffsetStack = new LinkedList();
	}
	
	
	/**
	 *
	 */
	public void setParameter(JRExporterParameter parameter, Object value)
	{
		this.parameters.put(parameter, value);
	}


	/**
	 *
	 */
	public Object getParameter(JRExporterParameter parameter)
	{
		return this.parameters.get(parameter);
	}


	/**
	 *
	 */
	public void setParameters(Map parameters)
	{
		this.parameters = parameters;
	}
	

	/**
	 *
	 */
	public Map getParameters()
	{
		return this.parameters;
	}
	
	protected ParameterResolver getParameterResolver()
	{
		if (this.parameterResolver == null)
		{
			boolean parametersOverrideHints;
			Boolean param = (Boolean) this.parameters.get(JRExporterParameter.PARAMETERS_OVERRIDE_REPORT_HINTS);
			if (param == null)
			{
				parametersOverrideHints = JRProperties.getBooleanProperty(JRExporterParameter.PROPERTY_EXPORT_PARAMETERS_OVERRIDE_REPORT_HINTS);
			}
			else
			{
				parametersOverrideHints = param.booleanValue();
			}
			
			if (parametersOverrideHints)
			{
				this.parameterResolver = new ParameterOverrideResolver();
			}
			else
			{
				this.parameterResolver = new ParameterOverriddenResolver();
			}
		}
		
		return this.parameterResolver;
	}

	/**
	 *
	 */
	protected String getStringParameter(JRExporterParameter parameter, String property)
	{
		return getParameterResolver().getStringParameter(parameter, property);
	}

	
	/**
	 *
	 */
	protected String getStringParameterOrDefault(JRExporterParameter parameter, String property)
	{
		return getParameterResolver().getStringParameterOrDefault(parameter, property);
	}

	
	/**
	 *
	 */
	protected boolean getBooleanParameter(JRExporterParameter parameter, String property, boolean defaultValue)
	{
		return getParameterResolver().getBooleanParameter(parameter, property, defaultValue);
	}

	
	/**
	 *
	 */
	protected int getIntegerParameter(JRExporterParameter parameter, String property, int defaultValue)
	{
		return getParameterResolver().getIntegerParameter(parameter, property, defaultValue);
	}

	
	/**
	 *
	 */
	public abstract void exportReport() throws JRException;


	protected void setOffset()
	{
		setOffset(true);
	}
	
	/**
	 *
	 */
	protected void setOffset(boolean setElementOffsets)
	{
		Integer offsetX = (Integer)this.parameters.get(JRExporterParameter.OFFSET_X);
		if (offsetX != null)
		{
			this.globalOffsetX = offsetX.intValue();
		}
		else
		{
			this.globalOffsetX = 0;
		}

		Integer offsetY = (Integer)this.parameters.get(JRExporterParameter.OFFSET_Y);
		if (offsetY != null)
		{
			this.globalOffsetY = offsetY.intValue();
		}
		else
		{
			this.globalOffsetY = 0;
		}
		
		if (setElementOffsets)
		{
			this.elementOffsetX = this.globalOffsetX;
			this.elementOffsetY = this.globalOffsetY;
		}
	}
	

	/**
	 *
	 */
	protected void setExportContext()
	{
		this.classLoaderSet = false;
		this.urlHandlerFactorySet = false;
		this.fileResolverSet = false;
		
		this.classLoader = (ClassLoader)this.parameters.get(JRExporterParameter.CLASS_LOADER);
		if (this.classLoader != null)
		{
			JRResourcesUtil.setThreadClassLoader(this.classLoader);
			this.classLoaderSet = true;
		}

		this.urlHandlerFactory = (URLStreamHandlerFactory) this.parameters.get(JRExporterParameter.URL_HANDLER_FACTORY);
		if (this.urlHandlerFactory != null)
		{
			JRResourcesUtil.setThreadURLHandlerFactory(this.urlHandlerFactory);
			this.urlHandlerFactorySet = true;
		}

		this.fileResolver = (FileResolver) this.parameters.get(JRExporterParameter.FILE_RESOLVER);
		if (this.fileResolver != null)
		{
			JRResourcesUtil.setThreadFileResolver(this.fileResolver);
			this.fileResolverSet = true;
		}
	}
		

	/**
	 *
	 */
	protected void resetExportContext()
	{
		if (this.classLoaderSet)
		{
			JRResourcesUtil.resetClassLoader();
		}
		
		if (this.urlHandlerFactorySet)
		{
			JRResourcesUtil.resetThreadURLHandlerFactory();
		}
		
		if (this.fileResolverSet)
		{
			JRResourcesUtil.resetThreadFileResolver();
		}
	}

	
	/**
	 * @deprecated replaced by {@link #setExportContext() setExportContext} 
	 */
	protected void setClassLoader()
	{
		setExportContext();
	}

	
	/**
	 * @deprecated replaced by {@link #resetExportContext() resetExportContext} 
	 */
	protected void resetClassLoader()
	{
		resetExportContext();
	}


	/**
	 *
	 */
	protected void setInput() throws JRException
	{
		this.jasperPrintList = (List)this.parameters.get(JRExporterParameter.JASPER_PRINT_LIST);
		if (this.jasperPrintList == null)
		{
			this.isModeBatch = false;
			
			this.jasperPrint = (JasperPrint)this.parameters.get(JRExporterParameter.JASPER_PRINT);
			if (this.jasperPrint == null)
			{
				InputStream is = (InputStream)this.parameters.get(JRExporterParameter.INPUT_STREAM);
				if (is != null)
				{
					this.jasperPrint = (JasperPrint)JRLoader.loadObject(is);
				}
				else
				{
					URL url = (URL)this.parameters.get(JRExporterParameter.INPUT_URL);
					if (url != null)
					{
						this.jasperPrint = (JasperPrint)JRLoader.loadObject(url);
					}
					else
					{
						File file = (File)this.parameters.get(JRExporterParameter.INPUT_FILE);
						if (file != null)
						{
							this.jasperPrint = (JasperPrint)JRLoader.loadObject(file);
						}
						else
						{
							String fileName = (String)this.parameters.get(JRExporterParameter.INPUT_FILE_NAME);
							if (fileName != null)
							{
								this.jasperPrint = (JasperPrint)JRLoader.loadObject(fileName);
							}
							else
							{
								throw new JRException("No input source supplied to the exporter.");
							}
						}
					}
				}
			}

			this.jasperPrintList = new ArrayList();
			this.jasperPrintList.add(this.jasperPrint);
		}
		else
		{
			this.isModeBatch = true;

			if (this.jasperPrintList.size() == 0)
			{
				throw new JRException("Empty input source supplied to the exporter in batch mode.");
			}

			this.jasperPrint = (JasperPrint)this.jasperPrintList.get(0);
		}

		this.filter = (ExporterFilter)this.parameters.get(JRExporterParameter.FILTER);
	}
	

	/**
	 *
	 */
	protected void setPageRange() throws JRException
	{
		int lastPageIndex = -1;
		if (this.jasperPrint.getPages() != null)
		{
			lastPageIndex = this.jasperPrint.getPages().size() - 1;
		}

		Integer start = (Integer)this.parameters.get(JRExporterParameter.START_PAGE_INDEX);
		if (start == null)
		{
			this.startPageIndex = 0;
		}
		else
		{
			this.startPageIndex = start.intValue();
			if (this.startPageIndex < 0 || this.startPageIndex > lastPageIndex)
			{
				throw new JRException("Start page index out of range : " + this.startPageIndex + " of " + lastPageIndex);
			}
		}

		Integer end = (Integer)this.parameters.get(JRExporterParameter.END_PAGE_INDEX);
		if (end == null)
		{
			this.endPageIndex = lastPageIndex;
		}
		else
		{
			this.endPageIndex = end.intValue();
			if (this.endPageIndex < this.startPageIndex || this.endPageIndex > lastPageIndex)
			{
				throw new JRException("End page index out of range : " + this.endPageIndex + " (" + this.startPageIndex + " : " + lastPageIndex + ")");
			}
		}

		Integer index = (Integer)this.parameters.get(JRExporterParameter.PAGE_INDEX);
		if (index != null)
		{
			int pageIndex = index.intValue();
			if (pageIndex < 0 || pageIndex > lastPageIndex)
			{
				throw new JRException("Page index out of range : " + pageIndex + " of " + lastPageIndex);
			}
			this.startPageIndex = pageIndex;
			this.endPageIndex = pageIndex;
		}
	}
	

	/**
	 *
	 */
	protected JRStyledText getStyledText(JRPrintText textElement, boolean setBackcolor)
	{
		return textElement.getStyledText(
				setBackcolor ? JRStyledTextAttributeSelector.ALL : JRStyledTextAttributeSelector.NO_BACKCOLOR);
	}

	
	protected JRStyledText getStyledText(JRPrintText textElement)
	{
		return getStyledText(textElement, true);
	}

	
	/**
	 *
	 */
	protected void setOutput()
	{
	}


	/**
	 * Returns the X axis offset used for element export.
	 * <p>
	 * This method should be used istead of {@link #globalOffsetX globalOffsetX} when
	 * exporting elements.
	 * 
	 * @return the X axis offset
	 */
	protected int getOffsetX()
	{
		return this.elementOffsetX;
	}


	/**
	 * Returns the Y axis offset used for element export.
	 * <p>
	 * This method should be used istead of {@link #globalOffsetY globalOffsetY} when
	 * exporting elements.
	 * 
	 * @return the Y axis offset
	 */
	protected int getOffsetY()
	{
		return this.elementOffsetY;
	}

	
	/**
	 * Sets the offsets for exporting elements from a {@link JRPrintFrame frame}.
	 * <p>
	 * After the frame elements are exported, a call to {@link #restoreElementOffsets() popElementOffsets} is required
	 * so that the previous offsets are resored.
	 * 
	 * @param frame
	 * @param relative
	 * @see #getOffsetX()
	 * @see #getOffsetY()
	 * @see #restoreElementOffsets()
	 */
	protected void setFrameElementsOffset(JRPrintFrame frame, boolean relative)
	{	
		if (relative)
		{
			setElementOffsets(0, 0);
		}
		else
		{
			int topPadding = frame.getLineBox().getTopPadding().intValue();
			int leftPadding = frame.getLineBox().getLeftPadding().intValue();

			setElementOffsets(getOffsetX() + frame.getX() + leftPadding, getOffsetY() + frame.getY() + topPadding);
		}
	}
	
	
	private void setElementOffsets(int offsetX, int offsetY)
	{
		this.elementOffsetStack.addLast(new int[]{this.elementOffsetX, this.elementOffsetY});
		
		this.elementOffsetX = offsetX;
		this.elementOffsetY = offsetY;
	}

	
	/**
	 * Restores offsets after a call to 
	 * {@link #setFrameElementsOffset(JRPrintFrame, boolean) setFrameElementsOffset}.
	 */
	protected void restoreElementOffsets()
	{
		int[] offsets = (int[]) this.elementOffsetStack.removeLast();
		this.elementOffsetX = offsets[0];
		this.elementOffsetY = offsets[1];
	}

	
	protected String getTextFormatFactoryClass(JRPrintText text)
	{
		String formatFactoryClass = text.getFormatFactoryClass();
		if (formatFactoryClass == null)
		{
			formatFactoryClass = this.jasperPrint.getFormatFactoryClass();
		}
		return formatFactoryClass;
	}

	protected Locale getTextLocale(JRPrintText text)
	{
		String localeCode = text.getLocaleCode();
		if (localeCode == null)
		{
			localeCode = this.jasperPrint.getLocaleCode();
		}
		return localeCode == null ? null : JRDataUtils.getLocale(localeCode);
	}

	protected TimeZone getTextTimeZone(JRPrintText text)
	{
		String tzId = text.getTimeZoneId();
		if (tzId == null)
		{
			tzId = this.jasperPrint.getTimeZoneId();
		}
		return tzId == null ? null : JRDataUtils.getTimeZone(tzId);
	}
	
	protected TextValue getTextValue(JRPrintText text, String textStr)
	{
		TextValue textValue;
		if (text.getValueClassName() == null)
		{
			textValue = getTextValueString(text, textStr);
		}
		else
		{
			try
			{
				Class valueClass = JRClassLoader.loadClassForRealName(text.getValueClassName());
				if (java.lang.Number.class.isAssignableFrom(valueClass))
				{
					textValue = getNumberCellValue(text, textStr);
				}
				else if (Date.class.isAssignableFrom(valueClass))
				{
					textValue = getDateCellValue(text, textStr);
				}
				else if (Boolean.class.equals(valueClass))
				{
					textValue = getBooleanCellValue(text, textStr);
				}
				else
				{
					textValue = getTextValueString(text, textStr);
				} 
			}
			catch (ParseException e)
			{
				//log.warn("Error parsing text value", e);
				textValue = getTextValueString(text, textStr);
			}
			catch (ClassNotFoundException e)
			{
				//log.warn("Error loading text value class", e);
				textValue = getTextValueString(text, textStr);
			}			
		}
		return textValue;
	}

	protected TextValue getTextValueString(JRPrintText text, String textStr)
	{
		return new StringTextValue(textStr);
	}

	protected TextValue getBooleanCellValue(JRPrintText text, String textStr)
	{
		Boolean value = null;
		if (textStr != null && textStr.length() > 0)
		{
			value = Boolean.valueOf(textStr);
		}
		return new BooleanTextValue(textStr, value);
	}

	protected TextValue getDateCellValue(JRPrintText text, String textStr) throws ParseException
	{
		TextValue textValue;
		String pattern = text.getPattern();
		if (pattern == null || pattern.trim().length() == 0)
		{
			textValue = getTextValueString(text, textStr);
		}
		else
		{
			DateFormat dateFormat = getDateFormat(getTextFormatFactoryClass(text), pattern, getTextLocale(text), getTextTimeZone(text));
			
			Date value = null;
			if (textStr != null && textStr.length() > 0)
			{
				value = dateFormat.parse(textStr);
			}
			textValue = new DateTextValue(textStr, value, pattern);
		}
		return textValue;
	}

	protected TextValue getNumberCellValue(JRPrintText text, String textStr) throws ParseException, ClassNotFoundException
	{
		TextValue textValue;
		String pattern = text.getPattern();
		if (pattern == null || pattern.trim().length() == 0)
		{
			if (textStr != null && textStr.length() > 0)
			{
				Number value = defaultParseNumber(textStr, JRClassLoader.loadClassForRealName(text.getValueClassName()));

				if (value != null)
				{
					textValue = new NumberTextValue(textStr, value, null);
				}
				else
				{
					textValue = getTextValueString(text, textStr);
				}
			}
			else
			{
				textValue = new NumberTextValue(textStr, null, null);
			}
		}
		else
		{
			NumberFormat numberFormat = getNumberFormat(getTextFormatFactoryClass(text), pattern, getTextLocale(text));
			
			Number value = null;
			if (textStr != null && textStr.length() > 0)
			{
				value = numberFormat.parse(textStr);
			}
			textValue = new NumberTextValue(textStr, value, pattern);
		}
		return textValue;
	}


	protected Number defaultParseNumber(String textStr, Class valueClass)
	{
		Number value = null;
		try
		{
			if (valueClass.equals(Byte.class))
			{
				value = Byte.valueOf(textStr);
			}
			else if (valueClass.equals(Short.class))
			{
				value = Short.valueOf(textStr);
			}
			else if (valueClass.equals(Integer.class))
			{
				value = Integer.valueOf(textStr);
			}
			else if (valueClass.equals(Long.class))
			{
				value = Long.valueOf(textStr);
			}
			else if (valueClass.equals(Float.class))
			{
				value = Float.valueOf(textStr);
			}
			else if (valueClass.equals(Double.class))
			{
				value = Double.valueOf(textStr);
			}
			else if (valueClass.equals(BigInteger.class))
			{
				value = new BigInteger(textStr);
			}
			else if (valueClass.equals(BigDecimal.class))
			{
				value = new BigDecimal(textStr);
			}
		}
		catch (NumberFormatException e)
		{
			//skip
		}
		return value;
	}
	

	protected DateFormat getDateFormat(String formatFactoryClass, String pattern, Locale lc, TimeZone tz)
	{
		String key = formatFactoryClass 
			+ "|" + pattern 
			+ "|" + (lc == null ? "" : JRDataUtils.getLocaleCode(lc)) 
			+ "|" + (tz == null ? "" : JRDataUtils.getTimeZoneId(tz));
		DateFormat dateFormat = (DateFormat)this.dateFormatCache.get(key);
		if (dateFormat == null)
		{
			FormatFactory formatFactory = DefaultFormatFactory.createFormatFactory(formatFactoryClass);//FIXMEFORMAT cache this too
			dateFormat = formatFactory.createDateFormat(pattern, lc, tz);
			this.dateFormatCache.put(key, dateFormat);
		}
		return dateFormat;
	}
	

	protected NumberFormat getNumberFormat(String formatFactoryClass, String pattern, Locale lc)
	{
		String key = formatFactoryClass 
			+ "|" + pattern 
			+ "|" + (lc == null ? "" : JRDataUtils.getLocaleCode(lc)); 
		NumberFormat numberFormat = (NumberFormat)this.numberFormatCache.get(key);
		if (numberFormat == null)
		{
			FormatFactory formatFactory = DefaultFormatFactory.createFormatFactory(formatFactoryClass);//FIXMEFORMAT cache this too
			numberFormat = formatFactory.createNumberFormat(pattern, lc);
			this.numberFormatCache.put(key, numberFormat);
		}
		return numberFormat;
	}

	
	protected ExporterFilter createFilter(final String exportPropertyPrefix) 
			throws JRException
	{
		String exportDefaultFactoryProperty = exportPropertyPrefix 
				+ PROPERTY_SUFFIX_DEFAULT_FILTER_FACTORY;
		
		//the default filter class is determined from 4 possible sources
		String defaultFilterClassName = null;
		
		if (this.jasperPrint.hasProperties())
		{
			//try first the exporter specific property from the report
			defaultFilterClassName = this.jasperPrint.getPropertiesMap().getProperty(
					exportDefaultFactoryProperty);
			
			//then the generic property from the report
			if (defaultFilterClassName == null)
			{
				defaultFilterClassName = this.jasperPrint.getPropertiesMap().getProperty(
						PROPERTY_DEFAULT_FILTER_FACTORY);
			}
		}
		
		//then the global exporter specific property
		if (defaultFilterClassName == null)
		{
			defaultFilterClassName = JRProperties.getProperty(exportDefaultFactoryProperty);
		}
		
		//and finally the global generic property
		if (defaultFilterClassName == null)
		{
			defaultFilterClassName = JRProperties.getProperty(PROPERTY_DEFAULT_FILTER_FACTORY);
		}
		
		ExporterFilterFactory defaultFactory = ExporterFilterFactoryUtil.getFilterFactory(defaultFilterClassName);
		
		JRExporterContext context = new JRExporterContext()
		{
			public String getExportPropertiesPrefix()
			{
				return exportPropertyPrefix;
			}

			public JasperPrint getExportedReport()
			{
				return JRAbstractExporter.this.jasperPrint;
			}
		};
		return defaultFactory.getFilter(context);
	}
}
