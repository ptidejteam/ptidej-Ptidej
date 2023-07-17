/*
 * (c) Copyright 2001-2003 Yann-Gaël Guéhéneuc,
 * École des Mines de Nantes and Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer;

import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author 	Yann-Gaël Guéhéneuc
 */
class WindowHelp extends JFrame {
	private static final long serialVersionUID = -205512839059327053L;
	private static final Dimension WINDOW_DIMENSION = new Dimension(600, 400);

	WindowHelp(final String title, final String text) {
		this
			.setLocation(
				(int) (Constants.SCREEN_DIMENSION.getWidth() / 2 - WindowHelp.WINDOW_DIMENSION
					.getWidth() / 2),
				(int) (Constants.SCREEN_DIMENSION.getHeight() / 2 - WindowHelp.WINDOW_DIMENSION
					.getHeight() / 2));
		this.setSize(WindowHelp.WINDOW_DIMENSION);
		this.setTitle(title);

		final JEditorPane helpTextArea = new JEditorPane();
		helpTextArea.setEditorKit(new HTMLEditorKit());
		helpTextArea.setEditable(false);
		helpTextArea.setText(text);
		helpTextArea.setCaretPosition(0);
		this.getContentPane().add(new JScrollPane(helpTextArea));
	}
}
