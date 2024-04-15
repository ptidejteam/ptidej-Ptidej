package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.HashSet;
import java.util.Set;
public class SnapshotDiff
{
private java.util.Set addedElements;

private java.util.Set removedElements;

public void <init>()
{
this.<init>();
this.addedElements = new java.util.HashSet();
this.removedElements = new java.util.HashSet();

}

public void <init>(java.util.Set addedElements, java.util.Set removedElements)
{
this.<init>();
this.addedElements = addedElements;
this.removedElements = removedElements;

}

public void setAddedElements(java.util.Set addedElements)
{
this.addedElements = addedElements;

}

public java.util.Set getAddedElements()
{
return this.addedElements;

}

public void setRemovedElements(java.util.Set removedElements)
{
this.removedElements = removedElements;

}

public java.util.Set getRemovedElements()
{
return this.removedElements;

}

public boolean isDiff()
{
return (this.addedElements.size() != 0 || this.removedElements.size() != 0);

}


}
