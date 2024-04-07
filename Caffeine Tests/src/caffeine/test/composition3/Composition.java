/*
 * (c) Copyright 2002-2003 Yann-Gaël Guéhéneuc,
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
package caffeine.test.composition3;

import caffeine.Constants;

public final class Composition {
	public static void main(final String[] args) {
		A a1 = new A();
		final A a2 = new A();
		final A a3 = new A();
		final A a4 = new A();
		a1.operation();

		// I force the collection of 'a1' for garbage
		// (and the call to its finalize() method).
		// This test shows the limitation of Sun's VM
		// implementation: The instance of A is garbage-
		// collected *before* its instance of B!!!!!!
		a1 = null;
		for (int i = 0; i < Constants.VACUUM_CLEANER_LATENT_PERIOD; i++) {
			System.gc();
		}

		a2.operation();
		a3.operation();
		a4.operation();
	}
}