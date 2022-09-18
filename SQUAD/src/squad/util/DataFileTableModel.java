/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package squad.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class DataFileTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 5949510568846640279L;
	protected Vector<String> data;
	protected Vector<String> columnNames;
	protected String datafile;

	public DataFileTableModel(String f) {
		this.datafile = f;
		initVectors();
	}

	public void initVectors() {
		String aLine;
		this.data = new Vector<String>();
		this.columnNames = new Vector<String>();
		try {
			FileInputStream fin = new FileInputStream(this.datafile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			// extract column names
			StringTokenizer st1 = new StringTokenizer(br.readLine(), ",");
			while (st1.hasMoreTokens())
				this.columnNames.addElement(st1.nextToken());
			// extract data
			while ((aLine = br.readLine()) != null) {
				StringTokenizer st2 = new StringTokenizer(aLine, ",");
				while (st2.hasMoreTokens())
					this.data.addElement(st2.nextToken());
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount() {
		return this.data.size() / getColumnCount();
	}

	public int getColumnCount() {
		return this.columnNames.size();
	}

	public String getColumnName(int columnIndex) {
		String colName = "";

		if (columnIndex <= getColumnCount())
			colName = (String) this.columnNames.elementAt(columnIndex);

		return colName;
	}

	public Class<String> getColumnClass(int columnIndex) {
		return String.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return (String) this.data.elementAt((rowIndex * getColumnCount())
				+ columnIndex);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		return;
	}
}
