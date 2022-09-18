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
 * Mirko Wawrowsky - mawawrosky@users.sourceforge.net
 */
package net.sf.jasperreports.engine.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRStyledTextAttributeSelector;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyledText;


/**
 * Exports a JasperReports document to CSV format. It has character output type and exports the document to a
 * grid-based layout.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRCsvExporter.java,v 1.1 2008/09/29 16:20:46 guehene Exp $
 */
public class JRCsvExporter extends JRAbstractExporter
{

	private static final String CSV_EXPORTER_PROPERTIES_PREFIX = JRProperties.PROPERTY_PREFIX + "export.csv.";

	/**
	 *
	 */
	protected String delimiter = null;	

	/**
	 *
	 */
	protected String recordDelimiter = null;	

	/**
	 *
	 */
	protected Writer writer = null;
	protected JRExportProgressMonitor progressMonitor = null;

	protected ExporterNature nature = null;

	
	/**
	 *
	 */
	public void exportReport() throws JRException
	{
		this.progressMonitor = (JRExportProgressMonitor)this.parameters.get(JRExporterParameter.PROGRESS_MONITOR);
		
		/*   */
		setOffset();

		/*   */
		setInput();
		
		if (!this.parameters.containsKey(JRExporterParameter.FILTER))
		{
			this.filter = createFilter(CSV_EXPORTER_PROPERTIES_PREFIX);
		}

		/*   */
		if (!this.isModeBatch)
		{
			setPageRange();
		}
		
		this.nature = new JRCsvExporterNature(this.filter);

		String encoding = 
			getStringParameterOrDefault(
				JRExporterParameter.CHARACTER_ENCODING, 
				JRExporterParameter.PROPERTY_CHARACTER_ENCODING
				);
		
		this.delimiter = 
			getStringParameterOrDefault(
				JRCsvExporterParameter.FIELD_DELIMITER, 
				JRCsvExporterParameter.PROPERTY_FIELD_DELIMITER
				);
		
		this.recordDelimiter = 
			getStringParameterOrDefault(
				JRCsvExporterParameter.RECORD_DELIMITER, 
				JRCsvExporterParameter.PROPERTY_RECORD_DELIMITER
				);
		
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
						this.writer = new OutputStreamWriter(os, encoding); 
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
						this.writer = new OutputStreamWriter(os, encoding);
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
	}


	/**
	 *
	 */
	protected void exportReportToWriter() throws JRException, IOException
	{
		for(int reportIndex = 0; reportIndex < this.jasperPrintList.size(); reportIndex++)
		{
			this.jasperPrint = (JasperPrint)this.jasperPrintList.get(reportIndex);

			List pages = this.jasperPrint.getPages();
			if (pages != null && pages.size() > 0)
			{
				if (this.isModeBatch)
				{
					this.startPageIndex = 0;
					this.endPageIndex = pages.size() - 1;
				}

				for(int i = this.startPageIndex; i <= this.endPageIndex; i++)
				{
					if (Thread.currentThread().isInterrupted())
					{
						throw new JRException("Current thread interrupted.");
					}
				
					JRPrintPage page = (JRPrintPage)pages.get(i);

					/*   */
					exportPage(page);
				}
			}
		}
				
		this.writer.flush();
	}


	/**
	 *
	 */
	protected void exportPage(JRPrintPage page) throws IOException
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
		
		JRExporterGridCell[][] grid = layout.getGrid();

		CutsInfo xCuts = layout.getXCuts();
		CutsInfo yCuts = layout.getYCuts();

		StringBuffer rowbuffer = null;
		
		JRPrintElement element = null;
		String text = null;
		boolean isFirstColumn = true;
		for(int y = 0; y < grid.length; y++)
		{
			rowbuffer = new StringBuffer();

			if (yCuts.isCutNotEmpty(y))
			{
				isFirstColumn = true;
				for(int x = 0; x < grid[y].length; x++)
				{
					if(grid[y][x].getWrapper() != null)
					{
						element = grid[y][x].getWrapper().getElement();
	
						if (element instanceof JRPrintText)
						{
							JRStyledText styledText = getStyledText((JRPrintText)element);
							if (styledText == null)
							{
								text = "";
							}
							else
							{
								text = styledText.getText();
							}
							
							if (!isFirstColumn)
							{
								rowbuffer.append(this.delimiter);
							}
							rowbuffer.append(
								prepareText(text)
								);
							isFirstColumn = false;
						}
					}
					else
					{
						if (xCuts.isCutNotEmpty(x))
						{
							if (!isFirstColumn)
							{
								rowbuffer.append(this.delimiter);
							}
							isFirstColumn = false;
						}
					}
				}
				
				if (rowbuffer.length() > 0)
				{
					this.writer.write(rowbuffer.toString());
					this.writer.write(this.recordDelimiter);
				}
			}
		}
		
		if (this.progressMonitor != null)
		{
			this.progressMonitor.afterPageExport();
		}
	}
	
	
	/**
	 *
	 */
	protected JRStyledText getStyledText(JRPrintText textElement)
	{
		return textElement.getFullStyledText(JRStyledTextAttributeSelector.NONE);
	}


	/**
	 *
	 */
	protected String prepareText(String source)
	{
		String str = null;
		
		if (source != null)
		{
			boolean putQuotes = false;
			
			if (
				source.indexOf(this.delimiter) >= 0
				|| source.indexOf(this.recordDelimiter) >= 0
				)
			{
				putQuotes = true;
			}
			
			StringBuffer sbuffer = new StringBuffer();
			StringTokenizer tkzer = new StringTokenizer(source, "\"\n", true);
			String token = null;
			while(tkzer.hasMoreTokens())
			{
				token = tkzer.nextToken();
				if ("\"".equals(token))
				{
					putQuotes = true;
					sbuffer.append("\"\"");
				}
				else if ("\n".equals(token))
				{
					//sbuffer.append(" ");
					putQuotes = true;
					sbuffer.append("\n");
				}
				else
				{
					sbuffer.append(token);
				}
			}
			
			str = sbuffer.toString();
			
			if (putQuotes)
			{
				str = "\"" + str + "\"";
			}
		}
		
		return str;
	}


}
