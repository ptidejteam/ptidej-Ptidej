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

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRImageMapRenderer;
import net.sf.jasperreports.engine.JRPrintAnchorIndex;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintImageAreaHyperlink;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.print.JRPrinterAWT;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.view.JRHyperlinkListener;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewerPanel.java,v 1.1 2008/09/29 16:22:11 guehene Exp $
 */
public class JRViewerPanel extends JPanel implements JRHyperlinkListener, JRViewerListener
{
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 * The DPI of the generated report.
	 */
	public static final int REPORT_RESOLUTION = 72;
	
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private PageRenderer lblPage;
	private javax.swing.JPanel pnlInScroll;
	private javax.swing.JPanel pnlLinks;
	private javax.swing.JPanel pnlPage;
	private javax.swing.JScrollPane scrollPane;

	private final JRViewerController viewerContext;
	private JRGraphics2DExporter exporter = null;
	private boolean pageError;

	private int downX = 0;
	private int downY = 0;
	
	private java.util.List hyperlinkListeners = new ArrayList();
	private Map linksMap = new HashMap();

	/**
	 * the screen resolution.
	 */
	private int screenResolution = REPORT_RESOLUTION;

	/**
	 * the zoom ratio adjusted to the screen resolution.
	 */
	protected float realZoom = 0f;

	private MouseListener mouseListener =
		new java.awt.event.MouseAdapter()
		{
			public void mouseClicked(java.awt.event.MouseEvent evt)
			{
				hyperlinkClicked(evt);
			}
		};

	protected KeyListener keyNavigationListener = new KeyListener()
	{
		public void keyTyped(KeyEvent evt)
		{
		}

		public void keyPressed(KeyEvent evt)
		{
			keyNavigate(evt);
		}

		public void keyReleased(KeyEvent evt)
		{
		}
	};
	
	public JRViewerPanel(JRViewerController viewerContext)
	{
		this.viewerContext = viewerContext;
		this.viewerContext.addListener(this);
		setScreenDetails();
		initComponents();
		addHyperlinkListener(this);
	}

