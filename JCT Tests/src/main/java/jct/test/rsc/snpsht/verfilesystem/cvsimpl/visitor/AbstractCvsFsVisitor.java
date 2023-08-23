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
package jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor;

import jct.test.rsc.snpsht.verfilesystem.IVerFsElement;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsRepository;
import jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor;

public abstract class AbstractCvsFsVisitor implements IVerFsVisitor {

	@Override
	public Object visit(IVerFsElement toVisit) {
		try {
			return visit((VerFsRepository) toVisit);
		} catch (Exception e1) {
			try {
				return visit((VerFsFileRev) toVisit);
			} catch (Exception e2) {
				throw new IllegalArgumentException("Illegal class element : "
						+ toVisit.getClass().toString());
			}
		}
	}

	public abstract Object visit(VerFsRepository toVisit);

	public abstract Object visit(VerFsFileRev toVisit);

}
