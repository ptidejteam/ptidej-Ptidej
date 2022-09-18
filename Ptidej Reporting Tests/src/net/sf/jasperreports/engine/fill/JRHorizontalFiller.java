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
package net.sf.jasperreports.engine.fill;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperReport;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRHorizontalFiller.java,v 1.1 2008/09/29 16:20:01 guehene Exp $
 */
public class JRHorizontalFiller extends JRBaseFiller
{

	private static final Log log = LogFactory.getLog(JRHorizontalFiller.class);

	private int lastDetailOffsetX = -1;
	private int lastDetailOffsetY = -1;


	/**
	 *
	 */
	protected JRHorizontalFiller(JasperReport jasperReport) throws JRException
	{
		this(jasperReport, null, null);
	}

	/**
	 *
	 */
	protected JRHorizontalFiller(JasperReport jasperReport, JRBaseFiller parentFiller) throws JRException
	{
		super(jasperReport, null, parentFiller);

		setPageHeight(this.pageHeight);
	}

	/**
	 *
	 */
	protected JRHorizontalFiller(JasperReport jasperReport, JREvaluator evaluator, JRBaseFiller parentFiller) throws JRException
	{
		super(jasperReport, evaluator, parentFiller);

		setPageHeight(this.pageHeight);
	}


	/**
	 *
	 */
	protected void setPageHeight(int pageHeight)
	{
		this.pageHeight = pageHeight;

		this.columnFooterOffsetY = pageHeight - this.bottomMargin;
		if (this.pageFooter != null)
			this.columnFooterOffsetY -= this.pageFooter.getHeight();
		if (this.columnFooter != null)
			this.columnFooterOffsetY -= this.columnFooter.getHeight();

		this.lastPageColumnFooterOffsetY = pageHeight - this.bottomMargin;
		if (this.lastPageFooter != null)
			this.lastPageColumnFooterOffsetY -= this.lastPageFooter.getHeight();
		if (this.columnFooter != null)
			this.lastPageColumnFooterOffsetY -= this.columnFooter.getHeight();
	}


	/**
	 *
	 */
	protected synchronized void fillReport() throws JRException
	{
		setLastPageFooter(false);

		if (next())
		{
			fillReportStart();

			while (next())
			{
				fillReportContent();
			}

			fillReportEnd();
		}
		else
		{
			if (log.isDebugEnabled())
			{
				log.debug("Fill " + this.fillerId + ": no data");
			}

			switch (this.whenNoDataType)
			{
				case JRReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL :
				{
					if (log.isDebugEnabled())
					{
						log.debug("Fill " + this.fillerId + ": all sections");
					}

					this.scriptlet.callBeforeReportInit();
					this.calculator.initializeVariables(JRVariable.RESET_TYPE_REPORT);
					this.scriptlet.callAfterReportInit();

					this.printPage = newPage();
					addPage(this.printPage);
					setFirstColumn();
					this.offsetY = this.topMargin;

					fillBackground();

					fillTitle();

					fillPageHeader(JRExpression.EVALUATION_DEFAULT);

					fillColumnHeaders(JRExpression.EVALUATION_DEFAULT);

					fillGroupHeaders(true);

					fillGroupFooters(true);

					fillSummary();

					break;
				}
				case JRReport.WHEN_NO_DATA_TYPE_BLANK_PAGE :
				{
					if (log.isDebugEnabled())
					{
						log.debug("Fill " + this.fillerId + ": blank page");
					}

					this.printPage = newPage();
					addPage(this.printPage);
					break;
				}
				case JRReport.WHEN_NO_DATA_TYPE_NO_DATA_SECTION:
				{
					if (log.isDebugEnabled())
					{
						log.debug("Fill " + this.fillerId + ": NoData section");
					}

					this.scriptlet.callBeforeReportInit();
					this.calculator.initializeVariables(JRVariable.RESET_TYPE_REPORT);
					this.scriptlet.callAfterReportInit();

					this.printPage = newPage();
					addPage(this.printPage);
					setFirstColumn();
					this.offsetY = this.topMargin;

					fillBackground();

					fillNoData();

					break;

				}
				case JRReport.WHEN_NO_DATA_TYPE_NO_PAGES :
				default :
				{
					if (log.isDebugEnabled())
					{
						log.debug("Fill " + this.fillerId + ": no pages");
					}
				}
			}
		}

		if (isSubreport())
		{
			//if (
			//	columnIndex == 0 ||
			//	(columnIndex > 0 && printPageStretchHeight < offsetY + bottomMargin)
			//	)
			//{
				this.printPageStretchHeight = this.offsetY + this.bottomMargin;
			//}

			if (this.fillContext.isUsingVirtualizer())
			{
				removePageIdentityDataProvider();
			}
		}

		if (this.fillContext.isIgnorePagination())
		{
			this.jasperPrint.setPageHeight(this.offsetY + this.bottomMargin);
		}
	}


