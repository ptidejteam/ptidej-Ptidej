package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.Date;
import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public interface ISnapshooter
extends java.lang.Iterable
{
public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(int i)
{

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(java.util.Date at)
{

}

public int getSnapshotCount()
{

}

public int getSnapshotIndex(java.util.Date d)
{

}

public jct.test.rsc.snpsht.utils.Pair getPeriodBounds(java.util.Date startTime, java.util.Date endTime) throws java.lang.IllegalArgumentException
{

}

public void addListener(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener listener)
{

}

public void removeListener(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener listener)
{

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener[] getListeners()
{

}


}
