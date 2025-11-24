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
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.WeakHashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @class IconManager
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * The IconManager provides a common way to relate and load images
 * for any Component. Responisble for finding, resolving and loading
 * the Images & Icons.
 */
public class IconManager {

  private static IconManager instance = new IconManager();

  // registered related images
  private WeakHashMap resourceMap = new WeakHashMap();
  // loaded images
  private HashMap imageMap = new HashMap();

  private IconManager() { }

  /** 
   * Get an instance of the IconManager
   *
   * @return IconManager
   */
  static public IconManager getInstance() {
    return instance;
  }

  /**
   * Given a resource name, load an image.
   *
   * @param String
   * @return Image
   */
  protected Image loadImageResource(String resourceName) {

    Toolkit kit = Toolkit.getDefaultToolkit();
    URL url = getClass().getResource(resourceName);

    if(url != null) {

      try {

        // Convert the URL into an image
        return kit.createImage((java.awt.image.ImageProducer)url.getContent());
        
      } catch(Throwable t) { 
        t.printStackTrace();
      }

    }

    return null;

  }

  /**
   * Register an image resource for this component. 
   *
   * @param Component 
   * @param String 
   */
  public void registerImageResource(Component comp, String resourceName) {
    
    Vector v = (Vector)resourceMap.get(comp);

    if(v == null) { 
      v = new Vector();
      resourceMap.put(comp, v);
    }

    if(!v.contains(resourceName))
      v.add(resourceName);

  }
 
  /**
   * Get an Image for a Component. Lazily start to load all registered
   * images as well.
   *
   * @param String
   * @return Image
   */
  public Image getImageResource(Component comp, String resourceName) {
    
    if(resourceName.charAt(0) != '/')
      resourceName = '/' + resourceName;

    // Get a previously loaded icon
    Image img = null;
    if((img = (Image)imageMap.get(resourceName)) == null) {

      MediaTracker tracker = new MediaTracker(comp);

      // Lazy load all related resources
      Vector v = (Vector)resourceMap.get(comp);
      if(v != null) {

        resourceMap.remove(comp);
      
        // Get the image, add to the tracker
        for(Iterator i = v.iterator(); i.hasNext();)
          trackImage(tracker, (String)i.next());

      } 

      if(v == null || !v.contains(resourceName))
        trackImage(tracker, resourceName);

      // Wait for all,
      try {
        tracker.waitForAll();
      } catch(InterruptedException e) { }
      
      if(tracker.checkAll())
        img = (Image)imageMap.get(resourceName);

    }

    if(img == null)
      throw new RuntimeException("Resource not found " + resourceName);

    return img;

  }

  /**
   * Start resolving the image with the given MediaTracker
   *
   * @post Images added to the tracker are moved to the image map
   */
  private final Image trackImage(MediaTracker tracker, String resourceName) {

    Image img = loadImageResource(resourceName);
    if(img != null) {
      
      // Start loading
      tracker.addImage(img, 1);
      imageMap.put(resourceName, img);
      
    }
    
    return img;

  }


  /**
   * Get an Icon for a Component. 
   *
   * @param String
   * @return Image
   */ 
  public Icon getIconResource(Component comp, String resourceName) {

    if(comp == null)
      throw new RuntimeException("Null component!");

    if(resourceName == null)
      throw new RuntimeException("Null resource name!");

    return new ImageIcon(getImageResource(comp, resourceName));
  }

}
