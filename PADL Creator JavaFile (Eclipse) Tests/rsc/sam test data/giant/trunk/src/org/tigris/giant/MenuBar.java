package org.tigris.giant;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.tigris.gef.base.CmdAdjustGrid;
import org.tigris.gef.base.CmdAdjustGuide;
import org.tigris.gef.base.CmdAdjustPageBreaks;
import org.tigris.gef.base.AlignAction;
import org.tigris.gef.base.CmdCopy;
import org.tigris.gef.base.DistributeAction;
import org.tigris.gef.base.CmdExit;
import org.tigris.gef.base.NudgeAction;
import org.tigris.gef.base.CmdPaste;
import org.tigris.gef.base.CmdPrint;
import org.tigris.gef.base.CmdPrintPageSetup;
import org.tigris.gef.base.CmdRemoveFromGraph;
import org.tigris.gef.base.CmdReorder;
import org.tigris.gef.base.CmdSelectAll;
import org.tigris.gef.base.CmdSelectInvert;
import org.tigris.gef.base.CmdShowProperties;
import org.tigris.gef.util.Localizer;

import org.tigris.giant.actions.NewAction;
import org.tigris.giant.actions.OpenAction;
import org.tigris.giant.actions.SaveAction;

/**
 * 
 * @author Bob Tarling
 * @since 16-Feb-04
 */
public class MenuBar extends JMenuBar {
    
    private static final long serialVersionUID = -9185822341339688372L;

    /** Set up the menus and keystrokes for menu items. Subclasses can
     *  override this, or you can use setMenuBar(). */
    public MenuBar() {
        add(makeFileMenu());
        add(makeEditMenu());
        add(makeViewMenu());
        add(makeArrangeMenu());
        add(makeWindowMenu());
    }
    
    private JMenu makeFileMenu() {
        JMenu fileMenu = new JMenu(Localizer.localize("GefBase", "File"));
        fileMenu.setMnemonic('F');
        JMenuItem newItem = fileMenu.add(new NewAction());
        JMenuItem openItem = fileMenu.add(new OpenAction(fileMenu));
        JMenuItem saveItem = fileMenu.add(new SaveAction(fileMenu));
        CmdPrint cmdPrint = new CmdPrint();
        JMenuItem printItem = fileMenu.add(cmdPrint);
        fileMenu.add(new CmdPrintPageSetup(cmdPrint));
        JMenuItem exitItem = fileMenu.add(new CmdExit());

        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK);
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK);
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK);
        KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK);
        KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK);

        newItem.setAccelerator(ctrlN);
        openItem.setAccelerator(ctrlO);
        saveItem.setAccelerator(ctrlS);
        printItem.setAccelerator(ctrlP);
        exitItem.setAccelerator(altF4);

        return fileMenu;
    }

    private JMenu makeEditMenu() {
        JMenu editMenu = new JMenu(Localizer.localize("GefBase", "Edit"));
        editMenu.setMnemonic('E');

        JMenu select = new JMenu(Localizer.localize("GefBase", "Select"));
        editMenu.add(select);
        select.add(new CmdSelectAll());
        select.add(new CmdSelectInvert());

        editMenu.addSeparator();

        JMenuItem copyItem = editMenu.add(new CmdCopy());
        copyItem.setMnemonic('C');
        JMenuItem pasteItem = editMenu.add(new CmdPaste());
        pasteItem.setMnemonic('P');

        JMenuItem deleteItem = editMenu.add(new CmdRemoveFromGraph());

        KeyStroke delKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK);
        KeyStroke ctrlC =  KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK);
        KeyStroke ctrlV =  KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK);
        
        deleteItem.setAccelerator(delKey);
        copyItem.setAccelerator(ctrlC);
        pasteItem.setAccelerator(ctrlV);

        return editMenu;
    }

    private JMenu makeViewMenu() {
        JMenu viewMenu = new JMenu(Localizer.localize("GefBase", "View"));
        viewMenu.setMnemonic('V');
        viewMenu.add(new CmdShowProperties());
        viewMenu.addSeparator();
        viewMenu.add(new CmdAdjustGrid());
        viewMenu.add(new CmdAdjustGuide());
        viewMenu.add(new CmdAdjustPageBreaks());

        return viewMenu;
    }
    
    private JMenu makeArrangeMenu() {
        JMenu arrangeMenu = new JMenu(Localizer.localize("GefBase", "Arrange"));
        add(arrangeMenu);
        arrangeMenu.setMnemonic('A');

        JMenu align = new JMenu(Localizer.localize("GefBase", "Align"));
        arrangeMenu.add(align);
        align.add(new AlignAction(AlignAction.ALIGN_TOPS));
        align.add(new AlignAction(AlignAction.ALIGN_BOTTOMS));
        align.add(new AlignAction(AlignAction.ALIGN_LEFTS));
        align.add(new AlignAction(AlignAction.ALIGN_RIGHTS));
        align.add(new AlignAction(AlignAction.ALIGN_H_CENTERS));
        align.add(new AlignAction(AlignAction.ALIGN_V_CENTERS));
        align.add(new AlignAction(AlignAction.ALIGN_TO_GRID));

        JMenu distribute =
            new JMenu(Localizer.localize("GefBase", "Distribute"));
        arrangeMenu.add(distribute);
        distribute.add(new DistributeAction(DistributeAction.H_SPACING));
        distribute.add(new DistributeAction(DistributeAction.H_CENTERS));
        distribute.add(new DistributeAction(DistributeAction.V_SPACING));
        distribute.add(new DistributeAction(DistributeAction.V_CENTERS));

        JMenu reorder = new JMenu(Localizer.localize("GefBase", "Reorder"));
        arrangeMenu.add(reorder);
        JMenuItem toBackItem = reorder.add(new CmdReorder(CmdReorder.SEND_TO_BACK));
        JMenuItem toFrontItem = reorder.add(new CmdReorder(CmdReorder.BRING_TO_FRONT));
        JMenuItem backwardItem = reorder.add(new CmdReorder(CmdReorder.SEND_BACKWARD));
        JMenuItem forwardItem = reorder.add(new CmdReorder(CmdReorder.BRING_FORWARD));

        JMenu nudge = new JMenu(Localizer.localize("GefBase", "Nudge"));
        arrangeMenu.add(nudge);
        nudge.add(new NudgeAction(NudgeAction.LEFT));
        nudge.add(new NudgeAction(NudgeAction.RIGHT));
        nudge.add(new NudgeAction(NudgeAction.UP));
        nudge.add(new NudgeAction(NudgeAction.DOWN));

        KeyStroke ctrlB =  KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK);
        KeyStroke ctrlF =  KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK);
        KeyStroke sCtrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
        KeyStroke sCtrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);

        toBackItem.setAccelerator(sCtrlB);
        toFrontItem.setAccelerator(sCtrlF);
        backwardItem.setAccelerator(ctrlB);
        forwardItem.setAccelerator(ctrlF);
        
        return arrangeMenu;
    }
    
    private JMenu makeWindowMenu() {
        JMenu windowMenu = new JMenu(Localizer.localize("GefBase", "Window"));
        add(windowMenu);
        windowMenu.setMnemonic('W');
        return windowMenu;
    }
}
