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
 * Joakim Sandstr�m - sanjoa@users.sourceforge.net
 */
package net.sf.jasperreports.engine.export;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintEllipse;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintGraphicElement;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintHyperlinkParameter;
import net.sf.jasperreports.engine.JRPrintHyperlinkParameters;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintRectangle;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRWrappingSvgRenderer;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRValueStringUtils;
import net.sf.jasperreports.engine.util.JRXmlWriteHelper;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

import org.w3c.tools.codec.Base64Encoder;


/**
 * Exports a JasperReports document to an XML file that contains the same data as a {@link net.sf.jasperreports.engine.JasperPrint}
 * object, but in XML format, instead of a serialized class. Such XML files can be parsed back into <tt>JasperPrint</tt>
 * object using the {@link net.sf.jasperreports.engine.xml.JRPrintXmlLoader} utility class. Their structure is validated
 * against an internal DTD file called jasperprint.dtd
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRXmlExporter.java,v 1.1 2008/09/29 16:20:46 guehene Exp $
 */
public class JRXmlExporter extends JRAbstractExporter
{

	/**
	 *
	 */
	private static final String XML_EXPORTER_PROPERTIES_PREFIX = JRProperties.PROPERTY_PREFIX + "export.xml.";
	private static final String PROPERTY_START_PAGE_INDEX = JRProperties.PROPERTY_PREFIX + "export.xml.start.page.index";
	private static final String PROPERTY_END_PAGE_INDEX = JRProperties.PROPERTY_PREFIX + "export.xml.end.page.index";
	private static final String PROPERTY_PAGE_COUNT = JRProperties.PROPERTY_PREFIX + "export.xml.page.count";
	protected static final String DEFAULT_XML_ENCODING = "UTF-8";
	protected static final String DEFAULT_OBJECT_TYPE = "java.lang.String";
	protected static final String HTML_FILES_SUFFIX = "_files";
	protected static final String IMAGE_PREFIX = "img_";

	/**
	 *
	 */
	protected JRXmlWriteHelper xmlWriter = null;
	protected String encoding = null;
	
	protected JRExportProgressMonitor progressMonitor = null;
	protected Map rendererToImagePathMap = null;
	protected Map imageNameToImageDataMap = null;
	protected Map fontsMap = new HashMap();
	protected Map stylesMap = new HashMap();

	/**
	 *
	 */
	protected String dtdLocation = null;
	protected boolean isEmbeddingImages = true;
	protected File destFile = null;
	protected File imagesDir = null;

	protected JRHyperlinkProducerFactory hyperlinkProducerFactory;
	
	/**
	 * 
	 */
	private static int imageId = 0;


