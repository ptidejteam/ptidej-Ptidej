/**
 *
    QuickUML; A simple UML tool that demonstrates one use of the 
    Java Diagram Package 

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

package uml.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import uml.diagram.AssociationLink;
import uml.diagram.AssociationLinkEditor;
import uml.diagram.AssociationLinkRenderer;
import uml.diagram.ClassEditor;
import uml.diagram.ClassFigure;
import uml.diagram.ClassRenderer;
import uml.diagram.CompositionLink;
import uml.diagram.CompositionLinkEditor;
import uml.diagram.CompositionLinkRenderer;
import uml.diagram.DependencyLink;
import uml.diagram.DependencyLinkEditor;
import uml.diagram.DependencyLinkRenderer;
import uml.diagram.GeneralizationLink;
import uml.diagram.GeneralizationLinkEditor;
import uml.diagram.GeneralizationLinkRenderer;
import uml.diagram.InterfaceEditor;
import uml.diagram.InterfaceFigure;
import uml.diagram.InterfaceRenderer;
import uml.diagram.NoteEditor;
import uml.diagram.NoteFigure;
import uml.diagram.NoteRenderer;
import uml.diagram.RealizationLink;
import uml.diagram.RealizationLinkEditor;
import uml.diagram.RealizationLinkRenderer;
import diagram.Diagram;
import diagram.DiagramModel;



/**
 * @class DiagramContainer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class DiagramContainer extends JScrollPane {

  protected Action saveAction = new SaveAction();
  protected Action closeAction = new CloseAction();
  protected Action printAction =  new PrintAction();
  protected Action scaledPrintAction =  new ScaledPrintAction();
  protected Action exportAction =  new ExportGIFAction();
  protected Action resizeAction = new ResizeAction();

  // Placeholders for the menu, the real actions are install on the diagram
  // when ever one is place in the container by the diagram ui
  protected Action copyAction = new CopyAction();
  protected Action cutAction = new CutAction();
  protected Action pasteAction = new PasteAction(); 

  protected Dimension defaultSize;

  // Color editor data
  private final static String[] colorProperties = {

    "composition.foreground","composition.background",
    "class.foreground","class.background",
    "association.foreground","association.background",
    "dependency.foreground","dependency.background",
    "diagram.foreground","diagram.background",
    "generalization.foreground","generalization.background",
    "interface.foreground","interface.background",
    "note.foreground","note.background",
    "realization.foreground","realization.background"

  };

  // Font editor data
  private final static String[] fontProperties = {

    "composition.font",
    "class.font",
    "association.font",
    "diagram.font",
    "generalization.font",
    "interface.font",
    "note.font",
    "dependency.font",
    "realization.font"
  };

  /**
   * Create a new Container for a diagram
   */
  public DiagramContainer() {

    super(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
    setView( createDiagram() );

  }

  /**
   * Update the menu bar. Add toggle option, etc.
   *
   * @param FlatMenuBar
   */
  public void updateMenus(FlatMenuBar menuBar) {

    JMenu menu = menuBar.getMenu("File");

    menu.add(new NewAction());
    menu.add(new OpenAction());
    menu.add(closeAction);
    menu.add(saveAction);
    menu.addSeparator();
    menu.add(exportAction);
    menu.addSeparator();
    menu.add(printAction);
    menu.add(scaledPrintAction);
 
    menu = menuBar.getMenu("Edit");
    menu.add(copyAction);
    menu.add(cutAction);
    menu.add(pasteAction);

    menu = menuBar.getMenu("Options");
    menu.add(resizeAction);
    menu.add(new FontAction(this, fontProperties));
    menu.add(new ColorAction(this, colorProperties));
    menu.add(new JSeparator(), -1);    
    
    JCheckBoxMenuItem item = new JCheckBoxMenuItem(new ToggleRefreshAction());
    item.setState(true);
    menu.add(item, -1);

    menu = menuBar.getMenu("Tool");
    menu.add(new BuildAction(this));

  }


  /**
   * Create a new diagram
   */
  public Diagram createDiagram() {

    Diagram diagram = new Diagram();

    // Install Link renderers
    diagram.setFigureRenderer(CompositionLink.class, new CompositionLinkRenderer());
    diagram.setFigureRenderer(AssociationLink.class, new AssociationLinkRenderer());
    diagram.setFigureRenderer(GeneralizationLink.class, new GeneralizationLinkRenderer());
    diagram.setFigureRenderer(RealizationLink.class, new RealizationLinkRenderer());
    diagram.setFigureRenderer(DependencyLink.class, new DependencyLinkRenderer());

    // Install Figure renderers
    diagram.setFigureRenderer(ClassFigure.class, new ClassRenderer());
    diagram.setFigureRenderer(InterfaceFigure.class, new InterfaceRenderer());
    diagram.setFigureRenderer(NoteFigure.class, new NoteRenderer());

    // Install Figure editors
    diagram.setFigureEditor(NoteFigure.class, new NoteEditor());
    diagram.setFigureEditor(ClassFigure.class, new ClassEditor());
    diagram.setFigureEditor(InterfaceFigure.class, new InterfaceEditor());

    // Install Link editors
    diagram.setFigureEditor(CompositionLink.class, new CompositionLinkEditor());
    diagram.setFigureEditor(GeneralizationLink.class, new GeneralizationLinkEditor());
    diagram.setFigureEditor(RealizationLink.class, new RealizationLinkEditor());
    diagram.setFigureEditor(DependencyLink.class, new DependencyLinkEditor());
    diagram.setFigureEditor(AssociationLink.class, new AssociationLinkEditor());

    if(defaultSize != null) 
      resizeDiagram(diagram, defaultSize);

    return diagram;

  }

  /**
   * Get the diagram for this view.
   *
   * @return Diagram
   */
  public Diagram getView() {
    return (Diagram)getViewport().getView();
  }

  /**
   * Set the diagram for this view.
   *
   * @param Diagram
   */
  public void setView(Diagram diagram) {

    Diagram oldDiagram = getView();
    setViewportView(diagram);

    if(diagram == null) { // Disable actions that need a diagram

      closeAction.setEnabled(false);
      saveAction.setEnabled(false);
      exportAction.setEnabled(false);
      printAction.setEnabled(false);
      scaledPrintAction.setEnabled(false);
      resizeAction.setEnabled(false);

      copyAction.setEnabled(false);
      cutAction.setEnabled(false);
      pasteAction.setEnabled(false);

    } else if(oldDiagram == null) { // Enable actions that need a diagram

      closeAction.setEnabled(true);
      saveAction.setEnabled(true);
      exportAction.setEnabled(true);
      printAction.setEnabled(true);
      scaledPrintAction.setEnabled(true);
      resizeAction.setEnabled(true);

      copyAction.setEnabled(true);
      cutAction.setEnabled(true);
      pasteAction.setEnabled(true);

    }

    super.firePropertyChange("diagram.container", oldDiagram, diagram);

  }

  /**
   * Find the Frame for this event
   */
  protected Component getFrame(ActionEvent e) {  
    return getFrame((Component)e.getSource());
  }

  protected Frame getFrame(Component frame) {

    for(;!(frame instanceof Frame); frame = frame.getParent())
      if(frame instanceof JPopupMenu)
        frame = ((JPopupMenu)frame).getInvoker();

    return (frame instanceof Frame) ? (Frame)frame : null;

  }
  
  public Frame getFrame() {
    return getFrame(this);
  }

  /**
   * Load an Icon with the IconManager
   */
  protected Icon getIcon(String name) {
    return IconManager.getInstance().getIconResource(this, name);
  }

  /**
   * Popup an error message
   */
  protected void displayError(Throwable t) {
    t.printStackTrace();
    displayError(t.getClass().getName(), t.getMessage());
  }

  /**
   * Popup an error message
   */
  protected void displayError(String title, String msg) {
    JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Resize & update the diagram
   */
  protected void resizeDiagram(Diagram diagram, Dimension d) {

    diagram.setMinimumSize(d);
    diagram.setPreferredSize(d);     
    diagram.setBounds(0, 0, d.width, d.height);

    doLayout();

  }


  /**
   * @class NewAction
   *
   */
  protected class NewAction extends AbstractAction {
    
    public NewAction() {
      super("New", getIcon("images/New.gif"));
    }

    public void actionPerformed(ActionEvent e) {
      setView( createDiagram() );
    }

  }


  /**
   * @class CloseAction
   *
   */
  protected class CloseAction extends AbstractAction {
    
    public CloseAction() {
      super("Close", getIcon("images/Close.gif"));
    }

    public void actionPerformed(ActionEvent e) {
      setView( null );
    }

  }

  /**
   * @class OpenAction
   *
   */
  protected class OpenAction extends FileAction {
    
    private SimpleFilter filter = new SimpleFilter("dia", "Diagrams");

    public OpenAction() {
      super("Open", getIcon("images/Open.gif"));
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {

      // Configure the chooser for the open request
      JFileChooser chooser = getChooser();
      chooser.setAcceptAllFileFilterUsed(false);
      chooser.setFileFilter(filter);

      if(JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(getFrame(e)))
        openFile(chooser.getSelectedFile());
      
    }
    
    public void openFile(File file) {

      try {

        // Check that the file name matches
        String name = file.getName().toLowerCase();
        if(!name.endsWith(".dia")) 
          throw new RuntimeException("Not a valid diagram file extension");
                
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        
        // Get the current view or create one
        Diagram diagram = getView();
        if(diagram == null)
          setView(diagram = createDiagram());
        
        diagram.setModel((DiagramModel)ois.readObject());
        diagram.repaint();

      } catch(Throwable t) { 
        t.printStackTrace();
        displayError("File Error", "Invalid diagram file");
      }
      
    }

  } /* OpenAction */



  /**
   * @class SaveAction
   *
   */
  protected class SaveAction extends FileAction {
    
    private SimpleFilter filter = new SimpleFilter("dia", "Diagrams");

    public SaveAction() {
      super("Save", getIcon("images/Save.gif"));
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {

      // Configure the chooser for the save request
      JFileChooser chooser = getChooser();
      chooser.setAcceptAllFileFilterUsed(false);
      chooser.setFileFilter(filter);

      if(JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(getFrame(e)))
        saveFile(chooser.getSelectedFile());
      
    }
    
    public void saveFile(File file) {

      // Adjust the file name to match
      String name = file.getName().toLowerCase();
      if(!name.endsWith(".dia")) 
        file = new File(file.getName() + ".dia");

      try {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject((DiagramModel)getView().getModel());

      } catch(Throwable t) { 
        t.printStackTrace();
        displayError("File Error", "Error writing to file");
      }

    }

  } /* SaveAction */


  /**
   * @class ExportAction
   *
   */
  protected class ExportGIFAction extends ExportAction {

    private SimpleFilter filter = new SimpleFilter("gif", "Images");

    public ExportGIFAction() {
      super("Save Image", getIcon("images/ExportImage.gif"));
    }

    protected Component getComponent() {
      return getView();
    }

    public void actionPerformed(ActionEvent e) {

      // Configure the chooser for the save request
      JFileChooser chooser = getChooser();
      chooser.setAcceptAllFileFilterUsed(false);
      chooser.setFileFilter(filter);

      if(JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(getFrame(e))) {
        try {
          writeGIF(chooser.getSelectedFile());
        } catch(Throwable t) { displayError(t); }
      }

    }

  }

  /**
   * @class PrintAction
   *
   */
  protected class PrintAction extends PrintableAction {

    public PrintAction() {
      super("Print ...");
    }

    public Component getComponent() {
      return getView();
    }

  }

  /**
   * @class ScaledPrintAction
   *
   */
  protected class ScaledPrintAction extends ScaledPrintableAction {

    public ScaledPrintAction() {
      super("Scaled Print", getIcon("images/Print.gif"));
    }

    public Component getComponent() {
      return getView();
    }

  }

  /**
   * @class CopyAction
   *
   */
  protected class CopyAction extends AbstractAction {
    
    public CopyAction() {
      super("Copy", getIcon("images/Copy.gif"));
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('C', Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
      
      Diagram diagram = getView();
      if(diagram != null) {

        Action action = diagram.getActionMap().get("copy");
        if(action != null)
          action.actionPerformed(e);

      }

    }

  }

  /**
   * @class CutAction
   *
   */
  protected class CutAction extends AbstractAction {
    
    public CutAction() {
      super("Cut", getIcon("images/Cut.gif"));
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('X', Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {

      Diagram diagram = getView();
      if(diagram != null) {

        Action action = diagram.getActionMap().get("cut");
        if(action != null)
          action.actionPerformed(e);

      }

    }

  }

  /**
   * @class PasteAction
   *
   */
  protected class PasteAction extends AbstractAction {
    
    public PasteAction() {
      super("Paste", getIcon("images/Paste.gif"));
      putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('V', Event.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {

      Diagram diagram = getView();
      if(diagram != null) {

        Action action = diagram.getActionMap().get("paste");
        if(action != null)
          action.actionPerformed(e);

      }

    }

  }


  /**
   * @class ResizeAction
   *
   */
  protected class ResizeAction extends AbstractAction {
    
    public ResizeAction() {
      super("Resize ...");
    }

    public void actionPerformed(ActionEvent e) {
      
      Diagram diagram = getView();
      if(diagram != null) 
        promptResize(diagram);

    }

    protected void promptResize(Diagram diagram) {
      
      SizePanel size = new SizePanel(diagram);
      int n = JOptionPane.showConfirmDialog(DiagramContainer.this, 
                                            size, "Resize Diagram", 
                                            JOptionPane.OK_CANCEL_OPTION);
      if(n == JOptionPane.OK_OPTION) {
        
        defaultSize = size.getDimension(defaultSize);
        resizeDiagram(diagram, defaultSize);     
        
      }
      
    }

  }


  /**
   * @class ToggleRefreshAction
   *
   */
  protected class ToggleRefreshAction extends AbstractAction {

    public ToggleRefreshAction() {
      super("Fast refresh");
    }

    public void actionPerformed(ActionEvent e) {

      Diagram diagram = getView();
      if(diagram != null) {

        boolean toggle = !diagram.isFastRefreshEnabled();
        diagram.enableFastRefresh(toggle);

      }

    }

  }

}