	/**
	 *
	 */
	private void fillReportStart() throws JRException
	{
		this.scriptlet.callBeforeReportInit();
		this.calculator.initializeVariables(JRVariable.RESET_TYPE_REPORT);
		this.scriptlet.callAfterReportInit();

		this.printPage = newPage();
		addPage(this.printPage);
		setFirstColumn();
		this.offsetY = this.topMargin;

		fillBackground();

		fillTitle();

		fillPageHeader(JRExpression.EVALUATION_DEFAULT);

		fillColumnHeaders(JRExpression.EVALUATION_DEFAULT);

		fillGroupHeaders(true);

		fillDetail();
	}


	private void setFirstColumn()
	{
		this.columnIndex = 0;
		this.offsetX = this.leftMargin;
		setColumnNumberVariable();
	}

	/**
	 *
	 */
	private void fillReportContent() throws JRException
	{
		this.calculator.estimateGroupRuptures();

		fillGroupFooters(false);

		resolveGroupBoundElements(JRExpression.EVALUATION_OLD, false);
		this.scriptlet.callBeforeGroupInit();
		this.calculator.initializeVariables(JRVariable.RESET_TYPE_GROUP);
		this.scriptlet.callAfterGroupInit();

		fillGroupHeaders(false);

		fillDetail();
	}


	/**
	 *
	 */
	private void fillReportEnd() throws JRException
	{
		fillGroupFooters(true);

		fillSummary();
	}


	/**
	 *
	 */
	 private void fillTitle() throws JRException
	 {
		if (log.isDebugEnabled() && !this.title.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": title");
		}

		this.title.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (this.title.isToPrint())
		{
			while (
				this.title.getHeight() > this.pageHeight - this.bottomMargin - this.offsetY
				)
			{
				addPage(false);
			}

			this.title.evaluate(JRExpression.EVALUATION_DEFAULT);

			JRPrintBand printBand = this.title.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.title.getHeight());

