/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
import infovis.panel.MainFrameDecorator;
import agile2d.AgileJFrame;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AGLMain extends AgileJFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
    AgileJFrame frame = new AgileJFrame("OpenGL Infovis Toolkit");
    
    new MainFrameDecorator(frame);
    frame.setVisible(true);
    frame.pack();
    }

}
