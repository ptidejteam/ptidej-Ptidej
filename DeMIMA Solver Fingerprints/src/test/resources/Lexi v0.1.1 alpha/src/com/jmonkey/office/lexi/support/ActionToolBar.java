package com.jmonkey.office.lexi.support;

// Java API Improts
import java.awt.Insets;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public final class ActionToolBar extends JToolBar {
	/* registry of listeners created for Action-JButton
	* linkage.  This is needed so that references can
	* be cleaned up at remove time to allow GC.
	*/
	private static Hashtable listenerRegistry = null;

	/**
	* Add a new JButton which dispatches the action.
	*
	* @param a the Action object to add as a new menu item
	*/
	public JButton add(Action a) {
		JButton b =
			new JButton(
				(String) a.getValue(Action.NAME),
				(Icon) a.getValue(Action.SMALL_ICON));
		b.setHorizontalTextPosition(JButton.CENTER);
		b.setVerticalTextPosition(JButton.BOTTOM);
		b.setEnabled(a.isEnabled());
		b.addActionListener(a);
		add(b);
		PropertyChangeListener actionPropertyChangeListener =
			createActionChangeListener(b);
		if (listenerRegistry == null) {
			listenerRegistry = new Hashtable();
		}
		listenerRegistry.put(b, actionPropertyChangeListener);
		listenerRegistry.put(actionPropertyChangeListener, a);
		a.addPropertyChangeListener(actionPropertyChangeListener);
		return b;
	}
	/**
	* Add a new JButton which dispatches the action.
	*
	* @param a the Action object to add as a new menu item
	* @param showText true if the button should show the action text.
	*/
	public JButton add(boolean showText, Action a) {
		JButton b =
			showText
				? new JButton(
					(String) a.getValue(Action.NAME),
					(Icon) a.getValue(Action.SMALL_ICON))
				: new JButton((Icon) a.getValue(Action.SMALL_ICON));
		if (showText) {
			b.setHorizontalTextPosition(JButton.CENTER);
			b.setVerticalTextPosition(JButton.BOTTOM);
		}
		else {
			b.setMargin(new Insets(0, 0, 0, 0));
		}
		b.setEnabled(a.isEnabled());
		b.addActionListener(a);
		add(b);
		//	PropertyChangeListener actionPropertyChangeListener =
		//		createActionChangeListener(b);
		//	if (listenerRegistry == null) {
		//		listenerRegistry = new Hashtable();
		//	}
		//	listenerRegistry.put(b, actionPropertyChangeListener);
		//	listenerRegistry.put(actionPropertyChangeListener, a);
		//	a.addPropertyChangeListener(actionPropertyChangeListener);
		b.setAction(a);
		return b;
	}
}
