package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.Date;
import javax.swing.event.EventListenerList;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
abstract public class AbstractSnapshooterExtender
implements jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterExtender
{
private jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter extendedSnapshooter;

private javax.swing.event.EventListenerList listeners;

public void <init>(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter extendedSnapshooter)
{
this.<init>();
this.seExtendedSnapshooter(extendedSnapshooter);
this.listeners = new javax.swing.event.EventListenerList();

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.extendedSnapshooter.setManager(manager);

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.extendedSnapshooter.getManager();

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(java.util.Date at)
{
return this.getSnapshot(this.getSnapshotIndex(at));

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotIterator iterator()
{
return new jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotIterator(this);

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter getExtendedSnapshooter()
{
return this.extendedSnapshooter;

}

public void seExtendedSnapshooter(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter extendedSnapshooter)
{
this.extendedSnapshooter = extendedSnapshooter;

}

public void addListener(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener listener)
{
this.listeners.add(class, listener);

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener[] getListeners()
{
return this.listeners.getListeners(class);

}

public void removeListener(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener listener)
{
this.listeners.remove(class, listener);

}


}
