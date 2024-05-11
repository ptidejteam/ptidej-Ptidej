/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.utils.diffutils.patch.unified;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jct.test.rsc.snpsht.utils.diffutils.IFSMDiffUtils;
import jct.test.rsc.snpsht.utils.diffutils.patch.IPatch;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.ContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.DiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState;
import jct.test.rsc.snpsht.utils.diffutils.patch.state.PatchStartState;
import jct.test.rsc.snpsht.utils.diffutils.state.IFSMEndState;

public class UniPatch implements IFSMDiffUtils<IPatchFSMState>, IPatch {
	private IDiffUtilsContext<IContextFileReader, IContextFileWriter> context;
	private IPatchFSMState currentState;

	/* (non-Javadoc)
	 * @see jct.test.rsc.snpsht.utils.diffutils.patch.IPatch#patch(java.io.InputStream, java.io.OutputStream, java.io.InputStream)
	 */
	@Override
	public boolean patch(
		InputStream oldRevision,
		OutputStream newRevision,
		InputStream patch) throws IOException {
		// Build context
		this.context =
			new DiffUtilsContext<IContextFileReader, IContextFileWriter>(
				new ContextFileReader(oldRevision),
				new ContextFileWriter(newRevision),
				new ContextFileReader(patch));
		
		// Load start state
		this.currentState = new PatchStartState(this);
		
		// Run FSM
		this.currentState.process();
		
		this.context.close();
		
		return (this.currentState instanceof IFSMEndState);
	}

	/* (non-Javadoc)
	 * @see jct.test.rsc.snpsht.utils.diffutils.patch.IPatch#getContext()
	 */
	@Override
	public IDiffUtilsContext<IContextFileReader, IContextFileWriter> getContext() {
		return this.context;
	}

	/* (non-Javadoc)
	 * @see jct.test.rsc.snpsht.utils.diffutils.patch.IPatch#changeState(jct.test.rsc.snpsht.utils.diffutils.patch.state.IPatchFSMState)
	 */
	@Override
	public void changeState(IPatchFSMState state) throws IOException {
		this.currentState = state;
		this.currentState.process();
	}

	/* (non-Javadoc)
	 * @see jct.test.rsc.snpsht.utils.diffutils.patch.IPatch#getCurrentState()
	 */
	@Override
	public IPatchFSMState getCurrentState() {
		return this.currentState;
	}

}
