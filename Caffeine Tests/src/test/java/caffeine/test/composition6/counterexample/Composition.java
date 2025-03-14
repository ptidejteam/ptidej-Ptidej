/*
 * (c) Copyright 2002-2003 Yann-Gaël Guéhéneuc,
 * ecole des Mines de Nantes and Object Technology International, Inc.
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
package caffeine.test.composition6.counterexample;

import caffeine.Constants;

public class Composition {
	public static void main(final String[] args) {
		A a = new A(); 		// EX(A, B) = false
		a.attach(new B());	// EX(B, A) = true
		a.operation();		// LT(A, B) \in \parallel
		B b = a.getB();		// EX(B, A) = false

		// I force the collection of 'a' for garbage
		// (and the call to its finalize() method).
		a = null;
		for (int i = 0; i < Constants.VACUUM_CLEANER_LATENT_PERIOD; i++) {
			System.gc();
		}

		// I prevent the garbage collection of 'b'.
		b.operation();		// LT(B, A) \in \parallel
	}
}