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
package net.sf.jasperreports.swing;

import java.awt.event.KeyListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewerToolbar.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class JRViewerToolbar extends JPanel implements JRViewerListener
{
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected final JRViewerController viewerContext;
	
	protected float MIN_ZOOM = 0.5f;
	protected float MAX_ZOOM = 10f;
	protected int zooms[] = {50, 75, 100, 125, 150, 175, 200, 250, 400, 800};
	protected int defaultZoomIndex = 2;
	protected List saveContributors = new ArrayList();
	protected File lastFolder = null;
	protected JRSaveContributor lastSaveContributor = null;
	protected DecimalFormat zoomDecimalFormat = new DecimalFormat("#.##");
	
	protected javax.swing.JToggleButton btnActualSize;
	protected javax.swing.JButton btnFirst;
	protected javax.swing.JToggleButton btnFitPage;
	protected javax.swing.JToggleButton btnFitWidth;
	protected javax.swing.JButton btnLast;
	protected javax.swing.JButton btnNext;
	protected javax.swing.JButton btnPrevious;
	protected javax.swing.JButton btnPrint;
	protected javax.swing.JButton btnReload;
	protected javax.swing.JButton btnSave;
	protected javax.swing.JButton btnZoomIn;
	protected javax.swing.JButton btnZoomOut;
	protected javax.swing.JComboBox cmbZoom;
	protected javax.swing.JPanel pnlSep01;
	protected javax.swing.JPanel pnlSep02;
	protected javax.swing.JPanel pnlSep03;
	protected javax.swing.JTextField txtGoTo;

	public JRViewerToolbar(JRViewerController viewerContext)
	{
		this.viewerContext = viewerContext;
		this.viewerContext.addListener(this);

		initComponents();
		initSaveContributors();
	}

	private void initComponents()
	{
		this.btnSave = new javax.swing.JButton();
		this.btnPrint = new javax.swing.JButton();
		this.btnReload = new javax.swing.JButton();
		this.pnlSep01 = new javax.swing.JPanel();
		this.btnFirst = new javax.swing.JButton();
		this.btnPrevious = new javax.swing.JButton();
		this.btnNext = new javax.swing.JButton();
		this.btnLast = new javax.swing.JButton();
		this.txtGoTo = new javax.swing.JTextField();
		this.pnlSep02 = new javax.swing.JPanel();
		this.btnActualSize = new javax.swing.JToggleButton();
		this.btnFitPage = new javax.swing.JToggleButton();
		this.btnFitWidth = new javax.swing.JToggleButton();
		this.pnlSep03 = new javax.swing.JPanel();
		this.btnZoomIn = new javax.swing.JButton();
		this.btnZoomOut = new javax.swing.JButton();
		this.cmbZoom = new javax.swing.JComboBox();
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(int i = 0; i < this.zooms.length; i++)
		{
			model.addElement("" + this.zooms[i] + "%");
		}
		this.cmbZoom.setModel(model);
		
		setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 2));

		this.btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/save.GIF")));
		this.btnSave.setToolTipText(this.viewerContext.getBundleString("save"));
		this.btnSave.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnSave.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnSave.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnSave.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSaveActionPerformed(evt);
			}
		});
		add(this.btnSave);

		this.btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/print.GIF")));
		this.btnPrint.setToolTipText(this.viewerContext.getBundleString("print"));
		this.btnPrint.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnPrint.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnPrint.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnPrint.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrintActionPerformed(evt);
			}
		});
		add(this.btnPrint);

		this.btnReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/reload.GIF")));
		this.btnReload.setToolTipText(this.viewerContext.getBundleString("reload"));
		this.btnReload.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnReload.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnReload.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnReload.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnReload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}
		});
		add(this.btnReload);

		this.pnlSep01.setMaximumSize(new java.awt.Dimension(10, 10));
		add(this.pnlSep01);

		this.btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/first.GIF")));
		this.btnFirst.setToolTipText(this.viewerContext.getBundleString("first.page"));
		this.btnFirst.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnFirst.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnFirst.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnFirst.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnFirst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFirstActionPerformed(evt);
			}
		});
		add(this.btnFirst);

		this.btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/previous.GIF")));
		this.btnPrevious.setToolTipText(this.viewerContext.getBundleString("previous.page"));
		this.btnPrevious.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnPrevious.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnPrevious.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnPrevious.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnPrevious.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPreviousActionPerformed(evt);
			}
		});
		add(this.btnPrevious);

		this.btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/next.GIF")));
		this.btnNext.setToolTipText(this.viewerContext.getBundleString("next.page"));
		this.btnNext.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnNext.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnNext.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnNext.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNextActionPerformed(evt);
			}
		});
		add(this.btnNext);

		this.btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/last.GIF")));
		this.btnLast.setToolTipText(this.viewerContext.getBundleString("last.page"));
		this.btnLast.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnLast.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnLast.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnLast.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnLast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLastActionPerformed(evt);
			}
		});
		add(this.btnLast);

		this.txtGoTo.setToolTipText(this.viewerContext.getBundleString("go.to.page"));
		this.txtGoTo.setMaximumSize(new java.awt.Dimension(40, 23));
		this.txtGoTo.setMinimumSize(new java.awt.Dimension(40, 23));
		this.txtGoTo.setPreferredSize(new java.awt.Dimension(40, 23));
		this.txtGoTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtGoToActionPerformed(evt);
			}
		});
		add(this.txtGoTo);

		this.pnlSep02.setMaximumSize(new java.awt.Dimension(10, 10));
		add(this.pnlSep02);

		this.btnActualSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/actualsize.GIF")));
		this.btnActualSize.setToolTipText(this.viewerContext.getBundleString("actual.size"));
		this.btnActualSize.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnActualSize.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnActualSize.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnActualSize.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnActualSize.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnActualSizeActionPerformed(evt);
			}
		});
		add(this.btnActualSize);

		this.btnFitPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitpage.GIF")));
		this.btnFitPage.setToolTipText(this.viewerContext.getBundleString("fit.page"));
		this.btnFitPage.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnFitPage.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnFitPage.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnFitPage.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnFitPage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFitPageActionPerformed(evt);
			}
		});
		add(this.btnFitPage);

		this.btnFitWidth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitwidth.GIF")));
		this.btnFitWidth.setToolTipText(this.viewerContext.getBundleString("fit.width"));
		this.btnFitWidth.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnFitWidth.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnFitWidth.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnFitWidth.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnFitWidth.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFitWidthActionPerformed(evt);
			}
		});
		add(this.btnFitWidth);

		this.pnlSep03.setMaximumSize(new java.awt.Dimension(10, 10));
		add(this.pnlSep03);

		this.btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomin.GIF")));
		this.btnZoomIn.setToolTipText(this.viewerContext.getBundleString("zoom.in"));
		this.btnZoomIn.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnZoomIn.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnZoomIn.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnZoomIn.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnZoomInActionPerformed(evt);
			}
		});
		add(this.btnZoomIn);

		this.btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomout.GIF")));
		this.btnZoomOut.setToolTipText(this.viewerContext.getBundleString("zoom.out"));
		this.btnZoomOut.setMargin(new java.awt.Insets(2, 2, 2, 2));
		this.btnZoomOut.setMaximumSize(new java.awt.Dimension(23, 23));
		this.btnZoomOut.setMinimumSize(new java.awt.Dimension(23, 23));
		this.btnZoomOut.setPreferredSize(new java.awt.Dimension(23, 23));
		this.btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnZoomOutActionPerformed(evt);
			}
		});
		add(this.btnZoomOut);

		this.cmbZoom.setEditable(true);
		this.cmbZoom.setToolTipText(this.viewerContext.getBundleString("zoom.ratio"));
		this.cmbZoom.setMaximumSize(new java.awt.Dimension(80, 23));
		this.cmbZoom.setMinimumSize(new java.awt.Dimension(80, 23));
		this.cmbZoom.setPreferredSize(new java.awt.Dimension(80, 23));
		this.cmbZoom.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cmbZoomActionPerformed(evt);
			}
		});
		this.cmbZoom.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cmbZoomItemStateChanged(evt);
			}
		});
		add(this.cmbZoom);
	}
	
	public void init()
	{
		this.cmbZoom.setSelectedIndex(this.defaultZoomIndex);
	}
	
	public void addComponentKeyListener(KeyListener listener)
	{
		this.btnSave.addKeyListener(listener);
		this.btnPrint.addKeyListener(listener);
		this.btnReload.addKeyListener(listener);
		this.btnFirst.addKeyListener(listener);
		this.btnPrevious.addKeyListener(listener);
		this.btnNext.addKeyListener(listener);
		this.btnLast.addKeyListener(listener);
		this.txtGoTo.addKeyListener(listener);
		this.btnActualSize.addKeyListener(listener);
		this.btnFitPage.addKeyListener(listener);
		this.btnFitWidth.addKeyListener(listener);
		this.btnZoomIn.addKeyListener(listener);
		this.btnZoomOut.addKeyListener(listener);
		this.cmbZoom.addKeyListener(listener);
	}

	void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
		// Add your handling code here:

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setLocale(this.getLocale());
		fileChooser.updateUI();
		for(int i = 0; i < this.saveContributors.size(); i++)
		{
			fileChooser.addChoosableFileFilter((JRSaveContributor)this.saveContributors.get(i));
		}

		if (this.saveContributors.contains(this.lastSaveContributor))
		{
			fileChooser.setFileFilter(this.lastSaveContributor);
		}
		else if (this.saveContributors.size() > 0)
		{
			fileChooser.setFileFilter((JRSaveContributor)this.saveContributors.get(0));
		}
		
		if (this.lastFolder != null)
		{
			fileChooser.setCurrentDirectory(this.lastFolder);
		}
		
		int retValue = fileChooser.showSaveDialog(this);
		if (retValue == JFileChooser.APPROVE_OPTION)
		{
			FileFilter fileFilter = fileChooser.getFileFilter();
			File file = fileChooser.getSelectedFile();
			
			this.lastFolder = file.getParentFile();

			JRSaveContributor contributor = null;

			if (fileFilter instanceof JRSaveContributor)
			{
				contributor = (JRSaveContributor)fileFilter;
			}
			else
			{
				int i = 0;
				while(contributor == null && i < this.saveContributors.size())
				{
					contributor = (JRSaveContributor)this.saveContributors.get(i++);
					if (!contributor.accept(file))
					{
						contributor = null;
					}
				}

				if (contributor == null)
				{
					contributor = new JRPrintSaveContributor(getLocale(), this.viewerContext.getResourceBundle());
				}
			}

			this.lastSaveContributor = contributor;
			
			try
			{
				contributor.save(this.viewerContext.getJasperPrint(), file);
			}
			catch (JRException e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, this.viewerContext.getBundleString("error.saving"));
			}
		}
	}//GEN-LAST:event_btnSaveActionPerformed

	void btnPrintActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPrintActionPerformed
	{//GEN-HEADEREND:event_btnPrintActionPerformed
		// Add your handling code here:

		Thread thread =
			new Thread(
				new Runnable()
				{
					public void run()
					{
						try
						{
							JasperPrintManager.printReport(JRViewerToolbar.this.viewerContext.getJasperPrint(), true);
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
							JOptionPane.showMessageDialog(JRViewerToolbar.this, 
									JRViewerToolbar.this.viewerContext.getBundleString("error.printing"));
						}
					}
				}
			);

		thread.start();

	}//GEN-LAST:event_btnPrintActionPerformed

	void btnReloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnReloadActionPerformed
	{//GEN-HEADEREND:event_btnReloadActionPerformed
		// Add your handling code here:
		this.viewerContext.reload();
	}//GEN-LAST:event_btnReloadActionPerformed

	void btnFirstActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnFirstActionPerformed
	{//GEN-HEADEREND:event_btnFirstActionPerformed
		// Add your handling code here:
		this.viewerContext.setPageIndex(0);
		this.viewerContext.refreshPage();
	}//GEN-LAST:event_btnFirstActionPerformed

	void btnPreviousActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPreviousActionPerformed
	{//GEN-HEADEREND:event_btnPreviousActionPerformed
		// Add your handling code here:
		this.viewerContext.setPageIndex(this.viewerContext.getPageIndex() - 1);
		this.viewerContext.refreshPage();
	}//GEN-LAST:event_btnPreviousActionPerformed

	void btnNextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNextActionPerformed
	{//GEN-HEADEREND:event_btnNextActionPerformed
		// Add your handling code here:
		this.viewerContext.setPageIndex(this.viewerContext.getPageIndex() + 1);
		this.viewerContext.refreshPage();
	}//GEN-LAST:event_btnNextActionPerformed

	void btnLastActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLastActionPerformed
	{//GEN-HEADEREND:event_btnLastActionPerformed
		// Add your handling code here:
		this.viewerContext.setPageIndex(this.viewerContext.getPageCount() - 1);
		this.viewerContext.refreshPage();
	}//GEN-LAST:event_btnLastActionPerformed

	void txtGoToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGoToActionPerformed
		try
		{
			int pageNumber = Integer.parseInt(this.txtGoTo.getText());
			if (
				pageNumber != this.viewerContext.getPageIndex() + 1
				&& pageNumber > 0
				&& pageNumber <= this.viewerContext.getPageCount()
				)
			{
				this.viewerContext.setPageIndex(pageNumber - 1);
				this.viewerContext.refreshPage();
			}
		}
		catch(NumberFormatException e)
		{
		}
	}//GEN-LAST:event_txtGoToActionPerformed

	void btnActualSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualSizeActionPerformed
		// Add your handling code here:
		if (this.btnActualSize.isSelected())
		{
			this.btnFitPage.setSelected(false);
			this.btnFitWidth.setSelected(false);
			this.cmbZoom.setSelectedIndex(-1);
			this.viewerContext.setZoomRatio(1);
			this.btnActualSize.setSelected(true);
		}
	}//GEN-LAST:event_btnActualSizeActionPerformed

	void btnFitPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFitPageActionPerformed
		// Add your handling code here:
		if (this.btnFitPage.isSelected())
		{
			this.btnActualSize.setSelected(false);
			this.btnFitWidth.setSelected(false);
			this.cmbZoom.setSelectedIndex(-1);
			this.viewerContext.fitPage();
			this.btnFitPage.setSelected(true);
		}
	}//GEN-LAST:event_btnFitPageActionPerformed

	void btnFitWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFitWidthActionPerformed
		// Add your handling code here:
		if (this.btnFitWidth.isSelected())
		{
			this.btnActualSize.setSelected(false);
			this.btnFitPage.setSelected(false);
			this.cmbZoom.setSelectedIndex(-1);
			this.viewerContext.fitWidth();
			this.btnFitWidth.setSelected(true);
		}
	}//GEN-LAST:event_btnFitWidthActionPerformed

	void btnZoomInActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomInActionPerformed
	{//GEN-HEADEREND:event_btnZoomInActionPerformed
		// Add your handling code here:
		this.btnActualSize.setSelected(false);
		this.btnFitPage.setSelected(false);
		this.btnFitWidth.setSelected(false);

		int newZoomInt = (int)(100 * getZoomRatio());
		int index = Arrays.binarySearch(this.zooms, newZoomInt);
		if (index < 0)
		{
			this.viewerContext.setZoomRatio(this.zooms[- index - 1] / 100f);
		}
		else if (index < this.cmbZoom.getModel().getSize() - 1)
		{
			this.viewerContext.setZoomRatio(this.zooms[index + 1] / 100f);
		}
	}//GEN-LAST:event_btnZoomInActionPerformed


	protected float getZoomRatio()
	{
		float newZoom = this.viewerContext.getZoom();

		try
		{
			newZoom =
				this.zoomDecimalFormat.parse(
					String.valueOf(this.cmbZoom.getEditor().getItem())
					).floatValue() / 100f;
		}
		catch(ParseException e)
		{
		}

		return newZoom;
	}

	void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnZoomOutActionPerformed
	{//GEN-HEADEREND:event_btnZoomOutActionPerformed
		// Add your handling code here:
		this.btnActualSize.setSelected(false);
		this.btnFitPage.setSelected(false);
		this.btnFitWidth.setSelected(false);

		int newZoomInt = (int)(100 * getZoomRatio());
		int index = Arrays.binarySearch(this.zooms, newZoomInt);
		if (index > 0)
		{
			this.viewerContext.setZoomRatio(this.zooms[index - 1] / 100f);
		}
		else if (index < -1)
		{
			this.viewerContext.setZoomRatio(this.zooms[- index - 2] / 100f);
		}
	}//GEN-LAST:event_btnZoomOutActionPerformed

	void cmbZoomActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cmbZoomActionPerformed
	{//GEN-HEADEREND:event_cmbZoomActionPerformed
		// Add your handling code here:
		float newZoom = getZoomRatio();

		if (newZoom < this.MIN_ZOOM)
		{
			newZoom = this.MIN_ZOOM;
		}

		if (newZoom > this.MAX_ZOOM)
		{
			newZoom = this.MAX_ZOOM;
		}

		this.viewerContext.setZoomRatio(newZoom);
	}//GEN-LAST:event_cmbZoomActionPerformed

	void cmbZoomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbZoomItemStateChanged
		// Add your handling code here:
		this.btnActualSize.setSelected(false);
		this.btnFitPage.setSelected(false);
		this.btnFitWidth.setSelected(false);
	}//GEN-LAST:event_cmbZoomItemStateChanged


	/**
	 *
	 */
	public void addSaveContributor(JRSaveContributor contributor)
	{
		this.saveContributors.add(contributor);
	}


	/**
	 *
	 */
	public void removeSaveContributor(JRSaveContributor contributor)
	{
		this.saveContributors.remove(contributor);
	}


	/**
	 *
	 */
	public JRSaveContributor[] getSaveContributors()
	{
		return (JRSaveContributor[])this.saveContributors.toArray(new JRSaveContributor[this.saveContributors.size()]);
	}


	/**
	 * Replaces the save contributors with the ones provided as parameter. 
	 */
	public void setSaveContributors(JRSaveContributor[] saveContributors)
	{
		this.saveContributors = new ArrayList();
		if (saveContributors != null)
		{
			this.saveContributors.addAll(Arrays.asList(saveContributors));
		}
	}


	/**
	 *
	 */
	protected void initSaveContributors()
	{
		final String[] DEFAULT_CONTRIBUTORS =
			{
				"net.sf.jasperreports.view.save.JRPrintSaveContributor",
				"net.sf.jasperreports.view.save.JRPdfSaveContributor",
				"net.sf.jasperreports.view.save.JRRtfSaveContributor",
				"net.sf.jasperreports.view.save.JROdtSaveContributor",
				"net.sf.jasperreports.view.save.JRHtmlSaveContributor",
				"net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor",
				"net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor",
				"net.sf.jasperreports.view.save.JRCsvSaveContributor",
				"net.sf.jasperreports.view.save.JRXmlSaveContributor",
				"net.sf.jasperreports.view.save.JREmbeddedImagesXmlSaveContributor"
			};

		for(int i = 0; i < DEFAULT_CONTRIBUTORS.length; i++)
		{
			try
			{
				Class saveContribClass = JRClassLoader.loadClassForName(DEFAULT_CONTRIBUTORS[i]);
				Constructor constructor = saveContribClass.getConstructor(new Class[]{Locale.class, ResourceBundle.class});
				JRSaveContributor saveContrib = (JRSaveContributor)constructor.newInstance(new Object[]{getLocale(), this.viewerContext.getResourceBundle()});
				this.saveContributors.add(saveContrib);
			}
			catch (Exception e)
			{
			}
		}
	}

	protected void reportLoaded()
	{
		this.btnReload.setEnabled(this.viewerContext.isReloadSupported());
	}

	protected void refreshPage()
	{
		if (!this.viewerContext.hasPages())
		{
			this.btnSave.setEnabled(false);
			this.btnPrint.setEnabled(false);
			this.btnActualSize.setEnabled(false);
			this.btnFitPage.setEnabled(false);
			this.btnFitWidth.setEnabled(false);
			this.btnZoomIn.setEnabled(false);
			this.btnZoomOut.setEnabled(false);
			this.cmbZoom.setEnabled(false);

			return;
		}

		this.btnSave.setEnabled(true);
		this.btnPrint.setEnabled(true);
		this.btnActualSize.setEnabled(true);
		this.btnFitPage.setEnabled(true);
		this.btnFitWidth.setEnabled(true);
		this.btnZoomIn.setEnabled(this.viewerContext.getZoom() < this.MAX_ZOOM);
		this.btnZoomOut.setEnabled(this.viewerContext.getZoom() > this.MIN_ZOOM);
		this.cmbZoom.setEnabled(true);
	}

	protected void pageChanged()
	{
		if (this.viewerContext.hasPages())
		{
			int pageIndex = this.viewerContext.getPageIndex();
			this.btnFirst.setEnabled( (pageIndex > 0) );
			this.btnPrevious.setEnabled( (pageIndex > 0) );
			boolean notLast = pageIndex < this.viewerContext.getPageCount() - 1;
			this.btnNext.setEnabled(notLast);
			this.btnLast.setEnabled(notLast);
			this.txtGoTo.setEnabled(this.btnFirst.isEnabled() || this.btnLast.isEnabled());
			this.txtGoTo.setText("" + (pageIndex + 1));
		}
		else
		{
			this.btnFirst.setEnabled(false);
			this.btnPrevious.setEnabled(false);
			this.btnNext.setEnabled(false);
			this.btnLast.setEnabled(false);
			this.txtGoTo.setEnabled(false);
			this.txtGoTo.setText("");
		}
	}

	protected void zoomChanged()
	{
		this.cmbZoom.getEditor().setItem(this.zoomDecimalFormat.format(this.viewerContext.getZoom() * 100) + "%");
	}

	public boolean isFitPage()
	{
		return this.btnFitPage.isSelected();
	}

	public boolean isFitWidth()
	{
		return this.btnFitPage.isSelected();
	}

	public void setFitWidth()
	{
		this.btnFitWidth.setSelected(true);
	}

	protected void fitPage()
	{
		this.btnFitPage.setSelected(true);
	}

	protected void fitWidth()
	{
		this.btnFitWidth.setSelected(true);
	}

	public void viewerEvent(JRViewerEvent event)
	{
		switch (event.getCode())
		{
		case JRViewerEvent.EVENT_FIT_PAGE:
			fitPage();
			break;
		case JRViewerEvent.EVENT_FIT_WIDTH:
			fitWidth();
			break;
		case JRViewerEvent.EVENT_PAGE_CHANGED:
			pageChanged();
			break;
		case JRViewerEvent.EVENT_REFRESH_PAGE:
			refreshPage();
			break;
		case JRViewerEvent.EVENT_ZOOM_CHANGED:
			zoomChanged();
			break;
		case JRViewerEvent.EVENT_REPORT_LOADED:
			reportLoaded();
			break;
		}
	}
}
