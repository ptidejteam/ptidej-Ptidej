/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.panel;

import infovis.Graph;
import infovis.Table;
import infovis.Tree;
import infovis.Visualization;
import infovis.column.StringColumn;
import infovis.graph.DefaultGraph;
import infovis.graph.io.GraphReaderFactory;
import infovis.graph.visualization.GraphVisualization;
import infovis.graph.visualization.MatrixVisualization;
import infovis.table.DefaultTable;
import infovis.table.io.TableReaderFactory;
import infovis.table.visualization.ScatterPlotVisualization;
import infovis.tree.DefaultTree;
import infovis.tree.io.TreeReaderFactory;
import infovis.tree.visualization.TreeVisualization;
import infovis.tree.visualization.TreemapVisualization;
import infovis.tree.visualization.treemap.SliceAndDice;
import infovis.visualization.VisualizationFactory;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;


/**
 * Component to create a visualization program as simply as possible.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class MainFrameDecorator {
    JFrame         frame;
    JMenuBar       menuBar;
    JMenu          fileMenu;
    JMenu          helpMenu;
    JFileChooser   fileChooser;
    JMenu          visualizationMenu;
    Table          table;
    Visualization  visualization = null;
    ControlPanel   controlPanel;
    JComponent     splashScreen;
    boolean        creatingWindow = true;
    
    AbstractAction fileOpenAction = new DefaultAction("Open", 'O') {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            chooseFile();
        }
    };
    AbstractAction quitAction = new DefaultAction("Quit", 'Q') {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
    AbstractAction aboutAction = new AbstractAction("About...") {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,
                                          "The Visualization Toolkit\n(C) Jean-Daniel Fekete and INRIA, France",
                                          "About the Visualization Toolkit",
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * Creates a new MainFrameDecorator object.
     *
     * @param frame the JFrame to decorate.
     */
    public MainFrameDecorator(JFrame frame) {
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.fileChooser.setCurrentDirectory(new File("."));
        this.menuBar = new JMenuBar();
        frame.setJMenuBar(this.menuBar);
        createMenus();
        frame.getContentPane().add(createSplashScreen());
    }

    /**
     * Creates the menus.
     */
    protected void createMenus() {
        this.fileMenu = createFileMenu();
        this.menuBar.add(this.fileMenu);
        this.visualizationMenu = new JMenu("Visualization");
        this.visualizationMenu.setEnabled(false);
        this.menuBar.add(this.visualizationMenu);
        this.helpMenu = createHelpMenu();
        this.menuBar.add(this.helpMenu);
    }

    /**
     * Creates the file menu.
     *
     * @return the file menu.
     */
    public JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        fileMenu.add(this.fileOpenAction);
        fileMenu.addSeparator();
        fileMenu.add(this.quitAction);
        return fileMenu;
    }

    /**
     * Creates the help menu.
     *
     * @return the help menu.
     */
    public JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        helpMenu.add(this.aboutAction);
        return helpMenu;
    }

    /**
     * Create the splash screen.
     *
     * @return the splash screen.
     */
    public JComponent createSplashScreen() {
        this.splashScreen = new SplashPanel();
        return this.splashScreen;
    }

    /**
     * shows a file chooser and wait for an selection.
     */
    protected void chooseFile() {
        int ret = this.fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File   file = this.fileChooser.getSelectedFile();
            String error = openFile(file);
            if (error == null) {
                this.frame.pack();
            } else {
                JOptionPane.showMessageDialog(null, "Error",
                                              "Couldn't read file " +
                                              file.getAbsoluteFile(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param file DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String openFile(File file) {
        String name = file.getAbsolutePath();

        this.table = new DefaultTable();
        Graph   graph = new DefaultGraph(this.table);
        boolean loaded = GraphReaderFactory.readGraph(name, graph);

        if (loaded) {
            this.visualization = createGraphVisualization(graph);
            return null;
        }
        Tree tree = new DefaultTree(this.table);
        loaded = TreeReaderFactory.readTree(name, tree);
        if (loaded) {
            this.visualization = createTreeVisualization(tree);
            return null;
        }

        loaded = TableReaderFactory.readTable(name, this.table);
        if (loaded) {
            this.visualization = createTableVisualization(tree);
            return null;
        }

        return "invalid format";
    }

    /**
     * DOCUMENT ME!
     *
     * @param t DOCUMENT ME!
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static StringColumn getStringColumn(Table t, int index) {
        StringColumn ret = null;
        for (int i = 0; i < t.getColumnCount(); i++) {
            ret = StringColumn.getColumn(t, i);
            if (ret != null && !ret.isInternal() && index-- == 0)
                return ret;
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param table DOCUMENT ME!
     * @param visualization DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Visualization createContols(Table table,
                                       final Visualization visualization) {
        //visualization.setLabelColumn(getStringColumn(table, 0));

        final ControlPanel control = (ControlPanel)visualization.createDefaultControls();
        if (this.splashScreen != null) {
            this.frame.getContentPane().remove(this.splashScreen);
        }
        if (this.controlPanel != null && ! this.creatingWindow) {
            this.frame.getContentPane().remove(this.controlPanel);
            this.controlPanel.getVisualization().dispose();
            this.controlPanel = null;
        }
        else if (this.controlPanel != null && this.creatingWindow) {
            JFrame window = new JFrame(table.getName());
            window.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    control.dispose();
                }
            });
            window.setSize(500, 500);
            window.getContentPane().add(control);
            window.pack();
            window.setVisible(true);
            return visualization;
        }
        this.controlPanel = control;
        this.frame.getContentPane().add(this.controlPanel);
        this.frame.pack();
        System.gc();
        System.runFinalization();
        VisualizationFactory.Creator[] creators = 
            VisualizationFactory.sharedInstance().getCompatibleCreators(table);
        this.visualizationMenu.removeAll();
        for (int i = 0; i < creators.length; i++) {
            addVisualizationMenu(creators[i], i);
        }
        this.visualizationMenu.setEnabled(creators.length != 0);
        
        return visualization;
    }
    
    public void addVisualizationMenu(final VisualizationFactory.Creator creator, int i) {
        final String name = creator.getName();
        this.visualizationMenu.add(new DefaultAction(name, '1'+i) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
                Visualization visualization = creator.create(MainFrameDecorator.this.table);
                if (visualization != null)
                    createContols(MainFrameDecorator.this.table, visualization);
            }
        });
    }

    /**
     * DOCUMENT ME!
     *
     * @param graph DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public GraphVisualization createGraphVisualization(Graph graph) {
        this.table = graph;
        MatrixVisualization visualization = new MatrixVisualization(graph);
        createContols(graph, visualization);
        return visualization;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tree DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public TreeVisualization createTreeVisualization(Tree tree) {
        this.table = tree;
        TreemapVisualization visualization = new TreemapVisualization(tree,
                                                                      null,
                                                                      new SliceAndDice());
        createContols(tree, visualization);
        return visualization;
    }

    /**
     * DOCUMENT ME!
     *
     * @param table DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Visualization createTableVisualization(Table table) {
        this.table = table;
        Visualization visualization = new ScatterPlotVisualization(table);
        createContols(table, visualization);
        return visualization;
    }

    /**
     * Main program.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("InfoVis Toolkit");
        new MainFrameDecorator(frame);
        frame.pack();
        frame.setVisible(true);
    }
}
