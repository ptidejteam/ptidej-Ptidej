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

import caffeine.Caffeine;
import caffeine.Constants;
import caffeine.test.Primitive;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.1
 */
public final class CaffeineLauncher extends Primitive {
	public CaffeineLauncher(final String name) {
		super(name);
	}
	public void test() {
		try {
			Caffeine
				.getUniqueInstance()
				.start(
				// "../Caffeine Tests/src/caffeine/test/collection/Test.trace",
					"../Caffeine/Rules/AllDynamicEvents.pl",
					"../Caffeine/cfparse.jar;../Caffeine/javassist.jar;../Caffeine/bin;../Caffeine Tests/bin",
					"caffeine.test.collection.A",
					new String[] { "caffeine.test.collection.A",
							"caffeine.test.collection.A$InnerA" },
					Constants.GENERATE_COLLECTION_EVENT,
					new String[][] {
							new String[] { "caffeine.test.collection.A",
									"java.util.Hashtable", "hashtable" },
							new String[] { "caffeine.test.collection.A",
									"java.util.Vector", "vector" } });
		}
		catch (final Exception e) {
			this.setProgramTerminated(true);
			this.setThrownException(e);
			throw new RuntimeException(e);
		}
	}
}