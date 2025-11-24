package com.jmonkey.office.lexi.support;


// java AWT Imports
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

// JMonkey Imports
//import com.jmonkey.core.Registry;

public final class PropertySheet extends JPanel {
	private Properties _P = null;
	private Frame _PARENT = null;

	private final class PairTableModel extends AbstractTableModel {

		public PairTableModel() {
			super();
		}

		public int getRowCount() {
			return getProperties().size();
		}

		public int getColumnCount() {
			return 2;
		}

		public String getColumnName(int columnIndex) {
			switch(columnIndex) {
				case 0:
					return "Key";
				case 1:
					return "Value";
				default:
					return null;
			}
		}

		public Class getColumnClass(int columnIndex) {
			return java.lang.String.class;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			switch(columnIndex) {
				case 0:
					return false;
				case 1:
					return true;
				default:
					return false;
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
				case 0:
					return (String)getProperties().keySet().toArray()[rowIndex].toString();
				case 1:
					return (String)getProperties().getProperty((String)getProperties().keySet().toArray()[rowIndex].toString());
				default:
					return "";
			}
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			switch(columnIndex) {
				case 0:
					//getProperties().keySet().toArray()[rowIndex] = aValue.toString();
					break;
				case 1:
					getProperties().setProperty((String)getProperties().keySet().toArray()[rowIndex].toString(), aValue.toString());
					break;
			}
		}
	}
	public PropertySheet(Frame parent, Properties p) {
		super();
		this._PARENT = parent;
		this._P = p;
		this.init();
	}
	public final Properties getProperties() {
		return _P;
	}
	private void init() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(new JTable(new PairTableModel())), BorderLayout.CENTER);
	}
}
