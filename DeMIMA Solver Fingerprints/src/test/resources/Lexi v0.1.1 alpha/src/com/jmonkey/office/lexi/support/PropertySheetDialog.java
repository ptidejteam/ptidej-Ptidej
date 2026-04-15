package com.jmonkey.office.lexi.support;


// java AWT Imports
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

// JMonkey Imports
//import com.jmonkey.core.Registry;

public final class PropertySheetDialog extends JDialog {
	private Properties _P = null;
	private Frame _PARENT = null;
	private boolean _ALLOW_ADD = false;
	private PairTableModel _MODEL = null;

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
	private PropertySheetDialog(Frame parent, Properties p, boolean allowAdd) {
		super(parent);
		this._PARENT = parent;
		this._P = p;
		this._ALLOW_ADD = allowAdd;
		this.init();
		this.pack();
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
	public static final Properties display(Frame parent, Properties p) {
		PropertySheetDialog psd = new PropertySheetDialog(parent, p, false);
		return psd.getProperties();
	}
	public static final Properties display(Frame parent, Properties p, boolean allowAdd) {
		PropertySheetDialog psd = new PropertySheetDialog(parent, p, allowAdd);
		return psd.getProperties();
	}
	private void doExit() {
		this.dispose();
	}
	protected final Properties getProperties() {
		return _P;
	}
	private void init() {
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JPanel spacerPanel = new JPanel();
		spacerPanel.setLayout(new GridLayout());
		if(_ALLOW_ADD) {
			JButton addButton = new JButton("Add Key");
			addButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												String inputValue = JOptionPane.showInputDialog("What is the key you want to add?");
											if(inputValue != null) {
												if(inputValue.trim().length() > 0) {
														_P.setProperty(inputValue, "");
														// redraw the table
													if(_MODEL != null) {
															_MODEL.fireTableDataChanged();
														}
													}
												}
											}
										});
			spacerPanel.add(addButton);
		}

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
							  			public void actionPerformed(ActionEvent e) {
							  				doExit();
							  			}
							  		});
		spacerPanel.add(closeButton);
		buttonPanel.add(spacerPanel, BorderLayout.EAST);

		content.add(buttonPanel, BorderLayout.SOUTH);

		_MODEL = new PairTableModel();
		content.add(new JScrollPane(new JTable(_MODEL)), BorderLayout.CENTER);

		this.setContentPane(content);
		// Added this to dispose of
		// the main app window when
		// it gets closed.
		this.addWindowListener(new WindowAdapter() {
					   			public void windowClosing(java.awt.event.WindowEvent e) {
					   				doExit();
					   			}
					   		});
	}
}
