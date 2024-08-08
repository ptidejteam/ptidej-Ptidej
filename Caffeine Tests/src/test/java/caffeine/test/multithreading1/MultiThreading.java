/*
 * (c) Copyright 2001 Yann-Gaël Guéhéneuc,
 * Ecole des Mines de Nantes and Object Technology Internationl, Inc.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * Yann-Gaël Guéhéneuc, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF YANN-GAEL GUEHENEUC IS
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package caffeine.test.multithreading1;

public class MultiThreading {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
				}
				catch (final InterruptedException ie) {
					ie.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " just terminated.");
				// throw new NullPointerException();
				// System.exit(0);
			}
		}, "son").start();

		System.out.println(Thread.currentThread().getName() + " just terminated.");
		// throw new NullPointerException();
		// System.exit(0);
	}
}