	/**
	 *
	 */
	public void exportReport() throws JRException
	{
		this.progressMonitor = (JRExportProgressMonitor)this.parameters.get(JRExporterParameter.PROGRESS_MONITOR);
		
		/*   */
		setOffset();

		try
		{
			/*   */
			setExportContext();
	
			/*   */
			setInput();
	
			if (!this.parameters.containsKey(JRExporterParameter.FILTER))
			{
				this.filter = createFilter(XML_EXPORTER_PROPERTIES_PREFIX);
			}

			/*   */
			setPageRange();
	
			this.dtdLocation = (String)this.parameters.get(JRXmlExporterParameter.DTD_LOCATION);
			if (this.dtdLocation == null)
			{
				this.dtdLocation = JRXmlConstants.JASPERPRINT_SYSTEM_ID;
			}
			
			this.encoding = (String)this.parameters.get(JRExporterParameter.CHARACTER_ENCODING);
			if (this.encoding == null)
			{
				this.encoding = DEFAULT_XML_ENCODING;
			}
			
			setHyperlinkProducerFactory();

			StringBuffer sb = (StringBuffer)this.parameters.get(JRExporterParameter.OUTPUT_STRING_BUFFER);
			if (sb != null)
			{
				StringBuffer buffer = exportReportToBuffer();
				sb.append(buffer.toString());
			}
			else
			{
				Writer outWriter = (Writer)this.parameters.get(JRExporterParameter.OUTPUT_WRITER);
				if (outWriter != null)
				{
					try
					{
						exportReportToStream(outWriter);
					}
					catch (IOException e)
					{
						throw new JRException("Error writing to writer : " + this.jasperPrint.getName(), e);
					}
				}
				else
				{
					OutputStream os = (OutputStream)this.parameters.get(JRExporterParameter.OUTPUT_STREAM);
					if (os != null)
					{
						try
						{
							exportReportToStream(new OutputStreamWriter(os, this.encoding));
						}
						catch (Exception e)
						{
							throw new JRException("Error writing to OutputStream : " + this.jasperPrint.getName(), e);
						}
					}
					else
					{
						this.destFile = (File)this.parameters.get(JRExporterParameter.OUTPUT_FILE);
						if (this.destFile == null)
						{
							String fileName = (String)this.parameters.get(JRExporterParameter.OUTPUT_FILE_NAME);
							if (fileName != null)
							{
								this.destFile = new File(fileName);
							}
							else
							{
								throw new JRException("No output specified for the exporter.");
							}
						}
						
						this.imagesDir = new File(this.destFile.getParent(), this.destFile.getName() + HTML_FILES_SUFFIX);
						
						Boolean isEmbeddingImagesParameter = (Boolean)this.parameters.get(JRXmlExporterParameter.IS_EMBEDDING_IMAGES);
						if (isEmbeddingImagesParameter == null)
						{
							isEmbeddingImagesParameter = Boolean.TRUE;
						}
						this.isEmbeddingImages = isEmbeddingImagesParameter.booleanValue();
						
						exportReportToFile();
					}
				}
			}
		}
		finally
		{
			resetExportContext();
		}
	}


