/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.graph.visualization;

import infovis.Graph;
import infovis.column.filter.ComposeExceptFilter;
import infovis.column.filter.ExceptNamed;
import infovis.column.filter.InternalFilter;
import infovis.graph.InDegree;
import infovis.graph.OutDegree;
import infovis.panel.ControlPanel;
import infovis.panel.DetailTable;
import infovis.panel.DynamicQueryPanel;
import infovis.table.FilteredTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

/**
 * Control panel for a Matrix BasicVisualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class MatrixControlPanel extends ControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DetailTable rowDetail;
    protected DetailTable columnDetail;
    protected DynamicQueryPanel rowQueryPanel;
    protected DynamicQueryPanel columnQueryPanel;
    protected FilteredTable filteredGraph;
    protected JCheckBox hideFiltered;
    protected JCheckBox squareEdges;

    /**
     * Constructor for MatrixControlPanel.
     *
     * @param visualization
     */
    public MatrixControlPanel(MatrixVisualization visualization) {
        super(visualization);
    }

    /**
     * Returns the MatrixVisualization.
     *
     * @return the MatrixVisualization.
     */
    public MatrixVisualization getMatrix() {
        return (MatrixVisualization) getVisualization();
    }

    /**
     * Returns the Graph
     *
     * @return Return the Graph
     */
    public Graph getGraph() {
        return getMatrix().getGraph();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public FilteredTable getFilteredGraph() {
        if (this.filteredGraph == null) {
            this.filteredGraph =
                new FilteredTable(
                    getGraph().getVertexTable(),
                    new ComposeExceptFilter(
                        new ExceptNamed(OutDegree.OUTDEGREE_COLUMN),
                        new ComposeExceptFilter(
                            new ExceptNamed(InDegree.INDEGREE_COLUMN),
                            InternalFilter.sharedInstance())));
        }
        return this.filteredGraph;
    }

    /**
     * @see infovis.panel.ControlPanel#createDetailPane()
     */
    protected JComponent createDetailPane() {
        MatrixVisualization matrix = getMatrix();

        Box stack = new Box(BoxLayout.Y_AXIS);
        JComponent detail = super.createDetailPane();
        detail.setBorder(
            BorderFactory.createTitledBorder("Edge details"));
        stack.add(detail);

        this.rowDetail =
            new DetailTable(
                getFilteredGraph(),
                matrix.getRowSelection());
        JTable rowJTable = new JTable(this.rowDetail);
        JScrollPane rowJScroll = new JScrollPane(rowJTable);
        rowJScroll.setBorder(
            BorderFactory.createTitledBorder("Row details"));
        stack.add(rowJScroll);

        this.columnDetail =
            new DetailTable(
                getFilteredGraph(),
                matrix.getColumnSelection());
        JTable columnJTable = new JTable(this.columnDetail);
        JScrollPane columnJScroll = new JScrollPane(columnJTable);
        columnJScroll.setBorder(
            BorderFactory.createTitledBorder("Column details"));
        stack.add(columnJScroll);
        return stack;
    }

    /**
     * @see infovis.panel.ControlPanel#createFiltersPane()
     */
    protected JComponent createFiltersPane() {
        Box stack = new Box(BoxLayout.Y_AXIS);
        JComponent filters = super.createFiltersPane();
        filters.setBorder(
            BorderFactory.createTitledBorder("Edge dynamic queries"));
        stack.add(filters);

        Box checkBoxes = new Box(BoxLayout.X_AXIS);

        this.hideFiltered =
            new JCheckBox(
                "Hide Filtereed",
                !getMatrix().isFilteredRowVisible());
        this.hideFiltered.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMatrix().setFilteredColumnVisible(
                    !MatrixControlPanel.this.hideFiltered.isSelected());
                getMatrix().setFilteredRowVisible(
                    !MatrixControlPanel.this.hideFiltered.isSelected());
            }
        });
        checkBoxes.add(this.hideFiltered);

        this.squareEdges =
            new JCheckBox("Square Edges", getMatrix().isSquared());
        this.squareEdges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMatrix().setSquared(MatrixControlPanel.this.squareEdges.isSelected());
            }
        });
        checkBoxes.add(this.squareEdges);
        stack.add(checkBoxes);

        this.rowQueryPanel =
            new DynamicQueryPanel(
                getVisualization(),
                getGraph().getVertexTable(),
                getMatrix().getRowFilter(),
                getFilteredGraph().getFilter());
        JScrollPane rowQueryScroll =
            new JScrollPane(
                this.rowQueryPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        rowQueryScroll.setBorder(
            BorderFactory.createTitledBorder("Row dynamic queries"));
        stack.add(rowQueryScroll);

        this.columnQueryPanel =
            new DynamicQueryPanel(
                getVisualization(),
                getGraph().getVertexTable(),
                getMatrix().getColumnFilter(),
                getFilteredGraph().getFilter());
        JScrollPane columnQueryScroll =
            new JScrollPane(
                this.columnQueryPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        columnQueryScroll.setBorder(
            BorderFactory.createTitledBorder("Column dynamic queries"));
        stack.add(columnQueryScroll);

        return stack;
    }

    /**
     * @see infovis.panel.ControlPanel#createStdVisualPane()
     */
    protected JComponent createStdVisualPane() {
        MatrixVisualPanel visualPanel =
            new MatrixVisualPanel(
                getMatrix(),
                getFilteredGraph().getFilter());
        return visualPanel;
    }

}
