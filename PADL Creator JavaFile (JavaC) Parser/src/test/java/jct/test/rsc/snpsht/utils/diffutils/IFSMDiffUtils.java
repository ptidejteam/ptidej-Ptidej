/* (c) Copyright 2008 and following years, Julien Tanteri,
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
package jct.test.rsc.snpsht.utils.diffutils;

import java.io.IOException;

import jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileIO;
import jct.test.rsc.snpsht.utils.diffutils.patch.context.IDiffUtilsContext;
import jct.test.rsc.snpsht.utils.diffutils.state.IFSMState;

public interface IFSMDiffUtils<T extends IFSMState> {
	public IDiffUtilsContext<? extends IContextFileIO, ? extends IContextFileIO> getContext();
	public void changeState(T state) throws IOException;
	public T getCurrentState();
}
