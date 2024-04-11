package org.tigris.giant;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import org.tigris.gef.ui.IStatusBar;
import org.tigris.gef.ui.ToolBar;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import org.tigris.panelbeater.PanelManager;

import org.tigris.giant.persistance.AntLoader;
import org.tigris.giant.persistance.GiantLoader;

/** 
 * 
 */

public class Giant implements IStatusBar {

    //private ApplicationFrame applicationFrame;
    //private JFrame workbenchFrame;
    PanelManager panelManager;
    
    /** A statusbar (shown at bottom of window). */
    private JLabel _statusbar = new JLabel(" ");
    private ToolBar toolbar;
    private static Giant instance;
    
    ////////////////////////////////////////////////////////////////
    // main

    public static void main(String args[]) {
        instance = new Giant();
    }
    
    public static Giant getInstance() {
        return instance;
    }
    
    public Giant() {
        // init localizer and resourceloader
        Localizer.addResource(
            "GefBase",
            "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource(
            "GefPres",
            "org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());

        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
        ResourceLoader.addResourceLocation("/org/tigris/giant/Images");

        panelManager = new PanelManager();
        panelManager.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                panelManager.dispose();
            }
            public void windowClosed(WindowEvent event) {
                System.exit(0);
            }
        });

        panelManager.setTitle("giANT");

        panelManager.setJMenuBar(makeMenuBar());

        DiagramPanel diagramPanel = new DiagramPanel();
        diagramPanel.setName("untitled");
        
        ExplorerPanel explorerPanel = new ExplorerPanel();
        
        panelManager.setMode(PanelManager.INTERNAL_FRAME_MODE, PanelManager.CENTER);
        panelManager.add(diagramPanel, BorderLayout.CENTER);
        panelManager.add(explorerPanel, BorderLayout.WEST);
        panelManager.add(new EditorPanel(), BorderLayout.EAST);
        panelManager.add(new ConsolePanel(), BorderLayout.SOUTH);
        panelManager.add(new TodoPanel(), BorderLayout.SOUTH);

        panelManager.setBounds(50, 50, 850, 650);

        panelManager.setVisible(true);
    }

    /** Set up the menus and keystrokes for menu items. Subclasses can
     *  override this, or you can use setMenuBar(). */
    protected JMenuBar makeMenuBar() {
        return new MenuBar();
    }
    
    ////////////////////////////////////////////////////////////////
    // IStatusListener implementation

    /** Show a message in the statusbar. */
    public void showStatus(String msg) {
        if (_statusbar != null)
            _statusbar.setText(msg);
    }
    
    public void newDiagram() {
        panelManager.add(new DiagramPanel(), BorderLayout.CENTER);
    }
    
    public void openDiagram() {
        JFileChooser fc = new JFileChooser();
        panelManager.showOpenDialog(fc);
        File file = fc.getSelectedFile();
        File dir = fc.getCurrentDirectory();
        DiagramPanel diagramPanel = new DiagramPanel();
        diagramPanel.setName(file.getAbsolutePath());
        panelManager.add(diagramPanel, BorderLayout.CENTER);
        AntLoader antLoader = new AntLoader();
        if (antLoader.load(file)) {
            GiantLoader giantLoader = new GiantLoader();
            if (!giantLoader.load(dir, file)) {
                Layouter layouter = new Layouter();
                layouter.layout();
            }
        }
    }
}
