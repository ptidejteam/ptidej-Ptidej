/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import javax.swing.WindowConstants;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.window.HelpWindow;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/20
 */
public class Controls {
	private static Controls UniqueInstance;
	public static Controls getInstance() {
		if (Controls.UniqueInstance == null) {
			Controls.UniqueInstance = new Controls();
		}
		return Controls.UniqueInstance;
	}

	private boolean areAntiPatternsListening = false;
	private boolean areCodeSmellsListening = false;
	private boolean areDesignPatternsListening = false;
	private boolean areMicroPatternsListening = false;
	private boolean areToolsListening = false;
	private boolean areVisitorsListening = false;
	private boolean canModifyRuleCards = false;

	private Controls() {
		// Yann 2007/02/20: Demo!
		// For the sake of giving away a usable demo of Ptidej,
		// I enable/disable some of its functionalities,
		// according to the presence of a certain magic file :-)
		final String directory = util.io.Files.getClassPath(DesktopFrame.class);
		final File magicFile = new File(directory + "ptidej/viewer/ui/Magic");
		if (magicFile.exists()) {
			try {
				final LineNumberReader reader =
					new LineNumberReader(new FileReader(magicFile));
				final String line = reader.readLine();
				reader.close();

				if (line.equals("Let's rock'n'roll!")) {
					this.za();
				}
				else if (line.equals("I ain't SAD!")) {
					this.zb();
				}
				else {
					this.zc();
				}
			}
			catch (final FileNotFoundException e) {
			}
			catch (final IOException e) {
			}
		}
		else {
			// TODO Does not work in a JAR.
			//	this.zc();
			this.za();
		}
	}
	public boolean areAntiPatternsListening() {
		return this.areAntiPatternsListening;
	}
	public boolean areCodeSmellsListening() {
		return this.areCodeSmellsListening;
	}
	public boolean areDesignPatternsListening() {
		return this.areDesignPatternsListening;
	}
	public boolean areMicroPatternsListening() {
		return this.areMicroPatternsListening;
	}
	public boolean areToolsListening() {
		return this.areToolsListening;
	}
	public boolean areVisitorsListening() {
		return this.areVisitorsListening;
	}
	public boolean canModifyRuleCards() {
		return this.canModifyRuleCards;
	}
	public void canModifyRuleCards(final boolean aBoolean) {
		this.canModifyRuleCards = aBoolean;
	}
	public void setAntiPatternsListening(final boolean aBoolean) {
		this.areAntiPatternsListening = aBoolean;
	}
	public void setCodeSmellsListening(final boolean aBoolean) {
		this.areCodeSmellsListening = aBoolean;
	}
	public void setDesignPatternsListening(final boolean aBoolean) {
		this.areDesignPatternsListening = aBoolean;
	}
	public void setMicroPatternsListening(final boolean aBoolean) {
		this.areMicroPatternsListening = aBoolean;
	}
	public void setToolsListening(final boolean aBoolean) {
		this.areToolsListening = aBoolean;
	}
	public void setVisitorsListening(final boolean aBoolean) {
		this.areVisitorsListening = aBoolean;
	}
	private void za() {
		this.canModifyRuleCards(true);
		this.setAntiPatternsListening(true);
		this.setCodeSmellsListening(true);
		this.setDesignPatternsListening(true);
		this.setMicroPatternsListening(true);
		this.setToolsListening(true);
		this.setVisitorsListening(true);
	}
	private void zb() {
		this.canModifyRuleCards(true);
		this.setAntiPatternsListening(true);
		this.setCodeSmellsListening(true);
		this.setDesignPatternsListening(false);
		this.setMicroPatternsListening(false);
		this.setToolsListening(false);
		this.setVisitorsListening(false);

		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("You are running a *demo* of the Ptidej tool.");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("Some functionalities are therefore disabled.");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("For a *full* version, please kindly contact:");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("Yann-Gaël Guéhéneuc, guehene@iro.umontreal.ca.\n");

		final HelpWindow helpWindow =
			(HelpWindow) SingletonBag.getInstance(HelpWindow.class);
		helpWindow
			.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		helpWindow.setVisible(true);
	}
	private void zc() {
		this.canModifyRuleCards(false);
		this.setAntiPatternsListening(true);
		this.setCodeSmellsListening(false);
		this.setDesignPatternsListening(false);
		this.setMicroPatternsListening(false);
		this.setToolsListening(false);
		this.setVisitorsListening(false);

		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("You are running a *demo* of the Ptidej tool.");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("Some functionalities are therefore disabled.");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("For a *full* version, please kindly contact:");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println("Yann-Gaël Guéhéneuc, guehene@iro.umontreal.ca.\n");

		final HelpWindow helpWindow =
			(HelpWindow) SingletonBag.getInstance(HelpWindow.class);
		helpWindow
			.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		helpWindow.setVisible(true);
	}
}
