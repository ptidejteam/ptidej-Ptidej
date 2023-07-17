/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.ui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import ptidej.viewer.ui.AbstractExternalWindow;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.widget.ScrollPane;
import util.io.ProxyConsole;

public final class HelpWindow extends AbstractExternalWindow {
	private static final long serialVersionUID = 1L;

	public HelpWindow() {
		super("Help");

		final StringBuffer buffer = new StringBuffer();
		try {
			final InputStream stream =
				DesktopFrame.class.getClassLoader().getResourceAsStream(
                        "texts/Help.txt");
			final BufferedReader reader =
				new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				buffer.append('\n');
			}
			reader.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		final JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setBackground(Color.WHITE);
		textPane.setSelectionColor(super.getBackground());
		textPane.setSelectedTextColor(textPane.getBackground().darker());
		textPane.setText(buffer.toString());

		this.setSize(new Dimension(515, 500));
		this.getContentPane().add(
			new ScrollPane(
				textPane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
}
