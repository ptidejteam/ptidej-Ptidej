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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @class Diagram
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is the main component used in the diagram package. It represents the visible 
 * surface that will be displayed in an application and used as a canvas that displays
 * a DiagramModel.
 */
public class Diagram extends JComponent 
  implements Scrollable {
  
  // UI Class
  private static final String uiClassID = "DiagramUI";

  // Data models
  private SelectionModel selectionModel;
  private DiagramModel diagramModel;

  // Cache a rectangle (for repainting)
  private Rectangle figureBounds = new Rectangle();

  // Renderer and Editor maps
  private HashMap rendererMap = new HashMap();
  private HashMap editorMap = new HashMap();

  private boolean fastRefresh;

  static {
    UIManager.put("DiagramUI", "diagram.DiagramUI");
  }

  /**
   * Create a new surface with double buffering 
   */
  public Diagram() {
    this(new DefaultDiagramModel());
  }


  /**
   * Create a new surface
   *
   * @param DiagramModel model
   */
  public Diagram(DiagramModel model) {
    this(model, new DefaultSelectionModel());
  }


  /**
   * Create a new surface
   *
   * @param DiagramModel model
   */
  public Diagram(DiagramModel model, SelectionModel selectionModel) {
    this(model, selectionModel, true);

  }

  /**
   * Create a new surface
   *
   * @param DiagramModel model
   * @param boolean buffering 
   */
  public Diagram(DiagramModel model, SelectionModel selectionModel, boolean doubleBuffered) {

    if(model == null || selectionModel == null)
      throw new IllegalArgumentException();

    setDoubleBuffered(doubleBuffered);

    updateUI();
    
    setOpaque(true);
    setModel(model);
    setSelectionModel(selectionModel);

    enableFastRefresh(true);

  }


  /**
   * Set the UI delegate for this Diagram
   *
   * @return DiagramUI 
   */
  public DiagramUI getUI() {
    return (DiagramUI)ui;
  }


  /**
   * Returns a string that specifies the name of the l&f class that renders this component
   */
  public String getUIClassID() {
    return uiClassID;
  }


  /**
   * Set the UI delegate for this Diagram
   *
   * @param DiagramUI 
   */
  public void setUI(DiagramUI ui) {
    super.setUI(ui);
  }


  /**
   * Notification from the UIFactory that the ComponentUI has changed
   */
  public void updateUI() { 
    setUI((DiagramUI)UIManager.getUI(this));
  }


  /**
   * Set the DiagramModel
   *
   * @param DiagramModel
   */
  public void setModel(DiagramModel diagramModel) {
     
    DiagramModel oldValue = this.diagramModel;
    this.diagramModel = diagramModel;

    firePropertyChange("model", oldValue, diagramModel);
    
  }


  /**
   * Get the DiagramModel
   *
   * @return DiagramModel
   */
  public DiagramModel getModel() {
    return diagramModel;
  }


  /**
   * Set the SelectionModel that is used internally to keep track of which
   * items have been selected.
   *
   * @param SelectionModel
   */
  public void setSelectionModel(SelectionModel selectionModel) {

    SelectionModel oldValue = this.selectionModel;
    this.selectionModel = selectionModel;

    firePropertyChange("selectionModel", oldValue, selectionModel);

  }


  /**
   * Get the SelectionModel that contains the currently selected items
   *
   * @return SelectionModel
   */
  public SelectionModel getSelectionModel() {
    return selectionModel;
  }


  /**
   * Get this surfaces rendering component. This will be used to
   * paint each of the Figures that are contained within each
   * layer.
   *
   * @param Class
   *
   * @return FigureRenderer
   */
  public FigureRenderer getFigureRenderer(Class itemClass) {
    
    for(Object o = null; o == null && itemClass != null; itemClass = itemClass.getSuperclass()) {
      if((o = rendererMap.get(itemClass)) != null)
        return (FigureRenderer)o;
    }
    
    return null;

  }

  /**
   * Set the renderer for a particular class of Figures.
   *
   * @param Class
   * @param FigureRenderer
   */
  public void setFigureRenderer(Class itemClass, FigureRenderer renderer) {

    if(renderer == null)
      rendererMap.remove(itemClass);
    else
      rendererMap.put(itemClass, renderer);

  }


  /**
   * Get this surfaces rendering component. This will be used to
   * paint each of the Figures that are contained within each
   * layer.
   *
   * @param Class
   *
   * @return FigureEditor
   */
  public FigureEditor getFigureEditor(Class itemClass) {

    for(Object o = null; o == null && itemClass != null; itemClass = itemClass.getSuperclass()) {
      if((o = editorMap.get(itemClass)) != null)
        return (FigureEditor)o;
    }
    
    return null;

  }


  /**
   * Set the editor for a particular class of Figures.
   *
   * @param Class
   * @param FigureEditor
   */
  public void setFigureEditor(Class itemClass, FigureEditor editor) {

    if(editor == null)
      editorMap.remove(itemClass);
    else
      editorMap.put(itemClass, editor);

  }


  /**
   * Find the Figure at the given point.
   *
   * @param Point2D
   *
   * @return Figure or null
   */
  public Figure findFigure(Point2D pt) {
    return (ui == null) ? null : ((DiagramUI)ui).findFigure(pt);
  }


  /**
   * Enable fast refresh. This forces repaints more often, good for
   * faster machines. 
   *
   * @param boolean
   */
  public void enableFastRefresh(boolean flag) {

    if(flag != fastRefresh) {

      boolean last = fastRefresh;
      fastRefresh = flag;
      
      firePropertyChange("fastRefresh", (last ? "true":"false"), (last ? "false":"true"));
    
    }

  }

  /**
   * Test to see if fast refresh is enabled.
   *
   * @return boolean
   */
  public boolean isFastRefreshEnabled() {
    return fastRefresh;
  }


  /**
   * Get the extended bounds for a Figure, these are bounds that a component should use
   * to include a small area for decoration, such as different borders, or arrow heads
   * that fall just outside the Figures normal bounds.
   *
   * @param Figure
   * @param Rectangle2D reuse a rectangle
   * 
   * @return Rectangle2D
   */
  public Rectangle2D getDecoratedBounds(Figure figure, Rectangle2D rcBounds) {
    
    // Get the renderer for this Figure
    FigureRenderer renderer = null;
    if(figure == null || (renderer = getFigureRenderer(figure.getClass())) == null)
      return rcBounds;

    // Return the suggested bounds for this figure
    return (rcBounds = renderer.getDecoratedBounds(this, figure, rcBounds));

  }


  // Scrollable implementation


  private void checkScrollableParameters(Rectangle visibleRect, int orientation) {

    if (visibleRect == null) 
      throw new IllegalArgumentException("visibleRect must be non-null");

    switch (orientation) {
        case SwingConstants.VERTICAL:
        case SwingConstants.HORIZONTAL:
          break;
        default:
          throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
    }

  }

  /**
   * Returns the preferred size of the viewport for a view component.
   */
  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  /**
   * Components that display logical rows or columns should compute the scroll increment 
   * that will completely expose one new row or column, depending on the value of
   * orientation.
   * 
   * Set a minmum scroll size of 4 pixels in either direction
   */
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    
    checkScrollableParameters(visibleRect, orientation);
    return 4;

  }

  /**
   * Components that display logical rows or columns should compute the scroll increment 
   * that will completely expose one block of rows or columns, depending on the value 
   * of orientation.
   *
   * Allow block scrolls as big as the request made
   */
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    checkScrollableParameters(visibleRect, orientation);
    return (orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width;

  }

  /**
   * Return true if a viewport should always force the width of this Scrollable to match 
   * the width of the viewport.  
   */
  public boolean getScrollableTracksViewportWidth() {

    if(getParent() instanceof JViewport) 
      return (((JViewport)getParent()).getWidth() > getPreferredSize().width);
    
    return false;

  }

  /**
   * Components that display logical rows or columns should compute the scroll increment 
   * that will completely expose one new row or column, depending on the value of orientation.
   */
  public boolean getScrollableTracksViewportHeight() {

    if(getParent() instanceof JViewport) 
      return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
    
    return false;

  }

}
