/* (c) Copyright 2008 and following years, Yann-Gaël Guéhéneuc,
 * ecole Polytechnique de Montreal.
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
package caffeine.test.util;

import JIP.engine.JIPTerm;
import caffeine.logic.Engine;
import caffeine.logic.EngineListener;
import caffeine.logic.Event;

public class TestDataGeneratorFromEvents implements EngineListener {
	private final StringBuffer allEvents = new StringBuffer();
	public void eventEmitted(final Event event) {
		this.allEvents.append(event.toTraceData());
		this.allEvents.append('\n');
	}
	public void engineTerminated(
		JIPTerm solution,
		Engine engine,
		long time,
		long steps) {

		System.err.println();
		System.err.println();
		System.err.println();
		System.err.println(this.allEvents);
	}
	public void engineStarted(Engine engine) {
	}
	public void engineInitialized(Engine engine) {
	}
}
