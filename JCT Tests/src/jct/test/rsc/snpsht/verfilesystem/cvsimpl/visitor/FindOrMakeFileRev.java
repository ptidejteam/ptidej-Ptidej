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
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsRepository;

public class FindOrMakeFileRev extends AbstractCvsFsVisitor {

	private String[] pathTokens;
	private String id;
	private int index;
	private VerFsManager manager;

	public FindOrMakeFileRev(VerFsManager manager) {
		this.manager = manager;
	}

	public IVerFsElement findOrCreate(String relativePath, String id) {
		this.pathTokens = relativePath.split("/");
		this.index = 0;
		this.id = id;

		Object o = this.manager.getRoot().accept(this);

		if (o == null)
			return null;
		else
			return (IVerFsElement) o;
	}

	@Override
	public IVerFsElement visit(VerFsRepository toVisit) {
		Object o;
		IVerFsElement elem;

		if (this.index == this.pathTokens.length - 1) {
			return this.manager.addSimpleRevision(
				toVisit,
				this.pathTokens[this.index],
				this.id);
		} else {
			elem = toVisit.getChild(this.pathTokens[this.index]);

			if (elem == null) {
				elem =
					this.manager.addComplexElement(
						toVisit,
						this.pathTokens[this.index]);
			}
			
			this.index++;
			o = elem.accept(this);

			if (o == null)
				return null;
			else
				return (IVerFsElement) o;
		}
	}

	@Override
	public IVerFsElement visit(VerFsFileRev toVisit) {
		if (toVisit.getId().compareTo(this.id) == 0) {
			return toVisit;
		} else {
			return null;
		}
	}

}
