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
package jct.test.rsc.snpsht.verfilesystem;

import jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor;

public abstract class AbstractVerFsElement implements IVerFsElement {
	protected IVerFsComplexElement parent;
	protected String name;

	protected AbstractVerFsElement(
		String name,
		IVerFsComplexElement parent) {
		this.name = name;
		this.parent = parent;
	}

	@Override
	public String getPath() {
		if(this.parent == null) {
			return "/" + this.name;
		} else {
			return this.parent.getPath() + "/" + this.name;
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IVerFsComplexElement getParent() {
		return this.parent;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setParent(IVerFsComplexElement parent) {
		this.parent = parent;
	}

	@Override
	public Object accept(IVerFsVisitor visitor) {
		return visitor.visit(this);

	}
}
