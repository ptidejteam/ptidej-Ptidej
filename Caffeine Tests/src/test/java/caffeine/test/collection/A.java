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
package caffeine.test.collection;

import java.util.Hashtable;
import java.util.Vector;

/**
 * @author 	Yann-Gaël Guéhéneuc
 * @version	0.1
 */
public class A {
	public class InnerA {
		private String name;

		public InnerA(final String name) {
			this.name = name;
		}
		public String toString() {
			return this.name;
		}
	}

	private final Hashtable hashtable;
	private final Vector vector;

	public A() {
		this.hashtable = new Hashtable();
		this.hashtable.put(Integer.valueOf(2), new A.InnerA("Hello"));
		this.hashtable.put(Integer.valueOf(3), new A.InnerA("World!"));

		this.vector = new Vector();
		this.vector.add(new A.InnerA("Bonjour"));
		this.vector.addElement(new A.InnerA("le monde !"));
	}
	public void run() {
		final StringBuffer buffer = new StringBuffer(12);
		buffer.append(this.hashtable.get(Integer.valueOf(2)));
		buffer.append(' ');
		buffer.append(this.hashtable.get(Integer.valueOf(3)));
		System.out.println(buffer);

		buffer.setLength(0);
		buffer.append(this.vector.elementAt(0));
		buffer.append(' ');
		buffer.append(this.vector.elementAt(1));
		System.out.println(buffer);
	}

	public static void main(final String[] args) {
		final A a = new A();
		a.run();
	}
}
