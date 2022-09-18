/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.gcd.ui.extension;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.beans.PropertyVetoException;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public final class GCDController extends JInternalFrame {
	private static final long serialVersionUID = 811135470546904244L;

	public static final int X_ORIGIN = 10;
	public static final int Y_ORIGIN = 10;
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;

	private static int gcdControllerNumber = -1;

	// This field plays part in an aggregation relationship:
	// The aggregation relationship between one instance of
	// class GCDController and instances of class GCD.
	private final Vector vectorOfGCDs = new Vector();

	public GCDController() {
		super("GCD Controller", false, true, true, true);
	}
	public void close() {
		// The user requests this internal frame to close.
		// The internal frame removes itself dynamically from
		// its enclosing container, without any reference on
		// this enclosing container. Then, it disposes of
		// itself.
		final Container parent = this.getParent();
		super.dispose();
		final GCDController gcdController = new GCDController();
		parent.add(gcdController);
		gcdController.initialize();
	}
	/*
	 * I need this initialize() method because the parent container of this
	 * widget is not set by Swing until all the widgets belong to a container.
	 * (See method ExtendedGCD.main(...).)
	 * I need the parent container to add a ContainerListener.
	 */
	public void initialize() {
		GCDController.gcdControllerNumber++;

		this.setBounds(
			GCDController.X_ORIGIN + 100 * GCDController.gcdControllerNumber,
			GCDController.Y_ORIGIN + 100 * GCDController.gcdControllerNumber,
			GCDController.WIDTH,
			GCDController.HEIGHT);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				GCDController.this.close();
			}
		});

		final DefaultListModel defaultListModel = new DefaultListModel();
		this.getContentPane().add(
			"Center",
			new JScrollPane(new JList(defaultListModel)));

		this.getParent().addContainerListener(new ContainerAdapter() {
			public void componentAdded(ContainerEvent e) {
				if (e.getChild() instanceof GCD) {
					final GCD gcd = (GCD) e.getChild();
					GCDController.this.vectorOfGCDs.add(gcd);
					defaultListModel.addElement(gcd.getName());
				}
			}
			public void componentRemoved(ContainerEvent e) {
				if (e.getChild() instanceof GCD) {
					final GCD gcd = (GCD) e.getChild();
					GCDController.this.vectorOfGCDs.remove(gcd);
					defaultListModel.removeElement(gcd.getName());
				}
				// throw new NullPointerException();
			}
		});

		final JButton addButton = new JButton("Add GCD");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GCDController.this.getParent().add(new GCD(), JLayeredPane.PALETTE_LAYER);
			}
		});
		this.getContentPane().add("North", addButton);

		final JButton removeButton = new JButton("Remove GCD");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					((GCD) GCDController.this.vectorOfGCDs.lastElement()).setClosed(true);
				}
				catch (NoSuchElementException nsee) {
				}
				catch (PropertyVetoException pve) {
				}
			}
		});
		this.getContentPane().add("South", removeButton);

		this.setVisible(true);
	}
}