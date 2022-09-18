
package org.tigris.giant.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigImage;
import org.tigris.gef.presentation.FigNode;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.ResourceLoader;

import org.tigris.giant.model.TargetNode;

/**
 * A Fig representing a target node of an Ant script
 * @author Bob Tarling
 */
public class TargetFig extends FigNode {
    
    Fig boundryFig;
    FigText nameFig;
    FigImage imageFig;
        
    public TargetFig() {
        //This is the code necessary to load the picture into the node so that you can paint onto the canvas
        ImageIcon targetIcon =
            ResourceLoader.lookupIconResource("NodeTarget");
        Image targetImage = targetIcon.getImage();

        Font font = new Font("sans", Font.PLAIN, 10);
        nameFig = new FigText(17,1,53,16);
        nameFig.setFont(font);
        nameFig.setJustification(FigText.JUSTIFY_LEFT);
        nameFig.setTextColor(Color.black);
        nameFig.setLineColor(Color.white);
        nameFig.setLineSpacing(-4);
        nameFig.setMovable(false);
        nameFig.setText("anon");
        
        imageFig = new FigImage(1, 1, targetImage);
        imageFig.setResizable(false);
        imageFig.setMovable(false);
        boundryFig = new FigRect(0,0,70,40);
    
        addFig(boundryFig);
        addFig(imageFig);
        addFig(nameFig);
    }
    
    public String getName() {
        return ((TargetNode)getOwner()).getName();
    }
    
    /**
     * @return
     */
    public Fig getBoundryFig() {
        return boundryFig;
    }

    public boolean isDragConnectable() {
        return false;
    }
    
    public String toString() {
        if (getOwner() == null) return null;
        return getOwner().toString();
    }
    
    
    /** Called whenever the properties of the underlying node
     * model change
     */
    public void propertyChange(PropertyChangeEvent pce) {
        super.propertyChange(pce);
        if (pce.getPropertyName().equals("name")) {
            nameFig.setText((String)pce.getNewValue());
        }
    }

    /**
     * Called to tie this fig to a model node
     */
    public void setOwner(Object node) {
        super.setOwner(node);
        String name = ((TargetNode)node).getName();
        nameFig.setText(name);
    }

    /**
     * Set the bounding box to the given rect. Figs in the group are
     * scaled and/or positioned to fit.
     * This overrides the ancestor version which has poor
     * resize/position technique.
     * Fires PropertyChange with "bounds"
     * @param x new X co ordinate for fig
     * @param y new Y co ordinate for fig
     * @param w new width for fig
     * @param h new height for fig
     */
    public void setBoundsImpl(int x, int y, int w, int h) {
        Rectangle oldBounds = getBounds();
        boundryFig.setBounds(x, y, w, h);
        imageFig.setBounds(x+1, y+1, 16, 16);
        nameFig.setBounds(x+17, y+1, w-18, h-2);
        calcBounds(); //_x = x; _y = y; _w = w; _h = h;
        firePropChange("bounds", oldBounds, getBounds());
        updateEdges();
    }
    
}