	private void initComponents()
	{
		this.scrollPane = new javax.swing.JScrollPane();
		this.scrollPane.getHorizontalScrollBar().setUnitIncrement(5);
		this.scrollPane.getVerticalScrollBar().setUnitIncrement(5);

		this.pnlInScroll = new javax.swing.JPanel();
		this.pnlPage = new javax.swing.JPanel();
		this.jPanel4 = new javax.swing.JPanel();
		this.pnlLinks = new javax.swing.JPanel();
		this.jPanel5 = new javax.swing.JPanel();
		this.jPanel6 = new javax.swing.JPanel();
		this.jPanel7 = new javax.swing.JPanel();
		this.jPanel8 = new javax.swing.JPanel();
		this.jLabel1 = new javax.swing.JLabel();
		this.jPanel9 = new javax.swing.JPanel();
		this.lblPage = new PageRenderer();

		setMinimumSize(new java.awt.Dimension(450, 150));
		setPreferredSize(new java.awt.Dimension(450, 150));

		setLayout(new java.awt.BorderLayout());
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				pnlMainComponentResized(evt);
			}
		});

		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.pnlInScroll.setLayout(new java.awt.GridBagLayout());

		this.pnlPage.setLayout(new java.awt.BorderLayout());
		this.pnlPage.setMinimumSize(new java.awt.Dimension(100, 100));
		this.pnlPage.setPreferredSize(new java.awt.Dimension(100, 100));

		this.jPanel4.setLayout(new java.awt.GridBagLayout());
		this.jPanel4.setMinimumSize(new java.awt.Dimension(100, 120));
		this.jPanel4.setPreferredSize(new java.awt.Dimension(100, 120));

		this.pnlLinks.setLayout(null);
		this.pnlLinks.setMinimumSize(new java.awt.Dimension(5, 5));
		this.pnlLinks.setPreferredSize(new java.awt.Dimension(5, 5));
		this.pnlLinks.setOpaque(false);
		this.pnlLinks.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				pnlLinksMousePressed(evt);
			}
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				pnlLinksMouseReleased(evt);
			}
		});
		this.pnlLinks.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				pnlLinksMouseDragged(evt);
			}
		});

		GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		this.jPanel4.add(this.pnlLinks, gridBagConstraints);

		this.jPanel5.setBackground(java.awt.Color.gray);
		this.jPanel5.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel5.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		this.jPanel4.add(this.jPanel5, gridBagConstraints);

		this.jPanel6.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel6.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		this.jPanel4.add(this.jPanel6, gridBagConstraints);

		this.jPanel7.setBackground(java.awt.Color.gray);
		this.jPanel7.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel7.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		this.jPanel4.add(this.jPanel7, gridBagConstraints);

		this.jPanel8.setBackground(java.awt.Color.gray);
		this.jPanel8.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel8.setPreferredSize(new java.awt.Dimension(5, 5));
		this.jLabel1.setText("jLabel1");
		this.jPanel8.add(this.jLabel1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		this.jPanel4.add(this.jPanel8, gridBagConstraints);

		this.jPanel9.setMinimumSize(new java.awt.Dimension(5, 5));
		this.jPanel9.setPreferredSize(new java.awt.Dimension(5, 5));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		this.jPanel4.add(this.jPanel9, gridBagConstraints);

		this.lblPage.setBackground(java.awt.Color.white);
		this.lblPage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
		this.lblPage.setOpaque(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		this.jPanel4.add(this.lblPage, gridBagConstraints);

		this.pnlPage.add(this.jPanel4, java.awt.BorderLayout.CENTER);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		this.pnlInScroll.add(this.pnlPage, gridBagConstraints);

		this.scrollPane.setViewportView(this.pnlInScroll);
		add(this.scrollPane, java.awt.BorderLayout.CENTER);
		
	}

	public KeyListener getKeyNavigationListener()
	{
		return this.keyNavigationListener;
	}
	
	protected void setScreenDetails()
	{
		this.screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
	}

	public void addHyperlinkListener(JRHyperlinkListener listener)
	{
		this.hyperlinkListeners.add(listener);
	}

	public void removeHyperlinkListener(JRHyperlinkListener listener)
	{
		this.hyperlinkListeners.remove(listener);
	}

	public JRHyperlinkListener[] getHyperlinkListeners()
	{
		return (JRHyperlinkListener[])this.hyperlinkListeners.toArray(new JRHyperlinkListener[this.hyperlinkListeners.size()]);
	}


	/**
	 *
	 */
	public void gotoHyperlink(JRPrintHyperlink hyperlink)
	{
		switch(hyperlink.getHyperlinkType())
		{
			case JRHyperlink.HYPERLINK_TYPE_REFERENCE :
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR :
			{
				if (hyperlink.getHyperlinkAnchor() != null)
				{
					Map anchorIndexes = this.viewerContext.getJasperPrint().getAnchorIndexes();
					JRPrintAnchorIndex anchorIndex = (JRPrintAnchorIndex)anchorIndexes.get(hyperlink.getHyperlinkAnchor());
					if (anchorIndex.getPageIndex() != this.viewerContext.getPageIndex())
					{
						this.viewerContext.setPageIndex(anchorIndex.getPageIndex());
						this.viewerContext.refreshPage();
					}
					Container container = this.pnlInScroll.getParent();
					if (container instanceof JViewport)
					{
						JViewport viewport = (JViewport) container;

						int newX = (int)(anchorIndex.getElementAbsoluteX() * this.realZoom);
						int newY = (int)(anchorIndex.getElementAbsoluteY() * this.realZoom);

						int maxX = this.pnlInScroll.getWidth() - viewport.getWidth();
						int maxY = this.pnlInScroll.getHeight() - viewport.getHeight();

						if (newX < 0)
						{
							newX = 0;
						}
						if (newX > maxX)
						{
							newX = maxX;
						}
						if (newY < 0)
						{
							newY = 0;
						}
						if (newY > maxY)
						{
							newY = maxY;
						}

						viewport.setViewPosition(new Point(newX, newY));
					}
				}

				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE :
			{
				int page = this.viewerContext.getPageIndex() + 1;
				if (hyperlink.getHyperlinkPage() != null)
				{
					page = hyperlink.getHyperlinkPage().intValue();
				}

				if (page >= 1 && page <= this.viewerContext.getJasperPrint().getPages().size() && page != this.viewerContext.getPageIndex() + 1)
				{
					this.viewerContext.setPageIndex(page - 1);
					this.viewerContext.refreshPage();
					Container container = this.pnlInScroll.getParent();
					if (container instanceof JViewport)
					{
						JViewport viewport = (JViewport) container;
						viewport.setViewPosition(new Point(0, 0));
					}
				}

				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR :
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Hyperlink anchor    : " + hyperlink.getHyperlinkAnchor());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE :
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
					System.out.println("Hyperlink page      : " + hyperlink.getHyperlinkPage());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_CUSTOM:
			{
				if (isOnlyHyperlinkListener())
				{
					System.out.println("Hyperlink of type " + hyperlink.getLinkType());
					System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
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


	protected boolean isOnlyHyperlinkListener()
	{
		int listenerCount;
		if (this.hyperlinkListeners == null)
		{
			listenerCount = 0;
		}
		else
		{
			listenerCount = this.hyperlinkListeners.size();
			if (this.hyperlinkListeners.contains(this))
			{
				--listenerCount;
			}
		}
		return listenerCount == 0;
	}

	protected void paintPage(Graphics2D grx)
	{
		if (this.pageError)
		{
			paintPageError(grx);
			return;
		}
		
		try
		{
			if (this.exporter == null)
			{
				this.exporter = getGraphics2DExporter();
			}
			else
			{
				this.exporter.reset();
			}

			this.exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.viewerContext.getJasperPrint());
			this.exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, grx.create());
			this.exporter.setParameter(JRExporterParameter.PAGE_INDEX, Integer.valueOf(this.viewerContext.getPageIndex()));
			this.exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, Float.valueOf(this.realZoom));
			this.exporter.setParameter(JRExporterParameter.OFFSET_X, Integer.valueOf(1)); //lblPage border
			this.exporter.setParameter(JRExporterParameter.OFFSET_Y, Integer.valueOf(1));
			this.exporter.exportReport();
		}
		catch(Exception e)
		{
			this.pageError = true;
			e.printStackTrace();
			
			paintPageError(grx);
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					JOptionPane.showMessageDialog(JRViewerPanel.this, JRViewerPanel.this.viewerContext.getBundleString("error.displaying"));
				}
			});
		}

	}

	protected JRGraphics2DExporter getGraphics2DExporter() throws JRException
	{
		return new JRGraphics2DExporter();
	}

	protected void paintPageError(Graphics2D grx)
	{
		AffineTransform origTransform = grx.getTransform();
		
		AffineTransform transform = new AffineTransform();
		transform.translate(1, 1);
		transform.scale(this.realZoom, this.realZoom);
		grx.transform(transform);
		
		try
		{
			drawPageError(grx);
		}
		finally
		{
			grx.setTransform(origTransform);
		}
	}

	protected void drawPageError(Graphics grx)
	{
		grx.setColor(Color.white);
		grx.fillRect(0, 0, this.viewerContext.getJasperPrint().getPageWidth() + 1, this.viewerContext.getJasperPrint().getPageHeight() + 1);
	}

	class PageRenderer extends JLabel
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		private boolean renderImage;

		public PageRenderer()
		{
		}

		public void paintComponent(Graphics g)
		{
			if (isRenderImage())
			{
				super.paintComponent(g);
			}
			else
			{
				paintPage((Graphics2D)g.create());
			}
		}

		public boolean isRenderImage()
		{
			return this.renderImage;
		}

		public void setRenderImage(boolean renderImage)
		{
			this.renderImage = renderImage;
		}
	}

	void pnlMainComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlMainComponentResized
		// Add your handling code here:
		if (this.viewerContext.isFitPage())
		{
			this.viewerContext.fitPage();
		}
		else if (this.viewerContext.isFitWidth())
		{
			this.viewerContext.fitWidth();
		}

	}//GEN-LAST:event_pnlMainComponentResized

	protected void fitPage()
	{
		float heightRatio = getPageCanvasHeight() / this.viewerContext.getJasperPrint().getPageHeight();
		float widthRatio = getPageCanvasWidth() / this.viewerContext.getJasperPrint().getPageWidth();
		setRealZoomRatio(heightRatio < widthRatio ? heightRatio : widthRatio);
	}

	protected void fitWidth()
	{
		setRealZoomRatio(getPageCanvasWidth() / this.viewerContext.getJasperPrint().getPageWidth());
	}

	protected float getPageCanvasWidth()
	{
		return (float) this.pnlInScroll.getVisibleRect().getWidth() - 20f;
	}

	protected float getPageCanvasHeight()
	{
		return (float) this.pnlInScroll.getVisibleRect().getHeight() - 20f;
	}

	void pnlLinksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMousePressed
		// Add your handling code here:
		this.pnlLinks.setCursor(new Cursor(Cursor.MOVE_CURSOR));

		this.downX = evt.getX();
		this.downY = evt.getY();
	}//GEN-LAST:event_pnlLinksMousePressed

	void pnlLinksMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMouseDragged
		// Add your handling code here:

		Container container = this.pnlInScroll.getParent();
		if (container instanceof JViewport)
		{
			JViewport viewport = (JViewport) container;
			Point point = viewport.getViewPosition();
			int newX = point.x - (evt.getX() - this.downX);
			int newY = point.y - (evt.getY() - this.downY);

			int maxX = this.pnlInScroll.getWidth() - viewport.getWidth();
			int maxY = this.pnlInScroll.getHeight() - viewport.getHeight();

			if (newX < 0)
			{
				newX = 0;
			}
			if (newX > maxX)
			{
				newX = maxX;
			}
			if (newY < 0)
			{
				newY = 0;
			}
			if (newY > maxY)
			{
				newY = maxY;
			}

			viewport.setViewPosition(new Point(newX, newY));
		}
	}//GEN-LAST:event_pnlLinksMouseDragged

	void pnlLinksMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLinksMouseReleased
		// Add your handling code here:
		this.pnlLinks.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}//GEN-LAST:event_pnlLinksMouseReleased

	protected void pageChanged()
	{
		if (this.viewerContext.hasPages())
		{
			this.pageError = false;
		}
	}

	protected void refreshPage()
	{
		if (!this.viewerContext.hasPages())
		{
			this.pnlPage.setVisible(false);
			
			if (this.viewerContext.getJasperPrint() != null)
			{
				JOptionPane.showMessageDialog(this, this.viewerContext.getBundleString("no.pages"));
			}

			return;
		}

		this.pnlPage.setVisible(true);

		Dimension dim = new Dimension(
			(int)(this.viewerContext.getJasperPrint().getPageWidth() * this.realZoom) + 8, // 2 from border, 5 from shadow and 1 extra pixel for image
			(int)(this.viewerContext.getJasperPrint().getPageHeight() * this.realZoom) + 8
			);
		this.pnlPage.setMaximumSize(dim);
		this.pnlPage.setMinimumSize(dim);
		this.pnlPage.setPreferredSize(dim);

		long maxImageSize = JRProperties.getLongProperty(JRViewer.VIEWER_RENDER_BUFFER_MAX_SIZE);
		boolean renderImage;
		if (maxImageSize <= 0)
		{
			renderImage = false;
		}
		else
		{
			long imageSize = JRPrinterAWT.getImageSize(this.viewerContext.getJasperPrint(), this.realZoom);
			renderImage = imageSize <= maxImageSize;
		}

		this.lblPage.setRenderImage(renderImage);

		if (renderImage)
		{
			setPageImage();
		}

		this.pnlLinks.removeAll();
		this.linksMap = new HashMap();

		createHyperlinks();

		if (!renderImage)
		{
			this.lblPage.setIcon(null);

			validate();
			repaint();
		}
	}

	protected void setPageImage()
	{
		Image image;
		if (this.pageError)
		{
			image = getPageErrorImage();
		}
		else
		{
			try
			{
				image = JasperPrintManager.printPageToImage(this.viewerContext.getJasperPrint(), this.viewerContext.getPageIndex(), this.realZoom);
			}
			catch (Exception e)
			{
				this.pageError = true;
				e.printStackTrace();

				image = getPageErrorImage();
				JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.displaying"));
			}
		}
		ImageIcon imageIcon = new ImageIcon(image);
		this.lblPage.setIcon(imageIcon);
	}

	protected Image getPageErrorImage()
	{
		Image image = new BufferedImage(
				(int) (this.viewerContext.getJasperPrint().getPageWidth() * this.realZoom) + 1,
				(int) (this.viewerContext.getJasperPrint().getPageHeight() * this.realZoom) + 1,
				BufferedImage.TYPE_INT_RGB
				);
		
		Graphics2D grx = (Graphics2D) image.getGraphics();
		AffineTransform transform = new AffineTransform();
		transform.scale(this.realZoom, this.realZoom);
		grx.transform(transform);

		drawPageError((Graphics2D) grx);
		
		return image;
	}

	protected void zoomChanged()
	{
		this.realZoom = this.viewerContext.getZoom() * this.screenResolution / REPORT_RESOLUTION;
	}

	protected void createHyperlinks()
	{
		java.util.List pages = this.viewerContext.getJasperPrint().getPages();
		JRPrintPage page = (JRPrintPage)pages.get(this.viewerContext.getPageIndex());
		createHyperlinks(page.getElements(), 0, 0);
	}

	protected void createHyperlinks(List elements, int offsetX, int offsetY)
	{
		if(elements != null && elements.size() > 0)
		{
			for(Iterator it = elements.iterator(); it.hasNext();)
			{
				JRPrintElement element = (JRPrintElement)it.next();

				JRImageMapRenderer imageMap = null;
				if (element instanceof JRPrintImage)
				{
					JRRenderable renderer = ((JRPrintImage) element).getRenderer();
					if (renderer instanceof JRImageMapRenderer)
					{
						imageMap = (JRImageMapRenderer) renderer;
						if (!imageMap.hasImageAreaHyperlinks())
						{
							imageMap = null;
						}
					}
				}
				boolean hasImageMap = imageMap != null;

				JRPrintHyperlink hyperlink = null;
				if (!hasImageMap && element instanceof JRPrintHyperlink)
				{
					hyperlink = (JRPrintHyperlink) element;
				}
				boolean hasHyperlink = hyperlink != null && hyperlink.getHyperlinkType() != JRHyperlink.HYPERLINK_TYPE_NONE;

				if (hasHyperlink || hasImageMap)
				{
					JPanel link;
					if (hasHyperlink)
					{
						link = new JPanel();
						link.addMouseListener(this.mouseListener);
					}
					else //hasImageMap
					{
						Rectangle renderingArea = new Rectangle(0, 0, element.getWidth(), element.getHeight());
						link = new ImageMapPanel(renderingArea, imageMap);
					}

					if (hasHyperlink)
					{
						link.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					link.setLocation(
						(int)((element.getX() + offsetX) * this.realZoom),
						(int)((element.getY() + offsetY) * this.realZoom)
						);
					link.setSize(
						(int)(element.getWidth() * this.realZoom),
						(int)(element.getHeight() * this.realZoom)
						);
					link.setOpaque(false);

					String toolTip;
					if (hasHyperlink)
					{
						toolTip = getHyperlinkTooltip(hyperlink);
					}
					else //hasImageMap
					{
						toolTip = "";//not null to register the panel as having a tool tip
					}
					link.setToolTipText(toolTip);

					this.pnlLinks.add(link);
					this.linksMap.put(link, element);
				}

				if (element instanceof JRPrintFrame)
				{
					JRPrintFrame frame = (JRPrintFrame) element;
					int frameOffsetX = offsetX + frame.getX() + frame.getLineBox().getLeftPadding().intValue();
					int frameOffsetY = offsetY + frame.getY() + frame.getLineBox().getTopPadding().intValue();
					createHyperlinks(frame.getElements(), frameOffsetX, frameOffsetY);
				}
			}
		}
	}


	protected class ImageMapPanel extends JPanel implements MouseListener, MouseMotionListener
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		protected final List imageAreaHyperlinks;

		public ImageMapPanel(Rectangle renderingArea, JRImageMapRenderer imageMap)
		{
			try
			{
				this.imageAreaHyperlinks = imageMap.getImageAreaHyperlinks(renderingArea);//FIXMECHART
			}
			catch (JRException e)
			{
				throw new JRRuntimeException(e);
			}

			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public String getToolTipText(MouseEvent event)
		{
			String tooltip = null;
			JRPrintImageAreaHyperlink imageMapArea = getImageMapArea(event);
			if (imageMapArea != null)
			{
				tooltip = getHyperlinkTooltip(imageMapArea.getHyperlink());
			}

			if (tooltip == null)
			{
				tooltip = super.getToolTipText(event);
			}

			return tooltip;
		}

		public void mouseDragged(MouseEvent e)
		{
			pnlLinksMouseDragged(e);
		}

		public void mouseMoved(MouseEvent e)
		{
			JRPrintImageAreaHyperlink imageArea = getImageMapArea(e);
			if (imageArea != null
					&& imageArea.getHyperlink().getHyperlinkType() != JRHyperlink.HYPERLINK_TYPE_NONE)
			{
				e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			else
			{
				e.getComponent().setCursor(Cursor.getDefaultCursor());
			}
		}

		protected JRPrintImageAreaHyperlink getImageMapArea(MouseEvent e)
		{
			return getImageMapArea((int) (e.getX() / JRViewerPanel.this.realZoom), (int) (e.getY() / JRViewerPanel.this.realZoom));
		}

		protected JRPrintImageAreaHyperlink getImageMapArea(int x, int y)
		{
			JRPrintImageAreaHyperlink image = null;
			if (this.imageAreaHyperlinks != null)
			{
				for (ListIterator it = this.imageAreaHyperlinks.listIterator(this.imageAreaHyperlinks.size()); image == null && it.hasPrevious();)
				{
					JRPrintImageAreaHyperlink area = (JRPrintImageAreaHyperlink) it.previous();
					if (area.getArea().containsPoint(x, y))
					{
						image = area;
					}
				}
			}
			return image;
		}

		public void mouseClicked(MouseEvent e)
		{
			JRPrintImageAreaHyperlink imageMapArea = getImageMapArea(e);
			if (imageMapArea != null)
			{
				hyperlinkClicked(imageMapArea.getHyperlink());
			}
		}

		public void mouseEntered(MouseEvent e)
		{
		}

		public void mouseExited(MouseEvent e)
		{
		}

		public void mousePressed(MouseEvent e)
		{
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			pnlLinksMousePressed(e);
		}

		public void mouseReleased(MouseEvent e)
		{
			e.getComponent().setCursor(Cursor.getDefaultCursor());
			pnlLinksMouseReleased(e);
		}
	}


	protected String getHyperlinkTooltip(JRPrintHyperlink hyperlink)
	{
		String toolTip;
		toolTip = hyperlink.getHyperlinkTooltip();
		if (toolTip == null)
		{
			toolTip = getFallbackTooltip(hyperlink);
		}
		return toolTip;
	}


	protected String getFallbackTooltip(JRPrintHyperlink hyperlink)
	{
		String toolTip = null;
		switch(hyperlink.getHyperlinkType())
		{
			case JRHyperlink.HYPERLINK_TYPE_REFERENCE :
			{
				toolTip = hyperlink.getHyperlinkReference();
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_ANCHOR :
			{
				if (hyperlink.getHyperlinkAnchor() != null)
				{
					toolTip = "#" + hyperlink.getHyperlinkAnchor();
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_LOCAL_PAGE :
			{
				if (hyperlink.getHyperlinkPage() != null)
				{
					toolTip = "#page " + hyperlink.getHyperlinkPage();
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_ANCHOR :
			{
				toolTip = "";
				if (hyperlink.getHyperlinkReference() != null)
				{
					toolTip = toolTip + hyperlink.getHyperlinkReference();
				}
				if (hyperlink.getHyperlinkAnchor() != null)
				{
					toolTip = toolTip + "#" + hyperlink.getHyperlinkAnchor();
				}
				break;
			}
			case JRHyperlink.HYPERLINK_TYPE_REMOTE_PAGE :
			{
				toolTip = "";
				if (hyperlink.getHyperlinkReference() != null)
				{
					toolTip = toolTip + hyperlink.getHyperlinkReference();
				}
				if (hyperlink.getHyperlinkPage() != null)
				{
					toolTip = toolTip + "#page " + hyperlink.getHyperlinkPage();
				}
				break;
			}
			default :
			{
				break;
			}
		}
		return toolTip;
	}

	void hyperlinkClicked(MouseEvent evt)
	{
		JPanel link = (JPanel)evt.getSource();
		JRPrintHyperlink element = (JRPrintHyperlink)this.linksMap.get(link);
		hyperlinkClicked(element);
	}

	protected void hyperlinkClicked(JRPrintHyperlink hyperlink)
	{
		try
		{
			JRHyperlinkListener listener = null;
			for(int i = 0; i < this.hyperlinkListeners.size(); i++)
			{
				listener = (JRHyperlinkListener)this.hyperlinkListeners.get(i);
				listener.gotoHyperlink(hyperlink);
			}
		}
		catch(JRException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, this.viewerContext.getBundleString("error.hyperlink"));
		}
	}

	protected void setRealZoomRatio(float newZoom)
	{
		if (newZoom > 0 && this.realZoom != newZoom)
		{
			float zoom = newZoom * REPORT_RESOLUTION / this.screenResolution;
			this.viewerContext.setZoomRatio(zoom);
		}
	}

	public void setFitWidthZoomRatio()
	{
		setRealZoomRatio(getPageCanvasWidth() / this.viewerContext.getJasperPrint().getPageWidth());
	}

	public void setFitPageZoomRatio()
	{
		setRealZoomRatio(getPageCanvasHeight() / this.viewerContext.getJasperPrint().getPageHeight());
	}

	protected void keyNavigate(KeyEvent evt)
	{
		boolean refresh = true;
		switch (evt.getKeyCode())
		{
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_PAGE_DOWN:
			dnNavigate(evt);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_PAGE_UP:
			upNavigate(evt);
			break;
		case KeyEvent.VK_HOME:
			homeEndNavigate(0);
			break;
		case KeyEvent.VK_END:
			homeEndNavigate(this.viewerContext.getPageCount() - 1);
			break;
		default:
			refresh = false;
		}
		
		if (refresh)
		{
			this.viewerContext.refreshPage();
		}
	}

	protected void dnNavigate(KeyEvent evt)
	{
		int bottomPosition = this.scrollPane.getVerticalScrollBar().getValue();
		this.scrollPane.dispatchEvent(evt);
		if((this.scrollPane.getViewport().getHeight() > this.pnlPage.getHeight() ||
				this.scrollPane.getVerticalScrollBar().getValue() == bottomPosition) &&
				this.viewerContext.getPageIndex() < this.viewerContext.getPageCount() - 1)
		{
			this.viewerContext.setPageIndex(this.viewerContext.getPageIndex() + 1);
			if(this.scrollPane.isEnabled())
				this.scrollPane.getVerticalScrollBar().setValue(0);
		}
	}

	protected void upNavigate(KeyEvent evt)
	{
		if((this.scrollPane.getViewport().getHeight() > this.pnlPage.getHeight() ||
				this.scrollPane.getVerticalScrollBar().getValue() == 0) &&
				this.viewerContext.getPageIndex() > 0)
		{
			this.viewerContext.setPageIndex(this.viewerContext.getPageIndex() - 1);
			if(this.scrollPane.isEnabled())
				this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum());
		}
		else
		{
			this.scrollPane.dispatchEvent(evt);
		}
	}

	protected void homeEndNavigate(int pageNumber)
	{
		this.viewerContext.setPageIndex(pageNumber);
		if(this.scrollPane.isEnabled())
			this.scrollPane.getVerticalScrollBar().setValue(0);
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
		}
	}
}
