/*
 * $Id: JEventFilterPanel.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import fr.emn.oadymppac.Solver;

public class JEventFilterPanel extends JPanel {
	class MyCellRenderer extends JLabel implements ListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -593907946373955751L;
		public MyCellRenderer() {
			this.setOpaque(true);
		}
		public Component getListCellRendererComponent(
			final JList list,
			final Object value,
			final int index,
			final boolean isSelected,
			final boolean cellHasFocus) {
			this.setText(value.toString());
			try {
				this.setBackground(isSelected ? Color.black : EventColorManager
					.getEventColorManager(JEventFilterPanel.this.solver)
					.getColorByClass(
						Class
							.forName(
								"fr.emn.oadymppac.event." + value.toString())
							.getDeclaredConstructor().newInstance()));
				this.setForeground(isSelected ? Color.gray : Color.black);
			}
			catch (final ClassNotFoundException e) {
				System.out.println("Class not found : " + value.toString());
			}
			catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return this;
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4212213202120953369L;
	JList filterList;

	Solver solver;

	public JEventFilterPanel(final ListSelectionListener l) {
		final String[] labels =
			{ "ActivateEvent", "DeactivateEvent", "NewConstraintEvent",
					"NewVariableEvent", "ReduceEvent", "RejectEvent",
					"RestoreEvent", "SelectConstraintEvent",
					"SelectUpdateEvent", "SolutionEvent", "SuspendEvent",
					"TellEvent", "TrueEvent", "WakeUpEvent" };
		this.filterList = new JList(labels);
		this.filterList
			.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.filterList.setCellRenderer(new MyCellRenderer());
		this.add(this.filterList);
		this.register(l);
	}

	public JEventFilterPanel(final ListSelectionListener l, final Solver solver) {
		this(l);
		this.solver = solver;
	}

	public void register(final ListSelectionListener c) {
		this.filterList.addListSelectionListener(c);
	}
}