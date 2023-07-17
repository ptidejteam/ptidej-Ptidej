/* *******************************************************************
 * Copyright (c) 2003 Contributors.
 * All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Common Public License v1.0 
 * which accompanies this distribution and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html 
 *  
 * Contributors: 
 *     Mik Kersten     initial implementation 
 * ******************************************************************/
package padl.creator.aspectjlst.util;

import org.aspectj.tools.ajc.Main;

/**
 * Wrapper for ajdoc's use of the AspectJ compiler.
 * 
 * @author Mik Kersten
 */
public class AjcCompilerWrapper extends Main {

	private static AjcCompilerWrapper INSTANCE = null;

	public static boolean hasErrors() {
		return AjcCompilerWrapper.INSTANCE.ourHandler.getErrors().length > 0;
	}

	public static void main(final String[] args) {
		AjcCompilerWrapper.INSTANCE = new AjcCompilerWrapper();
		AjcCompilerWrapper.INSTANCE.runMain(args, true);

	}
}
