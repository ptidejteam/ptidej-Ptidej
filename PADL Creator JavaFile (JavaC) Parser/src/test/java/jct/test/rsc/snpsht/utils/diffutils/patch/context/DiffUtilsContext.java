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
package jct.test.rsc.snpsht.utils.diffutils.patch.context;

import java.io.IOException;

public class DiffUtilsContext<T extends IContextFileIO, U extends IContextFileIO> implements IDiffUtilsContext<T, U> {
	private T diffContext;
	private U newRevisionContext;
	private IContextFileReader oldRevisionContext;
	
	public DiffUtilsContext(IContextFileReader oldRevisionContext, U newRevisionContext, T diffContext) {
		this.newRevisionContext = newRevisionContext;
		this.diffContext = diffContext;
		this.oldRevisionContext = oldRevisionContext;
	}

	@Override
	public U getNewRevisionContext() {
		return this.newRevisionContext;
	}

	@Override
	public IContextFileReader getOldRevisionContext() {
		return this.oldRevisionContext;
	}

	@Override
	public T getDiffContext() {
		return this.diffContext;
	}

	@Override
	public void close() throws IOException {
		this.diffContext.close();
		this.newRevisionContext.close();
		this.oldRevisionContext.close();
	}

}
