/**
 *
    Java Diagram Package; An extremely flexible and fast multipurpose diagram 
    component for Swing.
    Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package diagram;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.CellRendererPane;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.RepaintManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import diagram.figures.PolyLink;

/**
 * @class DiagramUI
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is the based UI delegate for a Diagram component.
 */
public class DiagramUI extends ComponentUI { 

  static { // Install the UI with the UIManager

    UIManager.put("diagram.background", new Color(0xEF, 0xEF, 0xEF));
    UIManager.put("diagram.foreground", Color.black);

  }

  // Associated components
  protected Diagram diagram;
  protected boolean fastRefresh;

  protected CellRendererPane cellRendererPane = new CellRendererPane();
  protected ModelHandler modelListener = new ModelHandler();
  protected PropertyChangeHandler propertyListener = new PropertyChangeHandler();

  // Layer for sorting the figures 
  protected Layer figureLayer = new Layer();
  protected Layer linkLayer = new Layer();

  // Rectangles cached for bounds calculations
  protected Rectangle clip = new Rectangle();
  protected Rectangle bounds = new Rectangle();

  // Caches for editors and renderers
  private static HashMap editorCache = new HashMap();
  private static HashMap rendererCache = new HashMap();

  // Map for links
  protected HashMap linkMap = new HashMap();

  // Orginal Color & Border
  private Color originalForeground;
  private Color originalBackground;

  /**
   *
   */
  public DiagramUI() {
    UIManager.getDefaults().addPropertyChangeListener(propertyListener);
  }

  /**
   * Get a FigureRenderer from the cache, if a renderer of that class does
   * not yet exist, create and cache on using the default constructor.
   *
   * @return FigureRenderer
   */
  public synchronized static FigureRenderer getRenderer(Class c) {

    FigureRenderer renderer = (FigureRenderer)rendererCache.get(c);

    if(renderer == null) {
      try { 
        renderer = (FigureRenderer)c.newInstance();
        rendererCache.put(c, renderer);
      } catch(Throwable t) { /* ignore renderers that have no default constructor */ }
    }

    return renderer;

  }


  /**
   * Get a FigureEditor from the cache, if a renderer of that class does
   * not yet exist, create and cache on using the default constructor.
   *
   * @return FigureEditor
   */
  public synchronized static FigureEditor getEditor(Class c) {

    FigureEditor editor = (FigureEditor)editorCache.get(c);

    if(editor == null) {
      try { 
        editor = (FigureEditor)c.newInstance();
        editorCache.put(c, editor);
      } catch(Throwable t) { /* ignore editors that have no default constructor */ }
    }

    return editor;

  }

  /**
   * Create a new UI for a Diagram
   *
   * @return ComponentUI
   */
  public static ComponentUI createUI(JComponent c) {
    return new DiagramUI();
  }

 
  /**
   * Install this UI on a Diagram
   *
   * @param JComponent
   */
  public void installUI(JComponent c) {

    if(!(c instanceof Diagram))
      throw new RuntimeException("This UI is for Diagram components only");
    
    diagram = (Diagram)c;

    // Install the listeners
    diagram.add(cellRendererPane);
    diagram.addPropertyChangeListener(propertyListener);

    DiagramModel model = diagram.getModel();
    if(model != null)
      model.addDiagramDataListener(modelListener);

    SelectionModel selectionModel = diagram.getSelectionModel();
    if(selectionModel != null)
      selectionModel.addSelectionListener(modelListener);

    // Install the key mapping for editors
    InputMap map = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "cancelEditing");

    // Install the key mappings for the clipboard
    map = diagram.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

