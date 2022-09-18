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
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 * Ling Li - lonecatz@users.sourceforge.net
 * Martin Clough - mtclough@users.sourceforge.net
 */
package net.sf.jasperreports.engine.export;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintAnchor;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintEllipse;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintRectangle;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.export.legacy.BorderOffset;
import net.sf.jasperreports.engine.util.BreakIteratorSplitCharacter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.JRTextAttribute;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.SplitCharacter;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Exports a JasperReports document to PDF format. It has binary output type and exports the document to
 * a free-form layout.
 * <p>
 * Since classic AWT fonts can be sometimes very different from PDF fonts, a font mapping feature was added.
 * By using the {@link JRExporterParameter#FONT_MAP} parameter, a logical font like "sansserif" can be mapped
 * to a system specific font, like "Helvetica-BoldOblique". PDF font mapping is a little more complicated, because
 * for a logical font, PDF cand provide two or more fonts, from the same family but with different styles (like
 * "Helvetica", "Helvetica-Bold", "Helvetica-BoldOblique"). So every key in the map is a simple bean containing
 * font family, bold and italic flag, and every value is another bean containing the PDF font name, encoding and
 * embedding flag.
 * @see FontKey
 * @see PdfFont
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRPdfExporter.java,v 1.1 2008/09/29 16:20:46 guehene Exp $
 */
public class JRPdfExporter extends JRAbstractExporter
{

	/**
	 * @deprecated Replaced by {@link JRPdfExporterParameter#PROPERTY_FORCE_SVG_SHAPES}.
	 */
	public static final String PDF_FORCE_SVG_SHAPES = JRPdfExporterParameter.PROPERTY_FORCE_SVG_SHAPES;

	private static final String PDF_EXPORTER_PROPERTIES_PREFIX = JRProperties.PROPERTY_PREFIX + "export.pdf.";
	private static final String EMPTY_BOOKMARK_TITLE = "";

	/**
	 *
	 */
	protected static final String JR_PAGE_ANCHOR_PREFIX = "JR_PAGE_ANCHOR_";

	protected static boolean fontsRegistered = false;

	/**
	 *
	 */
	protected Document document = null;
	protected PdfContentByte pdfContentByte = null;
	protected PdfWriter pdfWriter = null;

	protected Document imageTesterDocument = null;
	protected PdfContentByte imageTesterPdfContentByte = null;

	protected JRExportProgressMonitor progressMonitor = null;

	protected int reportIndex = 0;

	/**
	 *
	 */
	protected boolean forceSvgShapes;
	protected boolean isCreatingBatchModeBookmarks;
	protected boolean isCompressed;
	protected boolean isEncrypted;
	protected boolean is128BitKey;
	protected String userPassword;
	protected String ownerPassword;
	protected int permissions = 0;
	protected Character pdfVersion;
	protected String pdfJavaScript;

	/**
	 *
	 */
	protected Map loadedImagesMap = null;
	protected Image pxImage = null;

	private BookmarkStack bookmarkStack = null;

	private Map fontMap = null;

	private SplitCharacter splitCharacter;
	protected JRHyperlinkProducerFactory hyperlinkProducerFactory;
	
	/**
	 *
	 */
	protected Image getPxImage()
	{
		if (this.pxImage == null)
		{
			try
			{
				this.pxImage =
					Image.getInstance(
						JRLoader.loadBytesFromLocation("net/sf/jasperreports/engine/images/pixel.GIF", null)
						);
			}
			catch(Exception e)
			{
				throw new JRRuntimeException(e);
			}
		}

		return this.pxImage;
	}


	/**
	 *
	 */
	public void exportReport() throws JRException
	{
		registerFonts();

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
				this.filter = createFilter(PDF_EXPORTER_PROPERTIES_PREFIX);
			}

			/*   */
			if (!this.isModeBatch)
			{
				setPageRange();
			}

			this.isCreatingBatchModeBookmarks = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					JRPdfExporterParameter.PROPERTY_CREATE_BATCH_MODE_BOOKMARKS,
					false
					);

			this.forceSvgShapes = //FIXME certain properties need to be read from each individual document in batch mode; check all exporters and all props
				getBooleanParameter(
					JRPdfExporterParameter.FORCE_SVG_SHAPES,
					JRPdfExporterParameter.PROPERTY_FORCE_SVG_SHAPES,
					false
					);

			this.isCompressed = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_COMPRESSED,
					JRPdfExporterParameter.PROPERTY_COMPRESSED,
					false
					);

			this.isEncrypted = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_ENCRYPTED,
					JRPdfExporterParameter.PROPERTY_ENCRYPTED,
					false
					);

			this.is128BitKey = 
				getBooleanParameter(
					JRPdfExporterParameter.IS_128_BIT_KEY,
					JRPdfExporterParameter.PROPERTY_128_BIT_KEY,
					false
					);

			this.userPassword = 
				getStringParameter(
					JRPdfExporterParameter.USER_PASSWORD,
					JRPdfExporterParameter.PROPERTY_USER_PASSWORD
					);

			this.ownerPassword = 
				getStringParameter(
					JRPdfExporterParameter.OWNER_PASSWORD,
					JRPdfExporterParameter.PROPERTY_OWNER_PASSWORD
					);

			Integer permissionsParameter = (Integer)this.parameters.get(JRPdfExporterParameter.PERMISSIONS);
			if (permissionsParameter != null)
			{
				this.permissions = permissionsParameter.intValue();
			}

			String strPdfVersion = 
				getStringParameter(
					JRPdfExporterParameter.PDF_VERSION,
					JRPdfExporterParameter.PROPERTY_PDF_VERSION
					);
			this.pdfVersion = 
				(strPdfVersion == null || strPdfVersion.length() == 0) 
				? null 
				: Character.valueOf(strPdfVersion.charAt(0));

			this.fontMap = (Map) this.parameters.get(JRExporterParameter.FONT_MAP);

			setSplitCharacter();
			setHyperlinkProducerFactory();

			this.pdfJavaScript = 
				getStringParameter(
					JRPdfExporterParameter.PDF_JAVASCRIPT,
					JRPdfExporterParameter.PROPERTY_PDF_JAVASCRIPT
					);

			OutputStream os = (OutputStream)this.parameters.get(JRExporterParameter.OUTPUT_STREAM);
			if (os != null)
			{
				exportReportToStream(os);
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
					exportReportToStream(os);
					os.flush();
				}
				catch (IOException e)
				{
					throw new JRException("Error trying to export to file : " + destFile, e);
				}
				finally
				{
					if (os != null)
					{
						try
						{
							os.close();
						}
						catch(IOException e)
						{
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


	protected void setSplitCharacter()
	{
		boolean useFillSplitCharacter;
		Boolean useFillSplitCharacterParam = (Boolean) this.parameters.get(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY);
		if (useFillSplitCharacterParam == null)
		{
			useFillSplitCharacter = 
				JRProperties.getBooleanProperty(
					this.jasperPrint.getPropertiesMap(),
					JRPdfExporterParameter.PROPERTY_FORCE_LINEBREAK_POLICY,
					false
					);
		}
		else
		{
			useFillSplitCharacter = useFillSplitCharacterParam.booleanValue();
		}

		if (useFillSplitCharacter)
		{
			this.splitCharacter = new BreakIteratorSplitCharacter();
		}
	}


	protected void setHyperlinkProducerFactory()
	{
		this.hyperlinkProducerFactory = (JRHyperlinkProducerFactory) this.parameters.get(JRExporterParameter.HYPERLINK_PRODUCER_FACTORY);
	}


	/**
	 *
	 */
	protected void exportReportToStream(OutputStream os) throws JRException
	{
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();

		this.document =
			new Document(
				new Rectangle(
					this.jasperPrint.getPageWidth(),
					this.jasperPrint.getPageHeight()
				)
			);

		this.imageTesterDocument =
			new Document(
				new Rectangle(
					10, //jasperPrint.getPageWidth(),
					10 //jasperPrint.getPageHeight()
				)
			);

		boolean closeDocuments = true;
		try
		{
			this.pdfWriter = PdfWriter.getInstance(this.document, os);
			this.pdfWriter.setCloseStream(false);

			if (this.pdfVersion != null)
				this.pdfWriter.setPdfVersion(this.pdfVersion.charValue());

			if (this.isCompressed)
				this.pdfWriter.setFullCompression();

			if (this.isEncrypted)
			{
				this.pdfWriter.setEncryption(
					this.is128BitKey,
					this.userPassword,
					this.ownerPassword,
					this.permissions
					);
			}

			// Add meta-data parameters to generated PDF document
			// mtclough@users.sourceforge.net 2005-12-05
			String title = (String)this.parameters.get(JRPdfExporterParameter.METADATA_TITLE);
			if( title != null )
				this.document.addTitle(title);

			String author = (String)this.parameters.get(JRPdfExporterParameter.METADATA_AUTHOR);
			if( author != null )
				this.document.addAuthor(author);

			String subject = (String)this.parameters.get(JRPdfExporterParameter.METADATA_SUBJECT);
			if( subject != null )
				this.document.addSubject(subject);

			String keywords = (String)this.parameters.get(JRPdfExporterParameter.METADATA_KEYWORDS);
			if( keywords != null )
				this.document.addKeywords(keywords);

			String creator = (String)this.parameters.get(JRPdfExporterParameter.METADATA_CREATOR);
			if( creator != null )
				this.document.addCreator(creator);
			else
				this.document.addCreator("JasperReports (" + this.jasperPrint.getName() + ")");

			this.document.open();

			if(this.pdfJavaScript != null)
				this.pdfWriter.addJavaScript(this.pdfJavaScript);

			this.pdfContentByte = this.pdfWriter.getDirectContent();

			initBookmarks();

			PdfWriter imageTesterPdfWriter =
				PdfWriter.getInstance(
					this.imageTesterDocument,
					new NullOutputStream() // discard the output
					);
			this.imageTesterDocument.open();
			this.imageTesterDocument.newPage();
			this.imageTesterPdfContentByte = imageTesterPdfWriter.getDirectContent();
			this.imageTesterPdfContentByte.setLiteral("\n");

			for(this.reportIndex = 0; this.reportIndex < this.jasperPrintList.size(); this.reportIndex++)
			{
				this.jasperPrint = (JasperPrint)this.jasperPrintList.get(this.reportIndex);
				this.loadedImagesMap = new HashMap();
				this.document.setPageSize(new Rectangle(this.jasperPrint.getPageWidth(), this.jasperPrint.getPageHeight()));
				
				BorderOffset.setLegacy(
					JRProperties.getBooleanProperty(this.jasperPrint, BorderOffset.PROPERTY_LEGACY_BORDER_OFFSET, false)
					);

				List pages = this.jasperPrint.getPages();
				if (pages != null && pages.size() > 0)
				{
					if (this.isModeBatch)
					{
						this.document.newPage();

						if( this.isCreatingBatchModeBookmarks ){
							//add a new level to our outline for this report
							addBookmark(0, this.jasperPrint.getName(), 0, 0);
						}

						this.startPageIndex = 0;
						this.endPageIndex = pages.size() - 1;
					}

					for(int pageIndex = this.startPageIndex; pageIndex <= this.endPageIndex; pageIndex++)
					{
						if (Thread.currentThread().isInterrupted())
						{
							throw new JRException("Current thread interrupted.");
						}

						JRPrintPage page = (JRPrintPage)pages.get(pageIndex);

						this.document.newPage();

						this.pdfContentByte = this.pdfWriter.getDirectContent();

						this.pdfContentByte.setLineCap(2);//PdfContentByte.LINE_CAP_PROJECTING_SQUARE since iText 1.02b

						writePageAnchor(pageIndex);

						/*   */
						exportPage(page);
					}
				}
				else
				{
					this.document.newPage();
					this.pdfContentByte = this.pdfWriter.getDirectContent();
					this.pdfContentByte.setLiteral("\n");
				}
			}

			closeDocuments = false;
			this.document.close();
			this.imageTesterDocument.close();
		}
		catch(DocumentException e)
		{
			throw new JRException("PDF Document error : " + this.jasperPrint.getName(), e);
		}
		catch(IOException e)
		{
			throw new JRException("Error generating PDF report : " + this.jasperPrint.getName(), e);
		}
		finally
		{
			if (closeDocuments) //only on exception
			{
				try
				{
					this.document.close();
				}
				catch (Throwable e)
				{
					// ignore, let the original exception propagate
				}

				try
				{
					this.imageTesterDocument.close();
				}
				catch (Throwable e)
				{
					// ignore, let the original exception propagate
				}
			}
		}

		//return os.toByteArray();
	}


	protected void writePageAnchor(int pageIndex) throws DocumentException {
		Map pdfFontAttrs = getDefaultPdfFontAttributes();
		Chunk chunk;
		if (pdfFontAttrs == null) {
			chunk = new Chunk(" ");
		} else {
			Font pdfFont = getFont(pdfFontAttrs);
			chunk = new Chunk(" ", pdfFont);
		}
		
		chunk.setLocalDestination(JR_PAGE_ANCHOR_PREFIX + this.reportIndex + "_" + (pageIndex + 1));

		ColumnText colText = new ColumnText(this.pdfContentByte);
		colText.setSimpleColumn(
			new Phrase(chunk),
			0,
			this.jasperPrint.getPageHeight(),
			1,
			1,
			0,
			Element.ALIGN_LEFT
			);

		colText.go();
	}

	protected Map getDefaultPdfFontAttributes() {
		Map attrs;
		JRStyle style = this.jasperPrint.getDefaultStyle();
		if (style != null) {
			attrs = new HashMap();
			attrs.put(JRTextAttribute.PDF_FONT_NAME, style.getPdfFontName());
			attrs.put(JRTextAttribute.PDF_ENCODING, style.getPdfEncoding());
			attrs.put(JRTextAttribute.IS_PDF_EMBEDDED, style.isPdfEmbedded());
		} else {
			JRReportFont font = this.jasperPrint.getDefaultFont();
			if (font != null) {
				attrs = new HashMap();
				attrs.put(JRTextAttribute.PDF_FONT_NAME, font.getPdfFontName());
				attrs.put(JRTextAttribute.PDF_ENCODING, font.getPdfEncoding());
				attrs.put(JRTextAttribute.IS_PDF_EMBEDDED, font.isPdfEmbedded() ? Boolean.TRUE : Boolean.FALSE);
			} else {
				attrs = null;
			}
		}
		return attrs;
	}

	/**
	 *
	 */
	protected void exportPage(JRPrintPage page) throws JRException, DocumentException, IOException
	{
		Collection elements = page.getElements();
		exportElements(elements);

		if (this.progressMonitor != null)
		{
			this.progressMonitor.afterPageExport();
		}
	}


	protected void exportElements(Collection elements) throws DocumentException, IOException, JRException
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
	 *
	 */
	protected void exportLine(JRPrintLine line)
	{
		float lineWidth = line.getLinePen().getLineWidth().floatValue(); 
		if (lineWidth > 0f)
		{
			preparePen(this.pdfContentByte, line.getLinePen(), PdfContentByte.LINE_CAP_BUTT);

			if (line.getWidth() == 1)
			{
				if (line.getHeight() == 1)
				{
					//Nothing to draw
				}
				else
				{
					//Vertical line
					if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
					{
						this.pdfContentByte.moveTo(
							line.getX() + getOffsetX() + 0.5f - lineWidth / 3,
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY()
							);
						this.pdfContentByte.lineTo(
							line.getX() + getOffsetX() + 0.5f - lineWidth / 3,
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight()
							);

						this.pdfContentByte.stroke();
						
						this.pdfContentByte.moveTo(
							line.getX() + getOffsetX() + 0.5f + lineWidth / 3,
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY()
							);
						this.pdfContentByte.lineTo(
							line.getX() + getOffsetX() + 0.5f + lineWidth / 3,
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight()
							);
					}
					else
					{
						this.pdfContentByte.moveTo(
							line.getX() + getOffsetX() + 0.5f,
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY()
							);
						this.pdfContentByte.lineTo(
							line.getX() + getOffsetX() + 0.5f,
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight()
							);
					}
				}
			}
			else
			{
				if (line.getHeight() == 1)
				{
					//Horizontal line
					if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
					{
						this.pdfContentByte.moveTo(
							line.getX() + getOffsetX(),
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - 0.5f + lineWidth / 3
							);
						this.pdfContentByte.lineTo(
							line.getX() + getOffsetX() + line.getWidth(),
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - 0.5f + lineWidth / 3
							);

						this.pdfContentByte.stroke();
						
						this.pdfContentByte.moveTo(
							line.getX() + getOffsetX(),
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - 0.5f - lineWidth / 3
							);
						this.pdfContentByte.lineTo(
							line.getX() + getOffsetX() + line.getWidth(),
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - 0.5f - lineWidth / 3
							);
					}
					else
					{
						this.pdfContentByte.moveTo(
							line.getX() + getOffsetX(),
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - 0.5f
							);
						this.pdfContentByte.lineTo(
							line.getX() + getOffsetX() + line.getWidth(),
							this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - 0.5f
							);
					}
				}
				else
				{
					//Oblique line
					if (line.getDirection() == JRLine.DIRECTION_TOP_DOWN)
					{
						if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
						{
							double xtrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getWidth(), 2) / Math.pow(line.getHeight(), 2))); 
							double ytrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getHeight(), 2) / Math.pow(line.getWidth(), 2))); 
							
							this.pdfContentByte.moveTo(
								line.getX() + getOffsetX() + (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() + (float)ytrans
								);
							this.pdfContentByte.lineTo(
								line.getX() + getOffsetX() + line.getWidth() + (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight() + (float)ytrans
								);

							this.pdfContentByte.stroke();
							
							this.pdfContentByte.moveTo(
								line.getX() + getOffsetX() - (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - (float)ytrans
								);
							this.pdfContentByte.lineTo(
								line.getX() + getOffsetX() + line.getWidth() - (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight() - (float)ytrans
								);
						}
						else
						{
							this.pdfContentByte.moveTo(
								line.getX() + getOffsetX(),
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY()
								);
							this.pdfContentByte.lineTo(
								line.getX() + getOffsetX() + line.getWidth(),
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight()
								);
						}
					}
					else
					{
						if (line.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
						{
							double xtrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getWidth(), 2) / Math.pow(line.getHeight(), 2))); 
							double ytrans = lineWidth / (3 * Math.sqrt(1 + Math.pow(line.getHeight(), 2) / Math.pow(line.getWidth(), 2))); 
							
							this.pdfContentByte.moveTo(
								line.getX() + getOffsetX() + (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight() - (float)ytrans
								);
							this.pdfContentByte.lineTo(
								line.getX() + getOffsetX() + line.getWidth() + (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - (float)ytrans
								);

							this.pdfContentByte.stroke();

							this.pdfContentByte.moveTo(
								line.getX() + getOffsetX() - (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight() + (float)ytrans
								);
							this.pdfContentByte.lineTo(
								line.getX() + getOffsetX() + line.getWidth() - (float)xtrans,
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() + (float)ytrans
								);
						}
						else
						{
							this.pdfContentByte.moveTo(
								line.getX() + getOffsetX(),
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY() - line.getHeight()
								);
							this.pdfContentByte.lineTo(
								line.getX() + getOffsetX() + line.getWidth(),
								this.jasperPrint.getPageHeight() - line.getY() - getOffsetY()
								);
						}
					}
				}
			}

			this.pdfContentByte.stroke();

			this.pdfContentByte.setLineDash(0f);
			this.pdfContentByte.setLineCap(PdfContentByte.LINE_CAP_PROJECTING_SQUARE);
		}
	}


	/**
	 *
	 */
	protected void exportRectangle(JRPrintRectangle rectangle)
	{
		this.pdfContentByte.setRGBColorFill(
			rectangle.getBackcolor().getRed(),
			rectangle.getBackcolor().getGreen(),
			rectangle.getBackcolor().getBlue()
			);

		preparePen(this.pdfContentByte, rectangle.getLinePen(), PdfContentByte.LINE_CAP_PROJECTING_SQUARE);

		float lineWidth = rectangle.getLinePen().getLineWidth().floatValue();
		float lineOffset = BorderOffset.getOffset(rectangle.getLinePen());
		
		if (rectangle.getMode() == JRElement.MODE_OPAQUE)
		{
			this.pdfContentByte.roundRectangle(
				rectangle.getX() + getOffsetX(),
				this.jasperPrint.getPageHeight() - rectangle.getY() - getOffsetY() - rectangle.getHeight(),
				rectangle.getWidth(),
				rectangle.getHeight(),
				rectangle.getRadius()
				);

			this.pdfContentByte.fill();
		}

		if (lineWidth > 0f)
		{
			if (rectangle.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				this.pdfContentByte.roundRectangle(
					rectangle.getX() + getOffsetX() - lineWidth / 3,
					this.jasperPrint.getPageHeight() - rectangle.getY() - getOffsetY() - rectangle.getHeight() - lineWidth / 3,
					rectangle.getWidth() + 2 * lineWidth / 3,
					rectangle.getHeight() + 2 * lineWidth / 3,
					rectangle.getRadius()
					);

				this.pdfContentByte.stroke();
				
				this.pdfContentByte.roundRectangle(
					rectangle.getX() + getOffsetX() + lineWidth / 3,
					this.jasperPrint.getPageHeight() - rectangle.getY() - getOffsetY() - rectangle.getHeight() + lineWidth / 3,
					rectangle.getWidth() - 2 * lineWidth / 3,
					rectangle.getHeight() - 2 * lineWidth / 3,
					rectangle.getRadius()
					);
				
				this.pdfContentByte.stroke();
			}
			else
			{
				this.pdfContentByte.roundRectangle(
					rectangle.getX() + getOffsetX() + lineOffset,
					this.jasperPrint.getPageHeight() - rectangle.getY() - getOffsetY() - rectangle.getHeight() + lineOffset,
					rectangle.getWidth() - 2 * lineOffset,
					rectangle.getHeight() - 2 * lineOffset,
					rectangle.getRadius()
					);

				this.pdfContentByte.stroke();
			}
		}

		this.pdfContentByte.setLineDash(0f);
	}


	/**
	 *
	 */
	protected void exportEllipse(JRPrintEllipse ellipse)
	{
		this.pdfContentByte.setRGBColorFill(
			ellipse.getBackcolor().getRed(),
			ellipse.getBackcolor().getGreen(),
			ellipse.getBackcolor().getBlue()
			);

		preparePen(this.pdfContentByte, ellipse.getLinePen(), PdfContentByte.LINE_CAP_PROJECTING_SQUARE);

		float lineWidth = ellipse.getLinePen().getLineWidth().floatValue();
		float lineOffset = BorderOffset.getOffset(ellipse.getLinePen());
		
		if (ellipse.getMode() == JRElement.MODE_OPAQUE)
		{
			this.pdfContentByte.ellipse(
				ellipse.getX() + getOffsetX(),
				this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() - ellipse.getHeight(),
				ellipse.getX() + getOffsetX() + ellipse.getWidth(),
				this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY()
				);

			this.pdfContentByte.fill();
		}

		if (lineWidth > 0f)
		{
			if (ellipse.getLinePen().getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				this.pdfContentByte.ellipse(
					ellipse.getX() + getOffsetX() - lineWidth / 3,
					this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() - ellipse.getHeight() - lineWidth / 3,
					ellipse.getX() + getOffsetX() + ellipse.getWidth() + lineWidth / 3,
					this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() + lineWidth / 3
					);

				this.pdfContentByte.stroke();

				this.pdfContentByte.ellipse(
					ellipse.getX() + getOffsetX() + lineWidth / 3,
					this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() - ellipse.getHeight() + lineWidth / 3,
					ellipse.getX() + getOffsetX() + ellipse.getWidth() - lineWidth / 3,
					this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() - lineWidth / 3
					);

				this.pdfContentByte.stroke();
			}
			else
			{
				this.pdfContentByte.ellipse(
					ellipse.getX() + getOffsetX() + lineOffset,
					this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() - ellipse.getHeight() + lineOffset,
					ellipse.getX() + getOffsetX() + ellipse.getWidth() - lineOffset,
					this.jasperPrint.getPageHeight() - ellipse.getY() - getOffsetY() - lineOffset
					);

				this.pdfContentByte.stroke();
			}
		}

		this.pdfContentByte.setLineDash(0f);
	}


	/**
	 *
	 */
	protected void exportImage(JRPrintImage printImage) throws DocumentException, IOException,  JRException
	{
		if (printImage.getMode() == JRElement.MODE_OPAQUE)
		{
			this.pdfContentByte.setRGBColorFill(
				printImage.getBackcolor().getRed(),
				printImage.getBackcolor().getGreen(),
				printImage.getBackcolor().getBlue()
				);
			this.pdfContentByte.rectangle(
				printImage.getX() + getOffsetX(),
				this.jasperPrint.getPageHeight() - printImage.getY() - getOffsetY(),
				printImage.getWidth(),
				- printImage.getHeight()
				);
			this.pdfContentByte.fill();
		}

		int topPadding = printImage.getLineBox().getTopPadding().intValue();
		int leftPadding = printImage.getLineBox().getLeftPadding().intValue();
		int bottomPadding = printImage.getLineBox().getBottomPadding().intValue();
		int rightPadding = printImage.getLineBox().getRightPadding().intValue();

		int availableImageWidth = printImage.getWidth() - leftPadding - rightPadding;
		availableImageWidth = (availableImageWidth < 0)?0:availableImageWidth;

		int availableImageHeight = printImage.getHeight() - topPadding - bottomPadding;
		availableImageHeight = (availableImageHeight < 0)?0:availableImageHeight;

		JRRenderable renderer = printImage.getRenderer();

		if (
			renderer != null &&
			availableImageWidth > 0 &&
			availableImageHeight > 0
			)
		{
			if (renderer.getType() == JRRenderable.TYPE_IMAGE)
			{
				// Image renderers are all asked for their image data at some point. 
				// Better to test and replace the renderer now, in case of lazy load error.
				renderer = JRImageRenderer.getOnErrorRendererForImageData(renderer, printImage.getOnErrorType());
			}
		}
		else
		{
			renderer = null;
		}

		if (renderer != null)
		{
			int xoffset = 0;
			int yoffset = 0;

			Chunk chunk = null;

			float scaledWidth = availableImageWidth;
			float scaledHeight = availableImageHeight;

			if (renderer.getType() == JRRenderable.TYPE_IMAGE)
			{
				com.lowagie.text.Image image = null;

				float xalignFactor = getXAlignFactor(printImage);
				float yalignFactor = getYAlignFactor(printImage);

				switch(printImage.getScaleImage())
				{
					case JRImage.SCALE_IMAGE_CLIP :
					{
						// Image load might fail, from given image data. 
						// Better to test and replace the renderer now, in case of lazy load error.
						renderer = 
							JRImageRenderer.getOnErrorRendererForDimension(
								renderer, 
								printImage.getOnErrorType()
								);
						if (renderer == null)
						{
							break;
						}
						
						int normalWidth = availableImageWidth;
						int normalHeight = availableImageHeight;

						Dimension2D dimension = renderer.getDimension();
						if (dimension != null)
						{
							normalWidth = (int)dimension.getWidth();
							normalHeight = (int)dimension.getHeight();
						}

						xoffset = (int)(xalignFactor * (availableImageWidth - normalWidth));
						yoffset = (int)(yalignFactor * (availableImageHeight - normalHeight));

						int minWidth = Math.min(normalWidth, availableImageWidth);
						int minHeight = Math.min(normalHeight, availableImageHeight);

						BufferedImage bi =
							new BufferedImage(minWidth, minHeight, BufferedImage.TYPE_INT_ARGB);

						Graphics2D g = bi.createGraphics();
						if (printImage.getMode() == JRElement.MODE_OPAQUE)
						{
							g.setColor(printImage.getBackcolor());
							g.fillRect(0, 0, minWidth, minHeight);
						}
						renderer.render(
							g,
							new java.awt.Rectangle(
								(xoffset > 0 ? 0 : xoffset),
								(yoffset > 0 ? 0 : yoffset),
								normalWidth,
								normalHeight
								)
							);
						g.dispose();

						xoffset = (xoffset < 0 ? 0 : xoffset);
						yoffset = (yoffset < 0 ? 0 : yoffset);

						//awtImage = bi.getSubimage(0, 0, minWidth, minHeight);

						//image = com.lowagie.text.Image.getInstance(awtImage, printImage.getBackcolor());
						image = com.lowagie.text.Image.getInstance(bi, null);

						break;
					}
					case JRImage.SCALE_IMAGE_FILL_FRAME :
					{
						if (printImage.isUsingCache() && this.loadedImagesMap.containsKey(renderer))
						{
							image = (com.lowagie.text.Image)this.loadedImagesMap.get(renderer);
						}
						else
						{
							try
							{
								image = com.lowagie.text.Image.getInstance(renderer.getImageData());
								this.imageTesterPdfContentByte.addImage(image, 10, 0, 0, 10, 0, 0);
							}
							catch(Exception e)
							{
								JRImageRenderer tmpRenderer = 
									JRImageRenderer.getOnErrorRendererForImage(
										JRImageRenderer.getInstance(renderer.getImageData()), 
										printImage.getOnErrorType()
										);
								if (tmpRenderer == null)
								{
									break;
								}
								java.awt.Image awtImage = tmpRenderer.getImage();
								image = com.lowagie.text.Image.getInstance(awtImage, null);
							}

							if (printImage.isUsingCache())
							{
								this.loadedImagesMap.put(renderer, image);
							}
						}

						image.scaleAbsolute(availableImageWidth, availableImageHeight);
						break;
					}
					case JRImage.SCALE_IMAGE_RETAIN_SHAPE :
					default :
					{
						if (printImage.isUsingCache() && this.loadedImagesMap.containsKey(renderer))
						{
							image = (com.lowagie.text.Image)this.loadedImagesMap.get(renderer);
						}
						else
						{
							try
							{
								image = com.lowagie.text.Image.getInstance(renderer.getImageData());
								this.imageTesterPdfContentByte.addImage(image, 10, 0, 0, 10, 0, 0);
							}
							catch(Exception e)
							{
								JRImageRenderer tmpRenderer = 
									JRImageRenderer.getOnErrorRendererForImage(
										JRImageRenderer.getInstance(renderer.getImageData()), 
										printImage.getOnErrorType()
										);
								if (tmpRenderer == null)
								{
									break;
								}
								java.awt.Image awtImage = tmpRenderer.getImage();
								image = com.lowagie.text.Image.getInstance(awtImage, null);
							}

							if (printImage.isUsingCache())
							{
								this.loadedImagesMap.put(renderer, image);
							}
						}

						image.scaleToFit(availableImageWidth, availableImageHeight);

						xoffset = (int)(xalignFactor * (availableImageWidth - image.getPlainWidth()));
						yoffset = (int)(yalignFactor * (availableImageHeight - image.getPlainHeight()));

						xoffset = (xoffset < 0 ? 0 : xoffset);
						yoffset = (yoffset < 0 ? 0 : yoffset);

						break;
					}
				}

				if (image != null)
				{
					chunk = new Chunk(image, 0, 0);

					scaledWidth = image.getScaledWidth();
					scaledHeight = image.getScaledHeight();
				}
			}
			else
			{
				double normalWidth = availableImageWidth;
				double normalHeight = availableImageHeight;

				double displayWidth = availableImageWidth;
				double displayHeight = availableImageHeight;

				double ratioX = 1f;
				double ratioY = 1f;
				
				Rectangle2D clip = null;

				Dimension2D dimension = renderer.getDimension();
				if (dimension != null)
				{
					normalWidth = dimension.getWidth();
					normalHeight = dimension.getHeight();
					displayWidth = normalWidth;
					displayHeight = normalHeight;
					
					float xalignFactor = getXAlignFactor(printImage);
					float yalignFactor = getYAlignFactor(printImage);

					switch (printImage.getScaleImage())
					{
						case JRImage.SCALE_IMAGE_CLIP:
						{
							xoffset = (int) (xalignFactor * (availableImageWidth - normalWidth));
							yoffset = (int) (yalignFactor * (availableImageHeight - normalHeight));
							clip =
								new Rectangle2D.Double(
									- xoffset,
									- yoffset,
									availableImageWidth,
									availableImageHeight
									);
							break;
						}
						case JRImage.SCALE_IMAGE_FILL_FRAME:
						{
							ratioX = availableImageWidth / normalWidth;
							ratioY = availableImageHeight / normalHeight;
							normalWidth *= ratioX;
							normalHeight *= ratioY;
							xoffset = 0;
							yoffset = 0;
							break;
						}
						case JRImage.SCALE_IMAGE_RETAIN_SHAPE:
						default:
						{
							ratioX = availableImageWidth / normalWidth;
							ratioY = availableImageHeight / normalHeight;
							ratioX = ratioX < ratioY ? ratioX : ratioY;
							ratioY = ratioX;
							normalWidth *= ratioX;
							normalHeight *= ratioY;
							xoffset = (int) (xalignFactor * (availableImageWidth - normalWidth));
							yoffset = (int) (yalignFactor * (availableImageHeight - normalHeight));
							break;
						}
					}
				}

				PdfTemplate template = this.pdfContentByte.createTemplate((float)displayWidth, (float)displayHeight);

				Graphics2D g = this.forceSvgShapes
					? template.createGraphicsShapes((float)displayWidth, (float)displayHeight)
					: template.createGraphics(availableImageWidth, availableImageHeight, new LocalFontMapper());

				if (clip != null)
				{
					g.setClip(clip);
				}
				
				if (printImage.getMode() == JRElement.MODE_OPAQUE)
				{
					g.setColor(printImage.getBackcolor());
					g.fillRect(0, 0, (int)displayWidth, (int)displayHeight);
				}

				Rectangle2D rectangle = new Rectangle2D.Double(0, 0, displayWidth, displayHeight);

				renderer.render(g, rectangle);
				g.dispose();

				this.pdfContentByte.saveState();
				this.pdfContentByte.addTemplate(
					template,
					(float)ratioX, 0f, 0f, (float)ratioY,
					printImage.getX() + getOffsetX() + xoffset,
					this.jasperPrint.getPageHeight()
						- printImage.getY() - getOffsetY()
						- (int)normalHeight
						- yoffset
					);
				this.pdfContentByte.restoreState();

				Image image = getPxImage();
				image.scaleAbsolute(availableImageWidth, availableImageHeight);
				chunk = new Chunk(image, 0, 0);
			}

			/*
			image.setAbsolutePosition(
				printImage.getX() + offsetX + borderOffset,
				jasperPrint.getPageHeight() - printImage.getY() - offsetY - image.scaledHeight() - borderOffset
				);

			pdfContentByte.addImage(image);
			*/


			if (chunk != null)
			{
				setAnchor(chunk, printImage, printImage);
				setHyperlinkInfo(chunk, printImage);

				ColumnText colText = new ColumnText(this.pdfContentByte);
				int upperY = this.jasperPrint.getPageHeight() - printImage.getY() - topPadding - getOffsetY() - yoffset;
				int lowerX = printImage.getX() + leftPadding + getOffsetX() + xoffset;
				colText.setSimpleColumn(
					new Phrase(chunk),
					lowerX,
					upperY - scaledHeight,
					lowerX + scaledWidth,
					upperY,
					scaledHeight,
					Element.ALIGN_LEFT
					);

				colText.go();
			}
		}


		if (
			printImage.getLineBox().getTopPen().getLineWidth().floatValue() <= 0f &&
			printImage.getLineBox().getLeftPen().getLineWidth().floatValue() <= 0f &&
			printImage.getLineBox().getBottomPen().getLineWidth().floatValue() <= 0f &&
			printImage.getLineBox().getRightPen().getLineWidth().floatValue() <= 0f
			)
		{
			if (printImage.getLinePen().getLineWidth().floatValue() > 0f)
			{
				exportPen(printImage.getLinePen(), printImage);
			}
		}
		else
		{
			/*   */
			exportBox(
				printImage.getLineBox(),
				printImage
				);
		}
	}


	private float getXAlignFactor(JRPrintImage printImage)
	{
		float xalignFactor = 0f;
		switch (printImage.getHorizontalAlignment())
		{
			case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
			{
				xalignFactor = 1f;
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_CENTER :
			{
				xalignFactor = 0.5f;
				break;
			}
			case JRAlignment.HORIZONTAL_ALIGN_LEFT :
			default :
			{
				xalignFactor = 0f;
				break;
			}
		}
		return xalignFactor;
	}


	private float getYAlignFactor(JRPrintImage printImage)
	{
		float yalignFactor = 0f;
		switch (printImage.getVerticalAlignment())
		{
			case JRAlignment.VERTICAL_ALIGN_BOTTOM :
			{
				yalignFactor = 1f;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_MIDDLE :
			{
				yalignFactor = 0.5f;
				break;
			}
			case JRAlignment.VERTICAL_ALIGN_TOP :
			default :
			{
				yalignFactor = 0f;
				break;
			}
		}
		return yalignFactor;
	}


	/**
	 *
	 */
	protected void setHyperlinkInfo(Chunk chunk, JRPrintHyperlink link)
	{
		switch(link.getHyperlinkType())
		{
			case JRHyperlink.HYPERLINK_TYPE_REFERENCE :
			{
				if (link.getHyperlinkReference() != null)
				{
					switch(link.getHyperlinkTarget())
					{
						case JRHyperlink.HYPERLINK_TARGET_BLANK :
						{
							chunk.setAction(
								PdfAction.javaScript(
									"if (app.viewerVersion < 7)"
										+ "{this.getURL(\"" + link.getHyperlinkReference() + "\");}"
										+ "else {app.launchURL(\"" + link.getHyperlinkReference() + "\", true);};",
									this.pdfWriter
									)
								);
							break;
						}
						case JRHyperlink.HYPERLINK_TARGET_SELF :
						default :
						{
							chunk.setAnchor(link.getHyperlinkReference());
							break;
						}
					}
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR :
			{
				if (link.getHyperlinkAnchor() != null)
				{
					chunk.setLocalGoto(link.getHyperlinkAnchor());
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE :
			{
				if (link.getHyperlinkPage() != null)
				{
					chunk.setLocalGoto(JR_PAGE_ANCHOR_PREFIX + this.reportIndex + "_" + link.getHyperlinkPage().toString());
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
					chunk.setRemoteGoto(
						link.getHyperlinkReference(),
						link.getHyperlinkAnchor()
						);
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
					chunk.setRemoteGoto(
						link.getHyperlinkReference(),
						link.getHyperlinkPage().intValue()
						);
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_CUSTOM :
			{
				if (this.hyperlinkProducerFactory != null)
				{
					String hyperlink = this.hyperlinkProducerFactory.produceHyperlink(link);
					if (hyperlink != null)
					{
						switch(link.getHyperlinkTarget())
						{
							case JRHyperlink.HYPERLINK_TARGET_BLANK :
							{
								chunk.setAction(
									PdfAction.javaScript(
										"if (app.viewerVersion < 7)"
											+ "{this.getURL(\"" + hyperlink + "\");}"
											+ "else {app.launchURL(\"" + hyperlink + "\", true);};",
										this.pdfWriter
										)
									);
								break;
							}
							case JRHyperlink.HYPERLINK_TARGET_SELF :
							default :
							{
								chunk.setAnchor(hyperlink);
								break;
							}
						}
					}
				}
			}
			case JRHyperlink.HYPERLINK_TYPE_NONE :
			default :
			{
				break;
			}
		}
	}


	/**
	 *
	 */
	protected Phrase getPhrase(JRStyledText styledText, JRPrintText textElement)
	{
		Phrase phrase = new Phrase();

		String text = styledText.getText();

		int runLimit = 0;

		AttributedCharacterIterator iterator = styledText.getAttributedString().getIterator();

		while(runLimit < styledText.length() && (runLimit = iterator.getRunLimit()) <= styledText.length())
		{
			Chunk chunk = getChunk(iterator.getAttributes(), text.substring(iterator.getIndex(), runLimit));
			setAnchor(chunk, textElement, textElement);
			setHyperlinkInfo(chunk, textElement);
			phrase.add(chunk);

			iterator.setIndex(runLimit);
		}

		return phrase;
	}


	/**
	 *
	 */
	protected Chunk getChunk(Map attributes, String text)
	{
		Font font = getFont(attributes);

		Chunk chunk = new Chunk(text, font);

		Color backcolor = (Color)attributes.get(TextAttribute.BACKGROUND);
		if (backcolor != null)
		{
			chunk.setBackground(backcolor);
		}

		Object script = attributes.get(TextAttribute.SUPERSCRIPT);
		if (script != null)
		{
			if (TextAttribute.SUPERSCRIPT_SUPER.equals(script))
			{
				chunk.setTextRise(font.getCalculatedLeading(1f)/2);
			}
			else if (script != null && TextAttribute.SUPERSCRIPT_SUB.equals(script))
			{
				chunk.setTextRise(-font.getCalculatedLeading(1f)/2);
			}
		}

		if (this.splitCharacter != null)
		{
			chunk.setSplitCharacter(this.splitCharacter);
		}

		return chunk;
	}


	/**
	 *
	 */
	protected Font getFont(Map attributes)
	{
		JRFont jrFont = new JRBaseFont(attributes);

		Exception initialException = null;

		Color forecolor = (Color)attributes.get(TextAttribute.FOREGROUND);

		Font font = null;
		PdfFont pdfFont = null;
		FontKey key = new FontKey(jrFont.getFontName(), jrFont.isBold(), jrFont.isItalic());

		if (this.fontMap != null && this.fontMap.containsKey(key))
		{
			pdfFont = (PdfFont) this.fontMap.get(key);
		}
		else
		{
			pdfFont = new PdfFont(jrFont.getPdfFontName(), jrFont.getPdfEncoding(), jrFont.isPdfEmbedded());
		}

		try
		{
			font = FontFactory.getFont(
				pdfFont.getPdfFontName(),
				pdfFont.getPdfEncoding(),
				pdfFont.isPdfEmbedded(),
				jrFont.getFontSize(),
				(pdfFont.isPdfSimulatedBold() ? Font.BOLD : 0)
					| (pdfFont.isPdfSimulatedItalic() ? Font.ITALIC : 0)
					| (jrFont.isUnderline() ? Font.UNDERLINE : 0)
					| (jrFont.isStrikeThrough() ? Font.STRIKETHRU : 0),
				forecolor
				);

			// check if FontFactory didn't find the font
			if (font.getBaseFont() == null && font.getFamily() == Font.UNDEFINED)
			{
				font = null;
			}
		}
		catch(Exception e)
		{
			initialException = e;
		}

		if (font == null)
		{
			byte[] bytes = null;

			try
			{
				bytes = JRLoader.loadBytesFromLocation(pdfFont.getPdfFontName(), this.classLoader, this.urlHandlerFactory, this.fileResolver);
			}
			catch(JRException e)
			{
				throw
					new JRRuntimeException(
						"Could not load the following font : "
						+ "\npdfFontName   : " + pdfFont.getPdfFontName()
						+ "\npdfEncoding   : " + pdfFont.getPdfEncoding()
						+ "\nisPdfEmbedded : " + pdfFont.isPdfEmbedded(),
						initialException
						);
			}

			BaseFont baseFont = null;

			try
			{
				baseFont =
					BaseFont.createFont(
						pdfFont.getPdfFontName(),
						pdfFont.getPdfEncoding(),
						pdfFont.isPdfEmbedded(),
						true,
						bytes,
						null
						);
			}
			catch(DocumentException e)
			{
				throw new JRRuntimeException(e);
			}
			catch(IOException e)
			{
				throw new JRRuntimeException(e);
			}

			font =
				new Font(
					baseFont,
					jrFont.getFontSize(),
					((pdfFont.isPdfSimulatedBold()) ? Font.BOLD : 0)
						| ((pdfFont.isPdfSimulatedItalic()) ? Font.ITALIC : 0)
						| (jrFont.isUnderline() ? Font.UNDERLINE : 0)
						| (jrFont.isStrikeThrough() ? Font.STRIKETHRU : 0),
					forecolor
					);
		}

		return font;
	}


	/**
	 *
	 */
	protected void exportText(JRPrintText text) throws DocumentException
	{
		JRStyledText styledText = getStyledText(text, false);

		if (styledText == null)
		{
			return;
		}

		int textLength = styledText.length();

		int x = text.getX() + getOffsetX();
		int y = text.getY() + getOffsetY();
		int width = text.getWidth();
		int height = text.getHeight();
		int topPadding = text.getLineBox().getTopPadding().intValue();
		int leftPadding = text.getLineBox().getLeftPadding().intValue();
		int bottomPadding = text.getLineBox().getBottomPadding().intValue();
		int rightPadding = text.getLineBox().getRightPadding().intValue();

		int xFillCorrection = 0;
		int yFillCorrection = 0;

		double angle = 0;

		switch (text.getRotation())
		{
			case JRTextElement.ROTATION_LEFT :
			{
				y = text.getY() + getOffsetY() + text.getHeight();
				xFillCorrection = 1;
				width = text.getHeight();
				height = text.getWidth();
				int tmpPadding = topPadding;
				topPadding = leftPadding;
				leftPadding = bottomPadding;
				bottomPadding = rightPadding;
				rightPadding = tmpPadding;
				angle = Math.PI / 2;
				break;
			}
			case JRTextElement.ROTATION_RIGHT :
			{
				x = text.getX() + getOffsetX() + text.getWidth();
				yFillCorrection = -1;
				width = text.getHeight();
				height = text.getWidth();
				int tmpPadding = topPadding;
				topPadding = rightPadding;
				rightPadding = bottomPadding;
				bottomPadding = leftPadding;
				leftPadding = tmpPadding;
				angle = - Math.PI / 2;
				break;
			}
			case JRTextElement.ROTATION_UPSIDE_DOWN :
			{
				x = text.getX() + getOffsetX() + text.getWidth();
				y = text.getY() + getOffsetY() + text.getHeight();
				int tmpPadding = topPadding;
				topPadding = bottomPadding;
				bottomPadding = tmpPadding;
				tmpPadding = leftPadding;
				leftPadding = rightPadding;
				rightPadding = tmpPadding;
				angle = Math.PI;
				break;
			}
			case JRTextElement.ROTATION_NONE :
			default :
			{
			}
		}

		AffineTransform atrans = new AffineTransform();
		atrans.rotate(angle, x, this.jasperPrint.getPageHeight() - y);
		this.pdfContentByte.transform(atrans);

		if (text.getMode() == JRElement.MODE_OPAQUE)
		{
			Color backcolor = text.getBackcolor();
			this.pdfContentByte.setRGBColorFill(
				backcolor.getRed(),
				backcolor.getGreen(),
				backcolor.getBlue()
				);
			this.pdfContentByte.rectangle(
				x + xFillCorrection,
				this.jasperPrint.getPageHeight() - y + yFillCorrection,
				width,
				- height
				);
			this.pdfContentByte.fill();
		}

		if (textLength > 0)
		{
			int horizontalAlignment = Element.ALIGN_LEFT;
			switch (text.getHorizontalAlignment())
			{
				case JRAlignment.HORIZONTAL_ALIGN_LEFT :
				{
					if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR)
					{
						horizontalAlignment = Element.ALIGN_LEFT;
					}
					else
					{
						horizontalAlignment = Element.ALIGN_RIGHT;
					}
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_CENTER :
				{
					horizontalAlignment = Element.ALIGN_CENTER;
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
				{
					if (text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR)
					{
						horizontalAlignment = Element.ALIGN_RIGHT;
					}
					else
					{
						horizontalAlignment = Element.ALIGN_LEFT;
					}
					break;
				}
				case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
				{
					horizontalAlignment = Element.ALIGN_JUSTIFIED;
					break;
				}
				default :
				{
					horizontalAlignment = Element.ALIGN_LEFT;
				}
			}

			float verticalOffset = 0f;
			switch (text.getVerticalAlignment())
			{
				case JRAlignment.VERTICAL_ALIGN_TOP :
				{
					verticalOffset = 0f;
					break;
				}
				case JRAlignment.VERTICAL_ALIGN_MIDDLE :
				{
					verticalOffset = (height - topPadding - bottomPadding - text.getTextHeight()) / 2f;
					break;
				}
				case JRAlignment.VERTICAL_ALIGN_BOTTOM :
				{
					verticalOffset = height - topPadding - bottomPadding - text.getTextHeight();
					break;
				}
				default :
				{
					verticalOffset = 0f;
				}
			}

			ColumnText colText = new ColumnText(this.pdfContentByte);
			colText.setSimpleColumn(
				getPhrase(styledText, text),
				x + leftPadding,
				this.jasperPrint.getPageHeight()
					- y
					- topPadding
					- verticalOffset
					- text.getLeadingOffset(),
					//+ text.getLineSpacingFactor() * text.getFont().getSize(),
				x + width - rightPadding,
				this.jasperPrint.getPageHeight()
					- y
					- height
					+ bottomPadding,
				0,//text.getLineSpacingFactor(),// * text.getFont().getSize(),
				horizontalAlignment
				);

			colText.setLeading(0, text.getLineSpacingFactor());// * text.getFont().getSize());
			colText.setRunDirection(
				text.getRunDirection() == JRPrintText.RUN_DIRECTION_LTR
				? PdfWriter.RUN_DIRECTION_LTR : PdfWriter.RUN_DIRECTION_RTL
				);

			colText.go();
		}

		atrans = new AffineTransform();
		atrans.rotate(-angle, x, this.jasperPrint.getPageHeight() - y);
		this.pdfContentByte.transform(atrans);

		/*   */
		exportBox(
			text.getLineBox(),
			text
			);
	}


	/**
	 *
	 */
	protected void exportBox(JRLineBox box, JRPrintElement element)
	{
		exportTopPen(box.getTopPen(), box.getLeftPen(), box.getRightPen(), element);
		exportLeftPen(box.getTopPen(), box.getLeftPen(), box.getBottomPen(), element);
		exportBottomPen(box.getLeftPen(), box.getBottomPen(), box.getRightPen(), element);
		exportRightPen(box.getTopPen(), box.getBottomPen(), box.getRightPen(), element);

		this.pdfContentByte.setLineDash(0f);
		this.pdfContentByte.setLineCap(PdfContentByte.LINE_CAP_PROJECTING_SQUARE);
	}


	/**
	 *
	 */
	protected void exportPen(JRPen pen, JRPrintElement element)
	{
		exportTopPen(pen, pen, pen, element);
		exportLeftPen(pen, pen, pen, element);
		exportBottomPen(pen, pen, pen, element);
		exportRightPen(pen, pen, pen, element);

		this.pdfContentByte.setLineDash(0f);
		this.pdfContentByte.setLineCap(PdfContentByte.LINE_CAP_PROJECTING_SQUARE);
	}


	/**
	 *
	 */
	protected void exportTopPen(
		JRPen topPen, 
		JRPen leftPen, 
		JRPen rightPen, 
		JRPrintElement element)
	{
		if (topPen.getLineWidth().floatValue() > 0f)
		{
			float leftOffset = leftPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(leftPen);
			float rightOffset = rightPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(rightPen);
			
			preparePen(this.pdfContentByte, topPen, PdfContentByte.LINE_CAP_BUTT);
			
			if (topPen.getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				float topOffset = topPen.getLineWidth().floatValue();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() - leftOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() + topOffset / 3
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() + rightOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() + topOffset / 3
					);
				this.pdfContentByte.stroke();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + leftOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - topOffset / 3
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() - rightOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - topOffset / 3
					);
				this.pdfContentByte.stroke();
			}
			else
			{
				float topOffset =  BorderOffset.getOffset(topPen);
				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() - leftOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - topOffset
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() + rightOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - topOffset
					);
				this.pdfContentByte.stroke();
			}
		}
	}


	/**
	 *
	 */
	protected void exportLeftPen(JRPen topPen, JRPen leftPen, JRPen bottomPen, JRPrintElement element)
	{
		if (leftPen.getLineWidth().floatValue() > 0f)
		{
			float topOffset = topPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(topPen);
			float bottomOffset = bottomPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(bottomPen);

			preparePen(this.pdfContentByte, leftPen, PdfContentByte.LINE_CAP_BUTT);

			if (leftPen.getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				float leftOffset = leftPen.getLineWidth().floatValue();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() - leftOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() + topOffset
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() - leftOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() - bottomOffset
					);
				this.pdfContentByte.stroke();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + leftOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - topOffset / 3
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + leftOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() + bottomOffset / 3
					);
				this.pdfContentByte.stroke();
			}
			else
			{
				float leftOffset =  BorderOffset.getOffset(leftPen);
				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + leftOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() + topOffset
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + leftOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() - bottomOffset
					);
				this.pdfContentByte.stroke();
			}
		}
	}


	/**
	 *
	 */
	protected void exportBottomPen(JRPen leftPen, JRPen bottomPen, JRPen rightPen, JRPrintElement element)
	{
		if (bottomPen.getLineWidth().floatValue() > 0f)
		{
			float leftOffset = leftPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(leftPen);
			float rightOffset = rightPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(rightPen);
			
			preparePen(this.pdfContentByte, bottomPen, PdfContentByte.LINE_CAP_BUTT);
			
			if (bottomPen.getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				float bottomOffset = bottomPen.getLineWidth().floatValue();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() - leftOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() - bottomOffset / 3
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() + rightOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() - bottomOffset / 3
					);
				this.pdfContentByte.stroke();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + leftOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() + bottomOffset / 3
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() - rightOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() + bottomOffset / 3
					);
				this.pdfContentByte.stroke();
			}
			else
			{
				float bottomOffset =  BorderOffset.getOffset(bottomPen);
				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() - leftOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() + bottomOffset
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() + rightOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() + bottomOffset
					);
				this.pdfContentByte.stroke();
			}
		}
	}


	/**
	 *
	 */
	protected void exportRightPen(JRPen topPen, JRPen bottomPen, JRPen rightPen, JRPrintElement element)
	{
		if (rightPen.getLineWidth().floatValue() > 0f)
		{
			float topOffset = topPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(topPen);
			float bottomOffset = bottomPen.getLineWidth().floatValue() / 2 - BorderOffset.getOffset(bottomPen);

			preparePen(this.pdfContentByte, rightPen, PdfContentByte.LINE_CAP_BUTT);

			if (rightPen.getLineStyle().byteValue() == JRPen.LINE_STYLE_DOUBLE)
			{
				float rightOffset = rightPen.getLineWidth().floatValue();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + element.getWidth() + rightOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() + topOffset
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() + rightOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() - bottomOffset
					);
				this.pdfContentByte.stroke();

				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + element.getWidth() - rightOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - topOffset / 3
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() - rightOffset / 3,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() + bottomOffset / 3
					);
				this.pdfContentByte.stroke();
			}
			else
			{
				float rightOffset =  BorderOffset.getOffset(rightPen);
				this.pdfContentByte.moveTo(
					element.getX() + getOffsetX() + element.getWidth() - rightOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() + topOffset
					);
				this.pdfContentByte.lineTo(
					element.getX() + getOffsetX() + element.getWidth() - rightOffset,
					this.jasperPrint.getPageHeight() - element.getY() - getOffsetY() - element.getHeight() - bottomOffset
					);
				this.pdfContentByte.stroke();
			}
		}
	}


	/**
	 *
	 */
	private static void preparePen(PdfContentByte pdfContentByte, JRPen pen, int lineCap)
	{
		float lineWidth = pen.getLineWidth().floatValue();

		if (lineWidth <= 0)
		{
			return;
		}
		
		pdfContentByte.setLineWidth(lineWidth);
		pdfContentByte.setLineCap(lineCap);

		Color color = pen.getLineColor();
		pdfContentByte.setRGBColorStroke(
			color.getRed(),
			color.getGreen(),
			color.getBlue()
			);

		switch (pen.getLineStyle().byteValue())
		{
			case JRPen.LINE_STYLE_DOUBLE :
			{
				pdfContentByte.setLineWidth(lineWidth / 3);
				pdfContentByte.setLineDash(0f);
				break;
			}
			case JRPen.LINE_STYLE_DOTTED :
			{
				switch (lineCap)
				{
					case PdfContentByte.LINE_CAP_BUTT :
					{
						pdfContentByte.setLineDash(lineWidth, lineWidth, 0f);
						break;
					}
					case PdfContentByte.LINE_CAP_PROJECTING_SQUARE :
					{
						pdfContentByte.setLineDash(0, 2 * lineWidth, 0f);
						break;
					}
				}
				break;
			}
			case JRPen.LINE_STYLE_DASHED :
			{
				switch (lineCap)
				{
					case PdfContentByte.LINE_CAP_BUTT :
					{
						pdfContentByte.setLineDash(5 * lineWidth, 3 * lineWidth, 0f);
						break;
					}
					case PdfContentByte.LINE_CAP_PROJECTING_SQUARE :
					{
						pdfContentByte.setLineDash(4 * lineWidth, 4 * lineWidth, 0f);
						break;
					}
				}
				break;
			}
			case JRPen.LINE_STYLE_SOLID :
			default :
			{
				pdfContentByte.setLineDash(0f);
				break;
			}
		}
	}


	protected static synchronized void registerFonts ()
	{
		if (!fontsRegistered)
		{
			List fontFiles = JRProperties.getProperties(JRProperties.PDF_FONT_FILES_PREFIX);
			if (!fontFiles.isEmpty())
			{
				for (Iterator i = fontFiles.iterator(); i.hasNext();)
				{
					JRProperties.PropertySuffix font = (JRProperties.PropertySuffix) i.next();
					String file = font.getValue();
					if (file.toLowerCase().endsWith(".ttc"))
					{
						FontFactory.register(file);
					}
					else
					{
						String alias = font.getSuffix();
						FontFactory.register(file, alias);
					}
				}
			}

			List fontDirs = JRProperties.getProperties(JRProperties.PDF_FONT_DIRS_PREFIX);
			if (!fontDirs.isEmpty())
			{
				for (Iterator i = fontDirs.iterator(); i.hasNext();)
				{
					JRProperties.PropertySuffix dir = (JRProperties.PropertySuffix) i.next();
					FontFactory.registerDirectory(dir.getValue());
				}
			}

			fontsRegistered = true;
		}
	}


	static protected class Bookmark
	{
		final PdfOutline pdfOutline;
		final int level;

		Bookmark(Bookmark parent, int x, int top, String title)
		{
			this(parent, new PdfDestination(PdfDestination.XYZ, x, top, 0), title);
		}

		Bookmark(Bookmark parent, PdfDestination destination, String title)
		{
			this.pdfOutline = new PdfOutline(parent.pdfOutline, destination, title, false);
			this.level = parent.level + 1;
		}

		Bookmark(PdfOutline pdfOutline, int level)
		{
			this.pdfOutline = pdfOutline;
			this.level = level;
		}
	}

	static protected class BookmarkStack
	{
		LinkedList stack;

		BookmarkStack()
		{
			this.stack = new LinkedList();
		}

		void push(Bookmark bookmark)
		{
			this.stack.add(bookmark);
		}

		Bookmark pop()
		{
			return (Bookmark) this.stack.removeLast();
		}

		Bookmark peek()
		{
			return (Bookmark) this.stack.getLast();
		}
	}


	protected void initBookmarks()
	{
		this.bookmarkStack = new BookmarkStack();

		int rootLevel = this.isModeBatch && this.isCreatingBatchModeBookmarks ? -1 : 0;
		Bookmark bookmark = new Bookmark(this.pdfContentByte.getRootOutline(), rootLevel);
		this.bookmarkStack.push(bookmark);
	}


	protected void addBookmark(int level, String title, int x, int y)
	{
		Bookmark parent = this.bookmarkStack.peek();
		while(parent.level > level - 1)
		{
			this.bookmarkStack.pop();
			parent = this.bookmarkStack.peek();
		}

		for (int i = parent.level + 1; i < level; ++i)
		{
			Bookmark emptyBookmark = new Bookmark(parent, parent.pdfOutline.getPdfDestination(), EMPTY_BOOKMARK_TITLE);
			this.bookmarkStack.push(emptyBookmark);
			parent = emptyBookmark;
		}

		Bookmark bookmark = new Bookmark(parent, x, this.jasperPrint.getPageHeight() - y, title);
		this.bookmarkStack.push(bookmark);
	}


	protected void setAnchor(Chunk chunk, JRPrintAnchor anchor, JRPrintElement element)
	{
		String anchorName = anchor.getAnchorName();
		if (anchorName != null)
		{
			chunk.setLocalDestination(anchorName);

			if (anchor.getBookmarkLevel() != JRAnchor.NO_BOOKMARK)
			{
				addBookmark(anchor.getBookmarkLevel(), anchor.getAnchorName(), element.getX(), element.getY());
			}
		}
	}


	protected void exportFrame(JRPrintFrame frame) throws DocumentException, IOException, JRException
	{
		if (frame.getMode() == JRElement.MODE_OPAQUE)
		{
			int x = frame.getX() + getOffsetX();
			int y = frame.getY() + getOffsetY();

			Color backcolor = frame.getBackcolor();
			this.pdfContentByte.setRGBColorFill(
				backcolor.getRed(),
				backcolor.getGreen(),
				backcolor.getBlue()
				);
			this.pdfContentByte.rectangle(
				x,
				this.jasperPrint.getPageHeight() - y,
				frame.getWidth(),
				- frame.getHeight()
				);
			this.pdfContentByte.fill();
		}

		setFrameElementsOffset(frame, false);
		try
		{
			exportElements(frame.getElements());
		}
		finally
		{
			restoreElementOffsets();
		}

		exportBox(frame.getLineBox(), frame);
	}


	/**
	 * Output stream implementation that discards all the data.
	 */
	public static class NullOutputStream extends OutputStream
	{
		public NullOutputStream()
		{
		}

		public void write(int b)
		{
			// discard the data
		}

		public void write(byte[] b, int off, int len)
		{
			// discard the data
		}

		public void write(byte[] b)
		{
			// discard the data
		}
	}


	/**
	 *
	 */
	class LocalFontMapper implements FontMapper
	{
		public LocalFontMapper()
		{
		}

		public BaseFont awtToPdf(java.awt.Font font)
		{
			return getFont(font.getAttributes()).getBaseFont();
		}

		public java.awt.Font pdfToAwt(BaseFont font, int size)
		{
			return null;
		}
	}
}
