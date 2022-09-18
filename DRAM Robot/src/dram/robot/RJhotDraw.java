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
package dram.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import CH.ifa.draw.samples.javadraw.JavaDrawApp;

/**
 * @author rachedsa
 * Created on 2005-01-31
*/
public class RJhotDraw {

	static int keyInput[] = { KeyEvent.VK_H, KeyEvent.VK_E, KeyEvent.VK_L,
			KeyEvent.VK_L, KeyEvent.VK_O };
	RJhotDraw() {
		//app = new JavaDrawApp();

	}
	public static void main(String[] args) throws AWTException, IOException {
		JavaDrawApp window = new JavaDrawApp();
		window.open();

		Robot robot = new Robot();

		robot.delay(1000);

		//Pour se positionner sur le rectangle dans le toolbar et activer le boutton
		robot.mouseMove(8, 170);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);

		//Pour se positionner dans le composant ou dessiner le rectancle
		robot.mouseMove(80, 170);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);
		robot.mouseMove(200, 300);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);

		// Pour se positionner dans le menu et l'activer

		robot.mouseMove(6, 30);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);

		//Pour activer exit et fermer l'application

		robot.mouseMove(10, 180);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(1000);

		System.out.println("fini");
	}

}
