/*
 * $Id: JSelectorPanel.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version $Revision: 1.2 $
 */
package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class JSelectorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6222579792142511764L;
	JEventFieldSelector x_field = new JEventFieldSelector("x_field");
	JEventFieldSelector y_field = new JEventFieldSelector("y_field");
	JEventFieldSelector w_field = new JEventFieldSelector("w_field");
	JEventFieldSelector h_field = new JEventFieldSelector("h_field");

	public JSelectorPanel() {
		//setSize(new Dimension(200, 100));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final JPanel coordPane = new JPanel();
		coordPane.add(this.x_field);
		coordPane.add(this.y_field);
		coordPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		coordPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		coordPane.setPreferredSize(new Dimension(250, 40));
		coordPane.setMaximumSize(new Dimension(250, 40));

		final JPanel sizePane = new JPanel();
		sizePane.add(this.w_field);
		sizePane.add(this.h_field);
		sizePane.setAlignmentX(Component.CENTER_ALIGNMENT);
		sizePane.setAlignmentY(Component.TOP_ALIGNMENT);
		sizePane.setPreferredSize(new Dimension(250, 40));
		sizePane.setMaximumSize(new Dimension(250, 40));

		this.add(coordPane);
		this.add(sizePane);
		//        setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void register(final ActionListener c) {
		this.x_field.getSelector().addActionListener(c);
		this.y_field.getSelector().addActionListener(c);
		this.w_field.getSelector().addActionListener(c);
		this.h_field.getSelector().addActionListener(c);
	}
}