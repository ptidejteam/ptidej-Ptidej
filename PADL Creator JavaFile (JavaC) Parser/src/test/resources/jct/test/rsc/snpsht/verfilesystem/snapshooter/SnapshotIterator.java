package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
public class SnapshotIterator
implements java.util.Iterator
{
private jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter iterateSs;

private int index;

public void <init>(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter iterateSs)
{
this.<init>();
this.index = -1;
this.iterateSs = iterateSs;
this.iterateSs.addListener(new jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener()
{
void <init>()
{
this.<init>();

}

public void snapshotContentChanged(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter source)
{

}

public void snapshotCountChanged(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter source, int oldCount, int newCount)
{
throw new java.util.ConcurrentModificationException("Iterated snapshooter modified during iteration.");

}

});

}

public boolean hasNext()
{
return this.index < this.iterateSs.getSnapshotCount() - 1;

}

public boolean hasPrevious()
{
return 0 < this.index;

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot next()
{
if(! this.hasNext()) return null;
this.index ++;
return this.current();

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot previous()
{
if(! this.hasNext()) return null;
this.index --;
return this.current();

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot current()
{
return this.iterateSs.getSnapshot(this.index);

}

public void remove()
{

}


}
