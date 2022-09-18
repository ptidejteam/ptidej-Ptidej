/*
 * $Id: JEventFieldSelector.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

package fr.emn.oadymppac.widgets;

import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import fr.emn.oadymppac.event.BasicSolverEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version $Revision: 1.2 $
 */

public class JEventFieldSelector extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3831822169233175751L;
	JComboBox selector;
	JLabel label = new JLabel("No Label");

	Vector eventAtts = new Vector();

	public JEventFieldSelector(final BasicSolverEvent event) {
		// retrieve the event fields using reflexion API
	}

	public JEventFieldSelector(final String str) {
		this.eventAtts.addElement("");
		this.eventAtts.addElement("n");
		this.eventAtts.addElement("depth");
		this.eventAtts.addElement("time");
		this.eventAtts.addElement("class");
		this.setLabel(str);
		this.setupSelector();
		this.add(this.label);
		this.add(this.selector);
	}
	public String getLabel() {
		return this.label.getText();
	}
	public String getSelection() {
		return this.selector.getSelectedItem().toString();
	}
	public JComboBox getSelector() {
		return this.selector;
	}
	public void setLabel(final String str) {
		this.label.setText(str);
	}

	public void setupSelector() {
		this.selector = new JComboBox(this.eventAtts);
	}

	public void setupSelector(
		final JComboBox selector,
		final BasicSolverEvent event) {

	}
}