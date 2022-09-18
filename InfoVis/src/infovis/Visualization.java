/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis;

import infovis.column.BooleanColumn;
import infovis.column.ColumnFilter;
import infovis.column.FilterColumn;
import infovis.column.IntColumn;
import infovis.column.NumberColumn;
import infovis.column.ObjectColumn;
import infovis.column.filter.NotTypedFilter;
import infovis.panel.ControlPanelFactory;
import infovis.table.TableProxy;
import infovis.utils.Permutation;
import infovis.utils.PermutedIterator;
import infovis.utils.RowComparator;
import infovis.utils.RowFilter;
import infovis.utils.RowIterator;
import infovis.visualization.ColorVisualization;
import infovis.visualization.DefaultVisualColumn;
import infovis.visualization.ExcentricLabels;
import infovis.visualization.Fisheyes;
import infovis.visualization.LabeledComponent;
import infovis.visualization.Orientable;
import infovis.visualization.VisualColumnDescriptor;
import infovis.visualization.VisualColumnProxy;
import infovis.visualization.color.ColorVisualizationFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import agile2d.opengl.AGLGraphics2D;


/**
 * Base class for all the visualizations.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public abstract class Visualization extends TableProxy
    implements ChangeListener, MouseListener, MouseMotionListener, 
        LabeledComponent, Orientable, RowFilter {
    /* Name of the orientations options */
    /* Name of the visual dimensions managed by the visualization. */
    
    /** Name of the selection visual column */
    public static final String VISUAL_SELECTION = "selection";
    /** Name of the filter visual column */
    public static final String VISUAL_FILTER = "filter";
    /** Name of the sort visual column */
    public static final String VISUAL_SORT = "sort";
    /** Name of the label visual column */
    public static final String VISUAL_LABEL = "label";
    /** Name of the color visual column */
    public static final String VISUAL_COLOR = "color";
    /** Name of the size visual column */
    public static final String VISUAL_SIZE = "size";
    /** Name of the shape visual column */
    public static final String VISUAL_SHAPE = "#shape";

    /** Name of the optional IntColumn managing the permutation. */
    public static final String PERMUTATION_COLUMN = "#permutation";
    /** Name of the optional IntColumn managing the inverse permutation. */
    public static final String INVERSEPERMUTATION_COLUMN = "#inversePermutation";
    /** The Panel parent of this visualization */
    protected JComponent parent;
    /** The boolean column containing and managing the selection */
    protected BooleanColumn selection;
    /** The FilterColumn for this visualization. */
    protected FilterColumn filter;
    /** The Column used to sort this visualization. */
    protected Column sortColumn;
    /** The RowComparator used to sort the rows of the visualization. */
    protected RowComparator comparator;
    /** The RowFilter used to hide the rows of the visualization. */
    protected RowFilter hideFilter;
    /** The permutation of rows used by this visualization. */
    protected Permutation permutation;
    /** The column used for labeling */
    protected Column labelColumn;
    /** The column used for color */
    protected Column colorColumn;
    /** The ColorVisualization */
    protected ColorVisualization colorVisualization;
    /** The default color */
    protected Color defaultColor = new Color(0, 0, 0.6f);
    /** The default stroke */
    protected BasicStroke defaultStroke = new BasicStroke(0);
    /** The color of selected items */
    protected Color selectedColor = Color.RED;
    /** The color of unselected items */
    protected Color unselectedColor = Color.BLACK;
    /** The color of filtered items */
    protected Color filteredColor = Color.BLACK;
    /** True if using smooth shading */
    protected boolean smooth;
    /** The default font */
    protected Font defaultFont;
    /** The orientation */
    protected int orientation = ORIENTATION_SOUTH;
    
    protected ExcentricLabels excentric;
    protected Fisheyes fisheyes;
    /** Non null when drawing on an AGLGraphics2D */
    protected transient AGLGraphics2D aglGraphics;
    
    /** True when painting labels */
    protected boolean showingLabel = true;
    /** The default size */
    protected double                  defaultSize = 30;
    /** The column used for size */
    protected NumberColumn     sizeColumn;
    protected double                  minSize = 1;
    protected double                  maxSize = 50;
    
    private ObjectColumn       shapes;
    private boolean            shapesUpdated;
    private Rectangle2D        oldBounds;
    private Map                managedColumns = new HashMap();
    private Map                visualColumns = new HashMap();
    private long               redisplayTime;
    private long               layoutTime;
    protected int              displayedItems;
    private boolean           displayingStatistics = false;
    private Rectangle2D.Double hitBox = new Rectangle2D.Double();

    static class ManagedColumn {
        int count;
        int invalidateCount;
    }
    /**
     * Creates a new Visualization object.
     *
     * @param table The table.
     */
    public Visualization(Table table) {
        this(table, null, null);
    }

    /**
     * Creates a new Visualization object.
     *
     * @param table The table.
     * @param selection the selection column or null
     * @param filter the FilterColumn or null.
     */
    public Visualization(Table table, BooleanColumn selection,
                         FilterColumn filter) {
        super(table);
        declareVisualColumns();
        setVisualColumn(
            VISUAL_SELECTION,
            selection != null ? 
                selection : BooleanColumn.findColumn(table, Table.SELECTION_COLUMN));
        setVisualColumn(
            VISUAL_FILTER,
            filter != null ? 
                filter : FilterColumn.findColumn(table, Table.FILTER_COLUMN));
        this.shapes = new ObjectColumn(VISUAL_SHAPE);
    }
    
    /**
     * Declares the visual columns of this visualization.
     */
    protected void declareVisualColumns() {
        putVisualColumn(VISUAL_SELECTION,
            new DefaultVisualColumn(false, new NotTypedFilter(BooleanColumn.class)) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    BooleanColumn c = (BooleanColumn)column;
                    Visualization.this.selection = c;
                }
        });
        putVisualColumn(VISUAL_FILTER,
            new DefaultVisualColumn(false, new NotTypedFilter(FilterColumn.class)) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    Visualization.this.filter = (FilterColumn)column;
                }
        });
        putVisualColumn(VISUAL_SORT, 
            new DefaultVisualColumn(false) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    Visualization.this.sortColumn = column;
                    Visualization.this.comparator = column;
                    permuteRows();
                }
        });
        putVisualColumn(VISUAL_COLOR,
            new DefaultVisualColumn(false) {
                public void setColumn(Column column) {
                    assert (column== null || Visualization.this.table.indexOf(column) != -1);
                    super.setColumn(column);
                    Visualization.this.colorColumn = column;
                    if (Visualization.this.colorColumn != null) {
                        Visualization.this.colorVisualization =
                            ColorVisualizationFactory.createColorVisualization(Visualization.this.colorColumn);
                    }
                    else
                        Visualization.this.colorVisualization = null;

                }
        });
        putVisualColumn(VISUAL_LABEL,
            new DefaultVisualColumn(false) {
                public void setColumn(Column column) {
                    assert (column== null || Visualization.this.table.indexOf(column) != -1);
                    super.setColumn(column);
                    Visualization.this.labelColumn = column;
                }
        });
        putVisualColumn(VISUAL_SIZE, 
            new DefaultVisualColumn(true, new NotTypedFilter(NumberColumn.class)) {
                public void setColumn(Column column) {
                    assert (column== null || Visualization.this.table.indexOf(column) != -1);
                    super.setColumn(column);
                    Visualization.this.sizeColumn = (NumberColumn)column;
                }
        });
    }
    
    protected void putVisualColumn(String name, VisualColumnDescriptor vc) {
      if (this.visualColumns.containsKey(name))
          throw new RuntimeException("visual column "+name+" already declared");
        this.visualColumns.put(name, vc);
    }
    
    protected void putVisualColumn(String name, VisualColumnProxy vc) {
        this.visualColumns.put(name, vc);
    }
    
    public boolean isVisualColumnInvalidate(String name) {
        VisualColumnDescriptor vc = (VisualColumnDescriptor)this.visualColumns.get(name);
        return vc != null && vc.isInvalidate();
    }
    
    protected void setVisualColumnInvalidate(String name, boolean invalidate) {
        VisualColumnDescriptor vc = (VisualColumnDescriptor)this.visualColumns.get(name);
        if (vc == null)
            throw new RuntimeException("visual column "+name+" not declared");
        vc.setInvalidate(true);
    }
    
    protected void setVisualColumnFilter(String name, ColumnFilter filter) {
        VisualColumnDescriptor vc = (VisualColumnDescriptor)this.visualColumns.get(name);
        if (vc == null)
            throw new RuntimeException("visual column "+name+" not declared");
        vc.setFilter(filter);   
    }
    
    public VisualColumnDescriptor getVisualColumnDescriptor(String name) {
        VisualColumnDescriptor vc = (VisualColumnDescriptor)this.visualColumns.get(name);
        return vc;
    }
    
    /**
     * Releases all the resources used by the visualization.
     *
     */
    public void dispose() {
        for (Iterator iter = this.visualColumns.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry)iter.next();
            VisualColumnDescriptor vc = (VisualColumnDescriptor)entry.getValue();
            vc.setColumn(null);
        }
        setExcentric(null);
        setFisheyes(null);
        this.shapes = null;
        this.table = null;
         
    }

    /**
     * Returns the Table.
     *
     * @return the Table.
     */
    public Table getTable() {
        return this.table;
    }
    
    public Rectangle2D getBounds() {
        return this.oldBounds;
    }

    /**
     * Returns the Control Panel suited to manage this visualization.
     * 
     * Don't override, instead, change the ControlPanelFactory.
     *
     * @return the Control Panel suited to manage this visualization.
     */
    public final JComponent createDefaultControls() {
        ControlPanelFactory factory = ControlPanelFactory.sharedInstance();
        
        return factory.createControlPanel(this);
    }
    
    public static void setControlPanelCreator(Class visClass, Class cpClass) {
        ControlPanelFactory.sharedInstance().setDefault(visClass, cpClass);
    }

    /**
     * Returns the owning VisualizationPanel.
     *
     * @return the owning VisualizationPanel.
     */
    public JComponent getParent() {
        return this.parent;
    }

    /**
     * Sets the owning VisualizationPanel.
     *
     * @param parent owning VisualizationPanel.
     */
    public void setParent(JComponent parent) {
        if (this.parent != parent) {
            if (this.parent != null) {
                uninstall(this.parent);
            }
            this.parent = parent;
            if (this.parent != null) {
                install(this.parent);
            }
        }
    }

    /**
     * Installs the visualization into its Swing component.
     *
     * @param parent the Swing parent component.
     */
    protected void install(JComponent parent) {
        parent.addMouseListener(this);
        parent.addMouseMotionListener(this);
        if (this.excentric != null)
            this.excentric.setVisualization(this);
    }

    /**
     * Desinstalls the visualization from its Swing component.
     *
     * @param parent the Swing parent component.
     */
    protected void uninstall(JComponent parent) {
        parent.removeMouseListener(this);
        parent.removeMouseMotionListener(this);
        if (this.excentric != null)
            this.excentric.setVisualization(null);
        parent = null;
    }

    // Repaint/recomputeShape management
    /**
     * Invalidates the contents of the Visualization if the column has
     * requested so.  Otherwise, just repaint.
     *
     * @param c the Column triggering the invalidate/repaint.
     */
    public void invalidate(Column c) {
        if (isInvalidateColumn(c)) {
            this.shapesUpdated = false;
        }
        repaint();
    }

    /**
     * Invalidates the contents of the Visualization.
     */
    public void invalidate() {
        this.shapesUpdated = false;
        repaint();
    }

    /**
     * Trigger a repaint on the visualization pane.
     */
    public void repaint() {
        if (this.parent != null)
            this.parent.repaint();
    }

    /**
     * Returns <code>true</code> if modifying this column triggers a
     * recomputation of the visualization.
     *
     * @param c the Column.
     *
     * @return <code>true</code> if modifying this column triggers a
     *         recomputation of the visualization.
     */
    public boolean isInvalidateColumn(Column c) {
        ManagedColumn m = (ManagedColumn)this.managedColumns.get(c);
        return m != null && m.invalidateCount != 0;
    }

    /**
     * Adds, remove or replace a Column managed by this Visualization.
     * 
     * <p>
     * If the dynamic variable <code>invalidateColumn</code> is set to
     * <code>true</code> when this method is called, the column is declared
     * as <b>invalidating</b>, meaning that the shapes will be recomputed
     * when this column is changed.
     * </p>
     * 
     * <p>
     * The dynamic variable <code>invalidateColumn</code> is reset to
     * <code>false</code> by this method.
     * </p>
     *
     * @param oldC the column that is removed or null.
     * @param newC the column that is added or null.
     *
     * @return <code>true</code> if something had to be changed.
     */
    protected boolean changeManagedColumn(String visual, Column oldC, Column newC) {
        if (oldC == newC) {
//            invalidateColumn = false;
            return false;
        }
        
        VisualColumnDescriptor vc = getVisualColumnDescriptor(visual);
        boolean invalidateColumn;
        if (vc == null) {
            invalidateColumn = true;
        }
        else {
            invalidateColumn = vc.isInvalidate();
            vc.setColumn(newC);
        }
        if (oldC != null) {
            ManagedColumn m = (ManagedColumn)this.managedColumns.get(oldC);
            if (invalidateColumn) {
                m.invalidateCount--;
            }
            m.count--;
            if (m.count == 0) {
                oldC.removeChangeListener(this);
                this.managedColumns.remove(oldC);
            }
        }
        if (newC != null) {
            ManagedColumn m = (ManagedColumn)this.managedColumns.get(newC);
            if (m == null) {
                m = new ManagedColumn();
                this.managedColumns.put(newC, m);
                newC.addChangeListener(this);
            }
            m.count++;
            if (invalidateColumn) {
                m.invalidateCount++;
            }
        }
        if (invalidateColumn) {
            this.shapesUpdated = false;
        }
        repaint();
        return true;
    }
    
    public boolean setVisualColumn(String name, Column column) {
        VisualColumnDescriptor vc = getVisualColumnDescriptor(name);
        if (vc == null) {
            throw new RuntimeException("invalid visual column "+name);
        }
        return changeManagedColumn(name, vc.getColumn(), column);
    }
    
    public Column getVisualColumn(String name) {
        VisualColumnDescriptor vc = getVisualColumnDescriptor(name);
        if (vc == null)
            return null;
        return vc.getColumn();
    }

    // Management of managed Columns
    /**
     * Returns the current ListSelectionModel of this pickable.
     *
     * @return the current ListSelectionModel of this pickable.
     */
    public BooleanColumn getSelection() {
        return this.selection;
    }

    /**
     * Returns the filter.
     *
     * @return FilterColumn
     */
    public FilterColumn getFilter() {
        return this.filter;
    }

    /**
     * Returns <code>true</code> if the row is filtered.
     *
     * @param row the row.
     *
     * @return <code>true</code> if the row is filtered.
     */
    public boolean isFiltered(int row) {
        return this.filter != null && this.filter.isFiltered(row);
    }

    /**
     * Returns the orientation.
     * @return int
     */
    public int getOrientation() {
        return this.orientation;
    }

    /**
     * Sets the orientation.
     * @param orientation The orientation to set
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
        invalidate();
    }

    /**
     * Returns the colorVisualization.
     *
     * @return ColorVisualization
     */
    public ColorVisualization getColorVisualization(String visualColor) {
        if (visualColor.equals(VISUAL_COLOR))
            return this.colorVisualization;
        return null;
    }

    /**
     * Install the color associated with the specified row in the graphics.
     *
     * @param g the graphics.
     * @param row the row.
     */
    public void setColorFor(Graphics2D g, int row) {
        if (this.aglGraphics != null) {
            this.aglGraphics.setColor(getColorValue(row));
        } else {
            g.setColor(getColorAt(row));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getColorValue(int row) {
        if (this.colorVisualization == null) {
            return this.defaultColor.getRGB();
        } else {
            return this.colorVisualization.getColorValue(row);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Color getColorAt(int row) {
        if (this.colorVisualization == null) {
            return this.defaultColor;
        } else {
            return this.colorVisualization.getColor(row);
        }
    }

    /**
     * Returns the label associated with the specified row.
     *
     * @param row the row.
     *
     * @return the label associated with the specified row.
     */
    public String getLabelAt(int row) {
        if (this.labelColumn == null)
            return null;
        return this.labelColumn.getValueAt(row);
    }

    public double getSizeAt(int row) {
        if (this.sizeColumn == null)
            return this.defaultSize;
        double smin = this.sizeColumn.getDoubleMin();
        double smax = this.sizeColumn.getDoubleMax();
        if (smin == smax)
            return this.maxSize;
        double sscale = (this.maxSize - this.minSize) / (smax - smin);
        return (this.sizeColumn.getDoubleAt(row) - smin) * sscale + this.minSize;
    }

    /**
     * Returns the maxSize.
     *
     * @return double
     */
    public double getMaxSize() {
        return this.maxSize;
    }

    /**
     * Returns the minSize.
     *
     * @return double
     */
    public double getMinSize() {
        return this.minSize;
    }

    /**
     * Sets the maxSize.
     *
     * @param maxSize The maxSize to set
     */
    public void setMaxSize(double maxSize) {
        this.maxSize = maxSize;
        invalidate();
    }

    /**
     * Sets the minSize.
     *
     * @param minSize The minSize to set
     */
    public void setMinSize(double minSize) {
        this.minSize = minSize;
        invalidate();
    }

    // Painting
    /**
     * Draw the shape of a specified row onto the specified graphics.
     *
     * @param graphics the graphics.
     * @param row the row.
     */
    public void paintShape(Graphics2D graphics, int row) {
        Shape s = getShapeAt(row);
        if (s == null)
            return;
        s = transformShape(s);

        if (this.smooth) {
            Rectangle2D   box = s.getBounds2D();
            Color         color = getColorAt(row);
            GradientPaint gradient = new GradientPaint((float)box.getMinX(),
                                                       (float)box.getMinY(),
                                                       color.brighter(),
                                                       (float)box.getMaxX(),
                                                       (float)box.getMaxY(),
                                                       color.darker(), false);
            graphics.setPaint(gradient);
        } else {
            setColorFor(graphics, row);
        }
        this.displayedItems++;
        graphics.fill(s);
        paintOutline(graphics, row, s);
        paintLabel(graphics, row, s);
    }

    /**
     * Transforms a shape through the Fisheye lens and return a transformed shape.
     * 
     * @param s the Shape
     * @return a transformed shape.
     */    
    public Shape transformShape(Shape s) {
        if (this.fisheyes != null) {
            return this.fisheyes.transform(s);
        }
        return s;
    }

    /**
     * Draw the outline of an item.
     *
     * @param graphics the graphics.
     * @param row the row.
     * @param s the Shape.
     */
    public void paintOutline(Graphics2D graphics, int row, Shape s) {
        if (this.selection != null && this.selection.isValueUndefined(row)) {
            if (this.unselectedColor != null && !this.smooth)
                graphics.setColor(this.unselectedColor);
        } else {
            if (this.selectedColor != null)
                graphics.setColor(this.selectedColor);
        }
        graphics.draw(s);
    }
    
    public void paintLabel(Graphics2D graphics, int row, Shape s) {
        if (this.showingLabel) {
            String label = getLabelAt(row);
            if (label == null)
                return;
            FontMetrics fm = graphics.getFontMetrics();
            Rectangle2D bounds = s.getBounds2D();
            Rectangle2D maxCharBounds = fm.getMaxCharBounds(graphics);
            if (maxCharBounds.getWidth() > bounds.getWidth()*2 ||
                maxCharBounds.getHeight() > (bounds.getHeight()*2))
                return; // no reason to try
            Shape clip = graphics.getClip();
            graphics.clip(s);
            Color c = getColorAt(row);
            int grey = (c.getRed()+c.getGreen()+c.getBlue())/3;
            if (grey < 127)
                graphics.setColor(Color.WHITE);
            else
                graphics.setColor(Color.BLACK);
            int w = fm.stringWidth(label);
            graphics.drawString(label,
                (float)(bounds.getCenterX()-w/2),
                (float)(bounds.getCenterY()+fm.getDescent()));
            graphics.setClip(clip);
        }
    }

    /**
     * Paint the background of the visualization surface.
     *
     * @param graphics the graphics.
     * @param bounds bounds the bounding box of the visualization.
     */
    public void paintBackground(Graphics2D graphics, Rectangle2D bounds) {
        GradientPaint paint = new GradientPaint((float)bounds.getX(),
                                                (float)bounds.getY(),
                                                this.parent.getBackground(),
                                                (float)bounds.getMaxX(),
                                                (float)bounds.getMaxY(),
                                                Color.WHITE);
        graphics.setPaint(paint);
        graphics.fill(bounds);
    }

    /**
     * Computes the shapes associated with the rows, and store them with
     * setShapeAt.
     *
     * @param bounds the bounding box of the visualization.
     */
    public abstract void computeShapes(Rectangle2D bounds);

    /**
     * Method for painting the visualization.
     *
     * @param graphics the graphics.
     * @param bounds the bounding box of the visualization.
     */
    public void paint(Graphics2D graphics, Rectangle2D bounds) {
        if (graphics instanceof AGLGraphics2D) {
            this.aglGraphics = (AGLGraphics2D) graphics;
        }
        else {
            this.aglGraphics = null;
        }
        paintBackground(graphics, bounds);
        validateShapes(bounds);
        graphics.setStroke(this.defaultStroke);
        graphics.setColor(this.defaultColor);
        long time = System.currentTimeMillis();
        this.displayedItems = 0;
        paintItems(graphics, bounds);
        this.redisplayTime = System.currentTimeMillis() - time;
        paintExcentric(graphics, bounds);
        paintStatistics(graphics, bounds);
        this.aglGraphics = null;
    }
    
    public void paintStatistics(Graphics2D graphics, Rectangle2D bounds) {
        if (this.displayingStatistics) {
            float fps = 1000.0f / this.redisplayTime;
            float ips = this.displayedItems * fps;
            graphics.setColor(Color.BLACK);
            graphics.drawString("Redisplay: "+this.redisplayTime+"ms, "+fps+"fps "+ips+"ips", 0, 10);
        }
    }
    
    /**
     * Method for filtering and painting the items.
     *
     * @param graphics the graphics.
     * @param bounds the bounding box of the visualization.
     */
    public void paintItems(Graphics2D graphics, Rectangle2D bounds) {
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            if (isFiltered(row))
                continue;
            paintShape(graphics, row);
        }
    }
    
    public void paintExcentric(Graphics2D graphics, Rectangle2D bounds) {
        if (this.excentric != null) {
            this.excentric.paint(graphics, bounds);
        }
    }

    /**
     * Checks whether the shapes should be recomputed and call
     * updateShapes then.
     *
     * @param bounds the Visualization bounds.
     */
    protected void validateShapes(Rectangle2D bounds) {
        if (!(this.shapesUpdated && this.oldBounds.equals(bounds))) {
            if (this.shapes.getRowCount() < this.table.getRowCount()) {
                this.shapes.setExtend(this.table.getRowCount(), null);
            }
            long time = System.currentTimeMillis();
            this.oldBounds = bounds;
            computeShapes(bounds);
            this.layoutTime = System.currentTimeMillis() - time;
            this.shapesUpdated = true;
        }
    }

    /**
     * Returns the ObjectColumn containing the shapes.
     *
     * @return the ObjectColumn containing the shapes.
     */
    public ObjectColumn getShapes() {
        return this.shapes;
    }

    /**
     * Returns the shape of stored for a specified row or null if none is
     * store.
     *
     * @param row the row.
     *
     * @return the shape of stored for a specified row or null if none is
     *         store.
     */
    public Shape getShapeAt(int row) {
        if (this.shapes.isValueUndefined(row))
            return null;
        Object s = this.shapes.get(row);
        if (s instanceof Shape) {
            Shape shape = (Shape)s;
            return shape;
        }
        return null;
    }

    /**
     * Associate a shape with a specified row
     *
     * @param row the row.
     * @param s the shape.
     */
    public void setShapeAt(int row, Shape s) {
        this.shapes.setExtend(row, s);
    }

    // Picking
    /**
     * Pick the top item.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @param bounds the bounding box of the visualization.
     *
     * @return int the index of the item on top.
     */
    public int pickTop(double x, double y, Rectangle2D bounds) {
        this.hitBox.setFrame(x, y, 1, 1);
        return pickTop(this.hitBox, bounds);
    }

    /**
     * Pick the top item.
     *
     * @param hitBox the bounds where the top item is searched.
     * @param bounds the total bounds where the visualization is displayed.
     *
     * @return int the index of the item on top.
     */
    public int pickTop(Rectangle2D hitBox, Rectangle2D bounds) {
        validateShapes(bounds);
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            if (isFiltered(row))
                continue;
            Shape s = getShapeAt(row);
            if (s != null && s.intersects(hitBox))
                return row;
        }
        return -1;
    }

    /**
     * Pick all the items under a rectangle.
     *
     * @param hitBox the bounds where the top item is searched.
     * @param bounds the total bounds where the visualization is displayed.
     * @param pick a BooleanColumn that will contain true at each row of
     *        items intersecting the hitBox.
     *
     * @return int the index of the item on top.
     */
    public BooleanColumn pickAll(Rectangle2D hitBox, Rectangle2D bounds,
                                 BooleanColumn pick) {
        validateShapes(bounds);

        if (pick == null)
            pick = new BooleanColumn("pickAll");
        else
            pick.clear();
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            if (isFiltered(row))
                continue;
            Shape s = getShapeAt(row);
            if (s != null && s.intersects(hitBox)) {
                pick.setExtend(row, true);
            }
        }
        return pick;
    }

    /**
     * Pick all the items under a rectangle.
     *
     * @param hitBox the bounds where the top item is searched.
     * @param bounds the total bounds where the visualization is displayed.
     * @param pick an IntColumn that will contain each row of items
     *        intersecting the hitBox.
     *
     * @return int the index of the item on top.
     */
    public IntColumn pickAll(Rectangle2D hitBox, Rectangle2D bounds,
                             IntColumn pick) {
        validateShapes(bounds);
        if (pick == null)
            pick = new IntColumn("pickAll");
        else
            pick.clear();
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            if (isFiltered(row))
                continue;
            Shape s = getShapeAt(row);
            if (s != null) {
                Rectangle2D rect = s.getBounds2D();
                if (rect.isEmpty() && hitBox.contains(rect.getX(), rect.getY()))
                    pick.add(row);
                else if (rect.intersects(hitBox)) {
                    pick.add(row);
                }
            }
        }
        return pick;
    }

    // Management of permutations
    /**
     * Compute the permutation from the comparator. 
     */
    protected void permuteRows() {
        RowComparator comp = this.comparator;
        RowFilter filter = this.hideFilter;
        if (comp == null && filter == null) {
            this.permutation = null;
            invalidate();
            return;
        }
        if (this.permutation == null) {
            this.permutation = new Permutation(IntColumn.findColumn(this.table,
                                                               PERMUTATION_COLUMN),
                                          IntColumn.findColumn(this.table,
                                                               INVERSEPERMUTATION_COLUMN),
                                          this.table.getRowCount());
        }
        if (filter != null)
            this.permutation.sort(this.table.getRowCount(), comp, filter);
        else
            this.permutation.sort(this.table.getRowCount(), comp);
        invalidate();
    }
    
    public void hideSelectedRows() {
        //TODO compose the hide filters if required
        //if (hideFilter == null)
            this.hideFilter = getSelection();
//        else {
//            hideFilter = composeFilter(hideFilter, getSelection());
//        }
        permuteRows();
    }
    
    public void hideFilteredRows() {
        //TODO compose the hide filters if required.
        this.hideFilter = getFilter();
        permuteRows();
    }
    
    public void showAllRows() {
        this.hideFilter = null;
        permuteRows();
        invalidate();
    }

    /**
     * Returns the permutation.
     *
     * @return Permutation
     */
    public Permutation getPermutation() {
        return this.permutation;
    }

    /**
     * Returns the current comparator.
     *
     * @return the current comparator.
     */
    public RowComparator getComparator() {
        return this.comparator;
    }

    /**
     * Sets the comparator.
     *
     * @param comparator The comparator to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setComparator(RowComparator comparator) {
        if (this.comparator != comparator) {
            setVisualColumn(VISUAL_SORT, null);
            this.comparator = comparator;
            permuteRows();
            return true;
        }
        return false;
    }

    /**
     * Returns the row at a specified permuted index.
     *
     * @param index the index.
     *
     * @return the row at a specified permuted index.
     */
    public int getRowAtIndex(int index) {
        if (this.permutation == null)
            return index;
        return this.permutation.getPermutation(index);
    }

    /**
     * Returns the index at a specified permuted row.
     *
     * @param row the row.
     *
     * @return the index at a specified permuted row.
     */
    public int getRowIndex(int row) {
        if (this.permutation == null)
            return row;
        return this.permutation.getInverse(row);
    }

    /**
     * Returns a <code>RowIterator</code> taking the permutation into
     * account.
     *
     * @return a <code>RowIterator</code> taking the permutation into
     *         account.
     */
    public RowIterator iterator() {
        if (this.permutation == null)
            return this.table.iterator();
        return new PermutedIterator(0, this.permutation);
    }

    // Default values
    /**
     * Returns the defaultFont.
     *
     * @return Font
     */
    public Font getDefaultFont() {
        if (this.defaultFont == null)
            return this.parent.getFont();
        return this.defaultFont;
    }

    /**
     * Sets the defaultFont.
     *
     * @param defaultFont The defaultFont to set
     */
    public void setDefaultFont(Font defaultFont) {
        this.defaultFont = defaultFont;
        repaint();
    }

    /**
     * Returns the showingLabel.
     * @return boolean
     */
    public boolean isShowingLabel() {
        return this.showingLabel;
    }

    /**
     * Sets the showingLabel.
     * @param showingLabel The showingLabel to set
     */
    public void setShowingLabel(boolean showingLabel) {
        this.showingLabel = showingLabel;
        repaint();
    }

    /**
     * Returns the defaultColor.
     *
     * @return Color
     */
    public Color getDefaultColor(String visualColor) {
        if (visualColor.equals(VISUAL_COLOR))
            return this.defaultColor;
        return null;
    }

    /**
     * Sets the defaultColor.
     *
     * @param defaultColor The defaultColor to set
     */
    public void setDefaultColor(String visualColor, Color defaultColor) {
        if (visualColor.equals(VISUAL_COLOR)) {
            this.defaultColor = defaultColor;
            repaint();
        }
    }

    /**
     * Returns the selectedColor.
     *
     * @return Color
     */
    public Color getSelectedColor() {
        return this.selectedColor;
    }

    /**
     * Sets the selectedColor.
     *
     * @param selectedColor The selectedColor to set
     */
    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        repaint();
    }

    /**
     * Returns the filteredColor.
     *
     * @return Color
     */
    public Color getFilteredColor() {
        return this.filteredColor;
    }

    /**
     * Returns the unselectedColor.
     *
     * @return Color
     */
    public Color getUnselectedColor() {
        return this.unselectedColor;
    }

    /**
     * Sets the unselectedColor.
     *
     * @param unselectedColor The unselectedColor to set
     */
    public void setUnselectedColor(Color unselectedColor) {
        this.unselectedColor = unselectedColor;
    }
    /**
     * Returns the smooth.
     * @return boolean
     */
    public boolean isSmooth() {
        return this.smooth;
    }

    /**
     * Sets the smooth.
     * @param smooth The smooth to set
     */
    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
        repaint();
    }

    /**
     * Sets the filteredColor.
     *
     * @param filteredColor The filteredColor to set
     */
    public void setFilteredColor(Color filteredColor) {
        this.filteredColor = filteredColor;
        repaint();
    }

    /**
     * Returns the defaultSize.
     *
     * @return double
     */
    public double getDefaultSize() {
        return this.defaultSize;
    }

    /**
     * Returns the defaultStroke.
     *
     * @return BasicStroke
     */
    public BasicStroke getDefaultStroke() {
        return this.defaultStroke;
    }

    /**
     * Sets the defaultStroke.
     *
     * @param defaultStroke The defaultStroke to set
     */
    public void setDefaultStroke(BasicStroke defaultStroke) {
        this.defaultStroke = defaultStroke;
        repaint();
    }

    /**
     * Sets the defaultSize.
     *
     * @param defaultSize The defaultSize to set
     */
    public void setDefaultSize(double defaultSize) {
        this.defaultSize = defaultSize;
        repaint();
    }
    
    /**
     * Returns the displayingStatistics.
     * @return boolean
     */
    public boolean isDisplayingStatistics() {
        return this.displayingStatistics;
    }

    /**
     * Sets the displayingStatistics.
     * @param displayingStatistics The displayingStatistics to set
     */
    public void setDisplayingStatistics(boolean displayingStatistics) {
        if (this.displayingStatistics != displayingStatistics) {
            this.displayingStatistics = displayingStatistics;
            repaint();
        }
    }

    /**
     * Returns the displayedItems.
     * @return int
     */
    public int getDisplayedItems() {
        return this.displayedItems;
    }

    /**
     * Returns the layoutTime.
     * @return long
     */
    public long getLayoutTime() {
        return this.layoutTime;
    }

    /**
     * Returns the redisplayTime.
     * @return long
     */
    public long getRedisplayTime() {
        return this.redisplayTime;
    }


    // interface ChangeListener
    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof Column) {
            Column column = (Column)e.getSource();
            invalidate(column);
        } else
            invalidate(null);
    }

    // interface MouseListener
    /**
     * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        mouseDragged(e);
    }
    
    

    // interface MouseMotionListener
    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        if (this.fisheyes != null) {
            this.fisheyes.setFocus(e.getX(), e.getY());
            repaint();
        }
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        int           sel = pickTop(e.getX(), e.getY(), this.parent.getBounds());
        BooleanColumn selection = this.selection;
        selection.disableNotify();
        try {
            if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0)
                selection.clear();
            if (sel != -1) {
                selection.setExtend(sel, true);
            }
        } finally {
            selection.enableNotify();
        }
    }
    /**
     * Returns the fisheyes.
     * @return Fisheyes
     */
    public Fisheyes getFisheyes() {
        return this.fisheyes;
    }

    /**
     * Sets the fisheyes.
     * @param fisheyes The fisheyes to set
     */
    public void setFisheyes(Fisheyes fisheyes) {
        this.fisheyes = fisheyes;
    }

    /**
     * Returns the excentric.
     * @return ExcentricLabels
     */
    public ExcentricLabels getExcentric() {
        return this.excentric;
    }

    /**
     * Sets the excentric.
     * @param excentric The excentric to set
     */
    public void setExcentric(ExcentricLabels excentric) {
        if (this.excentric == excentric)
            return;
        if (this.excentric != null) {
            this.excentric.setVisualization(null);
        }
        this.excentric = excentric;
        if (this.excentric != null) {
            this.excentric.setVisualization(this);
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public JComponent getComponent() {
        return getParent();
    }

}
