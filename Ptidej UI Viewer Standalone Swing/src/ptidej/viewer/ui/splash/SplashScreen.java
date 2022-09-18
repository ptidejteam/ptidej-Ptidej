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
package ptidej.viewer.ui.splash;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;

public class SplashScreen extends Window {
	private static final long serialVersionUID = 1L;
	private final Image splashImage;

	public SplashScreen(final String strImageKey) {
		super(new Frame());
		this.setVisible(false);
		final MediaTracker mTracker = new MediaTracker(this);
		this.splashImage =
			Utils.getImage(
				strImageKey.concat(Resources.ICON),
				SplashScreen.class);
		if (this.splashImage != null) {
			mTracker.addImage(this.splashImage, 0);
			try {
				mTracker.waitForID(0);
			}
			catch (final InterruptedException e) {
			}
			this.setSize(
				this.splashImage.getWidth(this),
				this.splashImage.getHeight(this));

			final Dimension screenDim =
				Toolkit.getDefaultToolkit().getScreenSize();
			final Dimension splashDim = this.getSize();

			if (splashDim.width > screenDim.width) {
				splashDim.width = screenDim.width;
			}
			if (splashDim.height > screenDim.height) {
				splashDim.height = screenDim.height;
			}

			this.setLocation(
				(screenDim.width - splashDim.width) / 2,
				(screenDim.height - splashDim.height) / 2);
		}
	}
	public void hideNow() {
		this.hide(0);
	}
	public void hide(final int waitOnSecondsBeforeHide) {
		try {
			Thread.sleep(waitOnSecondsBeforeHide * 1000);
		}
		catch (final Exception e) {
		}
		this.setVisible(false);
	}
	public void update(final Graphics g) {
		this.paint(g);
	}
	public void paint(final Graphics g) {
		if (this.splashImage != null) {
			g.drawImage(this.splashImage, 0, 0, this);
		}
	}
	public static void main(final String[] args) {
		final SplashScreen splashScreen =
			new SplashScreen(Resources.PTIDEJ_LOGO);
		splashScreen.setVisible(true);
	}
}