	/**
	 *
	 */
	protected void setHyperlinkProducerFactory()
	{
		this.hyperlinkProducerFactory = (JRHyperlinkProducerFactory) this.parameters.get(JRExporterParameter.HYPERLINK_PRODUCER_FACTORY);
	}
	
	
	/**
	 *
	 */
	protected void exportReportToFile() throws JRException
	{
		//if (!isEmbeddingImages)
		{
			this.rendererToImagePathMap = new HashMap();
			this.imageNameToImageDataMap = new HashMap();
		}
				
		Writer writer = null;
		try
		{
			OutputStream fileOutputStream = new FileOutputStream(this.destFile);
			writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, this.encoding));
			exportReportToStream(writer);
		}
		catch (IOException e)
		{
			throw new JRException("Error writing to file : " + this.destFile, e);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch(IOException e)
				{
				}
			}
		}
		
		if (!this.isEmbeddingImages)
		{
			Collection imageNames = this.imageNameToImageDataMap.keySet();
			if (imageNames != null && imageNames.size() > 0)
			{
				if (!this.imagesDir.exists())
				{
					this.imagesDir.mkdir();
				}
	
				for(Iterator it = imageNames.iterator(); it.hasNext();)
				{
					String imageName = (String)it.next();
					byte[] imageData = (byte[])this.imageNameToImageDataMap.get(imageName);

					File imageFile = new File(this.imagesDir, imageName);

					OutputStream fos = null;
					try
					{
						fos = new FileOutputStream(imageFile);
						fos.write(imageData, 0, imageData.length);
					}
					catch (IOException e)
					{
						throw new JRException("Error writing to image file : " + imageFile, e);
					}
					finally
					{
						if (fos != null)
						{
							try
							{
								fos.close();
							}
							catch(IOException e)
							{
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 *
	 */
	protected StringBuffer exportReportToBuffer() throws JRException
	{
		StringWriter buffer = new StringWriter();
		try
		{
			exportReportToStream(buffer);
		}
		catch (IOException e)
		{
			throw new JRException("Error while exporting report to buffer", e);
		}
		return buffer.getBuffer();
	}


	protected void exportReportToStream(Writer writer) throws JRException, IOException
	{
		this.xmlWriter = new JRXmlWriteHelper(writer);
		
		this.xmlWriter.writeProlog(this.encoding);
		this.xmlWriter.writePublicDoctype(JRXmlConstants.ELEMENT_jasperPrint, JRXmlConstants.JASPERPRINT_PUBLIC_ID, this.dtdLocation);

		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_jasperPrint);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, this.jasperPrint.getName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_pageWidth, this.jasperPrint.getPageWidth());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_pageHeight, this.jasperPrint.getPageHeight());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_orientation, this.jasperPrint.getOrientation(), JRXmlConstants.getOrientationMap(), JRReport.ORIENTATION_PORTRAIT);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_formatFactoryClass, this.jasperPrint.getFormatFactoryClass());		
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_locale, this.jasperPrint.getLocaleCode());		
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_timezone, this.jasperPrint.getTimeZoneId());		
		
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_property);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, PROPERTY_START_PAGE_INDEX);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_value, String.valueOf(this.startPageIndex));
		this.xmlWriter.closeElement();

		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_property);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, PROPERTY_END_PAGE_INDEX);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_value, String.valueOf(this.endPageIndex));
		this.xmlWriter.closeElement();

		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_property); //FIXME make this configurable?
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, PROPERTY_PAGE_COUNT);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_value, this.jasperPrint.getPages() == null ? null : String.valueOf(this.jasperPrint.getPages().size()));
		this.xmlWriter.closeElement();

		exportProperties(this.jasperPrint);
		
		JROrigin[] origins = this.jasperPrint.getOrigins();
		if (origins != null && origins.length > 0)
		{
			for(int i = 0; i < origins.length; i++)
			{
				exportOrigin(origins[i]);
			}
		}

		JRReportFont[] fonts = this.jasperPrint.getFonts();
		if (fonts != null && fonts.length > 0)
		{
			for(int i = 0; i < fonts.length; i++)
			{
				this.fontsMap.put(fonts[i].getName(), fonts[i]);
				exportReportFont(fonts[i]);
			}
		}
		
		JRStyle[] styles = this.jasperPrint.getStyles();
		if (styles != null && styles.length > 0)
		{
			for(int i = 0; i < styles.length; i++)
			{
				this.stylesMap.put(styles[i].getName(), styles[i]);
				exportStyle(styles[i]);
			}
		}


		List pages = this.jasperPrint.getPages();
		if (pages != null && pages.size() > 0)
		{
			JRPrintPage page = null;
			for(int i = this.startPageIndex; i <= this.endPageIndex; i++)
			{
				if (Thread.currentThread().isInterrupted())
				{
					throw new JRException("Current thread interrupted.");
				}
				
				page = (JRPrintPage)pages.get(i);
	
				/*   */
				exportPage(page);
			}
		}

		this.xmlWriter.closeElement();
		
		writer.flush();
	}


	protected void exportProperties(JRPropertiesHolder propertiesHolder) throws IOException
	{
		if (propertiesHolder.hasProperties())
		{
			JRPropertiesMap propertiesMap = propertiesHolder.getPropertiesMap();
			String[] propertyNames = propertiesMap.getPropertyNames();
			if (propertyNames != null && propertyNames.length > 0)
			{
				for(int i = 0; i < propertyNames.length; i++)
				{
					this.xmlWriter.startElement(JRXmlConstants.ELEMENT_property);
					this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, propertyNames[i]);
					String value = propertiesMap.getProperty(propertyNames[i]);
					if (value != null)
					{
						this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_value, value);
					}
					this.xmlWriter.closeElement();
				}
			}
		}
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportReportFont(JRReportFont font) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_reportFont);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, font.getName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isDefault, font.isDefault());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_fontName, font.getFontName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_size, font.getFontSize());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isBold, font.isBold());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isItalic, font.isItalic());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isUnderline, font.isUnderline());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isStrikeThrough, font.isStrikeThrough());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfFontName, font.getPdfFontName());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfEncoding, font.getPdfEncoding());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isPdfEmbedded, font.isPdfEmbedded());
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 */
	protected void exportStyle(JRStyle style) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_style);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, style.getName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isDefault, style.isDefault());

		if (style.getStyle() != null)
		{
			JRStyle baseStyle = 
				(JRStyle)this.stylesMap.get(
						style.getStyle().getName()
					);
			if(baseStyle != null)
			{
				this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_style, style.getStyle().getName());
			}
			else
			{
				throw 
					new JRRuntimeException(
						"Referenced report style not found : " 
						+ style.getStyle().getName()
						);
			}
		}
	
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_mode, style.getOwnMode(), JRXmlConstants.getModeMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_forecolor, style.getOwnForecolor());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_backcolor, style.getOwnBackcolor());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_fill, style.getOwnFill(), JRXmlConstants.getFillMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_radius, style.getOwnRadius());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_scaleImage, style.getOwnScaleImage(), JRXmlConstants.getScaleImageMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hAlign, style.getOwnHorizontalAlignment(), JRXmlConstants.getHorizontalAlignMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_vAlign, style.getOwnVerticalAlignment(), JRXmlConstants.getVerticalAlignMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_rotation, style.getOwnRotation(), JRXmlConstants.getRotationMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_lineSpacing, style.getOwnLineSpacing(), JRXmlConstants.getLineSpacingMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_markup, style.getOwnMarkup());
		//xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pattern, style.getOwnPattern());//FIXME if pattern in text field is equal to this, then it should be removed there (inheritance)
		//xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isBlankWhenNull, style.isOwnBlankWhenNull());
		
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_fontName, style.getOwnFontName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_fontSize, style.getOwnFontSize());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isBold, style.isOwnBold());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isItalic, style.isOwnItalic());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isUnderline, style.isOwnUnderline());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isStrikeThrough, style.isOwnStrikeThrough());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfFontName, style.getOwnPdfFontName());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfEncoding, style.getOwnPdfEncoding());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isPdfEmbedded, style.isOwnPdfEmbedded());
		
		exportPen(style.getLinePen());
		exportBox(style.getLineBox());
		
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 */
	protected void exportOrigin(JROrigin origin) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_origin);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_report, origin.getReportName());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_group, origin.getGroupName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_band, origin.getBandType(), JRXmlConstants.getBandTypeMap());
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportPage(JRPrintPage page) throws JRException, IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_page);

		Collection elements = page.getElements();
		exportElements(elements);

		this.xmlWriter.closeElement();
		
		if (this.progressMonitor != null)
		{
			this.progressMonitor.afterPageExport();
		}
	}


	protected void exportElements(Collection elements) throws IOException, JRException
	{
		if (elements != null && elements.size() > 0)
		{
			JRPrintElement element;
			for(Iterator it = elements.iterator(); it.hasNext();)
			{
				element = (JRPrintElement)it.next();
				
				if (this.filter == null || this.filter.isToExport(element))
				{
					if (element instanceof JRPrintLine)
					{
						exportLine((JRPrintLine)element);
					}
					else if (element instanceof JRPrintRectangle)
					{
						exportRectangle((JRPrintRectangle)element);
					}
					else if (element instanceof JRPrintEllipse)
					{
						exportEllipse((JRPrintEllipse)element);
					}
					else if (element instanceof JRPrintImage)
					{
						exportImage((JRPrintImage)element);
					}
					else if (element instanceof JRPrintText)
					{
						exportText((JRPrintText)element);
					}
					else if (element instanceof JRPrintFrame)
					{
						exportFrame((JRPrintFrame) element);
					}
				}
			}
		}
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportLine(JRPrintLine line) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_line);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_direction, line.getDirection(), JRXmlConstants.getDirectionMap(), JRLine.DIRECTION_TOP_DOWN);

		exportReportElement(line);
		exportGraphicElement(line);
		
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportReportElement(JRPrintElement element) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_reportElement);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_key, element.getKey());
		JRStyle style = element.getStyle();
		if (style != null)
		{
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_style, style.getName());
		}
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_mode, element.getOwnMode(), JRXmlConstants.getModeMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_x, element.getX() + getOffsetX());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_y, element.getY() + getOffsetY());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_width, element.getWidth());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_height, element.getHeight());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_forecolor, element.getOwnForecolor());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_backcolor, element.getOwnBackcolor());
		JROrigin origin = element.getOrigin();
		if (origin != null)
		{
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_origin, this.jasperPrint.getOriginsMap().get(origin));
		}
		
		exportProperties(element);
		
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportGraphicElement(JRPrintGraphicElement element) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_graphicElement);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_fill, element.getOwnFill(), JRXmlConstants.getFillMap());
		exportPen(element.getLinePen());
		this.xmlWriter.closeElement(true);
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportPen(JRPen pen) throws IOException
	{
		exportPen(JRXmlConstants.ELEMENT_pen, pen);
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportPen(String element, JRPen pen) throws IOException
	{
		this.xmlWriter.startElement(element);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_lineWidth, pen.getOwnLineWidth());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_lineStyle, pen.getOwnLineStyle(), JRXmlConstants.getLineStyleMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_lineColor, pen.getOwnLineColor());
		this.xmlWriter.closeElement(true);
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportRectangle(JRPrintRectangle rectangle) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_rectangle);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_radius, rectangle.getOwnRadius());

		exportReportElement(rectangle);
		exportGraphicElement(rectangle);
		
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportEllipse(JRPrintEllipse ellipse) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_ellipse);

		exportReportElement(ellipse);
		exportGraphicElement(ellipse);
		
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws JRException 
	 * @throws IOException 
	 *
	 */
	protected void exportImage(JRPrintImage image) throws JRException, IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_image);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_scaleImage, image.getOwnScaleImage(), JRXmlConstants.getScaleImageMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hAlign, image.getOwnHorizontalAlignment(), JRXmlConstants.getHorizontalAlignMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_vAlign, image.getOwnVerticalAlignment(), JRXmlConstants.getVerticalAlignMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isLazy, image.isLazy(), false);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_onErrorType, image.getOnErrorType(), JRXmlConstants.getOnErrorTypeMap(), JRImage.ON_ERROR_TYPE_ERROR);
		
		if (this.hyperlinkProducerFactory == null)
		{
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkType, image.getLinkType());
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkReference, image.getHyperlinkReference());
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkAnchor, image.getHyperlinkAnchor());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkPage, image.getHyperlinkPage());
		}
		else
		{
			String reference = this.hyperlinkProducerFactory.produceHyperlink(image);
			if (reference != null)
			{
				this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkType, JRHyperlinkHelper.HYPERLINK_TYPE_REFERENCE);
				this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkReference, reference);
			}
		}
		
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkTarget, image.getHyperlinkTarget(), JRXmlConstants.getHyperlinkTargetMap(), JRHyperlink.HYPERLINK_TARGET_SELF);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkTooltip, image.getHyperlinkTooltip());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_anchorName, image.getAnchorName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_bookmarkLevel, image.getBookmarkLevel(), JRAnchor.NO_BOOKMARK);

		exportReportElement(image);
		exportBox(image.getLineBox());
		exportGraphicElement(image);
		

		JRRenderable renderer = image.getRenderer();
		if (renderer != null)
		{
			this.xmlWriter.startElement(JRXmlConstants.ELEMENT_imageSource);
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isEmbedded, this.isEmbeddingImages && !image.isLazy(), false);
	
			String imageSource = "";
			
			if (renderer.getType() == JRRenderable.TYPE_SVG)
			{
				renderer = 
					new JRWrappingSvgRenderer(
						renderer, 
						new Dimension(image.getWidth(), image.getHeight()),
						JRElement.MODE_OPAQUE == image.getMode() ? image.getBackcolor() : null
						);
			}
				
			if (image.isLazy())
			{
				imageSource = ((JRImageRenderer)renderer).getImageLocation();
			}
			else if (this.isEmbeddingImages)
			{
				try
				{
					ByteArrayInputStream bais = new ByteArrayInputStream(renderer.getImageData());
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
					Base64Encoder encoder = new Base64Encoder(bais, baos);
					encoder.process();
					
					imageSource = new String(baos.toByteArray(), DEFAULT_XML_ENCODING);
				}
				catch (IOException e)
				{
					throw new JRException("Error embedding image into XML.", e);
				}
			}
			else
			{
				if (renderer.getType() == JRRenderable.TYPE_IMAGE && this.rendererToImagePathMap.containsKey(renderer))
				{
					imageSource = (String)this.rendererToImagePathMap.get(renderer);
				}
				else
				{
					imageSource = IMAGE_PREFIX + getNextImageId();
					this.imageNameToImageDataMap.put(imageSource, renderer.getImageData());
					
					imageSource = new File(this.imagesDir, imageSource).getPath();
					this.rendererToImagePathMap.put(renderer, imageSource);
				}
			}
			
			this.xmlWriter.writeCDATA(imageSource);
			this.xmlWriter.closeElement();
		}
		
		if (this.hyperlinkProducerFactory == null)
		{
			exportHyperlinkParameters(image);
		}
		
		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 *
	 */
	protected void exportText(JRPrintText text) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_text);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_textAlignment, text.getOwnHorizontalAlignment(), JRXmlConstants.getHorizontalAlignMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_verticalAlignment, text.getOwnVerticalAlignment(), JRXmlConstants.getVerticalAlignMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_textHeight, text.getTextHeight());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_rotation, text.getOwnRotation(), JRXmlConstants.getRotationMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_runDirection, text.getRunDirection(), JRXmlConstants.getRunDirectionMap(), JRPrintText.RUN_DIRECTION_LTR);
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_lineSpacing, text.getOwnLineSpacing(), JRXmlConstants.getLineSpacingMap());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_markup, text.getOwnMarkup());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_lineSpacingFactor, text.getLineSpacingFactor());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_leadingOffset, text.getLeadingOffset());

		if (this.hyperlinkProducerFactory == null)
		{
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkType, text.getLinkType());
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkReference, text.getHyperlinkReference());
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkAnchor, text.getHyperlinkAnchor());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkPage, text.getHyperlinkPage());
		}
		else
		{
			String reference = this.hyperlinkProducerFactory.produceHyperlink(text);
			if (reference != null)
			{
				this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkType, JRHyperlinkHelper.HYPERLINK_TYPE_REFERENCE);
				this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkReference, reference);
			}
		}
		
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkTarget, text.getHyperlinkTarget(), JRXmlConstants.getHyperlinkTargetMap(), JRHyperlink.HYPERLINK_TARGET_SELF);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_hyperlinkTooltip, text.getHyperlinkTooltip());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_anchorName, text.getAnchorName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_bookmarkLevel, text.getBookmarkLevel(), JRAnchor.NO_BOOKMARK);
		
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_valueClass, text.getValueClassName());
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pattern, text.getPattern());		
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_formatFactoryClass, text.getFormatFactoryClass());		
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_locale, text.getLocaleCode());		
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_timezone, text.getTimeZoneId());		
		
		exportReportElement(text);
		exportBox(text.getLineBox());

		exportFont(text);

		if (text.getOriginalText() != null)
		{
			this.xmlWriter.writeCDATAElement(JRXmlConstants.ELEMENT_textContent, text.getOriginalText(),
					JRXmlConstants.ATTRIBUTE_truncateIndex, text.getTextTruncateIndex());
		}
		
		this.xmlWriter.writeCDATAElement(JRXmlConstants.ELEMENT_textTruncateSuffix, text.getTextTruncateSuffix());
		
		if (this.hyperlinkProducerFactory == null)
		{
			exportHyperlinkParameters(text);
		}

		this.xmlWriter.closeElement();
	}


	/**
	 * @throws IOException 
	 *
	 */
	private void exportBox(JRLineBox box) throws IOException
	{
		if (box != null)
		{
			this.xmlWriter.startElement(JRXmlConstants.ELEMENT_box);

			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_padding, box.getOwnPadding());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_topPadding, box.getOwnTopPadding());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_leftPadding, box.getOwnLeftPadding());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_bottomPadding, box.getOwnBottomPadding());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_rightPadding, box.getOwnRightPadding());

			exportPen(JRXmlConstants.ELEMENT_pen, box.getPen());
			exportPen(JRXmlConstants.ELEMENT_topPen, box.getTopPen());
			exportPen(JRXmlConstants.ELEMENT_leftPen, box.getLeftPen());
			exportPen(JRXmlConstants.ELEMENT_bottomPen, box.getBottomPen());
			exportPen(JRXmlConstants.ELEMENT_rightPen, box.getRightPen());
			
			this.xmlWriter.closeElement(true);
		}
	}


	/**
	 *
	 */
	protected void exportFont(JRFont font) throws IOException
	{
		if (font != null)
		{
			this.xmlWriter.startElement(JRXmlConstants.ELEMENT_font);

			if(font.getReportFont() != null)
			{
				JRFont baseFont = 
					(JRFont)this.fontsMap.get(
						font.getReportFont().getName()
						);
				if(baseFont != null)
				{
					this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_reportFont, font.getReportFont().getName());
				}
				else
				{
					throw 
						new JRRuntimeException(
							"Referenced report font not found : " 
							+ font.getReportFont().getName()
							);
				}
			}
		
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_fontName, font.getOwnFontName());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_size, font.getOwnFontSize());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isBold, font.isOwnBold());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isItalic, font.isOwnItalic());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isUnderline, font.isOwnUnderline());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isStrikeThrough, font.isOwnStrikeThrough());
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfFontName, font.getOwnPdfFontName());
			this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_pdfEncoding, font.getOwnPdfEncoding());
			this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_isPdfEmbedded, font.isOwnPdfEmbedded());
			this.xmlWriter.closeElement(true);
		}
	}
	
	
	protected void exportFrame(JRPrintFrame frame) throws IOException, JRException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_frame);
		
		setFrameElementsOffset(frame, true);
		try
		{
			exportReportElement(frame);
			exportBox(frame.getLineBox());
			exportElements(frame.getElements());

			this.xmlWriter.closeElement();
		}
		finally
		{
			restoreElementOffsets();
		}
	}
	
	
	/**
	 * 
	 */
	private static synchronized int getNextImageId(){
		return imageId++;
	}


	protected void exportHyperlinkParameters(JRPrintHyperlink hyperlink) throws IOException
	{
		JRPrintHyperlinkParameters hyperlinkParameters = hyperlink.getHyperlinkParameters();
		if (hyperlinkParameters != null)
		{
			for (Iterator it = hyperlinkParameters.getParameters().iterator(); it.hasNext();)
			{
				JRPrintHyperlinkParameter parameter = (JRPrintHyperlinkParameter) it.next();
				exportHyperlinkParameter(parameter);
			}
		}
	}


	protected void exportHyperlinkParameter(JRPrintHyperlinkParameter parameter) throws IOException
	{
		this.xmlWriter.startElement(JRXmlConstants.ELEMENT_hyperlinkParameter);
		this.xmlWriter.addEncodedAttribute(JRXmlConstants.ATTRIBUTE_name, parameter.getName());
		this.xmlWriter.addAttribute(JRXmlConstants.ATTRIBUTE_class, parameter.getValueClass(), DEFAULT_OBJECT_TYPE);
		
		if (parameter.getValue() != null)
		{
			String data = JRValueStringUtils.serialize(parameter.getValueClass(), parameter.getValue());
			this.xmlWriter.writeCDATAElement(JRXmlConstants.ELEMENT_hyperlinkParameterValue, data);
		}
		
		this.xmlWriter.closeElement();
	}
}
