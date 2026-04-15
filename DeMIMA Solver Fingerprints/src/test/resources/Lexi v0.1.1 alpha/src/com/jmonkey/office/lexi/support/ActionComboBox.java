package com.jmonkey.office.lexi.support;


import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

import javax.swing.Action;
import javax.swing.JComboBox;

public final class ActionComboBox extends JComboBox implements ItemListener {
	private final Hashtable _ACTIONS = new Hashtable();

	public ActionComboBox() {
		super();
		this.addItemListener(this);
	}
	public ActionComboBox(Action[] items) {
		super();
		for(int i = 0; i < items.length; i++) {
			this.addItem(items[i]);
		}
		this.addItemListener(this);
	}
	public void addItem(Action a) {
		if(a != null) {
			if(!_ACTIONS.containsKey((String)a.getValue(Action.NAME))) {
				_ACTIONS.put((String)a.getValue(Action.NAME), a);
				super.addItem((String)a.getValue(Action.NAME));
			}
		}
	}
	public Object getItemAt(int index) {
		String name = (String)super.getItemAt(index);
		if(_ACTIONS.containsKey(name)) {
			return ((Action)_ACTIONS.get(name));
		} else {
			return null;
		}
	}
	public void insertItemAt(Action a, int index) {
		if(a != null) {
			if(!_ACTIONS.containsKey((String)a.getValue(Action.NAME))) {
				_ACTIONS.put((String)a.getValue(Action.NAME), a);
				super.insertItemAt((String)a.getValue(Action.NAME), index);
			}
		}
	}
	public void itemStateChanged(ItemEvent e) {
		String name = (String)e.getItem();
		if(_ACTIONS.containsKey(name)) {
			((Action)_ACTIONS.get(name)).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, name));
		}
	}
	public void removeAllItems() {
		_ACTIONS.clear();
		super.removeAllItems();
	}
	public void removeItem(Object anObject) {
		throw new RuntimeException("Method removeItem(Object anObject) not implemented in " + ActionComboBox.class.getName() + ". User removeAllItems() instead.");
	}
	public void removeItemAt(int anIndex) {
		throw new RuntimeException("Method removeItemAt(int anIndex) not implemented in " + ActionComboBox.class.getName() + ". User removeAllItems() instead.");
	}
}
