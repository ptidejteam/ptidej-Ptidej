package uml.diagram;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;

import uml.ui.FlatTextArea;

/**
 * @class ClassRendererComponent
 * @author Eric Crahen
 */
public class ClassRendererComponent extends CustomComponent {

  protected static final CustomUI classUI = new CustomUI("class");
  protected static final Insets margin = new Insets(1,1,1,1);

  protected JTextField title = new JTextField();
  protected FlatTextArea fields = new FlatTextArea(true);
  protected FlatTextArea members = new FlatTextArea(true);
  protected int divider = -1;

  static { // Set up some default colors

    UIManager.put("class.background", new Color(0xFF, 0xFF, 0xDD));
    UIManager.put("class.foreground", Color.black);
    UIManager.put("class.border", BorderFactory.createLineBorder(Color.black, 1));

  }

  /**
   * Create a new Component for painting classes
   */
  public ClassRendererComponent() {

    // Layout the component
    this.setLayout(null);
      
    // Title area
    title.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    title.setOpaque(true);
    title.setMargin(margin);
    title.setHorizontalAlignment(JTextField.CENTER);

    this.add(title);

    // Field text area
    fields.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    fields.setMargin(margin);
    this.add(fields);

    // Member text area
    members.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    members.setMargin(margin);
    this.add(members);

    setUI(classUI);

  }


  public void setTitle(String s) {
    title.setText(s);
  }

  public String getTitle() {
    return title.getText();
  }

  public void setFields(String s) {
    fields.setText(s);
  }

  public String getFields() {
    return fields.getText();
  }

  public void setMembers(String s) {
    members.setText(s);
  }

  public String getMembers() {
    return members.getText();
  }

  public void setDivider(int divider) {
    this.divider = divider;
  }

  /**
   * Create a built in layout, there seems to be a bug with current LayoutManagers
   * placing TextAreas in scroll panes with borders in the same component correctly.
   * They leave an extra pixel at the bottom with the hieght would be an odd number.
   *
   * This will garuntee the component will be laid out as expected.
   */
  public void doLayout() {

    Insets insets = this.getInsets();

    int w = this.getWidth() - (insets.left + insets.right);
    int h = this.getHeight() - (insets.top + insets.bottom);
    
    int x = insets.left;
    int y = insets.top;

    // Layout the title across the top
    int componentHeight = title.getPreferredSize().height + 2;

    title.reshape(x, y, w, componentHeight);

    // Shift down
    y += componentHeight;
    h -= componentHeight;

    // Layout the fields in the middle
    //    componentHeight = (divider == -1) ? (int)((double)h*(3.0/8.0)) : divider;

    componentHeight = (divider == -1) ? fields.getPreferredSize().height + 2: divider + 1;  
    fields.reshape(x, y, w, componentHeight);
    
    // Shift down
    y += componentHeight;
    h -= componentHeight;

    // Layout the members at the bottom 
    componentHeight = h;
    members.reshape(x, y, w, componentHeight);

  }

}
