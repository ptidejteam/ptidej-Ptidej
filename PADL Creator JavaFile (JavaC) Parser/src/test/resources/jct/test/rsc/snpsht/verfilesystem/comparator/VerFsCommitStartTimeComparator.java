package jct.test.rsc.snpsht.verfilesystem.comparator;
import java.util.Comparator;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
public class VerFsCommitStartTimeComparator
implements java.util.Comparator
{
public void <init>()
{
this.<init>();

}

public int compare(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit o1, jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit o2)
{
return o1.getCommittingTime().getTime().compareTo(o2.getCommittingTime().getTime());

}


}
