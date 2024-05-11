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
package jct.test.rsc.snpsht.verfilesystem.snapshooter;

import java.util.Date;
import javax.swing.event.EventListenerList;

import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

public abstract class AbstractSnapshooterExtender implements
		ISnapshooterExtender {

	private ISnapshooter extendedSnapshooter;
	private EventListenerList listeners;

	public AbstractSnapshooterExtender(ISnapshooter extendedSnapshooter) {
		seExtendedSnapshooter(extendedSnapshooter);
		this.listeners = new EventListenerList();
	}

	@Override
	public void setManager(VerFsManager manager) {
		this.extendedSnapshooter.setManager(manager);
	}

	@Override
	public VerFsManager getManager() {
		return this.extendedSnapshooter.getManager();
	}

	@Override
	public Snapshot getSnapshot(Date at) {
		return getSnapshot(getSnapshotIndex(at));
	}

	@Override
	public SnapshotIterator iterator() {
		return new SnapshotIterator(this);
	}

	@Override
	public ISnapshooter getExtendedSnapshooter() {
		return this.extendedSnapshooter;
	}

	@Override
	public void seExtendedSnapshooter(ISnapshooter extendedSnapshooter) {
		this.extendedSnapshooter = extendedSnapshooter;
	}

	@Override
	public void addListener(ISnapshooterListener listener) {
		this.listeners.add(ISnapshooterListener.class, listener);
	}

	@Override
	public ISnapshooterListener[] getListeners() {
		return this.listeners.getListeners(ISnapshooterListener.class);
	}

	@Override
	public void removeListener(ISnapshooterListener listener) {
		this.listeners.remove(ISnapshooterListener.class, listener);
	}

}
