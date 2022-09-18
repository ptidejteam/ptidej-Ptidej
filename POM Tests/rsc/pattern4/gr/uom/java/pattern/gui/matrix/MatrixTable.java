package gr.uom.java.pattern.gui.matrix;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MatrixTable extends JTable {

	private JList rowHeader;
	private String[] columnToolTips;

    
    public MatrixTable(MatrixTableModel tableModel) {
        super(tableModel);
		
		List classNameList = tableModel.getClassNameList();
        DefaultListModel list = new DefaultListModel();
        columnToolTips = new String[classNameList.size()];
		
		for(int i=0; i<classNameList.size(); i++) {
            list.addElement(classNameList.get(i));
            columnToolTips[i] = (String)classNameList.get(i);
        }

        rowHeader = new JList(list);

        LookAndFeel.installColorsAndFont
            (rowHeader, "TableHeader.background", 
            "TableHeader.foreground", "TableHeader.font");


        rowHeader.setFixedCellHeight(this.getRowHeight());
        rowHeader.setCellRenderer(new RowHeaderRenderer());
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i=0; i<this.getColumnModel().getColumnCount(); i++)
			this.getColumnModel().getColumn(i).setPreferredWidth(15);
    }
    
    public String getToolTipText(MouseEvent e) {
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);
		
		return "(" + columnToolTips[rowIndex] + " , " + 
			columnToolTips[realColumnIndex] + ")";	
	}
	
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(columnModel) {
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);
                if(realColumnIndex >= 0)
                	tip = columnToolTips[realColumnIndex];
                return tip;
			}
		};
	}

	public JList getRowHeader() {
		return rowHeader;
	}
}