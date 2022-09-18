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
 * Alex Parfenov - aparfeno@users.sourceforge.net
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */

package net.sf.jasperreports.engine.export;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRImageMapRenderer;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintElementIndex;
import net.sf.jasperreports.engine.JRPrintEllipse;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintGraphicElement;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintImageArea;
import net.sf.jasperreports.engine.JRPrintImageAreaHyperlink;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintRectangle;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JRWrappingSvgRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRColorUtil;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStringUtil;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.Pair;


/**
 * Exports a JasperReports document to HTML format. It has character output type and exports the document to a
 * grid-based layout.
 * <p>
 * Since classic AWT fonts can be sometimes very different from HTML fonts, a font mapping feature was added.
 * By using the {@link JRExporterParameter#FONT_MAP} parameter, a logical font like "sansserif" can be mapped to a
 * list of HTML specific fonts, like "Arial, Verdana, Tahoma". Both map keys and values are strings.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRHtmlExporter.java,v 1.1 2008/09/29 16:20:46 guehene Exp $
 */
public class JRHtmlExporter extends JRAbstractExporter
{

	private static final String HTML_EXPORTER_PROPERTIES_PREFIX = JRProperties.PROPERTY_PREFIX + "export.html.";

	/**
	 * @deprecated Replaced by  {@link JRHtmlExporterParameter#PROPERTY_FRAMES_AS_NESTED_TABLES}.
	 */
	public static final String PROPERTY_FRAMES_AS_NESTED_TABLES = JRHtmlExporterParameter.PROPERTY_FRAMES_AS_NESTED_TABLES;
	
	/**
	 *
	 */
	protected static final String JR_PAGE_ANCHOR_PREFIX = "JR_PAGE_ANCHOR_";

	/**
	 *
	 */
	protected static final String CSS_TEXT_ALIGN_LEFT = "left";
	protected static final String CSS_TEXT_ALIGN_RIGHT = "right";
	protected static final String CSS_TEXT_ALIGN_CENTER = "center";
	protected static final String CSS_TEXT_ALIGN_JUSTIFY = "justify";

	/**
	 *
	 */
	protected static final String HTML_VERTICAL_ALIGN_TOP = "top";
	protected static final String HTML_VERTICAL_ALIGN_MIDDLE = "middle";
	protected static final String HTML_VERTICAL_ALIGN_BOTTOM = "bottom";
	
	public static final String IMAGE_NAME_PREFIX = "img_";
	protected static final int IMAGE_NAME_PREFIX_LEGTH = IMAGE_NAME_PREFIX.length();

	/**
	 *
	 */
	protected Writer writer = null;
	protected JRExportProgressMonitor progressMonitor = null;
	protected Map rendererToImagePathMap = null;
	protected Map imageMaps;
	protected Map imageNameToImageDataMap = null;
	protected List imagesToProcess = null;
	protected boolean isPxImageLoaded = false;

	protected int reportIndex = 0;
	protected int pageIndex = 0;

	/**
	 *
	 */
	protected File imagesDir = null;
	protected String imagesURI = null;
	protected boolean isOutputImagesToDir = false;
	protected boolean isRemoveEmptySpace;
	protected boolean isWhitePageBackground;
	protected String encoding;
	protected String sizeUnit = null;
	protected boolean isUsingImagesToAlign;
	protected boolean isWrapBreakWord;
	protected boolean isIgnorePageMargins;

	/**
	 *
	 */
	protected String htmlHeader = null;
	protected String betweenPagesHtml = null;
	protected String htmlFooter = null;

	protected StringProvider emptyCellStringProvider = null;


	/**
	 *
	 */
	protected Map fontMap = null;

	private LinkedList backcolorStack;
	private Color backcolor;

	protected JRHyperlinkProducerFactory hyperlinkProducerFactory;
	
	protected boolean deepGrid;
	
	protected ExporterNature nature = null;


