// $Id: InformationDialog.java 216 2006/08/29 00:00:00 b00__1 Exp $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.argoeclipse.internal.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.argouml.i18n.Translator;

/**
 * An dialog that informs the user.
 * @author Bogdan Pistol
 */
public class InformationDialog extends Dialog {
    
    /**
     * The message of the dialog.
     */
    private String messageDetails;
    
    /**
     * A short description.
     */
    private String messageHeader;
    
    /**
     * The messageTitle of the dialog.
     */
    private String messageTitle;
    
    /**
     * The string on the Close button.
     */
    private String closeButtonText;
    
    /**
     * @param parent the parent shell
     * @param title the title of the dialog
     * @param header a short description
     * @param details the message for the user
     */
    public InformationDialog(Shell parent, String title, String header,
            String details) {
        super(parent, 0);
        messageTitle = title;
        messageDetails = details;
        messageHeader = header;
        closeButtonText = Translator.localize("button.close"); //$NON-NLS-1$
    }
    
    /**
     * Opens the dialog.
     */
    public void open () {
        Shell parent = getParent();
        final Shell shell = new Shell(parent, SWT.DIALOG_TRIM
                | SWT.APPLICATION_MODAL);
        shell.setText(messageTitle);
        GridLayout layout = new GridLayout();
        layout.verticalSpacing = 15;
        layout.marginTop = 15;
        shell.setLayout(layout);
        shell.setLayoutData(new GridData(GridData.FILL_BOTH));
        new Label(shell, SWT.NONE).setText(messageHeader);
        Text text = new Text(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
                | SWT.MULTI);
        text.setLayoutData(new GridData(GridData.FILL_BOTH));
        text.setText(messageDetails);
        text.setEditable(false);
        Button close = new Button(shell, SWT.PUSH);
        String spaces = "    "; //$NON-NLS-1$
        close.setText(spaces + closeButtonText + spaces);
        close.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, false, false));
        close.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                shell.dispose();
            }
        });
        shell.open();
        Display display = parent.getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
    
}
