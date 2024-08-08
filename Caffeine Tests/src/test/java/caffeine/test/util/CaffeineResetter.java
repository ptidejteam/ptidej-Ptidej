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
import caffeine.Caffeine;
import caffeine.logic.Engine;
import caffeine.logic.EngineListener;
import caffeine.logic.Event;

public class CaffeineResetter implements EngineListener {
	public void eventEmitted(final Event event) {
	}
	public void engineTerminated(
		final JIPTerm solution,
		final Engine engine,
		final long time,
		final long steps) {

		Caffeine.getUniqueInstance().stop();
	}
	public void engineStarted(final Engine engine) {
	}
	public void engineInitialized(final Engine engine) {
	}
}