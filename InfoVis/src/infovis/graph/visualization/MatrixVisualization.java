/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.graph.visualization;

import infovis.Column;
import infovis.Graph;
import infovis.column.BooleanColumn;
import infovis.column.ColumnComparator;
import infovis.column.FilterColumn;
import infovis.column.IntColumn;
import infovis.column.filter.NotTypedFilter;
import infovis.graph.InDegree;
import infovis.graph.OutDegree;
import infovis.utils.IntVector;
import infovis.utils.Permutation;
import infovis.utils.PermutedIterator;
import infovis.utils.RowComparator;
import infovis.utils.RowIterator;
import infovis.utils.TableIterator;
import infovis.visualization.DefaultVisualColumn;

import java.awt.geom.Rectangle2D;

import javax.swing.event.ChangeEvent;


/**
 * Graph Visualization using Adjacency Matrix representation.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class MatrixVisualization extends GraphVisualization {
    public static final String VISUAL_VERTEX_LABEL = "vertexLabel";
    public static final String VISUAL_ROW_SELECTION = "rowSelection";
    public static final String VISUAL_ROW_SORT = "rowSort";
    public static final String VISUAL_ROW_FILTER = "rowFilter";
    public static final String VISUAL_COLUMN_SELECTION = "columnSelection";
    public static final String VISUAL_COLUMN_FILTER = "columnFilter";
    public static final String VISUAL_COLUMN_SORT = "columnSort";
    /** Name of the column containing the rowSelection */
    public static final String ROWSELECTION_COLUMN = SELECTION_COLUMN; //"#rowSelection";
    /** Name of the column containing the columnSelection */
    public static final String COLUMNSELECTION_COLUMN = "#columnSelection";
    /** Name of the column containing the rowFilter */
    public static final String ROWFILTER_COLUMN = FILTER_COLUMN;
    /** Name of the column containing the columnFilter */
    public static final String COLUMNFILTER_COLUMN = "#columnFilter";
    /** Name of the IntColumn containing the rowPermutation */
    public static final String ROWPERMUTATION_COLUMN = PERMUTATION_COLUMN;
    /** Name of the optional IntColumn managing the inverse permutation. */
    public static final String INVERSEROWPERMUTATION_COLUMN = INVERSEPERMUTATION_COLUMN;
    /** Name of the IntColumn containing the columnPermutation */
    public static final String COLUMNPERMUTATION_COLUMN = "#columnPermutation";
    /** Name of the optional IntColumn managing the inverse permutation. */
    public static final String INVERSECOLUMNPERMUTATION_COLUMN = "#inverseColumnPermutation";
    protected int              leftMargin = 30;
    protected int              topMargin = 30;
    protected Column           vertexLabelColumn;
    protected BooleanColumn    rowSelection;
    protected FilterColumn     rowFilter;
    protected Column           rowSortColumn;
    protected Permutation      rowPermutation;
    protected RowComparator    rowComparator;
    protected IntVector        rowPosition;
    protected boolean          filteredRowVisible = true;
    protected BooleanColumn    columnSelection;
    protected FilterColumn     columnFilter;
    protected Column           columnSortColumn;
    protected Permutation      columnPermutation;
    protected RowComparator    columnComparator;
    protected IntVector        columnPosition;
    protected boolean          filteredColumnVisible = true;
    protected boolean         squared;
    
    static {
        setControlPanelCreator(MatrixVisualization.class, MatrixControlPanel.class);
    }

    /**
     * Constructor for MatrixVisualization.
     *
     * @param graph the graph.
     */
    public MatrixVisualization(Graph graph) {
        super(graph);
        setRowSelection(BooleanColumn.findColumn(graph.getVertexTable(),
            ROWSELECTION_COLUMN));
        setColumnSelection(BooleanColumn.findColumn(graph.getVertexTable(),
                                                    COLUMNSELECTION_COLUMN));
        setRowFilter(FilterColumn.findColumn(graph.getVertexTable(),
            ROWFILTER_COLUMN));
        setColumnFilter(FilterColumn.findColumn(graph.getVertexTable(),
            COLUMNFILTER_COLUMN));
        OutDegree.getColumn(graph); // create a maintained outDegree column.
        InDegree.getColumn(graph);
    }
    
    /**
     * @see infovis.Visualization#declareVisualColumns()
     */
    protected void declareVisualColumns() {
        super.declareVisualColumns();
        putVisualColumn(VISUAL_VERTEX_LABEL,
            new DefaultVisualColumn(false) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    MatrixVisualization.this.vertexLabelColumn = column;
                }
        });
        putVisualColumn(VISUAL_ROW_SELECTION, 
            new DefaultVisualColumn(false, new NotTypedFilter(BooleanColumn.class)) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    MatrixVisualization.this.rowSelection = (BooleanColumn)column;
                }
        });
        putVisualColumn(VISUAL_ROW_SORT, 
            new DefaultVisualColumn(true) {
                public void setColumn(Column column) {
                    assert (column== null || MatrixVisualization.this.graph.getVertexTable().indexOf(column) != -1);
                    super.setColumn(column);
                    MatrixVisualization.this.rowSortColumn = column;
                    MatrixVisualization.this.rowComparator = column;
                    permuteRowRows();
                }
        });
        putVisualColumn(VISUAL_ROW_FILTER, 
            new DefaultVisualColumn(false, new NotTypedFilter(FilterColumn.class)) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    MatrixVisualization.this.rowFilter = (FilterColumn)column;
                }
        });
        putVisualColumn(VISUAL_COLUMN_SELECTION,
            new DefaultVisualColumn(false, new NotTypedFilter(BooleanColumn.class)) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    MatrixVisualization.this.columnSelection = (BooleanColumn)column;
                }
        });
        putVisualColumn(VISUAL_COLUMN_SORT,
            new DefaultVisualColumn(true) {
                public void setColumn(Column column) {
                    assert (column== null || MatrixVisualization.this.graph.getEdgeTable().indexOf(column) != -1);
                    super.setColumn(column);
                    MatrixVisualization.this.columnSortColumn = column;
                    MatrixVisualization.this.columnComparator = column;
                    permuteColumnColumns();
                }
        });
        putVisualColumn(VISUAL_COLUMN_FILTER,
            new DefaultVisualColumn(false, new NotTypedFilter(FilterColumn.class)) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    MatrixVisualization.this.columnFilter = (FilterColumn)column;
                }
        });
    }

    /**
     * Returns the leftMargin.
     *
     * @return int
     */
    public int getLeftMargin() {
        return this.leftMargin;
    }

    /**
     * Sets the leftMargin.
     *
     * @param leftMargin The leftMargin to set
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * Returns the topMargin.
     *
     * @return int
     */
    public int getTopMargin() {
        return this.topMargin;
    }

    /**
     * Sets the topMargin.
     *
     * @param topMargin The topMargin to set
     */
    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    /**
     * @see infovis.Visualization#computeShapes(Rectangle2D)
     */
    public void computeShapes(Rectangle2D bounds) {
        if (getColumnPositionCount() == 0 || getRowPositionCount() == 0)
            return;
        double w = (bounds.getWidth() - this.leftMargin) / getColumnPositionCount();
        double h = (bounds.getHeight() - this.topMargin) / getRowPositionCount();

        if (this.squared) {
            if (w < h) {
                h = w;
            } else {
                w = h;
            }
        }
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int edge = iter.nextRow();

            int v1 = this.graph.getInVertex(edge);
            int row = getRowPosition(v1);
            if (row == -1) {
                setShapeAt(edge, null);
                continue;
            }

            int v2 = this.graph.getOutVertex(edge);
            int col = getColumnPosition(v2);
            if (col == -1) {
                setShapeAt(edge, null);
                continue;
            }

            Rectangle2D.Double s = (Rectangle2D.Double)getShapeAt(edge);
            if (s == null) {
                s = new Rectangle2D.Double(w * col + this.leftMargin,
                                           h * row + this.topMargin, w, h);
                setShapeAt(edge, s);
            } else {
                s.setRect(w * col + this.leftMargin, h * row + this.topMargin, w, h);
            }
        }
    }
    
    /**
     * @see infovis.Visualization#isFiltered(int)
     */
    public boolean isFiltered(int edge) {
        if (super.isFiltered(edge))
            return true;
        int row = this.graph.getInVertex(edge);
        if (isRowFiltered(row))
            return true;
        int col = this.graph.getOutVertex(edge);
        return isColumnFiltered(col);
    }


    public String getLabelAt(int edge) {
        if (this.labelColumn == null) {
            if (this.vertexLabelColumn == null)
                return null;
            int inVertex = this.graph.getInVertex(edge);
            int outVertex = this.graph.getOutVertex(edge);
            return this.vertexLabelColumn.getValueAt(inVertex) + "--" +
                   this.vertexLabelColumn.getValueAt(outVertex);
        }
        return this.labelColumn.getValueAt(edge);
    }

    /**
     * Returns the vertexLabelColumn.
     *
     * @return Column
     */
    public Column getVertexLabelColumn() {
        return this.vertexLabelColumn;
    }

    /**
     * Sets the vertexLabelColumn.
     *
     * @param vertexLabelColumn The vertexLabelColumn to set.
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setVertexLabelColumn(Column vertexLabelColumn) {
        return changeManagedColumn(VISUAL_VERTEX_LABEL, this.vertexLabelColumn, vertexLabelColumn);
    }

    // Row permutation management
    /**
     * Returns the rowSelection.
     *
     * @return BooleanColumn
     */
    public BooleanColumn getRowSelection() {
        return this.rowSelection;
    }

    /**
     * Sets the rowSelection.
     *
     * @param rowSelection The rowSelection to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setRowSelection(BooleanColumn rowSelection) {
        return changeManagedColumn(VISUAL_ROW_SELECTION, this.rowSelection, rowSelection);
    }

    /**
     * Returns the rowFilter.
     *
     * @return FilterColumn
     */
    public FilterColumn getRowFilter() {
        return this.rowFilter;
    }

    /**
     * Sets the rowFilter.
     *
     * @param rowFilter The rowFilter to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setRowFilter(FilterColumn rowFilter) {
        return changeManagedColumn(VISUAL_ROW_FILTER, this.rowFilter, rowFilter);
    }

    /**
     * Returns <code>true</code> if the specified row is filtered.
     *
     * @param row the row.
     *
     * @return <code>true</code> if the row is filtered.
     */
    public boolean isRowFiltered(int row) {
        return this.rowFilter != null && this.rowFilter.isFiltered(row);
    }
    
    public RowIterator vertexIterator() {
        return this.graph.vertexIterator();
    }


    /**
     * Returns the sortColumn.
     *
     * @return Column
     */
    public Column getRowSortColumn() {
        return this.rowSortColumn;
    }

    /**
     * Sets the sortColumn.
     *
     * @param rowSortColumn The sortColumn to set.
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setRowSortColumn(Column rowSortColumn) {
        return changeManagedColumn(VISUAL_ROW_SORT, this.rowSortColumn, rowSortColumn);
    }

    /**
     * Compute the rowPermutation according to the current comparator.
     */
    protected void permuteRowRows() {
        RowComparator comp = this.rowComparator;
        if (comp == null) {
            this.rowPermutation = null;
            this.rowPosition = null;
            return;
        }
        if (this.rowPermutation == null) {
            this.rowPermutation = new Permutation(IntColumn.findColumn(this.graph.getVertexTable(),
                                                                  ROWPERMUTATION_COLUMN),
                                             IntColumn.findColumn(this.graph.getVertexTable(),
                                                                  INVERSEROWPERMUTATION_COLUMN),
                                             this.graph.getVertexTable().getRowCount());
        }
        this.rowPermutation.sort(this.graph.getVertexTable().getRowCount(), comp);
        hideFilteredRowPositions();
    }

    /**
     * Returns the row permutation.
     *
     * @return the row permutation.
     */
    public Permutation getRowPermutation() {
        return this.rowPermutation;
    }

    /**
     * Returns the current comparator.
     *
     * @return the current comparator.
     */
    public RowComparator getRowComparator() {
        return this.rowComparator;
    }

    /**
     * Sets the row comparator.
     *
     * @param rowComparator The row comparator to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setRowComparator(RowComparator rowComparator) {
        if (this.rowComparator != rowComparator) {
            setRowSortColumn(null);
            this.rowComparator = rowComparator;
            permuteRowRows();
            invalidate();
            return true;
        }
        return false;
    }

    /**
     * Returns an iterator over the permuted rows.
     *
     * @return an iterator over the permuted rows.
     */
    public RowIterator rowIterator() {
        if (this.rowPermutation != null)
            return new PermutedIterator(0, this.rowPermutation);
        else
            return new TableIterator(0, this.graph.getVertexTable().getRowCount());
    }

    /**
     * Hide the filtered row positions.
     */
    public void hideFilteredRowPositions() {
        if (this.rowPosition == null) {
            this.rowPosition = new IntVector(this.graph.getVertexTable().getRowCount());
        }
        this.rowPosition.resize(this.graph.getVertexTable().getRowCount());
        int position = 0;
        for (RowIterator iter = rowIterator(); iter.hasNext();) {
            int row = iter.nextRow();
            if (!this.filteredRowVisible && isRowFiltered(row)) {
                this.rowPosition.set(row, -1);
            } else {
                this.rowPosition.set(row, position++);
            }
        }
        invalidate();
    }

    /**
     * DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRowPosition(int row) {
        if (this.rowPosition != null)
            return this.rowPosition.get(row);
        return row;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRowPositionCount() {
        if (this.rowPosition != null)
            return this.rowPosition.size();
        return this.graph.getVertexTable().getRowCount();
    }

    /**
     * Returns the filteredRowVisible.
     *
     * @return boolean
     */
    public boolean isFilteredRowVisible() {
        return this.filteredRowVisible;
    }

    /**
     * Sets the filteredRowVisible.
     *
     * @param filteredRowVisible The filteredRowVisible to set.
     *
     * @return <code>true</code> if the variable has been set.
     */
    public boolean setFilteredRowVisible(boolean filteredRowVisible) {
        if (this.filteredRowVisible != filteredRowVisible) {
            this.filteredRowVisible = filteredRowVisible;
            hideFilteredRowPositions();
            return true;
        }
        return false;
    }

    // Column permutation management
    /**
     * Returns the columnSelection.
     *
     * @return BooleanColumn
     */
    public BooleanColumn getColumnSelection() {
        return this.columnSelection;
    }

    /**
     * Sets the columnSelection.
     *
     * @param columnSelection The columnSelection to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setColumnSelection(BooleanColumn columnSelection) {
        return changeManagedColumn(VISUAL_COLUMN_SELECTION, this.columnSelection, columnSelection);
    }

    /**
     * Returns the columnFilter.
     *
     * @return FilterColumn
     */
    public FilterColumn getColumnFilter() {
        return this.columnFilter;
    }

    /**
     * Sets the columnFilter.
     *
     * @param columnFilter The columnFilter to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setColumnFilter(FilterColumn columnFilter) {
        return changeManagedColumn(VISUAL_COLUMN_FILTER, this.columnFilter, columnFilter);
    }

    /**
     * Returns <code>true</code> if the specified column is filtered.
     *
     * @param column the column.
     *
     * @return <code>true</code> if the column is filtered.
     */
    public boolean isColumnFiltered(int column) {
        return this.columnFilter != null && this.columnFilter.isFiltered(column);
    }

    /**
     * Returns the sortColumn.
     *
     * @return Column
     */
    public Column getColumnSortColumn() {
        return this.columnSortColumn;
    }

    /**
     * Sets the sortColumn.
     *
     * @param columnSortColumn The sortColumn to set.
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setColumnSortColumn(Column columnSortColumn) {
        return changeManagedColumn(VISUAL_COLUMN_SORT, this.columnSortColumn, columnSortColumn);
    }

    /**
     * Compute the columnPermutation according to the current comparator.
     */
    protected void permuteColumnColumns() {
        RowComparator comp = this.columnComparator;
        if (comp == null) {
            this.columnPermutation = null;
            this.columnPosition = null;
            return;
        }
        if (this.columnPermutation == null) {
            this.columnPermutation = new Permutation(IntColumn.findColumn(this.graph.getVertexTable(),
                                                                     COLUMNPERMUTATION_COLUMN),
                                                IntColumn.findColumn(this.graph.getVertexTable(),
                                                                     INVERSECOLUMNPERMUTATION_COLUMN),
                                                this.graph.getVertexTable().getRowCount());
        }
        this.columnPermutation.sort(this.graph.getVertexTable().getRowCount(), comp);
        hideFilteredColumnPositions();
    }

    /**
     * Returns the column permutation.
     *
     * @return the column permutation.
     */
    public Permutation getColumnPermutation() {
        return this.columnPermutation;
    }

    /**
     * Returns the current comparator.
     *
     * @return the current comparator.
     */
    public RowComparator getColumnComparator() {
        return this.columnComparator;
    }

    /**
     * Sets the column comparator.
     *
     * @param columnComparator The column comparator to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setColumnComparator(ColumnComparator columnComparator) {
        if (this.columnComparator != columnComparator) {
            setColumnSortColumn(null);
            this.columnComparator = columnComparator;
            permuteColumnColumns();
            invalidate();
            return true;
        }
        return false;
    }

    /**
     * Returns an iterator over the permuted columns.
     *
     * @return an iterator over the permuted columns.
     */
    public RowIterator columnIterator() {
        if (this.columnPermutation != null)
            return new PermutedIterator(0, this.columnPermutation);
        else
            return new TableIterator(0, this.graph.getVertexTable().getRowCount());
    }

    /**
     * Hide the filtered column positions.
     */
    public void hideFilteredColumnPositions() {
        if (this.columnPosition == null) {
            this.columnPosition = new IntVector(this.graph.getVertexTable().getRowCount());
        }
        this.columnPosition.resize(this.graph.getVertexTable().getRowCount());
        int position = 0;
        for (RowIterator iter = columnIterator(); iter.hasNext();) {
            int column = iter.nextRow();
            if (!this.filteredColumnVisible && isColumnFiltered(column)) {
                this.columnPosition.set(column, -1);
            } else {
                this.columnPosition.set(column, position++);
            }
        }
        invalidate();
    }

    /**
     * Returns the position of the specified column or -1 if it is hidden.
     *
     * @param col the column.
     *
     * @return the position of the specified column or -1 if it is hidden.
     */
    public int getColumnPosition(int col) {
        if (this.columnPosition != null)
            return this.columnPosition.get(col);
        return col;
    }

    /**
     * Returns the count of visible column positions.
     *
     * @return the count of visible column positions.
     */
    public int getColumnPositionCount() {
        if (this.columnPosition != null)
            return this.columnPosition.size();
        return this.graph.getVertexTable().getRowCount();
    }

    /**
     * Returns the filteredColumnVisible.
     *
     * @return boolean
     */
    public boolean isFilteredColumnVisible() {
        return this.filteredColumnVisible;
    }

    /**
     * Sets the filteredColumnVisible.
     *
     * @param filteredColumnVisible The filteredColumnVisible to set
     *
     * @return <code>true</code> if the variable has been set.
     */
    public boolean setFilteredColumnVisible(boolean filteredColumnVisible) {
        if (this.filteredColumnVisible != filteredColumnVisible) {
            this.filteredColumnVisible = filteredColumnVisible;
            hideFilteredColumnPositions();
            return true;
        }
        return false;
    }
    /**
     * Returns the squared.
     * @return boolean
     */
    public boolean isSquared() {
        return this.squared;
    }

    /**
     * Sets the squared.
     * @param squared The squared to set
     */
    public void setSquared(boolean squared) {
        if (this.squared != squared) {
            this.squared = squared;
            invalidate();
        }
    }

    public void stateChanged(ChangeEvent e) {
        // Synchronize the rowSelection and columnSelection with the edgeSelection.
        if (e.getSource() == this.selection) {
            try {
                this.columnSelection.disableNotify();
                this.rowSelection.disableNotify();
                this.columnSelection.clear();
                this.rowSelection.clear();
                for (int index = this.selection.getMinSelectionIndex();
                         index <= this.selection.getMaxSelectionIndex();
                         index++) {
                    if (this.selection.isSelectedIndex(index)) {
                        int row = this.graph.getInVertex(index);
                        if (row != -1)
                            this.rowSelection.setExtend(row, true);
                        int column = this.graph.getOutVertex(index);
                        if (column != -1)
                            this.columnSelection.setExtend(column, true);
                    }
                }
            } finally {
                this.columnSelection.enableNotify();
                this.rowSelection.enableNotify();
            }
        } else if (e.getSource() == this.columnFilter) {
            hideFilteredColumnPositions();
        } else if (e.getSource() == this.rowFilter) {
            hideFilteredRowPositions();
        }
        super.stateChanged(e);
    }

}
