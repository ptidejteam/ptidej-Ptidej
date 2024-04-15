package jct.test.rsc.snpsht.verfilesystem.comparator;
import java.util.Comparator;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
public class VerFsFileRevUpdateTimeComparator
implements java.util.Comparator
{
public void <init>()
{
this.<init>();

}

public int compare(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev o1, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev o2)
{
return o1.getUpdateTime().getTime().compareTo(o2.getUpdateTime().getTime());

}


}
