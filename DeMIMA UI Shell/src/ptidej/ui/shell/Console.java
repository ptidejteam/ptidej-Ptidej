/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
 * University of Montreal.
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
package ptidej.ui.shell;

import bsh.Capabilities;
import bsh.EvalError;
import bsh.Interpreter;

public class Console {
	public static void main(final String[] args) {
		if (!Capabilities.classExists("bsh.util.Util")) {
			System.out.println("Can't find the BeanShell utilities...");
		}

		if (Capabilities.haveSwing()) {
			try {
				final Interpreter interpreter = new Interpreter();
				interpreter.eval("desktopPADL()");
			}
			catch (final EvalError e) {
				System.err.println("Couldn't start desktop: " + e);
			}
		}
		else {
			System.err
				.println("Can't find javax.swing package: "
						+ " An AWT based Console is available but not built by default.");
		}
	}
}
