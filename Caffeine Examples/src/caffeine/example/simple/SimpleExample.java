/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.simple;

public class SimpleExample {
	public static void main(final String[] args) {
		final SimpleExample simpleExample = new SimpleExample();
		simpleExample.run();
	}
	private final void run() {
		final HelloWorld helloWorld = new HelloWorld();
		System.out.println(helloWorld.getGreetings());
	}
}