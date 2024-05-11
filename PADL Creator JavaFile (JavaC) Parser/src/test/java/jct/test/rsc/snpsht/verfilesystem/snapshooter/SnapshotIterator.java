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
package jct.test.rsc.snpsht.verfilesystem.snapshooter;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SnapshotIterator implements Iterator<Snapshot> {
	private ISnapshooter iterateSs;
	private int index;
	
	public SnapshotIterator(ISnapshooter iterateSs) {
		this.index = -1;
		this.iterateSs = iterateSs;
		this.iterateSs.addListener(new ISnapshooterListener() {

			@Override
			public void snapshotContentChanged(ISnapshooter source) {
			}

			@Override
			public void snapshotCountChanged(
				ISnapshooter source,
				int oldCount,
				int newCount) {
				throw new ConcurrentModificationException("Iterated snapshooter modified during iteration.");
			}
			
		});
	}

	@Override
	public boolean hasNext() {
		return this.index < this.iterateSs.getSnapshotCount()-1;
	}

	public boolean hasPrevious() {
		return 0 < this.index;
	}

	@Override
	public Snapshot next() {
		if(!hasNext()) return null;
		
		this.index++;
		return current();
	}

	public Snapshot previous() {
		if(!hasNext()) return null;
		
		this.index--;
		return current();
	}
	
	public Snapshot current() {
		return this.iterateSs.getSnapshot(this.index);
	}

	/**
	 * Not implemented, not logic to implement
	 */
	@Override
	public void remove() {
		
	}

}
