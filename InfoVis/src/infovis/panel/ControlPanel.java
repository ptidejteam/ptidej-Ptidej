/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Table;
import infovis.Visualization;
import infovis.column.ColumnFilter;
import infovis.column.filter.InternalFilter;
import infovis.table.FilteredTable;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;


/**
 * Control Panel for showing selectio, dynamic queries and eveything
 * else.
 *
 * Should evolve with new components.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ControlPanel extends JSplitPane {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Visualization               visualization;
    Table                       table;
    VisualizationPanel          visualizationPanel;
    FilteredTable               filteredTable;
    protected JTable            jtable;
    protected JComponent        detail;
    protected JTabbedPane       tabs;
    protected DynamicQueryPanel dynamicQueryPanel;
    protected JComponent    stdVisual;
    protected ExcentricLabelControlPanel excentric;
    protected FisheyesControlPanel     fisheyes;
    /**
     * Constructor for ControlPanel.
     */
    public ControlPanel(Visualization visualization) {
        this(visualization, InternalFilter.sharedInstance());
    }
    
    /**
     * Constructor for ControlPanel.
     */
    public ControlPanel(Visualization visualization, ColumnFilter filter) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.visualization = visualization;
        this.table = visualization.getTable();
        this.visualizationPanel = new VisualizationPanel(visualization);
        this.filteredTable = new FilteredTable(this.table, filter);

        JSplitPane hsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.detail = createDetailPane();
        hsplit.setTopComponent(this.detail);
        this.tabs = new JTabbedPane();
        hsplit.setBottomComponent(this.tabs);
        //hsplit.setResizeWeight(1);
        hsplit.setDividerLocation(200);

        this.tabs.add("Filters", createFiltersPane());
        this.tabs.add("Visual", createStdVisualPane());
        createOtherTabs();
        
//        AGLCanvas canvas = new AGLCanvas();
//        canvas.getContentPane().add(visualizationPanel);
//
//        setLeftComponent(canvas);
        setLeftComponent(this.visualizationPanel);
        setRightComponent(hsplit);
        setResizeWeight(1);
    }
    
    public void dispose() {
        this.visualization.setParent(null);
        this.visualization.dispose();
        this.visualization = null;
    }

    /**
     * Creates other tables required by the Visualization.
     */
    protected void createOtherTabs() {
        this.tabs.add("Excentric", createExcentricPane());
        this.tabs.add("Fisheyes", createFisheyesPane());
    }
    
    /**
     * Creates a tab for changing the Excentric Labels paramters.
     */
    protected JComponent createExcentricPane() {
        this.excentric = new ExcentricLabelControlPanel(this.visualization);
        return this.excentric;
    }
    
    /**
     * Creates a tab for changing the Fisheyes paramters.
     */
    protected JComponent createFisheyesPane() {
        this.fisheyes = new FisheyesControlPanel(this.visualization);
        return this.fisheyes;
    }

    /**
     * Creates a tab for showing selected items on a table.
     */
    protected JComponent createDetailPane() {
        DetailTable detail = new DetailTable(getFilteredTable(),
                                      this.visualization.getSelection());
        this.jtable = new JTable(detail);
        //jtable.setPreferredScrollableViewportSize(new Dimension(450, 200));
        JScrollPane jscroll = new JScrollPane(this.jtable);

        //jscroll.getViewport().setViewSize(new Dimension(450, 200));
        return jscroll;
    }

    /**
     * Creates a tab for showing dynamic query filters.
     */
    protected JComponent createFiltersPane() {
        this.dynamicQueryPanel = new DynamicQueryPanel(getVisualization(), getFilteredTable());
        JScrollPane queryScroll = new JScrollPane(this.dynamicQueryPanel,
                                                  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                                  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return queryScroll;
    }

    /**
     * Creates a tab for showing how visual variable are visualized and change it.
     */
    protected JComponent createStdVisualPane() {
        return new StdVisualPanel(this.visualization, getFilter());
    }

    /**
     * Returns the jtable.
     * @return JTable
     */
    public JTable getJtable() {
        return this.jtable;
    }

    /**
     * Returns the table.
     * @return Table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Returns the dynamicQueryPanel.
     * @return DynamicQueryPanel
     */
    public DynamicQueryPanel getDynamicQueryPanel() {
        return this.dynamicQueryPanel;
    }

    /**
     * Returns the detail.
     * @return DetailTable
     */
    public JComponent getDetail() {
        return this.detail;
    }

    /**
     * Returns the stdVisual.
     * @return StdVisualPanel
     */
    public JComponent getStdVisual() {
        return this.stdVisual;
    }

    /**
     * Returns the tabs.
     * @return JTabbedPane
     */
    public JTabbedPane getTabs() {
        return this.tabs;
    }

    /**
     * Returns the visualization.
     * @return BasicVisualization
     */
    public Visualization getVisualization() {
        return this.visualization;
    }

    /**
     * Returns the filteredTable.
     * @return FilteredTable
     */
    public FilteredTable getFilteredTable() {
        return this.filteredTable;
    }
    /**
     * Returns the filter.
     * @return ColumnFilter
     */
    public ColumnFilter getFilter() {
        return this.filteredTable.getFilter();
    }

    /**
     * Sets the filter.
     * @param filter The filter to set
     */
    public void setFilter(ColumnFilter filter) {
        this.filteredTable.setFilter(filter);
    }

}
