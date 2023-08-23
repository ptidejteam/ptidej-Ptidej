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
package jct.test.rsc.snpsht.verfilesystem.flag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;

public class AbstractVerFsFlag implements IVerFsFlag {
	protected Set<VerFsFileRev> files;
	
	public AbstractVerFsFlag() {
		this.files = new HashSet<VerFsFileRev>();
	}

	public void addChild(VerFsFileRev child) {
		this.files.add(child);
	}

	public void addChildren(Collection<VerFsFileRev> children) {
		this.files.addAll(children);
	}

	public void addChildren(VerFsFileRev[] children) {
		for(VerFsFileRev child : children)
			this.files.add(child);
	}

	public void removeChild(VerFsFileRev child) {
		this.files.remove(child);
	}

	@Override
	public Set<VerFsFileRev> getChildren() {
		return this.files;
	}

}
