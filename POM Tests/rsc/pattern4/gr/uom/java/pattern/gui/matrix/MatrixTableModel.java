package gr.uom.java.pattern.gui.matrix;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class MatrixTableModel extends AbstractTableModel {
	
	private String[] columnNames;
	private Object[][] data;
	private List<String> classNameList;
	
	public MatrixTableModel(double[][] matrix, List<String> classNameList) {
		this.classNameList = classNameList;
		columnNames = new String[matrix[0].length];
		data = new Integer[matrix.length][matrix[0].length];
		
		for(int i=0; i<matrix.length; i++) {
			columnNames[i] = classNameList.get(i);
			for(int j=0; j<matrix[i].length; j++) {
				if(matrix[i][j] != 0.0)
					data[i][j] = (int)matrix[i][j];
			}
		}
	}
	
	public List<String> getClassNameList() {
		return classNameList;
	}
	
	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
}