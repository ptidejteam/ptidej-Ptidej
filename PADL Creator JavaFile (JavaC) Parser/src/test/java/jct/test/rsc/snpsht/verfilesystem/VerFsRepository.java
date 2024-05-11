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

import java.util.ArrayList;
import java.util.List;

import jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor;

public class VerFsRepository extends AbstractVerFsElement
		implements IVerFsComplexElement {

	private List<IVerFsElement> children;

	protected VerFsRepository(
		String name,
		IVerFsComplexElement parent) {
		this(name, parent, new ArrayList<IVerFsElement>());
	}

	protected VerFsRepository(
		String name,
		IVerFsComplexElement parent,
		List<IVerFsElement> children) {
		super(name, parent);

		this.children = children;
	}

	@Override
	public List<IVerFsElement> getChildren() {
		return this.children;
	}

	protected void setChildren(List<IVerFsElement> children) {
		this.children = children;
	}

	protected void addChildren(List<IVerFsElement> children) {
		this.children.addAll(children);
	}

	protected void addChild(IVerFsElement child) {
		this.children.add(child);
	}

	protected void removeChildren(List<IVerFsElement> children) {
		this.children.removeAll(children);
	}

	protected void removeChild(IVerFsElement child) {
		this.children.remove(child);
	}
	
	@Override
	public boolean containsChild(IVerFsElement child) {
		return this.children.contains(child);
	}

	@Override
	public String getId() {
		return getPath();
	}

	@Override
	public boolean containsChildId(String childId) {
		for (IVerFsElement child : this.children) {
			if (childId.compareTo(child.getId())==0) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public IVerFsElement getChildId(String childId) {
		for (IVerFsElement child : this.children) {
			if (childId.compareTo(child.getId())==0) {
				return child;
			}
		}
		
		return null;
	}

	@Override
	public boolean containsChild(String childName) {
		for (IVerFsElement child : this.children) {
			if (childName.compareTo(child.getName())==0) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public IVerFsElement getChild(String childName) {
		for (IVerFsElement child : this.children) {
			if (childName.compareTo(child.getName())==0) {
				return child;
			}
		}
		
		return null;
	}

	@Override
	public String prettyPrint() {
		String pp = "+-[" + getName() + "]\n";
		String tmp;

		for (IVerFsElement fe : this.children) {
			tmp = fe.prettyPrint();
			for (String line : tmp.split("\n")) {
				pp += "| " + line + "\n";
			}
		}

		return pp;
	}
	
	@Override
	public Object accept(IVerFsVisitor visitor) {
		return visitor.visit(this);
		
	}
}