			if (this.title.willOverflow() && !this.title.isSplitAllowed() && isSubreport())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, false);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.title.refill(this.pageHeight - this.bottomMargin - this.offsetY - this.title.getHeight());
			}

			fillBand(printBand);
			this.offsetY += printBand.getHeight();

			while (this.title.willOverflow())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, false);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.title.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.title.getHeight());

				fillBand(printBand);
				this.offsetY += printBand.getHeight();
			}

			resolveBandBoundElements(this.title, JRExpression.EVALUATION_DEFAULT);

			if (this.isTitleNewPage)
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, false);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);
			}
		}
	}


	/**
	 *
	 */
	private void fillPageHeader(byte evaluation) throws JRException
	{
		if (log.isDebugEnabled() && !this.pageHeader.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": page header");
		}

		setNewPageColumnInBands();

		this.pageHeader.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (this.pageHeader.isToPrint())
		{
			int reattempts = getMasterColumnCount();
			if (this.isCreatingNewPage)
			{
				--reattempts;
			}

			boolean filled = fillBandNoOverflow(this.pageHeader, evaluation);

			for (int i = 0; !filled && i < reattempts; ++i)
			{
				resolveGroupBoundElements(evaluation, false);
				resolveColumnBoundElements(evaluation);
				resolvePageBoundElements(evaluation);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				filled = fillBandNoOverflow(this.pageHeader, evaluation);
			}

			if (!filled)
			{
				throw new JRRuntimeException("Infinite loop creating new page due to page header overflow.");
			}
		}

		this.columnHeaderOffsetY = this.offsetY;

		this.isNewPage = true;
		this.isFirstPageBand = true;
	}


	private boolean fillBandNoOverflow(JRFillBand band, byte evaluation) throws JRException
	{
		int availableStretch = this.columnFooterOffsetY - this.offsetY - band.getHeight();
		boolean overflow = availableStretch < 0;

		if (!overflow)
		{
			band.evaluate(evaluation);
			JRPrintBand printBand = band.fill(availableStretch);

			overflow = band.willOverflow();
			if (overflow)
			{
				band.rewind();
			}
			else
			{
				fillBand(printBand);
				this.offsetY += printBand.getHeight();

				resolveBandBoundElements(band, evaluation);
			}
		}

		return !overflow;
	}


	/**
	 *
	 */
	private void fillColumnHeaders(byte evaluation) throws JRException
	{
		if (log.isDebugEnabled() && !this.columnHeader.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": column headers");
		}

		setNewPageColumnInBands();

		for(this.columnIndex = 0; this.columnIndex < this.columnCount; this.columnIndex++)
		{
			setColumnNumberVariable();

			this.columnHeader.evaluatePrintWhenExpression(evaluation);

			if (this.columnHeader.isToPrint())
			{
				int reattempts = getMasterColumnCount();
				if (this.isCreatingNewPage)
				{
					--reattempts;
				}

				boolean fits = this.columnHeader.getHeight() <= this.columnFooterOffsetY - this.offsetY;
				for (int i = 0; !fits && i < reattempts; ++i)
				{
					fillPageFooter(evaluation);

					resolveGroupBoundElements(evaluation, false);
					resolveColumnBoundElements(evaluation);
					resolvePageBoundElements(evaluation);
					this.scriptlet.callBeforePageInit();
					this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
					this.scriptlet.callAfterPageInit();

					addPage(false);

					fillPageHeader(evaluation);

					fits = this.columnHeader.getHeight() <= this.columnFooterOffsetY - this.offsetY;
				}

				if (!fits)
				{
					throw new JRRuntimeException("Infinite loop creating new page due to column header size.");
				}

				this.offsetX = this.leftMargin + this.columnIndex * (this.columnSpacing + this.columnWidth);
				this.offsetY = this.columnHeaderOffsetY;

				fillFixedBand(this.columnHeader, evaluation, false);
			}
		}

		setFirstColumn();

		this.isNewColumn = true;
		this.isFirstColumnBand = true;
	}


	/**
	 *
	 */
	private void fillGroupHeaders(boolean isFillAll) throws JRException
	{
		if (this.groups != null && this.groups.length > 0)
		{
			for(int i = 0; i < this.groups.length; i++)
			{
				if(isFillAll)
				{
					fillGroupHeader(this.groups[i]);
				}
				else
				{
					if (this.groups[i].hasChanged())
					{
						fillGroupHeader(this.groups[i]);
					}
				}
			}
		}
	}


	/**
	 *
	 */
	private void fillGroupHeader(JRFillGroup group) throws JRException
	{
		JRFillBand groupHeader = (JRFillBand)group.getGroupHeader();

		if (log.isDebugEnabled() && !groupHeader.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": " + group.getName() + " header");
		}

		byte evalPrevPage = (group.isTopLevelChange()?JRExpression.EVALUATION_OLD:JRExpression.EVALUATION_DEFAULT);

		if (
			(group.isStartNewPage() || group.isResetPageNumber()) && !this.isNewPage
			|| ( group.isStartNewColumn() && !this.isNewColumn )
			)
		{
			fillPageBreak(
				group.isResetPageNumber(),
				evalPrevPage,
				JRExpression.EVALUATION_DEFAULT,
				true
				);
		}

		groupHeader.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (groupHeader.isToPrint())
		{
			while (
				groupHeader.getHeight() > this.columnFooterOffsetY - this.offsetY ||
				group.getMinHeightToStartNewPage() > this.columnFooterOffsetY - this.offsetY
				)
			{
				fillPageBreak(
					false,
					evalPrevPage,
					JRExpression.EVALUATION_DEFAULT,
					true
					);
			}
		}

		setNewGroupInBands(group);

		group.setFooterPrinted(false);

		if (groupHeader.isToPrint())
		{
			setFirstColumn();

			fillColumnBand(groupHeader, JRExpression.EVALUATION_DEFAULT);
		}

		group.setHeaderPrinted(true);

		this.isNewGroup = true;
		this.isFirstPageBand = false;
	}


	/**
	 *
	 */
	private void fillGroupHeadersReprint(byte evaluation) throws JRException
	{
		if (this.groups != null && this.groups.length > 0)
		{
			for(int i = 0; i < this.groups.length; i++)
			{
				fillGroupHeaderReprint(this.groups[i], evaluation);
			}
		}
	}


	/**
	 *
	 */
	 private void fillGroupHeaderReprint(JRFillGroup group, byte evaluation) throws JRException
	 {
		if (
			group.isReprintHeaderOnEachPage() &&
			(!group.hasChanged() || (group.hasChanged() && group.isHeaderPrinted()))
			)
		{
			JRFillBand groupHeader = (JRFillBand)group.getGroupHeader();

			groupHeader.evaluatePrintWhenExpression(evaluation);

			if (groupHeader.isToPrint())
			{
				setFirstColumn();

				while (
					groupHeader.getHeight() > this.columnFooterOffsetY - this.offsetY ||
					group.getMinHeightToStartNewPage() > this.columnFooterOffsetY - this.offsetY
					)
				{
					fillPageBreak(false, evaluation, evaluation, true);
				}

				fillColumnBand(groupHeader, evaluation);
			}

			this.isFirstPageBand = false;
		}
	}


	/**
	 *
	 */
	private void fillDetail() throws JRException
	{
		if (log.isDebugEnabled() && !this.detail.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": detail");
		}

		if (!this.detail.isPrintWhenExpressionNull())
		{
			this.calculator.estimateVariables();
			this.detail.evaluatePrintWhenExpression(JRExpression.EVALUATION_ESTIMATED);
		}

		if (this.detail.isToPrint())
		{
			while (
				(this.columnIndex == this.columnCount - 1 || this.isNewGroup)
				&& this.detail.getHeight() > this.columnFooterOffsetY - this.offsetY
				)
			{
				byte evalPrevPage = (this.isNewGroup?JRExpression.EVALUATION_DEFAULT:JRExpression.EVALUATION_OLD);

				fillPageBreak(
					false,
					evalPrevPage,
					JRExpression.EVALUATION_DEFAULT,
					true
					);
			}
		}

		this.scriptlet.callBeforeDetailEval();
		this.calculator.calculateVariables();
		this.scriptlet.callAfterDetailEval();

		if (!this.detail.isPrintWhenExpressionNull())
		{
			this.detail.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);
		}

		if (this.detail.isToPrint())
		{
			if (
				this.offsetX == this.lastDetailOffsetX
				&& this.offsetY == this.lastDetailOffsetY
				)
			{
				if (this.columnIndex == this.columnCount - 1)
				{
					setFirstColumn();
				}
				else
				{
					this.columnIndex++;
					this.offsetX += this.columnWidth + this.columnSpacing;
					this.offsetY -= this.detail.getHeight();

					setColumnNumberVariable();
				}
			}

			fillFixedBand(this.detail, JRExpression.EVALUATION_DEFAULT, false);

			this.lastDetailOffsetX = this.offsetX;
			this.lastDetailOffsetY = this.offsetY;
		}

		this.isNewPage = false;
		this.isNewColumn = false;
		this.isNewGroup = false;
		this.isFirstPageBand = false;
		this.isFirstColumnBand = false;
	}


	/**
	 *
	 */
	private void fillGroupFooters(boolean isFillAll) throws JRException
	{
		if (this.groups != null && this.groups.length > 0)
		{
			byte evaluation = (isFillAll)?JRExpression.EVALUATION_DEFAULT:JRExpression.EVALUATION_OLD;

			for(int i = this.groups.length - 1; i >= 0; i--)
			{
				if (isFillAll)
				{
					fillGroupFooter(this.groups[i], evaluation);
				}
				else
				{
					if (this.groups[i].hasChanged())
					{
						fillGroupFooter(this.groups[i], evaluation);
					}
				}
			}
		}
	}


	/**
	 *
	 */
	private void fillGroupFooter(JRFillGroup group, byte evaluation) throws JRException
	{
		JRFillBand groupFooter = (JRFillBand)group.getGroupFooter();

		if (log.isDebugEnabled() && !groupFooter.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": " + group.getName() + " footer");
		}

		groupFooter.evaluatePrintWhenExpression(evaluation);

		if (groupFooter.isToPrint())
		{
			setFirstColumn();

			if (
				groupFooter.getHeight() > this.columnFooterOffsetY - this.offsetY
				)
			{
				fillPageBreak(false, evaluation, evaluation, true);
			}

			fillColumnBand(groupFooter, evaluation);
		}

		this.isNewPage = false;
		this.isNewColumn = false;
		this.isFirstPageBand = false;
		this.isFirstColumnBand = false;

		group.setHeaderPrinted(false);
		group.setFooterPrinted(true);
	}


	/**
	 *
	 */
	 private void fillColumnFooters(byte evaluation) throws JRException
	 {
		if (log.isDebugEnabled() && !this.columnFooter.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": column footers");
		}

		/*
		if (!isSubreport)
		{
			offsetY = columnFooterOffsetY;
		}
		*/

		if (isSubreport())
		{
			this.columnFooterOffsetY = this.offsetY;
		}

		int tmpColumnFooterOffsetY = this.columnFooterOffsetY;

		if (this.isFloatColumnFooter || this.fillContext.isIgnorePagination())
		{
			tmpColumnFooterOffsetY = this.offsetY;
		}

		for(this.columnIndex = 0; this.columnIndex < this.columnCount; this.columnIndex++)
		{
			setColumnNumberVariable();

			this.offsetX = this.leftMargin + this.columnIndex * (this.columnSpacing + this.columnWidth);
			this.offsetY = tmpColumnFooterOffsetY;

			this.columnFooter.evaluatePrintWhenExpression(evaluation);

			if (this.columnFooter.isToPrint())
			{
				fillFixedBand(this.columnFooter, evaluation, false);
			}
		}
	}


	/**
	 *
	 */
	private void fillPageFooter(byte evaluation) throws JRException
	{
		JRFillBand crtPageFooter = getCurrentPageFooter();

		if (log.isDebugEnabled() && !crtPageFooter.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": " + (this.isLastPageFooter ? "last " : "") + "page footer");
		}

		this.offsetX = this.leftMargin;

		if (!isSubreport() && !this.fillContext.isIgnorePagination())
		{
			this.offsetY = this.pageHeight - crtPageFooter.getHeight() - this.bottomMargin;
		}

		crtPageFooter.evaluatePrintWhenExpression(evaluation);

		if (crtPageFooter.isToPrint())
		{
			fillFixedBand(crtPageFooter, evaluation);
		}
	}


	/**
	 *
	 */
	private void fillSummary() throws JRException
	{
		if (log.isDebugEnabled() && !this.summary.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": summary");
		}

		this.offsetX = this.leftMargin;

		if (this.lastPageFooter == this.missingFillBand)
		{
			if (
				!this.isSummaryNewPage
				//&& columnIndex == 0
				&& this.summary.getHeight() <= this.columnFooterOffsetY - this.offsetY
				)
			{
				fillSummarySamePage();
			}
			else
			{
				fillSummaryNewPage();
			}
		}
		else
		{
			if (
				!this.isSummaryNewPage
				&& this.summary.getHeight() <= this.lastPageColumnFooterOffsetY - this.offsetY
				)
			{
				setLastPageFooter(true);

				fillSummarySamePage();
			}
			else if (
				!this.isSummaryNewPage
				&& this.summary.getHeight() <= this.columnFooterOffsetY - this.offsetY
				)
			{
				fillSummarySamePageMixedFooters();
			}
			else if (this.offsetY <= this.lastPageColumnFooterOffsetY)
			{
				setLastPageFooter(true);

				fillSummaryNewPage();
			}
			else
			{
				fillPageBreak(false, JRExpression.EVALUATION_DEFAULT, JRExpression.EVALUATION_DEFAULT, false);

				setLastPageFooter(true);

				if (this.isSummaryNewPage)
				{
					fillSummaryNewPage();
				}
				else
				{
					fillSummarySamePage();
				}
			}
		}

		resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
		resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
		resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
		resolveReportBoundElements();
	}


	/**
	 *
	 */
	private void fillSummarySamePage() throws JRException
	{
		this.summary.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (this.summary != this.missingFillBand && this.summary.isToPrint())
		{
			this.summary.evaluate(JRExpression.EVALUATION_DEFAULT);

			JRPrintBand printBand = this.summary.fill(this.columnFooterOffsetY - this.offsetY - this.summary.getHeight());

			if (this.summary.willOverflow() && !this.summary.isSplitAllowed())
			{
				fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

				fillPageFooter(JRExpression.EVALUATION_DEFAULT);

				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.summary.refill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());

				fillBand(printBand);
				this.offsetY += printBand.getHeight();
			}
			else
			{
				fillBand(printBand);
				this.offsetY += printBand.getHeight();

				fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

				fillPageFooter(JRExpression.EVALUATION_DEFAULT);
			}

			while (this.summary.willOverflow())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.summary.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());

				fillBand(printBand);
				this.offsetY += printBand.getHeight();
			}

			resolveBandBoundElements(this.summary, JRExpression.EVALUATION_DEFAULT);
		}
		else
		{
			fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

			fillPageFooter(JRExpression.EVALUATION_DEFAULT);
		}
	}


	/**
	 *
	 */
	private void fillSummarySamePageMixedFooters() throws JRException
	{
		this.summary.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (this.summary != this.missingFillBand && this.summary.isToPrint())
		{
			this.summary.evaluate(JRExpression.EVALUATION_DEFAULT);

			JRPrintBand printBand = this.summary.fill(this.columnFooterOffsetY - this.offsetY - this.summary.getHeight());

			if (this.summary.willOverflow() && !this.summary.isSplitAllowed())
			{
				if (this.offsetY <= this.lastPageColumnFooterOffsetY)
				{
					setLastPageFooter(true);

					fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

					fillPageFooter(JRExpression.EVALUATION_DEFAULT);

					resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
					resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
					resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
					this.scriptlet.callBeforePageInit();
					this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
					this.scriptlet.callAfterPageInit();

					addPage(false);

					printBand = this.summary.refill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());

					fillBand(printBand);
					this.offsetY += printBand.getHeight();
				}
				else
				{
					fillPageBreak(false, JRExpression.EVALUATION_DEFAULT, JRExpression.EVALUATION_DEFAULT, false);

					setLastPageFooter(true);

					printBand = this.summary.refill(this.lastPageColumnFooterOffsetY - this.offsetY - this.summary.getHeight());
					//printBand = summary.refill(pageHeight - bottomMargin - offsetY - summary.getHeight());

					fillBand(printBand);
					this.offsetY += printBand.getHeight();

					fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

					fillPageFooter(JRExpression.EVALUATION_DEFAULT);
				}
			}
			else
			{
				fillBand(printBand);
				this.offsetY += printBand.getHeight();

				fillPageBreak(false, JRExpression.EVALUATION_DEFAULT, JRExpression.EVALUATION_DEFAULT, false);

				setLastPageFooter(true);

				if (this.summary.willOverflow())
				{
					printBand = this.summary.fill(this.lastPageColumnFooterOffsetY - this.offsetY - this.summary.getHeight());

					fillBand(printBand);
					this.offsetY += printBand.getHeight();
				}

				fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

				fillPageFooter(JRExpression.EVALUATION_DEFAULT);
			}

			while (this.summary.willOverflow())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.summary.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());

				fillBand(printBand);
				this.offsetY += printBand.getHeight();
			}

			resolveBandBoundElements(this.summary, JRExpression.EVALUATION_DEFAULT);
		}
		else
		{
			if(this.offsetY > this.lastPageColumnFooterOffsetY)
			{
				fillPageBreak(false, JRExpression.EVALUATION_DEFAULT, JRExpression.EVALUATION_DEFAULT, false);
			}

			setLastPageFooter(true);

			fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

			fillPageFooter(JRExpression.EVALUATION_DEFAULT);
		}
	}


	/**
	 *
	 */
	private void fillSummaryNewPage() throws JRException
	{
		fillColumnFooters(JRExpression.EVALUATION_DEFAULT);

		fillPageFooter(JRExpression.EVALUATION_DEFAULT);

		this.summary.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (this.summary != this.missingFillBand && this.summary.isToPrint())
		{
			resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
			resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
			resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
			this.scriptlet.callBeforePageInit();
			this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
			this.scriptlet.callAfterPageInit();

			addPage(false);

			this.columnIndex = -1;// FIXME why?

			this.summary.evaluate(JRExpression.EVALUATION_DEFAULT);

			JRPrintBand printBand = this.summary.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());

			if (this.summary.willOverflow() && !this.summary.isSplitAllowed() && isSubreport())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.summary.refill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());
			}

			fillBand(printBand);
			this.offsetY += printBand.getHeight();

			while (this.summary.willOverflow())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, true);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.summary.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.summary.getHeight());

				fillBand(printBand);
				this.offsetY += printBand.getHeight();
			}

			resolveBandBoundElements(this.summary, JRExpression.EVALUATION_DEFAULT);
		}
	}


	/**
	 *
	 */
	private void fillBackground() throws JRException
	{
		if (log.isDebugEnabled() && !this.background.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": background");
		}
		
		//offsetX = leftMargin;
		
		//if (!isSubreport)
		//{
		//  offsetY = pageHeight - pageFooter.getHeight() - bottomMargin;
		//}
		
		if (this.background.getHeight() <= this.pageHeight - this.bottomMargin - this.offsetY)
		{
			this.background.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);
			
			if (this.background.isToPrint())
			{
				this.background.evaluate(JRExpression.EVALUATION_DEFAULT);
				
				JRPrintBand printBand = this.background.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.background.getHeight());
				
				fillBand(printBand);
				//offsetY += printBand.getHeight();
			}
		}
	}


	/**
	 *
	 */
	private void addPage(boolean isResetPageNumber) throws JRException
	{
		if (isSubreport())
		{
			if (!this.parentFiller.isBandOverFlowAllowed())
			{
				throw new JRRuntimeException("Subreport overflowed on a band that does not support overflow.");
			}

			//if (
			//	columnIndex == 0 ||
			//	(columnIndex > 0 && printPageStretchHeight < offsetY + bottomMargin)
			//	)
			//{
				this.printPageStretchHeight = this.offsetY + this.bottomMargin;
			//}

			if (this.fillContext.isUsingVirtualizer())
			{
				removePageIdentityDataProvider();
			}

			suspendSubreportRunner();
		}

		this.printPage = newPage();

		if (isSubreport() && this.fillContext.isUsingVirtualizer())
		{
			addPageIdentityDataProvider();
		}

		if (isResetPageNumber)
		{
			this.calculator.getPageNumber().setValue(Integer.valueOf(1));
		}
		else
		{
			this.calculator.getPageNumber().setValue(
				Integer.valueOf(((Number)this.calculator.getPageNumber().getValue()).intValue() + 1)
				);
		}

		this.calculator.getPageNumber().setOldValue(
			this.calculator.getPageNumber().getValue()
			);

		addPage(this.printPage);
		setFirstColumn();
		this.offsetY = this.topMargin;

		this.lastDetailOffsetX = -1;
		this.lastDetailOffsetY = -1;

		fillBackground();
	}

	/**
	 * Sets the column number value computed based on {@link #columnIndex columnIndex}
	 */
	private void setColumnNumberVariable()
	{
		JRFillVariable columnNumberVar = this.calculator.getColumnNumber();
		columnNumberVar.setValue(Integer.valueOf(this.columnIndex + 1));
		columnNumberVar.setOldValue(columnNumberVar.getValue());
	}

	/**
	 *
	 */
	private void fillPageBreak(
		boolean isResetPageNumber,
		byte evalPrevPage,
		byte evalNextPage,
		boolean isReprintGroupHeaders
		) throws JRException
	{
		if (this.isCreatingNewPage)
		{
			throw new JRException("Infinite loop creating new page.");
		}

		this.isCreatingNewPage = true;

		fillColumnFooters(evalPrevPage);

		fillPageFooter(evalPrevPage);

		resolveGroupBoundElements(evalPrevPage, false);
		resolveColumnBoundElements(evalPrevPage);
		resolvePageBoundElements(evalPrevPage);
		this.scriptlet.callBeforePageInit();
		this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
		this.scriptlet.callAfterPageInit();

		addPage(isResetPageNumber);

		fillPageHeader(evalNextPage);

		fillColumnHeaders(evalNextPage);

		if (isReprintGroupHeaders)
		{
			fillGroupHeadersReprint(evalNextPage);
		}

		this.isCreatingNewPage = false;
	}


	/**
	 *
	 *
	private void fillColumnBreak(
		byte evalPrevPage,
		byte evalNextPage
		) throws JRException
	{
		if (columnIndex == columnCount - 1)
		{
			fillPageBreak(false, evalPrevPage, evalNextPage);
		}
		else
		{
			fillColumnFooter(evalPrevPage);

			resolveGroupBoundImages(evalPrevPage, false);
			resolveColumnBoundImages(evalPrevPage);
			resolveGroupBoundTexts(evalPrevPage, false);
			resolveColumnBoundTexts(evalPrevPage);
			scriptlet.callBeforeColumnInit();
			calculator.initializeVariables(JRVariable.RESET_TYPE_COLUMN);
			scriptlet.callAfterColumnInit();

			columnIndex += 1;
			offsetX = leftMargin + columnIndex * (columnSpacing + columnWidth);
			offsetY = columnHeaderOffsetY;

			calculator.getColumnNumber().setValue(
				Integer.valueOf(((Number)calculator.getColumnNumber().getValue()).intValue() + 1)
				);
			calculator.getColumnNumber().setOldValue(
				calculator.getColumnNumber().getValue()
				);

			fillColumnHeader(evalNextPage);
		}
	}


	/**
	 *
	 */
	protected void fillPageBand(JRFillBand band, byte evaluation) throws JRException
	{
		band.evaluate(evaluation);

		JRPrintBand printBand = band.fill(this.columnFooterOffsetY - this.offsetY - band.getHeight());

		if (band.willOverflow() && !band.isSplitAllowed())
		{
			fillPageBreak(false, evaluation, evaluation, true);

			printBand = band.refill(this.columnFooterOffsetY - this.offsetY - band.getHeight());
		}

		fillBand(printBand);
		this.offsetY += printBand.getHeight();

		while (band.willOverflow())
		{
			fillPageBreak(false, evaluation, evaluation, true);

			printBand = band.fill(this.columnFooterOffsetY - this.offsetY - band.getHeight());

			fillBand(printBand);
			this.offsetY += printBand.getHeight();
		}

		resolveBandBoundElements(band, evaluation);
	}


	/**
	 *
	 */
	protected void fillColumnBand(JRFillBand band, byte evaluation) throws JRException
	{
		band.evaluate(evaluation);

		JRPrintBand printBand = band.fill(this.columnFooterOffsetY - this.offsetY - band.getHeight());

		if (band.willOverflow() && !band.isSplitAllowed())
		{
			fillPageBreak(false, evaluation, evaluation, true);

			printBand = band.refill(this.columnFooterOffsetY - this.offsetY - band.getHeight());
		}

		fillBand(printBand);
		this.offsetY += printBand.getHeight();

		while (band.willOverflow())
		{
			fillPageBreak(false, evaluation, evaluation, true);

			printBand = band.fill(this.columnFooterOffsetY - this.offsetY - band.getHeight());

			fillBand(printBand);
			this.offsetY += printBand.getHeight();
		}

		resolveBandBoundElements(band, evaluation);
	}


	/**
	 *
	 */
	protected void fillFixedBand(JRFillBand band, byte evaluation) throws JRException
	{
		fillFixedBand(band, evaluation, true);
	}


	protected void fillFixedBand(JRFillBand band, byte evaluation, boolean allowShrinking) throws JRException
	{
		band.evaluate(evaluation);

		JRPrintBand printBand = band.fill();

		fillBand(printBand);
		this.offsetY += allowShrinking ? printBand.getHeight() : band.getHeight();

		resolveBandBoundElements(band, evaluation);
	}


	/**
	 *
	 */
	protected void fillBand(JRPrintBand band)
	{
		java.util.List elements = band.getElements();

		if (elements != null && elements.size() > 0)
		{
			JRPrintElement element = null;
			for(Iterator it = elements.iterator(); it.hasNext();)
			{
				element = (JRPrintElement)it.next();
				element.setX(element.getX() + this.offsetX);
				element.setY(element.getY() + this.offsetY);
				this.printPage.addElement(element);
			}
		}
	}


	/**
	 *
	 */
	private void setNewPageColumnInBands()
	{
		this.title.setNewPageColumn(true);
		this.pageHeader.setNewPageColumn(true);
		this.columnHeader.setNewPageColumn(true);
		this.detail.setNewPageColumn(true);
		this.columnFooter.setNewPageColumn(true);
		this.pageFooter.setNewPageColumn(true);
		this.lastPageFooter.setNewPageColumn(true);
		this.summary.setNewPageColumn(true);
		this.noData.setNewPageColumn(true);

		if (this.groups != null && this.groups.length > 0)
		{
			for(int i = 0; i < this.groups.length; i++)
			{
				((JRFillBand)this.groups[i].getGroupHeader()).setNewPageColumn(true);
				((JRFillBand)this.groups[i].getGroupFooter()).setNewPageColumn(true);
			}
		}
	}


	/**
	 *
	 */
	private void setNewGroupInBands(JRGroup group)
	{
		this.title.setNewGroup(group, true);
		this.pageHeader.setNewGroup(group, true);
		this.columnHeader.setNewGroup(group, true);
		this.detail.setNewGroup(group, true);
		this.columnFooter.setNewGroup(group, true);
		this.pageFooter.setNewGroup(group, true);
		this.lastPageFooter.setNewGroup(group, true);
		this.summary.setNewGroup(group, true);

		if (this.groups != null && this.groups.length > 0)
		{
			for(int i = 0; i < this.groups.length; i++)
			{
				((JRFillBand)this.groups[i].getGroupHeader()).setNewGroup(group, true);
				((JRFillBand)this.groups[i].getGroupFooter()).setNewGroup(group, true);
			}
		}
	}


	/**
	 *
	 */
	private JRFillBand getCurrentPageFooter()
	{
		return this.isLastPageFooter ? this.lastPageFooter : this.pageFooter;
	}


	/**
	 *
	 */
	private void setLastPageFooter(boolean isLastPageFooter)
	{
		this.isLastPageFooter = isLastPageFooter;

		if (isLastPageFooter)
		{
			this.columnFooterOffsetY = this.lastPageColumnFooterOffsetY;
		}
	}

	/**
	 *
	 */
	private void fillNoData() throws JRException
	{
		if (log.isDebugEnabled() && !this.noData.isEmpty())
		{
			log.debug("Fill " + this.fillerId + ": noData");
		}

		this.noData.evaluatePrintWhenExpression(JRExpression.EVALUATION_DEFAULT);

		if (this.noData.isToPrint())
		{
			while (
					this.noData.getHeight() > this.pageHeight - this.bottomMargin - this.offsetY
				)
			{
				addPage(false);
			}

			this.noData.evaluate(JRExpression.EVALUATION_DEFAULT);

			JRPrintBand printBand = this.noData.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.noData.getHeight());

			if (this.noData.willOverflow() && !this.noData.isSplitAllowed() && isSubreport())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, false);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.noData.refill(this.pageHeight - this.bottomMargin - this.offsetY - this.noData.getHeight());
			}

			fillBand(printBand);
			this.offsetY += printBand.getHeight();

			while (this.noData.willOverflow())
			{
				resolveGroupBoundElements(JRExpression.EVALUATION_DEFAULT, false);
				resolveColumnBoundElements(JRExpression.EVALUATION_DEFAULT);
				resolvePageBoundElements(JRExpression.EVALUATION_DEFAULT);
				this.scriptlet.callBeforePageInit();
				this.calculator.initializeVariables(JRVariable.RESET_TYPE_PAGE);
				this.scriptlet.callAfterPageInit();

				addPage(false);

				printBand = this.noData.fill(this.pageHeight - this.bottomMargin - this.offsetY - this.noData.getHeight());

				fillBand(printBand);
				this.offsetY += printBand.getHeight();
			}
			resolveBandBoundElements(this.noData, JRExpression.EVALUATION_DEFAULT);
		}
	}

}