    // some keyboards have special keys for copy/cut/paste
    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_CUT, 0, false), "cut");
    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_COPY, 0, false), "copy");
    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_PASTE, 0, false), "paste");

    // add mappings for the usualy copy/cut/paste keystokes as well
    map.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false), "cut");
    map.put(KeyStroke.getKeyStroke("control X"), "cut");
    map.put(KeyStroke.getKeyStroke("control C"), "copy");
    map.put(KeyStroke.getKeyStroke("control INSERT"), "copy");
    map.put(KeyStroke.getKeyStroke("control V"), "paste");
    map.put(KeyStroke.getKeyStroke("shift INSERT"), "paste");

    installRenderers(diagram);
    installEditors(diagram);

    // Change the colors
    installColors(diagram);

  }

  /**
   * Install theme for the diagram under this UI
   *
   * @param Diagram
   */
  protected void installColors(Diagram diagram) {  

    originalForeground = diagram.getForeground();
    originalBackground = diagram.getBackground();

    diagram.setBackground(UIManager.getColor("diagram.background"));
    diagram.setForeground(UIManager.getColor("diagram.foreground"));
    
  }

  /**
   * Install editors for the diagram under this UI
   *
   * @param Diagram
   */
  protected void installRenderers(Diagram diagram) {

    diagram.setFigureRenderer(Object.class, getRenderer(DefaultFigureRenderer.class));
    diagram.setFigureRenderer(PolyLink.class, getRenderer(DefaultLinkRenderer.class));

  }

  /**
   * Install renderer for the diagram under this UI
   *
   * @param Diagram
   */
  protected void installEditors(Diagram diagram) {

    diagram.setFigureEditor(Object.class, getEditor(DefaultFigureEditor.class));
    diagram.setFigureEditor(PolyLink.class, getEditor(DefaultLinkEditor.class));

  }

  /**
   * Uninstall this UI from a Diagram
   *
   * @param JComponent
   */
  public void uninstallUI(JComponent c) {

    if(!(c instanceof Diagram))
      throw new RuntimeException("This UI is for Diagram components only");

    if(c != diagram)
      throw new RuntimeException("This UI is not installed on this Diagram");

    // Remove the listeners
    diagram.remove(cellRendererPane);
    diagram.removePropertyChangeListener(propertyListener);

    DiagramModel model = diagram.getModel();
    if(model != null)
      model.removeDiagramDataListener(modelListener);

    SelectionModel selectionModel = diagram.getSelectionModel();
    if(selectionModel != null)
      selectionModel.removeSelectionListener(modelListener);
    
    // Remove the key mapping for editors
    InputMap map = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    map.remove(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false));

    // Remove the key mappings for the clipboard
    map = diagram.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

    // some keyboards have special keys for copy/cut/paste
    map.remove(KeyStroke.getKeyStroke(KeyEvent.VK_CUT, 0, false));
    map.remove(KeyStroke.getKeyStroke(KeyEvent.VK_COPY, 0, false));
    map.remove(KeyStroke.getKeyStroke(KeyEvent.VK_PASTE, 0, false));

    // remove mappings for the usualy copy/cut/paste keystokes as well
    map.remove(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false));
    map.remove(KeyStroke.getKeyStroke("control X"));
    map.remove(KeyStroke.getKeyStroke("control C"));
    map.remove(KeyStroke.getKeyStroke("control INSERT"));
    map.remove(KeyStroke.getKeyStroke("control V"));
    map.remove(KeyStroke.getKeyStroke("shift INSERT"));

    // Uninstall colors
    uninstallColors(diagram);

    // Clear any reused objects that need clearing
    diagram = null;

    linkLayer.removeAll();
    figureLayer.removeAll();
    
  }

  /**
   * Install theme for the diagram under this UI
   *
   * @param Diagram
   */
  protected void uninstallColors(Diagram diagram) {  

    diagram.setBackground(originalBackground);
    diagram.setForeground(originalForeground);
    
  }

  /**
   * Find the Figure at the given point.
   *
   * @param Point2D
   *
   * @return Figure or null
   */
  public Figure findFigure(Point2D pt) {

    Figure figure = figureLayer.findFigure(pt);
    if(figure == null) 
      figure = linkLayer.findFigure(pt);

    return figure;

  }


  /**
   * Paint the associated Diagram component
   *
   * @param Graphics
   */
  public void paint(Graphics g, JComponent c) {

    linkLayer.paintLayer(g);
    figureLayer.paintLayer(g);

  }

  /**
   * Refresh a Figure by using the decorated area recommended by the renderer
   * that paints that figure. this either damages or repaints depending on wether
   * or not fast refresh is enabled.
   *
   * @param Figure
   */
  public void refreshFigure(Figure figure) {

    if(fastRefresh)
      repaintFigure(figure);
    else
      damageFigure(figure);

  }

  /**
   * Refresh a region
   *
   * @param Rectangle - region to update or null for whole diagram
   */
  public void refreshRegion(Rectangle rc) {

    if(fastRefresh)
      repaintRegion(rc);
    else
      damageRegion(rc);

  }

  /**
   * Repaint an area of the associated Diagram component
   *
   * @param Rectangle - region to update or null for whole diagram
   */
  public void repaintRegion(Rectangle rc) {

    RepaintManager m = RepaintManager.currentManager(diagram);

    if(rc != null)
      m.addDirtyRegion(diagram, rc.x, rc.y, rc.width, rc.height);
    else
      m.markCompletelyDirty(diagram);

    m.paintDirtyRegions();

  }

  /**
   * Repaint a Figure by using the decorated area recommended by the renderer
   * that paints that figure
   *
   * @param Figure
   */
  public void repaintFigure(Figure figure) {

    FigureRenderer renderer = diagram.getFigureRenderer(figure.getClass());
    bounds = (Rectangle)renderer.getDecoratedBounds(diagram, figure, bounds);
    
    repaintRegion(bounds);

  }

  /**
   * Damage an area of the associated Diagram component
   *
   * @param Rectangle - region to update or null for whole diagram
   */
  public void damageRegion(Rectangle rc) {

    RepaintManager m = RepaintManager.currentManager(diagram);

    if(rc == null)
      m.markCompletelyDirty(diagram);
    else
      m.addDirtyRegion(diagram, rc.x, rc.y, rc.width, rc.height);

  }

  /**
   * Damage a Figure by using the decorated area recommended by the renderer
   * that paints that figure
   *
   * @param Figure
   */
  public void damageFigure(Figure figure) {

    // Damage the figure
    FigureRenderer renderer = diagram.getFigureRenderer(figure.getClass());
    bounds = (Rectangle)renderer.getDecoratedBounds(diagram, figure, bounds);   
    damageRegion(bounds);

  }


  /**
   * Create an association between a link & figure so that the link
   * can be repainted with the figure.
   */
  public void addConnection(Figure figure, Link link) {
    
    // Map the figure to the link
    ArrayList list = (ArrayList)linkMap.get(figure);
    if(list == null) {
      list = new ArrayList();
      linkMap.put(figure, list);
    }

    list.add(link);
    
  }

  /**
   * Break an association between a link & figure so that the link
   * can no longer be repainted with the figure.
   */
  public void removeConnection(Figure figure, Link link) {

    ArrayList list = (ArrayList)linkMap.get(figure);
    if(list != null) { 

      list.remove(link);
      if(list.isEmpty())
        linkMap.remove(figure);

    }

  }

  /**
   * Get the null terminated list of Figures connected with a specific figure
   *
   * @param Figure
   * @param Figure[] reuse
   * @return Figure[]
   */
  public Figure[] getConnected(Figure figure, Figure[] array) {

    // Get the maped figures
    ArrayList list = (ArrayList)linkMap.get(figure);
    if(list != null) {
      
      // Allocate an array if needed
      if(array == null)
        array = new Figure[list.size()];

      array = (Figure[])list.toArray((Object[])array);

    } else if(array != null && list != null && list.size() > 0)
      list.set(0, null); // Terminate array
      
    return array;

  }

  /**
   * @class Layer
   * 
   * A Layer keeps track of the z-order for items in a Diagram. The z-order
   * in which the figures are displayed is not part of the data model, its 
   * a responibiliy of the view of that data model.
   */
  protected class Layer {

    // List to keep track of items in the layer
    protected ArrayList figureList = new ArrayList();
    
    /**
     * Add a figure to this Layer
     */
    public void add(Figure f) {
      figureList.add(f);
    }

    /**
     * Remove all Figures associated with this Layer
     */
    public void removeAll() {
      figureList.clear();
    }

    /**
     * Remove a Figure from this Layer
     */
    public void remove(Figure f) {
      figureList.remove(f);
    }

    /**
     * Move a Figure to the top of the list (higher z-order)
     *
     * @param Figure
     */
    public void raise(Figure f) {
      
      // Pop it out of the list and Push it back into the back
      figureList.remove(f);
      figureList.add(f);
      
    }
    
    /**
     * Move a Figure to the top of the list (lower z-order)
     *
     * @param Figure
     */
    public void lower(Figure f) {
      
      // Pop it out of the list and Push it back into the front
      figureList.remove(f);
      figureList.add(0, f);
      
    }
    
    /**
     * Test the layer to see if it contains a specific Figure
     *
     * @param Figure
     */
    public boolean contains(Figure f) {
      return figureList.contains(f);
    }
    
    /**
     * Find the Figure at the given point. This searches for the higest
     * z-order match.
     *
     * @param Object if an instance of Class is provided only a result of
     * that type can be returned.
     *
     * @return Figure
     */
    public Figure findFigure(Point2D pt) {
      
      // Walk the list backwards to
      for(int i = figureList.size(); --i >= 0;) {
        
        Figure f = (Figure)figureList.get(i);
        if(f.contains(pt))
          return f;
        
      }
      
      return null;
      
    }

    /**
     * Paint the layer on some Diagram. Subclasses can override the
     * paintComponet method to change how this layer is painted.
     *
     * @param Graphics context to paint on
     */
    public void paintLayer(Graphics g) {

      clip = g.getClipBounds(clip);

      SelectionModel selectionModel = diagram.getSelectionModel();

      for(int i=0; i < figureList.size(); i++) {
      
        // Get each Figure and paint it with the renderer the diagram supplies.
        Figure figure = (Figure)figureList.get(i);
        FigureRenderer renderer = diagram.getFigureRenderer(figure.getClass());
        if(renderer == null)
          throw new RuntimeException("No renderer for this Figure");

        // Draw the item if it intersects the clipping rectangle
        if(clip == null || figure.intersects(clip)) {

          // Figure out if this is a selected component
          boolean hasFocus = (selectionModel == null) ? false : selectionModel.contains(figure);
          
          // Get the Component that should be used to render this Figure
          Component c = renderer.getRendererComponent(diagram, figure, hasFocus);
          if(c == null)
            throw new RuntimeException("No renderer Component for this Figure");

          // Set the bounds of the Component to match its counterpart (Figure)
          bounds = (Rectangle)renderer.getDecoratedBounds(diagram, figure, bounds);
          
          // Paint the figure
          paintFigure(g, c, bounds);
          
        }
        
      }


    }

    
    /**
     * Paint the figure on the Diagram with the given rendering Component
     *
     * @param Graphics
     * @param Component
     * @param Rectangle
     */
    public void paintFigure(Graphics g, Component c, Rectangle r) {
      cellRendererPane.paintComponent(g, c, diagram, r.x, r.y, r.width, r.height, true);
    }


  } /* Layer */


  /**
   * @class ModelHandler
   *
   * Listens to the DiagramModel for Figures being added, removed, selected and
   * deselected.
   */
  protected class ModelHandler 
    implements DiagramModelListener, DiagramSelectionListener {

    /**
     * Notify of a new figure being added to a DiagramModel
     *
     * @param DiagramModel
     * @param Figure
     */
    public void figureAdded(DiagramModel model, Figure figure) {

      if(figure instanceof Link) {

        Link link = (Link)figure;
        linkLayer.add(link);      
        
        addConnection(link.getSource(), link);
        addConnection(link.getSink(), link);

      } else
        figureLayer.add(figure);
      
    }
    
    /**
     * Notify of a figure being removed from a DiagramModel
     *
     * @param DiagramModel
     * @param Figure
     */
    public void figureRemoved(DiagramModel model, Figure figure) {

      if(figure instanceof Link) {

        Link link = (Link)figure;
        linkLayer.remove(link);

        removeConnection(link.getSource(), link);
        removeConnection(link.getSink(), link);

      } else
        figureLayer.remove(figure);

    }
      
    /**
     * Figure selected
     */
    public void figureAdded(SelectionModel model, Figure figure) {
      
      if((figure instanceof Link) && linkLayer.contains(figure))
        linkLayer.raise(figure);
      else if(figureLayer.contains(figure))
        figureLayer.raise(figure);

    }
    
    /**
     * Figure deselected
     */
    public void figureRemoved(SelectionModel model, Figure figure) { }
    
  } /* ModelListener */


  /**
   * @class PropertyChangeHandler
   *
   */
  protected class PropertyChangeHandler implements PropertyChangeListener {

    public void propertyChange(PropertyChangeEvent e) {

      String propertyName = e.getPropertyName();

      // Listen for DiagramModel changes.
      if(propertyName.equals("model")) {

        DiagramModel oldModel = (DiagramModel)e.getOldValue();
        DiagramModel newModel = (DiagramModel)e.getNewValue();

        if(oldModel != null)
          oldModel.removeDiagramDataListener(modelListener);

        linkLayer.removeAll();
        figureLayer.removeAll();
        linkMap.clear();

        // Update the layers for the new model
        if(newModel != null) {

          newModel.addDiagramDataListener(modelListener);       
          for(Iterator i = newModel.iterator();i.hasNext();) 
            modelListener.figureAdded(newModel, (Figure)i.next());
          
          
        }

        // redraw
        repaintDiagram();

      } else if(propertyName.equals("selectionModel")) {

        SelectionModel oldModel = (SelectionModel)e.getOldValue();
        SelectionModel newModel = (SelectionModel)e.getNewValue();

        if(oldModel != null)
          oldModel.removeSelectionListener(modelListener);
        
        if (newModel != null) 
          newModel.addSelectionListener(modelListener);
        
        repaintDiagram();

      } else if(propertyName.equals("fastRefresh")) {
        fastRefresh = e.getNewValue().equals("true");
      } else if(propertyName.equals("diagram.background")) {
        diagram.setBackground((Color)e.getNewValue());
      } else if(propertyName.equals("diagram.foreground")) {
        diagram.setForeground((Color)e.getNewValue());
      } else if(propertyName.equals("diagram.border")) {
        diagram.setBorder((Border)e.getNewValue());
      }

    }

  } /* PropertyChangeHandler */

  /**
   * Force a repaint
   */
  private final void repaintDiagram() {
    diagram.invalidate();
    diagram.repaint();
  }

}
