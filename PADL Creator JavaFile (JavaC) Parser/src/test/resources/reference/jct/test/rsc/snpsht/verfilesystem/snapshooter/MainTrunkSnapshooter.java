package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public class MainTrunkSnapshooter
extends jct.test.rsc.snpsht.verfilesystem.snapshooter.MainSingleBranchSnapshooter
{
public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(manager, manager.getTrunk());

}


}
