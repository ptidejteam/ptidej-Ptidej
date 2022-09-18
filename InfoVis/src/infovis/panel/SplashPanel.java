/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class SplashPanel extends JEditorPane {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SplashPanel() {
        super();
        setPreferredSize(new Dimension(500, 500));
        try {
            setPage("file:spplash.html");
        }
        catch (IOException e) {
            setText(
                "<html><head><title>The InfoVis Toolkit</title></head>\n"+
                "<text>"+
                "<p>Welcome to the <b>InfoVis Toolkit</b> version 0.5beta<br>\n"+
                "Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France.</p>\n"+
                "<p>This software is published under the terms of the QPL Software License\n"+
                "a copy of which has been included with this distribution in the\n"+
                "license-infovis.txt file.</p>\n"+ 
                "</text></html>\n");
        }
        setEditable(false);
    }

}