	public JRHtmlExporter()
	{
		this.backcolorStack = new LinkedList();
		this.backcolor = null;
	}


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
				this.filter = createFilter(HTML_EXPORTER_PROPERTIES_PREFIX);
			}

			/*   */
			if (!this.isModeBatch)
			{
				setPageRange();
			}
	
			this.htmlHeader = (String)this.parameters.get(JRHtmlExporterParameter.HTML_HEADER);
			this.betweenPagesHtml = (String)this.parameters.get(JRHtmlExporterParameter.BETWEEN_PAGES_HTML);
			this.htmlFooter = (String)this.parameters.get(JRHtmlExporterParameter.HTML_FOOTER);
	
			this.imagesDir = (File)this.parameters.get(JRHtmlExporterParameter.IMAGES_DIR);
			if (this.imagesDir == null)
			{
				String dir = (String)this.parameters.get(JRHtmlExporterParameter.IMAGES_DIR_NAME);
				if (dir != null)
				{
					this.imagesDir = new File(dir);
				}
			}
	
			this.isRemoveEmptySpace = 
				getBooleanParameter(
					JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					JRHtmlExporterParameter.PROPERTY_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					false
					);
	
			this.isWhitePageBackground = 
				getBooleanParameter(
					JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					JRHtmlExporterParameter.PROPERTY_WHITE_PAGE_BACKGROUND,
					true
					);
	
			Boolean isOutputImagesToDirParameter = (Boolean)this.parameters.get(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR);
			if (isOutputImagesToDirParameter != null)
			{
				this.isOutputImagesToDir = isOutputImagesToDirParameter.booleanValue();
			}
	
			String uri = (String)this.parameters.get(JRHtmlExporterParameter.IMAGES_URI);
			if (uri != null)
			{
				this.imagesURI = uri;
			}
	
			this.encoding = 
				getStringParameterOrDefault(
					JRExporterParameter.CHARACTER_ENCODING, 
					JRExporterParameter.PROPERTY_CHARACTER_ENCODING
					);
	
			this.rendererToImagePathMap = new HashMap();
			this.imageMaps = new HashMap();
			this.imagesToProcess = new ArrayList();
			this.isPxImageLoaded = false;
	
			//backward compatibility with the IMAGE_MAP parameter
			this.imageNameToImageDataMap = (Map)this.parameters.get(JRHtmlExporterParameter.IMAGES_MAP);
	//		if (imageNameToImageDataMap == null)
	//		{
	//			imageNameToImageDataMap = new HashMap();
	//		}
			//END - backward compatibility with the IMAGE_MAP parameter
	
			this.isWrapBreakWord = 
				getBooleanParameter(
					JRHtmlExporterParameter.IS_WRAP_BREAK_WORD,
					JRHtmlExporterParameter.PROPERTY_WRAP_BREAK_WORD,
					false
					);
	
			this.sizeUnit = 
				getStringParameterOrDefault(
					JRHtmlExporterParameter.SIZE_UNIT,
					JRHtmlExporterParameter.PROPERTY_SIZE_UNIT
					);
	
			this.isUsingImagesToAlign = 
				getBooleanParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					JRHtmlExporterParameter.PROPERTY_USING_IMAGES_TO_ALIGN,
					true
					);
		
			if (this.isUsingImagesToAlign)
			{
				this.emptyCellStringProvider =
					new StringProvider()
					{
						public String getStringForCollapsedTD(Object value, int width, int height, String sizeUnit)
						{
							return "><img alt=\"\" src=\"" + value + "px\" style=\"width: " + width + sizeUnit + "; height: " + height + sizeUnit + ";\"/>";
						}
						public String getStringForEmptyTD(Object value)
						{
							return "<img alt=\"\" src=\"" + value + "px\" border=\"0\"/>";
						}
					};

				loadPxImage();
			}
			else
			{
				this.emptyCellStringProvider =
					new StringProvider()
					{
						public String getStringForCollapsedTD(Object value, int width, int height, String sizeUnit)
						{
							return " style=\"width: " + width + sizeUnit + "; height: " + height + sizeUnit + ";\">";
						}
						public String getStringForEmptyTD(Object value)
						{
							return "";
						}
					};
			}
			
			this.isIgnorePageMargins = 
				getBooleanParameter(
					JRExporterParameter.IGNORE_PAGE_MARGINS,
					JRExporterParameter.PROPERTY_IGNORE_PAGE_MARGINS,
					false
					);
			
			this.fontMap = (Map) this.parameters.get(JRExporterParameter.FONT_MAP);
						
			setHyperlinkProducerFactory();
			setDeepGrid();
			
			this.nature = new JRHtmlExporterNature(this.filter, this.deepGrid, this.isIgnorePageMargins);
	
			StringBuffer sb = (StringBuffer)this.parameters.get(JRExporterParameter.OUTPUT_STRING_BUFFER);
			if (sb != null)
			{
				try
				{
					this.writer = new StringWriter();
					exportReportToWriter();
					sb.append(this.writer.toString());
				}
				catch (IOException e)
				{
					throw new JRException("Error writing to StringBuffer writer : " + this.jasperPrint.getName(), e);
				}
				finally
				{
					if (this.writer != null)
					{
						try
						{
							this.writer.close();
						}
						catch(IOException e)
						{
						}
					}
				}
			}
			else
			{
				this.writer = (Writer)this.parameters.get(JRExporterParameter.OUTPUT_WRITER);
				if (this.writer != null)
				{
					try
					{
						exportReportToWriter();
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
							this.writer = new OutputStreamWriter(os, this.encoding);
							exportReportToWriter();
						}
						catch (IOException e)
						{
							throw new JRException("Error writing to OutputStream writer : " + this.jasperPrint.getName(), e);
						}
					}
					else
					{
						File destFile = (File)this.parameters.get(JRExporterParameter.OUTPUT_FILE);
						if (destFile == null)
						{
							String fileName = (String)this.parameters.get(JRExporterParameter.OUTPUT_FILE_NAME);
							if (fileName != null)
							{
								destFile = new File(fileName);
							}
							else
							{
								throw new JRException("No output specified for the exporter.");
							}
						}
	
						try
						{
							os = new FileOutputStream(destFile);
							this.writer = new OutputStreamWriter(os, this.encoding);
						}
						catch (IOException e)
						{
							throw new JRException("Error creating to file writer : " + this.jasperPrint.getName(), e);
						}
	
						if (this.imagesDir == null)
						{
							this.imagesDir = new File(destFile.getParent(), destFile.getName() + "_files");
						}
	
						if (isOutputImagesToDirParameter == null)
						{
							this.isOutputImagesToDir = true;
						}
	
						if (this.imagesURI == null)
						{
							this.imagesURI = this.imagesDir.getName() + "/";
						}
	
						try
						{
							exportReportToWriter();
						}
						catch (IOException e)
						{
							throw new JRException("Error writing to file writer : " + this.jasperPrint.getName(), e);
						}
						finally
						{
							if (this.writer != null)
							{
								try
								{
									this.writer.close();
								}
								catch(IOException e)
								{
								}
							}
						}
					}
				}
			}
	
			if (this.isOutputImagesToDir)
			{
				if (this.imagesDir == null)
				{
					throw new JRException("The images directory was not specified for the exporter.");
				}
	
				if (this.isPxImageLoaded || (this.imagesToProcess != null && this.imagesToProcess.size() > 0))
				{
					if (!this.imagesDir.exists())
					{
						this.imagesDir.mkdir();
					}
	
					if (this.isPxImageLoaded)
					{
						JRRenderable pxRenderer =
							JRImageRenderer.getInstance("net/sf/jasperreports/engine/images/pixel.GIF");
						byte[] imageData = pxRenderer.getImageData();
	
						File imageFile = new File(this.imagesDir, "px");
						FileOutputStream fos = null;
	
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
	
					for(Iterator it = this.imagesToProcess.iterator(); it.hasNext();)
					{
						JRPrintElementIndex imageIndex = (JRPrintElementIndex)it.next();
	
						JRPrintImage image = getImage(this.jasperPrintList, imageIndex);
						JRRenderable renderer = image.getRenderer();
						if (renderer.getType() == JRRenderable.TYPE_SVG)
						{
							renderer =
								new JRWrappingSvgRenderer(
									renderer,
									new Dimension(image.getWidth(), image.getHeight()),
									JRElement.MODE_OPAQUE == image.getMode() ? image.getBackcolor() : null
									);
						}
	
						byte[] imageData = renderer.getImageData();
	
						File imageFile = new File(this.imagesDir, getImageName(imageIndex));
						FileOutputStream fos = null;
	
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
		finally
		{
			resetExportContext();
		}
	}


	protected void setHyperlinkProducerFactory()
	{
		this.hyperlinkProducerFactory = (JRHyperlinkProducerFactory) this.parameters.get(JRExporterParameter.HYPERLINK_PRODUCER_FACTORY);
	}
	
	
	protected void setDeepGrid()
	{
		boolean nestedTables;
		Boolean nestedTablesParam = (Boolean) this.parameters.get(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES);
		if (nestedTablesParam == null)
		{
			nestedTables = JRProperties.getBooleanProperty(PROPERTY_FRAMES_AS_NESTED_TABLES);
		}
		else
		{
			nestedTables = nestedTablesParam.booleanValue();
		}
		
		this.deepGrid = !nestedTables;
	}


	public static JRPrintImage getImage(List jasperPrintList, String imageName)
	{
		return getImage(jasperPrintList, getPrintElementIndex(imageName));
	}


	public static JRPrintImage getImage(List jasperPrintList, JRPrintElementIndex imageIndex)
	{
		JasperPrint report = (JasperPrint)jasperPrintList.get(imageIndex.getReportIndex());
		JRPrintPage page = (JRPrintPage)report.getPages().get(imageIndex.getPageIndex());

		Integer[] elementIndexes = imageIndex.getAddressArray();
		Object element = page.getElements().get(elementIndexes[0].intValue());

		for (int i = 1; i < elementIndexes.length; ++i)
		{
			JRPrintFrame frame = (JRPrintFrame) element;
			element = frame.getElements().get(elementIndexes[i].intValue());
		}

		return (JRPrintImage) element;
	}


	/**
	 *
	 */
	protected void exportReportToWriter() throws JRException, IOException
	{
		if (this.htmlHeader == null)
		{
			// no doctype because of bug 1430880
//			writer.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
//			writer.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
			this.writer.write("<html>\n");
			this.writer.write("<head>\n");
			this.writer.write("  <title></title>\n");
			this.writer.write("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + this.encoding + "\"/>\n");
			this.writer.write("  <style type=\"text/css\">\n");
			this.writer.write("    a {text-decoration: none}\n");
			this.writer.write("  </style>\n");
			this.writer.write("</head>\n");
			this.writer.write("<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">\n");
			this.writer.write("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n");
			this.writer.write("<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">\n");
			this.writer.write("\n");
		}
		else
		{
			this.writer.write(this.htmlHeader);
		}

		for(this.reportIndex = 0; this.reportIndex < this.jasperPrintList.size(); this.reportIndex++)
		{
			this.jasperPrint = (JasperPrint)this.jasperPrintList.get(this.reportIndex);

			List pages = this.jasperPrint.getPages();
			if (pages != null && pages.size() > 0)
			{
				if (this.isModeBatch)
				{
					this.startPageIndex = 0;
					this.endPageIndex = pages.size() - 1;
				}

				JRPrintPage page = null;
				for(this.pageIndex = this.startPageIndex; this.pageIndex <= this.endPageIndex; this.pageIndex++)
				{
					if (Thread.currentThread().isInterrupted())
					{
						throw new JRException("Current thread interrupted.");
					}

					page = (JRPrintPage)pages.get(this.pageIndex);

					this.writer.write("<a name=\"" + JR_PAGE_ANCHOR_PREFIX + this.reportIndex + "_" + (this.pageIndex + 1) + "\"/>\n");

					/*   */
					exportPage(page);

					if (this.reportIndex < this.jasperPrintList.size() - 1 || this.pageIndex < this.endPageIndex)
					{
						if (this.betweenPagesHtml == null)
						{
							this.writer.write("<br/>\n<br/>\n");
						}
						else
						{
							this.writer.write(this.betweenPagesHtml);
						}
					}

					this.writer.write("\n");
				}
			}
		}

		if (this.htmlFooter == null)
		{
			this.writer.write("</td><td width=\"50%\">&nbsp;</td></tr>\n");
			this.writer.write("</table>\n");
			this.writer.write("</body>\n");
			this.writer.write("</html>\n");
		}
		else
		{
			this.writer.write(this.htmlFooter);
		}

		this.writer.flush();
	}


	/**
	 *
	 */
	protected void exportPage(JRPrintPage page) throws JRException, IOException
	{
		JRGridLayout layout = 
			new JRGridLayout(
				this.nature,
				page.getElements(),
				this.jasperPrint.getPageWidth(), 
				this.jasperPrint.getPageHeight(),
				this.globalOffsetX, 
				this.globalOffsetY, 
				null //address
				);

		exportGrid(layout, this.isWhitePageBackground);

		if (this.progressMonitor != null)
		{
			this.progressMonitor.afterPageExport();
		}
	}
	

	/**
	 *
	 */
	protected void exportGrid(JRGridLayout gridLayout, boolean whitePageBackground) throws IOException, JRException
	{
		CutsInfo xCuts = gridLayout.getXCuts();
		JRExporterGridCell[][] grid = gridLayout.getGrid();

		this.writer.write("<table style=\"width: " + gridLayout.getWidth() + this.sizeUnit + "\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"");
		if (whitePageBackground)
		{
			this.writer.write(" bgcolor=\"white\"");
		}
		this.writer.write(">\n");

		if (whitePageBackground)
		{
			setBackcolor(Color.white);
		}

		this.writer.write("<tr>\n");
		int width = 0;
		for(int i = 1; i < xCuts.size(); i++)
		{
			width = xCuts.getCut(i) - xCuts.getCut(i - 1);
			this.writer.write("  <td" + this.emptyCellStringProvider.getStringForCollapsedTD(this.imagesURI, width, 1, this.sizeUnit) + "</td>\n");
		}
		this.writer.write("</tr>\n");
		
		for(int y = 0; y < grid.length; y++)
		{
			if (gridLayout.getYCuts().isCutSpanned(y) || !this.isRemoveEmptySpace)
			{
				JRExporterGridCell[] gridRow = grid[y];
				
				int rowHeight = JRGridLayout.getRowHeight(gridRow);
				
				boolean hasEmptyCell = hasEmptyCell(gridRow);
				
				this.writer.write("<tr valign=\"top\"");
				if (!hasEmptyCell)
				{
					this.writer.write(" style=\"height:" + rowHeight + this.sizeUnit + "\"");
				}
				this.writer.write(">\n");

				for(int x = 0; x < gridRow.length; x++)
				{
					JRExporterGridCell gridCell = gridRow[x];
					if(gridCell.getWrapper() == null)
					{
						writeEmptyCell(gridCell, rowHeight);						
					}
					else
					{
						
						JRPrintElement element = gridCell.getWrapper().getElement();

						if (element instanceof JRPrintLine)
						{
							exportLine((JRPrintLine)element, gridCell);
						}
						else if (element instanceof JRPrintRectangle)
						{
							exportRectangle((JRPrintRectangle)element, gridCell);
						}
						else if (element instanceof JRPrintEllipse)
						{
							exportRectangle((JRPrintEllipse)element, gridCell);
						}
						else if (element instanceof JRPrintImage)
						{
							exportImage((JRPrintImage)element, gridCell);
						}
						else if (element instanceof JRPrintText)
						{
							exportText((JRPrintText)element, gridCell);
						}
						else if (element instanceof JRPrintFrame)
						{
							exportFrame((JRPrintFrame) element, gridCell);
						}
					}

					x += gridCell.getColSpan() - 1;
				}

				this.writer.write("</tr>\n");
			}
		}

		if (whitePageBackground)
		{
			restoreBackcolor();
		}

		this.writer.write("</table>\n");
	}


	private boolean hasEmptyCell(JRExporterGridCell[] gridRow)
	{
		if (gridRow[0].getWrapper() == null) // quick exit
		{
			return true;
		}
		
		boolean hasEmptyCell = false;
		for(int x = 1; x < gridRow.length; x++)
		{
			if (gridRow[x].getWrapper() == null)
			{
				hasEmptyCell = true;
				break;
			}
		}

		return hasEmptyCell;
	}


	protected void writeEmptyCell(JRExporterGridCell cell, int rowHeight) throws IOException
	{
		this.writer.write("  <td");
		if (cell.getColSpan() > 1)
		{
			this.writer.write(" colspan=\"" + cell.getColSpan() + "\"");
		}

		StringBuffer styleBuffer = new StringBuffer();
		appendBackcolorStyle(cell, styleBuffer);
		appendBorderStyle(cell.getBox(), styleBuffer);

		if (styleBuffer.length() > 0)
		{
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(this.emptyCellStringProvider.getStringForCollapsedTD(this.imagesURI, cell.getWidth(), rowHeight, this.sizeUnit));
		this.writer.write("</td>\n");
	}


	/**
	 *
	 */
	protected void exportLine(JRPrintLine line, JRExporterGridCell gridCell) throws IOException
	{
		writeCellTDStart(gridCell);

		StringBuffer styleBuffer = new StringBuffer();

		appendBackcolorStyle(gridCell, styleBuffer);
		
		String side = null;
		float ratio = line.getWidth() / line.getHeight();
		if (ratio > 1)
		{
			if (line.getDirection() == JRLine.DIRECTION_TOP_DOWN)
			{
				side = "top";
			}
			else
			{
				side = "bottom";
			}
		}
		else
		{
			if (line.getDirection() == JRLine.DIRECTION_TOP_DOWN)
			{
				side = "left";
			}
			else
			{
				side = "right";
			}
		}

		appendPen(
			styleBuffer,
			line.getLinePen(),
			side
			);

		if (styleBuffer.length() > 0)
		{
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">");

		this.writer.write(this.emptyCellStringProvider.getStringForEmptyTD(this.imagesURI));

		this.writer.write("</td>\n");
	}


	/**
	 *
	 */
	protected void writeCellTDStart(JRExporterGridCell gridCell) throws IOException
	{
		this.writer.write("  <td");
		if (gridCell.getColSpan() > 1)
		{
			this.writer.write(" colspan=\"" + gridCell.getColSpan() +"\"");
		}
		if (gridCell.getRowSpan() > 1)
		{
			this.writer.write(" rowspan=\"" + gridCell.getRowSpan() + "\"");
		}
	}


	/**
	 *
	 */
	protected void exportRectangle(JRPrintGraphicElement element, JRExporterGridCell gridCell) throws IOException
	{
		writeCellTDStart(gridCell);

		StringBuffer styleBuffer = new StringBuffer();

		appendBackcolorStyle(gridCell, styleBuffer);
		
		appendPen(
			styleBuffer,
			element.getLinePen(),
			null
			);

		if (styleBuffer.length() > 0)
		{
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">");

		this.writer.write(this.emptyCellStringProvider.getStringForEmptyTD(this.imagesURI));

		this.writer.write("</td>\n");
	}


	/**
	 *
	 */
	protected void exportStyledText(JRStyledText styledText) throws IOException
	{
		String text = styledText.getText();

		int runLimit = 0;

		AttributedCharacterIterator iterator = styledText.getAttributedString().getIterator();

		while(runLimit < styledText.length() && (runLimit = iterator.getRunLimit()) <= styledText.length())
		{
			exportStyledTextRun(iterator.getAttributes(), text.substring(iterator.getIndex(), runLimit));

			iterator.setIndex(runLimit);
		}
	}


	/**
	 *
	 */
	protected void exportStyledTextRun(Map attributes, String text) throws IOException
	{
		String fontFamily;
		String fontFamilyAttr = (String)attributes.get(TextAttribute.FAMILY);
		if (this.fontMap != null && this.fontMap.containsKey(fontFamilyAttr))
		{
			fontFamily = (String) this.fontMap.get(fontFamilyAttr);
		}
		else
		{
			fontFamily = fontFamilyAttr;
		}
		this.writer.write("<span style=\"font-family: ");
		this.writer.write(fontFamily);
		this.writer.write("; ");

		Color forecolor = (Color)attributes.get(TextAttribute.FOREGROUND);
		if (!Color.black.equals(forecolor))
		{
			this.writer.write("color: #");
			this.writer.write(JRColorUtil.getColorHexa(forecolor));
			this.writer.write("; ");
		}

		Color runBackcolor = (Color)attributes.get(TextAttribute.BACKGROUND);
		if (runBackcolor != null)
		{
			this.writer.write("background-color: #");
			this.writer.write(JRColorUtil.getColorHexa(runBackcolor));
			this.writer.write("; ");
		}

		this.writer.write("font-size: ");
		this.writer.write(String.valueOf(attributes.get(TextAttribute.SIZE)));
		this.writer.write(this.sizeUnit);
		this.writer.write(";");

		/*
		if (!horizontalAlignment.equals(CSS_TEXT_ALIGN_LEFT))
		{
			writer.write(" text-align: ");
			writer.write(horizontalAlignment);
			writer.write(";");
		}
		*/

		if (TextAttribute.WEIGHT_BOLD.equals(attributes.get(TextAttribute.WEIGHT)))
		{
			this.writer.write(" font-weight: bold;");
		}
		if (TextAttribute.POSTURE_OBLIQUE.equals(attributes.get(TextAttribute.POSTURE)))
		{
			this.writer.write(" font-style: italic;");
		}
		if (TextAttribute.UNDERLINE_ON.equals(attributes.get(TextAttribute.UNDERLINE)))
		{
			this.writer.write(" text-decoration: underline;");
		}
		if (TextAttribute.STRIKETHROUGH_ON.equals(attributes.get(TextAttribute.STRIKETHROUGH)))
		{
			this.writer.write(" text-decoration: line-through;");
		}

		if (TextAttribute.SUPERSCRIPT_SUPER.equals(attributes.get(TextAttribute.SUPERSCRIPT)))
		{
			this.writer.write(" vertical-align: super;");
		}
		else if (TextAttribute.SUPERSCRIPT_SUB.equals(attributes.get(TextAttribute.SUPERSCRIPT)))
		{
			this.writer.write(" vertical-align: sub;");
		}

		this.writer.write("\">");

		this.writer.write(
			JRStringUtil.htmlEncode(text)
			);

		this.writer.write("</span>");
	}


	/**
	 *
	 */
	protected void exportText(JRPrintText text, JRExporterGridCell gridCell) throws IOException
	{
		JRStyledText styledText = getStyledText(text);

		int textLength = 0;

		if (styledText != null)
		{
			textLength = styledText.length();
		}

		writeCellTDStart(gridCell);//FIXME why dealing with cell style if no text to print (textLength == 0)?

		String verticalAlignment = HTML_VERTICAL_ALIGN_TOP;

		switch (text.getVerticalAlignment())
		{
			case JRAlignment.VERTICAL_ALIGN_BOTTOM :
			{
				verticalAlignment = HTML_VERTICAL_ALIGN_BOTTOM;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_MIDDLE :
			{
				verticalAlignment = HTML_VERTICAL_ALIGN_MIDDLE;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_TOP :
			default :
			{
				verticalAlignment = HTML_VERTICAL_ALIGN_TOP;
			}
		}

		if (!verticalAlignment.equals(HTML_VERTICAL_ALIGN_TOP))
		{
			this.writer.write(" valign=\"");
			this.writer.write(verticalAlignment);
			this.writer.write("\"");
		}

		if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_RTL)
		{
			this.writer.write(" dir=\"rtl\"");
		}

		StringBuffer styleBuffer = new StringBuffer();
		appendBackcolorStyle(gridCell, styleBuffer);
		appendBorderStyle(gridCell.getBox(), styleBuffer);

		String horizontalAlignment = CSS_TEXT_ALIGN_LEFT;

		if (textLength > 0)
		{
			switch (text.getHorizontalAlignment())
			{
				case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
				{
					horizontalAlignment = CSS_TEXT_ALIGN_RIGHT;
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_CENTER :
				{
					horizontalAlignment = CSS_TEXT_ALIGN_CENTER;
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
				{
					horizontalAlignment = CSS_TEXT_ALIGN_JUSTIFY;
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_LEFT :
				default :
				{
					horizontalAlignment = CSS_TEXT_ALIGN_LEFT;
				}
			}

			if (
				(text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR
				 && !horizontalAlignment.equals(CSS_TEXT_ALIGN_LEFT))
				|| (text.getRunDirection() == JRPrintText.RUN_DIRECTION_RTL
					&& !horizontalAlignment.equals(CSS_TEXT_ALIGN_RIGHT))
				)
			{
				styleBuffer.append("text-align: ");
				styleBuffer.append(horizontalAlignment);
				styleBuffer.append(";");
			}
		}

		if (this.isWrapBreakWord)
		{
			styleBuffer.append("width: " + gridCell.getWidth() + this.sizeUnit + "; ");
			styleBuffer.append("word-wrap: break-word; ");
		}
		
		if (text.getLineSpacing() != JRTextElement.LINE_SPACING_SINGLE)
		{
			styleBuffer.append("line-height: " + text.getLineSpacingFactor() + "; ");
		}

		if (styleBuffer.length() > 0)
		{
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">");

		if (text.getAnchorName() != null)
		{
			this.writer.write("<a name=\"");
			this.writer.write(text.getAnchorName());
			this.writer.write("\"/>");
		}

		boolean startedHyperlink = startHyperlink(text);

		if (textLength > 0)
		{
			exportStyledText(styledText);
		}
		else
		{
			this.writer.write(this.emptyCellStringProvider.getStringForEmptyTD(this.imagesURI));
		}

		if (startedHyperlink)
		{
			endHyperlink();
		}

		this.writer.write("</td>\n");
	}


	protected boolean startHyperlink(JRPrintHyperlink link) throws IOException
	{
		String href = getHyperlinkURL(link);

		if (href != null)
		{
			this.writer.write("<a href=\"");
			this.writer.write(href);
			this.writer.write("\"");

			String target = getHyperlinkTarget(link);
			if (target != null)
			{
				this.writer.write(" target=\"");
				this.writer.write(target);
				this.writer.write("\"");
			}

			if (link.getHyperlinkTooltip() != null)
			{
				this.writer.write(" title=\"");
				this.writer.write(JRStringUtil.xmlEncode(link.getHyperlinkTooltip()));
				this.writer.write("\"");
			}
			
			this.writer.write(">");
		}
		
		return href != null;
	}


	protected String getHyperlinkTarget(JRPrintHyperlink link)
	{
		String target = null;
		switch(link.getHyperlinkTarget())
		{
			case JRHyperlink.HYPERLINK_TARGET_BLANK :
			{
				target = "_blank";
				break;
			}
			case JRHyperlink.HYPERLINK_TARGET_PARENT :
			{
				target = "_parent";
				break;
			}
			case JRHyperlink.HYPERLINK_TARGET_TOP :
			{
				target = "_top";
				break;
			}
			case JRHyperlink.HYPERLINK_TARGET_SELF :
			default :
			{
				break;
			}
		}
		return target;
	}


	protected String getHyperlinkURL(JRPrintHyperlink link)
	{
		String href = null;
		JRHyperlinkProducer customHandler = getCustomHandler(link);		
		if (customHandler == null)
		{
			switch(link.getHyperlinkType())
			{
				case JRHyperlink.HYPERLINK_TYPE_REFERENCE :
				{
					if (link.getHyperlinkReference() != null)
					{
						href = link.getHyperlinkReference();
					}
					break;
				}
				case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR :
				{
					if (link.getHyperlinkAnchor() != null)
					{
						href = "#" + link.getHyperlinkAnchor();
					}
					break;
				}
				case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE :
				{
					if (link.getHyperlinkPage() != null)
					{
						href = "#" + JR_PAGE_ANCHOR_PREFIX + this.reportIndex + "_" + link.getHyperlinkPage().toString();
					}
					break;
				}
				case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR :
				{
					if (
						link.getHyperlinkReference() != null &&
						link.getHyperlinkAnchor() != null
						)
					{
						href = link.getHyperlinkReference() + "#" + link.getHyperlinkAnchor();
					}
					break;
				}
				case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE :
				{
					if (
						link.getHyperlinkReference() != null &&
						link.getHyperlinkPage() != null
						)
					{
						href = link.getHyperlinkReference() + "#" + JR_PAGE_ANCHOR_PREFIX + "0_" + link.getHyperlinkPage().toString();
					}
					break;
				}
				case JRHyperlink.HYPERLINK_TYPE_NONE :
				default :
				{
					break;
				}
			}
		}
		else
		{
			href = customHandler.getHyperlink(link);
		}
		
		return href;
	}


	protected JRHyperlinkProducer getCustomHandler(JRPrintHyperlink link)
	{
		return this.hyperlinkProducerFactory == null ? null : this.hyperlinkProducerFactory.getHandler(link.getLinkType());
	}


	protected void endHyperlink() throws IOException
	{
		this.writer.write("</a>");
	}


	protected boolean appendBorderStyle(JRLineBox box, StringBuffer styleBuffer)
	{
		boolean addedToStyle = false;
		
		if (box != null)
		{
			addedToStyle |= appendPen(
				styleBuffer,
				box.getTopPen(),
				"top"
				);
			addedToStyle |= appendPadding(
				styleBuffer,
				box.getTopPadding(),
				"top"
				);
			addedToStyle |= appendPen(
				styleBuffer,
				box.getLeftPen(),
				"left"
				);
			addedToStyle |= appendPadding(
				styleBuffer,
				box.getLeftPadding(),
				"left"
				);
			addedToStyle |= appendPen(
				styleBuffer,
				box.getBottomPen(),
				"bottom"
				);
			addedToStyle |= appendPadding(
				styleBuffer,
				box.getBottomPadding(),
				"bottom"
				);
			addedToStyle |= appendPen(
				styleBuffer,
				box.getRightPen(),
				"right"
				);
			addedToStyle |= appendPadding(
				styleBuffer,
				box.getRightPadding(),
				"right"
				);
		}
		
		return addedToStyle;
	}


	protected Color appendBackcolorStyle(JRExporterGridCell gridCell, StringBuffer styleBuffer)
	{
		Color cellBackcolor = gridCell.getCellBackcolor();
		if (cellBackcolor != null && (this.backcolor == null || cellBackcolor.getRGB() != this.backcolor.getRGB()))
		{
			styleBuffer.append("background-color: #");
			styleBuffer.append(JRColorUtil.getColorHexa(cellBackcolor));
			styleBuffer.append("; ");

			return cellBackcolor;
		}

		return null;
	}


	/**
	 *
	 */
	protected void exportImage(JRPrintImage image, JRExporterGridCell gridCell) throws JRException, IOException
	{
		writeCellTDStart(gridCell);

		String horizontalAlignment = CSS_TEXT_ALIGN_LEFT;

		switch (image.getHorizontalAlignment())
		{
			case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
			{
				horizontalAlignment = CSS_TEXT_ALIGN_RIGHT;
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_CENTER :
			{
				horizontalAlignment = CSS_TEXT_ALIGN_CENTER;
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_LEFT :
			default :
			{
				horizontalAlignment = CSS_TEXT_ALIGN_LEFT;
			}
		}

		if (!horizontalAlignment.equals(CSS_TEXT_ALIGN_LEFT))
		{
			this.writer.write(" align=\"");
			this.writer.write(horizontalAlignment);
			this.writer.write("\"");
		}

		String verticalAlignment = HTML_VERTICAL_ALIGN_TOP;

		switch (image.getVerticalAlignment())
		{
			case JRAlignment.VERTICAL_ALIGN_BOTTOM :
			{
				verticalAlignment = HTML_VERTICAL_ALIGN_BOTTOM;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_MIDDLE :
			{
				verticalAlignment = HTML_VERTICAL_ALIGN_MIDDLE;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_TOP :
			default :
			{
				verticalAlignment = HTML_VERTICAL_ALIGN_TOP;
			}
		}

		if (!verticalAlignment.equals(HTML_VERTICAL_ALIGN_TOP))
		{
			this.writer.write(" valign=\"");
			this.writer.write(verticalAlignment);
			this.writer.write("\"");
		}

		StringBuffer styleBuffer = new StringBuffer();
		appendBackcolorStyle(gridCell, styleBuffer);
		
		boolean addedToStyle = appendBorderStyle(gridCell.getBox(), styleBuffer);
		if (!addedToStyle)
		{
			appendPen(
				styleBuffer,
				image.getLinePen(),
				null
				);
		}

		if (styleBuffer.length() > 0)
		{
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">");

		if (image.getAnchorName() != null)
		{
			this.writer.write("<a name=\"");
			this.writer.write(image.getAnchorName());
			this.writer.write("\"/>");
		}
		
		JRRenderable renderer = image.getRenderer();
		JRRenderable originalRenderer = renderer;
		boolean imageMapRenderer = renderer != null 
				&& renderer instanceof JRImageMapRenderer
				&& ((JRImageMapRenderer) renderer).hasImageAreaHyperlinks();

		boolean startedHyperlink = false;
		boolean hasHyperlinks = false;

		if(renderer != null || this.isUsingImagesToAlign)
		{
			if (imageMapRenderer)
			{
				hasHyperlinks = true;
			}
			else
			{
				hasHyperlinks = startedHyperlink = startHyperlink(image);
			}
			
			this.writer.write("<img");
			String imagePath = null;
			String imageMapName = null;
			List imageMapAreas = null;
	
			byte scaleImage = image.getScaleImage();
			if (renderer != null)
			{
				if (renderer.getType() == JRRenderable.TYPE_IMAGE && this.rendererToImagePathMap.containsKey(renderer.getId()))
				{
					imagePath = (String)this.rendererToImagePathMap.get(renderer.getId());
				}
				else
				{
					if (image.isLazy())
					{
						imagePath = ((JRImageRenderer)renderer).getImageLocation();
					}
					else
					{
						JRPrintElementIndex imageIndex = getElementIndex(gridCell);
						this.imagesToProcess.add(imageIndex);
	
						String imageName = getImageName(imageIndex);
						imagePath = this.imagesURI + imageName;
	
						//backward compatibility with the IMAGE_MAP parameter
						if (this.imageNameToImageDataMap != null)
						{
							if (renderer.getType() == JRRenderable.TYPE_SVG)
							{
								renderer =
									new JRWrappingSvgRenderer(
										renderer,
										new Dimension(image.getWidth(), image.getHeight()),
										JRElement.MODE_OPAQUE == image.getMode() ? image.getBackcolor() : null
										);
							}
							this.imageNameToImageDataMap.put(imageName, renderer.getImageData());
						}
						//END - backward compatibility with the IMAGE_MAP parameter
					}
	
					this.rendererToImagePathMap.put(renderer.getId(), imagePath);
				}
				
				if (imageMapRenderer)
				{
					Rectangle renderingArea = new Rectangle(image.getWidth(), image.getHeight());
					
					if (renderer.getType() == JRRenderable.TYPE_IMAGE)
					{
						imageMapName = (String) this.imageMaps.get(new Pair(renderer.getId(), renderingArea));
					}
	
					if (imageMapName == null)
					{
						imageMapName = "map_" + getElementIndex(gridCell).toString();
						imageMapAreas = ((JRImageMapRenderer) originalRenderer).getImageAreaHyperlinks(renderingArea);//FIXMECHART
						
						if (renderer.getType() == JRRenderable.TYPE_IMAGE)
						{
							this.imageMaps.put(new Pair(renderer.getId(), renderingArea), imageMapName);
						}
					}
				}
			}
			else 		// ie: 	if(isUsingImagesToAlign)
			{
				loadPxImage();
				imagePath = this.imagesURI + "px";
				scaleImage = JRImage.SCALE_IMAGE_FILL_FRAME;
			}
	
			this.writer.write(" src=\"");
			if (imagePath != null)
				this.writer.write(imagePath);
			this.writer.write("\"");
		
			int imageWidth = image.getWidth() - image.getLineBox().getLeftPadding().intValue() - image.getLineBox().getRightPadding().intValue();
			if (imageWidth < 0)
			{
				imageWidth = 0;
			}
		
			int imageHeight = image.getHeight() - image.getLineBox().getTopPadding().intValue() - image.getLineBox().getBottomPadding().intValue();
			if (imageHeight < 0)
			{
				imageHeight = 0;
			}
		
			switch (scaleImage)
			{
				case JRImage.SCALE_IMAGE_FILL_FRAME :
				{
					this.writer.write(" style=\"width: ");
					this.writer.write(String.valueOf(imageWidth));
					this.writer.write(this.sizeUnit);
					this.writer.write("; height: ");
					this.writer.write(String.valueOf(imageHeight));
					this.writer.write(this.sizeUnit);
					this.writer.write("\"");
		
					break;
				}
				case JRImage.SCALE_IMAGE_CLIP : //FIXMEIMAGE image clip could be achieved by cutting the image and preserving the image type
				case JRImage.SCALE_IMAGE_RETAIN_SHAPE :
				default :
				{
					double normalWidth = imageWidth;
					double normalHeight = imageHeight;
		
					if (!image.isLazy())
					{
						// Image load might fail. 
						JRRenderable tmpRenderer = 
							JRImageRenderer.getOnErrorRendererForDimension(renderer, image.getOnErrorType());
						Dimension2D dimension = tmpRenderer == null ? null : tmpRenderer.getDimension();
						// If renderer was replaced, ignore image dimension.
						if (tmpRenderer == renderer && dimension != null)
						{
							normalWidth = dimension.getWidth();
							normalHeight = dimension.getHeight();
						}
					}
		
					if (imageHeight > 0)
					{
						double ratio = normalWidth / normalHeight;
		
						if( ratio > (double)imageWidth / (double)imageHeight )
						{
							this.writer.write(" style=\"width: ");
							this.writer.write(String.valueOf(imageWidth));
							this.writer.write(this.sizeUnit);
							this.writer.write("\"");
						}
						else
						{
							this.writer.write(" style=\"height: ");
							this.writer.write(String.valueOf(imageHeight));
							this.writer.write(this.sizeUnit);
							this.writer.write("\"");
						}
					}
				}
			}
			
			if (imageMapName != null)
			{
				this.writer.write(" usemap=\"#" + imageMapName + "\"");
			}
			
			this.writer.write(" alt=\"\"");
			
			if (hasHyperlinks)
			{
				this.writer.write(" border=\"0\"");
			}
			this.writer.write("/>");
			if (startedHyperlink)
			{
				endHyperlink();
			}
			
			if (imageMapAreas != null)
			{
				this.writer.write("\n");
				writeImageMap(imageMapName, image, imageMapAreas);
			}
		}
		this.writer.write("</td>\n");
	}


	protected JRPrintElementIndex getElementIndex(JRExporterGridCell gridCell)
	{
		JRPrintElementIndex imageIndex =
			new JRPrintElementIndex(
					this.reportIndex,
					this.pageIndex,
					gridCell.getWrapper().getAddress()
					);
		return imageIndex;
	}


	protected void writeImageMap(String imageMapName, JRPrintImage image, List imageMapAreas) throws IOException
	{
		this.writer.write("<map name=\"" + imageMapName + "\">\n");

		for (ListIterator it = imageMapAreas.listIterator(imageMapAreas.size()); it.hasPrevious();)
		{
			JRPrintImageAreaHyperlink areaHyperlink = (JRPrintImageAreaHyperlink) it.previous();
			JRPrintImageArea area = areaHyperlink.getArea();

			this.writer.write("  <area shape=\"" + JRPrintImageArea.getHtmlShape(area.getShape()) + "\"");
			writeImageAreaCoordinates(area.getCoordinates());			
			writeImageAreaHyperlink(areaHyperlink.getHyperlink());
			this.writer.write("/>\n");
		}
		
		if (image.getHyperlinkType() != JRHyperlink.HYPERLINK_TYPE_NONE)
		{
			this.writer.write("  <area shape=\"default\"");
			writeImageAreaCoordinates(new int[]{0, 0, image.getWidth(), image.getHeight()});//for IE
			writeImageAreaHyperlink(image);
			this.writer.write("/>\n");
		}
		
		this.writer.write("</map>\n");
	}

	
	protected void writeImageAreaCoordinates(int[] coords) throws IOException
	{
		if (coords != null && coords.length > 0)
		{
			StringBuffer coordsEnum = new StringBuffer(coords.length * 4);
			coordsEnum.append(coords[0]);
			for (int i = 1; i < coords.length; i++)
			{
				coordsEnum.append(',');
				coordsEnum.append(coords[i]);
			}
			this.writer.write(" coords=\"" + coordsEnum + "\"");
		}		
	}


	protected void writeImageAreaHyperlink(JRPrintHyperlink hyperlink) throws IOException
	{
		String href = getHyperlinkURL(hyperlink);
		if (href == null)
		{
			this.writer.write(" nohref=\"nohref\"");
		}
		else
		{
			this.writer.write(" href=\"" + href + "\"");
			
			String target = getHyperlinkTarget(hyperlink);
			if (target != null)
			{
				this.writer.write(" target=\"");
				this.writer.write(target);
				this.writer.write("\"");
			}
		}

		if (hyperlink.getHyperlinkTooltip() != null)
		{
			this.writer.write(" title=\"");
			this.writer.write(JRStringUtil.xmlEncode(hyperlink.getHyperlinkTooltip()));
			this.writer.write("\"");
		}
	}


	/**
	 *
	 */
	protected void loadPxImage() throws JRException
	{
		this.isPxImageLoaded = true;
		//backward compatibility with the IMAGE_MAP parameter
		if (this.imageNameToImageDataMap != null && !this.imageNameToImageDataMap.containsKey("px"))
		{
			JRRenderable pxRenderer =
				JRImageRenderer.getInstance("net/sf/jasperreports/engine/images/pixel.GIF");
			this.rendererToImagePathMap.put(pxRenderer.getId(), this.imagesURI + "px");
			this.imageNameToImageDataMap.put("px", pxRenderer.getImageData());
		}
		//END - backward compatibility with the IMAGE_MAP parameter
	}


	/**
	 *
	 */
	protected static interface StringProvider
	{

		/**
		 *
		 */
		public String getStringForCollapsedTD(Object value, int width, int height, String sizeUnit);

		/**
		 *
		 */
		public String getStringForEmptyTD(Object value);

	}


	/**
	 *
	 */
	private boolean appendPadding(StringBuffer sb, Integer padding, String side)
	{
		boolean addedToStyle = false;
		
		if (padding.intValue() > 0)
		{
			sb.append("padding");
			if (side != null)
			{
				sb.append("-");
				sb.append(side);
			}
			sb.append(": ");
			sb.append(padding);
			sb.append(this.sizeUnit);
			sb.append("; ");

			addedToStyle = true;
		}
		
		return addedToStyle;
	}


	/**
	 *
	 */
	private boolean appendPen(StringBuffer sb, JRPen pen, String side)
	{
		boolean addedToStyle = false;
		
		float borderWidth = pen.getLineWidth().floatValue();
		if (0f < borderWidth && borderWidth < 1f)
		{
			borderWidth = 1f;
		}

		String borderStyle = null;
		switch (pen.getLineStyle().byteValue())
		{
			case JRPen.LINE_STYLE_DOUBLE :
			{
				borderStyle = "double";
				break;
			}
			case JRPen.LINE_STYLE_DOTTED :
			{
				borderStyle = "dotted";
				break;
			}
			case JRPen.LINE_STYLE_DASHED :
			{
				borderStyle = "dashed";
				break;
			}
			case JRPen.LINE_STYLE_SOLID :
			default :
			{
				borderStyle = "solid";
				break;
			}
		}

		if (borderWidth > 0f)
		{
			sb.append("border");
			if (side != null)
			{
				sb.append("-");
				sb.append(side);
			}
			sb.append("-style: ");
			sb.append(borderStyle);
			sb.append("; ");

			sb.append("border");
			if (side != null)
			{
				sb.append("-");
				sb.append(side);
			}
			sb.append("-width: ");
			sb.append((int)borderWidth);
			sb.append(this.sizeUnit);
			sb.append("; ");

			sb.append("border");
			if (side != null)
			{
				sb.append("-");
				sb.append(side);
			}
			sb.append("-color: #");
			sb.append(JRColorUtil.getColorHexa(pen.getLineColor()));
			sb.append("; ");

			addedToStyle = true;
		}

		return addedToStyle;
	}


	/**
	 *
	 */
	public static String getImageName(JRPrintElementIndex printElementIndex)
	{
		return IMAGE_NAME_PREFIX + printElementIndex.toString();
	}


	/**
	 *
	 */
	public static JRPrintElementIndex getPrintElementIndex(String imageName)
	{
		if (!imageName.startsWith(IMAGE_NAME_PREFIX))
		{
			throw new JRRuntimeException("Invalid image name: " + imageName);
		}

		return JRPrintElementIndex.parsePrintElementIndex(imageName.substring(IMAGE_NAME_PREFIX_LEGTH));
	}


	protected void exportFrame(JRPrintFrame frame, JRExporterGridCell gridCell) throws IOException, JRException
	{
		writeCellTDStart(gridCell);

		StringBuffer styleBuffer = new StringBuffer();
		Color frameBackcolor = appendBackcolorStyle(gridCell, styleBuffer);
		appendBorderStyle(gridCell.getBox(), styleBuffer);

		if (styleBuffer.length() > 0)
		{
			this.writer.write(" style=\"");
			this.writer.write(styleBuffer.toString());
			this.writer.write("\"");
		}

		this.writer.write(">\n");

		if (frameBackcolor != null)
		{
			setBackcolor(frameBackcolor);
		}
		try
		{
			exportGrid(gridCell.getLayout(), false);
		}
		finally
		{
			if (frameBackcolor != null)
			{
				restoreBackcolor();
			}
		}

		this.writer.write("</td>\n");
	}


	protected void setBackcolor(Color color)
	{
		this.backcolorStack.addLast(this.backcolor);

		this.backcolor = color;
	}


	protected void restoreBackcolor()
	{
		this.backcolor = (Color) this.backcolorStack.removeLast();
	}
	
}

